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
package org.cloudsmith.geppetto.forge.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.Parameter;
import org.cloudsmith.geppetto.forge.Property;
import org.cloudsmith.geppetto.forge.Provider;
import org.cloudsmith.geppetto.forge.Type;
import org.cloudsmith.geppetto.forge.impl.DependencyImpl;
import org.cloudsmith.geppetto.forge.impl.MetadataImpl;
import org.cloudsmith.geppetto.forge.impl.RepositoryImpl;
import org.cloudsmith.geppetto.forge.impl.TypeImpl;
import org.eclipse.emf.common.util.EList;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
	public static class ChecksumMapAdapter implements JsonSerializer<Map<String, byte[]>>,
			JsonDeserializer<Map<String, byte[]>> {

		public static final java.lang.reflect.Type TYPE = new TypeToken<Map<String, byte[]>>() {
		}.getType();

		private static int hexToByte(char c) {
			return c >= 'a'
					? c - ('a' - 10)
					: c - '0';
		}

		@Override
		public Map<String, byte[]> deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {

			Set<Map.Entry<String, JsonElement>> entrySet = json.getAsJsonObject().entrySet();
			Map<String, byte[]> result = new HashMap<String, byte[]>();
			for(Map.Entry<String, JsonElement> entry : entrySet) {
				String hexString = entry.getValue().getAsString();
				int top = hexString.length() / 2;
				byte[] bytes = new byte[top];
				for(int idx = 0, cidx = 0; idx < top; ++idx) {
					int val = hexToByte(hexString.charAt(cidx++)) << 4;
					val |= hexToByte(hexString.charAt(cidx++));
					bytes[idx] = (byte) (val & 0xff);
				}
				result.put(entry.getKey(), bytes);
			}
			return result;
		}

		@Override
		public JsonElement serialize(Map<String, byte[]> src, java.lang.reflect.Type typeOfSrc,
				JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			StringBuilder hexBuilder = new StringBuilder(32);
			for(Map.Entry<String, byte[]> entry : src.entrySet()) {
				hexBuilder.setLength(0);
				byte[] bytes = entry.getValue();
				for(int idx = 0; idx < bytes.length; ++idx)
					RepositoryImpl.appendHex(hexBuilder, bytes[idx]);
				result.addProperty(entry.getKey(), hexBuilder.toString());
			}
			return result;
		}
	}

	public static abstract class ContainerDeserializer<T> implements JsonDeserializer<T> {
		protected static <T> void deserializeInto(JsonElement json, EList<T> result, Class<? extends T> cls,
				JsonDeserializationContext context) {
			JsonArray jsonTypes = json.getAsJsonArray();
			Iterator<JsonElement> elements = jsonTypes.iterator();
			while(elements.hasNext()) {
				@SuppressWarnings("unchecked")
				T elem = (T) context.deserialize(elements.next(), cls);
				result.add(elem);
			}
		}
	}

	public static class EListSerializer<T> implements JsonSerializer<EList<T>> {

		@Override
		public JsonElement serialize(EList<T> src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			JsonArray result = new JsonArray();
			for(T elem : src)
				result.add(context.serialize(elem));
			return result;
		}
	}

	private static final GsonBuilder gsonBuilder;

	static {
		GsonBuilder bld = new GsonBuilder();
		bld.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		bld.setPrettyPrinting();
		bld.excludeFieldsWithoutExposeAnnotation();
		bld.registerTypeAdapter(new TypeToken<EList<Dependency>>() {
		}.getType(), new EListSerializer<Dependency>());
		bld.registerTypeAdapter(new TypeToken<EList<Type>>() {
		}.getType(), new EListSerializer<Type>());
		bld.registerTypeAdapter(new TypeToken<EList<Provider>>() {
		}.getType(), new EListSerializer<Provider>());
		bld.registerTypeAdapter(new TypeToken<EList<Property>>() {
		}.getType(), new EListSerializer<Property>());
		bld.registerTypeAdapter(new TypeToken<EList<Parameter>>() {
		}.getType(), new EListSerializer<Parameter>());
		bld.registerTypeAdapter(new TypeToken<MetadataImpl>() {
		}.getType(), new MetadataImpl.JsonAdapter());
		bld.registerTypeAdapter(new TypeToken<DependencyImpl>() {
		}.getType(), new DependencyImpl.JsonAdapter());
		bld.registerTypeAdapter(new TypeToken<TypeImpl>() {
		}.getType(), new TypeImpl.Deserializer());
		bld.registerTypeAdapter(ChecksumMapAdapter.TYPE, new ChecksumMapAdapter());
		gsonBuilder = bld;
	}

	public static Gson getGSon() {
		return gsonBuilder.create();
	}
}
