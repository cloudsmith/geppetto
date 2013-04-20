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
package org.cloudsmith.geppetto.graph;

import java.io.File;

import org.eclipse.core.runtime.IPath;

/**
 * Producer of HREF attribute consisting of file path relative to root + url parameters.
 * 
 */
public class RelativeFileHrefProducer extends AbstractHrefProducer {

	@Override
	protected String file2HrefPath(File f, File root) {
		return relativePathToFile(f.getAbsolutePath(), root).getPath();
	}

	@Override
	public String path2HrefPath(IPath f, IPath root) {
		return relativePathToFile(f, root).toPortableString();
	}

}
