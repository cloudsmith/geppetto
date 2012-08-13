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
package org.cloudsmith.xtext.textflow;

/**
 * <p>
 * Describes metrics of a text flow. Given the flow between --- and ---:
 * </p>
 * 
 * <pre>
 * ---
 * 123456
 * 123456789
 *   123
 * 
 * ---
 * </pre>
 * <p>
 * The result of the various measures are:
 * <table>
 * <tr>
 * <td>{@link #endsWithBreak()}</td>
 * <td>true</td>
 * </tr>
 * <tr>
 * <td>{@link #getHeight()}</td>
 * <td>4</td>
 * </tr>
 * <tr>
 * <td>{@link #gtLastUsedIndent()}</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>{@link #getWidth()}</td>
 * <td>9</td>
 * </tr>
 * <tr>
 * <td>{@link #getWidthOfLastLine()}</td>
 * <td>5</td>
 * </tr>
 * <tr>
 * <td>{@link #isEmpty()}</td>
 * <td>false</td>
 * </tr>
 * </table>
 * </p>
 * 
 */
public interface IMetrics {
	/**
	 * Returns true if the measured flow ends with one or more breaks.
	 * 
	 * @return true if flow ends with break
	 */
	public boolean endsWithBreak();

	/**
	 * Returns the position on the line where next unwrapped text will appear.
	 * 
	 * @return start position on line
	 */
	int getAppendLinePosition();

	/**
	 * @return number of breaks at end of flow
	 */
	int getEndBreakCount();

	/**
	 * <p>
	 * Produces the minimum amount of lines to present the content in the flow. A value of 0 is returned if the flow is empty. The last line
	 * counts even if not terminated with a line break.
	 * </p>
	 * <p>
	 * As an example the result for both <code>"abc\n123"</code> and <code>"abc\n123\n" is 2.
	 * 
	 * @return a text height >= 0 < MAXINT
	 */
	int getHeight();

	/**
	 * Returns the current indentation count. At the end of a text flow, the value is typically 0.
	 * 
	 * @return the current indentation count (>= 0)
	 */
	public int getIndentation();

	/**
	 * Returns the number of indents emitted for last line. Returns 0 if last line of text was not indented.
	 * This is may be different than the indent in effect at the end of the flow.
	 * 
	 * @return the last emitted indent count
	 */
	public int getLastUsedIndentation();

	/**
	 * Produces the current maximum width (in characters) of the flow.
	 * 
	 * @return a text width >= 0 < MAXINT
	 */
	int getWidth();

	/**
	 * Returns the width of the last line in the flow with content other than just a line break.
	 * This includes a flow that has whitespace after an indent.
	 * 
	 * @return width of last line with content other than just break
	 */
	public int getWidthOfLastLine();

	/**
	 * Returns true if no output was produced in this flow.
	 * 
	 * @return true if the flow is empty
	 */
	public boolean isEmpty();
}