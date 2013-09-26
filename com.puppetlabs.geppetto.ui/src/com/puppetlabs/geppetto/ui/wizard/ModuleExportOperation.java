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
package com.puppetlabs.geppetto.ui.wizard;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.ui.UIPlugin;

public abstract class ModuleExportOperation implements IRunnableWithProgress {
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
			if(defaultFilter.accept(file)) {
				if(file.isDirectory())
					// Directories are always accepted unless the default filter rejects
					return true;

				// Check if the file or a parent directory of the file is among the
				// accepted files.
				File f = file;
				while(f != null) {
					if(acceptedFiles.contains(f))
						return true;
					f = f.getParentFile();
				}
			}
			return false;
		}
	}

	private final List<ExportSpec> exportSpecs;

	private final File destination;

	private final List<IStatus> errorTable = new ArrayList<IStatus>();

	public ModuleExportOperation(List<ExportSpec> exportSpecs, File destination, IOverwriteQuery overwriteImplementor) {
		this.exportSpecs = exportSpecs;
		this.destination = destination;
	}

	protected File getDestination() {
		return destination;
	}

	protected abstract Forge getForge();

	public IStatus getStatus() {
		if(errorTable.isEmpty())
			return Status.OK_STATUS;
		if(errorTable.size() == 1)
			return errorTable.get(0);
		return new MultiStatus(
			errorTable.get(0).getPlugin(), 0, errorTable.toArray(new IStatus[errorTable.size()]),
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
					getForge().build(
						spec.getModuleRoot(), destination, spec.getFileFilter(), null, null, diagWithProgress);
					diagWithProgress.taskDone();
				}
				catch(IOException e) {
					errorTable.add(UIPlugin.createStatus(IStatus.ERROR, NLS.bind(
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
