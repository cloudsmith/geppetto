/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.common.diagnostic;

public class DetailedFileDiagnostic extends FileDiagnostic {
	private Integer offset;

	private Integer length;

	private String issue;

	private String[] issueData;

	/**
	 * The issue is a String naming a particular issue that makes it possible to
	 * have a more detailed understanding of an error and what could be done to
	 * repair it. (As opposed to parsing the error message to gain an
	 * understanding). Error messages may
	 * 
	 * @return the value of the '<em>issue</em>' attribute.
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * The issue data is optional data associated with a particular issue - it
	 * is typically used to pass values calculated during
	 * org.cloudsmith.geppetto.validation and that may be meaningful to code
	 * that tries to repair or analyze a particular problem and where it may be
	 * expensive to recompute these values.
	 * 
	 * @return the value of the '<em>issueData</em>' attribute.
	 */
	public String[] getIssueData() {
		return issueData;
	}

	/**
	 * Length is the length (from offset) that is the textual representation
	 * that the diagnostic apply to , or 0 if there where no visible textual
	 * representation. A length of -1 indicates that length is irrelevant.
	 * 
	 * @return the value of the '<em>length</em>' attribute.
	 */
	public Integer getLength() {
		return length;
	}

	@Override
	public String getLocationLabel() {
		int lineNumber = getLineNumber();
		int offset = getOffset();
		int length = getLength();
		StringBuilder builder = new StringBuilder();
		if(lineNumber > 0)
			builder.append(lineNumber);
		else
			builder.append("-");

		if(offset >= 0) {
			builder.append("(");
			builder.append(offset);
			if(length >= 0) {
				builder.append(",");
				builder.append(length);
			}
			builder.append(")");
		}
		return builder.toString();
	}

	/**
	 * The offset from the beginning of the parsed text, or -1 if offset is not
	 * available.
	 * 
	 * @return the value of the '<em>offset</em>' attribute.
	 */
	public Integer getOffset() {
		return offset;
	}

	public void setIssue(String newIssue) {
		issue = newIssue;
	}

	public void setIssueData(String[] newIssueData) {
		issueData = newIssueData;
	}

	public void setLength(Integer newLength) {
		length = newLength;
	}

	public void setOffset(Integer newOffset) {
		offset = newOffset;
	}

}
