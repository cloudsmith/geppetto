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
package org.cloudsmith.xtext.serializer;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration.CommentType;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.parsetree.reconstr.impl.TokenUtil;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * <p>
 * A Comment associator that does the following:
 * <ul>
 * <li>Scans the given input INode for semantic positions and comment sequences</li>
 * <li>Makes lexical default associations based on comment type, position and linebreaks</li>
 * <li>Refines the decisions based on association with grammar punctuation.</li>
 * <li>Decodes left / right associations, as well as associations to left -1 position (for element before punctuation). Supporting right + 1 is left
 * as an exercise.</li>
 * <li>Transforms the decisions into a series of {@code Predicate<INode, INode, INode> } that expects to be applied with the nodes 'preceding',
 * 'from', 'to' as described by {@link ICommentReconcilement}.</li>
 * <li>Produces an {@link ICommentReconcilement} based on the predicates</li>
 * </ul>
 * </p>
 * <p>
 * The left/right associations of a comment sequence CS is done with these rules:
 * <ul>
 * <li>A CS that ends with a SL comment associates to the left, unless the CS only has whitespace on its left.</li>
 * <li>A CS that ends with a NL (for grammars where comment terminal contains trailing WS/NL) is handled as a CS ending with SL.</li>
 * <li>All other CS associate to the right</li>
 * <li>A left association to a <i>list separator</i> associates to the element preceding the separator</li>
 * <li>A right association to a <i>right punctuation</i> is changed to a left association</li>
 * </ul>
 * </p>
 * <p>
 * The implementation is generic, but makes assumptions about the grammar:
 * <ul>
 * <li>All comma keywords are interpreted as <i>list separators</i> and cause left association to skip to the preceding element.</li>
 * <li>A set of right punctuation keywords <code>,;:)}]</code> cause a right association to be changed to left.</li>
 * </ul>
 * <p>
 * A specialized implementation can easily override these. Customization can also easily be made by overriding the method
 * {@link #computeLanguageSpecificAssociations(CommentSemanticSequence)} is which is called last, thus allowing any association already made to be
 * changed.
 * </p>
 */
public class CommentAssociator {

	/**
	 * Describes either a sequence of comments or a semantic location (EObject and first/last INodes)
	 */
	public abstract static class CommentAssociationEntry {
		abstract boolean isComments();

		boolean isSemantic() {
			return !isComments();
		}
	}

	/**
	 * Describes a sequence of comments and their association (left/right, and a possible "skip" of 1 to the left.
	 * TODO: Can be simplified and made to check allowed invariants as left(0), left(1), right(0) are the only allowed.
	 * Note that supporting bigger skip counts, or right skip means that the hidden token sequencer needs to maintain a larger
	 * peep hole.
	 */
	public static class Comments extends CommentAssociationEntry {
		List<INode> commentNodes;

		boolean rightAssociation;

		boolean firstOnLine;

		int skipCount;

		public Comments(List<INode> commentNodes) {
			this.commentNodes = Lists.newArrayList(commentNodes); // keep a copy
			this.rightAssociation = true;
			this.skipCount = 0;
		}

		public List<INode> getComments() {
			return commentNodes;
		}

		public int getSkipCount() {
			return skipCount;
		}

		@Override
		public boolean isComments() {
			return true;
		}

		public boolean isEmpty() {
			return commentNodes.size() == 0;
		}

		public boolean isFirstOnLine() {
			return firstOnLine;
		}

		public boolean isLeft() {
			return !rightAssociation;
		}

		public boolean isRight() {
			return rightAssociation;
		}

		public void setDirectionLeft() {
			rightAssociation = false;
		}

		public void setDirectionRight() {
			rightAssociation = true;
		}

		public Comments setFirstOnLine(boolean first) {
			firstOnLine = first;
			return this;
		}

		public void setSkipCount(int skipCount) {
			this.skipCount = skipCount;
		}
	}

	/**
	 * Describes a sequence of CommentAssociationEntry, with housekeeping/lookup logic.
	 * 
	 */
	public static class CommentSemanticSequence implements Iterable<CommentAssociationEntry> {
		private List<CommentAssociationEntry> sequence = Lists.newArrayList();

		public void addComments(List<INode> comments) {
			// empty comment sequences are simply ignored to reduce work later
			if(comments.size() < 1)
				return;

			// a sequence that has a subsequent comment to the left of the preceding comment is broken
			// into two sequences
			int prevLinePos = posOnLine(comments.get(0));
			for(int i = 1; i < comments.size(); i++) {
				if(posOnLine(comments.get(i)) < prevLinePos) {
					sequence.add(new Comments(comments.subList(0, i)));
					sequence.add(new Comments(comments.subList(i, comments.size())).setFirstOnLine(true));
					return;
				}
			}
			sequence.add(new Comments(comments));
		}

		public void addSemantic(EObject semantic, INode first, INode last) {
			// avoid duplicate entry (typically occurs at the start as reverse scanning is needed to find a start with
			// semantic information - this point may be different than the given start, but is typically the given point).
			if(!sequence.isEmpty() && sequence.get(sequence.size() - 1).isSemantic()) {
				Semantic lastSemantic = (Semantic) sequence.get(sequence.size() - 1);
				if(lastSemantic.getFirstNode() == first && lastSemantic.getSemantic() == semantic)
					return;
			}
			sequence.add(new Semantic(semantic, first, last));
		}

		public List<CommentAssociationEntry> getEntryList() {
			return Collections.unmodifiableList(sequence);
		}

		public Semantic getFollowingSemantic(int commentPos, int skipCount) {
			for(int i = commentPos + 1; i < sequence.size(); i++) {
				if(sequence.get(i).isComments())
					continue;
				if(skipCount == 0)
					return (Semantic) sequence.get(i);
				skipCount--;
			}
			return null;
		}

		public Semantic getPrecedingSemantic(int commentPos, int skipCount) {
			for(int i = commentPos - 1; i >= 0; i--) {
				if(sequence.get(i).isComments())
					continue;
				if(skipCount == 0)
					return (Semantic) sequence.get(i);
				skipCount--;
			}
			return null;
		}

		@Override
		public Iterator<CommentAssociationEntry> iterator() {
			return Iterators.unmodifiableIterator(sequence.iterator());
		}
	}

	/**
	 * Describes a "semantic position" based on a semantic object (a "token owner") and
	 * the two leaf nodes "first" / "last" (identical for leaf positions).
	 * 
	 */
	public static class Semantic extends CommentAssociationEntry {
		private EObject semantic;

		private INode first;

		private INode last;

		public Semantic(EObject semantic, INode first, INode last) {
			this.semantic = semantic;
			this.first = first;
			this.last = last;
		}

		public INode getFirstNode() {
			return first;
		}

		public INode getLastNode() {
			return last;
		}

		public EObject getSemantic() {
			return semantic;
		}

		@Override
		public boolean isComments() {
			return false;
		}

	}

	/**
	 * A useful method for debugging - detail printer.
	 * 
	 * @param sequence
	 */
	public static void dumpCommentAssociations(CommentSemanticSequence sequence) {
		StringBuilder builder = new StringBuilder();
		for(CommentAssociationEntry e : sequence) {
			if(e.isComments()) {
				Comments c = (Comments) e;
				builder.append("Comments(").append(c.isRight()
						? "->"
						: "<-").append(" ").append(c.getSkipCount());
				if(c.isFirstOnLine())
					builder.append(" <fol>");
				builder.append("): ");
				for(INode n : ((Comments) e).getComments())
					builder.append(n.getText().replace("\n", "\\n")).append(",");
				builder.append("\n");
			}
			else {
				Semantic semantic = (Semantic) e;
				builder.append("Semantic: ");
				builder.append(semantic.getSemantic().getClass().getSimpleName()).append(", ");
				builder.append(new GrammarElementTitleSwitch().doSwitch(semantic.getFirstNode().getGrammarElement()));
				builder.append(", ");
				builder.append(new GrammarElementTitleSwitch().doSwitch(semantic.getLastNode().getGrammarElement()));
				builder.append("\n");
			}
		}
		System.out.print(builder.toString());

	}

	protected static boolean isFirstOnLine(INode n) {
		if(n == null)
			throw new IllegalArgumentException("given node is null");
		String s = n.getRootNode().getText();
		int offsetOfNode = n.getTotalOffset();
		int offsetOfLastNL = Math.max(0, 1 + CharSequences.lastIndexOf(s, "\n", offsetOfNode - 1));
		return CharSequences.indexOfNonWhitespace(s, offsetOfLastNL) == offsetOfNode;
	}

	protected static int posOnLine(INode n) {
		if(n == null)
			throw new IllegalArgumentException("given node is null");
		String s = n.getRootNode().getText();
		int offsetOfNode = n.getTotalOffset();
		int offsetOfLastNL = Math.max(0, 1 + CharSequences.lastIndexOf(s, "\n", offsetOfNode - 1));
		return offsetOfNode - offsetOfLastNL;
	}

	protected TokenUtil tokenUtil;

	protected ICommentConfiguration<CommentType> commentConfiguration;

	/**
	 * An instance of CommentAssociator uses TokenUtil to answer questions about nodes/tokens, and
	 * needs an ICommentConfiguration to get more detailed information about comments.
	 * 
	 * @param tokenUtil
	 * @param commentConfigurationProvider
	 */
	@Inject
	public CommentAssociator(TokenUtil tokenUtil,
			Provider<ICommentConfiguration<CommentType>> commentConfigurationProvider) {
		this.tokenUtil = tokenUtil;
		this.commentConfiguration = commentConfigurationProvider.get();
	}

	/**
	 * Associates comments in the given {@link ICompositeNode} with positions in a serialization sequence.
	 * 
	 * @param node
	 *            the node for which a reconcilement is wanted
	 * @return an ICommentReconcilement that can be used to reconcile comments in a serialization sequence.
	 */
	public ICommentReconcilement associateComments(ICompositeNode node) {
		CommentSemanticSequence sequence = createSequence(node, commentConfiguration);
		// perform a default lexical left/right association of each comment sequence
		computeLeftAssociations(sequence);

		// perform semantic modifications (association to punctuation)
		computePunctuationAssociations(sequence);

		computeLanguageSpecificAssociations(sequence);

		// dumpCommentAssociations(sequence);

		// encode as ICommentReconcilement
		final List<Pair<Predicate<Triple<INode, INode, INode>>, List<INode>>> predicates = toPredicates(sequence);
		final Map<INode, Comments> commentNodes = Maps.newHashMap();
		for(CommentAssociationEntry e : sequence.getEntryList())
			if(e.isComments())
				for(INode n : ((Comments) e).getComments())
					commentNodes.put(n, (Comments) e);
		// commentNodes.addAll(((Comments) e).getComments());
		return new ICommentReconcilement() {

			@Override
			public List<INode> commentNodesFor(INode preceding, INode last, INode current) {
				Triple<INode, INode, INode> input = Tuples.create(preceding, last, current);
				List<INode> result = Lists.newArrayList();
				for(Pair<Predicate<Triple<INode, INode, INode>>, List<INode>> pair : predicates)
					if(pair.getFirst().apply(input))
						result.addAll(pair.getSecond());
				return result;
			}

			@Override
			public String getWhitespaceBetween(INode prevCommentNode, INode node) {
				Comments c = commentNodes.get(node);
				if(prevCommentNode == null && c != null && c.isFirstOnLine())
					return "\n";
				return "";
			}

			@Override
			public boolean isReconciledCommentNode(INode node) {
				return commentNodes.containsKey(node);
			}
		};
	}

	/**
	 * This implementation does nothing. It is intended that a language specific implementation
	 * further refines the result. This call is given a sequence that already has been processed for lexical
	 * association and punctuation.
	 * 
	 * @param sequence
	 */
	protected void computeLanguageSpecificAssociations(CommentSemanticSequence sequence) {
		// does nothing
	}

	/**
	 * Comments are by default associated with what follows (right association). This method finds and assigns Left association to
	 * those comment sequences that are last on line and not also first on line.
	 * 
	 * @param sequence
	 *            - the sequence to modify
	 */
	protected void computeLeftAssociations(CommentSemanticSequence sequence) {
		for(CommentAssociationEntry e : sequence) {
			if(e.isComments()) {
				Comments c = (Comments) e;
				List<INode> commentNodes = c.getComments();
				if(commentNodes.size() < 1)
					continue;
				INode firstNode = commentNodes.get(0);

				if(!isFirstOnLine(firstNode)) {
					INode lastNode = commentNodes.get(commentNodes.size() - 1);
					// SL comments by definition end the line (even if they lexically do not contain a NL in some grammar).
					// For other types of comments, they may end with NL, and should then be treated the same way.
					if(commentConfiguration.classify(lastNode) == CommentType.SingleLine ||
							lastNode.getText().endsWith("\n"))
						c.setDirectionLeft();
				}
			}
		}
	}

	/**
	 * <p>
	 * This implementation associates comment sequences that are left associative with the +1 preceding semantic element if the immediately preceding
	 * semantic element is a <i>list separator</i> (see {@link #isListSeparator(Semantic)}, and modifies a right associative sequence to left, if the
	 * immediately following semantic element is a right punctuation as determined by {@link #isRightPunctuation(Semantic)}.
	 * </p>
	 * <p>
	 * This means that given:
	 * 
	 * <pre>
	 * foo(a, b /* 1 &#42;/)
	 * </pre>
	 * 
	 * will get an association between the comment 1 and 'b' (as opposed to 1 and the closing ')').
	 * </p>
	 * 
	 * @param sequence
	 */
	protected void computePunctuationAssociations(CommentSemanticSequence sequence) {
		List<CommentAssociationEntry> entries = sequence.getEntryList();
		for(int i = 0; i < entries.size(); i++) {
			CommentAssociationEntry e = entries.get(i);
			if(e.isComments()) {
				Comments c = (Comments) e;
				if(c.isLeft()) {
					if(isListSeparator(sequence.getPrecedingSemantic(i, 0)))
						c.setSkipCount(1); // skip the list separator
				}
				else {
					// a right association to punctuation is changed to left
					if(isRightPunctuation(sequence.getFollowingSemantic(i, 0)))
						c.setDirectionLeft();
				}
			}
		}
	}

	/**
	 * Creates a CommentSemanticSequence for the root node. This sequence is a flattened list of
	 * semantic positions and comment sequences. All comment sequences are associated with what follows (by default).
	 */
	protected CommentSemanticSequence createSequence(ICompositeNode rootNode,
			ICommentConfiguration<CommentType> commentConfiguration) {
		CommentSemanticSequence sequence = new CommentSemanticSequence();
		List<INode> currentComments = Lists.newArrayList();

		NodeIterator nodeIterator = new NodeIterator(rootNode);

		// rewind to previous token with token owner
		while(nodeIterator.hasPrevious()) {
			INode node = nodeIterator.previous();
			if(tokenUtil.isToken(node)) {
				EObject prevEObject = tokenUtil.getTokenOwner(node);
				if(prevEObject != null) {
					// starting location
					sequence.addSemantic(prevEObject, node, getLastLeaf(node));
					break;
				}
			}
		}

		INode node = null;
		while(nodeIterator.hasNext()) {
			node = nodeIterator.next();
			// collect comments...
			if(tokenUtil.isCommentNode(node)) {
				currentComments.add(node);
				continue;
			}
			// skip uninteresting...
			if(!tokenUtil.isToken(node))
				continue;

			// looking at something possibly containing leading comments
			ILeafNode nonHidden = null;
			for(ILeafNode leaf : node.getLeafNodes()) {
				if(!leaf.isHidden()) {
					nonHidden = leaf;
					break;
				}
				else if(tokenUtil.isCommentNode(leaf)) {
					currentComments.add(leaf);
				}
				// else it is whitespace... which is ignored
			}

			// no need to search inside node, since its leading comments and first non hidden are now known.
			nodeIterator.prune();

			// add comment record
			sequence.addComments(currentComments);
			currentComments.clear();

			// add the comments sequence breaking location
			sequence.addSemantic(tokenUtil.getTokenOwner(node), nonHidden, getLastLeaf(node));

			if(node.getOffset() > rootNode.getOffset() + rootNode.getLength()) {
				// found next EObject outside rootNode
				break;
			}
		}
		// deal with trailing comments
		if(!currentComments.isEmpty()) {
			sequence.addComments(currentComments);
			EObject last = tokenUtil.getTokenOwner(node);
			if(last == null)
				last = getEObjectForRemainingComments(rootNode);
			sequence.addSemantic(last, node, getLastLeaf(node));
		}

		return sequence;
	}

	private EObject getEObjectForRemainingComments(ICompositeNode rootNode) {
		TreeIterator<INode> i = rootNode.getAsTreeIterable().iterator();
		while(i.hasNext()) {
			INode o = i.next();
			if(o.hasDirectSemanticElement())
				return o.getSemanticElement();
		}
		return null;
	}

	private INode getFirstLeaf(INode node) {
		for(ILeafNode leaf : node.getLeafNodes()) {
			if(!leaf.isHidden()) {
				return leaf;
			}
		}
		return node;
	}

	private INode getLastLeaf(INode node) {
		while(node instanceof ICompositeNode)
			node = ((ICompositeNode) node).getLastChild();
		return node;
	}

	/**
	 * Produces a predicate for reconciliation of left associated comment sequence.
	 * 
	 * @param semantic
	 * @return
	 */
	protected Predicate<Triple<INode, INode, INode>> getPredicateLeft(final Semantic semantic) {

		return new Predicate<Triple<INode, INode, INode>>() {
			public boolean apply(Triple<INode, INode, INode> o) {
				INode from = o.getSecond();
				// to (third), and preceding (first) are ignored
				if(tokenUtil.getTokenOwner(from) != semantic.getSemantic())
					return false;
				if(from.getGrammarElement() != semantic.getLastNode().getGrammarElement())
					return false;
				return true;
			}
		};
	}

	/**
	 * Produces a predicate for reconciliation of left-skip-one associated comment sequence.
	 * 
	 * @param semantic
	 * @return
	 */
	protected Predicate<Triple<INode, INode, INode>> getPredicateLeftSkipOne(final Semantic semantic,
			final Semantic punctuation) {
		// This if for: <semantic> (preceding/first), <punctuation> (from/second), <comment> <unknown> (to/third)

		return new Predicate<Triple<INode, INode, INode>>() {
			public boolean apply(Triple<INode, INode, INode> o) {
				INode preceding = o.getFirst();
				INode from = o.getSecond();
				Semantic sem = semantic;
				// 'to' (third) is ignored
				if(tokenUtil.getTokenOwner(preceding) != semantic.getSemantic())
					return false;

				if(preceding.getGrammarElement() != sem.getLastNode().getGrammarElement())
					return false;

				if(!isListSeparator(from))
					return false;
				return true;
			}
		};
	}

	/**
	 * Produces a predicate for reconciliation of right associated comment sequence.
	 * 
	 * @param semantic
	 * @return
	 */
	protected Predicate<Triple<INode, INode, INode>> getPredicateRight(final Semantic semantic) {

		return new Predicate<Triple<INode, INode, INode>>() {
			public boolean apply(Triple<INode, INode, INode> o) {
				INode to = o.getThird();
				Semantic sem = semantic;
				// from (second), and preceding (first) are ignored
				if(tokenUtil.getTokenOwner(to) != sem.getSemantic())
					return false;
				if(getFirstLeaf(to).getGrammarElement() != sem.getFirstNode().getGrammarElement())
					return false;
				return true;
			}
		};
	}

	/**
	 * This implementation returns true for a Keyword being a ','. A specialized implementation may do more elaborate checks.
	 * 
	 * @param semantic
	 * @return
	 */
	protected boolean isListSeparator(INode node) {
		if(node == null)
			return false;
		EObject ge = node.getGrammarElement();
		if(ge == null)
			return false;
		return ge instanceof Keyword && ",".equals(node.getText());
	}

	/**
	 * This implementation returns true for a Keyword being a ','. A specialized implementation may do more elaborate checks.
	 * 
	 * @param semantic
	 * @return
	 */
	protected boolean isListSeparator(Semantic semantic) {
		if(semantic == null)
			return false;
		return isListSeparator(semantic.getFirstNode()); // first and last are the same in the case where this is true
	}

	/**
	 * This implementation returns true for a Keyword being one of the characters ',' ';' '}' ']' ')'. A specialized
	 * implementation may do mor elaborate checks.
	 * 
	 * @param semantic
	 * @return
	 */
	protected boolean isRightPunctuation(Semantic semantic) {
		if(semantic == null)
			return false;
		INode node = semantic.getFirstNode(); // first and last are the same when the rest is true
		if(node == null)
			return false;
		EObject ge = node.getGrammarElement();
		if(ge == null)
			return false;
		return ge instanceof Keyword && ",;}])".contains(node.getText());
	}

	protected List<Pair<Predicate<Triple<INode, INode, INode>>, List<INode>>> toPredicates(
			CommentSemanticSequence sequence) {
		List<CommentAssociationEntry> entries = sequence.getEntryList();
		List<Pair<Predicate<Triple<INode, INode, INode>>, List<INode>>> result = Lists.newArrayList();
		for(int i = 0; i < entries.size(); i++) {
			CommentAssociationEntry e = entries.get(i);
			if(e.isComments()) {
				Comments c = (Comments) e;
				if(c.isRight()) {
					if(c.getSkipCount() > 0)
						throw new UnsupportedOperationException("right associated comment and skip count not supported");
					result.add(Tuples.create(getPredicateRight(sequence.getFollowingSemantic(i, 0)), c.getComments()));
				}
				else {
					if(c.getSkipCount() == 0)
						result.add(Tuples.create(getPredicateLeft(sequence.getPrecedingSemantic(i, 0)), c.getComments()));
					else if(c.getSkipCount() == 1)
						result.add(Tuples.create(
							getPredicateLeftSkipOne(
								sequence.getPrecedingSemantic(i, 1), sequence.getPrecedingSemantic(i, 0)),
							c.getComments()));
					else
						throw new UnsupportedOperationException(
							"left associated comment and skip count > 1 not supported");
				}
			}
		}
		return result;
	}

}
