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

public class FlexibleQuantity implements IFlexibleQuantity {

	protected int normal;

	protected int max;

	protected int min;

	public FlexibleQuantity() {
		this(1);
	}

	public FlexibleQuantity(int normal) {
		this(normal, normal, normal);
	}

	public FlexibleQuantity(int min, int normal, int max) {
		this.min = min;
		this.max = max;
		this.normal = normal;
		if(min > normal || normal > max)
			throw new IllegalArgumentException("Values must comply with: min <= normal <= max");
	}

	@Override
	public int apply(int length) {
		if(length < 0)
			return normal;
		if(length < min)
			return min;
		if(length > max)
			return max;
		return length;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getNormal() {
		return normal;
	}

}
