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
package org.cloudsmith.geppetto.pp.dsl.tests.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit.serializer.DebugSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;

/**
 * A DebugSequenceAcceptor that implements IHiddenTokenSequencer.
 * 
 */
public class DebugHiddenTokenSequencer extends DebugSequenceAcceptor implements IHiddenTokenSequencer {

	@Override
	public void init(EObject context, EObject semanticObject, ISequenceAcceptor sequenceAcceptor, Acceptor errorAcceptor) {
		add("HIDDEN INIT", "", "", -1, NO_NODE);

	}

}
