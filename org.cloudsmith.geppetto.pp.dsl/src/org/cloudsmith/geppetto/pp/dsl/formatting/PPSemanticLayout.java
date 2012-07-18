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
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorExpression;
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
		FIRST, STATEMENT, UNPARENTHESIZED_FUNCTION, UNPARENTHESIZED_ARG;
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
		internalFormatStatementList(
			node.getChildren(), grammarAccess.getCaseAccess().getStatementsExpressionListParserRuleCall_4_0());
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
			node.getChildren(), grammarAccess.getDefinitionAccess().getStatementsExpressionListParserRuleCall_4_0());
		return false;
	}

	protected boolean _format(ElseExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(), grammarAccess.getElseExpressionAccess().getStatementsExpressionListParserRuleCall_2_0());
		return false;
	}

	protected boolean _format(ElseIfExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(),
			grammarAccess.getElseIfExpressionAccess().getThenStatementsExpressionListParserRuleCall_3_0());
		return false;
	}

	protected boolean _format(HostClassDefinition o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(),
			grammarAccess.getHostClassDefinitionAccess().getStatementsExpressionListParserRuleCall_5_0());
		return false;
	}

	protected boolean _format(IfExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(),
			grammarAccess.getIfExpressionAccess().getThenStatementsExpressionListParserRuleCall_3_0());
		return false;
	}

	protected boolean _format(NodeDefinition o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(), grammarAccess.getNodeDefinitionAccess().getStatementsExpressionListParserRuleCall_5_0());
		return false;
	}

	protected boolean _format(PuppetManifest manifest, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		internalFormatStatementList(
			node.getChildren(), grammarAccess.getPuppetManifestAccess().getStatementsExpressionListParserRuleCall_1_0());
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
		node.getStyleClassifiers().add(rstyle);
		return false;
	}

	protected boolean _format(SelectorExpression se, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0(), Alignment.left);
		return false;

	}

	protected void internalFormatStatementList(List<IDomNode> nodes, EObject grammarElement) {
		boolean first = true;
		Iterator<IDomNode> itor = nodes.iterator();
		while(itor.hasNext()) {
			IDomNode n = itor.next();
			if(n.getGrammarElement() == grammarElement) {
				IDomNode firstToken = DomModelUtils.firstTokenWithText(n);
				if(first) {
					// first in body
					firstToken.getStyleClassifiers().add(StatementStyle.FIRST);
					first = false;
				}
				// mark all (except func args) as being a STATEMENT
				firstToken.getStyleClassifiers().add(StatementStyle.STATEMENT);
				if(n.getSemanticObject() instanceof LiteralNameOrReference) {
					// in case it is needed
					firstToken.getStyleClassifiers().add(StatementStyle.UNPARENTHESIZED_FUNCTION);
					if(itor.hasNext()) {
						n = itor.next();
					}
				}
			}
		}

	}
}
