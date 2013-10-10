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
package com.puppetlabs.geppetto.forge.maven.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;

/**
 * Stores information about a release that cannot be contained in a POM
 */
public class PuppetModuleReleaseInfo extends Entity {
	private static final String[] readmeFiles = {
			"README.markdown", "README.md", "README.txt", "README", "README.mkdn", "README.mkd" };

	private static final String[] changeLogFiles = { "Changes", "Changes.md", "Changelog", "Changelog.md" };

	private static final String[] licenseFiles = { "LICENSE", "COPYING" };

	private static File findOneOf(File[] children, String[] fileNames) {
		for(File file : children)
			if(isFile(file.getName(), fileNames))
				return file;
		return null;
	}

	public static boolean isChangelogFile(String fileName) {
		return isFile(fileName, changeLogFiles);
	}

	private static boolean isFile(String fileName, String[] validNames) {
		for(String n : validNames)
			if(n.equalsIgnoreCase(fileName))
				return true;
		return false;
	}

	public static boolean isLicenseFile(String fileName) {
		return isFile(fileName, licenseFiles);
	}

	public static boolean isReadmeFile(String fileName) {
		return isFile(fileName, readmeFiles);
	}

	public static String readContent(InputStream input) throws IOException {
		byte[] buf = new byte[4096];
		int cnt;
		StringWriter writer = new StringWriter();
		while((cnt = input.read(buf)) > 0)
			writer.write(new String(buf, 0, cnt, Charsets.UTF_8));
		return writer.toString();
	}

	@Expose
	private ModuleName moduleName;

	@Expose
	private String changelog;

	@Expose
	private String license;

	@Expose
	private Metadata metadata;

	@Expose
	private String readme;

	@Expose
	private List<String> tags;

	@Expose
	private Integer downloadCount;

	public String getChangelog() {
		return changelog;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public String getLicense() {
		return license;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * We need an extra module name since the one in the metadata cannot be trusted
	 * and the maven coordinates are lower cased
	 * 
	 * @return The name of the module
	 */
	public ModuleName getModuleName() {
		return moduleName;
	}

	public String getReadme() {
		return readme;
	}

	public List<String> getTags() {
		return tags == null
				? Collections.<String> emptyList()
				: Collections.unmodifiableList(tags);
	}

	/**
	 * Reads the changelog, license, and readme from the module directory. All files
	 * are optional and empty files will result in corresponding <code>null</code> entries.
	 * 
	 * @param moduleDir
	 *            The module directory
	 * @throws IOException
	 *             If something goes wrong during reading
	 */
	public void populate(File moduleDir) throws IOException {
		File[] children = moduleDir.listFiles();
		setChangelog(readChangelog(children));
		setLicense(readLicense(children));
		setReadme(readReadme(children));
		setDownloadCount(0);
	}

	private String readChangelog(File[] children) throws IOException {
		return readContent(children, changeLogFiles);
	}

	private String readContent(File[] children, String[] fileNames) throws IOException {
		File file = findOneOf(children, fileNames);
		if(file == null)
			return null;
		FileInputStream in = new FileInputStream(file);
		try {
			return readContent(in);
		}
		finally {
			in.close();
		}
	}

	private String readLicense(File[] children) throws IOException {
		return readContent(children, licenseFiles);
	}

	private String readReadme(File[] children) throws IOException {
		return readContent(children, readmeFiles);
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public void setModuleName(ModuleName moduleName) {
		this.moduleName = moduleName;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
