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
package com.puppetlabs.geppetto.pp.adapters;

import com.puppetlabs.geppetto.pp.pptp.Type;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * The resolution info adapter associates an instance of ResolutionInfo with a (weak) key.
 * The intended use is that a resolver associates information about its resolution progress.
 * TODO: generalize naming - this class is really a TypeReference
 */
public class ClassifierAdapter extends AdapterImpl {

	public static final int RESOURCE_IS_BAD = -1;

	public static final int UNKNOWN = 0;

	public static final int RESOURCE_IS_REGULAR = 1;

	public static final int RESOURCE_IS_DEFAULT = 2;

	public static final int RESOURCE_IS_OVERRIDE = 3;

	public static final int RESOURCE_IS_CLASSPARAMS = 4;

	public static final int COLLECTOR_IS_REGULAR = 5;

	// Default size seems to be 10 slots - which is overkill
	private int classifier = UNKNOWN;

	private Type resourceType = null;

	private String resourceTypeName;

	/**
	 * Type of this parameter is determined by usage (typically an xtext.IEObjectDescription) but it
	 * is determined by a linking service.
	 */
	private Object targetObjectDescription;

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

	public Type getResourceType() {
		return resourceType;
	}

	/**
	 * @return the resourceTypeName
	 */
	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public Object getTargetObjectDescription() {
		return targetObjectDescription;
	}

	public <T> T getTargetObjectDescription(Class<T> clazz) {
		return clazz.cast(targetObjectDescription);
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

	@Override
	public boolean isAdapterForType(Object type) {
		return type == ClassifierAdapter.class;
	}

	/**
	 * Stores a Type in the adapter for given key.
	 * 
	 * @param key
	 * @param t
	 */
	public void setClassifier(int classifier) {
		this.classifier = classifier;
	}

	public void setResourceType(Type resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @param resourceTypeName
	 *            the resourceTypeName to set
	 */
	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	/**
	 * @param targetObjectDescription
	 *            - typically an Xtext IEObjectDescription referencing a link target
	 */
	public void setTargetObject(Object targetObjectDescription) {
		this.targetObjectDescription = targetObjectDescription;
	}

}
