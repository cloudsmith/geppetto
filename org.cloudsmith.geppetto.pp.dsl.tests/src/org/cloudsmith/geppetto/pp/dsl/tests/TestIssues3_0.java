/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Tests specific to reported issues using Puppet 3.0 and 3.0 validation.
 * Inherits from TestIssues to also test all validations for 2.7.
 * Override methods in this class if they should be tested a different way for 3.0.
 * 
 */
public class TestIssues3_0 extends TestIssues {

	@Override
	protected Class<? extends ISetup> getSetupClass() {
		return PPTestSetup3_0.class;
	}

	public void test_inheritFromParameterizedClass_issue381() throws Exception {
		String code = "class base($basevar) {} class derived inherits base {}";
		Resource r = loadAndLinkSingleResource(code);

		tester.validate(r.getContents().get(0)).assertOK(); // assertWarning(IPPDiagnostics.ISSUE__ASSIGNMENT_TO_VAR_NAMED_STRING);
		resourceErrorDiagnostics(r).assertOK();
	}

	@Override
	public void test_Issue400() throws Exception {
		ImmutableList<String> source = ImmutableList.of("notify { [a, b, c]:", //
			"}", //
			"$var = Notify[a]", //
			"$var -> case 'x' {", "  'x' : {", //
			"    notify { d:", //
			"    }", //
			"  }", //
			"} ~> 'x' ? {", //
			"  'y'     => Notify[b],", //
			"  default => Notify[c]", //
			"}\n");
		String code = Joiner.on("\n").join(source).toString();
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();

	}

	@Override
	public void test_Issue403() throws Exception {
		String code = "class foo(a) { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	public void test_variableNamedString_issue408() throws Exception {
		String code = "$string = 'gotcha'";
		XtextResource r = getResourceFromString(code);

		tester.validate(r.getContents().get(0)).assertWarning(IPPDiagnostics.ISSUE__ASSIGNMENT_TO_VAR_NAMED_STRING);
		resourceErrorDiagnostics(r).assertOK();

	}

}
