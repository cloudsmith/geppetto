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

import org.cloudsmith.geppetto.pp.OWS;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests Puppet ResourceExpression
 * - regular ResourceExpression
 * - VirtualResourceExpression
 * ResourceExpression used as:
 * - default resource
 * - resource override
 * 
 * Tests serialization and validation.
 */
public class TestWsAndComments extends AbstractPuppetTests {
	// IMPORTANT - MAKE SURE THESE ARE NOT SCREWED UP ON CHECKIN - MAKES IT VERY DIFFICULT TO READ
	// @formatter:off
	// PLACE formatting samples here

	// @formatter:on

	public void test_Serialize_LeadingComments() throws Exception {
		String code = "# 1. sl cmnt\n" + "# 2. sl cmnt\n" + "$a";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);

	}

	public void test_Serialize_LeadingCommentsModeled() throws Exception {
		PuppetManifest mf = pf.createPuppetManifest();
		OWS ows = pf.createOWS();
		mf.setLeadingSpaceAndComments(ows);
		ows.getValues().add("# 1. sl cmnt\n");
		ows.getValues().add("# 2. sl cmnt\n");
		VariableExpression var = pf.createVariableExpression();
		var.setVarName("$a");
		mf.getStatements().add(var);
		String code = "# 1. sl cmnt\n" + "# 2. sl cmnt\n" + "$a";
		String s = serializeFormatted(mf);
		assertEquals("serialization should produce same result", code, s);

	}

	public void test_Serialize_SmokeTest() throws Exception {
		String code = "$a";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);

		s = serialize(r.getContents().get(0));
		assertEquals("formatted serialization should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2() throws Exception {
		String code = "$a + $b";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2Formatted() throws Exception {
		String code = "$a + $b";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization with formatting should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2Formatted2() throws Exception {
		String code = "$a+$b";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization with formatting should add space around +", "$a + $b", s);
	}

	public void test_Serialize_SmokeTest2WithComment() throws Exception {
		String code = "$x = $a/* add a */+/* with b */$b";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals(
			"serialization with formatting should add space around +", "$x = $a /* add a */ + /* with b */ $b", s);
	}

	public void test_Serialize_SmokeTest3() throws Exception {
		String code = "$a + 'apa'";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}
}
