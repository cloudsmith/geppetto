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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.internal.MojoDescriptorCreator;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.MavenPluginManager;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.Parameter;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptorBuilder;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.repository.RepositorySystem;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.InterpolationFilterReader;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.xml.XmlStreamReader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Before;

public class AbstractForgeTestMojo {
	File pom;

	ProjectBuildingRequest buildingRequest;

	Properties userProps;

	private PlexusContainer container;

	private PluginDescriptor pluginDescriptor;

	private Map<String, MojoDescriptor> mojoDescriptors;

	private File basedir;

	private MavenSession mavenSession;

	protected MavenSession createMavenSession() {
		buildingRequest.setUserProperties(userProps);
		try {
			MavenProject project = getContainer().lookup(ProjectBuilder.class).build(pom, buildingRequest).getProject();
			mavenSession = newMavenSession(project);
			return mavenSession;
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			return null; // keep
		}
	}

	private void finalizeMojoConfiguration(MojoExecution mojoExecution) {
		MojoDescriptor mojoDescriptor = mojoExecution.getMojoDescriptor();
		MavenProject project = getMavenSession().getCurrentProject();
		PluginDescriptor pluginDescriptor = mojoDescriptor.getPluginDescriptor();
		Plugin plugin = project.getPlugin(pluginDescriptor.getPluginLookupKey());
		pluginDescriptor.setPlugin(plugin);

		Xpp3Dom executionConfiguration = mojoExecution.getConfiguration();
		if(executionConfiguration == null)
			executionConfiguration = new Xpp3Dom("configuration");

		Xpp3Dom defaultConfiguration = MojoDescriptorCreator.convert(mojoDescriptor);
		Xpp3Dom finalConfiguration = new Xpp3Dom("configuration");

		if(mojoDescriptor.getParameters() != null) {
			for(Parameter parameter : mojoDescriptor.getParameters()) {
				Xpp3Dom parameterConfiguration = executionConfiguration.getChild(parameter.getName());

				if(parameterConfiguration == null)
					parameterConfiguration = executionConfiguration.getChild(parameter.getAlias());

				Xpp3Dom parameterDefaults = defaultConfiguration.getChild(parameter.getName());
				parameterConfiguration = Xpp3Dom.mergeXpp3Dom(parameterConfiguration, parameterDefaults, Boolean.TRUE);

				if(parameterConfiguration != null) {
					parameterConfiguration = new Xpp3Dom(parameterConfiguration, parameter.getName());

					if(StringUtils.isEmpty(parameterConfiguration.getAttribute("implementation")) &&
							StringUtils.isNotEmpty(parameter.getImplementation())) {
						parameterConfiguration.setAttribute("implementation", parameter.getImplementation());
					}
					finalConfiguration.addChild(parameterConfiguration);
				}
			}
		}

		if(plugin != null) {
			String goal = mojoExecution.getGoal();
			for(PluginExecution pe : plugin.getExecutions()) {
				if(pe.getGoals().contains(goal)) {
					Xpp3Dom execConfig = (Xpp3Dom) pe.getConfiguration();
					if(execConfig != null)
						finalConfiguration = Xpp3Dom.mergeXpp3Dom(execConfig, finalConfiguration);
					break;
				}
			}
		}
		mojoExecution.setConfiguration(finalConfiguration);
	}

	protected File getBasedir() {
		if(basedir == null)
			basedir = new File(new File(System.getProperty("basedir", ".")).toURI().normalize());
		return basedir;
	}

	protected PlexusContainer getContainer() {
		if(container == null) {
			try {
				container = new DefaultPlexusContainer(setupContainerConfiguration());
			}
			catch(PlexusContainerException e) {
				e.printStackTrace();
				fail("Failed to create plexus container.");
			}
		}
		return container;
	}

	protected MavenSession getMavenSession() {
		if(mavenSession == null)
			fail("No maven session has been created");
		return mavenSession;
	}

