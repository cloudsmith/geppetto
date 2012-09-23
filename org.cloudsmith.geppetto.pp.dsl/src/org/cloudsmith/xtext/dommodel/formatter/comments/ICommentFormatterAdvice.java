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

import com.google.inject.Provider;

/**
 * <p>
 * Describes advice/preferences to a comment formatter.
 * </p>
 * <p>
 * Note that parts of the advice may be needed by a "comment extractor" that separates the comment text from its comment container. This happens when
 * there is an ambiguity if whitespace is part of the container/the language it is embedded in, or if it is part of the comment.
 * </p>
 * <p>
 * The formatting advice described by this interface is considered to be a useful set of "preferences" for comment formatting in a generic sense.
 * Naturally, if the default textual comment processor is overridden with a more advanced parser, such a processor may use only parts of this advice,
 * and may use an extended interface to provide additional implementation specific advice. When doing so, the implementation should naturally also use
 * custom preference pages.
 * </p>
 * <p>
 * <b>TODO</b>: This construct is a bit of a crutch. The best way to handle comment formatting is to parse the comment into a Dom, and then apply
 * styling with CSS. The "comment formatter advice" would then simply be a CSS. The current implementation is basically one single complex "style",
 * with a special mechanism to apply it.
 * </p>
 */
public interface ICommentFormatterAdvice {

	public static enum BannerAdvice {
		/**
		 * Banners are left verbatim.
		 */
		NoWrap,

		/**
		 * Banner lines longer than the max preferred width are truncated to fit.
		 */
		Truncate,

		/**
		 * Banner lines are folded if they are longer than the available width.
		 */
		Fold,

		// // For future expansion
		// // TODO: implement this
		// /**
		// * Banner lines are truncated if too long, and expanded to the available width if shorter.
		// */
		// TruncateExpand;
	}

	public static enum CommentTextAdvice {
		/**
		 * No formatting at all (all whitespace is preserved)
		 */
		NoFormat,

		/**
		 * Light formatting is performed (aligning beginning of lines, unambiguous leading and trailing space
		 * is removed).
		 */
		NoWrap,

		/**
		 * Lines longer than the available width are folded at closest whitespace position if this
		 * reduces the overshoot of the width.
		 */
		Fold,

		// // For future expansion
		// // TODO: implement this
		// /**
		// * <p>
		// * Flow/re-flow paragraphs. The exact definition of "word", "space" and "paragraph" is determined by the comment processor. A simple text
		// * oriented processor would typically define words as characters separated by whitespace, and a paragraph end as an empty line, a line that
		// is
		// * special and with non {@code FlowParagraph} formatting, or the end of the comment. A more advanced processor (aware of say HTML markup)
		// * would naturally use other rules.
		// * </p>
		// * <p>
		// * This type of formatting affects the layout both when comments are made narrower and wider.
		// * </p>
		// * <p>
		// * If a selected formatter does not support this, it may instead just apply folding when text overshoots
		// * </p>
		// */
		// FlowParagraph;
	}

	public static class DefaultCommentAdvice implements ICommentFormatterAdvice {

		/**
		 * @return true
		 */
		@Override
		public boolean enabled() {
			return true;
		}

		/**
		 * @return {@link org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.LineAlignment.LineAlignment#LEFT
		 *         LineAlignment.LEFT}
		 */
		@Override
		public boolean getAlignSpecialLinesLeft() {
			return true;
		}

		/**
		 * Describes how a comment processor should handle banner lines that are longer than the
		 * available width. A <i>banner line</li> is a line with 5 or more identical non letter or digit
		 * characters (they do not have to be the same as the comment style's beginning of line character(s).
		 */
		@Override
		public BannerAdvice getBannerAdvice() {
			return BannerAdvice.Truncate;
		}

		@Override
		public CommentTextAdvice getCommentTextAdvice() {
			return CommentTextAdvice.Fold;
		}

		@Override
		public boolean isDoubleDollarVerbatim() {
			return true;
		}

	}

	public static class DefaultCommentAdviceProvider implements Provider<ICommentFormatterAdvice> {
		@Override
		public ICommentFormatterAdvice get() {
			return new DefaultCommentAdvice();
		}

	}

	// public static enum LineAlignment {
	// /**
	// * The line is placed flush left (no left margin).
	// */
	// Left,
	//
	// /**
	// * The line is aligned with the text's left margin.
	// */
	// Text,
	//
	// /**
	// * The line is left as it is (no leading WS removed or inserted).
	// */
	// Verbatim, ;
	// }

	/**
	 * If the advice is enabled, the more detailed advice should be used. If not, the comment should
	 * be formatted in verbatim/"preserve whitespace" mode.
	 * 
	 * @return true if the advice is enabled
	 */
	public boolean enabled();

	/**
	 * Returns how <i>homogeneous</i> lines (other than banners) should be aligned.
	 * 
	 * @return
	 */
	public boolean getAlignSpecialLinesLeft();

	/**
	 * Returns how <i>banner lines</li> should be formatted.
	 * 
	 * @see BannerAdvice
	 * @return how banner lines should be formatted
	 */
	public BannerAdvice getBannerAdvice();

	/**
	 * Returns how <i>comment text lines</li> should be formatted.
	 * 
	 * @see CommentTextAdvice
	 * @return how comment text lines should be formatted
	 */
	public CommentTextAdvice getCommentTextAdvice();

	/**
	 * Returns if embedded sequences of $ ... $ should be verbatim or not
	 * 
	 * @return true if text between $ should be verbatim
	 */
	boolean isDoubleDollarVerbatim();

}
