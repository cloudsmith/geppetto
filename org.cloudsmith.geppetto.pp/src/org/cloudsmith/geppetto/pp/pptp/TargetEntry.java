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
package org.cloudsmith.geppetto.pp.pptp;

import java.io.File;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Entry</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getDescription <em>Description</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getFile <em>File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getFunctions <em>Functions</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getTypes <em>Types</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getTypeFragments <em>Type Fragments</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getMetaType <em>Meta Type</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry()
 * @model abstract="true"
 * @generated
 */
public interface TargetEntry extends INamed {
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
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(File)
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_File()
	 * @model dataType="org.cloudsmith.geppetto.pp.pptp.File"
	 * @generated
	 */
	File getFile();

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Function> getFunctions();

	/**
	 * Returns the value of the '<em><b>Meta Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A meta type is a definition that applies to all defined types.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Meta Type</em>' containment reference.
	 * @see #setMetaType(Type)
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_MetaType()
	 * @model containment="true"
	 * @generated
	 */
	Type getMetaType();

	/**
	 * Returns the value of the '<em><b>Type Fragments</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.TypeFragment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Fragments</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type Fragments</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_TypeFragments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeFragment> getTypeFragments();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

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
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetEntry_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getDescription <em>Description</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(File value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getMetaType <em>Meta Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Meta Type</em>' containment reference.
	 * @see #getMetaType()
	 * @generated
	 */
	void setMetaType(Type value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.TargetEntry#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // TargetEntry
