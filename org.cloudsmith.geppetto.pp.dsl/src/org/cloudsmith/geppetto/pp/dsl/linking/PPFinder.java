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

import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.linking.PPResourceLinker.NameInScopeFilter;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

/**
 * Utility class for finding references.
 * 
 */
public class PPFinder {
	private final static EClass[] DEF_AND_TYPE_ARGUMENTS = {
			PPPackage.Literals.DEFINITION_ARGUMENT, PPTPPackage.Literals.TYPE_ARGUMENT };

	// Note that order is important
	private final static EClass[] DEF_AND_TYPE = { PPTPPackage.Literals.TYPE, PPPackage.Literals.DEFINITION };

	private static final EClass[] FUNC = { PPTPPackage.Literals.FUNCTION };

	private final static EClass[] CLASS_AND_TYPE = {
			PPPackage.Literals.HOST_CLASS_DEFINITION, PPTPPackage.Literals.TYPE };

	private Resource resource;

	@Inject
	private ISearchPathProvider searchPathProvider;

	/**
	 * Access to container manager for PP language
	 */
	@Inject
	private IContainer.Manager manager;

	private PPSearchPath searchPath;

	private Multimap<String, IEObjectDescription> exportedPerLastSegment;

	/**
	 * Access to the 'pp' services (container management and more).
	 */
	@Inject
	IResourceServiceProvider resourceServiceProvider;

	/**
	 * Access to naming of model elements.
	 */
	@Inject
	IQualifiedNameProvider fqnProvider;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider
	 * that is aware of the context (builder, dirty editors, etc.). It is used to obtain the
	 * index for a particular resource. This special provider is obtained here.
	 */
	@Inject
	org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider indexProvider;

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(PPDSLConstants.PP_DEBUG_LINKER)
	private ITracer tracer;

	private Map<String, IEObjectDescription> metaCache;

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	private void buildExportedObjectsIndex(IResourceDescription descr, IResourceDescriptions descriptionIndex) {
		// The current (possibly dirty) exported resources
		IResourceDescription dirty = resourceServiceProvider.getResourceDescriptionManager().getResourceDescription(
			resource);
		String pathToCurrent = resource.getURI().path();

		Multimap<String, IEObjectDescription> map = ArrayListMultimap.create();
		// add all (possibly dirty in global index)
		for(IEObjectDescription d : dirty.getExportedObjects())
			map.put(d.getQualifiedName().getLastSegment(), d);
		// add all from global index, except those for current resource
		for(IEObjectDescription d : getExportedObjects(descr, descriptionIndex))
			if(!d.getEObjectURI().path().equals(pathToCurrent))
				map.put(d.getQualifiedName().getLastSegment(), d);
		exportedPerLastSegment = map;
	}

