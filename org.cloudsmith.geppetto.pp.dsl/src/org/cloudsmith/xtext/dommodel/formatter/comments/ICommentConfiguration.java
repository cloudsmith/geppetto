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
package org.cloudsmith.xtext.dommodel.formatter.comments;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.ImplementedBy;

/**
 * <p>
 * An ICommentConfiguration describes the configuration of comment (container-) extraction, parsing, and formatting.
 * </p>
 * <p>
 * It is responsible for <i>comment classification</i> - associating a more specific comment type for an IDomNode representing a comment. The class
 * used to describe the comment type is configurable via the generic type {@code T}. The default implementation uses {@link CommentType} as {@code T}.
 * This type is expected by the default configuration of comment formatting, but a custom implementation may use a different type (throughout) if
 * needed).
 * </p>
 * <p>
 * The comment type classification can be used in a CSS selector, thus allowing a layout manager to be selected per comment type. The default layout
 * manager is capable of providing reasonable formatting as described by an {@code ICommentConfiguration}, but can naturally be customized.
 * </p>
 * <p>
 * Since the style classifiers of an IDomNode is a Set of Objects, a concrete implementation may choose any suitable instances to describe the various
 * types of comments in the DSL language being laid out. This interface contains an enumerator for the three most common comments types (
 * {@code Singleline}, {@code Multiline}, and {@code Documentation}), but this enumerator says nothing about the interior syntax and semantics of the
 * respective comments.
 * </p>
 * 
 * <p>
 * Note that the default {@code ICommentConfiguration} implementation assumes that the three default comment types use Java conventions. Simply bind a
 * specialization of the {@link ICommentConfiguration.Default} class, or a different/more advanced implementation when so required:
 * <ul>
 * <li>If comment grammar rules are not "ML_COMMENT" and "SL_COMMENT". (This also requires customization of the {@code IHiddenTokenHelper} class which
 * is used to determine if a token is a comment (of any type).</li>
 * <li>If comments do not follow Java comment syntax (e.g. using '#' instead of '//' for single line comments.</li>
 * <li>A different comment type scheme is wanted than {@link CommentType}.</li>
 * <li>If different types of comments should be formatted with different {@link ICommentFormatterAdvice}.</li>
 * </ul>
 * </p>
 * <p>
 * To summarize: The {@code ICommentConfiguration} classifies a comment. The resulting classifier is used to select styling (i.e. a selection of an
 * {@code ILayoutManager} and and other style information as determined by a CSS driven formatter). The selected ILayoutManager is responsible for the
 * formatting of the comment(s). By default an ILayoutManager is configured for comments that use an {@code ICommentConfiguration} to map the
 * {@code CommentType} to a description of the comment's syntax and semantics ({@link #getContainerInformation(Object)}), and a description of wanted
 * formatting ({@link #getFormatterAdvice(Object)}). It is the responsibility of the selected {@code ILayoutManager} to apply the container
 * information and formatting advice to the comment using an implementation specific comment processor.
 * </p>
 * 
 */
@ImplementedBy(ICommentConfiguration.Default.class)
public interface ICommentConfiguration<T> {

	public static enum CommentType {
		/**
		 * Returned when a comment classifier is asked to classify something that
		 * is not one of the known comment types (or not a comment at all).
		 */
		Unknown, SingleLine, Multiline, Documentation;
	}

	/**
	 * <p>
	 * An implementation of {@link ICommentConfiguration} that classifies comments based on the grammar rule name of the comment terminals;
	 * "ML_COMMENT", and "SL_COMMENT". This implementation returns {@link CommentType#Unknown} for all other nodes.
	 * </p>
	 * <p>
	 * When asked to classify an IDomNode, this node must either have an INode attached, or an attached grammar rule that describes the comment type.
	 * </p>
	 */
	public static class Default implements ICommentConfiguration<CommentType> {

		private static final ICommentFormatterAdvice defaultAdvice = new ICommentFormatterAdvice.DefaultCommentAdvice();

		@Override
		public CommentType classify(EObject grammarElement) {
			if(grammarElement != null && grammarElement instanceof AbstractRule) {
				AbstractRule rule = (AbstractRule) grammarElement;

				if("ML_COMMENT".equals(rule.getName()))
					return CommentType.Multiline;
				if("SL_COMMENT".equals(rule.getName()))
					return CommentType.SingleLine;
			}
			return CommentType.Unknown;

		}

		/**
		 * Returns the comment type based on the node's attached {@code INode} if available, otherwise
		 * the node's attached <i>grammar element</i>. The node must be a leaf node.
		 */
		@Override
		public CommentType classify(IDomNode node) {
			INode inode = node.getNode();
			if(inode != null)
				return classify(inode);
			if(node.isLeaf())
				return classify(node.getGrammarElement());
			return CommentType.Unknown;
		}

		@Override
		public CommentType classify(INode node) {
			EObject ge = node.getGrammarElement();
			if(node instanceof ILeafNode)
				return classify(ge);
			return CommentType.Unknown;
		}

		@Override
		public ICommentContainerInformation getContainerInformation(CommentType commentType) {
			// Preconditions.checkArgument(genericCommentType instanceof CommentType, "must be instance of CommentType");
			// CommentType commentType = (CommentType) genericCommentType;
			switch(commentType) {
				case SingleLine:
					return new ICommentContainerInformation.JavaSLCommentContainer();
				case Multiline:
					return new ICommentContainerInformation.JavaLikeMLCommentContainer();
				case Documentation:
					return new ICommentContainerInformation.JavaDocLikeCommentContainer();
			}
			// Unknown type
			return new ICommentContainerInformation.UnknownCommentContainer();
		}

		/**
		 * @return {@link DefaultCommentFormattingAdvice} irrespective of {@code commentType}.
		 */
		@Override
		public ICommentFormatterAdvice getFormatterAdvice(CommentType commentType) {
			return defaultAdvice;
		}
	}

	public T classify(EObject abstractRule);

	public T classify(IDomNode node);

	public T classify(INode node);

	/**
	 * Returns an {@link ICommentContainerInformation} for the given commentType (a classifier produced
	 * by one of the {@code classify} methods, typically an instance of {@link CommentType}), describing
	 * the expected container for this type of comment.
	 * 
	 * @param commentType
	 *            describes the comment type
	 * @return information about the comment container of the given {@code commentType}
	 */
	public ICommentContainerInformation getContainerInformation(T commentType);

	/**
	 * Returns an {@link ICommentFormatterAdvice} describing how a comment of the given commentType (a classifier produced
	 * by one of the {@code classify} methods, typically an instance of {@link CommentType}) should be formatted
	 * with respect to its textual content and in relationship to its container.
	 * 
	 * @param commentType
	 *            describes the comment type
	 * @return formatting advice for a comment of the given {@code commentType}
	 */
	public ICommentFormatterAdvice getFormatterAdvice(T commentType);
}
