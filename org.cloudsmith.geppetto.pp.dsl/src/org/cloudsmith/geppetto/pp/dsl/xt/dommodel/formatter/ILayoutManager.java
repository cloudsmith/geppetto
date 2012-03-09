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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter;

import org.cloudsmith.geppetto.pp.dsl.formatting.ILineSeparatorInformation;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.textflow.ITextFlow;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;

/**
 * An ILayoutManager is responsible for providing textual output to an {@link ITextFlow}.
 * 
 * 
 * 
 */
public interface ILayoutManager {
	public static class hack {
		public void hacking() {
			IFormattingContext ctx;
		}
	}

	public interface ILayoutContext {
		public Acceptor getErrorAcceptor();

		public IIndentationInformation getIndentationInformation();

		public ILineSeparatorInformation getLineSeparationInformation();

		public ITextRegion getRegionToFormat();
	}

	public ITextFlow format(IDomNode dom, ILayoutContext context);
}
