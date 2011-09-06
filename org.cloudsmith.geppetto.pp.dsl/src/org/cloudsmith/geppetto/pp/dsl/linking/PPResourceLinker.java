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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.ExprList;
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
import org.cloudsmith.geppetto.pp.dsl.contentassist.PPProposalsGenerator;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
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
public class PPResourceLinker implements IPPDiagnostics {

	static class NameInScopeFilter implements Iterable<IEObjectDescription> {
		private final Iterable<IEObjectDescription> unfiltered;

		final private NameInScopePredicate filter;

		NameInScopeFilter(boolean startsWith, Iterable<IEObjectDescription> unfiltered, QualifiedName name,
				QualifiedName scope, EClass[] eclasses) {
			boolean absolute = name.getSegmentCount() > 0 && "".equals(name.getSegment(0));
			this.unfiltered = unfiltered;
			filter = new NameInScopePredicate(absolute, startsWith, absolute
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

		final boolean startsWith;

		final EClass[] eclasses;

		public NameInScopePredicate(boolean absolute, boolean startsWith, QualifiedName name, QualifiedName scopeName,
				EClass[] eclasses) {
			this.absolute = absolute;
			this.scopeName = scopeName == null
					? QualifiedName.EMPTY
					: scopeName;
			this.name = name;
			this.eclasses = eclasses;
			this.startsWith = startsWith;
		}

		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName candidateName = candidate.getQualifiedName();
			// error, not a valid name (can not possibly match).
			if(candidateName.getSegmentCount() == 0)
				return false;

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
			if(matches(candidateName, name, startsWith))
				return true;
			// if(candidateName.equals(name) || (startsWith && candidateName.startsWith(name))
			// return true;

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
			if(startsWith)
				return candidateName.startsWith(scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(
					name));
			return scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(name).equals(candidateName);
		}

		private boolean matches(QualifiedName candidate, QualifiedName query, boolean startsWith) {
			if(!startsWith)
				return candidate.equals(query);
			if(query.getSegmentCount() > candidate.getSegmentCount())
				return false;
			if(!candidate.skipLast(1).equals(query.skipLast(1)))
				return false;
			if(!candidate.getLastSegment().startsWith(query.getLastSegment()))
				return false;
			return true;
		}
	}

	private static class SearchResult {
		private List<IEObjectDescription> adjusted;

		private List<IEObjectDescription> raw;

		private SearchResult(List<IEObjectDescription> pathAdjusted, List<IEObjectDescription> raw) {
			this.adjusted = pathAdjusted;
			this.raw = raw;
		}

		List<IEObjectDescription> getAdjusted() {
			return adjusted;
		}

