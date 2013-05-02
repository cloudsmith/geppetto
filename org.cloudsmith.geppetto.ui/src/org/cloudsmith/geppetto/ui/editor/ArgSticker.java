/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.ui.editor;

import org.cloudsmith.geppetto.forge.util.Argument;
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
