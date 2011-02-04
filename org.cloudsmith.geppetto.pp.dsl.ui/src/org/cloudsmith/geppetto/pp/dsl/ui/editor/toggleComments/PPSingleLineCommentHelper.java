/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.editor.toggleComments;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.xtext.ui.editor.toggleComments.ISingleLineCommentHelper;

public class PPSingleLineCommentHelper implements ISingleLineCommentHelper {

	private static final String[] result = { "#" };

	public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return result;
	}

}
