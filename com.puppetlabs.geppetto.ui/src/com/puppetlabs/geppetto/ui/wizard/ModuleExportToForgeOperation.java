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

import static com.puppetlabs.geppetto.forge.Forge.PUBLISHER;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.forge.AlreadyPublishedException;
import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.client.ForgeException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

public abstract class ModuleExportToForgeOperation extends ModuleExportOperation {

	private final boolean dryRun;

	private Diagnostic diagnostic;

	public ModuleExportToForgeOperation(List<ExportSpec> exportSpecs, File destination, boolean dryRun) {
		super(exportSpecs, destination, null);
		this.dryRun = dryRun;
	}

	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	protected abstract ForgeService getForgeService();

	@Override
	public void run(IProgressMonitor monitor) throws InterruptedException {
		monitor.beginTask(null, 1000);
		try {
			super.run(new SubProgressMonitor(monitor, 300));
			if(!getStatus().isOK())
				return;

			File[] destEntries = getDestination().listFiles();
			if(destEntries == null || destEntries.length == 0)
				return;

			ArrayList<File> tarballs = new ArrayList<File>();
			ArrayList<String> subtaskNames = new ArrayList<String>();
			for(File builtModule : destEntries) {
				String name = builtModule.getName();
				if(name.endsWith(".tar.gz") || name.endsWith(".tgz")) {
					tarballs.add(builtModule);
					subtaskNames.add("Publishing " + name);
				}
			}
			if(tarballs.isEmpty())
				return;

			DiagnosticWithProgress result = new DiagnosticWithProgress(monitor, 700, subtaskNames, 500);
			diagnostic = result;
			try {
				boolean noPublishingMade = true;
				for(File builtModule : tarballs) {
					try {
						getForgeService().publish(builtModule, dryRun, result);
						result.taskDone();
						noPublishingMade = false;
						continue;
					}
					catch(AlreadyPublishedException e) {
						result.addChild(new Diagnostic(Diagnostic.WARNING, PUBLISHER, e.getMessage()));
						result.taskDone();
						continue;
					}
					catch(ForgeException e) {
						result.addChild(new Diagnostic(Diagnostic.ERROR, PUBLISHER, e.getMessage()));
					}
					catch(Exception e) {
						result.addChild(new ExceptionDiagnostic(
							Diagnostic.ERROR, PUBLISHER, "Unable to publish module " + builtModule.getName(), e));
					}
					return;
				}

				if(noPublishingMade) {
					result.addChild(new Diagnostic(
						Diagnostic.INFO, PUBLISHER, "All modules have already been published at their current version"));
				}
			}
			finally {
				result.done();
			}
			if(result.getSeverity() == Diagnostic.OK)
				return;
		}
		finally {
			monitor.done();
		}
	}
}
