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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ForgePreferences;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.semver.Version;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class CacheImpl implements Cache {
	private static final char[] hexChars = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static void appendHex(StringBuilder bld, byte b) {
		bld.append(hexChars[(b & 0xf0) >> 4]);
		bld.append(hexChars[b & 0x0f]);
	}

	private static void delete(File fileOrDir) throws IOException {
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				delete(child);
		if(!fileOrDir.delete() && fileOrDir.exists())
			throw new IOException("Unable to delete " + fileOrDir);
	}

	@Inject
	private ForgePreferences forgePreferences;

	@Inject
	private ReleaseService releaseService;

	private transient String cacheKey;

	private transient File location;

	@Override
	public void clean() throws IOException {
		delete(getLocation());
	}

	private synchronized String getCacheKey() {
		if(cacheKey == null) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				String uriStr = forgePreferences.getBaseURL();
				StringBuilder bld = new StringBuilder(uriStr.replaceAll("[^\\p{Alnum}]+", "_"));
				int last = bld.length() - 1;
				if(bld.charAt(last) == '_')
					bld.setLength(last);
				bld.append('-');
				byte[] digest = md.digest(uriStr.getBytes("UTF-8"));
				for(int idx = 0; idx < digest.length; ++idx)
					appendHex(bld, digest[idx]);
				cacheKey = bld.toString();
			}
			catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			catch(NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		return cacheKey;
	}

	@Override
	public synchronized File getLocation() {
		if(location == null) {
			String cacheLocation = forgePreferences.getCacheLocation();
			if(cacheLocation == null) {
				String userHome = System.getProperty("user.home");
				if(userHome == null)
					throw new RuntimeException("Unable to obtain users home directory");
				File dir = new File(userHome);
				if(!dir.isDirectory())
					throw new RuntimeException(userHome + " is not a directory");
				location = new File(new File(dir, ".puppet/var/puppet-module/cache"), getCacheKey());
			}
			else
				location = new File(cacheLocation);
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
