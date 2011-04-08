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
package org.cloudsmith.geppetto.forge.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.Repository;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Repository</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.RepositoryImpl#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.RepositoryImpl#getCacheKey <em>Cache Key</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RepositoryImpl extends EObjectImpl implements Repository {
	/**
	 * The default value of the '{@link #getRepository() <em>Repository</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected static final URI REPOSITORY_EDEFAULT = null;

	public static void appendHex(StringBuilder bld, byte b) {
		bld.append(hexChars[(b & 0xf0) >> 4]);
		bld.append(hexChars[b & 0x0f]);
	}

	private static HttpURLConnection checkResponse(HttpURLConnection conn) throws IOException {
		int responseCode = conn.getResponseCode();
		//
		// When S3 is overloaded or having other problems of a transient
		// nature, it tends to return this error. since this can probably
		// be fixed by retry, we throw it as an exception
		if(responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
			throw new IOException(conn.getURL() + ": internal server error");
		}
		if(responseCode == HttpURLConnection.HTTP_UNAVAILABLE) {
			throw new IOException(conn.getURL() + ": service unavailable");
		}
		if(responseCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
			throw new IOException(conn.getURL() + ": gateway timeout");
		}

		if(responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
			throw new FileNotFoundException(conn.getURL() + ": " + conn.getURL());
		}

		// 2xx response codes are ok, everything else is an error
		if(responseCode / 100 == 2)
			return conn;

		StringBuilder bld = new StringBuilder();
		bld.append(conn.getURL());
		bld.append(": response code ");
		bld.append(responseCode);

		InputStream errorData = conn.getErrorStream();
		// Some errors, like Service Unavailable, are produced by the
		// container and not S3, so they may not include an error stream.
		if(errorData != null) {
			// Here we use our simple SAX parser to pull the message field
			// out of the error xml S3 returns to us
			Reader rdr = new InputStreamReader(errorData, getEncoding(conn));
			char[] buf = new char[1024];
			int cnt;
			bld.append(": ");
			while((cnt = rdr.read(buf)) > 0)
				bld.append(buf, 0, cnt);
		}
		throw new IOException(bld.toString());
	}

	static String getEncoding(URLConnection conn) {
		String encoding = conn.getContentEncoding();
		if(encoding == null)
			encoding = "UTF-8";
		return encoding;
	}

	/**
	 * The cached value of the '{@link #getRepository() <em>Repository</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected URI repository = REPOSITORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getCacheKey() <em>Cache Key</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCacheKey()
	 * @generated
	 * @ordered
	 */
	protected static final String CACHE_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCacheKey() <em>Cache Key</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCacheKey()
	 * @generated
	 * @ordered
	 */
	protected String cacheKey = CACHE_KEY_EDEFAULT;

	private static char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public HttpURLConnection connect(HttpMethod method, String urlSuffix) throws IOException {
		try {
			StringBuilder bld = new StringBuilder(repository.toString());
			int len = bld.length();
			while(len > 0 && bld.charAt(len - 1) == '/') {
				--len;
				bld.setLength(len);
			}
			if(urlSuffix != null && urlSuffix.length() > 0) {
				int start = 0;
				while(urlSuffix.charAt(start) == '/')
					++start;
				bld.append('/');
				bld.append(urlSuffix, start, urlSuffix.length());
			}
			URL url = new URL(bld.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method.getName());
			conn.connect();
			return checkResponse(conn);
		}
		catch(MalformedURLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case ForgePackage.REPOSITORY__REPOSITORY:
				return getRepository();
			case ForgePackage.REPOSITORY__CACHE_KEY:
				return getCacheKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case ForgePackage.REPOSITORY__REPOSITORY:
				return REPOSITORY_EDEFAULT == null
						? repository != null
						: !REPOSITORY_EDEFAULT.equals(repository);
			case ForgePackage.REPOSITORY__CACHE_KEY:
				return CACHE_KEY_EDEFAULT == null
						? cacheKey != null
						: !CACHE_KEY_EDEFAULT.equals(cacheKey);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public synchronized String getCacheKey() {
		if(cacheKey == null) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				String uriStr = repository.toString();
				StringBuilder bld = new StringBuilder(uriStr.replaceAll("[^\\p{Alnum}]+", "_"));
				int last = bld.length() - 1;
				if(bld.charAt(last) == '_')
					bld.setLength(last);
				bld.append('-');
				byte[] digest = md.digest(uriStr.getBytes("UTF-8"));
				for(int idx = 0; idx < digest.length; ++idx)
					appendHex(bld, digest[idx]);
				cacheKey = bld.toString();
			}
			catch(UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			catch(NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		return cacheKey;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public URI getRepository() {
		return repository;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if(eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (repository: ");
		result.append(repository);
		result.append(", cacheKey: ");
		result.append(cacheKey);
		result.append(')');
		return result.toString();
	}
} // RepositoryImpl
