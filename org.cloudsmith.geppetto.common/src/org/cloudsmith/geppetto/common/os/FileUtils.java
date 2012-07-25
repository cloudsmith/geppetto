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
package org.cloudsmith.geppetto.common.os;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class FileUtils {
	public static final Pattern DEFAULT_EXCLUDES = Pattern.compile("^[\\.~#].*$");

	public static void cp(File source, File destDir, String fileName) throws IOException {
		InputStream in = new FileInputStream(source);
		try {
			cp(in, destDir, fileName);
			if(source.canExecute())
				new File(destDir, fileName).setExecutable(true, false);
		}
		finally {
			StreamUtil.close(in);
		}
	}

	public static void cp(InputStream source, File destDir, String fileName) throws IOException {
		cp(source, destDir, fileName, -1);
	}

	public static void cp(InputStream source, File destDir, String fileName, long timestamp) throws IOException {
		File target = new File(destDir, fileName);
		OutputStream out = new FileOutputStream(target);
		try {
			StreamUtil.copy(source, out);
			if(timestamp > 0)
				target.setLastModified(timestamp);
		}
		finally {
			StreamUtil.close(out);
		}
	}

	public static void cpR(File source, File destDir, Pattern excludeNames, boolean createTop,
			boolean includeEmptyFolders) throws IOException {
		String name = source.getName();
		if(excludeNames != null && excludeNames.matcher(name).matches())
			return;

		File[] children = source.listFiles();
		if(children == null) {
			File destFile = new File(destDir, name);
			if(destFile.exists())
				throw new IOException(destFile.getAbsolutePath() + " already exists");
			cp(source, destDir, name);
			return;
		}

		if(children.length == 0) {
			if(!includeEmptyFolders)
				return;
		}

		if(createTop) {
			destDir = new File(destDir, name);
		}

		if(!(destDir.mkdir() || destDir.isDirectory()))
			throw new IOException("Unable to create directory " + destDir.getAbsolutePath());

		for(File child : children)
			cpR(child, destDir, excludeNames, true, includeEmptyFolders);
	}

	public static void rmR(File fileOrDir) {
		rmR(fileOrDir, null);
	}

	public static void rmR(File fileOrDir, Pattern excludePattern) {
		rmR(fileOrDir, excludePattern, null);
	}

	public static void rmR(File fileOrDir, Pattern excludePattern, IProgressMonitor monitor) {
		if(monitor != null)
			monitor.beginTask(null, IProgressMonitor.UNKNOWN);
		try {
			rmRwithInitializedMonitor(fileOrDir, excludePattern, monitor);
		}
		finally {
			if(monitor != null)
				monitor.done();
		}
	}

	private static void rmRwithInitializedMonitor(File fileOrDir, Pattern excludePattern, IProgressMonitor monitor) {
		if(excludePattern != null && excludePattern.matcher(fileOrDir.getName()).matches())
			return;

		if(monitor != null) {
			if(monitor.isCanceled())
				throw new OperationCanceledException();
			monitor.worked(1);
		}
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				rmRwithInitializedMonitor(child, excludePattern, monitor);
		fileOrDir.delete();
	}

	public static void unzip(File zipFile, File destDir) throws IOException {
		InputStream in = new FileInputStream(zipFile);
		try {
			unzip(in, destDir);
		}
		finally {
			StreamUtil.close(in);
		}
	}

	public static void unzip(InputStream inputs, File dest) throws IOException {

		if(!(dest.isDirectory() || dest.mkdirs()))
			throw new IOException("Not a directory: " + dest.getAbsolutePath());

		ZipInputStream input = new ZipInputStream(inputs);
		ZipEntry entry;
		while((entry = input.getNextEntry()) != null) {
			String name = entry.getName();
			if(entry.isDirectory()) {
				File destDir = new File(dest, name);
				if(!(destDir.isDirectory() || destDir.mkdir()))
					throw new IOException("Not a directory: " + destDir.getAbsolutePath());
				continue;
			}
			cp(input, dest, name, entry.getTime());
		}
	}
}
