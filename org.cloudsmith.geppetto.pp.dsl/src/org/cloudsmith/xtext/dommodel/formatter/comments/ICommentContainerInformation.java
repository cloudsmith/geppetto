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
 * An ICommentContainerInformation describes a "comment container", comment start/end, and repeating tokens, as well
 * as its start offset on the line (since this affects parsing of certain types of comments).
 * 
 * Also see the concrete implementations {@link JavaSLCommentContainer}, {@link JavaLikeMLCommentContainer}, {@link JavaDocLikeCommentContainer},
 * and {@link HashSLCommentContainer}.
 */
public interface ICommentContainerInformation {
	public static abstract class AbstractCommentContainerInformation implements ICommentContainerInformation {
		private final int leftPosition;

		protected AbstractCommentContainerInformation() {
			this.leftPosition = 0;
		}

		protected AbstractCommentContainerInformation(int leftPosition) {
			this.leftPosition = leftPosition;
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

	public abstract static class AbstractSLCommentContainer extends AbstractCommentContainerInformation {
		public AbstractSLCommentContainer() {
		}

		public AbstractSLCommentContainer(int leftPosition) {
			super(leftPosition);
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

	public static class HashSLCommentContainer extends AbstractSLCommentContainer {

		public HashSLCommentContainer() {
		}

		public HashSLCommentContainer(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContainerInformation create(int leftPosition) {
			return new HashSLCommentContainer(leftPosition);
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

	public static class JavaDocLikeCommentContainer extends JavaLikeMLCommentContainer {
		public JavaDocLikeCommentContainer() {

		}

		public JavaDocLikeCommentContainer(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContainerInformation create(int leftPosition) {
			return new JavaDocLikeCommentContainer(leftPosition);
		}

		@Override
		public String getStartToken() {
			return "/**";
		}

	}

	public static class JavaLikeMLCommentContainer extends
			ICommentContainerInformation.AbstractCommentContainerInformation {

		public JavaLikeMLCommentContainer() {

		}

		public JavaLikeMLCommentContainer(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContainerInformation create(int leftPosition) {
			return new JavaLikeMLCommentContainer(leftPosition);
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

	public static class JavaSLCommentContainer extends AbstractSLCommentContainer {
		public JavaSLCommentContainer() {

		}

		public JavaSLCommentContainer(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContainerInformation create(int leftPosition) {
			return new JavaSLCommentContainer(leftPosition);
		}

		@Override
		public int getMarkerColumnWidth() {
			return 2;
		}

		public String getStartToken() {
			return "//";
		}
	}

	/**
	 * <p>
	 * Marker interface for an ICommentContainerInformation that is Opaque - i.e. it is unknown how to extract the comment text from its surrounding
	 * container. The only possible "formatting" is to output the verbatim comment container and text.
	 * </p>
	 * <p>
	 * Calling any of the informational methods on a class implementing this interface will result in an a {@code UnsupportedOperationException}. It
	 * is legal to create instances with {@code new} or via the factory {@link #create(int)} method. It is also possible to obtain the
	 * {@link #getLeftPosition()}.
	 * 
	 */
	public interface Unknown {
	}

	public static class UnknownCommentContainer extends AbstractCommentContainerInformation implements Unknown {
		public UnknownCommentContainer() {
		}

		public UnknownCommentContainer(int leftPosition) {
			super(leftPosition);
		}

		@Override
		public ICommentContainerInformation create(int leftPosition) {
			return new UnknownCommentContainer(leftPosition);
		}

		@Override
		public String getEndToken() {
			throw new UnsupportedOperationException("An Unknown comment container does not have an EndToken");
		}

		@Override
		public int getMarkerColumnWidth() {
			throw new UnsupportedOperationException("An Unknown comment container does not define a column width.");
		}

		@Override
		public String getRepeatingToken() {
			throw new UnsupportedOperationException("An Unknown comment container does not define a repeating token.");
		}

		@Override
		public String getStartToken() {
			throw new UnsupportedOperationException("An Unknown comment container does not have a start token.");
		}

		@Override
		public boolean isSLStyle() {
			throw new UnsupportedOperationException(
				"An Unknown comment container does not know if it is SL style or not.");
		}

	}

	/**
	 * Factory method that creates a new instance of the same class but for a different position.
	 * 
	 * @param leftPosition
	 *            the wanted leftmost position
	 * @return a new ICommentConext configured the same way as this context but for a different leftPosition
	 */
	public ICommentContainerInformation create(int leftPosition);

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
