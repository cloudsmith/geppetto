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
package org.cloudsmith.geppetto.pp.dsl.xt.formatter;

public interface ITextMetrics {
	/**
	 * Returns true if the measured text ends with a break
	 * 
	 * @return
	 */
	public boolean endsWithBreak();

	/**
	 * Produces the current height (in lines) of an abstract box of text.
	 * 
	 * @return a text height >= 0 < MAXINT
	 */
	int getHeight();

	/**
	 * Produces the current width (in characters) of an abstract box of text.
	 * 
	 * @return a text width >= 0 < MAXINT
	 */
	int getWidth();

	public int getWidthOfLastLine();

	public boolean isEmpty();
}
