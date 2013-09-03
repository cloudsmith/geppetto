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
package org.cloudsmith.geppetto.forge;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

/**
 * The default implementation of this class will extract metadata from a file named <tt>Modulefile</tt> which is
 * expected to be in ruby format.
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
	 * Determines the order in which extractors will be consulted. The &quot;metadata.json&quot; extractor
	 * will have a cardinal of 20, the Modulefile extractor has a cardinal of 10.
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
	 * The &quot;metadata.json&quot; extractor will responde <code>true</code> to this method. The
	 * &quot;Modulefile&quot; extractor will respond <code>false</code> since that file doesn't contain
	 * information about types and providers.
	 * 
	 * @return <code>true</code> to denote that the primary source for extraction contains type and provider
	 *         information.
	 */
	boolean hasTypesAndProviders();

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
	 * @param result
	 *            diagnostics generated during extraction
	 * @return The extracted metadata or <code>null</code> if extraction could not be performed. When that happens, the
	 *         result will contain the
	 *         reason.
	 * @throws IOException
	 *             if extraction failed
	 */
	Metadata parseMetadata(File moduleDir, boolean includeTypesAndChecksums, FileFilter filter, File[] extractedFrom,
			Diagnostic result) throws IOException;
}
