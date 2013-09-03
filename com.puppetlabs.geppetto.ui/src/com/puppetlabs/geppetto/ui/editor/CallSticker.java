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
package org.cloudsmith.geppetto.ui.editor;

import org.eclipse.jface.text.Position;

/**
 * @author thhal
 * 
 */
class CallSticker extends Position {
	public static final String CATEGORY = "org.cloudsmith.geppetto.call";

	private ArgSticker[] arguments;

	CallSticker(int offset, int length, ArgSticker[] arguments) {
		super(offset, length);
		this.arguments = arguments;
	}

	ArgSticker[] getArguments() {
		return arguments;
	}

	void setArguments(ArgSticker[] arguments) {
		this.arguments = arguments;
	}
}
