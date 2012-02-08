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
package org.cloudsmith.geppetto.pp.dsl.adapters;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

public class CrossReferenceAdapterFactory extends AdapterFactoryImpl {
	public static CrossReferenceAdapterFactory eINSTANCE = new CrossReferenceAdapterFactory();

	public CrossReferenceAdapter adapt(EObject target) {
		return adapt(target, CrossReferenceAdapter.class);
	}

	/**
	 * Type safe variant of adapt
	 * 
	 * @param <T>
	 * @param target
	 * @param type
	 * @return
	 */
	public <T> T adapt(EObject target, Class<T> type) {
		return type.cast(super.adapt(target, type));
	}

	@Override
	protected Adapter createAdapter(Notifier target, Object type) {
		return new CrossReferenceAdapter();
	}

	public List<IEObjectDescription> get(EObject target) {
		for(Adapter adapter : target.eAdapters())
			if(adapter.isAdapterForType(CrossReferenceAdapter.class))
				return ((CrossReferenceAdapter) adapter).get();
		return null;
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return type == CrossReferenceAdapter.class;
	}
}
