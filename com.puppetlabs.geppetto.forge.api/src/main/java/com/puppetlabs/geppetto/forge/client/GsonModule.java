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
package com.puppetlabs.geppetto.forge.client;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 */
public class GsonModule extends AbstractModule {
	public static class ChecksumMapAdapter implements JsonSerializer<Map<String, byte[]>>,
			JsonDeserializer<Map<String, byte[]>> {

		// @fmtOff
		public static final Type TYPE = new TypeToken<Map<String, byte[]>>() {}.getType();
		// @fmtOn

		private static void appendHex(StringBuilder bld, byte b) {
			bld.append(hexChars[(b & 0xf0) >> 4]);
			bld.append(hexChars[b & 0x0f]);
		}

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
					appendHex(hexBuilder, bytes[idx]);
				result.addProperty(entry.getKey(), hexBuilder.toString());
			}
			return result;
		}
	}

	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class DateJsonAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		public static String dateToString(Date src) {
			String target;
			synchronized(ISO_8601_TZ) {
				target = ISO_8601_TZ.format(src);
			}
			Matcher m = RFC_822_PTRN.matcher(target);
			if(m.matches()) {
				String tz = m.group(2);
				if("+0000".equals(tz))
					tz = "Z";
				else
					tz = tz.substring(0, 3) + ':' + tz.substring(3, 5);
				target = m.group(1) + tz;
			}
			return target;
		}

		public static Date stringToDate(String source) {
			Matcher m = ISO_8601_PTRN.matcher(source);
			if(m.matches()) {
				String tz = m.group(2);
				if("Z".equals(tz))
					tz = "+0000";
				else
					tz = tz.substring(0, 3) + tz.substring(4, 6);
				source = m.group(1) + tz;
			}
			synchronized(ISO_8601_TZ) {
				try {
					return ISO_8601_TZ.parse(source);
				}
				catch(ParseException e) {
					try {
						return HR_8601_TZ.parse(source);
					}
					catch(ParseException e2) {
						throw new JsonParseException(e);
					}
				}
			}
		}

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return stringToDate(json.getAsString());
		}

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(dateToString(src));
		}
	}

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
			try {
				return VersionRange.create(json.getAsString());
			}
			catch(IllegalArgumentException e) {
				return null;
			}
		}

		@Override
		public JsonElement serialize(VersionRange src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	public static GsonModule INSTANCE = new GsonModule();

	private static final Pattern ISO_8601_PTRN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d)(Z|(?:[+-]\\d\\d:\\d\\d))$");

	private static final Pattern RFC_822_PTRN = Pattern.compile("^(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d)([+-]\\d\\d\\d\\d)$");

	private static final SimpleDateFormat ISO_8601_TZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	private static final SimpleDateFormat HR_8601_TZ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

	private static char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private final GsonBuilder gsonBuilder;

	private final Gson gson;

	private GsonModule() {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(VersionRange.class, new VersionRangeJsonAdapter());
		gsonBuilder.registerTypeAdapter(Version.class, new VersionJsonAdapter());
		gsonBuilder.registerTypeAdapter(ModuleName.class, new ModuleName.JsonAdapter());
		gsonBuilder.registerTypeAdapter(ChecksumMapAdapter.TYPE, new ChecksumMapAdapter());
		gsonBuilder.registerTypeAdapter(Date.class, new DateJsonAdapter());

		gson = gsonBuilder.create();
	}

	@Override
	protected void configure() {
	}

	@Provides
	public Gson provideGson() {
		return gsonBuilder.create();
	}

	/**
	 * Creates a JSON representation for the given object using an internal
	 * synchronized {@link Gson} instance.
	 * 
	 * @param object
	 *            The object to produce JSON for
	 * @return JSON representation of the given <code>object</code>
	 */
	public String toJSON(Object object) {
		synchronized(gson) {
			return gson.toJson(object);
		}
	}
}
