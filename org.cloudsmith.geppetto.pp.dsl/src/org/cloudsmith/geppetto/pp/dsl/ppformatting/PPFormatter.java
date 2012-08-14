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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.parsetree.reconstr.ITokenStream;
import org.eclipse.xtext.util.Pair;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 * 
 * TODO: Formatting currently plagued by bugs in Xtext 2.0. See JUnit tests.
 */
public class PPFormatter extends AbstractDeclarativeFormatter {

	protected void assignmentExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		c.setLinewrap().after(ga.getAssignmentExpressionAccess().getGroup_1());
	}

	protected void atExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		// At expression
		// -- no space between EXPR and [
		// -- no space after opening [
		// -- no space before closing ]
		c.setNoSpace().before(ga.getAtExpressionAccess().getLeftSquareBracketKeyword_1_1());
		c.setNoSpace().after(ga.getAtExpressionAccess().getLeftSquareBracketKeyword_1_1());
		c.setNoSpace().before(ga.getAtExpressionAccess().getRightSquareBracketKeyword_1_3());

	}

	protected void caseExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();

		Keyword caseLbr = ga.getCaseAccess().getLeftCurlyBracketKeyword_3();
		Keyword caseRbr = ga.getCaseAccess().getRightCurlyBracketKeyword_5();
		c.setLinewrap().after(caseLbr);
		c.setLinewrap().around(caseRbr);
		c.setIndentation(caseLbr, caseRbr);

		caseLbr = ga.getCaseExpressionAccess().getLeftCurlyBracketKeyword_2();
		caseRbr = ga.getCaseExpressionAccess().getRightCurlyBracketKeyword_4();
		c.setLinewrap().after(caseLbr);
		c.setLinewrap().around(caseRbr);
		c.setIndentation(caseLbr, caseRbr);
	}

	protected void classExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();

		Keyword lbr = ga.getHostClassDefinitionAccess().getLeftCurlyBracketKeyword_4();
		Keyword rbr = ga.getHostClassDefinitionAccess().getRightCurlyBracketKeyword_6();
		c.setLinewrap().after(lbr);
		c.setLinewrap().around(rbr);
		c.setIndentation(lbr, rbr);

		// linewrap statements
		// not a good idea since non-parenthesised function calls are separate expressions,
		// each 'statement' must do its own terminating linewrap
		// c.setLinewrap().after(ga.getHostClassDefinitionAccess().getStatementsAssignment_5());

		// but not between what is a non parenthesized function call
		// TODO: Broken...
		c.setNoLinewrap().between(ga.getLiteralNameOrReferenceRule(), ga.getLiteralNameOrReferenceRule());
		// as per https://bugs.eclipse.org/bugs/show_bug.cgi?id=340166#c5
		// c.setNoLinewrap().between(ga.getLiteralNameOrReferenceRule(), ga.getExpressionListRule());

	}

	protected void collectExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		c.setLinewrap().after(ga.getCollectExpressionAccess().getLeftCurlyBracketKeyword_1_2_0());
		c.setIndentation(
			ga.getCollectExpressionAccess().getLeftCurlyBracketKeyword_1_2_0(),
			ga.getCollectExpressionAccess().getRightCurlyBracketKeyword_1_2_2());
		c.setLinewrap().before(ga.getCollectExpressionAccess().getRightCurlyBracketKeyword_1_2_2());
	}

	@Override
	protected void configureFormatting(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();

		// not a good idea since non-parenthesised function calls are separate expressions,
		// each 'statement' must do its own terminating linewrap
		// c.setLinewrap().after(ga.getPuppetManifestAccess().getStatementsAssignment_2());

		// Add add and preserve newlines around comments
		c.setLinewrap(0, 1, 2).before(ga.getSL_COMMENTRule());

		// NOTE: default set to 0 to allow "inline" comments like /* this one */ without requiring
		// a linewrap.
		c.setLinewrap(0, 0, 2).before(ga.getML_COMMENTRule());
		c.setLinewrap(0, 0, 1).after(ga.getML_COMMENTRule());

		stringExpressionConfiguration(c);
		ifExpressionConfiguration(c);
		caseExpressionConfiguration(c);
		classExpressionConfiguration(c);
		selectorExpressionConfiguration(c);
		definitionExpressionConfiguration(c);
		assignmentExpressionConfiguration(c);
		importExpressionConfiguration(c);
		literalListAndHashConfiguration(c);
		functionCallConfiguration(c);
		nodeExpressionConfiguration(c);
		// commas
		for(Keyword comma : ga.findKeywords(",")) {
			c.setNoSpace().before(comma);
		}

		// TODO: Old formatter needs this fixed since there is no endComma rule - they are just optional commas in
		// various expressions. They may be ok already with a general rule "no space before a comma keyword".
		// c.setNoSpace().before(ga.getEndCommaRule());

		// no space between unary operators (! -) and the following expression
		c.setNoSpace().after(ga.getNotExpressionAccess().getExclamationMarkKeyword_0());
		c.setNoSpace().after(ga.getUnaryMinusExpressionAccess().getHyphenMinusKeyword_0());

		resourceExpressionConfiguration(c);
		collectExpressionConfiguration(c);
		atExpressionConfiguration(c);
		parenthisedExpressionConfguration(c);
		manifestConfiguration(c);

		// DEBUG: uncomment next to get a diagram of the formatter
		// super.saveDebugGraphvizDiagram("debugDiagram.dot");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter#createFormatterStream(java.lang.String,
	 * org.eclipse.xtext.parsetree.reconstr.ITokenStream, boolean)
	 */
	@Override
	public ITokenStream createFormatterStream(String indent, ITokenStream out, boolean preserveWhitespaces) {
		// Create opportunity to use the TokenStreamWrapper around out... (used for debugging).
		return super.createFormatterStream(indent, out, preserveWhitespaces);
	}

	protected void definitionExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		Keyword lbr = ga.getDefinitionAccess().getLeftCurlyBracketKeyword_3();
		Keyword rbr = ga.getDefinitionAccess().getRightCurlyBracketKeyword_5();
		c.setLinewrap().after(lbr);
		c.setLinewrap().around(rbr);
		c.setIndentation(lbr, rbr);

		List<Pair<Keyword, Keyword>> pairs = ga.getDefinitionArgumentListAccess().findKeywordPairs("(", ")");
		if(pairs.size() == 1) {
			c.setIndentation(pairs.get(0).getFirst(), pairs.get(0).getSecond());
			c.setNoSpace().after(pairs.get(0).getFirst());
			c.setNoSpace().before(pairs.get(0).getSecond());
		}
		List<Keyword> commas = ga.getDefinitionArgumentListAccess().findKeywords(",");
		for(Keyword comma : commas)
			c.setLinewrap().after(comma);
	}

	protected void functionCallConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		c.setNoSpace().before(ga.getFunctionCallAccess().getLeftParenthesisKeyword_1_1());
		c.setNoSpace().after(ga.getFunctionCallAccess().getLeftParenthesisKeyword_1_1());
		c.setNoSpace().before(ga.getFunctionCallAccess().getRightParenthesisKeyword_1_3());
	}

	protected void ifExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		Keyword lbr = ga.getIfExpressionAccess().getLeftCurlyBracketKeyword_2();
		Keyword rbr = ga.getIfExpressionAccess().getRightCurlyBracketKeyword_4();
		c.setLinewrap().after(lbr);
		c.setIndentation(lbr, rbr);
		c.setLinewrap().around(rbr);

		lbr = ga.getElseExpressionAccess().getLeftCurlyBracketKeyword_1();
		rbr = ga.getElseExpressionAccess().getRightCurlyBracketKeyword_3();
		c.setLinewrap().after(lbr);
		c.setIndentation(lbr, rbr);
		c.setLinewrap().around(rbr);

		lbr = ga.getElseIfExpressionAccess().getLeftCurlyBracketKeyword_2();
		rbr = ga.getElseIfExpressionAccess().getRightCurlyBracketKeyword_4();
		c.setLinewrap().after(lbr);
		c.setIndentation(lbr, rbr);
		c.setLinewrap().around(rbr);
	}

	protected void importExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		// This does not work - no element of Import makes the linewrap take effect
		c.setLinewrap().after(ga.getImportExpressionAccess().getGroup_2());
	}

	protected void literalListAndHashConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		// -- no space after opening [
		// -- no space before closing ]
		c.setNoSpace().after(ga.getLiteralListAccess().getLeftSquareBracketKeyword_1());
		c.setNoSpace().before(ga.getLiteralListAccess().getRightSquareBracketKeyword_3());

		// -- no space after opening {
		// -- no space before closing }
		c.setNoSpace().after(ga.getLiteralHashAccess().getLeftCurlyBracketKeyword_1());
		c.setNoSpace().before(ga.getLiteralHashAccess().getRightCurlyBracketKeyword_4());
	}

	protected void manifestConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		// does not have intended (positive) effect, does instead screw up other statements
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=340166#c6
		// c.setLinewrap().after(ga.getPuppetManifestAccess().getStatementsAssignment_1());
	}

	protected void nodeExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();

		Keyword lbr = ga.getNodeDefinitionAccess().getLeftCurlyBracketKeyword_4();
		Keyword rbr = ga.getNodeDefinitionAccess().getRightCurlyBracketKeyword_6();
		c.setLinewrap().after(lbr);
		c.setLinewrap().around(rbr);
		c.setIndentation(lbr, rbr);
	}

	protected void parenthisedExpressionConfguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		c.setNoSpace().after(ga.getParenthisedExpressionAccess().getLeftParenthesisKeyword_0());
		c.setNoSpace().before(ga.getParenthisedExpressionAccess().getRightParenthesisKeyword_3());
	}

	/**
	 * Adds formatting instructions for ResourceExpression and VirtualResourceExpression.
	 * Sample formatting (wanted result) :
	 * 
	 * <pre>
	 * type {
	 *     "title" :
	 *         attr => expr,
	 *         attr => expr,
	 *         attr => expr ;
	 *     "title" :
	 *         attr => expr,
	 *         attr => expr
	 * }
	 * Type {
	 *     attr => expr,
	 *     attr => expr
	 * }    
	 * &#064; type {
	 * ... // as above
	 * }
	 * &#064;&#064; type {
	 * ... // as above
	 * }
	 * 
	 * </pre>
	 * 
	 * @param c
	 *            - formatter to configure
	 */
	protected void resourceExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();

		// + linebreak after «'title' :»
		for(Keyword colon : ga.getResourceBodyAccess().findKeywords(":")) {
			c.setLinewrap().after(colon);
			c.setNoLinewrap().before(colon);
		}
		// + indent the body of the resource expression
		{
			Keyword lbr = ga.getResourceExpressionAccess().getLeftCurlyBracketKeyword_0_1_1();
			Keyword rbr = ga.getResourceExpressionAccess().getRightCurlyBracketKeyword_0_1_3();
			c.setIndentation(lbr, rbr);
			c.setNoLinewrap().before(lbr);
			c.setLinewrap().after(lbr);
			c.setLinewrap().before(rbr);
			c.setLinewrap().after(rbr);
			Keyword endComma = ga.getAttributeOperationsAccess().getCommaKeyword_2();
			c.setLinewrap(1).between(endComma, rbr);
		}
		// repeat for literal class for of the rule
		{
			Keyword lbr = ga.getResourceExpressionAccess().getLeftCurlyBracketKeyword_1_2();
			Keyword rbr = ga.getResourceExpressionAccess().getRightCurlyBracketKeyword_1_4();
			c.setIndentation(lbr, rbr);
			c.setNoLinewrap().before(lbr);
			c.setLinewrap().after(lbr);
			c.setLinewrap().before(rbr);
			c.setLinewrap().after(rbr);
			Keyword endComma = ga.getAttributeOperationsAccess().getCommaKeyword_2();
			c.setLinewrap(1).between(endComma, rbr);
		}

		// + linebreak after «attribute (=>|+>) expr,»
		Keyword comma = ga.getAttributeOperationsAccess().getCommaKeyword_1_0_0();
		c.setNoLinewrap().before(comma);
		c.setLinewrap().after(comma);

		// c.setLinewrap().after(ga.getAttributeOperationsAccess().getCommaKeyword_2());
		// for(Keyword comma : ga.getAttributeOperationsAccess().findKeywords(",")) {
		// if(comma == ga.getAttributeOperationsAccess().get)
		// c.setLinewrap().after(comma);
		// }
		// linebreak after each resource body in a list of resource bodies
		{
			Keyword semi = ga.getResourceExpressionAccess().getSemicolonKeyword_0_1_2_1_0();
			c.setNoLinewrap().before(semi);
			c.setLinewrap(2).after(semi);
			Keyword endSemi = ga.getResourceExpressionAccess().getSemicolonKeyword_0_1_2_2();
			c.setNoLinewrap().before(endSemi);
			c.setLinewrap(1).after(endSemi);
		}
		{ // repeat for second group
			Keyword semi = ga.getResourceExpressionAccess().getSemicolonKeyword_1_3_1_0();
			c.setNoLinewrap().before(semi);
			c.setLinewrap(2).after(semi);
			Keyword endSemi = ga.getResourceExpressionAccess().getSemicolonKeyword_1_3_2();
			c.setNoLinewrap().before(endSemi);
			c.setLinewrap(1).after(endSemi);
		}
		// c.setLinewrap(1).between(endSemi, rbr);

		// no wrap when RESOURCE -> RESOURCE is used
		// c.setNoLinewrap().before(ga.getRelationshipExpressionAccess().getOpNameAssignment_1_1());
		c.setNoLinewrap().before(ga.getRelationshipExpressionAccess().getOpNameAssignment_1_1());

		// + indent the list of attribute operations but only when when they have a title
		// TODO: 1.0GA (changing resource expression grammar)
		c.setIndentationIncrement().before(ga.getResourceBodyAccess().getAttributesAssignment_0_2());
		c.setIndentationDecrement().after(ga.getResourceBodyAccess().getAttributesAssignment_0_2());

		// this will always indent even if there is no title
		// c.setIndentationIncrement().before(ga.getAttributeOperationsRule());
		// c.setIndentationDecrement().after(ga.getAttributeOperationsRule());

		// this was workaround for bug in Xtext 1.0
		// for(Assignment theCall : ga.getResourceBodyAccess().findAssignments(ga.getAttributeOperationsRule())) {
		// c.setIndentationIncrement().before(theCall);
		// c.setIndentationDecrement().after(theCall);
		// }

		// No space between the two @@ in a virtual exported resource
		{
			Keyword at1 = ga.getVirtualNameOrReferenceAccess().getCommercialAtKeyword_0();
			RuleCall at2 = ga.getVirtualNameOrReferenceAccess().getExportedATBooleanParserRuleCall_1_0();
			RuleCall value = ga.getVirtualNameOrReferenceAccess().getValueUnionNameOrReferenceParserRuleCall_2_0();

			c.setNoSpace().between(at1, at2);
			c.setNoSpace().between(at1, value);
			c.setNoSpace().between(at2, value);
			// ga.getVirtualNameOrReferenceAccess().getCommercialAtKeyword_0(),
			// ga.getVirtualNameOrReferenceAccess().getValueAssignment_2());
			// c.setNoSpace().between(
			// ga.getVirtualNameOrReferenceAccess().getCommercialAtKeyword_0(),
			// ga.getVirtualNameOrReferenceAccess().getExportedATBooleanParserRuleCall_1_0());
		}
	}

	protected void selectorExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		Keyword lbr = ga.getSelectorExpressionAccess().getLeftCurlyBracketKeyword_1_2_0_0();
		Keyword rbr = ga.getSelectorExpressionAccess().getRightCurlyBracketKeyword_1_2_0_4();
		c.setLinewrap().after(lbr);
		c.setLinewrap().before(rbr);
		c.setIndentation(lbr, rbr);
		Keyword comma = ga.getSelectorExpressionAccess().getCommaKeyword_1_2_0_2_0_0();
		// RuleCall endcomma = ga.getSelectorExpressionAccess().getEndCommaParserRuleCall_1_3_0_4_0();

		c.setLinewrap().after(comma);
		c.setLinewrap(1).between(comma, rbr);
		// c.setLinewrap(1).between(endcomma, rbr);
	}

	protected void stringExpressionConfiguration(FormattingConfig c) {
		PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
		c.setNoSpace().after(ga.getUnquotedStringAccess().getDollarSignLeftCurlyBracketKeyword_1());
		c.setNoSpace().before(ga.getUnquotedStringAccess().getRightCurlyBracketKeyword_3());
	}

	// /**
	// * The "input" formatting stream, used for debugging the input to the declarative formatter.
	// * Use the TokenStreamWrapper to debug the output.
	// */
	// @SuppressWarnings("unused")
	// private class PuppetFormattingStream extends FormattingConfigBasedStream {
	//
	// public PuppetFormattingStream(ITokenStream out, String indentation, FormattingConfig cfg,
	// IElementMatcher<ElementPattern> matcher, IHiddenTokenHelper hiddenTokenHelper, boolean preserveSpaces) {
	// super(out, indentation, cfg, matcher, hiddenTokenHelper, preserveSpaces);
	// }
	//
	// @Override
	// public void writeHidden(EObject grammarElement, String value) throws IOException {
	// // if(grammarElement instanceof AbstractRule && "OWS".equals(((AbstractRule)grammarElement).getName())) {
	// // debugPrintln("writeHidden OWS with value «"+value+"»");
	// // }
	// super.writeHidden(grammarElement, value);
	// }
	//
	// @Override
	// public void writeSemantic(EObject grammarElement, String value) throws IOException {
	// super.writeSemantic(grammarElement, value);
	// }
	// }
	//
	// @SuppressWarnings("unused")
	// private class TokenStreamWrapper implements ITokenStream {
	// final private ITokenStream wrapped;
	//
	// TokenStreamWrapper(ITokenStream out) {
	// this.wrapped = out;
	// }
	//
	// @Override
	// public void flush() throws IOException {
	// System.err.println("flush");
	// wrapped.flush();
	// }
	//
	// @Override
	// public void writeHidden(EObject grammarElement, String value) throws IOException {
	// PPGrammarAccess ga = (PPGrammarAccess) getGrammarAccess();
	// System.err.println("hidden : [" + value + "] element=" + grammarElement.toString());
	// if(grammarElement instanceof RuleCall &&
	// ((RuleCall) grammarElement).getRule().getName().equals("unionNameOrReference")) {
	// RuleCall rc = (RuleCall) grammarElement;
	// if(rc.eContainer() instanceof Assignment) {
	// Assignment a = (Assignment) rc.eContainer();
	// System.err.println("NameOrReference contained in assignment to: " + a.getFeature());
	// }
	// }
	// wrapped.writeHidden(grammarElement, value);
	//
	// }
	//
	// @Override
	// public void writeSemantic(EObject grammarElement, String value) throws IOException {
	// System.err.println("semantic : [" + value + "] element=" + grammarElement.toString());
	// wrapped.writeSemantic(grammarElement, value);
	// }
	// }

}
