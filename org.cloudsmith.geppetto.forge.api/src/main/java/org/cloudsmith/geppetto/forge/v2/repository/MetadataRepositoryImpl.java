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
package org.cloudsmith.geppetto.forge.v2.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.MatchRule;
import org.cloudsmith.geppetto.forge.v2.model.QName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.VersionRequirement;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;

import com.google.inject.Inject;

/**
 * Installs modules from a list of dependencies
 */
public class MetadataRepositoryImpl implements MetadataRepository {

	private static class Resolution {
		/**
		 * Version requirements that this solution must fulfill
		 */
		private final Set<VersionRequirement> versionRequirements;

		/**
		 * Resolutions that matches all requirements
		 */
		private final Collection<Release> releases;

		Resolution(Set<VersionRequirement> versionRequirements, Collection<Release> releases) {
			this.versionRequirements = versionRequirements;
			this.releases = releases;
		}

		Resolution(VersionRequirement vReq, Collection<Release> releases) {
			this(Collections.singleton(vReq), releases);
		}

		void addIfAllMatch(Set<VersionRequirement> allRequirements, Map<String, Release> relMap,
				Collection<Dependency> addedDependenciesCollector) {
			nextRelease: for(Release r : releases) {
				String v = r.getVersion();
				for(VersionRequirement vr : allRequirements)
					if(!vr.matches(v))
						continue nextRelease;

				if(addedDependenciesCollector != null) {
					Collection<Dependency> allDeps = collectAllDependencies(relMap.values());
					nextDep: for(Dependency dep : r.getMetadata().getDependencies()) {
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
		Collection<Dependency> collectAllDependencies(Collection<Release> releases) {
			Set<Dependency> deps = new HashSet<Dependency>();
			for(Release r : releases)
				deps.addAll(r.getMetadata().getDependencies());
			return deps;
		}

		boolean conflictsWith(VersionRequirement vr) {
			for(VersionRequirement vrn : versionRequirements)
				if(vrn.conflictsWith(vr))
					return true;
			return false;
		}

		Release getBestMatch() {
			Release bestMatch = null;
			for(Release release : releases)
				if(bestMatch == null ||
						VersionRequirement.VERSION_COMPARATOR.compare(release.getVersion(), bestMatch.getVersion()) > 0)
					bestMatch = release;
			return bestMatch;
		}

		Collection<Dependency> getDependencies() {
			HashSet<Dependency> dependencies = new HashSet<Dependency>();
			collectAllDependencies(releases);
			return dependencies;
		}

		boolean isAsRestrictiveAs(VersionRequirement vr) {
			for(VersionRequirement vrn : versionRequirements)
				if(vrn.isAsRestrictiveAs(vr))
					return true;
			return false;
		}

		Resolution merge(Resolution other, Collection<Dependency> addedDependenciesCollector) {
			Set<VersionRequirement> allRequirements = new HashSet<VersionRequirement>();
			allRequirements.addAll(versionRequirements);
			allRequirements.addAll(other.versionRequirements);

			Map<String, Release> relMap = new HashMap<String, Release>();
			addIfAllMatch(allRequirements, relMap, null);
			other.addIfAllMatch(allRequirements, relMap, addedDependenciesCollector);
			Collection<Release> releases = relMap.values();
			return releases.isEmpty()
					? null
					: new Resolution(allRequirements, relMap.values());
		}
	}

	// TODO: Keeping everything in memory is of course not ideal. Should use a local
	// file cache or similar
	private final Map<QName, Release[]> releasesPerModule = new HashMap<QName, Release[]>();

	@Inject
	private ReleaseService releaseService;

	@Inject
	private ModuleService moduleService;

	private static final Release[] emptyReleaseArray = new Release[0];

	public Collection<Release> deepResolve(Dependency dependency, Set<Dependency> unresolvedCollector)
			throws IOException {
		Map<QName, Resolution> resolutionCollector = new HashMap<QName, Resolution>();

		Set<Dependency> seen = new HashSet<Dependency>();
		resolve(dependency, seen, resolutionCollector, unresolvedCollector);
		List<Release> releases = new ArrayList<Release>(resolutionCollector.size());
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
	private Collection<Release> prune(Dependency dependency, Collection<Release> releases) {
		List<Release> pruned = new ArrayList<Release>(releases.size());
		Set<Dependency> seen = new HashSet<Dependency>();
		prune(dependency, seen, releases, pruned);
		return pruned;
	}

	private void prune(Dependency dependency, Set<Dependency> seen, Collection<Release> releases,
			Collection<Release> pruned) {
		if(!seen.add(dependency))
			return;

		for(Release release : releases) {
			if(dependency.matches(release)) {
				pruned.add(release);
				for(Dependency child : release.getMetadata().getDependencies())
					prune(child, seen, releases, pruned);
			}
		}
	}

	public Release[] refreshCache(QName fullName) throws IOException {
		String owner = fullName.getQualifier();
		String name = fullName.getName();
		Release[] releaseArray;
		try {
			List<Release> releases = moduleService.getReleases(owner, name, null);
			releaseArray = releases.toArray(new Release[releases.size()]);
		}
		catch(FileNotFoundException e) {
			releaseArray = emptyReleaseArray;
		}
		releasesPerModule.put(fullName, releaseArray);
		return releaseArray;
	}

	public Release resolve(Dependency dependency) throws IOException {
		VersionRequirement vReq = dependency.getVersionRequirement();
		Release[] candidates = releasesPerModule.get(dependency.getName());
		if(candidates == null)
			candidates = refreshCache(dependency.getName());

		int idx = candidates.length;
		while(--idx >= 0) {
			Release release = candidates[idx];
			if(vReq == null || vReq.matches(release.getVersion()))
				return release;
		}
		return null;
	}

	private void resolve(Dependency dependency, Set<Dependency> seen, Map<QName, Resolution> resolutionCollector,
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
	public Release resolve(QName name, String version) throws IOException {
		VersionRequirement vr = new VersionRequirement();
		vr.setMatchRule(MatchRule.PERFECT);
		vr.setVersion(version);
		Dependency dep = new Dependency();
		dep.setName(name);
		dep.setVersionRequirement(vr);
		return resolve(dep);
	}

	public Resolution resolveAll(Dependency dependency) throws IOException {
		VersionRequirement vReq = dependency.getVersionRequirement();
		Release[] candidates = releasesPerModule.get(dependency.getName());
		if(candidates == null)
			candidates = refreshCache(dependency.getName());
		int idx = candidates.length;
		ArrayList<Release> matchingReleases = null;
		while(--idx >= 0) {
			Release release = candidates[idx];
			if(vReq == null || vReq.matches(release.getVersion())) {
				if(matchingReleases == null)
					matchingReleases = new ArrayList<Release>();
				matchingReleases.add(release);
			}
		}
		if(matchingReleases != null)
			return new Resolution(vReq, matchingReleases);
		return null;
	}
}
