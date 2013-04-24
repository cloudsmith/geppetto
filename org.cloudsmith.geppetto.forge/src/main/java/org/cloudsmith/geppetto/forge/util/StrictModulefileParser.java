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
package org.cloudsmith.geppetto.forge.util;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.RootNode;

/**
 * A Modulefile parser that only accepts strict entries and adds them
 * to a Metadata instance
 */
public class StrictModulefileParser extends ModulefileParser {

	private final Metadata md;

	public StrictModulefileParser(Metadata md) {
		this.md = md;
	}

	private void addDependency(String value1, String value2) {
		Dependency dep = new Dependency();
		dep.setName(new ModuleName(value1));
		if(value2 != null)
			dep.setVersionRequirement(VersionRange.create(value2));
		md.getDependencies().add(dep);

	}

	@Override
	protected void call(String key, SourcePosition pos, String value, SourcePosition vp) {
		if("name".equals(key))
			try {
				md.setName(new ModuleName(value, true));
			}
			catch(IllegalArgumentException e) {
				// Try again, this time we allow uppercase letters
				md.setName(new ModuleName(value, false));
				addWarning(pos, e.getMessage());
			}
		else if("author".equals(key))
			md.setAuthor(value);
		else if("description".equals(key))
			md.setDescription(value);
		else if("license".equals(key))
			md.setLicense(value);
		else if("project_page".equals(key))
			md.setProjectPage(value);
		else if("source".equals(key))
			md.setSource(value);
		else if("summary".equals(key))
			md.setSummary(value);
		else if("version".equals(key))
			md.setVersion(Version.create(value));
		else if("dependency".equals(key))
			addDependency(value, null);
		else
			noResponse(key, pos, 1);
	}

	@Override
	protected void call(String key, SourcePosition pos, String value1, SourcePosition vp1, String value2,
			SourcePosition vp2) {
		if("dependency".equals(key))
			addDependency(value1, value2);
		else
			noResponse(key, pos, 2);
	}

	@Override
	protected void call(String key, SourcePosition pos, String value1, SourcePosition vp1, String value2,
			SourcePosition vp2, String value3, SourcePosition vp3) {
		if("dependency".equals(key))
			addDependency(value1, value2);
		else
			noResponse(key, pos, 3);
	}

	@Override
	public void parseRubyAST(RootNode root, Diagnostic diagnostics) {
		md.getDependencies().clear();
		md.getTypes().clear();
		md.getChecksums().clear();
		super.parseRubyAST(root, diagnostics);
	}
}
