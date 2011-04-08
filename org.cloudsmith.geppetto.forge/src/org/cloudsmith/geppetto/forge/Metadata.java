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
import java.net.URI;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metadata</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getUser <em>User</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getFullName <em>Full Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getLocation <em>Location</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getSource <em>Source</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getAuthor <em>Author</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getLicense <em>License</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getTypes <em>Types</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getSummary <em>Summary</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getDescription <em>Description</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getProjectPage <em>Project Page</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getChecksums <em>Checksums</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata()
 * @model
 * @generated
 */
public interface Metadata extends EObject {
	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Author()
	 * @model
	 * @generated
	 */
	String getAuthor();

	/**
	 * Returns the value of the '<em><b>Checksums</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Checksums</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Checksums</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Checksums()
	 * @model dataType="org.cloudsmith.geppetto.forge.Map<org.eclipse.emf.ecore.EString, org.cloudsmith.geppetto.forge.byteArray>" changeable="false"
	 * @generated
	 */
	Map<String, byte[]> getChecksums();

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.forge.Dependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dependency> getDependencies();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Returns the value of the '<em><b>Full Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Full Name</em>' attribute.
	 * @see #setFullName(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_FullName()
	 * @model required="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getFullName();

	/**
	 * Returns the value of the '<em><b>License</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>License</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>License</em>' attribute.
	 * @see #setLicense(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_License()
	 * @model
	 * @generated
	 */
	String getLicense();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Location()
	 * @model dataType="org.cloudsmith.geppetto.forge.File" transient="true" changeable="false"
	 * @generated
	 */
	File getLocation();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Project Page</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Page</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project Page</em>' attribute.
	 * @see #setProjectPage(URI)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_ProjectPage()
	 * @model dataType="org.cloudsmith.geppetto.forge.URI"
	 * @generated
	 */
	URI getProjectPage();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Source()
	 * @model
	 * @generated
	 */
	String getSource();

	/**
	 * Returns the value of the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Summary</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Summary</em>' attribute.
	 * @see #setSummary(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Summary()
	 * @model
	 * @generated
	 */
	String getSummary();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.forge.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_User()
	 * @model required="true"
	 * @generated
	 */
	String getUser();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMetadata_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" moduleDirDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void loadChecksums(File moduleDir) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" moduleFileDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void loadModuleFile(File moduleFile) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" puppetDirDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void loadTypeFiles(File puppetDir) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	VersionRequirement parseVersionRequirement(String versionRequirement);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" jsonFileDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void saveJSONMetadata(File jsonFile) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" moduleFileDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void saveModulefile(File moduleFile) throws IOException;

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getFullName <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Full Name</em>' attribute.
	 * @see #getFullName()
	 * @generated
	 */
	void setFullName(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getLicense <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>License</em>' attribute.
	 * @see #getLicense()
	 * @generated
	 */
	void setLicense(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getProjectPage <em>Project Page</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Project Page</em>' attribute.
	 * @see #getProjectPage()
	 * @generated
	 */
	void setProjectPage(URI value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getSummary <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Summary</em>' attribute.
	 * @see #getSummary()
	 * @generated
	 */
	void setSummary(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Metadata#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // Metadata
