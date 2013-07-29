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
package org.cloudsmith.geppetto.graph.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.cloudsmith.graph.graphcss.IFunctionFactory;
import org.cloudsmith.graph.graphcss.Rule;
import org.cloudsmith.graph.graphcss.Select;
import org.cloudsmith.graph.style.Alignment;
import org.cloudsmith.graph.style.Arrow;
import org.cloudsmith.graph.style.IStyleFactory;
import org.cloudsmith.graph.style.LineType;
import org.cloudsmith.graph.style.NodeShape;
import org.cloudsmith.graph.style.VerticalAlignment;
import org.cloudsmith.graph.style.themes.DefaultStyleTheme;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Extends the default style theme with catalog graph specifics.
 * 
 */
@Singleton
public class CatalogGraphTheme extends DefaultStyleTheme implements CatalogGraphStyles {
	@Inject
	IStyleFactory styles;

	@Inject
	IFunctionFactory functions;

	public static final String ERROR_COLOR = "#ff0000";

	public static final String COLOR__ORANGE = "#db7324"; // "#c56820"; // "#ffa144"; darker orange

	public static final String ADDED_COLOR = "#229a19"; // "#00ff00"; darker green

	public static final String REMOVED_COLOR = "#c5202b"; // "#ff0000"; darker red

