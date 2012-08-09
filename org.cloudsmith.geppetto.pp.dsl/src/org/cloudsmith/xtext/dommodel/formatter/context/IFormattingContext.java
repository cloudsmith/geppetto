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
package org.cloudsmith.xtext.dommodel.formatter.context;

import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.formatting.IIndentationInformation;

import com.google.inject.ImplementedBy;

/**
 * IFormattingContext provides basic formatting information to the formatter; the indent string, the line
 * separator to use, and if the formatter should preserve whitespace or not.
 * 
 */
@ImplementedBy(DefaultFormattingContext.class)
public interface IFormattingContext {

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
