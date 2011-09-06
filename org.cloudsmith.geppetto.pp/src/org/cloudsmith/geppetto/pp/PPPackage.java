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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.pp.PPFactory
 * @model kind="package"
 * @generated
 */
public interface PPPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.PuppetManifestImpl <em>Puppet Manifest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.PuppetManifestImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getPuppetManifest()
		 * @generated
		 */
		EClass PUPPET_MANIFEST = eINSTANCE.getPuppetManifest();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl <em>Resource Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getResourceBody()
		 * @generated
		 */
		EClass RESOURCE_BODY = eINSTANCE.getResourceBody();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESOURCE_BODY__ATTRIBUTES = eINSTANCE.getResourceBody_Attributes();

		/**
		 * The meta object literal for the '<em><b>Name Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESOURCE_BODY__NAME_EXPR = eINSTANCE.getResourceBody_NameExpr();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AttributeOperationImpl <em>Attribute Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AttributeOperationImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAttributeOperation()
		 * @generated
		 */
		EClass ATTRIBUTE_OPERATION = eINSTANCE.getAttributeOperation();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ATTRIBUTE_OPERATION__VALUE = eINSTANCE.getAttributeOperation_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ATTRIBUTE_OPERATION__KEY = eINSTANCE.getAttributeOperation_Key();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ATTRIBUTE_OPERATION__OP = eINSTANCE.getAttributeOperation_Op();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AttributeOperationsImpl <em>Attribute Operations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AttributeOperationsImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAttributeOperations()
		 * @generated
		 */
		EClass ATTRIBUTE_OPERATIONS = eINSTANCE.getAttributeOperations();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ATTRIBUTE_OPERATIONS__ATTRIBUTES = eINSTANCE.getAttributeOperations_Attributes();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.ICollectQuery <em>ICollect Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.ICollectQuery
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getICollectQuery()
		 * @generated
		 */
		EClass ICOLLECT_QUERY = eINSTANCE.getICollectQuery();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.VirtualCollectQueryImpl <em>Virtual Collect Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.VirtualCollectQueryImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVirtualCollectQuery()
		 * @generated
		 */
		EClass VIRTUAL_COLLECT_QUERY = eINSTANCE.getVirtualCollectQuery();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ExportedCollectQueryImpl <em>Exported Collect Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ExportedCollectQueryImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExportedCollectQuery()
		 * @generated
		 */
		EClass EXPORTED_COLLECT_QUERY = eINSTANCE.getExportedCollectQuery();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.HostClassDefinitionImpl <em>Host Class Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.HostClassDefinitionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getHostClassDefinition()
		 * @generated
		 */
		EClass HOST_CLASS_DEFINITION = eINSTANCE.getHostClassDefinition();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HOST_CLASS_DEFINITION__PARENT = eINSTANCE.getHostClassDefinition_Parent();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionImpl <em>Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.DefinitionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinition()
		 * @generated
		 */
		EClass DEFINITION = eINSTANCE.getDefinition();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEFINITION__CLASS_NAME = eINSTANCE.getDefinition_ClassName();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEFINITION__ARGUMENTS = eINSTANCE.getDefinition_Arguments();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEFINITION__STATEMENTS = eINSTANCE.getDefinition_Statements();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentListImpl <em>Definition Argument List</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.DefinitionArgumentListImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinitionArgumentList()
		 * @generated
		 */
		EClass DEFINITION_ARGUMENT_LIST = eINSTANCE.getDefinitionArgumentList();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEFINITION_ARGUMENT_LIST__ARGUMENTS = eINSTANCE.getDefinitionArgumentList_Arguments();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl <em>Definition Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinitionArgument()
		 * @generated
		 */
		EClass DEFINITION_ARGUMENT = eINSTANCE.getDefinitionArgument();

		/**
		 * The meta object literal for the '<em><b>Arg Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEFINITION_ARGUMENT__ARG_NAME = eINSTANCE.getDefinitionArgument_ArgName();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEFINITION_ARGUMENT__VALUE = eINSTANCE.getDefinitionArgument_Value();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEFINITION_ARGUMENT__OP = eINSTANCE.getDefinitionArgument_Op();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl <em>Case Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCaseExpression()
		 * @generated
		 */
		EClass CASE_EXPRESSION = eINSTANCE.getCaseExpression();

		/**
		 * The meta object literal for the '<em><b>Switch Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CASE_EXPRESSION__SWITCH_EXPR = eINSTANCE.getCaseExpression_SwitchExpr();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CASE_EXPRESSION__CASES = eINSTANCE.getCaseExpression_Cases();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.CaseImpl <em>Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.CaseImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCase()
		 * @generated
		 */
		EClass CASE = eINSTANCE.getCase();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CASE__STATEMENTS = eINSTANCE.getCase_Statements();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CASE__VALUES = eINSTANCE.getCase_Values();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.IfExpressionImpl <em>If Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.IfExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getIfExpression()
		 * @generated
		 */
		EClass IF_EXPRESSION = eINSTANCE.getIfExpression();

		/**
		 * The meta object literal for the '<em><b>Cond Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_EXPRESSION__COND_EXPR = eINSTANCE.getIfExpression_CondExpr();

		/**
		 * The meta object literal for the '<em><b>Then Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_EXPRESSION__THEN_STATEMENTS = eINSTANCE.getIfExpression_ThenStatements();

		/**
		 * The meta object literal for the '<em><b>Else Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_EXPRESSION__ELSE_STATEMENT = eINSTANCE.getIfExpression_ElseStatement();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralExpressionImpl <em>Literal Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralExpression()
		 * @generated
		 */
		EClass LITERAL_EXPRESSION = eINSTANCE.getLiteralExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralNameOrReferenceImpl <em>Literal Name Or Reference</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralNameOrReferenceImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralNameOrReference()
		 * @generated
		 */
		EClass LITERAL_NAME_OR_REFERENCE = eINSTANCE.getLiteralNameOrReference();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LITERAL_NAME_OR_REFERENCE__VALUE = eINSTANCE.getLiteralNameOrReference_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl <em>Resource Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getResourceExpression()
		 * @generated
		 */
		EClass RESOURCE_EXPRESSION = eINSTANCE.getResourceExpression();

		/**
		 * The meta object literal for the '<em><b>Resource Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESOURCE_EXPRESSION__RESOURCE_EXPR = eINSTANCE.getResourceExpression_ResourceExpr();

		/**
		 * The meta object literal for the '<em><b>Resource Data</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESOURCE_EXPRESSION__RESOURCE_DATA = eINSTANCE.getResourceExpression_ResourceData();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ImportExpressionImpl <em>Import Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ImportExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getImportExpression()
		 * @generated
		 */
		EClass IMPORT_EXPRESSION = eINSTANCE.getImportExpression();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IMPORT_EXPRESSION__VALUES = eINSTANCE.getImportExpression_Values();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralListImpl <em>Literal List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralListImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralList()
		 * @generated
		 */
		EClass LITERAL_LIST = eINSTANCE.getLiteralList();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LITERAL_LIST__ELEMENTS = eINSTANCE.getLiteralList_Elements();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralHashImpl <em>Literal Hash</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralHashImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralHash()
		 * @generated
		 */
		EClass LITERAL_HASH = eINSTANCE.getLiteralHash();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LITERAL_HASH__ELEMENTS = eINSTANCE.getLiteralHash_Elements();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.HashEntryImpl <em>Hash Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.HashEntryImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getHashEntry()
		 * @generated
		 */
		EClass HASH_ENTRY = eINSTANCE.getHashEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HASH_ENTRY__KEY = eINSTANCE.getHashEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HASH_ENTRY__VALUE = eINSTANCE.getHashEntry_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralBooleanImpl <em>Literal Boolean</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralBooleanImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralBoolean()
		 * @generated
		 */
		EClass LITERAL_BOOLEAN = eINSTANCE.getLiteralBoolean();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LITERAL_BOOLEAN__VALUE = eINSTANCE.getLiteralBoolean_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralUndefImpl <em>Literal Undef</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralUndefImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralUndef()
		 * @generated
		 */
		EClass LITERAL_UNDEF = eINSTANCE.getLiteralUndef();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralDefaultImpl <em>Literal Default</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralDefaultImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralDefault()
		 * @generated
		 */
		EClass LITERAL_DEFAULT = eINSTANCE.getLiteralDefault();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralRegexImpl <em>Literal Regex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralRegexImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralRegex()
		 * @generated
		 */
		EClass LITERAL_REGEX = eINSTANCE.getLiteralRegex();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LITERAL_REGEX__VALUE = eINSTANCE.getLiteralRegex_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralNameImpl <em>Literal Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.LiteralNameImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralName()
		 * @generated
		 */
		EClass LITERAL_NAME = eINSTANCE.getLiteralName();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LITERAL_NAME__VALUE = eINSTANCE.getLiteralName_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.VariableExpressionImpl <em>Variable Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.VariableExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVariableExpression()
		 * @generated
		 */
		EClass VARIABLE_EXPRESSION = eINSTANCE.getVariableExpression();

		/**
		 * The meta object literal for the '<em><b>Var Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_EXPRESSION__VAR_NAME = eINSTANCE.getVariableExpression_VarName();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.RelationshipExpressionImpl <em>Relationship Expression</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.RelationshipExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getRelationshipExpression()
		 * @generated
		 */
		EClass RELATIONSHIP_EXPRESSION = eINSTANCE.getRelationshipExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AssignmentExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAssignmentExpression()
		 * @generated
		 */
		EClass ASSIGNMENT_EXPRESSION = eINSTANCE.getAssignmentExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AppendExpressionImpl <em>Append Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AppendExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAppendExpression()
		 * @generated
		 */
		EClass APPEND_EXPRESSION = eINSTANCE.getAppendExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.OrExpressionImpl <em>Or Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.OrExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getOrExpression()
		 * @generated
		 */
		EClass OR_EXPRESSION = eINSTANCE.getOrExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AndExpressionImpl <em>And Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AndExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAndExpression()
		 * @generated
		 */
		EClass AND_EXPRESSION = eINSTANCE.getAndExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.RelationalExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getRelationalExpression()
		 * @generated
		 */
		EClass RELATIONAL_EXPRESSION = eINSTANCE.getRelationalExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.EqualityExpressionImpl <em>Equality Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.EqualityExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getEqualityExpression()
		 * @generated
		 */
		EClass EQUALITY_EXPRESSION = eINSTANCE.getEqualityExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ShiftExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getShiftExpression()
		 * @generated
		 */
		EClass SHIFT_EXPRESSION = eINSTANCE.getShiftExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AdditiveExpressionImpl <em>Additive Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AdditiveExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAdditiveExpression()
		 * @generated
		 */
		EClass ADDITIVE_EXPRESSION = eINSTANCE.getAdditiveExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.MultiplicativeExpressionImpl <em>Multiplicative Expression</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.MultiplicativeExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getMultiplicativeExpression()
		 * @generated
		 */
		EClass MULTIPLICATIVE_EXPRESSION = eINSTANCE.getMultiplicativeExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.MatchingExpressionImpl <em>Matching Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.MatchingExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getMatchingExpression()
		 * @generated
		 */
		EClass MATCHING_EXPRESSION = eINSTANCE.getMatchingExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.InExpressionImpl <em>In Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.InExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getInExpression()
		 * @generated
		 */
		EClass IN_EXPRESSION = eINSTANCE.getInExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.AtExpressionImpl <em>At Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.AtExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAtExpression()
		 * @generated
		 */
		EClass AT_EXPRESSION = eINSTANCE.getAtExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.CollectExpressionImpl <em>Collect Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.CollectExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCollectExpression()
		 * @generated
		 */
		EClass COLLECT_EXPRESSION = eINSTANCE.getCollectExpression();

		/**
		 * The meta object literal for the '<em><b>Class Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLLECT_EXPRESSION__CLASS_REFERENCE = eINSTANCE.getCollectExpression_ClassReference();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLLECT_EXPRESSION__QUERY = eINSTANCE.getCollectExpression_Query();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLLECT_EXPRESSION__ATTRIBUTES = eINSTANCE.getCollectExpression_Attributes();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.SelectorExpressionImpl <em>Selector Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.SelectorExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSelectorExpression()
		 * @generated
		 */
		EClass SELECTOR_EXPRESSION = eINSTANCE.getSelectorExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.SelectorEntryImpl <em>Selector Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.SelectorEntryImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSelectorEntry()
		 * @generated
		 */
		EClass SELECTOR_ENTRY = eINSTANCE.getSelectorEntry();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.FunctionCallImpl <em>Function Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.FunctionCallImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getFunctionCall()
		 * @generated
		 */
		EClass FUNCTION_CALL = eINSTANCE.getFunctionCall();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.BinaryOpExpressionImpl <em>Binary Op Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.BinaryOpExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getBinaryOpExpression()
		 * @generated
		 */
		EClass BINARY_OP_EXPRESSION = eINSTANCE.getBinaryOpExpression();

		/**
		 * The meta object literal for the '<em><b>Op Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BINARY_OP_EXPRESSION__OP_NAME = eINSTANCE.getBinaryOpExpression_OpName();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.BinaryExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getBinaryExpression()
		 * @generated
		 */
		EClass BINARY_EXPRESSION = eINSTANCE.getBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Left Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINARY_EXPRESSION__LEFT_EXPR = eINSTANCE.getBinaryExpression_LeftExpr();

		/**
		 * The meta object literal for the '<em><b>Right Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINARY_EXPRESSION__RIGHT_EXPR = eINSTANCE.getBinaryExpression_RightExpr();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl <em>Parameterized Expression</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getParameterizedExpression()
		 * @generated
		 */
		EClass PARAMETERIZED_EXPRESSION = eINSTANCE.getParameterizedExpression();

		/**
		 * The meta object literal for the '<em><b>Left Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PARAMETERIZED_EXPRESSION__LEFT_EXPR = eINSTANCE.getParameterizedExpression_LeftExpr();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PARAMETERIZED_EXPRESSION__PARAMETERS = eINSTANCE.getParameterizedExpression_Parameters();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl <em>Node Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getNodeDefinition()
		 * @generated
		 */
		EClass NODE_DEFINITION = eINSTANCE.getNodeDefinition();

		/**
		 * The meta object literal for the '<em><b>Host Names</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NODE_DEFINITION__HOST_NAMES = eINSTANCE.getNodeDefinition_HostNames();

		/**
		 * The meta object literal for the '<em><b>Parent Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NODE_DEFINITION__PARENT_NAME = eINSTANCE.getNodeDefinition_ParentName();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NODE_DEFINITION__STATEMENTS = eINSTANCE.getNodeDefinition_Statements();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.UnaryExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference UNARY_EXPRESSION__EXPR = eINSTANCE.getUnaryExpression_Expr();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryMinusExpressionImpl <em>Unary Minus Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.UnaryMinusExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryMinusExpression()
		 * @generated
		 */
		EClass UNARY_MINUS_EXPRESSION = eINSTANCE.getUnaryMinusExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryNotExpressionImpl <em>Unary Not Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.UnaryNotExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryNotExpression()
		 * @generated
		 */
		EClass UNARY_NOT_EXPRESSION = eINSTANCE.getUnaryNotExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionBlockImpl <em>Expression Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ExpressionBlockImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpressionBlock()
		 * @generated
		 */
		EClass EXPRESSION_BLOCK = eINSTANCE.getExpressionBlock();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPRESSION_BLOCK__STATEMENTS = eINSTANCE.getExpressionBlock_Statements();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ElseExpressionImpl <em>Else Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ElseExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getElseExpression()
		 * @generated
		 */
		EClass ELSE_EXPRESSION = eINSTANCE.getElseExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ElseIfExpressionImpl <em>Else If Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ElseIfExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getElseIfExpression()
		 * @generated
		 */
		EClass ELSE_IF_EXPRESSION = eINSTANCE.getElseIfExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl <em>Virtual Name Or Reference</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVirtualNameOrReference()
		 * @generated
		 */
		EClass VIRTUAL_NAME_OR_REFERENCE = eINSTANCE.getVirtualNameOrReference();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VIRTUAL_NAME_OR_REFERENCE__VALUE = eINSTANCE.getVirtualNameOrReference_Value();

		/**
		 * The meta object literal for the '<em><b>Exported</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VIRTUAL_NAME_OR_REFERENCE__EXPORTED = eINSTANCE.getVirtualNameOrReference_Exported();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ParenthesisedExpressionImpl <em>Parenthesised Expression</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ParenthesisedExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getParenthesisedExpression()
		 * @generated
		 */
		EClass PARENTHESISED_EXPRESSION = eINSTANCE.getParenthesisedExpression();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PARENTHESISED_EXPRESSION__EXPR = eINSTANCE.getParenthesisedExpression_Expr();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ExprListImpl <em>Expr List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ExprListImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExprList()
		 * @generated
		 */
		EClass EXPR_LIST = eINSTANCE.getExprList();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPR_LIST__EXPRESSIONS = eINSTANCE.getExprList_Expressions();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.DoubleQuotedStringImpl <em>Double Quoted String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.DoubleQuotedStringImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDoubleQuotedString()
		 * @generated
		 */
		EClass DOUBLE_QUOTED_STRING = eINSTANCE.getDoubleQuotedString();

		/**
		 * The meta object literal for the '<em><b>Text Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DOUBLE_QUOTED_STRING__TEXT_EXPRESSION = eINSTANCE.getDoubleQuotedString_TextExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.SingleQuotedStringImpl <em>Single Quoted String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.SingleQuotedStringImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSingleQuotedString()
		 * @generated
		 */
		EClass SINGLE_QUOTED_STRING = eINSTANCE.getSingleQuotedString();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SINGLE_QUOTED_STRING__TEXT = eINSTANCE.getSingleQuotedString_Text();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.StringExpressionImpl <em>String Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.StringExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getStringExpression()
		 * @generated
		 */
		EClass STRING_EXPRESSION = eINSTANCE.getStringExpression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.UnquotedStringImpl <em>Unquoted String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.UnquotedStringImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnquotedString()
		 * @generated
		 */
		EClass UNQUOTED_STRING = eINSTANCE.getUnquotedString();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference UNQUOTED_STRING__EXPRESSION = eINSTANCE.getUnquotedString_Expression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.IQuotedString <em>IQuoted String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.IQuotedString
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getIQuotedString()
		 * @generated
		 */
		EClass IQUOTED_STRING = eINSTANCE.getIQuotedString();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.InterpolatedVariableImpl <em>Interpolated Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.InterpolatedVariableImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getInterpolatedVariable()
		 * @generated
		 */
		EClass INTERPOLATED_VARIABLE = eINSTANCE.getInterpolatedVariable();

		/**
		 * The meta object literal for the '<em><b>Var Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTERPOLATED_VARIABLE__VAR_NAME = eINSTANCE.getInterpolatedVariable_VarName();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.TextExpressionImpl <em>Text Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.TextExpressionImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getTextExpression()
		 * @generated
		 */
		EClass TEXT_EXPRESSION = eINSTANCE.getTextExpression();

		/**
		 * The meta object literal for the '<em><b>Trailing</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEXT_EXPRESSION__TRAILING = eINSTANCE.getTextExpression_Trailing();

		/**
		 * The meta object literal for the '<em><b>Leading</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEXT_EXPRESSION__LEADING = eINSTANCE.getTextExpression_Leading();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.VerbatimTEImpl <em>Verbatim TE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.VerbatimTEImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVerbatimTE()
		 * @generated
		 */
		EClass VERBATIM_TE = eINSTANCE.getVerbatimTE();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VERBATIM_TE__TEXT = eINSTANCE.getVerbatimTE_Text();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionTEImpl <em>Expression TE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.ExpressionTEImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpressionTE()
		 * @generated
		 */
		EClass EXPRESSION_TE = eINSTANCE.getExpressionTE();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPRESSION_TE__EXPRESSION = eINSTANCE.getExpressionTE_Expression();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.pp.impl.VariableTEImpl <em>Variable TE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.pp.impl.VariableTEImpl
		 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVariableTE()
		 * @generated
		 */
		EClass VARIABLE_TE = eINSTANCE.getVariableTE();

		/**
		 * The meta object literal for the '<em><b>Var Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_TE__VAR_NAME = eINSTANCE.getVariableTE_VarName();

	}

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "pp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.cloudsmith.org/geppetto/1.0.0/PP";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "pp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	PPPackage eINSTANCE = org.cloudsmith.geppetto.pp.impl.PPPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 1;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionBlockImpl <em>Expression Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ExpressionBlockImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpressionBlock()
	 * @generated
	 */
	int EXPRESSION_BLOCK = 52;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_BLOCK__STATEMENTS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_BLOCK_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.PuppetManifestImpl <em>Puppet Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.PuppetManifestImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getPuppetManifest()
	 * @generated
	 */
	int PUPPET_MANIFEST = 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_MANIFEST__STATEMENTS = EXPRESSION_BLOCK__STATEMENTS;

	/**
	 * The number of structural features of the '<em>Puppet Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_MANIFEST_FEATURE_COUNT = EXPRESSION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl <em>Resource Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getResourceBody()
	 * @generated
	 */
	int RESOURCE_BODY = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_BODY__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Name Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_BODY__NAME_EXPR = 1;

	/**
	 * The number of structural features of the '<em>Resource Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_BODY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AttributeOperationImpl <em>Attribute Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AttributeOperationImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAttributeOperation()
	 * @generated
	 */
	int ATTRIBUTE_OPERATION = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION__KEY = 1;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION__OP = 2;

	/**
	 * The number of structural features of the '<em>Attribute Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AttributeOperationsImpl <em>Attribute Operations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AttributeOperationsImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAttributeOperations()
	 * @generated
	 */
	int ATTRIBUTE_OPERATIONS = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATIONS__ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>Attribute Operations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.ICollectQuery <em>ICollect Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.ICollectQuery
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getICollectQuery()
	 * @generated
	 */
	int ICOLLECT_QUERY = 5;

	/**
	 * The number of structural features of the '<em>ICollect Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ICOLLECT_QUERY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.UnaryExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 49;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.VirtualCollectQueryImpl <em>Virtual Collect Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.VirtualCollectQueryImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVirtualCollectQuery()
	 * @generated
	 */
	int VIRTUAL_COLLECT_QUERY = 6;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_COLLECT_QUERY__EXPR = UNARY_EXPRESSION__EXPR;

	/**
	 * The number of structural features of the '<em>Virtual Collect Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_COLLECT_QUERY_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ExportedCollectQueryImpl <em>Exported Collect Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ExportedCollectQueryImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExportedCollectQuery()
	 * @generated
	 */
	int EXPORTED_COLLECT_QUERY = 7;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPORTED_COLLECT_QUERY__EXPR = UNARY_EXPRESSION__EXPR;

	/**
	 * The number of structural features of the '<em>Exported Collect Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPORTED_COLLECT_QUERY_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.HostClassDefinitionImpl <em>Host Class Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.HostClassDefinitionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getHostClassDefinition()
	 * @generated
	 */
	int HOST_CLASS_DEFINITION = 8;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionImpl <em>Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.DefinitionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinition()
	 * @generated
	 */
	int DEFINITION = 9;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION__CLASS_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION__STATEMENTS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HOST_CLASS_DEFINITION__CLASS_NAME = DEFINITION__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HOST_CLASS_DEFINITION__ARGUMENTS = DEFINITION__ARGUMENTS;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HOST_CLASS_DEFINITION__STATEMENTS = DEFINITION__STATEMENTS;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HOST_CLASS_DEFINITION__PARENT = DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Host Class Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HOST_CLASS_DEFINITION_FEATURE_COUNT = DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentListImpl <em>Definition Argument List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.DefinitionArgumentListImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinitionArgumentList()
	 * @generated
	 */
	int DEFINITION_ARGUMENT_LIST = 10;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT_LIST__ARGUMENTS = 0;

	/**
	 * The number of structural features of the '<em>Definition Argument List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT_LIST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl <em>Definition Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDefinitionArgument()
	 * @generated
	 */
	int DEFINITION_ARGUMENT = 11;

	/**
	 * The feature id for the '<em><b>Arg Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT__ARG_NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT__OP = 2;

	/**
	 * The number of structural features of the '<em>Definition Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEFINITION_ARGUMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl <em>Case Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCaseExpression()
	 * @generated
	 */
	int CASE_EXPRESSION = 12;

	/**
	 * The feature id for the '<em><b>Switch Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__SWITCH_EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION__CASES = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Case Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.CaseImpl <em>Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.CaseImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCase()
	 * @generated
	 */
	int CASE = 13;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE__STATEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE__VALUES = 1;

	/**
	 * The number of structural features of the '<em>Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CASE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.IfExpressionImpl <em>If Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.IfExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getIfExpression()
	 * @generated
	 */
	int IF_EXPRESSION = 14;

	/**
	 * The feature id for the '<em><b>Cond Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__COND_EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__THEN_STATEMENTS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__ELSE_STATEMENT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralExpressionImpl <em>Literal Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralExpression()
	 * @generated
	 */
	int LITERAL_EXPRESSION = 15;

	/**
	 * The number of structural features of the '<em>Literal Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralNameOrReferenceImpl <em>Literal Name Or Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralNameOrReferenceImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralNameOrReference()
	 * @generated
	 */
	int LITERAL_NAME_OR_REFERENCE = 16;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_NAME_OR_REFERENCE__VALUE = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Name Or Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_NAME_OR_REFERENCE_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl <em>Resource Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getResourceExpression()
	 * @generated
	 */
	int RESOURCE_EXPRESSION = 17;

	/**
	 * The feature id for the '<em><b>Resource Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_EXPRESSION__RESOURCE_EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Data</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_EXPRESSION__RESOURCE_DATA = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Resource Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ImportExpressionImpl <em>Import Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ImportExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getImportExpression()
	 * @generated
	 */
	int IMPORT_EXPRESSION = 18;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_EXPRESSION__VALUES = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Import Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralListImpl <em>Literal List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralListImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralList()
	 * @generated
	 */
	int LITERAL_LIST = 19;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_LIST__ELEMENTS = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_LIST_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralHashImpl <em>Literal Hash</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralHashImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralHash()
	 * @generated
	 */
	int LITERAL_HASH = 20;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_HASH__ELEMENTS = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Hash</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_HASH_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.HashEntryImpl <em>Hash Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.HashEntryImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getHashEntry()
	 * @generated
	 */
	int HASH_ENTRY = 21;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HASH_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HASH_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Hash Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HASH_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralBooleanImpl <em>Literal Boolean</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralBooleanImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralBoolean()
	 * @generated
	 */
	int LITERAL_BOOLEAN = 22;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_BOOLEAN__VALUE = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Boolean</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_BOOLEAN_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralUndefImpl <em>Literal Undef</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralUndefImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralUndef()
	 * @generated
	 */
	int LITERAL_UNDEF = 23;

	/**
	 * The number of structural features of the '<em>Literal Undef</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_UNDEF_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralDefaultImpl <em>Literal Default</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralDefaultImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralDefault()
	 * @generated
	 */
	int LITERAL_DEFAULT = 24;

	/**
	 * The number of structural features of the '<em>Literal Default</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_DEFAULT_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralRegexImpl <em>Literal Regex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralRegexImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralRegex()
	 * @generated
	 */
	int LITERAL_REGEX = 25;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_REGEX__VALUE = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Regex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_REGEX_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.LiteralNameImpl <em>Literal Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.LiteralNameImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getLiteralName()
	 * @generated
	 */
	int LITERAL_NAME = 26;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_NAME__VALUE = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_NAME_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.VariableExpressionImpl <em>Variable Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.VariableExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVariableExpression()
	 * @generated
	 */
	int VARIABLE_EXPRESSION = 27;

	/**
	 * The feature id for the '<em><b>Var Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION__VAR_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.BinaryExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getBinaryExpression()
	 * @generated
	 */
	int BINARY_EXPRESSION = 46;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__LEFT_EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__RIGHT_EXPR = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.BinaryOpExpressionImpl <em>Binary Op Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.BinaryOpExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getBinaryOpExpression()
	 * @generated
	 */
	int BINARY_OP_EXPRESSION = 45;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_OP_EXPRESSION__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_OP_EXPRESSION__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_OP_EXPRESSION__OP_NAME = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Binary Op Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_OP_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.RelationshipExpressionImpl <em>Relationship Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.RelationshipExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getRelationshipExpression()
	 * @generated
	 */
	int RELATIONSHIP_EXPRESSION = 28;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Relationship Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AssignmentExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAssignmentExpression()
	 * @generated
	 */
	int ASSIGNMENT_EXPRESSION = 29;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The number of structural features of the '<em>Assignment Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AppendExpressionImpl <em>Append Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AppendExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAppendExpression()
	 * @generated
	 */
	int APPEND_EXPRESSION = 30;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPEND_EXPRESSION__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPEND_EXPRESSION__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The number of structural features of the '<em>Append Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPEND_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.OrExpressionImpl <em>Or Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.OrExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getOrExpression()
	 * @generated
	 */
	int OR_EXPRESSION = 31;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The number of structural features of the '<em>Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AndExpressionImpl <em>And Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AndExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAndExpression()
	 * @generated
	 */
	int AND_EXPRESSION = 32;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The number of structural features of the '<em>And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.RelationalExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getRelationalExpression()
	 * @generated
	 */
	int RELATIONAL_EXPRESSION = 33;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Relational Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.EqualityExpressionImpl <em>Equality Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.EqualityExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getEqualityExpression()
	 * @generated
	 */
	int EQUALITY_EXPRESSION = 34;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ShiftExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getShiftExpression()
	 * @generated
	 */
	int SHIFT_EXPRESSION = 35;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Shift Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AdditiveExpressionImpl <em>Additive Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AdditiveExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAdditiveExpression()
	 * @generated
	 */
	int ADDITIVE_EXPRESSION = 36;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Additive Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.MultiplicativeExpressionImpl <em>Multiplicative Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.MultiplicativeExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getMultiplicativeExpression()
	 * @generated
	 */
	int MULTIPLICATIVE_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Multiplicative Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.MatchingExpressionImpl <em>Matching Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.MatchingExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getMatchingExpression()
	 * @generated
	 */
	int MATCHING_EXPRESSION = 38;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHING_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHING_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHING_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>Matching Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHING_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.InExpressionImpl <em>In Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.InExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getInExpression()
	 * @generated
	 */
	int IN_EXPRESSION = 39;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IN_EXPRESSION__LEFT_EXPR = BINARY_OP_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IN_EXPRESSION__RIGHT_EXPR = BINARY_OP_EXPRESSION__RIGHT_EXPR;

	/**
	 * The feature id for the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IN_EXPRESSION__OP_NAME = BINARY_OP_EXPRESSION__OP_NAME;

	/**
	 * The number of structural features of the '<em>In Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IN_EXPRESSION_FEATURE_COUNT = BINARY_OP_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl <em>Parameterized Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getParameterizedExpression()
	 * @generated
	 */
	int PARAMETERIZED_EXPRESSION = 47;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_EXPRESSION__LEFT_EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_EXPRESSION__PARAMETERS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parameterized Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.AtExpressionImpl <em>At Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.AtExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getAtExpression()
	 * @generated
	 */
	int AT_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AT_EXPRESSION__LEFT_EXPR = PARAMETERIZED_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AT_EXPRESSION__PARAMETERS = PARAMETERIZED_EXPRESSION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>At Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AT_EXPRESSION_FEATURE_COUNT = PARAMETERIZED_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.CollectExpressionImpl <em>Collect Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.CollectExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getCollectExpression()
	 * @generated
	 */
	int COLLECT_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Class Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECT_EXPRESSION__CLASS_REFERENCE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECT_EXPRESSION__QUERY = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECT_EXPRESSION__ATTRIBUTES = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Collect Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.SelectorExpressionImpl <em>Selector Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.SelectorExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSelectorExpression()
	 * @generated
	 */
	int SELECTOR_EXPRESSION = 42;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_EXPRESSION__LEFT_EXPR = PARAMETERIZED_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_EXPRESSION__PARAMETERS = PARAMETERIZED_EXPRESSION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Selector Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_EXPRESSION_FEATURE_COUNT = PARAMETERIZED_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.SelectorEntryImpl <em>Selector Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.SelectorEntryImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSelectorEntry()
	 * @generated
	 */
	int SELECTOR_ENTRY = 43;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_ENTRY__LEFT_EXPR = BINARY_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_ENTRY__RIGHT_EXPR = BINARY_EXPRESSION__RIGHT_EXPR;

	/**
	 * The number of structural features of the '<em>Selector Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SELECTOR_ENTRY_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.FunctionCallImpl <em>Function Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.FunctionCallImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getFunctionCall()
	 * @generated
	 */
	int FUNCTION_CALL = 44;

	/**
	 * The feature id for the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__LEFT_EXPR = PARAMETERIZED_EXPRESSION__LEFT_EXPR;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__PARAMETERS = PARAMETERIZED_EXPRESSION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Function Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_FEATURE_COUNT = PARAMETERIZED_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl <em>Node Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getNodeDefinition()
	 * @generated
	 */
	int NODE_DEFINITION = 48;

	/**
	 * The feature id for the '<em><b>Host Names</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_DEFINITION__HOST_NAMES = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_DEFINITION__PARENT_NAME = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_DEFINITION__STATEMENTS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Node Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_DEFINITION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryMinusExpressionImpl <em>Unary Minus Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.UnaryMinusExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryMinusExpression()
	 * @generated
	 */
	int UNARY_MINUS_EXPRESSION = 50;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_MINUS_EXPRESSION__EXPR = UNARY_EXPRESSION__EXPR;

	/**
	 * The number of structural features of the '<em>Unary Minus Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_MINUS_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.UnaryNotExpressionImpl <em>Unary Not Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.UnaryNotExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnaryNotExpression()
	 * @generated
	 */
	int UNARY_NOT_EXPRESSION = 51;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_NOT_EXPRESSION__EXPR = UNARY_EXPRESSION__EXPR;

	/**
	 * The number of structural features of the '<em>Unary Not Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_NOT_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ElseExpressionImpl <em>Else Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ElseExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getElseExpression()
	 * @generated
	 */
	int ELSE_EXPRESSION = 53;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_EXPRESSION__STATEMENTS = EXPRESSION_BLOCK__STATEMENTS;

	/**
	 * The number of structural features of the '<em>Else Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_EXPRESSION_FEATURE_COUNT = EXPRESSION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ElseIfExpressionImpl <em>Else If Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ElseIfExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getElseIfExpression()
	 * @generated
	 */
	int ELSE_IF_EXPRESSION = 54;

	/**
	 * The feature id for the '<em><b>Cond Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_IF_EXPRESSION__COND_EXPR = IF_EXPRESSION__COND_EXPR;

	/**
	 * The feature id for the '<em><b>Then Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_IF_EXPRESSION__THEN_STATEMENTS = IF_EXPRESSION__THEN_STATEMENTS;

	/**
	 * The feature id for the '<em><b>Else Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_IF_EXPRESSION__ELSE_STATEMENT = IF_EXPRESSION__ELSE_STATEMENT;

	/**
	 * The number of structural features of the '<em>Else If Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELSE_IF_EXPRESSION_FEATURE_COUNT = IF_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl <em>Virtual Name Or Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVirtualNameOrReference()
	 * @generated
	 */
	int VIRTUAL_NAME_OR_REFERENCE = 55;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NAME_OR_REFERENCE__VALUE = LITERAL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NAME_OR_REFERENCE__EXPORTED = LITERAL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Virtual Name Or Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_NAME_OR_REFERENCE_FEATURE_COUNT = LITERAL_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ParenthesisedExpressionImpl <em>Parenthesised Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ParenthesisedExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getParenthesisedExpression()
	 * @generated
	 */
	int PARENTHESISED_EXPRESSION = 56;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESISED_EXPRESSION__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parenthesised Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESISED_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ExprListImpl <em>Expr List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ExprListImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExprList()
	 * @generated
	 */
	int EXPR_LIST = 57;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST__EXPRESSIONS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.StringExpressionImpl <em>String Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.StringExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getStringExpression()
	 * @generated
	 */
	int STRING_EXPRESSION = 60;

	/**
	 * The number of structural features of the '<em>String Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.DoubleQuotedStringImpl <em>Double Quoted String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.DoubleQuotedStringImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getDoubleQuotedString()
	 * @generated
	 */
	int DOUBLE_QUOTED_STRING = 58;

	/**
	 * The feature id for the '<em><b>Text Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_QUOTED_STRING__TEXT_EXPRESSION = STRING_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Quoted String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_QUOTED_STRING_FEATURE_COUNT = STRING_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.SingleQuotedStringImpl <em>Single Quoted String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.SingleQuotedStringImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getSingleQuotedString()
	 * @generated
	 */
	int SINGLE_QUOTED_STRING = 59;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SINGLE_QUOTED_STRING__TEXT = STRING_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Single Quoted String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SINGLE_QUOTED_STRING_FEATURE_COUNT = STRING_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.UnquotedStringImpl <em>Unquoted String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.UnquotedStringImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getUnquotedString()
	 * @generated
	 */
	int UNQUOTED_STRING = 61;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNQUOTED_STRING__EXPRESSION = STRING_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unquoted String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNQUOTED_STRING_FEATURE_COUNT = STRING_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.IQuotedString <em>IQuoted String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.IQuotedString
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getIQuotedString()
	 * @generated
	 */
	int IQUOTED_STRING = 62;

	/**
	 * The number of structural features of the '<em>IQuoted String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IQUOTED_STRING_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.InterpolatedVariableImpl <em>Interpolated Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.InterpolatedVariableImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getInterpolatedVariable()
	 * @generated
	 */
	int INTERPOLATED_VARIABLE = 63;

	/**
	 * The feature id for the '<em><b>Var Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTERPOLATED_VARIABLE__VAR_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Interpolated Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTERPOLATED_VARIABLE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.TextExpressionImpl <em>Text Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.TextExpressionImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getTextExpression()
	 * @generated
	 */
	int TEXT_EXPRESSION = 64;

	/**
	 * The feature id for the '<em><b>Trailing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION__TRAILING = 0;

	/**
	 * The feature id for the '<em><b>Leading</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION__LEADING = 1;

	/**
	 * The number of structural features of the '<em>Text Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.VerbatimTEImpl <em>Verbatim TE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.VerbatimTEImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVerbatimTE()
	 * @generated
	 */
	int VERBATIM_TE = 65;

	/**
	 * The feature id for the '<em><b>Trailing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERBATIM_TE__TRAILING = TEXT_EXPRESSION__TRAILING;

	/**
	 * The feature id for the '<em><b>Leading</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERBATIM_TE__LEADING = TEXT_EXPRESSION__LEADING;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERBATIM_TE__TEXT = TEXT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Verbatim TE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERBATIM_TE_FEATURE_COUNT = TEXT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.ExpressionTEImpl <em>Expression TE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.ExpressionTEImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getExpressionTE()
	 * @generated
	 */
	int EXPRESSION_TE = 66;

	/**
	 * The feature id for the '<em><b>Trailing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TE__TRAILING = TEXT_EXPRESSION__TRAILING;

	/**
	 * The feature id for the '<em><b>Leading</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TE__LEADING = TEXT_EXPRESSION__LEADING;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TE__EXPRESSION = TEXT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression TE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TE_FEATURE_COUNT = TEXT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.pp.impl.VariableTEImpl <em>Variable TE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.pp.impl.VariableTEImpl
	 * @see org.cloudsmith.geppetto.pp.impl.PPPackageImpl#getVariableTE()
	 * @generated
	 */
	int VARIABLE_TE = 67;

	/**
	 * The feature id for the '<em><b>Trailing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TE__TRAILING = TEXT_EXPRESSION__TRAILING;

	/**
	 * The feature id for the '<em><b>Leading</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TE__LEADING = TEXT_EXPRESSION__LEADING;

	/**
	 * The feature id for the '<em><b>Var Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TE__VAR_NAME = TEXT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable TE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TE_FEATURE_COUNT = TEXT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AdditiveExpression <em>Additive Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Additive Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.AdditiveExpression
	 * @generated
	 */
	EClass getAdditiveExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AndExpression <em>And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>And Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.AndExpression
	 * @generated
	 */
	EClass getAndExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AppendExpression <em>Append Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Append Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.AppendExpression
	 * @generated
	 */
	EClass getAppendExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AssignmentExpression <em>Assignment Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Assignment Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.AssignmentExpression
	 * @generated
	 */
	EClass getAssignmentExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AtExpression <em>At Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>At Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.AtExpression
	 * @generated
	 */
	EClass getAtExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AttributeOperation <em>Attribute Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Attribute Operation</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperation
	 * @generated
	 */
	EClass getAttributeOperation();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.AttributeOperation#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperation#getKey()
	 * @see #getAttributeOperation()
	 * @generated
	 */
	EAttribute getAttributeOperation_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.AttributeOperation#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperation#getOp()
	 * @see #getAttributeOperation()
	 * @generated
	 */
	EAttribute getAttributeOperation_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.AttributeOperation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperation#getValue()
	 * @see #getAttributeOperation()
	 * @generated
	 */
	EReference getAttributeOperation_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.AttributeOperations <em>Attribute Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Attribute Operations</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperations
	 * @generated
	 */
	EClass getAttributeOperations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.AttributeOperations#getAttributes
	 * <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.cloudsmith.geppetto.pp.AttributeOperations#getAttributes()
	 * @see #getAttributeOperations()
	 * @generated
	 */
	EReference getAttributeOperations_Attributes();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Binary Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.BinaryExpression
	 * @generated
	 */
	EClass getBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.BinaryExpression#getLeftExpr <em>Left Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Left Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.BinaryExpression#getLeftExpr()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_LeftExpr();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.BinaryExpression#getRightExpr <em>Right Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Right Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.BinaryExpression#getRightExpr()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_RightExpr();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.BinaryOpExpression <em>Binary Op Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Binary Op Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.BinaryOpExpression
	 * @generated
	 */
	EClass getBinaryOpExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.BinaryOpExpression#getOpName <em>Op Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Op Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.BinaryOpExpression#getOpName()
	 * @see #getBinaryOpExpression()
	 * @generated
	 */
	EAttribute getBinaryOpExpression_OpName();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.Case <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Case</em>'.
	 * @see org.cloudsmith.geppetto.pp.Case
	 * @generated
	 */
	EClass getCase();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.Case#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.cloudsmith.geppetto.pp.Case#getStatements()
	 * @see #getCase()
	 * @generated
	 */
	EReference getCase_Statements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.Case#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.cloudsmith.geppetto.pp.Case#getValues()
	 * @see #getCase()
	 * @generated
	 */
	EReference getCase_Values();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.CaseExpression <em>Case Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Case Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.CaseExpression
	 * @generated
	 */
	EClass getCaseExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.CaseExpression#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Cases</em>'.
	 * @see org.cloudsmith.geppetto.pp.CaseExpression#getCases()
	 * @see #getCaseExpression()
	 * @generated
	 */
	EReference getCaseExpression_Cases();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.CaseExpression#getSwitchExpr <em>Switch Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Switch Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.CaseExpression#getSwitchExpr()
	 * @see #getCaseExpression()
	 * @generated
	 */
	EReference getCaseExpression_SwitchExpr();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.CollectExpression <em>Collect Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Collect Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.CollectExpression
	 * @generated
	 */
	EClass getCollectExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.CollectExpression#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.cloudsmith.geppetto.pp.CollectExpression#getAttributes()
	 * @see #getCollectExpression()
	 * @generated
	 */
	EReference getCollectExpression_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.CollectExpression#getClassReference
	 * <em>Class Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Class Reference</em>'.
	 * @see org.cloudsmith.geppetto.pp.CollectExpression#getClassReference()
	 * @see #getCollectExpression()
	 * @generated
	 */
	EReference getCollectExpression_ClassReference();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.CollectExpression#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Query</em>'.
	 * @see org.cloudsmith.geppetto.pp.CollectExpression#getQuery()
	 * @see #getCollectExpression()
	 * @generated
	 */
	EReference getCollectExpression_Query();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.Definition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Definition</em>'.
	 * @see org.cloudsmith.geppetto.pp.Definition
	 * @generated
	 */
	EClass getDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.Definition#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Arguments</em>'.
	 * @see org.cloudsmith.geppetto.pp.Definition#getArguments()
	 * @see #getDefinition()
	 * @generated
	 */
	EReference getDefinition_Arguments();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.Definition#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.Definition#getClassName()
	 * @see #getDefinition()
	 * @generated
	 */
	EAttribute getDefinition_ClassName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.Definition#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.cloudsmith.geppetto.pp.Definition#getStatements()
	 * @see #getDefinition()
	 * @generated
	 */
	EReference getDefinition_Statements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.DefinitionArgument <em>Definition Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Definition Argument</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgument
	 * @generated
	 */
	EClass getDefinitionArgument();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.DefinitionArgument#getArgName <em>Arg Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Arg Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgument#getArgName()
	 * @see #getDefinitionArgument()
	 * @generated
	 */
	EAttribute getDefinitionArgument_ArgName();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.DefinitionArgument#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgument#getOp()
	 * @see #getDefinitionArgument()
	 * @generated
	 */
	EAttribute getDefinitionArgument_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.DefinitionArgument#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgument#getValue()
	 * @see #getDefinitionArgument()
	 * @generated
	 */
	EReference getDefinitionArgument_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.DefinitionArgumentList <em>Definition Argument List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Definition Argument List</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgumentList
	 * @generated
	 */
	EClass getDefinitionArgumentList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.DefinitionArgumentList#getArguments
	 * <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.cloudsmith.geppetto.pp.DefinitionArgumentList#getArguments()
	 * @see #getDefinitionArgumentList()
	 * @generated
	 */
	EReference getDefinitionArgumentList_Arguments();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.DoubleQuotedString <em>Double Quoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Double Quoted String</em>'.
	 * @see org.cloudsmith.geppetto.pp.DoubleQuotedString
	 * @generated
	 */
	EClass getDoubleQuotedString();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.DoubleQuotedString#getTextExpression
	 * <em>Text Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Text Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.DoubleQuotedString#getTextExpression()
	 * @see #getDoubleQuotedString()
	 * @generated
	 */
	EReference getDoubleQuotedString_TextExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ElseExpression <em>Else Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Else Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ElseExpression
	 * @generated
	 */
	EClass getElseExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ElseIfExpression <em>Else If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Else If Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ElseIfExpression
	 * @generated
	 */
	EClass getElseIfExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.EqualityExpression <em>Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Equality Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.EqualityExpression
	 * @generated
	 */
	EClass getEqualityExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ExportedCollectQuery <em>Exported Collect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Exported Collect Query</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExportedCollectQuery
	 * @generated
	 */
	EClass getExportedCollectQuery();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ExpressionBlock <em>Expression Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression Block</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExpressionBlock
	 * @generated
	 */
	EClass getExpressionBlock();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.ExpressionBlock#getStatements
	 * <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExpressionBlock#getStatements()
	 * @see #getExpressionBlock()
	 * @generated
	 */
	EReference getExpressionBlock_Statements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ExpressionTE <em>Expression TE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression TE</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExpressionTE
	 * @generated
	 */
	EClass getExpressionTE();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ExpressionTE#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExpressionTE#getExpression()
	 * @see #getExpressionTE()
	 * @generated
	 */
	EReference getExpressionTE_Expression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ExprList <em>Expr List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expr List</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExprList
	 * @generated
	 */
	EClass getExprList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.ExprList#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Expressions</em>'.
	 * @see org.cloudsmith.geppetto.pp.ExprList#getExpressions()
	 * @see #getExprList()
	 * @generated
	 */
	EReference getExprList_Expressions();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.FunctionCall <em>Function Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Function Call</em>'.
	 * @see org.cloudsmith.geppetto.pp.FunctionCall
	 * @generated
	 */
	EClass getFunctionCall();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.HashEntry <em>Hash Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Hash Entry</em>'.
	 * @see org.cloudsmith.geppetto.pp.HashEntry
	 * @generated
	 */
	EClass getHashEntry();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.HashEntry#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see org.cloudsmith.geppetto.pp.HashEntry#getKey()
	 * @see #getHashEntry()
	 * @generated
	 */
	EReference getHashEntry_Key();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.HashEntry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.HashEntry#getValue()
	 * @see #getHashEntry()
	 * @generated
	 */
	EReference getHashEntry_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.HostClassDefinition <em>Host Class Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Host Class Definition</em>'.
	 * @see org.cloudsmith.geppetto.pp.HostClassDefinition
	 * @generated
	 */
	EClass getHostClassDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.HostClassDefinition#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Parent</em>'.
	 * @see org.cloudsmith.geppetto.pp.HostClassDefinition#getParent()
	 * @see #getHostClassDefinition()
	 * @generated
	 */
	EReference getHostClassDefinition_Parent();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ICollectQuery <em>ICollect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>ICollect Query</em>'.
	 * @see org.cloudsmith.geppetto.pp.ICollectQuery
	 * @generated
	 */
	EClass getICollectQuery();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.IfExpression <em>If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>If Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.IfExpression
	 * @generated
	 */
	EClass getIfExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.IfExpression#getCondExpr <em>Cond Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Cond Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.IfExpression#getCondExpr()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_CondExpr();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.IfExpression#getElseStatement <em>Else Statement</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else Statement</em>'.
	 * @see org.cloudsmith.geppetto.pp.IfExpression#getElseStatement()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_ElseStatement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.IfExpression#getThenStatements
	 * <em>Then Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Then Statements</em>'.
	 * @see org.cloudsmith.geppetto.pp.IfExpression#getThenStatements()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_ThenStatements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ImportExpression <em>Import Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Import Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ImportExpression
	 * @generated
	 */
	EClass getImportExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.ImportExpression#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.cloudsmith.geppetto.pp.ImportExpression#getValues()
	 * @see #getImportExpression()
	 * @generated
	 */
	EReference getImportExpression_Values();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.InExpression <em>In Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>In Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.InExpression
	 * @generated
	 */
	EClass getInExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.InterpolatedVariable <em>Interpolated Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Interpolated Variable</em>'.
	 * @see org.cloudsmith.geppetto.pp.InterpolatedVariable
	 * @generated
	 */
	EClass getInterpolatedVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.InterpolatedVariable#getVarName <em>Var Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Var Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.InterpolatedVariable#getVarName()
	 * @see #getInterpolatedVariable()
	 * @generated
	 */
	EAttribute getInterpolatedVariable_VarName();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.IQuotedString <em>IQuoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>IQuoted String</em>'.
	 * @see org.cloudsmith.geppetto.pp.IQuotedString
	 * @generated
	 */
	EClass getIQuotedString();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralBoolean <em>Literal Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Boolean</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralBoolean
	 * @generated
	 */
	EClass getLiteralBoolean();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.LiteralBoolean#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralBoolean#isValue()
	 * @see #getLiteralBoolean()
	 * @generated
	 */
	EAttribute getLiteralBoolean_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralDefault <em>Literal Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Default</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralDefault
	 * @generated
	 */
	EClass getLiteralDefault();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralExpression <em>Literal Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralExpression
	 * @generated
	 */
	EClass getLiteralExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralHash <em>Literal Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Hash</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralHash
	 * @generated
	 */
	EClass getLiteralHash();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.LiteralHash#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralHash#getElements()
	 * @see #getLiteralHash()
	 * @generated
	 */
	EReference getLiteralHash_Elements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralList <em>Literal List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal List</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralList
	 * @generated
	 */
	EClass getLiteralList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.LiteralList#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralList#getElements()
	 * @see #getLiteralList()
	 * @generated
	 */
	EReference getLiteralList_Elements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralName <em>Literal Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralName
	 * @generated
	 */
	EClass getLiteralName();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.LiteralName#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralName#getValue()
	 * @see #getLiteralName()
	 * @generated
	 */
	EAttribute getLiteralName_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralNameOrReference <em>Literal Name Or Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Name Or Reference</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralNameOrReference
	 * @generated
	 */
	EClass getLiteralNameOrReference();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.LiteralNameOrReference#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralNameOrReference#getValue()
	 * @see #getLiteralNameOrReference()
	 * @generated
	 */
	EAttribute getLiteralNameOrReference_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralRegex <em>Literal Regex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Regex</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralRegex
	 * @generated
	 */
	EClass getLiteralRegex();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.LiteralRegex#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralRegex#getValue()
	 * @see #getLiteralRegex()
	 * @generated
	 */
	EAttribute getLiteralRegex_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.LiteralUndef <em>Literal Undef</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal Undef</em>'.
	 * @see org.cloudsmith.geppetto.pp.LiteralUndef
	 * @generated
	 */
	EClass getLiteralUndef();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.MatchingExpression <em>Matching Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Matching Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.MatchingExpression
	 * @generated
	 */
	EClass getMatchingExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.MultiplicativeExpression <em>Multiplicative Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Multiplicative Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.MultiplicativeExpression
	 * @generated
	 */
	EClass getMultiplicativeExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.NodeDefinition <em>Node Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Node Definition</em>'.
	 * @see org.cloudsmith.geppetto.pp.NodeDefinition
	 * @generated
	 */
	EClass getNodeDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.NodeDefinition#getHostNames <em>Host Names</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Host Names</em>'.
	 * @see org.cloudsmith.geppetto.pp.NodeDefinition#getHostNames()
	 * @see #getNodeDefinition()
	 * @generated
	 */
	EReference getNodeDefinition_HostNames();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.NodeDefinition#getParentName <em>Parent Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Parent Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.NodeDefinition#getParentName()
	 * @see #getNodeDefinition()
	 * @generated
	 */
	EReference getNodeDefinition_ParentName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.NodeDefinition#getStatements <em>Statements</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.cloudsmith.geppetto.pp.NodeDefinition#getStatements()
	 * @see #getNodeDefinition()
	 * @generated
	 */
	EReference getNodeDefinition_Statements();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.OrExpression <em>Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Or Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.OrExpression
	 * @generated
	 */
	EClass getOrExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ParameterizedExpression <em>Parameterized Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameterized Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ParameterizedExpression
	 * @generated
	 */
	EClass getParameterizedExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ParameterizedExpression#getLeftExpr
	 * <em>Left Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Left Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.ParameterizedExpression#getLeftExpr()
	 * @see #getParameterizedExpression()
	 * @generated
	 */
	EReference getParameterizedExpression_LeftExpr();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.ParameterizedExpression#getParameters
	 * <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.cloudsmith.geppetto.pp.ParameterizedExpression#getParameters()
	 * @see #getParameterizedExpression()
	 * @generated
	 */
	EReference getParameterizedExpression_Parameters();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ParenthesisedExpression <em>Parenthesised Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parenthesised Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ParenthesisedExpression
	 * @generated
	 */
	EClass getParenthesisedExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ParenthesisedExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.ParenthesisedExpression#getExpr()
	 * @see #getParenthesisedExpression()
	 * @generated
	 */
	EReference getParenthesisedExpression_Expr();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PPFactory getPPFactory();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.PuppetManifest <em>Puppet Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Puppet Manifest</em>'.
	 * @see org.cloudsmith.geppetto.pp.PuppetManifest
	 * @generated
	 */
	EClass getPuppetManifest();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.RelationalExpression <em>Relational Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Relational Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.RelationalExpression
	 * @generated
	 */
	EClass getRelationalExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.RelationshipExpression <em>Relationship Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Relationship Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.RelationshipExpression
	 * @generated
	 */
	EClass getRelationshipExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ResourceBody <em>Resource Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource Body</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceBody
	 * @generated
	 */
	EClass getResourceBody();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ResourceBody#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceBody#getAttributes()
	 * @see #getResourceBody()
	 * @generated
	 */
	EReference getResourceBody_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ResourceBody#getNameExpr <em>Name Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Name Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceBody#getNameExpr()
	 * @see #getResourceBody()
	 * @generated
	 */
	EReference getResourceBody_NameExpr();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ResourceExpression <em>Resource Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceExpression
	 * @generated
	 */
	EClass getResourceExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.pp.ResourceExpression#getResourceData
	 * <em>Resource Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Resource Data</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceExpression#getResourceData()
	 * @see #getResourceExpression()
	 * @generated
	 */
	EReference getResourceExpression_ResourceData();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.ResourceExpression#getResourceExpr
	 * <em>Resource Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Resource Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.ResourceExpression#getResourceExpr()
	 * @see #getResourceExpression()
	 * @generated
	 */
	EReference getResourceExpression_ResourceExpr();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.SelectorEntry <em>Selector Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Selector Entry</em>'.
	 * @see org.cloudsmith.geppetto.pp.SelectorEntry
	 * @generated
	 */
	EClass getSelectorEntry();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.SelectorExpression <em>Selector Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Selector Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.SelectorExpression
	 * @generated
	 */
	EClass getSelectorExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.ShiftExpression <em>Shift Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Shift Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.ShiftExpression
	 * @generated
	 */
	EClass getShiftExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.SingleQuotedString <em>Single Quoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Single Quoted String</em>'.
	 * @see org.cloudsmith.geppetto.pp.SingleQuotedString
	 * @generated
	 */
	EClass getSingleQuotedString();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.SingleQuotedString#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.cloudsmith.geppetto.pp.SingleQuotedString#getText()
	 * @see #getSingleQuotedString()
	 * @generated
	 */
	EAttribute getSingleQuotedString_Text();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.StringExpression <em>String Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.StringExpression
	 * @generated
	 */
	EClass getStringExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.TextExpression <em>Text Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Text Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.TextExpression
	 * @generated
	 */
	EClass getTextExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.TextExpression#getLeading <em>Leading</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Leading</em>'.
	 * @see org.cloudsmith.geppetto.pp.TextExpression#getLeading()
	 * @see #getTextExpression()
	 * @generated
	 */
	EReference getTextExpression_Leading();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.TextExpression#getTrailing <em>Trailing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Trailing</em>'.
	 * @see org.cloudsmith.geppetto.pp.TextExpression#getTrailing()
	 * @see #getTextExpression()
	 * @generated
	 */
	EReference getTextExpression_Trailing();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.UnaryExpression#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnaryExpression#getExpr()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Expr();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.UnaryMinusExpression <em>Unary Minus Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unary Minus Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnaryMinusExpression
	 * @generated
	 */
	EClass getUnaryMinusExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.UnaryNotExpression <em>Unary Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unary Not Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnaryNotExpression
	 * @generated
	 */
	EClass getUnaryNotExpression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.UnquotedString <em>Unquoted String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unquoted String</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnquotedString
	 * @generated
	 */
	EClass getUnquotedString();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.pp.UnquotedString#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.UnquotedString#getExpression()
	 * @see #getUnquotedString()
	 * @generated
	 */
	EReference getUnquotedString_Expression();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.VariableExpression <em>Variable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable Expression</em>'.
	 * @see org.cloudsmith.geppetto.pp.VariableExpression
	 * @generated
	 */
	EClass getVariableExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.VariableExpression#getVarName <em>Var Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Var Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.VariableExpression#getVarName()
	 * @see #getVariableExpression()
	 * @generated
	 */
	EAttribute getVariableExpression_VarName();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.VariableTE <em>Variable TE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable TE</em>'.
	 * @see org.cloudsmith.geppetto.pp.VariableTE
	 * @generated
	 */
	EClass getVariableTE();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.VariableTE#getVarName <em>Var Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Var Name</em>'.
	 * @see org.cloudsmith.geppetto.pp.VariableTE#getVarName()
	 * @see #getVariableTE()
	 * @generated
	 */
	EAttribute getVariableTE_VarName();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.VerbatimTE <em>Verbatim TE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Verbatim TE</em>'.
	 * @see org.cloudsmith.geppetto.pp.VerbatimTE
	 * @generated
	 */
	EClass getVerbatimTE();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.VerbatimTE#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.cloudsmith.geppetto.pp.VerbatimTE#getText()
	 * @see #getVerbatimTE()
	 * @generated
	 */
	EAttribute getVerbatimTE_Text();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.VirtualCollectQuery <em>Virtual Collect Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Virtual Collect Query</em>'.
	 * @see org.cloudsmith.geppetto.pp.VirtualCollectQuery
	 * @generated
	 */
	EClass getVirtualCollectQuery();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.pp.VirtualNameOrReference <em>Virtual Name Or Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Virtual Name Or Reference</em>'.
	 * @see org.cloudsmith.geppetto.pp.VirtualNameOrReference
	 * @generated
	 */
	EClass getVirtualNameOrReference();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.VirtualNameOrReference#isExported <em>Exported</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Exported</em>'.
	 * @see org.cloudsmith.geppetto.pp.VirtualNameOrReference#isExported()
	 * @see #getVirtualNameOrReference()
	 * @generated
	 */
	EAttribute getVirtualNameOrReference_Exported();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.pp.VirtualNameOrReference#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.pp.VirtualNameOrReference#getValue()
	 * @see #getVirtualNameOrReference()
	 * @generated
	 */
	EAttribute getVirtualNameOrReference_Value();

} // PPPackage
