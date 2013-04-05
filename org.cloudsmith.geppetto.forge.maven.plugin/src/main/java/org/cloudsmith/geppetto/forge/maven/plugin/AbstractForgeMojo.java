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

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.common.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.impl.ValidationModule;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParseException;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Goal which performs basic validation.
 */
public abstract class AbstractForgeMojo extends AbstractMojo {
	static final String IMPORTED_MODULES_ROOT = "importedModules";

	static final Charset UTF_8 = Charset.forName("UTF-8");

	public static boolean isNull(String field) {
		if(field == null)
			return true;

		field = field.trim();
		if(field.length() == 0)
			return true;

		return "null".equals(field);
	}

	public static boolean isParentOrEqual(File dir, File subdir) {
		if(dir == null || subdir == null)
			return false;

		return dir.equals(subdir) || isParentOrEqual(dir, subdir.getParentFile());
	}

	public static Properties readForgeProperties() throws IOException {
		Properties props = new Properties();
		InputStream inStream = AbstractForgeMojo.class.getResourceAsStream("/forge.properties");
		if(inStream == null)
			throw new FileNotFoundException("Resource forge.properties");
		try {
			props.load(inStream);
			return props;
		}
		finally {
			inStream.close();
		}
	}

	/**
	 * The directory where this plug-in will search for modules. The directory itself
	 * can be a module or it may be the root of a hierarchy where modules can be found.
	 */
	@Parameter(property = "forge.modules.root", defaultValue = "${project.basedir}")
	private String modulesRoot;

	@Component
	private MavenSession session;

	private transient File baseDir;

	private transient File buildDir;

	private transient File modulesDir;

	private transient Injector forgeInjector;

	private transient Logger log;

	protected void addForgePreferences(ForgePreferencesBean forgePreferences) {
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		Diagnostic diagnostic = new LoggingDiagnostic(getLogger());
		try {
			ForgePreferencesBean forgePreferences = new ForgePreferencesBean();
			addForgePreferences(forgePreferences);

			forgeInjector = Guice.createInjector(
				new ForgeMavenModule(forgePreferences, getFileFilter(), session.getCurrentProject()),
				new ValidationModule());

			invoke(diagnostic);
		}
		catch(JsonParseException e) {
			throw new MojoFailureException(getActionName() + " failed: Invalid Json: " + e.getMessage(), e);
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch(Exception e) {
			throw new MojoFailureException(getActionName() + " failed: " + e.getMessage(), e);
		}
		if(diagnostic.getSeverity() == Diagnostic.ERROR) {
			Exception e = diagnostic.getException();
			if(e == null)
				throw new MojoFailureException(diagnostic.getErrorText());
			throw new MojoFailureException(diagnostic.getErrorText(), e);
		}
	}

	protected Collection<File> findModuleRoots() {
		return getForge().findModuleRoots(getModulesDir(), null);
	}

	protected abstract String getActionName();

	/**
	 * Returns the basedir as an absolute path string without any '..' constructs.
	 * 
	 * @return The absolute path of basedir
	 */
	public File getBasedir() {
		if(baseDir == null) {
			MavenProject project = session.getCurrentProject();
			URI basedirURI;
			File basedir = project.getBasedir();
			if(basedir != null)
				basedirURI = basedir.toURI();
			else
				basedirURI = URI.create(session.getExecutionRootDirectory());
			baseDir = new File(basedirURI.normalize());
		}
		return baseDir;
	}

	protected synchronized File getBuildDir() {
		if(buildDir == null) {
			Build build = session.getCurrentProject().getBuild();
			String buildDirStr = build == null
					? null
					: build.getDirectory();
			if(buildDirStr == null)
				buildDir = new File(getBasedir(), "target");
			else {
				File bd = new File(buildDirStr);
				buildDir = new File(bd.toURI().normalize());
			}
		}
		return buildDir;
	}

	/**
	 * Returns an exclusion filter that rejects everything beneath the build directory plus everything that
	 * the default exclusion filter would reject.
	 * 
	 * @return <tt>true</tt> if the file can be accepted for inclusion
	 */
	protected FileFilter getFileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return ModuleUtils.DEFAULT_FILE_FILTER.accept(file) && !isParentOrEqual(getBuildDir(), file);
			}
		};
	}

	protected Forge getForge() {
		return forgeInjector.getInstance(Forge.class);
	}

	protected Logger getLogger() {
		if(log == null) {
			log = LoggerFactory.getLogger(getClass());
		}
		return log;
	}

	protected MetadataRepository getMetadataRepository() {
		return forgeInjector.getInstance(MetadataRepository.class);
	}

	protected Metadata getModuleMetadata(File moduleDirectory, Diagnostic diag) throws IOException {
		File[] extractedFrom = new File[1];
		Metadata md = getForge().createFromModuleDirectory(moduleDirectory, true, null, extractedFrom, diag);
		if(md == null)
			return null;

		ModuleName fullName = md.getName();
		if(fullName == null || fullName.getOwner() == null || fullName.getName() == null) {
			diag.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.FORGE, "A full name (user-module) must be specified in the Modulefile",
				extractedFrom[0]));
			return null;
		}

		Version ver = md.getVersion();
		if(ver == null) {
			diag.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.FORGE, "A version must be specified in the Modulefile", extractedFrom[0]));
			return null;
		}
		return md;
	}

	protected File getModulesDir() {
		if(modulesDir == null) {
			if(modulesRoot == null)
				modulesDir = getBasedir();
			else {
				File md = new File(modulesRoot);
				if(!md.isAbsolute())
					md = new File(getBasedir(), modulesRoot);
				modulesDir = new File(md.toURI().normalize());
			}
		}
		return modulesDir;
	}

	protected MavenProject getProject() {
		return session.getCurrentProject();
	}

	protected String getRelativePath(File file) {
		IPath rootPath = Path.fromOSString(getModulesDir().getAbsolutePath());
		IPath path = Path.fromOSString(file.getAbsolutePath());
		IPath relative = path.makeRelativeTo(rootPath);
		return relative.toPortableString();
	}

	protected ReleaseService getReleaseService() {
		return forgeInjector.getInstance(ReleaseService.class);
	}

	protected ValidationService getValidationService() {
		return forgeInjector.getInstance(ValidationService.class);
	}

	protected abstract void invoke(Diagnostic result) throws Exception;

	public void setLogger(Logger log) {
		this.log = log;
	}
}
