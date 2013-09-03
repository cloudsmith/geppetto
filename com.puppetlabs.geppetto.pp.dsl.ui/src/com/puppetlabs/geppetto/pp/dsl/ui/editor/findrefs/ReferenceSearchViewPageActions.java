/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import com.puppetlabs.geppetto.pp.dsl.ui.internal.PPPluginImages;
import org.eclipse.jface.action.Action;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public interface ReferenceSearchViewPageActions {

	public static class CollapseAll extends Action {
		private PPReferenceSearchViewPage page;

		public CollapseAll(PPReferenceSearchViewPage page) {
			super("Collapse All");
			setImageDescriptor(PPPluginImages.DESC_COLLAPSE_ALL);
			setToolTipText("Collapse All");
			this.page = page;
		}

		@Override
		public void run() {
			page.getViewer().collapseAll();
		}
	}

	public static class ExpandAll extends Action {
		private PPReferenceSearchViewPage page;

		public ExpandAll(PPReferenceSearchViewPage page) {
			super("Expand All");
			setImageDescriptor(PPPluginImages.DESC_EXPAND_ALL);
			setToolTipText("Expand All");
			this.page = page;
		}

		@Override
		public void run() {
			page.getViewer().expandAll();
		}
	}

	public static class ShowNext extends Action {

		private PPReferenceSearchViewPage page;

		public ShowNext(PPReferenceSearchViewPage page) {
			super("Show Next Match");
			setImageDescriptor(PPPluginImages.DESC_SEARCH_NEXT);
			setToolTipText("Show Next Match");
			this.page = page;
		}

		@Override
		public void run() {
			new TreeViewerNavigator(page).navigateNext(true);
		}
	}

	public static class ShowPrevious extends Action {

		private PPReferenceSearchViewPage page;

		public ShowPrevious(PPReferenceSearchViewPage page) {
			super("Show Previous Match");
			setImageDescriptor(PPPluginImages.DESC_SEARCH_PREVIOUS);
			setToolTipText("Show Previous Match");
			this.page = page;
		}

		@Override
		public void run() {
			new TreeViewerNavigator(page).navigateNext(false);
		}
	}
}
