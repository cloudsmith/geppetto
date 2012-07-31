/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
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
import java.util.Map;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Maps;

/**
 * The ResourceDocumentationAdapter associates EObject/List<INode> pp documentation with a Resource.
 */
public class ResourceDocumentationAdapter extends AdapterImpl {

	Map<EObject, List<INode>> semanticToCommentNodes = Maps.newHashMap();

	public void clear() {
		semanticToCommentNodes.clear();
	}

	public List<INode> get(EObject key) {
		return semanticToCommentNodes.get(key);
	}

	public Map<EObject, List<INode>> getAssociations() {
		return Collections.unmodifiableMap(semanticToCommentNodes);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == ResourceDocumentationAdapter.class;
	}

	public void put(EObject key, List<INode> value) {
		semanticToCommentNodes.put(key, value);
	}
}
