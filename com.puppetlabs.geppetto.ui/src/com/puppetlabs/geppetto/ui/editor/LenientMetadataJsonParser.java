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

import org.cloudsmith.geppetto.forge.util.CallSymbol;
import org.cloudsmith.geppetto.forge.util.MetadataJsonParser;

public class LenientMetadataJsonParser extends MetadataJsonParser {
	private final MetadataModel model;

	public LenientMetadataJsonParser(MetadataModel model) {
		this.model = model;
	}

	@Override
	protected void call(CallSymbol key, int line, int offset, int length, List<JElement> arguments) {
		if(model == null)
			// Never mind. We're just validating
			return;

		int nargs = arguments.size();
		ArgSticker[] args = new ArgSticker[nargs];
		for(int idx = 0; idx < nargs; ++idx) {
			JElement elem = arguments.get(idx);
			args[idx] = new ArgSticker(elem.getOffset(), elem.getLength(), elem.getValue());
		}
		model.addCall(key, new CallSticker(offset, length, args));
	}

	@Override
	protected String getBadNameMessage(IllegalArgumentException e, boolean dependency) {
		return MetadataModel.getBadNameMessage(e, dependency);
	}
}
