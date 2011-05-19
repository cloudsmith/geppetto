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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

public class ResourcePropertiesAdapterFactory extends AdapterFactoryImpl {
	public static ResourcePropertiesAdapterFactory eINSTANCE = new ResourcePropertiesAdapterFactory();

	public ResourcePropertiesAdapter adapt(Resource target) {
		return adapt(target, ResourcePropertiesAdapter.class);
	}

	/**
	 * Type safe variant of adapt
	 * 
	 * @param <T>
	 * @param target
	 * @param type
	 * @return
	 */
	public <T> T adapt(Resource target, Class<T> type) {
		return type.cast(super.adapt(target, type));
	}

	@Override
	protected Adapter createAdapter(Notifier target, Object type) {
		return new ResourcePropertiesAdapter();
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return type == ResourcePropertiesAdapter.class;
	}
}
