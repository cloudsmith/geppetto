package org.cloudsmith.geppetto.pp.dsl.serializer;

import java.util.Iterator;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.AttributeOperationsElements;
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
}
