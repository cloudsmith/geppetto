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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.forge.ForgeFactory
 * @model kind="package"
 * @generated
 */
public interface ForgePackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl <em>Forge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ForgeImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getForge()
		 * @generated
		 */
		EClass FORGE = eINSTANCE.getForge();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FORGE__REPOSITORY = eINSTANCE.getForge_Repository();

		/**
		 * The meta object literal for the '<em><b>Cache</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FORGE__CACHE = eINSTANCE.getForge_Cache();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FORGE__SERVICE = eINSTANCE.getForge_Service();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FORGE__VERSION = eINSTANCE.getForge_Version();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ForgeServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ForgeServiceImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getForgeService()
		 * @generated
		 */
		EClass FORGE_SERVICE = eINSTANCE.getForgeService();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl <em>Module Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getModuleInfo()
		 * @generated
		 */
		EClass MODULE_INFO = eINSTANCE.getModuleInfo();

		/**
		 * The meta object literal for the '<em><b>Full Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_INFO__FULL_NAME = eINSTANCE.getModuleInfo_FullName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_INFO__NAME = eINSTANCE.getModuleInfo_Name();

		/**
		 * The meta object literal for the '<em><b>Project URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_INFO__PROJECT_URL = eINSTANCE.getModuleInfo_ProjectURL();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_INFO__VERSION = eINSTANCE.getModuleInfo_Version();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.CacheImpl <em>Cache</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.CacheImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getCache()
		 * @generated
		 */
		EClass CACHE = eINSTANCE.getCache();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CACHE__LOCATION = eINSTANCE.getCache_Location();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CACHE__REPOSITORY = eINSTANCE.getCache_Repository();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.RepositoryImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REPOSITORY__REPOSITORY = eINSTANCE.getRepository_Repository();

		/**
		 * The meta object literal for the '<em><b>Cache Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REPOSITORY__CACHE_KEY = eINSTANCE.getRepository_CacheKey();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ReleaseInfoImpl <em>Release Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ReleaseInfoImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getReleaseInfo()
		 * @generated
		 */
		EClass RELEASE_INFO = eINSTANCE.getReleaseInfo();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RELEASE_INFO__FILE = eINSTANCE.getReleaseInfo_File();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RELEASE_INFO__VERSION = eINSTANCE.getReleaseInfo_Version();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl <em>Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.MetadataImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getMetadata()
		 * @generated
		 */
		EClass METADATA = eINSTANCE.getMetadata();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__NAME = eINSTANCE.getMetadata_Name();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__USER = eINSTANCE.getMetadata_User();

		/**
		 * The meta object literal for the '<em><b>Full Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__FULL_NAME = eINSTANCE.getMetadata_FullName();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__LOCATION = eINSTANCE.getMetadata_Location();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__VERSION = eINSTANCE.getMetadata_Version();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__SOURCE = eINSTANCE.getMetadata_Source();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__AUTHOR = eINSTANCE.getMetadata_Author();

		/**
		 * The meta object literal for the '<em><b>License</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__LICENSE = eINSTANCE.getMetadata_License();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference METADATA__DEPENDENCIES = eINSTANCE.getMetadata_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference METADATA__TYPES = eINSTANCE.getMetadata_Types();

		/**
		 * The meta object literal for the '<em><b>Summary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__SUMMARY = eINSTANCE.getMetadata_Summary();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__DESCRIPTION = eINSTANCE.getMetadata_Description();

		/**
		 * The meta object literal for the '<em><b>Project Page</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__PROJECT_PAGE = eINSTANCE.getMetadata_ProjectPage();

		/**
		 * The meta object literal for the '<em><b>Checksums</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METADATA__CHECKSUMS = eINSTANCE.getMetadata_Checksums();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl <em>Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.DependencyImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getDependency()
		 * @generated
		 */
		EClass DEPENDENCY = eINSTANCE.getDependency();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEPENDENCY__NAME = eINSTANCE.getDependency_Name();

		/**
		 * The meta object literal for the '<em><b>Version Requirement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEPENDENCY__VERSION_REQUIREMENT = eINSTANCE.getDependency_VersionRequirement();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEPENDENCY__REPOSITORY = eINSTANCE.getDependency_Repository();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.TypeImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE__PARAMETERS = eINSTANCE.getType_Parameters();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE__PROPERTIES = eINSTANCE.getType_Properties();

		/**
		 * The meta object literal for the '<em><b>Providers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE__PROVIDERS = eINSTANCE.getType_Providers();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ParameterImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.DocumentedImpl <em>Documented</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.DocumentedImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getDocumented()
		 * @generated
		 */
		EClass DOCUMENTED = eINSTANCE.getDocumented();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOCUMENTED__NAME = eINSTANCE.getDocumented_Name();

		/**
		 * The meta object literal for the '<em><b>Doc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOCUMENTED__DOC = eINSTANCE.getDocumented_Doc();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.PropertyImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.impl.ProviderImpl <em>Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ProviderImpl
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getProvider()
		 * @generated
		 */
		EClass PROVIDER = eINSTANCE.getProvider();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.forge.HttpMethod <em>Http Method</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.HttpMethod
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getHttpMethod()
		 * @generated
		 */
		EEnum HTTP_METHOD = eINSTANCE.getHttpMethod();

		/**
		 * The meta object literal for the '<em>File</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.io.File
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getFile()
		 * @generated
		 */
		EDataType FILE = eINSTANCE.getFile();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.net.URI
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

		/**
		 * The meta object literal for the '<em>IO Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.io.IOException
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIOException()
		 * @generated
		 */
		EDataType IO_EXCEPTION = eINSTANCE.getIOException();

		/**
		 * The meta object literal for the '<em>List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.util.List
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getList()
		 * @generated
		 */
		EDataType LIST = eINSTANCE.getList();

		/**
		 * The meta object literal for the '<em>Http URL Connection</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.net.HttpURLConnection
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getHttpURLConnection()
		 * @generated
		 */
		EDataType HTTP_URL_CONNECTION = eINSTANCE.getHttpURLConnection();

		/**
		 * The meta object literal for the '<em>byte Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getbyteArray()
		 * @generated
		 */
		EDataType BYTE_ARRAY = eINSTANCE.getbyteArray();

		/**
		 * The meta object literal for the '<em>Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.util.Map
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getMap()
		 * @generated
		 */
		EDataType MAP = eINSTANCE.getMap();

		/**
		 * The meta object literal for the '<em>String Array</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getStringArray()
		 * @generated
		 */
		EDataType STRING_ARRAY = eINSTANCE.getStringArray();

		/**
		 * The meta object literal for the '<em>Illegal Argument Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.IllegalArgumentException
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIllegalArgumentException()
		 * @generated
		 */
		EDataType ILLEGAL_ARGUMENT_EXCEPTION = eINSTANCE.getIllegalArgumentException();

		/**
		 * The meta object literal for the '<em>Incomplete Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.forge.IncompleteException
		 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIncompleteException()
		 * @generated
		 */
		EDataType INCOMPLETE_EXCEPTION = eINSTANCE.getIncompleteException();

	}

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "forge";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.cloudsmith.org/geppetto/1.0.0/Forge";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "forge";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ForgePackage eINSTANCE = org.cloudsmith.geppetto.forge.impl.ForgePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl <em>Forge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ForgeImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getForge()
	 * @generated
	 */
	int FORGE = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE__REPOSITORY = 1;

	/**
	 * The feature id for the '<em><b>Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE__CACHE = 2;

	/**
	 * The feature id for the '<em><b>Service</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE__SERVICE = 3;

	/**
	 * The number of structural features of the '<em>Forge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ForgeServiceImpl <em>Service</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ForgeServiceImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getForgeService()
	 * @generated
	 */
	int FORGE_SERVICE = 1;

	/**
	 * The number of structural features of the '<em>Service</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FORGE_SERVICE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl <em>Module Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getModuleInfo()
	 * @generated
	 */
	int MODULE_INFO = 2;

	/**
	 * The feature id for the '<em><b>Full Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_INFO__FULL_NAME = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_INFO__NAME = 1;

	/**
	 * The feature id for the '<em><b>Project URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_INFO__PROJECT_URL = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_INFO__VERSION = 3;

	/**
	 * The number of structural features of the '<em>Module Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_INFO_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.CacheImpl <em>Cache</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.CacheImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getCache()
	 * @generated
	 */
	int CACHE = 3;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE__LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE__REPOSITORY = 1;

	/**
	 * The number of structural features of the '<em>Cache</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.RepositoryImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 4;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Cache Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__CACHE_KEY = 1;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ReleaseInfoImpl <em>Release Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ReleaseInfoImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getReleaseInfo()
	 * @generated
	 */
	int RELEASE_INFO = 5;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELEASE_INFO__FILE = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELEASE_INFO__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Release Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELEASE_INFO_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl <em>Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.MetadataImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getMetadata()
	 * @generated
	 */
	int METADATA = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__NAME = 0;

	/**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__USER = 1;

	/**
	 * The feature id for the '<em><b>Full Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__FULL_NAME = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__VERSION = 3;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__LOCATION = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__SOURCE = 5;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__AUTHOR = 6;

	/**
	 * The feature id for the '<em><b>License</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__LICENSE = 7;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__TYPES = 8;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__SUMMARY = 9;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__DESCRIPTION = 10;

	/**
	 * The feature id for the '<em><b>Project Page</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__PROJECT_PAGE = 11;

	/**
	 * The feature id for the '<em><b>Checksums</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__CHECKSUMS = 12;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA__DEPENDENCIES = 13;

	/**
	 * The number of structural features of the '<em>Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METADATA_FEATURE_COUNT = 14;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl <em>Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.DependencyImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getDependency()
	 * @generated
	 */
	int DEPENDENCY = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Version Requirement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__VERSION_REQUIREMENT = 1;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__REPOSITORY = 2;

	/**
	 * The number of structural features of the '<em>Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.DocumentedImpl <em>Documented</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.DocumentedImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getDocumented()
	 * @generated
	 */
	int DOCUMENTED = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED__NAME = 0;

	/**
	 * The feature id for the '<em><b>Doc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED__DOC = 1;

	/**
	 * The number of structural features of the '<em>Documented</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.TypeImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getType()
	 * @generated
	 */
	int TYPE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = DOCUMENTED__NAME;

	/**
	 * The feature id for the '<em><b>Doc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__DOC = DOCUMENTED__DOC;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__PARAMETERS = DOCUMENTED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__PROPERTIES = DOCUMENTED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Providers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__PROVIDERS = DOCUMENTED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = DOCUMENTED_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ParameterImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = DOCUMENTED__NAME;

	/**
	 * The feature id for the '<em><b>Doc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DOC = DOCUMENTED__DOC;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = DOCUMENTED_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.PropertyImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = DOCUMENTED__NAME;

	/**
	 * The feature id for the '<em><b>Doc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DOC = DOCUMENTED__DOC;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = DOCUMENTED_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.impl.ProviderImpl <em>Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ProviderImpl
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getProvider()
	 * @generated
	 */
	int PROVIDER = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVIDER__NAME = DOCUMENTED__NAME;

	/**
	 * The feature id for the '<em><b>Doc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVIDER__DOC = DOCUMENTED__DOC;

	/**
	 * The number of structural features of the '<em>Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROVIDER_FEATURE_COUNT = DOCUMENTED_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.forge.HttpMethod <em>Http Method</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.HttpMethod
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getHttpMethod()
	 * @generated
	 */
	int HTTP_METHOD = 13;

	/**
	 * The meta object id for the '<em>File</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.io.File
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getFile()
	 * @generated
	 */
	int FILE = 14;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.net.URI
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getURI()
	 * @generated
	 */
	int URI = 15;

	/**
	 * The meta object id for the '<em>IO Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.io.IOException
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIOException()
	 * @generated
	 */
	int IO_EXCEPTION = 16;

	/**
	 * The meta object id for the '<em>List</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.util.List
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getList()
	 * @generated
	 */
	int LIST = 17;

	/**
	 * The meta object id for the '<em>Http URL Connection</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.net.HttpURLConnection
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getHttpURLConnection()
	 * @generated
	 */
	int HTTP_URL_CONNECTION = 18;

	/**
	 * The meta object id for the '<em>byte Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getbyteArray()
	 * @generated
	 */
	int BYTE_ARRAY = 19;

	/**
	 * The meta object id for the '<em>Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.util.Map
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getMap()
	 * @generated
	 */
	int MAP = 20;

	/**
	 * The meta object id for the '<em>String Array</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getStringArray()
	 * @generated
	 */
	int STRING_ARRAY = 21;

	/**
	 * The meta object id for the '<em>Illegal Argument Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.IllegalArgumentException
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIllegalArgumentException()
	 * @generated
	 */
	int ILLEGAL_ARGUMENT_EXCEPTION = 22;

	/**
	 * The meta object id for the '<em>Incomplete Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.IncompleteException
	 * @see org.cloudsmith.geppetto.forge.impl.ForgePackageImpl#getIncompleteException()
	 * @generated
	 */
	int INCOMPLETE_EXCEPTION = 23;

	/**
	 * Returns the meta object for data type '<em>byte Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>byte Array</em>'.
	 * @model instanceClass="byte[]"
	 * @generated
	 */
	EDataType getbyteArray();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Cache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cache</em>'.
	 * @see org.cloudsmith.geppetto.forge.Cache
	 * @generated
	 */
	EClass getCache();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Cache#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.cloudsmith.geppetto.forge.Cache#getLocation()
	 * @see #getCache()
	 * @generated
	 */
	EAttribute getCache_Location();

	/**
	 * Returns the meta object for the reference '{@link org.cloudsmith.geppetto.forge.Cache#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Repository</em>'.
	 * @see org.cloudsmith.geppetto.forge.Cache#getRepository()
	 * @see #getCache()
	 * @generated
	 */
	EReference getCache_Repository();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Dependency</em>'.
	 * @see org.cloudsmith.geppetto.forge.Dependency
	 * @generated
	 */
	EClass getDependency();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Dependency#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.Dependency#getName()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Dependency#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Repository</em>'.
	 * @see org.cloudsmith.geppetto.forge.Dependency#getRepository()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Repository();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Dependency#getVersionRequirement <em>Version Requirement</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version Requirement</em>'.
	 * @see org.cloudsmith.geppetto.forge.Dependency#getVersionRequirement()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_VersionRequirement();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Documented <em>Documented</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documented</em>'.
	 * @see org.cloudsmith.geppetto.forge.Documented
	 * @generated
	 */
	EClass getDocumented();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Documented#getDoc <em>Doc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Doc</em>'.
	 * @see org.cloudsmith.geppetto.forge.Documented#getDoc()
	 * @see #getDocumented()
	 * @generated
	 */
	EAttribute getDocumented_Doc();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Documented#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.Documented#getName()
	 * @see #getDocumented()
	 * @generated
	 */
	EAttribute getDocumented_Name();

	/**
	 * Returns the meta object for data type '{@link java.io.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>File</em>'.
	 * @see java.io.File
	 * @model instanceClass="java.io.File"
	 * @generated
	 */
	EDataType getFile();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Forge <em>Forge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Forge</em>'.
	 * @see org.cloudsmith.geppetto.forge.Forge
	 * @generated
	 */
	EClass getForge();

	/**
	 * Returns the meta object for the reference '{@link org.cloudsmith.geppetto.forge.Forge#getCache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Cache</em>'.
	 * @see org.cloudsmith.geppetto.forge.Forge#getCache()
	 * @see #getForge()
	 * @generated
	 */
	EReference getForge_Cache();

	/**
	 * Returns the meta object for the reference '{@link org.cloudsmith.geppetto.forge.Forge#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Repository</em>'.
	 * @see org.cloudsmith.geppetto.forge.Forge#getRepository()
	 * @see #getForge()
	 * @generated
	 */
	EReference getForge_Repository();

	/**
	 * Returns the meta object for the reference '{@link org.cloudsmith.geppetto.forge.Forge#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Service</em>'.
	 * @see org.cloudsmith.geppetto.forge.Forge#getService()
	 * @see #getForge()
	 * @generated
	 */
	EReference getForge_Service();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Forge#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.cloudsmith.geppetto.forge.Forge#getVersion()
	 * @see #getForge()
	 * @generated
	 */
	EAttribute getForge_Version();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ForgeFactory getForgeFactory();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.ForgeService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Service</em>'.
	 * @see org.cloudsmith.geppetto.forge.ForgeService
	 * @generated
	 */
	EClass getForgeService();

	/**
	 * Returns the meta object for enum '{@link org.cloudsmith.geppetto.forge.HttpMethod <em>Http Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Http Method</em>'.
	 * @see org.cloudsmith.geppetto.forge.HttpMethod
	 * @generated
	 */
	EEnum getHttpMethod();

	/**
	 * Returns the meta object for data type '{@link java.net.HttpURLConnection <em>Http URL Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Http URL Connection</em>'.
	 * @see java.net.HttpURLConnection
	 * @model instanceClass="java.net.HttpURLConnection"
	 * @generated
	 */
	EDataType getHttpURLConnection();

	/**
	 * Returns the meta object for data type '{@link java.lang.IllegalArgumentException <em>Illegal Argument Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Illegal Argument Exception</em>'.
	 * @see java.lang.IllegalArgumentException
	 * @model instanceClass="java.lang.IllegalArgumentException"
	 * @generated
	 */
	EDataType getIllegalArgumentException();

	/**
	 * Returns the meta object for data type '{@link org.cloudsmith.geppetto.forge.IncompleteException <em>Incomplete Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Incomplete Exception</em>'.
	 * @see org.cloudsmith.geppetto.forge.IncompleteException
	 * @model instanceClass="org.cloudsmith.geppetto.forge.IncompleteException"
	 * @generated
	 */
	EDataType getIncompleteException();

	/**
	 * Returns the meta object for data type '{@link java.io.IOException <em>IO Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>IO Exception</em>'.
	 * @see java.io.IOException
	 * @model instanceClass="java.io.IOException"
	 * @generated
	 */
	EDataType getIOException();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>List</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List" typeParameters="E"
	 * @generated
	 */
	EDataType getList();

	/**
	 * Returns the meta object for data type '{@link java.util.Map <em>Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Map</em>'.
	 * @see java.util.Map
	 * @model instanceClass="java.util.Map" typeParameters="K V"
	 * @generated
	 */
	EDataType getMap();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Metadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Metadata</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata
	 * @generated
	 */
	EClass getMetadata();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getAuthor()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getChecksums <em>Checksums</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Checksums</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getChecksums()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Checksums();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.forge.Metadata#getDependencies
	 * <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getDependencies()
	 * @see #getMetadata()
	 * @generated
	 */
	EReference getMetadata_Dependencies();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getDescription()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getFullName <em>Full Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Full Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getFullName()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_FullName();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getLicense <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>License</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getLicense()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_License();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getLocation()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Location();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getName()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getProjectPage <em>Project Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project Page</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getProjectPage()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_ProjectPage();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getSource()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Summary</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getSummary()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Summary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.forge.Metadata#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getTypes()
	 * @see #getMetadata()
	 * @generated
	 */
	EReference getMetadata_Types();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getUser()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_User();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Metadata#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.cloudsmith.geppetto.forge.Metadata#getVersion()
	 * @see #getMetadata()
	 * @generated
	 */
	EAttribute getMetadata_Version();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.ModuleInfo <em>Module Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Info</em>'.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo
	 * @generated
	 */
	EClass getModuleInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getFullName <em>Full Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Full Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo#getFullName()
	 * @see #getModuleInfo()
	 * @generated
	 */
	EAttribute getModuleInfo_FullName();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo#getName()
	 * @see #getModuleInfo()
	 * @generated
	 */
	EAttribute getModuleInfo_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getProjectURL <em>Project URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project URL</em>'.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo#getProjectURL()
	 * @see #getModuleInfo()
	 * @generated
	 */
	EAttribute getModuleInfo_ProjectURL();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo#getVersion()
	 * @see #getModuleInfo()
	 * @generated
	 */
	EAttribute getModuleInfo_Version();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.cloudsmith.geppetto.forge.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.cloudsmith.geppetto.forge.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Provider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Provider</em>'.
	 * @see org.cloudsmith.geppetto.forge.Provider
	 * @generated
	 */
	EClass getProvider();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.ReleaseInfo <em>Release Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Release Info</em>'.
	 * @see org.cloudsmith.geppetto.forge.ReleaseInfo
	 * @generated
	 */
	EClass getReleaseInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.cloudsmith.geppetto.forge.ReleaseInfo#getFile()
	 * @see #getReleaseInfo()
	 * @generated
	 */
	EAttribute getReleaseInfo_File();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.cloudsmith.geppetto.forge.ReleaseInfo#getVersion()
	 * @see #getReleaseInfo()
	 * @generated
	 */
	EAttribute getReleaseInfo_Version();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see org.cloudsmith.geppetto.forge.Repository
	 * @generated
	 */
	EClass getRepository();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Repository#getCacheKey <em>Cache Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Cache Key</em>'.
	 * @see org.cloudsmith.geppetto.forge.Repository#getCacheKey()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_CacheKey();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.forge.Repository#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Repository</em>'.
	 * @see org.cloudsmith.geppetto.forge.Repository#getRepository()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Repository();

	/**
	 * Returns the meta object for data type '<em>String Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>String Array</em>'.
	 * @model instanceClass="java.lang.String[]"
	 * @generated
	 */
	EDataType getStringArray();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.forge.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.cloudsmith.geppetto.forge.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.forge.Type#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.cloudsmith.geppetto.forge.Type#getParameters()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.forge.Type#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.cloudsmith.geppetto.forge.Type#getProperties()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.forge.Type#getProviders <em>Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Providers</em>'.
	 * @see org.cloudsmith.geppetto.forge.Type#getProviders()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Providers();

	/**
	 * Returns the meta object for data type '{@link java.net.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see java.net.URI
	 * @model instanceClass="java.net.URI"
	 * @generated
	 */
	EDataType getURI();

} // ForgePackage
