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
package com.puppetlabs.geppetto.puppetdb;

import java.util.List;

import org.osgi.service.prefs.BackingStoreException;

public interface PuppetDBManager {
	PuppetDBConnectionPreferences add(String hostname, int port) throws BackingStoreException;

	void flush() throws BackingStoreException;

	Object get(String value, int parseInt) throws BackingStoreException;

	List<PuppetDBConnectionPreferences> getPuppetDBs() throws BackingStoreException;
}
