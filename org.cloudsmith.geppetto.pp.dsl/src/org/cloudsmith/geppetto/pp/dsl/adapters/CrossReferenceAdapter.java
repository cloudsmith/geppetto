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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * The ResourcePropertiesAdapter associates an {@link IEObjectDescription} to an EObject.
 * A list is maintained to help when a resolution is ambiguous.
 * 
 */
public class CrossReferenceAdapter extends AdapterImpl {

	public static void clear(EObject o) {
		if(o != null) // can't be more cleared than that...
			CrossReferenceAdapterFactory.eINSTANCE.adapt(o).clear();
	}

	public static List<IEObjectDescription> get(EObject o) {
		return CrossReferenceAdapterFactory.eINSTANCE.get(o);
	}

	/**
	 * Convenience method to adapt and set the value in the adapter.
	 * 
	 * @param o
	 * @param description
	 */
	public static void set(EObject o, IEObjectDescription description) {
		CrossReferenceAdapterFactory.eINSTANCE.adapt(o).set(description);
	}

	public static void set(EObject o, Iterable<IEObjectDescription> descriptions) {
		CrossReferenceAdapterFactory.eINSTANCE.adapt(o).set(descriptions);
	}

	private List<IEObjectDescription> referenced;

	private static List<IEObjectDescription> emptyReferenced = Collections.emptyList();

	public CrossReferenceAdapter() {
		referenced = emptyReferenced;
	}

	public void clear() {
		referenced = emptyReferenced;
	}

	public List<IEObjectDescription> get() {
		return referenced;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == CrossReferenceAdapter.class;
	}

	public void set(IEObjectDescription o) {
		referenced = Collections.unmodifiableList(Lists.newArrayList(o));
	}

	public void set(Iterable<IEObjectDescription> descriptions) {
		List<IEObjectDescription> result = Lists.newArrayList();
		Iterables.addAll(result, descriptions);
		referenced = Collections.unmodifiableList(result);
	}
}
