/**
 * Copyright (c) 2011,2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgumentList;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.LiteralHash;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.UnlessExpression;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.dsl.ppdoc.DocumentationAssociator;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DeclarativeSemanticFlowLayout;
import org.cloudsmith.xtext.dommodel.formatter.DelegatingLayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.LayoutUtils;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Semantic layouts for PP
 * 
 */
@Singleton
public class PPSemanticLayout extends DeclarativeSemanticFlowLayout {
	private static class FirstLeafWithTextAndTheRest implements Predicate<IDomNode> {
		private boolean firstLeafSeen = false;

		@Override
		public boolean apply(IDomNode input) {
			if(!DomModelUtils.isHidden(input))
				firstLeafSeen = true;
			return firstLeafSeen;
		}
	}

	public enum ResourceStyle {
		EMPTY, SINGLEBODY_TITLE, SINGLEBODY_NO_TITLE, MULTIPLE_BODIES, COMPACTABLE;
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
		BLOCK,

		/**
		 * Can be rendered in compact form
		 */
		COMPACTABLE;
	}

	@Inject
	private PPGrammarAccess grammarAccess;

	@Inject
	private DocumentationAssociator documentationAssociator;

	@Inject
	private DefinitionArgumentListLayout definitionListArgumentLayout;

	@Inject
	private AssignmentLayout assignmentLayout;

	@Inject
	private LiteralListLayout literaListLayout;

	@Inject
	private CaseLayout caseLayout;

	@Inject
	private SelectorLayout selectorLayout;

	@Inject
	private LiteralHashLayout literaHashLayout;

	@Inject
	private Provider<IBreakAndAlignAdvice> adviceProvider;

	/**
	 * array of classifiers that represent {@code org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.StatementStyle.BLOCK} - used for fast
	 * lookup (faster
	 * that Xtext polymorph and EMF Switch)
	 */
	protected final static int[] blockClassIds = new int[] {
			PPPackage.CASE_EXPRESSION, PPPackage.DEFINITION, PPPackage.HOST_CLASS_DEFINITION, PPPackage.IF_EXPRESSION,
			PPPackage.NODE_DEFINITION, PPPackage.RESOURCE_EXPRESSION, PPPackage.SELECTOR_EXPRESSION };

	private static final int ATTRIBUTE_OPERATIONS_CLUSTER_SIZE = 20;

	@Inject
	private DomNodeLayoutFeeder feeder;

	private static Predicate<IDomNode> untilTheEnd = Predicates.alwaysFalse();

	@Inject
	IStyleFactory styles;

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

	protected boolean _format(AppendExpression ae, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return assignmentLayout._format(ae, styleSet, node, flow, context);
	}

	protected boolean _format(AssignmentExpression ae, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return assignmentLayout._format(ae, styleSet, node, flow, context);
	}

