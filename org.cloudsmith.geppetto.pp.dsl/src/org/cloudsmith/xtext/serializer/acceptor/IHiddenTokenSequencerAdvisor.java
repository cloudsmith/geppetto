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
package org.cloudsmith.xtext.serializer.acceptor;

import com.google.inject.ImplementedBy;

/**
 * Helps with advice to the HiddenTokenSequencer
 * 
 */
@ImplementedBy(org.cloudsmith.xtext.serializer.acceptor.IHiddenTokenSequencerAdvisor.Default.class)
public interface IHiddenTokenSequencerAdvisor {

	public static class Default implements IHiddenTokenSequencerAdvisor {

		@Override
		public boolean shouldSaveRestoreState() {
			return true;
		}

	}

	public boolean shouldSaveRestoreState();
}
