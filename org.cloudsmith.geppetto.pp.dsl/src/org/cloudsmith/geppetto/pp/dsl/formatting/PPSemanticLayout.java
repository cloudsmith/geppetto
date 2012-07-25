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
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DeclarativeSemanticFlowLayout;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.LayoutUtils;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
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
