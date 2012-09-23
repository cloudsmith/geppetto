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
package org.cloudsmith.xtext.dommodel;

import java.io.IOException;
import java.util.Iterator;

import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSetWithTracking;
import org.cloudsmith.xtext.dommodel.formatter.css.debug.EffectiveStyleAppender;
import org.cloudsmith.xtext.dommodel.formatter.css.debug.FormattingTracer;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Inject;

/**
 * Utilities for a IDomModel
 * 
 */
public class DomModelUtils {

	// IMPORTANT: To use this, call binder.requestStaticInjection(DomModelUtils.class)
	@Inject
	private static FormattingTracer tracer;

	private static void appendEffectiveStyle(Appendable result, IDomNode node, String prefix) throws IOException {
		if(tracer == null) {
			result.append(" effective: [-off-]");
			return;
		}
		StyleSet effective = tracer.getEffectiveStyle(node);
		if(effective == null) {
			result.append(" effective: [null]");
			return;
		}
		if(prefix.length() != 0) {
			result.append("\n");
			result.append(prefix);
		}

		result.append(" effective: [");
		EffectiveStyleAppender styleAppender = new EffectiveStyleAppender(result);
		ArrayListMultimap<String, IStyle<?>> ruleToStyle = ArrayListMultimap.create();
		if(effective instanceof StyleSetWithTracking) {
			StyleSetWithTracking trackingSet = (StyleSetWithTracking) effective;
			for(IStyle<?> style : effective.getStyles()) {
				ruleToStyle.put(trackingSet.getStyleSource(style).getRuleName(), style);
			}
		}
		else {
			for(IStyle<?> style : effective.getStyles()) {
				ruleToStyle.put("(unknown source)", style);
			}
		}
		boolean firstSource = true;
		for(String source : ruleToStyle.keySet()) {
			if(!firstSource)
				result.append(", ");
			firstSource = false;
			result.append("From: ");
			result.append(source);
			result.append(" {");
			boolean firstStyle = true;
			for(IStyle<?> style : ruleToStyle.get(source)) {
				if(!firstStyle)
					result.append(", ");
				firstStyle = false;
				style.visit(node, styleAppender);
			}
			result.append("}");
		}
		result.append("]");

	}

	private static void appendTypeAndClassifiers(Appendable result, IDomNode node) throws IOException {
		result.append(", ");
		NodeType nodeType = node.getNodeType();
		if(nodeType == null)
			result.append("(unknown node type)");
		else
			result.append(nodeType.toString());

		if(node != null && node.getStyleClassifiers().size() > 0) {
			result.append(" [");
			Joiner.on(", ").appendTo(result, node.getStyleClassifiers());
			result.append("] ");
		}

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
		if(node == null) {
			result.append("null");
			return;
		}

		if(!showHidden && isHidden(node))
			return;

		if(prefix.length() != 0) {
			result.append("\n");
			result.append(prefix);
		}
		// Text
		result.append("'");
		if(node.getText() != null)
			result.append(encodedString(node.getText()));
		result.append("' ");

		// Semantic
		result.append("s: ");
		result.append(semanticTitle(node));

		// Has INode or not (most have)
		if(node.getNode() == null)
			result.append(" NoNode");

		// Style classifiers

		// Grammar
		result.append(" g: ");
		// if(node == null)
		// result.append("-");

		if(!node.isLeaf()) {
			if(node.getGrammarElement() != null)
				result.append(new GrammarElementTitleSwitch().showAssignments().doSwitch(node.getGrammarElement()));
			else
				result.append("(unknown)");
			appendTypeAndClassifiers(result, node);

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

			// appendTypeAndClassifiers(result, node);
			appendEffectiveStyle(result, node, "");

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
			// result.append(" => '");
			// result.append(encodedString(node.getText()));
			// result.append("'");

			appendTypeAndClassifiers(result, node);
			appendEffectiveStyle(result, node, prefix + "    ");
			if(containsError(node) && node.getNode() != null) {
				SyntaxErrorMessage error = node.getNode().getSyntaxErrorMessage();
				if(error != null)
					result.append(" SyntaxError: [" + error.getIssueCode() + "] " + error.getMessage());
			}
		}
		else {
			result.append("neither leaf nor composite!! ");
			result.append(node.getClass().getName());
		}
	}

	public static boolean containsComment(IDomNode node) {
		return node.getStyleClassifiers().contains(IDomNode.NodeClassifier.CONTAINS_COMMENT);
	}

	public static boolean containsError(IDomNode node) {
		return node.getStyleClassifiers().contains(IDomNode.NodeClassifier.CONTAINS_ERROR);
	}

	public static boolean containsHidden(IDomNode node) {
		return node.getStyleClassifiers().contains(IDomNode.NodeClassifier.CONTAINS_HIDDEN);
	}

	public static boolean containsWhitespace(IDomNode node) {
		return node.getStyleClassifiers().contains(IDomNode.NodeClassifier.CONTAINS_WHITESPACE);
	}

