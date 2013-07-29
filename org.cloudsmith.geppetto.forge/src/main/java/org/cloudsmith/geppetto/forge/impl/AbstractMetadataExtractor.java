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

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.forge.util.Types;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

public abstract class AbstractMetadataExtractor implements MetadataExtractor {
	@Override
	public boolean canExtractFrom(File moduleDirectory, FileFilter filter) {
		File mdSource = new File(moduleDirectory, getPrimarySource());
		return filter.accept(mdSource) && mdSource.exists();
	}

	@Override
	public boolean hasTypesAndProviders() {
		return false;
	}

	@Override
	public Metadata parseMetadata(File moduleDirectory, boolean includeTypesAndChecksums, FileFilter filter,
			File[] extractedFrom, Diagnostic result) throws IOException {
		File metadataFile = new File(moduleDirectory, getPrimarySource());
		if(extractedFrom != null)
			extractedFrom[0] = metadataFile;

		if(!canExtractFrom(moduleDirectory, filter))
			throw new FileNotFoundException(metadataFile.getAbsolutePath());

		Metadata md = performMetadataExtraction(metadataFile, result);
		if(md != null && !hasTypesAndProviders() && includeTypesAndChecksums) {
			md.setTypes(Types.loadTypes(new File(moduleDirectory, "lib/puppet"), filter));
			md.setChecksums(Checksums.loadChecksums(moduleDirectory, filter));
		}
		return md;
	}

	protected abstract Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException;
}
