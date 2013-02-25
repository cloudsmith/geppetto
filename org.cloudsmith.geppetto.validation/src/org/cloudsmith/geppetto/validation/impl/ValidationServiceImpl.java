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
package org.cloudsmith.geppetto.validation.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath;
import org.cloudsmith.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.RubySyntaxException;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.validation.DetailedDiagnosticData;
import org.cloudsmith.geppetto.validation.DiagnosticData;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.IValidationConstants;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.DiagnosticType;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo.Resolution;
import org.cloudsmith.geppetto.validation.runner.PPDiagnosticsRunner;
import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner;
import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner.CatalogDiagnostic;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo.Rakefile;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo.Raketask;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * Note that all use of monitor assumes SubMonitor semantics (the receiver does *not* call done on monitors,
 * this is the responsibility of the caller if caller is not using a SubMonitor).
 */
public class ValidationServiceImpl implements ValidationService {
	/**
	 * 
	 */
	private static final String NAME_OF_DIR_WITH_RESTRICTED_SCOPE = "roles";

	private static final FileFilter directoryFilter = new FileFilter() {

		@Override
		public boolean accept(File f) {
			return f.isDirectory() && !f.getName().equals(".svn");
		}

	};

	private static final FilenameFilter ppFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".pp") && new File(dir, name).isFile();
		}

	};

	private static final FilenameFilter rbFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".rb") && new File(dir, name).isFile();
		}

	};

	private static final FilenameFilter ModulefileFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.equals("Modulefile") && new File(dir, name).isFile();
		}

	};

	private static final FilenameFilter RakefileFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			String lcname = name.toLowerCase();
			if(lcname.startsWith("rakefile")) {
				int length = lcname.length();
				if(!(length == 8 //
						||
						(length == 11 && lcname.endsWith(".rb")) //
				|| (length == 13 && lcname.endsWith(".rake"))))
					return false;
			}
			else if(!lcname.endsWith(".rake")) {
				return false;
			}
			// If the file is a directory, or something else, do not include it
			return new File(dir, name).isFile();

			//			// quickly disqualify those that are not [Rr]akefile[.rb]
			//			// (Do case independent comparison since some file systems do not discriminate on case)
			//			int namelength = name.length();
			//			if(!(namelength == 8 || namelength == 11))
			//				return false;
			//			switch(name.charAt(0)) {
			//				case 'r':
			//				case 'R':
			//					break;
			//				default:
			//					return false;
			//			}
			//			String lcname = name.toLowerCase();
			//			if(!"rakefile".equals(lcname.substring(0, 8)))
			//				return false;
			//			if(namelength == 11 && !lcname.endsWith(".rb"))
			//				return false;

		}

	};

	private final static String validationSource = ValidationService.class.getName();

	/**
	 * Add an exception diagnostic (not associated with any particular file).
	 * 
	 * @param diagnostics
	 * @param message
	 * @param e
	 */
	private static void addExceptionDiagnostic(DiagnosticChain diagnostics, String message, Exception e) {
		BasicDiagnostic bd = new BasicDiagnostic(
			Diagnostic.ERROR, validationSource, DiagnosticType.INTERNAL_ERROR.getCode(), message,
			new Object[] { e });
		diagnostics.add(bd);
	}

	/**
	 * Add diagnostic for a file.
	 * 
	 * @param diagnostics
	 * @param severity
	 * @param file
	 * @param rootDirectory
	 *        - used to relativize the file path in the report
	 * @param message
	 * @param issueId
	 */
	private static void addFileDiagnostic(DiagnosticChain diagnostics, int severity, File file, File rootDirectory,
			String message, String issueId) {

		int code = DiagnosticType.UNKNOWN.getCode(); // TODO: need "structure", rename "layout" flag
		DetailedDiagnosticData dataEntry = new DetailedDiagnosticData();
		File sourceFile = pathToFile(file.getAbsolutePath(), rootDirectory);
		dataEntry.setFile(sourceFile);
		dataEntry.setLineNumber(-1);
		dataEntry.setLength(-1);
		dataEntry.setOffset(-1);
		dataEntry.setIssue(issueId);
		dataEntry.setIssueData(new String[] {});
		dataEntry.setNode("");
		BasicDiagnostic bd = new BasicDiagnostic(severity, validationSource, code, message, new Object[] { dataEntry });
		diagnostics.add(bd);
	}

	private static void addFileError(DiagnosticChain diagnostics, File file, File rootDirectory, String message,
			String issueId) {
		addFileDiagnostic(diagnostics, Diagnostic.ERROR, file, rootDirectory, message, issueId);
	}

	private static void addFileWarning(DiagnosticChain diagnostics, File file, File rootDirectory, String message,
			String issueId) {
		addFileDiagnostic(diagnostics, Diagnostic.WARNING, file, rootDirectory, message, issueId);
	}

	/**
	 * Translate and add Xtext issue diagnostics to the chain.
	 * 
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	private static void addIssueDiagnostic(DiagnosticChain diagnostics, Issue issue, File processedFile,
			File rootDirectory) {
		int code = issue.isSyntaxError()
				? DiagnosticType.GEPPETTO_SYNTAX.getCode()
				: DiagnosticType.GEPPETTO.getCode();
		DetailedDiagnosticData dataEntry = new DetailedDiagnosticData();
		File sourceFile = uriToFile(issue.getUriToProblem(), rootDirectory);
		dataEntry.setFile(sourceFile);
		dataEntry.setLineNumber(issue.getLineNumber());
		dataEntry.setLength(issue.getLength());
		dataEntry.setOffset(issue.getOffset());
		dataEntry.setIssue(issue.getCode());
		dataEntry.setIssueData(issue.getData());
		dataEntry.setNode("");

		BasicDiagnostic bd = new BasicDiagnostic(
			translateIssueSeverity(issue.getSeverity()), validationSource, code, issue.getMessage(),
			new Object[] { dataEntry });
		diagnostics.add(bd);
	}

	/**
	 * Translate and add ruby issue diagnostics to the chain.
	 * 
	 * @param diagnostics
	 * @param issue
	 * @param rootDirectory
	 * @param rootDirectory
	 */
	private static void addRubyIssueDiagnostic(DiagnosticChain diagnostics, IRubyIssue issue, File processedFile,
			File rootDirectory) {
		int code = issue.isSyntaxError()
				? DiagnosticType.RUBY_SYNTAX.getCode()
				: DiagnosticType.RUBY.getCode();
		DetailedDiagnosticData dataEntry = new DetailedDiagnosticData();
		File sourceFile = pathToFile(issue.getFileName(), rootDirectory);
		dataEntry.setFile(sourceFile);
		dataEntry.setLineNumber(issue.getLine());
		dataEntry.setLength(issue.getLength());
		dataEntry.setOffset(issue.getStartOffset());
		dataEntry.setIssue(issue.getIdString());
		dataEntry.setIssueData(new String[] {}); // TODO: the Ruby issue passes Object[]
		dataEntry.setNode("");

		BasicDiagnostic bd = new BasicDiagnostic(issue.isSyntaxError()
				? Diagnostic.ERROR
				: Diagnostic.WARNING, validationSource, code, issue.getMessage(), new Object[] { dataEntry });
		diagnostics.add(bd);
	}

	/**
	 * Translate a file path to a file relative to rootFolder (if under this root, else return
	 * an absolute File).
	 * 
	 * @param uri
	 * @param rootFolder
	 *        - root directory/folder or a file name
	 * @return
	 */
	private static File pathToFile(String filePath, File rootFolder) {
		Path problemPath = new Path(filePath);
		Path rootPath = new Path(rootFolder.getPath());
		IPath relativePath = problemPath.makeRelativeTo(rootPath);
		return relativePath.toFile();
	}

	/**
	 * Translate the issue severity (an enum) to BasicDiagnostic (they are probably the same...)
	 * 
	 * @param severity
	 * @return
	 */
	private static int translateIssueSeverity(Severity severity) {
		switch(severity) {
			case INFO:
				return Diagnostic.INFO;
			case WARNING:
				return Diagnostic.WARNING;

			default:
			case ERROR:
				return Diagnostic.ERROR;

		}
	}

	/**
	 * Translate a URI to the file to a file relative to rootFolder (if under this root, else return
	 * an absolute File).
	 * 
	 * @param uri
	 * @param rootFolder
	 *        - root directory/folder or a file name
	 * @return
	 */
	private static File uriToFile(URI uri, File rootFolder) {
		// relativize file:
		if(!uri.isFile())
			return null;
		Path problemPath = new Path(uri.toFileString());
		Path rootPath = new Path(rootFolder.getPath());
		IPath relativePath = problemPath.makeRelativeTo(rootPath);
		return relativePath.toFile();
	}

	public ValidationServiceImpl() {
	}

	/**
	 * Add a BasicDiagnostic to the diagnostic chain as a translation of a catalog diagnostic.
	 * 
	 * @param diagnostics
	 * @param d
	 */
	private void addCatalogDiagnostics(DiagnosticChain diagnostics, CatalogDiagnostic d) {
		if(diagnostics == null)
			throw new IllegalArgumentException("DiagnosticChain can not be null");
		if(d == null)
			throw new IllegalArgumentException("Can not add null CatalogDiagnostic");

		// translate the code into codes defined for the ValidationService
		int code = d.getCode() == CatalogDiagnostic.CODE_PARSE_ERROR
				? DiagnosticType.CATALOG_PARSER.getCode()
				: DiagnosticType.CATALOG.getCode();

		// translate the data
		DiagnosticData dataEntry = new DiagnosticData();
		String s = d.getFileName();
		boolean hasDetails = false;
		if(s != null && s.length() > 0) {
			hasDetails = true;
			dataEntry.setFile(new File(s));
		}
		s = d.getNodeName();
		if(s != null && s.length() > 0) {
			hasDetails = true;
			dataEntry.setNode(s);
		}
		if(d.getLine() != -1) {
			hasDetails = true;
		}

		// it is -1 or some other value
		dataEntry.setLineNumber(d.getLine());

		// set data if there were details
		Object[] data = new Object[hasDetails
				? 0
				: 1];
		if(hasDetails)
			data[0] = dataEntry;

		// report as a BasicDiagnostic
		BasicDiagnostic bd = new BasicDiagnostic(d.getSeverity(), validationSource, code, d.getMessage(), data);
		diagnostics.add(bd);
	}

	/**
	 * Asserts a file/directory's existence and that it can be read.
	 * 
	 * @throw IllegalArgumentException if the file is not the right type, does not exists, or is not readable.
	 * @param file
	 * @param assertIsDirectory
	 *        - check if file is a directory
	 */
	private void assertFileAvailability(File file, boolean assertIsDirectory) {
		if(assertIsDirectory) {
			if(file.isDirectory() && file.canRead())
				return;
			throw new IllegalArgumentException("The file: " + file.toString() + " is not a readable directory.");
		}
		else if(file.exists() && file.canRead())
			return;
		throw new IllegalArgumentException("The file: " + file.toString() + " is not a readable file.");
	}

	/**
	 * @param moduleData - resolved module data
	 * @param root - root file for relativization
	 * @param diagnostics - where to report issues
	 */
	private void checkCircularDependencies(Multimap<String, MetadataInfo> moduleData, DiagnosticChain diagnostics,
			File root) {
		// problems: multiple versions of the same, etc.Use an identity set
		for(MetadataInfo mi : moduleData.values()) {
			Set<MetadataInfo> checkedModules = Sets.newIdentityHashSet();
			List<MetadataInfo> circle = Lists.newLinkedList();
			checkCircularity(mi, mi, circle, checkedModules);
		}
	}

	/**
	 * @param head
	 * @param circle
	 * @param checkedModules
	 */
	private void checkCircularity(MetadataInfo head, MetadataInfo current, List<MetadataInfo> circle,
			Set<MetadataInfo> checkedModules) {
		if(circle.contains(current))
			return;

		circle.add(0, current);
		for(Resolution r : current.getResolvedDependencies()) {
			if(r.metadata == head) {
				// circular
				head.addCircularity(circle);
			}
			else {
				// non circular
				checkCircularity(head, r.metadata, circle, checkedModules);
			}
		}
		// pop current
		circle.remove(0);
	}

	private void checkModuleLayout(DiagnosticChain diagnostics, File moduleRoot, File sourceRoot) {
		if(hasModulesSubDirectory(moduleRoot))
			addFileError(
				diagnostics, new File(moduleRoot, "modules"), sourceRoot, "Submodules in a module is not allowed",
				IValidationConstants.ISSUE__UNEXPECTED_SUBMODULE_DIRECTORY);
		if(!(hasMetadataFile(moduleRoot) || hasModuleFile(moduleRoot)))
			addFileError(
				diagnostics, moduleRoot, sourceRoot, "Missing 'metadata.json or Modulefile'",
				IValidationConstants.ISSUE__MISSING_MODULEFILE);

	}

	private void checkPuppetRootLayout(DiagnosticChain diagnostics, File moduleRoot, File sourceRoot) {
		// TODO: check each module under modules
		// TODO: additional checks (files that are required etc.)
	}

	/**
	 * @param circularity
	 * @return
	 */
	private Object circularitylabel(List<MetadataInfo> circularity) {
		StringBuilder result = new StringBuilder();
		Joiner.on("->").appendTo(result, Iterables.transform(circularity, new Function<MetadataInfo, String>() {
			public String apply(MetadataInfo mi) {
				return mi.getMetadata().getName();
			}
		}));
		result.append("->").append(circularity.get(0).getMetadata().getName());
		return result.toString();
	}

	/**
	 * Collects file matching filter while skipping all symbolically linked files.
	 * 
	 * @param root
	 * @param filter
	 * @param result
	 */
	private void collectFiles(File root, FilenameFilter filter, List<File> result) {
		for(File f : root.listFiles(filter))
			if(!FileUtils.isSymlink(f))
				result.add(f);
		for(File f : root.listFiles(directoryFilter))
			if(!FileUtils.isSymlink(f))
				collectFiles(f, filter, result);
	}

	private List<File> findFiles(File root, FilenameFilter filter) {
		List<File> result = Lists.newArrayList();
		collectFiles(root, filter, result);
		return result;

	}

	private List<File> findModulefileFiles(File root) {
		return findFiles(root, ModulefileFileFilter);
	}

	private List<File> findPPFiles(File root) {
		return findFiles(root, ppFileFilter);
	}

	private List<File> findRakefiles(File root) {
		return findFiles(root, RakefileFileFilter);
	}

	private List<File> findRubyFiles(File root) {
		return findFiles(root, rbFileFilter);
	}

	/**
	 * @param rubyHelper
	 * @param f
	 * @param root
	 * @param newChild
	 * @throws RubySyntaxException
	 * @throws IOException
	 */
	private Rakefile getRakefileInformation(RubyHelper rubyHelper, File f, File root, IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, 1);

		try {
			Map<String, String> taskInfo = rubyHelper.getRakefileTaskDescriptions(f);
			Path rootPath = new Path(root.getAbsolutePath());
			Path rakefilePath = new Path(f.getAbsolutePath());
			Rakefile result = new Rakefile(rakefilePath.makeRelativeTo(rootPath));

			if(taskInfo == null)
				return result;

			for(Entry<String, String> entry : taskInfo.entrySet())
				result.addTask(new Raketask(entry.getKey(), entry.getValue()));
			return result;
		}
		catch(IOException e) {
			// simply do not return any information - org.cloudsmith.geppetto.validation should have dealt with
			// errors
			//			System.err.println("IOException while processing Rakefile: " + e.getMessage());
			return null;
		}
		catch(RubySyntaxException e) {
			// simply do not return any information - org.cloudsmith.geppetto.validation should have dealt with
			// errors
			//			System.err.println("RubySyntaxException while processing Rakefile: " + e.getMessage());
			return null;
		}
		finally {
			worked(ticker, 1);
			//			System.err.println("Processed one Rakefile: " + f.getPath());

		}
	}

	private boolean hasModuleFile(File root) {
		File moduleFile = new File(root, "Modulefile");
		return moduleFile.isFile();
	}

	private boolean hasMetadataFile(File root) {
		File metadataFile = new File(root, "metadata.json");
		return metadataFile.isFile();
	}

	private boolean hasModulesSubDirectory(File root) {
		File modulesDir = new File(root, "modules");
		return modulesDir.isDirectory();
	}

	private boolean isOnPath(File f, PPSearchPath searchPath) {

		return -1 != searchPath.searchIndexOf(URI.createFileURI(f.getPath()));
	}

	private boolean isValidationWanted(File[] examinedFiles, File f) {
		if(examinedFiles == null || examinedFiles.length == 0)
			return true;
		try {
			IPath filePath = Path.fromOSString(f.getCanonicalPath());
			for(int i = 0; i < examinedFiles.length; i++) {
				File x = examinedFiles[i];
				IPath xPath = Path.fromOSString(x.getCanonicalPath());
				if(x.isDirectory()) {
					if(xPath.isPrefixOf(filePath))
						return true;
				}
				else {
					if(xPath.equals(filePath))
						return true;
				}
			}
		}
		catch(IOException e) {
			// the file or the entry in examined files does not exists
			// just continue
		}
		return false;
	}

	/**
	 * Loads Modulefile and returns the parsed Metadata. If parsing fails an error is reported on
	 * the diagnostics chain and null is returned
	 * 
	 * @param diagnostics
	 * @param moduleFile
	 * @param parentFile
	 * @param monitor
	 * @return null if the Modulefile could not be loaded
	 */
	private Metadata loadModulefileMetadata(DiagnosticChain diagnostics, File moduleFile, File parentFile,
			IProgressMonitor monitor) {
		// parse the "Modulefile" and get full name and version, use this as name of target entry
		try {
			Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
			metadata.loadModuleFile(moduleFile);
			return metadata;
		}
		catch(Exception e) {
			addFileError(
				diagnostics, moduleFile, parentFile, "Can not parse file: " + e.getMessage(),
				IValidationConstants.ISSUE__MODULEFILE_PARSE_ERROR);
		}
		return null;
	}

	private void rememberRootInResource(File root, Resource r) {
		if(root == null)
			throw new IllegalArgumentException("root can not be null");
		if(r == null)
			throw new IllegalArgumentException("resource can not be null");
		URI uri = URI.createFileURI(root.getAbsolutePath());
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		adapter.put(PPDSLConstants.RESOURCE_PROPERTY__ROOT_URI, uri);
	}

	/**
	 * @param monitor
	 *        - client should call done unless using a SubMonitor
	 */
	public BuildResult validate(DiagnosticChain diagnostics, File source, ValidationOptions options,
			File[] examinedFiles, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("diagnostics can not be null");
		if(source == null)
			throw new IllegalArgumentException("source can not be null");
		if(!source.exists())
			throw new IllegalArgumentException("source does not exist");
		if(!source.canRead())
			throw new IllegalArgumentException("source can not be read");
		if(options == null) {
			options = new ValidationOptions();
			options.setCheckLayout(false);
			options.setCheckModuleSemantics(false);
			options.setCheckReferences(false);
			options.setFileType(FileType.DETECT);
		}
		String sourceName = source.getName();
		boolean isDirectory = source.isDirectory();
		boolean isPP = !isDirectory && sourceName.endsWith(".pp");
		boolean isRB = !isDirectory && sourceName.endsWith(".rb");
		boolean isModulefile = !isDirectory && sourceName.equals("Modulefile");

		if(!isDirectory && examinedFiles != null && examinedFiles.length != 0)
			throw new IllegalArgumentException("examinedFiles must be empty when source is a regular file");

		if(options.getFileType() == FileType.DETECT) {
			if(!isDirectory)
				options.setFileType(FileType.SINGLE_SOURCE_FILE);
			else {
				// A directory that does not have a "Modulefile" is treated as a root
				// A directory that has a "modules" subdirectory is treated as a root
				if(hasModulesSubDirectory(source) || !(hasMetadataFile(source) || hasModuleFile(source)))
					options.setFileType(FileType.PUPPET_ROOT);
				else
					options.setFileType(FileType.MODULE_ROOT);
			}
		}
		boolean rubyServicesPresent = false;

		if(options.getFileType() == FileType.SINGLE_SOURCE_FILE) {
			if(isDirectory)
				throw new IllegalArgumentException("source is not a single source file as stated in options");
			if(isPP) {
				PPDiagnosticsRunner ppDr = new PPDiagnosticsRunner();
				try {
					IValidationAdvisor.ComplianceLevel complianceLevel = options.getComplianceLevel();
					if(complianceLevel == null)
						complianceLevel = IValidationAdvisor.ComplianceLevel.PUPPET_2_7;
					IPotentialProblemsAdvisor potentialProblems = options.getProblemsAdvisor();
					if(potentialProblems == null)
						potentialProblems = new DefaultPotentialProblemsAdvisor();
					ppDr.setUp(complianceLevel, potentialProblems);
					validatePPFile(ppDr, diagnostics, source, source.getParentFile(), monitor);
				}
				catch(Exception e) {
					addExceptionDiagnostic(
						diagnostics, "Internal error: Exception while setting up/tearing down pp org.cloudsmith.geppetto.validation", e);
				}
				finally {
					ppDr.tearDown();
				}

			}
			else if(isRB) {
				RubyHelper rubyHelper = new RubyHelper();
				rubyServicesPresent = rubyHelper.isRubyServicesAvailable();
				try {
					rubyHelper.setUp();
					validateRubyFile(rubyHelper, diagnostics, source, source.getParentFile(), monitor);
				}
				catch(Exception e) {
					addExceptionDiagnostic(
						diagnostics, "Internal error: Exception while setting up/tearing down pp org.cloudsmith.geppetto.validation", e);
				}
				finally {
					rubyHelper.tearDown();
				}

			}
			else if(isModulefile)
				validateModulefile(diagnostics, source, source.getParentFile(), options, monitor);
			else
				throw new IllegalArgumentException("unsupported source type");
			return new BuildResult(rubyServicesPresent);
		}

		if(!isDirectory)
			throw new IllegalArgumentException("source is not a directory as dictated by options");

		return validateDirectory(diagnostics, source, options, examinedFiles, monitor);
	}

	/**
	 * TODO: Is currently limited to .pp content.
	 */
	public Resource validate(DiagnosticChain diagnostics, String code, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("DiagnosticChain can not be null");
		if(code == null)
			throw new IllegalArgumentException("code can not be null");
		final SubMonitor ticker = SubMonitor.convert(monitor, 3);

		PPDiagnosticsRunner ppRunner = new PPDiagnosticsRunner();
		Resource r = null;
		worked(ticker, 1);
		try {
			ppRunner.setUp(IValidationAdvisor.ComplianceLevel.PUPPET_2_7, new DefaultPotentialProblemsAdvisor());
			worked(ticker, 1);
			File f = new File("/unnamed.pp");
			r = ppRunner.loadResource(code, URI.createFileURI(f.getPath()));
			// no need to remember "/" as the root
			IResourceValidator rv = ppRunner.getPPResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, f.getParentFile());
			}
			worked(ticker, 1);
			ppRunner.tearDown();
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error, failed to setUp PPDiagnostic.", e);
		}
		return r;
	}

	/**
	 * TODO: Horribly long method that should be refactored into several to get better optimization.
	 * 
	 * @param diagnostics
	 * @param root
	 * @param options
	 * @param examinedFiles
	 * @param monitor
	 * @return
	 */
	private BuildResult validateDirectory(DiagnosticChain diagnostics, File root, ValidationOptions options,
			File[] examinedFiles, IProgressMonitor monitor) {

		if(!(options.getFileType() == FileType.PUPPET_ROOT || options.getFileType() == FileType.MODULE_ROOT))
			throw new IllegalArgumentException("doDir can only process PUPPET_ROOT or MODULE_ROOT");

		// Process request to check layout
		if(options.isCheckLayout()) {
			if(options.getFileType() == FileType.MODULE_ROOT)
				checkModuleLayout(diagnostics, root, root);
			else if(options.getFileType() == FileType.PUPPET_ROOT)
				checkPuppetRootLayout(diagnostics, root, root);
		}

		List<File> ppFiles = findPPFiles(root);
		List<File> rbFiles = findRubyFiles(root);
		List<File> mdFiles = findModulefileFiles(root);
		List<File> rakeFiles = findRakefiles(root);

		final int workload = ppFiles.size() + mdFiles.size() * 3 + rbFiles.size() * 2 //
				+ rakeFiles.size() * 2 //
				+ 1 // load pptp
				+ 1 // "for the pot" (to make sure there is a final tick to report)
		;

		final SubMonitor ticker = SubMonitor.convert(monitor, workload); // TODO: scaling

		PPDiagnosticsRunner ppRunner = new PPDiagnosticsRunner();
		RubyHelper rubyHelper = new RubyHelper();

		try {
			IValidationAdvisor.ComplianceLevel complianceLevel = options.getComplianceLevel();
			if(complianceLevel == null)
				complianceLevel = IValidationAdvisor.ComplianceLevel.PUPPET_2_7;
			IPotentialProblemsAdvisor problemsAdvisor = options.getProblemsAdvisor();
			if(problemsAdvisor == null)
				problemsAdvisor = new DefaultPotentialProblemsAdvisor();
			ppRunner.setUp(complianceLevel, problemsAdvisor);
			rubyHelper.setUp();
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while setting up diagnostics.", e);
			return new BuildResult(rubyHelper.isRubyServicesAvailable()); // give up
		}
		ppRunner.configureEncoding(options.getEncodingProvider());
		ppRunner.configureSearchPath(root, options.getSearchPath(), options.getEnvironment());

		// get the configured search path
		final PPSearchPath searchPath = ppRunner.getDefaultSearchPath();

		// Modulefile processing
		// Modulefiles must be processed first in order to figure out containers and container
		// visibility.
		final IPath rootPath = new Path(root.getAbsolutePath());
		final IPath nodeRootPath = rootPath.append(NAME_OF_DIR_WITH_RESTRICTED_SCOPE);

		// collect info in a structure
		Multimap<String, MetadataInfo> moduleData = ArrayListMultimap.create();
		for(File f : mdFiles) {
			// load and remember all that loaded ok
			Metadata m = loadModulefileMetadata(diagnostics, f, root, ticker.newChild(1));
			if(m == null)
				worked(ticker, 1);
			else {
				String moduleName = m.getFullName().toLowerCase();
				if(options.isCheckModuleSemantics() && isOnPath(pathToFile(f.getAbsolutePath(), root), searchPath)) {
					// remember the metadata and where it came from
					// and if it represents a NODE as opposed to a regular MODULE
					moduleData.put(
						moduleName, new MetadataInfo(m, f, nodeRootPath.isPrefixOf(new Path(f.getAbsolutePath()))));
				}
				if(isValidationWanted(examinedFiles, f)) {
					validateModuleMetadata(m, diagnostics, f, root, options, ticker.newChild(1));
				}
				else
					worked(ticker, 1);
			}
		}

		if(options.isCheckModuleSemantics()) {
			for(String key : moduleData.keySet()) {
				// check there is only one version of each module
				Collection<MetadataInfo> versions = moduleData.get(key);
				boolean redeclared = versions.size() > 1;

				for(MetadataInfo info : versions) {
					// processed dependencies for one version of a modulefile
					// (in case of errors, there may not be as many ticks as originally requested)
					// this ticks before the fact (but there is "one for the pot" left at the end),
					// as this makes it easier to just do "continue" below.
					worked(ticker, 1);

					// skip checks for unwanted
					final boolean shouldDiagnosticBeReported = isValidationWanted(examinedFiles, info.getFile());
					// if(!)
					// continue;

					if(redeclared && shouldDiagnosticBeReported) {
						addFileError(
							diagnostics, info.getFile(), root, "Redefinition - equally named already exists",
							IValidationConstants.ISSUE__MODULEFILE_REDEFINITION);
					}
					// Resolve all dependencies
					for(Dependency d : info.getMetadata().getDependencies()) {

						// check dependency name and version requirement
						final String requiredName = d.getName().replace("/", "-").toLowerCase();
						final VersionRequirement versionRequirement = d.getVersionRequirement();
						if(requiredName == null || requiredName.isEmpty()) {
							if(shouldDiagnosticBeReported)
								addFileError(
									diagnostics, info.getFile(), root, "Dependency without name",
									IValidationConstants.ISSUE__MODULEFILE_DEPENDENCY_ERROR);
							continue; // not meaningful to resolve this dependency
						}
						if(versionRequirement != null &&
								(versionRequirement.getMatchRule() == null || versionRequirement.getVersion() == null)) {
							if(shouldDiagnosticBeReported)
								addFileError(
									diagnostics, info.getFile(), root,
									"Dependency version requirement error in dependency to: " + d.getName(),
									IValidationConstants.ISSUE__MODULEFILE_DEPENDENCY_ERROR);
							continue; // not meaningful to resolve this dependency
						}

						// find the best candidate (ignore the fact that there should just be one version of each
						// module - there may be several, and one of the match).
						// It is allowed to have modules without versions, they can only be matched by
						// a dependency that does not have a version requirement.
						//
						Collection<MetadataInfo> candidates = moduleData.get(requiredName);
						List<String> candidateVersions = Lists.newArrayList();
						List<MetadataInfo> unversioned = Lists.newArrayList();
						if(candidates != null)
							for(MetadataInfo mi : candidates) {
								String cv = mi.getMetadata().getVersion();
								if(cv == null || cv.length() < 1) {
									unversioned.add(mi);
									continue; // the (possibly) broken version is reported elsewhere
								}
								candidateVersions.add(cv);
							}

						// if the dependency has no version requirement use ">=0"
						if(versionRequirement == null) {
							// find best match for >= 0 if there are candidates with versions
							// the best will always win over unversioned.
							String best = null;
							if(candidateVersions.size() > 0) {
								VersionRequirement vr = ForgeFactory.eINSTANCE.createVersionRequirement();
								vr.setMatchRule(MatchRule.GREATER_OR_EQUAL);
								vr.setVersion("0");
								best = vr.findBestMatch(candidateVersions);

								if(best == null) {
									// this is an internal error, one should have been found
									throw new IllegalStateException("Should have found a best version >= 0");
								}
								// get the matched MetaDataInfo as the resolution of the dependency
								// and remember it
								for(MetadataInfo mi : candidates) {
									if(mi.getMetadata().getVersion().equals(best))
										info.addResolvedDependency(d, mi);
								}

							}
							// or there must be unversioned candidates
							else if(unversioned.size() == 0)
								addFileDiagnostic(
									diagnostics, (candidates.size() > 0
											? Diagnostic.WARNING
											: Diagnostic.ERROR), info.getFile(), root,
									"Unresolved Dependency to: " + d.getName() + " (unversioned).",
									IValidationConstants.ISSUE__MODULEFILE_UNSATISFIED_DEPENDENCY);
							else {
								// pick the first as resolution
								// worry about ambiguity elsewhere
								info.addResolvedDependency(d, unversioned.get(0));
							}
						}
						else {
							// there was a version requirement, it must match something with a version.
							String best = candidateVersions.size() < 1
									? null
									: d.getVersionRequirement().findBestMatch(candidateVersions);
							if(best == null || best.length() < 1) {
								info.addUnresolvedDependency(d);
								if(shouldDiagnosticBeReported)
									addFileDiagnostic(
										diagnostics,
										(candidates.size() > 0
												? Diagnostic.WARNING
												: Diagnostic.ERROR),
										info.getFile(),
										root,
										"Unresolved Dependency to: " + d.getName() + " version: " +
												d.getVersionRequirement(),
										IValidationConstants.ISSUE__MODULEFILE_UNSATISFIED_DEPENDENCY);
							}
							else {
								// get the matched MetaDataInfo as the resolution of the dependency
								// and remember it
								for(MetadataInfo mi : candidates) {
									if(mi.getMetadata().getVersion().equals(best))
										info.addResolvedDependency(d, mi);
								}
							}
						}

					}
				}
			}
			IPotentialProblemsAdvisor advisor = options.getProblemsAdvisor();
			if(advisor != null && advisor.circularDependencyPreference().isWarningOrError()) {
				ValidationPreference preference = options.getProblemsAdvisor().circularDependencyPreference();
				checkCircularDependencies(moduleData, diagnostics, root);
				for(MetadataInfo mi : moduleData.values()) {
					if(isValidationWanted(examinedFiles, mi.getFile())) {
						for(List<MetadataInfo> circularity : mi.getCircularities()) {
							StringBuilder message = new StringBuilder();
							message.append("Circular dependency: ");
							message.append(circularitylabel(circularity));
							addFileDiagnostic(
								diagnostics, preference.isError()
										? Diagnostic.ERROR
										: Diagnostic.WARNING, mi.getFile(), root, message.toString(),
								IPPDiagnostics.ISSUE__CIRCULAR_MODULE_DEPENDENCY);
						}
					}
				}
			}
		}
		// TODO: Wasteful to calculate the URL's more than once.
		// Could be done once per pp and rb (to separate the processing), or have all in one pile
		// and let processing look at extension.

		// Calculate containers
		// sets up iterateable over all files including pptp

		boolean useContainers = true;
		URI uri = options.getPlatformURI();
		if(useContainers) {
			List<URI> pptpURIs = Lists.newArrayList(uri != null
					? uri
					: PPDiagnosticsRunner.getDefaultPptpResourceURI());
			ppRunner.configureContainers(root, moduleData.values(), //
				Iterables.concat(Iterables.transform(Iterables.concat(ppFiles, rbFiles), new Function<File, URI>() {
					@Override
					public URI apply(File from) {
						return URI.createFileURI(from.getPath());
					}
				}), pptpURIs));
		}
		// Load pptp
		if(options.isCheckReferences()) {
			try {
				URI platformURI = options.getPlatformURI();
				ppRunner.loadResource(platformURI != null
						? platformURI
						: PPDiagnosticsRunner.getDefaultPptpResourceURI());
			}
			catch(IOException e) {
				addExceptionDiagnostic(diagnostics, "Internal Error: Could not load pptp.", e);
				return new BuildResult(rubyHelper.isRubyServicesAvailable()); // give up
			}
		}
		worked(ticker, 1);

		// Load all ruby
		for(File f : rbFiles) {
			try {
				// Skip "Rakefile.rb" or they will be processed twice (but still tick x2
				// onece for validate and once for load - as this is included in work-count)
				if(f.getName().toLowerCase().equals("rakefile.rb")) {
					worked(ticker, 2);
					continue;
				}
				// Syntax check ruby file
				// consumes one rb tick
				if(isValidationWanted(examinedFiles, f))
					validateRubyFile(rubyHelper, diagnostics, f, root, ticker.newChild(1));
				else
					worked(ticker, 1);

				// Load ruby file with pptp contribution
				// consumes one rb tick
				if(options.isCheckReferences()) {
					Resource r = ppRunner.loadResource(new FileInputStream(f), URI.createFileURI(f.getPath()));
					if(r != null)
						rememberRootInResource(root, r);
				}
				worked(ticker, 1);
			}
			catch(Exception e) {
				addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.getName() +
						": " + e, e);
				e.printStackTrace();
			}
		}
		RakefileInfo rakefileInfo = new RakefileInfo();
		//		System.err.println("Processing Rakefiles count: " + rakeFiles.size());

		for(File f : rakeFiles) {
			// Syntax check ruby file
			// consumes one rakefile tick
			if(isValidationWanted(examinedFiles, f))
				validateRubyFile(rubyHelper, diagnostics, f, root, ticker.newChild(1));
			else
				worked(ticker, 1);

			// parsing adds one rakefile work tick
			rakefileInfo.addRakefile(getRakefileInformation(rubyHelper, f, root, ticker.newChild(1)));

		}
		// Load all pp
		// crosslink and validate all
		Map<File, Resource> ppResources = Maps.newHashMapWithExpectedSize(ppFiles.size());
		for(File f : ppFiles) {
			try {
				ppResources.put(f, ppRunner.loadResource(new FileInputStream(f), URI.createFileURI(f.getPath())));
			}
			catch(IOException e) {
				addExceptionDiagnostic(diagnostics, "I/O Error: Exception while processing file: " + f.toString(), e);
			}
			catch(Exception e) {
				addExceptionDiagnostic(
					diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
			}
			// consume one pp tick
			worked(ticker, 1);
		}

		// Must set the root in all resources to allow cross reference error reports to contain
		// relative paths
		for(Resource r : ppResources.values())
			rememberRootInResource(root, r);

		IResourceValidator validator = ppRunner.getPPResourceValidator();
		long maxLinkTime = 0;
		// Turn on for debugging particular files
		// File slowCandidate = new File("/Users/henrik/gitrepos/forge-modules/jeffmccune-mockbuild/manifests/init.pp");

		for(Entry<File, Resource> r : ppResources.entrySet()) {
			File f = r.getKey();
			if(!isValidationWanted(examinedFiles, f))
				continue;
			long beforeTime = System.currentTimeMillis();
			boolean profileThis = false; // /* for debugging slow file */ f.equals(slowCandidate);
			if(options.isCheckReferences())
				ppRunner.resolveCrossReferences(r.getValue(), profileThis, ticker);
			long afterTime = System.currentTimeMillis();
			if(afterTime - beforeTime > maxLinkTime) {
				maxLinkTime = afterTime - beforeTime;
			}
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = validator.validate(r.getValue(), CheckMode.ALL, cancelMonitor);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		// // Debug stuff
		// if(slowestFile != null)
		// System.err.printf("Slowest file =%s (%s)\n", slowestFile.getAbsolutePath(), maxLinkTime);

		// // Compute the returned map
		// // Only the restricted modules are wanted (as everything else sees everything)
		// Iterable<File> filteredMdFiles = Iterables.filter(mdFiles, new Predicate<File>() {
		//
		// @Override
		// public boolean apply(File input) {
		// IPath p = new Path(input.getPath());
		// if(p.segmentCount() < 3)
		// return false;
		// p = p.removeLastSegments(2);
		// return NAME_OF_DIR_WITH_RESTRICTED_SCOPE.equals(p.lastSegment());
		// }
		// });
		AllModuleReferences all = ppRunner.getAllModulesState();

		// set the root to allow relative lookup of module exports
		all.setRoot(root);

		// // Debug stuff...
		// for(File f : result.getMap().keySet()) {
		// System.err.println("Exports for file: " + f.toString());
		// for(ExportsPerModule.Export export : result.getMap().get(f)) {
		// System.err.printf(
		// "    %s, %s, %s\n", export.getName(), export.getEClass().getName(), export.getParentName());
		// }
		// }
		ppRunner.tearDown();
		boolean rubyServicesAvailable = rubyHelper.isRubyServicesAvailable();
		rubyHelper.tearDown();
		// make sure everything is consumed
		ticker.setWorkRemaining(0);
		BuildResult buildResult = new BuildResult(rubyServicesAvailable);
		// buildResult.setExportsForNodes(result);
		buildResult.setAllModuleReferences(all);
		buildResult.setRakefileInfo(rakefileInfo);
		return buildResult;
	}

	public void validateManifest(DiagnosticChain diagnostics, File sourceFile, IProgressMonitor monitor) {
		ValidationOptions options = new ValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.SINGLE_SOURCE_FILE);

		validate(diagnostics, sourceFile, options, null, monitor);
	}

	/**
	 * @deprecated use {@link #validate(DiagnosticChain, String)} instead.
	 */
	@Deprecated
	public Resource validateManifest(DiagnosticChain diagnostics, String code, IProgressMonitor monitor) {
		return validate(diagnostics, code, monitor);
	}

	public BuildResult validateModule(DiagnosticChain diagnostics, File moduleRoot, IProgressMonitor monitor) {
		ValidationOptions options = new ValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.MODULE_ROOT);

		return validate(diagnostics, moduleRoot, options, null, monitor);
	}

	/**
	 * Validates a Modulefile by a) loading it, and b) validating the metadata
	 * 
	 * @param diagnostics
	 * @param source
	 * @param parentFile
	 */
	private void validateModulefile(DiagnosticChain diagnostics, File moduleFile, File parentFile,
			ValidationOptions options, IProgressMonitor monitor) {
		SubMonitor ticker = SubMonitor.convert(monitor, 11);
		Metadata metadata = loadModulefileMetadata(diagnostics, moduleFile, parentFile, ticker.newChild(1));
		if(metadata == null)
			return; // failed in some way and should have reported this

		validateModuleMetadata(metadata, diagnostics, moduleFile, parentFile, options, ticker.newChild(10));
	}

	/**
	 * Validate module meta data if options has isCheckModuleSemantics (otherwise this org.cloudsmith.geppetto.validation
	 * is a no-op). A tick is produced on the monitor even if org.cloudsmith.geppetto.validation is not wanted.
	 * NOTE: This method does not check dependency resolution.
	 * 
	 * @param metadata
	 * @param diagnostics
	 * @param moduleFile
	 * @param parentFile
	 * @param options
	 * @param monitor
	 */
	private void validateModuleMetadata(Metadata metadata, DiagnosticChain diagnostics, File moduleFile,
			File parentFile, ValidationOptions options, IProgressMonitor monitor) {

		SubMonitor ticker = SubMonitor.convert(monitor, 1);
		if(options.isCheckModuleSemantics()) {

			// must have name
			String moduleName = metadata.getFullName().toLowerCase();
			if(moduleName == null || moduleName.length() < 1)
				addFileError(
					diagnostics, moduleFile, parentFile, "A name must be specified.",
					IValidationConstants.ISSUE__MODULEFILE_NO_NAME);

			// must have version
			String version = metadata.getVersion();
			if(version == null || version.length() < 1)
				addFileWarning(
					diagnostics, moduleFile, parentFile, "A version should be specified.",
					IValidationConstants.ISSUE__MODULEFILE_NO_VERSION);
		}
		worked(ticker, 1);

	}

	private void validatePPFile(PPDiagnosticsRunner dr, DiagnosticChain diagnostics, File f, File root,
			IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2);
		worked(ticker, 1);
		try {
			FileInputStream input = new FileInputStream(f);
			Resource r = dr.loadResource(input, URI.createFileURI(f.getPath()));
			IResourceValidator rv = dr.getPPResourceValidator();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				public boolean isCanceled() {
					return ticker.isCanceled();
				}
			};

			List<Issue> issues = rv.validate(r, CheckMode.ALL, cancelMonitor);
			worked(ticker, 1);
			for(Issue issue : issues) {
				addIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
	}

	public void validateRepository(DiagnosticChain diagnostics, File catalogRoot, File factorData, File siteFile,
			String nodeName, IProgressMonitor monitor) {
		if(diagnostics == null)
			throw new IllegalArgumentException("diagnostics can not be null");
		if(catalogRoot == null)
			throw new IllegalArgumentException("catalogRoot can not be null");
		if(factorData == null)
			throw new IllegalArgumentException("factorData can not be null");
		if(siteFile == null)
			throw new IllegalArgumentException("siteFile can not be null");
		if(nodeName == null || nodeName.length() < 1)
			throw new IllegalArgumentException("nodeName can not be null or empty");

		assertFileAvailability(factorData, false);
		assertFileAvailability(siteFile, false);
		assertFileAvailability(catalogRoot, true);

		final SubMonitor ticker = SubMonitor.convert(monitor, 2000);
		// perform static org.cloudsmith.geppetto.validation first
		validateRepository(diagnostics, catalogRoot, ticker.newChild(1000));

		// check for early exit due to cancel or errors
		if(diagnostics instanceof Diagnostic) {
			int severity = ((Diagnostic) diagnostics).getSeverity();
			if(severity == Diagnostic.CANCEL || severity > Diagnostic.WARNING)
				return;
		}

		// perform a catalog production
		PuppetCatalogCompilerRunner runner = new PuppetCatalogCompilerRunner();
		runner.compileCatalog(siteFile, catalogRoot, nodeName, factorData, ticker.newChild(1000));
		for(CatalogDiagnostic d : runner.getDiagnostics())
			addCatalogDiagnostics(diagnostics, d);
	}

	public BuildResult validateRepository(DiagnosticChain diagnostics, File catalogRoot, IProgressMonitor monitor) {
		ValidationOptions options = new ValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		return validate(diagnostics, catalogRoot, options, null, monitor);
	}

	private void validateRubyFile(RubyHelper rubyHelper, DiagnosticChain diagnostics, File f, File root,
			IProgressMonitor monitor) {
		SubMonitor ticker = SubMonitor.convert(monitor, 1);
		try {
			IRubyParseResult result = rubyHelper.parse(f);
			for(IRubyIssue issue : result.getIssues()) {
				addRubyIssueDiagnostic(diagnostics, issue, f, root);
			}
		}
		catch(Exception e) {
			addExceptionDiagnostic(diagnostics, "Internal Error: Exception while processing file: " + f.toString(), e);
		}
		worked(ticker, 1);
	}

	private void worked(SubMonitor monitor, int amount) throws OperationCanceledException {
		if(monitor.isCanceled())
			throw new OperationCanceledException();
		monitor.worked(amount);
	}
}
