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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import java.util.Iterator;
import java.util.List;

import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.dsl.adapters.DocumentationAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.DocumentationAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.StatementStyle;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DeclarativeSemanticFlowLayout;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.LayoutUtils;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.CommentProcessor;
import org.cloudsmith.xtext.textflow.CommentProcessor.CommentFormattingOptions;
import org.cloudsmith.xtext.textflow.CommentProcessor.CommentText;
import org.cloudsmith.xtext.textflow.ICommentContext;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Semantic layouts for PP
 * 
 */
@Singleton
public class PPSemanticLayout extends DeclarativeSemanticFlowLayout {
	public enum ResourceStyle {
		EMPTY, SINGLEBODY_TITLE, SINGLEBODY_NO_TITLE, MULTIPLE_BODIES;
	}

	public enum StatementStyle {
		/**
		 * Statement is first in a statement list
		 */
		FIRST,

		/**
		 * This is a statement.
		 */
		STATEMENT,

		/**
		 * This is an unparenthesized call (an expression, not a statement)
		 */
		UNPARENTHESIZED_FUNCTION,

		/**
		 * This is an argument (an expression, not a statement)
		 */
		UNPARENTHESIZED_ARG,

		/**
		 * This statement is a block statement.
		 */
		BLOCK;
	}

	@Inject
	IStyleFactory styles;

	@Inject
	PPGrammarAccess grammarAccess;

	@Inject
	DomNodeLayoutFeeder feeder;

	protected final Predicate<IDomNode> caseColonPredicate = new Predicate<IDomNode>() {

		@Override
		public boolean apply(IDomNode input) {
			return input.getGrammarElement() == grammarAccess.getCaseAccess().getColonKeyword_2();
		}

	};

	/**
	 * array of classifiers that represent {@link StatementStyle.BLOCK} - used for fast lookup (faster
	 * that Xtext polymorph and EMF Switch)
	 */
	protected final static int[] blockClassIds = new int[] {
			PPPackage.CASE, PPPackage.DEFINITION, PPPackage.HOST_CLASS_DEFINITION, PPPackage.IF_EXPRESSION,
			PPPackage.NODE_DEFINITION, PPPackage.RESOURCE_EXPRESSION, PPPackage.SELECTOR_EXPRESSION };

	protected void _after(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		if(aos.eContainer() instanceof ResourceBody) {
			flow.changeIndentation(-1);
		}
	}

	protected void _before(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		if(aos.eContainer() instanceof ResourceBody) {
			flow.changeIndentation(1);
		}
	}

