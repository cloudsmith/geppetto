/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.tests;

import com.puppetlabs.geppetto.pp.AdditiveExpression;
import com.puppetlabs.geppetto.pp.FunctionCall;
import com.puppetlabs.geppetto.pp.LiteralBoolean;
import com.puppetlabs.geppetto.pp.LiteralDefault;
import com.puppetlabs.geppetto.pp.LiteralName;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.LiteralRegex;
import com.puppetlabs.geppetto.pp.LiteralUndef;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.SelectorEntry;
import com.puppetlabs.geppetto.pp.SelectorExpression;
import com.puppetlabs.geppetto.pp.SingleQuotedString;
import com.puppetlabs.geppetto.pp.VariableExpression;
import com.puppetlabs.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.junit.Test;

/**
 * Tests validation and serialization of SelectorExpression
 * 
 */
public class TestSelectorExpression extends AbstractPuppetTests {
	// @formatter:off

	static final String Sample_Selector = "x ? {\n  a => true,\n}\n";

	static final String Sample_Selector2 = "x ? {\n  a  => true,\n  be => false,\n}\n";

	// @formatter:on

	@Test
	public void test_Serialize_SelectorExpression() {
		PuppetManifest pp = pf.createPuppetManifest();
		SelectorExpression se = pf.createSelectorExpression();
		pp.getStatements().add(se);

		SelectorEntry entry = pf.createSelectorEntry();
		se.getParameters().add(entry);

		LiteralNameOrReference slhs = pf.createLiteralNameOrReference();
		slhs.setValue("x");
		LiteralNameOrReference entrylhs = pf.createLiteralNameOrReference();
		entrylhs.setValue("a");

		se.setLeftExpr(slhs);
		entry.setLeftExpr(entrylhs);
		LiteralBoolean b = pf.createLiteralBoolean();
		b.setValue(true);
		entry.setRightExpr(b);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Selector, s);

		// add a second entry and format
		entry = pf.createSelectorEntry();
		se.getParameters().add(entry);

		entrylhs = pf.createLiteralNameOrReference();
		entrylhs.setValue("be");
		entry.setLeftExpr(entrylhs);
		b = pf.createLiteralBoolean();
		b.setValue(false);
		entry.setRightExpr(b);

		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Selector2, s);

	}

	@Test
	public void test_Validate_SelectorExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		SelectorExpression se = pf.createSelectorExpression();
		pp.getStatements().add(se);

		SelectorEntry entry = pf.createSelectorEntry();
		se.getParameters().add(entry);

		AdditiveExpression slhs = pf.createAdditiveExpression();
		slhs.setOpName("+");
		slhs.setLeftExpr(createNameOrReference("1"));
		slhs.setRightExpr(createNameOrReference("1"));

		VariableExpression entrylhs = pf.createVariableExpression();
		entrylhs.setVarName("$a");

		se.setLeftExpr(slhs);
		entry.setLeftExpr(entrylhs);
		entry.setRightExpr(pf.createLiteralBoolean());

		tester.validate(se).assertAll(
			AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION));

		entry.setLeftExpr(slhs);
		se.setLeftExpr(entrylhs);
		tester.validate(se).assertAll(
			AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION));

	}

	@Test
	public void test_Validate_SelectorExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		SelectorExpression se = pf.createSelectorExpression();
		pp.getStatements().add(se);

		SelectorEntry entry = pf.createSelectorEntry();
		se.getParameters().add(entry);

		// -- Literal String
		{
			SingleQuotedString slhs = pf.createSingleQuotedString();
			slhs.setText("x");
			LiteralName entrylhs = pf.createLiteralName();
			entrylhs.setValue("a");

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}

		// -- LiteralName
		{
			LiteralName slhs = pf.createLiteralName();
			slhs.setValue("x");
			LiteralName entrylhs = pf.createLiteralName();
			entrylhs.setValue("a");

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- LiteralNameOrReference
		{
			LiteralNameOrReference slhs = createNameOrReference("x");
			LiteralNameOrReference entrylhs = createNameOrReference("a");

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- variable
		{
			VariableExpression slhs = pf.createVariableExpression();
			slhs.setVarName("$x");
			VariableExpression entrylhs = pf.createVariableExpression();
			entrylhs.setVarName("$a");

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- function call
		{
			FunctionCall slhs = pf.createFunctionCall();
			slhs.setLeftExpr(createNameOrReference("include"));
			slhs.getParameters().add(pf.createLiteralBoolean());
			FunctionCall entrylhs = pf.createFunctionCall();
			entrylhs.setLeftExpr(createNameOrReference("include"));
			entrylhs.getParameters().add(pf.createLiteralBoolean());

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// boolean
		{
			LiteralBoolean slhs = pf.createLiteralBoolean();
			LiteralBoolean entrylhs = pf.createLiteralBoolean();

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- undef
		{
			LiteralUndef slhs = pf.createLiteralUndef();
			LiteralUndef entrylhs = pf.createLiteralUndef();

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- regex
		{
			LiteralRegex slhs = pf.createLiteralRegex();
			slhs.setValue("/[a-z]*/");
			LiteralRegex entrylhs = pf.createLiteralRegex();
			entrylhs.setValue("/[a-z]*/");

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		}
		// -- default
		{
			LiteralDefault slhs = pf.createLiteralDefault();
			LiteralDefault entrylhs = pf.createLiteralDefault();

			se.setLeftExpr(slhs);
			entry.setLeftExpr(entrylhs);
			entry.setRightExpr(pf.createLiteralBoolean());

			tester.validate(se).assertOK();
		}
	}
}
