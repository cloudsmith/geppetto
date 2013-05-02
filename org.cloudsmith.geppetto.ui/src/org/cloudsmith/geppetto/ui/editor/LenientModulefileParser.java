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
	protected void call(CallSymbol key, SourcePosition pos, List<Argument> arguments) {
		int nargs = arguments.size();
		ArgSticker[] args = new ArgSticker[nargs];
		for(int idx = 0; idx < nargs; ++idx)
			args[idx] = new ArgSticker(arguments.get(idx));
		model.addCall(key, new CallSticker(pos.getStartOffset(), pos.getEndOffset() - pos.getStartOffset(), args));
	}
}
