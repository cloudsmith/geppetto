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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

import com.google.common.base.Function;
import com.google.inject.Singleton;

/**
 * A FunctionFactory producing values that are dynamically produced when applying a style to
 * a graph element.
 * 
 */
@Singleton
public class FunctionFactory implements IFunctionFactory {

	private static class LiteralString implements Function<IDomNode, String> {

		private String value;

		public LiteralString(String value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public String apply(IDomNode ge) {
			return value;
		}
	}

	private static class LiteralStringSet implements Function<IDomNode, Set<String>> {

		private Set<String> value;

		public LiteralStringSet(Set<String> value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Set<String> apply(IDomNode ge) {
			return value;
		}
	}

	public static class Not implements Function<IDomNode, Boolean> {

		Function<IDomNode, Boolean> function;

		public Not(Function<IDomNode, Boolean> function) {
			this.function = function;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Boolean apply(IDomNode ge) {
			return !function.apply(ge);
		}

	}

	private static final Function<IDomNode, String> textOfNodeFunc = new Function<IDomNode, String>() {

		@Override
		public String apply(IDomNode from) {
			return from.getText();
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.graph.graphcss.IFunctionFactory#literalString(java.lang.String)
	 */
	@Override
	public Function<IDomNode, String> literalString(String s) {
		return new LiteralString(s);
	}

	@Override
	public Function<IDomNode, String> textOfNode() {
		return textOfNodeFunc;
	}
}
