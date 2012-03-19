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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.EqualityExpression;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.MatchingExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.RelationshipExpression;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VirtualCollectQuery;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.PPFormatter;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.junit.validation.AssertableDiagnostics;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests for expressions not covered by separate test classes.
 * 
 */
public class TestExpressions extends AbstractPuppetTests {

	private PrintStream savedOut;

	// @formatter:off
	static final String Sample_Relationship = "file { 'file1':\n" + //
			"} -> file { 'file2':\n" + //
			"} -> file { 'file3':\n" + //
			"}\n";

	static final String Sample_Assignment1 = "$x = true\n";

	static final String Sample_Assignment2 = "$x[a] = true\n";

	static final String Sample_Append = "$x += true\n";

	static final String Sample_Match1 = "$x =~ /[a-z]*/\n";

	static final String Sample_Match2 = "$x !~ /[a-z]*/\n";

	static final String Sample_ClassDefinition = "class testClass {\n}\n";

	static final String Sample_If = //
	"if $a == 1 {\n" + //
			"  true\n" + //
			"} else {\n" + //
			"  false\n" + //
			"}\n" + //
			"if $a == 1 {\n" + //
			"  true\n" + //
			"} elsif $b < -3 {\n" + //
			"  false\n" + //
			"} else {\n" + //
			"  true\n" + //
			"}\n";

	/**
	 * Sends System.out to dev/null since there are many warnings about unknown variables (ignored unless
	 * explicitly tested for).
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		savedOut = System.out;
		OutputStream sink = new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
				// do nothing
			}

		};
		System.setOut(new PrintStream(sink));
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		System.setOut(savedOut);
	}

	// @formatter:on

	public void test_Parse_MatchingExpression() throws Exception {
		String code = "$a =~ /[a-z]*/\n";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}

