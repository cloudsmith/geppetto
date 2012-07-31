/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.dsl.ppdoc.DocumentationAssociator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.linking.lazy.LazyLinker;

import com.google.inject.Inject;

/**
 * Adds handling of documentation comments and linking of resources.
 * This implementation should be used when the PP additions should be performed once as part of linking.
 * (As is the case when running standalone/runtime only). This class should not be used for PP when participating
 * in the ui where a PPResource (in the ui package) and its PPResourceFactory should be used instead.
 * 
 */

public class PPLinker extends LazyLinker {

	@Inject
	private DocumentationAssociator documentationAssociator;

	@Inject
	PPResourceLinker resourceLinker;

	@Override
	protected void afterModelLinked(EObject model, IDiagnosticConsumer diagnosticsConsumer) {
		IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(diagnosticsConsumer);
		documentationAssociator.linkDocumentation(model);
		documentationAssociator.validateDocumentation(model, acceptor);
		resourceLinker.link(model, acceptor, false);
	}

}
