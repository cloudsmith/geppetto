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
package org.cloudsmith.geppetto.ui.wizard;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;

public class ModuleExportOperation implements IRunnableWithProgress {
	static class ExportSpec {
		private final File moduleDirectory;

		private final FileFilter fileFilter;

		ExportSpec(File moduleRoot, FileFilter fileFilter) {
			this.moduleDirectory = moduleRoot;
			this.fileFilter = fileFilter;
		}

		FileFilter getFileFilter() {
			return fileFilter;
		}

		File getModuleRoot() {
			return moduleDirectory;
		}
	}

	public static class ResourceFileFilter implements FileFilter {
		private final FileFilter defaultFilter;

		private final List<File> acceptedFiles;

		public ResourceFileFilter(Collection<IResource> resources, FileFilter defaultFilter) {
			this.defaultFilter = defaultFilter;
			acceptedFiles = new ArrayList<File>(resources.size());
			for(IResource resource : resources)
				acceptedFiles.add(resource.getLocation().toFile());
		}

		@Override
		public boolean accept(File file) {
			return defaultFilter.accept(file) && acceptedFiles.contains(file);
		}
	}

	private final Forge forge;

	private final List<ExportSpec> exportSpecs;

	private final File destination;

	private final List<IStatus> errorTable = new ArrayList<IStatus>();

	public ModuleExportOperation(Forge forge, List<ExportSpec> exportSpecs, File destination,
			IOverwriteQuery overwriteImplementor) {
		this.exportSpecs = exportSpecs;
		this.destination = destination;
		this.forge = forge;
	}

	protected File getDestination() {
		return destination;
	}

	protected Forge getForge() {
		return forge;
	}

	public IStatus getStatus() {
		if(errorTable.isEmpty())
			return Status.OK_STATUS;
		if(errorTable.size() == 1)
			return errorTable.get(0);
		return new MultiStatus(
			UIPlugin.getPlugin().getBundle().getSymbolicName(), IStatus.OK,
			errorTable.toArray(new IStatus[errorTable.size()]),
			DataTransferMessages.FileSystemExportOperation_problemsExporting, null);
	}

	public void run(IProgressMonitor monitor) throws InterruptedException {
		monitor.beginTask(null, 100);
		try {
			List<String> subtaskNames = new ArrayList<String>(exportSpecs.size());
			for(ExportSpec spec : exportSpecs)
				subtaskNames.add("Building module " + spec.getModuleRoot().getPath());

			DiagnosticWithProgress diagWithProgress = new DiagnosticWithProgress(monitor, 100, subtaskNames, 200);
			for(ExportSpec spec : exportSpecs) {
				if(monitor.isCanceled())
					throw new OperationCanceledException();
				try {
					forge.build(spec.getModuleRoot(), destination, spec.getFileFilter(), null, diagWithProgress);
					diagWithProgress.taskDone();
				}
				catch(IOException e) {
					errorTable.add(new Status(
						IStatus.ERROR, UIPlugin.getPlugin().getBundle().getSymbolicName(), 0, NLS.bind(
							DataTransferMessages.DataTransfer_errorExporting, spec.getModuleRoot().getAbsoluteFile(),
							e.getMessage()), e));
				}
			}
			diagWithProgress.done();
		}
		finally {
			monitor.done();
		}
	}
}
