/**
 * Copyright (c) 2006-2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeStatus;

/**
 * Interface for a style.
 * A style has a {@link StyleType} and a value of type <T>.
 * 
 * @param <T>
 */
public interface IStyle<T> {

	public StyleType getStyleType();

	/**
	 * Gets the value of the style. A graph element must be provided as the style may be dynamic
	 * and produce its value as a function of the given graph element. If a style returns false
	 * from {@link #isFunction()}, the parameter ge may be null. At all other times must ge be a valid
	 * graph element.
	 * 
	 * @param ge
	 * @return
	 */
	public T getValue(IDomNode ge);

	/**
	 * A style with a value determined as a function of a graphic element returns true. A style that
	 * returns false allows null being passed as the graph element in a call to {@link #getValue(IGraphElement)}.
	 * 
	 * @return
	 */
	public boolean isFunction();

	/**
	 * Returns true if the style is supported for a particular type of element.
	 * 
	 * @param type
	 * @return
	 */
	public boolean supports(NodeStatus type);

	/**
	 * Returns true if the style supports an IDomNode with the given set of NodeStatus bits.
	 * 
	 * @param types
	 * @return
	 */
	public boolean supports(Set<NodeStatus> types);

	/**
	 * Visiting this IStyle means it will call back to the given visitor method named after the style type.
	 * 
	 * @param node
	 *            The visited node element
	 * @param visitor
	 *            The visitor that will be called
	 */
	public void visit(IDomNode node, IStyleVisitor visitor);

}
