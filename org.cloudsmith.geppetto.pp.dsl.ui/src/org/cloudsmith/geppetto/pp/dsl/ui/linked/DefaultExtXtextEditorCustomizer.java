/**
 * Copyright (c) 2010, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
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
