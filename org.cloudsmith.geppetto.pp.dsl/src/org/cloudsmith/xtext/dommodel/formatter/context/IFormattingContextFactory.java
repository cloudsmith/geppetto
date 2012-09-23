/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;

/**
 * A provider of IFormattingContext that by default using {@link IFormattingContextFactory#get()} produces
 * a non whitespace preserving context, as opposed to {@link IFormattingContextFactory#getWhitespacePreserving()}.
 * 
 * <p>
 * Note that this Provider does <i>not</i> implement <code>com.google.inject.Provider&lt;IFormattingContext&gt;</code>
 * </p>
 */
public interface IFormattingContextFactory {
	enum FormattingOption {
		PreserveWhitespace, Format
	}

	/**
	 * Provides a {@link FormattingOption.Format} IFormattingContext for a semantic
	 * object.
	 * 
	 * @throws IllegalArgumentException
	 *             if the semantic object has a null eResource().
	 */
	public IFormattingContext create(EObject semantic);

	/**
	 * Provides an IFormattingContext for a semantic object, that is either whitespacePreserving,
	 * or formatting.
	 * 
	 */
	public IFormattingContext create(EObject semantic, FormattingOption option);

	/**
	 * Provides an IFormattingContext for an {@link XtextResource}, that is formatting.
	 * This is the same as calling {@link #create(XtextResource, FormattingOption)} with {@link FormattingOption.Format}.
	 * 
	 */
	public IFormattingContext create(XtextResource resource);

	/**
	 * Provides an IFormattingContext for an {@link XtextResource}, that is either whitespacePreserving,
	 * or formatting.
	 * 
	 */
	public IFormattingContext create(XtextResource resource, FormattingOption option);

}
