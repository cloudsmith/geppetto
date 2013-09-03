/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.xtext.dommodel.formatter;

import com.puppetlabs.xtext.dommodel.IDomNode;
import com.puppetlabs.xtext.dommodel.RegionMatch;
import com.puppetlabs.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import com.puppetlabs.xtext.dommodel.formatter.css.debug.FormattingTracer;

import com.google.inject.Inject;

/**
 * Abstract implementation of the most basic layout interface.
 * 
 */
public abstract class AbstractLayout implements ILayout {

	@Inject
	protected FormattingTracer tracer;

	/**
	 * <p>
	 * Intersects the text of the node at its offset with the given {@link ILayoutContext#getRegionToFormat()}. The result is a {@link RegionMatch}
	 * that can answer further questions about inside/outside/contained, and also split the text into the before-, inside-, and after-, parts.
	 * </p>
	 * <p>
	 * Note that even if text is not wanted for a node, does not mean it does not have to be processed - it may still affect the formatting in the
	 * wanted region.
	 * </p>
	 * 
	 * @param node
	 * @param context
	 * @return true if the node should be formatted and text produced
	 */
	protected RegionMatch intersect(IDomNode node, ILayoutContext context) {
		return new RegionMatch(node, context.getRegionToFormat());
	}

}
