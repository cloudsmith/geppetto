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

/**
 */
public class ExceptionDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private Exception exception;

	public ExceptionDiagnostic(int severity, String source, DiagnosticType type, String message, Exception exception) {
		super(severity, source, type, message);
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}
