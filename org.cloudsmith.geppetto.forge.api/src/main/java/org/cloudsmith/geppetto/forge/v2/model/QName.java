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
package org.cloudsmith.geppetto.forge.v2.model;

import java.io.Serializable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A qualified name
 */
public class QName implements Serializable {
	public static class JsonAdapter implements JsonDeserializer<QName>, JsonSerializer<QName> {
		@Override
		public QName deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return new QName(json.getAsString());
		}

		@Override
		public JsonElement serialize(QName src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	private static final long serialVersionUID = 8801018500147502545L;

	private final char separator;

	private final String qualifier;

	private final String name;

	public QName(String fullName) {
		int dashIdx = fullName.lastIndexOf('-');
		int idx = fullName.lastIndexOf('/');
		if(dashIdx > idx) {
			separator = '-';
			idx = dashIdx;
		}
		else
			separator = '/';

		if(!(idx > 0 && idx < fullName.length() - 1))
			throw new IllegalArgumentException("Name must be in the form <qualifier>-<name> or <qualifier>/<name>");

		this.qualifier = fullName.substring(0, idx);
		this.name = fullName.substring(idx + 1);
	}

	public QName(String qualifier, char separator, String name) {
		this.qualifier = qualifier;
		this.separator = separator;
		this.name = name;
	}

	/**
	 * Compares the two names for equality. Names can have different separators and still be equal.
	 * 
	 * @return The result of the comparison.
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof QName))
			return false;
		QName qo = (QName) o;
		return qualifier.equals(qo.qualifier) && name.equals(qo.name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the qualifier
	 */
	public String getQualifier() {
		return qualifier;
	}

	/**
	 * Computes the hash value for this qualified name. The separator is excluded from the computation
	 * 
	 * @return The computed hash code.
	 */
	@Override
	public int hashCode() {
		return qualifier.hashCode() * 31 + name.hashCode();
	}

	@Override
	public String toString() {
		return qualifier + separator + name;
	}
}
