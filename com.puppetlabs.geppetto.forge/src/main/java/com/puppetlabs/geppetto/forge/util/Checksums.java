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
package com.puppetlabs.geppetto.forge.util;

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.common.os.StreamUtil;

/**
 * Utilities for computing MD5 checksums on files.
 */
public class Checksums {
	private static final char[] hexChars = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static void appendChangedFiles(Map<String, byte[]> checksums, File file, List<File> result,
			FileFilter exclusionFilter) throws IOException {
		appendChangedFiles(
			checksums, file, getMessageDigest(), file.getAbsolutePath().length() + 1, result, exclusionFilter);
	}

	private static void appendChangedFiles(Map<String, byte[]> checksums, File file, MessageDigest md, int baseDirLen,
			List<File> result, FileFilter exclusionFilter) throws IOException {
		if(!isChecksumCandidate(file, exclusionFilter))
			return;

		File[] children = file.listFiles();
		if(children != null) {
			for(File child : children)
				appendChangedFiles(checksums, child, md, baseDirLen, result, exclusionFilter);
			return;
		}

		byte[] oldChecksum = checksums.get(file.getAbsolutePath().substring(baseDirLen));
		if(oldChecksum == null)
			result.add(file);
		else {
			byte[] newChecksum = computeChecksum(file, md);
			if(!Arrays.equals(oldChecksum, newChecksum))
				result.add(file);
		}
	}

	public static void appendHex(StringBuilder bld, byte b) {
		bld.append(hexChars[(b & 0xf0) >> 4]);
		bld.append(hexChars[b & 0x0f]);
	}

	public static void appendSHA1(StringBuilder bld, String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] digest = md.digest(value.getBytes("UTF-8"));
			for(int idx = 0; idx < digest.length; ++idx)
				appendHex(bld, digest[idx]);
		}
		catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] computeChecksum(File file, MessageDigest md) throws IOException {
		InputStream input = new FileInputStream(file);
		md.reset();
		try {
			byte[] buf = new byte[0x1000];
			int cnt;
			while((cnt = input.read(buf)) > 0)
				md.update(buf, 0, cnt);

		}
		finally {
			StreamUtil.close(input);
		}
		return md.digest();
	}

	/**
	 * Returns the hexadecimal SHA1 representation of the argument
	 * 
	 * @param value
	 *            The value to compute the digest for
	 * @return The SHA1 hex string
	 */
	public static String createSHA1(String value) {
		StringBuilder bld = new StringBuilder();
		appendSHA1(bld, value);
		return bld.toString();
	}

	private static MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isChecksumCandidate(File file, FileFilter filter) throws IOException {
		String filename = file.getName();
		if(METADATA_JSON_NAME.equals(filename) || "REVISION".equals(filename))
			return false;

		return filter.accept(file) && !FileUtils.isSymlink(file);
	}

	public static Map<String, byte[]> loadChecksums(File moduleDir, FileFilter exclusionFilter) throws IOException {
		if(exclusionFilter == null)
			exclusionFilter = ModuleUtils.DEFAULT_FILE_FILTER;
		Map<String, byte[]> checksums = new TreeMap<String, byte[]>();
		loadChecksums(
			checksums, getMessageDigest(), moduleDir, moduleDir.getAbsolutePath().length() + 1, exclusionFilter);
		return checksums;
	}

	private static void loadChecksums(Map<String, byte[]> checksums, MessageDigest md, File file, int basedirLen,
			FileFilter exclusionFilter) throws IOException {
		if(!isChecksumCandidate(file, exclusionFilter))
			return;

		File[] children = file.listFiles();
		if(children == null)
			checksums.put(file.getAbsolutePath().substring(basedirLen), computeChecksum(file, md));
		else {
			for(File child : children)
				loadChecksums(checksums, md, child, basedirLen, exclusionFilter);
		}
	}

}
