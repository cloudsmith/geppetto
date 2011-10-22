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

import java.util.Comparator;

import org.cloudsmith.geppetto.forge.impl.VersionRequirementImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.VersionRequirement#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.VersionRequirement#getMatchRule <em>Match Rule</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getVersionRequirement()
 * @model
 * @generated
 */
public interface VersionRequirement extends EObject {
	/**
	 * Comparator for versions.
	 */
	static Comparator<String> VERSION_COMPARATOR = new VersionRequirementImpl.VersionComparator();

	static VersionRequirement EMPTY_REQUIREMENT = VersionRequirementImpl.parseVersionRequirement(">= 0");

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model versionsDataType="org.cloudsmith.geppetto.forge.Iterable<org.eclipse.emf.ecore.EString>" versionsRequired="true"
	 * @generated
	 */
	String findBestMatch(Iterable<String> versions);

	/**
	 * Returns the value of the '<em><b>Match Rule</b></em>' attribute.
	 * The literals are from the enumeration {@link org.cloudsmith.geppetto.forge.MatchRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Match Rule</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Match Rule</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.MatchRule
	 * @see #setMatchRule(MatchRule)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getVersionRequirement_MatchRule()
	 * @model required="true"
	 * @generated
	 */
	MatchRule getMatchRule();

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
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getVersionRequirement_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model versionRequired="true"
	 * @generated
	 */
	boolean matches(String version);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.VersionRequirement#getMatchRule <em>Match Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Match Rule</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.MatchRule
	 * @see #getMatchRule()
	 * @generated
	 */
	void setMatchRule(MatchRule value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.VersionRequirement#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // VersionRequirement