	public void test_ParseCallWithEndComma() throws Exception {
		String code = "$a = shellquote(1,2,3,)";
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	public void test_Serialize_AppendExpression() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		b.setValue(true);
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Append, s);
	}

	public void test_Serialize_AssignmentExpression() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ae = pf.createAssignmentExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		b.setValue(true);
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Assignment1, s);

		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(v);
		at.getParameters().add(createNameOrReference("a"));
		ae.setLeftExpr(at);
		tester.validate(pp).assertOK();
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Assignment2, s);
	}

	/**
	 * No matter how formatter tries to add linewrapping there is none in the formatted result.
	 * 
	 * @see PPFormatter#functionCallConfiguration(FormattingConfig c)
	 */
	public void test_Serialize_CallAndDefine() throws Exception {
		String code = "class a {\n}\n$a = include('a')\ndefine b {\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	// Not relevant since new serializer always pretty prints
	// public void test_Serialize_IfExpression1() throws Exception {
	// String code = "if$a==1{true}else{false}if$a==1{true}elsif$b< -3{false}else{true}";
	// XtextResource r = getResourceFromString(code);
	// String s = serialize(r.getContents().get(0));
	//
	// // Broken in Xtext 2.0 - produces a semi formatted result, should leave string alone
	// assertEquals("serialization should produce same result as input", code, s);
	// }

	public void test_Serialize_CaseExpression() throws Exception {
		String code = "case $a {present : { $x=1 $y=2 } absent,foo: {$x=2 $y=2}}";
		String fmt = "case $a {\n  present     : {\n    $x = 1\n    $y = 2\n  }\n  absent, foo : {\n    $x = 2\n    $y = 2\n  }\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", fmt, s);
	}

	public void test_Serialize_Definition() throws Exception {
		String code = "define a {$a=10 $b=20}";
		String fmt = "define a {\n  $a = 10\n  $b = 20\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));

		assertEquals("serialization should produce specified result", fmt, s);
	}

	public void test_Serialize_HostClassDefinition() throws Exception {
		String code = "class a {$a=1 $b=2}";
		String fmt = "class a {\n  $a = 1\n  $b = 2\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", fmt, s);

	}

	public void test_Serialize_HostClassExpression() {
		PuppetManifest pp = pf.createPuppetManifest();
		HostClassDefinition cd = pf.createHostClassDefinition();
		pp.getStatements().add(cd);
		cd.setClassName("testClass");

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_ClassDefinition, s);

	}

	public void test_Serialize_IfExpression2() throws Exception {
		String code = "if$a==1{true}else{ false }if$a==1{true}elsif$b< -3{false}else{true}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", Sample_If, s);

	}

	public void test_Serialize_IfExpression3() throws Exception {
		String code = "if$a==1{$x=1 $y=2}elsif $a==2 {$x=3 $y=4}else{ $x=5 $y=6 }";
		String fmt = "if $a == 1 {\n  $x = 1\n  $y = 2\n} elsif $a == 2 {\n  $x = 3\n  $y = 4\n} else {\n  $x = 5\n  $y = 6\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", fmt, s);

	}

	public void test_Serialize_ImportExpressionDq() throws Exception {
		String code = "import \"a\"\nimport \"b\"\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));

		// DEBUG
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));

		assertEquals("serialization should produce specified result", code, s);
	}

	public void test_Serialize_ImportExpressionSq() throws Exception {
		String code = "import 'a'\nimport 'b'\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		// DEBUG
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));

		assertEquals("serialization should produce specified result", code, s);
	}

	public void test_Serialize_MatchingExpression() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		LiteralRegex regex = pf.createLiteralRegex();
		regex.setValue("/[a-z]*/");
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(regex);
		pp.getStatements().add(me);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Match1, s);

		me.setOpName("!~");
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Match2, s);
	}

	public void test_Serialize_NodeDefinition() throws Exception {
		String code = "node a {$a=1 $b=2}";
		String fmt = "node a {\n  $a = 1\n  $b = 2\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", fmt, s);

	}

	public void test_Serialize_RelationshipExpression() {
		// -- serialize file { 'file1': } -> file{'file2': } -> file{'file3' : }
		PuppetManifest pp = pf.createPuppetManifest();
		EList<Expression> statements = pp.getStatements();

		RelationshipExpression rel1 = pf.createRelationshipExpression();
		RelationshipExpression rel2 = pf.createRelationshipExpression();
		ResourceExpression r1 = createResourceExpression("file", "file1");
		ResourceExpression r2 = createResourceExpression("file", "file2");
		ResourceExpression r3 = createResourceExpression("file", "file3");

		rel1.setOpName("->");
		rel1.setLeftExpr(r1);
		rel1.setRightExpr(r2);
		rel2.setOpName("->");
		rel2.setLeftExpr(rel1);
		rel2.setRightExpr(r3);

		statements.add(rel2);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Relationship, s);

	}

	/**
	 * Tests append Notok states:
	 * - $x[a] += expr
	 * - a += expr
	 */
	public void test_Validate_AppendExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		pp.getStatements().add(ae);

		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(v);
		at.getParameters().add(createNameOrReference("a"));

		ae.setLeftExpr(at);
		ae.setRightExpr(b);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__NOT_APPENDABLE);

		ae.setLeftExpr(createNameOrReference("a"));
		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__NOT_APPENDABLE);

	}

	/**
	 * Tests append Notok states:
	 * - $0 += expr
	 */
	public void test_Validate_AppendExpression_NotOk_Decimal() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$0");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__ASSIGNMENT_DECIMAL_VAR);

	}

	/**
	 * Tests append Notok states:
	 * - $a::b += expr
	 */
	public void test_Validate_AppendExpression_NotOk_Scope() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$a::b");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__ASSIGNMENT_OTHER_NAMESPACE);

	}

	/**
	 * Tests append ok states:
	 * - $x += expr
	 */
	public void test_Validate_AppendExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		AppendExpression ae = pf.createAppendExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertOK();
	}

	public void test_Validate_AssignmentExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ae = pf.createAssignmentExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		LiteralNameOrReference v = createNameOrReference("x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__NOT_ASSIGNABLE);

	}

	/**
	 * Tests assignment not ok states:
	 * - $0 = expr
	 */
	public void test_Validate_AssignmentExpression_NotOk_Decimal() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ae = pf.createAssignmentExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$0");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__ASSIGNMENT_DECIMAL_VAR);
	}

	/**
	 * Tests assignment not ok states:
	 * - $a::b = expr
	 */
	public void test_Validate_AssignmentExpression_NotOk_Scope() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ae = pf.createAssignmentExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$a::b");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__ASSIGNMENT_OTHER_NAMESPACE);
	}

	/**
	 * Tests assignemt ok states:
	 * - $x = expr
	 * - $x[expr] = expr
	 */
	public void test_Validate_AssignmentExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression ae = pf.createAssignmentExpression();
		LiteralBoolean b = pf.createLiteralBoolean();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		ae.setLeftExpr(v);
		ae.setRightExpr(b);
		pp.getStatements().add(ae);

		tester.validate(pp).assertOK();

		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(v);
		at.getParameters().add(createNameOrReference("a"));
		ae.setLeftExpr(at);
		tester.validate(pp).assertOK();
	}

	public void test_Validate_ImportExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		ImportExpression ip = pf.createImportExpression();
		pp.getStatements().add(ip);

		tester.validate(ip).assertError(IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
	}

	public void test_Validate_ImportExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		ImportExpression ip = pf.createImportExpression();
		ip.getValues().add(createSqString("somewhere/*.pp"));
		pp.getStatements().add(ip);

		tester.validate(ip).assertOK();
		ip.getValues().add(createSqString("nowhere/*.pp"));
		tester.validate(ip).assertOK();
	}

	public void test_Validate_Manifest_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(v);
		v.setVarName("$x");
		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__NOT_TOPLEVEL);
	}

	public void test_Validate_Manifest_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression a = pf.createAssignmentExpression();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(a);
		v.setVarName("$x");
		a.setLeftExpr(v);
		LiteralNameOrReference value = createNameOrReference("10");
		a.setRightExpr(value);
		tester.validate(pp).assertOK();
	}

	public void test_Validate_MatchingExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		VariableExpression v2 = pf.createVariableExpression();
		v2.setVarName("$y");

		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(v2);
		pp.getStatements().add(me);

		tester.validate(me).assertError(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		me.setOpName("~=");
		tester.validate(me).assertAll(
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__ILLEGAL_OP),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION));
	}

	public void test_Validate_MatchingExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		MatchingExpression me = pf.createMatchingExpression();
		LiteralRegex regex = pf.createLiteralRegex();
		regex.setValue("/[a-z]*/");
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$x");
		me.setLeftExpr(v);
		me.setOpName("=~");
		me.setRightExpr(regex);
		pp.getStatements().add(me);

		tester.validate(me).assertOK();

		me.setOpName("!~");
		tester.validate(me).assertOK();
	}

	public void test_Validate_RelationshipExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		// -- check file { 'file1': } -> file{'file2': } -> file{'file3' : }
		RelationshipExpression rel1 = pf.createRelationshipExpression();
		pp.getStatements().add(rel1);

		ResourceExpression r1 = createResourceExpression("file", "file1");
		LiteralNameOrReference r2 = createNameOrReference("a");

		rel1.setOpName("->");
		rel1.setLeftExpr(r1);
		rel1.setRightExpr(r2);

		tester.validate(pp).assertError(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	/**
	 * Test that the four different relationship expressions operands can be used between
	 * the allowed operands.
	 */
	public void test_Validate_RelationshipExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		// -- check file { 'file1': } -> file{'file2': } -> file{'file3' : }
		RelationshipExpression rel1 = pf.createRelationshipExpression();
		RelationshipExpression rel2 = pf.createRelationshipExpression();
		pp.getStatements().add(rel2);
		ResourceExpression r1 = createResourceExpression("file", "'file1'");
		ResourceExpression r2 = createResourceExpression("file", "'file2'");
		ResourceExpression r3 = createResourceExpression("file", "'file3'");

		rel1.setOpName("->");
		rel1.setLeftExpr(r1);
		rel1.setRightExpr(r2);
		rel2.setOpName("->");
		rel2.setLeftExpr(rel1);
		rel2.setRightExpr(r3);

		tester.validator().checkRelationshipExpression(rel2);
		tester.diagnose().assertOK();

		// -- check the other operators
		rel2.setOpName("<-");
		tester.validator().checkRelationshipExpression(rel2);
		tester.diagnose().assertOK();

		rel2.setOpName("<~");
		tester.validator().checkRelationshipExpression(rel2);
		tester.diagnose().assertOK();

		rel2.setOpName("~>");
		tester.validator().checkRelationshipExpression(rel2);
		tester.diagnose().assertOK();

		// -- check the other possible left/right expressions
		// - virtual
		// - resource reference
		// - collect expression
		AtExpression at = pf.createAtExpression();
		at.setLeftExpr(createNameOrReference("x"));
		at.getParameters().add(createNameOrReference("a"));
		r1.setResourceExpr(at); // resource reference

		VirtualNameOrReference vn = pf.createVirtualNameOrReference();
		vn.setValue("y");
		vn.setExported(true);
		r2.setResourceExpr(vn);

		CollectExpression ce = pf.createCollectExpression();
		ce.setClassReference(createNameOrReference("User"));
		EqualityExpression predicate = pf.createEqualityExpression();
		predicate.setLeftExpr(createNameOrReference("name"));
		predicate.setOpName("==");
		predicate.setRightExpr(createNameOrReference("Luke"));

		VirtualCollectQuery q = pf.createVirtualCollectQuery();
		q.setExpr(predicate);
		ce.setQuery(q);

		rel2.setRightExpr(ce);

		tester.validator().checkRelationshipExpression(rel2);
		tester.diagnose().assertOK();

	}

	public void test_Validate_VariableExpression_NotOk() {
		PuppetManifest pp = pf.createPuppetManifest();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(v);

		// name is null
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		v.setVarName("");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		v.setVarName("x");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);

		// period is allowed in names, but not in variables
		v.setVarName("$3.4");
		tester.validate(v).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	public void test_Validate_VariableExpression_Ok() {
		PuppetManifest pp = pf.createPuppetManifest();
		VariableExpression v = pf.createVariableExpression();
		pp.getStatements().add(v);
		v.setVarName("$x");

		tester.validate(v).assertOK();

		v.setVarName("$abc123");
		tester.validate(v).assertOK();

		v.setVarName("$0");
		tester.validate(v).assertOK();

		v.setVarName("$3_4");
		tester.validate(v).assertOK();

	}

}
