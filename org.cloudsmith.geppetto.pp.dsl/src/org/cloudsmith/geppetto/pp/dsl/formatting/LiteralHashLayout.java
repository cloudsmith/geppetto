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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.cloudsmith.geppetto.common.stats.IntegerCluster;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.HashEntryElements;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.LiteralHashElements;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.DelegatingLayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * <p>
 * Performs semantic layout on a LiteralHash in combination with text-fit check.
 * </p>
 * <p>
 * if the LiteralHash list does not fit on the same line:
 * <ul>
 * <li>break after '{' and indent</li>
 * <li>break after each item on ',' (but not on end comma)</li>
 * <li>align '=>' using clustered width padding</li>
 * <li>dedent on '}'</li>
 * </ul>
 * <p>
 * The styling is assigned to the nodes directly to override all other rule based styling.
 * </p>
 */
public class LiteralHashLayout extends AbstractListLayout {
	@Inject
	private IStyleFactory styles;

	@Inject
	private PPGrammarAccess grammarAccess;

	private void assignAlignmentAndWidths(Map<IDomNode, Integer> operatorNodes, IntegerCluster cluster) {
		for(Entry<IDomNode, Integer> entry : operatorNodes.entrySet()) {
			int w = entry.getValue();
			entry.getKey().getStyles().add(
				StyleSet.withStyles(styles.align(Alignment.right), styles.width(2 + cluster.clusterMax(w) - w)));
		}

	}

	@Override
	protected IBreakAndAlignAdvice getAlignAdvice() {
		return breakAlignAdviceProvider.get();
	}

	@Override
	protected void markup(IDomNode node, final boolean breakAndAlign, final int clusterWidth, ITextFlow flow,
			ILayoutContext context) {

		Iterator<IDomNode> itor = node.treeIterator();
		Map<IDomNode, Integer> operatorNodes = Maps.newHashMap();
		IntegerCluster cluster = new IntegerCluster(clusterWidth);

		final LiteralHashElements access = grammarAccess.getLiteralHashAccess();
		final HashEntryElements hashAccess = grammarAccess.getHashEntryAccess();
		int previousKeyWidth = 0;
		while(itor.hasNext()) {
			IDomNode n = itor.next();
			EObject ge = n.getGrammarElement();
			if(ge == access.getLeftCurlyBracketKeyword_1()) {
				IDomNode nextLeaf = DomModelUtils.nextWhitespace(n);
				if(DomModelUtils.isWhitespace(nextLeaf) && breakAndAlign)
					nextLeaf.getStyles().add(StyleSet.withStyles(styles.oneLineBreak()));
			}
			else if(breakAndAlign) {
				if(ge == access.getCommaKeyword_2_1_0()) {
					IDomNode nextLeaf = DomModelUtils.nextWhitespace(n);
					if(DomModelUtils.isWhitespace(nextLeaf))
						nextLeaf.getStyles().add(StyleSet.withStyles(styles.oneLineBreak()));
				}
				else if(ge == hashAccess.getKeyLiteralNameOrStringParserRuleCall_0_0()) {
					DelegatingLayoutContext keyContext = new DelegatingLayoutContext(context);
					TextFlow keyFlow = new TextFlow(keyContext);
					ArrayList<IDomNode> children = Lists.newArrayList(n.getChildren());
					for(Iterator<IDomNode> subitor = children.iterator(); subitor.hasNext();) {
						IDomNode x = subitor.next();
						NodeType t = x.getNodeType();
						if(t == NodeType.ACTION || t == NodeType.WHITESPACE) {
							subitor.remove();
							continue;
						}
						break; // first non whitespace or action
					}
					feeder.sequence(children, keyFlow, context);
					previousKeyWidth = keyFlow.getWidthOfLastLine();
					cluster.add(previousKeyWidth);
					System.out.println("node= [" + keyFlow.getText() + "] width = " + previousKeyWidth);
				}
				else if(ge == hashAccess.getEqualsSignGreaterThanSignKeyword_1()) {
					operatorNodes.put(n, previousKeyWidth);
				}
			}
		}

		if(breakAndAlign)
			assignAlignmentAndWidths(operatorNodes, cluster);
	}
}
