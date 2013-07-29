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
package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

/**
 * Interface for performing save actions.
 * 
 */
public interface ISaveActions {
	public void perform(IResource r, IXtextDocument document);
}
