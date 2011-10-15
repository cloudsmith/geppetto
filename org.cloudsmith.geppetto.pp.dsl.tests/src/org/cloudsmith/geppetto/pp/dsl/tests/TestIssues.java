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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests specific to reported issues.
 * 
 */
public class TestIssues extends AbstractPuppetTests {

	/**
	 * [11] Geppetto does not yet know about parameterized classes
	 * https://github.com/cloudsmith/geppetto/issues#issue/11
	 * 
	 * @throws Exception
	 */
	public void test_Issue_11() throws Exception {
		String code = "class { 'file':\n" + //
				"owner => '666',\n" + //
				"}\n";
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	/**
	 * [4] Parser does not understand realize on multiple lines
	 * https://github.com/cloudsmith/geppetto/issues#issue/4
	 * 
	 * @throws Exception
	 */
	public void test_Issue_4() throws Exception {
		String code = "realize (\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				")";
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	/**
	 * [206] single char class name and inheritance results in things not being found
	 * An unqualified reference to an inherited variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_Issue206() throws Exception {
		String code = "class a {\n" + //
				"$x = 10\n" + //
				"class bb inherits a {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n"; //
		;
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}
}
