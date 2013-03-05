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
package org.cloudsmith.geppetto.forge;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 * 
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForgeService()
 * @model
 * @generated
 */
public interface ForgeService extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * @param cacheDirectory
	 *            The local cache directory
	 * @param repository
	 *            The remote repository
	 *            <!-- end-model-doc -->
	 * @model cacheDirectoryDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	Cache createCache(File cacheDirectory, Repository repository);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtain a service instance that gives you access to the module repository at the given location.
	 * 
	 * @param repository
	 *            The remote repository
	 * @param cache
	 *            The local cache
	 *            <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	Forge createForge(Repository repository, Cache cache);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Creates an instance that lets you create, publish, and consume modules.
	 * 
	 * @param repository
	 *            The location of the repository
	 *            <!-- end-model-doc -->
	 * @model repositoryDataType="org.cloudsmith.geppetto.forge.URI"
	 * @generated
	 */
	Forge createForge(URI repository);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtain a service instance that gives you access to the module repository at the given location.
	 * 
	 * @param repository
	 *            The location of the repository
	 * @param cacheDirectory
	 *            The local cache directory
	 *            <!-- end-model-doc -->
	 * @model repositoryDataType="org.cloudsmith.geppetto.forge.URI" cacheDirectoryDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	Forge createForge(URI repository, File cacheDirectory);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	Metadata createMetadata(String fullName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model uriDataType="org.cloudsmith.geppetto.forge.URI"
	 * @generated
	 */
	Repository createRepository(URI uri);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" jsonFileDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	Metadata loadJSONMetadata(File jsonFile) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * @param exclusionFilter
	 *            A filter that can be used to exclude files and directories
	 *            <!-- end-model-doc -->
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" moduleDirectoryDataType="org.cloudsmith.geppetto.forge.File"
	 *        exclusionFilterDataType="org.cloudsmith.geppetto.forge.FileFilter"
	 * @generated
	 */
	Metadata loadModule(File moduleDirectory, FileFilter exclusionFilter) throws IOException;

} // ForgeService
