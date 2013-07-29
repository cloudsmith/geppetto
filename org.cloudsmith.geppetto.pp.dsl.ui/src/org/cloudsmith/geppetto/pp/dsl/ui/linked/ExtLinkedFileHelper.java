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

package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * Helper class for dealing with external files in the ExtLinkedXtextEditor.
 * This class is responsible for managing the linked files - obtaining a link, unlinking it etc.
 * TODO: This class is specific to pp in a few places (file extension, name of project for links,
 * use of UTF-8), but is otherwise generic.
 */
public class ExtLinkedFileHelper {
	// TODO: Should be configurable
	public static final String AUTOLINK_PROJECT_NAME = "Geppetto.AutoFileSystemLinked";

	// TODO: Should be configurable
	public static final String ENCODING_UTF8 = "utf-8";

	private static void createLink(IProject project, IFile linkFile, java.net.URI uri) throws CoreException {
		IPath path = linkFile.getFullPath();

		IPath folders = path.removeLastSegments(1).removeFirstSegments(1);
		IPath checkPath = Path.ROOT;
		int segmentCount = folders.segmentCount();
		for(int i = 0; i < segmentCount; i++) {
			checkPath = checkPath.addTrailingSeparator().append(folders.segment(i));
			IFolder folder = project.getFolder(checkPath);
			if(!folder.exists())
				folder.create(true, true, null);
		}
		linkFile.createLink(uri, IResource.ALLOW_MISSING_LOCAL, null);
	}

	private static int getFirstFreeUntitled(IProject project) throws CoreException {
		IFolder untitledFolder = project.getFolder("untitled");
		if(!untitledFolder.exists()) {
			untitledFolder.create(true, true, new NullProgressMonitor());
			return 0;
		}
		IResource[] resources = untitledFolder.members();
		int result = resources.length > 0
				? 1
				: 0;
		for(IResource r : resources) {
			// tied to format "untitled-n"
			int dash = r.getName().indexOf("-");
			if(dash < 0)
				continue;
			int sequence = Integer.valueOf(r.getName().substring(dash + 1, r.getName().length() - 3));
			result = sequence > result
					? sequence
					: result;
		}
		return result + 1;
	}

	/**
	 * Throws WrappedException - the URI must refer to an existing file!
	 * 
	 * @param uri
	 * @return
	 */
	public static IFile obtainLink(java.net.URI uri, boolean untitled) {
		try {
			IWorkspace ws = ResourcesPlugin.getWorkspace();
			// get, or create project if non existing
			IProject project = ws.getRoot().getProject(AUTOLINK_PROJECT_NAME);
			boolean newProject = false;
			if(!project.exists()) {
				project.create(null);
				newProject = true;
			}
			if(!project.isOpen()) {
				project.open(null);
				// if project is hidden files can not be opened in all cases
				// TODO: Add reference to Xtext issue.
				project.setHidden(false);
			}

			if(newProject)
				project.setDefaultCharset(ENCODING_UTF8, new NullProgressMonitor());
			IFile linkFile = null;
			if(untitled) {
				int firstFree = getFirstFreeUntitled(project);
				IFolder untitledFolder = project.getFolder("untitled");
				String fileName = "untitled" + (firstFree > 1
						? "-" + Integer.toString(firstFree)
						: "") + ".b3";
				linkFile = untitledFolder.getFile(fileName);
			}
			else {
				// path in project that is the same as the external file's path to avoid collision
				// without need to create new names
				linkFile = project.getFile(uri.getPath());
			}
			if(linkFile.exists())
				linkFile.refreshLocal(1, null); // don't know if needed (or should be avoided...)
			else {
				// create the link
				createLink(project, linkFile, uri);
			}
			return linkFile;
		}
		catch(CoreException e) {
			throw new WrappedException(e);
		}
	}

	public static void unlinkInput(IFileEditorInput input) {
		IFile file = input.getFile();
		// only delete links if they are in the autolink project
		if(file.isLinked() && file.getProject().getName().equals(ExtLinkedFileHelper.AUTOLINK_PROJECT_NAME)) {
			try {
				// if the editor is disposed because workbench is shutting down, it is NOT a good
				// idea to delete the link
				if(PlatformUI.isWorkbenchRunning() && !PlatformUI.getWorkbench().isClosing())
					file.delete(true, null);
			}
			catch(CoreException e) {
				// Nothing to do really, it will be recreated/refreshed later if ever used - some garbage may stay behind...
				// TODO: log the issue
				e.printStackTrace();
			}
		}
	}
}
