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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

import com.google.common.base.Function;
import com.google.inject.Singleton;

/**
 * A FunctionFactory producing values that are dynamically produced when applying a style to
 * an {@link IDomNode}.
 * 
 */
@Singleton
public class FunctionFactory implements IFunctionFactory {

	private static class LiteralString implements Function<IDomNode, String> {

		private String value;

		public LiteralString(String value) {
			this.value = value;
		}

		@Override
		public String apply(IDomNode node) {
			return value;
		}
	}

	private static class LiteralStringSet implements Function<IDomNode, Set<String>> {

		private Set<String> value;

		public LiteralStringSet(Set<String> value) {
			this.value = value;
		}

		@Override
		public Set<String> apply(IDomNode node) {
			return value;
		}
	}

	public static class Not implements Function<IDomNode, Boolean> {

		Function<IDomNode, Boolean> function;

		public Not(Function<IDomNode, Boolean> function) {
			this.function = function;
		}

		@Override
		public Boolean apply(IDomNode node) {
			return !function.apply(node);
		}

	}

	private static final Function<IDomNode, String> textOfNodeFunc = new Function<IDomNode, String>() {

		@Override
		public String apply(IDomNode node) {
			return node.getText();
		}
	};

	private static final Pattern NON_WORD_CHAR_PATTERN = Pattern.compile("\\W+");

	private static final Function<IDomNode, Integer> firstNonWordFunc = new Function<IDomNode, Integer>() {

		@Override
		public Integer apply(IDomNode from) {
			Matcher m = NON_WORD_CHAR_PATTERN.matcher(from.getText());
			if(!m.find())
				return -1;
			return m.start();
		}

	};

	private static final Function<IDomNode, Integer> lastNonWordFunc = new Function<IDomNode, Integer>() {

		@Override
		public Integer apply(IDomNode from) {
			// reverses the string, and searches from the end
			// returns the index to the first char in the last location
			// i.e. xxx...yy returns 3

			String s = from.getText();
			String r = new StringBuilder(s).reverse().toString();
			Matcher m = NON_WORD_CHAR_PATTERN.matcher(s);
			if(!m.find())
				return -1;
			return s.length() - m.end();
		}

	};

	@Override
	public Function<IDomNode, Integer> firstNonWordChar() {
		return firstNonWordFunc;
	}

	@Override
	public Function<IDomNode, Integer> lastNonWordChar() {
		return lastNonWordFunc;
	}

	@Override
	public Function<IDomNode, String> literalString(String s) {
		return new LiteralString(s);
	}

	@Override
	public Function<IDomNode, Set<String>> literalStringSet(Set<String> set) {
		return new LiteralStringSet(set);
	}

	@Override
	public Function<IDomNode, Boolean> not(Function<IDomNode, Boolean> f) {
		return new Not(f);
	}

	@Override
	public Function<IDomNode, String> textOfNode() {
		return textOfNodeFunc;
	}
}
