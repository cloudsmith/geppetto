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
package org.cloudsmith.xtext.serializer.acceptor;

import java.util.List;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.sequencer.HiddenTokenSequencer;

/**
 * Class that fixes issue where a model without node model is serialized.
 * The default HiddenTokenSequencer only generates a default empty WS if there is
 * an attached node model.
 * TODO: ISSUE report this issue
 * 
 */
public class HiddenTokenSequencerForDom extends HiddenTokenSequencer {

	@Override
	protected List<INode> getHiddenNodesBetween(INode from, INode to) {
		List<INode> result = super.getHiddenNodesBetween(from, to);
		if(result == null) {
			delegate.acceptWhitespace(
				hiddenTokenHelper.getWhitespaceRuleFor(null, ""), IDomNode.IMPLIED_EMPTY_WHITESPACE, null);
		}
		return result;
	}
}
