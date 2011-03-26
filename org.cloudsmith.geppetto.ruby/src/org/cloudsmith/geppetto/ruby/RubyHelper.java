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
package org.cloudsmith.geppetto.ruby;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.ruby.spi.IRubyServices;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.google.common.collect.Lists;

/**
 * Provides access to ruby parsing / information services. If an implementation is not available, a mock
 * service is used - this can be checked with {@link #isRubyServicesAvailable()}. The mock service will provide
 * an empty parse result (i.e. "no errors or warning"), and will return empty results for information.
 * 
 * The caller can then adjust how to deal with service not being present.
 * 
 * To use the RubyHelper, a call must be made to {@link #setUp()}, then followed by a series of requests to parse
 * or get information. 
 *
 */
public class RubyHelper {
	
	private IRubyServices rubyProvider;

	/**
	 * Returns true if real ruby services are available. 
	 */
	public boolean isRubyServicesAvailable() {
		if(rubyProvider == null)
			loadRubyServiceExtension();
		return !rubyProvider.isMockService();
	}
	/**
	 * Should be called to initiate the ruby services. Each call to setUp should be paired with
	 * a call to tearDown or resources will be wasted.
	 */
	public void setUp() {
		if(rubyProvider == null)
			loadRubyServiceExtension();
		rubyProvider.setUp();
	}
	/**
	 * Loads a service extension, or creates a mock implementation.
	 */
	private void loadRubyServiceExtension() {
	IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(
			Activator.EXTENSION__RUBY_SERVICE);
		List<IRubyServices> services = Lists.newArrayList();
		for(IConfigurationElement e : configs) {
			try {
				services.add(IRubyServices.class.cast(e.createExecutableExtension(Activator.EXTENSION__RUBY_SERVICE_SERVICECLASS)));
			}
			catch(Exception e1) {
				System.err.println("Loading of RuntimeModule extension failed with exception: " + e1.getMessage());
			}
		}
		if(services.size() < 1) {
			System.err.println("No RubyServices loaded - some functionality will be limited.");
			rubyProvider = new MockRubyServices();
		}
		else
			rubyProvider = services.get(0);
	}
	
	public void tearDown() {
		if(rubyProvider == null)
			return; // ignore silently
		
		rubyProvider.tearDown();
		
	}
	/**
	 * Parse a .rb file and return information about syntax errors and warnings. Must be preceded with
	 * a call to setUp().
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException if setUp was not called.
	 */
	public IRubyParseResult parse(File file) throws IOException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before parse(File).");
		return rubyProvider.parse(file);
	}
	
	/**
	 * Returns a list of custom PP parser functions from the given .rb file. The returned list is
	 * empty if no function could be found.
	 * @param file
	 * @return
	 * @throws IOException - if there are errors reading the file
	 * @throws IllegalStateException - if setUp was not called
	 */
	public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getFunctionInfo(File).");
		if(file == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getFunctionInfo");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());

		return rubyProvider.getFunctionInfo(file);
		
	}
	/**
	 * Returns a list of custom PP types from the given .rb file. The returned list is
	 * empty if no type could be found.
	 * @param file
	 * @return
	 * @throws IOException - if there are errors reading the file
	 * @throws IllegalStateException - if setUp was not called
	 */
	public List<PPTypeInfo> getTypeInfo(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(file == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getTypeInfo");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		return rubyProvider.getTypeInfo(file);
		
	}
	private static class MockRubyServices implements IRubyServices {
		private static final List<PPFunctionInfo> emptyFunctionInfo = Collections.emptyList();
		private static final List<PPTypeInfo> emptyTypeInfo = Collections.emptyList();
		private static final IRubyParseResult emptyParseResult = new EmptyParseResult();
		
			private static class EmptyParseResult implements IRubyParseResult {
				private static final List<IRubyIssue> emptyIssues = Collections.emptyList();

				@Override
				public List<IRubyIssue> getIssues() {
					return emptyIssues;
				}

				@Override
				public boolean hasErrors() {
					return false;
				}

				@Override
				public boolean hasIssues() {
					return false;
				}
			
		}
		@Override
		public void setUp() {
			// do nothing
		}

		@Override
		public void tearDown() {
			// do nothing
		}

		@Override
		public IRubyParseResult parse(File file) throws IOException {
			return emptyParseResult;
		}

		@Override
		public List<PPFunctionInfo> getFunctionInfo(File file)
				throws IOException {
			return emptyFunctionInfo;
		}

		@Override
		public List<PPTypeInfo> getTypeInfo(File file) throws IOException {
			return emptyTypeInfo;
		}

		@Override
		public boolean isMockService() {
			return true;
		}
		
	}

}
