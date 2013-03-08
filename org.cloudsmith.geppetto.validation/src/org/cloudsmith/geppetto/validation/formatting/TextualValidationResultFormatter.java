/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.validation.formatting;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;

import org.cloudsmith.geppetto.common.diagnostic.DetailedFileDiagnostic;
import org.cloudsmith.geppetto.validation.runner.BuildResult;

/**
 * Formats diagnostics information as textual output. The general format is:<br/>
 * <code>SOURCE:SEVERITY:RESOURCEPATH:LOCATION:CODE:MESSAGE</code></br>
 * where <code>LOCATION</code> is one of:
 * <ul>
 * <li>LINE</li>
 * <li>LINE(OFFSET)</li>
 * <li>LINE(OFFSET,LENGTH)</li>
 * </ul>
 * and LINE is either a line number (starting with 1) or '-' if line number is not available, and where
 * OFFSET (character count from start of file), and LENGTH are included if set.
 * 
 */
public class TextualValidationResultFormatter implements IValidationResultFormatter {

	@Override
	public void format(BuildResult buildResult, BasicDiagnostic diagnostics, OutputStream out) {
		PrintStream p = out instanceof PrintStream
				? (PrintStream) out
				: new PrintStream(out);
		formatDignostics(diagnostics, p);
		p.flush();
	}

	/**
	 * SOURCE:SEVERITY:RESOURCEPATH:LOCATION:CODE:MESSAGE
	 * 
	 * @param d
	 * @param detail
	 * @param p
	 */
	private void formatDetail(Diagnostic d, DetailedFileDiagnostic detail, PrintStream p) {
		String source = d.getSource();
		String message = d.getMessage();
		String severity = severityLabel(d);
		String resourcePath = detail.getFile().getPath();
		String locationLabel = locationLabel(detail);
		String code = detail.getIssue();

		p.printf("%s:%s:%s:%s:%s:%s", source, severity, resourcePath, locationLabel, code, message);
	}

	/**
	 * @param diagnostics
	 * @param p
	 */
	private void formatDignostics(BasicDiagnostic diagnostics, PrintStream p) {
		for(Diagnostic d : diagnostics.getChildren()) {
			DetailedFileDiagnostic detail = getDetail(d);
			if(detail == null)
				formatExceptionDiagnostic(d, p);
			else
				formatDetail(d, detail, p);

		}
	}

	/**
	 * Outputs
	 * <pre>
	 * SOURCE:SEVERITY:MESSAGE
	 * STACK TRACE
	 * ---
	 * </pre>
	 * 
	 * @param d
	 * @param p
	 */
	private void formatExceptionDiagnostic(Diagnostic d, PrintStream p) {
		String source = d.getSource();
		String message = d.getMessage();
		String severity = severityLabel(d);
		p.printf("%s:%s:%s\n", source, severity, message);
		d.getException().printStackTrace(p);
		p.print("---\n");
	}

	private DetailedFileDiagnostic getDetail(Diagnostic d) {
		if(d.getData().size() < 1)
			return null;
		Object x = d.getData().get(0);
		if(x instanceof DetailedFileDiagnostic)
			return (DetailedFileDiagnostic) x;
		return null;
	}

	/**
	 * Formats location as
	 * LINE(OFFSET,LENGTH), or if LINE is -1 using '-'. If offset is >= 0 the () section is included,
	 * the ", length" part is only produced if length >= 0.
	 * 
	 * @param detail
	 * @return
	 */
	private String locationLabel(DetailedFileDiagnostic detail) {
		int lineNumber = detail.getLineNumber();
		int offset = detail.getOffset();
		int length = detail.getLength();
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

	protected String severityLabel(Diagnostic d) {
		String label = "unknown severity";
		switch(d.getSeverity()) {
			case Diagnostic.CANCEL:
				label = "canceled";
				break;
			case Diagnostic.WARNING:
				label = "warning";
				break;
			case Diagnostic.ERROR:
				label = "error";
				break;
			case Diagnostic.INFO:
				label = "info";
				break;
			case Diagnostic.OK:
				label = "ok";
				break;
		}
		return label;
	}

}