	public static String encodedString(String input) {
		input = input.replace("\n", "\\n");
		input = input.replace("\t", "\\t");
		input = input.replace("\r", "\\r");
		return input;

	}

	public static IDomNode findNodeForSemanticObject(IDomNode domModel, EObject o) {

		Iterator<IDomNode> itor = domModel.treeIterator();
		while(itor.hasNext()) {
			IDomNode n = itor.next();
			if(n.getSemanticObject() == o)
				return n;
		}
		return null;
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
	 * Returns the first node representing a textual non whitespace token. (i.e. a leaf node
	 * that has a textual representation).
	 * 
	 * @param node
	 * @return
	 */
	public static IDomNode firstTokenWithText(IDomNode node) {
		IDomNode n = firstLeaf(node);
		while(n != null && !isLeafWithText(n))
			n = nextLeaf(n);
		return n;
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
	 * Returns true if node holds only comment tokens - hidden or not.
	 * 
	 * @return true if node holds only comment tokens
	 */

	public static boolean isComment(IDomNode node) {
		return node.getNodeType().equals(IDomNode.NodeType.COMMENT);
	}

	/**
	 * A node that represents text that is hidden from the grammar.
	 * 
	 * @return true if node holds only hidden tokens
	 */
	public static boolean isHidden(IDomNode node) {
		return node.getStyleClassifiers().contains(IDomNode.NodeClassifier.HIDDEN);
	}

	/**
	 * Returns true for a node that is non whitspace and has a textual representation.
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isLeafWithText(IDomNode node) {
		if(!node.isLeaf())
			return false;
		if(node.getNodeType() == NodeType.WHITESPACE || node.getNodeType() == NodeType.ACTION)
			return false;
		if(node.getText().length() == 0)
			return false;
		return true;
	}

	/**
	 * Returns true for a node that has textual representation (even if text has zero length).
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isToken(IDomNode node) {
		if(!node.isLeaf())
			return false;
		if(node.getNodeType() == NodeType.ACTION)
			return false;
		return true;
	}

	/**
	 * Returns true if node holds only whitespace tokens - hidden or not.
	 * 
	 * @return true if node holds only whitespace tokens
	 */
	public static boolean isWhitespace(IDomNode node) {
		return node != null && node.getNodeType() == IDomNode.NodeType.WHITESPACE;
	}

	public static IDomNode lastLeaf(IDomNode node) {
		if(node == null || node.isLeaf())
			return node;
		int sz = node.getChildren().size();
		if(sz == 0)
			return previousLeaf(node);
		return lastLeaf(node.getChildren().get(sz - 1));
	}

	/**
	 * Returns the last node representing a textual non whitespace token. (i.e. a leaf node
	 * that has a textual representation).
	 * 
	 * @param node
	 * @return
	 */
	public static IDomNode lastTokenWithText(IDomNode node) {
		IDomNode n = lastLeaf(node);
		while(n != null && !isLeafWithText(n))
			n = previousLeaf(n);
		return n;
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

	public static IDomNode nextToken(IDomNode node) {
		IDomNode n = nextLeaf(node);
		while(n != null && !isLeafWithText(n))
			n = nextLeaf(n);
		return n;
	}

	public static IDomNode nextWhitespace(IDomNode node) {
		for(IDomNode n = nextLeaf(node); n != null; n = nextLeaf(n)) {
			if(isWhitespace(n))
				return n;
		}
		return null;
	}

	public static IDomNode nodeForGrammarElement(IDomNode node, EObject grammarElement) {
		Iterator<IDomNode> itor = node.treeIterator();
		if(itor.hasNext())
			for(IDomNode n = itor.next(); itor.hasNext(); n = itor.next()) {
				if(grammarElement == n.getGrammarElement())
					return n;
			}
		return null;
	}

	/**
	 * The position on the line for the IDomNode searches backwards in the total text from the start position
	 * of the text in the given node.
	 * 
	 * @param node
	 * @param lineDelimiter
	 * @return
	 */
	public static int posOnLine(IDomNode node, String lineDelimiter) {
		final INode n = node.getNode();
		if(n == null)
			return -1;
		int offsetOfNode = node.getNode().getTotalOffset();
		return offsetOfNode -
				Math.max(0, 1 + CharSequences.lastIndexOf(n.getRootNode().getText(), lineDelimiter, offsetOfNode - 1));
	}

	public static IDomNode previousLeaf(IDomNode node) {
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

	public static IDomNode previousToken(IDomNode node) {
		IDomNode n = previousLeaf(node);
		while(n != null && !isLeafWithText(n))
			n = previousLeaf(n);
		return n;
	}

	public static IDomNode previousWhitespace(IDomNode node) {
		for(IDomNode n = previousLeaf(node); n != null; n = previousLeaf(n)) {
			if(isWhitespace(n))
				return n;
		}
		return null;
	}

	private static String semanticTitle(IDomNode node) {

		EObject o = node.getSemanticObject();
		if(o == null)
			return "-";
		return o.eClass().getName();
	}
}
