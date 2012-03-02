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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;

/**
 * Utilities for a IDomModel
 * 
 */
public class DomModelUtils {

	private static void appendNodeStatus(Appendable result, Set<NodeStatus> statusBits) throws IOException {
		result.append(" ");
		result.append(statusBits.toString());
	}

	/**
	 * Creates a string representation of the given node. Useful for debugging.
	 * 
	 * @return a debug string for the given node.
	 */
	public static String compactDump(IDomNode node, boolean showHidden) {
		StringBuilder result = new StringBuilder();
		try {
			compactDump(node, showHidden, "", result);
		}
		catch(IOException e) {
			return e.getMessage();
		}
		return result.toString();
	}

	private static void compactDump(IDomNode node, boolean showHidden, String prefix, Appendable result)
			throws IOException {
		if(!showHidden && isHidden(node))
			return;
		if(prefix.length() != 0) {
			result.append("\n");
			result.append(prefix);
		}
		// Semantic
		result.append("s: ");
		result.append(semanticTitle(node));

		// Has INode or not
		if(node != null) {
			result.append(" n: ");
			result.append(Boolean.toString(node.getNode() != null));
		}

		// Grammar
		result.append(" g: ");
		if(node == null) {
			result.append("(null)");
		}
		else if(!node.isLeaf()) {
			if(node.getGrammarElement() != null)
				result.append(new GrammarElementTitleSwitch().showAssignments().doSwitch(node.getGrammarElement()));
			else
				result.append("(unknown)");
			appendNodeStatus(result, node.getNodeStatus());

			String newPrefix = prefix + "  ";
			result.append(" {");
			Iterator<? extends IDomNode> children = node.getChildren().iterator();
			while(children.hasNext()) {
				IDomNode child = children.next();
				compactDump(child, showHidden, newPrefix, result);
			}
			result.append("\n");
			result.append(prefix);
			result.append("}");

			if(containsError(node) && node.getNode() != null) {
				SyntaxErrorMessage error = node.getNode().getSyntaxErrorMessage();
				if(error != null)
					result.append(" SyntaxError: [" + error.getIssueCode() + "] " + error.getMessage());
			}
		}
		else if(node.isLeaf()) {
			// it is a leaf
			if(isHidden(node))
				result.append("hidden ");
			if(node.getGrammarElement() != null)
				result.append(new GrammarElementTitleSwitch().showAssignments().doSwitch(node.getGrammarElement()));
			else
				result.append("(unknown)");
			result.append(" => '");
			result.append(node.getText());
			result.append("'");
			appendNodeStatus(result, node.getNodeStatus());
			if(containsError(node) && node.getNode() != null) {
				SyntaxErrorMessage error = node.getNode().getSyntaxErrorMessage();
				if(error != null)
					result.append(" SyntaxError: [" + error.getIssueCode() + "] " + error.getMessage());
			}
		}
		else {
			result.append("unknown type ");
			result.append(node.getClass().getName());
		}
	}

	public static boolean containsComment(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.CONTAINS_COMMENT);
	}

	public static boolean containsError(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.CONTAINS_ERROR);
	}

	public static boolean containsHidden(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.CONTAINS_HIDDEN);
	}

	public static boolean containsWhitespace(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.CONTAINS_WHITESPACE);
	}

	public static IDomNode firstLeaf(IDomNode node) {
		if(node == null || node.isLeaf())
			return node;
		int sz = node.getChildren().size();
		if(sz == 0)
			return nextLeaf(node);
		return firstLeaf(node.getChildren().get(0));
	}

	/**
	 * This method converts a node to text.
	 * 
	 * Leading and trailing text from hidden tokens (whitespace/comments) is removed. Text from hidden tokens that is
	 * surrounded by text from non-hidden tokens is summarized to a single whitespace.
	 * 
	 * The preferred use case of this method is to convert the {@link ICompositeNode} that has been created for a data
	 * type rule to text.
	 * 
	 * This is also the recommended way to convert a node to text if you want to invoke
	 * {@link org.eclipse.xtext.conversion.IValueConverterService#toValue(String, String, INode)}
	 * 
	 */
	public static String getTokenText(IDomNode node) {
		if(node.isLeaf())
			return node.getText();

		StringBuilder builder = new StringBuilder(Math.max(node.getLength(), 1));
		boolean hiddenSeen = false;
		for(IDomNode leaf : node.getChildren()) {
			if(!isHidden(leaf)) {
				if(hiddenSeen && builder.length() > 0)
					builder.append(' ');
				builder.append(leaf.getText());
				hiddenSeen = false;
			}
			else {
				hiddenSeen = true;
			}
		}
		return builder.toString();
	}

	/**
	 * Returns true if rue if node holds only comment tokens - hidden or not.
	 * 
	 * @return true if node holds only comment tokens
	 */

	public static boolean isComment(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.COMMENT);
	}

	/**
	 * A node that represents text that is hidden from the grammar.
	 * 
	 * @return true if node holds only hidden tokens
	 */
	public static boolean isHidden(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.HIDDEN);
	}

	/**
	 * Returns true if node holds only whitespace tokens - hidden or not.
	 * 
	 * @return true if node holds only whitespace tokens
	 */
	public static boolean isWhitespace(IDomNode node) {
		return node.getNodeStatus().contains(IDomNode.NodeStatus.WHITESPACE);
	}

	public static IDomNode lastLeaf(IDomNode node) {
		if(node == null || node.isLeaf())
			return node;
		int sz = node.getChildren().size();
		if(sz == 0)
			return preceedingLeaf(node);
		return lastLeaf(node.getChildren().get(sz - 1));
	}

	public static IDomNode nextLeaf(IDomNode node) {
		IDomNode n = node.getNextSibling();
		if(n != null)
			return firstLeaf(n);
		Iterator<IDomNode> parents = node.parents();
		while(parents.hasNext()) {
			IDomNode parent = parents.next();
			IDomNode p = parent.getNextSibling();
			if(p != null)
				return firstLeaf(p);
		}
		// ran out of parents
		return null;
	}

	public static IDomNode preceedingLeaf(IDomNode node) {
		IDomNode n = node.getPreviousSibling();
		if(n != null)
			return lastLeaf(n);
		Iterator<IDomNode> parents = node.parents();
		while(parents.hasNext()) {
			IDomNode parent = parents.next();
			IDomNode p = parent.getPreviousSibling();
			if(p != null)
				return lastLeaf(p);
		}
		// ran out of parents
		return null;
	}

	private static String semanticTitle(IDomNode node) {

		EObject o = node.getSemanticElement();
		if(o == null)
			return "(null)";
		return o.eClass().getName();
	}
}
