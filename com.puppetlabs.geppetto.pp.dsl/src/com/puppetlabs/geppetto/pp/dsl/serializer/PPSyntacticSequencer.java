package org.cloudsmith.geppetto.pp.dsl.serializer;

import java.util.List;

import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;

import com.google.inject.Inject;

public class PPSyntacticSequencer extends AbstractPPSyntacticSequencer {
	@Inject
	PPGrammarAccess grammar;

	/**
	 * Inserts an optional end-comma after attribute operations that are not followed by
	 * an additional body, and if there is no node-model. (If there is an additional body
	 * there will be a ';' separating the bodies, and ",;" is not wanted at the end.
	 */
	@Override
	protected void emit_AttributeOperations_CommaKeyword_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		EObject container = semanticObject.eContainer();

		boolean multiBody = (container instanceof ResourceBody && ((ResourceExpression) container.eContainer()).getResourceData().size() > 1);

		if(nodes == null && !multiBody)
			acceptUnassignedKeyword(grammar.getAttributeOperationsAccess().getCommaKeyword_2(), ",", null);
		else
			super.emit_AttributeOperations_CommaKeyword_2_q(semanticObject, transition, nodes);
	}

	/**
	 * Insert optional end-comma in list if there is no node model.
	 */
	@Override
	protected void emit_LiteralList_CommaKeyword_2_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		// Only enforce end comma when serializing without a node model
		if(nodes == null)
			acceptUnassignedKeyword(grammarAccess.getLiteralListAccess().getCommaKeyword_2_2(), ",", null);
		else
			super.emit_LiteralList_CommaKeyword_2_2_q(semanticObject, transition, nodes);
	}

	/**
	 * Insert optional end-semicolon if there is more than one body, and no node-model.
	 */
	@Override
	protected void emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		// Only enforce end-semicolon when serializing without a node model
		if(nodes == null && ((ResourceExpression) semanticObject).getResourceData().size() > 1)
			acceptUnassignedKeyword(grammar.getResourceExpressionAccess().getSemicolonKeyword_0_1_2_2(), ";", null);
		else
			// only if present in node model
			super.emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(semanticObject, transition, nodes);

	}

	/**
	 * Insert the optional end-semicolon if there is more than one body, and no node model.
	 */
	@Override
	protected void emit_ResourceExpression_SemicolonKeyword_1_3_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {

		// Only enforce end-semicolon when serializing without a node model
		if(nodes == null && ((ResourceExpression) semanticObject).getResourceData().size() > 1)
			acceptUnassignedKeyword(grammar.getResourceExpressionAccess().getSemicolonKeyword_1_3_2(), ";", null);
		else
			// only if present in node model
			super.emit_ResourceExpression_SemicolonKeyword_1_3_2_q(semanticObject, transition, nodes);
	}

	/**
	 * Insert the optional end-comma in selector expression if there is no node model.
	 */
	@Override
	protected void emit_SelectorExpression_CommaKeyword_1_2_0_3_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		// only insert end-comma when there is no node-model
		if(nodes == null)
			acceptUnassignedKeyword(grammar.getSelectorExpressionAccess().getCommaKeyword_1_2_0_3(), ",", null);
		else

			super.emit_SelectorExpression_CommaKeyword_1_2_0_3_q(semanticObject, transition, nodes);
	}
	// /**
	// * Insert the optional end-comma in selector expression if there is no node model.
	// */
	// @Override
	// protected void emit_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q(EObject semanticObject,
	// ISynNavigable transition, List<INode> nodes) {
	//
	// // only insert end-comma when there is no node-model
	// if(nodes == null)
	// acceptUnassignedTerminal(
	// grammar.getSelectorExpressionAccess().getEndCommaParserRuleCall_1_2_0_3(), ",", null);
	// else
	// super.emit_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q(semanticObject, transition, nodes);
	// }
}
