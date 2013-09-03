/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.xtext.ui.editor.formatting;

import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * A dummy read only unit of work that can be used to perform
 * a readOnly on a document. Typically used to force a document to be updated
 * before a modify operation on the same document (due to an Xtext issue).
 * 
 */
public class DummyReadOnly implements IUnitOfWork<Object, XtextResource> {

	public static final DummyReadOnly Instance = new DummyReadOnly();

	@Override
	public Object exec(XtextResource state) throws Exception {
		return null;
	}

}