	protected boolean _format(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getAttributeOperationAccess().getKeyAttributeNameParserRuleCall_1_0(), Alignment.left,
			ATTRIBUTE_OPERATIONS_CLUSTER_SIZE);
		return false;
	}

	protected boolean _format(Case o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		Pair<Integer, Integer> counts = internalFormatStatementList(
			node, grammarAccess.getCaseAccess().getStatementsExpressionListParserRuleCall_4_0());
		boolean canBeCompacted = counts.getFirst() <= 1 && counts.getSecond() < 1;
		if(canBeCompacted && counts.getFirst() == 1) {
			// if the formatted statement list fits on one line, make this case eligible for same line output
			canBeCompacted = true;
		}
		if(canBeCompacted)
			node.getStyleClassifiers().add(StatementStyle.COMPACTABLE);
		return false;
	}

	protected boolean _format(CaseExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		return caseLayout._format(o, styleSet, node, flow, context);
	}

	protected boolean _format(Definition o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getDefinitionAccess().getStatementsExpressionListParserRuleCall_4_0());
		return false;
	}

	protected boolean _format(DefinitionArgumentList o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return definitionListArgumentLayout.format(o, styleSet, node, flow, context);
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

	protected boolean _format(LiteralHash o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		return literaHashLayout.format(node, flow, context);
	}

	protected boolean _format(LiteralList o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		return literaListLayout.format(node, flow, context);
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
		List<Object> styles = Lists.newArrayList();
		boolean compactResource = adviceProvider.get().compactResourceWhenPossible();
		switch(o.getResourceData().size()) {
			case 0:
				styles.add(ResourceStyle.EMPTY);
				if(compactResource)
					styles.add(ResourceStyle.COMPACTABLE);
				break;
			case 1:
				styles.add(o.getResourceData().get(0).getNameExpr() != null
						? ResourceStyle.SINGLEBODY_TITLE
						: ResourceStyle.SINGLEBODY_NO_TITLE);
				// if there is more than 1 attribute operation, the resource can't be compacted
				AttributeOperations attributes = o.getResourceData().get(0).getAttributes();
				if(compactResource && (attributes == null || attributes.getAttributes().size() < 2))
					styles.add(ResourceStyle.COMPACTABLE);
				break;
			default:
				styles.add(ResourceStyle.MULTIPLE_BODIES);
				break;
		}
		if(compactResource && styles.contains(ResourceStyle.COMPACTABLE)) {
			// must check if rendering would overflow
			node.getStyleClassifiers().addAll(styles);
			DelegatingLayoutContext dlc = new DelegatingLayoutContext(context);
			MeasuredTextFlow continuedFlow = new MeasuredTextFlow((MeasuredTextFlow) flow);
			int heightBefore = continuedFlow.getHeight();
			feeder.sequence(node.getChildren(), continuedFlow, dlc, new FirstLeafWithTextAndTheRest(), untilTheEnd);
			if(continuedFlow.getHeight() - heightBefore > 2) {
				node.getStyleClassifiers().remove(ResourceStyle.COMPACTABLE);
			}
		}
		else {
			// the style is set on the container and is used in containment checks for resource bodies.
			node.getStyleClassifiers().addAll(styles);
		}
		return false;
	}

	protected boolean _format(SelectorExpression se, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return selectorLayout._format(se, styleSet, node, flow, context);
	}

	protected boolean _format(SingleQuotedString o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		// Unless the actual string part is not marked verbatim, any literal new lines in the string will cause indentation
		for(IDomNode n : node.getChildren()) {
			if(n.getGrammarElement() == grammarAccess.getSingleQuotedStringAccess().getTextSqTextParserRuleCall_1_0()) {
				n.getStyles().put(styles.verbatim(true));
			}
		}
		return false;
	}

	protected boolean _format(UnlessExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node, grammarAccess.getUnlessExpressionAccess().getThenStatementsExpressionListParserRuleCall_3_0());
		return false;
	}

	protected boolean _format(VerbatimTE o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		flow.appendText(o.getText(), true);
		return true;
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
		List<INode> docNodes = documentationAssociator.getDocumentation(o);

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

	/**
	 * Assigns style classifiers for FIRST, STATEMENT, and BLOCK to the immediate children of the given node.
	 * 
	 * @param node
	 * @param grammarElement
	 * @return count of statements
	 */
	protected Pair<Integer, Integer> internalFormatStatementList(IDomNode node, EObject grammarElement) {
		List<IDomNode> nodes = node.getChildren();
		boolean first = true;
		Iterator<IDomNode> itor = nodes.iterator();
		int statementCount = 0;
		int blockCount = 0;
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
				statementCount++;
				if(isBlockStatement(semantic)) {
					firstToken.getStyleClassifiers().add(StatementStyle.BLOCK);
					blockCount++;
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
		return Tuples.pair(statementCount, blockCount);
	}

	/**
	 * Returns true if the semantic object represents a block statement (one that should be
	 * marked with {@link org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.StatementStyle.BLOCK}.)
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
