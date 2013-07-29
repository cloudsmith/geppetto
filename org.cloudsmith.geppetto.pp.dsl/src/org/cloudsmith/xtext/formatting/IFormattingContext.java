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
package org.cloudsmith.xtext.formatting;

import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;

/**
 * @author henrik
 * 
 */
interface IFormattingContext {
	IIndentationInformation getIndentationInformation();

	ILineSeparatorInformation getLineSeparatorInformation();

	boolean isWhitespacePreservation();
}