		List<IEObjectDescription> getRaw() {
			return raw;
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

	@Inject
	PPProposalsGenerator proposer;

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

	private static String proposalIssue(String issue, String[] proposals) {
		if(proposals == null || proposals.length == 0)
			return issue;
		return issue + IPPDiagnostics.ISSUE_PROPOSAL_SUFFIX;
	}

	private Multimap<String, IEObjectDescription> exportedPerLastSegment;

	private Resource resource;

	@Inject
	private ISearchPathProvider searchPathProvider;

	private PPSearchPath searchPath;

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	protected void _link(FunctionCall o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {

		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		String name = ((LiteralNameOrReference) o.getLeftExpr()).getValue();

		final SearchResult searchResult = findFunction(o, name, importedNames);
		final List<IEObjectDescription> found = searchResult.getAdjusted(); // findFunction(o, name, importedNames);
		if(found.size() > 0) {
			// record resolution at resource level
			importedNames.addResolved(found);
			internalLinkFunctionArguments(name, o, importedNames, acceptor);
			return; // ok, found
		}
		if(searchResult.getRaw().size() > 0) {
			// Not a hard error, it may be valid with a different path
			// not found on path, but exists somewhere in what is visible
			// record resolution at resource level
			importedNames.addResolved(searchResult.getRaw());
			internalLinkFunctionArguments(name, o, importedNames, acceptor);
			acceptor.acceptWarning("Found outside current path: '" + name + "'", o.getLeftExpr(), //
				IPPDiagnostics.ISSUE__NOT_ON_PATH //
			);
			return; // sort of ok
		}
		String[] proposals = proposer.computeProposals(name, exportedPerLastSegment.values(), searchPath, FUNC);
		acceptor.acceptError("Unknown function: '" + name + "'", o.getLeftExpr(), //
			proposalIssue(IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
			proposals);
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

		SearchResult searchResult = findHostClasses(o, parentString, importedNames);
		List<IEObjectDescription> descs = searchResult.getAdjusted(); // findHostClasses(o, parentString, importedNames);
		if(descs.size() > 0) {
			// make list only contain unique references
			descs = Lists.newArrayList(Sets.newHashSet(descs));

			// record resolution at resource level
			importedNames.addResolved(descs);

			if(descs.size() > 1) {
				// this is an ambiguous link - multiple targets available and order depends on the
				// order at runtime (may not be the same).
				importedNames.addAmbiguous(descs);
				acceptor.acceptWarning(
					"Ambiguous reference to: '" + parentString + "' found in: " +
							visibleResourceList(o.eResource(), descs), o,
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
					IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
					proposer.computeDistinctProposals(parentString, descs));
			}
			// must check for circularity
			List<QualifiedName> visited = Lists.newArrayList();
			visited.add(converter.toQualifiedName(o.getClassName()));
			checkCircularInheritence(o, descs, visited, acceptor, importedNames);
		}
		else if(searchResult.getRaw().size() > 0) {
			List<IEObjectDescription> raw = searchResult.getAdjusted(); // findHostClasses(o, parentString, importedNames);

			// Sort of ok, it is not on the current path
			// record resolution at resource level, so recompile knows about the dependencies
			importedNames.addResolved(raw);
			acceptor.acceptWarning(
				"Found outside currect search path: '" + parentString + "'", o,
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		}
		else {
			// record unresolved name at resource level
			importedNames.addUnresolved(converter.toQualifiedName(parentString));

			// ... and finally, if there was neither a type nor a definition reference
			String[] proposals = proposer.computeProposals(
				parentString, exportedPerLastSegment.values(), searchPath, CLASS_AND_TYPE);
			acceptor.acceptError(
				"Unknown class: '" + parentString + "'", o, //
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
				proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
				proposals);
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
			SearchResult searchResult = findHostClasses(o, className, importedNames);
			List<IEObjectDescription> descs = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
			if(descs.size() < 1) {
				if(searchResult.getRaw().size() > 0) {
					// Sort of ok
					importedNames.addResolved(searchResult.getRaw());
					acceptor.acceptWarning(
						"Found outside currect search path (parameters not validated): '" + className + "'", o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, IPPDiagnostics.ISSUE__NOT_ON_PATH);
					return; // skip validating parameters

				}

				// Add unresolved info at resource level
				importedNames.addUnresolved(converter.toQualifiedName(className));
				String[] proposals = proposer.computeProposals(
					className, exportedPerLastSegment.values(), searchPath, CLASS_AND_TYPE);
				acceptor.acceptError(
					"Unknown class: '" + className + "'", o, //
					PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
					proposals);
				return; // not meaningful to continue (do not report errors for each "inner name")
			}
			if(descs.size() > 0) {
				descs = Lists.newArrayList(Sets.newHashSet(descs));
				// Report resolution at resource level
				importedNames.addResolved(descs);

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same). ISSUE: o can be a ResourceBody
					importedNames.addAmbiguous(descs);
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + className + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(className, descs));
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

						String[] proposals = proposer.computeAttributeProposals(
							fqn, exportedPerLastSegment.values(), searchPath);
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
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
						String[] proposals = proposer.computeAttributeProposals(
							fqn, exportedPerLastSegment.values(), searchPath);
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
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
			SearchResult searchResult = findDefinitions(o, resourceTypeName, importedNames);
			List<IEObjectDescription> descs = searchResult.getAdjusted(); // findDefinitions(o, resourceTypeName, importedNames);
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
					importedNames.addAmbiguous(descs);
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + resourceTypeName + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(resourceTypeName, descs));
				}
				// Add resolved information at resource level
				if(usedResolution != null)
					importedNames.addResolved(usedResolution);
				else
					importedNames.addResolved(descs);
			}
			else if(searchResult.getRaw().size() > 0) {
				// sort of ok
				importedNames.addResolved(searchResult.getRaw());
				// do not record the type

				acceptor.acceptWarning(
					"Found outside search path: '" + resourceTypeName + "'", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, IPPDiagnostics.ISSUE__NOT_ON_PATH);

			}
			// only report unresolved if no raw (since if not found adjusted, error is reported as warning)
			if(searchResult.getRaw().size() < 1) {
				// ... and finally, if there was neither a type nor a definition reference
				if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null) {
					// Add unresolved info at resource level
					importedNames.addUnresolved(converter.toQualifiedName(resourceTypeName));
					String[] proposals = proposer.computeProposals(
						resourceTypeName, exportedPerLastSegment.values(), searchPath, DEF_AND_TYPE);
					acceptor.acceptError(
						"Unknown resource type: '" + resourceTypeName + "'", o,
						PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, //
						proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
						proposals);
				}
			}
		}
	}

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

