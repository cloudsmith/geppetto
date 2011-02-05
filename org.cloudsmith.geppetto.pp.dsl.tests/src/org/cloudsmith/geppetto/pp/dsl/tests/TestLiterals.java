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
package org.cloudsmith.geppetto.pp.dsl.tests;

import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;

/**
 * Tests Literals
 * 
 */
public class TestLiterals extends AbstractPuppetTests {

	public void test_Validate_LiteralNameOrReference_NotOk() {
		final String[] keywords = {
				"and", "or", "case", "default", "define", "import", "if", "elsif", "else", "inherits", "node", "in",
				"undef", "true", "false" };
		for(String s : keywords) {
			LiteralNameOrReference n = createNameOrReference(s);
			tester.validator().checkLiteralNameOrReference(n);
			tester.diagnose().assertError(IPPDiagnostics.ISSUE__RESERVED_WORD);
		}

		LiteralNameOrReference n = createNameOrReference("%#");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_NAME_OR_REF);

	}

	public void test_Validate_LiteralNameOrReference_Ok() {
		LiteralNameOrReference n = createNameOrReference("file");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertOK();

		n = createNameOrReference("File");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertOK();

		n = createNameOrReference("::File");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertOK();

		n = createNameOrReference("A::B::C");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertOK();

		n = createNameOrReference("class");
		tester.validator().checkLiteralNameOrReference(n);
		tester.diagnose().assertOK();
	}

	public void test_Validate_LiteralRegex_NotOk() {
		LiteralRegex r = pf.createLiteralRegex();
		r.setValue("/[a-zA-Z0-9]*");
		tester.validator().checkLiteralRegex(r);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_REGEX);

		r.setValue("/[a-zA-Z0-9]*\\/");
		tester.validator().checkLiteralRegex(r);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_REGEX);

		r.setValue("/[a-zA-Z0-9]*/i");
		tester.validator().checkLiteralRegex(r);
		tester.diagnose().assertError(IPPDiagnostics.ISSUE__UNSUPPORTED_REGEX_FLAGS);
	}

	/**
	 * Test regular expression ok states:
	 * TODO: add more tests - this is just a smoke-test
	 */
	public void test_Validate_LiteralRegex_Ok() {
		LiteralRegex r = pf.createLiteralRegex();
		r.setValue("/[a-zA-Z0-9]*/");
		tester.validator().checkLiteralRegex(r);
		tester.diagnose().assertOK();

	}

	public void test_Validate_SingleQuotedString_NotOk() {

		{ // -- unescaped single quote
			SingleQuotedString ls = pf.createSingleQuotedString();
			ls.setText("I have an unprotected single quote ' in me.");
			tester.validator().checkSingleQuotedString(ls);
			tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_STRING);
		}
		// TODO: Test DQ string
		// {
		// // -- unescaped double quote
		// DoubleQuotedString ls = pf.createDoubleQuotedString();
		// ls.setLeadingText("I have an unprotected double quote \" in me.");
		// tester.diagnose().assertError(IPPDiagnostics.ISSUE__NOT_STRING);
		// }
	}

	public void test_Validate_SingleQuotedString_Ok() {
		SingleQuotedString ls = pf.createSingleQuotedString();
		ls.setText("I am a single quoted string with a tab \\t char");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- control char
		ls.setText("I am a single quoted string with a tab \t");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- new line
		ls.setText("I am a single quoted string with a nl \n");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- TODO: test NBSP

		// NOTE: these are actually not supported as specific escapes as any
		// escaped character is the character itself - \u1234 is simply u1234
		//

		// -- unicode escape \\u [hexdigit]{4,4}
		ls.setText("\\u1a2b");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- hex escape \x[hexdigit]{2,3}
		ls.setText("\\x1a");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- octal escape \[0-7]{3,3}
		ls.setText("\\777");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- meta escape \M-[sourcecharexceptNL]
		ls.setText("\\M-A");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- control escape \c[sourcecharexceptNL] or \C-[sourcecharexceptNL]
		ls.setText("\\C-J");
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();
		ls.setText("\\cJ");

		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		// -- escaped backslash and quotes as well as any escaped character
		ls.setText("\\\\"); // i.e. '\\'
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		ls.setText("\\'"); // i.e. '\''
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();

		ls.setText("\\p"); // i.e. '\p'
		tester.validator().checkSingleQuotedString(ls);
		tester.diagnose().assertOK();
	}

	private String doubleQuote(String s) {
		return '"' + s + '"';
	}
}
