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
package org.cloudsmith.geppetto.forge.v2.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * A qualified name that is case insensitive and also separator insensitive when performing comparisons
 * and hash code calculations. The instance does however preserve both case and the separator. The
 * created instance is immutable and suitable for use as key in hash tables and trees.
 */
public class ModuleName implements Serializable, Comparable<ModuleName> {
	public static class BadNameCharactersException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		private static final String STRICT_MSG = "name should only contain lowercase letters, numbers, and underscores, and should begin with a letter";

		private static final String LENIENT_MSG = "name should only contain letters, numbers, and underscores, and should begin with a letter";

		public BadNameCharactersException(boolean strict) {
			super(strict
					? STRICT_MSG
					: LENIENT_MSG);
		}
	}

	public static class BadNameSyntaxException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		private static final String MSG = "name should be in the form <owner>-<name> or <owner>/<name>";

		public BadNameSyntaxException() {
			super(MSG);
		}
	}

	public static class BadOwnerCharactersException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		private static final String STRICT_MSG = "owner should only contain letters and numbers, and should begin with a letter";

		private static final String LENIENT_MSG = "owner should only contain letters and numbers";

		public BadOwnerCharactersException(boolean strict) {
			super(strict
					? STRICT_MSG
					: LENIENT_MSG);
		}
	}

	public static class JsonAdapter implements JsonDeserializer<ModuleName>, JsonSerializer<ModuleName> {
		@Override
		public ModuleName deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			String name = json.getAsString();
			String owner = null;
			int sepIdx = name.indexOf('/');
			if(sepIdx < 0)
				sepIdx = name.indexOf('-');

			if(sepIdx >= 0) {
				owner = ModuleName.safeOwner(name.substring(0, sepIdx));
				name = ModuleName.safeName(name.substring(sepIdx + 1), false);
			}
			else {
				name = ModuleName.safeName(name, false);
			}
			return new ModuleName(owner, name, false);
		}

		@Override
		public JsonElement serialize(ModuleName src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	private static final long serialVersionUID = 1L;

	private static final String NO_VALUE = "";

	private static final Pattern OWNER_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

	private static final Pattern STRICT_OWNER_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");

	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]*$");

	/**
	 * <p>
	 * Checks that the given name only contains lowercase letters, numbers and underscores and that it begins with a
	 * letter. This check is valid for the name part of a full module name.
	 * </p>
	 * Certain module names are disallowed:
	 * <ul>
	 * <li>main</li>
	 * <li>settings</li>
	 * </ul>
	 * 
	 * @param name
	 *            The name to check
	 * @param strict
	 *            <code>true</code> means do not allow uppercase letters or dash
	 * @return The checked name
	 * @throws BadNameCharactersException
	 *             if the contains illegal characters
	 */
	public static String checkName(String name, boolean strict) throws BadNameCharactersException {
		Pattern p = strict
				? STRICT_NAME_PATTERN
				: NAME_PATTERN;
		Matcher m = p.matcher(name);
		if(m.matches()) {
			if(name.equals("main") || name.equals("settings"))
				throw new BadNameCharactersException(strict);
			return name;
		}
		throw new BadNameCharactersException(strict);
	}

	/**
	 * Checks that the given name only contains letters and numbers. This is suitable for the <i>owner</i> part of a
	 * full module name.
	 * 
	 * @param owner
	 *            The name to check
	 * @param strict
	 *            <code>true</code> means that name must start with a letter
	 * @return The checked owner
	 * @throws BadOwnerCharactersException
	 *             if the contains illegal characters
	 */
	public static String checkOwner(String owner, boolean strict) throws BadOwnerCharactersException {
		Pattern p = strict
				? STRICT_OWNER_PATTERN
				: OWNER_PATTERN;
		Matcher m = p.matcher(owner);
		if(m.matches())
			return owner;
		throw new BadOwnerCharactersException(strict);
	}

	private static final StringBuilder createBuilder(String from, int idx) {
		StringBuilder bld = new StringBuilder(from.length());
		for(int catchUp = 0; catchUp < idx; catchUp++)
			bld.append(from.charAt(catchUp));
		return bld;
	}

	/**
	 * Creates a &quot;safe&quot; name from the given name. The following
	 * happens:
	 * <ul>
	 * <li>If <code>strict</code> is <code>true</code>, then all uppercase characters in the range 'A' - 'Z' are
	 * lowercased</li>
	 * <li>All characters that are not underscore, digit, or in the range 'a' - 'z' is replaced with an underscore</li>
	 * <li>If an underscore or digit is found at the first position (after replacement), then it is replaced by the
	 * letter 'z'</li>
	 * </ul>
	 * 
	 * @param name
	 *            The name to convert. Can be <code>null</code> in which case <code>null</code>it is returned.
	 * @param strict
	 *            <code>true</code> means do not allow uppercase letters or multiple separators
	 * @return The safe name or <code>null</code>.
	 */
	public static String safeName(String name, boolean strict) {
		if(name == null)
			return name;

		int top = name.length();
		StringBuilder bld = null;
		for(int idx = 0; idx < top; ++idx) {
			char c = name.charAt(idx);
			// @fmtOff
			if(!(   c >= 'a' && c <= 'z'
			     || !strict && (c == '-' || c >= 'A' && c <= 'Z')
			     || idx > 0 && (c == '_' || c >= '0' && c <= '9'))) {
			// @fmtOn
				if(c >= 'A' && c <= 'Z')
					c += 0x20;
				else
					c = idx == 0
							? 'z'
							: '_';
				if(bld == null)
					bld = createBuilder(name, idx);
			}
			if(bld != null)
				bld.append(c);
		}
		return bld == null
				? name
				: bld.toString();
	}

	/**
	 * Creates a &quot;safe&quot; owner name from the given name. All characters that are a digit or a letter is
	 * replaced with a 'z'.
	 * 
	 * @param owner
	 *            The name to convert. Can be <code>null</code> in which case <code>null</code>it is returned.
	 * @return The safe name or <code>null</code>.
	 */
	public static String safeOwner(String owner) {
		if(owner == null)
			return owner;

		int top = owner.length();
		StringBuilder bld = null;
		for(int idx = 0; idx < top; ++idx) {
			char c = owner.charAt(idx);
			if(!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')) {
				c = 'z';
				if(bld == null)
					bld = createBuilder(owner, idx);
			}
			if(bld != null)
				bld.append(c);
		}
		return bld == null
				? owner
				: bld.toString();
	}

	/**
	 * <p>
	 * Splits the <code>moduleName into two parts. The owner and the name. This method performs no validation
	 * of the names.
	 * </p>
	 * <p>
	 * The separator may be either '/' or '-' and if more than one separator is present, then one placed first wins.
	 * Thus<br/>
	 * &quot;foo-bar-baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar-baz&quot;, separator '-'<br/>
	 * &quot;foo/bar-baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar-baz&quot;, separator '/'<br/>
	 * &quot;foo/bar/baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar/baz&quot;, separator '/'<br/>
	 * &quot;foo-bar/baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar/baz&quot;, separator '-'<br/>
	 * </p>
	 * In case no separator is found, owner will be considered missing and the argument is returned as the
	 * second element.</p>
	 * 
	 * @param moduleName
	 * @return A two element array with the owner and name of the module. The first element in this array may be
	 *         <code>null</code> .
	 * @see #checkOwner(String)
	 * @see #checkName(String, boolean)
	 */
	public static String[] splitName(String moduleName) {
		String owner = null;
		String name;
		int sepIdx = moduleName.indexOf('/');
		if(sepIdx < 0)
			sepIdx = moduleName.indexOf('-');

		if(sepIdx >= 0) {
			owner = moduleName.substring(0, sepIdx);
			name = moduleName.substring(sepIdx + 1);
		}
		else
			name = moduleName;
		return new String[] { owner, name };
	}

	private final char separator;

	private final String owner;

	private final String name;

	private final String semanticName;

	private static final Pattern STRICT_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*$");

	private ModuleName(ModuleName m, char separator) {
		this.separator = separator;
		this.owner = m.owner;
		this.name = m.name;
		this.semanticName = m.semanticName;
	}

	/**
	 * Creates a name from a string with a separator. This is a equivalent to {@link #ModuleName(String, boolean)
	 * ModuleName(fullName, false)}
	 * 
	 * @param fullName
	 *            The name to set
	 * @throws BadOwnerCharactersException
	 * @throws BadNameCharactersException
	 * @throws BadNameSyntaxException
	 */
	public ModuleName(String fullName) throws BadNameSyntaxException, BadNameCharactersException,
			BadOwnerCharactersException {
		this(fullName, false);
	}

	/**
	 * <p>
	 * Creates a name from a string with a separator.
	 * </p>
	 * <p>
	 * The separator may be either '/' or '-' and if more than one separator is present, then one placed first wins.
	 * Thus<br/>
	 * &quot;foo-bar-baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar-baz&quot;, separator '-'<br/>
	 * &quot;foo/bar-baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar-baz&quot;, separator '/'<br/>
	 * &quot;foo/bar/baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar/baz&quot;, separator '/'<br/>
	 * &quot;foo-bar/baz&quot; yields owner = &quot;foo&quot;, name = &quot;bar/baz&quot;, separator '-'<br/>
	 * </p>
	 * 
	 * @param fullName
	 *            The name to set
	 * @param strict
	 *            <code>true</code> means do not allow <code>owner</code> that starts with a digit or uppercase letters
	 *            or dash in the <code>name</code>
	 * @throws BadNameCharactersException
	 * @throws BadOwnerCharactersException
	 * @throws BadNameSyntaxException
	 */
	public ModuleName(String fullName, boolean strict) throws BadNameSyntaxException, BadNameCharactersException,
			BadOwnerCharactersException {
		int idx = fullName.indexOf('/');
		if(idx > 0)
			separator = '/';
		else {
			idx = fullName.indexOf('-');
			separator = '-';
		}

		if(!(idx > 0 && idx < fullName.length() - 1))
			throw new BadNameSyntaxException();

		this.owner = checkOwner(fullName.substring(0, idx), strict);
		this.name = checkName(fullName.substring(idx + 1), strict);

		String semName = createSemanticName();
		if(semName.equals(fullName))
			semName = fullName; // Don't waste string instance here. This will be the common case
		this.semanticName = semName;
	}

	/**
	 * Creates a name using specified owner, name, and separator.
	 * 
	 * @param owner
	 * @param separator
	 * @param name
	 * @param strict
	 *            <code>true</code> means do not allow <code>owner</code> that starts with a digit or uppercase letters
	 *            or dash in the <code>name</code>
	 * @throws BadNameCharactersException
	 * @throws BadOwnerCharactersException
	 * @throws BadNameSyntaxException
	 */
	public ModuleName(String owner, char separator, String name, boolean strict) throws BadNameSyntaxException,
			BadOwnerCharactersException, BadNameCharactersException {
		this.owner = owner == null
				? NO_VALUE
				: checkOwner(owner, strict);

		if(!(separator == '-' || separator == '/'))
			throw new BadNameSyntaxException();

		this.separator = separator;
		this.name = name == null
				? NO_VALUE
				: checkName(name, strict);
		this.semanticName = createSemanticName();
	}

	public ModuleName(String qualifier, String name, boolean strict) {
		this(qualifier, '/', name, strict);
	}

	/**
	 * <p>
	 * Compare this name to <tt>other</tt> for lexical magnitude using case insensitive comparisons. The separator is
	 * considered but only after both owner and names are equal.
	 * </p>
	 * 
	 * @param other
	 *            The name to compare this name to.
	 * @return a positive integer to indicate that this name is lexicographically greater than <tt>other</tt>.
	 */
	@Override
	public int compareTo(ModuleName other) {
		int cmp = semanticName.compareTo(other.semanticName);
		if(cmp == 0)
			cmp = separator - other.separator;
		return cmp;
	}

	private String createSemanticName() {
		return owner.toLowerCase() + '/' + name.toLowerCase();
	}

	/**
	 * Compares the two names for equality. Names can have different separators or different case and still be equal.
	 * 
	 * @return The result of the comparison.
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof ModuleName))
			return false;
		ModuleName qo = (ModuleName) o;
		return semanticName.equals(qo.semanticName);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return the separator
	 */
	public char getSeparator() {
		return separator;
	}

	/**
	 * Computes the hash value for this qualified name. The separator is excluded from the computation
	 * 
	 * @return The computed hash code.
	 */
	@Override
	public int hashCode() {
		return semanticName.hashCode();
	}

	/**
	 * Returns the string representation fo this instance.
	 */
	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld);
		return bld.toString();
	}

	/**
	 * Present this object as a string onto the given builder.
	 * 
	 * @param builder
	 */
	public void toString(StringBuilder builder) {
		builder.append(owner);
		builder.append(separator);
		builder.append(name);
	}

	/**
	 * Returns an instance that is guaranteed to have the given
	 * separator. The returned instance might be this instance or
	 * a new instance depending on if this instance already has the
	 * given separator.
	 * 
	 * @param separator
	 * @return A name with the given separator, possibly this instance
	 */
	public ModuleName withSeparator(char separator) {
		return this.separator == separator
				? this
				: new ModuleName(this, separator);
	}
}
