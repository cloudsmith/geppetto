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
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.Documented;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.IncompleteException;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.Parameter;
import org.cloudsmith.geppetto.forge.Property;
import org.cloudsmith.geppetto.forge.Provider;
import org.cloudsmith.geppetto.forge.ReleaseInfo;
import org.cloudsmith.geppetto.forge.Repository;
import org.cloudsmith.geppetto.forge.Type;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
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
public class ForgePackageImpl extends EPackageImpl implements ForgePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass forgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass forgeServiceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cacheEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass repositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass releaseInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass metadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dependencyEClass = null;

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
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass documentedEClass = null;

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
	private EClass providerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass versionRequirementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum httpMethodEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum matchRuleEEnum = null;

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
	private EDataType uriEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType iterableEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType ioExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType listEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType httpURLConnectionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType byteArrayEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType mapEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType stringArrayEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType illegalArgumentExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType incompleteExceptionEDataType = null;

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
	 * This method is used to initialize {@link ForgePackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ForgePackage init() {
		if(isInited)
			return (ForgePackage) EPackage.Registry.INSTANCE.getEPackage(ForgePackage.eNS_URI);

		// Obtain or create and register package
		ForgePackageImpl theForgePackage = (ForgePackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ForgePackageImpl
				? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new ForgePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theForgePackage.createPackageContents();

		// Initialize created meta-data
		theForgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theForgePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ForgePackage.eNS_URI, theForgePackage);
		return theForgePackage;
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
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ForgePackageImpl() {
		super(eNS_URI, ForgeFactory.eINSTANCE);
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
		forgeEClass = createEClass(FORGE);
		createEAttribute(forgeEClass, FORGE__VERSION);
		createEReference(forgeEClass, FORGE__REPOSITORY);
		createEReference(forgeEClass, FORGE__CACHE);
		createEReference(forgeEClass, FORGE__SERVICE);

		forgeServiceEClass = createEClass(FORGE_SERVICE);

		moduleInfoEClass = createEClass(MODULE_INFO);
		createEAttribute(moduleInfoEClass, MODULE_INFO__FULL_NAME);
		createEAttribute(moduleInfoEClass, MODULE_INFO__NAME);
		createEAttribute(moduleInfoEClass, MODULE_INFO__PROJECT_URL);
		createEAttribute(moduleInfoEClass, MODULE_INFO__VERSION);

		cacheEClass = createEClass(CACHE);
		createEAttribute(cacheEClass, CACHE__LOCATION);
		createEReference(cacheEClass, CACHE__REPOSITORY);

		repositoryEClass = createEClass(REPOSITORY);
		createEAttribute(repositoryEClass, REPOSITORY__REPOSITORY);
		createEAttribute(repositoryEClass, REPOSITORY__CACHE_KEY);

		releaseInfoEClass = createEClass(RELEASE_INFO);
		createEAttribute(releaseInfoEClass, RELEASE_INFO__FILE);
		createEAttribute(releaseInfoEClass, RELEASE_INFO__VERSION);

		metadataEClass = createEClass(METADATA);
		createEAttribute(metadataEClass, METADATA__NAME);
		createEAttribute(metadataEClass, METADATA__USER);
		createEAttribute(metadataEClass, METADATA__FULL_NAME);
		createEAttribute(metadataEClass, METADATA__VERSION);
		createEAttribute(metadataEClass, METADATA__LOCATION);
		createEAttribute(metadataEClass, METADATA__SOURCE);
		createEAttribute(metadataEClass, METADATA__AUTHOR);
		createEAttribute(metadataEClass, METADATA__LICENSE);
		createEReference(metadataEClass, METADATA__TYPES);
		createEAttribute(metadataEClass, METADATA__SUMMARY);
		createEAttribute(metadataEClass, METADATA__DESCRIPTION);
		createEAttribute(metadataEClass, METADATA__PROJECT_PAGE);
		createEAttribute(metadataEClass, METADATA__CHECKSUMS);
		createEReference(metadataEClass, METADATA__DEPENDENCIES);

		dependencyEClass = createEClass(DEPENDENCY);
		createEAttribute(dependencyEClass, DEPENDENCY__NAME);
		createEAttribute(dependencyEClass, DEPENDENCY__REPOSITORY);
		createEReference(dependencyEClass, DEPENDENCY__VERSION_REQUIREMENT);

		typeEClass = createEClass(TYPE);
		createEReference(typeEClass, TYPE__PARAMETERS);
		createEReference(typeEClass, TYPE__PROPERTIES);
		createEReference(typeEClass, TYPE__PROVIDERS);

		parameterEClass = createEClass(PARAMETER);

		documentedEClass = createEClass(DOCUMENTED);
		createEAttribute(documentedEClass, DOCUMENTED__NAME);
		createEAttribute(documentedEClass, DOCUMENTED__DOC);

		propertyEClass = createEClass(PROPERTY);

		providerEClass = createEClass(PROVIDER);

		versionRequirementEClass = createEClass(VERSION_REQUIREMENT);
		createEAttribute(versionRequirementEClass, VERSION_REQUIREMENT__VERSION);
		createEAttribute(versionRequirementEClass, VERSION_REQUIREMENT__MATCH_RULE);

		// Create enums
		matchRuleEEnum = createEEnum(MATCH_RULE);
		httpMethodEEnum = createEEnum(HTTP_METHOD);

		// Create data types
		byteArrayEDataType = createEDataType(BYTE_ARRAY);
		fileEDataType = createEDataType(FILE);
		httpURLConnectionEDataType = createEDataType(HTTP_URL_CONNECTION);
		illegalArgumentExceptionEDataType = createEDataType(ILLEGAL_ARGUMENT_EXCEPTION);
		incompleteExceptionEDataType = createEDataType(INCOMPLETE_EXCEPTION);
		ioExceptionEDataType = createEDataType(IO_EXCEPTION);
		listEDataType = createEDataType(LIST);
		mapEDataType = createEDataType(MAP);
		stringArrayEDataType = createEDataType(STRING_ARRAY);
		uriEDataType = createEDataType(URI);
		iterableEDataType = createEDataType(ITERABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getbyteArray() {
		return byteArrayEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCache() {
		return cacheEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCache_Location() {
		return (EAttribute) cacheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCache_Repository() {
		return (EReference) cacheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDependency() {
		return dependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDependency_Name() {
		return (EAttribute) dependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDependency_Repository() {
		return (EAttribute) dependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDependency_VersionRequirement() {
		return (EReference) dependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDocumented() {
		return documentedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumented_Doc() {
		return (EAttribute) documentedEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumented_Name() {
		return (EAttribute) documentedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getFile() {
		return fileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getForge() {
		return forgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForge_Cache() {
		return (EReference) forgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForge_Repository() {
		return (EReference) forgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForge_Service() {
		return (EReference) forgeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getForge_Version() {
		return (EAttribute) forgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ForgeFactory getForgeFactory() {
		return (ForgeFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getForgeService() {
		return forgeServiceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getHttpMethod() {
		return httpMethodEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getHttpURLConnection() {
		return httpURLConnectionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getIllegalArgumentException() {
		return illegalArgumentExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getIncompleteException() {
		return incompleteExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getIOException() {
		return ioExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getIterable() {
		return iterableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getList() {
		return listEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getMap() {
		return mapEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getMatchRule() {
		return matchRuleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMetadata() {
		return metadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Author() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Checksums() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMetadata_Dependencies() {
		return (EReference) metadataEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Description() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_FullName() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_License() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Location() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Name() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_ProjectPage() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Source() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Summary() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMetadata_Types() {
		return (EReference) metadataEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_User() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getMetadata_Version() {
		return (EAttribute) metadataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModuleInfo() {
		return moduleInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleInfo_FullName() {
		return (EAttribute) moduleInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleInfo_Name() {
		return (EAttribute) moduleInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleInfo_ProjectURL() {
		return (EAttribute) moduleInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleInfo_Version() {
		return (EAttribute) moduleInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProvider() {
		return providerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getReleaseInfo() {
		return releaseInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getReleaseInfo_File() {
		return (EAttribute) releaseInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getReleaseInfo_Version() {
		return (EAttribute) releaseInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRepository() {
		return repositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRepository_CacheKey() {
		return (EAttribute) repositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRepository_Repository() {
		return (EAttribute) repositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getStringArray() {
		return stringArrayEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getType_Parameters() {
		return (EReference) typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getType_Properties() {
		return (EReference) typeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getType_Providers() {
		return (EReference) typeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getURI() {
		return uriEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVersionRequirement() {
		return versionRequirementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVersionRequirement_MatchRule() {
		return (EAttribute) versionRequirementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVersionRequirement_Version() {
		return (EAttribute) versionRequirementEClass.getEStructuralFeatures().get(0);
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
		addETypeParameter(listEDataType, "E");
		addETypeParameter(mapEDataType, "K");
		addETypeParameter(mapEDataType, "V");
		addETypeParameter(iterableEDataType, "E");

		// Set bounds for type parameters

		// Add supertypes to classes
		typeEClass.getESuperTypes().add(this.getDocumented());
		parameterEClass.getESuperTypes().add(this.getDocumented());
		propertyEClass.getESuperTypes().add(this.getDocumented());
		providerEClass.getESuperTypes().add(this.getDocumented());

		// Initialize classes and features; add operations and parameters
		initEClass(forgeEClass, Forge.class, "Forge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getForge_Version(), ecorePackage.getEString(), "version", null, 1, 1, Forge.class, !IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getForge_Repository(), this.getRepository(), null, "repository", null, 1, 1, Forge.class, IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(
			getForge_Cache(), this.getCache(), null, "cache", null, 1, 1, Forge.class, IS_TRANSIENT, !IS_VOLATILE,
			!IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getForge_Service(), this.getForgeService(), null, "service", null, 1, 1, Forge.class, IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		EOperation op = addEOperation(forgeEClass, this.getMetadata(), "build", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "moduleSource", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "destination", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());
		addEException(op, this.getIncompleteException());

		op = addEOperation(forgeEClass, null, "changes", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());
		EGenericType g1 = createEGenericType(this.getList());
		EGenericType g2 = createEGenericType(this.getFile());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(forgeEClass, null, "generate", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "destination", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMetadata(), "metadata", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(forgeEClass, this.getMetadata(), "install", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fullName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "destination", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "destinationIncludesTopFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "force", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(forgeEClass, null, "search", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "term", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());
		g1 = createEGenericType(this.getList());
		g2 = createEGenericType(this.getModuleInfo());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(forgeEClass, this.getReleaseInfo(), "getRelease", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fullName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		initEClass(
			forgeServiceEClass, ForgeService.class, "ForgeService", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(forgeServiceEClass, this.getCache(), "createCache", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "cacheDirectory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRepository(), "repository", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getForge(), "createForge", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "repository", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getForge(), "createForge", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "repository", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "cacheDirectory", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getForge(), "createForge", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRepository(), "repository", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCache(), "cache", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getMetadata(), "createMetadata", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fullName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getRepository(), "createRepository", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "uri", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(forgeServiceEClass, this.getMetadata(), "loadJSONMetadata", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "jsonFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(forgeServiceEClass, this.getMetadata(), "loadModule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "moduleDirectory", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		initEClass(
			moduleInfoEClass, ModuleInfo.class, "ModuleInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getModuleInfo_FullName(), ecorePackage.getEString(), "fullName", null, 0, 1, ModuleInfo.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getModuleInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, ModuleInfo.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getModuleInfo_ProjectURL(), this.getURI(), "projectURL", null, 0, 1, ModuleInfo.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getModuleInfo_Version(), ecorePackage.getEString(), "version", null, 0, 1, ModuleInfo.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cacheEClass, Cache.class, "Cache", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getCache_Location(), this.getFile(), "location", null, 0, 1, Cache.class, !IS_TRANSIENT, !IS_VOLATILE,
			!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getCache_Repository(), this.getRepository(), null, "repository", null, 1, 1, Cache.class, IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		op = addEOperation(cacheEClass, this.getFile(), "retrieve", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "fileName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(cacheEClass, null, "clean", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		initEClass(
			repositoryEClass, Repository.class, "Repository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getRepository_Repository(), this.getURI(), "repository", null, 1, 1, Repository.class, !IS_TRANSIENT,
			!IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getRepository_CacheKey(), ecorePackage.getEString(), "cacheKey", null, 1, 1, Repository.class,
			IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(repositoryEClass, this.getHttpURLConnection(), "connect", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getHttpMethod(), "method", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "urlSuffix", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		initEClass(
			releaseInfoEClass, ReleaseInfo.class, "ReleaseInfo", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getReleaseInfo_File(), ecorePackage.getEString(), "file", null, 0, 1, ReleaseInfo.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getReleaseInfo_Version(), ecorePackage.getEString(), "version", null, 0, 1, ReleaseInfo.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metadataEClass, Metadata.class, "Metadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getMetadata_Name(), ecorePackage.getEString(), "name", null, 1, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_User(), ecorePackage.getEString(), "user", null, 1, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_FullName(), ecorePackage.getEString(), "fullName", null, 1, 1, Metadata.class, IS_TRANSIENT,
			IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_Version(), ecorePackage.getEString(), "version", null, 1, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_Location(), this.getFile(), "location", null, 0, 1, Metadata.class, IS_TRANSIENT, !IS_VOLATILE,
			!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_Source(), ecorePackage.getEString(), "source", null, 0, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_Author(), ecorePackage.getEString(), "author", null, 0, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_License(), ecorePackage.getEString(), "license", null, 0, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getMetadata_Types(), this.getType(), null, "types", null, 0, -1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(
			getMetadata_Summary(), ecorePackage.getEString(), "summary", null, 0, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_Description(), ecorePackage.getEString(), "description", null, 0, 1, Metadata.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getMetadata_ProjectPage(), this.getURI(), "projectPage", null, 0, 1, Metadata.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getMap());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getbyteArray());
		g1.getETypeArguments().add(g2);
		initEAttribute(
			getMetadata_Checksums(), g1, "checksums", null, 0, 1, Metadata.class, !IS_TRANSIENT, !IS_VOLATILE,
			!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getMetadata_Dependencies(), this.getDependency(), null, "dependencies", null, 0, -1, Metadata.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		op = addEOperation(metadataEClass, null, "loadModuleFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "moduleFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(metadataEClass, null, "loadTypeFiles", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "puppetDir", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(metadataEClass, null, "loadChecksums", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "moduleDir", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(metadataEClass, null, "saveJSONMetadata", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "jsonFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(metadataEClass, null, "saveModulefile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "moduleFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(
			metadataEClass, this.getVersionRequirement(), "parseVersionRequirement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "versionRequirement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(
			dependencyEClass, Dependency.class, "Dependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getDependency_Name(), ecorePackage.getEString(), "name", null, 1, 1, Dependency.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getDependency_Repository(), this.getURI(), "repository", null, 0, 1, Dependency.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getDependency_VersionRequirement(), this.getVersionRequirement(), null, "versionRequirement", null, 0, 1,
			Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(dependencyEClass, ecorePackage.getEBoolean(), "matches", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getType_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, Type.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(
			getType_Properties(), this.getProperty(), null, "properties", null, 0, -1, Type.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(
			getType_Providers(), this.getProvider(), null, "providers", null, 0, -1, Type.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		op = addEOperation(typeEClass, null, "loadTypeFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "typeFile", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		op = addEOperation(typeEClass, null, "loadProvider", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFile(), "providerDir", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getIOException());

		initEClass(
			parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			documentedEClass, Documented.class, "Documented", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getDocumented_Name(), ecorePackage.getEString(), "name", null, 1, 1, Documented.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getDocumented_Doc(), ecorePackage.getEString(), "doc", null, 0, 1, Documented.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(providerEClass, Provider.class, "Provider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			versionRequirementEClass, VersionRequirement.class, "VersionRequirement", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getVersionRequirement_Version(), ecorePackage.getEString(), "version", null, 1, 1,
			VersionRequirement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getVersionRequirement_MatchRule(), this.getMatchRule(), "matchRule", null, 1, 1, VersionRequirement.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(versionRequirementEClass, ecorePackage.getEBoolean(), "matches", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "version", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(
			versionRequirementEClass, ecorePackage.getEString(), "findBestMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getIterable());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "versions", 1, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(matchRuleEEnum, MatchRule.class, "MatchRule");
		addEEnumLiteral(matchRuleEEnum, MatchRule.PERFECT);
		addEEnumLiteral(matchRuleEEnum, MatchRule.EQUIVALENT);
		addEEnumLiteral(matchRuleEEnum, MatchRule.COMPATIBLE);
		addEEnumLiteral(matchRuleEEnum, MatchRule.LESS);
		addEEnumLiteral(matchRuleEEnum, MatchRule.LESS_OR_EQUAL);
		addEEnumLiteral(matchRuleEEnum, MatchRule.GREATER);
		addEEnumLiteral(matchRuleEEnum, MatchRule.GREATER_OR_EQUAL);

		initEEnum(httpMethodEEnum, HttpMethod.class, "HttpMethod");
		addEEnumLiteral(httpMethodEEnum, HttpMethod.GET);
		addEEnumLiteral(httpMethodEEnum, HttpMethod.HEAD);
		addEEnumLiteral(httpMethodEEnum, HttpMethod.PUT);
		addEEnumLiteral(httpMethodEEnum, HttpMethod.POST);
		addEEnumLiteral(httpMethodEEnum, HttpMethod.DELETE);

		// Initialize data types
		initEDataType(byteArrayEDataType, byte[].class, "byteArray", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(fileEDataType, File.class, "File", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(
			httpURLConnectionEDataType, HttpURLConnection.class, "HttpURLConnection", IS_SERIALIZABLE,
			!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(
			illegalArgumentExceptionEDataType, IllegalArgumentException.class, "IllegalArgumentException",
			IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(
			incompleteExceptionEDataType, IncompleteException.class, "IncompleteException", IS_SERIALIZABLE,
			!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(
			ioExceptionEDataType, IOException.class, "IOException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(listEDataType, List.class, "List", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(mapEDataType, Map.class, "Map", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(
			stringArrayEDataType, String[].class, "StringArray", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(uriEDataType, java.net.URI.class, "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iterableEDataType, Iterable.class, "Iterable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // ForgePackageImpl
