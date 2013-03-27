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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.v2.client.ForgeException;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.semver.VersionRange;

import com.google.inject.name.Named;

/**
 * This class basically mimics the PMT (Puppet Module Tool)
 */
public interface Forge {
	/**
	 * Name of injected file filter
	 */
	String MODULE_FILE_FILTER = "module.file.filter";

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
	 *            filter annotated by {@link Named @Named}({@link #MODULE_FILE_FILTER}) will be used.
	 * @param resultingMetadata
	 *            A one element array that will receive the resulting metadata. Can be <tt>null</tt>.
	 * @return The resulting gzipped tar file.
	 */
	File build(File moduleSource, File destination, FileFilter filter, Metadata[] resultingMetadata)
			throws IOException, IncompleteException;

	/**
	 * List modified files in an installed module
	 * 
	 * @param path
	 *            The module directory
	 * @param filter
	 *            The filter that is used by the scan. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link #MODULE_FILE_FILTER}) will be used.
	 * @return A collection of modified files.
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
	 *            filter annotated by {@link Named @Named}({@link #MODULE_FILE_FILTER}) will be used.
	 * @param extractedFrom
	 *            A one element File array that will receive the file that the metadata was extracted from.
	 *            Can be <tt>null</tt> when that piece of information is of no interest
	 * @return The created metadata
	 * @throws IOException
	 */
	Metadata createFromModuleDirectory(File moduleDirectory, boolean includeTypesAndChecksums, FileFilter filter,
			File[] extractedFrom) throws IOException;

	/**
	 * Downloads and installs all dependencies extending from the modules described by <tt>metadatas</tt>.
	 * 
	 * @param metadatas
	 *            The dependencies to resolve
	 * @param importedModulesDir
	 *            The directory where the dependent modules will be installed
	 * @return A list files appointing the installed modules.
	 * @throws IOException
	 */
	Collection<File> downloadDependencies(Iterable<Metadata> metadatas, File importedModulesDir, Diagnostic result)
			throws IOException;

	/**
	 * Scan for valid directories containing a "metadata.json" or other files recognized by injected {@link MetadataExtractor metadata extractors}
	 * using the provided <tt>filter</tt> to discriminate unwanted files. A directory that contains such a file will not be scanned in turn.
	 * 
	 * @param modulesRoot
	 *            The directory where the scan starts. Can be a module in itself.
	 * @param filter
	 *            The filter that is used for selecting the files. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link #MODULE_FILE_FILTER}) will be used.
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
	 */
	void generate(File destination, Metadata metadata) throws IOException;

	/**
	 * Extract metadata from a packaged module
	 * 
	 * @param gzippedTarball
	 *            The packaged module file in gzipped tar format
	 * @return The metadata or null if no metadata was present in the file
	 */
	Metadata getMetadataFromPackage(File gzippedTarball) throws IOException;

	/**
	 * Consults injected {@link MetadataExtractor metadata extractors} to check if metadata can
	 * be extracted from the given location.
	 * 
	 * @param moduleDirectory
	 * @param filter
	 *            The filter that is used for selecting the files. Can be null in which case the injected
	 *            filter annotated by {@link Named @Named}({@link #MODULE_FILE_FILTER}) will be used.
	 * @return <tt>true</tt> if a least one metadata extractor can extract metadata from the given location
	 */
	boolean hasModuleMetadata(File moduleDirectory, FileFilter filter);

	/**
	 * Install a module (eg, 'user-modname') from the Forge repository. A
	 * module is an archive that contains one single folder. In some cases,
	 * like when installing into a pre-existing workspace project, it's
	 * desirable to skip this folder and instead expand everything beneath
	 * it into the given <code>destination</code>. This behavior can be
	 * enforced by setting the <code>destinationIncludesTopFolder</code> to <code>true</code>.
	 * 
	 * @param fullName
	 *            The name of the module
	 * @param range
	 *            version constraint to apply when selecting the module release. Can be <code>null</code> in which case the release with the highest
	 *            version wins
	 * @param destination
	 *            The destination for the install.
	 * @param destinationIncludesTopFolder
	 *            When <code>true</code>, assume that all content beneath the
	 *            top folder in the archive should be installed directly beneath the
	 *            given <code>destination</code>. When this flag is <code>false</code> the top folder of the archive will be expanded as-is beneath
	 *            the <code>destination</code>.
	 * @param force
	 *            Set to <code>true</code> to overwrite an existing module.
	 */
	Metadata install(ModuleName fullName, VersionRange range, File destination, boolean destinationIncludesTopFolder,
			boolean force) throws IOException;

	/**
	 * Install a specific release of a module from the Forge repository. A
	 * module is an archive that contains one single folder. In some cases,
	 * like when installing into a pre-existing workspace project, it's
	 * desirable to skip this folder and instead expand everything beneath
	 * it into the given <code>destination</code>. This behavior can be
	 * enforced by setting the <code>destinationIncludesTopFolder</code> to <code>true</code>.
	 * 
	 * @param release
	 *            The module release
	 * @param destination
	 *            The destination for the install.
	 * @param destinationIncludesTopFolder
	 *            When <code>true</code>, assume that all content beneath the
	 *            top folder in the archive should be installed directly beneath the
	 *            given <code>destination</code>. When this flag is <code>false</code> the top folder of the archive will be expanded as-is beneath
	 *            the <code>destination</code>.
	 * @param force
	 *            Set to <code>true</code> to overwrite an existing module.
	 */
	Metadata install(Release release, File destination, boolean destinationIncludesTopFolder, boolean force)
			throws IOException;

	/**
	 * Checks if the file <tt>source</tt> is a file that one of the injected {@link MetadataExtractor metadata extractors} would consider.
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
	 * Publish a gzipped module tarball to the Forge. The provided diagnostic is used for informational messages
	 * only. Any errors will yield an exception.
	 * 
	 * @param moduleTarball
	 *            The gzipped tarball
	 *            Set to <tt>true</tt> if all but the final step of sending to the Forge should be made
	 * @param result
	 *            The collector diagnostic.
	 * @throws AlreadyPublishedException
	 *             if the module is found on the forge at its current version prior to publishing
	 * @throws ForgeException
	 *             on communication errors with the forge
	 * @throws IOException
	 */
	void publish(File moduleTarball, boolean dryRun, Diagnostic diagnostic) throws ForgeException, IOException;

	/**
	 * Publish all gzipped module tarballs found under <tt>builtModulesDir</tt>. Report progress on the
	 * provided <tt>result</tt> diagnostic. The caller must check the severity of the <tt>result</tt> after this call has completed.
	 * 
	 * @param moduleTarballs
	 *            Module tarballs to be published.
	 * @param dryRun
	 *            Set to <tt>true</tt> if all but the final step of sending to the Forge should be made
	 * @param result
	 *            The collector diagnostic.
	 */
	void publishAll(File[] moduleTarballs, boolean dryRun, Diagnostic result);

	/**
	 * Resolves all dependencies extending from the modules described by <tt>metadatas</tt>.
	 * 
	 * @param metadatas
	 *            The dependencies to resolve
	 * @param unresolvedCollector
	 *            A collector where unresolved dependencies, if any, will be added.
	 * @return A set of releases that constitutes the successful part of the resolution
	 * @throws IOException
	 */
	Set<Release> resolveDependencies(Iterable<Metadata> metadatas, Set<Dependency> unresolvedCollector)
			throws IOException;

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
	 * Search the module repository for a module matching <code>term</code>
	 * 
	 * @param term
	 *            Search term
	 */
	List<Module> search(String term) throws IOException;
}
