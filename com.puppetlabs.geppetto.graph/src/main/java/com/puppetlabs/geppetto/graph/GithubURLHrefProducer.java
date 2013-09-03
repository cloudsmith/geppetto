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
package org.cloudsmith.geppetto.graph;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Producer of HREF attribute suitable for github (e.g. https://github.com/owner/proj/branch/<file>#Lnn).
 * The prefix "https://github.com/<ownder>/<repo>/<branch>/" should be set as the Configurable prefix.
 * 
 */
public class GithubURLHrefProducer extends RelativeFileHrefProducer {
	@Inject(optional = true)
	@Named(AbstractHrefProducer.URL_PREFIX_NAME)
	private String URLprefix;

	/**
	 * This implementation returns and URL to the file at github at a specific line.
	 * This requires that the prefix has been set correctly.
	 * 
	 * @param path
	 * @param line
	 * @param start
	 * @param length
	 * @return
	 */
	@Override
	protected String hrefToFileLocation(String path, int line, int start, int length) {
		StringBuilder builder = new StringBuilder();
		if(URLprefix == null)
			builder.append('/');
		else {
			builder.append(this.URLprefix);
			if(!URLprefix.endsWith("/"))
				builder.append('/');
		}
		builder.append(path);
		if(line != -1) {
			builder.append("#L");
			builder.append(line);
		}
		// offset-length not supported at github
		return builder.toString();
	}

	@Override
	protected String hrefToModule(String path) {
		return hrefToFileLocation(path, -1, -1, -1);
	}

	@Override
	protected String hrefToNode(String path) {
		return hrefToFileLocation(path, -1, -1, -1);
	}
}
