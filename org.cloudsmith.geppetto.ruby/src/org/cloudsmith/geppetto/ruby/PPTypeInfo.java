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
	public final Map<String, Entry> parameters;
	
	public PPTypeInfo(String typeName, String documentation, Map<String, Entry> properties, Map<String, Entry> parameters) {
		this.typeName = typeName;
		this.documentation = documentation;
		this.properties = Collections.unmodifiableMap(properties);
		this.parameters = Collections.unmodifiableMap(parameters);
	}
	public String getTypeName() {
		return typeName;
	}
	public String getDocumentation() {
		return documentation;
	}
	public Map<String, Entry> getProperties() {
		return properties;
	}
	public Map<String, Entry> getParameters() {
		return parameters;
	}
	public static class Entry {
		public final String documentation;
		
		public Entry(String documentation) {
			this.documentation = documentation;
		}
		public String getDocumentation() {
			return documentation;
		}
	}
}
