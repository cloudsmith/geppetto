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
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.linking.lazy.LazyLinker;

import com.google.inject.Inject;

/**
 * Adds handling of documentation comments and linking of resources.
 * 
 */

public class PPLinker extends LazyLinker {

	@Inject
	private DocumentationAssociator documentationAssociator;

	@Inject
	PPResourceLinker resourceLinker;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.linking.impl.AbstractCleaningLinker#afterModelLinked(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.diagnostics.IDiagnosticConsumer)
	 */
	@Override
	protected void afterModelLinked(EObject model, IDiagnosticConsumer diagnosticsConsumer) {
		IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(diagnosticsConsumer);
		documentationAssociator.linkDocumentation(model, acceptor);
		resourceLinker.link(model, acceptor);
	}

}
