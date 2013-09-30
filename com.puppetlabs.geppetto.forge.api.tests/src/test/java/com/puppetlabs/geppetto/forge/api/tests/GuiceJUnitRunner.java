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
package com.puppetlabs.geppetto.forge.api.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * A JUnit runner that performs Guice injection based on bindings from a {@TestModules} annotation
 */
public class GuiceJUnitRunner extends BlockJUnit4ClassRunner {

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	public @interface TestModules {
		Class<? extends Module>[] value();
	}

	private final Injector injector;

	public GuiceJUnitRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		TestModules testModulesAnnotation = testClass.getAnnotation(TestModules.class);
		List<Module> testModules;
		if(testModulesAnnotation == null)
			testModules = Collections.emptyList();
		else {
			Class<? extends Module>[] testModuleClasses = testModulesAnnotation.value();
			testModules = new ArrayList<Module>(testModuleClasses.length);
			try {
				for(Class<? extends Module> testModuleClass : testModuleClasses)
					testModules.add(testModuleClass.newInstance());
			}
			catch(Exception e) {
				throw new InitializationError(e);
			}
		}
		injector = Guice.createInjector(testModules);
	}

	@Override
	public Object createTest() throws Exception {
		Object test = super.createTest();
		injector.injectMembers(test);
		return test;
	}
}
