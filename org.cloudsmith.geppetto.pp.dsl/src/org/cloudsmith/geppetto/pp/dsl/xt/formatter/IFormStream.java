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
package org.cloudsmith.geppetto.pp.dsl.xt.formatter;

import org.eclipse.xtext.util.Strings;

/**
 * Interface for formatting stream.
 * 
 */
public interface IFormStream {

	/**
	 * Appends the given amount of line breaks to the stream. A value <= 0 is ignored.
	 * 
	 * @param count
	 *            >=0 number of line breaks
	 */
	public void breaks(int count);

	/**
	 * Changes indentation by +/- count.<br/>
	 * A count of 0 has no effect on indentation.<br/>
	 */
	public void changeIndentation(int count);

	/**
	 * Returns the current indentation count. This can be used to temporarily change indentation
	 * (e.g. to output multi line comments flush left), and then restore the previous indentation
	 * with a call to {@link #setIndentation(int)}.
	 * 
	 * @return the current indentation count (>= 0)
	 */
	public int getIndentation();

	/**
	 * Appends one space to the stream. This is the same as calling <code>spaces(1)</code>.
	 */
	public void oneSpace();

	/**
	 * <p>
	 * Sets the current indentation count. This is the same as calling<br/>
	 * <code>setIndentation(0);<br/>
	 * changeIndenation(count);</code>
	 * </p>
	 * <p>
	 * The resulting indentation is max(0, count)
	 * </p>
	 * 
	 * @param count
	 *            - the number of indents to set.
	 */
	public void setIndentation(int count);

	/**
	 * Appends the given amount of spaces to the stream.
	 * 
	 * @param count
	 */
	public void spaces(int count);

	/**
	 * Places the given string in the stream. The text may contain line separators, but each line will be
	 * processed and indentation will be applied. If the given string has leading whitespace this should most
	 * likely be removed first (see {@link Strings#removeLeadingWhitespace(String)}).
	 * 
	 * @param s
	 *            the text to append to the stream
	 */
	public void text(String s);

}
