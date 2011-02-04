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
package org.cloudsmith.geppetto.pp.dsl.serialization;

import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parsetree.reconstr.impl.AbstractHiddenTokenHelper;

import com.google.inject.Inject;

/**
 * Contains workarounds for hidden/whitespace and comments.
 * TODO: further cleanup of various commented experiments
 * TODO: Not used in Xtext 2.0 - can be removed...
 * 
 */
class PPHiddenTokenHelper extends AbstractHiddenTokenHelper {
	private AbstractRule wsRule;

	@Override
	public AbstractRule getWhitespaceRuleFor(ParserRule context, String whitespace) {
		if(context == null || !context.isDefinesHiddenTokens())
			return wsRule;
		if(context.getHiddenTokens().contains(wsRule))
			return wsRule;
		return null;
	}

	@SuppressWarnings("deprecation")
	public AbstractRule getWhitespaceRuleFor(String whitespace) {
		return wsRule;
	}

	public boolean isComment(AbstractRule rule) {
		return rule != null && ("ML_COMMENT".equals(rule.getName()) || "SL_COMMENT".equals(rule.getName()));
	}

	public boolean isWhitespace(AbstractRule rule) {
		return rule != null && "WS".equals(rule.getName());
	}

	@Inject
	@SuppressWarnings("unused")
	private void setGrammar(IGrammarAccess grammar) {
		wsRule = GrammarUtil.findRuleForName(grammar.getGrammar(), "WS");
	}

	// private AbstractRule owsRule;
	//
	// @Override
	// public AbstractRule getWhitespaceRuleFor(ParserRule context, String whitespace) {
	// if(context != null &&
	// // (context.getName().equals("WSR")))
	// (/* context.getName().equals("OWS") || */context.getName().equals("WSR"))) {
	// System.err.println("Request for getWhitespaceRule(ParserRule, String) with WSR rule returns null");
	// return null;
	// }
	// // if(whitespace.equals("\n"))
	// // return nlRule;
	// // if(whitespace.equals(" "))
	// // return spRule;
	// if(context != null) {
	// if(context.getName().equals("endComma"))
	// System.err.println("Endcomma rule queried for OWS");
	// }
	// return owsRule; // WORKS WHEN SET TO: owsRule;
	// // if (context == null || !context.isDefinesHiddenTokens())
	// // return wsRule;
	// // if (context.getHiddenTokens().contains(wsRule))
	// // return wsRule;
	// // return null;
	// }
	//
	// public AbstractRule getWhitespaceRuleFor(String whitespace) {
	// System.err.println("Request for getWhitespaceRule(String whitespace) returns null");
	// return null;
	// // return wsRule;
	// // return owsRule;
	// }
	//
	// public boolean isComment(AbstractRule rule) {
	// return rule != null && ("ML_COMMENT".equals(rule.getName()) || "SL_COMMENT".equals(rule.getName()));
	// }
	//
	// public boolean isWhitespace(AbstractRule rule) {
	// if(rule == null)
	// return false;
	// String n = rule.getName();
	// if("OWS".equals(n) || "endComma".equals(n) || "WSR".equals(n) || "SP".equals(n) || "NL".equals(n) ||
	// "CR".equals(n) || "TAB".equals(n))
	// return true;
	// return false;
	// // return rule != null && ("WSR".equals(rule.getName()));// || "OWS".equals(rule.getName()));
	// }
	//
	// @Inject
	// @SuppressWarnings("unused")
	// private void setGrammar(IGrammarAccess grammar) {
	// // owsRule = GrammarUtil.findRuleForName(grammar.getGrammar(), "OWS");
	// }

}
