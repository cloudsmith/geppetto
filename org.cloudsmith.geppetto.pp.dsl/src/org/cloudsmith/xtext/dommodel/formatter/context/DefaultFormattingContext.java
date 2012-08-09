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

import org.cloudsmith.xtext.formatting.IPreferredMaxWidthInformation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
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

		@Inject
		private Provider<IPreferredMaxWidthInformation> maxWidthProvider;

		public IFormattingContext create(EObject semantic) {
			return create(semantic, FormattingOption.Format);
		}

		public IFormattingContext create(EObject semantic, FormattingOption option) {
			return new DefaultFormattingContext(lineInfoProvider.get(), //
				identInfoProvider.get(), //
				option == FormattingOption.PreserveWhitespace, //
				maxWidthProvider.get(), //
				0); // additional indent - TODO: consider removing this option
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

	private IPreferredMaxWidthInformation preferredMaxWidthInfo;

	private int wrapIndentSize;

	public DefaultFormattingContext(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
			boolean whitespacePreservation, IPreferredMaxWidthInformation preferredMaxWidthInfo, int wrapIndentSize) {
		this.lineInfo = lineInfo;
		this.indentInfo = indentInfo;
		this.whitespacePreservation = whitespacePreservation;
		this.preferredMaxWidthInfo = preferredMaxWidthInfo;
		this.wrapIndentSize = wrapIndentSize;

	}

	/**
	 * Produces a non whitespace preserving formatting context.
	 */
	@Inject
	public DefaultFormattingContext(ILineSeparatorInformation lineInfo, IIndentationInformation indentInfo,
			IPreferredMaxWidthInformation maxWidthInfo) {
		this(lineInfo, indentInfo, false, maxWidthInfo, 0);
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
		return preferredMaxWidthInfo.getPreferredMaxWidth();
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
