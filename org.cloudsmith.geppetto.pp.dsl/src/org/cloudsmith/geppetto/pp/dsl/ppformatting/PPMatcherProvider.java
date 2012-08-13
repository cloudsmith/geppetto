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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.util.Collection;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.formatting.impl.ElementMatcherProvider;
import org.eclipse.xtext.formatting.impl.MatcherNFAProvider;
import org.eclipse.xtext.formatting.impl.MatcherState;

/**
 * Unused, but useful as a replacement when debugging (change to public and include in module).
 * 
 */
class PPMatcherProvider extends ElementMatcherProvider {

	protected static class PPTransitionMatcher<T extends IElementPattern> extends TransitionMatcher<T> implements
			IElementMatcher<T> {
		public PPTransitionMatcher(IGrammarAccess grammar, MatcherNFAProvider nfaProvider, Iterable<T> patterns) {
			super(grammar, nfaProvider, patterns);
		}

		@Override
		public Collection<T> matchNext(AbstractElement nextElement) {
			// System.err.println("matchNext: "+ str(nextElement));
			return super.matchNext(nextElement);
		}

		//
		// @Override
		// protected Pair<List<MatcherTransition>, List<MatcherState>> findTransitionPath(MatcherState from,
		// AbstractElement to, boolean returning, boolean canReturn) {
		// // System.err.println("findTransitionPath from: "+str(from) + " to: "+str(to) + " returning: "+returning + " canReturn: "+canReturn);
		// return super.findTransitionPath(from, to, returning, canReturn);
		// }

		@Override
		protected Collection<T> patternsForTwoStates(MatcherState first, MatcherState second) {
			// System.err.println("patternsForTwoStates first: "+ str(first) + " second: "+ str(second));
			return super.patternsForTwoStates(first, second);
		}
	}

	protected static String str(Object o) {
		if(o instanceof RuleCall)
			return ((RuleCall) o).getRule().getName();
		if(o == null)
			return "null";
		return o.toString();
	}

	@Override
	public <T extends IElementPattern> IElementMatcher<T> createMatcher(Iterable<T> rules) {
		return new PPTransitionMatcher<T>(grammar, nfaProvider, rules);
	}
}
