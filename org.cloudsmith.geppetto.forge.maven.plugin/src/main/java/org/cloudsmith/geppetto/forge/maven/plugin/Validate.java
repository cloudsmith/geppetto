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
package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference.IGNORE;
import static org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference.WARNING;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.common.os.StreamUtil.OpenBAStream;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.pp.dsl.target.PptpResourceUtil;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner;
import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner.Issue;
import org.cloudsmith.geppetto.puppetlint.PuppetLintService;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.cloudsmith.geppetto.validation.DetailedDiagnosticData;
import org.cloudsmith.geppetto.validation.DiagnosticType;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationServiceFactory;
import org.cloudsmith.geppetto.validation.runner.IEncodingProvider;
import org.cloudsmith.geppetto.validation.runner.PPDiagnosticsSetup;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;

/**
 * The <tt>validate</tt> goal can perform a very elaborate analyzis of the module using the Geppetto platform. It is also capable of running the
 * <tt>puppet-lint</tt> gem.
 */
@Mojo(name = "validate", requiresProject = false, defaultPhase = LifecyclePhase.VALIDATE)
public class Validate extends AbstractForgeMojo {
	private static int getSeverity(Issue issue) {
		switch(issue.getSeverity()) {
			case ERROR:
				return Diagnostic.ERROR;
			default:
				return Diagnostic.WARNING;
		}
	}

	private static int getSeverity(org.eclipse.emf.common.util.Diagnostic validationDiagnostic) {
		int severity;
		switch(validationDiagnostic.getSeverity()) {
			case org.eclipse.emf.common.util.Diagnostic.ERROR:
				severity = Diagnostic.ERROR;
				break;
			case org.eclipse.emf.common.util.Diagnostic.WARNING:
				severity = Diagnostic.WARNING;
				break;
			case org.eclipse.emf.common.util.Diagnostic.INFO:
				severity = Diagnostic.INFO;
				break;
			default:
				severity = Diagnostic.OK;
		}
		return severity;
	}

	private static String locationLabel(DetailedDiagnosticData detail) {
		int lineNumber = detail.getLineNumber();
		int offset = detail.getOffset();
		int length = detail.getLength();
		StringBuilder builder = new StringBuilder();
		if(lineNumber > 0)
			builder.append(lineNumber);
		else
			builder.append("-");

		if(offset >= 0) {
			builder.append("(");
			builder.append(offset);
			if(length >= 0) {
				builder.append(",");
				builder.append(length);
			}
			builder.append(")");
		}
		return builder.toString();
	}

	/**
	 * Set to <tt>true</tt> to enable validation using puppet-lint
	 */
	@Parameter(property = "forge.validation.enablePuppetLint", defaultValue = "false")
	private boolean enablePuppetLintValidation = false;

	/**
	 * An advisor to validation. Different implementations of this class capture the validation rules specific
	 * to a puppet language version.
	 */
	@Parameter(property = "forge.validation.complianceLevel", defaultValue = "PUPPET_2_7")
	private ComplianceLevel complianceLevel = ComplianceLevel.PUPPET_2_7;

	/**
	 * What environment to use during validation.
	 */
	@Parameter(property = "forge.validation.environment", defaultValue = "production")
	private String environment = "production";

	/**
	 * Check the module layout.
	 */
	@Parameter(property = "forge.validation.checkLayout", defaultValue = "true")
	private boolean checkLayout = true;

	/**
	 * Checking module semantics means that the module's dependencies are satisfied.
	 */
	@Parameter(property = "forge.validation.checkModuleSemantics", defaultValue = "true")
	private boolean checkModuleSemantics = true;

	/**
	 * If this is set to <tt>true</tt> then the validator will make an attempt to resolve and install all dependencies for the modules that are
	 * validated. Dependencies are resolved transitively and unresolved dependencies are considered to be validation errors.
	 */
	@Parameter(property = "forge.validation.checkReferences", defaultValue = "true")
	private boolean checkReferences = true;

	/**
	 * How should assignment to variable $string be treated. Puppet bug http://projects.puppetlabs.com/issues/14093.
	 */
	@Parameter(property = "forge.validation.assignmentToVarNamedString", defaultValue = "WARNING")
	private ValidationPreference assignmentToVarNamedString = WARNING;

	/**
	 * Puppet interprets the strings "false" and "true" as boolean true when they are used in a boolean context.
	 * This validation preference flags them as "not a boolean value"
	 */
	@Parameter(property = "forge.validation.booleansInStringForm", defaultValue = "IGNORE")
	private ValidationPreference booleansInStringForm = IGNORE;

