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

import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.collect.Sets;

/**
 * Abstract implementation of ILayoutContext
 * 
 */
public abstract class AbstractLayoutContext implements ILayoutContext {

	protected Set<IDomNode> consumed = Sets.newHashSet();

	@Override
	public abstract DomCSS getCSS();

	@Override
	public abstract Acceptor getErrorAcceptor();

	@Override
	public abstract IIndentationInformation getIndentationInformation();

	@Override
	public abstract ILineSeparatorInformation getLineSeparatorInformation();

	/**
	 * This default implementation returns 132
	 */
	@Override
	public int getPreferredMaxWidth() {
		return 132;
	}

	@Override
	public abstract ITextRegion getRegionToFormat();

	/**
	 * This default implementation returns 0
	 */
	@Override
	public int getWrapIndentSize() {
		return 0;
	}

	@Override
	public boolean isConsumed(IDomNode node) {
		return consumed.contains(node);
	}

	@Override
	public abstract boolean isWhitespacePreservation();

	@Override
	public void markConsumed(IDomNode node) {
		consumed.add(node);
	}

}
