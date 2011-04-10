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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.pptp.IPPTP;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.pptp.INamed;
import org.cloudsmith.geppetto.pp.pptp.Type;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;

/**
 * Handles special linking of ResourceExpression and ResourceBody.
 * 
 */

public class PPResourceLinker {

	class NameInScopeFilter implements Iterable<IEObjectDescription> {
		private final Iterable<IEObjectDescription> unfiltered;

		private final QualifiedName name;

		private final EObject scope;

		NameInScopeFilter(Iterable<IEObjectDescription> unfiltered, QualifiedName name, EObject scope) {
			this.unfiltered = unfiltered;
			this.name = name;
			this.scope = scope;
		}

		public Iterator<IEObjectDescription> iterator() {
			return Iterators.filter(unfiltered.iterator(), new NameInScopePredicate(name, getNameOfScope(scope)));
		}
	}

	public static class NameInScopePredicate implements Predicate<IEObjectDescription> {
		QualifiedName scopeName;

		QualifiedName name;

		public NameInScopePredicate(QualifiedName name, QualifiedName scopeName) {
			this.scopeName = scopeName == null
					? QualifiedName.EMPTY
					: scopeName;
			this.name = name;
		}

		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName candidateName = candidate.getQualifiedName();
			// filter out all that do not match on last segment (i.a. ?::...::x <-> ?::...::y)
			if(!candidateName.getLastSegment().equals(name.getLastSegment()))
				return false;
			// Since most references are exact (they are global), this is the fastest for the common case.
			if(candidateName.equals(name))
				return true;
			// // need to check if candidate's parent scope is same or outer scope of request
			// if(!scopeName.startsWith(candidateName.skipLast(1)))
			// return false; // does not share a common scope

			// need to find the common outer scope
			QualifiedName candidateParent = candidateName.skipLast(1);

			// NO! This makes it possible to refer to the parent i.e. class foo::bar { bar { }} - this is not
			// allowed
			// // if defined in same or an outer scope, name matches
			// if(candidateParent.getSegmentCount() <= scopeName.getSegmentCount())
			// return scopeName.startsWith(candidateParent);
			// if defined in an inner scope of a shared outer scope, the name needs to be appended to the common outer scope
			// 1. find the common outer scope
			int commonCount = 0;
			int limit = Math.min(scopeName.getSegmentCount(), candidateParent.getSegmentCount());
			for(int i = 0; i < limit; i++)
				if(scopeName.getSegment(i).equals(candidateName.getSegment(i)))
					commonCount++;
				else
					break;
			// if no common anscestor, then equality check above should have found it.
			if(commonCount == 0)
				return false;

