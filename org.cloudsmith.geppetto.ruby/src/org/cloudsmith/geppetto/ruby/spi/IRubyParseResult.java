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
package org.cloudsmith.geppetto.ruby.spi;

import java.util.List;

/**
 * Represents the result from a ruby parser.
 * Currently only provides abstraction of Ruby issues (syntax errors and warnings).
 *
 */
public interface IRubyParseResult {
	/**
	 * Returns a list of issues. Will return an empty list if there were no issues.
	 * @return
	 */
	public List<IRubyIssue> getIssues();
	
	/**
	 * Returns true if a syntax error was found.
	 */
	public boolean hasErrors();
	
	/**
	 * Returns true if there were issues found (warnings or errors).
	 */
	public boolean hasIssues();

}
