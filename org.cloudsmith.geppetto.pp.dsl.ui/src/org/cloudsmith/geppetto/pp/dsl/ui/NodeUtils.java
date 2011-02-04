/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Utils for navigating nodes.
 * 
 */
public class NodeUtils {
	/**
	 * Navigate to the parse node corresponding to the semantic object and
	 * fetch the leaf node that corresponds to the first feature with the given
	 * name, or the assignment of a composite node to first feature with given name.
	 * 
	 * @return null if there is no adapter, or feature does not exist.
	 */
	public static INode getFirstFeatureNode(EObject semantic, String feature) {
		throw new UnsupportedOperationException("Rewritten in Xtext 2.0 - work required");

		// ICompositeNode node = NodeModelUtils.getNode(semantic);
		// if(node != null) {
		// if(feature == null)
		// return null;
		// for(INode child : node.getChildren()) {
		// child.getGrammarElement()
		// if(child instanceof LeafNode) {
		// if(feature.equals(((LeafNode) child).getFeature())) {
		// return child;
		// }
		// }
		// else if(child instanceof CompositeNode) {
		// EObject ge = ((CompositeNode) child).getGrammarElement();
		// if(ge.eContainer() instanceof Assignment)
		// if(feature.equals(((Assignment) ge.eContainer()).getFeature()))
		// return child;
		// }
		// }
		// }
		// return null;
	}
}
