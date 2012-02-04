/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Jan Koehnlein Itemis AG - initial API and implementation
 *   Cloudsmith - specialization for Puppet
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.search;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class OpenPuppetElementHandler extends AbstractHandler {

	@Inject
	private IURIEditorOpener uriEditorOpener;

	@Inject
	private IXtextEObjectSearch searchEngine;

	// TODO: Passing a globalDescriptionsLabelProvider is not very good - want specialized formatting anyway.
	// So, this label provider can be removed
	@Inject
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	// TODO: This does not have to be configurable
	@Inject(optional = true)
	@Named("xtext.enable.styledLables")
	private boolean enableStyledLabels = true;

	private static final Logger LOG = Logger.getLogger(OpenPuppetElementHandler.class);

	protected ListDialog createSearchDialog(ExecutionEvent event, Shell activeShell, IXtextEObjectSearch searchEngine) {
		return new PPObjectSearchDialog(
			activeShell, searchEngine, globalDescriptionLabelProvider, isEnableStyledLabels());
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell activeShell = HandlerUtil.getActiveShell(event);
		ListDialog searchDialog = createSearchDialog(event, activeShell, searchEngine);
		int result = searchDialog.open();
		if(result == Window.OK) {
			try {
				Object[] selections = searchDialog.getResult();
				if(selections != null && selections.length > 0) {
					Object selection = selections[0];
					if(selection instanceof IEObjectDescription) {
						IEObjectDescription selectedObjectDescription = (IEObjectDescription) selection;
						uriEditorOpener.open(selectedObjectDescription.getEObjectURI(), true);
					}
				}
			}
			catch(Exception e) {
				LOG.error("Error opening editor", e);
				throw new ExecutionException("Error opening editor", e);
			}
		}
		return null;
	}

	public boolean isEnableStyledLabels() {
		return enableStyledLabels;
	}

	public void setEnableStyledLabels(boolean enableStyledLabels) {
		this.enableStyledLabels = enableStyledLabels;
	}

}
