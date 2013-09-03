/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * This is a verbatim copy of the class with the same name in the corresponding Xtext package.
 * But it is bound to implementations that makes it difficult to reuse. This variant simply imports classes
 * from the Geppetto specific package.
 * 
 * TODO: This copy may not be needed
 * 
 * @author Jan Koehnlein - Initial contribution and API
 */
public class ReferenceSearchViewTreeNode {

	private ReferenceSearchViewTreeNode parent;

	private List<ReferenceSearchViewTreeNode> children;

	private Object labelDescription;

	private Object description;

	public ReferenceSearchViewTreeNode(ReferenceSearchViewTreeNode parent, Object description, Object labelDescription) {
		super();
		this.parent = parent;
		this.description = description;
		this.labelDescription = labelDescription;
		if(parent != null) {
			parent.addChild(this);
		}
	}

	public void addChild(ReferenceSearchViewTreeNode child) {
		if(children == null) {
			children = Lists.newArrayList();
		}
		children.add(child);
	}

	public List<ReferenceSearchViewTreeNode> getChildren() {
		if(children == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(children);
	}

	public Object getDescription() {
		return description;
	}

	public Object getLabelDescription() {
		return labelDescription;
	}

	public ReferenceSearchViewTreeNode getParent() {
		return parent;
	}

	public void removeChild(ReferenceSearchViewTreeNode child) {
		if(children != null) {
			children.remove(child);
		}
	}

}
