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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences.data;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public abstract class AbstractPreferenceData implements IPreferenceStoreInitializer {

	@Inject
	IPreferenceStoreAccess access;

	private boolean initialized;

	protected abstract void doInitialize(IPreferenceStore store);

	protected boolean getBoolean(String property) {
		return access.getPreferenceStore().getBoolean(property);
	}

	protected boolean getContextualBoolean(IResource r, String property) {
		return getContextualStore(r).getBoolean(property);

	}

	protected <T extends Enum<T>> T getContextualEnum(IResource r, Class<T> anEnum, String property, T defaultValue) {
		Preconditions.checkNotNull(defaultValue);
		try {
			return Enum.valueOf(anEnum, getContextualString(r, property));
		}
		catch(IllegalArgumentException e) {
			return defaultValue;
		}

	}

	protected int getContextualInt(IResource r, String property) {
		Preconditions.checkNotNull(access, getInitializationErrorMessage());
		IPreferenceStore store = access.getContextPreferenceStore(r.getProject());
		return store.getInt(property);
	}

	protected IPreferenceStore getContextualStore(IResource r) {
		Preconditions.checkNotNull(access, getInitializationErrorMessage());
		return access.getContextPreferenceStore(r.getProject());
	}

	protected String getContextualString(IResource r, String property) {
		return getContextualStore(r).getString(property);

	}

	protected <T extends Enum<T>> T getEnum(Class<T> anEnum, String property, T defaultValue) {
		Preconditions.checkNotNull(defaultValue);
		try {
			return Enum.valueOf(anEnum, getString(property));
		}
		catch(IllegalArgumentException e) {
			return defaultValue;
		}
	}

	private String getInitializationErrorMessage() {
		return getClass().getName() + " has not been initialized.";
	}

	protected int getInt(IResource resource, String property) {
		return resource == null || isProjectSpecific(resource)
				? getInt(property)
				: getContextualInt(resource, property);
	}

	protected int getInt(String property) {
		return access.getPreferenceStore().getInt(property);
	}

	protected IPreferenceStoreAccess getPreferenceStoreAccess() {
		if(access == null)
			throw new IllegalStateException(getClass().getName() + " has not been initialized.");
		return access;
	}

	protected String getString(String property) {
		return access.getPreferenceStore().getString(property);
	}

	/**
	 * Return the ID of the Boolean preference to use to indicate that the preferences managed by the concrete class
	 * are project specific. This boolean is set to true when they are, and is false, or not set at all when the
	 * general defaults should be used.
	 * 
	 * @return the name of a the Boolean preference indicating that the preferences are project specific
	 */
	protected abstract String getUseProjectSettingsID();

	@Override
	public void initialize(IPreferenceStoreAccess access) {
		if(initialized)
			return;

		this.access = access;
		doInitialize(access.getWritablePreferenceStore());
		initialized = true;
	}

	/**
	 * @param r
	 *            the resource
	 * @return true if the given resource has project specific settings enabled
	 */
	public boolean isProjectSpecific(IResource r) {
		return getContextualBoolean(r, getUseProjectSettingsID());
	}
}
