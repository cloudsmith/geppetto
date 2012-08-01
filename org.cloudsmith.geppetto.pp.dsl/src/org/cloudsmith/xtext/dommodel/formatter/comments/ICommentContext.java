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
package org.cloudsmith.xtext.dommodel.formatter.comments;

import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;

/**
 * A CommentContext describes a "comment container", comment start/end and repeating tokens as well
 * as its start offset on the line (since this affects parsing of certain types of comments).
 * 
 * Also see the concrete implementations {@link JavaSLComment}, {@link JavaLikeMLComment}, {@link JavaDocLikeComment},
 * and {@link HashSLComment}.
 */
public interface ICommentContext {

	public static abstract class AbstractCommentContext implements ICommentContext {
		private final int leftPosition;

		protected AbstractCommentContext() {
			this.leftPosition = 0;
		}

		protected AbstractCommentContext(int leftPosition) {
			this.leftPosition = leftPosition;
		}

		@Override
		public boolean allowBannerInLeftMargin() {
			return true;
		}

		@Override
		public int getLeftMargin() {
			return 1;
		}

		@Override
		public int getLeftPosition() {
			return this.leftPosition;
		}

		@Override
		public Alignment getMarkerColumnAlignment() {
			return Alignment.right;
		}

	}

	public abstract static class AbstractSLComment extends ICommentContext.AbstractCommentContext {
		public AbstractSLComment() {
		}

		public AbstractSLComment(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public boolean allowBannerInLeftMargin() {
			return true;
		}

		@Override
		public String getEndToken() {
			return "";
		}

		@Override
		public Alignment getMarkerColumnAlignment() {
			return Alignment.left;
		}

		@Override
		public String getRepeatingToken() {
			return getStartToken();
		}

		@Override
		public boolean isSLStyle() {
			return true;
		}
	}

	public static class HashSLComment extends ICommentContext.AbstractSLComment {

		public HashSLComment() {
		}

		public HashSLComment(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContext getContextForPosition(int leftPosition) {
			return new HashSLComment(leftPosition);
		}

		@Override
		public int getMarkerColumnWidth() {
			return 1;
		}

		@Override
		public String getStartToken() {
			return "#";
		}

	}

	public static class JavaDocLikeComment extends ICommentContext.JavaLikeMLComment {
		public JavaDocLikeComment() {

		}

		public JavaDocLikeComment(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContext getContextForPosition(int leftPosition) {
			return new JavaDocLikeComment(leftPosition);
		}

		@Override
		public String getStartToken() {
			return "/**";
		}

	}

	public static class JavaLikeMLComment extends ICommentContext.AbstractCommentContext {

		public JavaLikeMLComment() {

		}

		public JavaLikeMLComment(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContext getContextForPosition(int leftPosition) {
			return new JavaLikeMLComment(leftPosition);
		}

		/**
		 * @return the endToken
		 */
		@Override
		public String getEndToken() {
			return "*/";
		}

		@Override
		public int getLeftMargin() {
			return 1;
		}

		@Override
		public Alignment getMarkerColumnAlignment() {
			return Alignment.right;
		}

		@Override
		public int getMarkerColumnWidth() {
			return 2;
		}

		/**
		 * @return the repeatingToken
		 */
		public String getRepeatingToken() {
			return "*";
		}

		/**
		 * @return the startToken
		 */
		public String getStartToken() {
			return "/*";
		}

		@Override
		public boolean isSLStyle() {
			return false;
		}

	}

	public static class JavaSLComment extends ICommentContext.AbstractSLComment {
		public JavaSLComment() {

		}

		public JavaSLComment(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContext getContextForPosition(int leftPosition) {
			return new JavaSLComment(leftPosition);
		}

		@Override
		public int getMarkerColumnWidth() {
			return 2;
		}

		public String getStartToken() {
			return "//";
		}
	}

	public boolean allowBannerInLeftMargin();

	/**
	 * 
	 * @param leftPosition
	 *            the wanted leftmost position
	 * @return a new ICommentConext configured the same way as this context but for a different leftPosition
	 */
	public ICommentContext getContextForPosition(int leftPosition);

	/**
	 * @return the ending token or "" if this is a {@link #isSLStyle()} type comment.
	 */
	public String getEndToken();

	/**
	 * @return the margin between the comment token and the text.
	 */
	int getLeftMargin();

	/**
	 * @return the left most position on the line for the Marker Column.
	 */
	public int getLeftPosition();

	/**
	 * @return the alignment of repeating markers (left, right supported).
	 */
	public Alignment getMarkerColumnAlignment();

	/**
	 * @return the width of the Marker Column (in which repeating is aligned)
	 */
	public int getMarkerColumnWidth();

	/**
	 * @return the marker token for all lines except first (and last if {@link #isSLStyle()} is false).
	 */
	public String getRepeatingToken();

	/**
	 * @return the marker token for the first line.
	 */
	public String getStartToken();

	/**
	 * @return true if the comment is a sequence of SL comments.
	 */
	public boolean isSLStyle();
}
