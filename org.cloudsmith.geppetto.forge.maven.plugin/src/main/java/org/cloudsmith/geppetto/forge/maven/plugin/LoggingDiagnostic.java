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
package org.cloudsmith.geppetto.forge.maven.plugin;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.slf4j.Logger;

/**
 */
public class LoggingDiagnostic extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private final Logger logger;

	public LoggingDiagnostic(Logger logger) {
		this.logger = logger;
	}

	@Override
	protected void childAdded(Diagnostic diagnostic) {
		logDiagnostic("", diagnostic);
	}

	private void logDiagnostic(String indent, Diagnostic diag) {
		if(diag == null)
			return;

		String msg = diag.getMessage();
		if(indent != null)
			msg = indent + msg;

		if(msg != null) {
			msg = diag.getType().name() + ": " + msg;
			switch(diag.getSeverity()) {
				case Diagnostic.DEBUG:
					logger.debug(msg);
					break;
				case Diagnostic.WARNING:
					logger.warn(msg);
					break;
				case Diagnostic.FATAL:
				case Diagnostic.ERROR:
					logger.error(msg);
					break;
				default:
					logger.info(msg);
			}
			if(indent == null)
				indent = "  ";
			else
				indent = indent + "  ";
		}

		for(Diagnostic child : diag.getChildren())
			logDiagnostic(indent, child);
	}

}
