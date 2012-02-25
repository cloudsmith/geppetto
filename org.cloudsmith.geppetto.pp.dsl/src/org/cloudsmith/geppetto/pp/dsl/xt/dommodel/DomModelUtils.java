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

import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.nodemodel.BidiIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;

/**
 * Utilities for a IDomModel
 * 
 */
public class DomModelUtils {

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
		if(node == null) {
			result.append("(null)");
		}
		else if(!node.isLeaf()) {
			if(node.getGrammarElement() != null)
				result.append(new GrammarElementTitleSwitch().doSwitch(node.getGrammarElement()));
			else
				result.append("(unknown)");
			String newPrefix = prefix + "  ";
			result.append(" {");
			BidiIterator<IDomNode> children = node.getChildren().iterator();
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
				result.append(new GrammarElementTitleSwitch().doSwitch(node.getGrammarElement()));
			else
				result.append("(unknown)");
			result.append(" => '");
			result.append(node.getText());
			result.append("'");
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
}