	protected boolean _format(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getAttributeOperationAccess().getKeyNameParserRuleCall_1_0(), Alignment.left);
		return false;
	}

	protected boolean _format(Case o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(node, grammarAccess.getCaseAccess().getStatementsExpressionListParserRuleCall_4_0());
		return false;
	}

	protected boolean _format(CaseExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		// unify the width of case expressions

		// to find the case nodes
		RuleCall caseRuleCall = grammarAccess.getCaseExpressionAccess().getCasesCaseParserRuleCall_3_0();
		// used to measure output of formatted case values
		ITextFlow.Measuring measuredFlow = new MeasuredTextFlow(context);
		// used to collect the widths of each case's width of its values
		List<Integer> widths = Lists.newArrayList();
		for(IDomNode n : node.getChildren()) {
			if(n.getGrammarElement() == caseRuleCall) {
				// visit all nodes in case until the colon is hit, and format the output to the measured flow
				feeder.sequence(n, measuredFlow, context, caseColonPredicate);
				// collect the width of the last case's values
				widths.add(measuredFlow.getWidthOfLastLine());
				measuredFlow.appendBreak(); // break to enable calculating width
			}
		}
		// pad the colons on their left side to equal width
		int max = measuredFlow.getWidth();
		for(IDomNode n : node.getChildren()) {
			if(n.getGrammarElement() == caseRuleCall) {
				Iterator<IDomNode> caseIterator = n.treeIterator();
				while(caseIterator.hasNext()) {
					IDomNode c = caseIterator.next();
					if(caseColonPredicate.apply(c)) {
						c.getStyles().add(
							StyleSet.withStyles(styles.align(Alignment.right), styles.width(1 + max - widths.remove(0))));
						break; // no need to continue past the ":"
					}
				}
			}
		}

		return false;
	}

	protected boolean _format(Definition o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {

		internalFormatStatementList(
			node, grammarAccess.getDefinitionAccess().getStatementsExpressionListParserRuleCall_4_0());
		return false;
	}

	protected boolean _format(ElseExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getElseExpressionAccess().getStatementsExpressionListParserRuleCall_2_0());
		return false;
	}

	protected boolean _format(ElseIfExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getElseIfExpressionAccess().getThenStatementsExpressionListParserRuleCall_3_0());
		return false;
	}

	protected boolean _format(HostClassDefinition o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {

		internalFormatStatementList(
			node, grammarAccess.getHostClassDefinitionAccess().getStatementsExpressionListParserRuleCall_5_0());
		return false;
	}

	protected boolean _format(IfExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getIfExpressionAccess().getThenStatementsExpressionListParserRuleCall_3_0());
		return false;
	}

	protected boolean _format(NodeDefinition o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {

		internalFormatStatementList(
			node, grammarAccess.getNodeDefinitionAccess().getStatementsExpressionListParserRuleCall_5_0());
		return false;
	}

	protected boolean _format(PuppetManifest manifest, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getPuppetManifestAccess().getStatementsExpressionListParserRuleCall_1_0());
		return false;
	}

	protected boolean _format(ResourceExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		ResourceStyle rstyle = null;
		switch(o.getResourceData().size()) {
			case 0:
				rstyle = ResourceStyle.EMPTY;
				break;
			case 1:
				rstyle = o.getResourceData().get(0).getNameExpr() != null
						? ResourceStyle.SINGLEBODY_TITLE
						: ResourceStyle.SINGLEBODY_NO_TITLE;
				break;
			default:
				rstyle = ResourceStyle.MULTIPLE_BODIES;
				break;
		}
		// the style is set on the container and is used in containment checks for resource bodies.
		node.getStyleClassifiers().add(rstyle);
		return false;
	}

	protected boolean _format(SelectorExpression se, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0(), Alignment.left);
		return false;

	}

	/**
	 * Returns the first significant IDomNode in a Statement - this is either the first
	 * token that represents documentation of the statement, or the first non documentation token
	 * (for non documentable, and documentable without documentation).
	 * 
	 * @param node
	 * @return
	 */
	protected IDomNode firstSignificantNode(IDomNode node) {
		IDomNode firstToken = DomModelUtils.firstTokenWithText(node);
		EObject o = node.getSemanticObject();
		if(o == null)
			return firstToken;
		DocumentationAdapter adapter = DocumentationAdapterFactory.eINSTANCE.adapt(o);
		List<INode> docNodes = adapter == null
				? null
				: adapter.getNodes();
		if(docNodes != null && docNodes.size() > 0) {
			INode firstDoc = docNodes.get(0);
			Iterator<IDomNode> itor = node.treeIterator();
			while(itor.hasNext()) {
				IDomNode n = itor.next();
				if(n.getNode() == firstDoc) {
					firstToken = n;
					break;
				}
				if(n == firstToken)
					break; // stop looking
			}
		}
		return firstToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.xtext.dommodel.formatter.FlowLayout#formatComment(org.cloudsmith.xtext.dommodel.formatter.css.StyleSet,
	 * org.cloudsmith.xtext.dommodel.IDomNode, org.cloudsmith.xtext.textflow.ITextFlow,
	 * org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext)
	 */
	@Override
	protected void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		// if output provides information about metrics, it is possible to reposition/reformat a comment.
		// Without information about current output position etc. a formatted result based on original position
		// may not be nice at all.

		// TODO: (do better when output is not measured)
		if(context.isWhitespacePreservation() || output instanceof ITextFlow.Measuring == false)
			super.formatComment(styleSet, node, output, context);
		else if(node.getGrammarElement() == this.grammarAccess.getML_COMMENTRule())
			formatMLComment(styleSet, node, (ITextFlow.Measuring) output, context);
		else
			super.formatComment(styleSet, node, output, context);

	}

	protected void formatMLComment(StyleSet styleSet, IDomNode node, ITextFlow.Measuring output,
			ILayoutContext layoutContext) {
		// How much space is left?
		int maxWidth = output.getPreferredMaxWidth();
		int current = output.getAppendLinePosition();
		int available = maxWidth - current;
		final String lineSeparator = layoutContext.getLineSeparatorInformation().getLineSeparator();

		// how wide will the output be if hanging at current?
		// position on line (can be -1 if there was no node model
		int pos = DomModelUtils.posOnLine(node, lineSeparator);
		// set up extraction context (use 0 if there was no INode model)
		ICommentContext in = new ICommentContext.JavaLikeMLComment(Math.max(0, pos));
		CommentProcessor cpr = new CommentProcessor();
		CommentText comment = cpr.separateCommentFromContainer(node.getText(), in, lineSeparator);

		// format in position 0 to measure it
		ICommentContext out = new ICommentContext.JavaLikeMLComment(0);
		TextFlow formatted = cpr.formatComment(
			comment, out, new CommentFormattingOptions(Integer.MAX_VALUE), layoutContext);
		int w = formatted.getWidth();
		if(w <= available) {
			// yay, it will fit as a hanging comment, reformat for this position.
			out = new ICommentContext.JavaLikeMLComment(current);
			formatted = cpr.formatComment(comment, out, new CommentFormattingOptions(Integer.MAX_VALUE), layoutContext);
			output.appendText(formatted.getText(), true);
		}
		else {
			// Did not fit, move to new line if not first on line.

			int use = current;
			// if output ends with a break, then current is at the leftmost position already
			if(!output.endsWithBreak()) {
				// if comment fits with effective indent, use that
				// otherwise, if comment fits with same indent, use that
				// otherwise, reformat for effective indent
				//
				final int indentationSize = layoutContext.getIndentationInformation().getIndentString().length();
				int pos_sameIndent = output.getLastUsedIndentation() * indentationSize;
				int pos_effectiveIndent = output.getIndentation() * indentationSize;
				use = pos_effectiveIndent;
				if(!(use + w <= available) && pos_sameIndent + w <= available)
					use = pos_sameIndent;

				// break and manually indent first line
				output.appendText(lineSeparator, true);
				output.appendText(CharSequences.spaces(use), true);
			}
			out = new ICommentContext.JavaLikeMLComment(use);
			// format (will wrap if required)
			formatted = cpr.formatComment(comment, out, new CommentFormattingOptions(maxWidth - use), layoutContext);
			output.appendText(formatted.getText(), true);
		}

	}

	protected void internalFormatStatementList(IDomNode node, EObject grammarElement) {
		List<IDomNode> nodes = node.getChildren();
		boolean first = true;
		Iterator<IDomNode> itor = nodes.iterator();
		while(itor.hasNext()) {
			IDomNode n = itor.next();
			if(n.getGrammarElement() == grammarElement) {
				IDomNode firstToken = firstSignificantNode(n);
				// IDomNode firstToken = DomModelUtils.firstTokenWithText(n);
				EObject semantic = n.getSemanticObject();
				if(first) {
					// first in body
					firstToken.getStyleClassifiers().add(StatementStyle.FIRST);
					first = false;
				}
				// mark all (except func args) as being a STATEMENT
				firstToken.getStyleClassifiers().add(StatementStyle.STATEMENT);
				if(isBlockStatement(semantic)) {
					firstToken.getStyleClassifiers().add(StatementStyle.BLOCK);
				}
				else if(semantic instanceof LiteralNameOrReference) {
					// this is an unparenthesized function call
					firstToken.getStyleClassifiers().add(StatementStyle.UNPARENTHESIZED_FUNCTION);
					// skip the optional single argument that follows
					if(itor.hasNext()) {
						n = itor.next();
					}
				}
			}
		}

	}

	/**
	 * Returns true if the semantic object represents a block statement (one that should be
	 * marked with {@link StatementStyle.BLOCK}.)
	 * 
	 * @param semantic
	 * @return
	 */
	protected boolean isBlockStatement(EObject semantic) {

		if(semantic == null)
			return false;
		final int id = semantic.eClass().getClassifierID();
		int length = blockClassIds.length;
		for(int i = 0; i < length; i++)
			if(blockClassIds[i] == id)
				return true;
		return false;
	}
}
