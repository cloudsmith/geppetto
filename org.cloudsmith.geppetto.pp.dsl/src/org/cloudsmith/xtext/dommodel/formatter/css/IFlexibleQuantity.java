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
package org.cloudsmith.xtext.dommodel.formatter.css;

/**
 * Describes a flexible int quantity (min, normal, max). Where min is the minimum allowed, normal
 * what is wanted if value is missing, and max, the maximum allowed value.
 * 
 */
public interface IFlexibleQuantity {

	/**
	 * Applies the flexible quantity to the given length, and produces a conformant value.
	 * 
	 * @param length
	 * @return
	 */
	int apply(int length);

	/**
	 * @return the max
	 */
	public int getMax();

	/**
	 * @return the min
	 */
	public int getMin();

	/**
	 * @return the normal
	 */
	public int getNormal();

}
