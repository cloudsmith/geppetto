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
package org.cloudsmith.geppetto.pp.util;

import java.util.List;

import org.cloudsmith.geppetto.pp.AdditiveExpression;
import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.BinaryExpression;
import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.DefinitionArgumentList;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.EqualityExpression;
import org.cloudsmith.geppetto.pp.ExportedCollectQuery;
import org.cloudsmith.geppetto.pp.ExprList;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionBlock;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HashEntry;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.ICollectQuery;
import org.cloudsmith.geppetto.pp.IQuotedString;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.InExpression;
import org.cloudsmith.geppetto.pp.InterpolatedVariable;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralDefault;
import org.cloudsmith.geppetto.pp.LiteralExpression;
import org.cloudsmith.geppetto.pp.LiteralHash;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.LiteralUndef;
import org.cloudsmith.geppetto.pp.MatchingExpression;
import org.cloudsmith.geppetto.pp.MultiplicativeExpression;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.OrExpression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ParameterizedExpression;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.RelationalExpression;
import org.cloudsmith.geppetto.pp.RelationshipExpression;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorEntry;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.ShiftExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.StringExpression;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.UnaryExpression;
import org.cloudsmith.geppetto.pp.UnaryMinusExpression;
import org.cloudsmith.geppetto.pp.UnaryNotExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.VirtualCollectQuery;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage
 * @generated
 */
