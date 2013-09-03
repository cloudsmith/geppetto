/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ui.editor.findrefs.IReferenceFinder.ILocalResourceAccess;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * This is a verbatim copy of the class with the same name in the corresponding Xtext package.
 * TODO: it may not be needed.
 * 
 * @author Jan Koehnlein - Initial contribution and API
 */
public class SimpleLocalResourceAccess implements ILocalResourceAccess {

	private ResourceSet resourceSet;

	public SimpleLocalResourceAccess(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work) {
		try {
			return work.exec(resourceSet);
		}
		catch(Exception exc) {
			throw new WrappedException(exc);
		}
	}

}
