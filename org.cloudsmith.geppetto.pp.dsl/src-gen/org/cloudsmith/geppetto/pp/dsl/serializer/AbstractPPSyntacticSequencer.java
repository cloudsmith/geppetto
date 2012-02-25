package org.cloudsmith.geppetto.pp.dsl.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("restriction")
public class AbstractPPSyntacticSequencer extends AbstractSyntacticSequencer {

	protected PPGrammarAccess grammarAccess;
	protected AbstractElementAlias match_AttributeOperations_CommaKeyword_2_q;
	protected AbstractElementAlias match_CollectExpression___LeftCurlyBracketKeyword_1_2_0_RightCurlyBracketKeyword_1_2_2__q;
	protected AbstractElementAlias match_DefinitionArgumentList_EndCommaParserRuleCall_3_q;
	protected AbstractElementAlias match_FunctionCall_EndCommaParserRuleCall_1_2_2_q;
	protected AbstractElementAlias match_LiteralHash_EndCommaParserRuleCall_3_q;
	protected AbstractElementAlias match_LiteralList_EndCommaParserRuleCall_3_q;
	protected AbstractElementAlias match_ResourceExpression_SemicolonKeyword_0_1_2_2_q;
	protected AbstractElementAlias match_ResourceExpression_SemicolonKeyword_1_3_2_q;
	protected AbstractElementAlias match_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (PPGrammarAccess) access;
		match_AttributeOperations_CommaKeyword_2_q = new TokenAlias(false, true, grammarAccess.getAttributeOperationsAccess().getCommaKeyword_2());
		match_CollectExpression___LeftCurlyBracketKeyword_1_2_0_RightCurlyBracketKeyword_1_2_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getCollectExpressionAccess().getLeftCurlyBracketKeyword_1_2_0()), new TokenAlias(false, false, grammarAccess.getCollectExpressionAccess().getRightCurlyBracketKeyword_1_2_2()));
		match_DefinitionArgumentList_EndCommaParserRuleCall_3_q = new TokenAlias(false, true, grammarAccess.getDefinitionArgumentListAccess().getEndCommaParserRuleCall_3());
		match_FunctionCall_EndCommaParserRuleCall_1_2_2_q = new TokenAlias(false, true, grammarAccess.getFunctionCallAccess().getEndCommaParserRuleCall_1_2_2());
		match_LiteralHash_EndCommaParserRuleCall_3_q = new TokenAlias(false, true, grammarAccess.getLiteralHashAccess().getEndCommaParserRuleCall_3());
		match_LiteralList_EndCommaParserRuleCall_3_q = new TokenAlias(false, true, grammarAccess.getLiteralListAccess().getEndCommaParserRuleCall_3());
		match_ResourceExpression_SemicolonKeyword_0_1_2_2_q = new TokenAlias(false, true, grammarAccess.getResourceExpressionAccess().getSemicolonKeyword_0_1_2_2());
		match_ResourceExpression_SemicolonKeyword_1_3_2_q = new TokenAlias(false, true, grammarAccess.getResourceExpressionAccess().getSemicolonKeyword_1_3_2());
		match_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q = new TokenAlias(false, true, grammarAccess.getSelectorExpressionAccess().getEndCommaParserRuleCall_1_2_0_3());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getEndCommaRule())
			return getendCommaToken(semanticObject, ruleCall, node);
		return "";
	}
	
	protected String getendCommaToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return ",";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_AttributeOperations_CommaKeyword_2_q.equals(syntax))
				emit_AttributeOperations_CommaKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_CollectExpression___LeftCurlyBracketKeyword_1_2_0_RightCurlyBracketKeyword_1_2_2__q.equals(syntax))
				emit_CollectExpression___LeftCurlyBracketKeyword_1_2_0_RightCurlyBracketKeyword_1_2_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_DefinitionArgumentList_EndCommaParserRuleCall_3_q.equals(syntax))
				emit_DefinitionArgumentList_EndCommaParserRuleCall_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_FunctionCall_EndCommaParserRuleCall_1_2_2_q.equals(syntax))
				emit_FunctionCall_EndCommaParserRuleCall_1_2_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_LiteralHash_EndCommaParserRuleCall_3_q.equals(syntax))
				emit_LiteralHash_EndCommaParserRuleCall_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_LiteralList_EndCommaParserRuleCall_3_q.equals(syntax))
				emit_LiteralList_EndCommaParserRuleCall_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ResourceExpression_SemicolonKeyword_0_1_2_2_q.equals(syntax))
				emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ResourceExpression_SemicolonKeyword_1_3_2_q.equals(syntax))
				emit_ResourceExpression_SemicolonKeyword_1_3_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q.equals(syntax))
				emit_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ','?
	 */
	protected void emit_AttributeOperations_CommaKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_CollectExpression___LeftCurlyBracketKeyword_1_2_0_RightCurlyBracketKeyword_1_2_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     endComma?
	 */
	protected void emit_DefinitionArgumentList_EndCommaParserRuleCall_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     endComma?
	 */
	protected void emit_FunctionCall_EndCommaParserRuleCall_1_2_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     endComma?
	 */
	protected void emit_LiteralHash_EndCommaParserRuleCall_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     endComma?
	 */
	protected void emit_LiteralList_EndCommaParserRuleCall_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ';'?
	 */
	protected void emit_ResourceExpression_SemicolonKeyword_0_1_2_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ';'?
	 */
	protected void emit_ResourceExpression_SemicolonKeyword_1_3_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     endComma?
	 */
	protected void emit_SelectorExpression_EndCommaParserRuleCall_1_2_0_3_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
