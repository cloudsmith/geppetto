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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.pp.dsl.target.PuppetTarget;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner;
import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner.Issue;
import org.cloudsmith.geppetto.puppetlint.PuppetLintService;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.runner.IEncodingProvider;
import org.cloudsmith.geppetto.validation.runner.PPDiagnosticsSetup;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;

/**
 * The <tt>validate</tt> goal can perform a very elaborate analyzis of the module using the Geppetto platform. It is also capable of running the
 * <tt>puppet-lint</tt> gem.
 */
@Mojo(name = "validate", requiresProject = false, defaultPhase = LifecyclePhase.COMPILE)
public class Validate extends AbstractForgeServiceMojo {
	private static int getSeverity(Issue issue) {
		switch(issue.getSeverity()) {
			case ERROR:
				return Diagnostic.ERROR;
			default:
				return Diagnostic.WARNING;
		}
	}

	/**
	 * Location of the forge cache. Defaults to ${user.home}/.puppet/var/puppet-module/cache/&lt;MD5 hash of service URL&gt;
	 */
	@Parameter(property = "forge.cache.location")
	private String cacheLocation;

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
	 * If this is set to <tt>true</tt> then the validator will make an attempt to resolve and install all dependencies for the modules that are
	 * validated. Dependencies are resolved transitively and unresolved dependencies are considered to be validation errors.
	 */
	@Parameter(property = "forge.validation.checkModuleSemantics", defaultValue = "false")
	private boolean checkModuleSemantics = false;

	/**
	 * If this is set to <tt>true</tt> then the validator will check puppet cross references.
	 */
	@Parameter(property = "forge.validation.checkReferences", defaultValue = "false")
	private boolean checkReferences = false;

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

	public Validate() {
		super();
	}

	@Override
	protected void addForgePreferences(ForgePreferencesBean forgePreferences, Diagnostic diagnostic) {
		super.addForgePreferences(forgePreferences, diagnostic);
		forgePreferences.setCacheLocation(cacheLocation);
	}

	private Diagnostic convertPuppetLintDiagnostic(File moduleRoot, Issue issue) {
		FileDiagnostic diagnostic = new FileDiagnostic(
			getSeverity(issue), PuppetLintService.PUPPET_LINT, issue.getMessage(), new File(getRelativePath(new File(
				moduleRoot, issue.getPath()))));
		diagnostic.setLineNumber(issue.getLineNumber());
		return diagnostic;
	}

	private void geppettoValidation(Collection<File> moduleLocations, Diagnostic result) throws IOException {

		Collection<File> importedModuleLocations = null;
		List<Metadata> metadatas = new ArrayList<Metadata>();
		for(File moduleRoot : moduleLocations) {
			Metadata md = getModuleMetadata(moduleRoot, result);
			if(md != null)
				metadatas.add(md);
		}

		if(result.getSeverity() == Diagnostic.ERROR)
			return;

		if(checkModuleSemantics) {
			File importedModulesDir = new File(getBuildDir(), IMPORTED_MODULES_ROOT);
			importedModuleLocations = getForge().downloadDependencies(metadatas, importedModulesDir, result);
		}
		if(importedModuleLocations == null)
			importedModuleLocations = Collections.emptyList();

		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
		ValidationOptions options = getValidationOptions(moduleLocations, importedModuleLocations);
		new PPDiagnosticsSetup(complianceLevel, options.getProblemsAdvisor()).createInjectorAndDoEMFRegistration();

		getValidationService().validate(
			result, getModulesDir(), options,
			importedModuleLocations.toArray(new File[importedModuleLocations.size()]), new NullProgressMonitor());
	}

	@Override
	protected String getActionName() {
		return "Validation";
	}

	private String getSearchPath(Collection<File> moduleLocations, Collection<File> importedModuleLocations) {
		StringBuilder searchPath = new StringBuilder();

		searchPath.append("lib/*:environments/$environment/*");

		for(File moduleLocation : moduleLocations)
			searchPath.append(":" + getRelativePath(moduleLocation) + "/*");

		for(File importedModuleLocation : importedModuleLocations)
			searchPath.append(":" + getRelativePath(importedModuleLocation) + "/*");
		return searchPath.toString();
	}

	private ValidationOptions getValidationOptions(Collection<File> moduleLocations,
			Collection<File> importedModuleLocations) {
		ValidationOptions options = new ValidationOptions();
		options.setCheckLayout(checkLayout);
		options.setCheckModuleSemantics(checkModuleSemantics);
		options.setCheckReferences(checkReferences);

		if(moduleLocations.size() == 1 && getModulesDir().equals(moduleLocations.iterator().next()))
			options.setFileType(FileType.MODULE_ROOT);
		else
			options.setFileType(FileType.PUPPET_ROOT);
		options.setPlatformURI(PuppetTarget.forComplianceLevel(complianceLevel, false).getPlatformURI());
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
		Collection<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(
				Diagnostic.ERROR, ValidationService.GEPPETTO, "No modules found in repository"));
			return;
		}

		if(checkLayout || checkModuleSemantics || checkReferences)
			geppettoValidation(moduleRoots, result);

		if(enablePuppetLintValidation)
			lintValidation(moduleRoots, result);
	}

	private void lintValidation(Collection<File> moduleLocations, Diagnostic result) throws IOException {
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
}
