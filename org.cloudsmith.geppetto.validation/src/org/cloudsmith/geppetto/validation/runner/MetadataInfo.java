/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.validation.runner;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class MetadataInfo {
	public static class Resolution {
		public final Dependency dependency;

		public final MetadataInfo metadata;

		Resolution(Dependency d, MetadataInfo mi) {
			this.dependency = d;
			this.metadata = mi;
		}
	}

	private Metadata metadata;

	private File file;

	private List<Resolution> resolvedDependencies;

	private List<Dependency> unresolvedDependencies;

	private List<List<MetadataInfo>> circularities = Lists.newArrayList();

	private boolean roleFlag;

	public MetadataInfo(Metadata data, File f, boolean roleFlag) {
		this.metadata = data;
		this.file = f;
		this.resolvedDependencies = Lists.newArrayList();
		this.unresolvedDependencies = Lists.newArrayList();
		this.roleFlag = roleFlag;
	}

	/**
	 * @param circle
	 */
	public void addCircularity(List<MetadataInfo> circle) {
		circularities.add(ImmutableList.copyOf(Lists.reverse(circle)));
	}

	public void addResolvedDependency(Dependency d, MetadataInfo mi) {
		resolvedDependencies.add(new Resolution(d, mi));
	}

	/**
	 * @param d
	 */
	public void addUnresolvedDependency(Dependency d) {
		unresolvedDependencies.add(d);

	}

	public List<List<MetadataInfo>> getCircularities() {
		return Collections.unmodifiableList(circularities);
	}

	public File getFile() {
		return file;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Collection<Resolution> getResolvedDependencies() {
		return Collections.unmodifiableList(resolvedDependencies);
	}

	public Collection<Dependency> getUnresolvedDependencies() {
		return Collections.unmodifiableList(unresolvedDependencies);
	}

	/**
	 * Returns true if this Metadatainfo represents a puppet module describing a "role"
	 * 
	 * @return true if this instance represents a role.
	 */
	public boolean isRole() {
		return roleFlag;
	}
}
