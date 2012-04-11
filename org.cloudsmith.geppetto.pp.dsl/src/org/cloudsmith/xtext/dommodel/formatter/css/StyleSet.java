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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cloudsmith.xtext.dommodel.IDomNode;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

/**
 * A set of styles.
 * This implementation is not thread safe.
 * 
 */
public class StyleSet {
	public static class ImmutableStyleSet extends StyleSet {

		public ImmutableStyleSet(StyleSet s) {
			this.styleMap = ImmutableMap.copyOf(s.styleMap);
		}

	}

	public static ImmutableStyleSet withImmutableStyle(IStyle<?> style) {
		return new ImmutableStyleSet(withStyles(style));
	}

	public static ImmutableStyleSet withImmutableStyles(IStyle<?>... styles) {
		return new ImmutableStyleSet(withStyles(styles));
	}

	public static StyleSet withStyle(IStyle<?> style) {
		return withStyles(style);
	}

	/**
	 * Factory method.
	 * 
	 * @param styles
	 * @return
	 */
	public static StyleSet withStyles(IStyle<?>... styles) {
		StyleSet styleMap = new StyleSet();
		for(IStyle<?> s : styles)
			styleMap.put(s);
		return styleMap;
	}

	// protected Map<Class<?>, StyleBase<? extends Object>> styleMap;
	protected Map<Class<?>, IStyle<?>> styleMap;

	public StyleSet() {
	}

	/**
	 * Add all style settings from map into this map - overwrite existing values.
	 * 
	 * @param map
	 * @return this map
	 */
	public StyleSet add(StyleSet map) {
		// avoid doing work if there is nothing to add
		if(map == null || map.styleMap == null || map.styleMap.size() == 0)
			return this;
		if(styleMap == null)
			styleMap = new HashMap<Class<?>, IStyle<?>>();
		styleMap.putAll(map.styleMap);
		return this;
	}

	/**
	 * Returns the style of the given class, or null of this style is not included in the the set.
	 * 
	 * @param x
	 * @param node
	 * @return
	 */
	public <T> T getStyle(Class<T> x, IDomNode node) {
		if(styleMap == null)
			return null;
		return x.cast(styleMap.get(x));
	}

	public Collection<IStyle<?>> getStyles() {
		if(styleMap == null)
			return Collections.emptyList();
		return styleMap.values();
	}

	/**
	 * Gets the value of a particular style, or null if not set. See the respective style type (doc) for
	 * information about returned type.
	 * 
	 * @param <T>
	 * 
	 * @param x
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getStyleValue(Class<? extends IStyle<T>> x, IDomNode node) {
		if(styleMap == null)
			return null;
		IStyle<?> style = styleMap.get(x);
		if(style == null)
			return null;
		return (T) (style.getValue(node));
	}

	/**
	 * Gets the value of a particular style, or the result of the function applied to the node if the
	 * style is not set.
	 * See the respective style type (doc) for information about returned type.
	 * 
	 * @param <T>
	 * 
	 * @param styleClass
	 *            - the class of the wanted style
	 * @param node
	 *            - the node for which a style value is wanted
	 * @param defaultFunc
	 *            - a function producing a default value
	 * @return the value of the style
	 */
	public <T> T getStyleValue(Class<? extends IStyle<T>> styleClass, IDomNode node, Function<IDomNode, T> defaultFunc) {
		T style = getStyleValue(styleClass, node);
		return style == null
				? defaultFunc.apply(node)
				: style;
	}

	/**
	 * Gets the value of a particular style, or the given default value if not set.
	 * See the respective style type (doc) for information about returned type.
	 * 
	 * @param <T>
	 * 
	 * @param x
	 * @return
	 */
	public <T> T getStyleValue(Class<? extends IStyle<T>> x, IDomNode node, T defaultValue) {
		T style = getStyleValue(x, node);
		return style == null
				? defaultValue
				: style;
	}

	/**
	 * Put a style in the map - overwrite any existing entry for this style.
	 * 
	 * @param style
	 */
	public void put(IStyle<?> style) {
		if(styleMap == null)
			styleMap = new HashMap<Class<?>, IStyle<?>>();
		styleMap.put(style.getClass(), style);
	}
}
