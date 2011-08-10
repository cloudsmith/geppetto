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
package org.cloudsmith.geppetto.pp.dsl;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * Converters for BeeLang terminals.
 */
public class PPTerminalConverters extends AbstractDeclarativeValueConverterService {

	@ValueConverter(rule = "ATBoolean")
	public IValueConverter<Boolean> ATBoolean() {

		/**
		 * Converts "@" to true and
		 */
		return new IValueConverter<Boolean>() {

			public String toString(Boolean value) {
				return value.booleanValue()
						? "@"
						: "";
			}

			public Boolean toValue(String string, INode node) {
				if(Strings.isEmpty(string))
					return false;
				return true;
			}

		};

	}

	@ValueConverter(rule = "BooleanValue")
	public IValueConverter<Boolean> BooleanValue() {
		return new IValueConverter<Boolean>() {

			public String toString(Boolean value) {
				return value.toString();
			}

			public Boolean toValue(String string, INode node) {
				if(Strings.isEmpty(string))
					throw new ValueConverterException("Could not convert empty string to boolean", node, null);
				return new Boolean(string).equals(Boolean.TRUE)
						? Boolean.TRUE
						: Boolean.FALSE;
			}

		};
	}

	/**
	 * Hack to see what happens with formatting.
	 * 
	 * @return
	 */
	@ValueConverter(rule = "SpecialDoubleQuote")
	public IValueConverter<String> specialDoubleQuote() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToString(String value) {
				return "\"";
			}

			@Override
			protected String internalToValue(String string, INode node) {
				return "";
			}
		};
	}

	@ValueConverter(rule = "sqText")
	public IValueConverter<String> sqText() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToString(String value) {
				return "'" + value + "'";
			}

			@Override
			protected String internalToValue(String string, INode node) {
				return string.substring(1, string.length() - 1);
			}
		};
	}

	// /**
	// * Converts a TEXT to a trimmed string with Java String semantics.
	// * (i.e. support for \\t and other escapes).
	// *
	// * @return
	// */
	// @ValueConverter(rule = "TextStringValue")
	// public IValueConverter<String> TextStringValue() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// String converted = Strings.convertToJavaString(value, true);
	// converted = converted.substring(1, converted.length() - 2);
	// // any » must be converted to \»
	// converted.replace("»", "\\»");
	// return "«" + converted + "»";
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// if(string.startsWith("«"))
	// string = string.substring(1);
	// if(string.endsWith("»"))
	// string = string.substring(0, string.length() - 1);
	// String lines[] = string.split("[\n\r]");
	// StringBuffer buf = new StringBuffer();
	// for(int i = 0; i < lines.length; i++) {
	// if(i != 0)
	// buf.append("\n");
	// buf.append(lines[i].trim());
	// }
	// return Strings.convertFromJavaString(buf.toString(), true);
	// }
	// };
	// }
	//
}
