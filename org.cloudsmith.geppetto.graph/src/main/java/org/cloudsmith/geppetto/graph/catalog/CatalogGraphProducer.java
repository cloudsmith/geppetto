/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.graph.catalog;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.CatalogEdge;
import org.cloudsmith.geppetto.catalog.CatalogResource;
import org.cloudsmith.geppetto.catalog.CatalogResourceParameter;
import org.cloudsmith.graph.ICancel;
import org.cloudsmith.graph.elements.Edge;
import org.cloudsmith.graph.elements.RootGraph;
import org.cloudsmith.graph.elements.Vertex;
import org.cloudsmith.graph.graphcss.GraphCSS;
import org.cloudsmith.graph.graphcss.StyleSet;
import org.cloudsmith.graph.style.Span;
import org.cloudsmith.graph.style.labels.LabelRow;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Produces a Catalog graph in DOT format.
 * 
 */
public class CatalogGraphProducer extends AbstractCatalogGraphProducer implements CatalogGraphStyles {

	private Vertex createVertexFor(CatalogResource resource, IPath root) {
		Vertex v = new Vertex("", STYLE_Resource);
		v.setStyles(labelStyleForResource(resource, root));
		return v;
	}

	private StyleSet labelStyleForResource(CatalogResource r, final IPath root) {

		// Produce a sorted list of parameters (that skips parameters that are really dependencies)
		List<LabelRow> labelRows = Lists.newArrayList();
		CatalogResourceParameter[] sortedProperties = Iterables.toArray(
			Iterables.filter(getParameterIterable(r), regularParameterPredicate), CatalogResourceParameter.class);

		Arrays.sort(sortedProperties, new Comparator<CatalogResourceParameter>() {

			@Override
			public int compare(CatalogResourceParameter o1, CatalogResourceParameter o2) {
				return o1.getName().compareTo(o2.getName());
			}

		});

		// Add a labelRow for the type[id]
		StringBuilder builder = new StringBuilder();
		builder.append(r.getType());
		builder.append("[");
		builder.append(r.getTitle());
		builder.append("]");

		int width = 0;
		boolean hasParameters = sortedProperties.length > 0;
		boolean hasFooter = r.getFile() != null;
		if(hasParameters || hasFooter)
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(3))));

		labelRows.add(getStyles().labelRow(STYLE_ResourceTitleRow, //
			getStyles().labelCell(STYLE_ResourceTitleCell, builder.toString(), Span.colSpan(3))));
		width += builder.length();

		// Rendering of separator line fails in graphviz 2.28 with an error
		// labelRows.add(getStyles().rowSeparator());
		if(hasParameters || hasFooter) {
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(3))));
			labelRows.add(getStyles().labelRow("RowSeparator", getStyles().labelCell("HRCell", "", Span.colSpan(3))));
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(3))));
		}
		// parameters
		for(CatalogResourceParameter a : sortedProperties) {
			final String aName = a.getName();

			// output file contents as "DATA" as we can't fit the entire contents into a cell.
			List<String> values = "content".equals(aName)
					? Lists.newArrayList("DATA")
					: a.getValue();
			builder = new StringBuilder();
			for(String v : values) {
				builder.append(v);
				builder.append(" ");
			}
			// remove trailing space
			builder.deleteCharAt(builder.length() - 1);
			String value = builder.toString();

			labelRows.add(getStyles().labelRow(STYLE_ResourcePropertyRow, //
				getStyles().labelCell(STYLE_ResourcePropertyName, a.getName()), //
				getStyles().labelCell(STYLE_ResourcePropertyValue, DOUBLE_RIGHT_ARROW + value, Span.colSpan(2)) //
			));

			int tmpWidth = a.getName().length() + value.length() + 2;
			if(tmpWidth > width)
				width = tmpWidth;
		}

		// A footer with filename[line]
		// (is not always present)
		if(hasFooter) {
			builder = new StringBuilder();
			// shorten the text by making it relative to root if possible
			if(root != null)
				builder.append(new Path(r.getFile()).makeRelativeTo(root).toString());
			else
				builder.append(r.getFile());
			if(r.getLine() != null) {
				builder.append("[");
				builder.append(r.getLine());
				builder.append("]");
			}
			String tooltip = builder.toString();
			if(builder.length() > width) {
				builder.delete(0, builder.length() - width);
				builder.insert(0, "[...]");
			}

			if(hasParameters)
				labelRows.add(getStyles().labelRow(
					"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(3))));
			int line = -1;
			try {
				line = Integer.valueOf(r.getLine());
			}
			catch(NumberFormatException e) {
				line = -1;
			}

			labelRows.add(getStyles().labelRow(STYLE_ResourceFileInfoRow, //
				getStyles().labelCell(STYLE_ResourceFileInfoCell, builder.toString(), Span.colSpan(3)).withStyles(//
					getStyles().tooltip(tooltip), //
					getStyles().href(getHrefProducer().hrefToManifest(new Path(r.getFile()), root, line)) //
				)) //
			);
		}
		else if(hasParameters) {
			// add a bit of padding at the bottom if there is no footer
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(3))));
		}

		return StyleSet.withStyle(//
		getStyles().labelFormat(//
			getStyles().labelTable(STYLE_ResourceTable, //
				labelRows.toArray(new LabelRow[labelRows.size()]))));

	}

	/**
	 * Produces a graph in DOT format for the given catalog on the given output stream.
	 * 
	 * @param cancel
	 *        enables cancellation of long running job
	 * @param catalog
	 *        the catalog for which a graph is produced
	 * @param out
	 *        where output in DOT format should be written
	 * @param root
	 *        the path to the root of file references in catalogs
	 * @param moduleData
	 *        Name -> 0* MetadataInfo representing one version of a module with given name
	 */
	public void produceGraph(ICancel cancel, Catalog catalog, String catalogName, OutputStream out, IPath root) {
		if(cancel == null || catalog == null || out == null)
			throw new IllegalArgumentException("one or more parameters are null");

		RootGraph g = produceRootGraph(cancel, catalog, catalogName, root);
		GraphCSS instanceRules = getInstanceRules();
		instanceRules.addAll(getTheme().getInstanceRules());
		getDotRenderer().write(cancel, out, g, getTheme().getDefaultRules(), instanceRules);
	}

	/**
	 * Produces the graph data structure (RootGraph, Vertexes, Edges).
	 * 
	 */
	private RootGraph produceRootGraph(ICancel cancel, Catalog catalog, String catalogName, final IPath root) {

		RootGraph g = new RootGraph(catalogName, "RootGraph", "root");

		// Iterate the catalog
		// What to do with these? Are they of any value?
		// catalog.getClasses(); // list of classnames
		// catalog.getTags(); // don't know if these have any value...

		Map<String, Vertex> vertexMap = Maps.newHashMap();
		Map<CatalogResource, Vertex> resourceVertexMap = Maps.newHashMap();

		for(CatalogResource r : catalog.getResources()) {
			StringBuilder builder = new StringBuilder();
			builder.append(r.getType().toLowerCase());
			builder.append("[");
			builder.append(r.getTitle().toLowerCase());
			builder.append("]");

			Vertex v = createVertexFor(r, root);
			g.addVertex(v);
			vertexMap.put(builder.toString(), v);
			resourceVertexMap.put(r, v);
		}

		for(CatalogResource r : catalog.getResources()) {
			final Vertex source = resourceVertexMap.get(r);
			for(CatalogResourceParameter p : Iterables.filter(
				getParameterIterable(r), Predicates.not(regularParameterPredicate))) {
				String aName = p.getName();
				String style = null;
				if("subscribe".equals(aName))
					style = CatalogGraphStyles.STYLE_SubscribeEdge;
				else if("before".equals(aName))
					style = CatalogGraphStyles.STYLE_BeforeEdge;
				else if("notify".equals(aName))
					style = CatalogGraphStyles.STYLE_NotifyEdge;
				else
					style = CatalogGraphStyles.STYLE_RequireEdge;
				for(String ref : p.getValue()) {
					Vertex target = vertexMap.get(ref.toLowerCase());
					if(target == null) {
						target = createVertexForMissingResource(ref);
						vertexMap.put(ref.toLowerCase(), target); // keep it if there are more references
						g.addVertex(target);
					}
					Edge edge = new Edge(aName, style, source, target);
					g.addEdge(edge);
				}
			}
		}
		for(CatalogEdge e : catalog.getEdges()) {
			Vertex source = vertexMap.get(e.getSource().toLowerCase());
			Vertex target = vertexMap.get(e.getTarget().toLowerCase());
			Edge edge = new Edge("", STYLE_ResourceEdge, source, target);
			g.addEdge(edge);
		}

		return g;
	}
}
