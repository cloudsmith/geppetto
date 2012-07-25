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

import com.google.common.base.Joiner;

/**
 * Describes desired spacing as an {@link IFlexibleQuantity}.
 * 
 */
public class Spacing extends FlexibleQuantity {
	public Spacing() {
		super(1);
	}

	public Spacing(int normal) {
		super(normal, normal, normal);
	}

	public Spacing(int min, int normal, int max) {
		super(min, normal, max);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spacing(");
		Joiner.on(",").appendTo(builder, getMin(), getNormal(), getMax());
		builder.append(")");
		return builder.toString();
	}

}
