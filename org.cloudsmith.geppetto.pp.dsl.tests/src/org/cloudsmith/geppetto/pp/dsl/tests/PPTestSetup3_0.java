/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.dsl.PPStandaloneSetup;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationAdvisorProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.service.AbstractGenericModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.util.Modules;

/**
 * Adds handling of PPTP.
 * 
 */
public class PPTestSetup3_0 extends PPStandaloneSetup {
	public static class PPTestModule extends AbstractGenericModule {

		public com.google.inject.Provider<IValidationAdvisor> provideValidationAdvisor() {
			return ValidationAdvisorProvider.create(IValidationAdvisor.ComplianceLevel.PUPPET_3_0);
		}

		// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
		public com.google.inject.Provider<XtextResourceSet> provideXtextResourceSet() {
			return new Provider<XtextResourceSet>() {

				@Override
				public XtextResourceSet get() {
					XtextResourceSet resourceSet = new SynchronizedXtextResourceSet();
					resourceSet.getResource(
						URI.createPlatformPluginURI("/org.cloudsmith.geppetto.pp.dsl/targets/puppet-3.0.0.pptp", true),
						true);
					return resourceSet;
				}

			};

		}

	}

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules.override(new org.cloudsmith.geppetto.pp.dsl.PPRuntimeModule()).with(
			new PPTestModule()));
	}
}
