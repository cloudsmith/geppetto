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
package com.puppetlabs.geppetto.catalog.impl;

import com.puppetlabs.geppetto.catalog.Catalog;
import com.puppetlabs.geppetto.catalog.CatalogEdge;
import com.puppetlabs.geppetto.catalog.CatalogFactory;
import com.puppetlabs.geppetto.catalog.CatalogMetadata;
import com.puppetlabs.geppetto.catalog.CatalogPackage;
import com.puppetlabs.geppetto.catalog.CatalogResource;
import com.puppetlabs.geppetto.catalog.CatalogResourceParameter;
import com.puppetlabs.geppetto.catalog.Taggable;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class CatalogPackageImpl extends EPackageImpl implements CatalogPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass catalogEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass catalogResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass catalogMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass catalogEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass catalogResourceParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass taggableEClass = null;

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
	 * This method is used to initialize {@link CatalogPackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CatalogPackage init() {
		if(isInited)
			return (CatalogPackage) EPackage.Registry.INSTANCE.getEPackage(CatalogPackage.eNS_URI);

		// Obtain or create and register package
		CatalogPackageImpl theCatalogPackage = (CatalogPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CatalogPackageImpl
				? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new CatalogPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theCatalogPackage.createPackageContents();

		// Initialize created meta-data
		theCatalogPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCatalogPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CatalogPackage.eNS_URI, theCatalogPackage);
		return theCatalogPackage;
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
	 * @see com.puppetlabs.geppetto.catalog.CatalogPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CatalogPackageImpl() {
		super(eNS_URI, CatalogFactory.eINSTANCE);
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
		catalogEClass = createEClass(CATALOG);
		createEAttribute(catalogEClass, CATALOG__NAME);
		createEAttribute(catalogEClass, CATALOG__VERSION);
		createEReference(catalogEClass, CATALOG__RESOURCES);
		createEAttribute(catalogEClass, CATALOG__CLASSES);
		createEReference(catalogEClass, CATALOG__METADATA);
		createEReference(catalogEClass, CATALOG__EDGES);

		catalogResourceEClass = createEClass(CATALOG_RESOURCE);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__FILE);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__LINE);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__TYPE);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__TITLE);
		createEReference(catalogResourceEClass, CATALOG_RESOURCE__PARAMETERS);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__VIRTUAL);
		createEAttribute(catalogResourceEClass, CATALOG_RESOURCE__EXPORTED);

		catalogMetadataEClass = createEClass(CATALOG_METADATA);
		createEAttribute(catalogMetadataEClass, CATALOG_METADATA__API_VERSION);

		catalogEdgeEClass = createEClass(CATALOG_EDGE);
		createEAttribute(catalogEdgeEClass, CATALOG_EDGE__TARGET);
		createEAttribute(catalogEdgeEClass, CATALOG_EDGE__SOURCE);

		catalogResourceParameterEClass = createEClass(CATALOG_RESOURCE_PARAMETER);
		createEAttribute(catalogResourceParameterEClass, CATALOG_RESOURCE_PARAMETER__NAME);
		createEAttribute(catalogResourceParameterEClass, CATALOG_RESOURCE_PARAMETER__VALUE);

		taggableEClass = createEClass(TAGGABLE);
		createEAttribute(taggableEClass, TAGGABLE__TAGS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCatalog() {
		return catalogEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalog_Classes() {
		return (EAttribute) catalogEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCatalog_Edges() {
		return (EReference) catalogEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCatalog_Metadata() {
		return (EReference) catalogEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalog_Name() {
		return (EAttribute) catalogEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCatalog_Resources() {
		return (EReference) catalogEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalog_Version() {
		return (EAttribute) catalogEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCatalogEdge() {
		return catalogEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogEdge_Source() {
		return (EAttribute) catalogEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogEdge_Target() {
		return (EAttribute) catalogEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CatalogFactory getCatalogFactory() {
		return (CatalogFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCatalogMetadata() {
		return catalogMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogMetadata_Api_version() {
		return (EAttribute) catalogMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCatalogResource() {
		return catalogResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_Exported() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_File() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_Line() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCatalogResource_Parameters() {
		return (EReference) catalogResourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_Title() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_Type() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResource_Virtual() {
		return (EAttribute) catalogResourceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCatalogResourceParameter() {
		return catalogResourceParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResourceParameter_Name() {
		return (EAttribute) catalogResourceParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCatalogResourceParameter_Value() {
		return (EAttribute) catalogResourceParameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTaggable() {
		return taggableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTaggable_Tags() {
		return (EAttribute) taggableEClass.getEStructuralFeatures().get(0);
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
		catalogEClass.getESuperTypes().add(this.getTaggable());
		catalogResourceEClass.getESuperTypes().add(this.getTaggable());

		// Initialize classes and features; add operations and parameters
		initEClass(catalogEClass, Catalog.class, "Catalog", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCatalog_Name(), ecorePackage.getEString(), "name", null, 0, 1, Catalog.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalog_Version(), ecorePackage.getEString(), "version", null, 0, 1, Catalog.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getCatalog_Resources(), this.getCatalogResource(), null, "resources", null, 0, -1, Catalog.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalog_Classes(), ecorePackage.getEString(), "classes", null, 0, -1, Catalog.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getCatalog_Metadata(), this.getCatalogMetadata(), null, "metadata", null, 0, 1, Catalog.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(
			getCatalog_Edges(), this.getCatalogEdge(), null, "edges", null, 0, -1, Catalog.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(
			catalogResourceEClass, CatalogResource.class, "CatalogResource", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCatalogResource_File(), ecorePackage.getEString(), "file", null, 0, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResource_Line(), ecorePackage.getEString(), "line", null, 0, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResource_Type(), ecorePackage.getEString(), "type", null, 1, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResource_Title(), ecorePackage.getEString(), "title", null, 1, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getCatalogResource_Parameters(), this.getCatalogResourceParameter(), null, "parameters", null, 0, -1,
			CatalogResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResource_Virtual(), ecorePackage.getEBoolean(), "virtual", null, 0, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResource_Exported(), ecorePackage.getEBoolean(), "exported", null, 0, 1, CatalogResource.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			catalogMetadataEClass, CatalogMetadata.class, "CatalogMetadata", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCatalogMetadata_Api_version(), ecorePackage.getEString(), "api_version", null, 0, 1,
			CatalogMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(
			catalogEdgeEClass, CatalogEdge.class, "CatalogEdge", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCatalogEdge_Target(), ecorePackage.getEString(), "target", null, 1, 1, CatalogEdge.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogEdge_Source(), ecorePackage.getEString(), "source", null, 1, 1, CatalogEdge.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			catalogResourceParameterEClass, CatalogResourceParameter.class, "CatalogResourceParameter", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCatalogResourceParameter_Name(), ecorePackage.getEString(), "name", null, 1, 1,
			CatalogResourceParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getCatalogResourceParameter_Value(), ecorePackage.getEString(), "value", null, 1, -1,
			CatalogResourceParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taggableEClass, Taggable.class, "Taggable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTaggable_Tags(), ecorePackage.getEString(), "tags", null, 0, -1, Taggable.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // CatalogPackageImpl
