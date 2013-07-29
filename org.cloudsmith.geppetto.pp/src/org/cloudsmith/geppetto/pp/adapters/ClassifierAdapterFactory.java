/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.pp.adapters;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

public class ClassifierAdapterFactory extends AdapterFactoryImpl {
	public static ClassifierAdapterFactory eINSTANCE = new ClassifierAdapterFactory();

	public ClassifierAdapter adapt(EObject target) {
		return adapt(target, ClassifierAdapter.class);
	}

	/**
	 * Type safe variant of adapt that synchronizes on target.
	 * 
	 * @param <T>
	 * @param target
	 * @param type
	 * @return
	 */
	public <T> T adapt(EObject target, Class<T> type) {
		synchronized(target) {
			return type.cast(super.adapt(target, type));
		}
	}

	@Override
	protected Adapter createAdapter(Notifier target, Object type) {
		return new ClassifierAdapter();
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return type == ClassifierAdapter.class;
	}
}
