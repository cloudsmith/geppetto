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
package org.cloudsmith.geppetto.pp.dsl.validation;

/**
 * Enumerator for validation preference.
 * 
 */
public enum ValidationPreference {

	IGNORE, WARNING, ERROR;

	public static ValidationPreference fromString(String s) {
		s = s.toLowerCase();
		if("ignore".equals(s))
			return IGNORE;
		if("warning".equals(s))
			return WARNING;
		if("error".equals(s))
			return ERROR;
		throw new IllegalArgumentException("The string '" + s + "' does not represent a ValidationPreference");
	}

	public boolean isError() {
		return this == ERROR;
	}

	public boolean isIgnore() {
		return this == IGNORE;
	}

	public boolean isWarning() {
		return this == WARNING;
	}

	public boolean isWarningOrError() {
		return this == WARNING || this == ERROR;
	}

	@Override
	public String toString() {
		switch(this) {
			case IGNORE:
				return "Ignore";
			case WARNING:
				return "Warning";
			case ERROR:
				return "Error";
		}
		throw new IllegalStateException("Can not happen");
	}
}
