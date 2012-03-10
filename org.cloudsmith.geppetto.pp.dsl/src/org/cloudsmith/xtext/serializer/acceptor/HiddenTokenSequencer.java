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
import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
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
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;
import org.eclipse.xtext.serializer.sequencer.ISyntacticSequencer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * This is an adapted version of HiddenTokenSequencer that emits implicit white space where it is allowed.
 * Implicit WS is emitted also when an INode model is not present.
 * 
 */
public class HiddenTokenSequencer implements IHiddenTokenSequencer, ISyntacticSequenceAcceptor {

	@Inject
	protected IHiddenTokenHelper hiddenTokenHelper;

	@Inject
	protected TokenUtil tokenUtil;

	protected ISequenceAcceptor delegate;

	protected INode lastNode;

	protected INode rootNode;

	protected ISyntacticSequencer sequencer;

	protected List<AbstractRule> currentHidden;

	protected List<List<AbstractRule>> hiddenStack = Lists.newArrayList();

	protected List<RuleCall> stack = Lists.newArrayList();

	public void acceptAssignedCrossRefDatatype(RuleCall rc, String tkn, EObject val, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptAssignedCrossRefDatatype(rc, tkn, val, index, node);
	}

	public void acceptAssignedCrossRefEnum(RuleCall rc, String token, EObject value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptAssignedCrossRefEnum(rc, token, value, index, node);
	}

	public void acceptAssignedCrossRefTerminal(RuleCall rc, String token, EObject value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptAssignedCrossRefTerminal(rc, token, value, index, node);
	}

	public void acceptAssignedDatatype(RuleCall rc, String token, Object value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptAssignedDatatype(rc, token, value, index, node);
	}

	public void acceptAssignedEnum(RuleCall enumRC, String token, Object value, int index, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptAssignedEnum(enumRC, token, value, index, node);
	}

	public void acceptAssignedKeyword(Keyword keyword, String token, Boolean value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptAssignedKeyword(keyword, token, value, index, node);
	}

	public void acceptAssignedKeyword(Keyword keyword, String token, String value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptAssignedKeyword(keyword, token, value, index, node);
	}

	public void acceptAssignedTerminal(RuleCall terminalRC, String token, Object value, int index, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptAssignedTerminal(terminalRC, token, value, index, node);
	}

	public void acceptUnassignedAction(Action action) {
		delegate.acceptUnassignedAction(action);
	}

	public void acceptUnassignedDatatype(RuleCall datatypeRC, String token, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptUnassignedDatatype(datatypeRC, token, node);
	}

	public void acceptUnassignedEnum(RuleCall enumRC, String token, ICompositeNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = getLastLeaf(node);
		delegate.acceptUnassignedEnum(enumRC, token, node);
	}

	public void acceptUnassignedKeyword(Keyword keyword, String token, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptUnassignedKeyword(keyword, token, node);
	}

	public void acceptUnassignedTerminal(RuleCall terminalRC, String token, ILeafNode node) {
		emitHiddenTokens(getHiddenNodesBetween(lastNode, node));
		lastNode = node;
		delegate.acceptUnassignedTerminal(terminalRC, token, node);
	}

	protected void emitHiddenTokens(List<INode> hiddens /* Set<INode> comments, */) {
		if(hiddens == null)
			return;
		boolean lastNonWhitespace = true;
		AbstractRule ws = hiddenTokenHelper.getWhitespaceRuleFor(null, "");
		for(INode node : hiddens)
			if(tokenUtil.isCommentNode(node)) {
				if(lastNonWhitespace)
					delegate.acceptWhitespace(hiddenTokenHelper.getWhitespaceRuleFor(null, ""), "", null);
				lastNonWhitespace = true;
				// comments.remove(node);
				delegate.acceptComment((AbstractRule) node.getGrammarElement(), node.getText(), (ILeafNode) node);
			}
			else {
				delegate.acceptWhitespace((AbstractRule) node.getGrammarElement(), node.getText(), (ILeafNode) node);
				lastNonWhitespace = false;
			}
		// NOTE: The original implementation has a FIXME note here that whitespace should be determined
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

		if(lastNonWhitespace && currentHidden.contains(ws)) {
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
		if(stack.size() > 0)
			pop();
		if(rootNode != null && rootNode == rootNode.getRootNode()) {
			List<INode> hidden = getRemainingHiddenNodesInContainer(lastNode, rootNode);
			if(!hidden.isEmpty()) {
				emitHiddenTokens(hidden);
				lastNode = rootNode;
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
		if(result == null) {
			AbstractRule ws = hiddenTokenHelper.getWhitespaceRuleFor(null, "");
			// only emit hidden whitespace, or visible whitespace where this is overridden using
			// isImpliedWhitespace
			boolean implied = currentHidden != null && currentHidden.contains(ws);
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
				return null;
		}
		return out;
	}

	protected INode getLastLeaf(INode node) {
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
		this.delegate = sequenceAcceptor;
		this.lastNode = NodeModelUtils.findActualNodeFor(semanticObject);
		this.rootNode = lastNode;
		initCurrentHidden(context);
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
		int sz = stack.size();
		if(sz == 0)
			return;
		RuleCall top = stack.remove(sz - 1);

		// if the rule call on top defines hidden, it pushed on the hidden stack, and state needs to
		// be restored
		if(top.getRule() instanceof ParserRule == false)
			return;
		if(((ParserRule) top.getRule()).isDefinesHiddenTokens())
			currentHidden = hiddenStack.remove(hiddenStack.size() - 1);
	}

	protected void push(RuleCall rc) {
		stack.add(rc);

		if(rc.getRule() instanceof ParserRule == false)
			return;
		ParserRule pr = (ParserRule) rc.getRule();
		if(!pr.isDefinesHiddenTokens())
			return;
		// if rule defines hidden, remember previous hidden, and set the new as current
		hiddenStack.add(currentHidden);
		currentHidden = pr.getHiddenTokens();
	}
}
