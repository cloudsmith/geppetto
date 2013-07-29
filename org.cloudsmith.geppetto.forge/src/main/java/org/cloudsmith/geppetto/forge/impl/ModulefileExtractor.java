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
package org.cloudsmith.geppetto.forge.impl;

import static org.cloudsmith.geppetto.forge.Forge.MODULEFILE_NAME;

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
		return MODULEFILE_NAME;
	}

	@Override
	protected Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException {
		Metadata metadata = new Metadata();
		ModuleUtils.parseModulefile(existingFile, metadata, result);
		return metadata;
	}
}