	/**
	 * How an (optional) default that is not placed last should be validated for a case expression.
	 */
	@Parameter(property = "forge.validation.caseDefaultShouldAppearLast", defaultValue = "IGNORE")
	private ValidationPreference caseDefaultShouldAppearLast = IGNORE;

	/**
	 * How should circular module dependencies be reported (ignore, warning, error).
	 */
	@Parameter(property = "forge.validation.circularDependencyPreference", defaultValue = "WARNING")
	private ValidationPreference circularDependencyPreference = WARNING;

	/**
	 * How to validate a dq string - style guide says single quoted should be used if possible.
	 */
	@Parameter(property = "forge.validation.dqStringNotRequired", defaultValue = "IGNORE")
	private ValidationPreference dqStringNotRequired = IGNORE;

	/**
	 * How to validate a dq string when it only contains a single interpolated variable.
	 */
	@Parameter(property = "forge.validation.dqStringNotRequiredVariable", defaultValue = "IGNORE")
	private ValidationPreference dqStringNotRequiredVariable = IGNORE;

	/**
	 * How the 'ensure' property should be validated if not placed first among a resource's properties.
	 */
	@Parameter(property = "forge.validation.ensureShouldAppearFirstInResource", defaultValue = "IGNORE")
	private ValidationPreference ensureShouldAppearFirstInResource = IGNORE;

	/**
	 * How to validate hyphens in non brace enclosed interpolations. In < 2.7 interpolation stops at a hyphen, but
	 * not in 2.7. Thus when using 2.6 code in 2.7 or vice versa, the result is different.
	 */
	@Parameter(property = "forge.validation.interpolatedNonBraceEnclosedHyphens", defaultValue = "WARNING")
	private ValidationPreference interpolatedNonBraceEnclosedHyphens = WARNING;

	/**
	 * How to validate a missing 'default' in switch type expressions i.e. 'case' and 'selector'
	 */
	@Parameter(property = "forge.validation.missingDefaultInSelector", defaultValue = "WARNING")
	private ValidationPreference missingDefaultInSelector = WARNING;

	/**
	 * How to 'validate' the presence of ML comments.
	 */
	@Parameter(property = "forge.validation.mlComments", defaultValue = "IGNORE")
	private ValidationPreference mlComments = IGNORE;

	/**
	 * How to validate right to left relationships ( e.g. a <- b and a <~ b)
	 */
	@Parameter(property = "forge.validation.rightToLeftRelationships", defaultValue = "IGNORE")
	private ValidationPreference rightToLeftRelationships = IGNORE;

	/**
	 * How an (almost required) default that is not placed last should be validated for a selector expression.
	 */
	@Parameter(property = "forge.validation.selectorDefaultShouldAppearLast", defaultValue = "IGNORE")
	private ValidationPreference selectorDefaultShouldAppearLast = IGNORE;

	/**
	 * How to validate unbraced interpolation.
	 */
	@Parameter(property = "forge.validation.unbracedInterpolation", defaultValue = "IGNORE")
	private ValidationPreference unbracedInterpolation = IGNORE;

	/**
	 * How to validate a literal resource title. Style guide says they should be single quoted.
	 */
	@Parameter(property = "forge.validation.unquotedResourceTitles", defaultValue = "IGNORE")
	private ValidationPreference unquotedResourceTitles = IGNORE;

	/**
	 * A list of {@link PuppetLintRunner.Option Option} declarations that controls the behavior
	 * of the puppetlint gem.
	 * 
	 * @See {@link #enablePuppetLintValidation}
	 */
	@Parameter(property = "forge.lint.options")
	private PuppetLintRunner.Option[] puppetLintOptions;

	private final IPotentialProblemsAdvisor potentialProblemsAdvisor = new IPotentialProblemsAdvisor() {
		@Override
		public ValidationPreference assignmentToVarNamedString() {
			return assignmentToVarNamedString;
		}

		@Override
		public ValidationPreference booleansInStringForm() {
			return booleansInStringForm;
		}

		@Override
		public ValidationPreference caseDefaultShouldAppearLast() {
			return caseDefaultShouldAppearLast;
		}

		@Override
		public ValidationPreference circularDependencyPreference() {
			return circularDependencyPreference;
		}

		@Override
		public ValidationPreference dqStringNotRequired() {
			return dqStringNotRequired;
		}

		@Override
		public ValidationPreference dqStringNotRequiredVariable() {
			return dqStringNotRequiredVariable;
		}

		@Override
		public ValidationPreference ensureShouldAppearFirstInResource() {
			return ensureShouldAppearFirstInResource;
		}

		@Override
		public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
			return interpolatedNonBraceEnclosedHyphens;
		}

