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
import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.common.diagnostic.DiagnosticType;
import org.cloudsmith.geppetto.common.diagnostic.ExceptionDiagnostic;
import org.cloudsmith.geppetto.forge.AlreadyPublishedException;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.v2.client.ForgeException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

public class ModuleExportToForgeOperation extends ModuleExportOperation {

	private Diagnostic diagnostic;

	public ModuleExportToForgeOperation(Forge forge, List<ExportSpec> exportSpecs, File destination) {
		super(forge, exportSpecs, destination, null);
	}

	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

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
						getForge().publish(builtModule, false, result);
						result.taskDone();
						noPublishingMade = false;
						continue;
					}
					catch(AlreadyPublishedException e) {
						result.addChild(new Diagnostic(Diagnostic.WARNING, DiagnosticType.PUBLISHER, e.getMessage()));
						result.taskDone();
						continue;
					}
					catch(ForgeException e) {
						result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PUBLISHER, e.getMessage()));
					}
					catch(Exception e) {
						result.addChild(new ExceptionDiagnostic(
							Diagnostic.ERROR, null, DiagnosticType.PUBLISHER, "Unable to publish module " +
									builtModule.getName(), e));
					}
					return;
				}

				if(noPublishingMade) {
					result.addChild(new Diagnostic(
						Diagnostic.INFO, DiagnosticType.PUBLISHER,
						"All modules have already been published at their current version"));
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
