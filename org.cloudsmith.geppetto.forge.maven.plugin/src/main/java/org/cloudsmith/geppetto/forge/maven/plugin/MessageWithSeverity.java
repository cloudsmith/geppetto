/**
 * Copyright 2012-, Cloudsmith Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.cloudsmith.geppetto.forge.maven.plugin;

import static java.lang.String.format;

import java.io.Serializable;

public class MessageWithSeverity implements Serializable {
	private static final long serialVersionUID = 3125174260505451357L;

	public static final int FATAL = 5;

	public static final int ERROR = 4;

	public static final int WARNING = 3;

	public static final int INFO = 2;

	public static final int DEBUG = 1;

	public static final int OK = 0;

	private static final String[] severityStrings = new String[] { "OK", "DEBUG", "INFO", "WARNING", "ERROR", "FATAL" };

	/**
	 * Return the severity as a string. The string &quot;UNKNOWN(&lt;severity&gt;)&quot; will
	 * be returned if the argument represents an unknown severity.
	 * 
	 * @param severity
	 * @return A string representing the severity
	 */
	public static String getSeverityString(int severity) {
		return severity >= 0 && severity < severityStrings.length
				? severityStrings[severity]
				: format("UNKNOWN(%d)", severity);
	}

	private int severity;

	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

	public String getSeverityString() {
		return getSeverityString(getSeverity());
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

	@Override
	public final String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld, 0);
		return bld.toString();
	}

	public void toString(StringBuilder bld, int indent) {
		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		bld.append(getSeverityString(severity));
		if(message != null) {
			bld.append(':');
			bld.append(message);
		}
	}
}
