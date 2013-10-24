/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.Files;

/**
 * Endpoint capable of performing downloads
 */
public class DefaultFiles implements Files {
	@Inject
	private ForgeClient client;

	@Override
	public InputStream download(VersionedName release) throws IOException {
		StringBuilder bld = new StringBuilder("files");
		bld.append('/');
		release.getModuleName().withSeparator('-').toString(bld);
		bld.append('-');
		release.getVersion().toString(bld);
		bld.append(".tar.gz");
		return client.download(bld.toString(), null);
	}

	@Override
	public void download(VersionedName release, OutputStream output) throws IOException {
		StringBuilder bld = new StringBuilder("files");
		bld.append('/');
		release.getModuleName().withSeparator('-').toString(bld);
		bld.append('-');
		release.getVersion().toString(bld);
		bld.append(".tar.gz");
		client.download(bld.toString(), null, output);
	}
}
