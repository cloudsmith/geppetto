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
package org.cloudsmith.geppetto.ruby;

/**
 * Represents information about a puppet custom function; its name and if it
 * returns a value.
 */
public class PPFunctionInfo {
	private String functionName;

	private boolean rValue;

	private String documentation;

	public PPFunctionInfo(String name, boolean rvalue, String documentation) {
		this.functionName = name;
		this.rValue = rvalue;
		this.documentation = new RubyDocProcessor().asHTML(documentation);

	}

	public String getDocumentation() {
		return documentation;
	}

	public String getFunctionName() {
		return functionName;
	}

	public boolean isRValue() {
		return rValue;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("(PPFunctionInfo name=");
		buf.append(getFunctionName());
		buf.append(" isRValue=");
		buf.append(isRValue());
		buf.append(")");
		return buf.toString();

	}

}
