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
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.ITargetElementContainer;
import org.cloudsmith.geppetto.pp.pptp.MetaType;
import org.cloudsmith.geppetto.pp.pptp.MetaVariable;
import org.cloudsmith.geppetto.pp.pptp.NameSpace;
import org.cloudsmith.geppetto.pp.pptp.PPTPFactory;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.PuppetDistribution;
import org.cloudsmith.geppetto.pp.pptp.TPVariable;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;
import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.pp.pptp.TypeFragment;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.ruby.spi.IRubyServices;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Provides access to ruby parsing / information services. If an implementation
 * is not available, a mock service is used - this can be checked with {@link #isRubyServicesAvailable()}. The mock service will provide an empty
 * parse result (i.e. "no errors or warning"), and will return empty results for
 * information.
 * 
 * The caller can then adjust how to deal with service not being present.
 * 
 * To use the RubyHelper, a call must be made to {@link #setUp()}, then followed
 * by a series of requests to parse or get information.
 * 
 */
public class RubyHelper {

	private static class MockRubyServices implements IRubyServices {
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

		private static final List<PPFunctionInfo> emptyFunctionInfo = Collections.emptyList();

		private static final List<PPTypeInfo> emptyTypeInfo = Collections.emptyList();

		private static final IRubyParseResult emptyParseResult = new EmptyParseResult();

		@Override
		public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException {
			return emptyFunctionInfo;
		}

		@Override
		public List<PPFunctionInfo> getFunctionInfo(String fileName, Reader reader) {
			return emptyFunctionInfo;
		}

		@Override
		public List<PPFunctionInfo> getLogFunctions(File file) throws IOException, RubySyntaxException {
			return emptyFunctionInfo;
		}

		@Override
		public PPTypeInfo getMetaTypeProperties(File file) throws IOException, RubySyntaxException {
			return emptyTypeInfo.get(0);
		}

		@Override
		public PPTypeInfo getMetaTypeProperties(String fileName, Reader reader) {
			return emptyTypeInfo.get(0);
		}

		@Override
		public Map<String, String> getRakefileTaskDescriptions(File file) {
			return Maps.newHashMap(); // empty map - can't discover anything about rakefiles
		}

		@Override
		public List<PPTypeInfo> getTypeInfo(File file) throws IOException {
			return emptyTypeInfo;
		}

		@Override
		public List<PPTypeInfo> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
			return Collections.emptyList();
		}

		@Override
		public List<PPTypeInfo> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException {
			return emptyTypeInfo;
		}

		@Override
		public List<PPTypeInfo> getTypePropertiesInfo(String fileName, Reader reader) throws IOException,
				RubySyntaxException {
			return emptyTypeInfo;
		}

		@Override
		public boolean isMockService() {
			return true;
		}

		@Override
		public IRubyParseResult parse(File file) throws IOException {
			return emptyParseResult;
		}

		@Override
		public IRubyParseResult parse(String path, Reader reader) throws IOException {
			return emptyParseResult;
		}

		@Override
		public void setUp() {
			// do nothing
		}

		@Override
		public void tearDown() {
			// do nothing
		}

	}

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

	private TPVariable addTPVariable(ITargetElementContainer container, String name, String documentation,
			boolean deprecated) {
		TPVariable var = PPTPFactory.eINSTANCE.createTPVariable();
		var.setName(name);
		var.setDocumentation(documentation);
		var.setAssignable(false);
		var.setDeprecated(deprecated);
		container.getContents().add(var);
		return var;
	}

	private String[] extractVersionFromName(String name) {
		String[] result = new String[] { name, "" };
		int lastHypen = name.lastIndexOf('-');
		if(lastHypen == -1)
			return result;
		result[0] = name.substring(0, lastHypen);
		result[1] = name.substring(lastHypen + 1);
		return result;

	}

	private List<Function> functionInfoToFunction(List<PPFunctionInfo> functionInfos) {
		List<Function> result = Lists.newArrayList();
		for(PPFunctionInfo info : functionInfos) {
			Function pptpFunc = PPTPFactory.eINSTANCE.createFunction();
			pptpFunc.setName(info.getFunctionName());
			pptpFunc.setRValue(info.isRValue());
			pptpFunc.setDocumentation(info.getDocumentation());
			result.add(pptpFunc);
		}
		return result;

	}

	/**
	 * Returns a list of custom PP parser functions from the given .rb file. The
	 * returned list is empty if no function could be found.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
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

	public List<PPFunctionInfo> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getFunctionInfo(File).");
		if(fileName == null)
			throw new IllegalArgumentException("Given filename is null");
		if(reader == null)
			throw new IllegalArgumentException("Given reader is null");

		return rubyProvider.getFunctionInfo(fileName, reader);

	}

	public PPTypeInfo getMetaTypeInfo(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(file == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getTypeInfo");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		return rubyProvider.getMetaTypeProperties(file);

	}

	public PPTypeInfo getMetaTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before calling this method");
		if(fileName == null)
			throw new IllegalArgumentException("Given fileName is null");
		if(reader == null)
			throw new IllegalArgumentException("Given reader is null");
		return rubyProvider.getMetaTypeProperties(fileName, reader);
	}

	public Map<String, String> getRakefileTaskDescriptions(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before calling this method.");
		if(file == null)
			throw new IllegalArgumentException("Given file is null");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());

		return rubyProvider.getRakefileTaskDescriptions(file);

	}

	public List<PPTypeInfo> getTypeFragments(File file) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(file == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getTypeInfo");
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		return rubyProvider.getTypePropertiesInfo(file);
	}

	public List<PPTypeInfo> getTypeFragments(String fileName, Reader reader) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before calling this method.");
		if(fileName == null)
			throw new IllegalArgumentException("Given file is null");
		if(reader == null)
			throw new IllegalArgumentException("Given reader is null");
		return rubyProvider.getTypePropertiesInfo(fileName, reader);
	}

	/**
	 * Returns a list of custom PP types from the given .rb file. The returned
	 * list is empty if no type could be found.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
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

	/**
	 * Returns a list of custom PP types from the given .rb file. The returned
	 * list is empty if no type could be found.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 *             - if there are errors reading the file
	 * @throws IllegalStateException
	 *             - if setUp was not called
	 */
	public List<PPTypeInfo> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(fileName == null)
			throw new IllegalArgumentException("Given filename is null - JRubyService.getTypeInfo");
		if(reader == null)
			throw new IllegalArgumentException("Given reader is null - JRubyService.getTypeInfo");
		return rubyProvider.getTypeInfo(fileName, reader);
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
	 * Loads a puppet distro into a pptp model and saves the result in a file.
	 * The distroDir should appoint a directory that contains the directories
	 * type and parser/functions. The path to the distroDir should contain a
	 * version string segment directly after a segment called 'puppet'. e.g.
	 * /somewhere/on/disk/puppet/2.6.2_0/some/path/to/puppet.
	 * 
	 * Will also load default settings:: variables, and meta variables.
	 * 
	 * Output file will contain a PPTP model as a result of this call.
	 * 
	 * @param distroDir
	 *            - path to a puppet directory
	 * @param outputFile
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public void loadAndSaveDistro(File distroDir, File outputFile) throws IOException, RubySyntaxException {
		TargetEntry target = loadDistroTarget(distroDir);

		// Load the default settings:: variables
		loadSettings(target);

		// Load the default meta variables
		loadMetaVariables(target);

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(outputFile.getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);
		targetResource.getContents().add(target);
		targetResource.save(null);
	}

	/**
	 * Loads a Puppet distribution target. The file should point to the "lib/puppet"
	 * directory where the sub-directories "parser" and "type" are. The path to
	 * this directory is expected to have a ../../ name on the form puppet-version
	 * e.g. /somewhere/puppet-2.6.9/lib/puppet.
	 * 
	 * The implementation will scan the known locations for definitions that
	 * should be reflected in the target - i.e. parser/functions/*.rb and
	 * type/*.rb
	 * 
	 * Note, this does not load default settings:: variables.
	 * 
	 * @throws IOException
	 *             on problems with reading files
	 * @throws RubySyntaxException
	 *             if there are syntax exceptions in the parsed ruby code
	 */
	public TargetEntry loadDistroTarget(File file) throws IOException, RubySyntaxException {
		if(file == null)
			throw new IllegalArgumentException("File can not be null");

		// Create a puppet distro target and parse info from the file path
		//
		PuppetDistribution puppetDistro = PPTPFactory.eINSTANCE.createPuppetDistribution();
		puppetDistro.setDescription("Puppet Distribution");
		IPath path = Path.fromOSString(file.getAbsolutePath());

		// NOTE: This is wrong, will always result in a version == "" as the version is
		// part of the "puppet" directory, not the directory puppet-x.x.x/lib/puppet that is given to
		// this function.
		//
		String versionString = "";
		boolean nextIsVersion = false;
		String[] segments = path.segments();
		int sc = segments.length;
		if(segments.length < 3 || !"puppet".equals(segments[sc - 1]) || !"lib".equals(segments[sc - 2]))
			throw new IllegalArgumentException("path to .../puppet/lib is not correct");
		final String distroName = segments[sc - 3];
		if(!distroName.startsWith("puppet-"))
			throw new IllegalArgumentException(
				"The ../../ of the given directory must be named on the form: 'puppet-<version>'");

		puppetDistro.setLabel("puppet");
		// 7 is the first char after 'puppet-'
		puppetDistro.setVersion(distroName.substring(7));

		// Load functions
		File parserDir = new File(file, "parser");
		File functionsDir = new File(parserDir, "functions");
		loadFunctions(puppetDistro, functionsDir);

		// Load logger functions
		for(Function f : loadLoggerFunctions(new File(file, "util/log.rb")))
			puppetDistro.getFunctions().add(f);

		// Load types
		try {
			File typesDir = new File(file, "type");
			loadTypes(puppetDistro, typesDir);

			// load additional properties into types
			// (currently only known such construct is for 'file' type
			// this implementation does however search all subdirectories
			// for such additions
			//
			for(File subDir : typesDir.listFiles(dirFilter))
				loadTypeFragments(puppetDistro, subDir);

		}
		catch(FileNotFoundException e) {
			// ignore
		}

		// load nagios types
		try {
			File nagios = new File(file, "external/nagios/base.rb");
			loadNagiosTypes(puppetDistro, nagios);
		}
		catch(FileNotFoundException e) {
			// ignore - no nagios
		}

		// load metatype
		try {
			File typeFile = new File(file, "type.rb");
			loadMetaType(puppetDistro, typeFile);
		}
		catch(FileNotFoundException e) {
			// ignore
		}

		return puppetDistro;
	}

	/**
	 * Load function(s) from a rubyfile (supposed to contain PP function
	 * declarations).
	 * 
	 * @param rbFile
	 * @param rememberFile
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<Function> loadFunctions(File rbFile) throws IOException, RubySyntaxException {
		return functionInfoToFunction(getFunctionInfo(rbFile));
	}

	/**
	 * Load function info into target.
	 * 
	 * @param target
	 * @param functionsDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadFunctions(TargetEntry target, File functionsDir) throws IOException, RubySyntaxException {
		if(functionsDir.isDirectory())
			for(File rbFile : functionsDir.listFiles(rbFileFilter))
				for(Function f : loadFunctions(rbFile))
					target.getFunctions().add(f);
	}

	public List<Function> loadLoggerFunctions(File rbFile) throws IOException, RubySyntaxException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before getTypeInfo(File).");
		if(rbFile == null)
			throw new IllegalArgumentException("Given file is null - JRubyService.getTypeInfo");
		if(!rbFile.exists())
			throw new FileNotFoundException(rbFile.getPath());
		return functionInfoToFunction(rubyProvider.getLogFunctions(rbFile));
	}

	private void loadMetaType(TargetEntry target, File rbFile) throws IOException, RubySyntaxException {
		PPTypeInfo info = getMetaTypeInfo(rbFile);
		MetaType type = PPTPFactory.eINSTANCE.createMetaType();
		type.setName(info.getTypeName());
		type.setDocumentation(info.getDocumentation());
		for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
			Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
			parameter.setName(entry.getKey());
			parameter.setDocumentation(entry.getValue().documentation);
			parameter.setRequired(entry.getValue().isRequired());
			type.getParameters().add(parameter);
		}
		// TODO: Scan the puppet source for providers for the type
		// This is a CHEAT - https://github.com/cloudsmith/geppetto/issues/37
		Parameter p = PPTPFactory.eINSTANCE.createParameter();
		p.setName("provider");
		p.setDocumentation("");
		p.setRequired(false);
		type.getParameters().add(p);

		// TODO: there are more interesting things to pick up (like valid
		// values)
		target.setMetaType(type);

	}

	/**
	 * Loads meta variables into the target. These are variables that looks like
	 * local variables in every scope, but they are not found from the outside.
	 * 
	 * @param target
	 */
	public void loadMetaVariables(TargetEntry target) {
		EList<MetaVariable> metaVars = target.getMetaVariables();

		MetaVariable metaName = PPTPFactory.eINSTANCE.createMetaVariable();
		metaName.setName("name");
		metaName.setDocumentation("");
		metaName.setDeprecated(false);
		metaVars.add(metaName);

		MetaVariable metaTitle = PPTPFactory.eINSTANCE.createMetaVariable();
		metaTitle.setName("title");
		metaTitle.setDocumentation("");
		metaTitle.setDeprecated(false);
		metaVars.add(metaTitle);

		MetaVariable metaModuleName = PPTPFactory.eINSTANCE.createMetaVariable();
		metaModuleName.setName("module_name");
		metaModuleName.setDocumentation("The name of the containing module");
		metaModuleName.setDeprecated(false);
		metaVars.add(metaModuleName);
	}

	private void loadNagiosTypes(TargetEntry target, File rbFile) throws IOException, RubySyntaxException {

		for(Type t : transform(getTypeInfo(rbFile))) {
			target.getTypes().add(t);
		}
	}

	public List<TargetEntry> loadPluginsTarget(File pluginsRoot) throws IOException, RubySyntaxException {

		List<TargetEntry> result = Lists.newArrayList();
		// for all the directories in pluginsRoot, load the content of that directory
		// as a module
		if(pluginsRoot == null || !pluginsRoot.isDirectory())
			return result; // do nothing (an empty list)

		for(File pluginRoot : pluginsRoot.listFiles()) {
			String[] nameParts = extractVersionFromName(pluginRoot.getName());
			PuppetDistribution plugin = PPTPFactory.eINSTANCE.createPuppetDistribution();
			plugin.setDescription("Puppet Plugin");
			plugin.setLabel(nameParts[0]);
			plugin.setVersion(nameParts[1]);

			// load functions (lib/puppet/parser/functions/*), and types (lib/puppet/type/*)
			File lib = new File(pluginRoot, "lib/puppet");
			if(!lib.exists())
				continue; // has no content that can be handled

			// Load Functions
			File functionsDir = new File(new File(lib, "parser"), "functions");
			loadFunctions(plugin, functionsDir);

			// Load Types
			try {
				File typesDir = new File(lib, "type");
				loadTypes(plugin, typesDir);

				// load additional properties into types
				// (currently only known such construct is for 'file' type
				// this implementation does however search all subdirectories
				// for such additions
				//
				if(typesDir != null && typesDir.isDirectory())
					for(File subDir : typesDir.listFiles(dirFilter))
						loadTypeFragments(plugin, subDir);

				if(plugin.getFunctions().size() > 0 || plugin.getTypes().size() > 0)
					result.add(plugin);
			}
			catch(FileNotFoundException e) {
				// ignore, just skipping functions, types etc if not present in plugin
			}
		}
		return result;
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

	/**
	 * Loads predefined variables in the settings:: namespace. These are hard to
	 * find in the puppet logic.
	 * 
	 * @param target
	 */
	public void loadSettings(TargetEntry target) {
		NameSpace settings = PPTPFactory.eINSTANCE.createNameSpace();
		settings.setReserved(true);
		settings.setName("settings");
		target.getContents().add(settings);

		// Create a wildcard to match all settings::*
		//
		TPVariable wildcard = PPTPFactory.eINSTANCE.createTPVariable();
		wildcard.setName("*");
		wildcard.setAssignable(false);
		settings.getContents().add(wildcard);

		// Add known names in settings (the most common ones). This to avoid
		// warnings
		//
		SettingsData settingsData = new SettingsData();
		for(SettingsData.Setting s : settingsData.settings) {
			addTPVariable(settings, s.name, s.documentation, s.deprecated);
		}
	}

	public List<TypeFragment> loadTypeFragments(File rbFile) throws IOException, RubySyntaxException {
		List<TypeFragment> result = Lists.newArrayList();
		for(PPTypeInfo type : getTypeFragments(rbFile)) {
			TypeFragment fragment = PPTPFactory.eINSTANCE.createTypeFragment();
			fragment.setName(type.getTypeName());

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

	private void loadTypeFragments(TargetEntry target, File subDir) throws IOException, RubySyntaxException {
		for(File f : subDir.listFiles(rbFileFilter)) {
			// try to get type property additions
			List<TypeFragment> result = loadTypeFragments(f);
			for(TypeFragment tf : result)
				target.getTypeFragments().add(tf);
		}
	}

	/**
	 * Load type(s) from ruby file.
	 * 
	 * @param rbFile
	 *            - the file to parse
	 * @param rememberFiles
	 *            - if entries should remember the file they came from.
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<Type> loadTypes(File rbFile) throws IOException, RubySyntaxException {
		// List<Type> result = Lists.newArrayList();
		return transform(getTypeInfo(rbFile));

		// for (PPTypeInfo info : getTypeInfo(rbFile)) {
		// Type type = PPTPFactory.eINSTANCE.createType();
		// type.setName(info.getTypeName());
		// type.setDocumentation(info.getDocumentation());
		// for (Map.Entry<String, PPTypeInfo.Entry> entry : info
		// .getParameters().entrySet()) {
		// Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
		// parameter.setName(entry.getKey());
		// parameter.setDocumentation(entry.getValue().documentation);
		// parameter.setRequired(entry.getValue().isRequired());
		// type.getParameters().add(parameter);
		// }
		// for (Map.Entry<String, PPTypeInfo.Entry> entry : info
		// .getProperties().entrySet()) {
		// Property property = PPTPFactory.eINSTANCE.createProperty();
		// property.setName(entry.getKey());
		// property.setDocumentation(entry.getValue().documentation);
		// property.setRequired(entry.getValue().isRequired());
		// type.getProperties().add(property);
		// }
		// result.add(type);
		// }
		// return result;
	}

	/**
	 * Load type info into target.
	 * 
	 * @param target
	 * @param typesDir
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	private void loadTypes(TargetEntry target, File typesDir) throws IOException, RubySyntaxException {
		if(!typesDir.isDirectory())
			return;
		for(File rbFile : typesDir.listFiles(rbFileFilter))
			for(Type t : loadTypes(rbFile))
				target.getTypes().add(t);
	}

	/**
	 * Parse a .rb file and return information about syntax errors and warnings.
	 * Must be preceded with a call to setUp().
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 *             if setUp was not called.
	 */
	public IRubyParseResult parse(File file) throws IOException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before parse(File).");
		return rubyProvider.parse(file);
	}

	/**
	 * Parse a .rb file and return information about syntax errors and warnings.
	 * Must be preceded with a call to setUp().
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 *             if setUp was not called.
	 */
	public IRubyParseResult parse(String path, Reader reader) throws IOException {
		if(rubyProvider == null)
			throw new IllegalStateException("Must call setUp() before parse(File).");
		return rubyProvider.parse(path, reader);
	}

	/**
	 * Should be called to initiate the ruby services. Each call to setUp should
	 * be paired with a call to tearDown or resources will be wasted.
	 */
	public void setUp() {
		if(rubyProvider == null)
			loadRubyServiceExtension();
		rubyProvider.setUp();
	}

	public void tearDown() {
		if(rubyProvider == null)
			return; // ignore silently

		rubyProvider.tearDown();

	}

	private List<Type> transform(List<PPTypeInfo> typeInfos) {
		List<Type> result = Lists.newArrayList();
		for(PPTypeInfo info : typeInfos) {
			Type type = PPTPFactory.eINSTANCE.createType();
			type.setName(info.getTypeName());
			type.setDocumentation(info.getDocumentation());
			for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
				Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
				parameter.setName(entry.getKey());
				parameter.setDocumentation(entry.getValue().documentation);
				parameter.setRequired(entry.getValue().isRequired());
				parameter.setNamevar(entry.getValue().isNamevar());
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
}
