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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.Repository;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Cache</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.CacheImpl#getLocation <em>Location</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.CacheImpl#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CacheImpl extends EObjectImpl implements Cache {
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final File LOCATION_EDEFAULT = null;

	private static void delete(File fileOrDir) throws IOException {
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				delete(child);
		if(!fileOrDir.delete() && fileOrDir.exists())
			throw new IOException("Unable to delete " + fileOrDir);
	}

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected File location = LOCATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRepository() <em>Repository</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected Repository repository;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CacheImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void clean() throws IOException {
		delete(getLocation());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case ForgePackage.CACHE__LOCATION:
				return getLocation();
			case ForgePackage.CACHE__REPOSITORY:
				return getRepository();
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
			case ForgePackage.CACHE__LOCATION:
				return LOCATION_EDEFAULT == null
						? location != null
						: !LOCATION_EDEFAULT.equals(location);
			case ForgePackage.CACHE__REPOSITORY:
				return repository != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public synchronized File getLocation() {
		if(location == null) {
			String userHome = System.getProperty("user.home");
			if(userHome == null)
				throw new RuntimeException("Unable to obtain users home directory");
			File dir = new File(userHome);
			if(!dir.isDirectory())
				throw new RuntimeException(userHome + " is not a directory");
			location = new File(new File(dir, ".puppet/var/puppet-module/cache"), repository.getCacheKey());
		}
		return location;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Repository getRepository() {
		return repository;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public File retrieve(String fileName) throws IOException {
		// This cache assumes that all leaf names are unique so we don't want
		// to preserve the folder structure
		String leaf = new File(fileName).getName();
		File cachedFile = new File(getLocation(), leaf);
		if(!cachedFile.exists()) {
			File dir = cachedFile.getParentFile();
			dir.mkdirs();
			InputStream in = repository.connect(HttpMethod.GET, fileName).getInputStream();
			try {
				FileUtils.cp(in, dir, leaf);
			}
			finally {
				StreamUtil.close(in);
			}
		}
		return cachedFile;
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
		result.append(" (location: ");
		result.append(location);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.CACHE;
	}

} // CacheImpl
