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
package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;

import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;
import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.ruby.RubyHelper;

import org.eclipse.core.runtime.Path;

import junit.framework.TestCase;

public class PuppetTPTests extends TestCase {

	public void testLoadDistro() throws Exception {
		File distroDir = TestDataProvider
		.getTestFile(new Path(
				"testData/mock-puppet-distro/puppet/2.6.2_0/puppet"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			TargetEntry target = helper.loadDistroTarget(distroDir);
			
			// check the target itself
			assertNotNull("Should have resultet in a TargetEntry", target);
			assertEquals("Should have defined description", "Puppet Distribution", target.getDescription());
			assertEquals("Should have defined name", "puppet 2.6.2_0", target.getName());
			assertEquals("Should have file set to defined file", distroDir, target.getFile());
			
			// should have found one type "mocktype"
			assertEquals("Should have found one type", 1, target.getTypes().size());
			Type type = target.getTypes().get(0);
			assertEquals("Should have found 'mocktype'", "mocktype", type.getName());
			assertEquals("Should have found documentation", "This is a mock type", type.getDocumentation());
			
			assertEquals("Should have three properties", 3, type.getProperties().size());
			{
				Property prop = getProperty("prop1", type);
				assertNotNull("Should have a property 'prop1", prop);
				assertEquals("Should have defined documentation", "This is property1", prop.getDocumentation());
			}
			{
				Property prop = getProperty("extra1", type);
				assertNotNull("Should have a property 'extra1", prop);
				assertEquals("Should have defined documentation", "An extra property called extra1", prop.getDocumentation());
			}
			{
				Property prop = getProperty("extra2", type);
				assertNotNull("Should have a property 'extra2", prop);
				assertEquals("Should have defined documentation", "An extra property called extra2", prop.getDocumentation());
			}
			
			
			
			assertEquals("Should have one parameter", 1, type.getParameters().size());
			Parameter param = getParameter("param1", type);
			assertNotNull("Should have a parameter 'param1", param);
			assertEquals("Should have defined documentation", "This is parameter1", param.getDocumentation());
			
			
			// one property
			
			// should have found one function "echotest"
		}
		finally {
			helper.tearDown();
		}
	}
	private Property getProperty(String name, Type type) {
		for(Property p : type.getProperties())
			if(name.equals(p.getName()))
				return p;
		return null;
	}
	private Parameter getParameter(String name, Type type) {
		for(Parameter p : type.getParameters())
			if(name.equals(p.getName()))
				return p;
		return null;
	}
}
