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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Match Rule</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getMatchRule()
 * @model
 * @generated
 */
public enum MatchRule implements Enumerator {
	/**
	 * The '<em><b>PERFECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Version must match exactly the specified version.
	 * <!-- end-user-doc -->
	 * 
	 * @see #PERFECT_VALUE
	 * @generated
	 * @ordered
	 */
	PERFECT(0, "PERFECT", "PERFECT"),

	/**
	 * The '<em><b>EQUIVALENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Version must be at least at the version specified, or at a higher service level (major and minor version levels must equal the specified
	 * version).
	 * <!-- end-user-doc -->
	 * 
	 * @see #EQUIVALENT_VALUE
	 * @generated
	 * @ordered
	 */
	EQUIVALENT(1, "EQUIVALENT", "EQUIVALENT"),

	/**
	 * The '<em><b>COMPATIBLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Version must be at least at the version specified, or at a higher service level or minor level (major version level must equal the specified
	 * version).
	 * <!-- end-user-doc -->
	 * 
	 * @see #COMPATIBLE_VALUE
	 * @generated
	 * @ordered
	 */
	COMPATIBLE(2, "COMPATIBLE", "COMPATIBLE"),

	/**
	 * The '<em><b>GREATER OR EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * Version must be at least at the version specified, or at a higher service, minor or major level.
	 * <!-- end-user-doc -->
	 * 
	 * @see #GREATER_OR_EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER_OR_EQUAL(3, "GREATER_OR_EQUAL", "GREATER_OR_EQUAL");

	/**
	 * The '<em><b>PERFECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Version must match exactly the specified version.
	 * <!-- end-model-doc -->
	 * 
	 * @see #PERFECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PERFECT_VALUE = 0;

	/**
	 * The '<em><b>EQUIVALENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Version must be at least at the version specified, or at a higher service level (major and minor version levels must equal the specified
	 * version).
	 * <!-- end-model-doc -->
	 * 
	 * @see #EQUIVALENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EQUIVALENT_VALUE = 1;

	/**
	 * The '<em><b>COMPATIBLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Version must be at least at the version specified, or at a higher service level or minor level (major version level must equal the specified
	 * version).
	 * <!-- end-model-doc -->
	 * 
	 * @see #COMPATIBLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COMPATIBLE_VALUE = 2;

	/**
	 * The '<em><b>GREATER OR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Version must be at least at the version specified, or at a higher service, minor or major level.
	 * <!-- end-model-doc -->
	 * 
	 * @see #GREATER_OR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_OR_EQUAL_VALUE = 3;

	/**
	 * An array of all the '<em><b>Match Rule</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final MatchRule[] VALUES_ARRAY = new MatchRule[] {
			PERFECT, EQUIVALENT, COMPATIBLE, GREATER_OR_EQUAL, };

	/**
	 * A public read-only list of all the '<em><b>Match Rule</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<MatchRule> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Match Rule</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static MatchRule get(int value) {
		switch(value) {
			case PERFECT_VALUE:
				return PERFECT;
			case EQUIVALENT_VALUE:
				return EQUIVALENT;
			case COMPATIBLE_VALUE:
				return COMPATIBLE;
			case GREATER_OR_EQUAL_VALUE:
				return GREATER_OR_EQUAL;
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Match Rule</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static MatchRule get(String literal) {
		for(int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatchRule result = VALUES_ARRAY[i];
			if(result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Match Rule</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static MatchRule getByName(String name) {
		for(int i = 0; i < VALUES_ARRAY.length; ++i) {
			MatchRule result = VALUES_ARRAY[i];
			if(result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private MatchRule(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // MatchRule
