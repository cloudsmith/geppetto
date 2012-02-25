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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.parsetree.reconstr.impl.TokenUtil;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Provides a mapping from semantic objects to comments before and after the object.
 * 
 */
public class FormattingCommentAssociator {
	public static class CommentAssociations {
		Multimap<EObject, INode> beforeMap;

		Multimap<EObject, INode> afterMap;

		public CommentAssociations() {
			beforeMap = ArrayListMultimap.create();
			afterMap = ArrayListMultimap.create();
		}

		protected void acceptAfter(EObject obj, Collection<? extends INode> commentNodes) {
			for(INode n : commentNodes)
				afterMap.put(obj, n);
			commentNodes.clear();
		}

		protected void acceptAfter(EObject obj, INode node) {
			afterMap.put(obj, node);
		}

		protected void acceptBefore(EObject obj, Collection<? extends INode> commentNodes) {
			for(INode n : commentNodes)
				beforeMap.put(obj, n);
			commentNodes.clear();
		}

		protected void acceptBefore(EObject obj, INode node) {
			beforeMap.put(obj, node);
		}

		public Iterator<INode> after(EObject obj) {
			Collection<INode> val = afterMap.get(obj);
			if(val == null)
				return Iterators.emptyIterator();
			return val.iterator();
		}

		public Iterator<INode> before(EObject obj) {
			Collection<INode> val = beforeMap.get(obj);
			if(val == null)
				return Iterators.emptyIterator();
			return val.iterator();
		}
	}

	@Inject
	protected TokenUtil tokenUtil;

	/**
	 * This implementation associates each comment with the next following semantic token's EObject, except for the
	 * case, where a line of the document end by a semantic element followed by a comment. Then, the the comment is
	 * associated with this preceding semantic token.
	 */
	protected void associateCommentsWithSemanticEObjects(CommentAssociations mapping, ICompositeNode rootNode) {
		// System.out.println(EmfFormatter.objToStr(rootNode));
		EObject currentEObject = null;
		List<ILeafNode> currentComments = new ArrayList<ILeafNode>();

		NodeIterator nodeIterator = new NodeIterator(rootNode);
		// rewind to previous token with token owner
		while(nodeIterator.hasPrevious() && currentEObject == null) {
			INode node = nodeIterator.previous();
			if(tokenUtil.isToken(node)) {
				currentEObject = tokenUtil.getTokenOwner(node);
			}
		}
		while(nodeIterator.hasNext()) {
			INode node = nodeIterator.next();
			if(tokenUtil.isCommentNode(node)) {
				currentComments.add((ILeafNode) node);
			}
			boolean isToken = tokenUtil.isToken(node);
			if((node instanceof ILeafNode || isToken) && node.getStartLine() != node.getEndLine() &&
					currentEObject != null) {
				// found a newline -> associating existing comments with currentEObject
				mapping.acceptAfter(currentEObject, currentComments);
				// addMapping(mapping, currentComments, currentEObject);
				currentEObject = null;
			}
			if(isToken) {
				Pair<List<ILeafNode>, List<ILeafNode>> leadingAndTrailingHiddenTokens = tokenUtil.getLeadingAndTrailingHiddenTokens(node);
				for(ILeafNode leadingHiddenNode : leadingAndTrailingHiddenTokens.getFirst()) {
					if(tokenUtil.isCommentNode(leadingHiddenNode)) {
						currentComments.add(leadingHiddenNode);
					}
				}
				nodeIterator.prune();
				currentEObject = tokenUtil.getTokenOwner(node);
				if(currentEObject != null) {
					mapping.acceptBefore(currentEObject, currentComments);
					// addMapping(mapping, currentComments, currentEObject);
					if(node.getOffset() > rootNode.getOffset() + rootNode.getLength()) {
						// found next EObject outside rootNode
						break;
					}
				}
			}
		}
		if(!currentComments.isEmpty()) {
			if(currentEObject != null) {
				mapping.acceptBefore(currentEObject, currentComments);
				// addMapping(mapping, currentComments, currentEObject);
			}
			else {
				EObject objectForRemainingComments = getEObjectForRemainingComments(rootNode);
				if(objectForRemainingComments != null) {
					mapping.acceptBefore(currentEObject, currentComments);
					// addMapping(mapping, currentComments, objectForRemainingComments);
				}
			}
		}
	}

	public CommentAssociations associateCommentsWithSemanticEObjects(EObject model, Set<ICompositeNode> roots) {
		CommentAssociations mapping = new CommentAssociations();
		for(ICompositeNode rootNode : roots)
			associateCommentsWithSemanticEObjects(mapping, rootNode);
		return mapping;
	}

	protected EObject getEObjectForRemainingComments(ICompositeNode rootNode) {
		TreeIterator<INode> i = rootNode.getAsTreeIterable().iterator();
		while(i.hasNext()) {
			INode o = i.next();
			if(o.hasDirectSemanticElement())
				return o.getSemanticElement();
		}
		return null;
	}

}
