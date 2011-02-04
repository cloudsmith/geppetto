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
package org.cloudsmith.geppetto.pp.adapters;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * The resolution info adapter associates an instance of ResolutionInfo with a (weak) key.
 * The intended use is that a resolver associates information about its resolution progress.
 */
public class ClassifierAdapter extends AdapterImpl {

	public static final int RESOURCE_IS_BAD = -1;

	public static final int UNKNOWN = 0;

	public static final int RESOURCE_IS_REGULAR = 1;

	public static final int RESOURCE_IS_DEFAULT = 2;

	public static final int RESOURCE_IS_OVERRIDE = 3;

	// Default size seems to be 10 slots - which is overkill
	private int classifier = UNKNOWN;

	/**
	 * Gets a Type set in the adapter for the given key, or null if no type have been
	 * set.
	 * 
	 * @param key
	 * @return
	 */
	public int getClassifier() {
		return classifier;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == ClassifierAdapter.class;
	}

	// Probably not needed, and if so, must be different for different types of adapters...
	// @Override
	// public void notifyChanged(Notification msg) {
	// int featureId = msg.getFeatureID(???.class);
	// if(featureId == PPPackage.RESOURCE_BODY__NAME_EXPR)
	// classifier = UNKNOWN;
	//
	// super.notifyChanged(msg);
	// }

	/**
	 * Stores a Type in the adapter for given key.
	 * 
	 * @param key
	 * @param t
	 */
	public void setClassifier(int classifier) {
		this.classifier = classifier;
	}

}
