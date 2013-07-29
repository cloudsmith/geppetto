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
package org.cloudsmith.geppetto.forge.util;

import java.util.List;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
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

	private void addDependency(String name, String versionRequirement, SourcePosition pos) {
		md.getDependencies().add(createDependency(name, versionRequirement, pos));
	}

	@Override
	protected void call(CallSymbol key, SourcePosition pos, List<Argument> args) {
		int nargs = args.size();
		switch(nargs) {
			case 1:
				String arg = args.get(0).toStringOrNull();
				switch(key) {
					case author:
						md.setAuthor(arg);
						break;
					case dependency:
						addDependency(arg, null, pos);
						break;
					case description:
						md.setDescription(arg);
						break;
					case license:
						md.setLicense(arg);
						break;
					case name: {
						ModuleName name = createModuleName(arg, false, pos);
						md.setName(name);
						setNameSeen();
						setFullName(name);
						break;
					}
					case project_page:
						md.setProjectPage(arg);
						break;
					case source:
						md.setSource(arg);
						break;
					case summary:
						md.setSummary(arg);
						break;
					case version:
						md.setVersion(createVersion(arg, pos));
						setVersionSeen();
						break;
					case dependencies:
						noResponse(key.name(), pos, 1);
				}
				break;
			case 2:
			case 3:
				if(key == CallSymbol.dependency) {
					addDependency(args.get(0).toStringOrNull(), args.get(1).toStringOrNull(), pos);
					if(nargs == 3)
						addWarning(pos, "Ignoring third argument to dependency");
					break;
				}
				// Fall through
			default:
				noResponse(key.name(), pos, 0);
		}
	}

	@Override
	public void parseRubyAST(RootNode root, Diagnostic diagnostics) {
		md.getDependencies().clear();
		md.getTypes().clear();
		md.getChecksums().clear();
		super.parseRubyAST(root, diagnostics);
	}
}
