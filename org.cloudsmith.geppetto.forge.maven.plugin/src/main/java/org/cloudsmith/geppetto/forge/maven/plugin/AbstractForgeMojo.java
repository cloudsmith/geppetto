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
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.impl.MetadataImpl;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.cloudsmith.geppetto.forge.util.ModuleFinder;
import org.cloudsmith.geppetto.forge.v2.Forge;
import org.cloudsmith.geppetto.forge.v2.client.ForgePreferences;
import org.cloudsmith.geppetto.forge.v2.client.ForgePreferencesBean;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.QName;
import org.cloudsmith.geppetto.validation.DiagnosticType;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * Goal which performs basic validation.
 */
public abstract class AbstractForgeMojo extends AbstractMojo {
	static final String IMPORTED_MODULES_ROOT = "importedModules";

	static final Charset UTF_8 = Charset.forName("UTF-8");

	private static boolean isNull(String field) {
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

	/**
	 * The ClientID to use when performing retrieval of OAuth token. This
	 * parameter is only used when the OAuth token is not provided.
	 */
	private String clientID;

	/**
	 * The ClientSecret to use when performing retrieval of OAuth token. This
	 * parameter is only used when the OAuth token is not provided.
	 */
	private String clientSecret;

	/**
	 * The login name. Not required when the OAuth token is provided.
	 */
	@Parameter(property = "forge.login")
	private String login;

	/**
	 * The OAuth token to use for authentication. If it is provided, then the
	 * login and password does not have to be provided.
	 */
	@Parameter(property = "forge.auth.token")
	private String oauthToken;

	/**
	 * The password. Not required when the OAuth token is provided.
	 */
	@Parameter(property = "forge.password")
	private String password;

	/**
	 * The service URL of the Puppet Forge server
	 */
	@Parameter(property = "forge.serviceURL", defaultValue = "http://forge-staging-web.puppetlabs.com/")
	private String serviceURL;

	@Component
	private MavenSession session;

	private ForgePreferencesBean forgePreferences;

	private transient File baseDir;

	private transient File buildDir;

	private transient File modulesDir;

	private transient Forge forge;

	private transient Logger log;

	public AbstractForgeMojo() {
		try {
			Properties props = readForgeProperties();
			clientID = props.getProperty("forge.oauth.clientID");
			clientSecret = props.getProperty("forge.oauth.clientSecret");
		}
		catch(IOException e) {
			// Not able to read properties
			throw new RuntimeException(e);
		}
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		Diagnostic diagnostic = new Diagnostic();
		try {
			if(serviceURL == null)
				throw new MojoExecutionException("Missing required configuration parameter: 'serviceURL'");
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
		logDiagnostic(null, diagnostic);
		if(diagnostic.getSeverity() == Diagnostic.ERROR)
			throw new MojoFailureException(diagnostic.getErrorText());
	}

	protected List<File> findModuleRoots() {
		ModuleFinder moduleFinder = new ModuleFinder(getModulesDir());
		return moduleFinder.findModuleRoots(getFileFilter());
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
				return MetadataImpl.DEFAULT_FILE_FILTER.accept(file) && !isParentOrEqual(getBuildDir(), file);
			}
		};
	}

	protected synchronized Forge getForge() {
		if(forge == null)
			forge = new Forge(getForgePreferences());
		return forge;
	}

	protected synchronized ForgePreferences getForgePreferences() {
		if(forgePreferences == null) {
			forgePreferences = new ForgePreferencesBean();
			if(!serviceURL.endsWith("/"))
				serviceURL += "/";
			forgePreferences.setBaseURL(serviceURL + "v2/");
			forgePreferences.setOAuthURL(serviceURL + "oauth/token");
			forgePreferences.setOAuthAccessToken(oauthToken);
			forgePreferences.setOAuthClientId(clientID);
			forgePreferences.setOAuthClientSecret(clientSecret);
			forgePreferences.setLogin(login);
			forgePreferences.setPassword(password);
			forgePreferences.setOAuthScopes("");
		}
		return forgePreferences;
	}

	protected Logger getLogger() {
		if(log == null)
			log = LoggerFactory.getLogger(getClass());
		return log;
	}

	protected Metadata getModuleMetadata(File moduleDirectory, Diagnostic diag) throws IOException {
		StringWriter writer = new StringWriter();
		try {
			ForgeService forgeService = ForgeFactory.eINSTANCE.createForgeService();
			org.cloudsmith.geppetto.forge.Metadata md;
			Gson gson = JsonUtils.getGSon();
			try {
				md = forgeService.loadJSONMetadata(new File(moduleDirectory, "metadata.json"));
			}
			catch(FileNotFoundException e) {
				md = forgeService.loadModule(moduleDirectory, getFileFilter());
			}
			// TODO: User the v2 Metadata throughout.
			gson.toJson(md, writer);
		}
		finally {
			StreamUtil.close(writer);
		}
		Gson gson = getForge().createGson();
		Metadata md = gson.fromJson(writer.toString(), Metadata.class);
		if(md.getVersion() == null)
			diag.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.GEPPETTO, "Module Version must not be null"));

		QName qname = md.getName();
		if(qname != null) {
			String qual = qname.getQualifier();
			String name = qname.getName();
			if(isNull(qual)) {
				qual = null;
				diag.addChild(new Diagnostic(
					Diagnostic.ERROR, DiagnosticType.GEPPETTO, "Module Qualifier must not be null"));
			}
			if(isNull(name)) {
				name = null;
				diag.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.GEPPETTO, "Module Name must not be null"));
			}
			if(qual == null || name == null) {
				qname = new QName(qual, name);
				md.setName(qname);
			}
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

	protected String getRelativePath(File file) {
		IPath rootPath = Path.fromOSString(getModulesDir().getAbsolutePath());
		IPath path = Path.fromOSString(file.getAbsolutePath());
		IPath relative = path.makeRelativeTo(rootPath);
		return relative.toPortableString();
	}

	protected abstract void invoke(Diagnostic result) throws Exception;

	private void logDiagnostic(String indent, Diagnostic diag) {
		if(diag == null)
			return;

		String msg = diag.getMessage();
		if(indent != null)
			msg = indent + msg;

		if(msg != null) {
			msg = diag.getType().name() + ": " + msg;
			switch(diag.getSeverity()) {
				case Diagnostic.DEBUG:
					getLogger().debug(msg);
					break;
				case Diagnostic.WARNING:
					getLogger().warn(msg);
					break;
				case Diagnostic.FATAL:
				case Diagnostic.ERROR:
					getLogger().error(msg);
					break;
				default:
					getLogger().info(msg);
			}
			if(indent == null)
				indent = "  ";
			else
				indent = indent + "  ";
		}

		for(Diagnostic child : diag.getChildren())
			logDiagnostic(indent, child);
	}

	public void setLogger(Logger log) {
		this.log = log;
	}
}
