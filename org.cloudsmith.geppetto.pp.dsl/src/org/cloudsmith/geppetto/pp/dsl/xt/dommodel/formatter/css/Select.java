/**
 * Copyright (c) 2006-2011 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.DomModelUtils;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeStatus;
import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

/**
 * Style Select is used to produce a configured Selector.
 * 
 */
public class Select {

	/**
	 * A compound rule, where all rules must be satisfied.
	 * 
	 */
	public static class And extends Selector {
		private int specificity = 0;

		private Selector[] selectors;

		public And(Selector... selectors) {
			this.selectors = selectors;
		}

		/**
		 * Important - two And selectors are considered equal only if they have the rules in the same order.
		 * The correctness of this can be discussed.
		 */
		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof And))
				return false;
			And a = (And) selector;
			if(selectors.length != a.selectors.length)
				return false;
			for(int i = selectors.length; i >= 0; i--)
				if(!selectors[i].equalMatch(a.selectors[i]))
					return false;
			return true;
		}

		@Override
		public int getSpecificity() {
			if(specificity != 0)
				return specificity;
			for(int i = 0; i < selectors.length; i++)
				specificity += selectors[i].getSpecificity();
			return specificity;
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			for(int i = 0; i < selectors.length; i++)
				if(!selectors[i].matches(element))
					return false;
			return true;
		}
	}

	/**
	 * Matches containment where each selector must match an containing element.
	 * The interpretation allows for "holes" - i.e. the rule (==A ==C) matches the containment
	 * in the context (X Y A B C element) since element is contained in a C, that in turn is contained
	 * in an A). This is similar to how the CSS containment rule works.
	 */
	public static class Containment extends Selector {
		private int specificity = 0;

		private Selector[] selectors;

		/**
		 * Selectors for containers - the nearest container first
		 * 
		 * @param selectors
		 */
		public Containment(Selector... selectors) {
			if(selectors == null || selectors.length < 1)
				throw new IllegalArgumentException("no selectors specified");
			this.selectors = selectors;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Containment))
				return false;
			Containment c = (Containment) selector;
			if(selectors.length != c.selectors.length)
				return false;
			for(int i = selectors.length; i >= 0; i--)
				if(!selectors[i].equalMatch(c.selectors[i]))
					return false;
			return true;
		}

		@Override
		public int getSpecificity() {
			if(specificity != 0)
				return specificity;
			for(int i = 0; i < selectors.length; i++)
				specificity += selectors[i].getSpecificity();
			return specificity;
		}

		/**
		 * context vector has nearest container first
		 */
		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			IDomNode[] context = Iterators.toArray(element.parents(), IDomNode.class);
			int startIdx = 0;
			int matchCount = 0;
			for(int si = 0; si < selectors.length; si++) {
				for(int ci = startIdx; ci < context.length; ci++)
					if(selectors[si].matches(context[ci])) {
						// match
						startIdx = ci + 1; // next rule must match context further away
						matchCount++; // one match ok
						break;
					}
			}
			// match if all containment rules where satisfied
			return (matchCount == selectors.length)
					? true
					: false;
		}

	}

	/**
	 * Matches on Element on NodeStatus, style class, and id.
	 * 
	 */
	public static class Element extends Selector {

		private Set<NodeStatus> matchElement;

		private Set<Object> matchClasses;

		private Object matchId;

		private int specificity = 0;

		private static String NoIdMatch = "";

		/**
		 * Selects all/any element.
		 */
		public Element() {
			this(EnumSet.noneOf(NodeStatus.class), null, null);
		}

		/**
		 * Node must have all the given style classes.
		 * 
		 * @param styleClasses
		 */
		public Element(Collection<Object> styleClasses) {
			this(EnumSet.noneOf(NodeStatus.class), styleClasses, null);
		}

		/**
		 * Node must have all of the given style classes and the given id.
		 * 
		 * @param styleClasses
		 * @param id
		 */
		public Element(Collection<Object> styleClasses, Object id) {
			this(EnumSet.noneOf(NodeStatus.class), styleClasses, id);
		}

		/**
		 * Node must have the given node status (may have other node status set).
		 * 
		 * @param t
		 */
		public Element(NodeStatus t) {
			this(EnumSet.of(t), null, null);
		}

		/**
		 * Node must have the given node status (may have other node status set), and
		 * all of the given style classes.
		 * 
		 * @param elementType
		 * @param styleClasses
		 */
		public Element(NodeStatus elementType, Collection<Object> styleClasses) {
			this(EnumSet.of(elementType), styleClasses, null);
		}

		/**
		 * Node must have the given node type (may have other node status set), and all
		 * of the style classes and the given id.
		 * 
		 * @param elementType
		 * @param styleClasses
		 * @param id
		 */
		public Element(NodeStatus elementType, Collection<Object> styleClasses, Object id) {
			this(EnumSet.of(elementType), styleClasses, id);
		}

		/**
		 * Node must have the given node type (may have other node status set), and the given style class
		 * (may have other style classes assigned), and the given id).
		 * 
		 * @param elementType
		 * @param styleClass
		 * @param id
		 */
		public Element(NodeStatus elementType, Object styleClass, Object id) {
			this(EnumSet.of(elementType), Sets.newHashSet(styleClass), id);
		}

		/**
		 * Node must have all the given node statuses (may have other node status set).
		 * 
		 * @param elementType
		 */
		public Element(Set<NodeStatus> elementType) {
			this(elementType, null, null);
		}

		/**
		 * Node must have the given node statuses (may have other status set), and all of the given style classes.
		 * 
		 * @param elementTypes
		 * @param styleClasses
		 */
		public Element(Set<NodeStatus> elementTypes, Collection<Object> styleClasses) {
			this(elementTypes, styleClasses, null);
		}

		/**
		 * Node must have all the given node statuses (may have other status set), and all of the given style classes
		 * and the given id.
		 * 
		 * @param elementType
		 *            - may be null (any type)
		 * @param styleClass
		 *            - may be null (any class)
		 * @param id
		 *            - may be null (any id)
		 */
		public Element(Set<NodeStatus> elementType, Collection<Object> styleClass, Object id) {

			if(elementType == null) {
				elementType = EnumSet.noneOf(NodeStatus.class);
			}
			this.matchElement = elementType;
			this.matchClasses = Sets.newHashSet();
			if(styleClass != null)
				this.matchClasses.addAll(styleClass);
			this.matchId = id != null
					? id
					: NoIdMatch;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Element))
				return false;
			Element e = (Element) selector;
			if(!matchElement.equals(e.matchElement))
				return false;
			if(!(matchClasses.size() == e.matchClasses.size() && e.matchClasses.containsAll(matchClasses)))
				return false;
			if(!matchId.equals(e.matchId))
				return false;
			return true;
		}

		@Override
		public int getSpecificity() {
			if(specificity > 0)
				return specificity;
			specificity = 0;

			if(matchId != NoIdMatch)
				specificity += IDSPECIFICITY;

			// NOTE: the number per matching class must be bigger than NodeStatus.numberOfValues

			specificity += matchClasses.size() * CLASSSPECIFICITY;

			specificity += matchElement.size();
			return specificity;
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;

			if(!element.getNodeStatus().containsAll(matchElement))
				return false;

			// i.e styleClass && styleClass && ...
			if(matchClasses.size() > 0 && !element.getStyleClassifiers().containsAll(matchClasses))
				return false;
			if(matchId != NoIdMatch && !matchId.equals(element.getNodeId()))
				return false;
			return true;
		}
	}

	/**
	 * Selects on grammar element - may contain one or more grammar elements.
	 * Selector matches if a dome node is associated with one of the grammar elements.
	 */
	public static class Grammar extends Selector {
		private Set<EObject> matchGrammar;

		Grammar(EObject... grammarElements) {
			Sets.newHashSet(grammarElements);
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(selector instanceof Grammar == false)
				return false;
			return matchGrammar.equals(((Grammar) selector).matchGrammar);
		}

		@Override
		public int getSpecificity() {
			return GRAMMARSPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return matchGrammar.contains(element.getGrammarElement());
		}
	}

	/**
	 * The next to most specific selector that matches anything.
	 * Should be combined with And selector to make the and specificity very high.
	 * 
	 */
	public static class Important extends Selector {

		public Important() {
		}

		@Override
		public boolean equalMatch(Selector selector) {
			return selector instanceof Important;
		}

		@Override
		public int getSpecificity() {
			return IMPORTANT_SPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode element) {
			return true;
		}
	}

	/**
	 * The most specific selector that matches on a instance using ==.
	 * 
	 */
	public static class Instance extends Selector {
		private final IDomNode element;

		public Instance(IDomNode element) {
			this.element = element;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Instance))
				return false;
			return this.element == ((Instance) selector).element;
		}

		@Override
		public int getSpecificity() {
			return MAX_SPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode element) {
			return this.element == element;
		}
	}

	/**
	 * Negates a selector and increases the specificity by one.
	 * e.g. selection of not(a) is more important than selection of just a.
	 * Not does not select a null element.
	 * 
	 */
	public static class Not extends Selector {
		private Selector selector;

		public Not(Selector selector) {
			this.selector = selector;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Not))
				return false;
			Not notSelector = (Not) selector;
			return this.selector.equalMatch(notSelector.selector);
		}

		@Override
		public int getSpecificity() {
			return this.selector.getSpecificity() + 1;
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return !this.selector.matches(element);
		}

	}

	/**
	 * A NullSelector is only useful in a NullRule. It matches nothing.
	 * 
	 */
	public static class NullSelector extends Selector {

		@Override
		public boolean equalMatch(Selector selector) {
			return false;
		}

		@Override
		public int getSpecificity() {
			return 0;
		}

		@Override
		public boolean matches(IDomNode element) {
			return false;
		}

	}

	/**
	 * Applies the delegate selector to the parent of the element being matched.
	 * 
	 */
	public static class ParentSelector extends Selector {
		private Selector parentSelector;

		public ParentSelector(Selector parentSelector) {
			this.parentSelector = parentSelector;
		}

		@Override
		public boolean equalMatch(Selector s) {
			if(!(s instanceof ParentSelector))
				return false;
			return parentSelector.equalMatch(((ParentSelector) s).parentSelector);
		}

		@Override
		public int getSpecificity() {
			return parentSelector.getSpecificity();
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return parentSelector.matches(element.getParent());
		}
	}

	public static class PredecessorSelector extends Selector {
		private Selector predecessorSelector;

		public PredecessorSelector(Selector predecessorSelector) {
			this.predecessorSelector = predecessorSelector;
		}

		@Override
		public boolean equalMatch(Selector s) {
			if(!(s instanceof SuccessorSelector))
				return false;
			return predecessorSelector.equalMatch(((PredecessorSelector) s).predecessorSelector);
		}

		@Override
		public int getSpecificity() {
			return predecessorSelector.getSpecificity();
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return predecessorSelector.matches(DomModelUtils.preceedingLeaf(element));
		}
	}

	public static abstract class Selector {
		public static final int MAX_SPECIFICITY = Integer.MAX_VALUE;

		public static final int IMPORTANT_SPECIFICITY = 30000;

		public static final int IDSPECIFICITY = 15000;

		public static final int CLASSSPECIFICITY = 120;

		public static final int GRAMMARSPECIFICITY = 100;

		static {
			assert (GRAMMARSPECIFICITY > NodeStatus.numberOfValues);
			assert (IDSPECIFICITY + 100 * CLASSSPECIFICITY + GRAMMARSPECIFICITY + NodeStatus.numberOfValues < IMPORTANT_SPECIFICITY);
			assert (IDSPECIFICITY > GRAMMARSPECIFICITY);
			assert (CLASSSPECIFICITY > GRAMMARSPECIFICITY);
			assert (CLASSSPECIFICITY - GRAMMARSPECIFICITY > NodeStatus.numberOfValues);
		}

		public And and(Selector selector) {
			return new And(this, selector);
		}

		public abstract boolean equalMatch(Selector selector);

		/**
		 * Returns the specificity in the same style as used in CSS:
		 * 100 * id count + 10 * class count + 1 * other count
		 * 
		 * @return
		 */
		public abstract int getSpecificity();

		public abstract boolean matches(IDomNode element);

		public Rule withStyle(IStyle<? extends Object> styles) {
			return new Rule(this, StyleSet.withStyles(styles));
		}

		public Rule withStyle(StyleSet styleMap) {
			return new Rule(this, styleMap);
		}

		public Rule withStyles(IStyle<?>... styles) {
			return new Rule(this, StyleSet.withStyles(styles));
		}
	}

	/**
	 * Applies the delegate selector to the successor of the matched element.
	 * 
	 */
	public static class SuccessorSelector extends Selector {
		private Selector successorSelector;

		public SuccessorSelector(Selector successorSelector) {
			this.successorSelector = successorSelector;
		}

		@Override
		public boolean equalMatch(Selector s) {
			if(!(s instanceof SuccessorSelector))
				return false;
			return successorSelector.equalMatch(((SuccessorSelector) s).successorSelector);
		}

		@Override
		public int getSpecificity() {
			return successorSelector.getSpecificity();
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return successorSelector.matches(DomModelUtils.nextLeaf(element));
		}
	}

	/**
	 * Selects matching text.
	 * 
	 */
	public static class Text extends Selector {
		private final String text;

		public Text(String text) {
			if(text == null)
				throw new IllegalArgumentException("text selector can not be null");
			this.text = text;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Text))
				return false;
			return this.text == ((Text) selector).text;
		}

		@Override
		public int getSpecificity() {
			return 1;
		}

		@Override
		public boolean matches(IDomNode element) {
			if(element == null)
				return false;
			return this.text.equals(element.getText());
		}
	}

	public static Select.And after(Selector elementSelector, Selector succcessor) {
		return new And(elementSelector, new SuccessorSelector(succcessor));
	}

	public static Select.And and(Select.Selector... selectors) {
		return new Select.And(selectors);
	}

	public static Select.Element any() {
		return new Select.Element();
	}

	public static Select.Element any(Object styleClass) {
		return new Select.Element(Sets.newHashSet(styleClass));
	}

	public static Select.Element any(Object styleClass, String id) {
		return new Select.Element(Sets.newHashSet(styleClass), id);
	}

	public static Select.And before(Selector elementSelector, Selector predecessor) {
		return new And(elementSelector, new PredecessorSelector(predecessor));
	}

	public static Select.And between(Selector predecessor, Selector successor) {
		return new And(new PredecessorSelector(predecessor), new SuccessorSelector(successor));
	}

	public static Select.And between(Selector elementSelector, Selector predecessor, Selector successor) {
		return new And(elementSelector, new And(new PredecessorSelector(predecessor), new SuccessorSelector(successor)));
	}

	public static Select.Element comment() {
		return new Select.Element(NodeStatus.COMMENT);
	}

	public static Select.Containment containment(Select.Selector... selectors) {
		return new Select.Containment(selectors);
	}

	public static Select.Element element(Collection<Object> styleClasses) {
		return new Select.Element(styleClasses, null);
	}

	public static Select.Element element(Collection<Object> styleClasses, String id) {
		return new Select.Element(styleClasses, id);
	}

	public static Select.Element element(NodeStatus type) {
		return new Select.Element(type);
	}

	public static Select.Element element(NodeStatus type, Collection<String> styleClasses, String id) {
		return new Select.Element(type, styleClasses, id);
	}

	public static Select.Element element(NodeStatus type, String styleClass) {
		return new Select.Element(type, Collections.singleton(styleClass), null);
	}

	public static Select.Element element(NodeStatus type, String styleClass, String id) {
		return new Select.Element(type, Collections.singleton(styleClass), id);
	}

	public static Select.Element element(Object styleClass) {
		return new Select.Element(Collections.singleton(styleClass), null);
	}

	public static Select.Element element(Object styleClass, String id) {
		return new Select.Element(Collections.singleton(styleClass), id);
	}

	public static Select.Instance instance(IDomNode x) {
		return new Select.Instance(x);
	}

	public static Select.And keyword(String text) {
		return new Select.And(new Element(NodeStatus.KEYWORD), new Text(text));
	}

	public static Select.Not not(Select.Selector selector) {
		return new Select.Not(selector);
	}

	public static Select.NullSelector nullSelector() {
		return new Select.NullSelector();
	}

	public static Select.ParentSelector parent(Selector selector) {
		return new Select.ParentSelector(selector);
	}

	public static Select.Element whitespace() {
		return new Select.Element(NodeStatus.WHITESPACE);
	}
}
