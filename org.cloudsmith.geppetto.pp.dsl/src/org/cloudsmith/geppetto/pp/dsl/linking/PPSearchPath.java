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
package org.cloudsmith.geppetto.pp.dsl.linking;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;

/**
 * notes:
 * rootURI - URI to file:: root, used to make paths relative if URI is an absolute file uri
 * 
 * 
 */
public class PPSearchPath {
	public interface IConfigurableProvider {
		/**
		 * Configure the search path provider so it knows the container of (distro) pptp, and
		 * root for absolute files (may be null).
		 * 
		 * @param rootDirectory
		 */
		public void configure(URI rootPath, String defalutPath, String environment);

	}

	public interface ISearchPathProvider {

		public PPSearchPath get(Resource r);
	}

	public static PPSearchPath fromString(String path, URI root) {
		List<IPath> p = Lists.newArrayList();

		if(path != null)
			path = path.trim();
		if(path == null || path.length() == 0)
			path = "*"; // everything

		// split the string on ':', and create a path per segment
		String[] paths = path.split(":");
		for(String s : paths) {
			if(s.length() == 0)
				continue; // skip empty segments
			p.add(new Path(s));
		}
		return new PPSearchPath(p, root);
	}

	private final URI rootURI;

	private List<IPath> searchPath;

	private PPSearchPath(List<IPath> p, URI root) {
		this.searchPath = p;
		this.rootURI = root;
	}

	public PPSearchPath(URI root) {
		searchPath = Lists.newArrayList();
		this.rootURI = root;
	}

	/**
	 * Evaluates the search path by replacing every occurrence of '$environment' with the
	 * given value.
	 * 
	 * @param environment
	 * @return a new PPSearchPath if there were replacements.
	 */
	public PPSearchPath evaluate(String environment) {
		int replacements = 0;
		ArrayList<IPath> newSearchPath = Lists.newArrayList(searchPath);
		for(IPath p : searchPath) {
			String s = p.toPortableString();
			if(s.contains("$environment")) {
				s = s.replace("$environment", environment);
				p = new Path(s);
				replacements++;
			}
			newSearchPath.add(p);
		}
		if(replacements > 0)
			return new PPSearchPath(newSearchPath, this.rootURI);
		return this;
	}

	public boolean isMatch(IPath candidate, IPath p) {
		final int candidateLimit = candidate.segmentCount();
		final int pLimit = p.segmentCount();
		for(int i = 0; i < pLimit; i++) {
			String s = p.segment(i);

			// * matches any remaining segments of candidate, including no segements
			if("*".equals(s))
				return true;

			// if more segments in candidate required than what it has
			if(i >= candidateLimit)
				return false;

			if(!s.equals(candidate.segment(i)))
				return false;
		}
		// all segments in p matched, there should only be one remaining segment in candidate
		// (if p does not end with *, only paths in p's final directory are matched
		if(candidateLimit - pLimit > 1)
			return false;
		return true;
	}

	public int searchIndexOf(IEObjectDescription d) {
		URI uri = EcoreUtil.getURI(d.getEObjectOrProxy());
		if(uri.isFile() && rootURI != null) {
			uri = uri.deresolve(rootURI.appendSegment(""));
		}
		return searchIndexOf(uri);
	}

	/**
	 * Computes the path position of the given URI, or -1 if not found.
	 * The pptp is always on the search path with index 0. TODO: This is wrong!
	 * It is only the distro path that is on 0, other pptp contributions are subject to filtering (i.e.
	 * ruby code).
	 * 
	 * @param uri
	 * @return search path index or -1 if not found
	 */
	public int searchIndexOf(URI uri) {
		String uriPath = uri.path();
		IPath p = new Path(uriPath);
		if("pptp".equals(p.getFileExtension()))
			return 0; // All pptp are searched first - ALWAYS
		if(uri.isPlatformResource())
			p = p.removeFirstSegments(2);
		for(int idx = 0; idx < searchPath.size(); idx++) {
			IPath q = searchPath.get(idx);
			if(isMatch(p, q))
				return idx + 1;

		}
		return -1;
	}
}
