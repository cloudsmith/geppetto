/**
 * Copyright (c) 2006-2009, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 */

package org.cloudsmith.geppetto.pp.dsl.ui.linked;

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
