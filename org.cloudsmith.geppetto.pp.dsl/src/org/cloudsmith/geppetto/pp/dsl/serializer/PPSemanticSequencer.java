package org.cloudsmith.geppetto.pp.dsl.serializer;

import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;

public class PPSemanticSequencer extends AbstractPPSemanticSequencer {
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
