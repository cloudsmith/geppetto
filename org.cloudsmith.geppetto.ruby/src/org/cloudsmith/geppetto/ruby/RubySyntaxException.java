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
package org.cloudsmith.geppetto.ruby;

/**
 * A simplified RubySyntaxException that should be thrown in operations where it is expected that syntax
 * errors are not present.
 *
 */
public class RubySyntaxException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RubySyntaxException() { }

}