	protected void cacheMetaParameters(EObject scopeDetermeningObject) {
		// System.err.println("Computing meta cache");
		metaCache = Maps.newHashMap();
		Resource scopeDetermeningResource = scopeDetermeningObject.eResource();

		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(scopeDetermeningResource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());
		if(descr == null)
			return; // give up - some sort of clean build
		EClass wantedType = PPTPPackage.Literals.TYPE_ARGUMENT;
		for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			for(IEObjectDescription objDesc : visibleContainer.getExportedObjects()) {
				QualifiedName q = objDesc.getQualifiedName();
				if("Type".equals(q.getFirstSegment())) {
					if(wantedType == objDesc.getEClass() || wantedType.isSuperTypeOf(objDesc.getEClass()))
						metaCache.put(q.getLastSegment(), objDesc);
				}
			}
		}
	}

	public void configure(EObject o) {
		configure(o.eResource());
	}

	public void configure(Resource r) {
		resource = r;
		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(resource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(resource.getURI());
		manager = resourceServiceProvider.getContainerManager();
		buildExportedObjectsIndex(descr, descriptionIndex);

	}

	protected List<IEObjectDescription> findExternal(EObject scopeDetermeningObject, QualifiedName fqn,
			PPImportedNamesAdapter importedNames, EClass... eClasses) {
		if(scopeDetermeningObject == null)
			throw new IllegalArgumentException("scope determening object is null");
		if(fqn == null)
			throw new IllegalArgumentException("name is null");
		if(eClasses == null || eClasses.length < 1)
			throw new IllegalArgumentException("eClass is null or empty");

		if(fqn.getSegmentCount() == 1 && "".equals(fqn.getSegment(0)))
			throw new IllegalArgumentException("FQN has one empty segment");

		// Not meaningful to record the fact that an Absolute reference was used as nothing
		// is named with an absolute FQN (i.e. it is only used to do lookup).
		final boolean absoluteFQN = "".equals(fqn.getSegment(0));
		importedNames.add(absoluteFQN
				? fqn.skipFirst(1)
				: fqn);

		List<IEObjectDescription> targets = Lists.newArrayList();
		Resource scopeDetermeningResource = scopeDetermeningObject.eResource();

		if(scopeDetermeningResource != resource) {
			// This is a lookup in the perspective of some other resource
			IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(scopeDetermeningResource);
			IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());

			// GIVE UP (the system is performing a build clean).
			if(descr == null)
				return targets;
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);

			// for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			// for(EClass aClass : eClasses)
			for(IEObjectDescription objDesc : new NameInScopeFilter(getExportedObjects(descr, descriptionIndex),
			// visibleContainer.getExportedObjects(),
			fqn, nameOfScope, eClasses))
				targets.add(objDesc);
		}
		else {
			// This is lookup from the main resource perspecive
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);
			for(IEObjectDescription objDesc : new NameInScopeFilter(
				exportedPerLastSegment.get(fqn.getLastSegment()), fqn, nameOfScope, eClasses))
				targets.add(objDesc);

		}
		if(tracer.isTracing()) {
			for(IEObjectDescription d : targets)
				tracer.trace("    : ", converter.toString(d.getName()), " in: ", d.getEObjectURI().path());
		}
		return searchPathAdjusted(targets);
	}

	private List<IEObjectDescription> findFunction(EObject scopeDetermeningObject, QualifiedName fqn,
			PPImportedNamesAdapter importedNames) {
		return findExternal(scopeDetermeningObject, fqn, importedNames, FUNC);
	}

	private List<IEObjectDescription> findFunction(EObject scopeDetermeningObject, String name,
			PPImportedNamesAdapter importedNames) {
		return findFunction(scopeDetermeningObject, converter.toQualifiedName(name), importedNames);
	}

	protected List<IEObjectDescription> findHostClasses(EObject scopeDetermeningResource, String name,
			PPImportedNamesAdapter importedNames) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		// make last segments initial char lower case (for references to the type itself - eg. 'File' instead of
		// 'file'.
		fqn = fqn.skipLast(1).append(toInitialLowerCase(fqn.getLastSegment()));
		return findExternal(scopeDetermeningResource, fqn, importedNames, CLASS_AND_TYPE);
	}

	private Iterable<IEObjectDescription> getExportedObjects(IResourceDescription descr,
			IResourceDescriptions descriptionIndex) {
		return Iterables.concat(Iterables.transform(
			manager.getVisibleContainers(descr, descriptionIndex),
			new Function<IContainer, Iterable<IEObjectDescription>>() {

				@Override
				public Iterable<IEObjectDescription> apply(IContainer from) {
					return from.getExportedObjects();
				}

			}));
	}

	private QualifiedName getNameOfScope(EObject o) {
		QualifiedName result = null;
		for(; o != null; o = o.eContainer()) {
			result = fqnProvider.getFullyQualifiedName(o);
			if(result != null)
				return result;
		}
		return QualifiedName.EMPTY;
	}

	/**
	 * Adjusts the list of found targets in accordance with the search path for the resource being
	 * linked. This potentially resolves ambiguities (if found result is further away on the path).
	 * May return more than one result, if more than one resolution exist with the same path index.
	 * 
	 * @param targets
	 * @return list of descriptions with lowest index.
	 */
	private List<IEObjectDescription> searchPathAdjusted(List<IEObjectDescription> targets) {
		int minIdx = Integer.MAX_VALUE;
		List<IEObjectDescription> result = Lists.newArrayList();
		for(IEObjectDescription d : targets) {
			int idx = searchPath.searchIndexOf(d);
			if(idx < 0)
				continue; // not found, skip
			if(idx < minIdx) {
				minIdx = idx;
				result.clear(); // forget worse result
			}
			// only remember if equal to best found so far
			if(idx <= minIdx)
				result.add(d);
		}
		return result;
	}

	private String toInitialLowerCase(String s) {
		if(s.length() < 1 || Character.isLowerCase(s.charAt(0)))
			return s;
		StringBuffer buf = new StringBuffer(s);
		buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));
		return buf.toString();
	}
}
