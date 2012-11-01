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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.Activator;

public class OsUtil {
	public static boolean chgrp(File dir, String group, String... filenames) throws IOException {
		if(isOS("win32", "win64"))
			return false;

		int result = runProcess(dir, concat("chgrp", group, filenames));
		if(result != 0)
			throw new IOException("Unable to change group of " + enumerateToString(filenames) + " to " + group +
					" in " + dir.getAbsolutePath());
		return true;
	}

	public static boolean chmod(File dir, int mode, String... filenames) throws IOException {
		if(isOS("win32", "win64"))
			return false;
		if(!isOS("macosx"))
			mode = mode & 0xFFF;

		String modeStr = String.format("%o", Integer.valueOf(mode));
		int result = runProcess(dir, concat("chmod", modeStr, filenames));
		if(result != 0)
			throw new IOException("Unable to change mode of " + enumerateToString(filenames) + " to " + modeStr +
					" in " + dir.getAbsolutePath());

		return true;
	}

	public static boolean chown(File dir, String owner, String... filenames) throws IOException {
		if(isOS("win32", "win64"))
			return false;
		int result = runProcess(dir, concat("chown", owner, filenames));
		if(result != 0)
			throw new IOException("Unable to change owner of " + enumerateToString(filenames) + " to " + owner +
					" in " + dir.getAbsolutePath());

		return true;
	}

	private static String[] concat(String p1, String p2, String[] ps) {
		String[] all = new String[ps.length + 2];
		all[0] = p1;
		all[1] = p2;
		System.arraycopy(ps, 0, all, 2, ps.length);
		return all;
	}

	public static void copyRecursive(File source, File target) throws IOException {
		if(source.equals(target))
			return;

		Set<File> visited = new HashSet<File>();
		copyRecursive(source, target, visited);
	}

	private static void copyRecursive(File source, File target, Set<File> visited) throws IOException {
		if(visited.contains(source))
			throw new IOException("Circular file structure detected");

		visited.add(source);

		if(source.isDirectory()) {
			for(File subSource : source.listFiles()) {
				File subTarget = new File(target, subSource.getPath());
				subTarget.mkdirs();
				if(!subTarget.isDirectory())
					throw new IOException("Unable to create or access directory " + subTarget.getAbsolutePath());
				copyRecursive(subSource, subTarget, visited);
			}
		}
		else {
			File directory = target.getParentFile();
			directory.mkdirs();
			if(!directory.isDirectory())
				throw new IOException("Unable to create or access directory " + directory.getAbsolutePath());
			OutputStream targetStream = new FileOutputStream(target);
			InputStream sourceStream = new FileInputStream(source);
			StreamUtil.copy(sourceStream, targetStream);
			sourceStream.close();
			targetStream.close();
		}
	}

	public static void copyRecursive(String source, String target) throws IOException {
		copyRecursive(new File(source), new File(target));
	}

