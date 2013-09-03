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
package com.puppetlabs.geppetto.ui.editor;

import com.puppetlabs.geppetto.forge.util.Argument;
import org.eclipse.jface.text.Position;

class ArgSticker extends Position {
	private Object value;

	ArgSticker(Argument arg) {
		super(arg.getOffset(), arg.getLength());
		this.value = arg.getValue();
	}

	ArgSticker(int offset, int length, Object value) {
		super(offset, length);
		this.value = value;
	}

	Object getValue() {
		return value;
	}

	void setValue(Object value) {
		this.value = value;
	}
}
