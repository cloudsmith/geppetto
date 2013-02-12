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
package org.cloudsmith.geppetto.validation;

import java.io.File;

/**
 */
public class DiagnosticData {
	private File file;

	private Integer lineNumber = new Integer(-1);

	private String node;

	/**
	 * File is a reference to a relative or absolute file, or is empty/null if
	 * the diagnostic is not file related.
	 * 
	 * All diagnostic relating to files given (directly or contained) in the
	 * calls to the ValidationService will be reported with path's relative to
	 * the given root, or in the case of a single file, the leaf part of the
	 * path (the file name). Diagnostics relating to absolute files may appear -
	 * these may refer to files that are used by a particular diagnostician
	 * (e.g. system libraries or general configuration files).
	 * 
	 * @return the value of the '<em>file</em>' attribute.
	 * 
	 *         Also see #node.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Line number is the (first) line for which a diagnostician reported the
	 * diagnostic, or -1 if linenumber is irrelevant (or if file is null or
	 * empty). Note that some diagnosticians may return a DetailedDiagnosticData
	 * with further information.
	 * 
	 * @return the value of the '<em>lineNumber</em>' attribute.
	 */
	public Integer getLineNumber() {
		return lineNumber;
	}

	/**
	 * The node name is a symbolic reference to the host where the filename is
	 * relevant, or the node associated with a particular diagnostic (a
	 * particular file may have diagnostics that are only relevant for a
	 * particular node, but reporting a diagnostic for a particular node does
	 * not indicate that the problem relates only to this node, only that it was
	 * detected during processing of the given node). The special node "SERVER"
	 * indicates if the problem was diagnosed as pertaining to the server side.
	 * 
	 * @return the value of the '<em>node</em>' attribute.
	 */
	public String getNode() {
		return node;
	}

	public void setFile(File newFile) {
		file = newFile;
	}

	public void setLineNumber(Integer newLineNumber) {
		lineNumber = newLineNumber;
	}

	public void setNode(String newNode) {
		node = newNode;
	}
}
