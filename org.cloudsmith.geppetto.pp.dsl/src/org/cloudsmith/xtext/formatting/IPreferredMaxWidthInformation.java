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
package org.cloudsmith.xtext.formatting;

import com.google.inject.ImplementedBy;

/**
 * Provides the Preferred max Width Information
 * 
 */
@ImplementedBy(IPreferredMaxWidthInformation.Default.class)
public interface IPreferredMaxWidthInformation {
	/**
	 * This default implementation returns the value 132.
	 * 
	 * @return 132
	 */
	public static class Default implements IPreferredMaxWidthInformation {
		@Override
		public int getPreferredMaxWidth() {
			return 132;
		}
	}

	public int getPreferredMaxWidth();
}
