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
package com.puppetlabs.geppetto.graph;

import java.io.File;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.validation.runner.AllModuleReferences.Export;
import com.puppetlabs.geppetto.validation.runner.MetadataInfo;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Base functionality for producing href for Exports and Modules.
 * 
 */
public abstract class AbstractHrefProducer implements IHrefProducer {

	public static final String URL_PREFIX_NAME = "href.producer.url.prefix";

	protected static IPath relativePathToFile(IPath filePath, IPath rootPath) {
		if(rootPath == null)
			return filePath;
		return filePath.makeRelativeTo(rootPath);
	}

	/**
	 * Translate a file path to a file relative to rootFolder (if under this root, else return
	 * an absolute File).
	 * 
	 * @param filePath
	 * @param rootFolder
	 *        - root directory/folder or a file name
	 * @return
	 */
	protected static File relativePathToFile(String filePath, File rootFolder) {
		return relativePathToFile(new Path(filePath), new Path(rootFolder.getPath())).toFile();
		// Path problemPath = new Path(filePath);
		// Path rootPath = new Path(rootFolder.getPath());
		// IPath relativePath = problemPath.makeRelativeTo(rootPath);
		// return relativePath.toFile();
	}

	protected abstract String file2HrefPath(File f, File root);

	@Override
	public String href(Export e, File root) {
		File f = e.getFile();
		if(f == null)
			return "";
		String path = f.getAbsolutePath();

		// filter out href's to pptp
		if(path.endsWith(".pptp"))
			return hrefToPPTP(e);

		return hrefToFileLocation(file2HrefPath(f, root), e.getLine(), e.getStart(), e.getLength());
	}

	public String href(MetadataInfo mi, File root) {
		File f = mi.getFile();
		if(f == null)
			return "";
		if(mi.isRole())
			return hrefToNode(file2HrefPath(f, root));
		return hrefToModule(file2HrefPath(f, root));
	}

	/**
	 * This default implementation returns an empty string.
	 */
	@Override
	public String hrefForEdge(String idFrom, String idTo, boolean splitEdge) {
		return "";
	}

	/**
	 * This default implementation returns an empty string
	 */
	@Override
	public String hrefForEdgeToPptp(String idFrom) {
		return "";
	}

	/**
	 * This default implementation returns an empty string
	 */
	@Override
	public String hrefForEdgeToUnresolved(String idFrom, boolean splitEdge) {
		return "";
	}

	/**
	 * This default implementation returns an empty string.
	 */
	@Override
	public String hrefForUnresolved(ModuleName fromModuleName, String name) {
		return "";
	}

	/**
	 * This implementation returns a relative url with line= offset= lenght= url parameters.
	 * 
	 * @param path
	 * @param line
	 * @param start
	 * @param length
	 * @return
	 */
	protected String hrefToFileLocation(String path, int line, int start, int length) {
		StringBuilder builder = new StringBuilder();
		builder.append(path);
		String prefix = "?";
		if(line != -1) {
			builder.append(prefix);
			builder.append("line=");
			builder.append(line);
			prefix = "&amp;";
		}
		if(start != -1) {
			builder.append(prefix);
			builder.append("offset=");
			builder.append(start);
			if(length > 0) {
				builder.append("&amp;length=");
				builder.append(length);
			}
		}
		return builder.toString();
	}

	/**
	 * This implementation returns a possibly relativized path with given line and start and length set to -1
	 */
	@Override
	public String hrefToManifest(IPath f, IPath root, int line) {
		return hrefToFileLocation(path2HrefPath(f, root), line, -1, -1);
	}

	/**
	 * This implementation returns the path
	 * 
	 * @param path
	 * @return
	 */
	protected String hrefToModule(String path) {
		return path;
	}

	/**
	 * This implementation returns the path
	 * 
	 * @param path
	 * @return
	 */
	protected String hrefToNode(String path) {
		return path;
	}

	/**
	 * This default implementation returns an empty string.
	 * 
	 * @param e
	 * @return
	 */
	protected String hrefToPPTP(Export e) {
		return "";
	}

	protected abstract String path2HrefPath(IPath f, IPath root);
}
