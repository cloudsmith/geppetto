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
public class ExceptionDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private Exception exception;

	public ExceptionDiagnostic(int severity, DiagnosticType type, String message, Exception exception) {
		super(severity, type, message);
		this.exception = exception;
	}

	@Override
	public Exception getException() {
		return exception;
	}
}
