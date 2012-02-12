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
package org.cloudsmith.geppetto.pp.dsl.linking;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Provider;

/**
 * A ResourceDescription for PP that adds specially imported names via resource adapter.
 * 
 */
public class PPResourceDescription extends DefaultResourceDescription {
	private IResourceScopeCache cache;

	private PPResourceDescriptionStrategy strategy;

	private static final String PPREFERENCE_DESCRIPTIONS_CACHE_KEY = DefaultReferenceDescription.class.getName() +
			"#getPPReferenceDescriptions";

	public PPResourceDescription(Resource resource, IDefaultResourceDescriptionStrategy strategy,
			IResourceScopeCache cache) {
		super(resource, strategy, cache);
		this.cache = cache;
		if(strategy instanceof PPResourceDescriptionStrategy)
			this.strategy = (PPResourceDescriptionStrategy) strategy;

	}

	protected List<PPReferenceDescription> computePPReferenceDescriptions() {
		if(strategy == null)
			throw new IllegalStateException(
				"Configuration not correct. PPResourceDescription requires a PPResourceDescriptionStrategy");

		final List<PPReferenceDescription> referenceDescriptions = Lists.newArrayList();
		IAcceptor<PPReferenceDescription> acceptor = new IAcceptor<PPReferenceDescription>() {
			public void accept(PPReferenceDescription referenceDescription) {
				referenceDescriptions.add(referenceDescription);
			}
		};

		EcoreUtil2.resolveLazyCrossReferences(getResource(), CancelIndicator.NullImpl);
		final Iterable<IEObjectDescription> allExported = getExportedObjects();

		Map<EObject, IEObjectDescription> eObject2exportedEObjects = createEObject2ExportedEObjectsMap(allExported);
		TreeIterator<EObject> contents = EcoreUtil.getAllProperContents(getResource(), true);
		while(contents.hasNext()) {
			EObject eObject = contents.next();
			List<IEObjectDescription> referenced = CrossReferenceAdapterFactory.eINSTANCE.get(eObject);
			if(referenced == null || referenced.isEmpty())
				continue;
			IEObjectDescription sourceContainer = findClosestExportedContainerDescriptor(
				eObject2exportedEObjects.get(eObject), allExported);
			for(IEObjectDescription targetDescriptor : referenced)
				if(!strategy.createPPReferenceDescriptions(eObject, sourceContainer, targetDescriptor, acceptor))
					contents.prune();
		}
		return referenceDescriptions;
	}

	/**
	 * Returns the length of a containing URI's fragment or 0 if the candidate is not a container.
	 * 
	 * @param contained
	 * @param containerCandidate
	 * @return
	 */
	private int containerSpecificity(IEObjectDescription contained, IEObjectDescription containerCandidate) {
		URI containedURI = contained.getEObjectURI();
		URI containerURI = containerCandidate.getEObjectURI();
		if(!containedURI.path().equals(containerURI.path()))
			return 0;
		// same resource, if desc's fragment is in at the start of the path, then contained is contained by containerCandidate
		if(containerURI.fragment().startsWith(containedURI.fragment()))
			return containerURI.fragment().length();
		return 0;
	}

	public IEObjectDescription findClosestExportedContainerDescriptor(IEObjectDescription element,
			Iterable<IEObjectDescription> exportedElements) {
		IEObjectDescription closest = null;
		int maxSpecificity = 0;
		for(IEObjectDescription containerCandidate : exportedElements) {
			int specificity = containerSpecificity(element, containerCandidate);
			if(specificity > maxSpecificity) {
				maxSpecificity = specificity;
				closest = containerCandidate;
			}
		}
		return closest;
	}

	/**
	 * Override that adds the specially imported names to the default.
	 * 
	 * @see org.eclipse.xtext.resource.impl.DefaultResourceDescription#getImportedNames()
	 */
	@Override
	public Iterable<QualifiedName> getImportedNames() {
		Iterable<QualifiedName> superResult = super.getImportedNames();
		PPImportedNamesAdapter adapter = PPImportedNamesAdapterFactory.eINSTANCE.adapt(getResource());
		if(adapter != null) {
			Collection<QualifiedName> imported = adapter.getNames();
			if(imported.isEmpty())
				return superResult;
			return Iterables.concat(superResult, ImmutableSet.copyOf(imported));
		}
		return superResult;
	}

	/**
	 * Gets an Iterable over PPReferenceDescriptions. This is similar to {@link #getReferenceDescriptions()} but works
	 * with adapters and IEObjectDescription.
	 */
	public Iterable<PPReferenceDescription> getPPReferenceDescriptions() {
		return cache.get(
			PPREFERENCE_DESCRIPTIONS_CACHE_KEY, getResource(), new Provider<List<PPReferenceDescription>>() {
				public List<PPReferenceDescription> get() {
					return computePPReferenceDescriptions();
				}
			});
	}

}
