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
package org.cloudsmith.geppetto.pp.impl;

import org.cloudsmith.geppetto.pp.*;
import org.cloudsmith.geppetto.pp.AdditiveExpression;
import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeOperations;
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
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HashEntry;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.InExpression;
import org.cloudsmith.geppetto.pp.InterpolatedVariable;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralDefault;
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
import org.cloudsmith.geppetto.pp.PPFactory;
import org.cloudsmith.geppetto.pp.PPPackage;
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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PPFactoryImpl extends EFactoryImpl implements PPFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PPPackage getPackage() {
		return PPPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static PPFactory init() {
		try {
			PPFactory thePPFactory = (PPFactory) EPackage.Registry.INSTANCE.getEFactory("http://www.cloudsmith.org/geppetto/1.0.0/PP");
			if(thePPFactory != null) {
				return thePPFactory;
			}
		}
		catch(Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PPFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PPFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch(eClass.getClassifierID()) {
			case PPPackage.PUPPET_MANIFEST:
				return createPuppetManifest();
			case PPPackage.EXPRESSION:
				return createExpression();
			case PPPackage.RESOURCE_BODY:
				return createResourceBody();
			case PPPackage.ATTRIBUTE_OPERATION:
				return createAttributeOperation();
			case PPPackage.ATTRIBUTE_OPERATIONS:
				return createAttributeOperations();
			case PPPackage.VIRTUAL_COLLECT_QUERY:
				return createVirtualCollectQuery();
			case PPPackage.EXPORTED_COLLECT_QUERY:
				return createExportedCollectQuery();
			case PPPackage.HOST_CLASS_DEFINITION:
				return createHostClassDefinition();
			case PPPackage.DEFINITION:
				return createDefinition();
			case PPPackage.DEFINITION_ARGUMENT_LIST:
				return createDefinitionArgumentList();
			case PPPackage.DEFINITION_ARGUMENT:
				return createDefinitionArgument();
			case PPPackage.CASE_EXPRESSION:
				return createCaseExpression();
			case PPPackage.CASE:
				return createCase();
			case PPPackage.IF_EXPRESSION:
				return createIfExpression();
			case PPPackage.LITERAL_NAME_OR_REFERENCE:
				return createLiteralNameOrReference();
			case PPPackage.RESOURCE_EXPRESSION:
				return createResourceExpression();
			case PPPackage.IMPORT_EXPRESSION:
				return createImportExpression();
			case PPPackage.LITERAL_LIST:
				return createLiteralList();
			case PPPackage.LITERAL_HASH:
				return createLiteralHash();
			case PPPackage.HASH_ENTRY:
				return createHashEntry();
			case PPPackage.LITERAL_BOOLEAN:
				return createLiteralBoolean();
			case PPPackage.LITERAL_UNDEF:
				return createLiteralUndef();
			case PPPackage.LITERAL_DEFAULT:
				return createLiteralDefault();
			case PPPackage.LITERAL_REGEX:
				return createLiteralRegex();
			case PPPackage.LITERAL_NAME:
				return createLiteralName();
			case PPPackage.VARIABLE_EXPRESSION:
				return createVariableExpression();
			case PPPackage.RELATIONSHIP_EXPRESSION:
				return createRelationshipExpression();
			case PPPackage.ASSIGNMENT_EXPRESSION:
				return createAssignmentExpression();
			case PPPackage.APPEND_EXPRESSION:
				return createAppendExpression();
			case PPPackage.OR_EXPRESSION:
				return createOrExpression();
			case PPPackage.AND_EXPRESSION:
				return createAndExpression();
			case PPPackage.RELATIONAL_EXPRESSION:
				return createRelationalExpression();
			case PPPackage.EQUALITY_EXPRESSION:
				return createEqualityExpression();
			case PPPackage.SHIFT_EXPRESSION:
				return createShiftExpression();
			case PPPackage.ADDITIVE_EXPRESSION:
				return createAdditiveExpression();
			case PPPackage.MULTIPLICATIVE_EXPRESSION:
				return createMultiplicativeExpression();
			case PPPackage.MATCHING_EXPRESSION:
				return createMatchingExpression();
			case PPPackage.IN_EXPRESSION:
				return createInExpression();
			case PPPackage.AT_EXPRESSION:
				return createAtExpression();
			case PPPackage.COLLECT_EXPRESSION:
				return createCollectExpression();
			case PPPackage.SELECTOR_EXPRESSION:
				return createSelectorExpression();
			case PPPackage.SELECTOR_ENTRY:
				return createSelectorEntry();
			case PPPackage.FUNCTION_CALL:
				return createFunctionCall();
			case PPPackage.NODE_DEFINITION:
				return createNodeDefinition();
			case PPPackage.UNARY_MINUS_EXPRESSION:
				return createUnaryMinusExpression();
			case PPPackage.UNARY_NOT_EXPRESSION:
				return createUnaryNotExpression();
			case PPPackage.ELSE_EXPRESSION:
				return createElseExpression();
			case PPPackage.ELSE_IF_EXPRESSION:
				return createElseIfExpression();
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE:
				return createVirtualNameOrReference();
			case PPPackage.PARENTHESISED_EXPRESSION:
				return createParenthesisedExpression();
			case PPPackage.EXPR_LIST:
				return createExprList();
			case PPPackage.DOUBLE_QUOTED_STRING:
				return createDoubleQuotedString();
			case PPPackage.SINGLE_QUOTED_STRING:
				return createSingleQuotedString();
			case PPPackage.UNQUOTED_STRING:
				return createUnquotedString();
			case PPPackage.INTERPOLATED_VARIABLE:
				return createInterpolatedVariable();
			case PPPackage.VERBATIM_TE:
				return createVerbatimTE();
			case PPPackage.EXPRESSION_TE:
				return createExpressionTE();
			case PPPackage.VARIABLE_TE:
				return createVariableTE();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AdditiveExpression createAdditiveExpression() {
		AdditiveExpressionImpl additiveExpression = new AdditiveExpressionImpl();
		return additiveExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AndExpression createAndExpression() {
		AndExpressionImpl andExpression = new AndExpressionImpl();
		return andExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AppendExpression createAppendExpression() {
		AppendExpressionImpl appendExpression = new AppendExpressionImpl();
		return appendExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AssignmentExpression createAssignmentExpression() {
		AssignmentExpressionImpl assignmentExpression = new AssignmentExpressionImpl();
		return assignmentExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AtExpression createAtExpression() {
		AtExpressionImpl atExpression = new AtExpressionImpl();
		return atExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AttributeOperation createAttributeOperation() {
		AttributeOperationImpl attributeOperation = new AttributeOperationImpl();
		return attributeOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AttributeOperations createAttributeOperations() {
		AttributeOperationsImpl attributeOperations = new AttributeOperationsImpl();
		return attributeOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Case createCase() {
		CaseImpl case_ = new CaseImpl();
		return case_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CaseExpression createCaseExpression() {
		CaseExpressionImpl caseExpression = new CaseExpressionImpl();
		return caseExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CollectExpression createCollectExpression() {
		CollectExpressionImpl collectExpression = new CollectExpressionImpl();
		return collectExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Definition createDefinition() {
		DefinitionImpl definition = new DefinitionImpl();
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DefinitionArgument createDefinitionArgument() {
		DefinitionArgumentImpl definitionArgument = new DefinitionArgumentImpl();
		return definitionArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DefinitionArgumentList createDefinitionArgumentList() {
		DefinitionArgumentListImpl definitionArgumentList = new DefinitionArgumentListImpl();
		return definitionArgumentList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DoubleQuotedString createDoubleQuotedString() {
		DoubleQuotedStringImpl doubleQuotedString = new DoubleQuotedStringImpl();
		return doubleQuotedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ElseExpression createElseExpression() {
		ElseExpressionImpl elseExpression = new ElseExpressionImpl();
		return elseExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ElseIfExpression createElseIfExpression() {
		ElseIfExpressionImpl elseIfExpression = new ElseIfExpressionImpl();
		return elseIfExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EqualityExpression createEqualityExpression() {
		EqualityExpressionImpl equalityExpression = new EqualityExpressionImpl();
		return equalityExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExportedCollectQuery createExportedCollectQuery() {
		ExportedCollectQueryImpl exportedCollectQuery = new ExportedCollectQueryImpl();
		return exportedCollectQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression createExpression() {
		ExpressionImpl expression = new ExpressionImpl();
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionTE createExpressionTE() {
		ExpressionTEImpl expressionTE = new ExpressionTEImpl();
		return expressionTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExprList createExprList() {
		ExprListImpl exprList = new ExprListImpl();
		return exprList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FunctionCall createFunctionCall() {
		FunctionCallImpl functionCall = new FunctionCallImpl();
		return functionCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public HashEntry createHashEntry() {
		HashEntryImpl hashEntry = new HashEntryImpl();
		return hashEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public HostClassDefinition createHostClassDefinition() {
		HostClassDefinitionImpl hostClassDefinition = new HostClassDefinitionImpl();
		return hostClassDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IfExpression createIfExpression() {
		IfExpressionImpl ifExpression = new IfExpressionImpl();
		return ifExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ImportExpression createImportExpression() {
		ImportExpressionImpl importExpression = new ImportExpressionImpl();
		return importExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InExpression createInExpression() {
		InExpressionImpl inExpression = new InExpressionImpl();
		return inExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InterpolatedVariable createInterpolatedVariable() {
		InterpolatedVariableImpl interpolatedVariable = new InterpolatedVariableImpl();
		return interpolatedVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralBoolean createLiteralBoolean() {
		LiteralBooleanImpl literalBoolean = new LiteralBooleanImpl();
		return literalBoolean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralDefault createLiteralDefault() {
		LiteralDefaultImpl literalDefault = new LiteralDefaultImpl();
		return literalDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralHash createLiteralHash() {
		LiteralHashImpl literalHash = new LiteralHashImpl();
		return literalHash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralList createLiteralList() {
		LiteralListImpl literalList = new LiteralListImpl();
		return literalList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralName createLiteralName() {
		LiteralNameImpl literalName = new LiteralNameImpl();
		return literalName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralNameOrReference createLiteralNameOrReference() {
		LiteralNameOrReferenceImpl literalNameOrReference = new LiteralNameOrReferenceImpl();
		return literalNameOrReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralRegex createLiteralRegex() {
		LiteralRegexImpl literalRegex = new LiteralRegexImpl();
		return literalRegex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LiteralUndef createLiteralUndef() {
		LiteralUndefImpl literalUndef = new LiteralUndefImpl();
		return literalUndef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MatchingExpression createMatchingExpression() {
		MatchingExpressionImpl matchingExpression = new MatchingExpressionImpl();
		return matchingExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MultiplicativeExpression createMultiplicativeExpression() {
		MultiplicativeExpressionImpl multiplicativeExpression = new MultiplicativeExpressionImpl();
		return multiplicativeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NodeDefinition createNodeDefinition() {
		NodeDefinitionImpl nodeDefinition = new NodeDefinitionImpl();
		return nodeDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OrExpression createOrExpression() {
		OrExpressionImpl orExpression = new OrExpressionImpl();
		return orExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ParenthesisedExpression createParenthesisedExpression() {
		ParenthesisedExpressionImpl parenthesisedExpression = new ParenthesisedExpressionImpl();
		return parenthesisedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PuppetManifest createPuppetManifest() {
		PuppetManifestImpl puppetManifest = new PuppetManifestImpl();
		return puppetManifest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RelationalExpression createRelationalExpression() {
		RelationalExpressionImpl relationalExpression = new RelationalExpressionImpl();
		return relationalExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RelationshipExpression createRelationshipExpression() {
		RelationshipExpressionImpl relationshipExpression = new RelationshipExpressionImpl();
		return relationshipExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ResourceBody createResourceBody() {
		ResourceBodyImpl resourceBody = new ResourceBodyImpl();
		return resourceBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ResourceExpression createResourceExpression() {
		ResourceExpressionImpl resourceExpression = new ResourceExpressionImpl();
		return resourceExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SelectorEntry createSelectorEntry() {
		SelectorEntryImpl selectorEntry = new SelectorEntryImpl();
		return selectorEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SelectorExpression createSelectorExpression() {
		SelectorExpressionImpl selectorExpression = new SelectorExpressionImpl();
		return selectorExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ShiftExpression createShiftExpression() {
		ShiftExpressionImpl shiftExpression = new ShiftExpressionImpl();
		return shiftExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SingleQuotedString createSingleQuotedString() {
		SingleQuotedStringImpl singleQuotedString = new SingleQuotedStringImpl();
		return singleQuotedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnaryMinusExpression createUnaryMinusExpression() {
		UnaryMinusExpressionImpl unaryMinusExpression = new UnaryMinusExpressionImpl();
		return unaryMinusExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnaryNotExpression createUnaryNotExpression() {
		UnaryNotExpressionImpl unaryNotExpression = new UnaryNotExpressionImpl();
		return unaryNotExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnquotedString createUnquotedString() {
		UnquotedStringImpl unquotedString = new UnquotedStringImpl();
		return unquotedString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VariableExpression createVariableExpression() {
		VariableExpressionImpl variableExpression = new VariableExpressionImpl();
		return variableExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VariableTE createVariableTE() {
		VariableTEImpl variableTE = new VariableTEImpl();
		return variableTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VerbatimTE createVerbatimTE() {
		VerbatimTEImpl verbatimTE = new VerbatimTEImpl();
		return verbatimTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VirtualCollectQuery createVirtualCollectQuery() {
		VirtualCollectQueryImpl virtualCollectQuery = new VirtualCollectQueryImpl();
		return virtualCollectQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VirtualNameOrReference createVirtualNameOrReference() {
		VirtualNameOrReferenceImpl virtualNameOrReference = new VirtualNameOrReferenceImpl();
		return virtualNameOrReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PPPackage getPPPackage() {
		return (PPPackage) getEPackage();
	}

} // PPFactoryImpl
