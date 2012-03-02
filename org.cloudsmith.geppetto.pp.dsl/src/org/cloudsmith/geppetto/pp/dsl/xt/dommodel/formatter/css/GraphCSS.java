/**
 * Copyright (c) 2006-2012 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

/**
 * A Graph CSS consists of a set of {@link Rule} describing the styling of an {@link IGraph}.
 * 
 */
public class GraphCSS {
	ArrayList<Rule> cssRules;

	/**
	 * Comparator that compares specificity of two rules, and if specificity is equal, the rule with
	 * lower index is considered to be 'before'.
	 */
	public static final Comparator<Rule> RULE_COMPARATOR = new Comparator<Rule>() {
		public int compare(Rule r1, Rule r2) {
			int r1s = r1.getSpecificity();
			int r2s = r2.getSpecificity();
			if(r1s < r2s)
				return -1;
			if(r1s > r2s)
				return 1;

			// if they are equal - they should be ordered on their index in the ruleset
			// the one with the lower index
			r1s = r1.getGraphCSS().indexOf(r1);
			r2s = r2.getGraphCSS().indexOf(r2);
			if(r1s < r2s)
				return -1;
			if(r1s > r2s)
				return 1;
			throw new IllegalStateException("Comparator MUST order rules");
		}
	};

	public GraphCSS() {
		cssRules = new ArrayList<Rule>();
	}

	/**
	 * Adds all rules from another ruleset.
	 * 
	 * @param ruleSet
	 */
	public void addAll(GraphCSS ruleSet) {
		// can't just add them using collection routines as the parent ruleSet must be set,
		// and rule cloned.
		//
		for(Rule r : ruleSet.cssRules)
			addRule(r);
	}

	public void addAll(Iterable<Rule> rules) {
		for(Rule r : rules)
			addRule(r);
	}

	/**
	 * Adds a rule to this rule set. If the rule is already in another ruleset, the rule is cloned.
	 * The added rule ruleSet property is set to this ruleSet.
	 * 
	 * @param rule
	 */
	public void addRule(Rule rule) {
		if(rule == Rule.NULL_RULE)
			return;

		rule = rule.getGraphCSS() != null
				? (Rule) rule.clone()
				: rule;
		rule.setGraphCSS(this);
		cssRules.add(rule);
	}

	/**
	 * @see #addRule(Rule)
	 * @param rule
	 * @param rules
	 */
	public void addRules(Rule rule, Rule... rules) {
		addRule(rule);
		for(Rule r : rules)
			addRule(r);
	}

	public void addUnique(Collection<Rule> rules) {
		boolean add;
		for(Rule r : rules) {
			add = true; // assume it is not there
			for(Rule q : cssRules)
				if(r.equalSelectorMatches(q)) {
					add = false;
					break;
				}
			// we have stopped iterating over the rule set so it is safe to add it here.
			// could be improved by collecting all to add first, and then add all of them
			// as this algorithm will rescan the just added rules - but this is perhaps wanted - the
			// ruleSet being copied may contain duplicate matching rules.
			if(add)
				addRule(r);
		}
	}

	/**
	 * Adds all unique rules from the argument ruleSet to this ruleset. A rule is considered unique if it has a
	 * different selector pattern than existing rules
	 * 
	 * @param ruleSet
	 */
	public void addUnique(GraphCSS ruleSet) {
		addUnique(ruleSet.cssRules);
	}

	/**
	 * Collects an (ordered) list of rules in order of specificity (lowest first).
	 * If two rules have the same specificity, the one added first to the rule set will have a lower index.
	 * 
	 * @return
	 */
	public List<Rule> collectRules(IDomNode element) {
		ArrayList<Rule> matches = new ArrayList<Rule>(cssRules.size() / 2); // guessing on size

		// if element has a style map, add a (matched) rule for it
		if(element.getStyles() != null)
			matches.add(new Rule(new Select.Instance(element), element.getStyles()));
		for(Rule r : cssRules)
			if(r.matches(element))
				matches.add(r);
		Collections.sort(matches, RULE_COMPARATOR);
		return matches;
	}

	/**
	 * Collects the style applicable to the element.
	 * 
	 * @param context
	 * @param element
	 * @return a style set with all collected styles
	 */
	public StyleSet collectStyles(IDomNode element) {
		StyleSet result = new StyleSet();
		for(Rule r : collectRules(element)) {
			r.collectStyles(result);
		}
		return result;
	}

	/**
	 * Collects the style applicable to the element by calling a visitor.
	 * 
	 * @param node
	 * @param collector
	 */
	public void collectStyles(IDomNode node, IStyleVisitor collector) {
		StyleSet collected = collectStyles(node);
		for(IStyle<? extends Object> s : collected.getStyles())
			s.visit(node, collector);
	}

	public int indexOf(Rule rule) {
		return cssRules.indexOf(rule);
	}
}
