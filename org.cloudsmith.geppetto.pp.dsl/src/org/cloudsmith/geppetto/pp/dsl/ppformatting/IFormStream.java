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
 * Inerface for PP formatting stream.
 * 
 */
public interface IFormStream {

	public void breakLine();

	public void dedent();

	public void flush() throws IOException;

	public String getText();

	public void indent();

	public void noSpace();

	public void oneSpace();

	public int size();

	public void space(int count);

	public void text(String s);

}
