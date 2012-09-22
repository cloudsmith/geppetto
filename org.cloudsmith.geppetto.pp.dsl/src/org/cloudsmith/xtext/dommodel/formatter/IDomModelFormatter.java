/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Itemis - initial API
 *   Cloudsmith - initial API and implementation
 * 
 */
package org.cloudsmith.xtext.dommodel.formatter;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;

// TODO: Fix next import
// import com.sun.istack.internal.Nullable;

/**
 * A Formatter capable of formatting a DomModel
 * TODO: rename to IFormatter (named differently to maintain sanity while implementing)
 * TODO: is the description of how the produced ReplaceRegion's offset/length correct?
 * TODO: Add @Nullable to the regionToFormat parameter (not found in com.google.inject.internal.Nullable).
 */
public interface IDomModelFormatter {

	/**
	 * Same as {@link #format(IDomNode, ITextRegion, IFormattingContext, org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor)}
	 * but uses an exception throwing error acceptor.
	 * 
	 * @param dom
	 * @param regionToFormat
	 * @param formattingContext
	 * @return
	 */
	public ReplaceRegion format(IDomNode dom, /* @Nullable */ITextRegion regionToFormat,
			IFormattingContext formattingContext);

	/**
	 * Formats the given dom node (and its children) and produces a ReplaceRegion with the formatted text.
	 * The returned region has the offset and length given by the regionToFormat, or if this region is null,
	 * offset 0, and the length of the produced formatted text.
	 * 
	 * @param dom
	 *            - the dom to format
	 * @param regionToFormat
	 *            - the region to format, or null if everything should be formatted
	 * @param formattingContext
	 *            - formatting parameters
	 * @param errors
	 *            - an acceptor of errors discovered during formatting
	 * @return
	 */
	public ReplaceRegion format(IDomNode dom, /* @Nullable */ITextRegion regionToFormat,
			IFormattingContext formattingContext, ISerializationDiagnostic.Acceptor errors);
}
