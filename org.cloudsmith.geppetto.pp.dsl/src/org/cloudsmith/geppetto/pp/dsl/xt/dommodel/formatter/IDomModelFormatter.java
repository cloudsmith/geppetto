/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Itemis - initial API
 *   Cloudsmith - initial API and implementation
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.internal.Nullable;

/**
 * A Formatter capable of formatting a DomModel
 * TODO: rename to IFormatter (named differently to maintain sanity while implementing)
 */
public interface IDomModelFormatter {

	public ReplaceRegion format(IDomNode dom, @Nullable ITextRegion regionToFormat,
			IFormattingContext formattingContext, ISerializationDiagnostic.Acceptor errors);

}
