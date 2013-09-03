/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   AT&T - initial API
 *   Puppet Labs
 * 
 */
package com.puppetlabs.xtext.dommodel.formatter.css;

/**
 * Describes Horizontal Alignment
 * 
 */
public enum Alignment {
	center, left, right,
	/**
	 * Alignment on separator. The separator's position is determined by the style {@link AlignedSeparatorIndex}.
	 * It is expected that a formatter defaults to regular expression "non word character" pattern, and uses the first
	 * matching character's index as the separator index.
	 */
	separator;

}
