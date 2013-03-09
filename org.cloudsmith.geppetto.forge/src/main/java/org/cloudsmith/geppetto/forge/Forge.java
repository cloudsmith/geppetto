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
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.client.ForgeException;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.semver.VersionRange;

/**
 * This class basically mimics the PMT (Puppet Module Tool)
 */
public interface Forge {
	/**
	 * Build a module for release. The end result is a gzipped tar file (.tar.gz) archive that
	 * contains the module source and a freshly generated metadata.json. This method
	 * will replace the metadata.json that resides at the root of the module source
	 * if it is already present.
	 * 
	 * @param moduleSource
	 *            The module directory
	 * @param destination
	 *            The directory where the created archive will end up. Created if necessary.
	 * @param exclusionFilter
	 *            A filter used for excluding files. Can be <tt>null</tt> to use the default {@link ModuleUtils#DEFAULT_FILE_FILTER}.
	 * @param resultingMetadata
	 *            A one element array that will receive the resulting metadata. Can be <tt>null</tt>.
	 * @return The resulting gzipped tar file.
	 */
	File build(File moduleSource, File destination, FileFilter exclusionFilter, Metadata[] resultingMetadata)
			throws IOException, IncompleteException;

	/**
	 * List modified files in an installed module
	 * 
	 * @param path
	 *            The module directory
	 * @param exclusionFilter
	 *            A filter used for excluding files. Can be <tt>null</tt> to use the default {@link ModuleUtils#DEFAULT_FILE_FILTER}.
	 */
	List<File> changes(File path, FileFilter exclusionFilter) throws IOException;

	/**
	 * Create a Metadata instance from a module structure. If a file named &quot;metadata.json&quot; exists
	 * in the <tt>moduleDirectory</tt> then that file will be the sole source of input. Otherwise, this method
	 * looks for a file named &quot;Modulefile&quot;, reads that, and then reads puppet types from the directory
	 * &quot;lib/puppet&quot;. The method then ends by calculating checksums.
	 * 
	 * @param moduleDirectory
	 *            The directory containing the module
	 * @param fromExistingMetadata
	 *            A one element boolean array that will receive a flag indicating whether or
	 *            not the returned metadata was from an existing &quot;metadata.json&quot;. Can be <tt>null</tt> when that piece of information
	 *            is of no interest
	 * @param exclusionFilter
	 *            A filter used for excluding files. Can be <tt>null</tt> to use the default {@link ModuleUtils#DEFAULT_FILE_FILTER}.
	 * @return The created metadata
	 * @throws IOException
	 */
	Metadata createFromModuleDir(File moduleDirectory, boolean[] fromExistingMetadata, FileFilter exclusionFilter)
			throws IOException;

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
	List<File> downloadDependencies(Iterable<Metadata> metadatas, File importedModulesDir, Diagnostic result)
			throws IOException;

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
	 * @param builtModule
	 *            The packaged module file in gzipped tar format
	 * @return The metadata or null if no metadata was present in the file
	 */
	Metadata getModuleMetadataFromPackage(File builtModule) throws IOException;

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
	 * Load metadata from a JSON file
	 * 
	 * @param jsonFile
	 *            The file containing the JSON representation
	 * @return The resulting metadata
	 * @throws IOException
	 */
	Metadata loadJSONMetadata(File jsonFile) throws IOException;

	/**
	 * Parse a Modulefile into a {@link Metadata} instance.
	 * 
	 * @param modulefile
	 *            The file to parse
	 * @return The resulting metadata
	 * @throws IOException
	 */
	Metadata parseModuleFile(File modulefile) throws IOException;

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
	 * @param builtModulesDir
	 *            The directory where the modules to be published reside
	 * @param dryRun
	 *            Set to <tt>true</tt> if all but the final step of sending to the Forge should be made
	 * @param result
	 *            The collector diagnostic.
	 */
	void publishAll(File builtModulesDir, boolean dryRun, Diagnostic result);

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
