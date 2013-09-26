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
package com.puppetlabs.geppetto.forge.impl;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.ERB;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.MetadataExtractor;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.util.Checksums;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;
import com.puppetlabs.geppetto.forge.util.TarUtils;
import com.puppetlabs.geppetto.forge.util.TarUtils.FileCatcher;
import com.puppetlabs.geppetto.semver.Version;

@Singleton
class ForgeImpl implements Forge {
	@Inject
	private ERB erb;

	@Inject
	private Gson gson;

	@Inject
	@Named(MODULE_FILE_FILTER)
	private FileFilter moduleFileFilter;

	@Inject
	Set<MetadataExtractor> metadataExtractors;

	private static Comparator<MetadataExtractor> extractorComparator = new Comparator<MetadataExtractor>() {
		@Override
		public int compare(MetadataExtractor a, MetadataExtractor b) {
			return a.getCardinal() - b.getCardinal();
		}
	};

	@Override
	public File build(File moduleSource, File destination, FileFilter fileFilter, Metadata[] resultingMetadata,
			File[] resultingMetadataFile, Diagnostic result) throws IOException {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;

		File[] extractedFrom = new File[1];

		Metadata md = createFromModuleDirectory(moduleSource, true, fileFilter, extractedFrom, result);
		if(result.getSeverity() >= ERROR)
			return null;

		if(resultingMetadata != null)
			resultingMetadata[0] = md;

		if(md == null)
			// Metadata could not be read. Errors are in result
			return null;

		for(File tst = destination; tst != null; tst = tst.getParentFile()) {
			if(fileFilter.accept(tst))
				// Destination folder might reside inside of the module when it
				// has been excluded.
				break;

			if(tst.equals(moduleSource))
				throw new IllegalArgumentException("Destination cannot reside within the module itself");
		}

		ModuleName fullName = md.getName();
		Version ver = md.getVersion();
		if(fullName == null || ver == null)
			// Reason has been added to the Diagnostic result
			return null;

		/**
		 * Copy the module to the location where it's being built. Ensure that it's
		 * an empty location prior to that.
		 */
		StringBuilder bld = new StringBuilder();
		ModuleUtils.buildFileName(fullName, ver, bld);
		String fullNameWithVersion = bld.toString();
		bld.append(".tar.gz");
		String zipArchiveName = bld.toString();

		File destModuleDir = new File(destination, fullNameWithVersion);
		if(!destination.mkdirs())
			FileUtils.rmR(destModuleDir);

		FileUtils.cpR(moduleSource, destModuleDir, fileFilter, false, true);

		File metadataJSON = new File(destModuleDir, METADATA_JSON_NAME);
		if(!extractedFrom[0].getName().equals(METADATA_JSON_NAME))
			internalSaveJSONMetadata(md, metadataJSON);

		if(resultingMetadataFile != null)
			resultingMetadataFile[0] = metadataJSON;

		final File moduleArchive = new File(destination, zipArchiveName);
		OutputStream out = new GZIPOutputStream(new FileOutputStream(moduleArchive));

		// Pack closes its output
		TarUtils.pack(destModuleDir, out, null, true, null);

		return moduleArchive;
	}

	@Override
	public List<File> changes(File path, FileFilter fileFilter) throws IOException {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;
		Metadata md = loadJSONMetadata(new File(path, METADATA_JSON_NAME));
		List<File> result = new ArrayList<File>();
		Checksums.appendChangedFiles(md.getChecksums(), path, result, fileFilter);
		return result;
	}

	@Override
	public Metadata createFromModuleDirectory(File moduleDirectory, boolean includeTypesAndChecksums,
			FileFilter filter, File[] extractedFrom, Diagnostic result) throws IOException {

		if(filter == null)
			filter = moduleFileFilter;

		if(extractedFrom == null)
			extractedFrom = new File[1];

		Metadata md = null;
		for(MetadataExtractor extractor : getMetadataExtractors())
			if(extractor.canExtractFrom(moduleDirectory, filter)) {
				md = extractor.parseMetadata(moduleDirectory, includeTypesAndChecksums, filter, extractedFrom, result);
				break;
			}

		if(result == null)
			return md;

		if(md == null) {
			result.addChild(new Diagnostic(ERROR, FORGE, "No Module Metadata found in directory " +
					moduleDirectory.getAbsolutePath()));
			return null;
		}
		return md;
	}

