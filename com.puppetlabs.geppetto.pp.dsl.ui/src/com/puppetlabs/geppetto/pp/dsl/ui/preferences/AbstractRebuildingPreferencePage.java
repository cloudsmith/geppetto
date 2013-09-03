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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import org.cloudsmith.geppetto.pp.dsl.ui.builder.PPBuildJob;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * Adds behavior to the abstract preference page that checks if a single preference id has
 * changed, and if so, triggers a clean build of the project.
 * 
 */
public abstract class AbstractRebuildingPreferencePage extends AbstractPreferencePage {

	private IPropertyChangeListener rebuildListener;

	private IPreferenceStore usedPreferenceStore;

	@Override
	public void dispose() {
		if(isPropertyPage()) {
			if(rebuildListener != null)
				usedPreferenceStore.removePropertyChangeListener(rebuildListener);
		}
		super.dispose();
	}

	protected abstract String getPreferenceId();

	@Override
	protected void updateFieldEditors(boolean enabled) {
		super.updateFieldEditors(enabled);
		if(isPropertyPage()) {
			if(rebuildListener == null) {

				rebuildListener = new IPropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if(getPreferenceId().equals(event.getProperty())) {
							// System.err.println("Puppet proj env changed");
							IProject proj = ((IProject) getElement());
							PPBuildJob job = new PPBuildJob(proj);
							job.schedule();
						}
					}

				};
			}
			// protect against getting different store instances (happens at least when debugging).
			IPreferenceStore store = getPreferenceStore();
			if(store != usedPreferenceStore) {
				if(usedPreferenceStore != null)
					usedPreferenceStore.removePropertyChangeListener(rebuildListener);
				usedPreferenceStore = store;
				store.addPropertyChangeListener(rebuildListener);
			}
		}
	}

}
