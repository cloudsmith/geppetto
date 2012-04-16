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
package org.cloudsmith.xtext.dommodel.formatter.css;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cloudsmith.xtext.dommodel.IDomNode;

/**
 * A DOM CSS consists of a set of {@link Rule} describing the styling of an {@link IDomNode}.
 * 
 */
public class DomCSS {
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
			r1s = r1.getDomCSS().indexOf(r1);
			r2s = r2.getDomCSS().indexOf(r2);
			if(r1s < r2s)
				return -1;
			if(r1s > r2s)
				return 1;
			throw new IllegalStateException("Comparator MUST order rules");
		}
	};

	public DomCSS() {
		cssRules = new ArrayList<Rule>();
	}

	/**
	 * Adds all rules from another style sheet.
	 * 
	 * @param ruleSet
	 */
	public void addAll(DomCSS ruleSet) {
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
	 * Adds a rule to this rule set. If the rule is already in another style sheet, the rule is cloned before
	 * being added. The added rule's domCSS property is set to this style sheet.
	 * 
	 * @param rule
	 */
	public void addRule(Rule rule) {
		if(rule == Rule.NULL_RULE)
			return;

		rule = rule.getDomCSS() != null
				? (Rule) rule.clone()
				: rule;
		rule.setDomCSS(this);
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

	/**
	 * Adds rules to this style sheet if they have a selector that is not equal to a selector already
	 * in the style sheet.
	 * 
	 * @param rules
	 */
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
	 * Adds all unique rules from the given style sheet to this style sheet. A rule is considered unique if it has a
	 * different selector pattern than existing rules
	 * 
	 * @param domCSS
	 */
	public void addUnique(DomCSS domCSS) {
		addUnique(domCSS.cssRules);
	}

	/**
	 * Collects an (ordered) list of rules in order of specificity (lowest first) that matches the given node.
	 * The node's instance style is taken into consideration with 'instance' specificity.
	 * If two rules have the same specificity, the one added first to the rule set will have a lower index.
	 * 
	 * @return - a list of matching Rules for the given node
	 */
	public List<Rule> collectRules(IDomNode node) {
		ArrayList<Rule> matches = new ArrayList<Rule>(5); // guessing on size

		// if element has a style map, add a (matched) rule for it
		if(node.getStyles() != null)
			matches.add(new Rule(new Select.Instance(node), node.getStyles()));
		for(Rule r : cssRules)
			if(r.matches(node))
				matches.add(r);
		Collections.sort(matches, RULE_COMPARATOR);
		return matches;
	}

	/**
	 * Collects the style applicable to the given node. (The styles from all matching rules are reduced to a
	 * resulting style set).
	 * 
	 * @param context
	 * @param element
	 * @return a style set with all collected styles
	 */
	public StyleSet collectStyles(IDomNode element) {
		StyleSetWithTracking result = new StyleSetWithTracking();
		for(Rule r : collectRules(element)) {
			result.setSource(r);
			r.collectStyles(result);
		}
		result.setSource(null); // just in case something else manipulates this set
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

	/**
	 * Returns the style sheet index of the given rule.
	 * 
	 * @param rule
	 * @return
	 */
	public int indexOf(Rule rule) {
		return cssRules.indexOf(rule);
	}
}
