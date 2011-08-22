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
package org.cloudsmith.geppetto.pp.dsl.linking;

/**
 * Contains information about a task parsed from "TODO", "FIXME" and the like
 * 
 */
public class PPTask {

	final private String msg;

	final private int line;

	final private int offset;

	final private int length;

	final boolean important;

	public PPTask(String msg, int line, int offset, int length, boolean important) {
		this.msg = msg;
		this.line = line;
		this.offset = offset;
		this.length = length;
		this.important = important;
	}

	public int getLength() {
		return length;
	}

	public int getLine() {
		return line;
	}

	public String getMsg() {
		return msg;
	}

	public int getOffset() {
		return offset;
	}

	public boolean isImportant() {
		return important;
	}
}
