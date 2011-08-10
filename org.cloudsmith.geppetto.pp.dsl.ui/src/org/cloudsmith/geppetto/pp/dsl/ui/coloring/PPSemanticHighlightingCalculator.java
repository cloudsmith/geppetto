/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.coloring;

import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.dsl.adapters.DocumentationAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.DocumentationAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPDocumentationParser.DocNode;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;

/**
 * Highlighting for puppet.
 * 
 */
public class PPSemanticHighlightingCalculator implements ISemanticHighlightingCalculator {
	private static class ZeroLengthFilteredAcceptorWrapper implements IHighlightedPositionAcceptor {
		private IHighlightedPositionAcceptor wrapped;

		public ZeroLengthFilteredAcceptorWrapper(IHighlightedPositionAcceptor wrapped) {
			this.wrapped = wrapped;
		}

		// @Override
		public void addPosition(int offset, int length, String... id) {
			// FOR DEBUGGING
			// StringBuffer buf = new StringBuffer();
			// buf.append(offset);
			// buf.append(", ");
			// buf.append(length);
			// for(String s : id)
			// buf.append(", ").append(s);
			// buf.append("\n");
			// System.err.print(buf.toString());
			if(length == 0)
				return;
			if(length < 0)
				return;
			wrapped.addPosition(offset, length, id);
		}

	}

	@Inject
	private PPDocumentationParser docParser;

	private PPGrammarAccess grammarAccess;

	private AbstractRule ruleVariable;

	private AbstractRule ruleSqText;

	// private AbstractRule ruleDQT_QUOTE;

	private AbstractRule ruleDQT_DOLLAR;

	private AbstractRule ruleDQ_STRING;

	private AbstractRule ruleExpression;

	private AbstractRule ruleUNION_VARIABLE_OR_NAME;

	private AbstractRule ruleUnionNameOrReference;

	private final PolymorphicDispatcher<Void> highlightDispatcher = new PolymorphicDispatcher<Void>(
		"highlight", 2, 2, Collections.singletonList(this), new PolymorphicDispatcher.ErrorHandler<Void>() {
			public Void handle(Object[] params, Throwable e) {
				handleError(params, e);
				return null;
			}
		});

	// // navigate to the parse node corresponding to the semantic object and
	// // fetch the leaf node that corresponds to the first feature with the given
	// // name, or the assignment of a composite node to first feature with given name.
	// //
	// public AbstractNode getFirstFeatureNode(EObject semantic, String feature) {
	// return NodeUtils.getFirstFeatureNode(semantic, feature);
	// }

	// // Sample semantic highlighting (highlighting based on model state)
	// //
	// public void highlight(SingleQuotedString o, IHighlightedPositionAcceptor acceptor){
	// highlightObject(o, DefaultHighlightingConfiguration.STRING_ID, acceptor);
	// }

	@Inject
	public PPSemanticHighlightingCalculator(IGrammarAccess grammarAccess) {
		this.grammarAccess = (PPGrammarAccess) grammarAccess;
		// get rules from grammar access to make comparisions of rule calls faster
		setupRules();
	}

	public void doHighlight(Object o, IHighlightedPositionAcceptor acceptor) {
		highlightDispatcher.invoke(o, acceptor);
	}

	protected void handleError(Object[] params, Throwable e) {
		Exceptions.throwUncheckedException(e);
	}

	public void highlight(Definition semantic, IHighlightedPositionAcceptor acceptor) {
		DocumentationAdapter adapter = DocumentationAdapterFactory.eINSTANCE.adapt(semantic);
		if(adapter != null && adapter.getNodes() != null) {
			List<DocNode> docNodes = docParser.parse(adapter.getNodes());
			for(DocNode dn : docNodes) {
				acceptor.addPosition(dn.getOffset(), dn.getLength(), highlightIDForDocStyle(dn.getStyle()));
			}
		}
	}

	/**
	 * A default 'do nothing' highlighting handler
	 */
	public void highlight(EObject o, IHighlightedPositionAcceptor acceptor) {
		// DO NOTHING

		// Uncomment next For debugging, and seeing opportunities for syntax highlighting
		// System.err.println("Missing highlight() method for: "+ o.getClass().getSimpleName());
	}

	public void highlight(HostClassDefinition semantic, IHighlightedPositionAcceptor acceptor) {
		DocumentationAdapter adapter = DocumentationAdapterFactory.eINSTANCE.adapt(semantic);
		if(adapter != null && adapter.getNodes() != null) {
			List<DocNode> docNodes = docParser.parse(adapter.getNodes());
			for(DocNode dn : docNodes) {
				acceptor.addPosition(dn.getOffset(), dn.getLength(), highlightIDForDocStyle(dn.getStyle()));
			}
		}
	}