	public static Process createProcess(File dir, String... parameters) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(parameters);
		if(dir != null)
			processBuilder.directory(dir);
		return processBuilder.start();
	}

	public static Process createProcess(Object... parameters) throws IOException {
		return createProcess(null, parameters);
	}

	public static void deleteRecursive(File file) throws IOException {
		Set<File> visited = new HashSet<File>();
		deleteRecursive(file, visited);
	}

	private static void deleteRecursive(File file, Set<File> visited) throws IOException {
		if(visited.contains(file))
			throw new IOException("Circular file structure detected");

		visited.add(file);
		if(file.isDirectory()) {
			for(File subFile : file.listFiles())
				deleteRecursive(subFile, visited);
		}

		if(!file.delete()) {
			if(file.exists())
				throw new IOException("Unable to delete " + file.getAbsolutePath());
		}
	}

	public static void deleteRecursive(String path) throws IOException {
		deleteRecursive(new File(path));
	}

	private static String enumerateToString(String[] tokens) {
		StringBuilder sb = new StringBuilder();

		boolean first = true;
		for(String token : tokens) {
			if(first)
				first = false;
			else {
				sb.append(',');
				sb.append(' ');
			}
			sb.append(token);
		}

		return sb.toString();
	}

	private static void find(File file, Pattern pattern, int limit, List<File> found, Set<File> visited)
			throws IOException {
		if(visited.contains(file))
			throw new IOException("Circular file structure detected");

		visited.add(file);

		if(limit > -1 && found.size() >= limit)
			return;

		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			Arrays.sort(subFiles);
			for(File subFile : subFiles) {
				find(subFile, pattern, limit, found, visited);
			}
		}
		else {
			Matcher m = pattern.matcher(file.getName());
			if(m.matches())
				found.add(file);
		}
	}

	public static File[] find(File file, String pattern) throws IOException {
		return find(file, pattern, -1);
	}

	public static File[] find(File file, String pattern, int limit) throws IOException {
		Set<File> visited = new HashSet<File>();
		Pattern compiledPattern = Pattern.compile(pattern);
		List<File> found = new ArrayList<File>();
		find(file, compiledPattern, limit, found, visited);

		return found.toArray(new File[found.size()]);
	}

	public static File[] find(String root, String pattern) throws IOException {
		return find(new File(root), pattern, -1);
	}

	public static File[] find(String root, String pattern, int limit) throws IOException {
		return find(new File(root), pattern, limit);
	}

	public static File findFirst(File file, String pattern) throws IOException {
		Set<File> visited = new HashSet<File>();
		Pattern compiledPattern = Pattern.compile(pattern);
		List<File> files = new ArrayList<File>(1);
		find(file, compiledPattern, 1, files, visited);
		if(files.size() == 1)
			return files.get(0);

		return null;
	}

	public static File findFirst(String root, String pattern) throws IOException {
		return findFirst(new File(root), pattern);
	}

	public static boolean isOS(String... oss) {
		String currentOs = Activator.getContext().getProperty("osgi.os");
		if(currentOs == null)
			throw new IllegalStateException("Missing required property: osgi.os");
		for(String os : oss)
			if(os.equals(currentOs))
				return true;
		return false;
	}

	public static boolean link(File dir, String target, String name) throws IOException {
		if(isOS("win32", "win64"))
			return false;
		int result = runProcess(dir, "ln", "-s", name, target);
		if(result != 0)
			throw new IOException("Unable to link " + target + " to " + name + " in " + dir.getAbsolutePath());

		return true;
	}

	public static int runProcess(Collection<String> params) throws IOException {
		return runProcess(params.toArray(new String[params.size()]));
	}

	public static int runProcess(File dir, OutputStream out, OutputStream err, String... parameters) throws IOException {
		Process process = createProcess(dir, parameters);
		Thread outCopier = StreamUtil.backgroundCopy(process.getInputStream(), out);
		Thread errCopier = StreamUtil.backgroundCopy(process.getErrorStream(), err);
		try {
			process.waitFor();
		}
		catch(InterruptedException e) {
			throw new IOException("Process was interrupted", e);
		}
		try {
			outCopier.join(2000);
		}
		catch(InterruptedException e) {
			// Ignore
		}
		try {
			errCopier.join(2000);
		}
		catch(InterruptedException e) {
			// Ignore
		}
		return process.exitValue();
	}

	public static int runProcess(File dir, String... parameters) throws IOException {
		return runProcess(dir, System.out, System.err, parameters);
	}

	public static int runProcess(String... parameters) throws IOException {
		return runProcess(null, parameters);
	}

	public static String stringifyCommand(String... parameters) {
		StringBuilder cmd = new StringBuilder();
		boolean empty = true;
		for(Object parameter : parameters) {
			if(empty)
				empty = false;
			else
				cmd.append(' ');
			cmd.append(parameter);
		}
		return cmd.toString();
	}
}
