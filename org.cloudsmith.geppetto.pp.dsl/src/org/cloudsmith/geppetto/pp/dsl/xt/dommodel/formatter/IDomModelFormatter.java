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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter;

import org.eclipse.xtext.parsetree.reconstr.ITokenStream;
import org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor;

/**
 * A Formatter capable of formatting a DomModel
 * 
 */
public interface IDomModelFormatter {
	ISequenceAcceptor createFormatterStream(String initalIndentation, ITokenStream outputStream,
			boolean preserveWhitespaces);

}
