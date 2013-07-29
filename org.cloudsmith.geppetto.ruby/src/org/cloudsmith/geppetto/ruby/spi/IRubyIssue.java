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
package org.cloudsmith.geppetto.ruby.spi;

/**
 * Represents an issue reported by a ruby parser.
 */
public interface IRubyIssue {
	public Object[] getData();

	/**
	 * Returns null if issue did not report a filename
	 * 
	 * @return
	 */
	public String getFileName();

	/**
	 * Returns "jruby.syntax.error" if this issue represents a syntax error,
	 * else the ID as a string determined by the ruby parser.
	 * 
	 * @return
	 */
	public String getIdString();

	/**
	 * Returns the number of characters known to cause the problem (from
	 * {@link #getStartOffset()}.
	 * 
	 * @return -1 if not available.
	 */
	public int getLength();

	/**
	 * Returns the line where the issue was found. The line number start with 1
	 * for the first line.
	 * 
	 * @return
	 */
	public int getLine();

	public String getMessage();

	/**
	 * Returns -1 if no start line has been set.
	 * 
	 * @return
	 */
	public int getStartLine();

	/**
	 * Returns the start position of the problem (from position 0 in the file)
	 * 
	 * @return -1 if not available.
	 */
	public int getStartOffset();

	/**
	 * Indicates if this is a syntax error. This is the same as getId() == null.
	 * 
	 * @return
	 */
	public boolean isSyntaxError();
}
