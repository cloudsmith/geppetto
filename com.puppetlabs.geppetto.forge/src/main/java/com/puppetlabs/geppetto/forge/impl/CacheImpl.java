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

import static org.cloudsmith.geppetto.forge.Forge.CACHE_LOCATION;
import static org.cloudsmith.geppetto.forge.model.Constants.API_V2_URL_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.semver.Version;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
class CacheImpl implements Cache {
	private static void delete(File fileOrDir) throws IOException {
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				delete(child);
		if(!fileOrDir.delete() && fileOrDir.exists())
			throw new IOException("Unable to delete " + fileOrDir);
	}

	@Inject
	private ReleaseService releaseService;

	private transient String cacheKey;

	@Inject(optional = true)
	@Named(CACHE_LOCATION)
	private File location;

	@Inject
	@Named(API_V2_URL_NAME)
	private String apiV2URL;

	@Override
	public void clean() throws IOException {
		delete(getLocation());
	}

	private synchronized String getCacheKey() {
		if(cacheKey == null) {
			StringBuilder bld = new StringBuilder(apiV2URL.replaceAll("[^\\p{Alnum}]+", "_"));
			int last = bld.length() - 1;
			if(bld.charAt(last) == '_')
				bld.setLength(last);
			bld.append('-');
			Checksums.appendSHA1(bld, apiV2URL);
			cacheKey = bld.toString();
		}
		return cacheKey;
	}

	@Override
	public synchronized File getLocation() {
		if(location == null) {
			if(location == null) {
				String userHome = System.getProperty("user.home");
				if(userHome == null)
					throw new RuntimeException("Unable to obtain users home directory");
				File dir = new File(userHome);
				if(!dir.isDirectory())
					throw new RuntimeException(userHome + " is not a directory");
				location = new File(new File(dir, ".puppet/var/puppet-module/cache"), getCacheKey());
			}
		}
		return location;
	}

	@Override
	public File retrieve(ModuleName qname, Version version) throws IOException {
		// This cache assumes that all leaf names are unique so we don't want
		// to preserve the folder structure
		StringBuilder bld = new StringBuilder();
		ModuleUtils.buildFileNameWithExtension(qname, version, bld);
		File cachedFile = new File(getLocation(), bld.toString());
		if(!cachedFile.exists()) {
			File dir = cachedFile.getParentFile();
			dir.mkdirs();
			OutputStream output = new FileOutputStream(cachedFile);
			try {
				releaseService.download(qname.getOwner(), qname.getName(), version, output);
			}
			finally {
				StreamUtil.close(output);
			}
		}
		return cachedFile;
	}
}
