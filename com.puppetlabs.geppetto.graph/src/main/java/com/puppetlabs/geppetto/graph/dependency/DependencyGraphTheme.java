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
package com.puppetlabs.geppetto.graph.dependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.puppetlabs.graph.graphcss.IFunctionFactory;
import com.puppetlabs.graph.graphcss.Rule;
import com.puppetlabs.graph.graphcss.Select;
import com.puppetlabs.graph.style.Alignment;
import com.puppetlabs.graph.style.Arrow;
import com.puppetlabs.graph.style.IStyleFactory;
import com.puppetlabs.graph.style.LineType;
import com.puppetlabs.graph.style.NodeShape;
import com.puppetlabs.graph.style.themes.DefaultStyleTheme;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Extends the default style theme with dependency graph specifics.
 * 
 */
@Singleton
public class DependencyGraphTheme extends DefaultStyleTheme implements DependencyGraphStyles {
	@Inject
	IStyleFactory styles;

	@Inject
	IFunctionFactory functions;

	public static final String ERROR_COLOR = "#ff0000";

	public static final String COLOR__ORANGE = "#ffa144";

	/* (non-Javadoc)
	 * 
	 * @see com.puppetlabs.graph.style.themes.DefaultStyleTheme#getInstanceRules() */
	@Override
	public Collection<Rule> getInstanceRules() {
		ArrayList<Rule> rules = Lists.newArrayList();
		rules.addAll(super.getInstanceRules());

		// ---VERTEX STYLES

		// Resolved Modules
		// TODO: Add a URL to the module itself (can be done by instance as they are all unique)
		// - uses default shape
		// - label is set per instance
		Collections.addAll(rules, Select.vertex(STYLE_CLASS_RESOLVED_MODULE).withStyles(//
			styles.lineColor(COLOR__DARKEST_GREY), //
			styles.shape(NodeShape.ellipse), //
			styles.tooltip("Module"), //
			styles.id(functions.idClassReplacer()), //
			styles.href("")));

		Collections.addAll(rules, Select.vertex(STYLE_CLASS_AMBIGUOUSLY_RESOLVED_MODULE).withStyles(//
			styles.lineColor(COLOR__DARKEST_GREY), //
			styles.color(COLOR__ORANGE), //
			styles.shape(NodeShape.ellipse), //
			styles.tooltip("Module"), //
			styles.id(functions.idClassReplacer()), //
			styles.tooltip("More than one module with the same name"), //
			styles.href("")));

		Collections.addAll(rules, Select.vertex(STYLE_CLASS_PPNODE_MODULE).withStyles(//
			styles.lineColor(COLOR__DARKEST_GREY), //
			styles.shape(NodeShape.diamond), //
			styles.tooltip("Role"), //
			styles.id(functions.idClassReplacer()), //
			styles.href("")));

		Collections.addAll(rules, Select.vertex(STYLE_CLASS_ROOT).withStyles(//
			styles.lineColor(COLOR__DARKEST_GREY), //
			styles.shape(NodeShape.component), // box with hinges look ok
			styles.tooltip("Stack"), //
			styles.id(functions.idClassReplacer()), //
			styles.href("") //
		));

		// Unresolved Modules (i.e. one that does not exist)
		Collections.addAll(rules, Select.vertex(STYLE_CLASS_UNRESOLVED_MODULE).withStyles(//
			styles.shape(NodeShape.ellipse),//
			styles.color(ERROR_COLOR), // red text
			styles.lineColor(ERROR_COLOR), // red outline
			styles.shapeBrush(LineType.dotted, 1.0, true, true), //
			styles.id(functions.idClassReplacer()), //
			styles.tooltip("Unresolved Module") //

		));

		// vertex representing a list of imports
		Collections.addAll(rules, Select.vertex(STYLE_CLASS_IMPORTS).withStyles(//
			styles.fontSize(7), //
			styles.shape(NodeShape.rectangle), //
			styles.id(functions.idClassReplacer()) //
		));

		// vertex representing a list of unresolved imports
		Collections.addAll(rules, Select.vertex(STYLE_CLASS_UNRESOLVED_IMPORTS).withStyles(//
			styles.fontSize(7), //
			styles.color(ERROR_COLOR), //
			styles.lineColor(ERROR_COLOR), //
			styles.shapeBrush(LineType.dotted, 1.0, true, true), //
			styles.id(functions.idClassReplacer()), //
			styles.shape(NodeShape.rectangle) //
		));

		// -- LABEL STYLES
		Collections.addAll(rules, Select.table(STYLE__IMPORT_TABLE).withStyles(//
			styles.rendered(true) // import tables always render the table (it is by definition not empty).
		));

		// cells in import tables should be left adjusted
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.table(STYLE__IMPORT_TABLE)), Select.cell()).withStyle( //
				styles.align(Alignment.left)));

		Collections.addAll(rules, Select.cell(STYLE__IMPORT_AMBIGUOUS_NAME_CELL).withStyles(//
			styles.tooltip("Found in more than one module (possibly in the module itself)"), //
			styles.color(COLOR__ORANGE) // same as edge label text
		));
		Collections.addAll(rules, Select.cell(STYLE__IMPORT_TYPE_CELL).withStyles(//
			styles.color(COLOR__DARK_GREY) // same as edge label text
		));

		// ---EDGE STYLES

		// Edge between module and imported list
		Collections.addAll(rules, Select.edge(STYLE_EDGE__IMPORT).withStyles(//
			styles.arrowHead(Arrow.none), //
			styles.weight(3.0), // Bring it close to module
			styles.id(functions.idClassReplacer()) //
		));

		// Edge between module and unresolved imported list
		Collections.addAll(rules, Select.edge(STYLE_EDGE__UIMPORT).withStyles(//
			styles.color(ERROR_COLOR), //
			styles.arrowHead(Arrow.none), //
			styles.lineColor(ERROR_COLOR), //
			styles.weight(3.0), // Bring it close to module
			styles.id(functions.idClassReplacer()) //
		));

		// Edge between list or module and resolved module
		Collections.addAll(rules, Select.edge(STYLE_EDGE__RESOLVED_DEP).withStyles(//
			styles.arrowHead(Arrow.vee), //
			styles.id(functions.idClassReplacer()) //
		));

		// Edge between list or module and resolved module
		Collections.addAll(rules, Select.edge(STYLE_EDGE__UNRESOLVED_DEP).withStyles(//
			styles.color(ERROR_COLOR), //
			styles.arrowHead(Arrow.vee), //
			styles.lineColor(ERROR_COLOR), //
			styles.id(functions.idClassReplacer()) //
		));

		// Edge between list or module and resolved module
		Collections.addAll(rules, Select.edge(STYLE_EDGE__UNRESOLVED_IMPLIED_DEP).withStyles(//
			styles.color(ERROR_COLOR), //
			styles.arrowHead(Arrow.vee), //
			styles.lineColor(ERROR_COLOR), //
			styles.lineBrush(LineType.dotted, 1.0), //
			styles.id(functions.idClassReplacer()) //
		));

		// Edge between list or module and resolved module
		Collections.addAll(rules, Select.edge(STYLE_EDGE__IMPLIED_DEP).withStyles(//
			styles.arrowHead(Arrow.vee), //
			styles.lineBrush(LineType.dotted, 1.0), //
			styles.id(functions.idClassReplacer()) //
		));

		return rules;
	}
}
