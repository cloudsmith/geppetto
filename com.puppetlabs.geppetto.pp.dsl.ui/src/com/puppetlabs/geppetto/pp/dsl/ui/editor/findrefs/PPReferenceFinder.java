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
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.puppetlabs.geppetto.pp.dsl.adapters.CrossReferenceAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.linking.PPReferenceDescription;
import com.puppetlabs.geppetto.pp.dsl.ui.internal.util.CancelablePredicate;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * This is a to a large part a copy of the ReferenceFinder in the corresponding package in Xtext, modified to search
 * for references the Geppetto way.
 * 
 * 
 */
public class PPReferenceFinder {

	public static class DescriptionURIPredicate extends CancelablePredicate<IEObjectDescription> {
		final private URI uri;

		public DescriptionURIPredicate(EObject eobj, IProgressMonitor monitor) {
			super(monitor);
			// Create URI the same way as IEObjectDescription URI is created
			uri = EObjectDescription.create("dummy", eobj).getEObjectURI();
		}

		public DescriptionURIPredicate(URI uri, IProgressMonitor monitor) {
			super(monitor);
			// Create URI the same way as IEObjectDescription URI is created
			this.uri = uri;
		}

		@Override
		public boolean monitoredApply(IEObjectDescription input) {
			return input.getEObjectURI().equals(uri);
		}

	}

	/**
	 * Executes <code>work</code> on the element referred to by the <code>targetURI</code>. That involves reloading the
	 * element if it is proxified or the editor it belonged to has been closed.
	 */
	interface ILocalResourceAccess {
		<R> R readOnly(URI resourceURI, IUnitOfWork<R, ResourceSet> work);
	}

	interface IPPQueryData {
		String getLabel();

		URI getLeadElementURI();

		URI getLocalContextResourceURI();

		Predicate<IReferenceDescription> getResultFilter();

		Set<URI> getTargetURIs();
	}

	private static class PPCrossReferencer {

		/**
		 * @param targetResources
		 * @return
		 */
		public static Map<EObject, Collection<IEObjectDescription>> find(Set<Resource> targetResources) {
			Map<EObject, Collection<IEObjectDescription>> result = Maps.newHashMap();
			for(Resource r : targetResources) {
				TreeIterator<EObject> itor = r.getAllContents();
				while(itor.hasNext()) {
					EObject e = itor.next();
					List<IEObjectDescription> references = CrossReferenceAdapterFactory.eINSTANCE.get(e);
					if(references != null && references.size() > 0)
						result.put(e, references);

				}
			}
			return result;
		}
	}

	private IResourceDescriptions index;

	private static Function<EObject, Resource> eobj2Resource = new Function<EObject, Resource>() {
		@Override
		public Resource apply(EObject from) {
			return from.eResource();
		}
	};

	private static final Iterable<IEObjectDescription> emptyIterable = new Iterable<IEObjectDescription>() {

		@Override
		public Iterator<IEObjectDescription> iterator() {
			return Iterators.emptyIterator();
		}

	};

	private static final Function<Map<EObject, Collection<IEObjectDescription>>, Integer> sizeFunction = new Function<Map<EObject, Collection<IEObjectDescription>>, Integer>() {

		@Override
		public Integer apply(Map<EObject, Collection<IEObjectDescription>> from) {
			// size of all references candidates
			int size = 0;
			for(Collection<?> c : from.values())
				size += c.size();
			return size;
		}

	};

	@Inject
	public PPReferenceFinder(IResourceDescriptions index) {
		this.index = index;
	}

	private void checkMonitor(IProgressMonitor monitor) {
		if(monitor != null && monitor.isCanceled())
			throw new OperationCanceledException("Puppet reference search canceled");
	}

	/**
	 * Returns the length of a containing URI's fragment or 0 if the candidate is not a container.
	 * 
	 * @param contained
	 * @param containerCandidate
	 * @return
	 */
	private int containerSpecificity(URI containedURI, URI containerURI) {
		if(!containedURI.path().equals(containerURI.path()))
			return 0;
		// same resource, if desc's fragment is in at the start of the path, then contained is contained by containerCandidate
		if(containedURI.fragment() != null && containedURI.fragment().startsWith(containerURI.fragment()))
			return containerURI.fragment().length();
		return 0;
	}

	public void findAllReferences(IPPQueryData queryData, ILocalResourceAccess localResourceAccess,
			final IAcceptor<IReferenceDescription> acceptor, IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
		if(!queryData.getTargetURIs().isEmpty()) {
			findLocalReferences(queryData, localResourceAccess, acceptor, subMonitor.newChild(1));
			findIndexedReferences(queryData, acceptor, subMonitor.newChild(1));
		}
	}

	protected IEObjectDescription findClosestExportedContainerDescriptor(EObject element,
			Iterable<IEObjectDescription> exportedElements) {
		IEObjectDescription closest = null;
		int maxSpecificity = 0;
		for(IEObjectDescription containerCandidate : exportedElements) {
			int specificity = containerSpecificity(
				EcoreUtil2.getNormalizedURI(element), containerCandidate.getEObjectURI());
			if(specificity > maxSpecificity) {
				maxSpecificity = specificity;
				closest = containerCandidate;
			}
		}
		return closest;
	}

