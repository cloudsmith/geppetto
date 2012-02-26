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
package org.cloudsmith.geppetto.pp.dsl.xt.serializer.acceptor;

import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.BaseDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.CompositeDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.LeafDomNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;

import com.google.inject.internal.Lists;

/**
 * TODO: Fix the calls that use an index - what to do there?
 * 
 */
public class DomModelSequenceAdapter implements ISequenceAcceptor {

	protected ISerializationDiagnostic.Acceptor errorAcceptor;

	private CompositeDomNode current;

	private List<CompositeDomNode> stack;

	public DomModelSequenceAdapter(ISerializationDiagnostic.Acceptor errorAcceptor) {
		current = new CompositeDomNode();
		stack = Lists.newArrayList();
		this.errorAcceptor = errorAcceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedCrossRefDatatype(org.eclipse.xtext.RuleCall,
	 * java.lang.String, org.eclipse.emf.ecore.EObject, int, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptAssignedCrossRefDatatype(RuleCall datatypeRC, String token, EObject value, int index,
			ICompositeNode node) {
		addLeafNodeToCurrent(GrammarUtil.containingCrossReference(datatypeRC), token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedCrossRefEnum(org.eclipse.xtext.RuleCall, java.lang.String,
	 * org.eclipse.emf.ecore.EObject, int, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptAssignedCrossRefEnum(RuleCall enumRC, String token, EObject value, int index, ICompositeNode node) {
		addLeafNodeToCurrent(GrammarUtil.containingCrossReference(enumRC), token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedCrossRefTerminal(org.eclipse.xtext.RuleCall,
	 * java.lang.String, org.eclipse.emf.ecore.EObject, int, org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptAssignedCrossRefTerminal(RuleCall terminalRC, String token, EObject value, int index,
			ILeafNode node) {
		addLeafNodeToCurrent(GrammarUtil.containingCrossReference(terminalRC), token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedDatatype(org.eclipse.xtext.RuleCall, java.lang.String,
	 * java.lang.Object, int, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptAssignedDatatype(RuleCall datatypeRC, String token, Object value, int index, ICompositeNode node) {
		addLeafNodeToCurrent(datatypeRC, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedEnum(org.eclipse.xtext.RuleCall, java.lang.String,
	 * java.lang.Object, int, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptAssignedEnum(RuleCall enumRC, String token, Object value, int index, ICompositeNode node) {
		addLeafNodeToCurrent(enumRC, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedKeyword(org.eclipse.xtext.Keyword, java.lang.String,
	 * java.lang.Boolean, int, org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptAssignedKeyword(Keyword keyword, String token, Boolean value, int index, ILeafNode node) {
		addLeafNodeToCurrent(keyword, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedKeyword(org.eclipse.xtext.Keyword, java.lang.String,
	 * java.lang.String, int, org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptAssignedKeyword(Keyword keyword, String token, String value, int index, ILeafNode node) {
		addLeafNodeToCurrent(keyword, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#acceptAssignedTerminal(org.eclipse.xtext.RuleCall, java.lang.String,
	 * java.lang.Object, int, org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptAssignedTerminal(RuleCall terminalRC, String token, Object value, int index, ILeafNode node) {
		addLeafNodeToCurrent(terminalRC, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor#acceptComment(org.eclipse.xtext.AbstractRule, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptComment(AbstractRule rule, String token, ILeafNode node) {
		addLeafNodeToCurrent(rule, token, node);
	}

	/**
	 * An unassigned rule call is not meaningful to keep in the DOM tree. It is basically information
	 * about the rule path taken to something concrete like a terminal.
	 * This implementation simply does nothing.
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#acceptUnassignedAction(org.eclipse.xtext.Action)
	 */
	@Override
	public void acceptUnassignedAction(Action action) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#acceptUnassignedDatatype(org.eclipse.xtext.RuleCall, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptUnassignedDatatype(RuleCall datatypeRC, String token, ICompositeNode node) {
		addLeafNodeToCurrent(datatypeRC, token, node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#acceptUnassignedEnum(org.eclipse.xtext.RuleCall, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public void acceptUnassignedEnum(RuleCall enumRC, String token, ICompositeNode node) {
		addLeafNodeToCurrent(enumRC, token, node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#acceptUnassignedKeyword(org.eclipse.xtext.Keyword, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptUnassignedKeyword(Keyword keyword, String token, ILeafNode node) {
		addLeafNodeToCurrent(keyword, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#acceptUnassignedTerminal(org.eclipse.xtext.RuleCall, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptUnassignedTerminal(RuleCall terminalRC, String token, ILeafNode node) {
		addLeafNodeToCurrent(terminalRC, token, node);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor#acceptWhitespace(org.eclipse.xtext.AbstractRule, java.lang.String,
	 * org.eclipse.xtext.nodemodel.ILeafNode)
	 */
	@Override
	public void acceptWhitespace(AbstractRule rule, String token, ILeafNode node) {
		addLeafNodeToCurrent(rule, token, node);
	}

	protected void addLeafNodeToCurrent(EObject rule, String token, INode node) {
		BaseDomNode result = new LeafDomNode();
		result.setText(token);
		result.setNode(node);
		result.setGrammarElement(rule);
		addNodeToCurrent(result);
	}

	protected void addNodeToCurrent(BaseDomNode node) {
		current.addChild(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#enterAssignedAction(org.eclipse.xtext.Action,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public boolean enterAssignedAction(Action action, EObject semanticChild, ICompositeNode node) {
		push();
		current.setGrammarElement(action);
		current.setSemanticElement(semanticChild);
		current.setNode(node);

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#enterAssignedParserRuleCall(org.eclipse.xtext.RuleCall,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.xtext.nodemodel.ICompositeNode)
	 */
	@Override
	public boolean enterAssignedParserRuleCall(RuleCall rc, EObject semanticChild, ICompositeNode node) {
		push();
		current.setGrammarElement(rc);
		current.setSemanticElement(semanticChild);
		current.setNode(node);

		return true;
	}

	/**
	 * This information is not terribly useful in the DOM as it says that the grammar has performed
	 * a rule call and is now in a particular rule. Eventually the rule will result is something.
	 * This implementatino simply does nothing.
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#enterUnassignedParserRuleCall(org.eclipse.xtext.RuleCall)
	 */
	@Override
	public void enterUnassignedParserRuleCall(RuleCall rc) {
		// push();
		// current.setGrammarElement(rc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#finish()
	 */
	@Override
	public void finish() {
		if(stack.size() > 0)
			pop();
		else
			current.doLayout();
	}

	protected CompositeDomNode getCurrent() {
		return current;
	}

	/**
	 * Returns the constructed Dom model. The returned IDomNode is always a composite node
	 * 
	 * @return the constructed dom model root
	 */
	public IDomNode getDomModel() {
		return current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#leaveAssignedAction(org.eclipse.xtext.Action,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void leaveAssignedAction(Action action, EObject semanticChild) {
		pop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor#leaveAssignedParserRuleCall(org.eclipse.xtext.RuleCall,
	 * org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void leaveAssignedParserRuleCall(RuleCall rc, EObject semanticChild) {
		pop();
	}

	/**
	 * Since the information about entry to a RuleCall is not recorded, neither is leaving it.
	 * This implementation does nothing.
	 * 
	 * @see org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor#leaveUnssignedParserRuleCall(org.eclipse.xtext.RuleCall)
	 */
	@Override
	public void leaveUnssignedParserRuleCall(RuleCall rc) {
	}

	/**
	 * Current is added as a child to the top of the stack, and the top of the stack is made current.
	 * The stack is popped.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             - if the calls to push/pop are not balanced and the stack is empty.
	 */
	protected void pop() {
		CompositeDomNode top = stack.remove(stack.size() - 1);
		// top.addChild(current);
		current = top;
	}

	/**
	 * Adds current to the stack, and creates a new empty CompositeDomNode as the current node.
	 */
	protected void push() {
		stack.add(current);
		CompositeDomNode newCurrent = new CompositeDomNode();
		current.addChild(newCurrent);
		current = newCurrent;
	}

}
