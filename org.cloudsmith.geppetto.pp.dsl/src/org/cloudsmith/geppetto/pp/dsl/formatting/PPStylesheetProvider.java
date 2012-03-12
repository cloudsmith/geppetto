/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
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
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess.ResourceExpressionElements;
import org.cloudsmith.xtext.dommodel.formatter.css.DefaultStylesheetProvider;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.Select;
import org.cloudsmith.xtext.dommodel.formatter.css.Select.Selector;
import org.eclipse.xtext.Keyword;

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
		final Selector inASingleBodiesResource = Select.containment(resourceSingleBodyTitle);

		final ResourceExpressionElements resourceExpressionAccess = grammarAccess.getResourceExpressionAccess();
		final Keyword resourceLeftCurly1 = resourceExpressionAccess.getLeftCurlyBracketKeyword_0_1_1();
		final Keyword resourceLeftCurly2 = resourceExpressionAccess.getLeftCurlyBracketKeyword_1_2();
		final Selector resourceLeftCurlyBracket = Select.grammar(resourceLeftCurly1, resourceLeftCurly2);

		DomCSS css = super.get();

		css.addRules(
		// RESOURCE
		// Resource expression with single titled body, should allow body to have title on same line
		// so, no line break, but one space here

			Select.and(inASingleBodiesResource, Select.whitespaceAfter(resourceLeftCurlyBracket)) //
			.withStyles( //
				styles.noLineBreak(), //
				styles.oneSpace(), //
				styles.indent()),

			// Note: Closing Resource "}" has DEDENT BREAK "}" BREAK by default

			// RESOURCE BODY
			// No space before the resource body title colon
			Select.whitespaceBefore(resourceBodyTitleColon).withStyles(styles.noSpace()),

			// A resource body has its attribute operations indented after the title ':'
			//
			Select.whitespaceAfter(resourceBodyTitleColon).withStyles( //
				styles.indent(), //
				styles.oneLineBreak()),

			// A resource body contained in a single bodied resource
			// should not indent after the title and instead use the resource's indent
			//
			Select.and(inASingleBodiesResource, Select.whitespaceAfter(resourceBodyTitleColon)).withStyles( //
				styles.indent(0), // required since override of default indent(1) is wanted
				styles.oneLineBreak()), //

			// AttributeOperaionts have each operation on a separate line
			//
			Select.whitespaceAfter(attributeOperationsComma).withStyles(//
				styles.oneLineBreak())

		);
		return css;
	}

}
