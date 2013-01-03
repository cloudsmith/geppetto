/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.v2.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.v2.client.Constants;
import org.cloudsmith.geppetto.forge.v2.model.Release;

/**
 * A CRUD service for {@link Release} objects
 */
public class ReleaseService extends ForgeService {
	private static String getReleasePath(String owner, String name, String version) {
		return Constants.COMMAND_GROUP_RELEASES + '/' + owner + '/' + name + '/' + version;
	}

	/**
	 * Create a new Release based on the given <code>gzipFile</code>. The
	 * combination of name and owner in the template must be unique.
	 * 
	 * @param owner
	 * @param name
	 * @param notes
	 * @param gzipFile
	 * @return
	 * @throws IOException
	 */
	public Release create(String owner, String name, String notes, InputStream gzipFile, long fileSize)
			throws IOException {
		Map<String, String> parts = new HashMap<String, String>();
		parts.put("owner", owner);
		parts.put("module", name);
		if(notes != null && !notes.isEmpty())
			parts.put("notes", notes);
		return getClient(true).postUpload(
			Constants.COMMAND_GROUP_RELEASES, parts, gzipFile, "application/x-compressed-tar", "tempfile.tar.gz",
			fileSize, Release.class);
	}

	/**
	 * Delete the Release identified by <code>owner</code>, <code>name</code>, and <code>version.
	 * 
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @throws IOException
	 */
	public void delete(String owner, String name, String version) throws IOException {
		getClient(true).delete(getReleasePath(owner, name, version));
	}

	/**
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @param output
	 *            The stream that will receive the file content
	 * @return The content of a particular release
	 * @throws IOException
	 */
	public void download(String owner, String name, String version, OutputStream output) throws IOException {
		String path = Constants.COMMAND_GROUP_FILES + '/' + owner + '-' + name + '-' + version + ".tar.gz";
		getClient(false).download(path, null, output);
	}

	/**
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @return Details about a particular release
	 * @throws IOException
	 */
	public Release get(String owner, String name, String version) throws IOException {
		return getClient(false).get(getReleasePath(owner, name, version), null, Release.class);
	}

	/**
	 * Returns a list of all known releases.
	 * 
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return A list of all Releases.
	 * @throws IOException
	 */
	public List<Release> list(ListPreferences listPreferences) throws IOException {
		List<Release> releases = getClient(false).get(Constants.COMMAND_GROUP_RELEASES, null, Constants.LIST_RELEASE);
		if(releases == null)
			releases = Collections.emptyList();
		return releases;
	}

	/**
	 * Updates the Release identified by <code>owner</code>, <code>name</code>, and <code>version with
	 * new data.
	 * 
	 * @param owner
	 *            The Module owner
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @param Release
	 *            New data for the Release.
	 * @return The updated Release
	 * @throws IOException
	 */
	public Release update(String owner, String name, String version, Release release) throws IOException {
		return getClient(true).patch(getReleasePath(owner, name, version), release, Release.class);
	}
}
