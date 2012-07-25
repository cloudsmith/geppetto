/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.formatting;

import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.ResourceStyle;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout.StatementStyle;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.ResourceExpressionElements;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.css.DefaultStylesheetProvider;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.Select;
import org.cloudsmith.xtext.dommodel.formatter.css.Select.Selector;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Provides the style sheet for PP.
 * 
 */
public class PPStylesheetProvider extends DefaultStylesheetProvider {

	@Inject
	private IStyleFactory styles;

	@Inject
	private IFunctionFactory functions;

	@Inject
	PPGrammarAccess grammarAccess;

	@Override
	public DomCSS get() {

		// look up grammar elements and create reusable selectors to make the style sheet more readable
		//
		final Selector attributeOperationsComma = Select.grammar(grammarAccess.getAttributeOperationsAccess().getCommaKeyword_1_0_0());

		final Selector resourceBodyTitleColon = Select.grammar(grammarAccess.getResourceBodyAccess().getColonKeyword_0_1());

		final Selector resourceSingleBodyTitle = Select.node(ResourceStyle.SINGLEBODY_TITLE);
		final Selector resourceSingleBodyNoTitle = Select.node(ResourceStyle.SINGLEBODY_NO_TITLE);
		final Selector inASingleBodiesResourceWithTitle = Select.containment(resourceSingleBodyTitle);
		final Selector inASingleBodiesResourceWithoutTitle = Select.containment(resourceSingleBodyNoTitle);

		final ResourceExpressionElements resourceExpressionAccess = grammarAccess.getResourceExpressionAccess();
		final Selector resourceLeftCurlyBracket = Select.grammar(resourceExpressionAccess.findKeywords("{"));
		final Selector resourceRightCurlyBracket = Select.grammar(resourceExpressionAccess.findKeywords("}"));
		final Selector resourceBodySemicolon = Select.grammar(resourceExpressionAccess.findKeywords(";"));
		final Selector optionalResourceEndBodySemicolon = Select.grammar(
			resourceExpressionAccess.getSemicolonKeyword_0_1_2_2(), //
			resourceExpressionAccess.getSemicolonKeyword_1_3_2());

		final Selector elseAndElsifKeywords = Select.grammar(Iterables.concat(
			grammarAccess.getIfExpressionAccess().findKeywords("else", "elsif"), //
			grammarAccess.getElseIfExpressionAccess().findKeywords("else", "elsif")));

		final Selector relationshipEdgeOperator = Select.grammar(grammarAccess.getRelationshipExpressionAccess().getOpNameEdgeOperatorParserRuleCall_1_1_0());
		final Selector atExpressionLeftBracket = Select.grammar(grammarAccess.getAtExpressionAccess().getLeftSquareBracketKeyword_1_1());

		// interpoation
		final Selector interpolationStart = Select.grammar(
			grammarAccess.getTextExpressionAccess().getDollarSignLeftCurlyBracketKeyword_1_1(), //
			grammarAccess.getUnquotedStringAccess().getDollarSignLeftCurlyBracketKeyword_1());
		final Selector interpolationEnd = Select.grammar(
			grammarAccess.getTextExpressionAccess().getRightCurlyBracketKeyword_1_3(), //
			grammarAccess.getUnquotedStringAccess().getRightCurlyBracketKeyword_3());

		final StyleSet resourceRightCurlyStyleNoDedent = StyleSet.withImmutableStyles(//
			styles.oneLineBreak(), //
			styles.noSpace(), //
			styles.dedent(0));

		final StyleSet noSpaceNoLine = StyleSet.withImmutableStyles(styles.noSpace(), styles.noLineBreak());
		final StyleSet noSpaceOneLine = StyleSet.withImmutableStyles(styles.noSpace(), styles.oneLineBreak());
		final StyleSet noSpaceTwoLines = StyleSet.withImmutableStyles(styles.noSpace(), styles.lineBreaks(2, 2, 2));
		final StyleSet oneSpaceNoLine = StyleSet.withImmutableStyles(styles.oneSpace(), styles.noLineBreak());

		DomCSS css = super.get();

		css.addRules(
			// RESOURCE
			// Resource expression with single titled body, should allow body to have title on same line
			// so, no line break, but one space here
			Select.and(inASingleBodiesResourceWithTitle, Select.whitespaceAfter(resourceLeftCurlyBracket)) //
			.withStyles( //
				styles.noLineBreak(), //
				styles.oneSpace(), //
				styles.indent(0))//
			.withRuleName("WsAfterLeftCurlyBracket.InASingleBodiedResourceWithTitle"),

			// Resource expression with single body without title, should not indent, as the body is
			// indented
			Select.and(inASingleBodiesResourceWithoutTitle, Select.whitespaceAfter(resourceLeftCurlyBracket)) //
			.withStyles( //
				styles.oneLineBreak(), //
				styles.indent(0)) //
			.withRuleName("WsAfterLeftCurlyBracket.InASingleBodiedResourceWithoutTitle"),

			Select.and(inASingleBodiesResourceWithTitle, Select.whitespaceBefore(resourceRightCurlyBracket)) //
			.withStyle(resourceRightCurlyStyleNoDedent)//
			.withRuleName("WsBeforeRightCurlyBracket.InASingleBodiesResourceWithTitle."),

			Select.and(inASingleBodiesResourceWithoutTitle, Select.whitespaceBefore(resourceRightCurlyBracket)) //
			.withStyle(resourceRightCurlyStyleNoDedent)//
			.withRuleName("WsBeforeRightCurlyBracket.InASingleBodiesResourceWithoutTitle."),

			// break bodies apart on body semicolon
			Select.whitespaceAfter(resourceBodySemicolon).withStyles(//
				styles.lineBreaks(2, 2, 2)).withRuleName("WsAfterResourceBodySemicolon"),
			Select.whitespaceBefore(resourceBodySemicolon).withStyles(//
				styles.noSpace(), //
				styles.noLineBreak())//
			.withRuleName("WsBeforeResourceBodySemicolon"),

			// only one line break after the end semicolon
			Select.whitespaceAfter(Select.important(optionalResourceEndBodySemicolon))//
			.withStyles(//
				styles.noSpace(), //
				styles.oneLineBreak())//
			.withRuleName("WsAfterImportantResourceEndBodySemicolon"),

			// Note: Closing Resource "}" has DEDENT BREAK "}" BREAK by default

			// RESOURCE BODY
			// No space before the resource body title colon
			Select.whitespaceBefore(resourceBodyTitleColon)//
			.withStyles(styles.noSpace())//
			.withRuleName("WsBeforeResourceBodyTitleColon"),

			// A resource body has its attribute operations indented after the title ':'
			//
			Select.whitespaceAfter(resourceBodyTitleColon)//
			.withStyles( //
				// styles.indent(), //
				styles.oneLineBreak())//
			.withRuleName("WsAfterResourceBodyTitleColon"), //

			// ATTRIBUTE OPERATIONS have each operation on a separate line
			// a => b, c => d
			// -------^
			Select.whitespaceAfter(attributeOperationsComma)//
			.withStyles(styles.oneLineBreak())//
			.withRuleName("WsAfterAttributeOperationsComma"),

			// xxx []
			// ---^
			Select.whitespaceBefore(atExpressionLeftBracket)//
			.withStyles(styles.noSpace(), //
				styles.noLineBreak())//
			.withRuleName("WsbeforeAtExpressionLeftBracket"),

			// @ name
			// -^
			// @@ name
			// --^
			Select.whitespaceAfter(Select.keyword("@"))//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsAfterKeyword@"),
			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getVirtualNameOrReferenceAccess().getExportedATBooleanParserRuleCall_1_0()))//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsAfterExportedMarker@"),

