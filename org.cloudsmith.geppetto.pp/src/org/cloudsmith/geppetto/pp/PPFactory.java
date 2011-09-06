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
package org.cloudsmith.geppetto.pp;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage
 * @generated
 */
public interface PPFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	PPFactory eINSTANCE = org.cloudsmith.geppetto.pp.impl.PPFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Additive Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Additive Expression</em>'.
	 * @generated
	 */
	AdditiveExpression createAdditiveExpression();

	/**
	 * Returns a new object of class '<em>And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>And Expression</em>'.
	 * @generated
	 */
	AndExpression createAndExpression();

	/**
	 * Returns a new object of class '<em>Append Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Append Expression</em>'.
	 * @generated
	 */
	AppendExpression createAppendExpression();

	/**
	 * Returns a new object of class '<em>Assignment Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Assignment Expression</em>'.
	 * @generated
	 */
	AssignmentExpression createAssignmentExpression();

	/**
	 * Returns a new object of class '<em>At Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>At Expression</em>'.
	 * @generated
	 */
	AtExpression createAtExpression();

	/**
	 * Returns a new object of class '<em>Attribute Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Attribute Operation</em>'.
	 * @generated
	 */
	AttributeOperation createAttributeOperation();

	/**
	 * Returns a new object of class '<em>Attribute Operations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Attribute Operations</em>'.
	 * @generated
	 */
	AttributeOperations createAttributeOperations();

	/**
	 * Returns a new object of class '<em>Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Case</em>'.
	 * @generated
	 */
	Case createCase();

	/**
	 * Returns a new object of class '<em>Case Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Case Expression</em>'.
	 * @generated
	 */
	CaseExpression createCaseExpression();

	/**
	 * Returns a new object of class '<em>Collect Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Collect Expression</em>'.
	 * @generated
	 */
	CollectExpression createCollectExpression();

	/**
	 * Returns a new object of class '<em>Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Definition</em>'.
	 * @generated
	 */
	Definition createDefinition();

	/**
	 * Returns a new object of class '<em>Definition Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Definition Argument</em>'.
	 * @generated
	 */
	DefinitionArgument createDefinitionArgument();

	/**
	 * Returns a new object of class '<em>Definition Argument List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Definition Argument List</em>'.
	 * @generated
	 */
	DefinitionArgumentList createDefinitionArgumentList();

	/**
	 * Returns a new object of class '<em>Double Quoted String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Double Quoted String</em>'.
	 * @generated
	 */
	DoubleQuotedString createDoubleQuotedString();

	/**
	 * Returns a new object of class '<em>Else Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Else Expression</em>'.
	 * @generated
	 */
	ElseExpression createElseExpression();

	/**
	 * Returns a new object of class '<em>Else If Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Else If Expression</em>'.
	 * @generated
	 */
	ElseIfExpression createElseIfExpression();

	/**
	 * Returns a new object of class '<em>Equality Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Equality Expression</em>'.
	 * @generated
	 */
	EqualityExpression createEqualityExpression();

	/**
	 * Returns a new object of class '<em>Exported Collect Query</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Exported Collect Query</em>'.
	 * @generated
	 */
	ExportedCollectQuery createExportedCollectQuery();

	/**
	 * Returns a new object of class '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expression</em>'.
	 * @generated
	 */
	Expression createExpression();

	/**
	 * Returns a new object of class '<em>Expression TE</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expression TE</em>'.
	 * @generated
	 */
	ExpressionTE createExpressionTE();

	/**
	 * Returns a new object of class '<em>Expr List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr List</em>'.
	 * @generated
	 */
	ExprList createExprList();

	/**
	 * Returns a new object of class '<em>Function Call</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Function Call</em>'.
	 * @generated
	 */
	FunctionCall createFunctionCall();

	/**
	 * Returns a new object of class '<em>Hash Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Hash Entry</em>'.
	 * @generated
	 */
	HashEntry createHashEntry();

	/**
	 * Returns a new object of class '<em>Host Class Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Host Class Definition</em>'.
	 * @generated
	 */
	HostClassDefinition createHostClassDefinition();

	/**
	 * Returns a new object of class '<em>If Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>If Expression</em>'.
	 * @generated
	 */
	IfExpression createIfExpression();

	/**
	 * Returns a new object of class '<em>Import Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Import Expression</em>'.
	 * @generated
	 */
	ImportExpression createImportExpression();

	/**
	 * Returns a new object of class '<em>In Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>In Expression</em>'.
	 * @generated
	 */
	InExpression createInExpression();

	/**
	 * Returns a new object of class '<em>Interpolated Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Interpolated Variable</em>'.
	 * @generated
	 */
	InterpolatedVariable createInterpolatedVariable();

	/**
	 * Returns a new object of class '<em>Literal Boolean</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Boolean</em>'.
	 * @generated
	 */
	LiteralBoolean createLiteralBoolean();

	/**
	 * Returns a new object of class '<em>Literal Default</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Default</em>'.
	 * @generated
	 */
	LiteralDefault createLiteralDefault();

	/**
	 * Returns a new object of class '<em>Literal Hash</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Hash</em>'.
	 * @generated
	 */
	LiteralHash createLiteralHash();

	/**
	 * Returns a new object of class '<em>Literal List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal List</em>'.
	 * @generated
	 */
	LiteralList createLiteralList();

	/**
	 * Returns a new object of class '<em>Literal Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Name</em>'.
	 * @generated
	 */
	LiteralName createLiteralName();

	/**
	 * Returns a new object of class '<em>Literal Name Or Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Name Or Reference</em>'.
	 * @generated
	 */
	LiteralNameOrReference createLiteralNameOrReference();

	/**
	 * Returns a new object of class '<em>Literal Regex</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Regex</em>'.
	 * @generated
	 */
	LiteralRegex createLiteralRegex();

	/**
	 * Returns a new object of class '<em>Literal Undef</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal Undef</em>'.
	 * @generated
	 */
	LiteralUndef createLiteralUndef();

	/**
	 * Returns a new object of class '<em>Matching Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Matching Expression</em>'.
	 * @generated
	 */
	MatchingExpression createMatchingExpression();

	/**
	 * Returns a new object of class '<em>Multiplicative Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Multiplicative Expression</em>'.
	 * @generated
	 */
	MultiplicativeExpression createMultiplicativeExpression();

	/**
	 * Returns a new object of class '<em>Node Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Node Definition</em>'.
	 * @generated
	 */
	NodeDefinition createNodeDefinition();

	/**
	 * Returns a new object of class '<em>Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Or Expression</em>'.
	 * @generated
	 */
	OrExpression createOrExpression();

	/**
	 * Returns a new object of class '<em>Parenthesised Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Parenthesised Expression</em>'.
	 * @generated
	 */
	ParenthesisedExpression createParenthesisedExpression();

	/**
	 * Returns a new object of class '<em>Puppet Manifest</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Puppet Manifest</em>'.
	 * @generated
	 */
	PuppetManifest createPuppetManifest();

	/**
	 * Returns a new object of class '<em>Relational Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Relational Expression</em>'.
	 * @generated
	 */
	RelationalExpression createRelationalExpression();

	/**
	 * Returns a new object of class '<em>Relationship Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Relationship Expression</em>'.
	 * @generated
	 */
	RelationshipExpression createRelationshipExpression();

	/**
	 * Returns a new object of class '<em>Resource Body</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Resource Body</em>'.
	 * @generated
	 */
	ResourceBody createResourceBody();

	/**
	 * Returns a new object of class '<em>Resource Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Resource Expression</em>'.
	 * @generated
	 */
	ResourceExpression createResourceExpression();

	/**
	 * Returns a new object of class '<em>Selector Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Selector Entry</em>'.
	 * @generated
	 */
	SelectorEntry createSelectorEntry();

	/**
	 * Returns a new object of class '<em>Selector Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Selector Expression</em>'.
	 * @generated
	 */
	SelectorExpression createSelectorExpression();

	/**
	 * Returns a new object of class '<em>Shift Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Shift Expression</em>'.
	 * @generated
	 */
	ShiftExpression createShiftExpression();

	/**
	 * Returns a new object of class '<em>Single Quoted String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Single Quoted String</em>'.
	 * @generated
	 */
	SingleQuotedString createSingleQuotedString();

	/**
	 * Returns a new object of class '<em>Unary Minus Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Unary Minus Expression</em>'.
	 * @generated
	 */
	UnaryMinusExpression createUnaryMinusExpression();

	/**
	 * Returns a new object of class '<em>Unary Not Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Unary Not Expression</em>'.
	 * @generated
	 */
	UnaryNotExpression createUnaryNotExpression();

	/**
	 * Returns a new object of class '<em>Unquoted String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Unquoted String</em>'.
	 * @generated
	 */
	UnquotedString createUnquotedString();

	/**
	 * Returns a new object of class '<em>Variable Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Variable Expression</em>'.
	 * @generated
	 */
	VariableExpression createVariableExpression();

	/**
	 * Returns a new object of class '<em>Variable TE</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Variable TE</em>'.
	 * @generated
	 */
	VariableTE createVariableTE();

	/**
	 * Returns a new object of class '<em>Verbatim TE</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Verbatim TE</em>'.
	 * @generated
	 */
	VerbatimTE createVerbatimTE();

	/**
	 * Returns a new object of class '<em>Virtual Collect Query</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Virtual Collect Query</em>'.
	 * @generated
	 */
	VirtualCollectQuery createVirtualCollectQuery();

	/**
	 * Returns a new object of class '<em>Virtual Name Or Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Virtual Name Or Reference</em>'.
	 * @generated
	 */
	VirtualNameOrReference createVirtualNameOrReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	PPPackage getPPPackage();

} // PPFactory
