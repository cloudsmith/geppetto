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

import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.LiteralListElements;
import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.Inject;

/**
 * <p>
 * Performs semantic layout on a LiteralList in combination with text-fit check.
 * </p>
 * <p>
 * if the LiteralList list does not fit on the same line, line breaks are added to the whitespace after all commas except the optional end comma.
 * </p>
 * 
 * <p>
 * The styling is assigned to the nodes directly to override all other rule based styling. Indentation is expected to be handled by default rules.
 * </p>
 */
public class LiteralListLayout extends AbstractListLayout {
	@Inject
	private IStyleFactory styles;

	@Inject
	private PPGrammarAccess grammarAccess;

	@Override
	protected void markup(IDomNode node, final boolean breakAndAlign, final int clusterWidth, ITextFlow flow,
			ILayoutContext context) {

		Iterator<IDomNode> itor = node.treeIterator();

		LiteralListElements access = grammarAccess.getLiteralListAccess();
		while(itor.hasNext()) {
			IDomNode n = itor.next();
			EObject ge = n.getGrammarElement();
			if(ge == access.getLeftSquareBracketKeyword_1()) {
				IDomNode nextLeaf = DomModelUtils.nextWhitespace(n);
				if(DomModelUtils.isWhitespace(nextLeaf) && breakAndAlign)
					nextLeaf.getStyles().add(StyleSet.withStyles(styles.oneLineBreak()));
			}
			else if(breakAndAlign && ge == access.getCommaKeyword_2_1_0()) {
				IDomNode nextLeaf = DomModelUtils.nextWhitespace(n);
				if(DomModelUtils.isWhitespace(nextLeaf))
					nextLeaf.getStyles().add(StyleSet.withStyles(styles.oneLineBreak()));
			}
		}

	}

}
