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

import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests WS and comments.
 * (Warnings from unknown variables ignored - they appear only on stdout which is nulled).
 */
public class TestWsAndComments extends AbstractPuppetTests {
	private PrintStream savedOut;

	// PLACE formatting samples here
	public static final String sample_definitionDocumentation1 = "# 1. sl cmnt1\n" //
			+ "# 2. sl cmnt2\n" + //
			"define myDefinition {\n" + //
			"}";

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

	public void test_Serialize_LeadingComments() throws Exception {
		String code = "# 1. sl cmnt\n" + "# 2. sl cmnt\n" + "$a\n";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);

	}

	public void test_Serialize_SmokeTest() throws Exception {
		String code = "$a\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);

		s = serialize(r.getContents().get(0));
		assertEquals("formatted serialization should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2() throws Exception {
		String code = "$a + $b\n";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2Formatted() throws Exception {
		String code = "$a + $b\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization with formatting should produce same result", code, s);
	}

	public void test_Serialize_SmokeTest2Formatted2() throws Exception {
		String code = "$a+$b";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization with formatting should add space around +", "$a + $b\n", s);
	}

	public void test_Serialize_SmokeTest2WithComment() throws Exception {
		String code = "$x = $a/* add a */+/* with b */$b\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals(
			"serialization with formatting should add space around +", "$x = $a /* add a */ + /* with b */ $b\n", s);
	}

	public void test_Serialize_SmokeTest3() throws Exception {
		String code = "$a + 'apa'\n";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}

	public void test_SerializeCommentMix() throws Exception {
		String code = "/* 1 */ class /* 2 */ a /* 3 */ { /* 4 */\n" + //
				"  /* 5 */ $b /* 6 */ = /* 7 */ 10 /* 8 */\n" + //
				"/* 9 */ } /* 10 */\n" + //
				"/* 11 */\n";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));
		assertEquals("serialization should produce same result", code, s);
	}
}
