/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.maven.plugin;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.forge.Forge.PACKAGE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;

import com.google.common.base.Charsets;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.model.Metadata;

/**
 * Goal that builds the module gzipped tarball and optionally generates the <tt>metadata.json</tt> file.
 */
@Mojo(name = "package", requiresProject = false, defaultPhase = LifecyclePhase.PACKAGE)
public class Package extends AbstractForgeMojo {
	private static final String[] readmeFiles = {
			"README.markdown", "README.md", "README.txt", "README", "README.mkdn", "README.mkd" };

	private static final String[] changeLogFiles = { "Changes", "Changes.md", "Changelog", "Changelog.md" };

	private static final String[] licenseFiles = { "LICENSE", "COPYING" };

	private static File findOneOf(File[] children, String[] fileNames) {
		for(File file : children) {
			String n = file.getName();
			for(String fileName : fileNames)
				if(n.equalsIgnoreCase(fileName))
					return file;
		}
		return null;
	}

	@Component
	private RepositorySystem repositorySystem;

	private File buildForge(File moduleSource, File destination, Metadata[] resultingMetadata, Diagnostic result)
			throws IOException {
		return getForgeUtil().build(moduleSource, destination, null, resultingMetadata, null, result);
	}

	@Override
	protected String getActionName() {
		return "Package";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		Collection<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(ERROR, PACKAGE, "No modules found in repository"));
			return;
		}

		File buildDir = getBuildDir();
		buildDir.mkdirs();
		if(moduleRoots.size() == 1) {
			MavenProject project = getProject();
			File moduleRoot = moduleRoots.iterator().next();
			Metadata[] resultingMetadata = new Metadata[1];
			project.getArtifact().setFile(buildForge(moduleRoot, buildDir, resultingMetadata, result));

			Artifact pmriArtifact = repositorySystem.createArtifact(
				project.getGroupId(), project.getArtifactId(), project.getVersion(), "compile", "pmri");

			PuppetModuleReleaseInfo pmri = new PuppetModuleReleaseInfo();
			File[] children = moduleRoot.listFiles();
			pmri.setMetadata(resultingMetadata[0]);
			pmri.setLicense(readLicense(children));
			pmri.setChangelog(readChangelog(children));
			pmri.setReadme(readReadme(children));

			File pmriFile = new File(buildDir, "release.pmri");
			OutputStream out = new FileOutputStream(pmriFile);
			try {
				Writer writer = new BufferedWriter(new OutputStreamWriter(out, Charsets.UTF_8));
				getGson().toJson(pmri, writer);
				writer.flush();
			}
			finally {
				out.close();
			}
			pmriArtifact.setFile(pmriFile);
			pmriArtifact.setResolved(true);
			project.addAttachedArtifact(pmriArtifact);
		}
		else {
			File builtModules = new File(buildDir, "builtModules");
			if(!(builtModules.mkdir() || builtModules.isDirectory())) {
				result.addChild(new Diagnostic(ERROR, PACKAGE, "Unable to create directory" + builtModules.getPath()));
				return;
			}
			for(File moduleRoot : moduleRoots)
				buildForge(moduleRoot, builtModules, null, result);
		}
	}

	private String readChangelog(File[] children) throws IOException {
		return readContent(children, changeLogFiles);
	}

	private String readContent(File[] children, String[] fileNames) throws IOException {
		File file = findOneOf(children, fileNames);
		if(file == null)
			return null;
		byte[] buf = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		try {
			in.read(buf);
			return new String(buf, Charsets.UTF_8);
		}
		finally {
			in.close();
		}
	}

	private String readLicense(File[] children) throws IOException {
		return readContent(children, licenseFiles);
	}

	private String readReadme(File[] children) throws IOException {
		return readContent(children, readmeFiles);
	}
}
