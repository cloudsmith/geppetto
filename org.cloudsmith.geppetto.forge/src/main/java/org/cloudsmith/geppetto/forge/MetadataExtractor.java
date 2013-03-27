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
package org.cloudsmith.geppetto.forge;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.cloudsmith.geppetto.forge.v2.model.Metadata;

/**
 * The default implementation of this class will extract metadata from a file named <tt>Modulefile</tt> which is expected to be in ruby format.
 */
public interface MetadataExtractor {
	/**
	 * Checks if the files needed to extract metadata are present.
	 * 
	 * @param moduleDirectory
	 * @param filter
	 *            The filter that is used by the scan.
	 * @return <tt>true</tt> to indicate that this extractor will be able to extract metadata
	 */
	boolean canExtractFrom(File moduleDirectory, FileFilter filter);

	/**
	 * Determines the order in which extractors will be consulted. The "metadata.json" extractor
	 * will have a cardinal of zero, the Modulefile extractor has a cardinal of 10.
	 * Please allow for gaps when implementing new extractors.
	 * 
	 * @return The cardinal for this extractor.
	 */
	int getCardinal();

	/**
	 * Returns the relative path of the file that this extractor will attempt to use as the primary source
	 * for metadata extraction.
	 * 
	 * @return The abstract file name, relative to the expected module directory.
	 */
	String getPrimarySource();

	/**
	 * Extracts metadata from the given module.
	 * 
	 * @param moduleDir
	 *            The module directory
	 * @param includeTypesAndChecksums
	 *            If set, analyze all types in and generated checksums
	 * @param filter
	 *            The filter that is used by the scan.
	 * @param extractedFrom
	 *            A one element File array that will receive the file that the metadata was extracted from.
	 *            Can be <tt>null</tt> when that piece of information is of no interest
	 * @return The extracted metadata
	 * @throws IOException
	 *             if extraction failed
	 */
	Metadata parseMetadata(File moduleDir, boolean includeTypesAndChecksums, FileFilter filter, File[] extractedFrom)
			throws IOException;
}
