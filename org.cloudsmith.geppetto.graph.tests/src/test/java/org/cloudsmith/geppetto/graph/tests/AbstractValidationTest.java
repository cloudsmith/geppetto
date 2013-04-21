/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.graph.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.impl.ForgeModule;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;
import org.cloudsmith.geppetto.graph.DependencyGraphProducer;
import org.cloudsmith.geppetto.graph.JavascriptHrefProducer;
import org.cloudsmith.geppetto.graph.SVGProducer;
import org.cloudsmith.geppetto.graph.dependency.DependencyGraphModule;
import org.cloudsmith.geppetto.pp.dsl.target.PuppetTarget;
import org.cloudsmith.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationAdvisor;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.impl.ValidationModule;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.IEncodingProvider;
import org.cloudsmith.geppetto.validation.runner.PPDiagnosticsSetup;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

public class AbstractValidationTest {
	private Injector injector;

	protected final void assertContainsErrorCode(Diagnostic chain, String errorCode) {
		List<String> issues = Lists.newArrayList();
		for(Diagnostic d : chain.getChildren())
			if(d.getIssue() != null)
				issues.add(d.getIssue());
		assertTrue("Should contain error: " + errorCode, issues.contains(errorCode));

	}

	protected final void dumpErrors(Diagnostic chain) {
		System.err.println(errorsToString(chain));
	}

	protected final void dumpExports(AllModuleReferences exports) {
		Multimap<File, Export> exportmap = exports.getExportsPerModule();
		for(File f : exportmap.keySet()) {
			System.err.println("Exports from: " + f);
			for(Export e : exportmap.get(f)) {
				System.err.printf("    %s, %s inherits %s\n", e.getEClass().getName(), e.getName(), e.getParentName());
			}
		}
	}

	protected final void dumpExports(Iterable<Export> exports) {
		System.err.println("START DUMP");
		System.err.println("==========");
		for(Export e : exports) {
			System.err.printf("    %s, %s inherits %s\n", e.getEClass().getName(), e.getName(), e.getParentName());
		}
	}

	protected final String errorsToString(Diagnostic chain) {
		StringBuilder builder = new StringBuilder();
		for(Diagnostic d : chain.getChildren()) {
			builder.append("Diagnostic: ");
			// remove the "found in:" part as the order is not stable
			String msg = d.getMessage();
			int idx = msg.indexOf("found in:");
			if(idx != -1)
				msg = msg.substring(0, idx);
			builder.append(msg);
			builder.append("\n");
		}
		return builder.toString();
	}

	public DependencyGraphProducer getDependencyGraphProducer() {
		return injector.getInstance(DependencyGraphProducer.class);
	}

	public SVGProducer getSVGProducer() {
		return injector.getInstance(SVGProducer.class);
	}

	protected ValidationOptions getValidationOptions() {
		return getValidationOptions(ComplianceLevel.PUPPET_2_7);
	}

	protected ValidationOptions getValidationOptions(ComplianceLevel complianceLevel) {
		ValidationOptions options = new ValidationOptions();
		options.setPlatformURI(PuppetTarget.forComplianceLevel(complianceLevel, false).getPlatformURI());
		options.setEncodingProvider(new IEncodingProvider() {
			public String getEncoding(URI file) {
				return "UTF-8";
			}
		});
		options.setProblemsAdvisor(ValidationAdvisor.create(complianceLevel, new DefaultPotentialProblemsAdvisor()));
		return options;
	}

	public ValidationService getValidationService() {
		return injector.getInstance(ValidationService.class);
	}

	@Before
	public void setUp() {
		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
		ValidationOptions options = getValidationOptions();
		injector = new PPDiagnosticsSetup(options.getComplianceLevel(), options.getProblemsAdvisor()).createInjectorAndDoEMFRegistration();
		ForgePreferencesBean prefs = new ForgePreferencesBean();
		injector = injector.createChildInjector(
			new ForgeModule(prefs), new ValidationModule(), new DependencyGraphModule(JavascriptHrefProducer.class, ""));
	}
}
