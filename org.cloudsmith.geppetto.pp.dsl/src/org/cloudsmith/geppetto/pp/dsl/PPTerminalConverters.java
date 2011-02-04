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

	// /**
	// * Checks if a name is a Java Identifier and thus comply with a QID
	// *
	// * @param value
	// * @return
	// */
	// private static boolean needsQuoting(String value) {
	// if(value.length() < 1)
	// return true;
	// if(!Character.isJavaIdentifierStart(value.charAt(0)))
	// return true;
	// for(int i = 1; i < value.length(); i++)
	// if(Character.isJavaIdentifierPart(value.charAt(i)))
	// return true;
	// return false;
	// }

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

	// // Currently unused
	// @ValueConverter(rule = "IntValue")
	// public IValueConverter<Integer> IntValue() {
	// return new IValueConverter<Integer>() {
	//
	// public String toString(Integer value) {
	// return value.toString();
	// }
	//
	// public Integer toValue(String string, AbstractNode node) throws ValueConverterException {
	// int radix = 10;
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException("Can not convert empty string to int", node, null);
	// try {
	// if(string.startsWith("0x") || string.startsWith("0X")) {
	// radix = 16;
	// string = string.substring(2);
	// }
	// else if(string.startsWith("0") && string.length() > 1)
	// radix = 8;
	//
	// return new Integer(Integer.valueOf(string, radix));
	// }
	// catch(NumberFormatException e) {
	// String format = "";
	// switch(radix) {
	// case 8:
	// format = "octal";
	// break;
	// case 10:
	// format = "decimal";
	// break;
	// case 16:
	// format = "hexadecimal";
	// break;
	// }
	// throw new ValueConverterException(
	// "Can not convert to " + format + " integer : " + string, node, null);
	// }
	// }
	//
	// };
	// }
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

	// Uses Java Pattern to parse and handle regex errors, the ruby regex pattern is different - could still
	// validate the flags - later also validate the syntax...

	// @ValueConverter(rule = "REGULAR_EXPR")
	// public IValueConverter<Pattern> Pattern() {
	// return new IValueConverter<Pattern>() {
	//
	// public String toString(Pattern value) {
	// StringBuffer buffer = new StringBuffer();
	// buffer.append("/");
	// buffer.append(value.toString());
	// buffer.append("/");
	// int flags = value.flags();
	// if((flags & Pattern.CANON_EQ) != 0)
	// buffer.append('c');
	// if((flags & Pattern.DOTALL) != 0)
	// buffer.append('d');
	// if((flags & Pattern.CASE_INSENSITIVE) != 0)
	// buffer.append('i');
	// if((flags & Pattern.MULTILINE) != 0)
	// buffer.append('m');
	// if((flags & Pattern.UNICODE_CASE) != 0)
	// buffer.append('u');
	// return buffer.toString();
	// }
	//
	// public Pattern toValue(String string, AbstractNode node) {
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException(
	// "Could not convert empty string to regular expression", node, null);
	// int firstSlash = string.indexOf('/');
	// int lastSlash = string.lastIndexOf('/');
	// if(lastSlash - firstSlash <= 0)
	// throw new ValueConverterException("The regular expression is empty", node, null);
	// String patternString = string.substring(firstSlash + 1, lastSlash);
	// String flagString = string.substring(lastSlash + 1);
	// int flags = 0;
	// int counts[] = new int[5];
	// for(int i = 0; i < flagString.length(); i++)
	// switch(flagString.charAt(i)) {
	// case 'i':
	// counts[0]++;
	// flags |= Pattern.CASE_INSENSITIVE;
	// break;
	// case 'm':
	// counts[1]++;
	// flags |= Pattern.MULTILINE;
	// break;
	// case 'u':
	// counts[2]++;
	// flags |= Pattern.UNICODE_CASE;
	// break;
	// case 'c':
	// counts[3]++;
	// flags |= Pattern.CANON_EQ;
	// break;
	// case 'd':
	// counts[4]++;
	// flags |= Pattern.DOTALL;
	// break;
	// default:
	// throw new ValueConverterException(
	// "Flag character after /: expected one of i, m, u, c, d, but got: '" +
	// flagString.charAt(i) + "'.", node, null);
	// }
	// for(int i = 0; i < counts.length; i++)
	// if(counts[i] > 1)
	// throw new ValueConverterException("Flag character after /: used multiple times.", node, null);
	// try {
	// return Pattern.compile(patternString, flags);
	// }
	// catch(PatternSyntaxException e) {
	// throw new ValueConverterException(
	// "Could not convert '" + string + "' to regular expression", node, e);
	// }
	// catch(IllegalArgumentException e) {
	// throw new ValueConverterException(
	// "Internal error translating pattern flags - please log bug report", node, e);
	// }
	// }
	//
	// };
	// }

	// Will perhaps be useful later
	//
	// @ValueConverter(rule = "RadixIntValue")
	// public IValueConverter<IntegerWithRadix> RadixIntValue() {
	// return new IValueConverter<IntegerWithRadix>() {
	//
	// public String toString(IntegerWithRadix value) {
	// return value.toString();
	// }
	//
	// public IntegerWithRadix toValue(String string, AbstractNode node) throws ValueConverterException {
	// int radix = 10;
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException("Can not convert empty string to int", node, null);
	// try {
	// if(string.startsWith("0x") || string.startsWith("0X")) {
	// radix = 16;
	// string = string.substring(2);
	// }
	// else if(string.startsWith("0") && string.length() > 1)
	// radix = 8;
	//
	// return new IntegerWithRadix(Integer.valueOf(string, radix), radix);
	// }
	// catch(NumberFormatException e) {
	// String format = "";
	// switch(radix) {
	// case 8:
	// format = "octal";
	// break;
	// case 10:
	// format = "decimal";
	// break;
	// case 16:
	// format = "hexadecimal";
	// break;
	// }
	// throw new ValueConverterException(
	// "Can not convert to " + format + " integer : " + string, node, null);
	// }
	// }
	//
	// };
	// }

	// Will perhaps be useful later
	// @ValueConverter(rule = "RealValue")
	// public IValueConverter<Double> RealValue() {
	// return new IValueConverter<Double>() {
	//
	// public String toString(Double value) {
	// return value.toString();
	// }
	//
	// public Double toValue(String string, AbstractNode node) {
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException("Could not convert empty string to double", node, null);
	// try {
	// return new Double(string);
	// }
	// catch(NumberFormatException e) {
	// throw new ValueConverterException("Could not convert '" + string + "' to double", node, null);
	// }
	// }
	//
	// };
	// }

	@ValueConverter(rule = "DQT_LEADING")
	public IValueConverter<String> DQ_LEADING() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToString(String value) {
				return "\"" + value;
			}

			@Override
			protected String internalToValue(String string, INode node) {
				return string.substring(1, string.length());
			}
		};
	}

	@ValueConverter(rule = "DQT_TRAILING")
	public IValueConverter<String> DQ_TRAILING() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToString(String value) {
				return value + "\"";
			}

			@Override
			protected String internalToValue(String string, INode node) {
				return string.substring(0, string.length() - 1);
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

	/**
	 * A VERBATIM (null) converter, just to ensure that there is a dataconverter (makes it shielded from
	 * the possibly eager formatter).
	 * 
	 * @return
	 */
	@ValueConverter(rule = "DQT_MID_AFTER_EXPR")
	public IValueConverter<String> VERBATIM() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToString(String value) {
				return value;
			}

			@Override
			protected String internalToValue(String string, INode node) {
				return string;
			}
		};
	}

	@ValueConverter(rule = "DQT_MID_AFTER_VAR")
	public IValueConverter<String> VERBATIM2() {
		return VERBATIM();
	}
	// Currently unused converts between Java string and string as used in language (i.e with quotes)
	// (This version does not preserve single quoted string, intead quotes all occurences and always results
	// in double quoted string.
	// Note that the convertToJavaString can not be used as a ruby/puppet string has additional escapes
	// for Control and Meta-Control (and more).

	// @ValueConverter(rule = "STRING")
	// public IValueConverter<String> STRING() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// return '"' + Strings.convertToJavaString(value) + '"';
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// // System.err.print("STRING: Converting from: (" + string + ")=>(" +
	// // string.substring(1, string.length() - 1) + ")\n");
	// return Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
	// }
	// };
	// }

	// // May be useful later, don't know if there is some template/ruby escape support that needs to be handled
	// //
	// @ValueConverter(rule = "TEXTEND")
	// public IValueConverter<String> TEXTEND() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// return "}»" + value + "»";
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// if(string.startsWith("}»"))
	// string = string.substring(2);
	// if(string.endsWith("»"))
	// string = string.substring(0, string.length() - 1);
	// return string;
	// }
	// };
	// }
	//
	// @ValueConverter(rule = "TEXTMID")
	// public IValueConverter<String> TEXTMID() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// return "}»" + value + "«{";
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// if(string.startsWith("}»"))
	// string = string.substring(2);
	// if(string.endsWith("«{"))
	// string = string.substring(0, string.length() - 2);
	// return string;
	// }
	// };
	// }
	//
	// @ValueConverter(rule = "TEXTSTART")
	// public IValueConverter<String> TEXTSTART() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// return "«" + value + "«{";
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// if(string.startsWith("«"))
	// string = string.substring(1);
	// if(string.endsWith("«{"))
	// string = string.substring(0, string.length() - 2);
	// return string;
	// }
	// };
	// }
	//
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
	// @ValueConverter(rule = "TextVerbatimValue")
	// public IValueConverter<String> TextVerbatimValue() {
	// return new AbstractNullSafeConverter<String>() {
	// @Override
	// protected String internalToString(String value) {
	// return "«" + value + "";
	// }
	//
	// @Override
	// protected String internalToValue(String string, AbstractNode node) {
	// if(string.startsWith("«"))
	// string = string.substring(1);
	// if(string.endsWith("›"))
	// string = string.substring(0, string.length() - 1);
	// return string;
	// }
	// };
	// }

	// Versions in puppet are just strings
	//
	// @ValueConverter(rule = "VersionLiteral")
	// public IValueConverter<Version> Version() {
	// return new IValueConverter<Version>() {
	//
	// public String toString(Version value) {
	// return versionFormatManager.toString(value);
	// // return value.toString();
	// }
	//
	// public Version toValue(String string, AbstractNode node) throws ValueConverterException {
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException("Can not convert empty string to Version", node, null);
	// try {
	// char c = string.charAt(0);
	// if(c == '"' || c == '\"')
	// string = Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
	//
	// // INJECT IVersionFormatManager and create Version
	// return versionFormatManager.toVersionValue(string);
	// // return Version.create(string);
	// }
	// catch(IllegalArgumentException e) {
	// throw new ValueConverterException("Version '" + string + "' is not a valid version: " +
	// e.getMessage(), node, null);
	// }
	// }
	//
	// };
	// }
	//
	// @ValueConverter(rule = "VersionRangeLiteral")
	// public IValueConverter<VersionRange> VersionRange() {
	// return new IValueConverter<VersionRange>() {
	//
	// public String toString(VersionRange value) {
	// return versionFormatManager.toString(value);
	// }
	//
	// public VersionRange toValue(String string, AbstractNode node) throws ValueConverterException {
	// if(Strings.isEmpty(string))
	// throw new ValueConverterException("Can not convert empty string to VersionRange", node, null);
	// try {
	// char c = string.charAt(0);
	// if(c == '"' || c == '\"')
	// string = Strings.convertFromJavaString(string.substring(1, string.length() - 1), true);
	//
	// return versionFormatManager.toVersionRangeValue(string);
	// }
	// catch(IllegalArgumentException e) {
	// throw new ValueConverterException("VersionRange '" + string + "' is not a valid range: " +
	// e.getMessage(), node, null);
	// }
	// }
	//
	// };
	// }
}
