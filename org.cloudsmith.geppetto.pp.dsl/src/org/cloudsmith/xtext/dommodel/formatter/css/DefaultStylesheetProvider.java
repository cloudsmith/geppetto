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
package org.cloudsmith.xtext.dommodel.formatter.css;

import org.cloudsmith.xtext.dommodel.IDomNode.NodeClassifier;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This Provider of a {@link DomCSS} style sheet provides styling rules that are not tied to a particular
 * grammar. This particular style sheet serves as an example; in a real implementation you can do things like:
 * <ul>
 * <li>Compose the style sheet based on preferences</li>
 * <li>Use a singleton/static sheet if there is not need to configure the rules</li>
 * <li>Use rules based on specific grammar rules in the target grammar instead of textual references to keywords</li>
 * <li>Compose the style sheet from other style sheets (themes/skins)</li>
 * </ul>
 * <p>
 * Since this default sheet uses very simple rules with low specificity, they can be combined with more specific rules for the target language.
 * </p>
 * 
 */
public class DefaultStylesheetProvider implements Provider<DomCSS> {

	@Inject
	private IStyleFactory styles;

	@Inject
	private IFunctionFactory functions;

	@Override
	public DomCSS get() {
		DomCSS css = new DomCSS();

		css.addRules( //
			// All nodes print their text
			Select.any()//
			.withStyle(styles.tokenText(functions.textOfNode()))//
			.withRuleName("DefaultCSS.TextOfNode"), //

			// Default spacing is one space per whitespace, no line break
			Select.whitespace()//
			.withStyles(//
				styles.oneSpace(), //
				styles.noLineBreak()) //
			.withRuleName("DefaultCSS.DefaultSpacing"), //

			// No leading whitespace, but accept a single empty line
			Select.before(Select.whitespace(), Select.node(NodeClassifier.FIRST_TOKEN))//
			.withStyles(//
				styles.noSpaceUnlessWrapped(), //
				styles.lineBreaks(0, 0, 1, false, false))//
			.withRuleName("DefaultCSS.NoLeadingButOneOptionalLineBeforeFirstToken"), //

			// One trailing line break minimum, but accept two
			Select.after(Select.whitespace(), Select.node(NodeClassifier.LAST_TOKEN)).withStyles(//
				styles.noSpaceUnlessWrapped(), //
				styles.lineBreaks(1, 1, 2))//
			.withRuleName("DefaultCSS.MinOneTrailingLinebreakOptionallyTwo"),

			// No space before ',', and one space after
			Select.before(Select.whitespace(), Select.important(Select.keyword(",")))//
			.withStyles(styles.noSpace())//
			.withRuleName("DefaultCSS.NoSpaceBeforeComma"), //
			Select.after(Select.whitespace(), Select.keyword(","))//
			.withStyles(styles.oneSpace())//
			.withRuleName("DefaultCSS.OneSpaceAfterComma"), //

			// Select.between(Select.whitespace(), Select.keyword("}"), Select.keyword(",")) //
			// .withStyles(styles.noSpace(), styles.noLineBreak()) //
			// .withRuleName("DefaultCSS.NoSpaceOrBreakBetweenRightCurlyBraceAndComma"),

			// Indent content between []
			Select.keyword("[").withStyle(styles.indent()) //
			.withRuleName("DefaultCSS.IndentLeftSquareBracket"), //

			Select.keyword("]").withStyle(styles.dedent()) //
			.withRuleName("DefaultCSS.DedentRightSquareBracket"), //

			// No space after '[' and no space before ']'
			Select.after(Select.whitespace(), Select.keyword("["))//
			.withStyles(styles.noSpaceUnlessWrapped()) //
			.withRuleName("DefaultCSS.NoSpaceAfterLeftSquareBracket"), //
			Select.before(Select.whitespace(), Select.keyword("]"))//
			.withStyles(styles.noSpaceUnlessWrapped()) //
			.withRuleName("DefaultCSS.NoSpaceAfterRightSquareBracket"), //

			// No space after '(' and no space before ')'
			Select.after(Select.whitespace(), Select.keyword("("))//
			.withStyles(styles.noSpaceUnlessWrapped())//
			.withRuleName("DefaultCSS.NoSpaceAfterRightParenthesis"), //
			Select.before(Select.whitespace(), Select.keyword(")"))//
			.withStyles(styles.noSpaceUnlessWrapped()) //
			.withRuleName("DefaultCSS.NoSpaceBeforeRightParenthesis"), //

			// Start indent on '{' and break line

			Select.whitespaceAfter(Select.keyword("{")).withStyles( //
				styles.indent(), //
				styles.noSpaceUnlessWrapped(), //
				styles.oneLineBreak())//
			.withRuleName("DefaultCSS.StartIndentOnLeftCurlyBracket"), //

			// Stop indent on '}' and break line before and after
			Select.whitespaceBefore(Select.keyword("}"))//
			.withStyles( //
				styles.noSpaceUnlessWrapped(), //
				styles.dedent(), //
				styles.lineBreaks(1, 1, 2)) //
			.withRuleName("DefaultCSS.StopIndentAfterRightCurlyBrace"), //

			Select.after(Select.whitespace(), Select.keyword("}"))//
			.withStyles( //
				styles.noSpaceUnlessWrapped(), //
				styles.lineBreaks(1, 1, 2)) //
			.withRuleName("DefaultCSS.BreakLineAfterRightCurlyBrace"));
		return css;
	}
}
