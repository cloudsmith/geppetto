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

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Joiner;
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			for(int i = 0; i < selectors.length; i++)
				if(!selectors[i].matches(node))
					return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			Joiner joiner = Joiner.on(", ");
			builder.append("and(");
			joiner.appendTo(builder, selectors);
			builder.append(")");
			return builder.toString();
		}
	}

	/**
	 * Matches containment where each selector must match an containing node.
	 * The interpretation allows for "holes" - i.e. the rule (==A ==C) matches the containment
	 * in the context (X Y A B C node) since node is contained in a C, that in turn is contained
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			IDomNode[] context = Iterators.toArray(node.parents(), IDomNode.class);
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("containment(");
			Joiner joiner = Joiner.on(", ");
			joiner.appendTo(builder, selectors);
			return builder.toString();
		}

	}

	/**
	 * A selector that is useful when debugging matching (wrap a real selector and set a breakpoint in {@link #matches(IDomNode)}.
	 */
	public static class DebugSelector extends Selector {
		private Selector wrappedSelector;

		public DebugSelector(Selector wrappedSelector) {
			this.wrappedSelector = wrappedSelector;
		}

		@Override
		public boolean equalMatch(Selector s) {
			return wrappedSelector.equalMatch(s);
		}

		@Override
		public int getSpecificity() {
			return wrappedSelector.getSpecificity();
		}

		@Override
		public boolean matches(IDomNode node) {
			boolean matches = false;
			if(node != null)
				matches = wrappedSelector.matches(node);
			return matches;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("debug(");
			builder.append(wrappedSelector.toString());
			builder.append(")");
			return builder.toString();
		}
	}

	/**
	 * Selects on grammar element - may contain one or more grammar elements.
	 * Selector matches if a DOM node is associated with one of the grammar elements.
	 */
	public static class GrammarSelector extends Selector {
		private Set<EObject> matchGrammar;

		GrammarSelector(EObject... grammarElements) {
			matchGrammar = Sets.newHashSet(grammarElements);
		}

		GrammarSelector(Iterable<? extends EObject> grammarElements) {
			matchGrammar = Sets.newHashSet(grammarElements);
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(selector instanceof GrammarSelector == false)
				return false;
			return matchGrammar.equals(((GrammarSelector) selector).matchGrammar);
		}

		@Override
		public int getSpecificity() {
			return GRAMMARSPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return matchGrammar.contains(node.getGrammarElement());
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("grammar(");
			builder.append(matchGrammar.toString());
			builder.append(")");
			return builder.toString();
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
		public boolean matches(IDomNode node) {
			return true;
		}

		@Override
		public String toString() {
			return "imortant()";
		}
	}

	/**
	 * The most specific selector that matches on a instance using ==.
	 * 
	 */
	public static class Instance extends Selector {
		private final IDomNode node;

		public Instance(IDomNode node) {
			this.node = node;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof Instance))
				return false;
			return this.node == ((Instance) selector).node;
		}

		@Override
		public int getSpecificity() {
			return MAX_SPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode node) {
			return this.node == node;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("instance(");
			builder.append(node.getNodeType());
			builder.append(", ");
			builder.append(node.hashCode());
			builder.append(")");
			return builder.toString();
		}
	}

	/**
	 * Matches IDomNodes on NodeType, style classifier, and id.
	 * 
	 */
	public static class NodeSelector extends Selector {

		private Set<NodeType> matchingNodeTypes;

		private Set<Object> matchingClassifiers;

		private Object matchingId;

		private int specificity = 0;

		private static String NoIdMatch = "";

		/**
		 * Selects all/any node.
		 */
		public NodeSelector() {
			this(NodeType.anySet, null, null);
		}

		/**
		 * Node must have all the given style classifiers.
		 * 
		 * @param styleClasses
		 */
		public NodeSelector(Collection<Object> styleClasses) {
			this(NodeType.anySet, styleClasses, null);
		}

		/**
		 * Node must have all of the given style classifiers and the given id.
		 * 
		 * @param styleClasses
		 * @param id
		 */
		public NodeSelector(Collection<Object> styleClasses, Object id) {
			this(NodeType.anySet, styleClasses, id);
		}

		/**
		 * Node must have the given node type
		 * 
		 * @param t
		 */
		public NodeSelector(NodeType t) {
			this(EnumSet.of(t), null, null);
		}

		/**
		 * Node must have the given node type and all of the given style classifiers.
		 * 
		 * @param nodeType
		 * @param styleClasses
		 */
		public NodeSelector(NodeType nodeType, Collection<Object> styleClasses) {
			this(EnumSet.of(nodeType), styleClasses, null);
		}

		/**
		 * Node must have the given node type, and all of the give style classifiers and the given id.
		 * 
		 * @param nodeType
		 * @param styleClasses
		 * @param id
		 */
		public NodeSelector(NodeType nodeType, Collection<Object> styleClasses, Object id) {
			this(EnumSet.of(nodeType), styleClasses, id);
		}

		/**
		 * Node must have the given node type, and the given style classifier
		 * (may have other style classes assigned), and the given id).
		 * 
		 * @param nodeType
		 * @param styleClass
		 * @param id
		 */
		public NodeSelector(NodeType nodeType, Object styleClass, Object id) {
			this(EnumSet.of(nodeType), Sets.newHashSet(styleClass), id);
		}

		/**
		 * Node must have all the given node statuses (may have other node status set).
		 * 
		 * @param nodeTypes
		 */
		public NodeSelector(Set<NodeType> nodeTypes) {
			this(nodeTypes, null, null);
		}

		/**
		 * Node must have the given node statuses (may have other status set), and all of the given style classes.
		 * 
		 * @param nodeTypes
		 * @param styleClasses
		 */
		public NodeSelector(Set<NodeType> nodeTypes, Collection<Object> styleClasses) {
			this(nodeTypes, styleClasses, null);
		}

		/**
		 * Node must have all the given node statuses (may have other status set), and all of the given style classes
		 * and the given id.
		 * 
		 * @param nodeTypes
		 *            - may be null (any type)
		 * @param styleClass
		 *            - may be null (any class)
		 * @param id
		 *            - may be null (any id)
		 */
		public NodeSelector(Set<NodeType> nodeTypes, Collection<Object> styleClass, Object id) {

			if(nodeTypes == null) {
				nodeTypes = EnumSet.noneOf(NodeType.class);
			}
			this.matchingNodeTypes = nodeTypes;
			this.matchingClassifiers = Sets.newHashSet();
			if(styleClass != null)
				this.matchingClassifiers.addAll(styleClass);
			this.matchingId = id != null
					? id
					: NoIdMatch;
		}

		@Override
		public boolean equalMatch(Selector selector) {
			if(!(selector instanceof NodeSelector))
				return false;
			NodeSelector e = (NodeSelector) selector;
			if(!matchingNodeTypes.equals(e.matchingNodeTypes))
				return false;
			if(!(matchingClassifiers.size() == e.matchingClassifiers.size() && e.matchingClassifiers.containsAll(matchingClassifiers)))
				return false;
			if(!matchingId.equals(e.matchingId))
				return false;
			return true;
		}

		@Override
		public int getSpecificity() {
			if(specificity > 0)
				return specificity;
			specificity = 0;

			if(matchingId != NoIdMatch)
				specificity += IDSPECIFICITY;

			// NOTE: the number per matching class must be bigger than NodeStatus.numberOfValues

			specificity += matchingClassifiers.size() * CLASSSPECIFICITY;

			switch(matchingNodeTypes.size()) {
				case 0: // will never matching anything - specificity does not matter
					break;
				case 1: // matches one type
					specificity += 2;
					break;
				case NodeType.numberOfValues:
					// matches all types (not very specific at all)
					break;
				default:
					// matches several but not all types (less specific than matching one)
					specificity += 1;
			}
			return specificity;
		}

		@Override
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;

			if(!matchingNodeTypes.contains(node.getNodeType()))
				return false;

			// i.e styleClass && styleClass && ...
			if(matchingClassifiers.size() > 0 && !node.getStyleClassifiers().containsAll(matchingClassifiers))
				return false;
			if(matchingId != NoIdMatch && !matchingId.equals(node.getNodeId()))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("node(type(");
			Joiner typeJoiner = Joiner.on(" || ");
			typeJoiner.appendTo(builder, matchingNodeTypes);
			builder.append(")");
			if(matchingClassifiers.size() > 0) {
				builder.append(", class(");
				Joiner classJoiner = Joiner.on(" && ");
				classJoiner.appendTo(builder, matchingClassifiers);
				builder.append(")");
			}
			if(matchingId != NoIdMatch) {
				builder.append(", id(");
				builder.append(matchingId);
				builder.append(")");
			}
			builder.append(")");
			return builder.toString();
		}
	}

	/**
	 * Negates a selector and increases the specificity by one.
	 * e.g. selection of not(a) is more important than selection of just a.
	 * Not does not select a null node.
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return !this.selector.matches(node);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("not(");
			builder.append(selector.toString());
			builder.append(")");
			return builder.toString();
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
		public boolean matches(IDomNode node) {
			return false;
		}

		@Override
		public String toString() {
			return "nullSelector()";
		}
	}

	/**
	 * Applies the delegate selector to the parent of the node being matched.
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return parentSelector.matches(node.getParent());
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("parent(");
			builder.append(parentSelector.toString());
			builder.append(")");
			return builder.toString();
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return predecessorSelector.matches(DomModelUtils.previousToken(node));
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("predecessor(");
			builder.append(predecessorSelector.toString());
			builder.append(")");
			return builder.toString();
		}

	}

	public static abstract class Selector {
		public static final int MAX_SPECIFICITY = Integer.MAX_VALUE;

		public static final int IMPORTANT_SPECIFICITY = 50000;

		public static final int IDSPECIFICITY = 15000;

		public static final int SEMANTICSPECIFICITY = 13000;

		public static final int CLASSSPECIFICITY = 120;

		public static final int GRAMMARSPECIFICITY = 100;

		static {
			assert (GRAMMARSPECIFICITY > NodeType.numberOfValues);
			assert (IDSPECIFICITY + 100 * CLASSSPECIFICITY + GRAMMARSPECIFICITY + NodeType.numberOfValues < IMPORTANT_SPECIFICITY);
			assert (IDSPECIFICITY > GRAMMARSPECIFICITY);
			assert (CLASSSPECIFICITY > GRAMMARSPECIFICITY);
			assert (CLASSSPECIFICITY - GRAMMARSPECIFICITY > NodeType.numberOfValues);
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

		public abstract boolean matches(IDomNode node);

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
	 * A Selector matching on semantic information
	 * 
	 */
	public static class SemanticSelector extends Selector {
		private EClass eClass;

		private boolean instance;

		private boolean nearest;

		public SemanticSelector(EClass eClass, boolean nearest, boolean instance) {
			this.eClass = eClass;
			this.instance = instance;
			this.nearest = nearest;
		}

		@Override
		public boolean equalMatch(Selector s) {
			if(!(s instanceof SemanticSelector))
				return false;
			SemanticSelector other = (SemanticSelector) s;
			return instance == other.instance && nearest == other.nearest && eClass.equals(other.eClass);

		}

		@Override
		public int getSpecificity() {
			return SEMANTICSPECIFICITY;
		}

		@Override
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			EObject semantic = nearest
					? node.getNearestSemanticObject()
					: node.getSemanticObject();
			if(semantic == null)
				return false;
			if(instance)
				return eClass.isInstance(semantic);
			return eClass.isSuperTypeOf(semantic.eClass());
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("semantic(");
			builder.append(eClass.getName());
			builder.append(", ");
			builder.append(nearest
					? "nearest"
					: "assigned");
			builder.append(", ");
			builder.append(instance
					? "instance"
					: "typeof");
			builder.append(")");
			return builder.toString();
		}
	}

	/**
	 * Applies the delegate selector to the successor of the matching node.
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return successorSelector.matches(DomModelUtils.nextToken(node));
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("successor(");
			builder.append(successorSelector.toString());
			builder.append(")");
			return builder.toString();
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
		public boolean matches(IDomNode node) {
			if(node == null)
				return false;
			return this.text.equals(node.getText());
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("text('");
			builder.append(text);
			builder.append("')");
			return builder.toString();
		}
	}

	/**
	 * Selects a node that is matched by the given nodeSelector if the node's predecessor matches
	 * the given predecessor selector.
	 * 
	 * @param nodeSelector
	 * @param predecessor
	 * @return
	 */
	public static Select.And after(Selector nodeSelector, Selector predecessor) {
		return new And(nodeSelector, new PredecessorSelector(predecessor));
	}

	public static Select.And and(Select.Selector... selectors) {
		return new Select.And(selectors);
	}

	public static Select.NodeSelector any() {
		return new Select.NodeSelector();
	}

	public static Select.NodeSelector any(Object styleClass) {
		return new Select.NodeSelector(Sets.newHashSet(styleClass));
	}

	public static Select.NodeSelector any(Object styleClass, String id) {
		return new Select.NodeSelector(Sets.newHashSet(styleClass), id);
	}

	/**
	 * Selects a node matching the given nodeSelector, if this node is before a node matching the
	 * given successor selector.
	 * 
	 * @param nodeSelector
	 * @param successor
	 * @return
	 */
	public static Select.And before(Selector nodeSelector, Selector successor) {
		return new And(nodeSelector, new SuccessorSelector(successor));
	}

	public static Select.And between(Selector predecessor, Selector successor) {
		return new And(new PredecessorSelector(predecessor), new SuccessorSelector(successor));
	}

	public static Select.And between(Selector nodeSelector, Selector predecessor, Selector successor) {
		return new And(nodeSelector, new And(new PredecessorSelector(predecessor), new SuccessorSelector(successor)));
	}

	public static Select.NodeSelector comment() {
		return new Select.NodeSelector(NodeType.COMMENT);
	}

	/**
	 * Matches containment where each selector must match an containing node.
	 * The interpretation allows for "holes" - i.e. the rule (==A ==C) matches the containment
	 * in the context (X Y A B C node) since node is contained in a C, that in turn is contained
	 * in an A). This is similar to how the CSS containment rule works.
	 */
	public static Select.Containment containment(Select.Selector... selectors) {
		return new Select.Containment(selectors);
	}

	public static Select.GrammarSelector grammar(Collection<? extends EObject> grammarElements) {
		return new Select.GrammarSelector(grammarElements);
	}

	public static Select.GrammarSelector grammar(EObject... grammarElements) {
		return new Select.GrammarSelector(grammarElements);
	}

	public static Select.GrammarSelector grammar(Iterable<? extends EObject> grammarElements) {
		return new Select.GrammarSelector(grammarElements);
	}

	public static Selector important() {
		return new Important();
	}

	public static Selector important(Selector s) {
		return new And(new Important(), s);
	}

	public static Select.Instance instance(IDomNode x) {
		return new Select.Instance(x);
	}

	public static Select.And keyword(String text) {
		return new Select.And(new NodeSelector(NodeType.KEYWORD), new Text(text));
	}

	/**
	 * Select node with a nearest semantic object with given clazz, or a supertype of given clazz.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Select.SemanticSelector nearestSemantic(EClass clazz) {
		return new SemanticSelector(clazz, true, false);
	}

	/**
	 * Select node with a nearest semantic object being an instance of the given clazz.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Select.SemanticSelector nearestSemanticInstance(EClass clazz) {
		return new SemanticSelector(clazz, true, true);
	}

	public static Select.NodeSelector node(Collection<Object> styleClasses) {
		return new Select.NodeSelector(styleClasses, null);
	}

	public static Select.NodeSelector node(Collection<Object> styleClasses, String id) {
		return new Select.NodeSelector(styleClasses, id);
	}

	public static Select.NodeSelector node(NodeType type) {
		return new Select.NodeSelector(type);
	}

	public static Select.NodeSelector node(NodeType type, Collection<String> styleClasses, String id) {
		return new Select.NodeSelector(type, styleClasses, id);
	}

	public static Select.NodeSelector node(NodeType type, String styleClass) {
		return new Select.NodeSelector(type, Collections.singleton(styleClass), null);
	}

	public static Select.NodeSelector node(NodeType type, String styleClass, String id) {
		return new Select.NodeSelector(type, Collections.singleton(styleClass), id);
	}

	public static Select.NodeSelector node(Object styleClass) {
		return new Select.NodeSelector(Collections.singleton(styleClass), null);
	}

	public static Select.NodeSelector node(Object styleClass, String id) {
		return new Select.NodeSelector(Collections.singleton(styleClass), id);
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

	/**
	 * Select node with a direct semantic object with given clazz, or a supertype of given clazz.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Select.SemanticSelector semantic(EClass clazz) {
		return new SemanticSelector(clazz, false, false);
	}

	/**
	 * Select node with a direct semantic object with given clazz.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Select.SemanticSelector semanticInstance(EClass clazz) {
		return new SemanticSelector(clazz, false, true);
	}

	public static Select.NodeSelector whitespace() {
		return new Select.NodeSelector(NodeType.WHITESPACE);
	}

	public static Selector whitespaceAfter(Selector predecessor) {
		return new And(new Select.NodeSelector(NodeType.WHITESPACE), new PredecessorSelector(predecessor));

	}

	public static Selector whitespaceBefore(Selector succcessor) {
		return new And(new Select.NodeSelector(NodeType.WHITESPACE), new SuccessorSelector(succcessor));

	}
}
