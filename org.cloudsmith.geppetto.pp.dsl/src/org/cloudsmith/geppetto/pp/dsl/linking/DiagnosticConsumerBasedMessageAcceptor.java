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
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticProducer;
import org.eclipse.xtext.nodemodel.INode;

/**
 * A message acceptor using an instance of an {@link IDiagnosticConsumer} as the recipient of
 * the message.
 * 
 */
public class DiagnosticConsumerBasedMessageAcceptor extends AbstractMessageAcceptor {
	private LinkingDiagnosticProducer producer;

	public DiagnosticConsumerBasedMessageAcceptor(IDiagnosticConsumer consumer) {
		if(consumer == null)
			throw new IllegalArgumentException("Consumer can not be null");
		producer = new LinkingDiagnosticProducer(consumer);
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

		if(source.eClass().getEStructuralFeature(feature.getName()) != feature) {
			throw new IllegalArgumentException("EClass '" + source.eClass().getName() +
					"' does not expose a feature '" + feature.getName() + //
					"' (id: " + feature.getFeatureID() + ")");
		}

		producer.setNode(getNode(source, feature, index));
		DiagnosticMessage m = new DiagnosticMessage(message, severity, issueCode, issueData);
		producer.addDiagnostic(m);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor#accept(org.eclipse.xtext.diagnostics.Severity, java.lang.String,
	 * org.eclipse.emf.ecore.EObject, int, int, java.lang.String, java.lang.String[])
	 */
	@Override
	public void accept(Severity severity, String message, EObject source, int textOffset, int textLength,
			String issueCode, String[] issueData) {
		throw new UnsupportedOperationException("Please implement support to report textual error");
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

		producer.setNode(node);
		DiagnosticMessage m = new DiagnosticMessage(message, severity, issueCode, issueData);
		producer.addDiagnostic(m);

	}
}
