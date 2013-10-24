/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.model.Release;
import com.puppetlabs.geppetto.semver.Version;

/**
 * @author thhal
 * 
 */
public interface Releases extends ForgeService<Release, VersionedName> {
	public static class DependencyOf extends Compare<Release> {
		public DependencyOf(VersionedName versionedName) {
			super("dependency_of", versionedName.toString());
		}
	}

	public static class OfModule extends Compare<Release> {
		public OfModule(ModuleName module) {
			super("module", module.withSeparator('-').toString());
		}
	}

	public static class OwnedBy extends Compare<Release> {
		public OwnedBy(String owner) {
			super("owner", owner);
		}
	}

	public static class WithVersion extends Compare<Release> {
		public WithVersion(Version version) {
			super("version", version.toString());
		}
	}

	public static final SortBy<Release> VERSION = new SortBy<Release>("version");

	public static final SortBy<Release> RELEASE_DATE = new SortBy<Release>("release_date");

	public static final SortBy<Release> MODULE = new SortBy<Release>("module");

	public static final SortBy<Release> DOWNLOAD_COUNT = new SortBy<Release>("download_count");
}
