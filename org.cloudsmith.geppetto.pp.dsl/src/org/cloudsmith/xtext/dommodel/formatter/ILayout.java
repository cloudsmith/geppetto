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
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;

/**
 * An ILayout is responsible for providing textual output to an {@link ITextFlow}.
 * 
 */
public interface ILayout {

	/**
	 * Formats the dom node and produces output in the flow.
	 * 
	 * @param dom
	 * @param flow
	 * @param context
	 * @return true if the layout manager processed all children of the given node
	 */
	public boolean format(IDomNode dom, ITextFlow flow, ILayoutContext context);

	/**
	 * Formats the dom node and produces output in the flow. The given style set contains the styles that
	 * should be applied to the given dom node. (This is typically the result of collecting the style from
	 * the css passed in the layout context). Note, that if this ILayout represents a formatter of an embedded language (or has
	 * separate rules for formatting), the passed styleset is for the containing language's opinion about the styling, and
	 * it may be ignored.
	 * 
	 * @param styleset
	 * @param dom
	 * @param flow
	 * @param context
	 * @return true if the layout manager processed all children of the given node
	 */
	public boolean format(StyleSet styleset, IDomNode dom, ITextFlow flow, ILayoutContext context);
}
