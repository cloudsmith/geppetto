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

import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.AlignmentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.ContainerNamesStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.ContainerStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.DedentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.IndentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.StyleNameStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.TokenTextStyle;

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
	 * @param f
	 *            - function producing a container name
	 * @return style with dynamically determined container name
	 */
	public ContainerStyle container(Function<IDomNode, String> f);

	/**
	 * @param containerName
	 *            - literal container name
	 * @return style with literal container name
	 */
	public ContainerStyle container(String containerName);

	/**
	 * @param f
	 *            - function producing a set of container names
	 * @return style with dynamic set of container names
	 */
	public ContainerNamesStyle containerNames(Function<IDomNode, Set<String>> f);

	/**
	 * @param containerNames
	 *            - a set of literal container names
	 * @return style with literal set of container names
	 */
	public ContainerNamesStyle containerNames(Set<String> containerNames);

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
	 * @return style with dynamically determined {@link LineBreaks} information.
	 */
	public LineBreakStyle lineBreaks(Function<IDomNode, LineBreaks> f);

	/**
	 * @return style with literal {@link LineBreaks} information describing "no line breaks"
	 */
	public LineBreakStyle noLineBreak();

	/**
	 * @return style with literal {@link Spacing} information describing "no space"
	 */
	public SpacingStyle noSpace();

	/**
	 * @return literal {@link LineBreaks} information describing "one line break"
	 */
	public LineBreakStyle oneLineBreak();

	/**
	 * @return literal {@link Spacing} information describing "one space"
	 */
	public SpacingStyle oneSpace();

	/**
	 * @param f
	 *            - function producing {@link Spacing} information
	 * @return dynamically determined {@link Spacing} information
	 */
	public SpacingStyle spacing(Function<IDomNode, Spacing> f);

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

}
