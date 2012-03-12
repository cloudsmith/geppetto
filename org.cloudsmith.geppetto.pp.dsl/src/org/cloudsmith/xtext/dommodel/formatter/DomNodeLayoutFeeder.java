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

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LayoutManagerStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * <p>
 * A DomNodeLayoutFeeder feeds {@link IDomNode} instances to an {@link ILayoutManager}.
 * </p>
 * <p>
 * This feeder obtains the layout manager to use by looking up the style of the respective node. If a node does not specify how to be formatted, a
 * {@link FlowLayout} will be used.
 * </p>
 * <p>
 * This class is useful in specialized layout managers that want to control certain aspects of formatting, but allows children to be formatted as per
 * their specified formatting styles. Simply calling {@link #sequence(IDomNode, ITextFlow, ILayoutContext)} on a node will invoke an
 * {@link ILayoutManager#format(StyleSet, IDomNode, ITextFlow, ILayoutContext)} for the given node (and all sub nodes).
 * </p>
 * <p>
 * The feeder prunes children by returning if the layout manager returns true from one of its format methods.
 * </p>
 * 
 */
public class DomNodeLayoutFeeder {

	@Inject
	@Named("Default")
	protected ILayoutManager defaultLayout;

	/**
	 * Sequences the IDomNode in depth first order. Each node is passed to an
	 * {@link ILayoutManager#format(StyleSet, IDomNode, ITextFlow, ILayoutContext)} where the layout manager is obtained via style collection.
	 * 
	 * @param dom
	 * @param output
	 * @param context
	 */
	public void sequence(IDomNode node, ITextFlow output, ILayoutContext context) {
		if(node.isLeaf())
			sequenceLeaf(node, output, context);
		else
			sequenceComposite(node, output, context);

	}

	protected void sequenceComposite(IDomNode node, ITextFlow output, ILayoutContext context) {
		final StyleSet styleSet = context.getCSS().collectStyles(node);
		final ILayoutManager layout = styleSet.getStyleValue(LayoutManagerStyle.class, node, defaultLayout);

		// if layout of composite by layout manager returns true, children are already processed
		if(!layout.format(styleSet, node, output, context))
			for(IDomNode n : node.getChildren())
				sequence(n, output, context);
	}

	protected void sequenceLeaf(IDomNode node, ITextFlow output, ILayoutContext context) {
		final StyleSet styleSet = context.getCSS().collectStyles(node);
		final ILayoutManager layout = styleSet.getStyleValue(LayoutManagerStyle.class, node, defaultLayout);
		layout.format(styleSet, node, output, context);
	}
}
