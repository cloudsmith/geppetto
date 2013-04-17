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
package org.cloudsmith.geppetto.forge.impl;

import java.io.File;
import java.io.IOException;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

public class ModulefileExtractor extends AbstractMetadataExtractor {
	public int getCardinal() {
		return 10;
	}

	@Override
	public String getPrimarySource() {
		return "Modulefile";
	}

	@Override
	protected Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException {
		return ModuleUtils.parseModulefile(existingFile, result);
	}
}
