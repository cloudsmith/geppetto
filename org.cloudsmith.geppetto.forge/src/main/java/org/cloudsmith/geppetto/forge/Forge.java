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
import java.util.Collection;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.DiagnosticType;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

/**
 * This class basically mimics the PMT (Puppet Module Tool)
 */
public interface Forge {
	public static final DiagnosticType FORGE = new DiagnosticType("FORGE", ForgeService.class.getName());

	public static final DiagnosticType PACKAGE = new DiagnosticType("PACKAGE", ForgeService.class.getName());

	public static final DiagnosticType PUBLISHER = new DiagnosticType("PUBLISHER", ForgeService.class.getName());

	public static final DiagnosticType PARSE_FAILURE = new DiagnosticType("PARSE_FAILURE", ForgeService.class.getName());

	public static final String MODULEFILE_NAME = "Modulefile";

	public static final String METADATA_JSON_NAME = "metadata.json";

	/**
	 * Name of injected file filter
	 */
	public static final String MODULE_FILE_FILTER = "module.file.filter";

	/**
	 * Name of optionally injected cache location
	 */
	public static final String CACHE_LOCATION = "forge.cache.location";

	/**
	 * Build a module for release. The end result is a gzipped tar file (.tar.gz) archive that
	 * contains the module source and a freshly generated metadata.json.
	 * 
	 * @param moduleSource
	 *            The module directory
	 * @param destination
	 *            The directory where the build will be performed and also where the created archive will end up
	 *            adjacent to the built module. Created if necessary.
	 * @param filter
	 *            The filter that is used for selecting the files. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link ForgeConstants#MODULE_FILE_FILTER}) will be used.
	 * @param resultingMetadata
	 *            A one element array that will receive the resulting metadata. Can be <tt>null</tt>.
	 * @param result
	 *            diagnostics generated during extraction
	 * @return The resulting gzipped tar file or <code>null</code> if extraction could not be performed. When that
	 *         happens, the result will contain
	 *         the reason.
	 * @throws IOException
	 */
	File build(File moduleSource, File destination, FileFilter filter, Metadata[] resultingMetadata, Diagnostic result)
			throws IOException;

	/**
	 * List modified files in an installed module
	 * 
	 * @param path
	 *            The module directory
	 * @param filter
	 *            The filter that is used by the scan. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link ForgeConstants#MODULE_FILE_FILTER}) will be used.
	 * @return A collection of modified files.
	 * @throws IOException
	 */
	Collection<File> changes(File path, FileFilter filter) throws IOException;

	/**
	 * Create a Metadata instance from a module structure. If a file named &quot;metadata.json&quot; exists
	 * in the <tt>moduleDirectory</tt> then that file will be the sole source of input. Otherwise, this method
	 * will consult injected {@link MetadataExtractor metadata extractors} to load the initial metadata,
	 * and then, unless <tt>includeTypesAndChecksums</tt> is <tt>false</tt> reads puppet types from the directory
	 * &quot;lib/puppet&quot; and generates checksums for all files.
	 * 
	 * @param moduleDirectory
	 *            The directory containing the module
	 * @param includeTypesAndChecksums
	 *            If set, analyze all types in and generated checksums
	 * @param filter
	 *            The filter that is used by the scan. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link ForgeConstants#MODULE_FILE_FILTER}) will be used.
	 * @param extractedFrom
	 *            A one element File array that will receive the file that the metadata was extracted from.
	 *            Can be <tt>null</tt> when that piece of information is of no interest
	 *            Can be <tt>null</tt> when that piece of information is of no interest
	 * @param result
	 *            diagnostics generated during extraction
	 * @return The extracted metadata
	 * @throws IOException
	 */
	Metadata createFromModuleDirectory(File moduleDirectory, boolean includeTypesAndChecksums, FileFilter filter,
			File[] extractedFrom, Diagnostic result) throws IOException;

	/**
	 * Scan for valid directories containing a &quot;metadata.json&quot; or other files recognized by injected
	 * {@link MetadataExtractor metadata extractors} using the provided <tt>filter</tt> to discriminate unwanted files.
	 * A directory that contains such a file will not be scanned in turn.
	 * 
	 * @param modulesRoot
	 *            The directory where the scan starts. Can be a module in itself.
	 * @param filter
	 *            The filter that is used for selecting the files. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link ForgeConstants#MODULE_FILE_FILTER}) will be used.
	 * @return A list of directories that seems to be module roots.
	 */
	Collection<File> findModuleRoots(File modulesRoot, FileFilter filter);

	/**
	 * Generate boilerplate for a new module
	 * 
	 * @param destination
	 *            The module directory
	 * @param metadata
	 *            The name of the module
	 * @throws IOException
	 */
	void generate(File destination, Metadata metadata) throws IOException;

	/**
	 * Extract metadata from a packaged module
	 * 
	 * @param gzippedTarball
	 *            The packaged module file in gzipped tar format
	 * @return The metadata or null if no metadata was present in the file
	 * @throws IOException
	 */
	Metadata getMetadataFromPackage(File gzippedTarball) throws IOException;

	/**
	 * Consults injected {@link MetadataExtractor metadata extractors} to check if metadata can
	 * be extracted from the given location.
	 * 
	 * @param moduleDirectory
	 * @param filter
	 *            The filter that is used for selecting the files. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link ForgeConstants#MODULE_FILE_FILTER}) will be used.
	 * @return <tt>true</tt> if a least one metadata extractor can extract metadata from the given location
	 */
	boolean hasModuleMetadata(File moduleDirectory, FileFilter filter);

	/**
	 * Checks if the file <tt>source</tt> is a file that one of the injected {@link MetadataExtractor metadata
	 * extractors} would consider.
	 * 
	 * @param source
	 *            The path to check. Should be relative to the expected module root
	 * @return <tt>true</tt> if the file would be used for metadata extraction
	 */
	boolean isMetadataFile(String source);

	/**
	 * Load metadata from a JSON file
	 * 
	 * @param jsonFile
	 *            The file containing the JSON representation
	 * @return The resulting metadata
	 * @throws IOException
	 */
	Metadata loadJSONMetadata(File jsonFile) throws IOException;

	/**
	 * Parse a Modulefile and create a Metadata instance from the result. The parser <i>will not evaluate</i> actual
	 * ruby code. It
	 * just parses the code and extracts values from the resulting AST.
	 * 
	 * 
	 * @param moduleFile
	 *            The file to parse
	 * @param result
	 *            Diagnostics collecting errors
	 * @return The resulting metadata
	 * @throws IOException
	 *             when it is not possible to read the <tt>modulefile</tt>.
	 * @throws IllegalArgumentException
	 *             if <tt>result</tt> is <tt>null</tt> and errors are detected in the file.
	 */
	Metadata loadModulefile(File moduleFile, Diagnostic result) throws IOException;

	/**
	 * Store the given metadata as JSON
	 * 
	 * @param md
	 *            The metadata to store
	 * @param jsonFile
	 *            The file to create
	 * @throws IOException
	 */
	void saveJSONMetadata(Metadata md, File jsonFile) throws IOException;

	/**
	 * Store the given <code>metadata</code> as a Modulefile (ruby format)
	 * 
	 * @param metadata
	 *            The metadata to store
	 * @param moduleFile
	 *            The file to create
	 * @throws IOException
	 */
	void saveModulefile(Metadata metadata, File moduleFile) throws IOException;
}
