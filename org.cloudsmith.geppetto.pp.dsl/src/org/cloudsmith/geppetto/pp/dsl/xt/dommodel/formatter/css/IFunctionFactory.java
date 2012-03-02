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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

import com.google.common.base.Function;

/**
 * @author henrik
 * 
 */
public interface IFunctionFactory {

	public Function<IDomNode, String> literalString(String s);

	/**
	 * @return
	 */
	public Function<IDomNode, String> textOfNode();

}
