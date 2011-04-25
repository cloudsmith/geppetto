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

	public static class Entry {
		public final String documentation;
		private boolean required;

		public Entry(String documentation, boolean required) {
			this.documentation = documentation;
			this.required = required;
		}

		public String getDocumentation() {
			return documentation;
		}

		public boolean isRequired() {
			return required;
		}
	}

	public final String typeName;
	public final String documentation;
	public final Map<String, Entry> properties;

	public final Map<String, Entry> parameters;

	public PPTypeInfo(String typeName, String documentation,
			Map<String, Entry> properties, Map<String, Entry> parameters) {
		this.typeName = typeName;
		this.documentation = documentation;
		if (properties == null)
			properties = Collections.emptyMap();
		if (parameters == null)
			parameters = Collections.emptyMap();
		this.properties = Collections.unmodifiableMap(properties);
		this.parameters = Collections.unmodifiableMap(parameters);
	}

	public String getDocumentation() {
		return documentation;
	}

	public Map<String, Entry> getParameters() {
		return parameters;
	}

	public Map<String, Entry> getProperties() {
		return properties;
	}

	public String getTypeName() {
		return typeName;
	}
}
