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
package org.cloudsmith.xtext.dommodel.formatter;

import java.util.Iterator;
import java.util.List;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.eclipse.xtext.AbstractElement;

import com.google.inject.internal.Lists;

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
	public static void unifyWidthAndAlign(IDomNode dom, AbstractElement grammarElement, Alignment alignment) {
		int max = 0;
		Iterator<IDomNode> iterator = dom.treeIterator();
		List<IDomNode> affected = Lists.newArrayList();
		while(iterator.hasNext()) {
			IDomNode node = iterator.next();
			if(node.getGrammarElement() == grammarElement) {
				max = Math.max(max, node.getText() == null
						? 0
						: node.getText().length());
				affected.add(node);
			}
		}
		for(IDomNode node : affected)
			node.getStyles().add(StyleSet.withStyles(//
				new StyleFactory.WidthStyle(max), //
				new StyleFactory.AlignmentStyle(alignment)));
	}
}
