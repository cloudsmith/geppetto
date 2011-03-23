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
package org.cloudsmith.geppetto.ruby;

import java.util.Collections;
import java.util.Map;

/**
 * Represents information about a PP Type (a Resource).
 */
public class PPTypeInfo {

	public final String typeName;
	public final String documentation;
	public final Map<String, Entry> properties;
	
	public PPTypeInfo(String typeName, String documentation, Map<String, Entry> properties) {
		this.typeName = typeName;
		this.documentation = documentation;
		this.properties = Collections.unmodifiableMap(properties);
	}
	public static class Entry {
		public final String documentation;
		
		Entry(String documentation) {
			this.documentation = documentation;
		}
	}
}
