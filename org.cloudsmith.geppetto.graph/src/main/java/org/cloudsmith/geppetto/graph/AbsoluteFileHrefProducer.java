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
 * Produces HREF strings in absolute file path format.
 * 
 */
public class AbsoluteFileHrefProducer extends AbstractHrefProducer {

	@Override
	protected String file2HrefPath(File f, File root) {
		return f.getAbsolutePath();
	}

	@Override
	public String path2HrefPath(IPath f, IPath root) {
		return f.toPortableString();
	}

}