	public void highlight(INode o, IHighlightedPositionAcceptor acceptor) {
		// what in grammar created this node
		EObject gElem = o.getGrammarElement();

		ruleCall: if(gElem instanceof RuleCall) {
			AbstractRule rule = ((RuleCall) gElem).getRule();
			// String ruleName = ((RuleCall) gElem).getRule().getName();

			if(rule == null)
				break ruleCall;

			// need to set default since keywords may be included
			// TODO: should be fixed by modifying the highligtinglexer
			if(ruleUnionNameOrReference == rule)
				acceptor.addPosition(o.getOffset(), o.getLength(), DefaultHighlightingConfiguration.DEFAULT_ID);

			if(ruleVariable == rule || ruleUNION_VARIABLE_OR_NAME == rule)
				acceptor.addPosition(o.getOffset(), o.getLength(), PPHighlightConfiguration.VARIABLE_ID);
			else if(ruleSqText == rule)
				acceptor.addPosition(o.getOffset(), o.getLength(), DefaultHighlightingConfiguration.STRING_ID);

			else if(ruleDQT_DOLLAR == rule /* || ruleDQT_QUOTE == rule */|| ruleDQ_STRING == rule)
				acceptor.addPosition(o.getOffset(), o.getLength(), PPHighlightConfiguration.TEMPLATE_TEXT_ID);
			else if(ruleExpression == rule) {
				EObject semantic = o.getSemanticElement();
				if(semantic instanceof ExpressionTE) {
					if(((ExpressionTE) semantic).getExpression() instanceof LiteralNameOrReference)
						acceptor.addPosition(o.getOffset(), o.getLength(), PPHighlightConfiguration.VARIABLE_ID);

				}
			}
		}
		if(gElem instanceof Keyword) {
			if(((Keyword) gElem).getValue().equals("\""))
				acceptor.addPosition(o.getOffset(), o.getLength(), DefaultHighlightingConfiguration.STRING_ID);
		}
	}

	public void highlight(NodeDefinition semantic, IHighlightedPositionAcceptor acceptor) {
		DocumentationAdapter adapter = DocumentationAdapterFactory.eINSTANCE.adapt(semantic);
		if(adapter != null && adapter.getNodes() != null) {
			List<DocNode> docNodes = docParser.parse(adapter.getNodes());
			for(DocNode dn : docNodes) {
				acceptor.addPosition(dn.getOffset(), dn.getLength(), highlightIDForDocStyle(dn.getStyle()));
			}
		}
	}

	public void highlight(PuppetManifest model, IHighlightedPositionAcceptor acceptor) {
		TreeIterator<EObject> all = model.eAllContents();
		while(all.hasNext())
			doHighlight(all.next(), acceptor);
	}

	public void highlight(ResourceExpression expr, IHighlightedPositionAcceptor acceptor) {
		EObject resourceExpr = expr.getResourceExpr();
		if(resourceExpr != null) {
			TreeIterator<EObject> all = resourceExpr.eAllContents();
			int counter = 0;
			while(all.hasNext()) {
				counter++;
				EObject x = all.next();
				if(x instanceof LiteralNameOrReference)
					highlightObject(x, PPHighlightConfiguration.RESOURCE_REF_ID, acceptor);
			}
			if(counter < 1)
				highlightObject(resourceExpr, PPHighlightConfiguration.RESOURCE_REF_ID, acceptor);
		}
		for(ResourceBody body : expr.getResourceData()) {
			if(body.getNameExpr() != null) {
				Expression nameExpr = body.getNameExpr();
				// TODO: FIX THIS WORKAROUND
				// See https://github.com/cloudsmith/geppetto/issues/72
				// if(nameExpr instanceof DoubleQuotedString &&
				// TextExpressionHelper.hasInterpolation((DoubleQuotedString) nameExpr))
				// continue;
				ICompositeNode node = NodeModelUtils.getNode(nameExpr);
				if(node != null) {
					acceptor.addPosition(node.getOffset(), node.getLength(), PPHighlightConfiguration.RESOURCE_TITLE_ID);
				}
			}
		}
	}

	private String highlightIDForDocStyle(int docStyle) {
		switch(docStyle) {
			case PPDocumentationParser.HEADING_1:
				return PPHighlightConfiguration.DOC_HEADING1_ID;
			case PPDocumentationParser.HEADING_2:
				return PPHighlightConfiguration.DOC_HEADING2_ID;
			case PPDocumentationParser.HEADING_3:
				return PPHighlightConfiguration.DOC_HEADING3_ID;
			case PPDocumentationParser.HEADING_4:
				return PPHighlightConfiguration.DOC_HEADING4_ID;
			case PPDocumentationParser.HEADING_5:
				return PPHighlightConfiguration.DOC_HEADING5_ID;
			case PPDocumentationParser.BOLD:
				return PPHighlightConfiguration.DOC_BOLD_ID;
			case PPDocumentationParser.ITALIC:
				return PPHighlightConfiguration.DOC_ITALIC_ID;

			case PPDocumentationParser.FIXED: // fall through
			case PPDocumentationParser.VERBATIM:
				return PPHighlightConfiguration.DOC_FIXED_ID;
			case PPDocumentationParser.COMMENT:
				return DefaultHighlightingConfiguration.COMMENT_ID;
			case PPDocumentationParser.PLAIN:
				return PPHighlightConfiguration.DOC_PLAIN_ID;
			default: // fall through
				return PPHighlightConfiguration.DOCUMENTATION_ID;
		}
	}

