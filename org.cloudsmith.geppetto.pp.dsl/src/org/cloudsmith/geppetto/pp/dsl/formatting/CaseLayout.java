/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.List;

import org.cloudsmith.geppetto.common.stats.IntegerCluster;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.StatementStyle;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DelegatingLayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.xtext.RuleCall;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A sub layout handler for CaseExpression and Case
 * 
 */
public class CaseLayout {

	private static class SkipInitialWhitespacePredicate implements Predicate<IDomNode> {

		boolean firstNonWhitespaceSeen = false;

		@Override
		public boolean apply(IDomNode input) {
			if(firstNonWhitespaceSeen == false && DomModelUtils.isWhitespace(input) != true)
				firstNonWhitespaceSeen = true;
			return firstNonWhitespaceSeen;
		}

	}

	@Inject
	private IStyleFactory styles;

	private PPGrammarAccess grammarAccess;

	@Inject
	private DomNodeLayoutFeeder feeder;

	@Inject
	private Provider<IBreakAndAlignAdvice> adviceProvider;

	protected final Predicate<IDomNode> caseColonPredicate = new Predicate<IDomNode>() {

		@Override
		public boolean apply(IDomNode input) {
			return input.getGrammarElement() == grammarAccess.getCaseAccess().getColonKeyword_2();
		}

	};

	/** used to find the case nodes */
	private final RuleCall caseRuleCall;

	@Inject
	public CaseLayout(PPGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
		caseRuleCall = grammarAccess.getCaseExpressionAccess().getCasesCaseParserRuleCall_3_0();

	}

	protected boolean _format(CaseExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		// unify the width of case expressions

		// Step 1, must format up to the first case expression to know the correct indentation of the case
		// expression. (At point of entry to this method, the whitespace between a preceding statement and the case
		// expression has not yet been processed, and thus, no WS, break, indent etc. has taken place.
		//
		DelegatingLayoutContext dlc = new DelegatingLayoutContext(context);
		MeasuredTextFlow continuedFlow = new MeasuredTextFlow((MeasuredTextFlow) flow);

		int currentMaxWidth = flow.getPreferredMaxWidth();
		int availableWidth = 0; // set when first case is seen

		IBreakAndAlignAdvice advice = adviceProvider.get();
		final boolean doCompaction = advice.compactCasesWhenPossible();
		final boolean doAlignment = advice.isAlignCases();

		// used to collect the widths of each case's width of its values
		List<Integer> widths = Lists.newArrayList();
		// int maxLastLine = 0;
		boolean firstCaseSeen = false;
		List<IDomNode> colonNodes = Lists.newArrayList();
		IntegerCluster clusters = new IntegerCluster(20);
		boolean allCompactable = true; // until proven wrong
		for(IDomNode n : node.getChildren()) {
			if(n.getGrammarElement() == caseRuleCall) {
				if(!firstCaseSeen) {
					// finish measurement of the position the case will appear at
					//
					continuedFlow.appendBreak();
					continuedFlow.getIndentation();
					availableWidth = currentMaxWidth - (continuedFlow.getIndentation() + 1) *
							continuedFlow.getIndentSize();
				}
				// used to measure output of formatted case values
				// adjust its width to available width (and do not mark items consumed in the given context)
				DelegatingLayoutContext innerContext = new DelegatingLayoutContext(context, availableWidth);
				TextFlow measuredFlow = new TextFlow(innerContext);
				// visit all nodes in case until the colon is hit, and format the output to the measured flow
				IDomNode colonNode = feeder.sequence(n, measuredFlow, innerContext, caseColonPredicate);
				if(doCompaction && !n.getStyleClassifiers().contains(StatementStyle.COMPACTABLE))
					allCompactable = false;

				colonNodes.add(colonNode);

				// collect the width of the last case's values
				int lastLineWidth = measuredFlow.getWidthOfLastLine();
				if(!firstCaseSeen) {
					// the space before the first case triggers case expression indentation of 1, this must be adjusted
					lastLineWidth -= measuredFlow.getIndentSize();
				}
				clusters.add(lastLineWidth);
				widths.add(lastLineWidth);
				firstCaseSeen = true;

			}
			else if(!firstCaseSeen) {
				// continue to feed everything (until first case seen). Exceptional case, there are no cases - then this is just wasted
				feeder.sequence(n, continuedFlow, dlc);
			}
		}
		List<Integer> remainingWidths = markupWidths(
			colonNodes, widths, availableWidth, clusters, doCompaction, doAlignment);

		if(doCompaction && allCompactable)
			markupCompact(colonNodes, remainingWidths, context);

		return false;
	}

