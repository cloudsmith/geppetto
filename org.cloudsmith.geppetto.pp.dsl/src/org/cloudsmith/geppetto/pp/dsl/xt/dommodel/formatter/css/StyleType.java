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

public enum StyleType {
	/**
	 * Sets the style name. Value is a {@link String}. This style can be used when semantic styling is wanted.
	 * The name is some user defined style name. Ignored by formatting.
	 */
	styleName,

	/**
	 * Sets the alignment of an individual item in a container. Value is an {@link Alignment}.
	 */
	align,

	/**
	 * Sets the names of containers available in a node. Value is a {@link List<String>}.
	 */
	containerNames,

	/**
	 * Sets the container for a node. The first found parent with a container with this name is used.
	 */
	container,

	/**
	 * The wanted spacing. Value is a Spacing.
	 */
	spacing,

	/**
	 * The wanted line breaks. Value is a LineBreaks.
	 */
	lineBreaks,

	/**
	 * Output after next linebreak is indented. The value indicates number of indentations.
	 */
	indent,

	/**
	 * Output after next linebreak is dedented. The value indicates number of dedentations.
	 */
	dedent,

	/**
	 * The token text to output. The value is of String type.
	 */
	tokenText,

}
