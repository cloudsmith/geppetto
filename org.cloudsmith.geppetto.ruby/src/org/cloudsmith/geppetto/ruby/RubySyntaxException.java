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

import java.util.List;

import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;

/**
 * A simplified RubySyntaxException that should be thrown in operations where it
 * is expected that syntax errors are not present.
 * 
 */
public class RubySyntaxException extends Exception {

	private static final long serialVersionUID = 1L;
	private String filename;
	private int line;
	private String message;

	public RubySyntaxException(List<IRubyIssue> issues) {
		FOUND: {
			for (IRubyIssue issue : issues) {
				if (issue.isSyntaxError()) {
					filename = issue.getFileName();
					line = issue.getLine();
					message = issue.getMessage();
					break FOUND;
				}
			}
			if(issues.size() < 1) {
				filename = "unknown";
				line = -1;
				message = "Can't find an error message to display!";
			}
			if(issues.size() == 1) {
				IRubyIssue issue = issues.get(0);
				filename = issue.getFileName();
				line = -1;
				message = issue.getMessage();				
			}
			else {
				IRubyIssue issue = issues.get(0);
				filename = issue.getFileName();
				line = -1;
				message = "Several non syntax errors - file is the first reported file.";
			}
		}
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	public String getFilename() {
		return filename;
	}

	public int getLine() {
		return line;
	}
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(filename);
		str.append(", line: ");
		str.append(line);
		str.append(" ");
		str.append(message);
		return str.toString();
	}
}
