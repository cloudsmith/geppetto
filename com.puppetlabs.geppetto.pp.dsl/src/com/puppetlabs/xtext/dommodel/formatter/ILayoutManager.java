/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.xtext.dommodel.formatter;

import com.puppetlabs.xtext.dommodel.IDomNode;
import com.puppetlabs.xtext.dommodel.formatter.context.IFormattingContext;
import com.puppetlabs.xtext.dommodel.formatter.css.DomCSS;
import com.puppetlabs.xtext.dommodel.formatter.css.StyleSet;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import com.puppetlabs.xtext.textflow.ITextFlow;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;

/**
 * An ILayoutManager adds handling of before/after composite dom nodes and expects a sequence of calls for a composite node:
 * <ul>
 * <li>{@link #beforeComposite(StyleSet, IDomNode, ITextFlow, ILayoutContext)}</li>
 * <li>{@link #format(IDomNode, ITextFlow, ILayoutContext)}</li>
 * <li>{@link #afterComposite(StyleSet, IDomNode, ITextFlow, ILayoutContext)}</li>
 * </ul>
 * 
 * 
 */
public interface ILayoutManager extends ILayout {

	public interface ILayoutContext extends IFormattingContext {
		/**
		 * 
		 * @return the style sheet to use
		 */
		public DomCSS getCSS();

		/**
		 * 
		 * @return where any errors should be emitted
		 */
		public Acceptor getErrorAcceptor();

		/**
		 * 
		 * @return the indentation information to use
		 */
		public IIndentationInformation getIndentationInformation();

		/**
		 * 
		 * @return the line separator information to use
		 */
		public ILineSeparatorInformation getLineSeparatorInformation();

		/**
		 * 
		 * @return the text region to format (or null for "everything").
		 */
		public ITextRegion getRegionToFormat();

		/**
		 * Returns true if the given node has been marked as consumed. See {@link #markConsumed(IDomNode)}.
		 * 
		 * @param node
		 * @return
		 */
		public boolean isConsumed(IDomNode node);

		/**
		 * 
		 * @return true if existing (non implied) white space should be preserved
		 */
		public boolean isWhitespacePreservation();

		/**
		 * Marks the given node as consumed - a formatter that gets a node that is marked consumed
		 * should simply ignore the node as its text has already been included /handled by an earlier
		 * rule.
		 * 
		 * @param node
		 */
		public void markConsumed(IDomNode node);
	}

	/**
	 * Called after all children of a composite have been processed. This call is always performed (even if format of the composite
	 * returned true).
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	public void afterComposite(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context);

	/**
	 * Called before format of a composite.
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	public void beforeComposite(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context);

	/**
	 * Formats the dom node and produces output in the flow.
	 * 
	 * @param dom
	 * @param flow
	 * @param context
	 * @return true if the layout manager processed all children of the given node
	 */
	public boolean format(IDomNode dom, ITextFlow flow, ILayoutContext context);

	/**
	 * Formats the dom node and produces output in the flow. The given style set is the styles that
	 * should be applied to the given dom node. (This is typically the result of collecting the style from
	 * the css passed in the layout context).
	 * 
	 * @param styleset
	 * @param dom
	 * @param flow
	 * @param context
	 * @return true if the layout manager processed all children of the given node
	 */
	public boolean format(StyleSet styleset, IDomNode dom, ITextFlow flow, ILayoutContext context);
}
