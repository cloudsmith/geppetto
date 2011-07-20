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

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.LiteralExpression;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

/**
 * Handles special linking of ResourceExpression, ResourceBody and Function references.
 */
public class PPResourceLinker {

	static class NameInScopeFilter implements Iterable<IEObjectDescription> {
		private final Iterable<IEObjectDescription> unfiltered;

		final private NameInScopePredicate filter;

		NameInScopeFilter(Iterable<IEObjectDescription> unfiltered, QualifiedName name, QualifiedName scope,
				EClass[] eclasses) {
			boolean absolute = name.getSegmentCount() > 0 && "".equals(name.getSegment(0));
			this.unfiltered = unfiltered;
			filter = new NameInScopePredicate(absolute, absolute
					? name.skipFirst(1)
					: name, scope, eclasses);
		}

		public Iterator<IEObjectDescription> iterator() {
			return Iterators.filter(unfiltered.iterator(), filter);
		}
	}

	public static class NameInScopePredicate implements Predicate<IEObjectDescription> {
		final QualifiedName scopeName;

		final QualifiedName name;

		final boolean absolute;

		final EClass[] eclasses;

		public NameInScopePredicate(boolean absolute, QualifiedName name, QualifiedName scopeName, EClass[] eclasses) {
			this.absolute = absolute;
			this.scopeName = scopeName == null
					? QualifiedName.EMPTY
					: scopeName;
			this.name = name;
			this.eclasses = eclasses;
		}

		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName candidateName = candidate.getQualifiedName();
			// error, not a valid name (can not possibly match).
			if(candidateName.getSegmentCount() == 0)
				return false;

			// filter out all that do not match on last segment (i.a. ?::...::x <-> ?::...::y)
			try {
				if(!candidateName.getLastSegment().equals(name.getLastSegment()))
					return false;
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("AOB");
			}

			// it is faster to compare exact match as this is a common case, before trying isSuperTypeOf
			int found = -1;
			for(int i = 0; i < eclasses.length; i++)
				if(eclasses[i] == candidate.getEClass() || eclasses[i].isSuperTypeOf(candidate.getEClass())) {
					found = i;
					break;
				}
			if(found < 0)
				return false; // wrong type

			if(absolute)
				return candidateName.equals(name);

			// Since most references are exact (they are global), this is the fastest for the common case.
			if(candidateName.equals(name))
				return true;

			// need to find the common outer scope
			QualifiedName candidateParent = candidateName.skipLast(1);

			// Note: it is not possible to refer to the parent i.e. class foo::bar { bar { }}

			// find the common outer scope
			int commonCount = 0;
			int limit = Math.min(scopeName.getSegmentCount(), candidateParent.getSegmentCount());
			for(int i = 0; i < limit; i++)
				if(scopeName.getSegment(i).equals(candidateName.getSegment(i)))
					commonCount++;
				else
					break;
			// if no common ancestor, then equality check above should have found it.
			if(commonCount == 0)
				return false;