			Select.whitespaceBefore(elseAndElsifKeywords)//
			.withStyle(oneSpaceNoLine)//
			.withRuleName("WsBeforeKeywordsElseAndElseif"), //

			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getUnaryMinusExpressionAccess().getHyphenMinusKeyword_0()))//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsAfterUnaryMinus"), //

			Select.whitespaceAfter(Select.grammar(grammarAccess.getNotExpressionAccess().getExclamationMarkKeyword_0()))//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsAfterUnaryNotEclamationMark"), //

			// Relationships are typically resource {} -> resource {}
			Select.whitespaceBefore(relationshipEdgeOperator)//
			.withStyle(oneSpaceNoLine)//
			.withRuleName("WsBeforeRelationshipEdgeOperator"), //

			Select.whitespaceAfter(relationshipEdgeOperator)//
			.withStyle(oneSpaceNoLine)//
			.withRuleName("WsAfterRelationshipEdgeOperator"), //

			// --Selector Expression
			Select.whitespaceBefore(
				Select.grammar(grammarAccess.getSelectorExpressionAccess().getEndCommaParserRuleCall_1_2_0_3()))//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsBeforeSelectorExpressionEndComma"), //

			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getSelectorExpressionAccess().getCommaKeyword_1_2_0_2_0_0()))//
			.withStyle(noSpaceOneLine)//
			.withRuleName("WsAfterSelectorExpressionComma"), //

			// --Interpolation
			Select.whitespaceAfter(interpolationStart)//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsAfterInterpolationStart"), //

			Select.whitespaceBefore(interpolationEnd)//
			.withStyle(noSpaceNoLine)//
			.withRuleName("WsBeforeInterpolationEnd"), //

			// --Statements
			// ws before subsequent statements in a block
			Select.whitespaceBefore(Select.and(//
				Select.node(StatementStyle.STATEMENT), //
				Select.node(StatementStyle.BLOCK), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyle(noSpaceTwoLines)//
			.withRuleName("WsBeforeSubsequentStatementInABlock"),

			Select.whitespaceBefore(Select.and(//
				Select.node(StatementStyle.STATEMENT), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyle(noSpaceOneLine)//
			.withRuleName("WsBeforeFirstStatementInABlock"), //

			// styles.noSpace(), styles.lineBreaks(functions.oneLineBreakUnlessPredecessorIsLinebreakingComment())),

			// No space between function name and ()
			Select.whitespaceBefore(
				Select.grammar(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1_1()))//
			.withStyle(noSpaceNoLine) //
			.withRuleName("WsBeforeFunctionCallLeftParenthesis"),

			// PP parser includes trailing WS in ML comment, and SL comment always ends with line break
			Select.whitespaceAfter(Select.node(NodeType.COMMENT))//
			.withStyle(styles.spacing(functions.oneSpaceUnlessPredecessorIsWhitespaceTerminated()))//
			.withRuleName("WsAfterComment")
		// ,

		// Select.whitespaceBefore(Select.grammar(grammarAccess.getLiteralListAccess().getEndCommaParserRuleCall_3())).withStyle(//
		// noSpaceNoLine)

		);
		return css;
	}
}