	/**
	 * Returns false if it is impossible that the given expression can result in a valid class
	 * reference at runtime.
	 * 
	 * TODO: this is a really stupid way of doing "type inference", but better than nothing.
	 * 
	 * @param e
	 * @return
	 */
	private boolean canBeAClassReference(Expression e) {
		switch(e.eClass().getClassifierID()) {
			case PPPackage.HOST_CLASS_DEFINITION:
			case PPPackage.ASSIGNMENT_EXPRESSION:
			case PPPackage.NODE_DEFINITION:
			case PPPackage.DEFINITION:
			case PPPackage.IMPORT_EXPRESSION:
			case PPPackage.RELATIONAL_EXPRESSION:
			case PPPackage.RESOURCE_EXPRESSION:
			case PPPackage.IF_EXPRESSION:
			case PPPackage.SELECTOR_EXPRESSION:
			case PPPackage.AND_EXPRESSION:
			case PPPackage.OR_EXPRESSION:
			case PPPackage.CASE_EXPRESSION:
			case PPPackage.EQUALITY_EXPRESSION:
			case PPPackage.RELATIONSHIP_EXPRESSION:
				return false;
		}
		return true;
	}

	protected void checkCircularInheritence(HostClassDefinition o, Collection<IEObjectDescription> descs,
			List<QualifiedName> stack, IMessageAcceptor acceptor, PPImportedNamesAdapter importedNames) {
		for(IEObjectDescription d : descs) {
			QualifiedName name = d.getName();
			if(stack.contains(name)) {
				// Gotcha!
				acceptor.acceptError( //
					"Circular inheritence", o, //
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, //
					IPPDiagnostics.ISSUE__CIRCULAR_INHERITENCE);
				return; // no use continuing
			}
			stack.add(name);
			String parentName = d.getUserData(PPDSLConstants.PARENT_NAME_DATA);
			if(parentName == null || parentName.length() == 0)
				continue;
			SearchResult searchResult = findHostClasses(d.getEObjectOrProxy(), parentName, importedNames);
			List<IEObjectDescription> parents = searchResult.getAdjusted(); // findHostClasses(d.getEObjectOrProxy(), parentName, importedNames);
			checkCircularInheritence(o, parents, stack, acceptor, importedNames);
		}
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
		SearchResult searchResult = findExternal(scopeDetermeningObject, fqn, importedNames, DEF_AND_TYPE_ARGUMENTS);
		List<IEObjectDescription> result = searchResult.getAdjusted(); // findExternal(
		// scopeDetermeningObject, fqn, importedNames, DEF_AND_TYPE_ARGUMENTS);

		// Search up the inheritance chain
		if(result.isEmpty()) {
			if(profileThis)
				System.err.println(indent + "  Searching parent " + fqn.skipLast(1));
			// find the parent type
			SearchResult searchResult2 = findExternal(
				scopeDetermeningObject, fqn.skipLast(1), importedNames, DEF_AND_TYPE);
			result = searchResult2.getAdjusted(); // findExternal(scopeDetermeningObject, fqn.skipLast(1), importedNames, DEF_AND_TYPE);
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

	protected SearchResult findDefinitions(EObject scopeDetermeningResource, String name,
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

	protected SearchResult findExternal(EObject scopeDetermeningObject, QualifiedName fqn,
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
				return new SearchResult(targets, targets);
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);

			// for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			// for(EClass aClass : eClasses)
			for(IEObjectDescription objDesc : new NameInScopeFilter(false, getExportedObjects(descr, descriptionIndex),
			// visibleContainer.getExportedObjects(),
			fqn, nameOfScope, eClasses))
				targets.add(objDesc);
		}
		else {
			// This is lookup from the main resource perspecive
			QualifiedName nameOfScope = getNameOfScope(scopeDetermeningObject);
			for(IEObjectDescription objDesc : new NameInScopeFilter(
				false, exportedPerLastSegment.get(fqn.getLastSegment()), fqn, nameOfScope, eClasses))
				targets.add(objDesc);

		}
		if(tracer.isTracing()) {
			for(IEObjectDescription d : targets)
				tracer.trace("    : ", converter.toString(d.getName()), " in: ", d.getEObjectURI().path());
		}
		return new SearchResult(searchPathAdjusted(targets), targets);
	}

	private SearchResult findFunction(EObject scopeDetermeningObject, QualifiedName fqn,
			PPImportedNamesAdapter importedNames) {
		return findExternal(scopeDetermeningObject, fqn, importedNames, FUNC);
	}

	private SearchResult findFunction(EObject scopeDetermeningObject, String name, PPImportedNamesAdapter importedNames) {
		return findFunction(scopeDetermeningObject, converter.toQualifiedName(name), importedNames);
	}

	protected SearchResult findHostClasses(EObject scopeDetermeningResource, String name,
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
	 * Link well known functions that must have references to defined things.
	 * 
	 * @param o
	 * @param importedNames
	 * @param acceptor
	 */
	protected void internalLinkFunctionArguments(String name, FunctionCall o, PPImportedNamesAdapter importedNames,
			IMessageAcceptor acceptor) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {
			int parameterIndex = -1;
			for(Expression pe : o.getParameters()) {
				parameterIndex++;
				String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					SearchResult searchResult = findHostClasses(o, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						acceptor.acceptWarning(
							"Ambiguous reference to: '" + className + "' found in: " +
									visibleResourceList(o.eResource(), foundClasses), o,
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
							proposer.computeDistinctProposals(className, foundClasses));
					}
					else if(foundClasses.size() < 1) {
						if(searchResult.getRaw().size() > 0) {
							// sort of ok
							acceptor.acceptWarning(
								"Found outside current search path: '" + className + "'", o,
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								IPPDiagnostics.ISSUE__NOT_ON_PATH);
						}
						else {
							// not found
							// record unresolved name at resource level
							importedNames.addUnresolved(converter.toQualifiedName(className));

							String[] p = proposer.computeProposals(
								className, exportedPerLastSegment.values(), searchPath, CLASS_AND_TYPE);
							acceptor.acceptError(
								"Unknown class: '" + className + "'", o, //
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, p), //
								p);
						}
					}
				}
				else {
					// warning or error depending on if this is a reasonable class reference expr or not
					if(canBeAClassReference(pe)) {
						acceptor.acceptWarning(
							"Can not determine until runtime if this is valid class reference", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
					else {
						acceptor.acceptError(
							"Not an acceptable parameter. Function '" + name + "' requires a class reference.", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
				}

			}
			// there should have been at least one argument
			if(parameterIndex < 0) {
				acceptor.acceptError("Call to '" + name + "' must have at least one argument.", o, //
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, //
					IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);

			}
		}
	}

	private void internalLinkFunctionArguments(String name, LiteralNameOrReference s, EList<Expression> statements,
			int idx, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {

			// there should have been at least one argument
			if(idx >= statements.size()) {
				acceptor.acceptError("Call to '" + name + "' must have at least one argument.", s, //
					PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, //
					IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
				return;
			}

			Expression param = statements.get(idx);
			List<Expression> parameterList = null;
			if(param instanceof ExprList)
				parameterList = ((ExprList) param).getExpressions();
			else
				parameterList = Lists.newArrayList(param);

			int parameterIndex = -1;
			for(Expression pe : parameterList) {
				parameterIndex++;
				String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					SearchResult searchResult = findHostClasses(s, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						if(param instanceof ExprList)
							acceptor.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " +
										visibleResourceList(s.eResource(), foundClasses), //
								param, //
								PPPackage.Literals.EXPR_LIST__EXPRESSIONS,
								parameterIndex, //
								IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
								proposer.computeDistinctProposals(className, foundClasses));
						else
							acceptor.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " +
										visibleResourceList(s.eResource(), foundClasses), //
								param.eContainer(), param.eContainingFeature(),
								idx, //
								IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
								proposer.computeDistinctProposals(className, foundClasses));

					}
					else if(foundClasses.size() < 1) {

						// not found
						// record unresolved name at resource level
						importedNames.addUnresolved(converter.toQualifiedName(className));

						String[] proposals = proposer.computeProposals(
							className, exportedPerLastSegment.values(), searchPath, CLASS_AND_TYPE);
						String issueCode = proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals);
						if(param instanceof ExprList) {
							acceptor.acceptError("Unknown class: '" + className + "'", //
								param, //
								PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
								issueCode, //
								proposals);
						}
						else {
							acceptor.acceptError("Unknown class: '" + className + "'", //
								param.eContainer(), param.eContainingFeature(), idx, //
								issueCode, //
								proposals);
						}
					}
				}
				else {
					// warning or error depending on if this is a reasonable class reference expr or not
					String msg = null;
					boolean error = false;
					if(canBeAClassReference(pe)) {
						msg = "Can not determine until runtime if this is valid class reference";
					}
					else {
						msg = "Not an acceptable parameter. Function '" + name + "' requires a class reference.";
						error = true;
					}
					if(param instanceof ExprList)
						acceptor.accept(error
								? Severity.ERROR
								: Severity.WARNING, msg, //
							param, //
							PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);

					else
						acceptor.accept(error
								? Severity.ERROR
								: Severity.WARNING, msg, //
							param.eContainer(), param.eContainingFeature(), idx, //
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
				}
			}
		}
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
				SearchResult searchResult = findFunction(s, name, importedNames);
				if(searchResult.getAdjusted().size() > 0 || searchResult.getRaw().size() > 0) {
					internalLinkFunctionArguments(
						name, (LiteralNameOrReference) s, statements, i, importedNames, acceptor);
					if(searchResult.getAdjusted().size() < 1)
						acceptor.acceptWarning("Found outside current path: '" + name + "'", s, //
							IPPDiagnostics.ISSUE__NOT_ON_PATH);
					continue each_top; // ok, found
				}
				String[] proposals = proposer.computeProposals(name, exportedPerLastSegment.values(), searchPath, FUNC);
				acceptor.acceptError(
					"Unknown function: '" + name + "'", s, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
					proposalIssue(IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
					proposals);

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
		searchPath = searchPathProvider.get(resource);

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