public class PPSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static PPPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PPSwitch() {
		if(modelPackage == null) {
			modelPackage = PPPackage.eINSTANCE;
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdditiveExpression(AdditiveExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAndExpression(AndExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Append Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Append Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAppendExpression(AppendExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assignment Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assignment Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssignmentExpression(AssignmentExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>At Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>At Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAtExpression(AtExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeOperation(AttributeOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Operations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Operations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeOperations(AttributeOperations object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryExpression(BinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary Op Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary Op Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryOpExpression(BinaryOpExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCase(Case object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCaseExpression(CaseExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Collect Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Collect Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCollectExpression(CollectExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDefinition(Definition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Definition Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Definition Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDefinitionArgument(DefinitionArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Definition Argument List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Definition Argument List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDefinitionArgumentList(DefinitionArgumentList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double Quoted String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Quoted String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleQuotedString(DoubleQuotedString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Else Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Else Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElseExpression(ElseExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Else If Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Else If Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElseIfExpression(ElseIfExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Equality Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Equality Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEqualityExpression(EqualityExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exported Collect Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exported Collect Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExportedCollectQuery(ExportedCollectQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionBlock(ExpressionBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression TE</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression TE</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionTE(ExpressionTE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExprList(ExprList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionCall(FunctionCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hash Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hash Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHashEntry(HashEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Host Class Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Host Class Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHostClassDefinition(HostClassDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ICollect Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ICollect Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICollectQuery(ICollectQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>If Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>If Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfExpression(IfExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImportExpression(ImportExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>In Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>In Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInExpression(InExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Interpolated Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Interpolated Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInterpolatedVariable(InterpolatedVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IQuoted String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IQuoted String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIQuotedString(IQuotedString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Boolean</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Boolean</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralBoolean(LiteralBoolean object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Default</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Default</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralDefault(LiteralDefault object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralExpression(LiteralExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Hash</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Hash</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralHash(LiteralHash object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralList(LiteralList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Name</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Name</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralName(LiteralName object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Name Or Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Name Or Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralNameOrReference(LiteralNameOrReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Regex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Regex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralRegex(LiteralRegex object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal Undef</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal Undef</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralUndef(LiteralUndef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Matching Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Matching Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMatchingExpression(MatchingExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiplicative Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiplicative Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiplicativeExpression(MultiplicativeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNodeDefinition(NodeDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrExpression(OrExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameterized Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameterized Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterizedExpression(ParameterizedExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parenthesised Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parenthesised Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParenthesisedExpression(ParenthesisedExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Puppet Manifest</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Puppet Manifest</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePuppetManifest(PuppetManifest object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Relational Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Relational Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelationalExpression(RelationalExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Relationship Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Relationship Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelationshipExpression(RelationshipExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceBody(ResourceBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceExpression(ResourceExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Selector Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Selector Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSelectorEntry(SelectorEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Selector Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Selector Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSelectorExpression(SelectorExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shift Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shift Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseShiftExpression(ShiftExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single Quoted String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single Quoted String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleQuotedString(SingleQuotedString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringExpression(StringExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextExpression(TextExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryExpression(UnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Minus Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Minus Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryMinusExpression(UnaryMinusExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary Not Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary Not Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryNotExpression(UnaryNotExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unquoted String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unquoted String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnquotedString(UnquotedString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableExpression(VariableExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable TE</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable TE</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableTE(VariableTE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Verbatim TE</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Verbatim TE</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVerbatimTE(VerbatimTE object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Virtual Collect Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Virtual Collect Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVirtualCollectQuery(VirtualCollectQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Virtual Name Or Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Virtual Name Or Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVirtualNameOrReference(VirtualNameOrReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if(theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty()
					? defaultCase(theEObject)
					: doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch(classifierID) {
			case PPPackage.PUPPET_MANIFEST: {
				PuppetManifest puppetManifest = (PuppetManifest) theEObject;
				T result = casePuppetManifest(puppetManifest);
				if(result == null)
					result = caseExpressionBlock(puppetManifest);
				if(result == null)
					result = caseExpression(puppetManifest);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EXPRESSION: {
				Expression expression = (Expression) theEObject;
				T result = caseExpression(expression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.RESOURCE_BODY: {
				ResourceBody resourceBody = (ResourceBody) theEObject;
				T result = caseResourceBody(resourceBody);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ATTRIBUTE_OPERATION: {
				AttributeOperation attributeOperation = (AttributeOperation) theEObject;
				T result = caseAttributeOperation(attributeOperation);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ATTRIBUTE_OPERATIONS: {
				AttributeOperations attributeOperations = (AttributeOperations) theEObject;
				T result = caseAttributeOperations(attributeOperations);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ICOLLECT_QUERY: {
				ICollectQuery iCollectQuery = (ICollectQuery) theEObject;
				T result = caseICollectQuery(iCollectQuery);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.VIRTUAL_COLLECT_QUERY: {
				VirtualCollectQuery virtualCollectQuery = (VirtualCollectQuery) theEObject;
				T result = caseVirtualCollectQuery(virtualCollectQuery);
				if(result == null)
					result = caseUnaryExpression(virtualCollectQuery);
				if(result == null)
					result = caseICollectQuery(virtualCollectQuery);
				if(result == null)
					result = caseExpression(virtualCollectQuery);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EXPORTED_COLLECT_QUERY: {
				ExportedCollectQuery exportedCollectQuery = (ExportedCollectQuery) theEObject;
				T result = caseExportedCollectQuery(exportedCollectQuery);
				if(result == null)
					result = caseUnaryExpression(exportedCollectQuery);
				if(result == null)
					result = caseICollectQuery(exportedCollectQuery);
				if(result == null)
					result = caseExpression(exportedCollectQuery);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.HOST_CLASS_DEFINITION: {
				HostClassDefinition hostClassDefinition = (HostClassDefinition) theEObject;
				T result = caseHostClassDefinition(hostClassDefinition);
				if(result == null)
					result = caseDefinition(hostClassDefinition);
				if(result == null)
					result = caseExpression(hostClassDefinition);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.DEFINITION: {
				Definition definition = (Definition) theEObject;
				T result = caseDefinition(definition);
				if(result == null)
					result = caseExpression(definition);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.DEFINITION_ARGUMENT_LIST: {
				DefinitionArgumentList definitionArgumentList = (DefinitionArgumentList) theEObject;
				T result = caseDefinitionArgumentList(definitionArgumentList);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.DEFINITION_ARGUMENT: {
				DefinitionArgument definitionArgument = (DefinitionArgument) theEObject;
				T result = caseDefinitionArgument(definitionArgument);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.CASE_EXPRESSION: {
				CaseExpression caseExpression = (CaseExpression) theEObject;
				T result = caseCaseExpression(caseExpression);
				if(result == null)
					result = caseExpression(caseExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.CASE: {
				Case case_ = (Case) theEObject;
				T result = caseCase(case_);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.IF_EXPRESSION: {
				IfExpression ifExpression = (IfExpression) theEObject;
				T result = caseIfExpression(ifExpression);
				if(result == null)
					result = caseExpression(ifExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_EXPRESSION: {
				LiteralExpression literalExpression = (LiteralExpression) theEObject;
				T result = caseLiteralExpression(literalExpression);
				if(result == null)
					result = caseExpression(literalExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_NAME_OR_REFERENCE: {
				LiteralNameOrReference literalNameOrReference = (LiteralNameOrReference) theEObject;
				T result = caseLiteralNameOrReference(literalNameOrReference);
				if(result == null)
					result = caseLiteralExpression(literalNameOrReference);
				if(result == null)
					result = caseExpression(literalNameOrReference);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.RESOURCE_EXPRESSION: {
				ResourceExpression resourceExpression = (ResourceExpression) theEObject;
				T result = caseResourceExpression(resourceExpression);
				if(result == null)
					result = caseExpression(resourceExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.IMPORT_EXPRESSION: {
				ImportExpression importExpression = (ImportExpression) theEObject;
				T result = caseImportExpression(importExpression);
				if(result == null)
					result = caseExpression(importExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_LIST: {
				LiteralList literalList = (LiteralList) theEObject;
				T result = caseLiteralList(literalList);
				if(result == null)
					result = caseLiteralExpression(literalList);
				if(result == null)
					result = caseExpression(literalList);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_HASH: {
				LiteralHash literalHash = (LiteralHash) theEObject;
				T result = caseLiteralHash(literalHash);
				if(result == null)
					result = caseLiteralExpression(literalHash);
				if(result == null)
					result = caseExpression(literalHash);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.HASH_ENTRY: {
				HashEntry hashEntry = (HashEntry) theEObject;
				T result = caseHashEntry(hashEntry);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_BOOLEAN: {
				LiteralBoolean literalBoolean = (LiteralBoolean) theEObject;
				T result = caseLiteralBoolean(literalBoolean);
				if(result == null)
					result = caseLiteralExpression(literalBoolean);
				if(result == null)
					result = caseExpression(literalBoolean);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_UNDEF: {
				LiteralUndef literalUndef = (LiteralUndef) theEObject;
				T result = caseLiteralUndef(literalUndef);
				if(result == null)
					result = caseLiteralExpression(literalUndef);
				if(result == null)
					result = caseExpression(literalUndef);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_DEFAULT: {
				LiteralDefault literalDefault = (LiteralDefault) theEObject;
				T result = caseLiteralDefault(literalDefault);
				if(result == null)
					result = caseLiteralExpression(literalDefault);
				if(result == null)
					result = caseExpression(literalDefault);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_REGEX: {
				LiteralRegex literalRegex = (LiteralRegex) theEObject;
				T result = caseLiteralRegex(literalRegex);
				if(result == null)
					result = caseLiteralExpression(literalRegex);
				if(result == null)
					result = caseExpression(literalRegex);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.LITERAL_NAME: {
				LiteralName literalName = (LiteralName) theEObject;
				T result = caseLiteralName(literalName);
				if(result == null)
					result = caseLiteralExpression(literalName);
				if(result == null)
					result = caseExpression(literalName);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.VARIABLE_EXPRESSION: {
				VariableExpression variableExpression = (VariableExpression) theEObject;
				T result = caseVariableExpression(variableExpression);
				if(result == null)
					result = caseExpression(variableExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.RELATIONSHIP_EXPRESSION: {
				RelationshipExpression relationshipExpression = (RelationshipExpression) theEObject;
				T result = caseRelationshipExpression(relationshipExpression);
				if(result == null)
					result = caseBinaryOpExpression(relationshipExpression);
				if(result == null)
					result = caseBinaryExpression(relationshipExpression);
				if(result == null)
					result = caseExpression(relationshipExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ASSIGNMENT_EXPRESSION: {
				AssignmentExpression assignmentExpression = (AssignmentExpression) theEObject;
				T result = caseAssignmentExpression(assignmentExpression);
				if(result == null)
					result = caseBinaryExpression(assignmentExpression);
				if(result == null)
					result = caseExpression(assignmentExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.APPEND_EXPRESSION: {
				AppendExpression appendExpression = (AppendExpression) theEObject;
				T result = caseAppendExpression(appendExpression);
				if(result == null)
					result = caseBinaryExpression(appendExpression);
				if(result == null)
					result = caseExpression(appendExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.OR_EXPRESSION: {
				OrExpression orExpression = (OrExpression) theEObject;
				T result = caseOrExpression(orExpression);
				if(result == null)
					result = caseBinaryExpression(orExpression);
				if(result == null)
					result = caseExpression(orExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.AND_EXPRESSION: {
				AndExpression andExpression = (AndExpression) theEObject;
				T result = caseAndExpression(andExpression);
				if(result == null)
					result = caseBinaryExpression(andExpression);
				if(result == null)
					result = caseExpression(andExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.RELATIONAL_EXPRESSION: {
				RelationalExpression relationalExpression = (RelationalExpression) theEObject;
				T result = caseRelationalExpression(relationalExpression);
				if(result == null)
					result = caseBinaryOpExpression(relationalExpression);
				if(result == null)
					result = caseBinaryExpression(relationalExpression);
				if(result == null)
					result = caseExpression(relationalExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EQUALITY_EXPRESSION: {
				EqualityExpression equalityExpression = (EqualityExpression) theEObject;
				T result = caseEqualityExpression(equalityExpression);
				if(result == null)
					result = caseBinaryOpExpression(equalityExpression);
				if(result == null)
					result = caseBinaryExpression(equalityExpression);
				if(result == null)
					result = caseExpression(equalityExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.SHIFT_EXPRESSION: {
				ShiftExpression shiftExpression = (ShiftExpression) theEObject;
				T result = caseShiftExpression(shiftExpression);
				if(result == null)
					result = caseBinaryOpExpression(shiftExpression);
				if(result == null)
					result = caseBinaryExpression(shiftExpression);
				if(result == null)
					result = caseExpression(shiftExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ADDITIVE_EXPRESSION: {
				AdditiveExpression additiveExpression = (AdditiveExpression) theEObject;
				T result = caseAdditiveExpression(additiveExpression);
				if(result == null)
					result = caseBinaryOpExpression(additiveExpression);
				if(result == null)
					result = caseBinaryExpression(additiveExpression);
				if(result == null)
					result = caseExpression(additiveExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.MULTIPLICATIVE_EXPRESSION: {
				MultiplicativeExpression multiplicativeExpression = (MultiplicativeExpression) theEObject;
				T result = caseMultiplicativeExpression(multiplicativeExpression);
				if(result == null)
					result = caseBinaryOpExpression(multiplicativeExpression);
				if(result == null)
					result = caseBinaryExpression(multiplicativeExpression);
				if(result == null)
					result = caseExpression(multiplicativeExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.MATCHING_EXPRESSION: {
				MatchingExpression matchingExpression = (MatchingExpression) theEObject;
				T result = caseMatchingExpression(matchingExpression);
				if(result == null)
					result = caseBinaryOpExpression(matchingExpression);
				if(result == null)
					result = caseBinaryExpression(matchingExpression);
				if(result == null)
					result = caseExpression(matchingExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.IN_EXPRESSION: {
				InExpression inExpression = (InExpression) theEObject;
				T result = caseInExpression(inExpression);
				if(result == null)
					result = caseBinaryOpExpression(inExpression);
				if(result == null)
					result = caseBinaryExpression(inExpression);
				if(result == null)
					result = caseExpression(inExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.AT_EXPRESSION: {
				AtExpression atExpression = (AtExpression) theEObject;
				T result = caseAtExpression(atExpression);
				if(result == null)
					result = caseParameterizedExpression(atExpression);
				if(result == null)
					result = caseExpression(atExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.COLLECT_EXPRESSION: {
				CollectExpression collectExpression = (CollectExpression) theEObject;
				T result = caseCollectExpression(collectExpression);
				if(result == null)
					result = caseExpression(collectExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.SELECTOR_EXPRESSION: {
				SelectorExpression selectorExpression = (SelectorExpression) theEObject;
				T result = caseSelectorExpression(selectorExpression);
				if(result == null)
					result = caseParameterizedExpression(selectorExpression);
				if(result == null)
					result = caseExpression(selectorExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.SELECTOR_ENTRY: {
				SelectorEntry selectorEntry = (SelectorEntry) theEObject;
				T result = caseSelectorEntry(selectorEntry);
				if(result == null)
					result = caseBinaryExpression(selectorEntry);
				if(result == null)
					result = caseExpression(selectorEntry);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.FUNCTION_CALL: {
				FunctionCall functionCall = (FunctionCall) theEObject;
				T result = caseFunctionCall(functionCall);
				if(result == null)
					result = caseParameterizedExpression(functionCall);
				if(result == null)
					result = caseExpression(functionCall);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.BINARY_OP_EXPRESSION: {
				BinaryOpExpression binaryOpExpression = (BinaryOpExpression) theEObject;
				T result = caseBinaryOpExpression(binaryOpExpression);
				if(result == null)
					result = caseBinaryExpression(binaryOpExpression);
				if(result == null)
					result = caseExpression(binaryOpExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.BINARY_EXPRESSION: {
				BinaryExpression binaryExpression = (BinaryExpression) theEObject;
				T result = caseBinaryExpression(binaryExpression);
				if(result == null)
					result = caseExpression(binaryExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.PARAMETERIZED_EXPRESSION: {
				ParameterizedExpression parameterizedExpression = (ParameterizedExpression) theEObject;
				T result = caseParameterizedExpression(parameterizedExpression);
				if(result == null)
					result = caseExpression(parameterizedExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.NODE_DEFINITION: {
				NodeDefinition nodeDefinition = (NodeDefinition) theEObject;
				T result = caseNodeDefinition(nodeDefinition);
				if(result == null)
					result = caseExpression(nodeDefinition);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.UNARY_EXPRESSION: {
				UnaryExpression unaryExpression = (UnaryExpression) theEObject;
				T result = caseUnaryExpression(unaryExpression);
				if(result == null)
					result = caseExpression(unaryExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.UNARY_MINUS_EXPRESSION: {
				UnaryMinusExpression unaryMinusExpression = (UnaryMinusExpression) theEObject;
				T result = caseUnaryMinusExpression(unaryMinusExpression);
				if(result == null)
					result = caseUnaryExpression(unaryMinusExpression);
				if(result == null)
					result = caseExpression(unaryMinusExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.UNARY_NOT_EXPRESSION: {
				UnaryNotExpression unaryNotExpression = (UnaryNotExpression) theEObject;
				T result = caseUnaryNotExpression(unaryNotExpression);
				if(result == null)
					result = caseUnaryExpression(unaryNotExpression);
				if(result == null)
					result = caseExpression(unaryNotExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EXPRESSION_BLOCK: {
				ExpressionBlock expressionBlock = (ExpressionBlock) theEObject;
				T result = caseExpressionBlock(expressionBlock);
				if(result == null)
					result = caseExpression(expressionBlock);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ELSE_EXPRESSION: {
				ElseExpression elseExpression = (ElseExpression) theEObject;
				T result = caseElseExpression(elseExpression);
				if(result == null)
					result = caseExpressionBlock(elseExpression);
				if(result == null)
					result = caseExpression(elseExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.ELSE_IF_EXPRESSION: {
				ElseIfExpression elseIfExpression = (ElseIfExpression) theEObject;
				T result = caseElseIfExpression(elseIfExpression);
				if(result == null)
					result = caseIfExpression(elseIfExpression);
				if(result == null)
					result = caseExpression(elseIfExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE: {
				VirtualNameOrReference virtualNameOrReference = (VirtualNameOrReference) theEObject;
				T result = caseVirtualNameOrReference(virtualNameOrReference);
				if(result == null)
					result = caseLiteralExpression(virtualNameOrReference);
				if(result == null)
					result = caseExpression(virtualNameOrReference);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.PARENTHESISED_EXPRESSION: {
				ParenthesisedExpression parenthesisedExpression = (ParenthesisedExpression) theEObject;
				T result = caseParenthesisedExpression(parenthesisedExpression);
				if(result == null)
					result = caseExpression(parenthesisedExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EXPR_LIST: {
				ExprList exprList = (ExprList) theEObject;
				T result = caseExprList(exprList);
				if(result == null)
					result = caseExpression(exprList);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.DOUBLE_QUOTED_STRING: {
				DoubleQuotedString doubleQuotedString = (DoubleQuotedString) theEObject;
				T result = caseDoubleQuotedString(doubleQuotedString);
				if(result == null)
					result = caseStringExpression(doubleQuotedString);
				if(result == null)
					result = caseIQuotedString(doubleQuotedString);
				if(result == null)
					result = caseExpression(doubleQuotedString);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.SINGLE_QUOTED_STRING: {
				SingleQuotedString singleQuotedString = (SingleQuotedString) theEObject;
				T result = caseSingleQuotedString(singleQuotedString);
				if(result == null)
					result = caseStringExpression(singleQuotedString);
				if(result == null)
					result = caseIQuotedString(singleQuotedString);
				if(result == null)
					result = caseExpression(singleQuotedString);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.STRING_EXPRESSION: {
				StringExpression stringExpression = (StringExpression) theEObject;
				T result = caseStringExpression(stringExpression);
				if(result == null)
					result = caseExpression(stringExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.UNQUOTED_STRING: {
				UnquotedString unquotedString = (UnquotedString) theEObject;
				T result = caseUnquotedString(unquotedString);
				if(result == null)
					result = caseStringExpression(unquotedString);
				if(result == null)
					result = caseExpression(unquotedString);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.IQUOTED_STRING: {
				IQuotedString iQuotedString = (IQuotedString) theEObject;
				T result = caseIQuotedString(iQuotedString);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.INTERPOLATED_VARIABLE: {
				InterpolatedVariable interpolatedVariable = (InterpolatedVariable) theEObject;
				T result = caseInterpolatedVariable(interpolatedVariable);
				if(result == null)
					result = caseExpression(interpolatedVariable);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.TEXT_EXPRESSION: {
				TextExpression textExpression = (TextExpression) theEObject;
				T result = caseTextExpression(textExpression);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.VERBATIM_TE: {
				VerbatimTE verbatimTE = (VerbatimTE) theEObject;
				T result = caseVerbatimTE(verbatimTE);
				if(result == null)
					result = caseTextExpression(verbatimTE);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.EXPRESSION_TE: {
				ExpressionTE expressionTE = (ExpressionTE) theEObject;
				T result = caseExpressionTE(expressionTE);
				if(result == null)
					result = caseTextExpression(expressionTE);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPPackage.VARIABLE_TE: {
				VariableTE variableTE = (VariableTE) theEObject;
				T result = caseVariableTE(variableTE);
				if(result == null)
					result = caseTextExpression(variableTE);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

} // PPSwitch
