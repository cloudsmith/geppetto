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
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.forge.util.Types;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public abstract class AbstractMetadataExtractor implements MetadataExtractor {
	@Inject
	@Named(ForgeModule.MODULE_FILE_FILTER)
	private FileFilter fileFilter;

	@Override
	public boolean canExtractFrom(File moduleDirectory) {
		return new File(moduleDirectory, getPrimarySource()).exists();
	}

	@Override
	public Metadata parseMetadata(File moduleDirectory, boolean includeTypesAndChecksums, File[] extractedFrom)
			throws IOException {
		File metadataFile = new File(moduleDirectory, getPrimarySource());
		if(!metadataFile.exists())
			throw new FileNotFoundException(metadataFile.getAbsolutePath());

		Metadata md = performMetadataExtraction(metadataFile);
		if(getCardinal() > 0 && includeTypesAndChecksums) {
			md.setTypes(Types.loadTypes(new File(moduleDirectory, "lib/puppet"), fileFilter));
			md.setChecksums(Checksums.loadChecksums(moduleDirectory, fileFilter));
		}
		if(extractedFrom != null)
			extractedFrom[0] = metadataFile;
		return md;
	}

	protected abstract Metadata performMetadataExtraction(File existingFile) throws IOException;
}
