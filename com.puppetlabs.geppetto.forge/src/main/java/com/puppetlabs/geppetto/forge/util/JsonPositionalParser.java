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
package org.cloudsmith.geppetto.forge.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.FilePosition;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JsonPositionalParser {
	public static class JArray extends JElement {
		private final List<JElement> elements;

		JArray(Object sourceRef, int[] pos, List<JElement> elements) {
			super(sourceRef, pos);
			this.elements = elements;
		}

		@Override
		public List<Object> getValue() {
			int top = elements.size();
			ArrayList<Object> values = new ArrayList<Object>(top);
			for(int idx = 0; idx < top; ++idx)
				values.add(elements.get(idx).getValue());
			return values;
		}

		public List<JElement> getValues() {
			return elements;
		}
	}

	public static abstract class JElement implements FilePosition {
		private final int offset;

		private final int length;

		private final int line;

		private final File file;

		JElement(Object sourceRef, int[] pos) {
			this.file = (File) sourceRef;
			this.line = pos[0];
			this.offset = pos[1];
			this.length = pos[2];
		}

		public File getFile() {
			return file;
		}

		public int getLength() {
			return length;
		}

		public int getLine() {
			return line;
		}

		public int getOffset() {
			return offset;
		}

		public abstract Object getValue();

		public String toStringOrNull() {
			return null;
		}
	}

	public static class JEntry extends JElement {
		private final String key;

		private final JElement value;

		JEntry(Object sourceRef, int[] pos, String key, JElement value) {
			super(sourceRef, pos);
			this.key = key;
			this.value = value;
		}

		public JElement getElement() {
			return value;
		}

		public String getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return value == null
					? null
					: value.getValue();
		}
	}

	public static class JObject extends JElement {
		private final List<JEntry> entries;

		JObject(Object sourceRef, int[] pos, List<JEntry> entries) {
			super(sourceRef, pos);
			this.entries = entries;
		}

		public List<JEntry> getEntries() {
			return entries;
		}

		@Override
		public Map<String, Object> getValue() {
			int idx = entries.size();
			Map<String, Object> map = new HashMap<String, Object>(idx);
			while(--idx >= 0) {
				JEntry entry = entries.get(idx);
				map.put(entry.getKey(), entry.getValue());
			}
			return map;
		}
	}

	public static class JPrimitive extends JElement {
		private final Object value;

		JPrimitive(Object sourceRef, int[] pos, Object value) {
			super(sourceRef, pos);
			this.value = value;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public String toStringOrNull() {
			return value instanceof String
					? (String) value
					: null;
		}
	}

	static class JsonExtFactory extends JsonFactory {
		public JsonParser createJsonParser(File f, Reader reader) throws IOException, JsonParseException {
			return _createJsonParser(reader, _createContext(f, true));
		}
	}

	private JsonParser jp;

	private String content;

	private int[] adjustPosition(int line, int offset, int length) {
		int top = content.length();
		++length;
		while(offset < top) {
			char c = content.charAt(offset);
			if(!(c == ',' || Character.isWhitespace(c)))
				break;
			++offset;
			--length;
			if(c == '\n')
				++line;
		}
		return new int[] { line, offset, length };
	}

	public JElement parse(File file, String content) throws JsonParseException, IOException {
		JsonExtFactory jsonFactory = new JsonExtFactory();
		this.content = content;
		jp = jsonFactory.createJsonParser(file, new StringReader(content));
		JsonToken token = jp.nextToken();
		if(token == JsonToken.NOT_AVAILABLE)
			return null;
		return parseValue(token);
	}

	private JArray parseArray() throws JsonParseException, IOException {
		JsonLocation loc = jp.getTokenLocation();
		long startPos = loc.getCharOffset();
		int line = loc.getLineNr();
		List<JElement> values = new ArrayList<JElement>();
		for(;;) {
			JsonToken token = jp.nextToken();
			if(token == JsonToken.END_ARRAY) {
				int[] pos = adjustPosition(
					line, (int) startPos, (int) (jp.getCurrentLocation().getCharOffset() - startPos));
				return new JArray(loc.getSourceRef(), pos, values);
			}
			values.add(parseValue(token));
		}
	}

	private JEntry parseField() throws JsonParseException, IOException {
		JsonLocation loc = jp.getTokenLocation();
		long startPos = loc.getCharOffset();
		int line = loc.getLineNr();
		String key = jp.getCurrentName();
		JElement value = parseValue(jp.nextToken());

		// We get weird positions from the parser that includes whitespace etc. We adjust
		// that here
		int[] pos = adjustPosition(line, (int) startPos, (int) (jp.getCurrentLocation().getCharOffset() - startPos));
		return new JEntry(loc.getSourceRef(), pos, key, value);
	}

	private JObject parseObject() throws JsonParseException, IOException {
		JsonLocation loc = jp.getTokenLocation();
		long startPos = loc.getCharOffset();
		int line = loc.getLineNr();
		List<JEntry> entries = new ArrayList<JEntry>();
		for(;;) {
			switch(jp.nextToken()) {
				case END_OBJECT:
					int[] pos = adjustPosition(
						line, (int) startPos, (int) (jp.getCurrentLocation().getCharOffset() - startPos));
					return new JObject(loc.getSourceRef(), pos, entries);
				case FIELD_NAME:
					entries.add(parseField());
					continue;
				default:
					throw new JsonParseException("Field name expected", jp.getTokenLocation());
			}
		}
	}

	private JElement parseValue(JsonToken token) throws JsonParseException, IOException {
		JsonLocation loc = jp.getCurrentLocation();
		long startPos = loc.getCharOffset();
		int line = loc.getLineNr();
		switch(token) {
			case START_ARRAY:
				return parseArray();
			case START_OBJECT:
				return parseObject();
			case VALUE_FALSE:
			case VALUE_NULL:
			case VALUE_TRUE:
			case VALUE_NUMBER_FLOAT:
			case VALUE_NUMBER_INT:
			case VALUE_STRING:
				Object value = null;
				switch(token) {
					case VALUE_FALSE:
						value = Boolean.FALSE;
						break;
					case VALUE_TRUE:
						value = Boolean.TRUE;
						break;
					case VALUE_NUMBER_FLOAT:
						value = jp.getDoubleValue();
						break;
					case VALUE_NUMBER_INT:
						value = jp.getLongValue();
						break;
					case VALUE_STRING:
						value = jp.getText();
				}
				long endPos = jp.getCurrentLocation().getCharOffset();
				int[] pos = adjustPosition(line, (int) startPos, (int) (endPos - startPos));
				return new JPrimitive(loc.getSourceRef(), pos, value);
			default:
				throw new JsonParseException("Value expected", loc);
		}
	}
}
