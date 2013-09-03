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
package com.puppetlabs.geppetto.catalog.util;

import com.puppetlabs.geppetto.catalog.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * 
 * @see com.puppetlabs.geppetto.catalog.CatalogPackage
 * @generated
 */
public class CatalogAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static CatalogPackage modelPackage;

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CatalogSwitch<Adapter> modelSwitch = new CatalogSwitch<Adapter>() {
		@Override
		public Adapter caseCatalog(Catalog object) {
			return createCatalogAdapter();
		}

		@Override
		public Adapter caseCatalogEdge(CatalogEdge object) {
			return createCatalogEdgeAdapter();
		}

		@Override
		public Adapter caseCatalogMetadata(CatalogMetadata object) {
			return createCatalogMetadataAdapter();
		}

		@Override
		public Adapter caseCatalogResource(CatalogResource object) {
			return createCatalogResourceAdapter();
		}

		@Override
		public Adapter caseCatalogResourceParameter(CatalogResourceParameter object) {
			return createCatalogResourceParameterAdapter();
		}

		@Override
		public Adapter caseTaggable(Taggable object) {
			return createTaggableAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CatalogAdapterFactory() {
		if(modelPackage == null) {
			modelPackage = CatalogPackage.eINSTANCE;
		}
	}

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.Catalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.Catalog
	 * @generated
	 */
	public Adapter createCatalogAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.CatalogEdge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.CatalogEdge
	 * @generated
	 */
	public Adapter createCatalogEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.CatalogMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.CatalogMetadata
	 * @generated
	 */
	public Adapter createCatalogMetadataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.CatalogResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.CatalogResource
	 * @generated
	 */
	public Adapter createCatalogResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.CatalogResourceParameter <em>Resource Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.CatalogResourceParameter
	 * @generated
	 */
	public Adapter createCatalogResourceParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.puppetlabs.geppetto.catalog.Taggable <em>Taggable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see com.puppetlabs.geppetto.catalog.Taggable
	 * @generated
	 */
	public Adapter createTaggableAdapter() {
		return null;
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if(object == modelPackage) {
			return true;
		}
		if(object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

} // CatalogAdapterFactory
