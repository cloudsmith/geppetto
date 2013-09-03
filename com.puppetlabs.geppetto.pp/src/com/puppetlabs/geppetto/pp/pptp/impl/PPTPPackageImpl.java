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
package com.puppetlabs.geppetto.pp.pptp.impl;

import java.io.File;

import com.puppetlabs.geppetto.pp.pptp.AbstractType;
import com.puppetlabs.geppetto.pp.pptp.Function;
import com.puppetlabs.geppetto.pp.pptp.IDocumented;
import com.puppetlabs.geppetto.pp.pptp.INamed;
import com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer;
import com.puppetlabs.geppetto.pp.pptp.MetaType;
import com.puppetlabs.geppetto.pp.pptp.MetaVariable;
import com.puppetlabs.geppetto.pp.pptp.NameSpace;
import com.puppetlabs.geppetto.pp.pptp.PPTPFactory;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import com.puppetlabs.geppetto.pp.pptp.Parameter;
import com.puppetlabs.geppetto.pp.pptp.Property;
import com.puppetlabs.geppetto.pp.pptp.PuppetDistribution;
import com.puppetlabs.geppetto.pp.pptp.TPVariable;
import com.puppetlabs.geppetto.pp.pptp.TargetElement;
import com.puppetlabs.geppetto.pp.pptp.TargetEntry;
import com.puppetlabs.geppetto.pp.pptp.Type;
import com.puppetlabs.geppetto.pp.pptp.TypeArgument;
import com.puppetlabs.geppetto.pp.pptp.TypeFragment;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PPTPPackageImpl extends EPackageImpl implements PPTPPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass targetEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass puppetDistributionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass abstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass metaTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nameSpaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tpVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass iTargetElementContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass metaVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass iDocumentedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass iNamedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass targetElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType fileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link PPTPPackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PPTPPackage init() {
		if(isInited)
			return (PPTPPackage) EPackage.Registry.INSTANCE.getEPackage(PPTPPackage.eNS_URI);

		// Obtain or create and register package
		PPTPPackageImpl thePPTPPackage = (PPTPPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PPTPPackageImpl
				? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new PPTPPackageImpl());

		isInited = true;

		// Create package meta-data objects
		thePPTPPackage.createPackageContents();

		// Initialize created meta-data
		thePPTPPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePPTPPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PPTPPackage.eNS_URI, thePPTPPackage);
		return thePPTPPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PPTPPackageImpl() {
		super(eNS_URI, PPTPFactory.eINSTANCE);
	}

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if(isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		targetEntryEClass = createEClass(TARGET_ENTRY);
		createEAttribute(targetEntryEClass, TARGET_ENTRY__DESCRIPTION);
		createEReference(targetEntryEClass, TARGET_ENTRY__FUNCTIONS);
		createEReference(targetEntryEClass, TARGET_ENTRY__TYPES);
		createEAttribute(targetEntryEClass, TARGET_ENTRY__VERSION);
		createEReference(targetEntryEClass, TARGET_ENTRY__TYPE_FRAGMENTS);
		createEReference(targetEntryEClass, TARGET_ENTRY__META_TYPE);
		createEAttribute(targetEntryEClass, TARGET_ENTRY__LABEL);
		createEReference(targetEntryEClass, TARGET_ENTRY__META_VARIABLES);

		puppetDistributionEClass = createEClass(PUPPET_DISTRIBUTION);

		functionEClass = createEClass(FUNCTION);
		createEAttribute(functionEClass, FUNCTION__RVALUE);

		abstractTypeEClass = createEClass(ABSTRACT_TYPE);
		createEReference(abstractTypeEClass, ABSTRACT_TYPE__EREFERENCE0);
		createEReference(abstractTypeEClass, ABSTRACT_TYPE__PROPERTIES);
		createEReference(abstractTypeEClass, ABSTRACT_TYPE__PARAMETERS);

		iDocumentedEClass = createEClass(IDOCUMENTED);
		createEAttribute(iDocumentedEClass, IDOCUMENTED__DOCUMENTATION);

		iNamedEClass = createEClass(INAMED);
		createEAttribute(iNamedEClass, INAMED__NAME);

		targetElementEClass = createEClass(TARGET_ELEMENT);
		createEAttribute(targetElementEClass, TARGET_ELEMENT__DEPRECATED);

		propertyEClass = createEClass(PROPERTY);

		parameterEClass = createEClass(PARAMETER);

		typeFragmentEClass = createEClass(TYPE_FRAGMENT);

		typeEClass = createEClass(TYPE);
		createEAttribute(typeEClass, TYPE__SUPER_TYPE);

		metaTypeEClass = createEClass(META_TYPE);

		typeArgumentEClass = createEClass(TYPE_ARGUMENT);
		createEAttribute(typeArgumentEClass, TYPE_ARGUMENT__REQUIRED);
		createEAttribute(typeArgumentEClass, TYPE_ARGUMENT__NAMEVAR);

		nameSpaceEClass = createEClass(NAME_SPACE);
		createEAttribute(nameSpaceEClass, NAME_SPACE__RESERVED);

		tpVariableEClass = createEClass(TP_VARIABLE);
		createEAttribute(tpVariableEClass, TP_VARIABLE__ASSIGNABLE);
		createEAttribute(tpVariableEClass, TP_VARIABLE__PATTERN);

		iTargetElementContainerEClass = createEClass(ITARGET_ELEMENT_CONTAINER);
		createEReference(iTargetElementContainerEClass, ITARGET_ELEMENT_CONTAINER__CONTENTS);

		metaVariableEClass = createEClass(META_VARIABLE);

		// Create data types
		fileEDataType = createEDataType(FILE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAbstractType() {
		return abstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAbstractType_EReference0() {
		return (EReference) abstractTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAbstractType_Parameters() {
		return (EReference) abstractTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAbstractType_Properties() {
		return (EReference) abstractTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getFile() {
		return fileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFunction_RValue() {
		return (EAttribute) functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIDocumented() {
		return iDocumentedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getIDocumented_Documentation() {
		return (EAttribute) iDocumentedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getINamed() {
		return iNamedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getINamed_Name() {
		return (EAttribute) iNamedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getITargetElementContainer() {
		return iTargetElementContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getITargetElementContainer_Contents() {
		return (EReference) iTargetElementContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMetaType() {
		return metaTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMetaVariable() {
		return metaVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNameSpace() {
		return nameSpaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNameSpace_Reserved() {
		return (EAttribute) nameSpaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PPTPFactory getPPTPFactory() {
		return (PPTPFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPuppetDistribution() {
		return puppetDistributionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTargetElement() {
		return targetElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTargetElement_Deprecated() {
		return (EAttribute) targetElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTargetEntry() {
		return targetEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTargetEntry_Description() {
		return (EAttribute) targetEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTargetEntry_Functions() {
		return (EReference) targetEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTargetEntry_Label() {
		return (EAttribute) targetEntryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTargetEntry_MetaType() {
		return (EReference) targetEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTargetEntry_MetaVariables() {
		return (EReference) targetEntryEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTargetEntry_TypeFragments() {
		return (EReference) targetEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTargetEntry_Types() {
		return (EReference) targetEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTargetEntry_Version() {
		return (EAttribute) targetEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTPVariable() {
		return tpVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTPVariable_Assignable() {
		return (EAttribute) tpVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTPVariable_Pattern() {
		return (EAttribute) tpVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getType_SuperType() {
		return (EAttribute) typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeArgument() {
		return typeArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTypeArgument_Namevar() {
		return (EAttribute) typeArgumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTypeArgument_Required() {
		return (EAttribute) typeArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeFragment() {
		return typeFragmentEClass;
	}

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if(isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		targetEntryEClass.getESuperTypes().add(this.getITargetElementContainer());
		puppetDistributionEClass.getESuperTypes().add(this.getTargetEntry());
		functionEClass.getESuperTypes().add(this.getIDocumented());
		functionEClass.getESuperTypes().add(this.getTargetElement());
		abstractTypeEClass.getESuperTypes().add(this.getTargetElement());
		targetElementEClass.getESuperTypes().add(this.getINamed());
		targetElementEClass.getESuperTypes().add(this.getIDocumented());
		propertyEClass.getESuperTypes().add(this.getTypeArgument());
		parameterEClass.getESuperTypes().add(this.getTypeArgument());
		typeFragmentEClass.getESuperTypes().add(this.getAbstractType());
		typeEClass.getESuperTypes().add(this.getAbstractType());
		metaTypeEClass.getESuperTypes().add(this.getAbstractType());
		typeArgumentEClass.getESuperTypes().add(this.getTargetElement());
		nameSpaceEClass.getESuperTypes().add(this.getTargetElement());
		nameSpaceEClass.getESuperTypes().add(this.getITargetElementContainer());
		tpVariableEClass.getESuperTypes().add(this.getTargetElement());
		metaVariableEClass.getESuperTypes().add(this.getTargetElement());

		// Initialize classes and features; add operations and parameters
		initEClass(
			targetEntryEClass, TargetEntry.class, "TargetEntry", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTargetEntry_Description(), ecorePackage.getEString(), "description", null, 0, 1, TargetEntry.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTargetEntry_Functions(), this.getFunction(), null, "functions", null, 0, -1, TargetEntry.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(
			getTargetEntry_Types(), this.getType(), null, "types", null, 0, -1, TargetEntry.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(
			getTargetEntry_Version(), ecorePackage.getEString(), "version", null, 0, 1, TargetEntry.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTargetEntry_TypeFragments(), this.getTypeFragment(), null, "typeFragments", null, 0, -1,
			TargetEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTargetEntry_MetaType(), this.getMetaType(), null, "metaType", null, 0, 1, TargetEntry.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTargetEntry_Label(), ecorePackage.getEString(), "label", null, 0, 1, TargetEntry.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTargetEntry_MetaVariables(), this.getMetaVariable(), null, "metaVariables", null, 0, -1,
			TargetEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			puppetDistributionEClass, PuppetDistribution.class, "PuppetDistribution", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(functionEClass, Function.class, "Function", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getFunction_RValue(), ecorePackage.getEBoolean(), "rValue", null, 0, 1, Function.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			abstractTypeEClass, AbstractType.class, "AbstractType", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getAbstractType_EReference0(), this.getIDocumented(), null, "EReference0", null, 0, 1, AbstractType.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(
			getAbstractType_Properties(), this.getProperty(), null, "properties", null, 0, -1, AbstractType.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(
			getAbstractType_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, AbstractType.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(
			iDocumentedEClass, IDocumented.class, "IDocumented", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getIDocumented_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1, IDocumented.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iNamedEClass, INamed.class, "INamed", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getINamed_Name(), ecorePackage.getEString(), "name", null, 0, 1, INamed.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			targetElementEClass, TargetElement.class, "TargetElement", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTargetElement_Deprecated(), ecorePackage.getEBoolean(), "deprecated", null, 0, 1, TargetElement.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			typeFragmentEClass, TypeFragment.class, "TypeFragment", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getType_SuperType(), ecorePackage.getEString(), "superType", null, 0, 1, Type.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaTypeEClass, MetaType.class, "MetaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			typeArgumentEClass, TypeArgument.class, "TypeArgument", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTypeArgument_Required(), ecorePackage.getEBoolean(), "required", null, 0, 1, TypeArgument.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTypeArgument_Namevar(), ecorePackage.getEBoolean(), "namevar", null, 0, 1, TypeArgument.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			nameSpaceEClass, NameSpace.class, "NameSpace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getNameSpace_Reserved(), ecorePackage.getEBoolean(), "reserved", null, 0, 1, NameSpace.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			tpVariableEClass, TPVariable.class, "TPVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTPVariable_Assignable(), ecorePackage.getEBoolean(), "assignable", null, 0, 1, TPVariable.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTPVariable_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, TPVariable.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			iTargetElementContainerEClass, ITargetElementContainer.class, "ITargetElementContainer", IS_ABSTRACT,
			IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getITargetElementContainer_Contents(), this.getTargetElement(), null, "contents", null, 0, -1,
			ITargetElementContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			metaVariableEClass, MetaVariable.class, "MetaVariable", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		// Initialize data types
		initEDataType(fileEDataType, File.class, "File", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // PPTPPackageImpl
