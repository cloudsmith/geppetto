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

import org.cloudsmith.xtext.dommodel.IDomNode;

/**
 * A DOM style rule contains a selector {@link Select.Selector} (which can be compound), and
 * a {@link StyleSet}.
 * 
 * A DOM style rule is typically added to a {@link DomCSS}.
 * A rule can only be added to one DomCSS (at a time).
 * 
 * Note that the easiest is to use {@link Select.Selector#withStyles(IStyle...)} and related methods
 * to create Rule instances.
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
	 * The CSS this Rule is contained in.
	 */
	private DomCSS domCSS;

	private String ruleName = "";

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
	 *            - the rule selector
	 * @param styles
	 *            - the styles to use may be null
	 */
	public Rule(Select.Selector selector, StyleSet styles) {
		this.selector = selector;
		// create a new style map so things can be added to it without
		// destroying the input map
		styleSet = new StyleSet();
		if(styles != null)
			styleSet.add(styles);
	}

	/**
	 * Add given style to rule's style set.
	 * 
	 * @param style
	 *            - style to add
	 */
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
	 * Add all styles in this rule to the given style set and return the given set.
	 * 
	 * @param result
	 * @return
	 */
	public StyleSet collectStyles(StyleSet result) {
		result.add(styleSet);
		return result;
	}

	/**
	 * Add all matching styles in this rule to the given style set and return the given set if the given node
	 * matches the selector.
	 * 
	 * @param result
	 *            - where styles are added if given node matches selector
	 * @param node
	 *            - the node to match against the selector
	 * @return
	 */
	public StyleSet collectStylesIfMatch(StyleSet result, IDomNode node) {
		if(selector.matches(node))
			result.add(styleSet);
		return result;
	}

	/**
	 * Returns true if this rule has the same selector match as the given rule. (Note for each type of selector
	 * how the selector equality is tested).
	 * 
	 * @param rule
	 *            - the rule to test for equal selectors
	 * @return true if they have the same selector matching
	 */
	public boolean equalSelectorMatches(Rule rule) {
		return selector.equalMatch(rule.selector);
	}

	/**
	 * Return the DomCSS this rule is part of.
	 * 
	 * @return
	 */
	public DomCSS getDomCSS() {
		return domCSS;
	}

	public String getRuleName() {
		return ruleName;
	}

	/**
	 * Return the specificity of the rule's selector.
	 * 
	 * @return the selector specificity
	 */
	public int getSpecificity() {
		return selector.getSpecificity();
	}

	/**
	 * Matches the given node against the rule's selector and returns the result.
	 * 
	 * @param node
	 * @return
	 */
	public boolean matches(IDomNode node) {
		return selector.matches(node);
	}

	/**
	 * Sets the parent style sheet of this rule.
	 * DON'T CALL THIS METHOD UNLESS YOU KNOW WHAT YOU ARE DOING.
	 * 
	 * @param styleSheet
	 */
	public void setDomCSS(DomCSS styleSheet) {
		domCSS = styleSheet;
	}

	public void setRuleName(String name) {
		ruleName = name;
	}

	public Rule withRuleName(String name) {
		setRuleName(name);
		return this;
	}
}
