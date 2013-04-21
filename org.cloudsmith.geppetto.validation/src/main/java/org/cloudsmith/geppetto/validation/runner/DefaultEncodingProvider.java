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
package org.cloudsmith.geppetto.validation.runner;

import org.eclipse.emf.common.util.URI;

/**
 * A default implementation of IEncodingProvider that always returns UTF-8
 * 
 */
public class DefaultEncodingProvider implements IEncodingProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cloudsmith.geppetto.validation.runner.IEncodingProvider#getEncoding
	 * (java.io.File)
	 */
	@Override
	public String getEncoding(URI uri) {
		return "UTF-8";
	}

}
