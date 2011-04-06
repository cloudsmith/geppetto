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

import java.util.List;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

/**
 * Handles special linking of ResourceExpression and ResourceBody.
 * 
 */

public class PPResourceLinker {

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

	protected List<IEObjectDescription> findDefinitionArguments(Resource scopeDetermeningResource, QualifiedName fqn) {
		return findExternal(scopeDetermeningResource, fqn, PPPackage.Literals.DEFINITION_ARGUMENT);
	}

	protected List<IEObjectDescription> findDefinitions(Resource scopeDetermeningResource, String name) {
		if(name == null)
			throw new IllegalArgumentException("name is null");
		QualifiedName fqn = converter.toQualifiedName(name);
		return findExternal(scopeDetermeningResource, fqn, PPPackage.Literals.DEFINITION);
	}

	protected List<IEObjectDescription> findExternal(Resource scopeDetermeningResource, QualifiedName fqn, EClass eClass) {
		if(scopeDetermeningResource == null)
			throw new IllegalArgumentException("resource is null");
		if(fqn == null)
			throw new IllegalArgumentException("name is null");

		List<IEObjectDescription> targets = Lists.newArrayList();
		IResourceDescription descr = descriptionIndex.getResourceDescription(scopeDetermeningResource.getURI());
		for(IContainer visibleContainer : manager.getVisibleContainers(descr, descriptionIndex)) {
			for(IEObjectDescription objDesc : visibleContainer.getExportedObjects(eClass, fqn, false)) {
				targets.add(objDesc);
			}
		}

		// DEBUG
		for(IEObjectDescription d : targets)
			System.out.println("PPResourceLinker found target: " + converter.toString(d.getName()));

		return targets;
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
				linkResourceExpression((ResourceExpression) o, acceptor);
			else if(o.eClass() == PPPackage.Literals.RESOURCE_BODY)
				linkResourceBodyExpression((ResourceBody) o, acceptor);
		}

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

	protected void linkResourceBodyExpression(ResourceBody o, IMessageAcceptor acceptor) {

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
				for(AttributeOperation ao : aos.getAttributes()) {
					// TODO: don't know if there are meta parameters for anything but types
					if(isMetaParameter(ao.getKey()))
						continue;
					p = PPTP.findProperty(resourceType, ao.getKey());
					if(p == null)
						p = PPTP.findParameter(resourceType, ao.getKey());
					if(p == null)
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in type: '" + resourceType.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
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
							QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
							// If there is at least one type/definition that lists the key, then mark it
							// as ok. If there are other problems (multiple definitions with same name etc,
							// the property could be ok in one, but not in another instance.
							// finding that A'::x exists but not A''::x requires a lot more work
							if(findDefinitionArguments(o.eResource(), fqn).size() > 0)
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

	protected void linkResourceExpression(ResourceExpression o, IMessageAcceptor acceptor) {
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
			theType = PPTP.findType(resourceTypeName);
			adapter.setResourceType(theType);
			if(theType == null) {
				List<IEObjectDescription> descs = findDefinitions(o.eResource(), resourceTypeName);
				if(descs.size() > 0)
					adapter.setTargetObject(descs.get(0));

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
			Resource r = d.getEObjectOrProxy().eResource();
			if(r != null) {
				String path = r.getURI().path();
				if(!resources.contains(path))
					resources.add(path);
			}
		}
		StringBuffer buf = new StringBuffer();
		int count = 0;

		// if there are 4 include all, else limit to 3 - typically 2 (fresh user mistake) or is *many*
		final int countCap = resources.size() == 4
				? 4
				: 3;
		for(String s : resources) {
			buf.append(s);
			if(count > countCap) {
				buf.append(", and ");
				buf.append(resources.size() - countCap);
				buf.append(" other files...");
				break;
			}
			if(count++ > 0)
				buf.append(", ");
		}

		return buf.toString();
	}
}
