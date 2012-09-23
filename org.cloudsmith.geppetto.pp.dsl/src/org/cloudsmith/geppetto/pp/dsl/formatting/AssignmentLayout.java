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

import java.util.Iterator;
import java.util.List;

import org.cloudsmith.geppetto.common.stats.IntegerCluster;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DelegatingLayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.WidthStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Keyword;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A sub layout handler for AssignmentExpression that optionally cluster-aligns a sequence of assignments.
 * 
 */
public class AssignmentLayout {

	private static class FirstLeafWithTextAndTheRest implements Predicate<IDomNode> {
		private boolean firstLeafSeen = false;

		@Override
		public boolean apply(IDomNode input) {
			if(!DomModelUtils.isHidden(input))
				firstLeafSeen = true;
			return firstLeafSeen;
		}
	}

	@Inject
	private IStyleFactory styles;

	@Inject
	private DomNodeLayoutFeeder feeder;

	@Inject
	private Provider<IBreakAndAlignAdvice> adviceProvider;

	private Action theAssignmentExpression;

	private Keyword theEqualSign;

	private Keyword thePlusEqualSign;

	private Action theAppendExpression;

	private static Predicate<IDomNode> untilTheEnd = Predicates.alwaysFalse();

	@Inject
	public AssignmentLayout(PPGrammarAccess grammarAccess) {
		// selectorEntryRule = grammarAccess.getSelectorEntryRule();
		theAssignmentExpression = grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0();
		theEqualSign = grammarAccess.getAssignmentExpressionAccess().getEqualsSignKeyword_1_1();
		theAppendExpression = grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0();
		thePlusEqualSign = grammarAccess.getAppendExpressionAccess().getPlusSignEqualsSignKeyword_1_1();
	}

	protected boolean _format(AppendExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return commonFormat(styleSet, node, flow, context);
	}

	protected boolean _format(AssignmentExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return commonFormat(styleSet, node, flow, context);
	}

	protected boolean commonFormat(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {

		// unify the width of assignment expression LHS by scanning forward...

		int availableWidth = 132; // irrelevant, but a value needed for width

		IBreakAndAlignAdvice advice = adviceProvider.get();
		final boolean doAlignment = advice.isAlignAssignments();
		if(!doAlignment)
			return false;

		// used to collect the widths of each LHS width (typically just a variable name, but can be complex).
		List<Integer> widths = Lists.newArrayList();

		List<IDomNode> equalSignNodes = Lists.newArrayList();
		IntegerCluster clusters = new IntegerCluster(advice.clusterSize());

		boolean containsAppend = false;

		// while siblings are assignments, starting with the current one
		for(IDomNode aeNode = node; aeNode != null && includeInSequence(aeNode); aeNode = aeNode.getNextSibling()) {
			DelegatingLayoutContext innerContext = new DelegatingLayoutContext(context, availableWidth);
			TextFlow measuredFlow = new TextFlow(innerContext);

			// iterate to format the LHS expression and measure, and find the = sign (which gets padded)
			Iterator<IDomNode> itor = aeNode.treeIterator();
			while(itor.hasNext()) {
				IDomNode n = itor.next();
				// forward until assignment expression =, or +=
				if(!(n.getGrammarElement() == theAssignmentExpression || n.getGrammarElement() == theAppendExpression))
					continue;

				// Measure assignment expression
				feeder.sequence(n, measuredFlow, innerContext, new FirstLeafWithTextAndTheRest(), untilTheEnd);

				// forward to = or += sign
				while(itor.hasNext()) {
					n = itor.next();
					if(n.getGrammarElement() == theEqualSign || n.getGrammarElement() == thePlusEqualSign)
						break;
				}
				if(n.getGrammarElement() == thePlusEqualSign)
					containsAppend = true;

				if(!itor.hasNext())
					break; // WAT, nothing after the '=', give up on this assignment, try to align the others

				// If assignment node already has a width, it was processed by a preceding assignment
				// and we were done a long time ago...
				//
				WidthStyle widthStyle = n.getStyles().getStyle(WidthStyle.class, n);
				if(widthStyle != null)
					return false;

				equalSignNodes.add(n);

				// collect the width of the last selector entry's values
				int lastLineWidth = measuredFlow.getWidthOfLastLine();
				clusters.add(lastLineWidth);
				widths.add(lastLineWidth);
				break;
			}
		}
		markupWidths(equalSignNodes, widths, availableWidth, clusters, doAlignment, containsAppend);

		return false;
	}

	protected boolean includeInSequence(IDomNode node) {
		EObject semantic = node.getSemanticObject();
		return semantic instanceof AssignmentExpression || semantic instanceof AppendExpression;

	}

	/**
	 * assign widths and alignment to the equal sign nodes
	 * 
	 * @param equalSignNodes
	 * @param widths
	 * @param availableWidth
	 * @param clusters
	 * @param doCompaction
	 * @return
	 */
	private void markupWidths(List<IDomNode> equalSignNodes, List<Integer> widths, int availableWidth,
			IntegerCluster clusters, boolean doAlignment, boolean containsAppend) {
		// assign widths and alignment to the fat comma nodes
		int opW = containsAppend
				? 2
				: 1;
		for(int i = 0; i < equalSignNodes.size(); i++) {
			IDomNode c = equalSignNodes.get(i);
			int w = widths.get(i);
			int mw = doAlignment
					? clusters.clusterMax(w)
					: w;
			if(doAlignment)
				c.getStyles().add(StyleSet.withStyles(styles.align(Alignment.right), //
					styles.width(opW + mw - w)));
		}
	}
}
