/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG (http://www.itemis.eu) - intial API
 *   Cloudsmith - intial API and implementation
 *   
 * 
 */
package org.cloudsmith.xtext.dommodel;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Sets;

/**
 * A node in a document model.
 * TODO: Should probably be capable of providing diagnostics/errors since formatting is often not wanted / required
 * to be verbatim.
 * 
 */
public interface IDomNode {
	/**
	 * Generic NodeClassifiers that are suitable as "style classifiers".
	 * It is expected that an creator of a DOM provides these as styleClassifiers for resulting nodes.
	 */
	public enum NodeClassifier {
		/**
		 * Consists of only hidden tokens.
		 */
		HIDDEN,

		/**
		 * Contains one or more whitespace tokens
		 */
		CONTAINS_WHITESPACE,

		/**
		 * Contains one or more comment tokens
		 */
		CONTAINS_COMMENT,

		/**
		 * Contains one or more hidden tokens.
		 */
		CONTAINS_HIDDEN,

		/**
		 * Content is only comment(s)
		 */
		ALL_COMMENT,

		/**
		 * Content is only whitespace(s)
		 */
		ALL_WHITESPACE,

		/**
		 * Content consists of only whitespace and comments
		 */
		ALL_COMMENT_WHITESPACE,

		/**
		 * Contains one or more nodes with associated error(s).
		 */
		CONTAINS_ERROR,

		/**
		 * Node represents an instantiation. The semantic EObject is a reference to the EClassifier (class)
		 * of which an instance is created.
		 */
		INSTANTIATION,

		/**
		 * Node represents a cross reference
		 */
		CROSSREF,

		/**
		 * Node represents token that is unassigned
		 */
		UNASSIGNED,

		/**
		 * Node represents token that is assigned.
		 */
		ASSIGNED,

		/**
		 * First non whitespace
		 */
		FIRST_TOKEN,

		/**
		 * Last non whitespace
		 */
		LAST_TOKEN,

		/**
		 * The content in the node is implied (typically used with automatically inserted whitespace).
		 */
		IMPLIED,

		/**
		 * Used in combination with @link {@link NodeType#COMMENT} to describe that the comment ends with
		 * a new line.
		 */
		LINESEPARATOR_TERMINATED,

		/**
		 * Used in combination with @link {@link NodeType#COMMENT} to describe that the comment token
		 * contains multiple lines.
		 */
		MULTIPLE_LINES,

		;

	}

	public enum NodeType {

		/**
		 * Consists of only comment tokens
		 */
		COMMENT,

		/**
		 * Consists of only whitespace tokens
		 */
		WHITESPACE,

		/**
		 * Node represents a keyword.
		 */
		KEYWORD,

		/**
		 * Node represents a terminal.
		 */
		TERMINAL,

		/**
		 * Node represents an enumerator.
		 */
		ENUM,

		/**
		 * Node represents a data type.
		 */
		DATATYPE,

		/**
		 * Node represents an action.
		 */
		ACTION,

		/**
		 * Node represents a rule call.
		 */
		RULECALL, ;

		/**
		 * Count of enums - useful for "any" check
		 */
		public static final int numberOfValues = 8;

		public static final Set<NodeType> whitespaceSet = Collections.unmodifiableSet(EnumSet.of(WHITESPACE));

		// All bits set except for whitepsace
		public static final Set<NodeType> nonWhitespaceSet = Collections.unmodifiableSet(Sets.complementOf(EnumSet.of(NodeType.WHITESPACE)));

		public static final Set<NodeType> anySet = Collections.unmodifiableSet(EnumSet.allOf(NodeType.class));

	}

	// IMPORTANT: Must be a unique instance of empty string - do not change to = ""
	public static final String IMPLIED_EMPTY_WHITESPACE = new String("");

	/**
	 * Returns the children of a composite node, or an empty list if the node is a leaf, or a composite with no
	 * children.
	 * 
	 * @return a list of this node's children (or an empty list)
	 */
	public List<IDomNode> getChildren();

	/**
	 * NOTE: If there a nodes in the DOM that represents layout structure e.g. table/columns it is not possible to
	 * provide a grammarElement for the Table or Row. Return null for those as well - or move to a sub interface.
	 * 
	 * Returns the grammar element that created this node. May return <code>null</code> in case of unrecoverable syntax
	 * errors. This happens usually when a keyword occurred at an unexpected offset.
	 * 
	 * @return the grammar element that created this node. May return <code>null</code>.
	 */
	public EObject getGrammarElement();

	/**
	 * NOTE: if there are nodes in the DOM that represents layout - this can be difficult to compute - what is
	 * the length of a table? The length of rows and columns may be affected by layout.
	 * NOTE: the INode reports length excluding hidden tokens - confused over the difference / getText, getTotalLength
	 * etc. Should the DOM behave the same way.
	 * 
	 * Returns the length of this node excluding hidden tokens. If this node is a hidden leaf node, the
	 * total length is returned.
	 * 
	 * @return the length of this node excluding hidden tokens.
	 */
	public int getLength();

	/**
	 * Returns the nearest semantic object that is associated with the (sub) tree rooted in this node.
	 * May return <code>null</code> for situation like when a parser refused to create any objects due to
	 * unrecoverable errors.
	 * 
	 * @return the nearest semantic object that is associated with the (sub) tree rooted in this node. May return <code>null</code>.
	 */
	public EObject getNearestSemanticObject();

	/**
	 * Returns the next sibling of this node, or null, if this is the last sibling in a composite node.
	 * 
	 * @return
	 */
	public IDomNode getNextSibling();

	/**
	 * Returns the INode that covers the same text as this IDomNode. May return <code>null</null> if there is
	 * no such node.
	 * 
	 * @return the INode that covers the same logical text sequence as this IDomNode. May return <code>null</code>
	 */
	public INode getNode();

	/**
	 * Returns a node identifier which may be used in selection rules. May return null (in which case selection
	 * on identity is not possible).
	 * 
	 * @return the user assigned identity of the node.
	 */
	public Object getNodeId();

	/**
	 * Returns the {@link NodeType} of this dom node.
	 * 
	 * @return
	 */
	public NodeType getNodeType();

	/**
	 * Returns the offset of this node excluding hidden tokens. If this node is a hidden leaf node or
	 * a composite node that does only contain hidden leaf nodes, the
	 * total offset is returned.
	 * 
	 * @return the offset of this node excluding hidden tokens.
	 */
	int getOffset();

	/**
	 * Returns the parent of the node or <code>null</code> if and only if this is the root node.
	 * 
	 * @return the parent of this node or <code>null</code>.
	 */
	public IDomNode getParent();

	/**
	 * Returns the node being the previous node among this node's parent's children.
	 * 
	 * @return the previous sibling, or null if this node is the first child (or not a child at all).
	 */
	public IDomNode getPreviousSibling();

	/**
	 * Returns the semantic object associated with the (sub) tree rooted in this node, or null if
	 * no semantic object is directly associated with this node. Also see {@link #getNearestSemanticObject()}.
	 * 
	 * @return the direct semantic object, or null
	 */
	public EObject getSemanticObject();

	/**
	 * Returns an mutable set of style classifiers which may be used in matching rules.
	 */
	public Set<Object> getStyleClassifiers();

	/**
	 * A DomNode may have an associated StyleSet; styles applicable only to this node that match with the
	 * highest specificity.
	 * 
	 * @return
	 */
	public StyleSet getStyles();

	/**
	 * Returns the text that is covered by this node (including hidden tokens). The result is never <code>null</code> but may be empty.
	 * 
	 * @return the text that is covered by this node (including hidden tokens). Never <code>null</code>.
	 */
	String getText();

	/**
	 * Returns <code>true</code> if this node has any children. A node may have no children - this does not
	 * make it a leaf node ({@link #isLeaf()}.
	 * 
	 * @return <code>true</code> if this node has any children.
	 */
	public boolean hasChildren();

	/**
	 * Nodes are either Leaf nodes, or composite nodes (that may have children).
	 * 
	 * @return true if node is a leaf node
	 */
	public boolean isLeaf();

	/**
	 * Returns an iterator over this nodes parents, starting with the immediate parent.
	 * 
	 * @return
	 */
	public Iterator<IDomNode> parents();

	/**
	 * Sets a node identifier which may be used in selection rules. If not set, a node has a null (non selectable)
	 * identity.
	 */
	public void setNodeId(Object id);

	/**
	 * @return
	 */
	Iterator<IDomNode> treeIterator();

}
