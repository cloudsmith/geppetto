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
package org.cloudsmith.geppetto.pp.dsl.ui.editor;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.FormatterGeneralPreferences;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.xtext.ui.editor.XtextSourceViewerConfiguration;

/**
 * Overrides the XtextSourceViewerConfiguratio to provide:
 * <ul>
 * <li>Puppet specific tab-size. (Note that the editor configuration always has convert tab to spaces turned on).</li>
 * </ul>
 * 
 */
public class PPSourceViewerConfiguration extends XtextSourceViewerConfiguration {

	/*
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getTabWidth(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public int getTabWidth(ISourceViewer sourceViewer) {
		if(fPreferenceStore != null)
			return fPreferenceStore.getInt(FormatterGeneralPreferences.FORMATTER_INDENTSIZE);
		return 2;
	}

}
