package org.cloudsmith.geppetto.pp.dsl.serializer;

import java.util.Iterator;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.AttributeOperationsElements;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.SelectorExpressionElements;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;

public class PPSemanticSequencer extends AbstractPPSemanticSequencer {
	/**
	 * Constraint:
	 * (attributes+=AttributeOperation (attributes+=AttributeOperation | attributes+=AttributeOperation)*)
	 */
	@Override
	protected void sequence_AttributeOperations(EObject context, AttributeOperations semanticObject) {

		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		AttributeOperationsElements access = grammarAccess.getAttributeOperationsAccess();
		Iterator<AttributeOperation> itor = semanticObject.getAttributes().iterator();
		int index = 0;
		while(itor.hasNext()) {
			AttributeOperation ao = itor.next();
			if(index == 0) {
				feeder.accept(access.getAttributesAttributeOperationParserRuleCall_0_0(), ao, index);
			}
			else
				feeder.accept(access.getAttributesAttributeOperationParserRuleCall_1_0_1_0(), ao, index);
			index++;
		}
		feeder.finish();

	}

	/**
	 * Overrides the default implementation because it believes that a LiteralBoolean with unset boolean value is
	 * transient (while it is not).
	 * TODO: LOG XTEXT ISSUE!!
	 * Constraint:
	 * value=BooleanValue
	 */
	@Override
	protected void sequence_LiteralBoolean(EObject context, LiteralBoolean semanticObject) {
		// if(errorAcceptor != null) {
		// if(transientValues.isValueTransient(semanticObject, PPPackage.Literals.LITERAL_BOOLEAN__VALUE) == ValueTransient.YES)
		// errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, PPPackage.Literals.LITERAL_BOOLEAN__VALUE));
		// }
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(
			grammarAccess.getLiteralBooleanAccess().getValueBooleanValueParserRuleCall_0(), semanticObject.isValue());
		feeder.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.serializer.AbstractPPSemanticSequencer#sequence_SelectorExpression(org.eclipse.emf.ecore.EObject,
	 * org.cloudsmith.geppetto.pp.SelectorExpression)
	 */
	@Override
	protected void sequence_SelectorExpression(EObject context, SelectorExpression semanticObject) {
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		SelectorExpressionElements access = grammarAccess.getSelectorExpressionAccess();

		feeder.accept(access.getSelectorExpressionLeftExprAction_1_0(), semanticObject.getLeftExpr());
		Iterator<Expression> itor = semanticObject.getParameters().iterator();
		int index = 0;
		// always serialize with the non-shortened form left ? { a => b, ... }
		while(itor.hasNext()) {
			Expression p = itor.next();
			if(index == 0)
				feeder.accept(access.getParametersSelectorEntryParserRuleCall_1_2_0_1_0(), p, index);
			else
				feeder.accept(access.getParametersSelectorEntryParserRuleCall_1_2_0_2_0_1_0(), p, index);
			index++;
		}
		feeder.finish();
		// super.sequence_SelectorExpression(context, semanticObject);
	}
}
