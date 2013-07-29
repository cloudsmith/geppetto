/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG (http://www.itemis.eu) - initial API and implementation
 *   Puppet Labs
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs;

import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;

/**
 * A ReferenceSearchResultLabelProvider that provides styled text and uses an explicit PPDescriptionLabelProvider rather
 * than the global label provider (requires registration).
 * 
 * TODO: Consider registering the label provider.
 * TODO: Contribute the styled string extension back to Xtext
 * 
 */
public class ReferenceSearchResultLabelProvider extends StyledCellLabelProvider {

	@Inject
	private PPDescriptionLabelProvider descriptionProvider;

	public Image getImage(Object element) {
		if(element instanceof ReferenceSearchViewTreeNode) {
			return descriptionProvider.getImage(((ReferenceSearchViewTreeNode) element).getLabelDescription());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	public StyledString getStyledText(Object element) {
		if(element instanceof ReferenceSearchViewTreeNode) {
			return descriptionProvider.getStyledText(((ReferenceSearchViewTreeNode) element).getLabelDescription());
		}
		return descriptionProvider.getStyledText("<invalid use of label provider>");
	}

	public String getText(Object element) {
		if(element instanceof ReferenceSearchViewTreeNode) {
			return descriptionProvider.getText(((ReferenceSearchViewTreeNode) element).getLabelDescription());
		}
		return "<invalid use of label provider>";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.StyledCellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
	 */
	@Override
	public void update(ViewerCell cell) {
		Object o = cell.getElement();
		StyledString st = getStyledText(o);
		if(st == null)
			st = new StyledString("<cell with null element>");
		cell.setText(st.toString());
		cell.setStyleRanges(st.getStyleRanges());
		cell.setImage(getImage(o));
	}
}
