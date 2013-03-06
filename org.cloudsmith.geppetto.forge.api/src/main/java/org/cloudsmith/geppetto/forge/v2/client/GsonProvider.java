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
package org.cloudsmith.geppetto.forge.v2.client;

import java.lang.reflect.Type;

import org.cloudsmith.geppetto.forge.v2.model.QName;
import org.org.cloudsmith.geppetto.semver.Version;
import org.org.cloudsmith.geppetto.semver.VersionRange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.Provider;

/**
 * A provider of {@link Gson} instances. Configured to handle the Forge timestamp
 * notation and string representation of {@link QName} and {@link VersionRequirement}.
 */
public class GsonProvider implements Provider<Gson> {
	public static class VersionJsonAdapter implements JsonSerializer<Version>, JsonDeserializer<Version> {
		@Override
		public Version deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return Version.create(json.getAsString());
		}

		@Override
		public JsonElement serialize(Version src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class VersionRangeJsonAdapter implements JsonSerializer<VersionRange>, JsonDeserializer<VersionRange> {
		@Override
		public VersionRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return VersionRange.create(json.getAsString());
		}

		@Override
		public JsonElement serialize(VersionRange src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	private static final GsonBuilder gsonBuilder;

	private static final Gson gson;

	static {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(VersionRange.class, new VersionRangeJsonAdapter());
		gsonBuilder.registerTypeAdapter(Version.class, new VersionJsonAdapter());
		gsonBuilder.registerTypeAdapter(QName.class, new QName.JsonAdapter());
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		gson = gsonBuilder.create();
	}

	/**
	 * Creates a JSON representation for the given object using an internal
	 * synchronized {@link Gson} instance.
	 * 
	 * @param object
	 *            The object to produce JSON for
	 * @return JSON representation of the given <code>object</code>
	 */
	public static String toJSON(Object object) {
		synchronized(gson) {
			return gson.toJson(object);
		}
	}

	@Override
	public Gson get() {
		return gsonBuilder.create();
	}
}
