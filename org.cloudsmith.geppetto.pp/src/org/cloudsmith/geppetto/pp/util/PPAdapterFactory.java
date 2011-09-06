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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage
 * @generated
 */
public class PPAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static PPPackage modelPackage;

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PPSwitch<Adapter> modelSwitch = new PPSwitch<Adapter>() {
		@Override
		public Adapter caseAdditiveExpression(AdditiveExpression object) {
			return createAdditiveExpressionAdapter();
		}

		@Override
		public Adapter caseAndExpression(AndExpression object) {
			return createAndExpressionAdapter();
		}

		@Override
		public Adapter caseAppendExpression(AppendExpression object) {
			return createAppendExpressionAdapter();
		}

		@Override
		public Adapter caseAssignmentExpression(AssignmentExpression object) {
			return createAssignmentExpressionAdapter();
		}

		@Override
		public Adapter caseAtExpression(AtExpression object) {
			return createAtExpressionAdapter();
		}

		@Override
		public Adapter caseAttributeOperation(AttributeOperation object) {
			return createAttributeOperationAdapter();
		}

		@Override
		public Adapter caseAttributeOperations(AttributeOperations object) {
			return createAttributeOperationsAdapter();
		}

		@Override
		public Adapter caseBinaryExpression(BinaryExpression object) {
			return createBinaryExpressionAdapter();
		}

		@Override
		public Adapter caseBinaryOpExpression(BinaryOpExpression object) {
			return createBinaryOpExpressionAdapter();
		}

		@Override
		public Adapter caseCase(Case object) {
			return createCaseAdapter();
		}

		@Override
		public Adapter caseCaseExpression(CaseExpression object) {
			return createCaseExpressionAdapter();
		}

		@Override
		public Adapter caseCollectExpression(CollectExpression object) {
			return createCollectExpressionAdapter();
		}

		@Override
		public Adapter caseDefinition(Definition object) {
			return createDefinitionAdapter();
		}

		@Override
		public Adapter caseDefinitionArgument(DefinitionArgument object) {
			return createDefinitionArgumentAdapter();
		}

		@Override
		public Adapter caseDefinitionArgumentList(DefinitionArgumentList object) {
			return createDefinitionArgumentListAdapter();
		}

		@Override
		public Adapter caseDoubleQuotedString(DoubleQuotedString object) {
			return createDoubleQuotedStringAdapter();
		}

		@Override
		public Adapter caseElseExpression(ElseExpression object) {
			return createElseExpressionAdapter();
		}

		@Override
		public Adapter caseElseIfExpression(ElseIfExpression object) {
			return createElseIfExpressionAdapter();
		}

		@Override
		public Adapter caseEqualityExpression(EqualityExpression object) {
			return createEqualityExpressionAdapter();
		}

		@Override
		public Adapter caseExportedCollectQuery(ExportedCollectQuery object) {
			return createExportedCollectQueryAdapter();
		}

		@Override
		public Adapter caseExpression(Expression object) {
			return createExpressionAdapter();
		}

		@Override
		public Adapter caseExpressionBlock(ExpressionBlock object) {
			return createExpressionBlockAdapter();
		}

		@Override
		public Adapter caseExpressionTE(ExpressionTE object) {
			return createExpressionTEAdapter();
		}

		@Override
		public Adapter caseExprList(ExprList object) {
			return createExprListAdapter();
		}

		@Override
		public Adapter caseFunctionCall(FunctionCall object) {
			return createFunctionCallAdapter();
		}

		@Override
		public Adapter caseHashEntry(HashEntry object) {
			return createHashEntryAdapter();
		}

		@Override
		public Adapter caseHostClassDefinition(HostClassDefinition object) {
			return createHostClassDefinitionAdapter();
		}

		@Override
		public Adapter caseICollectQuery(ICollectQuery object) {
			return createICollectQueryAdapter();
		}

		@Override
		public Adapter caseIfExpression(IfExpression object) {
			return createIfExpressionAdapter();
		}

		@Override
		public Adapter caseImportExpression(ImportExpression object) {
			return createImportExpressionAdapter();
		}

		@Override
		public Adapter caseInExpression(InExpression object) {
			return createInExpressionAdapter();
		}

		@Override
		public Adapter caseInterpolatedVariable(InterpolatedVariable object) {
			return createInterpolatedVariableAdapter();
		}

		@Override
		public Adapter caseIQuotedString(IQuotedString object) {
			return createIQuotedStringAdapter();
		}

		@Override
		public Adapter caseLiteralBoolean(LiteralBoolean object) {
			return createLiteralBooleanAdapter();
		}

		@Override
		public Adapter caseLiteralDefault(LiteralDefault object) {
			return createLiteralDefaultAdapter();
		}

		@Override
		public Adapter caseLiteralExpression(LiteralExpression object) {
			return createLiteralExpressionAdapter();
		}

		@Override
		public Adapter caseLiteralHash(LiteralHash object) {
			return createLiteralHashAdapter();
		}

		@Override
		public Adapter caseLiteralList(LiteralList object) {
			return createLiteralListAdapter();
		}

		@Override
		public Adapter caseLiteralName(LiteralName object) {
			return createLiteralNameAdapter();
		}

		@Override
		public Adapter caseLiteralNameOrReference(LiteralNameOrReference object) {
			return createLiteralNameOrReferenceAdapter();
		}

		@Override
		public Adapter caseLiteralRegex(LiteralRegex object) {
			return createLiteralRegexAdapter();
		}

		@Override
		public Adapter caseLiteralUndef(LiteralUndef object) {
			return createLiteralUndefAdapter();
		}

		@Override
		public Adapter caseMatchingExpression(MatchingExpression object) {
			return createMatchingExpressionAdapter();
		}

		@Override
		public Adapter caseMultiplicativeExpression(MultiplicativeExpression object) {
			return createMultiplicativeExpressionAdapter();
		}

		@Override
		public Adapter caseNodeDefinition(NodeDefinition object) {
			return createNodeDefinitionAdapter();
		}

		@Override
		public Adapter caseOrExpression(OrExpression object) {
			return createOrExpressionAdapter();
		}

		@Override
		public Adapter caseParameterizedExpression(ParameterizedExpression object) {
			return createParameterizedExpressionAdapter();
		}

		@Override
		public Adapter caseParenthesisedExpression(ParenthesisedExpression object) {
			return createParenthesisedExpressionAdapter();
		}

		@Override
		public Adapter casePuppetManifest(PuppetManifest object) {
			return createPuppetManifestAdapter();
		}

		@Override
		public Adapter caseRelationalExpression(RelationalExpression object) {
			return createRelationalExpressionAdapter();
		}

		@Override
		public Adapter caseRelationshipExpression(RelationshipExpression object) {
			return createRelationshipExpressionAdapter();
		}

		@Override
		public Adapter caseResourceBody(ResourceBody object) {
			return createResourceBodyAdapter();
		}

		@Override
		public Adapter caseResourceExpression(ResourceExpression object) {
			return createResourceExpressionAdapter();
		}

		@Override
		public Adapter caseSelectorEntry(SelectorEntry object) {
			return createSelectorEntryAdapter();
		}

		@Override
		public Adapter caseSelectorExpression(SelectorExpression object) {
			return createSelectorExpressionAdapter();
		}

		@Override
		public Adapter caseShiftExpression(ShiftExpression object) {
			return createShiftExpressionAdapter();
		}

		@Override
		public Adapter caseSingleQuotedString(SingleQuotedString object) {
			return createSingleQuotedStringAdapter();
		}

		@Override
		public Adapter caseStringExpression(StringExpression object) {
			return createStringExpressionAdapter();
		}

		@Override
		public Adapter caseTextExpression(TextExpression object) {
			return createTextExpressionAdapter();
		}

		@Override
		public Adapter caseUnaryExpression(UnaryExpression object) {
			return createUnaryExpressionAdapter();
		}

		@Override
		public Adapter caseUnaryMinusExpression(UnaryMinusExpression object) {
			return createUnaryMinusExpressionAdapter();
		}

		@Override
		public Adapter caseUnaryNotExpression(UnaryNotExpression object) {
			return createUnaryNotExpressionAdapter();
		}

		@Override
		public Adapter caseUnquotedString(UnquotedString object) {
			return createUnquotedStringAdapter();
		}

		@Override
		public Adapter caseVariableExpression(VariableExpression object) {
			return createVariableExpressionAdapter();
		}

		@Override
		public Adapter caseVariableTE(VariableTE object) {
			return createVariableTEAdapter();
		}

		@Override
		public Adapter caseVerbatimTE(VerbatimTE object) {
			return createVerbatimTEAdapter();
		}

		@Override
		public Adapter caseVirtualCollectQuery(VirtualCollectQuery object) {
			return createVirtualCollectQueryAdapter();
		}

		@Override
		public Adapter caseVirtualNameOrReference(VirtualNameOrReference object) {
			return createVirtualNameOrReferenceAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PPAdapterFactory() {
		if(modelPackage == null) {
			modelPackage = PPPackage.eINSTANCE;
		}
	}

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AdditiveExpression <em>Additive Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AdditiveExpression
	 * @generated
	 */
	public Adapter createAdditiveExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AndExpression <em>And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AndExpression
	 * @generated
	 */
	public Adapter createAndExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AppendExpression <em>Append Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AppendExpression
	 * @generated
	 */
	public Adapter createAppendExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AssignmentExpression <em>Assignment Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AssignmentExpression
	 * @generated
	 */
	public Adapter createAssignmentExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AtExpression <em>At Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AtExpression
	 * @generated
	 */
	public Adapter createAtExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AttributeOperation <em>Attribute Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperation
	 * @generated
	 */
	public Adapter createAttributeOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.AttributeOperations <em>Attribute Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperations
	 * @generated
	 */
	public Adapter createAttributeOperationsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.BinaryExpression
	 * @generated
	 */
	public Adapter createBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.BinaryOpExpression <em>Binary Op Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.BinaryOpExpression
	 * @generated
	 */
	public Adapter createBinaryOpExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.Case <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.Case
	 * @generated
	 */
	public Adapter createCaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.CaseExpression <em>Case Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.CaseExpression
	 * @generated
	 */
	public Adapter createCaseExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.CollectExpression <em>Collect Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.CollectExpression
	 * @generated
	 */
	public Adapter createCollectExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.Definition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.Definition
	 * @generated
	 */
	public Adapter createDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.DefinitionArgument <em>Definition Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgument
	 * @generated
	 */
	public Adapter createDefinitionArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.DefinitionArgumentList <em>Definition Argument List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgumentList
	 * @generated
	 */
	public Adapter createDefinitionArgumentListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.DoubleQuotedString <em>Double Quoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.DoubleQuotedString
	 * @generated
	 */
	public Adapter createDoubleQuotedStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ElseExpression <em>Else Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ElseExpression
	 * @generated
	 */
	public Adapter createElseExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ElseIfExpression <em>Else If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ElseIfExpression
	 * @generated
	 */
	public Adapter createElseIfExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.EqualityExpression <em>Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.EqualityExpression
	 * @generated
	 */
	public Adapter createEqualityExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ExportedCollectQuery <em>Exported Collect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ExportedCollectQuery
	 * @generated
	 */
	public Adapter createExportedCollectQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ExpressionBlock <em>Expression Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ExpressionBlock
	 * @generated
	 */
	public Adapter createExpressionBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ExpressionTE <em>Expression TE</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ExpressionTE
	 * @generated
	 */
	public Adapter createExpressionTEAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ExprList <em>Expr List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ExprList
	 * @generated
	 */
	public Adapter createExprListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.FunctionCall <em>Function Call</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.FunctionCall
	 * @generated
	 */
	public Adapter createFunctionCallAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.HashEntry <em>Hash Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.HashEntry
	 * @generated
	 */
	public Adapter createHashEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.HostClassDefinition <em>Host Class Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.HostClassDefinition
	 * @generated
	 */
	public Adapter createHostClassDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ICollectQuery <em>ICollect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ICollectQuery
	 * @generated
	 */
	public Adapter createICollectQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.IfExpression <em>If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.IfExpression
	 * @generated
	 */
	public Adapter createIfExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ImportExpression <em>Import Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ImportExpression
	 * @generated
	 */
	public Adapter createImportExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.InExpression <em>In Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.InExpression
	 * @generated
	 */
	public Adapter createInExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.InterpolatedVariable <em>Interpolated Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.InterpolatedVariable
	 * @generated
	 */
	public Adapter createInterpolatedVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.IQuotedString <em>IQuoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.IQuotedString
	 * @generated
	 */
	public Adapter createIQuotedStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralBoolean <em>Literal Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralBoolean
	 * @generated
	 */
	public Adapter createLiteralBooleanAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralDefault <em>Literal Default</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralDefault
	 * @generated
	 */
	public Adapter createLiteralDefaultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralExpression <em>Literal Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralExpression
	 * @generated
	 */
	public Adapter createLiteralExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralHash <em>Literal Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralHash
	 * @generated
	 */
	public Adapter createLiteralHashAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralList <em>Literal List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralList
	 * @generated
	 */
	public Adapter createLiteralListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralName <em>Literal Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralName
	 * @generated
	 */
	public Adapter createLiteralNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralNameOrReference <em>Literal Name Or Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralNameOrReference
	 * @generated
	 */
	public Adapter createLiteralNameOrReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralRegex <em>Literal Regex</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralRegex
	 * @generated
	 */
	public Adapter createLiteralRegexAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.LiteralUndef <em>Literal Undef</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.LiteralUndef
	 * @generated
	 */
	public Adapter createLiteralUndefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.MatchingExpression <em>Matching Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.MatchingExpression
	 * @generated
	 */
	public Adapter createMatchingExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.MultiplicativeExpression <em>Multiplicative Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.MultiplicativeExpression
	 * @generated
	 */
	public Adapter createMultiplicativeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.NodeDefinition <em>Node Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.NodeDefinition
	 * @generated
	 */
	public Adapter createNodeDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.OrExpression <em>Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.OrExpression
	 * @generated
	 */
	public Adapter createOrExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ParameterizedExpression <em>Parameterized Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ParameterizedExpression
	 * @generated
	 */
	public Adapter createParameterizedExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ParenthesisedExpression <em>Parenthesised Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ParenthesisedExpression
	 * @generated
	 */
	public Adapter createParenthesisedExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.PuppetManifest <em>Puppet Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.PuppetManifest
	 * @generated
	 */
	public Adapter createPuppetManifestAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.RelationalExpression <em>Relational Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.RelationalExpression
	 * @generated
	 */
	public Adapter createRelationalExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.RelationshipExpression <em>Relationship Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.RelationshipExpression
	 * @generated
	 */
	public Adapter createRelationshipExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ResourceBody <em>Resource Body</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ResourceBody
	 * @generated
	 */
	public Adapter createResourceBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ResourceExpression <em>Resource Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ResourceExpression
	 * @generated
	 */
	public Adapter createResourceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.SelectorEntry <em>Selector Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.SelectorEntry
	 * @generated
	 */
	public Adapter createSelectorEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.SelectorExpression <em>Selector Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.SelectorExpression
	 * @generated
	 */
	public Adapter createSelectorExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.ShiftExpression <em>Shift Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.ShiftExpression
	 * @generated
	 */
	public Adapter createShiftExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.SingleQuotedString <em>Single Quoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.SingleQuotedString
	 * @generated
	 */
	public Adapter createSingleQuotedStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.StringExpression <em>String Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.StringExpression
	 * @generated
	 */
	public Adapter createStringExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.TextExpression <em>Text Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.TextExpression
	 * @generated
	 */
	public Adapter createTextExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.UnaryExpression
	 * @generated
	 */
	public Adapter createUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.UnaryMinusExpression <em>Unary Minus Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.UnaryMinusExpression
	 * @generated
	 */
	public Adapter createUnaryMinusExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.UnaryNotExpression <em>Unary Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.UnaryNotExpression
	 * @generated
	 */
	public Adapter createUnaryNotExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.UnquotedString <em>Unquoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.UnquotedString
	 * @generated
	 */
	public Adapter createUnquotedStringAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.VariableExpression <em>Variable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.VariableExpression
	 * @generated
	 */
	public Adapter createVariableExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.VariableTE <em>Variable TE</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.VariableTE
	 * @generated
	 */
	public Adapter createVariableTEAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.VerbatimTE <em>Verbatim TE</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.VerbatimTE
	 * @generated
	 */
	public Adapter createVerbatimTEAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.VirtualCollectQuery <em>Virtual Collect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.VirtualCollectQuery
	 * @generated
	 */
	public Adapter createVirtualCollectQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.pp.VirtualNameOrReference <em>Virtual Name Or Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.pp.VirtualNameOrReference
	 * @generated
	 */
	public Adapter createVirtualNameOrReferenceAdapter() {
		return null;
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if(object == modelPackage) {
			return true;
		}
		if(object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

} // PPAdapterFactory