	protected Map<String, MojoDescriptor> getMojoDescriptors() {
		if(mojoDescriptors == null) {
			mojoDescriptors = new HashMap<String, MojoDescriptor>();
			for(MojoDescriptor mojoDescriptor : getPluginDescriptor().getMojos()) {
				mojoDescriptors.put(mojoDescriptor.getGoal(), mojoDescriptor);
			}
		}
		return mojoDescriptors;
	}

	protected PluginDescriptor getPluginDescriptor() {
		if(pluginDescriptor == null) {
			try {
				InputStream is = getClass().getResourceAsStream("/META-INF/maven/plugin.xml");
				try {
					XmlStreamReader reader = ReaderFactory.newXmlReader(is);
					InterpolationFilterReader interpolationFilterReader = new InterpolationFilterReader(
						new BufferedReader(reader), container.getContext().getContextData());

					PlexusContainer pc = getContainer();
					pluginDescriptor = new PluginDescriptorBuilder().build(interpolationFilterReader);
					Artifact artifact = pc.lookup(RepositorySystem.class).createArtifact(
						pluginDescriptor.getGroupId(), pluginDescriptor.getArtifactId(), pluginDescriptor.getVersion(),
						".jar");
					artifact.setFile(getBasedir());
					pluginDescriptor.setClassRealm(pc.getContainerRealm());
					pluginDescriptor.setPluginArtifact(artifact);
					pluginDescriptor.setArtifacts(Arrays.asList(artifact));

					for(ComponentDescriptor<?> desc : pluginDescriptor.getComponents())
						pc.addComponentDescriptor(desc);
				}
				finally {
					is.close();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
		return pluginDescriptor;
	}

	protected MavenPluginManager getPluginManager() {
		try {
			return getContainer().lookup(MavenPluginManager.class);
		}
		catch(ComponentLookupException e) {
			e.printStackTrace();
			fail("Failed to obtain plugin manager.");
			return null;
		}
	}

	protected File getTestFile(String path) {
		return new File(getBasedir(), path);
	}

	protected Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution) throws Exception,
			ComponentConfigurationException {

		return getPluginManager().getConfiguredMojo(Mojo.class, session, execution);
	}

	protected MavenSession newMavenSession(MavenProject project) {
		MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		MavenExecutionResult result = new DefaultMavenExecutionResult();

		MavenSession session = new MavenSession(container, new MavenRepositorySystemSession(), request, result);
		session.setCurrentProject(project);
		session.setProjects(Arrays.asList(project));
		return session;
	}

	protected MojoExecution newMojoExecution(Plugin plugin, String goal, String executionId) {
		return new MojoExecution(plugin, goal, executionId);
	}

	protected MojoExecution newMojoExecution(String goal) {
		MojoDescriptor mojoDescriptor = getMojoDescriptors().get(goal);
		assertNotNull(mojoDescriptor);
		MojoExecution execution = new MojoExecution(mojoDescriptor);
		finalizeMojoConfiguration(execution);
		return execution;
	}

	protected void setTestForgeModulesRoot(String project) {
		File projectFile = getTestFile("src/test/resources/workspace/" + project);
		String absPath = projectFile.getAbsolutePath();
		assertTrue("Project file " + absPath + " is not a directory", projectFile.isDirectory());
		userProps.put("testForgeModulesRoot", absPath);
	}

	@Before
	public void setUp() throws Exception {
		pom = getTestFile("src/test/resources/unit/publisher/pom.xml");
		Assert.assertNotNull(pom);
		Assert.assertTrue(pom.exists());
		MavenExecutionRequest request = new DefaultMavenExecutionRequest();

		mavenSession = null;
		buildingRequest = request.getProjectBuildingRequest();
		userProps = new Properties();
		userProps.put("testForgeServiceURL", System.getProperty("forge.base.url"));
	}

	protected ContainerConfiguration setupContainerConfiguration() {
		ClassWorld classWorld = new ClassWorld("plexus.core", Thread.currentThread().getContextClassLoader());

		return new DefaultContainerConfiguration().setClassWorld(classWorld).setName("embedder");
	}
}