			// commonPart+requestedName == candidate (i.e. wanted "c::d" in scope "a::b" - check "a::b::c::d"
			return scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(name).equals(candidateName);
		}
	}

	/**
	 * Access puppet target platform.
	 */
	@Inject
	private IPPTP PPTP;

	/**
	 * Access to container manager for PP language
	 */
	@Inject
	private IContainer.Manager manager;

	/**
	 * Access to global index maintained by Xtext
	 */
	@Inject
	private IResourceDescriptions descriptionIndex;

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	/**
	 * PP FQN to/from Xtext FQN converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	@Inject
	IQualifiedNameProvider fqnProvider;

	protected void _link(FunctionCall o, IMessageAcceptor acceptor) {
		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		String name = ((LiteralNameOrReference) o.getLeftExpr()).getValue();
		if(PPTP.findFunction(name) != null)
			return; // ok, found

		acceptor.acceptError(
			"Unknown function: '" + name + "'", o.getLeftExpr(), IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE);

		// functions can not be added in pp code, PPTP contains all of them.
	}

	protected void _link(ResourceBody o, IMessageAcceptor acceptor) {

		ResourceExpression resource = (ResourceExpression) o.eContainer();
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		INamed p = null;
		if(!(adapter.getClassifier() == RESOURCE_IS_CLASSPARAMS || adapter.getClassifier() == RESOURCE_IS_OVERRIDE)) {
			Type resourceType = adapter.getResourceType();
			// if resourceType is unknown then this is handled by other rules (not possible to
			// check the properties).
			//
			if(resourceType != null) {
				AttributeOperations aos = o.getAttributes();
				if(aos != null)
					for(AttributeOperation ao : aos.getAttributes()) {
						// TODO: don't know if there are meta parameters for anything but types
						if(isMetaParameter(ao.getKey()))
							continue;
						p = PPTP.findProperty(resourceType, ao.getKey());
						if(p == null)
							p = PPTP.findParameter(resourceType, ao.getKey());
						if(p == null)
							acceptor.acceptError(
								"Unknown parameter: '" + ao.getKey() + "' in type: '" + resourceType.getName() + "'",
								ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
								IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY);
					}
			}
			else {
				IEObjectDescription desc = (IEObjectDescription) adapter.getTargetObjectDescription();
				// if no type, and no descrption - then do not flag undefined parameters as errors
				//
				if(desc != null) {
					AttributeOperations aos = o.getAttributes();
					if(aos != null)
						for(AttributeOperation ao : aos.getAttributes()) {
							// Seems like all definitions and classes also support the meta stuff
							if(isMetaParameter(ao.getKey()))
								continue;

							QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
							// If there is at least one type/definition that lists the key, then mark it
							// as ok. If there are other problems (multiple definitions with same name etc,
							// the property could be ok in one, but not in another instance.
							// finding that A'::x exists but not A''::x requires a lot more work
							if(findDefinitionArguments(o, fqn).size() > 0)
								continue; // found one such parameter == ok
							acceptor.acceptError(
								"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
								PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
								IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY);

						}
				}
			}
		}

	}

	protected void _link(ResourceExpression o, IMessageAcceptor acceptor) {
		classifier.classify(o);

		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return;

		// If resource is good, and not 'class', then it must have a known reference type.
		// TODO: possibly check a resource override if the expression is constant (or it is impossible to lookup
		// the resource type - also requires getting the type name from the override's expression).
		Type theType = null;
		if(!(resourceType == RESOURCE_IS_CLASSPARAMS || resourceType == RESOURCE_IS_OVERRIDE)) {
			// TODO: MAKE THIS A SCOPED SEARCH
			theType = PPTP.findType(resourceTypeName);
			adapter.setResourceType(theType);
			if(theType == null) {
				List<IEObjectDescription> descs = findDefinitions(o, resourceTypeName);
				if(descs.size() > 0) {
					// make list only contain unique references
					descs = Lists.newArrayList(Sets.newHashSet(descs));
					removeDisqualifiedContainers(descs, o);
					// if any remain, pick the first
					if(descs.size() > 0)
						adapter.setTargetObject(descs.get(0));

					// // find the first desc that is a definition (parent ok), or something else (definition),
					// // in which case it can not be the container.
					// for(IEObjectDescription d : descs)
					// if(d.getEClass() == PPPackage.Literals.DEFINITION || !isParent(d, o)) {
					// adapter.setTargetObject(d);
					// // adapter.setTargetObject(descs.get(0));
					// break;
					// }
				}

				// if this is ambiguous report a warning - in RT, the first found will be silently used
				// but order can not be determined with certainty so warning is in place.
				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same).
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + resourceTypeName + "' found in: " + visibleResourceList(descs),
						o, PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE);
				}
			}
			// ... and finally, if there was neither a type nor a definition reference
			if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null)
				acceptor.acceptError(
					"Unknown resource type: '" + resourceTypeName + "'", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
		}
	}

	protected List<IEObjectDescription> findDefinitionArguments(EObject scopeDetermeningObject, QualifiedName fqn) {
		return findExternal(scopeDetermeningObject, fqn, PPPackage.Literals.DEFINITION_ARGUMENT);
	}

	protected List<IEObjectDescription> findDefinitions(EObject scopeDetermeningResource, String name) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		return findExternal(scopeDetermeningResource, fqn, PPPackage.Literals.DEFINITION);
	}

	protected List<IEObjectDescription> findExternal(EObject scopeDetermeningObject, QualifiedName fqn, EClass eClass) {
		if(scopeDetermeningObject == null)
			throw new IllegalArgumentException("scope determening object is null");
		if(fqn == null)
			throw new IllegalArgumentException("name is null");
		if(eClass == null)
			throw new IllegalArgumentException("eClass is null");

		List<IEObjectDescription> targets = Lists.newArrayList();
		Resource scopeDetermeningResource = scopeDetermeningObject.eResource();
		IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());

		// GIVE UP (can happen with stale/bad/old workspace state), will correct itself after refresh
		// TODO: log this with message to refresh and report if problem persist
		if(descr == null)
			return targets;

		for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			for(IEObjectDescription objDesc : new NameInScopeFilter(
				visibleContainer.getExportedObjectsByType(eClass), fqn, scopeDetermeningObject)) {
				// for(IEObjectDescription objDesc : visibleContainer.getExportedObjects(eClass, fqn, false)) {
				targets.add(objDesc);
			}
		}

		// DEBUG
		for(IEObjectDescription d : targets)
			System.out.println("PPResourceLinker found target: " + converter.toString(d.getName()));

		return targets;
	}

	// FOR DEBUGGING
	// public void listVisibleResources(Resource myResource, IResourceDescriptions index) {
	// IResourceDescription descr = index.getResourceDescription(myResource.getURI());
	// for(IContainer visibleContainer : manager.getVisibleContainers(descr, index)) {
	// for(IResourceDescription visibleResourceDesc : visibleContainer.getResourceDescriptions()) {
	// for(IEObjectDescription objDesc : visibleResourceDesc.getExportedObjects())
	// System.out.println("\texported: " + converter.toString(objDesc.getQualifiedName()) + " type: " +
	// objDesc.getEClass().getName());
	// System.out.println(visibleResourceDesc.getURI());
	// }
	// }
	// }

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
	 * Returns true if the name is a <i>meta parameter</i> defined in puppet/type.rb; i.e. a parameter
	 * applicable to all types.
	 * 
	 * @param parameterName
	 * @return true if the given parameter is a meta parameter.
	 */
	private boolean isMetaParameter(String parameterName) {
		Type metaType = PPTP.getMetaType();
		if(metaType == null)
			return false;
		return PPTP.findParameter(metaType, parameterName) != null;
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
	public void link(EObject model, IMessageAcceptor acceptor) {
		TreeIterator<EObject> everything = model.eAllContents();
		// it is important that ResourceExpresion are linked before ResourceBodyExpression (but that should
		// be ok with the tree iterator as the bodies are contained).

		while(everything.hasNext()) {
			EObject o = everything.next();
			if(o.eClass() == PPPackage.Literals.RESOURCE_EXPRESSION)
				_link((ResourceExpression) o, acceptor);
			else if(o.eClass() == PPPackage.Literals.RESOURCE_BODY)
				_link((ResourceBody) o, acceptor);
			else if(o.eClass() == PPPackage.Literals.FUNCTION_CALL)
				_link((FunctionCall) o, acceptor);
		}

	}

	/**
	 * Surgically remove all disqualified descriptions (those that are HostClass and a container
	 * of the given object 'o'.
	 * 
	 * @param descs
	 * @param o
	 */
	private void removeDisqualifiedContainers(List<IEObjectDescription> descs, ResourceExpression o) {
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			if(x.getEClass() == PPPackage.Literals.DEFINITION || !isParent(x, o))
				continue;
			litor.remove();
		}
	}

	/**
	 * Collects the (unique) set of resource paths and returns a message with <=5 (+ ... and x others).
	 * 
	 * @param descriptors
	 * @return
	 */
	protected String visibleResourceList(List<IEObjectDescription> descriptors) {
		// collect the (unique) resource paths
		List<String> resources = Lists.newArrayList();
		for(IEObjectDescription d : descriptors) {
			// Resource r = d.getEObjectOrProxy().eResource();
			// if(r != null) {
			String path = EcoreUtil.getURI(d.getEObjectOrProxy()).devicePath();
			// String path = r.getURI().path();
			if(!resources.contains(path))
				resources.add(path);
			// }
		}
		StringBuffer buf = new StringBuffer();
		buf.append(resources.size());
		buf.append(" resource");
		buf.append(resources.size() > 1
				? "s {"
				: " {");

		int count = 0;

		// if there are 4 include all, else limit to 3 - typically 2 (fresh user mistake) or is *many*
		final int countCap = resources.size() == 4
				? 4
				: 3;
		for(String s : resources) {
			if(count > 0)
				buf.append(", ");
			buf.append(count);
			buf.append("=");
			buf.append(s);
			if(count++ > countCap) {
				buf.append("and ");
				buf.append(resources.size() - countCap);
				buf.append(" other files...");
				break;
			}
		}
		buf.append("}");
		return buf.toString();
	}
}
