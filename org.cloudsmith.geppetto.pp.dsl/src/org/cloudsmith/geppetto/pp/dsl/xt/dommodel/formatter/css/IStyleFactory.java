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

import com.google.common.base.Function;

/**
 * A Factory for style values - see {@link IStyle}.
 * 
 */
public interface IStyleFactory {

	/**
	 * @see StyleType#align
	 * @param containerNames
	 * @return
	 */
	public IStyle<Alignment> align(Alignment x);

	/**
	 * @see StyleType#align
	 * @param containerNames
	 * @return
	 */
	public IStyle<Alignment> align(Function<IDomNode, Alignment> f);

	/**
	 * @see StyleType#container
	 * @param containerName
	 * @return
	 */
	public IStyle<String> container(Function<IDomNode, String> f);

	/**
	 * @see StyleType#container
	 * @param containerName
	 * @return
	 */
	public IStyle<String> container(String containerName);

	/**
	 * @see StyleType#containerNames
	 * @param containerNames
	 * @return
	 */
	public IStyle<Set<String>> containerNames(Function<IDomNode, Set<String>> f);

	/**
	 * @see StyleType#containerNames
	 * @param containerNames
	 * @return
	 */
	public IStyle<Set<String>> containerNames(Set<String> containerNames);

	/**
	 * Dedent 1.
	 * 
	 * @see StyleType#dedent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> dedent();

	/**
	 * @see StyleType#dedent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> dedent(Function<IDomNode, Integer> f);

	/**
	 * @see StyleType#dedent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> dedent(Integer s);

	/**
	 * Indent 1.
	 * 
	 * @see StyleType#indent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> indent();

	/**
	 * @see StyleType#indent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> indent(Function<IDomNode, Integer> f);

	/**
	 * @see StyleType#indent
	 * @param containerNames
	 * @return
	 */
	public IStyle<Integer> indent(Integer s);

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<LineBreaks> lineBreaks(Function<IDomNode, LineBreaks> f);

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<LineBreaks> noLineBreak();

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<Spacing> noSpace();

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<LineBreaks> oneLineBreak();

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<Spacing> oneSpace();

	/**
	 * @see StyleType#spacing
	 * @param containerNames
	 * @return
	 */
	public IStyle<Spacing> spacing(Function<IDomNode, Spacing> f);

	/**
	 * @see StyleType#styleName
	 * @param containerNames
	 * @return
	 */
	public IStyle<String> styleName(Function<IDomNode, String> f);

	/**
	 * @see StyleType#styleName
	 * @param containerNames
	 * @return
	 */
	public IStyle<String> styleName(String s);

	/**
	 * @see StyleType#tokenText
	 * @param containerNames
	 * @return
	 */
	public IStyle<String> tokenText(Function<IDomNode, String> f);

	/**
	 * @see StyleType#tokenText
	 * @param containerNames
	 * @return
	 */
	public IStyle<String> tokenText(String s);

}
