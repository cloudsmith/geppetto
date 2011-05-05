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
package org.cloudsmith.geppetto.pp.dsl.validation;

import org.cloudsmith.geppetto.common.tracer.IStringProvider;

/**
 * A very simple translator of Expression type to a label.
 * 
 */
public class PPExpressionTypeNameProvider implements IStringProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.IStringProvider#doToString(java.lang.Object)
	 */
	@Override
	public String doToString(Object o) {
		// *very* simple implementation
		return o.getClass().getSimpleName().replaceAll("Impl", "");
	}

}
