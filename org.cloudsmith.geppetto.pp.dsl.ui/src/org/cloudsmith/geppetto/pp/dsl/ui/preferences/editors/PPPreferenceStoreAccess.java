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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.ui.editor.preferences.FixedScopedPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;

/**
 * This specialization of the PreferenceStoreAccessImpl makes use of a ProjectAwareScopedPreferenceStore
 * to deal with writing project scope preferences.
 * 
 */
public class PPPreferenceStoreAccess extends PreferenceStoreAccessImpl {

	/**
	 * Used as an ugly crutch as the super version inckudes a store obtained via
	 * the Activator of a plugin that does not expose it. The super version calls back
	 * to get the WriteablePreference store for a context - BUT THIS IS WRONG! IT NEEDS TO GET
	 * ONE THAT IS SUITABLE FOR READING.
	 */
	private boolean superRead = false;

	@Override
	public IPreferenceStore getContextPreferenceStore(Object context) {
		IPreferenceStore superStore = null;
		try {
			superRead = true;
			superStore = super.getContextPreferenceStore(context);
		}
		finally {
			superRead = false;
		}
		return superStore;
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		IPreferenceStore superStore = null;
		try {
			superRead = true;
			superStore = super.getPreferenceStore();
		}
		finally {
			superRead = false;
		}
		return superStore;
	}

	/**
	 * Returns a store that has project, instance, ui stores. This is not suitable for Writing
	 * as it writes in the project scope !
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected IPreferenceStore getReadableAndWritablePreferenceStore(Object context) {
		lazyInitialize();
		if(context instanceof IFileEditorInput) {
			context = ((IFileEditorInput) context).getFile().getProject();
		}
		if(context instanceof IProject) {
			ProjectScope projectScope = new ProjectScope((IProject) context);
			FixedScopedPreferenceStore result = new FixedScopedPreferenceStore(projectScope, getQualifier());
			result.setSearchContexts(new IScopeContext[] { projectScope, new InstanceScope(), new ConfigurationScope() });
			return result;
		}
		return getWritablePreferenceStore();
	}

	@Override
	@SuppressWarnings("deprecation")
	public IPreferenceStore getWritablePreferenceStore() {
		lazyInitialize();
		ProjectAwareScopedPreferenceStore result = new ProjectAwareScopedPreferenceStore(
			new InstanceScope(), getQualifier());
		result.setSearchContexts(new IScopeContext[] { new InstanceScope(), new ConfigurationScope() });
		return result;
	}

	@Override
	@SuppressWarnings("deprecation")
	public IPreferenceStore getWritablePreferenceStore(Object context) {
		if(superRead)
			return getReadableAndWritablePreferenceStore(context);

		lazyInitialize();
		if(context instanceof IFileEditorInput) {
			context = ((IFileEditorInput) context).getFile().getProject();
		}
		if(context instanceof IProject) {
			ProjectScope projectScope = new ProjectScope((IProject) context);
			ProjectAwareScopedPreferenceStore result = new ProjectAwareScopedPreferenceStore(
				projectScope, getQualifier());
			result.setSearchContexts(new IScopeContext[] { projectScope, new InstanceScope(), new ConfigurationScope() });
			return result;
		}
		return getWritablePreferenceStore();
	}

}
