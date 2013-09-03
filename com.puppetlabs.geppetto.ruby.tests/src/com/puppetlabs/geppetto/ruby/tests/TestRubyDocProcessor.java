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
package com.puppetlabs.geppetto.ruby.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.puppetlabs.geppetto.ruby.RubyDocProcessorSimple;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * @author henrik
 * 
 */
public class TestRubyDocProcessor {
	public static final List<String> content1 = ImmutableList.of( //
		"This is an example:\n", //
		"  verbatim\n", //
		"  verbatim\n", //
		"Non verbatim\n");

	public static final List<String> content1Expected = ImmutableList.of( //
		"<p>This is an example:\n", //
		"</p>\n", //
		"<pre>", //
		"  verbatim\n", //
		"  verbatim\n", //
		"</pre>\n", //
		"<p>Non verbatim\n", //
		"</p>");

	@Test
	public void testRubyDocprocessor() {

		String result = RubyDocProcessorSimple.asHTML(Joiner.on("").join(content1));
		assertEquals(Joiner.on("").join(content1Expected), result);

	}
}
