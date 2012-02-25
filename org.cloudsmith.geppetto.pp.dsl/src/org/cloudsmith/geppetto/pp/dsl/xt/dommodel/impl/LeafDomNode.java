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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

/**
 * A Leaf DomNode
 * 
 */
public class LeafDomNode extends BaseDomNode {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.BaseDomNode#getNodeAt(int)
	 */
	@Override
	IDomNode getNodeAt(int index) {
		throw new UnsupportedOperationException("A LeafNode does not support getNodeAt(i)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return true;
	}
}
