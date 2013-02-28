/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
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
import java.util.Properties;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class AbstractForgeTestMojo extends AbstractMojoTestCase {
	File pom;

	ProjectBuildingRequest buildingRequest;

	Properties userProps;

	protected MavenSession createMavenSession() {
		buildingRequest.setUserProperties(userProps);
		try {
			MavenProject project = lookup(ProjectBuilder.class).build(pom, buildingRequest).getProject();
			return newMavenSession(project);
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			return null; // keep
		}
	}

	@Override
	protected Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution) throws Exception,
			ComponentConfigurationException {
		MavenProject project = session.getCurrentProject();
		MojoDescriptor mojoDescriptor = execution.getMojoDescriptor();
		Mojo mojo = (Mojo) lookup(mojoDescriptor.getRole(), mojoDescriptor.getRoleHint());

		Xpp3Dom configuration = null;
		Plugin plugin = project.getPlugin(mojoDescriptor.getPluginDescriptor().getPluginLookupKey());
		if(plugin != null) {
			String goal = execution.getGoal();
			configuration = (Xpp3Dom) plugin.getConfiguration();
			for(PluginExecution pe : plugin.getExecutions()) {
				if(pe.getGoals().contains(goal)) {
					Xpp3Dom execConfig = (Xpp3Dom) pe.getConfiguration();
					if(execConfig != null) {
						if(configuration == null)
							configuration = execConfig;
						else
							configuration = Xpp3Dom.mergeXpp3Dom(execConfig, configuration);
					}
					break;
				}
			}
		}
		if(configuration == null)
			configuration = new Xpp3Dom("configuration");

		PlexusConfiguration pluginConfiguration = new XmlPlexusConfiguration(configuration);
		ComponentConfigurator configurator = getContainer().lookup(ComponentConfigurator.class, "basic");

		ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session, execution);
		configurator.configureComponent(mojo, pluginConfiguration, evaluator, getContainer().getContainerRealm());

		return mojo;
	}

	protected void setTestForgeModulesRoot(String project) {
		File projectFile = getTestFile("src/test/resources/workspace/" + project);
		String absPath = projectFile.getAbsolutePath();
		assertTrue("Project file " + absPath + " is not a directory", projectFile.isDirectory());
		userProps.put("testForgeModulesRoot", absPath);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		pom = getTestFile("src/test/resources/unit/publisher/pom.xml");
		Assert.assertNotNull(pom);
		Assert.assertTrue(pom.exists());
		MavenExecutionRequest request = new DefaultMavenExecutionRequest();

		buildingRequest = request.getProjectBuildingRequest();
		userProps = new Properties();
		userProps.put("testForgeServiceURL", System.getProperty("forge.base.url"));
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
