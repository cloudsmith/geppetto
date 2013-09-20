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
package com.puppetlabs.geppetto.puppetdb.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.inject.Singleton;
import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.geppetto.puppetdb.PuppetDBManager;

@Singleton
public class PuppetDBManagerImpl implements PuppetDBManager {

	private static final String PUPPETDB_NODE = "PuppetDB";

	static Preferences getPuppetDBNode() {
		return instanceNode().node(PUPPETDB_NODE);
	}

	static boolean hasPuppetDBNode() throws BackingStoreException {
		return instanceNode().nodeExists(PUPPETDB_NODE);
	}

	private static Preferences instanceNode() {
		return Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE);
	}

	/**
	 * Adds a new node to the storage.
	 * 
	 * @param hostname
	 *            The hostname of the new instance
	 * @param port
	 *            The port of the new instance
	 * @throws BackingStoreException
	 * @throws IllegalArgumentException
	 *             if the node already exists
	 */
	@Override
	public PuppetDBConnectionPreferences add(String hostname, int port) throws BackingStoreException {
		PuppetDBConnectionPreferences pa = new PuppetDBConnectionPreferencesImpl(hostname, port);
		String ident = pa.getIdentifier();
		Preferences root = getPuppetDBNode();
		if(root.nodeExists(ident))
			throw new IllegalArgumentException("A node for " + ident + " already exists");
		root.node(ident);
		return pa;
	}

	@Override
	public void flush() throws BackingStoreException {
		getPuppetDBNode().flush();
	}

	/**
	 * Returns the instance identified by <code>hostname</code> and <code>port</code> or <code>null</code> if no such instance has been
	 * added to the storage.
	 * 
	 * @param hostname
	 *            The hostname of the wanted instance
	 * @param port
	 *            The port of the wanted instance
	 * @return The matching instance or <code>null</code>.
	 */
	@Override
	public PuppetDBConnectionPreferences get(String hostname, int port) throws BackingStoreException {
		if(hasPuppetDBNode()) {
			PuppetDBConnectionPreferences pa = new PuppetDBConnectionPreferencesImpl(hostname, port);
			if(getPuppetDBNode().nodeExists(pa.getIdentifier()))
				return pa;
		}
		return null;
	}

	/**
	 * Returns a list of known instances.
	 * 
	 * @return A list, potentially empty but never <code>null</code>.
	 */
	@Override
	public List<PuppetDBConnectionPreferences> getPuppetDBs() throws BackingStoreException {
		if(!hasPuppetDBNode())
			return Collections.emptyList();

		Preferences puppetDBNode = getPuppetDBNode();
		String[] childrenNames = puppetDBNode.childrenNames();
		if(childrenNames.length == 0)
			return Collections.emptyList();

		ArrayList<PuppetDBConnectionPreferences> result = new ArrayList<PuppetDBConnectionPreferences>(childrenNames.length);
		for(String prefName : puppetDBNode.childrenNames()) {
			int sep = prefName.lastIndexOf(':');
			result.add(new PuppetDBConnectionPreferencesImpl(prefName.substring(0, sep), Integer.parseInt(prefName.substring(sep + 1))));
		}
		return result;
	}
}
