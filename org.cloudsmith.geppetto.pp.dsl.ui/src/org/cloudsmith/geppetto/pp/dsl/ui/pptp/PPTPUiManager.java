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
package org.cloudsmith.geppetto.pp.dsl.ui.pptp;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.pptp.PPTPManager;
import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;
import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.pp.pptp.TypeFragment;
import org.cloudsmith.geppetto.pp.pptp.util.PPTPLinker;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

/**
 * Manages the PPTP in a UI scenario.
 */
@Singleton
public class PPTPUiManager extends PPTPManager implements IResourceChangeListener {

	private enum TPTYPE {
		IRRELEVANT, TYPE, FUNCTION, EXTENSION;
	}

	protected IPath libPuppetPrefix = new Path("lib/puppet");

	/**
	 * Adds listening and updates of TP View of Workspace.
	 */
	public PPTPUiManager() {
		// Superclass sets up default
		loadWorkspaceTPView();

		// Listen for changes
		// only interested in post-change messages
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * Returns the type/relevance of a .rb file path relative to a root.
	 * 
	 * @param projectRelativePath
	 * @return
	 */
	protected TPTYPE getTPTYPE(IPath projectRelativePath) {
		if(!libPuppetPrefix.isPrefixOf(projectRelativePath))
			return TPTYPE.IRRELEVANT;
		if("parser".equals(projectRelativePath.segment(2))) {
			if(!"functions".equals(projectRelativePath.segment(3)))
				return TPTYPE.IRRELEVANT;
			return TPTYPE.FUNCTION;
		}
		if(!"type".equals(projectRelativePath.segment(2)))
			return TPTYPE.IRRELEVANT;
		if(projectRelativePath.segmentCount() > 3)
			return TPTYPE.EXTENSION;
		return TPTYPE.TYPE;
	}

	protected TPTYPE getTPTYPE(IResource resource) {
		if(!"rb".equals(resource.getFileExtension()))
			return TPTYPE.IRRELEVANT;
		return getTPTYPE(resource.getProjectRelativePath());
	}

	/**
	 * Loads a TP View based on the projects in the workspace.
	 * (Note: can not simply take the root of the workspace and process as a rootDirectory since
	 * projects may be linked from different locations).
	 */
	protected void loadWorkspaceTPView() {
		List<File> puppetLibs = Lists.newArrayList();
		for(IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if(!p.isAccessible())
				continue;
			IFile f = p.getFile(new Path("lib/puppet"));
			if(f.getLocation().toFile().exists())
				puppetLibs.add(f.getLocation().toFile());
		}
		createTPView(puppetLibs);
	}

	/**
	 * TODO:
	 * - if a Moduleinfo file is change the target will not show the correct version
	 * - If a Modulefile is added or removed, the target will still contain the wrong type (Directory or Module)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta rootDelta = event.getDelta();
		final List<IResource> changed = Lists.newArrayList();
		final List<IResource> added = Lists.newArrayList();
		final List<IResource> removed = Lists.newArrayList();
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) {
				// only interested in files with the "rb" extension

				IResource resource = delta.getResource();
				if(!(resource.getType() == IResource.FILE && "rb".equalsIgnoreCase(resource.getFileExtension())))
					return true;
				switch(delta.getKind()) {
					case IResourceDelta.CHANGED:
						changed.add(resource);
						break;
					case IResourceDelta.REMOVED:
						removed.add(resource);
						break;
					case IResourceDelta.ADDED:
						added.add(resource);
						break;
				}
				return true;
			}
		};
		try {
			rootDelta.accept(visitor);
		}
		catch(CoreException e) {
			System.err.println("Error while processing .rb delta" + e.getMessage());
		}

		// TODO: Run this in the background

		RubyHelper helper = new RubyHelper();
		HANDLECHANGE: try {
			helper.setUp();
			if(!helper.isRubyServicesAvailable())
				break HANDLECHANGE;

			for(IResource r : removed) {
				if(getTPTYPE(r) == TPTYPE.IRRELEVANT)
					continue; // ignore this
				// removals are easy, simply remove everything contributed from the file
				PPTPLinker.removeContributionsFromFile(theTargetPlatform, r.getLocation().toFile());
			}

			for(IResource r : changed) {
				// handle change as a removal and an addition
				if(getTPTYPE(r) == TPTYPE.IRRELEVANT)
					continue; // ignore this
				// removals are easy, simply remove everything contributed from the file
				PPTPLinker.removeContributionsFromFile(theTargetPlatform, r.getLocation().toFile());
			}
			// since all changed have been removed, add them
			added.addAll(changed);

			for(IResource r : added) {
				// must first figure out how this file should be parsed
				switch(getTPTYPE(r)) {
					case IRRELEVANT:
						continue;
					case EXTENSION: {
						List<TypeFragment> fragments = Collections.emptyList();
						try {
							fragments = helper.loadTypeFragments(r.getLocation().toFile(), true);
						}
						catch(Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue;
						}
						// TODO: Ensure that all possible targets are added first
						// i.e. a project even if it has no contributions should be added to the target
						// at all times...
						TargetEntry target = PPTPLinker.findTargetEntry(
							theTargetPlatform, r.getProject().getLocation().toFile());
						for(TypeFragment tf : fragments)
							PPTPLinker.addTypeFragment(target, tf);
						break;
					}
					case FUNCTION: {
						List<Function> functions = Collections.emptyList();
						try {
							functions = helper.loadFunctions(r.getLocation().toFile(), true);
						}
						catch(Exception e) {
							e.printStackTrace();
							continue;
						}
						// TODO: Ensure that all possible targets are added first
						// i.e. a project even if it has no contributions should be added to the target
						// at all times...
						TargetEntry target = PPTPLinker.findTargetEntry(theTargetPlatform, new File(
							r.getProject().getLocation().toFile(), "lib/puppet"));
						if(target == null)
							System.err.println("NULL TARGET BUG! for: " + r.getProject().getLocation().toFile());
						for(Function f : functions)
							target.getFunctions().add(f);
						break;
					}

					case TYPE: {
						List<Type> types = Collections.emptyList();
						try {
							types = helper.loadTypes(r.getLocation().toFile(), true);
						}
						catch(Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue;
						}
						// TODO: Ensure that all possible targets are added first
						// i.e. a project even if it has no contributions should be added to the target
						// at all times...
						TargetEntry target = PPTPLinker.findTargetEntry(
							theTargetPlatform, r.getProject().getLocation().toFile());
						for(Type t : types)
							PPTPLinker.addType(target, t);
						break;
					}
				}
			}
		}
		finally {
			helper.tearDown();
		}
	}

}
