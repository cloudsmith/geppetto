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

import java.util.Map;

import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.cloudsmith.geppetto.ruby.resource.PptpRubyResourceFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Tests.
 */
@SuiteClasses({
// @fmtOff
	TestRubyDocProcessor.class,
	TestRubyDocProcessor2.class,
	PptpResourceTests.class,
	SmokeTest.class,
	PuppetFunctionTests.class,
	PuppetTypeTests.class,
	PuppetTPTests.class
// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
	@BeforeClass
	public static void initRubyService() {
		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
		Registry registry = EPackage.Registry.INSTANCE;
		if(!registry.containsKey(org.eclipse.xtext.XtextPackage.eNS_URI))
			registry.put(org.eclipse.xtext.XtextPackage.eNS_URI, org.eclipse.xtext.XtextPackage.eINSTANCE);

		if(!registry.containsKey(org.cloudsmith.geppetto.pp.PPPackage.eNS_URI))
			registry.put(org.cloudsmith.geppetto.pp.PPPackage.eNS_URI, org.cloudsmith.geppetto.pp.PPPackage.eINSTANCE);

		if(!registry.containsKey(org.cloudsmith.geppetto.pp.pptp.PPTPPackage.eNS_URI))
			registry.put(
				org.cloudsmith.geppetto.pp.pptp.PPTPPackage.eNS_URI,
				org.cloudsmith.geppetto.pp.pptp.PPTPPackage.eINSTANCE);

		Map<String, Object> factoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		if(!factoryMap.containsKey("pptp"))
			factoryMap.put("pptp", new org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl());

		if(!factoryMap.containsKey("rb"))
			factoryMap.put("rb", new PptpRubyResourceFactory());
	}
}
