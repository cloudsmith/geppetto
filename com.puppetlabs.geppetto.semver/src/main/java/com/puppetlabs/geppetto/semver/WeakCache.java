/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.semver;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Generic weak key, weak value cache that ensures that all current entries of T are
 * that are equal also are the exact same instance
 * 
 * @param <T>
 */
public class WeakCache<T> {
	private final Map<T, WeakReference<T>> cache = new WeakHashMap<T, WeakReference<T>>();

	/**
	 * Return the cached instance of the given value. Cache it if its not in the cache yet.
	 * The method returns <code>null</code> on <code>null</code> input.
	 * 
	 * @param value
	 *            The value to lookup, and perhaps also store, in the cache
	 * @return The cached value.
	 */
	public T cache(T value) {
		if(value != null) {
			WeakReference<T> ref = cache.get(value);
			if(ref != null) {
				T cachedValue = ref.get();
				if(cachedValue != null)
					return cachedValue;
			}
			cache.put(value, new WeakReference<T>(value));
		}
		return value;
	}
}
