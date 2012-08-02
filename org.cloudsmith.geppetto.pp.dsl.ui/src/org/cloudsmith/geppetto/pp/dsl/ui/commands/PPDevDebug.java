/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.commands;

import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.dsl.eval.PPExpressionEquivalenceCalculator;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.CSSDomFormatter;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext.FormattingContextProvider;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.cloudsmith.xtext.textflow.ITextFlow.WithText;
import org.cloudsmith.xtext.textflow.TextFlowWithDebugRecording;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.nodemodel.BidiIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentUtil;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * A command to use for development debugging purposes.
 * The intent is to install this using the org.cloudsmith.geppetto.pp.dsl.ui.devdebug
 * fragment which makes it visible in the outline menu.
 * 
 */
public class PPDevDebug extends AbstractHandler {
	public static String compactDump(INode node, boolean showHidden) {
		StringBuilder result = new StringBuilder();
		try {
			compactDump(node, showHidden, "", result);
		}
		catch(IOException e) {
			return e.getMessage();
		}
		return result.toString();
	}

	private static void compactDump(INode node, boolean showHidden, String prefix, Appendable result)
			throws IOException {
		if(!showHidden && node instanceof ILeafNode && ((ILeafNode) node).isHidden())
			return;
		if(prefix.length() != 0) {
			result.append("\n");
			result.append(prefix);
		}
		if(node instanceof ICompositeNode) {
			result.append(new GrammarElementTitleSwitch().doSwitch(node.getGrammarElement()));
			String newPrefix = prefix + "  ";
			result.append(" {");
			BidiIterator<INode> children = ((ICompositeNode) node).getChildren().iterator();
			while(children.hasNext()) {
				INode child = children.next();
				compactDump(child, showHidden, newPrefix, result);
			}
			result.append("\n");
			result.append(prefix);
			result.append("}");
		}
		else if(node instanceof ILeafNode) {
			if(((ILeafNode) node).isHidden())
				result.append("hidden ");
			if(node.getGrammarElement() == null)
				result.append("error");
			else
				result.append(new GrammarElementTitleSwitch().doSwitch(node.getGrammarElement()));
			result.append(" => '");
			result.append(node.getText());
			result.append("'");
		}
		else if(node == null) {
			result.append("(null)");
		}
		else {
			result.append("unknown type ");
			result.append(node.getClass().getName());
		}
	}

	@Inject
	@Named(PPUiConstants.DEBUG_OPTION_PARSER)
	private ITracer tracer;

	@Inject
	private IContainer.Manager manager;

	@Inject
	private IResourceDescriptions descriptionIndex;

	@Inject
	IQualifiedNameConverter converter;

	@Inject
	private Provider<PPExpressionEquivalenceCalculator> eqProvider;

	@Inject
	private ISerializer serializer;

	@Inject
	IDomModelFormatter domFormatter;

	@Inject
	FormattingContextProvider formattingContextProvider;

	@Inject
	private DomNodeLayoutFeeder layoutFeeder;

	@Inject
	private Provider<DomCSS> cssProvider;

	private TextFlowWithDebugRecording recordedDebugFormatResult;

	@Inject
	public PPDevDebug() {

	}

	private IStatus checkExpressionEq(XtextResource resource) {
		PuppetManifest manifest = (PuppetManifest) resource.getContents().get(0);
		EList<Expression> statements = manifest.getStatements();
		if(statements.get(0) instanceof AssignmentExpression == false)
			return Status.OK_STATUS;
		Expression rightExpr = ((AssignmentExpression) statements.get(0)).getRightExpr();
		if(rightExpr instanceof LiteralList == false)
			return Status.OK_STATUS;
		LiteralList testList = (LiteralList) rightExpr;
		int i = 1;
		int ok = 0;
		int failed = 0;
		PPExpressionEquivalenceCalculator eq = eqProvider.get();
		for(Expression e : testList.getElements()) {
			if(e instanceof LiteralList == false) {
				System.err.println("Not a sublist");
				return Status.OK_STATUS;
			}
			LiteralList element = (LiteralList) e;
			if(element.getElements().size() != 3) {
				System.err.println("Not 3 elements");
				return Status.OK_STATUS;
			}
			List<Expression> triplet = element.getElements();

			if(triplet.get(0) instanceof LiteralBoolean == false) {
				System.err.println("first must be boolean");
				return Status.OK_STATUS;
			}
			boolean expected = ((LiteralBoolean) triplet.get(0)).isValue();
			boolean result = eq.isEquivalent(triplet.get(1), triplet.get(2));

			StringBuilder builder = new StringBuilder();
			builder.append("[");
			builder.append(i++);
			builder.append("] ");
			if(result == expected) {
				ok++;
				builder.append("passed :");
			}
			else {
				builder.append("failed: ");
				failed++;
			}
			builder.append(NodeModelUtils.getNode(triplet.get(1)).getText());
			builder.append("  <--> ");
			builder.append(NodeModelUtils.getNode(triplet.get(1)).getText());
			System.out.println(builder);

		}
		System.out.println("eq test complete passed[" + ok + "] failed[" + failed + "]");
		return Status.OK_STATUS;
	}

