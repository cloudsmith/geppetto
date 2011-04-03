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
package org.cloudsmith.geppetto.pp.dsl.validation;

import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper patterns for puppet
 * (hint: http://www.regexplanet.com/simple/index.html for interactive testing of java regex).
 * 
 */
// terminal NAME : ('0'..'9' | 'a'..'z') EXT_WORD_CHAR* ;
// terminal CLASS_NAME : (('a'..'z') EXT_WORD_CHAR*)? ('::' ('a'..'z') EXT_WORD_CHAR*)+;
// terminal CLASS_REF: ('::'? ('A'..'Z') EXT_WORD_CHAR*)+ ;
//
// terminal fragment EXT_WORD_CHAR : WORD_CHAR | '-' ;
// terminal fragment EXT_WORD_CHAR_NUM : EXT_WORD_CHAR | '-' | '.'; // to allow decimals 0.0E-2 as a name, hex is already ok 0x0aef
// terminal fragment WORD_CHAR : ('0'..'9')|('a'..'z')|('A'..'Z')|'_';

@Singleton
public class PPPatternHelper {

	protected final Pattern namePattern;

	protected final Pattern classRefPattern;

	protected final Pattern classNamePattern;

	protected final Pattern regexpPattern;

	protected final Pattern variablePattern;

	protected final Pattern sqStringPattern;

	protected final Pattern unrecognizedEscapes;

	protected final Pattern recognizedEscapes;

	/**
	 * Intended as Ruby %r{[\w-]} equivalence
	 */
	private static final String EXT_WORD_CHAR = "[0-9a-zA-Z_\\.-]";

	/**
	 * Intended as Ruby %r{[\w]} equivalence
	 */
	private static final String WORD_CHAR = "[0-9a-zA-Z_\\.]";

	@Inject
	public PPPatternHelper() {
		namePattern = Pattern.compile("[0-9a-z]" + EXT_WORD_CHAR + "*");
		classRefPattern = Pattern.compile("((::)?[A-Z]" + EXT_WORD_CHAR + "*)+");
		classNamePattern = Pattern.compile("([a-z]" + EXT_WORD_CHAR + "*)?(::[a-z]" + EXT_WORD_CHAR + "*)+");

		// regexp may not:
		// be empty - '//'
		// start with a * - '/*...'
		// contain newline character
		regexpPattern = Pattern.compile("/([^/\\n\\*\\\\]|(\\\\[^\\n]))([^/\\n\\\\]|(\\\\[^\\n]))*/[a-z]*");
		variablePattern = Pattern.compile("\\$(::)?(" + WORD_CHAR + "+::)*" + WORD_CHAR + "+");

		// sq string may not contain unescaped single quote
		sqStringPattern = Pattern.compile("([^'\\\\]|\\\\.)*");

		unrecognizedEscapes = Pattern.compile("\\\\[^stn '\"\\\\\\r\\n]");

		recognizedEscapes = Pattern.compile("\\\\[\\\\nrst'\" ]");
	}

	public Pattern getRecognizedEscapePattenr() {
		return recognizedEscapes;
	}

	/**
	 * Finds unrecognized escapes if recognized escapes have been removed from the input first.
	 * 
	 * @return
	 */
	public Pattern getUnrecognizedEscapesPattern() {
		return unrecognizedEscapes;
	}

	public boolean isCLASSNAME(String s) {
		if(s == null || s.length() == 0)
			return false;
		return classNamePattern.matcher(s).matches() && !s.contains(":::");
	}

	public boolean isCLASSREF(String s) {
		if(s == null || s.length() == 0)
			return false;
		return classRefPattern.matcher(s).matches() && !s.contains(":::");
	}

	public boolean isNAME(String s) {
		if(s == null || s.length() == 0)
			return false;
		return namePattern.matcher(s).matches() && !s.contains(":::");
	}

	public boolean isREGEXP(String s) {
		if(s == null || s.length() == 0)
			return false;
		return regexpPattern.matcher(s).matches();
	}

	/**
	 * Checks the content *between* ' ' (i.e. do not include the quotes in the given input).
	 * 
	 * @param s
	 * @return
	 */
	public boolean isSQSTRING(String s) {
		if(s == null || s.length() == 0)
			return true; // ok if empty
		return sqStringPattern.matcher(s).matches();
	}

	public boolean isVARIABLE(String s) {
		if(s == null || s.length() == 0)
			return false;
		return variablePattern.matcher(s).matches();
	}
}
