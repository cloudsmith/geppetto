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
package org.cloudsmith.xtext.dommodel.formatter;

import org.cloudsmith.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.formatting.IIndentationInformation;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * IFormattingContext provides basic formatting information to the formatter; the indent string, the line
 * separator to use, and if the formatter should preserve whitespace or not.
 * 
 */
@ImplementedBy(IFormattingContext.Default.class)
public interface IFormattingContext {

	/**
	 * This is a default implementation of IFormattingContext. It can be used without injection.
	 * 
	 */
	public class Default implements IFormattingContext {

		private ILineSeparatorInformation lineInfo;

		private IIndentationInformation indentInfo;

		private boolean whitespacePreservation;

		private int preferredMaxWidth;

		private int wrapIndentSize;

		/**
		 * Produces a non whitespace preserving formatting context using a preferred max width of 132
		 * characters.
		 * 
		 * @param lineInfo
		 * @param indentInfo
		 */
		@Inject
		public Default(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo) {
			this(lineInfo, indentInfo, false);
		}

		/**
		 * Produces a context using a preferred max width of 132, and 0 additional wrap indent.
		 * characters.
		 * 
		 * @param lineInfo
		 * @param indentInfo
		 * @param whitespacePreservation
		 */
		public Default(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
				boolean whitespacePreservation) {
			this(lineInfo, indentInfo, whitespacePreservation, 132, 0);
		}

		public Default(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
				boolean whitespacePreservation, int preferredMaxWidth, int wrapIndentSize) {
			this.lineInfo = lineInfo;
			this.indentInfo = indentInfo;
			this.whitespacePreservation = whitespacePreservation;
			this.preferredMaxWidth = preferredMaxWidth;
			this.wrapIndentSize = wrapIndentSize;
		}

		@Override
		public IIndentationInformation getIndentationInformation() {
			return indentInfo;
		}

		@Override
		public ILineSeparatorInformation getLineSeparatorInformation() {
			return lineInfo;
		}

		@Override
		public int getPreferredMaxWidth() {
			return preferredMaxWidth;
		}

		@Override
		public int getWrapIndentSize() {
			return wrapIndentSize;
		}

		@Override
		public boolean isWhitespacePreservation() {
			return whitespacePreservation;
		}
	}

	/**
	 * A provider of IFormattingContext that by default using {@link FormattingContextProvider#get()} produces
	 * a non whitespace preserving context, as opposed to {@link FormattingContextProvider#getWhitespacePreserving()}.
	 * 
	 */
	public class FormattingContextProvider implements com.google.inject.Provider<IFormattingContext> {
		@Inject
		private ILineSeparatorInformation lineInfo;

		@Inject
		private IIndentationInformation indentInfo;

		/**
		 * Provides a non whitespace preserving IFormattingContext
		 * 
		 * @see com.google.inject.Provider#get()
		 */
		@Override
		public IFormattingContext get() {
			return new Default(lineInfo, indentInfo, false);
		}

		public IFormattingContext get(boolean whitespacePreserving) {
			return new Default(lineInfo, indentInfo, whitespacePreserving);
		}

		/**
		 * Provides a whitespace preserving IFormattingContext
		 * 
		 * @see com.google.inject.Provider#get()
		 */
		public IFormattingContext getWhiteSpacePreserving() {
			return new Default(lineInfo, indentInfo, true);
		}

	}

	public IIndentationInformation getIndentationInformation();

	public ILineSeparatorInformation getLineSeparatorInformation();

	/**
	 * @return the maximum preferred width of a text flow
	 */
	public int getPreferredMaxWidth();

	/**
	 * @return the wanted (additional) indent size for auto wrapped lines.
	 */
	public int getWrapIndentSize();

	public boolean isWhitespacePreservation();
}
