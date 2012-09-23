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

import java.util.HashMap;
import java.util.Map;

/**
 * A StyleSet that allows tracking of the source of each style.
 * 
 */
public class StyleSetWithTracking extends StyleSet {
	protected Rule source;

	protected Map<Class<?>, Rule> sourceMap;

	@Override
	public StyleSet add(StyleSet map) {
		StyleSet result = super.add(map);
		if(sourceMap == null)
			sourceMap = new HashMap<Class<?>, Rule>();
		if(map.styleMap != null)
			for(Class<?> clazz : map.styleMap.keySet())
				sourceMap.put(clazz, source);
		return result;
	}

	public Rule getStyleSource(IStyle<?> x) {
		return sourceMap == null
				? null
				: sourceMap.get(x.getClass());
	}

	@Override
	public void put(IStyle<?> style) {
		super.put(style);
		if(sourceMap == null)
			sourceMap = new HashMap<Class<?>, Rule>();
		sourceMap.put(style.getClass(), source);
	}

	public void setSource(Rule source) {
		this.source = source;
	}
}
