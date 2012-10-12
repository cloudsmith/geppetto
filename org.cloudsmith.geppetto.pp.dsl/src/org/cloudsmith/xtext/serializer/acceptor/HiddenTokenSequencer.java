/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG (http://www.itemis.eu) - initial API and implementation
 *   Cloudsmith - adaption to DomModel and Contextual formatter
 * 
 */
package org.cloudsmith.xtext.serializer.acceptor;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.serializer.ICommentReconcilement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.parsetree.reconstr.impl.TokenUtil;
import org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.ISyntacticSequencer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * This is an adapted version of HiddenTokenSequencer that emits implicit white space where it is allowed.
 * Implicit WS is emitted also when an INode model is not present.
 * 
 */
public class HiddenTokenSequencer implements IHiddenTokenSequencer2, ISyntacticSequenceAcceptor {

	@Inject
	protected IHiddenTokenHelper hiddenTokenHelper;

	@Inject
	protected TokenUtil tokenUtil;

	protected ISequenceAcceptor delegate;

	protected INode lastNode;

	protected INode lastNodes[] = new INode[2];

	protected INode rootNode;

	protected ISyntacticSequencer sequencer;

	/**
	 * List of 'tokens' that are currently hidden.
	 */
	protected List<AbstractRule> currentHidden;

	/**
	 * Stack tracking the state of 'hidden tokens'
	 */
	protected List<List<AbstractRule>> hiddenStack = Lists.newArrayList();

	protected List<RuleCall> stack = Lists.newArrayList();

	/**
	 * When searching for hidden nodes between INodes 'from' and 'to', the {@link #hiddenInLastNode} describes
	 * the state of 'hidden' when 'from' was sequenced, and {@link #currentHidden} when 'to' is sequenced.
	 */
	protected List<AbstractRule> hiddenInLastNode;

	private ICommentReconcilement commentReconcilement;

	@Override
	public void acceptAssignedCrossRefDatatype(RuleCall rc, String tkn, EObject val, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptAssignedCrossRefDatatype(rc, tkn, val, index, node);
	}

	@Override
	public void acceptAssignedCrossRefEnum(RuleCall rc, String token, EObject value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptAssignedCrossRefEnum(rc, token, value, index, node);
	}

	@Override
	public void acceptAssignedCrossRefKeyword(Keyword kw, String token, EObject value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptAssignedCrossRefKeyword(kw, token, value, index, node);
	}

	@Override
	public void acceptAssignedCrossRefTerminal(RuleCall rc, String token, EObject value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptAssignedCrossRefTerminal(rc, token, value, index, node);
	}

	@Override
	public void acceptAssignedDatatype(RuleCall rc, String token, Object value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptAssignedDatatype(rc, token, value, index, node);
	}

	@Override
	public void acceptAssignedEnum(RuleCall enumRC, String token, Object value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptAssignedEnum(enumRC, token, value, index, node);
	}

	@Override
	public void acceptAssignedKeyword(Keyword keyword, String token, Object value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptAssignedKeyword(keyword, token, value, index, node);
	}

	@Override
	public void acceptAssignedTerminal(RuleCall terminalRC, String token, Object value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptAssignedTerminal(terminalRC, token, value, index, node);
	}

	@Override
	public void acceptUnassignedAction(Action action) {
		delegate.acceptUnassignedAction(action);
	}

	@Override
	public void acceptUnassignedDatatype(RuleCall datatypeRC, String token, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptUnassignedDatatype(datatypeRC, token, node);
	}

	@Override
	public void acceptUnassignedEnum(RuleCall enumRC, String token, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberLastLeaf(node);
		delegate.acceptUnassignedEnum(enumRC, token, node);
	}

	@Override
	public void acceptUnassignedKeyword(Keyword keyword, String token, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptUnassignedKeyword(keyword, token, node);
	}

	@Override
	public void acceptUnassignedTerminal(RuleCall terminalRC, String token, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		rememberNode(node);
		delegate.acceptUnassignedTerminal(terminalRC, token, node);
	}

	protected void emitHiddenTokens(List<INode> hiddens /* Set<INode> comments, */) {
		if(hiddens == null)
			return;
		boolean lastNonWhitespace = true;
		AbstractRule ws = hiddenTokenHelper.getWhitespaceRuleFor(null, "");
		INode prevCommentNode = null;
		for(INode node : hiddens)
			if(tokenUtil.isCommentNode(node)) {
				if(lastNonWhitespace) {
					String wsString = commentReconcilement != null
							? commentReconcilement.getWhitespaceBetween(prevCommentNode, node)
							: "";
					delegate.acceptWhitespace(hiddenTokenHelper.getWhitespaceRuleFor(null, wsString), wsString, null);
				}
				delegate.acceptComment((AbstractRule) node.getGrammarElement(), node.getText(), (ILeafNode) node);
				lastNonWhitespace = true;
				prevCommentNode = node;
			}
			else {
				delegate.acceptWhitespace((AbstractRule) node.getGrammarElement(), node.getText(), (ILeafNode) node);
				lastNonWhitespace = false;
			}
		// NOTE: The original implementation has a FIX-ME note here that whitespace should be determined
		// correctly. (Well, it did not work until a check was added if the ws was hidden or not).

		// Longer explanation:
		// When there is no WS between two elements and no node model the contextual serializer/formatter
		// performs serialization by inserting an IMPLICIT WS.
		// When the node model is created, empty ws nodes are skipped, and thus have to be created (this happens
		// here). The created whitespace node should *NOT* be marked as implicit, since it by virtue of having been
		// parsed is now the source text and should not be subject to formatting (like the IMPLICIT WS always is)
		// when in "preserve whitespace" mode.
		// Finally, what the fix below does is to also check if a missing WS should be emitted based on
		// if whitespace is visible or not.
		// THIS IS PROBABLY STILL NOT ENOUGH, as it may overrule the attempt to treat visible WS as eligible for formatting
		// see isImpliedWhitespace and where it is called.

		// if(lastNonWhitespace && currentHidden.contains(ws)) {
		if(lastNonWhitespace && (hiddenInLastNode.contains(ws) || currentHidden.contains(ws))) {
			// hiddenInLastNode.contains(ws)) {
			delegate.acceptWhitespace(ws, "", null);
		}
	}

	public boolean enterAssignedAction(Action action, EObject semanticChild, ICompositeNode node) {
		return delegate.enterAssignedAction(action, semanticChild, node);
	}

	public boolean enterAssignedParserRuleCall(RuleCall rc, EObject semanticChild, ICompositeNode node) {
		push(rc);
		return delegate.enterAssignedParserRuleCall(rc, semanticChild, node);
	}

	public void enterUnassignedParserRuleCall(RuleCall rc) {
		push(rc);
		delegate.enterUnassignedParserRuleCall(rc);
	}

	public void finish() {
		if(rootNode != null && rootNode == rootNode.getRootNode()) {
			List<INode> hidden = getRemainingHiddenNodesInContainer(lastNode, rootNode);
			if(!hidden.isEmpty()) {
				emitHiddenTokens(hidden);
				lastNodes[1] = lastNodes[0];
				lastNode = lastNodes[0] = rootNode;
			}
		}
		delegate.finish();
	}

	protected Set<INode> getCommentsForEObject(EObject semanticObject, INode node) {
		if(node == null)
			return Collections.emptySet();
		Set<INode> result = Sets.newHashSet();
		BidiTreeIterator<INode> ti = node.getAsTreeIterable().iterator();
		while(ti.hasNext()) {
			INode next = ti.next();
			if(next.getSemanticElement() != null && next.getSemanticElement() != semanticObject) {
				ti.prune();
				continue;
			}
			if(tokenUtil.isCommentNode(next))
				result.add(next);
		}
		return result;
	}

	protected List<INode> getHiddenNodesBetween(INode from, INode to) {
		List<INode> result = getHiddenNodesBetween2(from, to);
		result = reconcileComments(result, lastNodes[1], from, to);
		if(result == null) {
			AbstractRule ws = hiddenTokenHelper.getWhitespaceRuleFor(null, "");
			// only emit hidden whitespace, or visible whitespace where this is overridden using
			// isImpliedWhitespace
			// boolean implied = currentHidden != null && currentHidden.contains(ws);
			boolean impliedFrom = hiddenInLastNode != null && hiddenInLastNode.contains(ws);
			boolean impliedTo = currentHidden != null && currentHidden.contains(ws);
			boolean implied = impliedFrom || impliedTo;
			int sz = stack.size();
			implied = isImpliedWhitespace(implied, sz == 0
					? null
					: stack.get(sz - 1), from, to);
			if(implied) {
				delegate.acceptWhitespace(ws, IDomNode.IMPLIED_EMPTY_WHITESPACE, null);
			}
		}
		return result;
	}

	protected List<INode> getHiddenNodesBetween2(INode from, INode to) {
		if(from == null || to == null)
			return null;
		List<INode> out = Lists.newArrayList();
		NodeIterator ni = new NodeIterator(from);
		while(ni.hasNext()) {
			INode next = ni.next();
			if(tokenUtil.isWhitespaceOrCommentNode(next)) {
				out.add(next);
			}
			else if(next.equals(to)) {
				if(next instanceof ICompositeNode &&
						(GrammarUtil.isDatatypeRuleCall(next.getGrammarElement()) ||
								GrammarUtil.isEnumRuleCall(next.getGrammarElement()) || next.getGrammarElement() instanceof CrossReference))
					while(ni.hasNext()) {
						INode next2 = ni.next();
						if(tokenUtil.isWhitespaceOrCommentNode(next2)) {
							out.add(next2);
						}
						else if(next2 instanceof ILeafNode)
							return out;
					}
				else
					return out;
			}
			else if(tokenUtil.isToken(next))
				return out; // null;
		}
		return out;
	}

	private INode getLastLeaf(INode node) {
		while(node instanceof ICompositeNode)
			node = ((ICompositeNode) node).getLastChild();
		return node;
	}

	protected List<INode> getRemainingHiddenNodesInContainer(INode from, INode root) {
		if(from == null || root == null)
			return Collections.emptyList();
		List<INode> out = Lists.newArrayList();
		NodeIterator ni = new NodeIterator(from);
		while(ni.hasNext()) {
			INode next = ni.next();
			if(next.getTotalOffset() > root.getTotalEndOffset())
				return out;
			else if(tokenUtil.isWhitespaceOrCommentNode(next)) {
				out.add(next);
			}
			else if(tokenUtil.isToken(next))
				return Collections.emptyList();
		}
		return out;
	}

	public void init(EObject context, EObject semanticObject, ISequenceAcceptor sequenceAcceptor, Acceptor errorAcceptor) {
		init(context, semanticObject, sequenceAcceptor, errorAcceptor, null);
	}

	@Override
	public void init(EObject context, EObject semanticObject, ISequenceAcceptor sequenceAcceptor,
			Acceptor errorAcceptor, ICommentReconcilement commentReconciliator) {
		this.delegate = sequenceAcceptor;
		this.lastNode = NodeModelUtils.findActualNodeFor(semanticObject);
		this.rootNode = lastNode;
		lastNodes[0] = lastNodes[1] = this.lastNode;
		initCurrentHidden(context);
		this.commentReconcilement = commentReconciliator;
	}

	protected void initCurrentHidden(EObject context) {
		// when called for a specific parser rule, its hidden() spec (if any) is made current
		// otherwise the hidden() spec of the grammar is made current.
		// (There is no real way to calculate the calling chain to a particular starting parser rule)
		//
		if(context instanceof ParserRule) {
			ParserRule pr = (ParserRule) context;
			if(pr.isDefinesHiddenTokens())
				currentHidden = pr.getHiddenTokens();
			else {
				Grammar grammar = GrammarUtil.getGrammar(context);
				currentHidden = grammar.getHiddenTokens();
			}
			// TODO: Verify this is correct
			hiddenInLastNode = currentHidden;
		}

	}

	/**
	 * This method should be overridden in an implementation where certain visible whitespace
	 * rules should be subject to formatting.
	 * 
	 * @param defaultResult
	 *            - the result to return if the already made decision is ok
	 * @param rc
	 *            - the {@link RuleCall} (or {@link Grammar}) in the call chain that determined what is hidden
	 * @param from
	 *            - the node left of where the ws appears, or null if there is no node model
	 * @param to
	 *            - the node right if where the ws appears, or null if there is no node model
	 * @return true if this WS should be eligible for formatting
	 */
	protected boolean isImpliedWhitespace(boolean defaultResult, EObject rc, INode from, INode to) {
		return defaultResult;
	}

	public void leaveAssignedAction(Action action, EObject semanticChild) {
		delegate.leaveAssignedAction(action, semanticChild);
	}

	public void leaveAssignedParserRuleCall(RuleCall rc, EObject semanticChild) {
		delegate.leaveAssignedParserRuleCall(rc, semanticChild);
		pop();
	}

	public void leaveUnssignedParserRuleCall(RuleCall rc) {
		delegate.leaveUnssignedParserRuleCall(rc);
		pop();
	}

	protected void pop() {
		RuleCall top = stack.remove(stack.size() - 1);

		// if the rule call on top defines hidden, it pushed on the hidden stack, and state needs to
		// be restored
		final AbstractRule r = top.getRule();
		if(r instanceof ParserRule && ((ParserRule) r).isDefinesHiddenTokens()) {
			currentHidden = hiddenStack.remove(hiddenStack.size() - 1);
		}
	}

	protected void push(RuleCall rc) {
		stack.add(rc);

		// if rule defines hidden, remember previous hidden, and set the new as current
		final AbstractRule r = rc.getRule();
		if(r instanceof ParserRule && ((ParserRule) r).isDefinesHiddenTokens()) {
			hiddenStack.add(currentHidden);
			currentHidden = ((ParserRule) r).getHiddenTokens();
		}
	}

	protected List<INode> reconcileComments(List<INode> currentResult, INode preceding, INode from, INode to) {
		if(preceding == null || from == null || to == null || commentReconcilement == null)
			return currentResult;
		List<INode> result = commentReconcilement.commentNodesFor(preceding, from, to);

		// If the normal comment hunt includes a comment - remove it if it is reconciled.
		// It should perhaps not appear in that position
		//
		if(currentResult != null) {
			{
				ListIterator<INode> litor = currentResult.listIterator();
				while(litor.hasNext()) {
					INode n = litor.next();
					if(tokenUtil.isCommentNode(n) && commentReconcilement.isReconciledCommentNode(n))
						litor.remove();
				}
			}
			// Add the comment nodes the reconciler has for this position
			// (possibly something just removed - that is expected)

			// Also, remove whitespace from current result, if there is any reconciled comments
			if(result.size() > 0) {
				ListIterator<INode> litor = currentResult.listIterator();
				while(litor.hasNext()) {
					INode n = litor.next();
					if(tokenUtil.isWhitespaceNode(n))
						litor.remove();
				}
			}
			for(INode n : result)
				currentResult.add(n);
			result = currentResult;
		}
		return result;
	}

	/**
	 * Remembers the last leaf of given node unless it is null.
	 * 
	 * @param nodeToRemember
	 */
	private void rememberLastLeaf(INode node) {
		if(node == null)
			return;

		lastNodes[1] = lastNodes[0];
		lastNode = lastNodes[0] = getLastLeaf(node);
		hiddenInLastNode = currentHidden;
	}

	/**
	 * Remembers the given node unless it is null.
	 * 
	 * @param nodeToRemember
	 */
	private void rememberNode(INode nodeToRemember) {
		if(nodeToRemember == null)
			return;
		lastNodes[1] = lastNodes[0];
		lastNode = lastNodes[0] = nodeToRemember;
		hiddenInLastNode = currentHidden;
	}
}
