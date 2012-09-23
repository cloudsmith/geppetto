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

import java.util.Collection;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LayoutManagerStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.dommodel.formatter.css.debug.FormattingTracer;
import org.cloudsmith.xtext.textflow.ITextFlow;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
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

	@Inject
	protected FormattingTracer tracer;

	/**
	 * Sequences a collection of IDomNode in depth first order. Each node is passed to an
	 * {@link ILayoutManager#format(StyleSet, IDomNode, ITextFlow, ILayoutContext)} where the layout manager is obtained via style collection.
	 * 
	 * @param dom
	 * @param output
	 * @param context
	 */
	public void sequence(Collection<IDomNode> nodes, ITextFlow output, ILayoutContext context) {
		sequence(nodes, output, context, Predicates.<IDomNode> alwaysTrue(), Predicates.<IDomNode> alwaysFalse());
	}

	public IDomNode sequence(Collection<IDomNode> nodes, ITextFlow output, ILayoutContext context,
			Predicate<IDomNode> include, Predicate<IDomNode> until) {
		IDomNode last = null;
		for(IDomNode n : nodes) {
			if(until.apply(n))
				return n;
			last = sequence(n, output, context, include, until);
		}
		return last;
	}

	/**
	 * Sequences the IDomNode in depth first order. Each node is passed to an
	 * {@link ILayoutManager#format(StyleSet, IDomNode, ITextFlow, ILayoutContext)} where the layout manager is obtained via style collection.
	 * 
	 * @param dom
	 * @param output
	 * @param context
	 */
	public void sequence(IDomNode node, ITextFlow output, ILayoutContext context) {
		sequence(node, output, context, Predicates.<IDomNode> alwaysTrue(), Predicates.<IDomNode> alwaysFalse());
	}

	public IDomNode sequence(IDomNode node, ITextFlow output, ILayoutContext context, Predicate<IDomNode> until) {
		return sequence(node, output, context, Predicates.<IDomNode> alwaysTrue(), until);
	}

	public IDomNode sequence(IDomNode node, ITextFlow output, ILayoutContext context, Predicate<IDomNode> include,
			Predicate<IDomNode> until) {
		if(until.apply(node))
			return node;
		if(node.isLeaf()) {
			if(include.apply(node))
				sequenceLeaf(node, output, context);
		}
		else
			return sequenceComposite(node, output, context, include, until);
		return null;
	}

	protected IDomNode sequenceComposite(IDomNode node, ITextFlow output, ILayoutContext context,
			Predicate<IDomNode> include, Predicate<IDomNode> until) {
		final StyleSet styleSet = context.getCSS().collectStyles(node);
		tracer.recordEffectiveStyle(node, styleSet);

		final ILayoutManager layout = styleSet.getStyleValue(LayoutManagerStyle.class, node, defaultLayout);

		layout.beforeComposite(styleSet, node, output, context);
		// if layout of composite by layout manager returns true, children are already processed
		IDomNode last = null;
		if(!layout.format(styleSet, node, output, context))
			for(IDomNode n : node.getChildren()) {
				if(until.apply(n))
					return n;
				last = sequence(n, output, context, include, until);
			}
		layout.afterComposite(styleSet, node, output, context);
		return last;
	}

	protected void sequenceLeaf(IDomNode node, ITextFlow output, ILayoutContext context) {
		final StyleSet styleSet = context.getCSS().collectStyles(node);
		tracer.recordEffectiveStyle(node, styleSet);

		final ILayoutManager layout = styleSet.getStyleValue(LayoutManagerStyle.class, node, defaultLayout);
		layout.format(styleSet, node, output, context);
	}
}
