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
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.PPTPFactory;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.PuppetDistribution;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;
import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.pp.pptp.TypeFragment;
import org.cloudsmith.geppetto.pp.pptp.util.PPTPLinker;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.ruby.spi.IRubyServices;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

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

	private static final FilenameFilter rbFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".rb");
		}

	};
	private static final FileFilter dirFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}

	};

	/**
	 * Loads a Puppet distribution target. The file should point to the "puppet" directory where the
	 * sub-directories "parser" and "type" are. The path to this file is expected to contain a version segment
	 * after the first "puppet" segment e.g. '/somewhere/puppet/2.6.0_0/somewhere/puppet/'
	 * 
	 * The implementation will scan the known locations for definitions that
	 * should be reflected in the target - i.e. parser/functions/*.rb and type/*.rb
	 * @throws IOException on problems with reading files
	 * @throws RubySyntaxException if there are syntax exceptions in the parsed ruby code
	 */
	public TargetEntry loadDistroTarget(File file) throws IOException, RubySyntaxException {
		if(file == null)
			throw new IllegalArgumentException("File can not be null");

		// Create a puppet distro target and parse info from the file path
		//
		PuppetDistribution puppetDistro = PPTPFactory.eINSTANCE.createPuppetDistribution();
		puppetDistro.setDescription("Puppet Distribution");
		IPath path = Path.fromOSString(file.getAbsolutePath());
		String versionString = "";
		boolean nextIsVersion = false;
		for(String s : path.segments())
			if(nextIsVersion) {
				versionString = s;
				break;
			}
			else if("puppet".equals(s))
				nextIsVersion = true;

		puppetDistro.setName("puppet "+versionString);
		// remember this file (for a possible reload), but do not remember file for the contained elements
		puppetDistro.setFile(file);
		final boolean rememberFiles = false;
		// Load functions
		File parserDir = new File(file, "parser");
		File functionsDir = new File(parserDir, "functions");
		loadFunctions(puppetDistro, functionsDir, rememberFiles);

		// Load types
		File typesDir = new File(file, "type");
		loadTypes(puppetDistro, typesDir, rememberFiles);

		// load additional properties into types
		// (currently only known such construct is for 'file' type
		// this implementation does however search all subdirectories
		// for such additions
		//
		for(File subDir : typesDir.listFiles(dirFilter))
			loadTypeFragments(puppetDistro, subDir, rememberFiles);
		return puppetDistro;
	}

	private void loadTypeFragments(TargetEntry target, File subDir, boolean rememberFile) throws IOException, RubySyntaxException {
		for(File f : subDir.listFiles(rbFileFilter)) {
			// try to get type property additions
			List<TypeFragment> result = loadTypeFragments(f, rememberFile);
			for(TypeFragment tf : result)
				PPTPLinker.addTypeFragment(target, tf);
		}
	}
	
	public List<TypeFragment> loadTypeFragments(File rbFile, boolean rememberFile) throws IOException, RubySyntaxException {
		List<TypeFragment> result = Lists.newArrayList();
		for(PPTypeInfo type : getTypeProperties(rbFile)) {
			TypeFragment fragment = PPTPFactory.eINSTANCE.createTypeFragment();
			fragment.setName(type.getTypeName());
			if(rememberFile)
				fragment.setFile(rbFile);
			
			// add the properties (will typically load just one).
			for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getProperties().entrySet()) {
				Property property = PPTPFactory.eINSTANCE.createProperty();
				property.setName(entry.getKey());
				property.setDocumentation(entry.getValue().documentation);
				property.setRequired(entry.getValue().isRequired());
				fragment.getProperties().add(property);
			}
			
			// add the parameters (will typically load just one).
			for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getParameters().entrySet()) {
				Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
				parameter.setName(entry.getKey());
				parameter.setDocumentation(entry.getValue().documentation);
				parameter.setRequired(entry.getValue().isRequired());
				fragment.getParameters().add(parameter);
			}

			result.add(fragment);
		}
		return result;
		
	}

	/**
	 * Load type info into target.
	 * @param target
	 * @param typesDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadTypes(TargetEntry target, File typesDir, boolean rememberFiles) throws IOException, RubySyntaxException {
		if(!typesDir.isDirectory())
			return;
		for(File rbFile : typesDir.listFiles(rbFileFilter))
			for(Type t : loadTypes(rbFile, rememberFiles))
				PPTPLinker.addType(target, t);
	}
	/**
	 * Load type(s) from ruby file.
	 * @param rbFile - the file to parse
	 * @param rememberFiles - if entries should remember the file they came from.
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<Type> loadTypes(File rbFile, boolean rememberFiles) throws IOException, RubySyntaxException {
		List<Type> result = Lists.newArrayList();
		for(PPTypeInfo info : getTypeInfo(rbFile)) {
			Type type = PPTPFactory.eINSTANCE.createType();
			if(rememberFiles)
				type.setFile(rbFile);
			type.setName(info.getTypeName());
			type.setDocumentation(info.getDocumentation());
			for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
				Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
				parameter.setName(entry.getKey());
				parameter.setDocumentation(entry.getValue().documentation);
				parameter.setRequired(entry.getValue().isRequired());
				type.getParameters().add(parameter);
			}
			for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getProperties().entrySet()) {
				Property property = PPTPFactory.eINSTANCE.createProperty();
				property.setName(entry.getKey());
				property.setDocumentation(entry.getValue().documentation);
				property.setRequired(entry.getValue().isRequired());
				type.getProperties().add(property);
			}
			result.add(type);
		}
		return result;
	}
	/**
	 * Load function info into target.
	 * @param target
	 * @param functionsDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadFunctions(TargetEntry target, File functionsDir, boolean rememberFile) throws IOException, RubySyntaxException {
		if(functionsDir.isDirectory())
			for(File rbFile : functionsDir.listFiles(rbFileFilter))
				for(Function f : loadFunctions(rbFile, rememberFile))
					target.getFunctions().add(f);
	}
	/**
	 * Load function(s) from a rubyfile (supposed to contain PP function declarations).
	 * @param rbFile
	 * @param rememberFile
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<Function> loadFunctions(File rbFile, boolean rememberFile) throws IOException, RubySyntaxException {
		List<Function> result = Lists.newArrayList();
		for(PPFunctionInfo info : getFunctionInfo(rbFile)) {
			Function pptpFunc = PPTPFactory.eINSTANCE.createFunction();
			if(rememberFile)
				pptpFunc.setFile(rbFile);
			pptpFunc.setName(info.getFunctionName());
			pptpFunc.setRValue(info.isRValue());
			pptpFunc.setDocumentation(info.getDocumentation());
			result.add(pptpFunc);
		}
		return result;
		
	}

	/**
	 * Loads a target by scanning the relevant .rb files under the given "lib/puppet" directory.
	 * The libPuppetDir should refer to the directory where "parser/functions" and "type" directories
	 * are. An empty TargetEntry is returned if there are no types or functions found.
	 * @param file
	 * @throws IOException
	 */
	public TargetEntry loadDirectoryTarget(File libPuppetDir) throws IOException, RubySyntaxException {

		// If there is a file "{libPuppetDir}/../../Modulefile" then this is a Module, otherwise
		// just a directory.
		//
		File moduleFile = new File(libPuppetDir.getParentFile().getParentFile(),"Modulefile");
		TargetEntry result = null;
		final boolean rememberFiles = true; // remember individually loaded files for dynamic change support
		if(moduleFile.exists()) {
			result = PPTPFactory.eINSTANCE.createPuppetModule();

			// parse the "Modulefile" and get fulle name and version, use this as name of target entry
			try {
				Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
				metadata.loadModuleFile(moduleFile);

				result.setName(metadata.getUser() + '/' + metadata.getName());
				result.setVersion(metadata.getVersion());
				result.setDescription(metadata.getDescription());
			} catch(Exception e) {
				// TODO: Log this
				result.setName(moduleFile.getParentFile().getName());
				result.setVersion("not found");
				result.setDescription("No description - Error while loading metadata");
			}
		}
		else {
			// just a directory
			result = PPTPFactory.eINSTANCE.createRubyDirectory();
			result.setName(libPuppetDir.getParentFile().getParentFile().getName());
			result.setFile(libPuppetDir);
			result.setVersion("");
			result.setDescription("Non module loaded from directory - no description available");
		}
		// always remember the target entry file
		result.setFile(libPuppetDir);

		// Load functions
		File parserDir = new File(libPuppetDir, "parser");
		File functionsDir = new File(parserDir, "functions");
		loadFunctions(result, functionsDir, rememberFiles);

		// Load types
		File typesDir = new File(libPuppetDir, "type");
		loadTypes(result, typesDir, rememberFiles);

		// load additional properties into types
		// (currently only known such construct is for 'file' type
		// this implementation does however search all subdirectories
		// for such additions
		//
		if(typesDir.isDirectory())
			for(File subDir : typesDir.listFiles(dirFilter))
				loadTypeFragments(result, subDir, true);
		return result;

	}

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
	public List<PPTypeInfo> getTypeProperties(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(file == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getTypeInfo");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		return rubyProvider.getTypePropertiesInfo(file);

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

		@Override
		public List<PPTypeInfo> getTypePropertiesInfo(File file)
		throws IOException, RubySyntaxException {
			return emptyTypeInfo;
		}

	}
	/**
	 * Loads a puppet distro into a pptp model and saves the result in a file.
	 * The distroDir should appoint a directory that contains the directories type and parser/functions.
	 * The path to the distroDir should contain a version string segment directly after a segment called
	 * 'puppet'. e.g. /somewhere/on/disk/puppet/2.6.2_0/some/path/to/puppet.
	 * 
	 * Output file will contain a PPTP model as a result of this call.
	 * 
	 * @param distroDir - path to a puppet directory
	 * @param outputFile
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public void loadAndSaveDistro(File distroDir, File outputFile) throws IOException, RubySyntaxException {
		TargetEntry target = loadDistroTarget(distroDir);
		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl(); 
		URI fileURI = 
		URI.createFileURI(outputFile.getAbsolutePath()); 
		Resource targetResource = resourceSet.createResource(fileURI); 
		targetResource.getContents().add(target); 
		targetResource.save(null);
	}
}
