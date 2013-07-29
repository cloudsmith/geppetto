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
package org.cloudsmith.geppetto.diagnostic;

/**
 */
public class ExecutionDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private final int exitStatus;

	private final String errorMessage;

	public ExecutionDiagnostic(int severity, DiagnosticType type, int exitStatus, String out, String errorMessage) {
		super(severity, type, out);
		this.exitStatus = exitStatus;
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getExitStatus() {
		return exitStatus;
	}
}