	private void highlightObject(EObject semantic, String highlightID, IHighlightedPositionAcceptor acceptor) {
		INode node = NodeModelUtils.getNode(semantic);
		if(node == null) {
			// TODO: WARNING - no node
			return;
		}
		acceptor.addPosition(node.getOffset(), node.getLength(), highlightID);
	}

	public boolean isSpecialSpace(char c) {
		switch(c) {
			case '\u00A0': // NBSP
			case '\u1680': // OGHAM SPACE MARK");
			case '\u2000': // EN QUAD");
			case '\u2001': // EM QUAD");
			case '\u2002': // EN SPACE");
			case '\u2003': // EM SPACE");
			case '\u2004': // THREE-PER-EM SPACE");
			case '\u2005': // FOUR-PER-EM SPACE");
			case '\u2006': // SIX-PER-EM SPACE");
			case '\u2007': // FIGURE SPACE");
			case '\u2008': // PUNCTUATION SPACE");
			case '\u2009': // THIN SPACE");
			case '\u200A': // HAIR SPACE");
			case '\u200B': // ZERO WIDTH SPACE");
			case '\u202F': // NARROW NO-BREAK SPACE");
			case '\u3000': // IDEOGRAPHIC SPACE");
				return true;
		}
		return false;
	}

	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		if(resource == null)
			return;
		acceptor = new ZeroLengthFilteredAcceptorWrapper(acceptor);
		// highligting based on inspection of parser nodes
		provideNodeBasedHighlighting(resource, acceptor);

		// highlighting based on created model
		provideSemanticHighlighting(resource, acceptor);

		// highligting based on the text itself
		provideTextualHighlighting(resource, acceptor);
	}

	/**
	 * Iterate over parser nodes and provide highlighting based on rule calls.
	 * 
	 * @param resource
	 * @param acceptor
	 */
	protected void provideNodeBasedHighlighting(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		BidiTreeIterable<INode> allNodes = resource.getParseResult().getRootNode().getAsTreeIterable();
		for(INode node : allNodes) {
			EObject gElem = node.getGrammarElement();
			if(gElem instanceof RuleCall) {
				doHighlight(node, acceptor);
			}
			else if(gElem instanceof Keyword) {
				doHighlight(node, acceptor);
			}
		}
	}

	// NOTE: Currently unused, and needs to be modified for Xtext 2.0
	// // helper method that takes care of highlighting the first feature element
	// // of a semantic object using a given text style ID
	// private void highlightFirstFeature(EObject semobject, String featurename, String highlightID,
	// IHighlightedPositionAcceptor acceptor) {
	// // fetch the parse node for the entity
	// AbstractNode nodetohighlight = getFirstFeatureNode(semobject, featurename);
	// if(nodetohighlight == null) {
	// // TODO: WARNING - Could not find node
	// return;
	// }
	// acceptor.addPosition(nodetohighlight.getOffset(), nodetohighlight.getLength(), highlightID);
	// }

	/**
	 * Iterate over the generated model and provide highlighting
	 * 
	 * @param resource
	 * @param acceptor
	 */
	private void provideSemanticHighlighting(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		EList<EObject> contents = resource.getContents();
		if(contents == null || contents.size() == 0)
			return; // nothing there at all - probably an empty file
		PuppetManifest model = (PuppetManifest) contents.get(0);
		doHighlight(model, acceptor);
	}

	public void provideTextualHighlighting(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		ICompositeNode root = resource.getParseResult().getRootNode();
		String text = root.getText();
		int limit = text.length();
		for(int i = 0; i < limit; i++)
			if(isSpecialSpace(text.charAt(i)))
				acceptor.addPosition(root.getOffset() + i, 1, PPHighlightConfiguration.SPECIAL_SPACE_ID);
		// int fromIndex = 0;
		// for(fromIndex = text.indexOf('\u00A0', fromIndex); fromIndex != -1; fromIndex = text.indexOf(
		// '\u00A0', fromIndex + 1))
		// acceptor.addPosition(root.getOffset() + fromIndex, 1, PPHighlightConfiguration.SPECIAL_SPACE_ID);
	}

	/**
	 * Set up rules for faster comparison
	 */
	private void setupRules() {
		ruleVariable = grammarAccess.getDollarVariableRule();
		ruleSqText = grammarAccess.getSqTextRule();
		// ruleDQT_QUOTE = grammarAccess.getDQT_QUOTERule();
		ruleDQT_DOLLAR = grammarAccess.getDQT_DOLLARRule();
		ruleDQ_STRING = grammarAccess.getDoubleStringCharactersRule();
		ruleExpression = grammarAccess.getExpressionRule();
		ruleUNION_VARIABLE_OR_NAME = grammarAccess.getUNION_VARIABLE_OR_NAMERule();
		ruleUnionNameOrReference = grammarAccess.getUnionNameOrReferenceRule();
	}
}
