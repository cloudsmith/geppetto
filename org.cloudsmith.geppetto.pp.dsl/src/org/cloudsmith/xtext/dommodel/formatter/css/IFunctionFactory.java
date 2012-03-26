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
package org.cloudsmith.xtext.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;

import com.google.common.base.Function;
import com.google.inject.ImplementedBy;

/**
 * Interface for commonly used functions in Dom based formatting.
 * 
 */
@ImplementedBy(FunctionFactory.class)
public interface IFunctionFactory {

	public Function<IDomNode, Integer> firstNonWordChar();

	public Function<IDomNode, Integer> lastNonWordChar();

	/**
	 * A function producing a literal string when applied to a node (ignores the given node).
	 * 
	 * @param s
	 *            - produced String
	 * @return
	 */
	public Function<IDomNode, String> literalString(String s);

	/**
	 * A function producing a literal string set when applied to a node (ignores the given node).
	 * 
	 * @param set
	 *            - produced set
	 * @return
	 */
	public Function<IDomNode, Set<String>> literalStringSet(Set<String> set);

	/**
	 * @return
	 */
	public Function<IDomNode, Spacing> noSpaceUnlessNextIsLinebreakingComment();

	/**
	 * A function returning the inverse of the Boolean returned by the given function applied to the given node.
	 * 
	 * @param f
	 * @return
	 */
	public Function<IDomNode, Boolean> not(Function<IDomNode, Boolean> f);

	/**
	 * Produces a LineBreaks style of 1 linebreak unless the following node is a comment that ends with
	 * a linebreak.
	 * 
	 * @return
	 */
	public Function<IDomNode, LineBreaks> oneLineBreakUnlessNextIsLinebreakingComment();

	/**
	 * @return
	 */
	public Function<IDomNode, LineBreaks> oneLineBreakUnlessPredecessorIsLinebreakingComment();

	/**
	 * Produces a SpacingStyle of 1 space unless node is whitespace terminated in which case
	 * it produces SpacingStyle 0
	 * 
	 * @return
	 */
	public Function<IDomNode, Spacing> oneSpaceUnlessPredecessorIsWhitespaceTerminated();

	/**
	 * A function returning the {@link IDomNode#getText()} of the node it is applied to.
	 * 
	 * @return
	 */
	public Function<IDomNode, String> textOfNode();

}
