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
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.AlignmentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.DedentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.IndentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LayoutManagerStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.StyleNameStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.VerbatimStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.WidthStyle;

import com.google.common.base.Function;
import com.google.inject.ImplementedBy;

/**
 * A Factory for basic DOM styling - see {@link IStyle}.
 * 
 * Note to implementors: To extend formatting, simply created wanted {@link IStyle} implementations
 * that are supported by an extended formatter. Do not extend this interface.
 */
@ImplementedBy(StyleFactory.class)
public interface IStyleFactory {

	/**
	 * @param x
	 *            the alignment
	 * @return style with literal {@link Alignment}
	 */
	public AlignmentStyle align(Alignment x);

	/**
	 * @param f
	 *            - function producing {@link Alignment}
	 * @return style with dynamic {@link Alignment}
	 */
	public AlignmentStyle align(Function<IDomNode, Alignment> f);

	/**
	 * Dedent 1.
	 * 
	 * @return style with dedent count 1 (number of indents to reverse)
	 */
	public DedentStyle dedent();

	/**
	 * @param f
	 *            - function producing integer dedent value
	 * @return style with dynamically determined dedent count (number of indents to reverse)
	 */
	public DedentStyle dedent(Function<IDomNode, Integer> f);

	/**
	 * @param i
	 * @return style with literal dedent (number of indents to reverse)
	 */
	public DedentStyle dedent(Integer i);

	/**
	 * Indent 1.
	 * 
	 * @param containerNames
	 * @return style with one literal indent
	 */
	public IndentStyle indent();

	/**
	 * @param containerNames
	 * @return style with dynamically determined indent (number of indents)
	 */
	public IndentStyle indent(Function<IDomNode, Integer> f);

	/**
	 * @param i
	 * @return style with literal number of indents
	 */
	public IndentStyle indent(Integer i);

	/**
	 * @param f
	 * @return style with dynamically determined layout manager
	 */
	LayoutManagerStyle layout(Function<IDomNode, ILayoutManager> f);

	/**
	 * @param manager
	 * @return style with layout manager
	 */
	LayoutManagerStyle layout(ILayoutManager manager);

	/**
	 * @param f
	 * @return style with dynamically determined {@link LineBreaks} information.
	 */
	public LineBreakStyle lineBreaks(Function<IDomNode, LineBreaks> f);

	/**
	 * @See {@link LineBreaks}
	 * @param min
	 * @param normal
	 * @param max
	 * @return
	 */
	public LineBreakStyle lineBreaks(int min, int normal, int max);

	public LineBreakStyle lineBreaks(int min, int normal, int max, boolean acceptCommentWithBreak,
			boolean acceptExisting);

	/**
	 * @return style with literal {@link LineBreaks} information describing "no line breaks"
	 */
	public LineBreakStyle noLineBreak();

	/**
	 * @return style with literal {@link Spacing} information describing "no space" that may not be wrapped.
	 */
	public SpacingStyle noSpace();

	/**
	 * @return style with literal {@link Spacing} information describing "no space" but that may be wrapped.
	 */
	public SpacingStyle noSpaceUnlessWrapped();

	/**
	 * @return literal {@link LineBreaks} information describing "one line break"
	 */
	public LineBreakStyle oneLineBreak();

	/**
	 * @return literal {@link Spacing} information describing "one space". This space may not be wrapped
	 */
	public SpacingStyle oneNonWrappableSpace();

	/**
	 * @return literal {@link Spacing} information describing "one space". This space may be wrapped
	 */
	public SpacingStyle oneSpace();

	/**
	 * @param f
	 * @return
	 */
	IndentStyle separatorAlignmentIndex(Function<IDomNode, Integer> f);

	/**
	 * @param i
	 * @return
	 */
	IndentStyle separatorAlignmentIndex(Integer i);

	/**
	 * @param f
	 *            - function producing {@link Spacing} information
	 * @return dynamically determined {@link Spacing} information
	 */
	public SpacingStyle spacing(Function<IDomNode, Spacing> f);

	/**
	 * @param min
	 * @param normal
	 * @param max
	 * @return
	 */
	SpacingStyle spacing(int min, int normal, int max);

	/**
	 * @return style with dynamically determined "style name" (for open ended use).
	 */
	public StyleNameStyle styleName(Function<IDomNode, String> f);

	/**
	 * @return style with literal "style name" (for open ended use)
	 */
	public StyleNameStyle styleName(String s);

	/**
	 * @param f
	 *            - function producing token text
	 * @return a style with dynamically determined token text
	 */
	public TokenTextStyle tokenText(Function<IDomNode, String> f);

	/**
	 * @param s
	 *            - literal token text
	 * @return a style with literal token text
	 */
	public TokenTextStyle tokenText(String s);

	/**
	 * @param b
	 * @return
	 */
	VerbatimStyle verbatim(boolean b);

	/**
	 * @param f
	 * @return
	 */
	VerbatimStyle verbatim(Function<IDomNode, Boolean> f);

	/**
	 * @param f
	 * @return
	 */
	WidthStyle width(Function<IDomNode, Integer> f);

	/**
	 * @param i
	 * @return
	 */
	WidthStyle width(Integer i);

}
