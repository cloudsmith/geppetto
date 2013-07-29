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
package org.cloudsmith.geppetto.pp.dsl.ui.internal;

import org.cloudsmith.geppetto.pp.dsl.ui.pptp.PptpTargetProjectHandler;
import org.eclipse.ui.IStartup;

import com.google.inject.Inject;

/**
 * Checks the workspace state.
 * Pariticipates early in the startup sequence. (Needs to run before user hits a "build" that would
 * otherwise initializes this bundle).
 * 
 */
public class PPStartup implements IStartup {

	@Inject
	PptpTargetProjectHandler pptpHandler;

	@Override
	public void earlyStartup() {
		// make sure all projects are ok
		pptpHandler.initializePuppetWorkspace();
	}
}
