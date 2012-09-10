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
package org.cloudsmith.xtext.dommodel.formatter;

import java.util.Iterator;
import java.util.List;

import org.cloudsmith.geppetto.common.stats.IntegerCluster;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.eclipse.xtext.AbstractElement;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Layout Utility functions
 * 
 */
public class LayoutUtils {

	/**
	 * <p>
	 * Unifies the width of the nodes matching the given grammar element in the subtree rooted at the given node. The resulting width is set as the
	 * width style of the corresponding nodes.
	 * </p>
	 * 
	 * <p>
	 * The width is calculated using the unformatted text in the dom nodes with matching grammar elements.
	 * </p>
	 * 
	 * @param dom
	 *            - the subtree root to scan
	 * @param grammarElement
	 */
	public static void unifyWidthAndAlign(IDomNode dom, AbstractElement grammarElement, Alignment alignment,
			int clusterMax) {

		IntegerCluster cluster = new IntegerCluster(clusterMax);
		// int max = 0;
		Iterator<IDomNode> iterator = dom.treeIterator();
		List<IDomNode> affected = Lists.newArrayList();
		while(iterator.hasNext()) {
			IDomNode node = iterator.next();
			if(node.getGrammarElement() == grammarElement) {
				// node = DomModelUtils.firstTokenWithText(node);
				if(node.getText() == null)
					continue;
				int length = node.getText().length();
				cluster.add(length);
				//
				// max = Math.max(max, node.getText() == null
				// ? 0
				// : node.getText().length());
				affected.add(node);
			}
		}
		for(IDomNode node : affected)
			node.getStyles().add(StyleSet.withStyles(//
				new StyleFactory.WidthStyle(cluster.clusterMax(node.getText().length())), //
				new StyleFactory.AlignmentStyle(alignment)));
	}

	@Inject
	DomNodeLayoutFeeder feeder;

	/**
	 * Returns true if the node, when formatted and written to the given flow will fit on what remains
	 * on the line. The flow and contexts are not affected by this operation.
	 * 
	 * @param node
	 *            - the node to measure
	 * @param flow
	 *            - the flow where the node is to be written (at some later point in time)
	 * @param context
	 *            - the context used to write to the given flow
	 * @return true if the formatted node fits on the same line
	 */
	public boolean fitsOnSameLine(IDomNode node, ITextFlow flow, ILayoutContext context) {
		DelegatingLayoutContext dlc = new DelegatingLayoutContext(context);
		MeasuredTextFlow continuedFlow = new MeasuredTextFlow((MeasuredTextFlow) flow);
		int h0 = continuedFlow.getHeight();
		for(IDomNode n : node.getChildren()) {
			feeder.sequence(n, continuedFlow, dlc);
		}
		int h1 = continuedFlow.getHeight();
		// if output causes break (height increases), or at edge (the '{' will not fit).
		return h1 <= h0 && continuedFlow.getWidthOfLastLine() < continuedFlow.getPreferredMaxWidth();
	}
}