	private boolean compactable(List<IDomNode> colonNodes, List<Integer> remainingWidths, ILayoutContext context) {
		// must measure each, stop if not all fits, otherwise prevent compaction
		final Predicate<IDomNode> alwaysFalse = Predicates.<IDomNode> alwaysFalse();
		for(int i = 0; i < colonNodes.size(); i++) {
			IDomNode p = colonNodes.get(i).getParent();
			IDomNode statements = DomModelUtils.nodeForGrammarElement(
				p, grammarAccess.getCaseAccess().getStatementsExpressionListParserRuleCall_4_0());
			DelegatingLayoutContext caseStatementContext = new DelegatingLayoutContext(context, remainingWidths.get(i));
			TextFlow caseStatementFlow = new TextFlow(caseStatementContext);
			if(statements != null)
				feeder.sequence(
					statements, caseStatementFlow, caseStatementContext, new SkipInitialWhitespacePredicate(),
					alwaysFalse);
			// only 1 line high and did not overflow
			if(!(caseStatementFlow.getHeight() <= 1 && caseStatementFlow.getWidthOfLastLine() <= remainingWidths.get(i))) {
				return false;
			}
		}
		return true;
	}

	private void markupCompact(List<IDomNode> colonNodes, List<Integer> remainingWidths, ILayoutContext context) {

		if(compactable(colonNodes, remainingWidths, context))
			// prevent whitespace after '{' and before '}' to break
			for(int i = 0; i < colonNodes.size(); i++) {
				IDomNode p = colonNodes.get(i).getParent();
				for(IDomNode n : p.getChildren()) {
					if(n.getGrammarElement() == grammarAccess.getCaseAccess().getLeftCurlyBracketKeyword_3()) {
						IDomNode ws = DomModelUtils.nextWhitespace(n);
						if(ws != null)
							ws.getStyles().add(StyleSet.withStyles(styles.oneSpace(), styles.noLineBreak()));
					}
					else if(n.getGrammarElement() == grammarAccess.getCaseAccess().getRightCurlyBracketKeyword_5()) {
						IDomNode ws = DomModelUtils.previousWhitespace(n);
						if(ws != null)
							ws.getStyles().add(StyleSet.withStyles(styles.oneSpace(), styles.noLineBreak()));
					}
				}
			}
	}

	/**
	 * assign widths and alignment to the colon nodes
	 * compute available width for remainder if all cases are compactable
	 * 
	 * @param colonNodes
	 * @param widths
	 * @param availableWidth
	 * @param clusters
	 * @param doCompaction
	 * @return
	 */
	private List<Integer> markupWidths(List<IDomNode> colonNodes, List<Integer> widths, int availableWidth,
			IntegerCluster clusters, boolean doCompaction, boolean doAlignment) {
		// assign widths and alignment to the colon nodes
		// compute available width for remainder if all cases are compactable
		List<Integer> remainingWidths = doCompaction
				? Lists.<Integer> newArrayList()
				: null;
		for(int i = 0; i < colonNodes.size(); i++) {
			IDomNode c = colonNodes.get(i);
			int w = widths.get(i);
			int mw = doAlignment
					? clusters.clusterMax(w)
					: w;
			if(doAlignment)
				c.getStyles().add(StyleSet.withStyles(styles.align(Alignment.right), //
					styles.width(1 + mw - w)));
			if(doCompaction)
				remainingWidths.add(availableWidth - mw - 6); // 6 = ": { " +" }"
		}
		return remainingWidths;
	}
}