		@Override
		public ValidationPreference missingDefaultInSelector() {
			return missingDefaultInSelector;
		}

		@Override
		public ValidationPreference mlComments() {
			return mlComments;
		}

		@Override
		public ValidationPreference rightToLeftRelationships() {
			return rightToLeftRelationships;
		}

		@Override
		public ValidationPreference selectorDefaultShouldAppearLast() {
			return selectorDefaultShouldAppearLast;
		}

		@Override
		public ValidationPreference unbracedInterpolation() {
			return unbracedInterpolation;
		}

		@Override
		public ValidationPreference unquotedResourceTitles() {
			return unquotedResourceTitles;
		}
	};

	private Diagnostic convertPuppetLintDiagnostic(File moduleRoot, Issue issue) {
		Diagnostic diagnostic = new Diagnostic();
		diagnostic.setSeverity(getSeverity(issue));
		diagnostic.setMessage(issue.getMessage());
		diagnostic.setType(DiagnosticType.PUPPET_LINT);
		diagnostic.setResourcePath(getRelativePath(new File(moduleRoot, issue.getPath())));
		diagnostic.setLocationLabel(Integer.toString(issue.getLineNumber()));
		return diagnostic;
	}

	private Diagnostic convertValidationDiagnostic(org.eclipse.emf.common.util.Diagnostic validationDiagnostic) {

		Object dataObj = validationDiagnostic.getData().get(0);
		String resourcePath = null;
		String locationLabel = null;
		if(dataObj instanceof DetailedDiagnosticData) {
			DetailedDiagnosticData details = (DetailedDiagnosticData) dataObj;
			resourcePath = details.getFile().getPath();
			if(resourcePath != null && resourcePath.startsWith(BUILD_DIR))
				// We don't care about warnings/errors from imported modules
				return null;
			locationLabel = locationLabel(details);
		}

		Diagnostic diagnostic = new Diagnostic();
		diagnostic.setSeverity(getSeverity(validationDiagnostic));
		diagnostic.setType(DiagnosticType.getByCode(validationDiagnostic.getCode()));
		diagnostic.setMessage(validationDiagnostic.getMessage());
		diagnostic.setResourcePath(resourcePath);
		diagnostic.setLocationLabel(locationLabel);
		return diagnostic;
	}

	private File downloadAndInstall(ReleaseService releaseService, File modulesRoot, Release release,
			Diagnostic diagnostic) throws IOException {
		OpenBAStream content = new OpenBAStream();
		Module module = release.getModule();
		releaseService.download(module.getOwner().getUsername(), module.getName(), release.getVersion(), content);
		File moduleDir = new File(modulesRoot, module.getName());
		TarUtils.unpack(new GZIPInputStream(content.getInputStream()), moduleDir, false);
		return moduleDir;
	}

	private void geppettoValidation(List<File> moduleLocations, Diagnostic result) throws IOException {

		MetadataRepository metadataRepo = getForge().createMetadataRepository();

		List<File> importedModuleLocations = null;
		List<Metadata> metadatas = new ArrayList<Metadata>();
		for(File moduleRoot : moduleLocations)
			metadatas.add(getModuleMetadata(moduleRoot, result));

		if(result.getSeverity() == Diagnostic.ERROR)
			return;

		if(checkReferences) {
			Set<Dependency> unresolvedCollector = new HashSet<Dependency>();
			Set<Release> releasesToDownload = resolveDependencies(metadataRepo, metadatas, unresolvedCollector);
			for(Dependency unresolved : unresolvedCollector)
				result.addChild(new Diagnostic(Diagnostic.WARNING, DiagnosticType.GEPPETTO, String.format(
					"Unable to resolve dependency: %s:%s", unresolved.getName(),
					unresolved.getVersionRequirement().toString())));

			if(!releasesToDownload.isEmpty()) {
				File importedModulesDir = new File(getBuildDir(), IMPORTED_MODULES_ROOT);
				importedModulesDir.mkdirs();
				importedModuleLocations = new ArrayList<File>();

				ReleaseService releaseService = getForge().createReleaseService();
				for(Release release : releasesToDownload) {
					result.addChild(new Diagnostic(
						Diagnostic.INFO, DiagnosticType.GEPPETTO, "Installing dependent module " +
								release.getFullName() + ':' + release.getVersion()));
					importedModuleLocations.add(downloadAndInstall(releaseService, importedModulesDir, release, result));
				}
			}
			else {
				if(unresolvedCollector.isEmpty())
					result.addChild(new Diagnostic(
						Diagnostic.INFO, DiagnosticType.GEPPETTO, "No additional dependencies were detected"));
			}
		}
		if(importedModuleLocations == null)
			importedModuleLocations = Collections.emptyList();
		BasicDiagnostic diagnostics = new BasicDiagnostic();

		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
		ValidationOptions options = getValidationOptions(moduleLocations, importedModuleLocations);
		new PPDiagnosticsSetup(complianceLevel, options.getProblemsAdvisor()).createInjectorAndDoEMFRegistration();

		ValidationServiceFactory.createValidationService().validate(
			diagnostics, getModulesRoot(), options,
			importedModuleLocations.toArray(new File[importedModuleLocations.size()]), new NullProgressMonitor());

		for(org.eclipse.emf.common.util.Diagnostic diagnostic : diagnostics.getChildren()) {
			Diagnostic diag = convertValidationDiagnostic(diagnostic);
			if(diag != null)
				result.addChild(diag);
		}
	}

	@Override
	protected String getActionName() {
		return "Validation";
	}

	private String getSearchPath(List<File> moduleLocations, List<File> importedModuleLocations) {
		StringBuilder searchPath = new StringBuilder();

		searchPath.append("lib/*:environments/$environment/*");

		for(File moduleLocation : moduleLocations)
			searchPath.append(":" + getRelativePath(moduleLocation) + "/*");

		for(File importedModuleLocation : importedModuleLocations)
			searchPath.append(":" + getRelativePath(importedModuleLocation) + "/*");
		return searchPath.toString();
	}

	private ValidationOptions getValidationOptions(List<File> moduleLocations, List<File> importedModuleLocations) {
		ValidationOptions options = new ValidationOptions();
		options.setCheckLayout(checkLayout);
		options.setCheckModuleSemantics(checkModuleSemantics);
		options.setCheckReferences(checkReferences);

		if(moduleLocations.size() == 1 && getModulesRoot().equals(moduleLocations.get(0)))
			options.setFileType(FileType.MODULE_ROOT);
		else
			options.setFileType(FileType.PUPPET_ROOT);

		switch(complianceLevel) {
			case PUPPET_2_6:
				options.setPlatformURI(PptpResourceUtil.getPuppet_2_6_9());
				break;
			case PUPPET_2_7:
				options.setPlatformURI(PptpResourceUtil.getPuppet_2_7_19());
				break;
			case PUPPET_3_0:
				options.setPlatformURI(PptpResourceUtil.getPuppet_3_0_0());
		}

		options.setEncodingProvider(new IEncodingProvider() {
			public String getEncoding(URI file) {
				return UTF_8.name();
			}
		});

		options.setSearchPath(getSearchPath(moduleLocations, importedModuleLocations));
		options.setProblemsAdvisor(potentialProblemsAdvisor);
		return options;
	}

	@Override
	protected void invoke(Diagnostic result) throws IOException {
		List<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.GEPPETTO, "No modules found in repository"));
			return;
		}

		if(checkLayout || checkModuleSemantics || checkReferences)
			geppettoValidation(moduleRoots, result);

		if(enablePuppetLintValidation)
			lintValidation(moduleRoots, result);
	}

	private void lintValidation(List<File> moduleLocations, Diagnostic result) throws IOException {
		PuppetLintRunner runner = PuppetLintService.getInstance().getPuppetLintRunner();
		getLog().debug("Performing puppet lint validation on all modules");
		if(puppetLintOptions == null)
			puppetLintOptions = new PuppetLintRunner.Option[0];
		for(File moduleRoot : moduleLocations) {
			for(PuppetLintRunner.Issue issue : runner.run(moduleRoot, puppetLintOptions)) {
				Diagnostic diag = convertPuppetLintDiagnostic(moduleRoot, issue);
				if(diag != null)
					result.addChild(diag);
			}
		}
	}

	private Set<Release> resolveDependencies(MetadataRepository metadataRepo, List<Metadata> metadatas,
			Set<Dependency> unresolvedCollector) throws IOException {
		// Resolve missing dependencies
		Set<Dependency> deps = new HashSet<Dependency>();
		for(Metadata metadata : metadatas)
			deps.addAll(metadata.getDependencies());

		// Remove the dependencies that appoints modules that we have in the
		// workspace
		Iterator<Dependency> depsItor = deps.iterator();
		nextDep: while(depsItor.hasNext()) {
			Dependency dep = depsItor.next();
			for(Metadata metadata : metadatas)
				if(dep.matches(metadata)) {
					depsItor.remove();
					continue nextDep;
				}
		}

		// Resolve remaining dependencies
		Set<Release> releasesToDownload = new HashSet<Release>();
		for(Dependency dep : deps)
			releasesToDownload.addAll(metadataRepo.deepResolve(dep, unresolvedCollector));
		return releasesToDownload;
	}
}
