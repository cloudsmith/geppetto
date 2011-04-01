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
package org.cloudsmith.geppetto.pp.dsl.pptp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.common.eclipse.BundledFilesUtils;
import org.cloudsmith.geppetto.pp.pptp.PPTPFactory;
import org.cloudsmith.geppetto.pp.pptp.PuppetTarget;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

/**
 * Manages the Puppet TP
 * 
 */
@Singleton
public class PPTPManager {

	protected final PuppetTarget theTargetPlatform;

	/**
	 * Create a PPTP Manager configured with the current default PPTP and all contributions from the
	 * current workspace.
	 */
	public PPTPManager() {
		theTargetPlatform = PPTPFactory.eINSTANCE.createPuppetTarget();
		loadDefaultTP();
		loadWorkspaceTPView();
	}

	/**
	 * Create a TP View based on the directories in a given root directory.
	 * Also see {@link #createWorkspaceTPView()}.
	 * 
	 * @param rootDirectory
	 */
	public PPTPManager(File rootDirectory) {
		theTargetPlatform = PPTPFactory.eINSTANCE.createPuppetTarget();
		loadDefaultTP();
		loadDirectoryWorkspaceTPView(rootDirectory);
	}

	/**
	 * Creates a TP View based on a set of File references to x/lib/puppet where x can be a puppet module
	 * or any directory.
	 * Also see #
	 * 
	 * @param puppetLibs
	 */
	protected void createTPView(List<File> puppetLibs) {

		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			if(!helper.isRubyServicesAvailable())
				// sorry, must give up
				return;
			for(File libPuppetDir : puppetLibs) {
				try {
					TargetEntry entry = helper.loadDirectoryTarget(libPuppetDir);
					theTargetPlatform.getEntries().add(entry);
				}
				catch(Exception e) {
					// skip this entry...
					// TODO: deal with this
					e.printStackTrace();
				}
			}
		}
		finally {
			helper.tearDown();
		}

	}

	/**
	 * Hm, not very safe, anyone can change stuff this way...
	 * 
	 * @return
	 */
	public PuppetTarget getTargetPlatform() {
		return theTargetPlatform;
	}

	private void loadDefaultTP() {
		// TODO: let a preference control the default
		IPath defaultTPPath = new Path("targets/puppet-2.6.2_0.pptp");
		File pptpFile = null;
		try {
			pptpFile = BundledFilesUtils.getFileFromClassBundle(PPTPManager.class, defaultTPPath);
		}
		catch(IOException e) {
			// TODO: Log this
			System.err.println("Could not load default tp");
			return;
		}

		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(pptpFile.getAbsolutePath());
		Resource targetResource = resourceSet.getResource(fileURI, true);
		EList<EObject> contents = targetResource.getContents();
		if(contents.size() != 1 || !(contents.get(0) instanceof TargetEntry)) {
			System.err.println("Could not load default to - no target entry found");
			return;
		}

		TargetEntry target = (TargetEntry) contents.get(0);

		// Hm, can not just stick this into the active TP as it is in another resource
		// and reference is a containement ref
		theTargetPlatform.getEntries().add(EcoreUtil.copy(target));
	}

	private void loadDirectoryWorkspaceTPView(File rootDirectory) {
		if(rootDirectory == null)
			throw new IllegalArgumentException("null root");
		if(!rootDirectory.isDirectory())
			throw new IllegalArgumentException("not a directory: " + rootDirectory.getPath());
		if(!rootDirectory.exists())
			throw new IllegalArgumentException("directory does not exist: " + rootDirectory.getPath());

		List<File> puppetLibs = Lists.newArrayList();
		for(File childOfRoot : rootDirectory.listFiles()) {
			File puppetLibDir = new File(childOfRoot, "lib/puppet");
			if(puppetLibDir.exists())
				puppetLibs.add(puppetLibDir);
		}
		createTPView(puppetLibs);

	}

	/**
	 * Loads a TP View based on the projects in the workspace.
	 * (Note: can not simply take the root of the workspace and process as a rootDirectory since
	 * projects may be linked from different locations).
	 */
	protected void loadWorkspaceTPView() {
		List<File> puppetLibs = Lists.newArrayList();
		for(IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IFile f = p.getFile(new Path("lib/puppet"));
			if(f.exists())
				puppetLibs.add(f.getLocation().toFile());
		}
		createTPView(puppetLibs);
	}

}
