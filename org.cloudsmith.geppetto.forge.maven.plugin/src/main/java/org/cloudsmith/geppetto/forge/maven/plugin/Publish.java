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

import static org.cloudsmith.geppetto.diagnostic.Diagnostic.ERROR;
import static org.cloudsmith.geppetto.forge.Forge.FORGE;
import static org.cloudsmith.geppetto.forge.Forge.PUBLISHER;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.client.OAuthModule;

import com.google.inject.Module;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "publish", requiresProject = false, defaultPhase = LifecyclePhase.DEPLOY)
public class Publish extends AbstractForgeServiceMojo {
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
	 * Set to <tt>true</tt> to enable validation using puppet-lint
	 */
	@Parameter(property = "forge.publish.dryrun")
	private boolean dryRun;

	public Publish() {
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

	@Override
	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		super.addModules(diagnostic, modules);

		if(login == null || login.length() == 0)
			diagnostic.addChild(new Diagnostic(ERROR, FORGE, "login must be specified"));

		if(password == null || password.length() == 0)
			diagnostic.addChild(new Diagnostic(ERROR, FORGE, "password must be specified"));

		if(diagnostic.getSeverity() >= ERROR)
			return;

		modules.add(new OAuthModule(clientID, clientSecret, login, password));
	}

	@Override
	protected String getActionName() {
		return "Publishing";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		File[] builtModules;
		File targetFile = getProject().getArtifact().getFile();
		if(targetFile == null) {
			File builtModulesDir = new File(getBuildDir(), "builtModules");
			builtModules = builtModulesDir.listFiles();
			if(builtModules == null || builtModules.length == 0) {
				result.addChild(new Diagnostic(ERROR, PUBLISHER, "Unable find any packaged modules in " +
						builtModulesDir.getAbsolutePath()));
				return;
			}
		}
		else
			builtModules = new File[] { targetFile };

		getForge().publishAll(builtModules, dryRun, result);
	}
}
