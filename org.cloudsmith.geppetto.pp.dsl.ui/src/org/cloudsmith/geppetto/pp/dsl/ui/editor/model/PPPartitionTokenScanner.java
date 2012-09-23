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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.model;

import org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit.PPTokenTypeToPartionMapper;
import org.eclipse.xtext.ui.editor.model.PartitionTokenScanner;

/**
 * A Paritioning token scanner that merges String partitions (since they are represented by a sequence of tokens in PP
 * and the default implementation only merges tokens in the default partition).
 * 
 */
public class PPPartitionTokenScanner extends PartitionTokenScanner {

	/**
	 * @see org.eclipse.xtext.ui.editor.model.PartitionTokenScanner#shouldMergePartitions(java.lang.String)
	 */
	@Override
	protected boolean shouldMergePartitions(String contentType) {
		return super.shouldMergePartitions(contentType) ||
				PPTokenTypeToPartionMapper.STRING_LITERAL_PARTITION.equals(contentType);

	}
}
