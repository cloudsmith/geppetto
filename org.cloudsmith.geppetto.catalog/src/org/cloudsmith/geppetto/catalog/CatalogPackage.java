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
package org.cloudsmith.geppetto.catalog;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.cloudsmith.geppetto.catalog.CatalogFactory
 * @model kind="package"
 * @generated
 */
public interface CatalogPackage extends EPackage {
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
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl <em>Catalog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalog()
		 * @generated
		 */
		EClass CATALOG = eINSTANCE.getCatalog();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG__NAME = eINSTANCE.getCatalog_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG__VERSION = eINSTANCE.getCatalog_Version();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CATALOG__RESOURCES = eINSTANCE.getCatalog_Resources();

		/**
		 * The meta object literal for the '<em><b>Classes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG__CLASSES = eINSTANCE.getCatalog_Classes();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CATALOG__METADATA = eINSTANCE.getCatalog_Metadata();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CATALOG__EDGES = eINSTANCE.getCatalog_Edges();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogResource()
		 * @generated
		 */
		EClass CATALOG_RESOURCE = eINSTANCE.getCatalogResource();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__FILE = eINSTANCE.getCatalogResource_File();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__LINE = eINSTANCE.getCatalogResource_Line();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__TYPE = eINSTANCE.getCatalogResource_Type();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__TITLE = eINSTANCE.getCatalogResource_Title();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CATALOG_RESOURCE__PARAMETERS = eINSTANCE.getCatalogResource_Parameters();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__VIRTUAL = eINSTANCE.getCatalogResource_Virtual();

		/**
		 * The meta object literal for the '<em><b>Exported</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE__EXPORTED = eINSTANCE.getCatalogResource_Exported();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogMetadataImpl <em>Metadata</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogMetadataImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogMetadata()
		 * @generated
		 */
		EClass CATALOG_METADATA = eINSTANCE.getCatalogMetadata();

		/**
		 * The meta object literal for the '<em><b>Api version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_METADATA__API_VERSION = eINSTANCE.getCatalogMetadata_Api_version();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogEdge()
		 * @generated
		 */
		EClass CATALOG_EDGE = eINSTANCE.getCatalogEdge();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_EDGE__TARGET = eINSTANCE.getCatalogEdge_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_EDGE__SOURCE = eINSTANCE.getCatalogEdge_Source();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceParameterImpl <em>Resource Parameter</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogResourceParameterImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogResourceParameter()
		 * @generated
		 */
		EClass CATALOG_RESOURCE_PARAMETER = eINSTANCE.getCatalogResourceParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE_PARAMETER__NAME = eINSTANCE.getCatalogResourceParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CATALOG_RESOURCE_PARAMETER__VALUE = eINSTANCE.getCatalogResourceParameter_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.catalog.impl.TaggableImpl <em>Taggable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.catalog.impl.TaggableImpl
		 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getTaggable()
		 * @generated
		 */
		EClass TAGGABLE = eINSTANCE.getTaggable();

		/**
		 * The meta object literal for the '<em><b>Tags</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TAGGABLE__TAGS = eINSTANCE.getTaggable_Tags();

	}

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "catalog";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.cloudsmith.org/geppetto/1.0.0/Catalog";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "catalog";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	CatalogPackage eINSTANCE = org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.TaggableImpl <em>Taggable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.TaggableImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getTaggable()
	 * @generated
	 */
	int TAGGABLE = 5;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TAGGABLE__TAGS = 0;

	/**
	 * The number of structural features of the '<em>Taggable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TAGGABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl <em>Catalog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalog()
	 * @generated
	 */
	int CATALOG = 0;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__TAGS = TAGGABLE__TAGS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__NAME = TAGGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__VERSION = TAGGABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__RESOURCES = TAGGABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__CLASSES = TAGGABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__METADATA = TAGGABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG__EDGES = TAGGABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_FEATURE_COUNT = TAGGABLE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogResource()
	 * @generated
	 */
	int CATALOG_RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__TAGS = TAGGABLE__TAGS;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__FILE = TAGGABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__LINE = TAGGABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__TYPE = TAGGABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__TITLE = TAGGABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__PARAMETERS = TAGGABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__VIRTUAL = TAGGABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Exported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE__EXPORTED = TAGGABLE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE_FEATURE_COUNT = TAGGABLE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogMetadataImpl <em>Metadata</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogMetadataImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogMetadata()
	 * @generated
	 */
	int CATALOG_METADATA = 2;

