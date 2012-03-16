package org.cloudsmith.geppetto.pp.dsl.serializer;

import java.util.List;

import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;

import com.google.inject.Inject;

public class PPSyntacticSequencer extends AbstractPPSyntacticSequencer {
	@Inject
	PPGrammarAccess grammar;

	/**
	 * The "," between AttributeOperation(s) is optional in the grammar for validation purposes, but
	 * is required for a valid textual representation.
	 */
	@Override
	protected void emit_AttributeOperations_CommaKeyword_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		EObject container = semanticObject.eContainer();

		boolean multiBody = (container instanceof ResourceBody && ((ResourceExpression) container.eContainer()).getResourceData().size() > 1);

		if(!multiBody)
			acceptUnassignedKeyword(grammar.getAttributeOperationsAccess().getCommaKeyword_2(), ",", nodes == null ||
					nodes.isEmpty()
					? null
					: (ILeafNode) nodes.get(0));
	}

	/**
	 * The ";" after last body is optional, but should always be there if there is more than one body.
	 */
	@Override
	protected void emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {
		if(((ResourceExpression) semanticObject).getResourceData().size() > 1)
			acceptUnassignedKeyword(
				grammar.getResourceExpressionAccess().getSemicolonKeyword_0_1_2_2(), ";",
				nodes == null || nodes.isEmpty()
						? null
						: (ILeafNode) nodes.get(0));
		else
			// only if present in node model
			super.emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(semanticObject, transition, nodes);

	}

	@Override
	protected void emit_ResourceExpression_SemicolonKeyword_1_3_2_q(EObject semanticObject, ISynNavigable transition,
			List<INode> nodes) {

		if(((ResourceExpression) semanticObject).getResourceData().size() > 1)
			acceptUnassignedKeyword(
				grammar.getResourceExpressionAccess().getSemicolonKeyword_1_3_2(), ";",
				nodes == null || nodes.isEmpty()
						? null
						: (ILeafNode) nodes.get(0));
		else
			// only if present in node model
			super.emit_ResourceExpression_SemicolonKeyword_1_3_2_q(semanticObject, transition, nodes);
	}

	@Override
	protected void emit_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q(EObject semanticObject,
			ISynNavigable transition, List<INode> nodes) {
		acceptUnassignedTerminal(
			grammar.getSelectorExpressionAccess().getEndCommaParserRuleCall_1_2_0_3(), ",",
			nodes == null || nodes.isEmpty()
					? null
					: (ILeafNode) nodes.get(0));
	}
}
