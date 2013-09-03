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
package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.io.File;
import java.io.IOException;

import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.Node;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Utils for JRubyparser
 * 
 */
public class JRubyparserUtils {

	/**
	 * Finds position on line for a node.
	 * This is a very expensive operation as it needs to open the file, read it and scan!
	 * 
	 * @param n
	 * @return
	 */
	public static int posOnLine(Node n) {
		SourcePosition p = n.getPosition();
		String s = rootContent(n);
		if(s == null)
			return -1;
		for(int i = p.getStartOffset(); i > 0; i--)
			if(s.charAt(i) == '\n')
				return p.getStartOffset() - i - 1;
		return p.getStartOffset(); // it was on the firstline.
	}

	/**
	 * Reads the root content of the Node's source file and returns it as a string.
	 * Returns null on failure to read the content.
	 * 
	 * @param n
	 * @return
	 */
	public static String rootContent(Node n) {
		SourcePosition p = n.getPosition();
		File f = new File(p.getFile());
		try {
			return Files.toString(f, Charsets.UTF_8);
		}
		catch(IOException e) {
			return null;
		}

	}
}
