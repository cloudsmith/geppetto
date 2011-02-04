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
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForge()
 * @model
 * @generated
 */
public interface Forge extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Build a module for release. The end result is a gzipped tar file (.tar.gz) archive that
	 * contains the module source and a freshly generated metadata.json. This method
	 * will replace the metadata.json that resides at the root of the module source
	 * if it is already present.
	 * 
	 * @param moduleSource
	 *            The module directory
	 * @param destination
	 *            The directory where the created archive will end up. Created if necessary.
	 *            <!-- end-model-doc -->
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException org.cloudsmith.geppetto.forge.IncompleteException"
	 *        moduleSourceDataType="org.cloudsmith.geppetto.forge.File" destinationDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	Metadata build(File moduleSource, File destination) throws IOException, IncompleteException;

	/**
	 * <!-- begin-model-doc -->
	 * List modified files in an installed module
	 * 
	 * @param path
	 *            The module directory
	 *            <!-- end-model-doc -->
	 * @model dataType="org.cloudsmith.geppetto.forge.File" pathDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	List<File> changes(File path) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Generate boilerplate for a new module
	 * 
	 * @param destination
	 *            The module directory
	 * @param metadata
	 *            The name of the module
	 *            <!-- end-model-doc -->
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" destinationDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void generate(File destination, Metadata metadata) throws IOException;

	/**
	 * Returns the value of the '<em><b>Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cache</em>' reference.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForge_Cache()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false"
	 * @generated
	 */
	Cache getCache();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException"
	 * @generated
	 */
	ReleaseInfo getRelease(String fullName) throws IOException;

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' attribute.
	 * <!-- begin-model-doc -->
	 * The repository location
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Repository</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForge_Repository()
	 * @model dataType="org.cloudsmith.geppetto.forge.URI" required="true" changeable="false"
	 * @generated
	 */
	Repository getRepository();

	/**
	 * Returns the value of the '<em><b>Service</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Service</em>' reference.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForge_Service()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false"
	 * @generated
	 */
	ForgeService getService();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-model-doc -->
	 * The version of the repository service
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getForge_Version()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getVersion();

	/**
	 * <!-- begin-model-doc -->
	 * Install a module (eg, 'user-modname') from a repository or file. A
	 * module is an archive that contains one single folder. In some cases,
	 * like when installing into a pre-existing workspace project, it's
	 * desirable to skip this folder and instead expand everything beneath
	 * it into the given <code>destination</code>. This behavior can be
	 * enforced by setting the <code>destinationIncludesTopFolder</code> to <code>true</code>.
	 * 
	 * @param fullName
	 *            The name of the module
	 * @param destination
	 *            The destination for the install.
	 * @param destinationIncludesTopFolder
	 *            When <code>true</code>, assume that all content beneath the
	 *            top folder in the archive should be installed directly beneath the
	 *            given <code>destination</code>. When this flag is <code>false</code> the top folder of the archive will be expanded as-is beneath
	 *            the <code>destination</code>.
	 * @param force
	 *            Set to <code>true</code> to overwrite an existing module.
	 *            <!-- end-model-doc -->
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" destinationDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	Metadata install(String fullName, File destination, boolean destinationIncludesTopFolder, boolean force)
			throws IOException;

	/**
	 * <!-- begin-model-doc -->
	 * Search the module repository for a module matching <code>term</code>
	 * 
	 * @param term
	 *            Search term
	 *            <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	List<ModuleInfo> search(String term) throws IOException;

} // Forge
