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

import com.google.common.base.Joiner;

/**
 * Describes desired line breaks as an {@link IFlexibleQuantity}, how to handle
 * already emitted line breaks ({@link #isExistingAcceptable()} in which case the number of new line breaks
 * is reduced by the number of existing line breaks (down to 0), and if a successor comment that is terminated
 * by a line break is accepted as a line break or not ({@link #isCommentEndingWithBreakAcceptable()}.
 * 
 */
public class LineBreaks extends FlexibleQuantity {
	private boolean acceptComment;

	private boolean acceptExisting;

	/**
	 * This is the same as calling {@link #LineBreaks(int, int, int, boolean, boolean)} with 1, 1, 1, true, true.
	 */
	public LineBreaks() {
		this(1, 1, 1, true, true);
	}

	/**
	 * This is the same as calling {@link #LineBreaks(int, int, int, boolean, boolean)} with normal, normal, normal, true, true.
	 */
	public LineBreaks(int normal) {
		this(normal, normal, normal, true, true);
	}

	/**
	 * This is the same as calling {@link #LineBreaks(int, int, int, boolean, boolean)} with min, normal, max, true, true.
	 */
	public LineBreaks(int min, int normal, int max) {
		this(min, normal, max, true, true);
	}

	/**
	 * Sets the flexible quantity from the given min, normal and max parameters and if comments
	 * ending with line break should be accepted as a line break, and if existing already emitted line breaks
	 * should be included in the count or not.
	 * 
	 * @param min
	 * @param normal
	 * @param max
	 * @param acceptCommentEndingWithBreak
	 * @param acceptExisting
	 */
	public LineBreaks(int min, int normal, int max, boolean acceptCommentEndingWithBreak, boolean acceptExisting) {
		super(min, normal, max);
		this.acceptComment = acceptCommentEndingWithBreak;
		this.acceptExisting = acceptExisting;
	}

	/**
	 * Returns <code>true</code> if this line break spec accepts a following line break terminated
	 * comment as a line break (of count 1). If <code>false</code> is returned a line break is wanted
	 * before the comment.
	 * 
	 * @return <code>true</code> if a line break terminated comment counts as a line break
	 */
	public boolean isCommentEndingWithBreakAcceptable() {
		return acceptComment;
	}

	/**
	 * Returns true if this line break spec accepts an existing emitted sequence as part of the count (<code>true</code>),
	 * or if a new count should be emitted (<code>false</code>).
	 * 
	 * @return <code>true</code> if existing already emitted line breaks are included in the count
	 */
	public boolean isExistingAcceptable() {
		return acceptExisting;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LineBreaks(");
		Joiner.on(",").appendTo(
			builder, getMin(), getNormal(), getMax(), isCommentEndingWithBreakAcceptable(), isExistingAcceptable());
		builder.append(")");
		return builder.toString();
	}
}
