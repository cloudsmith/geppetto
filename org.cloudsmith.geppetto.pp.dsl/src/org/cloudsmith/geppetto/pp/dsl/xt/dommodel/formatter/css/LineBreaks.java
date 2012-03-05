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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

/**
 * Describes desired line breaks as an {@link IFlexibleQuantity}.
 * 
 */
public class LineBreaks extends FlexibleQuantity {
	public LineBreaks() {
		super(1);
	}

	public LineBreaks(int normal) {
		super(normal, normal, normal);
	}

	public LineBreaks(int min, int normal, int max) {
		super(min, normal, max);
	}
}
