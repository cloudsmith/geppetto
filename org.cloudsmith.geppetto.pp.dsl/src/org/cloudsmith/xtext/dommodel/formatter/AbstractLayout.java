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

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.debug.FormattingTracer;
import org.eclipse.xtext.util.ITextRegion;

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
	 * Checks if the given node is within the specified region to format given by the {@link ILayoutContext#getRegionToFormat()}.
	 * </p>
	 * <p>
	 * Note that even if text is not wanted for a node, does not mean it does nmot have to be processed - it may still affect the formatting in the
	 * wanted region.
	 * </p>
	 * 
	 * <b>TODO</b>: Consider returning Enum (BEFORE,IN, AFTER) to enable pruning of nodes after the region to format.
	 * 
	 * @param node
	 * @param context
	 * @return true if the node should be formatted and text produced
	 */
	protected boolean isFormattingWanted(IDomNode node, ILayoutContext context) {
		ITextRegion regionToFormat = context.getRegionToFormat();
		if(regionToFormat == null)
			return true;
		return regionToFormat.contains(node.getOffset());
	}

}
