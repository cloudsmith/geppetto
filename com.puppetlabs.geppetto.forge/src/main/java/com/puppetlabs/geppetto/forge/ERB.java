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
package com.puppetlabs.geppetto.forge;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.puppetlabs.geppetto.forge.v2.model.Metadata;

/**
 * Perform very basic ERB (Embedded Ruby) template processing on a file in context of a {@link Metadata} instance.
 * &quot;very basic&quot; in this case means that no Ruby expressions are evaluated. This instance only handles:
 * <ul>
 * <li>&lt;%#<i>comment</i>%&gt;</li>
 * <li>&lt;%=<i>expression</i>%&gt;</li>
 * <li>&lt;%% (escape, output as '<%')</li>
 * </ul>
 * where <i>expression</i> must be in the form <tt>metadata.&lt;variable&gt;</tt> and
 * the variable must be one of the attributes in {@link Metadata}.
 */
public interface ERB {

	/**
	 * Generate a new file to <tt>result</tt> using the <tt>template</tt> file as input
	 * and <tt>context</tt> as the source for variable substitution.
	 * 
	 * @param context
	 * @param template
	 * @param result
	 * @throws IOException
	 *             on failure to read or write
	 * @throws UnsupportedOperationException
	 *             on constructs that are not handled
	 */
	void generate(Metadata context, File template, File result) throws IOException;

	/**
	 * Generate a new file to <tt>result</tt> using the <tt>template</tt> file as input
	 * and <tt>context</tt> as the source for variable substitution.
	 * 
	 * @param context
	 * @param template
	 * @param result
	 * @throws IOException
	 *             on failure to read or write
	 * @throws UnsupportedOperationException
	 *             on constructs that are not handled
	 */
	void generate(Metadata context, Reader template, Writer result) throws IOException;
}
