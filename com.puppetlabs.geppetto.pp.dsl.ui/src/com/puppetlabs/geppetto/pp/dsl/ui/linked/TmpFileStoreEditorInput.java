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

package com.puppetlabs.geppetto.pp.dsl.ui.linked;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * An implementation of filestore that indicates that the file is a temporary
 * "untitled" file.
 * 
 */
public class TmpFileStoreEditorInput extends FileStoreEditorInput {
	/**
	 * Property name of property that indicates "true" if a linked resource is backed by
	 * a temporary file.
	 */
	public static final QualifiedName UNTITLED_PROPERTY = new QualifiedName("org.eclipse.b3.beelang.ui", "untitled");

	public TmpFileStoreEditorInput(IFileStore fileStore) {
		super(fileStore);
	}

}
