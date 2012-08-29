package org.cloudsmith.geppetto.pp.dsl.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.cloudsmith.geppetto.pp.AdditiveExpression;
import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
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
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HashEntry;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.InExpression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralClass;
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
import org.cloudsmith.geppetto.pp.UnlessExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.VirtualCollectQuery;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public abstract class AbstractPPSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private PPGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == PPPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case PPPackage.ADDITIVE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0()) {
					sequence_AdditiveExpression(context, (AdditiveExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.AND_EXPRESSION:
				if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_AndExpression(context, (AndExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.APPEND_EXPRESSION:
				if(context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_AppendExpression(context, (AppendExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.ASSIGNMENT_EXPRESSION:
				if(context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_AssignmentExpression(context, (AssignmentExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.AT_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_AtExpression(context, (AtExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.ATTRIBUTE_OPERATION:
				if(context == grammarAccess.getAttributeOperationRule()) {
					sequence_AttributeOperation(context, (AttributeOperation) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.ATTRIBUTE_OPERATIONS:
				if(context == grammarAccess.getAttributeOperationsRule()) {
					sequence_AttributeOperations(context, (AttributeOperations) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.CASE:
				if(context == grammarAccess.getCaseRule()) {
					sequence_Case(context, (Case) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.CASE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCaseExpressionRule() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_CaseExpression(context, (CaseExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.COLLECT_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_CollectExpression(context, (CollectExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.DEFINITION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getDefinitionRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_Definition(context, (Definition) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.DEFINITION_ARGUMENT:
				if(context == grammarAccess.getDefinitionArgumentRule()) {
					sequence_DefinitionArgument(context, (DefinitionArgument) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.DEFINITION_ARGUMENT_LIST:
				if(context == grammarAccess.getDefinitionArgumentListRule()) {
					sequence_DefinitionArgumentList(context, (DefinitionArgumentList) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.DOUBLE_QUOTED_STRING:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getDoubleQuotedStringRule() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralNameOrStringRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getQuotedStringRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getStringExpressionRule() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_DoubleQuotedString(context, (DoubleQuotedString) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.ELSE_EXPRESSION:
				if(context == grammarAccess.getElseExpressionRule()) {
					sequence_ElseExpression(context, (ElseExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.ELSE_IF_EXPRESSION:
				if(context == grammarAccess.getElseIfExpressionRule()) {
					sequence_ElseIfExpression(context, (ElseIfExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.EQUALITY_EXPRESSION:
				if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_EqualityExpression(context, (EqualityExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.EXPORTED_COLLECT_QUERY:
				if(context == grammarAccess.getCollectQueryRule() ||
				   context == grammarAccess.getExportedCollectQueryRule()) {
					sequence_ExportedCollectQuery(context, (ExportedCollectQuery) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.EXPR_LIST:
				if(context == grammarAccess.getExpressionListRule()) {
					sequence_ExpressionList(context, (ExprList) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.EXPRESSION_TE:
				if(context == grammarAccess.getTextExpressionRule()) {
					sequence_TextExpression(context, (ExpressionTE) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.FUNCTION_CALL:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_FunctionCall(context, (FunctionCall) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.HASH_ENTRY:
				if(context == grammarAccess.getHashEntryRule()) {
					sequence_HashEntry(context, (HashEntry) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.HOST_CLASS_DEFINITION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostClassDefinitionRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_HostClassDefinition(context, (HostClassDefinition) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.IF_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getIfExpressionRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_IfExpression(context, (IfExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.IMPORT_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getImportExpressionRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_ImportExpression(context, (ImportExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.IN_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0()) {
					sequence_InExpression(context, (InExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_BOOLEAN:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralBooleanRule() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralBoolean(context, (LiteralBoolean) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_CLASS:
				if(context == grammarAccess.getLiteralClassRule() ||
				   context == grammarAccess.getParentNameRule()) {
					sequence_LiteralClass(context, (LiteralClass) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_DEFAULT:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralDefaultRule() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getParentNameRule() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralDefault(context, (LiteralDefault) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_HASH:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralHashRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralHash(context, (LiteralHash) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_LIST:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralListRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralList(context, (LiteralList) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_NAME:
				if(context == grammarAccess.getLiteralNameRule() ||
				   context == grammarAccess.getLiteralNameOrStringRule()) {
					sequence_LiteralName(context, (LiteralName) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_NAME_OR_REFERENCE:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralNameOrReferenceRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getParentNameRule() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralNameOrReference(context, (LiteralNameOrReference) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_REGEX:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralRegexRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralRegex(context, (LiteralRegex) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.LITERAL_UNDEF:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralUndefRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_LiteralUndef(context, (LiteralUndef) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.MATCHING_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0()) {
					sequence_MatchingExpression(context, (MatchingExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.MULTIPLICATIVE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0()) {
					sequence_MultiplicativeExpression(context, (MultiplicativeExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.NODE_DEFINITION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getNodeDefinitionRule() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_NodeDefinition(context, (NodeDefinition) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.OR_EXPRESSION:
				if(context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_OrExpression(context, (OrExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.PARENTHESISED_EXPRESSION:
				if(context == grammarAccess.getExpressionWithHiddenRule()) {
					sequence_ExpressionWithHidden(context, (ParenthesisedExpression) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getParenthisedExpressionRule() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_ParenthisedExpression(context, (ParenthesisedExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.PUPPET_MANIFEST:
				if(context == grammarAccess.getPuppetManifestRule()) {
					sequence_PuppetManifest(context, (PuppetManifest) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.RELATIONAL_EXPRESSION:
				if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_RelationalExpression(context, (RelationalExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.RELATIONSHIP_EXPRESSION:
				if(context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_RelationshipExpression(context, (RelationshipExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.RESOURCE_BODY:
				if(context == grammarAccess.getResourceBodyRule()) {
					sequence_ResourceBody(context, (ResourceBody) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.RESOURCE_EXPRESSION:
				if(context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0()) {
					sequence_ResourceExpression(context, (ResourceExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.SELECTOR_ENTRY:
				if(context == grammarAccess.getSelectorEntryRule()) {
					sequence_SelectorEntry(context, (SelectorEntry) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.SELECTOR_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_SelectorExpression(context, (SelectorExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.SHIFT_EXPRESSION:
				if(context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0()) {
					sequence_ShiftExpression(context, (ShiftExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.SINGLE_QUOTED_STRING:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralNameOrStringRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getQuotedStringRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getSingleQuotedStringRule() ||
				   context == grammarAccess.getStringExpressionRule() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_SingleQuotedString(context, (SingleQuotedString) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.UNARY_MINUS_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryMinusExpressionRule() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_UnaryMinusExpression(context, (UnaryMinusExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.UNARY_NOT_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getNotExpressionRule() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule()) {
					sequence_NotExpression(context, (UnaryNotExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.UNLESS_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule() ||
				   context == grammarAccess.getUnlessExpressionRule()) {
					sequence_UnlessExpression(context, (UnlessExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.UNQUOTED_STRING:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getHostReferenceRule() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getLiteralExpressionRule() ||
				   context == grammarAccess.getLiteralNameOrStringRule() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getStringExpressionRule() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule() ||
				   context == grammarAccess.getUnquotedStringRule()) {
					sequence_UnquotedString(context, (UnquotedString) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.VARIABLE_EXPRESSION:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule() ||
				   context == grammarAccess.getVariableExpressionRule()) {
					sequence_VariableExpression(context, (VariableExpression) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.VARIABLE_TE:
				if(context == grammarAccess.getTextExpressionRule()) {
					sequence_TextExpression(context, (VariableTE) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.VERBATIM_TE:
				if(context == grammarAccess.getStringPartRule()) {
					sequence_StringPart(context, (VerbatimTE) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getTextExpressionRule()) {
					sequence_TextExpression(context, (VerbatimTE) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.VIRTUAL_COLLECT_QUERY:
				if(context == grammarAccess.getCollectQueryRule() ||
				   context == grammarAccess.getVirtualCollectQueryRule()) {
					sequence_VirtualCollectQuery(context, (VirtualCollectQuery) semanticObject); 
					return; 
				}
				else break;
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE:
				if(context == grammarAccess.getAdditiveExpressionRule() ||
				   context == grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAndExpressionRule() ||
				   context == grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAppendExpressionRule() ||
				   context == grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAssignmentExpressionRule() ||
				   context == grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getAtExpressionRule() ||
				   context == grammarAccess.getAtExpressionAccess().getAtExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getCollectExpressionRule() ||
				   context == grammarAccess.getCollectExpressionAccess().getCollectExpressionClassReferenceAction_1_0() ||
				   context == grammarAccess.getEqualityExpressionRule() ||
				   context == grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getExpressionListRule() ||
				   context == grammarAccess.getExpressionListAccess().getExprListExpressionsAction_1_0() ||
				   context == grammarAccess.getFunctionCallRule() ||
				   context == grammarAccess.getFunctionCallAccess().getFunctionCallLeftExprAction_1_0() ||
				   context == grammarAccess.getInExpressionRule() ||
				   context == grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMatchingExpressionRule() ||
				   context == grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getMultiplicativeExpressionRule() ||
				   context == grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getOrExpressionRule() ||
				   context == grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getPrimaryExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionRule() ||
				   context == grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getRelationshipExpressionRule() ||
				   context == grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getResourceExpressionRule() ||
				   context == grammarAccess.getResourceExpressionAccess().getResourceExpressionResourceExprAction_0_1_0() ||
				   context == grammarAccess.getSelectorEntryRule() ||
				   context == grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0() ||
				   context == grammarAccess.getSelectorExpressionRule() ||
				   context == grammarAccess.getSelectorExpressionAccess().getSelectorExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getShiftExpressionRule() ||
				   context == grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0() ||
				   context == grammarAccess.getUnaryOrHigherExpressionRule() ||
				   context == grammarAccess.getVirtualNameOrReferenceRule()) {
					sequence_VirtualNameOrReference(context, (VirtualNameOrReference) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (leftExpr=AdditiveExpression_AdditiveExpression_1_0 opName=AdditiveOperator rightExpr=MultiplicativeExpression)
	 */
	protected void sequence_AdditiveExpression(EObject context, AdditiveExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAdditiveExpressionAccess().getAdditiveExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getAdditiveExpressionAccess().getOpNameAdditiveOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getAdditiveExpressionAccess().getRightExprMultiplicativeExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=AndExpression_AndExpression_1_0 rightExpr=RelationalExpression)
	 */
	protected void sequence_AndExpression(EObject context, AndExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAndExpressionAccess().getAndExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getAndExpressionAccess().getRightExprRelationalExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=AppendExpression_AppendExpression_1_0 rightExpr=OrExpression)
	 */
	protected void sequence_AppendExpression(EObject context, AppendExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAppendExpressionAccess().getAppendExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getAppendExpressionAccess().getRightExprOrExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=AssignmentExpression_AssignmentExpression_1_0 rightExpr=AppendExpression)
	 */
	protected void sequence_AssignmentExpression(EObject context, AssignmentExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignmentExpressionAccess().getAssignmentExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getAssignmentExpressionAccess().getRightExprAppendExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((leftExpr=AtExpression_AtExpression_1_0 (parameters+=Expression parameters+=Expression*)?) | leftExpr=AtExpression_AtExpression_1_0)
	 */
	protected void sequence_AtExpression(EObject context, AtExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (key=attributeName ((op='=>' | op='+>') value=Expression)?)
	 */
	protected void sequence_AttributeOperation(EObject context, AttributeOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (attributes+=AttributeOperation (attributes+=AttributeOperation | attributes+=AttributeOperation)*)
	 */
	protected void sequence_AttributeOperations(EObject context, AttributeOperations semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (switchExpr=AssignmentExpression cases+=Case*)
	 */
	protected void sequence_CaseExpression(EObject context, CaseExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (values+=AssignmentExpression values+=AssignmentExpression* statements+=ExpressionList*)
	 */
	protected void sequence_Case(EObject context, Case semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (classReference=CollectExpression_CollectExpression_1_0 query=CollectQuery attributes=AttributeOperations?)
	 */
	protected void sequence_CollectExpression(EObject context, CollectExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((arguments+=DefinitionArgument arguments+=DefinitionArgument*)?)
	 */
	protected void sequence_DefinitionArgumentList(EObject context, DefinitionArgumentList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (argName=UNION_VARIABLE_OR_NAME ((op='=' | op='=>') value=AssignmentExpression)?)
	 */
	protected void sequence_DefinitionArgument(EObject context, DefinitionArgument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (className=classname arguments=DefinitionArgumentList? statements+=ExpressionList*)
	 */
	protected void sequence_Definition(EObject context, Definition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (stringPart+=TextExpression*)
	 */
	protected void sequence_DoubleQuotedString(EObject context, DoubleQuotedString semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (statements+=ExpressionList*)
	 */
	protected void sequence_ElseExpression(EObject context, ElseExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (condExpr=AssignmentExpression thenStatements+=ExpressionList* (elseStatement=ElseIfExpression | elseStatement=ElseExpression)?)
	 */
	protected void sequence_ElseIfExpression(EObject context, ElseIfExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=EqualityExpression_EqualityExpression_1_0 opName=EqualityOperator rightExpr=ShiftExpression)
	 */
	protected void sequence_EqualityExpression(EObject context, EqualityExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEqualityExpressionAccess().getEqualityExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getEqualityExpressionAccess().getOpNameEqualityOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getEqualityExpressionAccess().getRightExprShiftExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expr=Expression?)
	 */
	protected void sequence_ExportedCollectQuery(EObject context, ExportedCollectQuery semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expressions+=ExpressionList_ExprList_1_0 expressions+=RelationshipExpression expressions+=RelationshipExpression*)
	 */
	protected void sequence_ExpressionList(EObject context, ExprList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expr=AssignmentExpression?)
	 */
	protected void sequence_ExpressionWithHidden(EObject context, ParenthesisedExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((leftExpr=FunctionCall_FunctionCall_1_0 (parameters+=Expression parameters+=Expression*)?) | leftExpr=FunctionCall_FunctionCall_1_0)
	 */
	protected void sequence_FunctionCall(EObject context, FunctionCall semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (key=LiteralNameOrString value=AssignmentExpression)
	 */
	protected void sequence_HashEntry(EObject context, HashEntry semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.HASH_ENTRY__KEY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.HASH_ENTRY__KEY));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.HASH_ENTRY__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.HASH_ENTRY__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getHashEntryAccess().getKeyLiteralNameOrStringParserRuleCall_0_0(), semanticObject.getKey());
		feeder.accept(grammarAccess.getHashEntryAccess().getValueAssignmentExpressionParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (className=classname arguments=DefinitionArgumentList? parent=ParentName? statements+=ExpressionList*)
	 */
	protected void sequence_HostClassDefinition(EObject context, HostClassDefinition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (condExpr=AssignmentExpression thenStatements+=ExpressionList* (elseStatement=ElseIfExpression | elseStatement=ElseExpression)?)
	 */
	protected void sequence_IfExpression(EObject context, IfExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((values+=QuotedString values+=QuotedString*)?)
	 */
	protected void sequence_ImportExpression(EObject context, ImportExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=InExpression_InExpression_1_0 opName='in' rightExpr=UnaryOrHigherExpression)
	 */
	protected void sequence_InExpression(EObject context, InExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getInExpressionAccess().getInExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getInExpressionAccess().getOpNameInKeyword_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getInExpressionAccess().getRightExprUnaryOrHigherExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=BooleanValue
	 */
	protected void sequence_LiteralBoolean(EObject context, LiteralBoolean semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.LITERAL_BOOLEAN__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.LITERAL_BOOLEAN__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLiteralBooleanAccess().getValueBooleanValueParserRuleCall_0(), semanticObject.isValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {LiteralClass}
	 */
	protected void sequence_LiteralClass(EObject context, LiteralClass semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {LiteralDefault}
	 */
	protected void sequence_LiteralDefault(EObject context, LiteralDefault semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((elements+=HashEntry elements+=HashEntry*)?)
	 */
	protected void sequence_LiteralHash(EObject context, LiteralHash semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((elements+=AssignmentExpression elements+=AssignmentExpression*)?)
	 */
	protected void sequence_LiteralList(EObject context, LiteralList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=name
	 */
	protected void sequence_LiteralNameOrReference(EObject context, LiteralNameOrReference semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLiteralNameOrReferenceAccess().getValueNameParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=name
	 */
	protected void sequence_LiteralName(EObject context, LiteralName semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.LITERAL_NAME__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.LITERAL_NAME__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLiteralNameAccess().getValueNameParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=REGULAR_EXPRESSION
	 */
	protected void sequence_LiteralRegex(EObject context, LiteralRegex semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.LITERAL_REGEX__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.LITERAL_REGEX__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLiteralRegexAccess().getValueREGULAR_EXPRESSIONTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {LiteralUndef}
	 */
	protected void sequence_LiteralUndef(EObject context, LiteralUndef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=MatchingExpression_MatchingExpression_1_0 opName=MatchingOperator rightExpr=LiteralRegex)
	 */
	protected void sequence_MatchingExpression(EObject context, MatchingExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMatchingExpressionAccess().getMatchingExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getMatchingExpressionAccess().getOpNameMatchingOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getMatchingExpressionAccess().getRightExprLiteralRegexParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=MultiplicativeExpression_MultiplicativeExpression_1_0 opName=MultiplicativeOperator rightExpr=MatchingExpression)
	 */
	protected void sequence_MultiplicativeExpression(EObject context, MultiplicativeExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMultiplicativeExpressionAccess().getMultiplicativeExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getMultiplicativeExpressionAccess().getOpNameMultiplicativeOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getMultiplicativeExpressionAccess().getRightExprMatchingExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (hostNames+=HostReference hostNames+=HostReference* parentName=HostReference? statements+=ExpressionList*)
	 */
	protected void sequence_NodeDefinition(EObject context, NodeDefinition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=CollectExpression
	 */
	protected void sequence_NotExpression(EObject context, UnaryNotExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.UNARY_EXPRESSION__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.UNARY_EXPRESSION__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNotExpressionAccess().getExprCollectExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=OrExpression_OrExpression_1_0 rightExpr=AndExpression)
	 */
	protected void sequence_OrExpression(EObject context, OrExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getOrExpressionAccess().getOrExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getOrExpressionAccess().getRightExprAndExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expr=AssignmentExpression?)
	 */
	protected void sequence_ParenthisedExpression(EObject context, ParenthesisedExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (statements+=ExpressionList*)
	 */
	protected void sequence_PuppetManifest(EObject context, PuppetManifest semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=RelationalExpression_RelationalExpression_1_0 opName=RelationalOperator rightExpr=EqualityExpression)
	 */
	protected void sequence_RelationalExpression(EObject context, RelationalExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRelationalExpressionAccess().getRelationalExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getRelationalExpressionAccess().getOpNameRelationalOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getRelationalExpressionAccess().getRightExprEqualityExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=RelationshipExpression_RelationshipExpression_1_0 opName=EdgeOperator rightExpr=ResourceExpression)
	 */
	protected void sequence_RelationshipExpression(EObject context, RelationshipExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRelationshipExpressionAccess().getRelationshipExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getRelationshipExpressionAccess().getOpNameEdgeOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getRelationshipExpressionAccess().getRightExprResourceExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     ((nameExpr=Expression attributes=AttributeOperations?) | attributes=AttributeOperations)
	 */
	protected void sequence_ResourceBody(EObject context, ResourceBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (resourceExpr=ResourceExpression_ResourceExpression_0_1_0 (resourceData+=ResourceBody resourceData+=ResourceBody*)?) | 
	 *         resourceExpr=ResourceExpression_ResourceExpression_0_1_0 | 
	 *         (resourceExpr=LiteralClass (resourceData+=ResourceBody resourceData+=ResourceBody*)?)
	 *     )
	 */
	protected void sequence_ResourceExpression(EObject context, ResourceExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=SelectorEntry_SelectorEntry_1_0 rightExpr=Expression)
	 */
	protected void sequence_SelectorEntry(EObject context, SelectorEntry semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getSelectorEntryAccess().getRightExprExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         leftExpr=SelectorExpression_SelectorExpression_1_0 
	 *         ((parameters+=SelectorEntry (parameters+=SelectorEntry | parameters+=SelectorEntry)*) | parameters+=SelectorEntry)
	 *     )
	 */
	protected void sequence_SelectorExpression(EObject context, SelectorExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftExpr=ShiftExpression_ShiftExpression_1_0 opName=ShiftOperator rightExpr=AdditiveExpression)
	 */
	protected void sequence_ShiftExpression(EObject context, ShiftExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR));
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getShiftExpressionAccess().getShiftExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		feeder.accept(grammarAccess.getShiftExpressionAccess().getOpNameShiftOperatorParserRuleCall_1_1_0(), semanticObject.getOpName());
		feeder.accept(grammarAccess.getShiftExpressionAccess().getRightExprAdditiveExpressionParserRuleCall_1_2_0(), semanticObject.getRightExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     text=sqText
	 */
	protected void sequence_SingleQuotedString(EObject context, SingleQuotedString semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.SINGLE_QUOTED_STRING__TEXT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.SINGLE_QUOTED_STRING__TEXT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSingleQuotedStringAccess().getTextSqTextParserRuleCall_1_0(), semanticObject.getText());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (text=doubleStringCharacters?)
	 */
	protected void sequence_StringPart(EObject context, VerbatimTE semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expression=ExpressionWithHidden
	 */
	protected void sequence_TextExpression(EObject context, ExpressionTE semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.EXPRESSION_TE__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.EXPRESSION_TE__EXPRESSION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTextExpressionAccess().getExpressionExpressionWithHiddenParserRuleCall_1_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     varName=dollarVariable
	 */
	protected void sequence_TextExpression(EObject context, VariableTE semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.VARIABLE_TE__VAR_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.VARIABLE_TE__VAR_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTextExpressionAccess().getVarNameDollarVariableParserRuleCall_2_1_0(), semanticObject.getVarName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     text=doubleStringCharacters
	 */
	protected void sequence_TextExpression(EObject context, VerbatimTE semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.VERBATIM_TE__TEXT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.VERBATIM_TE__TEXT));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTextExpressionAccess().getTextDoubleStringCharactersParserRuleCall_0_1_0(), semanticObject.getText());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     expr=CollectExpression
	 */
	protected void sequence_UnaryMinusExpression(EObject context, UnaryMinusExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.UNARY_EXPRESSION__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.UNARY_EXPRESSION__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUnaryMinusExpressionAccess().getExprCollectExpressionParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (condExpr=AssignmentExpression thenStatements+=ExpressionList*)
	 */
	protected void sequence_UnlessExpression(EObject context, UnlessExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expression=AssignmentExpression?)
	 */
	protected void sequence_UnquotedString(EObject context, UnquotedString semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     varName=dollarVariable
	 */
	protected void sequence_VariableExpression(EObject context, VariableExpression semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVariableExpressionAccess().getVarNameDollarVariableParserRuleCall_0(), semanticObject.getVarName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expr=Expression?)
	 */
	protected void sequence_VirtualCollectQuery(EObject context, VirtualCollectQuery semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (exported=ATBoolean? value=unionNameOrReference)
	 */
	protected void sequence_VirtualNameOrReference(EObject context, VirtualNameOrReference semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
