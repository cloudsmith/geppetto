/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.ppformatting;

import java.util.Collections;
import java.util.Iterator;

import com.puppetlabs.geppetto.pp.AndExpression;
import com.puppetlabs.geppetto.pp.AppendExpression;
import com.puppetlabs.geppetto.pp.AssignmentExpression;
import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.AttributeOperation;
import com.puppetlabs.geppetto.pp.AttributeOperations;
import com.puppetlabs.geppetto.pp.BinaryExpression;
import com.puppetlabs.geppetto.pp.BinaryOpExpression;
import com.puppetlabs.geppetto.pp.Case;
import com.puppetlabs.geppetto.pp.CaseExpression;
import com.puppetlabs.geppetto.pp.CollectExpression;
import com.puppetlabs.geppetto.pp.Definition;
import com.puppetlabs.geppetto.pp.DefinitionArgument;
import com.puppetlabs.geppetto.pp.DefinitionArgumentList;
import com.puppetlabs.geppetto.pp.DoubleQuotedString;
import com.puppetlabs.geppetto.pp.ElseExpression;
import com.puppetlabs.geppetto.pp.ElseIfExpression;
import com.puppetlabs.geppetto.pp.ExportedCollectQuery;
import com.puppetlabs.geppetto.pp.ExprList;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.ExpressionTE;
import com.puppetlabs.geppetto.pp.HashEntry;
import com.puppetlabs.geppetto.pp.HostClassDefinition;
import com.puppetlabs.geppetto.pp.IQuotedString;
import com.puppetlabs.geppetto.pp.IfExpression;
import com.puppetlabs.geppetto.pp.ImportExpression;
import com.puppetlabs.geppetto.pp.LiteralBoolean;
import com.puppetlabs.geppetto.pp.LiteralClass;
import com.puppetlabs.geppetto.pp.LiteralDefault;
import com.puppetlabs.geppetto.pp.LiteralHash;
import com.puppetlabs.geppetto.pp.LiteralList;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.LiteralRegex;
import com.puppetlabs.geppetto.pp.LiteralUndef;
import com.puppetlabs.geppetto.pp.NodeDefinition;
import com.puppetlabs.geppetto.pp.OrExpression;
import com.puppetlabs.geppetto.pp.ParenthesisedExpression;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.ResourceBody;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.SelectorEntry;
import com.puppetlabs.geppetto.pp.SelectorExpression;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.TextExpression;
import com.puppetlabs.geppetto.pp.UnaryMinusExpression;
import com.puppetlabs.geppetto.pp.UnaryNotExpression;
import com.puppetlabs.geppetto.pp.UnlessExpression;
import com.puppetlabs.geppetto.pp.UnquotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.VariableTE;
import com.puppetlabs.geppetto.pp.VerbatimTE;
import com.puppetlabs.geppetto.pp.VirtualCollectQuery;
import com.puppetlabs.geppetto.pp.VirtualNameOrReference;
import com.puppetlabs.geppetto.pp.dsl.ppformatting.FormattingCommentAssociator.CommentAssociations;
import com.puppetlabs.geppetto.pp.dsl.services.PPGrammarAccess;
import com.puppetlabs.xtext.dommodel.formatter.context.IFormattingContext;
import com.puppetlabs.xtext.textflow.ITextFlow;
import com.puppetlabs.xtext.textflow.MeasuredTextFlow;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.serializer.sequencer.IContextFinder;
import org.eclipse.xtext.util.EmfFormatter;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public class PPExpressionFormatter {

	@SuppressWarnings("unused")
	@Inject
	private PPGrammarAccess grammarAccess;

	@Inject
	protected IContextFinder contextFinder;

	@Inject
	IFormattingContext formattingContext;

	private PolymorphicDispatcher<Void> formatDispatcher = new PolymorphicDispatcher<Void>(
		"_format", 2, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Void> get()) {
		@Override
		protected Void handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	@Inject
	FormattingCommentAssociator commentAssociator;

	private CommentAssociations commentAssociations;

	@Inject
	public PPExpressionFormatter(IGrammarAccess ga) {
		// let it crash - it is a configuration error
		grammarAccess = (PPGrammarAccess) ga;
	}

	protected void _format(AndExpression o, ITextFlow.WithText stream) {
		// AndExpressionElements access = grammarAccess.getAndExpressionAccess();
		// doFormat(o.getLeftExpr(), stream, access.getAndExpressionLeftExprAction_1_0());
		// stream.oneSpace();
		// stream.text(op);
		// stream.oneSpace();
		// doFormat(o.getRightExpr(), stream);

		internalFormatBinaryExpression(o, "and", stream);
	}

	protected void _format(AppendExpression o, ITextFlow.WithText stream) {
		internalFormatBinaryExpression(o, "+=", stream);
	}

	protected void _format(AssignmentExpression o, ITextFlow.WithText stream) {
		internalFormatBinaryExpression(o, "=", stream);
	}

	protected void _format(AtExpression o, ITextFlow.WithText stream) {
		// TODO: wrap the list if wider than max width
		doFormat(o.getLeftExpr(), stream);
		stream.appendText("[");
		Iterator<Expression> itor = o.getParameters().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.appendText(",");
				stream.appendSpace();
			}
		}
		stream.appendText("]");
	}

	protected void _format(BinaryOpExpression o, ITextFlow.WithText stream) {
		internalFormatBinaryExpression(o, o.getOpName(), stream);
	}

	protected void _format(Case o, ITextFlow.WithText stream) {

	}

	protected void _format(CaseExpression o, ITextFlow.WithText stream) {
		stream.appendText("case");
		stream.appendSpace();
		doFormat(o.getSwitchExpr(), stream);
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);

		// process cases
		int width = 0;
		for(Case c : o.getCases()) {
			MeasuredTextFlow inner = new MeasuredTextFlow(formattingContext);
			Iterator<Expression> itor = c.getValues().iterator();
			while(itor.hasNext()) {
				doFormat(itor.next(), inner);
				if(itor.hasNext()) {
					inner.appendText(",");
					inner.appendSpace();
				}
			}
			width = inner.getWidth();
		}
		for(Case c : o.getCases()) {
			int before = stream.size();
			Iterator<Expression> itor = c.getValues().iterator();
			while(itor.hasNext()) {
				doFormat(itor.next(), stream);
				if(itor.hasNext()) {
					stream.appendText(",");
					stream.appendSpace();
				}
			}
			int after = stream.size();
			stream.appendSpaces(width - (before - after));
			stream.appendSpace();
			stream.appendText(":");
			stream.appendSpace();
			stream.appendText("{");
			stream.changeIndentation(1);
			;
			formatStatementList(c.getStatements(), stream);
			stream.changeIndentation(-1);
			stream.appendText("}");
			stream.appendBreaks(1);
		}

		stream.changeIndentation(-1);
		stream.appendBreaks(1);
		stream.appendText("}");

	}

	protected void _format(CollectExpression o, ITextFlow.WithText stream) {
		doFormat(o.getClassReference(), stream);
		stream.appendSpace();
		doFormat(o.getQuery(), stream);
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		internalFormat(o.getAttributes(), true, stream);
		stream.changeIndentation(-1);
		stream.appendBreaks(1);
		stream.appendText("}");
	}

	protected void _format(Definition o, ITextFlow.WithText stream) {
		stream.appendText("define");
		stream.appendSpace();
		stream.appendText(o.getClassName());
		stream.appendSpace();
		internalFormatArguments(o.getArguments(), stream);
		if(o.getArguments() != null)
			stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);

		formatStatementList(o.getStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendBreaks(1);
		stream.appendText("}");
	}

	protected void _format(DoubleQuotedString o, ITextFlow.WithText stream) {
		stream.appendText("\"");
		for(TextExpression te : o.getStringPart()) {
			doFormat(te, stream);
		}
		stream.appendText("\"");
	}

	protected void _format(ElseExpression o, ITextFlow.WithText stream) {
		stream.appendText("else");
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		formatStatementList(o.getStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendText("}");
	}

	protected void _format(ElseIfExpression o, ITextFlow.WithText stream) {
		stream.appendText("elsif");
		stream.appendSpace();
		doFormat(o.getCondExpr(), stream);
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		formatStatementList(o.getThenStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendText("}");
		if(o.getElseStatement() != null) {
			stream.appendSpace();
			doFormat(o.getElseStatement(), stream);
		}
	}

	protected void _format(ExportedCollectQuery o, ITextFlow.WithText stream) {
		stream.appendText("<<|");
		stream.appendSpace();
		doFormat(o.getExpr(), stream);
		stream.appendSpace();
		stream.appendText("|>>");
	}

	protected void _format(ExpressionTE o, ITextFlow.WithText stream) {
		stream.appendText("${");
		doFormat(((ParenthesisedExpression) o.getExpression()).getExpr(), stream);
		stream.appendText("}");
	}

	protected void _format(ExprList o, ITextFlow.WithText stream) {
		int size = o.getExpressions().size();
		for(int i = 0; i < size; i++) {
			Expression e = o.getExpressions().get(i);
			doFormat(e, stream);
			if(i + 1 < size) {
				stream.appendText(",");
				stream.appendSpace();
			}
		}
	}

	protected void _format(HashEntry o, ITextFlow.WithText stream) {
		doFormat(o.getKey(), stream);
		stream.appendSpace();
		stream.appendText("=>");
		stream.appendSpace();
		doFormat(o.getValue(), stream);
	}

	protected void _format(HostClassDefinition o, ITextFlow.WithText stream) {
		stream.appendText("class");
		stream.appendSpace();
		stream.appendText(o.getClassName());
		stream.appendSpace();
		internalFormatArguments(o.getArguments(), stream);
		if(o.getArguments() != null)
			stream.appendSpace();
		if(o.getParent() != null) {
			stream.appendText("inherits");
			stream.appendSpace();
			doFormat(o.getParent(), stream);
			stream.appendSpace();
		}
		stream.appendText("{");
		if(o.getStatements().size() > 0) {
			stream.changeIndentation(1);
			;
			stream.appendBreaks(1);

			formatStatementList(o.getStatements(), stream);
			stream.changeIndentation(-1);
		}
		stream.appendBreaks(1);
		stream.appendText("}");
	}

	protected void _format(IfExpression o, ITextFlow.WithText stream) {
		stream.appendText("if");
		stream.appendSpace();
		doFormat(o.getCondExpr(), stream);
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		formatStatementList(o.getThenStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendText("}");
		if(o.getElseStatement() != null) {
			stream.appendSpace();
			doFormat(o.getElseStatement(), stream);
		}

	}

	protected void _format(ImportExpression o, ITextFlow.WithText stream) {
		stream.appendText("import");
		stream.appendSpace();
		Iterator<IQuotedString> itor = o.getValues().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.appendText(",");
				stream.appendSpace();
			}
		}
	}

	protected void _format(LiteralBoolean o, ITextFlow.WithText stream) {
		stream.appendText(String.valueOf(o.isValue()));
	}

	protected void _format(LiteralClass o, ITextFlow.WithText stream) {
		stream.appendText("class");
	}

	protected void _format(LiteralDefault o, ITextFlow.WithText stream) {
		stream.appendText("default");
	}

	protected void _format(LiteralHash o, ITextFlow.WithText stream) {
		stream.appendText("[");
		internalCommaSeparatedList(o.getElements(), stream);
		stream.appendText("]");
	}

	protected void _format(LiteralList o, ITextFlow.WithText stream) {
		stream.appendText("[");
		internalCommaSeparatedList(o.getElements(), stream);
		stream.appendText("]");
	}

	protected void _format(LiteralName o, ITextFlow.WithText stream) {
		stream.appendText(o.getValue());
	}

	protected void _format(LiteralNameOrReference o, ITextFlow.WithText stream) {
		stream.appendText(o.getValue());
	}

	protected void _format(LiteralRegex o, ITextFlow.WithText stream) {
		stream.appendText(o.getValue());
	}

	protected void _format(LiteralUndef o, ITextFlow.WithText stream) {
		stream.appendText("undef");
	}

	protected void _format(NodeDefinition o, ITextFlow.WithText stream) {
		stream.appendText("node");
		stream.appendSpace();
		internalCommaSeparatedList(o.getHostNames(), stream);
		stream.appendSpace();
		if(o.getParentName() != null) {
			doFormat(o.getParentName(), stream);
			stream.appendSpace();
		}
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		formatStatementList(o.getStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendText("}");
	}

	protected void _format(OrExpression o, ITextFlow.WithText stream) {
		internalFormatBinaryExpression(o, "or", stream);
	}

	protected void _format(ParenthesisedExpression o, ITextFlow.WithText stream) {
		stream.appendText("(");
		doFormat(o.getExpr(), stream);
		stream.appendText(")");
	}

	protected void _format(PuppetManifest o, ITextFlow.WithText stream) {
		formatStatementList(o.getStatements(), stream);
	}

	protected void _format(ResourceBody o, ITextFlow.WithText stream) {
		throw new UnsupportedOperationException("Should not be called - use internalFormat");
	}

	protected void _format(ResourceExpression o, ITextFlow.WithText stream) {
		doFormat(o.getResourceExpr(), stream);
		stream.appendSpace();
		stream.appendText("{");
		int bodyCount = o.getResourceData().size();
		switch(bodyCount) {
			case 0:
				stream.appendBreaks(1);
				break;
			case 1:
				ResourceBody body = o.getResourceData().get(0);
				// no space after brace if first body has no title
				if(body.getNameExpr() != null)
					stream.appendSpace();
				internalFormatResourceBody(body, true, stream);
				stream.appendBreaks(1);
				break;
			default:
				stream.changeIndentation(1);
				;
				stream.appendBreaks(1);
				Iterator<ResourceBody> itor = o.getResourceData().iterator();
				while(itor.hasNext()) {
					internalFormatResourceBody(itor.next(), false, stream);
					stream.appendText(";");
					stream.appendBreaks(1);
					// add extra blank line between resources, but not between last and closing brace
					if(itor.hasNext())
						stream.appendBreaks(1);
				}
				stream.changeIndentation(-1);
				break;
		}
		stream.appendText("}");
	}

	/**
	 * TODO: Column collections
	 * 
	 * @param o
	 * @param stream
	 */
	protected void _format(SelectorEntry o, ITextFlow.WithText stream) {
		internalFormatBinaryExpression(o, "=>", stream);
	}

	protected void _format(SelectorExpression o, ITextFlow.WithText stream) {
		doFormat(o.getLeftExpr(), stream);
		stream.appendSpace();
		stream.appendText("?");
		stream.appendSpace();

		// always surround with {} even if they are optional when there is only one entry
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);

		// calculate column width
		// Simply skip entries that can be very wide/unknown - format the rest
		// TODO: Set "folding" in the stream to allow nested List/hash to fold
		//
		int width = 0;
		for(Expression e : o.getParameters()) {
			MeasuredTextFlow inner = new MeasuredTextFlow(formattingContext);
			// if e is not a SelectorEntry, it is really a syntax error, but do something reasonable
			doFormat(e instanceof SelectorEntry
					? ((SelectorEntry) e).getLeftExpr()
					: e, inner);
			width = inner.getWidth();
		}
		for(Expression e : o.getParameters()) {
			if(e instanceof SelectorEntry == false) {
				doFormat(e, stream);
				stream.appendBreaks(1);
			}
			else {
				SelectorEntry se = (SelectorEntry) e;
				int before = stream.size();
				doFormat(se.getLeftExpr(), stream);
				int after = stream.size();
				// pad to width
				stream.appendSpaces(width - (after - before));
				stream.appendSpace();
				stream.appendText("=>");
				stream.appendSpace();
				doFormat(se.getRightExpr(), stream);
				stream.appendText(",");
				stream.appendBreaks(1);
			}
		}
		stream.changeIndentation(-1);
		stream.appendText("}");
	}

	protected void _format(SingleQuotedString o, ITextFlow.WithText stream) {
		stream.appendText("'");
		stream.appendText(o.getText());
		stream.appendText("'");
	}

	protected void _format(UnaryMinusExpression o, ITextFlow.WithText stream) {
		stream.appendText("-");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnaryNotExpression o, ITextFlow.WithText stream) {
		stream.appendText("!");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnlessExpression o, ITextFlow.WithText stream) {
		stream.appendText("if");
		stream.appendSpace();
		doFormat(o.getCondExpr(), stream);
		stream.appendSpace();
		stream.appendText("{");
		stream.changeIndentation(1);
		stream.appendBreaks(1);
		formatStatementList(o.getThenStatements(), stream);
		stream.changeIndentation(-1);
		stream.appendText("}");
	}

	protected void _format(UnquotedString o, ITextFlow.WithText stream) {
		stream.appendText("${");
		doFormat(o.getExpression(), stream);
		stream.appendText("}");
	}

	protected void _format(VariableExpression o, ITextFlow.WithText stream) {
		stream.appendText(o.getVarName());
	}

	protected void _format(VariableTE o, ITextFlow.WithText stream) {
		stream.appendText(o.getVarName());
	}

	protected void _format(VerbatimTE o, ITextFlow.WithText stream) {
		stream.appendText(o.getText());
	}

	protected void _format(VirtualCollectQuery o, ITextFlow.WithText stream) {
		stream.appendText("<|");
		stream.appendSpace();
		doFormat(o.getExpr(), stream);
		stream.appendSpace();
		stream.appendText("|>");
	}

	protected void _format(VirtualNameOrReference o, ITextFlow.WithText stream) {
		stream.appendText("@");
		if(o.isExported()) {
			stream.appendText("@");
		}
		stream.appendText(o.getValue());
	}

	private void doFormat(EObject o, ITextFlow stream) {
		// EObject context = getContext(o);

		Iterator<INode> itor = commentAssociations.before(o);
		while(itor.hasNext()) {
			INode n = itor.next();
			stream.appendText(n.getText());
		}

		formatDispatcher.invoke(o, stream);
		itor = commentAssociations.after(o);
		while(itor.hasNext()) {
			INode n = itor.next();
			stream.appendText(n.getText());
		}
	}

	public void format(EObject o, ITextFlow.WithText stream) {
		NodeModelUtils.findActualNodeFor(o);
		commentAssociations = commentAssociator.associateCommentsWithSemanticEObjects(
			o, Sets.newHashSet(NodeModelUtils.findActualNodeFor(o).getRootNode()));
		doFormat(o, stream);
		// trailing comments
		Iterator<INode> itor = commentAssociations.before(null);
		while(itor.hasNext())
			stream.appendText(itor.next().getText());
	}

	protected void formatStatementList(EList<Expression> statements, ITextFlow.WithText stream) {
		int size = statements.size();
		for(int i = 0; i < size; i++) {
			Expression s = statements.get(i);
			if(s instanceof LiteralNameOrReference) {
				_format((LiteralNameOrReference) s, stream);
				if(i + 1 < size) {
					stream.appendSpace();
					doFormat(statements.get(i + 1), stream);
					i++;
				}
			}
			else
				doFormat(s, stream);
			stream.appendBreaks(1);
		}
	}

	protected EObject getContext(EObject semanticObject) {
		Iterator<EObject> contexts = contextFinder.findContextsByContentsAndContainer(semanticObject, null).iterator();
		if(!contexts.hasNext())
			throw new RuntimeException("No Context for " + EmfFormatter.objPath(semanticObject) + " could be found");
		return contexts.next();
	}

	/**
	 * @param hostNames
	 * @param stream
	 */
	private void internalCommaSeparatedList(Iterable<? extends EObject> elements, ITextFlow.WithText stream) {
		Iterator<? extends EObject> itor = elements.iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.appendText(",");
				stream.appendSpace();
			}
		}
	}

	protected void internalFormat(AttributeOperations o, boolean commaLast, ITextFlow.WithText stream) {
		if(o == null)
			return;
		int maxWidth = 0;
		if(o != null) {
			final int size = o.getAttributes().size();
			for(AttributeOperation ao : o.getAttributes()) {
				String key = ao.getKey();
				if(key != null)
					maxWidth = Math.max(maxWidth, key.length());
			}
			int counter = 0;
			for(AttributeOperation ao : o.getAttributes()) {
				String key = ao.getKey();
				if(key == null)
					key = "";
				stream.appendText(key);
				stream.appendSpaces(maxWidth - key.length() + 1);
				stream.appendText(ao.getOp());
				stream.appendSpace();
				doFormat(ao.getValue(), stream);
				if(commaLast || counter + 1 < size)
					stream.appendText(",");
				if(counter + 1 < size)
					stream.appendBreaks(1);
				counter++;
			}
		}

	}

	/**
	 * @param arguments
	 * @param stream
	 */
	private void internalFormatArguments(DefinitionArgumentList arguments, ITextFlow.WithText stream) {
		if(arguments == null)
			return;
		stream.appendText("(");
		Iterator<DefinitionArgument> itor = arguments.getArguments().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.appendText(",");
				stream.appendSpace();
			}
		}
		stream.appendText(")");
	}

	protected void internalFormatBinaryExpression(BinaryExpression o, String op, ITextFlow.WithText stream) {
		doFormat(o.getLeftExpr(), stream);
		stream.appendSpace();
		stream.appendText(op);
		stream.appendSpace();
		doFormat(o.getRightExpr(), stream);
	}

	protected void internalFormatResourceBody(ResourceBody o, boolean commaLast, ITextFlow.WithText stream) {
		if(o.getNameExpr() != null) {
			doFormat(o.getNameExpr(), stream);
			stream.appendText(":");
		}
		if(o.getAttributes() != null && o.getAttributes().getAttributes().size() > 0) {
			stream.changeIndentation(1);
			stream.appendBreaks(1);
			internalFormat(o.getAttributes(), commaLast, stream);
			stream.changeIndentation(-1);
		}
	}
}
