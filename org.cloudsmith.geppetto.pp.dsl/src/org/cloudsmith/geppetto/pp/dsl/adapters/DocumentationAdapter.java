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
package org.cloudsmith.geppetto.pp.dsl.adapters;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;

/**
 * The Documentation Adapter associates a List of Comment INode instances with a semantic EObject.
 */
public class DocumentationAdapter extends AdapterImpl {

	private final static List<INode> EMPTY = Collections.emptyList();

	List<INode> documentationNodes;

	/**
	 * Gets the nodes stored or null if no nodes have been set as documentation.
	 * 
	 * @return
	 */
	public List<INode> getNodes() {
		return Collections.unmodifiableList(documentationNodes != null
				? documentationNodes
				: EMPTY);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == DocumentationAdapter.class;
	}

	/**
	 * Stores a (copy of a) node list.
	 * 
	 * @param key
	 * @param t
	 */
	public void setNodes(List<INode> nodes) {

		documentationNodes = Lists.newArrayList(nodes);
	}

}
