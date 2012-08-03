/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.dommodel.formatter.context;

import org.cloudsmith.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * <p>
 * This is a default implementation of IFormattingContext. Instances are created without consideration to the resource being formatted
 * ("one-size-fits-all"). Use more specialized contexts for resource and container based creation of IFormattingContext (e.g. to format code in
 * different "projects"/"containers" differently).
 * </p>
 * <p>
 * The returned IFormattingContext has a fixed max width of 132 characters, and picks up indentation and line separator information from the bindings
 * of {@link ILineSeparatorInformation}, and {@link IIndentationInformation}
 * <p>
 * The DefaultFormattingContext can be used without injection,or via a guice binding of:
 * 
 * <pre>
 * bind(IFormattingContextFactory.class).to(DefaultFormattingContext.Factory.class);
 * </pre>
 * 
 * </p>
 * <p>
 * It is also possible to bind this class via a simple binding to the <code>IFormattingContext.class</code>, but this assumes a very simple
 * configuration.
 * </p>
 */
public class DefaultFormattingContext implements IFormattingContext {

	/**
	 * This factory ignores the given semantic object, and returns the same result for all.
	 * 
	 */
	public static class Factory implements IFormattingContextFactory {
		@Inject
		private Provider<ILineSeparatorInformation> lineInfoProvider;

		@Inject
		private Provider<IIndentationInformation> identInfoProvider;

		public IFormattingContext create(EObject semantic) {
			return create(semantic, FormattingOption.Format);
		}

		public IFormattingContext create(EObject semantic, FormattingOption option) {
			return new DefaultFormattingContext(
				lineInfoProvider.get(), identInfoProvider.get(), option == FormattingOption.PreserveWhitespace);
		}

		@Override
		public IFormattingContext create(XtextResource resource) {
			return create((EObject) null, FormattingOption.Format);
		}

		@Override
		public IFormattingContext create(XtextResource resource, FormattingOption option) {
			return create((EObject) null, option);
		}
	}

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
	public DefaultFormattingContext(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo) {
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
	public DefaultFormattingContext(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
			boolean whitespacePreservation) {
		this(lineInfo, indentInfo, whitespacePreservation, 132, 0);
	}

	public DefaultFormattingContext(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
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
