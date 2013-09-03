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
package com.puppetlabs.geppetto.puppetlint.impl;

import static java.lang.String.format;

import com.puppetlabs.geppetto.puppetlint.PuppetLintRunner.Issue;
import com.puppetlabs.geppetto.puppetlint.PuppetLintRunner.Severity;

/**
 * An issue produced by puppet-lint
 */
public class PuppetLintIssue implements Issue {
	private final String path;

	private final Severity severity;

	private final String checkName;

	private final String message;

	private final int lineNumber;

	/**
	 * Creates a new PuppetLintIssue using the provided arguments
	 * 
	 * @param path
	 *            The relative path of the file
	 * @param severity
	 *            The severity (warning or error)
	 * @param checkName
	 *            The name of the check
	 * @param message
	 *            The generated message
	 * @param lineNumber
	 *            The line number
	 */
	public PuppetLintIssue(String path, Severity severity, String checkName, String message, int lineNumber) {
		this.path = path;
		this.severity = severity;
		this.checkName = checkName;
		this.message = message;
		this.lineNumber = lineNumber;
	};

	@Override
	public String getCheckName() {
		return checkName;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Severity getSeverity() {
		return severity;
	}

	@Override
	public String toString() {
		return format("%s: %s:%d: %s", severity, path, lineNumber, message);
	}
}