			// commonPart+requestedName == candidate (i.e. wanted "c::d" in scope "a::b" - check "a::b::c::d"
			return scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(name).equals(candidateName);
		}
	}

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(PPDSLConstants.PP_DEBUG_LINKER)
	private ITracer tracer;

	/**
	 * Access to container manager for PP language
	 */
	@Inject
	private IContainer.Manager manager;

	/**
	 * Access to the 'pp' services (container management and more).
	 */
	@Inject
	IResourceServiceProvider resourceServiceProvider;

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider
	 * that is aware of the context (builder, dirty editors, etc.). It is used to obtain the
	 * index for a particular resource. This special provider is obtained here.
	 */
	@Inject
	org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider indexProvider;

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	/**
	 * Access to naming of model elements.
	 */
	@Inject
	IQualifiedNameProvider fqnProvider;

	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	private Map<String, IEObjectDescription> metaCache;

	protected final static EClass[] DEF_AND_TYPE_ARGUMENTS = {
			PPPackage.Literals.DEFINITION_ARGUMENT, PPTPPackage.Literals.TYPE_ARGUMENT };

	// Note that order is important
	protected final static EClass[] DEF_AND_TYPE = { PPTPPackage.Literals.TYPE, PPPackage.Literals.DEFINITION };

	private static final EClass[] FUNC = { PPTPPackage.Literals.FUNCTION };

	private final static EClass[] CLASS_AND_TYPE = {
			PPPackage.Literals.HOST_CLASS_DEFINITION, PPTPPackage.Literals.TYPE };

	private Multimap<String, IEObjectDescription> exportedPerLastSegment;

	private Resource resource;

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	protected void _link(FunctionCall o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {

		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		String name = ((LiteralNameOrReference) o.getLeftExpr()).getValue();

		final List<IEObjectDescription> found = findFunction(o, name, importedNames);
		if(found.size() > 0) {
			// record resolution at resource level
			importedNames.addResolved(found);
			return; // ok, found
		}

		acceptor.acceptError(
			"Unknown function: '" + name + "'", o.getLeftExpr(), IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE);
		// record failure at resource level
		importedNames.addUnresolved(converter.toQualifiedName(name));
	}

	protected void _link(HostClassDefinition o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		LiteralExpression parent = o.getParent();
		if(parent == null)
			return;
		String parentString = null;
		if(parent.eClass() == PPPackage.Literals.LITERAL_DEFAULT)
			parentString = "default";
		else if(parent.eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE)
			parentString = ((LiteralNameOrReference) parent).getValue();
		if(parentString == null || parentString.length() < 1)
			return;

		List<IEObjectDescription> descs = findHostClasses(o, parentString, importedNames);
		if(descs.size() > 0) {
			// make list only contain unique references
			descs = Lists.newArrayList(Sets.newHashSet(descs));
			// // removes containers that contain o
			// removeDisqualifiedContainers(descs, o);

			// record resolution at resource level
			importedNames.addResolved(descs);

			if(descs.size() > 1) {
				// this is an ambiguous link - multiple targets available and order depends on the
				// order at runtime (may not be the same).
				acceptor.acceptWarning(
					"Ambiguous reference to: '" + parentString + "' found in: " +
							visibleResourceList(o.eResource(), descs), o,
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
					IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE, computeProposals(parentString, descs));
			}
		}
		else {
			// record unresolved name at resource level
			importedNames.addUnresolved(converter.toQualifiedName(parentString));

			// ... and finally, if there was neither a type nor a definition reference
			acceptor.acceptError(
				"Unknown class: '" + parentString + "'", o, PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
				IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	protected void _link(ResourceBody o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor,
			boolean profileThis) {

		ResourceExpression resource = (ResourceExpression) o.eContainer();
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		if(adapter.getClassifier() == RESOURCE_IS_CLASSPARAMS) {
			// pp: class { classname : parameter => value ... }

			final String className = stringConstantEvaluator.doToString(o.getNameExpr());
			if(className == null) {
				acceptor.acceptError(
					"Not a valid class reference", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					IPPDiagnostics.ISSUE__NOT_CLASSREF);
				return; // not meaningful to continue
			}
			List<IEObjectDescription> descs = findHostClasses(o, className, importedNames);
			if(descs.size() < 1) {
				// Add unresolved info at resource level
				importedNames.addUnresolved(converter.toQualifiedName(className));
				acceptor.acceptError(
					"Unknown class: '" + className + "'", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
				return; // not meaningful to continue (do not report errors for each "inner name")
			}
			if(descs.size() > 0) {
				descs = Lists.newArrayList(Sets.newHashSet(descs));
				// removeDisqualifiedContainers(descs, o);

				// Report resolution at resource level
				importedNames.addResolved(descs);

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same). ISSUE: o can be a ResourceBody
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + className + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE, computeProposals(className, descs));
				}
				// use the first description found to find parameters
				IEObjectDescription desc = descs.get(0);
				AttributeOperations aos = o.getAttributes();
				if(aos != null)
					for(AttributeOperation ao : aos.getAttributes()) {
						QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
						// Accept name if there is at least one type/definition that lists the key
						// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
						// the property could be ok in one, but not in another instance.
						// finding that A'::x exists but not A''::x requires a lot more work
						if(findAttributes(o, fqn, importedNames, profileThis).size() > 0)
							continue; // found one such parameter == ok
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY);
					}

			}

		}
		else if(adapter.getClassifier() == RESOURCE_IS_OVERRIDE) {
			// do nothing
		}
		else {
			// normal resource
			IEObjectDescription desc = (IEObjectDescription) adapter.getTargetObjectDescription();
			// do not flag undefined parameters as errors if type is unknown
			if(desc != null) {
				AttributeOperations aos = o.getAttributes();
				List<AttributeOperation> nameVariables = Lists.newArrayList();
				if(aos != null)
					for(AttributeOperation ao : aos.getAttributes()) {
						QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
						// Accept name if there is at least one type/definition that lists the key
						// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
						// the property could be ok in one, but not in another instance.
						// finding that A'::x exists but not A''::x requires a lot more work
						List<IEObjectDescription> foundAttributes = findAttributes(o, fqn, importedNames, profileThis);
						// if the ao is a namevar reference, remember it so uniqueness can be validated
						if(foundAttributes.size() > 0) {
							if(containsNameVar(foundAttributes))
								nameVariables.add(ao);
							continue; // found one such parameter == ok
						}
						// if the reference is to "name" (and it was not found), then this is a deprecated
						// reference to the namevar
						if("name".equals(ao.getKey())) {
							nameVariables.add(ao);
							acceptor.acceptWarning(
								"Deprecated use of the alias 'name' for resource name parameter. Use the type's real name variable.",
								ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
								IPPDiagnostics.ISSUE__RESOURCE_DEPRECATED_NAME_ALIAS);
							continue;
						}
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY);
					}
				if(nameVariables.size() > 1) {
					for(AttributeOperation ao : nameVariables)
						acceptor.acceptError(
							"Duplicate resource name specification", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_NAME_PARAMETER);
				}
			}
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	protected void _link(ResourceExpression o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		classifier.classify(o);

		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return;

		// If resource is good, and not 'class', then it must have a known reference type.
		// the resource type - also requires getting the type name from the override's expression).
		if(resourceType == RESOURCE_IS_CLASSPARAMS) {
			// resource is pp: class { classname : parameter => value }
			// do nothing
		}
		else if(resourceType == RESOURCE_IS_OVERRIDE) {
			// TODO: possibly check a resource override if the expression is constant (or it is impossible to lookup
			// do nothing
		}
		else {
			// normal resource
			List<IEObjectDescription> descs = findDefinitions(o, resourceTypeName, importedNames);
			if(descs.size() > 0) {
				// make list only contain unique references
				descs = Lists.newArrayList(Sets.newHashSet(descs));
				removeDisqualifiedContainers(descs, o);
				// if any remain, pick the first type (or the first if there are no types)
				IEObjectDescription usedResolution = null;
				if(descs.size() > 0) {
					usedResolution = getFirstTypeDescription(descs);
					adapter.setTargetObject(usedResolution);
				}

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same).
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + resourceTypeName + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE, computeProposals(resourceTypeName, descs));
				}
				// Add resolved information at resource level
				if(usedResolution != null)
					importedNames.addResolved(usedResolution);
				else
					importedNames.addResolved(descs);
			}
			// ... and finally, if there was neither a type nor a definition reference
			if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null) {
				// Add unresolved info at resource level
				importedNames.addUnresolved(converter.toQualifiedName(resourceTypeName));
				acceptor.acceptError(
					"Unknown resource type: '" + resourceTypeName + "'", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
			}
		}
	}

	private void buildExportedObjectsIndex(IResourceDescription descr, IResourceDescriptions descriptionIndex) {
		Multimap<String, IEObjectDescription> map = ArrayListMultimap.create();
		for(IEObjectDescription d : getExportedObjects(descr, descriptionIndex))
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

	private String[] computeProposals(String currentName, List<IEObjectDescription> descs) {
		List<String> proposals = Lists.newArrayList();
		if(currentName.startsWith("::"))
			return new String[0]; // can not make a global name more specific than what it already is
		for(IEObjectDescription d : descs) {
			String s = converter.toString(d.getQualifiedName());
			if(!s.startsWith("::")) {
				String s2 = "::" + s;
				if(!(s2.equals(currentName) || proposals.contains(s2)))
					proposals.add(s2);
			}
			if(s.equals(currentName) || proposals.contains(s))
				continue;
			proposals.add(s);
		}
		String[] props = proposals.toArray(new String[proposals.size()]);
		Arrays.sort(props);
		return props;
	}

	private boolean containsNameVar(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription d : descriptions)
			if("true".equals(d.getUserData(PPDSLConstants.PARAMETER_NAMEVAR)))
				return true;
		return false;
	}

	/**
	 * Find an attribute being a DefinitionArgument, Property, or Parameter for the given type, or a
	 * meta Property or Parameter defined for the type 'Type'.
	 * 
	 * @param scopeDetermeningObject
	 * @param fqn
	 * @return
	 */
	protected List<IEObjectDescription> findAttributes(EObject scopeDetermeningObject, QualifiedName fqn,
			PPImportedNamesAdapter importedNames, boolean profileThis) {
		List<IEObjectDescription> result = null;

		// do meta lookup first as this is made fast via a cache and these are used more frequent
		// than other parameters (measured).
		if(metaCache == null)
			cacheMetaParameters(scopeDetermeningObject);
		IEObjectDescription d = metaCache.get(fqn.getLastSegment());
		if(d == null)
			result = findAttributesWithGuard(
				scopeDetermeningObject, fqn, importedNames, Lists.<QualifiedName> newArrayList(), profileThis, "");
		else
			result = Lists.newArrayList(d);
		return result;
	}

	protected List<IEObjectDescription> findAttributesWithGuard(EObject scopeDetermeningObject, QualifiedName fqn,
			PPImportedNamesAdapter importedNames, List<QualifiedName> stack, boolean profileThis, String indent) {
		// Protect against circular inheritance
		if(stack.contains(fqn))
			return Collections.emptyList();
		stack.add(fqn);
		// if(profileThis)
		// System.err.println(indent + "Looking for  " + fqn);

		// find a regular DefinitionArgument, Property or Parameter
		List<IEObjectDescription> result = findExternal(
			scopeDetermeningObject, fqn, importedNames, DEF_AND_TYPE_ARGUMENTS);

		// Search up the inheritance chain
		if(result.isEmpty()) {
			if(profileThis)
				System.err.println(indent + "  Searching parent " + fqn.skipLast(1));
			// find the parent type
			result = findExternal(scopeDetermeningObject, fqn.skipLast(1), importedNames, DEF_AND_TYPE);
			if(!result.isEmpty()) {
				IEObjectDescription firstFound = result.get(0);
				String parentName = firstFound.getUserData(PPDSLConstants.PARENT_NAME_DATA);
				if(parentName != null && parentName.length() > 1) {
					// find attributes for parent

					QualifiedName attributeFqn = converter.toQualifiedName(parentName);
					attributeFqn = attributeFqn.append(fqn.getLastSegment());
					if(profileThis)
						System.err.println(indent + "  Returning result of search for " + attributeFqn);
					return findAttributesWithGuard(
						scopeDetermeningObject, attributeFqn, importedNames, stack, profileThis, indent + "    ");
				}
				result = Collections.emptyList();
			}
		}
		return result;
	}

	protected List<IEObjectDescription> findDefinitions(EObject scopeDetermeningResource, String name,
			PPImportedNamesAdapter importedNames) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		// make all segments initial char lower case (if references is to the type itself - eg. 'File' instead of
		// 'file', or 'Aa::Bb' instead of 'aa::bb'
		QualifiedName fqn2 = QualifiedName.EMPTY;
		for(int i = 0; i < fqn.getSegmentCount(); i++)
			fqn2 = fqn2.append(toInitialLowerCase(fqn.getSegment(i)));
		// fqn2 = fqn.skipLast(1).append(toInitialLowerCase(fqn.getLastSegment()));

		// TODO: Note that order is important, TYPE has higher precedence and should be used for linking
		// This used to work when list was iterated per type, not it is iterated once with type check
		// first - thus if a definition is found before a type, it is earlier in the list.
		return findExternal(scopeDetermeningResource, fqn2, importedNames, DEF_AND_TYPE);
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
		return targets;
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

	/**
	 * Returns the first type description. If none is found, the first description is returned.
	 * 
	 * @param descriptions
	 * @return
	 */
	private IEObjectDescription getFirstTypeDescription(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription e : descriptions) {
			if(e.getEClass() == PPTPPackage.Literals.TYPE)
				return e;
		}
		return descriptions.get(0);
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
	 * Links/validates unparenthesized function calls.
	 * 
	 * @param statements
	 * @param acceptor
	 */
	protected void internalLinkUnparenthesisedCall(EList<Expression> statements, PPImportedNamesAdapter importedNames,
			IMessageAcceptor acceptor) {
		if(statements == null || statements.size() == 0)
			return;

		each_top: for(int i = 0; i < statements.size(); i++) {
			Expression s = statements.get(i);

			// -- may be a non parenthesized function call
			if(s instanceof LiteralNameOrReference) {
				// there must be one more expression in the list (a single argument, or
				// an Expression list
				// TODO: different issue, can be fixed by adding "()" if this is a function call without
				// parameters, but difficult as validator does not know if function exists (would need
				// an adapter to be able to pick this up in validation).
				if((i + 1) >= statements.size()) {
					continue each_top; // error reported by validation.
				}
				// the next expression is consumed as a single arg, or an expr list
				// TODO: if there are expressions that can not be used as arguments check them here
				i++;
				// Expression arg = statements.get(i); // not used yet...
				String name = ((LiteralNameOrReference) s).getValue();
				if(findFunction(s, name, importedNames).size() > 0)
					return; // ok, found

				acceptor.acceptError(
					"Unknown function: '" + name + "'", s, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
					IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE);

				continue each_top;
			}
		}

	}

	/**
	 * Returns true if the descriptions resource path is the same as for the given object and
	 * the fragment path of the given object starts with the fragment path of the description.
	 * 
	 * (An alternative impl would be to first check if they are from the same resource - if so,
	 * it is know that this resource is loaded (since we have the given o) and it should
	 * be possible to search up the containment chain.
	 * 
	 * @param desc
	 * @param o
	 * @return
	 */
	protected boolean isParent(IEObjectDescription desc, EObject o) {
		URI descUri = desc.getEObjectURI();
		URI oUri = o.eResource().getURI();
		if(!descUri.path().equals(oUri.path()))
			return false;
		// same resource, if desc's fragment is in at the start of the path, then o is contained
		boolean result = o.eResource().getURIFragment(o).startsWith(descUri.fragment());
		return result;
	}

	/**
	 * Link all resources in the model
	 * 
	 * @param model
	 * @param acceptor
	 */
	public void link(EObject model, IMessageAcceptor acceptor, boolean profileThis) {
		resource = model.eResource();
		// clear names remembered in the past
		PPImportedNamesAdapter importedNames = PPImportedNamesAdapterFactory.eINSTANCE.adapt(resource);
		importedNames.clear();

		long before = System.currentTimeMillis();
		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(resource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(resource.getURI());
		long after = System.currentTimeMillis();
		if(profileThis) {
			System.err.printf("Getting index for resource takes (%s)\n", after - before);
		}
		if(descr == null) {
			if(tracer.isTracing()) {
				tracer.trace("Cleaning resource: " + resource.getURI().path());
			}
			return;
		}

		manager = resourceServiceProvider.getContainerManager();

		// Compute a cache based on last segment of qualified names
		before = System.currentTimeMillis();
		buildExportedObjectsIndex(descr, descriptionIndex);
		after = System.currentTimeMillis();
		if(profileThis)
			System.err.printf("Building index took: %s", after - before);

		if(tracer.isTracing())
			tracer.trace("Linking resource: ", resource.getURI().path(), "{");

		// Need to get everything in the resource, not just the content of the PuppetManifest (as the manifest has top level
		// expressions that need linking).
		TreeIterator<EObject> everything = resource.getAllContents();
		// it is important that ResourceExpresion are linked before ResourceBodyExpression (but that should
		// be ok with the tree iterator as the bodies are contained).

		// TODO: Should also validate INHERIT for CLASS and DEFINITION
		HashMap<EClass, Long> profilePerClass = Maps.newHashMap();
		if(profileThis) {
			profilePerClass.put(PPPackage.Literals.RESOURCE_EXPRESSION, 0L);
			profilePerClass.put(PPPackage.Literals.RESOURCE_BODY, 0L);
			profilePerClass.put(PPPackage.Literals.FUNCTION_CALL, 0L);
			profilePerClass.put(PPPackage.Literals.PUPPET_MANIFEST, 0L);
			profilePerClass.put(PPPackage.Literals.HOST_CLASS_DEFINITION, 0L);
		}
		while(everything.hasNext()) {
			EObject o = everything.next();
			EClass clazz = o.eClass();
			long beforeLink = System.currentTimeMillis();
			if(clazz == PPPackage.Literals.RESOURCE_EXPRESSION)
				_link((ResourceExpression) o, importedNames, acceptor);
			else if(clazz == PPPackage.Literals.RESOURCE_BODY)
				_link((ResourceBody) o, importedNames, acceptor, profileThis);
			else if(clazz == PPPackage.Literals.FUNCTION_CALL)
				_link((FunctionCall) o, importedNames, acceptor);

			// these are needed to link un-parenthesised function calls
			else if(clazz == PPPackage.Literals.PUPPET_MANIFEST)
				internalLinkUnparenthesisedCall(((PuppetManifest) o).getStatements(), importedNames, acceptor);
			else if(clazz == PPPackage.Literals.HOST_CLASS_DEFINITION) {
				_link((HostClassDefinition) o, importedNames, acceptor);
				internalLinkUnparenthesisedCall(((HostClassDefinition) o).getStatements(), importedNames, acceptor);
			}
			long afterLink = System.currentTimeMillis();
			if(profileThis && profilePerClass.get(clazz) != null)
				profilePerClass.put(clazz, profilePerClass.get(clazz) + (afterLink - beforeLink));
		}
		if(profileThis)
			for(Map.Entry<EClass, Long> entry : profilePerClass.entrySet())
				System.err.printf("Linking for class=%s (%s)\n", entry.getKey().getName(), entry.getValue());
		if(tracer.isTracing())
			tracer.trace("}");

	}

	/**
	 * Surgically remove all disqualified descriptions (those that are HostClass and a container
	 * of the given object 'o'.
	 * 
	 * @param descs
	 * @param o
	 */
	private void removeDisqualifiedContainers(List<IEObjectDescription> descs, Expression o) {
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			if(x.getEClass() == PPPackage.Literals.DEFINITION || !isParent(x, o))
				continue;
			litor.remove();
		}
	}

	private String toInitialLowerCase(String s) {
		if(s.length() < 1 || Character.isLowerCase(s.charAt(0)))
			return s;
		StringBuffer buf = new StringBuffer(s);
		buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));
		return buf.toString();
	}

	/**
	 * Collects the (unique) set of resource paths and returns a message with <=5 (+ ... and x others).
	 * 
	 * @param descriptors
	 * @return
	 */
	protected String visibleResourceList(Resource r, List<IEObjectDescription> descriptors) {
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		URI root = (URI) adapter.get(PPDSLConstants.RESOURCE_PROPERTY__ROOT_URI);

		// collect the (unique) resource paths
		List<String> resources = Lists.newArrayList();
		for(IEObjectDescription d : descriptors) {
			URI uri = EcoreUtil.getURI(d.getEObjectOrProxy());
			if(root != null) {
				uri = uri.deresolve(root.appendSegment(""));
			}
			boolean isPptpResource = "pptp".equals(uri.fileExtension());
			String path = isPptpResource
					? uri.lastSegment().replace(".pptp", "")
					: uri.devicePath();
			if(!resources.contains(path))
				resources.add(path);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(resources.size());
		buf.append(" resource");
		buf.append(resources.size() > 1
				? "s ["
				: " [");

		int count = 0;

		// if there are 4 include all, else limit to 3 - typically 2 (fresh user mistake) or is *many*
		final int countCap = resources.size() == 4
				? 4
				: 3;
		for(String s : resources) {
			if(count > 0)
				buf.append(", ");
			// buf.append(count);
			// buf.append("=");
			buf.append(s);
			if(count++ > countCap) {
				buf.append("and ");
				buf.append(resources.size() - countCap);
				buf.append(" other files...");
				break;
			}
		}
		buf.append("]");
		return buf.toString();
	}
}
