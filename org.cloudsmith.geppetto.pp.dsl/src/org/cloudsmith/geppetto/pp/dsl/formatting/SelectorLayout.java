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
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
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
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A sub layout handler for SelectorExpression and SelctorEntry
 * 
 */
public class SelectorLayout {

	@Inject
	private IStyleFactory styles;

	private PPGrammarAccess grammarAccess;

	@Inject
	private DomNodeLayoutFeeder feeder;

	@Inject
	private Provider<IBreakAndAlignAdvice> adviceProvider;

	protected final Predicate<IDomNode> caseFatCommaPredicate = new Predicate<IDomNode>() {

		@Override
		public boolean apply(IDomNode input) {
			return input.getGrammarElement() == grammarAccess.getSelectorEntryAccess().getEqualsSignGreaterThanSignKeyword_1_1();
		}

	};

	/** used to find the case nodes */
	private final ParserRule selectorEntryRule;

	private static final int SELECTOR_EXPRESSION_CLUSTER_SIZE = 20;

	@Inject
	public SelectorLayout(PPGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
		selectorEntryRule = grammarAccess.getSelectorEntryRule();

	}

	protected boolean _format(SelectorExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		// unify the width of selector entry expressions

		// Step 1, must format up to the first selector entry expression to know the correct indentation of the selector entry
		// expression. (At point of entry to this method, the whitespace between a preceding statement and the selector
		// expression has not yet been processed, and thus, no WS, break, indent etc. has taken place.
		//
		DelegatingLayoutContext dlc = new DelegatingLayoutContext(context);
		MeasuredTextFlow continuedFlow = new MeasuredTextFlow((MeasuredTextFlow) flow);

		int currentMaxWidth = flow.getPreferredMaxWidth();
		int availableWidth = 0; // set when first case is seen

		// IBreakAndAlignAdvice advice = adviceProvider.get(); // TODO: Advice? this formatting ?
		final boolean doAlignment = true; // advice.isAlignCases();

		// used to collect the widths of each case's width of its values
		List<Integer> widths = Lists.newArrayList();
		// int maxLastLine = 0;
		boolean firstSelectoEntrySeen = false;
		List<IDomNode> fatCommaNodes = Lists.newArrayList();
		IntegerCluster clusters = new IntegerCluster(SELECTOR_EXPRESSION_CLUSTER_SIZE);
		for(IDomNode n : node.getChildren()) {
			if(n.getGrammarElement() instanceof RuleCall &&
					((RuleCall) n.getGrammarElement()).getRule() == selectorEntryRule) {
				if(!firstSelectoEntrySeen) {
					// finish measurement of the position the selectorEntry will appear at
					//
					continuedFlow.appendBreak();
					continuedFlow.getIndentation();
					availableWidth = currentMaxWidth - (continuedFlow.getIndentation() + 1) *
							continuedFlow.getIndentSize();
				}
				// used to measure output of formatted selector entry values
				// adjust its width to available width (and do not mark items consumed in the given context)
				DelegatingLayoutContext innerContext = new DelegatingLayoutContext(context, availableWidth);
				TextFlow measuredFlow = new TextFlow(innerContext);
				// visit all nodes in case until the colon is hit, and format the output to the measured flow
				IDomNode fatCommaNode = feeder.sequence(n, measuredFlow, innerContext, caseFatCommaPredicate);

				fatCommaNodes.add(fatCommaNode);

				// collect the width of the last selector entry's values
				int lastLineWidth = measuredFlow.getWidthOfLastLine();
				if(!firstSelectoEntrySeen) {
					// the space before the first entry triggers selector expression indentation of 1, this must be adjusted
					lastLineWidth -= measuredFlow.getIndentSize();
				}
				clusters.add(lastLineWidth);
				widths.add(lastLineWidth);
				firstSelectoEntrySeen = true;

			}
			else if(!firstSelectoEntrySeen) {
				// continue to feed everything (until first case seen). Exceptional case, there are no entries - then this is just wasted
				feeder.sequence(n, continuedFlow, dlc);
			}
		}
		markupWidths(fatCommaNodes, widths, availableWidth, clusters, /* doCompaction, */doAlignment);

		return false;
	}

	/**
	 * assign widths and alignment to the fat comma nodes
	 * compute available width for remainder if all cases are compactable
	 * 
	 * @param fatCommaNodes
	 * @param widths
	 * @param availableWidth
	 * @param clusters
	 * @param doCompaction
	 * @return
	 */
	private void markupWidths(List<IDomNode> fatCommaNodes, List<Integer> widths, int availableWidth,
			IntegerCluster clusters, boolean doAlignment) {
		// assign widths and alignment to the fat comma nodes
		for(int i = 0; i < fatCommaNodes.size(); i++) {
			IDomNode c = fatCommaNodes.get(i);
			int w = widths.get(i);
			int mw = doAlignment
					? clusters.clusterMax(w)
					: w;
			if(doAlignment)
				c.getStyles().add(StyleSet.withStyles(styles.align(Alignment.right), //
					styles.width(2 + mw - w)));
		}
	}
}
