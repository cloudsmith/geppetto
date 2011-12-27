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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * 
 *
 */
public abstract class AbstractMessageAcceptor implements IMessageAcceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String[])
	 */
	@Override
	public abstract void accept(Severity severity, String message, EObject source, EStructuralFeature feature,
			int index, String issueCode, String... issueData);

	@Override
	public void accept(Severity severity, String message, EObject source, String issueCode, String... issueData) {
		accept(
			severity, message, source.eContainer(), source.eContainingFeature(), indexOfSourceInParent(source),
			issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.xtext.nodemodel.INode, java.lang.String, java.lang.String[])
	 */
	@Override
	public abstract void accept(Severity severity, String message, INode node, String issueCode, String... issueData);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void acceptError(String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData) {
		accept(Severity.ERROR, message, source, feature, index, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.String, java.lang.String)
	 */
	@Override
	public void acceptError(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		acceptError(message, source, feature, INSIGNIFICANT_INDEX, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void acceptError(String message, EObject source, int textOffset, int length, String issueCode,
			String... issueData) {
		accept(Severity.ERROR, message, source, textOffset, length, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject, int,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void acceptError(String message, EObject source, int index, String issueCode, String... issueData) {
		acceptError(message, source.eContainer(), source.eContainingFeature(), index, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void acceptError(String message, EObject source, String issueCode, String... issueData) {
		acceptError(
			message, source.eContainer(), source.eContainingFeature(), indexOfSourceInParent(source), issueCode,
			issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.xtext.nodemodel.INode, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void acceptError(String message, INode node, String issueCode, String... issueData) {
		accept(Severity.ERROR, message, node, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptWarning(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String[])
	 */
	@Override
	public void acceptWarning(String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData) {
		accept(Severity.WARNING, message, source, feature, index, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptWarning(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.String, java.lang.String[])
	 */
	@Override
	public void acceptWarning(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		acceptWarning(message, source, feature, INSIGNIFICANT_INDEX, issueCode, issueData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptError(java.lang.String, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void acceptWarning(String message, EObject source, int textOffset, int length, String issueCode,
			String... issueData) {
		accept(Severity.WARNING, message, source, textOffset, length, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptWarning(java.lang.String, org.eclipse.emf.ecore.EObject, int,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public void acceptWarning(String message, EObject source, int index, String issueCode, String... issueData) {
		acceptWarning(message, source.eContainer(), source.eContainingFeature(), index, issueCode, issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptWarning(java.lang.String, org.eclipse.emf.ecore.EObject, java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void acceptWarning(String message, EObject source, String issueCode, String... issueData) {
		acceptWarning(
			message, source.eContainer(), source.eContainingFeature(), indexOfSourceInParent(source), issueCode,
			issueData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#acceptWarning(java.lang.String, org.eclipse.xtext.nodemodel.INode,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public void acceptWarning(String message, INode node, String issueCode, String... issueData) {
		accept(Severity.WARNING, message, node, issueCode, issueData);
	}

	/**
	 * @return the INode corresponding to the 'obj.structuralFeature([index])*'
	 */
	protected INode getNode(EObject obj, EStructuralFeature structuralFeature, int index) {
		INode parserNode = NodeModelUtils.getNode(obj);
		if(parserNode != null) {
			if(structuralFeature != null) {
				List<INode> nodes = NodeModelUtils.findNodesForFeature(obj, structuralFeature);
				if(index < 0) // insignificant index
					index = 0;
				if(nodes.size() > index)
					parserNode = nodes.get(index);
			}
			return parserNode;
		}
		return null;
	}

	/**
	 * @return the index of source in its containing feature, or {@link #INSIGNIFICANT_INDEX} if containing feature is not indexed.
	 */
	private int indexOfSourceInParent(EObject source) {
		EObject container = source.eContainer();
		if(container == null)
			throw new IllegalArgumentException("source is not contained");
		EStructuralFeature feature = source.eContainingFeature();
		if(!feature.isMany())
			return INSIGNIFICANT_INDEX;
		List<?> featurelist = (List<?>) container.eGet(feature);
		return ECollections.indexOf(featurelist, source, 0);
	}
}
