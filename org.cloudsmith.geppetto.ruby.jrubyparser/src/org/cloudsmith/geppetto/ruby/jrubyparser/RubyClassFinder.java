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
package org.cloudsmith.geppetto.ruby.jrubyparser;

import java.util.List;

import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Lists;

/**
 * Finds a class
 * 
 */
public class RubyClassFinder {

	private List<String> wantedName = null;

	/**
	 * Returned when a visited node detect it is not meaningful to visit its
	 * children.
	 */
	public static final Object DO_NOT_VISIT_CHILDREN = new Object();

	public ClassNode findClass(Node root, String... qualifiedName) {
		wantedName = Lists.newArrayList(qualifiedName);

		for (Node n : root.childNodes()) {
			if (n.getNodeType() == NodeType.NEWLINENODE)
				n = ((NewlineNode) n).getNextNode();
			switch (n.getNodeType()) {
			case ROOTNODE: // fall through
			case BLOCKNODE:
				return findClass(n, qualifiedName);
			case CLASSNODE:
				ClassNode classNode = (ClassNode) n;
				if (wantedName.equals(new ConstEvaluator().eval(classNode
						.getCPath())))
					return (ClassNode) n;
			}
		}
		return null;
	}
}
