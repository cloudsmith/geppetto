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
package com.puppetlabs.geppetto.forge.v3.repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.MetadataRepository;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v3.ForgeService.ProgressMonitor;
import com.puppetlabs.geppetto.forge.v3.ForgeService.Visitor;
import com.puppetlabs.geppetto.forge.v3.Releases;
import com.puppetlabs.geppetto.forge.v3.model.Release;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 * Installs modules from a list of dependencies
 */
@Singleton
public class MetadataRepositoryImpl implements MetadataRepository {

	private static class Resolution {
		/**
		 * Version requirements that this solution must fulfill
		 */
		private final Set<VersionRange> versionRequirements;

		/**
		 * Resolutions that matches all requirements
		 */
		private final Collection<Metadata> releases;

		Resolution(Set<VersionRange> versionRequirements, Collection<Metadata> releases) {
			this.versionRequirements = versionRequirements;
			this.releases = releases;
		}

		Resolution(VersionRange vReq, Collection<Metadata> releases) {
			this(Collections.singleton(vReq), releases);
		}

		void addIfAllMatch(Set<VersionRange> allRequirements, Map<Version, Metadata> relMap,
				Collection<Dependency> addedDependenciesCollector) {
			nextRelease: for(Metadata r : releases) {
				Version v = r.getVersion();
				for(VersionRange vr : allRequirements)
					if(!vr.isIncluded(v))
						continue nextRelease;

				if(addedDependenciesCollector != null) {
					Collection<Dependency> allDeps = collectAllDependencies(relMap.values());
					nextDep: for(Dependency dep : r.getDependencies()) {
						for(Dependency haveDep : allDeps) {
							if(haveDep.getName().equals(dep.getName()) &&
									haveDep.getVersionRequirement().isAsRestrictiveAs(dep.getVersionRequirement()))
								continue nextDep;
						}
						addedDependenciesCollector.add(dep);
					}
				}
				relMap.put(v, r);
			}
		}

		/**
		 * @param releases
		 * @return
		 */
		Collection<Dependency> collectAllDependencies(Collection<Metadata> releases) {
			Set<Dependency> deps = new HashSet<Dependency>();
			for(Metadata r : releases)
				deps.addAll(r.getDependencies());
			return deps;
		}

		boolean conflictsWith(VersionRange vr) {
			for(VersionRange vrn : versionRequirements)
				if(vrn.intersect(vr) == null)
					return true;
			return false;
		}

		Metadata getBestMatch() {
			Metadata bestMatch = null;
			for(Metadata release : releases)
				if(bestMatch == null || release.getVersion().compareTo(bestMatch.getVersion()) > 0)
					bestMatch = release;
			return bestMatch;
		}

		Collection<Dependency> getDependencies() {
			HashSet<Dependency> dependencies = new HashSet<Dependency>();
			collectAllDependencies(releases);
			return dependencies;
		}

		boolean isAsRestrictiveAs(VersionRange vr) {
			for(VersionRange vrn : versionRequirements)
				if(vrn.isAsRestrictiveAs(vr))
					return true;
			return false;
		}

		Resolution merge(Resolution other, Collection<Dependency> addedDependenciesCollector) {
			Set<VersionRange> allRequirements = new HashSet<VersionRange>();
			allRequirements.addAll(versionRequirements);
			allRequirements.addAll(other.versionRequirements);

			Map<Version, Metadata> relMap = new HashMap<Version, Metadata>();
			addIfAllMatch(allRequirements, relMap, null);
			other.addIfAllMatch(allRequirements, relMap, addedDependenciesCollector);
			Collection<Metadata> releases = relMap.values();
			return releases.isEmpty()
					? null
					: new Resolution(allRequirements, relMap.values());
		}
	}

	// TODO: Keeping everything in memory is of course not ideal. Should use a local
	// file cache or similar
	private final Map<ModuleName, Metadata[]> releasesPerModule = new HashMap<ModuleName, Metadata[]>();

	private static final Metadata[] emptyReleaseArray = new Metadata[0];

	@Inject
	Releases releases;

	public Collection<Metadata> deepResolve(Dependency dependency, Set<Dependency> unresolvedCollector)
			throws IOException {
		Map<ModuleName, Resolution> resolutionCollector = new HashMap<ModuleName, Resolution>();

		Set<Dependency> seen = new HashSet<Dependency>();
		resolve(dependency, seen, resolutionCollector, unresolvedCollector);
		List<Metadata> releases = new ArrayList<Metadata>(resolutionCollector.size());
		for(Resolution resolution : resolutionCollector.values())
			releases.add(resolution.getBestMatch());
		seen.clear();

		return prune(dependency, releases);
	}

