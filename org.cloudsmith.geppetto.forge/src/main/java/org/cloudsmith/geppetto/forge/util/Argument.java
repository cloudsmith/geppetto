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
package org.cloudsmith.geppetto.forge.util;

public class Argument {
	private final int offset;

	private final int length;

	private final Object value;

	public Argument(int offset, int length, Object value) {
		this.offset = offset;
		this.length = length;
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public int getOffset() {
		return offset;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value == null
				? "null"
				: value.toString();
	}

	public String toStringOrNull() {
		return value == null
				? null
				: value.toString();
	}
}
