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

import java.io.File;

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
		logDiagnostic(0, diagnostic);
	}

	private void logDiagnostic(int indent, Diagnostic diag) {
		if(diag == null)
			return;

		StringBuilder bld = new StringBuilder();
		for(int idx = 0; idx < indent; ++idx)
			bld.append(' ');

		String msg = diag.getMessage();

		if(msg != null) {
			bld.append(diag.getType().name());
			bld.append(':');

			File file = diag.getFile();
			if(file != null) {
				bld.append(' ');
				bld.append(file.getPath());
				bld.append(':');
			}

			if(diag.appendLocationLabel(bld, false))
				bld.append(':');

			bld.append(' ');
			bld.append(msg);
			msg = bld.toString();
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
			indent += 4;
		}

		for(Diagnostic child : diag.getChildren())
			logDiagnostic(indent, child);
	}

}
