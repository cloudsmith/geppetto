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
package org.cloudsmith.geppetto.validation.runner;

import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;

import org.cloudsmith.geppetto.pp.dsl.PPStandaloneSetup;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Setup of PP runtime with overrides for validation service.
 * 
 */
public class PPDiagnosticsSetup extends PPStandaloneSetup {

	private final ComplianceLevel complianceLevel;

	private IPotentialProblemsAdvisor problemsAdvisor;

	public PPDiagnosticsSetup(IValidationAdvisor.ComplianceLevel complianceLevel,
			IPotentialProblemsAdvisor problemsAdvisor) {
		this.complianceLevel = complianceLevel;
		this.problemsAdvisor = problemsAdvisor;

	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(getCommonModule(), new PPDiagnosticsModule(complianceLevel, problemsAdvisor));
	}

}
