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
package org.cloudsmith.geppetto.common;

public class Strings {
	/**
	 * Return the argument if is null or if its length > 0, else return <code>null</code>.
	 * 
	 * @param s
	 *            The sequence to trim or <code>null</code>
	 * @return The trimmed sequence or <code>null</code>
	 */
	public static String emptyToNull(String s) {
		return s == null || s.length() == 0
				? null
				: s;
	}

	/**
	 * Trim both left and right whitespace. Return the result if the resulting
	 * length > 0, else return <code>null</code>.
	 * 
	 * @param s
	 *            The string to trim or <code>null</code>
	 * @return The trimmed string or <code>null</code>
	 */
	public static String trimToNull(String s) {
		if(s != null) {
			s = s.trim();
			if(s.length() == 0)
				s = null;
		}
		return s;
	}
}
