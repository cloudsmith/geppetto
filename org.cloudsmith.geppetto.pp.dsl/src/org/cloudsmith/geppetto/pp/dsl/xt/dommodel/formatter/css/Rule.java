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

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

/**
 * A graph style rule contains a selector {@link Select.Selector} (which can be compound), and
 * a {@link StyleSet}.
 * 
 * A graph style rule is typically added to a {@link GraphCSS}.
 * A rule can only be added to one GCSS (at a time).
 * 
 * Note that the easiest is to use {@link Select.Selector#withStyles(IStyle...)} and related methods
 * to create a Rule.
 */
public final class Rule implements Cloneable {
	/**
	 * A Rule that never matches.
	 */
	public static final Rule NULL_RULE = new Rule(new Select.NullSelector());

	/**
	 * The selector used for this rule.
	 */
	private Select.Selector selector;

	/**
	 * The style set associated with the rule - i.e. the styles to apply if
	 * the rule triggers.
	 */
	private StyleSet styleSet;

	/**
	 * The GCSS this Rule is contained in.
	 */
	private GraphCSS graphCSS;

	/**
	 * Create a Rule with an empty style set.
	 * 
	 * @param selector
	 */
	public Rule(Select.Selector selector) {
		this(selector, null);
	}

	/**
	 * Create a Rule with a copy of the content of the given style set.
	 * 
	 * @param selector
	 * @param smap
	 *            may be null
	 */
	public Rule(Select.Selector selector, StyleSet smap) {
		this.selector = selector;
		// create a new style map so things can be added to it without
		// destroying the input map
		styleSet = new StyleSet();
		if(smap != null)
			styleSet.add(smap);
	}

	public void add(IStyle<?> style) {
		styleSet.put(style);
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		}
		catch(CloneNotSupportedException e) {
			// merde! something is really wrong
			return null;
		}
	}

	/**
	 * Add all styles to the given style set and return it.
	 * 
	 * @param result
	 * @return
	 */
	public StyleSet collectStyles(StyleSet result) {
		result.add(styleSet);
		return result;
	}

	/**
	 * Add all matching styles to the given style set and return it.
	 * 
	 * @param result
	 * @param element
	 * @return
	 */
	public StyleSet collectStylesIfMatch(StyleSet result, IDomNode element) {
		if(selector.matches(element))
			result.add(styleSet);
		return result;
	}

	/**
	 * Returns true if this rule has the same selector match as the argument. Note for each type of selector
	 * how the selector equality is tested.
	 * 
	 * @param rule
	 * @return true if they have the same selector matching
	 */
	public boolean equalSelectorMatches(Rule rule) {
		return selector.equalMatch(rule.selector);
	}

	public GraphCSS getGraphCSS() {
		return graphCSS;
	}

	public int getSpecificity() {
		return selector.getSpecificity();
	}

	public boolean matches(IDomNode element) {
		return selector.matches(element);
	}

	/**
	 * Sets the parent rule set of this rule.
	 * DON'T CALL THIS METHOD UNLESS YOU KNOW WHAT YOU ARE DOING.
	 * 
	 * @param ruleSet
	 */
	public void setGraphCSS(GraphCSS ruleSet) {
		graphCSS = ruleSet;
	}
}
