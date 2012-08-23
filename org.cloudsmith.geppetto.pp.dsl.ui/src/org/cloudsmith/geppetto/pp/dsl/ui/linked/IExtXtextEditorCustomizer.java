/**
 * Copyright (c) 2006-2009, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
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
