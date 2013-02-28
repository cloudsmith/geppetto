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
package org.cloudsmith.geppetto.forge.maven.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.validation.DiagnosticType;

public class Diagnostic extends MessageWithSeverity {

	private static final long serialVersionUID = -1645151837353215387L;

	private int httpCode = 0;

	private String source;

	private String resourcePath;

	private String locationLabel;

	private String issue;

	private List<Diagnostic> children;

	private int type;

	private static final Pattern lineNumberPattern = Pattern.compile("^(\\d+).*");

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

	public void addChild(Diagnostic child) {
		if(getSeverity() < child.getSeverity())
			setSeverity(child.getSeverity());
		if(children == null)
			children = new ArrayList<Diagnostic>();
		children.add(child);
	}

	public void addChildren(Collection<Diagnostic> children) {
		for(Diagnostic child : children)
			addChild(child);
	}

	public List<Diagnostic> getChildren() {
		return children == null
				? Collections.<Diagnostic> emptyList()
				: children;
	}

	public String getErrorText() {
		StringBuilder bld = new StringBuilder();
		toString(ERROR, bld, 0);
		return bld.toString();
	}

	public int getHttpCode() {
		if(httpCode != 0)
			return httpCode;

		return getSeverity() == ERROR
				? 202
				: 200;
	}

	/**
	 * The issue is a String naming a particular issue that makes it possible to have a more detailed understanding of an error
	 * and what could be done to repair it. (As opposed to parsing the error message to gain an understanding). Error messages may
	 * be subject to NLS translation, but never the issue.
	 * 
	 * @return the issue
	 */
	public String getIssue() {
		return issue;
	}

	public int getLine() {
		if(locationLabel != null) {
			Matcher m = lineNumberPattern.matcher(locationLabel);
			if(m.matches())
				return Integer.parseInt(m.group(1));
		}
		return 0;
	}

	/**
	 * The location in a resource formatted as LINE(OFFSET,LENGTH), or if LINE is -1 using '-'. If offset is >= 0 the () section
	 * is included, the ", length" part is only produced if length >= 0
	 * 
	 * @return the locationLabel
	 */
	public String getLocationLabel() {
		return locationLabel;
	}

	/**
	 * ResourcePath is a reference to a relative or absolute file, or is empty/null if the diagnostic is not file related.
	 * 
	 * All diagnostic relating to files given (directly or contained) in the calls to the ValidationService will be reported with
	 * path's relative to the given root, or in the case of a single file, the leaf part of the path (the file name). Diagnostics
	 * relating to absolute files may appear - these may refer to files that are used by a particular diagnostician (e.g. system
	 * libraries or general configuration files).
	 * 
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
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

	public void setHttpCode(int code) {
		this.httpCode = code;
	}

	/**
	 * @param issue
	 *            the issue to set
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/**
	 * @param locationLabel
	 *            the locationLabel to set
	 */
	public void setLocationLabel(String locationLabel) {
		this.locationLabel = locationLabel;
	}

	/**
	 * @param resourcePath
	 *            the resourcePath to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
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

	private void toString(int severity, StringBuilder bld, int indent) {
		if(getSeverity() < severity)
			// Severity is transitive, so nothing to add here
			return;

		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		if(getMessage() == null && resourcePath == null && locationLabel == null) {
			if(children == null) {
				bld.append(getSeverityString());
				return;
			}
		}
		else {
			bld.append(getSeverityString());
			bld.append(':');

			if(resourcePath != null) {
				bld.append(resourcePath);
				bld.append(':');
			}
			if(locationLabel != null) {
				bld.append(locationLabel);
				bld.append(':');
			}
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
				children.get(idx).toString(severity, bld, indent);
				if(++idx >= top)
					break;
				bld.append('\n');
			}
		}
	}

	@Override
	public void toString(StringBuilder bld, int indent) {
		toString(INFO, bld, indent);
	}
}
