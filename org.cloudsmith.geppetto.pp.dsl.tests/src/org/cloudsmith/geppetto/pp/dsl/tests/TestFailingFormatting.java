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

import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests for problematic formatting.
 * 
 */
public class TestFailingFormatting extends AbstractPuppetTests {

	/**
	 * Formatter seems to not switch back to non hidden state interpolation.
	 * 
	 */
	public void test_Serialize_DqStringInterpolation() throws Exception {
		String code = "$a = \"a${1}b\"\nclass a {\n}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * Without interpolation formatting does the right thing.
	 */
	public void test_Serialize_DqStringNoInterpolation() throws Exception {
		String code = "$a = \"ab\"\nclass a {\n}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));

		assertEquals("serialization should produce specified result", code, s);
	}

}
