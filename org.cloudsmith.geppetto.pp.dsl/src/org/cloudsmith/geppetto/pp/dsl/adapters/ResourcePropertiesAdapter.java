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
package org.cloudsmith.geppetto.pp.dsl.adapters;

import java.util.Map;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

import com.google.common.collect.Maps;

/**
 * The ResourcePropertiesAdapter associates key/value String properties with a Resource.
 */
public class ResourcePropertiesAdapter extends AdapterImpl {

	Map<String, Object> properties = Maps.newHashMap();

	public void clear() {
		properties.clear();
	}

	public Object get(String key) {
		return properties.get(key);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == ResourcePropertiesAdapter.class;
	}

	public void put(String key, Object value) {
		properties.put(key, value);
	}
}
