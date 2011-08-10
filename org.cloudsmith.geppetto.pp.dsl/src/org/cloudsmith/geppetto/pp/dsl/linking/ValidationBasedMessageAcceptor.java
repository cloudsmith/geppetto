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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * A message acceptor using an instance of an {@link IDiagnosticConsumer} as the recipient of
 * the message.
 * 
 */
public class ValidationBasedMessageAcceptor extends AbstractMessageAcceptor {
	private ValidationMessageAcceptor acceptor;

	public ValidationBasedMessageAcceptor(ValidationMessageAcceptor acceptor) {
		if(acceptor == null)
			throw new IllegalArgumentException("Acceptor can not be null");
		this.acceptor = acceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void accept(Severity severity, String message, EObject source, EStructuralFeature feature, int index,
			String issueCode, String... issueData) {

		if(severity == null)
			throw new IllegalArgumentException("severity can not be null");
		if(feature == null)
			throw new IllegalArgumentException("feature can not be null");
		if(source == null)
			throw new IllegalArgumentException("source can not be null");

		switch(severity) {
			case ERROR:
				acceptor.acceptError(message, source, feature, index, issueCode, issueData);
				break;
			case INFO:
				acceptor.acceptInfo(message, source, feature, index, issueCode, issueData);
				break;
			case WARNING:
				acceptor.acceptWarning(message, source, feature, index, issueCode, issueData);
				break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.xtext.nodemodel.INode, java.lang.String, java.lang.String)
	 */
	@Override
	public void accept(Severity severity, String message, EObject source, int textOffset, int textLength,
			String issueCode, String[] issueData) {

		if(source == null)
			throw new IllegalArgumentException("source can not be null");
		switch(severity) {
			case ERROR:
				acceptor.acceptError(message, source, textOffset, textLength, issueCode, issueData);
				break;
			case INFO:
				acceptor.acceptInfo(message, source, textOffset, textLength, issueCode, issueData);
				break;
			case WARNING:
				acceptor.acceptWarning(message, source, textOffset, textLength, issueCode, issueData);
				break;
		}

		// Not so good, the abstract implementation handles finding the correct index
		// accept(severity, message, e.eContainer(), e.eContainingFeature(), INSIGNIFICANT_INDEX, issueCode, issueData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.xtext.nodemodel.INode, java.lang.String, java.lang.String)
	 */
	@Override
	public void accept(Severity severity, String message, INode node, String issueCode, String... issueData) {

		if(node == null)
			throw new IllegalArgumentException("node can not be null");

		EObject e = node.getSemanticElement();
		if(e == null)
			throw new IllegalArgumentException("node must reference an EObject");
		switch(severity) {
			case ERROR:
				acceptError(message, e, issueCode, issueData);
				break;
			case INFO:
				throw new UnsupportedOperationException("INFO severity is unsupported at the moment");
				// acceptInfo(message, e, issueCode, issueData);
				// break;
			case WARNING:
				acceptWarning(message, e, issueCode, issueData);
				// acceptor.acceptWarning(message, source, feature, index, issueCode, issueData);
				break;
		}

		// Not so good, the abstract implementation handles finding the correct index
		// accept(severity, message, e.eContainer(), e.eContainingFeature(), INSIGNIFICANT_INDEX, issueCode, issueData);

	}
}
