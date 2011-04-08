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
package org.cloudsmith.geppetto.forge.util;

import org.cloudsmith.geppetto.forge.*;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.Documented;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.Parameter;
import org.cloudsmith.geppetto.forge.Property;
import org.cloudsmith.geppetto.forge.Provider;
import org.cloudsmith.geppetto.forge.ReleaseInfo;
import org.cloudsmith.geppetto.forge.Repository;
import org.cloudsmith.geppetto.forge.Type;
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
 * @see org.cloudsmith.geppetto.forge.ForgePackage
 * @generated
 */
public class ForgeAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ForgePackage modelPackage;

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForgeSwitch<Adapter> modelSwitch = new ForgeSwitch<Adapter>() {
		@Override
		public Adapter caseCache(Cache object) {
			return createCacheAdapter();
		}

		@Override
		public Adapter caseDependency(Dependency object) {
			return createDependencyAdapter();
		}

		@Override
		public Adapter caseDocumented(Documented object) {
			return createDocumentedAdapter();
		}

		@Override
		public Adapter caseForge(Forge object) {
			return createForgeAdapter();
		}

		@Override
		public Adapter caseForgeService(ForgeService object) {
			return createForgeServiceAdapter();
		}

		@Override
		public Adapter caseMetadata(Metadata object) {
			return createMetadataAdapter();
		}

		@Override
		public Adapter caseModuleInfo(ModuleInfo object) {
			return createModuleInfoAdapter();
		}

		@Override
		public Adapter caseParameter(Parameter object) {
			return createParameterAdapter();
		}

		@Override
		public Adapter caseProperty(Property object) {
			return createPropertyAdapter();
		}

		@Override
		public Adapter caseProvider(Provider object) {
			return createProviderAdapter();
		}

		@Override
		public Adapter caseReleaseInfo(ReleaseInfo object) {
			return createReleaseInfoAdapter();
		}

		@Override
		public Adapter caseRepository(Repository object) {
			return createRepositoryAdapter();
		}

		@Override
		public Adapter caseType(Type object) {
			return createTypeAdapter();
		}

		@Override
		public Adapter caseVersionRequirement(VersionRequirement object) {
			return createVersionRequirementAdapter();
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
	public ForgeAdapterFactory() {
		if(modelPackage == null) {
			modelPackage = ForgePackage.eINSTANCE;
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
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Cache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Cache
	 * @generated
	 */
	public Adapter createCacheAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Dependency
	 * @generated
	 */
	public Adapter createDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Documented <em>Documented</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Documented
	 * @generated
	 */
	public Adapter createDocumentedAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Forge <em>Forge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Forge
	 * @generated
	 */
	public Adapter createForgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.ForgeService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.ForgeService
	 * @generated
	 */
	public Adapter createForgeServiceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Metadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Metadata
	 * @generated
	 */
	public Adapter createMetadataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.ModuleInfo <em>Module Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.ModuleInfo
	 * @generated
	 */
	public Adapter createModuleInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Provider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Provider
	 * @generated
	 */
	public Adapter createProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.ReleaseInfo <em>Release Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.ReleaseInfo
	 * @generated
	 */
	public Adapter createReleaseInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Repository
	 * @generated
	 */
	public Adapter createRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cloudsmith.geppetto.forge.VersionRequirement <em>Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.cloudsmith.geppetto.forge.VersionRequirement
	 * @generated
	 */
	public Adapter createVersionRequirementAdapter() {
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

} // ForgeAdapterFactory
