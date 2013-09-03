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
package com.puppetlabs.geppetto.diagnostic;

import java.io.Serializable;

public final class DiagnosticType implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String name;

	private final String source;

	public DiagnosticType(String name, String source) {
		this.name = name;
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public String getSource() {
		return source;
	}
}
