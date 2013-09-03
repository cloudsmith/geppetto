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
package org.cloudsmith.geppetto.ruby.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.cloudsmith.geppetto.ruby.RubyDocProcessor;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * @author henrik
 * 
 */
public class TestRubyDocProcessor2 {
	public static final List<String> content1 = ImmutableList.of( //
		"This is an example:\n", //
		"  verbatim\n", //
		"  verbatim\n", //
		"Non verbatim\n");

	public static final List<String> content1Expected = ImmutableList.of( //
		"<p>This is an example:", //
		"<pre>", //
		"verbatim\n", //
		"verbatim\n", //
		"</pre>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content2 = ImmutableList.of( //
		"This is an example:\n", //
		"    verbatim\n", //
		"     verbatim\n", //
		"Non verbatim\n");

	public static final List<String> content2Expected = ImmutableList.of( //
		"<p>This is an example:", //
		"<pre>", //
		"  verbatim\n", //
		"   verbatim\n", //
		"</pre>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content3 = ImmutableList.of( //
		"This is an example:\n", //
		"* bullet1\n", //
		"* bullet2\n", //
		"  bullet2\n", //
		"  bullet2\n", //
		"Non verbatim\n");

	public static final List<String> content3Expected = ImmutableList.of( //
		"<p>This is an example:", //
		"<ul>", //
		"<li><p>bullet1</p></li>", //
		"<li><p>bullet2 bullet2 bullet2</p></li>", //
		"</ul>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content4 = ImmutableList.of( //
		"This is an example:\n", //
		" * bullet2\n", //
		"   bullet2\n", //
		"     verbatim\n", //
		"      verbatim\n", //
		"Non verbatim\n");

	public static final List<String> content4Expected = ImmutableList.of( //
		"<p>This is an example:", //
		"<ul><li><p>bullet2 bullet2", //
		"<pre>", //
		"verbatim\n", //
		" verbatim\n", //
		"</pre></p></li></ul>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content5 = ImmutableList.of( //
		"This is an _example_:\n", //
		"* **bullet1**\n", //
		"* *bullet2*\n", //
		"  +bullet2+\n", //
		"  `bullet2`\n", //
		"Non verbatim\n");

	public static final List<String> content5Expected = ImmutableList.of( //
		"<p>This is an <i>example</i>:", //
		"<ul>", //
		"<li><p><strong>bullet1</strong></p></li>", //
		"<li><p><b>bullet2</b> <tt>bullet2</tt> <tt>bullet2</tt></p></li>", //
		"</ul>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content5a = ImmutableList.of( //
		"--\n", //
		" comment\n", //
		"++\n", //
		"This is an _example_:\n", //
		"--\n", //
		" comment\n", //
		"++\n", //
		"* **bullet1**\n", //
		"--\n", //
		" comment\n", //
		"++\n", //
		"* *bullet2*\n", //
		"--\n", //
		" comment\n", //
		"++\n", //
		"  +bullet2+\n", //
		"--\n", //
		" comment\n", //
		"++\n", //
		"  `bullet2`\n", //
		"--\n", //
		" comment\n", //
		"++\n", //
		"Non verbatim\n");

	public static final List<String> content6 = ImmutableList.of( //
		"=Head1\n", //
		"This is an _example_:\n", //
		"==Head2\n", //
		"* **bullet1**\n", //
		"* *bullet2*\n", //
		"  +bullet2+\n", //
		"  `bullet2`\n", //
		"===Head3\n", //
		"Non verbatim\n", //
		"========Head8\n" //
	);

	public static final List<String> content7 = ImmutableList.of( //
		"This is an example:\n", //
		" * bullet1\n", //
		"   bullet1\n", //
		"     * bullet2\n", //
		"       bullet2\n", //
		"Non verbatim\n");

	public static final List<String> content7Expected = ImmutableList.of( //
		"<p>This is an example:", //
		"<ul><li><p>bullet2 bullet2", //
		"<pre>", //
		"verbatim\n", //
		" verbatim\n", //
		"</pre></p></li></ul>", //
		"Non verbatim", //
		"</p>");

	public static final List<String> content6Expected = ImmutableList.of( //
		"<h1>Head1</h1>", //
		"<p>This is an <i>example</i>:</p>", //
		"<h2>Head2</h2>", //
		"<ul>", //
		"<li><p><strong>bullet1</strong></p></li>", //
		"<li><p><b>bullet2</b> <tt>bullet2</tt> <tt>bullet2</tt></p></li>", //
		"</ul>", //
		"<h3>Head3</h3>", //
		"<p>Non verbatim</p>", //
		"<h5>Head8</h5>" //
	);

	@Test
	public void testRubyDocprocessor_1() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content1));
		assertEquals(Joiner.on("").join(content1Expected), result);
	}

	@Test
	public void testRubyDocprocessor_2() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content2));
		assertEquals(Joiner.on("").join(content2Expected), result);
	}

	@Test
	public void testRubyDocprocessor_3() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content3));
		assertEquals(Joiner.on("").join(content3Expected), result);
	}

	@Test
	public void testRubyDocprocessor_4() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content4));
		assertEquals(Joiner.on("").join(content4Expected), result);
	}

	@Test
	public void testRubyDocprocessor_5() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content5));
		assertEquals(Joiner.on("").join(content5Expected), result);
	}

	@Test
	public void testRubyDocprocessor_5a() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content5a));
		assertEquals(Joiner.on("").join(content5Expected), result);
	}

	@Test
	public void testRubyDocprocessor_6() {
		String result = new RubyDocProcessor().asHTML(Joiner.on("").join(content6));
		assertEquals(Joiner.on("").join(content6Expected), result);
	}
}
