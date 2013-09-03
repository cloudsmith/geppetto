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
package com.puppetlabs.geppetto.ui.dialog;

import com.puppetlabs.geppetto.forge.v1.model.ModuleInfo;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.util.StringUtil;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.internal.misc.StringMatcher;

public class ModuleListSelectionDialog extends ElementListSelectionDialog {

	protected static class ModuleLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			return StringUtil.getModuleText((ModuleInfo) element);
		}
	}

	public ModuleListSelectionDialog(Shell parent) {
		super(parent, new ModuleLabelProvider());

		setMessage(UIPlugin.getLocalString("_UI_SelectModule")); //$NON-NLS-1$          
		setFilter("*"); //$NON-NLS-1$
		setTitle(UIPlugin.getLocalString("_UI_ModuleSelection_title")); //$NON-NLS-1$
	}

	@Override
	protected FilteredList createFilteredList(Composite parent) {
		final FilteredList filteredList = super.createFilteredList(parent);

		filteredList.setFilterMatcher(new FilteredList.FilterMatcher() {

			private StringMatcher fMatcher;

			@Override
			public boolean match(Object element) {
				return fMatcher.match(filteredList.getLabelProvider().getText(element));
			}

			@Override
			public void setFilter(String pattern, boolean ignoreCase, boolean ignoreWildCards) {
				fMatcher = new StringMatcher('*' + pattern + '*', ignoreCase, ignoreWildCards);
			}
		});

		return filteredList;
	}

}
