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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapterFactory;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.TPVariable;
import org.cloudsmith.geppetto.pp.pptp.TargetElement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * Overrides the default description strategy to provide the super class name for element
 * that implement this aspect.
 * 
 */
public class PPResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	public class DefaultReferenceDescription implements IReferenceDescription {

		private int indexInList = -1;

		private URI sourceEObjectUri;

		private URI targetEObjectUri;

		private EReference eReference;

		private URI containerEObjectURI;

		public DefaultReferenceDescription(EObject from, IEObjectDescription to, int i, URI containerEObjectURI) {
			this.sourceEObjectUri = EcoreUtil2.getNormalizedURI(from);
			this.targetEObjectUri = to.getEObjectURI();
			this.eReference = null; // See how far we get :)
			this.indexInList = i;
			this.containerEObjectURI = containerEObjectURI;
		}

		public URI getContainerEObjectURI() {
			return containerEObjectURI;
		}

		public EReference getEReference() {
			return eReference;
		}

		public int getIndexInList() {
			return indexInList;
		}

		public URI getSourceEObjectUri() {
			return sourceEObjectUri;
		}

		public URI getTargetEObjectUri() {
			return targetEObjectUri;
		}

	}

	private final static Logger LOG = Logger.getLogger(PPResourceDescriptionStrategy.class);

	@Inject
	IQualifiedNameConverter converter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy#createEObjectDescriptions(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.util.IAcceptor)
	 */
	@Override
	public boolean createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if(getQualifiedNameProvider() == null)
			return false;
		try {
			QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
			if(qualifiedName != null) {
				acceptor.accept(EObjectDescription.create(qualifiedName, eObject, getDataForEObject(eObject)));
			}
		}
		catch(Exception exc) {
			LOG.error(exc.getMessage());
		}
		return true;
	}

	/**
	 * PP specific reference descriptors.
	 * 
	 * @return true if children of the from eobject should be traversed
	 * @param source
	 *            - the eobject with a referece
	 * @param sourceContainer
	 *            - the closest exported container to source
	 * @param targetDescriptor
	 *            - the descriptor the source references
	 * @param acceptor
	 *            - where the resulting PPReferenceDescription should be accepted
	 */
	public boolean createPPReferenceDescriptions(EObject source, IEObjectDescription sourceContainer,
			IEObjectDescription targetDescriptor, IAcceptor<PPReferenceDescription> acceptor) {
		List<IEObjectDescription> targetReferences = CrossReferenceAdapterFactory.eINSTANCE.get(source);
		if(targetReferences != null) {
			acceptor.accept(PPReferenceDescription.create(
				EcoreUtil2.getNormalizedURI(source), sourceContainer, targetDescriptor));
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy#createReferenceDescriptions(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.common.util.URI, org.eclipse.xtext.util.IAcceptor)
	 */
	@Override
	public boolean createReferenceDescriptions(EObject from, URI exportedContainerURI,
			IAcceptor<IReferenceDescription> acceptor) {
		boolean result = super.createReferenceDescriptions(from, exportedContainerURI, acceptor);
		// Add PP references
		List<IEObjectDescription> xrefs = CrossReferenceAdapterFactory.eINSTANCE.get(from);
		if(xrefs != null && xrefs.size() > 0) {
			result = true;
			for(int i = 0; i < xrefs.size(); i++)
				acceptor.accept(new DefaultReferenceDescription(from, xrefs.get(i), i, exportedContainerURI));
		}
		return result;
	}

	/**
	 * Computes data to associate with an IEObjectDescription of the given EObject.
	 * 
	 * @param eObject
	 * @return
	 */
	protected Map<String, String> getDataForEObject(EObject eObject) {
		// pp: class x inherits y {}
		if(eObject.eClass() == PPPackage.Literals.HOST_CLASS_DEFINITION) {
			HostClassDefinition d = (HostClassDefinition) eObject;
			Map<String, String> data = Maps.newHashMapWithExpectedSize(2);
			if(d.getParent() != null && d.getParent().eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE) {
				String parentName = ((LiteralNameOrReference) d.getParent()).getValue();
				data.put(PPDSLConstants.PARENT_NAME_DATA, parentName);
			}
			int argCount = d.getArguments() == null
					? 0
					: d.getArguments().getArguments().size();
			if(argCount > 0)
				data.put(PPDSLConstants.CLASS_ARG_COUNT, Integer.toString(argCount));
			return data;
		}
		else if(eObject.eClass() == PPPackage.Literals.DEFINITION_ARGUMENT) {
			DefinitionArgument arg = (DefinitionArgument) eObject;
			Expression e = arg.getValue();
			if(e != null) {
				ICompositeNode n = NodeModelUtils.getNode(e);
				Map<String, String> data = Maps.newHashMapWithExpectedSize(1);
				data.put(PPDSLConstants.DEFAULT_EXPRESSION_DATA, n.getText());
				return data;
			}
		}
		else if(PPTPPackage.Literals.TARGET_ELEMENT.isSuperTypeOf(eObject.eClass())) {
			Map<String, String> data = Maps.newHashMapWithExpectedSize(1);
			if(((TargetElement) eObject).isDeprecated())
				data.put(PPDSLConstants.ELEMENT_DEPRECATED, "true");
			if(eObject.eClass() == PPTPPackage.Literals.PARAMETER) {
				Parameter p = (Parameter) eObject;
				if(p.isNamevar())
					data.put(PPDSLConstants.PARAMETER_NAMEVAR, "true");
				return data;
			}
			if(eObject.eClass() == PPTPPackage.Literals.TP_VARIABLE) {
				TPVariable tpvar = (TPVariable) eObject;
				String pattern = tpvar.getPattern();
				if(pattern != null && pattern.length() > 0) {
					data.put(PPDSLConstants.VARIABLE_PATTERN, pattern);
				}
				return data;
			}
		}
		// TODO: pp: node x inherits [name | stringexpr | regexp | default]
		// TODO: https://github.com/cloudsmith/geppetto/issues/67

		return Collections.<String, String> emptyMap();
	}

}
