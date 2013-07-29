/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 *
 */

package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorInput;

/**
 *
 *
 */
public class DefaultExtXtextEditorCustomizer implements IExtXtextEditorCustomizer {
	@Override
	public String customEditorTitle(IEditorInput input) {
		return null;
	}

	@Override
	public void customizeEditorContextMenu(IMenuManager menuManager) {
		// do nothing
	}
}
