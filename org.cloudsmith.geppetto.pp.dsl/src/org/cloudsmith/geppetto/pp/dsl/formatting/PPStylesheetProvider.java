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
		final Selector resourceCompactable = Select.node(ResourceStyle.COMPACTABLE);
		final Selector inline = Select.node(StatementStyle.INLINE);
		final Selector notInline = Select.not(Select.node(StatementStyle.INLINE));

		final Selector lambdaLeftBrace = Select.grammar(
			grammarAccess.getJava8LambdaAccess().getLeftCurlyBracketKeyword_5(),
			grammarAccess.getRubyLambdaAccess().getLAMBDATerminalRuleCall_0());

		final Selector lambdaRightBrace = Select.grammar(
			grammarAccess.getJava8LambdaAccess().getRightCurlyBracketKeyword_7(),
			grammarAccess.getRubyLambdaAccess().getRightCurlyBracketKeyword_6());

		final Selector inCompactableResource = Select.containment(resourceCompactable);
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

		// interpolation
		final Selector interpolationStart = Select.grammar(
			grammarAccess.getTextExpressionAccess().getDollarSignLeftCurlyBracketKeyword_1_1(), //
			grammarAccess.getUnquotedStringAccess().getDollarSignLeftCurlyBracketKeyword_1());
		final Selector interpolationEnd = Select.grammar(
			grammarAccess.getTextExpressionAccess().getRightCurlyBracketKeyword_1_3(), //
			grammarAccess.getUnquotedStringAccess().getRightCurlyBracketKeyword_3());

		final StyleSet resourceRightCurlyStyleNoDedent = StyleSet.withImmutableStyles(//
			styles.oneLineBreak(), //
			styles.noSpaceUnlessWrapped(), //
			styles.dedent(0));

		final StyleSet noSpaceUnlessWrappedNoLine = StyleSet.withImmutableStyles(
			styles.noSpaceUnlessWrapped(), styles.noLineBreak());
		// final StyleSet noSpaceNoLine = StyleSet.withImmutableStyles(styles.noSpace(), styles.noLineBreak());
		final StyleSet noSpaceOneLine = StyleSet.withImmutableStyles(
			styles.noSpaceUnlessWrapped(), styles.oneLineBreak());
		final StyleSet noSpaceOneLineOptionallyTwo = StyleSet.withImmutableStyles(
			styles.noSpaceUnlessWrapped(), styles.lineBreaks(1, 1, 2));
		final StyleSet noSpaceTwoLines = StyleSet.withImmutableStyles(
			styles.noSpaceUnlessWrapped(), styles.lineBreaks(2, 2, 2));
		final StyleSet oneSpaceNoLine = StyleSet.withImmutableStyles(styles.oneSpace(), styles.noLineBreak());
		final StyleSet oneOptionalSpaceNoLine = StyleSet.withImmutableStyles(//
			styles.oneSpace(), styles.noLineBreak());

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

			Select.and(Select.important(inCompactableResource), Select.whitespaceBefore(resourceRightCurlyBracket)) //
			.withStyles(styles.noLineBreak(), styles.oneSpace()) //
			.withRuleName("WsBeforeRightCurlyBracket-Compactable"), //

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
				styles.noSpaceUnlessWrapped(), //
				styles.noLineBreak())//
			.withRuleName("WsBeforeResourceBodySemicolon"),

			// only one line break after the end semicolon
			Select.whitespaceAfter(Select.important(optionalResourceEndBodySemicolon))//
			.withStyles(//
				styles.noSpaceUnlessWrapped(), //
				styles.oneLineBreak())//
			.withRuleName("WsAfterImportantResourceEndBodySemicolon"),

			// Note: Closing Resource "}" has DEDENT BREAK "}" BREAK by default

			// RESOURCE BODY
			// No space before the resource body title colon
			Select.whitespaceBefore(resourceBodyTitleColon)//
			.withStyles(styles.noSpaceUnlessWrapped())//
			.withRuleName("WsBeforeResourceBodyTitleColon"),

			// A resource body has its attribute operations indented after the title ':'
			// If compact, stay on the same line
			Select.and(inCompactableResource, Select.whitespaceAfter(resourceBodyTitleColon)) //
			.withStyle(oneSpaceNoLine) //
			.withRuleName("WsAfterResourceBodyTitleColon-Compactable"), //

			Select.whitespaceAfter(resourceBodyTitleColon)//
			.withStyles( //
				styles.oneLineBreak())//
			.withRuleName("WsAfterResourceBodyTitleColon"), //

			// ATTRIBUTE OPERATIONS have each operation on a separate line
			// a => b, c => d
			// -------^
			// If compact, stay on the same line
			Select.and(inCompactableResource, Select.whitespaceAfter(attributeOperationsComma)) //
			.withStyle(oneSpaceNoLine) //
			.withRuleName("WsAfterAttributeOperationsComma-Compactable"), //

			Select.whitespaceAfter(attributeOperationsComma)//
			.withStyles(styles.oneLineBreak())//
			.withRuleName("WsAfterAttributeOperationsComma"),

			// xxx []
			// ---^
			Select.whitespaceBefore(atExpressionLeftBracket)//
			.withStyles(styles.noSpaceUnlessWrapped(), //
				styles.noLineBreak())//
			.withRuleName("WsbeforeAtExpressionLeftBracket"),

			// @ name
			// -^
			// @@ name
			// --^
			Select.whitespaceAfter(Select.keyword("@"))//
			.withStyle(noSpaceUnlessWrappedNoLine)//
			.withRuleName("WsAfterKeyword@"),
			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getVirtualNameOrReferenceAccess().getExportedATBooleanParserRuleCall_1_0()))//
			.withStyle(noSpaceUnlessWrappedNoLine)//
			.withRuleName("WsAfterExportedMarker@"),

			Select.whitespaceBefore(elseAndElsifKeywords)//
			.withStyle(oneSpaceNoLine)//
			.withRuleName("WsBeforeKeywordsElseAndElseif"), //

			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getUnaryMinusExpressionAccess().getHyphenMinusKeyword_0()))//
			.withStyle(noSpaceUnlessWrappedNoLine)//
			.withRuleName("WsAfterUnaryMinus"), //

			Select.whitespaceAfter(Select.grammar(grammarAccess.getNotExpressionAccess().getExclamationMarkKeyword_0()))//
			.withStyle(noSpaceUnlessWrappedNoLine)//
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
				Select.grammar(grammarAccess.getSelectorExpressionAccess().getCommaKeyword_1_2_0_3()))//
			.withStyle(noSpaceUnlessWrappedNoLine)//
			.withRuleName("WsBeforeSelectorExpressionEndComma"), //

			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getSelectorExpressionAccess().getCommaKeyword_1_2_0_2_0_0()))//
			.withStyle(noSpaceOneLine)//
			.withRuleName("WsAfterSelectorExpressionComma"), //

			Select.whitespaceAfter(//
				Select.grammar(grammarAccess.getSelectorExpressionAccess().getRightCurlyBracketKeyword_1_2_0_4())) //
			.withStyle(oneOptionalSpaceNoLine) //
			.withRuleName("WsAfterSelectorRightBrace"), //

			// --Interpolation
			interpolationStart.withStyle(styles.indent())//
			.withRuleName("InterpolationStart"), //

			Select.whitespaceAfter(interpolationStart)//
			.withStyles(//
				styles.noSpace(), // non wrappable
				styles.noLineBreak()) // may contain a complex expression
			.withRuleName("WsAfterInterpolationStart"), //

			Select.whitespaceBefore(interpolationEnd)//
			.withStyles(//
				styles.noSpace(), // non wrappable
				styles.noLineBreak(), //
				styles.dedent()) // may contain a complex expression
			.withRuleName("WsBeforeInterpolationEnd"), //

			Select.whitespaceAfter(interpolationEnd) //
			.withStyles(//
				styles.noLineBreak()) //
			.withRuleName("WsAfterInterpolationEnd"), //

			// --Statements
			// ws before subsequent statements in a block
			// non inline
			Select.whitespaceBefore(Select.and(//
				Select.not(Select.node(StatementStyle.INLINE)), //
				Select.node(StatementStyle.STATEMENT), //
				Select.node(StatementStyle.BLOCK), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyle(noSpaceTwoLines)//
			.withRuleName("WsBeforeNonFirstBlockStatement"),

			Select.whitespaceBefore(Select.and(//
				Select.not(Select.node(StatementStyle.INLINE)), //
				Select.node(StatementStyle.STATEMENT), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyle(noSpaceOneLineOptionallyTwo)//
			.withRuleName("WsBeforeNonFirstStatement"), //

			// inline
			// Although inlined, gets same treatment since it is a block
			Select.whitespaceBefore(Select.and(//
				Select.node(StatementStyle.INLINE), //
				Select.node(StatementStyle.STATEMENT), //
				Select.node(StatementStyle.BLOCK), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyle(noSpaceTwoLines)//
			.withRuleName("WsBeforeNonFirstBlockStatement"),

			Select.whitespaceBefore(Select.and(//
				Select.node(StatementStyle.INLINE), Select.node(StatementStyle.STATEMENT), //
				Select.not(Select.node(StatementStyle.FIRST))))//
			.withStyles(styles.oneSpace(), styles.noLineBreak())//
			.withRuleName("WsBeforeNonFirstStatement"), //

			// styles.noSpace(), styles.lineBreaks(functions.oneLineBreakUnlessPredecessorIsLinebreakingComment())),

			// No space between function name and ()
			Select.whitespaceBefore(
				Select.grammar(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1_0_0_1()))//
			// Select.grammar(grammarAccess.getFunctionCallAccess().getLeftParenthesisKeyword_1_1()))//
			.withStyle(noSpaceUnlessWrappedNoLine) //
			.withRuleName("WsBeforeFunctionCallLeftParenthesis"),

			// PP parser includes trailing WS in ML comment, and SL comment always ends with line break
			// These two rules allows the WS before a comment to have a break (even if in an expression)
			// and one space after (unless the predecessor is whitespace terminated).
			Select.whitespaceBefore(Select.node(NodeType.COMMENT))//
			.withStyles(//
				styles.oneSpace(),//
				styles.lineBreaks(0, 0, 2, false, false))//
			.withRuleName("WsBeforeComment"),

			Select.whitespaceAfter(Select.node(NodeType.COMMENT))//
			.withStyles(//
				styles.spacing(functions.oneSpaceUnlessPredecessorIsWhitespaceTerminated()),//
				styles.lineBreaks(0, 0, 2, false, false))//
			.withRuleName("WsAfterComment"),

			// Method Call
			Select.whitespaceBefore(Select.grammar(grammarAccess.getMethodCallAccess().getFullStopKeyword_1_1())) //
			.withStyles(styles.noSpace(), styles.noLineBreak()).withRuleName("WsBeforeDot"),
			Select.whitespaceAfter(Select.grammar(grammarAccess.getMethodCallAccess().getFullStopKeyword_1_1())) //
			.withStyles(styles.noSpace(), styles.noLineBreak()).withRuleName("WsAfterDot"),

			// The x.y() should not have whitespace before and after the opening '(' - the parenthesis are
			// optional (the ')' is handled like all parentheses, but the opening is special
			Select.whitespaceBefore(
				Select.grammar(grammarAccess.getMethodCallAccess().getParenthesizedLPARBooleanParserRuleCall_1_2_1_0_0()))//
			.withStyles(styles.noSpace(), styles.noLineBreak()) //
			.withRuleName("WsBeforeMethodCall_LPAR"),

			// No space after '('
			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getMethodCallAccess().getParenthesizedLPARBooleanParserRuleCall_1_2_1_0_0()))//
			.withStyles(styles.noSpaceUnlessWrapped())//
			.withRuleName("NoSpaceAfterMethodCallLeftParenthesis"), //

			// Lambdas
			Select.whitespaceAfter(Select.grammar(grammarAccess.getJava8LambdaAccess().getVerticalLineKeyword_0())) //
			.withStyle(styles.noSpace()).withRuleName("WsAfterLeftJ8Pipe"),
			Select.whitespaceBefore(Select.grammar(grammarAccess.getJava8LambdaAccess().getVerticalLineKeyword_3())) //
			.withStyle(styles.noSpace()).withRuleName("WsBeforeRightJ8Pipe"),
			Select.whitespaceBefore(Select.grammar(grammarAccess.getRubyLambdaAccess().getVerticalLineKeyword_1())) //
			.withStyle(styles.noSpace()).withRuleName("WsBeforeLeftRPipe"),
			Select.whitespaceAfter(Select.grammar(grammarAccess.getRubyLambdaAccess().getVerticalLineKeyword_1())) //
			.withStyle(styles.noSpace()).withRuleName("WsAfterLeftRPipe"),
			Select.whitespaceBefore(Select.grammar(grammarAccess.getRubyLambdaAccess().getVerticalLineKeyword_4())) //
			.withStyle(styles.noSpace()).withRuleName("WsBeforeRightRPipe"),

			Select.whitespaceAfter(
				Select.grammar(grammarAccess.getRubyLambdaAccess().getVerticalLineKeyword_4()).and(inline)) //
			.withStyles(styles.oneSpace(), styles.indent(0), styles.noLineBreak()).withRuleName(
				"WsAfterInlineRightRPipe"),
			Select.whitespaceAfter(Select.grammar(grammarAccess.getRubyLambdaAccess().getVerticalLineKeyword_4())) //
			.withStyles(styles.indent(), styles.oneLineBreak()).withRuleName("WsAfterRightRPipe"),

			// Start indent on lambda '{' that is inline (in case there is a break)
			Select.whitespaceAfter(lambdaLeftBrace.and(inline)).//
			withStyles( //
				styles.indent(), //
				styles.oneSpace(), //
				styles.noLineBreak())//
			.withRuleName("WsAfterInlineLambdaLeftBrace"), //

			// Stop indent on '}' and emit one space before
			Select.whitespaceBefore(lambdaRightBrace.and(inline))//
			.withStyles( //
				styles.oneSpace(), //
				styles.dedent(), //
				styles.noLineBreak()) //
			.withRuleName("WsAfterInlineLambdaRightBrace"), //

			// No space and optinal one linebreak after a lambda '}' if inline
			Select.whitespaceAfter(lambdaRightBrace.and(inline))//
			.withStyles( //
				styles.noSpaceUnlessWrapped(), //
				styles.lineBreaks(0, 1, 2)) //
			.withRuleName("DefaultCSS.BreakLineAfterRightCurlyBrace"),

			// Separator
			Select.whitespaceBefore(
				Select.grammar(grammarAccess.getSeparatorExpressionAccess().getSemicolonKeyword_1())) //
			.withStyles(styles.noSpace(), styles.noLineBreak()).withRuleName("WsBeforeSeparator"),

			// optional linebreak if non inline
			Select.whitespaceAfter( //
				Select.and(
					notInline, Select.grammar(grammarAccess.getSeparatorExpressionAccess().getSemicolonKeyword_1()))) //
			.withStyles(styles.oneSpace(), styles.lineBreaks(0, 1, 1, true, true)) //
			.withRuleName("WsAfterSeparator"),

			// no linebreak if inlining
			Select.whitespaceAfter(Select.and( //
				Select.grammar(grammarAccess.getSeparatorExpressionAccess().getSemicolonKeyword_1()), //
				inline))//
			.withStyles(styles.oneSpace(), styles.noLineBreak()) //
			.withRuleName("WsAfterSeparator")

		// ,

		// Select.whitespaceBefore(Select.grammar(grammarAccess.getLiteralListAccess().getEndCommaParserRuleCall_3())).withStyle(//
		// noSpaceNoLine)

		);
		return css;
	}
}
