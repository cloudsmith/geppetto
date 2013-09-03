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
package org.cloudsmith.geppetto.common.util;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author filip.hrbek@gmail.com
 * 
 */
public class GeneratorUtil {

	protected static final Pattern CHARACTERS_TO_ESCAPE_IN_RUBY = Pattern.compile("\\'");

	public static void emitRubyStringLiteral(Appendable builder, String string) throws IOException {
		builder.append('\'').append(getRubyEscapedString(string)).append('\'');
	}

	public static void emitRubyStringLiteral(StringBuilder builder, String string) {
		try {
			emitRubyStringLiteral((Appendable) builder, string);
		}
		catch(IOException e) {
			// this should never happen - StringBuilder's append does not throw an exception
			throw new RuntimeException(e);
		}
	}

	protected static String getRubyEscapedString(String string) {
		return CHARACTERS_TO_ESCAPE_IN_RUBY.matcher(string).replaceAll("\\\\$0");
	}

	public static String getRubyStringLiteral(String string) {
		return '\'' + getRubyEscapedString(string) + '\'';
	}

}
