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
package org.cloudsmith.xtext.textflow;

import org.eclipse.xtext.util.Strings;

/**
 * <p>
 * Interface for a flow of text.
 * </p>
 * <p>
 * This interface extends {@link Appendable} to allow an ITextFlow to be a direct receiver of output from a {@link java.util.Formatter}.
 * </p>
 * 
 */
public interface ITextFlow extends Appendable {

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

	/**
	 * A text flow that measures
	 * 
	 */
	public interface Measuring extends ITextFlow, IMetrics {

	}

	/**
	 * Interface for an ITextFlow that can append its content as individual calls to an output flow.
	 * 
	 */
	public interface Recording extends ITextFlow {

		/**
		 * Appends the recorded content of this flow into the given flow. Note that the recorded flow may
		 * contain absolute indentation settings. To ensure proper handling of indentation the caller should
		 * take appropriate measures (e.g. providing a flow that does not allow changes to the absolute indent,
		 * or that modifies them to being relative to the container). Alternatively, if the caller allows the
		 * replayed flow to "break out" it should get the currentIndent before calling this method, and then restore
		 * this value after the call.
		 * 
		 * @param output
		 *            where the content of this flow should be appended
		 */
		public void appendTo(ITextFlow output);

	}

	/**
	 * Interface for an ITextFlow capable of representing its contents as formatted text.
	 * 
	 */
	public interface WithText extends ITextFlow {

		/**
		 * Returns the content of the flow as formatted text.
		 * 
		 * @return the formatted content
		 */
		public CharSequence getText();

		/**
		 * Returns the length of the text that will be returned from {@link #getText()}.
		 * 
		 * @return the length of the contained text
		 */
		public int size();
	}

	/**
	 * Appends one line break. This is the same as calling <code>appendBreaks(1, false)</code>
	 * 
	 * @return the flow
	 */
	public ITextFlow appendBreak();

	/**
	 * Appends the given amount of line breaks to the stream. A value <= 0 is ignored.
	 * This is the same as calling <code>appendBreaks(count, false)</code>.
	 * 
	 * @param count
	 *            >=0 number of line breaks
	 * @return the flow
	 */
	public ITextFlow appendBreaks(int count);

	/**
	 * Appends the given amount of line breaks to the stream. A value <= 0 is ignored but is interpreted
	 * as a position where automatic line-wrap may occur. If the verbatim flag is <code>true</code> the break will not
	 * output any indentation.
	 * 
	 * @param count
	 * @param verbatim
	 * @return the flow
	 */
	public ITextFlow appendBreaks(int count, boolean verbatim);

	/**
	 * Appends one space to the stream. This is the same as calling <code>spaces(1)</code>.
	 * 
	 * @return the flow
	 */
	public ITextFlow appendSpace();

	/**
	 * Appends the given amount of spaces to the stream. If the given count is 0, no space is emitted, and the
	 * flow is given the permission to break the line at this position to satisfy the max width constraint. If
	 * the given count is < 0, no space is emitted, and the flow is not permitted to break at this position.
	 * 
	 * @param count
	 * @return the flow
	 */
	public ITextFlow appendSpaces(int count);

	/**
	 * <p>
	 * Appends the given string to the flow. The text may contain line separators, but each line is subject to indentation. If the given string has
	 * leading whitespace this should most likely be removed first (see {@link Strings#removeLeadingWhitespace(String)}).
	 * </p>
	 * <p>
	 * To output text verbatim, see {@link #appendText(CharSequence, boolean)}.
	 * </p>
	 * 
	 * @param s
	 *            the text to append to the stream
	 * @return the flow
	 */
	public ITextFlow appendText(CharSequence s);

	/**
	 * Outputs the verbatim text. If any line separators are found in the text they are included in {@link IMetrics} but they do not trigger
	 * indentation.
	 * 
	 * @param s
	 *            the text to append to the stream
	 * @param verbatim
	 *            - if true text is not subject to indentation processing
	 * @return the flow
	 */
	public ITextFlow appendText(CharSequence s, boolean verbatim);

	/**
	 * Changes indentation by +/- count.<br/>
	 * A count of 0 has no effect on indentation.<br/>
	 * 
	 * @return the flow
	 */
	public ITextFlow changeIndentation(int count);

	/**
	 * Returns true if the measured flow ends with one or more breaks.
	 * 
	 * @return true if flow ends with break
	 */
	public boolean endsWithBreak();

	/**
	 * Ensures that flow ends with at least the given amount of line breaks. If the number of line breaks at
	 * the end of the flow is already >= count, then this call has no effect. If smaller than count, the flow will have
	 * the given amount of line breaks at the end of the flow after the operation completes.
	 * 
	 * @param count
	 * @return
	 */
	public ITextFlow ensureBreaks(int count);

	/**
	 * @return number of breaks at end of flow
	 */
	int getEndBreakCount();

	/**
	 * Returns the current indentation count. This can be used to temporarily change indentation
	 * (e.g. to output multi line comments flush left), and then restore the previous indentation
	 * with a call to {@link #setIndentation(int)}.
	 * 
	 * @return the current indentation count (>= 0)
	 */
	public int getIndentation();

	/**
	 * 
	 * @return the allowed max width in number of characters before automatic wrapping kicks in.
	 */
	public int getPreferredMaxWidth();

	/**
	 * Returns the current wrap indentation.
	 * 
	 * @return the current wrap indentation
	 */
	public int getWrapIndentation();

	/**
	 * Returns the flag that controls if the first line should be indented.
	 * 
	 * @return
	 */
	public boolean isIndentFirstLine();

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
	public ITextFlow setIndentation(int count);

	/**
	 * Puts the text flow in a mode where the first output will use the current indent before output of first
	 * text. This only has effect if a) an indent other than 0 has already been set, and b) no output has been appended.
	 * Note that changes to the indent after etIndentFirstLine(true) has been called does not alter the initial indent.
	 * 
	 * @param flag
	 */
	public void setIndentFirstLine(boolean flag);

	/**
	 * <p>
	 * Sets the additional increment to use when a line is auto wrapped (does not fit on current line). The default is 1.
	 * </p>
	 * 
	 * @param count
	 */
	public void setWrapIndentation(int count);

}