	/**
	 * Prunes the collection <code>releases</code> so that it only contains releases that
	 * can be reached from the transitive scope rooted by <code>dependency</code>.
	 * 
	 * @param dependency
	 *            The root of the dependency scope
	 * @param releases
	 *            The collection of releases to prune
	 * @return The pruned collection of releases
	 */
	private Collection<Metadata> prune(Dependency dependency, Collection<Metadata> releases) {
		List<Metadata> pruned = new ArrayList<Metadata>(releases.size());
		Set<Dependency> seen = new HashSet<Dependency>();
		prune(dependency, seen, releases, pruned);
		return pruned;
	}

	private void prune(Dependency dependency, Set<Dependency> seen, Collection<Metadata> releases,
			Collection<Metadata> pruned) {
		if(!seen.add(dependency))
			return;

		for(Metadata release : releases) {
			if(dependency.matches(release)) {
				pruned.add(release);
				for(Dependency child : release.getDependencies())
					prune(child, seen, releases, pruned);
			}
		}
	}

	public Metadata[] refreshCache(ModuleName fullName) throws IOException {
		final List<Metadata> rlist = new ArrayList<Metadata>();
		try {
			releases.accept(new Releases.OfModule(fullName), null, false, new Visitor<Release>() {
				@Override
				public void visit(Release release, ProgressMonitor monitor) {
					rlist.add(release.getMetadata());
				}
			}, null);
		}
		catch(InvocationTargetException e) {
			// Never thrown
		}
		int sz = rlist.size();
		Metadata[] releaseArray = sz == 0
				? emptyReleaseArray
				: rlist.toArray(new Metadata[sz]);
		releasesPerModule.put(fullName, releaseArray);
		return releaseArray;
	}

	public Metadata resolve(Dependency dependency) throws IOException {
		VersionRange vReq = dependency.getVersionRequirement();
		Metadata[] candidates = releasesPerModule.get(dependency.getName());
		if(candidates == null)
			candidates = refreshCache(dependency.getName());

		int idx = candidates.length;
		while(--idx >= 0) {
			Metadata release = candidates[idx];
			if(vReq == null || vReq.isIncluded(release.getVersion()))
				return release;
		}
		return null;
	}

	private void resolve(Dependency dependency, Set<Dependency> seen, Map<ModuleName, Resolution> resolutionCollector,
			Set<Dependency> unresolvedCollector) throws IOException {

		if(!seen.add(dependency))
			return;

		Resolution resolution = resolutionCollector.get(dependency.getName());
		if(resolution != null) {
			if(resolution.isAsRestrictiveAs(dependency.getVersionRequirement()))
				// This dependency is already covered by the resolution
				return;

			if(resolution.conflictsWith(dependency.getVersionRequirement()))
				// We have a conflict on our hands
				unresolvedCollector.add(dependency);
		}

		Resolution newRel = resolveAll(dependency);
		if(newRel == null) {
			unresolvedCollector.add(dependency);
			return;
		}

		Collection<Dependency> addedDependencies = new HashSet<Dependency>();
		if(resolution != null) {
			newRel = resolution.merge(newRel, addedDependencies);
			if(newRel == null) {
				unresolvedCollector.add(dependency);
				return;
			}
		}
		else {
			addedDependencies = newRel.getDependencies();
			resolutionCollector.put(dependency.getName(), newRel);
		}
		for(Dependency childDep : addedDependencies)
			resolve(childDep, seen, resolutionCollector, unresolvedCollector);
	}

	@Override
	public Metadata resolve(ModuleName name, Version version) throws IOException {
		VersionRange vr = VersionRange.exact(version);
		Dependency dep = new Dependency();
		dep.setName(name);
		dep.setVersionRequirement(vr);
		return resolve(dep);
	}

	private Resolution resolveAll(Dependency dependency) throws IOException {
		VersionRange vReq = dependency.getVersionRequirement();
		Metadata[] candidates = releasesPerModule.get(dependency.getName());
		if(candidates == null)
			candidates = refreshCache(dependency.getName());
		int idx = candidates.length;
		ArrayList<Metadata> matchingReleases = null;
		while(--idx >= 0) {
			Metadata release = candidates[idx];
			if(vReq == null || vReq.isIncluded(release.getVersion())) {
				if(matchingReleases == null)
					matchingReleases = new ArrayList<Metadata>();
				matchingReleases.add(release);
			}
		}
		if(matchingReleases != null)
			return new Resolution(vReq, matchingReleases);
		return null;
	}
}
