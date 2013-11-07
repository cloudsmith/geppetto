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

import java.io.IOException;
import java.security.KeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;

import org.osgi.service.prefs.BackingStoreException;

import com.puppetlabs.puppetdb.javaclient.PuppetDBClient;

/**
 * Holds all preferences used when connecting a connection is established to
 * a PuppetDB instance by means of a {@link PuppetDBClient}.
 */
public interface PuppetDBConnectionPreferences {
	/**
	 * Generates the certificate for the Certificate Authority using the given <code>factory</code>. This
	 * method may return <code>null</code> if no such certificate has been set.
	 * 
	 * @param factory
	 *            The factory to use for the generation
	 * @return The generated certificate or <code>null</code> if no certificat has been assigned.
	 * @throws CertificateException
	 * @throws IOException
	 * @throws StorageException
	 */
	Certificate generateCaCertificate(CertificateFactory factory) throws CertificateException, IOException, BackingStoreException;

	Certificate generateHostCertificate(CertificateFactory factory) throws CertificateException, IOException, BackingStoreException;

	KeySpec generateHostPrivateKey() throws KeyException, IOException, BackingStoreException;

	PuppetDBClient getClient() throws BackingStoreException;

	String getHostname();

	String getIdentifier();

	int getPort();

	void remove() throws BackingStoreException;

	void setCaCert(String pemContent) throws BackingStoreException;

	void setHostCert(String pemContent) throws BackingStoreException;

	void setHostPrivateKey(String pemContent) throws BackingStoreException;
}
