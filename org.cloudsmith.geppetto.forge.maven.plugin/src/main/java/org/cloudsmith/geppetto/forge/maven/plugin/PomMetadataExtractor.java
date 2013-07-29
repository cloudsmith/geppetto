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
package org.cloudsmith.geppetto.forge.maven.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.Restriction;
import org.apache.maven.model.Scm;
import org.apache.maven.project.MavenProject;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.impl.AbstractMetadataExtractor;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.cloudsmith.geppetto.validation.ValidationService;

import com.google.inject.Inject;

public class PomMetadataExtractor extends AbstractMetadataExtractor {

	@Inject
	private MavenProject mavenProject;

	@Override
	public boolean canExtractFrom(File moduleDirectory, FileFilter filter) {
		// @fmtOff
		return mavenProject != null
			&& mavenProject.getFile() != null
			&& filter.accept(mavenProject.getFile())
			&& "puppet-module".equals(mavenProject.getPackaging())
			&& moduleDirectory != null
			&& filter.accept(moduleDirectory)
			&& moduleDirectory.equals(mavenProject.getFile().getParentFile());
		// @fmtOn
	}

	@Override
	public int getCardinal() {
		return 20;
	}

	@Override
	public String getPrimarySource() {
		return "pom.xml";
	}

	@Override
	public Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException {
		Metadata metadata = new Metadata();
		metadata.setName(new ModuleName(mavenProject.getGroupId(), mavenProject.getArtifactId(), true));
		metadata.setVersion(Version.create(mavenProject.getVersion()));
		metadata.setSummary(mavenProject.getName());

		Scm scm = mavenProject.getScm();
		if(scm != null)
			metadata.setSource(scm.getUrl());

		metadata.setProjectPage(mavenProject.getUrl());

		List<Dependency> forgeDeps = null;
		for(org.apache.maven.model.Dependency dep : mavenProject.getDependencies()) {
			if("test".equals(dep.getScope()))
				continue;

			try {
				org.apache.maven.artifact.versioning.VersionRange vr = org.apache.maven.artifact.versioning.VersionRange.createFromVersionSpec(dep.getVersion());
				List<Restriction> restrictions = vr.getRestrictions();
				if(restrictions.size() == 1) {
					Restriction r = restrictions.get(0);
					Version lower = r.getLowerBound() == null
							? Version.MIN
							: Version.create(r.getLowerBound().toString());
					Version upper = r.getUpperBound() == null
							? Version.MAX
							: Version.create(r.getUpperBound().toString());

					Dependency forgeDep = new Dependency();
					forgeDep.setName(new ModuleName(dep.getGroupId(), dep.getArtifactId(), true));
					forgeDep.setVersionRequirement(VersionRange.create(
						lower, r.isLowerBoundInclusive(), upper, r.isUpperBoundInclusive()));

					if(forgeDeps == null)
						forgeDeps = new ArrayList<Dependency>();
					forgeDeps.add(forgeDep);
				}
			}
			catch(InvalidVersionSpecificationException e) {
				result.addChild(new FileDiagnostic(
					Diagnostic.WARNING, ValidationService.GEPPETTO, e.getMessage(), mavenProject.getFile()));
			}
		}
		metadata.setDependencies(forgeDeps);
		return metadata;
	}
}