	/**
	 * The feature id for the '<em><b>Api version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_METADATA__API_VERSION = 0;

	/**
	 * The number of structural features of the '<em>Metadata</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_METADATA_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogEdge()
	 * @generated
	 */
	int CATALOG_EDGE = 3;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_EDGE__TARGET = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_EDGE__SOURCE = 1;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_EDGE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceParameterImpl <em>Resource Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogResourceParameterImpl
	 * @see org.cloudsmith.geppetto.catalog.impl.CatalogPackageImpl#getCatalogResourceParameter()
	 * @generated
	 */
	int CATALOG_RESOURCE_PARAMETER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE_PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE_PARAMETER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Resource Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CATALOG_RESOURCE_PARAMETER_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.Catalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Catalog</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog
	 * @generated
	 */
	EClass getCatalog();

	/**
	 * Returns the meta object for the attribute list '{@link org.cloudsmith.geppetto.catalog.Catalog#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Classes</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getClasses()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_Classes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.catalog.Catalog#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getEdges()
	 * @see #getCatalog()
	 * @generated
	 */
	EReference getCatalog_Edges();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.catalog.Catalog#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Metadata</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getMetadata()
	 * @see #getCatalog()
	 * @generated
	 */
	EReference getCatalog_Metadata();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.Catalog#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getName()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.catalog.Catalog#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getResources()
	 * @see #getCatalog()
	 * @generated
	 */
	EReference getCatalog_Resources();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.Catalog#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Catalog#getVersion()
	 * @see #getCatalog()
	 * @generated
	 */
	EAttribute getCatalog_Version();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.CatalogEdge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogEdge
	 * @generated
	 */
	EClass getCatalogEdge();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogEdge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogEdge#getSource()
	 * @see #getCatalogEdge()
	 * @generated
	 */
	EAttribute getCatalogEdge_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogEdge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogEdge#getTarget()
	 * @see #getCatalogEdge()
	 * @generated
	 */
	EAttribute getCatalogEdge_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CatalogFactory getCatalogFactory();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.CatalogMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Metadata</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogMetadata
	 * @generated
	 */
	EClass getCatalogMetadata();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogMetadata#getApi_version <em>Api version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Api version</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogMetadata#getApi_version()
	 * @see #getCatalogMetadata()
	 * @generated
	 */
	EAttribute getCatalogMetadata_Api_version();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.CatalogResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource
	 * @generated
	 */
	EClass getCatalogResource();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#isExported <em>Exported</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Exported</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#isExported()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_Exported();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#getFile()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_File();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#getLine()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_Line();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getParameters
	 * <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#getParameters()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EReference getCatalogResource_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#getTitle()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#getType()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResource#isVirtual <em>Virtual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResource#isVirtual()
	 * @see #getCatalogResource()
	 * @generated
	 */
	EAttribute getCatalogResource_Virtual();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter <em>Resource Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource Parameter</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResourceParameter
	 * @generated
	 */
	EClass getCatalogResourceParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getName()
	 * @see #getCatalogResourceParameter()
	 * @generated
	 */
	EAttribute getCatalogResourceParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getValue()
	 * @see #getCatalogResourceParameter()
	 * @generated
	 */
	EAttribute getCatalogResourceParameter_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.catalog.Taggable <em>Taggable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Taggable</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Taggable
	 * @generated
	 */
	EClass getTaggable();

	/**
	 * Returns the meta object for the attribute list '{@link org.cloudsmith.geppetto.catalog.Taggable#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Tags</em>'.
	 * @see org.cloudsmith.geppetto.catalog.Taggable#getTags()
	 * @see #getTaggable()
	 * @generated
	 */
	EAttribute getTaggable_Tags();

} // CatalogPackage