	private IStatus doDebug(XtextResource resource) {
		return formattedDomDump(resource);
		// return visibleResourcesDump(resource);
		// return checkExpressionEq(resource);
	}

	public void dumpParseTree(XtextResource resource) {
		System.out.println("DUMP PARSE TREE: [");
		System.out.println(compactDump(resource.getParseResult().getRootNode(), false));
		System.out.println("]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("=> Running PPDevDebug...(");

		EvaluationContext ctx = (EvaluationContext) event.getApplicationContext();
		String pluginid = PPActivator.getInstance().getBundle().getSymbolicName();
		Object editor = ctx.getVariable("activeEditor");
		if(editor == null || !(editor instanceof XtextEditor)) {
			return new Status(IStatus.ERROR, pluginid, "Handler invoked on wrong type of editor: XtextEditor");
		}
		XtextEditor xtextEditor = (XtextEditor) editor;
		IXtextDocument xtextDocument = XtextDocumentUtil.get(xtextEditor);
		if(xtextDocument == null) {
			return new Status(IStatus.ERROR, pluginid, "No document found in current editor");
		}
		IStatus result = xtextDocument.readOnly(new IUnitOfWork<IStatus, XtextResource>() {
			@Override
			public IStatus exec(XtextResource state) throws Exception {
				return doDebug(state);
			}
		});
		System.out.println("DEVDEBUG DONE STATUS : " + result.toString() + "\n)");
		return null; // dictated by Handler API
	}

	/**
	 * Performs a format and then dumps the result to stdout.
	 * 
	 * @param resource
	 * @return
	 */
	private IStatus formattedDomDump(XtextResource resource) {
		if(serializer instanceof DomBasedSerializer == false)
			return new Status(
				IStatus.ERROR, "org.cloudsmith.geppetto.pp.dsl.ui", "Not configured to use DomBasedSerializer");
		DomBasedSerializer domSerializer = ((DomBasedSerializer) serializer);
		IDomNode dom = domSerializer.serializeToDom(resource.getContents().get(0), false);
		ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;

		// ReplaceRegion r = domFormatter.format(dom, null /* all text */, formattingContextProvider.get(false), errors);
		ReplaceRegion r = getDomFormatter().format(
			dom, null /* all text */, formattingContextProvider.get(false), errors);

		serializer.serialize(resource.getContents().get(0), SaveOptions.newBuilder().format().getOptions());

		System.out.println("Dom Dump after formatting:");
		System.out.print(DomModelUtils.compactDump(dom, true));
		System.out.println("");

		System.out.println("Recorded TextFlow:\n");
		StringBuilder textFlowOperations = new StringBuilder();
		recordedDebugFormatResult.appendTo(textFlowOperations);
		System.out.println(textFlowOperations.toString());

		return Status.OK_STATUS;

	}

	private IDomModelFormatter getDomFormatter() {
		return new CSSDomFormatter(cssProvider, layoutFeeder) {
			@Override
			protected WithText getTextFlow(IFormattingContext context) {
				recordedDebugFormatResult = new TextFlowWithDebugRecording(context);
				return recordedDebugFormatResult;
			}
		};
	}

	public void listAllResources(Resource myResource, IResourceDescriptions index) {
		for(IResourceDescription visibleResourceDesc : index.getAllResourceDescriptions()) {
			for(IEObjectDescription objDesc : visibleResourceDesc.getExportedObjects())
				System.out.println("\texported: " + converter.toString(objDesc.getQualifiedName()) + " type: " +
						objDesc.getEClass().getName());
			System.out.println(visibleResourceDesc.getURI());
		}

	}

	public void listVisibleResources(Resource myResource, IResourceDescriptions index) {
		IResourceDescription descr = index.getResourceDescription(myResource.getURI());
		for(IContainer visibleContainer : manager.getVisibleContainers(descr, index)) {
			for(IResourceDescription visibleResourceDesc : visibleContainer.getResourceDescriptions()) {
				for(IEObjectDescription objDesc : visibleResourceDesc.getExportedObjects())
					System.out.println("\texported: " + converter.toString(objDesc.getQualifiedName()) + " type: " +
							objDesc.getEClass().getName());
				System.out.println(visibleResourceDesc.getURI());
			}
		}
	}

	private IStatus visibleResourcesDump(XtextResource resource) {

		// System.out.println("ALL RESOURCES:");
		// listAllResources(resource, descriptionIndex);
		System.out.println("VISIBLE RESOURCES:");
		listVisibleResources(resource, descriptionIndex);
		if(tracer.isTracing()) {
			dumpParseTree(resource);
		}
		return Status.OK_STATUS;
	}

}
