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

package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorInput;

import com.google.inject.ImplementedBy;

/**
 * 
 *
 */
@ImplementedBy(DefaultExtXtextEditorCustomizer.class)
public interface IExtXtextEditorCustomizer {
	/**
	 * Provides opportunity to provide the editor label/tab-title. If null is returned, the
	 * default title is used.
	 * 
	 * @param input
	 * @return an alternate editor tab label, or null to keep the default
	 */
	public String customEditorTitle(IEditorInput input);

	public void customizeEditorContextMenu(IMenuManager menuManager);
}
