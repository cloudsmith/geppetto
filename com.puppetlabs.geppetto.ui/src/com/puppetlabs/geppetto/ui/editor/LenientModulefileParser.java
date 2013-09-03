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
package org.cloudsmith.geppetto.ui.editor;

import java.util.List;

import org.cloudsmith.geppetto.forge.util.Argument;
import org.cloudsmith.geppetto.forge.util.CallSymbol;
import org.cloudsmith.geppetto.forge.util.ModulefileParser;
import org.jrubyparser.SourcePosition;

public class LenientModulefileParser extends ModulefileParser {
	private final MetadataModel model;

	public LenientModulefileParser(MetadataModel model) {
		this.model = model;
	}

	@Override
	protected void call(CallSymbol key, SourcePosition pos, List<Argument> args) {
		int nargs = args.size();
		switch(nargs) {
			case 1:
				switch(key) {
					case name: {
						String name = args.get(0).toStringOrNull();
						if(name != null) {
							setFullName(createModuleName(name, false, pos));
							setNameSeen();
						}
						break;
					}
					case version: {
						String ver = args.get(0).toStringOrNull();
						if(ver != null) {
							createVersion(ver, pos);
							setVersionSeen();
						}
						break;
					}
					case dependency:
						createDependency(args.get(0).toStringOrNull(), null, pos);
						break;
					case author:
					case description:
					case license:
					case project_page:
					case source:
					case summary:
						break;
					default:
						noResponse(key.name(), pos, 1);
						return;
				}
				break;
			case 2:
			case 3:
				if(key == CallSymbol.dependency) {
					createDependency(args.get(0).toStringOrNull(), args.get(1).toStringOrNull(), pos);
					if(nargs == 3)
						addWarning(pos, "Ignoring third argument to dependency");
					break;
				}
				// Fall through
			default:
				noResponse(key.name(), pos, nargs);
				return;
		}

		ArgSticker[] argStickers = new ArgSticker[nargs];
		for(int idx = 0; idx < nargs; ++idx)
			argStickers[idx] = new ArgSticker(args.get(idx));

		model.addCall(
			key, new CallSticker(pos.getStartOffset(), pos.getEndOffset() - pos.getStartOffset(), argStickers));
	}

	@Override
	protected String getBadNameMessage(IllegalArgumentException e, boolean dependency) {
		return MetadataModel.getBadNameMessage(e, dependency);
	}
}
