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

	@Override
	public void test_Issue403() throws Exception {
		String code = "class foo(a) { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

}
