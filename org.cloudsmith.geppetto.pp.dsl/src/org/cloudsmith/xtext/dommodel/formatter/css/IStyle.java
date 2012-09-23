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
package org.cloudsmith.xtext.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;

/**
 * Interface for a style.
 * A style has a value of type <T>.
 * 
 * @param <T>
 */
public interface IStyle<T> {

	/**
	 * Gets the value of the style. A {@link IDomNode} must be provided as the style may be dynamic
	 * and produce its value as a function of the given node. If a style returns false
	 * from {@link #isFunction()}, the parameter node may be null. At all other times must node be a valid {@link IDomNode}.
	 * 
	 * @param node
	 * @return the style's value
	 */
	public T getValue(IDomNode node);

	/**
	 * A style with a value determined by a function applied to an {@link IDomNode} returns true. A style that
	 * returns false allows null being passed as the node in a call to {@link #getValue(IDomNode)}.
	 * 
	 * @return true if style value is determined by a function
	 */
	public boolean isFunction();

	/**
	 * Returns true if this style is supported for the given type.
	 * 
	 * @See {@link IDomNode#getNodeType()}
	 * @param type
	 *            - the {@link NodeType} being tested if it is supported
	 * @return true if given type is supported
	 */
	public boolean supports(NodeType type);

	/**
	 * Returns true if this style supports all of the given types.
	 * 
	 * @See {@link IDomNode#getNodeType()}
	 * @param types
	 *            - the set of {@link NodeType} being tested if they are all supported
	 * @return true if all of the given types are supported
	 */
	public boolean supports(Set<NodeType> types);

	/**
	 * Visiting this IStyle means it will call back to the given visitor method named after the style type.
	 * 
	 * @param node
	 *            The visited node
	 * @param visitor
	 *            The visitor that will be called
	 */
	public void visit(IDomNode node, IStyleVisitor visitor);

}