	/**
	 * @param queryData
	 * @param acceptor
	 * @param newChild
	 */
	public void findIndexedReferences(final IPPQueryData queryData, final IAcceptor<IReferenceDescription> acceptor,
			IProgressMonitor monitor) {
		findIndexedReferences(queryData.getTargetURIs(), acceptor, queryData.getResultFilter(), monitor);
	}

	protected void findIndexedReferences(Set<URI> targetURIs, IAcceptor<IReferenceDescription> acceptor,
			Predicate<IReferenceDescription> filter, IProgressMonitor monitor) {

		// use only resource path of targets to disqualify references from within the targets
		Set<URI> targetResourceURIs = newHashSet(transform(targetURIs, new Function<URI, URI>() {
			public URI apply(URI from) {
				return from.trimFragment();
			}
		}));

		// All resources that may have one of the targets as source
		final Iterable<IResourceDescription> allResourceDescriptions = index.getAllResourceDescriptions();
		int numResources = Iterables.size(allResourceDescriptions); // NOTE: the Iterables.size is optimized for Collection<?>
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Find puppet references", numResources);

		for(IResourceDescription resourceDescription : allResourceDescriptions) {

			if(resourceDescription != null) {

				if(subMonitor.isCanceled())
					return;

				if(!targetResourceURIs.contains(resourceDescription.getURI())) {
					// local references are filtered out - this is an external reference

					for(IReferenceDescription referenceDescription : resourceDescription.getReferenceDescriptions()) {
						if(subMonitor.isCanceled())
							return;
						if(targetURIs.contains(referenceDescription.getTargetEObjectUri()) &&
								(filter == null || filter.apply(referenceDescription))) {
							acceptor.accept(referenceDescription);
						}
					}

				}
				subMonitor.worked(1);
			}
		}

	}

	/**
	 * @param queryData
	 * @param localResourceAccess
	 * @param acceptor
	 * @param newChild
	 */
	private void findLocalReferences(final IPPQueryData queryData, ILocalResourceAccess localResourceAccess,
			final IAcceptor<IReferenceDescription> acceptor, IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, "Find references", 1);
		localResourceAccess.readOnly(queryData.getLocalContextResourceURI(), new IUnitOfWork<Boolean, ResourceSet>() {
			public Boolean exec(ResourceSet localContext) throws Exception {
				Set<EObject> targets = newHashSet();
				for(URI targetURI : queryData.getTargetURIs()) {
					EObject target = localContext.getEObject(targetURI, true);
					if(target != null)
						targets.add(target);
				}
				findLocalReferences(targets, acceptor, queryData.getResultFilter(), subMonitor);
				return true;
			}
		});

	}

	public void findLocalReferences(Set<? extends EObject> targets, IAcceptor<IReferenceDescription> acceptor,
			Predicate<IReferenceDescription> filter, IProgressMonitor monitor) {
		checkMonitor(monitor);

		if(targets != null && !targets.isEmpty()) {
			Set<Resource> targetResources = Sets.newHashSet(Iterables.transform(targets, eobj2Resource));

			Map<EObject, Collection<IEObjectDescription>> objectsWithXRef = PPCrossReferencer.find(targetResources);

			SubMonitor subMonitor = SubMonitor.convert(monitor, "Find local puppet references", targets.size());
			for(EObject target : targets) {

				Predicate<IEObjectDescription> p = new DescriptionURIPredicate(
					target, subMonitor.newChild(sizeFunction.apply(objectsWithXRef)));

				Map<Resource, Iterable<IEObjectDescription>> exportedObjectsByResourceCache = Maps.newHashMap();
				for(Entry<EObject, Collection<IEObjectDescription>> entry : objectsWithXRef.entrySet()) {
					for(IEObjectDescription targetCandidate : entry.getValue())
						if(p.apply(targetCandidate)) {
							EObject eObj = entry.getKey();
							Resource r = eObj.eResource();
							Iterable<IEObjectDescription> exported = exportedObjectsByResourceCache.get(r);
							if(exported == null)
								exportedObjectsByResourceCache.put(r, exported = getExportedElements(r));
							// find the exported container closest to the source referencing the target
							IEObjectDescription closestExported = findClosestExportedContainerDescriptor(eObj, exported);
							IReferenceDescription refDesc = PPReferenceDescription.create(
								EcoreUtil2.getNormalizedURI(eObj), closestExported, targetCandidate);
							if(filter == null || filter.apply(refDesc))
								acceptor.accept(refDesc);
						}
				}
			}
		}
	}

	protected Iterable<IEObjectDescription> getExportedElements(Resource resource) {
		IResourceDescription resourceDescription = index.getResourceDescription(EcoreUtil2.getNormalizedURI(resource));
		if(resourceDescription != null)
			return resourceDescription.getExportedObjects();
		return emptyIterable;
	}
}