	public static final String MODIFIED_COLOR = COLOR__ORANGE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.graph.style.themes.DefaultStyleTheme#defaultFontFamily()
	 */
	@Override
	public String defaultFontFamily() {
		return super.defaultFontFamily(); // i.e. "Verdana"
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.graph.style.themes.DefaultStyleTheme#getInstanceRules()
	 */
	@Override
	public Collection<Rule> getInstanceRules() {
		ArrayList<Rule> rules = Lists.newArrayList();
		rules.addAll(super.getInstanceRules());

		// ---VERTEX STYLES

		// vertex representing a CatalogResource
		Collections.addAll(rules, Select.vertex(STYLE_Resource).withStyles(//
			styles.fontSize(8), //
			styles.shape(NodeShape.rectangle), //
			styles.href("")
		// styles.id(functions.idClassReplacer()) //
		));

		// vertex representing a CatalogResource
		Collections.addAll(rules, Select.vertex(STYLE_MissingResource).withStyles(//
			styles.fontSize(8), //
			styles.shape(NodeShape.rectangle), //
			styles.href(""), //
			styles.shapeBrush(LineType.solid, 1, true, true), //
			styles.fillColor(ERROR_COLOR), //
			styles.color("#ffffff") //
		// styles.id(functions.idClassReplacer()) //
		));

		// vertex representing an ADDED CatalogResource
		Collections.addAll(rules, Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Added)).withStyles(//
			styles.color(ADDED_COLOR), //
			styles.lineColor(ADDED_COLOR) //
		));
		// vertex representing a REMOVED CatalogResource
		Collections.addAll(rules, Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Removed)).withStyles(//
			styles.color(REMOVED_COLOR), //
			styles.lineColor(REMOVED_COLOR), //
			styles.shapeBrush(LineType.dotted, 1.0, false, true) //
		));
		// vertex representing a MODIFIED CatalogResource
		Collections.addAll(rules, Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Modified)).withStyles(//
			styles.lineColor(MODIFIED_COLOR) //
		// styles.shapeBrush(LineType.dashed, 1.0, false, true), //
		// styles.shape(NodeShape.Msquare)//
		));

		// -- LABEL STYLES
		Collections.addAll(rules, Select.table(STYLE_ResourceTable).withStyles(//
			styles.rendered(true), // The resource table is always rendered
			styles.align(Alignment.left) //
		));

		// Resource Title (bigger and with a bit of distance)
		Collections.addAll(
			rules, //
			Select.and(Select.containment(Select.table(STYLE_ResourceTable)), Select.cell(STYLE_ResourceTitleCell)).withStyles(//
				// styles.color(COLOR__DARKEST_GREY), //
				// styles.cellPadding(5), //
				styles.align(Alignment.left) //
			));

		// Title color
		Collections.addAll(rules, Select.cell(STYLE_ResourceTitleCell).withStyles(//
			styles.fontSize(8), //
			styles.color(COLOR__ALMOST_BLACK) //
		));

		// Tweak the title color if it's vertex is added, removed, or modified
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Modified))), //
				Select.cell(STYLE_ResourceTitleCell)).withStyles(//
				styles.color(MODIFIED_COLOR) //
			));
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Removed))), //
				Select.cell(STYLE_ResourceTitleCell)).withStyles(//
				styles.color(REMOVED_COLOR) //
			));
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.vertex(Lists.newArrayList(STYLE_Resource, STYLE_Added))), //
				Select.cell(STYLE_ResourceTitleCell)).withStyles(//
				styles.color(ADDED_COLOR) //
			));

		Collections.addAll(rules, Select.cell(STYLE_ResourcePropertyName).withStyles(//
			styles.fontSize(8), //
			// styles.backgroundColor("#ffdddd"), // DEBUG COLOR some kind of pink
			styles.align(Alignment.left),//
			styles.cellSpacing(0), //
			styles.cellPadding(0) //
		));

		// Value color
		Collections.addAll(rules, Select.cell(STYLE_ResourcePropertyValue).withStyles(//
			styles.fontSize(8), //
			styles.color(COLOR__DARK_GREY), //
			// styles.backgroundColor("#ddddff"), // DEBUG COLOR some kind of baby blue
			styles.align(Alignment.left),//
			styles.cellSpacing(0), //
			styles.cellPadding(0) //
		));

		// Resource Footer with file and line info
		Collections.addAll(
			rules, //
			Select.and(Select.containment(Select.table(STYLE_ResourceTable)), Select.cell(STYLE_ResourceFileInfoCell)).withStyles(//
				styles.fontSize(7), //
				styles.color(COLOR__MID_GREY), //
				styles.align(Alignment.left) //
			));

		// A Row that acts like a HR separator line
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.table(STYLE_ResourceTable)), Select.cell("HRCell")).withStyles(//
				styles.height(0.2), //
				styles.backgroundColor(COLOR__LIGHT_GREY), //
				styles.cellPadding(0), //
				// styles.width(100), //
				styles.align(Alignment.center) //
			));

		// A Row that acts like a space separator line
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.table(STYLE_ResourceTable)), Select.cell("SpacingCell")).withStyles(//
				styles.height(0.2), //
				styles.cellPadding(3), //
				// styles.width(100), //
				styles.align(Alignment.center) //
			));

		// cells in CatalogResource tables should be left adjusted
		Collections.addAll(rules, //
			Select.and(Select.containment(Select.table(STYLE_ResourceTable)), Select.cell()).withStyles( //
				styles.verticalAlign(VerticalAlignment.middle), // modified parameters have rowspan 2
				styles.align(Alignment.left)));

		// Modified
		Collections.addAll(rules, Select.cell(STYLE_Modified).withStyles(//
			styles.tooltip("Modified"), //
			styles.color(MODIFIED_COLOR)));
		// Removed
		Collections.addAll(rules, Select.cell(STYLE_Removed).withStyles(//
			styles.tooltip("Old Value"), //
			styles.color(REMOVED_COLOR)));
		// Removed
		Collections.addAll(rules, Select.cell(STYLE_Added).withStyles(//
			styles.tooltip("New Value"), //
			styles.color(ADDED_COLOR)));

		Collections.addAll(rules, Select.cell(STYLE_ResourcePropertyMarker).withStyles(//
			styles.fontSize(8), //
			styles.color("#ffffff"), //
			styles.align(Alignment.center) //
		));
		Collections.addAll(
			rules, Select.cell(Lists.newArrayList(STYLE_ResourcePropertyMarker, STYLE_Added)).withStyles(//
				// styles.backgroundColor(ADDED_COLOR), //
				styles.color(ADDED_COLOR), //
				styles.align(Alignment.text), //
				styles.verticalAlign(VerticalAlignment.middle) //
			));
		Collections.addAll(
			rules, Select.cell(Lists.newArrayList(STYLE_ResourcePropertyMarker, STYLE_Removed)).withStyles(//
				// styles.backgroundColor(REMOVED_COLOR), //
				styles.color(REMOVED_COLOR), //
				styles.align(Alignment.text), //
				styles.verticalAlign(VerticalAlignment.middle) //
			));

		// ---EDGE STYLES

		// Normal edge
		Collections.addAll(rules, Select.edge(STYLE_ResourceEdge).withStyles(//
			styles.arrowHead(Arrow.vee), //
			styles.weight(3.0) // Bring it close to resource
		// styles.id(functions.idClassReplacer()) //
		));

		// Removed edge
		Collections.addAll(rules, Select.edge(STYLE_Removed).withStyles(//
			styles.color(REMOVED_COLOR), //
			styles.lineColor(REMOVED_COLOR), //
			styles.lineBrush(LineType.dotted, 1.0), //
			styles.weight(0.0) // Should not affect layout too much
		// styles.id(functions.idClassReplacer()) //
		));

		// Added edge
		Collections.addAll(rules, Select.edge(STYLE_Added).withStyles(//
			styles.color(ADDED_COLOR), //
			styles.lineColor(ADDED_COLOR) //
		));

		// Edge to missing resource
		Collections.addAll(rules, //
			Select.between(Select.any(), Select.vertex(STYLE_MissingResource)).withStyles(//
				styles.color(ERROR_COLOR), //
				styles.lineColor(ERROR_COLOR) //
			));

		// Edge to removed resource, where edge is not removed
		Collections.addAll(rules, //
			Select.and( //
				Select.not(Select.edge(STYLE_Removed)), //
				Select.between(Select.any(), Select.vertex(STYLE_Removed))//
			).withStyles(//
				styles.color(ERROR_COLOR), //
				styles.lineColor(ERROR_COLOR) //
			));
		return rules;
	}
}