	@Override
	public Collection<File> findModuleRoots(File modulesRoot, FileFilter fileFilter) {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;
		return ModuleUtils.findModuleRoots(modulesRoot, fileFilter, getMetadataExtractors());
	}

	@Override
	public void generate(File destination, Metadata metadata) throws IOException {
		if(!(destination.mkdirs() || destination.exists()))
			throw new IOException(destination + " could not be created");

		InputStream input = ForgeService.class.getResourceAsStream("/templates/generator.zip");
		if(input == null)
			throw new FileNotFoundException("Unable to find zipped template for generate");

		ZipInputStream template = new ZipInputStream(input);
		try {
			installTemplate(metadata, destination, template);
		}
		finally {
			template.close();
		}
	}

	public List<MetadataExtractor> getMetadataExtractors() {
		ArrayList<MetadataExtractor> extractors = new ArrayList<MetadataExtractor>(metadataExtractors);
		Collections.sort(extractors, extractorComparator);
		return extractors;
	}

	@Override
	public Metadata getMetadataFromPackage(File builtModule) throws IOException {
		InputStream input = new FileInputStream(builtModule);
		try {
			final Metadata[] mdReceiver = new Metadata[1];
			TarUtils.unpack(new GZIPInputStream(input), null, true, new FileCatcher() {
				@Override
				public boolean accept(String fileName) {
					return METADATA_JSON_NAME.equals(fileName);
				}

				@Override
				public boolean catchData(String fileName, InputStream fileData) {
					InputStreamReader reader = new InputStreamReader(fileData);
					mdReceiver[0] = gson.fromJson(reader, Metadata.class);
					return true; // We're done. Skip further processing
				}
			});
			return mdReceiver[0];
		}
		finally {
			input.close();
		}
	}

	@Override
	public boolean hasModuleMetadata(File moduleDirectory, FileFilter filter) {
		if(filter == null)
			filter = moduleFileFilter;
		for(MetadataExtractor extractor : metadataExtractors)
			if(extractor.canExtractFrom(moduleDirectory, filter))
				return true;
		return false;
	}

	private void installTemplate(Metadata metadata, File destinationBase, ZipInputStream template) throws IOException {

		ZipEntry zipEntry;
		while((zipEntry = template.getNextEntry()) != null) {
			String name = zipEntry.getName();
			if(zipEntry.isDirectory()) {
				File destination = new File(destinationBase, name);
				if(!destination.mkdirs())
					throw new IOException(destination + " could not be created");
				continue;
			}

			if(name.endsWith(".erb")) {
				File destination = new File(destinationBase, name.substring(0, name.length() - 4));
				BufferedReader reader = new BufferedReader(new InputStreamReader(template));
				BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
				try {
					erb.generate(metadata, reader, writer);
				}
				finally {
					StreamUtil.close(writer);
				}
			}
			else {
				File destination = new File(destinationBase, name);
				FileUtils.cp(template, destination.getParentFile(), destination.getName());
			}
		}
	}

	private void internalSaveJSONMetadata(Metadata md, File jsonFile) throws IOException {
		Writer writer = new BufferedWriter(new FileWriter(jsonFile));
		try {
			gson.toJson(md, writer);
		}
		finally {
			StreamUtil.close(writer);
		}
	}

	@Override
	public boolean isMetadataFile(String source) {
		if(source != null) {
			for(MetadataExtractor extractor : metadataExtractors)
				if(source.equals(extractor.getPrimarySource()))
					return true;
		}
		return false;
	}

	public Metadata loadJSONMetadata(File jsonFile) throws IOException {
		Reader reader = new BufferedReader(new FileReader(jsonFile));
		try {
			Metadata md = gson.fromJson(reader, Metadata.class);
			return md;
		}
		finally {
			StreamUtil.close(reader);
		}
	}

	@Override
	public Metadata loadModulefile(File moduleFile, Diagnostic diagnostic) throws IOException {
		Metadata metadata = new Metadata();
		ModuleUtils.parseModulefile(moduleFile, metadata, diagnostic);
		return metadata;
	}

	public void saveJSONMetadata(Metadata md, File jsonFile) throws IOException {
		internalSaveJSONMetadata(new Metadata(md), jsonFile);
	}

	@Override
	public void saveModulefile(Metadata md, File moduleFile) throws IOException {
		ModuleUtils.saveAsModulefile(md, moduleFile);
	}
}
