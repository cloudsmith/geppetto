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

/**
 * Describes desired spacing in terms of min, max, and normal
 * 
 */
public class Spacing {
	private int normal;

	private int max;

	private int min;

	public Spacing() {
		this(1);
	}

	public Spacing(int normal) {
		this(normal, normal, normal);
	}

	public Spacing(int min, int normal, int max) {
		this.min = min;
		this.max = max;
		this.normal = normal;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @return the normal
	 */
	public int getNormal() {
		return normal;
	}
}
