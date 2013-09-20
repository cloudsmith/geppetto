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
package com.puppetlabs.geppetto.puppetdb.ui.views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpSubstitution {
	private final Pattern pattern;

	private final String replacement;

	public RegexpSubstitution(String pattern, String replacement) {
		this.pattern = Pattern.compile(pattern);
		this.replacement = replacement;
	}

	public String replaceOrNull(String str) {
		Matcher m = pattern.matcher(str);
		return m.matches()
				? m.replaceAll(replacement)
				: null;
	}
}
