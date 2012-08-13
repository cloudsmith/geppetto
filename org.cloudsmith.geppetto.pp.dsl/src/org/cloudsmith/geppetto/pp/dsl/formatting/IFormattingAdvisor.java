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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;

/**
 * The Formatting Advisor provides answers to the formatter when there are options.
 * 
 */
@ImplementedBy(IFormattingAdvisor.Default.class)
public interface IFormattingAdvisor {

	public interface CommentAdvice {
		/**
		 * @return how banner lines should be formatted
		 */
		public WrappingAdvice bannerWrapping();

		/**
		 * Note: if formatting for ML comment is off, then no left alignment of '*' etc. is performed.
		 * 
		 * @return if formatting is turned on or not
		 */
		public boolean enabled();

		/**
		 * Note: If wrapping is off, a comment may still be left-aligned etc.
		 * 
		 * @return if formatting should wrap the comment (at all)
		 */
		public boolean wrapping();
	}

	@Singleton
	public static class Default implements IFormattingAdvisor {
		private static final CommentAdvice commentAdvice = new DefaultCommentAdvice();

		@Override
		public CommentAdvice multiLineFormattingAdvice() {
			return commentAdvice;
		}

		@Override
		public CommentAdvice singleLineCommentFormattingAdvice() {
			return commentAdvice;
		}

	}

	public static class DefaultCommentAdvice implements CommentAdvice {

		@Override
		public WrappingAdvice bannerWrapping() {
			return WrappingAdvice.Truncate;
		}

		@Override
		public boolean enabled() {
			return true;
		}

		@Override
		public boolean wrapping() {
			return true;
		}

	}

	public enum WrappingAdvice {
		NoWrap, Truncate, Fold;
	}

	public CommentAdvice multiLineFormattingAdvice();

	public CommentAdvice singleLineCommentFormattingAdvice();
}
