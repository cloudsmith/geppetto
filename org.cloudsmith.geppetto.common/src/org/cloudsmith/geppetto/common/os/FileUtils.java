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
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.cloudsmith.geppetto.common.util.EclipseUtils;

public class FileUtils {
	public static final Pattern DEFAULT_EXCLUDES = Pattern.compile("^[\\.~#].*$");

	private static final Method Files_isSymbolicLink;

	private static final Method Files_readSymbolicLink;

	private static final Method File_toPath;

	static {
		Method isSymbolicLink = null;
		Method readSymbolicLink = null;
		Method toPath = null;
		try {
			Class<?> class_Files = Class.forName("java.nio.file.Files");
			Class<?> class_Path = Class.forName("java.nio.file.Path");
			isSymbolicLink = class_Files.getMethod("isSymbolicLink", class_Path);
			readSymbolicLink = class_Files.getMethod("readSymbolicLink", class_Path);
			toPath = File.class.getMethod("toPath");
		}
		catch(Exception e) {
		}
		Files_isSymbolicLink = isSymbolicLink;
		Files_readSymbolicLink = readSymbolicLink;
		File_toPath = toPath;
	}

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

	/**
	 * Returns the File for a class relative resource path
	 * 
	 * @param clazz
	 *            The class used when obtaining the resource
	 * @param resourcePath
	 *            The path to the resource
	 * @return The file that corresponds to the resource or <code>null</code> if the resource was not found or cannot be reached as a file.
	 * @throws IOException
	 * @see {@link Class#getResource(String)}
	 * @see {@link EclipseUtils#getResourceAsFile(URL)}
	 */
	public static File getFileFromClassResource(Class<?> clazz, String resourcePath) throws IOException {
		URL url = clazz.getResource(resourcePath);
		if(url != null)
			return EclipseUtils.getResourceAsFile(url);
		return null;
	}

	/**
	 * @param file
	 *            The file to examine
	 * @return <code>true</code> if the <code>file</code> represents a symbolic link
	 */
	public static boolean isSymlink(File file) {
		if(file == null)
			return false;

		try {
			// Use Java 7 Files.isSymbolicLink(Path) if it is available
			//
			if(Files_isSymbolicLink != null)
				return ((Boolean) Files_isSymbolicLink.invoke(null, File_toPath.invoke(file))).booleanValue();

			File canon;
			if(file.getParent() == null)
				canon = file;
			else {
				File canonDir = file.getParentFile().getCanonicalFile();
				canon = new File(canonDir, file.getName());
			}
			return !canon.getCanonicalFile().equals(canon.getAbsoluteFile());
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * @param file
	 *            The file that represents the symbolic link source
	 * @return The symbolic link target or <code>null</code> if file does not represent a symbolic link
	 */
	public static String readSymbolicLink(File file) {
		if(file == null)
			return null;

		try {
			if(Files_readSymbolicLink != null) {
				Object target = Files_readSymbolicLink.invoke(null, File_toPath.invoke(file));
				return target == null
						? null
						: target.toString();
			}
		}
		catch(Exception e) {
		}
		return null;
	}

	public static void rmR(File fileOrDir) {
		rmR(fileOrDir, null);
	}

	public static void rmR(File fileOrDir, Pattern excludePattern) {
		if(excludePattern != null && excludePattern.matcher(fileOrDir.getName()).matches())
			return;

		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				rmR(child, excludePattern);
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

			long entryTime = entry.getTime();

			// Fix entry time by taking the time zone into account
			entryTime += Calendar.getInstance().getTimeZone().getOffset(entryTime);

			cp(input, dest, name, entryTime);
		}
	}
}
