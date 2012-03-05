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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.io.IOException;

/**
 * Interface for formatting stream.
 * 
 */
public interface IFormStream {

	/**
	 * Changes indentation by +/- count.<br/>
	 * . A count of 0 has no efect on indentation.
	 * <b>Example:</b>
	 * Calling this method with count 3 is the same as calling {@link #indent()} 3 times. Calling this method
	 * with count -3 is the same as calling {@link #dedent()} 3 times.
	 */
	public void changeIndentation(int count);

	public void dedent();

	public void flush() throws IOException;

	public String getText();

	public void indent();

	public void lineBreak();

	public void lineBreaks(int count);

	public void oneSpace();

	public int size();

	public void spaces(int count);

	public void text(String s);

}
