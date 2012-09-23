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
 * Describes desired spacing as an {@link IFlexibleQuantity}, with the additional support to
 * describe the space quantity as breakable (the default).
 * 
 */
public class Spacing extends FlexibleQuantity {
	private boolean breakable;

	public Spacing() {
		super(1);
		this.breakable = true;
	}

	public Spacing(boolean breakable) {
		this(1, 1, 1, true);
	}

	public Spacing(int normal) {
		this(normal, normal, normal, true);
	}

	public Spacing(int normal, boolean breakable) {
		this(normal, normal, normal, breakable);
	}

	public Spacing(int min, int normal, int max) {
		this(min, normal, max, true);
	}

	public Spacing(int min, int normal, int max, boolean breakable) {
		super(min, normal, max);
		this.breakable = breakable;
	}

	public boolean isBreakable() {
		return breakable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spacing(");
		Joiner.on(",").appendTo(builder, getMin(), getNormal(), getMax(), isBreakable());
		builder.append(")");
		return builder.toString();
	}
}
