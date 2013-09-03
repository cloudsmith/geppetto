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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;

import com.google.inject.Singleton;

/**
 * This specialization of the PreferenceStoreAccessImpl makes use of a ProjectAwareScopedPreferenceStore
 * to deal with writing project scope preferences and to deliver events from instance and project scopes
 * at all times since project scope does not have to have any values set.
 * 
 */
@Singleton
public class PPPreferenceStoreAccess extends PreferenceStoreAccessImpl {
	private IScopeContext[] instanceAndConfigurationScopes = new IScopeContext[] {
			InstanceScope.INSTANCE, ConfigurationScope.INSTANCE };

	@Override
	public IPreferenceStore getContextPreferenceStore(Object context) {
		lazyInitialize();
		return new ChainedPreferenceStore(new IPreferenceStore[] { //
			getReadableAndWritablePreferenceStore(context), //
					PPDSLActivator.getDefault().getPreferenceStore(), //
					EditorsUI.getPreferenceStore() });
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		lazyInitialize();
		return new ChainedPreferenceStore(new IPreferenceStore[] { //
			getWritablePreferenceStore(), //
					PPDSLActivator.getDefault().getPreferenceStore(), //
					EditorsUI.getPreferenceStore() });
	}

	/**
	 * Returns a store that has project, instance, ui stores. This is not suitable for Writing
	 * as it writes in the project scope !
	 * 
	 * @param context
	 * @return
	 */
	protected IPreferenceStore getReadableAndWritablePreferenceStore(Object context) {
		lazyInitialize();
		if(context instanceof IFileEditorInput) {
			context = ((IFileEditorInput) context).getFile().getProject();
		}
		if(context instanceof IProject) {
			ProjectScope projectScope = new ProjectScope((IProject) context);
			ProjectAwareScopedPreferenceStore result = new ProjectAwareScopedPreferenceStore(
				projectScope, getQualifier());
			result.setSearchContexts(new IScopeContext[] {
					projectScope, InstanceScope.INSTANCE, ConfigurationScope.INSTANCE });
			return result;
		}
		return getWritablePreferenceStore();
	}

	@Override
	public IPreferenceStore getWritablePreferenceStore() {
		lazyInitialize();
		ProjectAwareScopedPreferenceStore result = new ProjectAwareScopedPreferenceStore(
			InstanceScope.INSTANCE, getQualifier());
		result.setSearchContexts(instanceAndConfigurationScopes);
		return result;
	}

	@Override
	public IPreferenceStore getWritablePreferenceStore(Object context) {
		lazyInitialize();
		if(context instanceof IFileEditorInput) {
			context = ((IFileEditorInput) context).getFile().getProject();
		}
		if(context instanceof IProject) {
			ProjectScope projectScope = new ProjectScope((IProject) context);
			ProjectAwareScopedPreferenceStore result = new ProjectAwareScopedPreferenceStore(
				projectScope, getQualifier());
			result.setSearchContexts(new IScopeContext[] {
					projectScope, InstanceScope.INSTANCE, ConfigurationScope.INSTANCE });
			return result;
		}
		return getWritablePreferenceStore();
	}

}
