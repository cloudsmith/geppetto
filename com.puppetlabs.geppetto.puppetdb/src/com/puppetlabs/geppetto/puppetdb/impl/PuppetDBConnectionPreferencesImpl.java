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

import static com.puppetlabs.geppetto.injectable.CommonModuleProvider.getCommonModule;
import static com.puppetlabs.geppetto.puppetdb.impl.PuppetDBManagerImpl.getPuppetDBNode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.inject.AbstractModule;
import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.puppetdb.javaclient.BasicAPIPreferences;
import com.puppetlabs.puppetdb.javaclient.PuppetDBClient;
import com.puppetlabs.puppetdb.javaclient.PuppetDBClientFactory;
import com.puppetlabs.puppetdb.javaclient.ssl.AbstractSSLSocketFactoryProvider;
import com.puppetlabs.puppetdb.javaclient.ssl.KeySpecFactory;

public class PuppetDBConnectionPreferencesImpl implements PuppetDBConnectionPreferences {
	private static final String PREF_CA_CERT = "ca-cert";

	private static final Charset ASCII = Charset.forName("ASCII");

	private static final String PREF_HOST_CERT = "host-cert";

	private static final String PREF_HOST_PRIVATE_KEY = "host-private-key";

	private final String hostname;

	private final int port;

	public PuppetDBConnectionPreferencesImpl(String hostname, int port) {
		super();
		this.hostname = hostname;
		this.port = port;
	}

	@Override
	public Certificate generateCaCertificate(CertificateFactory factory) throws CertificateException, IOException {
		return generateCertificate(PREF_CA_CERT, factory);
	}

	private Certificate generateCertificate(String prefName, CertificateFactory factory) throws CertificateException, IOException {
		try {
			String certString = getPreference(prefName);
			return certString == null
					? null
					: factory.generateCertificate(new ByteArrayInputStream(certString.getBytes(ASCII)));
		}
		catch(BackingStoreException e) {
			throw new IOException(e);
		}
	}

	@Override
	public Certificate generateHostCertificate(CertificateFactory factory) throws CertificateException, IOException {
		return generateCertificate(PREF_HOST_CERT, factory);
	}

	@Override
	public KeySpec generateHostPrivateKey() throws KeyException, IOException {
		try {
			String hostPrivateKey = getHostPrivateKey();
			if(hostPrivateKey == null)
				return null;

			return KeySpecFactory.readKeySpec(new BufferedReader(new StringReader(hostPrivateKey)), "PuppetDB host private key preference");
		}
		catch(BackingStoreException e) {
			throw new IOException(e);
		}
	}

	public String getCaCert() throws BackingStoreException {
		return getPreference(PREF_CA_CERT);
	}

	@Override
	public PuppetDBClient getClient() throws BackingStoreException {
		BasicAPIPreferences prefs = new BasicAPIPreferences();
		prefs.setServiceHostname(getHostname());
		prefs.setServicePort(getPort());
		prefs.setAllowAllHosts(true);
		PuppetDBClient client;
		if(getHostCert() == null)
			client = PuppetDBClientFactory.newClient(prefs, getCommonModule());
		else {
			client = PuppetDBClientFactory.newClient(prefs, getCommonModule(), new AbstractModule() {
				@Override
				protected void configure() {
					bind(SSLSocketFactory.class).toProvider(new AbstractSSLSocketFactoryProvider() {
						@Override
						protected Certificate getCACertificate(CertificateFactory factory) throws IOException, GeneralSecurityException {
							return generateCaCertificate(factory);
						}

						@Override
						protected Certificate getHostCertificate(CertificateFactory factory) throws IOException, GeneralSecurityException {
							return generateHostCertificate(factory);
						}

						@Override
						protected KeySpec getPrivateKeySpec() throws KeyException, IOException {
							return generateHostPrivateKey();
						}
					});
				}
			});
		}
		return client;
	}

	public String getHostCert() throws BackingStoreException {
		return getPreference(PREF_HOST_CERT);
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	public String getHostPrivateKey() throws BackingStoreException {
		return getPreference(PREF_HOST_PRIVATE_KEY);
	}

	@Override
	public String getIdentifier() {
		return hostname + ':' + port;
	}

	@Override
	public int getPort() {
		return port;
	}

	private String getPreference(String prefName) throws BackingStoreException {
		return getPreferences().get(prefName, null);
	}

	private Preferences getPreferences() {
		return getPuppetDBNode().node(getIdentifier());
	}

	@Override
	public void remove() throws BackingStoreException {
		getPreferences().removeNode();
	}

	@Override
	public void setCaCert(String pemContent) throws BackingStoreException {
		setPreference(PREF_CA_CERT, pemContent);
	}

	@Override
	public void setHostCert(String pemContent) throws BackingStoreException {
		setPreference(PREF_HOST_CERT, pemContent);
	}

	@Override
	public void setHostPrivateKey(String pemContent) throws BackingStoreException {
		setPreference(PREF_HOST_PRIVATE_KEY, pemContent);
	}

	private void setPreference(String prefName, String prefValue) throws BackingStoreException {
		Preferences prefs = getPreferences();
		if(prefValue == null)
			prefs.remove(prefName);
		else
			prefs.put(prefName, prefValue);
	}
}
