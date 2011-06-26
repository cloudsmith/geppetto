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
import java.util.Map;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.TargetElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
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
	 * Computes data to associate with an IEObjectDescription of the given EObject.
	 * 
	 * @param eObject
	 * @return
	 */
	protected Map<String, String> getDataForEObject(EObject eObject) {
		// pp: class x inherits y {}
		if(eObject.eClass() == PPPackage.Literals.HOST_CLASS_DEFINITION) {
			HostClassDefinition d = (HostClassDefinition) eObject;
			if(d.getParent() != null && d.getParent().eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE) {
				String parentName = ((LiteralNameOrReference) d.getParent()).getValue();
				Map<String, String> data = Maps.newHashMapWithExpectedSize(1);
				data.put(PPDSLConstants.PARENT_NAME_DATA, parentName);
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
		}
		// TODO: pp: node x inherits [name | stringexpr | regexp | default]
		// TODO: https://github.com/cloudsmith/geppetto/issues/67

		return Collections.<String, String> emptyMap();
	}
}
