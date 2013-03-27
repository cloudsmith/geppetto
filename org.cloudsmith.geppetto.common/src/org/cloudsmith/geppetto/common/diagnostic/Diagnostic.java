/**
 * Copyright 2013-, Cloudsmith Inc.
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
package org.cloudsmith.geppetto.common.diagnostic;

import static java.lang.String.format;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

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

	private String source;

	private List<Diagnostic> children;

	private int type;

	public Diagnostic() {
	}

	/**
	 * Creates a new Diagnostic instance
	 * 
	 * @param severity
	 *            Severity (see constants in {@link MessageWithSeverity})
	 * @param type
	 *            The type of message
	 * @param message
	 *            The textual content of the message
	 */
	public Diagnostic(int severity, DiagnosticType type, String message) {
		setSeverity(severity);
		setMessage(message);
		setType(type);
	}

	/**
	 * Creates a new Diagnostic instance
	 * 
	 * @param severity
	 *            Severity (see constants in {@link MessageWithSeverity})
	 * @param type
	 *            The type of message
	 * @param message
	 *            The textual content of the message
	 */
	public Diagnostic(int severity, String source, DiagnosticType type, String message) {
		setSource(source);
		setSeverity(severity);
		setMessage(message);
		setType(type);
	}

	public void addChild(Diagnostic child) {
		if(getSeverity() < child.getSeverity())
			setSeverity(child.getSeverity());
		if(children == null)
			children = new ArrayList<Diagnostic>();
		children.add(child);
		childAdded(child);
	}

	public void addChildren(Collection<Diagnostic> children) {
		for(Diagnostic child : children)
			addChild(child);
	}

	public boolean appendLocationLabel(StringBuilder builder, boolean withOffsets) {
		return false;
	}

	/**
	 * Implementors may want to override this method for direct logging purposes
	 * 
	 * @param child
	 *            The child that was added to this instance
	 */
	protected void childAdded(Diagnostic child) {
		// Default is to do nothing.
	}

	public List<Diagnostic> getChildren() {
		return children == null
				? Collections.<Diagnostic> emptyList()
				: children;
	}

	public String getErrorText() {
		StringBuilder bld = new StringBuilder();
		toString(ERROR, bld, false, 0);
		return bld.toString();
	}

	/**
	 * Scans the children, depth first, until an ExceptionDiagnostic is found. The
	 * exception held by that diagnostic is return.
	 * 
	 * @return The first exception found or <code>null</code> if no exception exists.
	 */
	public Exception getException() {
		if(children != null)
			for(Diagnostic child : children) {
				Exception found = child.getException();
				if(found != null)
					return found;
			}
		return null;
	}

	/**
	 * Returns <tt>null</tt> unless subclassed
	 * 
	 * @return <tt>null</tt>
	 * @see FileDiagnostic
	 */
	public File getFile() {
		return null;
	}

	/**
	 * Returns <tt>-1</tt> unless subclassed
	 * 
	 * @return <tt>-1</tt>
	 * @see FileDiagnostic
	 */
	public int getLine() {
		return -1;
	}

	/**
	 * Returns the result of calling {{@link #appendLocationLabel(StringBuilder, boolean)} on a StringBuilder or <tt>null</tt> if no location label is
	 * present.
	 * 
	 * @param withOffsets
	 *            Flag that indicates if offsets from the beginning of file are
	 *            of interest (can be used for highlighting in editors).
	 * @return The location label or <tt>null</tt>
	 * @see FileDiagnostic
	 */
	public String getLocationLabel(boolean withOffsets) {
		StringBuilder bld = new StringBuilder();
		return appendLocationLabel(bld, withOffsets)
				? bld.toString()
				: null;
	}

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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return The type of diagnostic
	 */
	public DiagnosticType getType() {
		return DiagnosticType.values()[type];
	}

	/**
	 * This method is needed by net.sf.json but should not otherwise
	 * be used.
	 * 
	 * @param children
	 *            The new children to set
	 * @see #addChild(Diagnostic)
	 * @see #addChildren(Collection)
	 */
	public void setChildren(List<Diagnostic> children) {
		this.children = null;
		addChildren(children);
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

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	public void setType(DiagnosticType type) {
		this.type = type.ordinal();
	}

	@Override
	public final String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld, 0);
		return bld.toString();
	}

	private void toString(int severity, StringBuilder bld, boolean includeTopSeverity, int indent) {
		if(getSeverity() < severity)
			// Severity is transitive, so nothing to add here
			return;

		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		String resourcePath = getFile() == null
				? null
				: getFile().getPath();

		if(getMessage() == null && resourcePath == null) {
			if(children == null) {
				bld.append(getSeverityString());
				return;
			}
		}
		else {
			if(includeTopSeverity || indent > 0) {
				bld.append(getSeverityString());
				bld.append(':');
			}

			if(resourcePath != null) {
				bld.append(resourcePath);
				bld.append(':');
			}
			if(appendLocationLabel(bld, true))
				bld.append(':');

			if(getMessage() != null) {
				bld.append(getMessage());
				bld.append(':');
			}

			bld.setLength(bld.length() - 1);
			if(children != null) {
				bld.append('\n');
				indent += 4;
			}
		}

		if(children != null) {
			int top = children.size();
			int idx = 0;
			for(;;) {
				children.get(idx).toString(severity, bld, includeTopSeverity, indent);
				if(++idx >= top)
					break;
				bld.append('\n');
			}
		}
	}

	public void toString(StringBuilder bld, int indent) {
		toString(INFO, bld, true, indent);
	}
}
