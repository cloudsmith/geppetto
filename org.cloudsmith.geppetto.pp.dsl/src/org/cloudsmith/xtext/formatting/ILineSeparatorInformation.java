/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.cloudsmith.xtext.formatting;

import com.google.inject.ImplementedBy;

/**
 * THIS IS A VERBATIM COPY FROM XTEXT 2.3
 * NOTE: picking up the line separator for the current platform is not "safe" since
 * preferences may be set differently. It needs to be easy for users to get an
 * ILineSeparatorInformation suitable for a resource!
 * 
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.3
 */
@ImplementedBy(ILineSeparatorInformation.Default.class)
public interface ILineSeparatorInformation {

	class Default implements ILineSeparatorInformation {

		public String getLineSeparator() {
			return System.getProperty("line.separator");
		}
	}

	String getLineSeparator();
}
