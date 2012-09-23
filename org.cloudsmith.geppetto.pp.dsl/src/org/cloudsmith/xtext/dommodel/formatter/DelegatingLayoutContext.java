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

import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;

/**
 * A delegating Layout Context that can override the max width in the delegate, and that does not mark
 * consumed items as consumed in the delegate.
 * 
 */
public class DelegatingLayoutContext extends AbstractLayoutContext {

	private final ILayoutContext delegate;

	private final int preferredMaxWidth;

	public DelegatingLayoutContext(ILayoutContext delegate) {
		this.delegate = delegate;
		this.preferredMaxWidth = delegate.getPreferredMaxWidth();

	}

	public DelegatingLayoutContext(ILayoutContext delegate, int preferredMaxWidth) {
		this.delegate = delegate;
		this.preferredMaxWidth = preferredMaxWidth;
	}

	@Override
	public DomCSS getCSS() {
		return delegate.getCSS();
	}

	@Override
	public Acceptor getErrorAcceptor() {
		// TODO: This is not optimal, errors from temporary/exploratory formatting may show up in the
		// main error reporting (OTOH: something is wrong with the setup, and it does perhaps not
		// matter where it appears.
		return delegate.getErrorAcceptor();
	}

	@Override
	public IIndentationInformation getIndentationInformation() {
		return delegate.getIndentationInformation();
	}

	@Override
	public ILineSeparatorInformation getLineSeparatorInformation() {
		return delegate.getLineSeparatorInformation();
	}

	@Override
	public int getPreferredMaxWidth() {
		return this.preferredMaxWidth;
	}

	@Override
	public ITextRegion getRegionToFormat() {
		return delegate.getRegionToFormat();
	}

	@Override
	public boolean isWhitespacePreservation() {
		return delegate.isWhitespacePreservation();
	}

}
