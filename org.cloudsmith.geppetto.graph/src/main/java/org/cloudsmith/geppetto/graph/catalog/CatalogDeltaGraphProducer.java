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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.CatalogEdge;
import org.cloudsmith.geppetto.catalog.CatalogResource;
import org.cloudsmith.geppetto.catalog.CatalogResourceParameter;
import org.cloudsmith.graph.ICancel;
import org.cloudsmith.graph.IGraphElement;
import org.cloudsmith.graph.ILabeledGraphElement;
import org.cloudsmith.graph.elements.Edge;
import org.cloudsmith.graph.elements.RootGraph;
import org.cloudsmith.graph.elements.Vertex;
import org.cloudsmith.graph.graphcss.StyleSet;
import org.cloudsmith.graph.style.Span;
import org.cloudsmith.graph.style.labels.ILabelTemplate;
import org.cloudsmith.graph.style.labels.LabelCell;
import org.cloudsmith.graph.style.labels.LabelRow;
import org.cloudsmith.graph.style.labels.LabelStringTemplate;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

/**
 * Produces a Catalog graph in DOT format.
 * 
 */
public class CatalogDeltaGraphProducer extends AbstractCatalogGraphProducer implements CatalogGraphStyles {

	private static class MarkerFunction implements Function<IGraphElement, ILabelTemplate> {

		private String marker;

		MarkerFunction(String marker) {
			this.marker = marker;
		}

		@Override
		public ILabelTemplate apply(IGraphElement from) {
			String text = "";
			if(from instanceof ILabeledGraphElement) {

				ILabeledGraphElement labeled = (ILabeledGraphElement) from;
				String label = labeled.getLabel();
				if(label.length() < 1)
					text = marker;
				else {
					StringBuilder builder = new StringBuilder();
					builder.append(marker);
					builder.append(" ");
					builder.append(label);
					text = builder.toString();
				}
			}
			return new LabelStringTemplate(text);
		}

	}

	private static class PropertyDeltaInfo {
		int modifiedCount;

		int width;

		String singleResourceStyle;

		PropertyDeltaInfo() {
			modifiedCount = 0;
			width = 0;
			singleResourceStyle = null;
		}
	}

	private static final String GT = "&gt;";

	private static final String LT = "&lt;";

	private static Function<IGraphElement, ILabelTemplate> markRemoved = new MarkerFunction(LT);

	private static Function<IGraphElement, ILabelTemplate> markAdded = new MarkerFunction(GT);

	private PropertyDeltaInfo computePropertyRows(CatalogResource oldR, CatalogResource newR, List<LabelRow> labelRows) {
		final PropertyDeltaInfo result = new PropertyDeltaInfo();
		final Function<IGraphElement, Boolean> renderMarkerColumnFunc = new Function<IGraphElement, Boolean>() {

			@Override
			public Boolean apply(IGraphElement from) {
				return result.modifiedCount > 0;
			}
		};

		Multimap<String, String> properties = HashMultimap.create();
		Map<String, String> oldProperties = Maps.newHashMap();
		Map<String, String> newProperties = Maps.newHashMap();

		Map<String, String> propertiesToUse = null;
		if(oldR == null || newR == null) {
			if(oldR == null) {
				result.singleResourceStyle = STYLE_Added;
				propertiesToUse = newProperties;
			}
			else {
				result.singleResourceStyle = STYLE_Removed;
				propertiesToUse = oldProperties;

			}
		}

		if(oldR != null)
			for(CatalogResourceParameter p : Iterables.filter(getParameterIterable(oldR), regularParameterPredicate)) {
				final String name = p.getName();
				final String value = stringify(p.getValue());
				properties.put(name, value);
				oldProperties.put(name, value);
			}
		if(newR != null)
			for(CatalogResourceParameter p : Iterables.filter(getParameterIterable(newR), regularParameterPredicate)) {
				final String name = p.getName();
				final String value = stringify(p.getValue());
				properties.put(name, value);
				newProperties.put(name, value);
			}

		// Sort the keys
		Set<String> sortedNames = Sets.newTreeSet();
		sortedNames.addAll(properties.keySet());

		for(String propertyName : sortedNames) {
			Collection<String> values = properties.get(propertyName);
			String styleClass = null;
			// if two different values (can only happen if two resources were given)
			if(values.size() > 1) {
				styleClass = STYLE_Modified;
				String valueOld = oldProperties.get(propertyName);
				labelRows.add(getStyles().labelRow(
					STYLE_ResourcePropertyRow, //
					createResourcePropertyMarker(LT, STYLE_Removed, renderMarkerColumnFunc), //
					getStyles().labelCell(
						Sets.newHashSet(STYLE_ResourcePropertyName, STYLE_Removed), propertyName, Span.rowSpan(1)), //
					getStyles().labelCell(
						Sets.newHashSet(STYLE_ResourcePropertyValue, STYLE_Removed), DOUBLE_RIGHT_ARROW + //
								valueOld, Span.colSpan(1))//
				));

				String valueNew = newProperties.get(propertyName);
				labelRows.add(getStyles().labelRow(
					STYLE_ResourcePropertyRow, //
					createResourcePropertyMarker(GT, STYLE_Added, renderMarkerColumnFunc), //
					getStyles().labelCell(
						Sets.newHashSet(STYLE_ResourcePropertyName, STYLE_Added), propertyName, Span.rowSpan(1)), //
					getStyles().labelCell(
						Sets.newHashSet(STYLE_ResourcePropertyValue, STYLE_Added), DOUBLE_RIGHT_ARROW + //
								valueNew, Span.colSpan(1)) //
				));
				result.width = Math.max(
					result.width, propertyName.length() + Math.max(valueOld.length(), valueNew.length()) + 2);
			}
			else {
				// added, removed, or unmodified
				String value = null;
				String marker = "";
				if(result.singleResourceStyle == null) {
					boolean inOld = oldProperties.containsKey(propertyName);
					boolean inNew = newR != null && newProperties.containsKey(propertyName);
					if(inOld && inNew) {
						styleClass = STYLE_UnModified;
						value = oldProperties.get(propertyName);
					}
					else if(inNew) {
						styleClass = STYLE_Added;
						value = newProperties.get(propertyName);
						result.modifiedCount++;
						marker = GT;
					}
					else if(inOld) {
						styleClass = STYLE_Removed;
						value = oldProperties.get(propertyName);
						result.modifiedCount++;
						marker = LT;
					}
				}
				else {
					styleClass = result.singleResourceStyle;
					value = propertiesToUse.get(propertyName);
				}
				// Can not output the entire file content as the value, simply use "DATA"
				if("content".equals(propertyName))
					value = "DATA";

				labelRows.add(getStyles().labelRow(STYLE_ResourcePropertyRow, //
					createResourcePropertyMarker(marker, styleClass, renderMarkerColumnFunc), //
					getStyles().labelCell(Sets.newHashSet(STYLE_ResourcePropertyName, styleClass), propertyName), //
					getStyles().labelCell(Sets.newHashSet(STYLE_ResourcePropertyValue, styleClass), DOUBLE_RIGHT_ARROW + //
							value, Span.colSpan(1)) //
				));

				result.width = Math.max(result.width, propertyName.length() + value.length() + 2);
			}
		}
		return result;
	}

	private LabelCell createResourcePropertyMarker(String marker, String styleName,
			Function<IGraphElement, Boolean> renderedFunc) {
		return getStyles().labelCell(Sets.newHashSet(STYLE_ResourcePropertyMarker, styleName), marker)//
		.withStyles(// getStyles().fixedSize(true),
			getStyles().fixedSize(true), getStyles().width(8), getStyles().height(8), getStyles().cellSpacing(0), //
			getStyles().cellPadding(0), getStyles().rendered(renderedFunc) //
		);
	}

	private void createVertexesFor(Iterable<CatalogResource> resources, //
			Map<String, Vertex> vertexMap, //
			Map<CatalogResource, Vertex> resourceVertexMap, //
			Map<Vertex, CatalogResource> catalogMap) {
		for(CatalogResource r : resources) {
			Vertex v = createVertexFor(r);
			vertexMap.put(keyOf(r), v);
			resourceVertexMap.put(r, v);
			catalogMap.put(v, r);
		}

	}

	private Vertex createVertexFor(CatalogResource resource) {
		Vertex v = new Vertex("", STYLE_Resource);
		return v;
	}

	private void edgesForCatalogEdges(Iterable<CatalogEdge> catalogEdges, //
			Map<String, Vertex> vertexMap, //
			Map<String, Edge> edgeMap, //
			Set<String> edges) {
		for(CatalogEdge e : catalogEdges) {
			Vertex source = vertexMap.get(e.getSource().toLowerCase());
			Vertex target = vertexMap.get(e.getTarget().toLowerCase());
			Edge edge = new Edge("", STYLE_ResourceEdge, source, target);
			final String key = keyOf(e);
			edgeMap.put(key, edge);
			edges.add(key);
		}
	}

	private void edgesForResources(RootGraph g, Iterable<CatalogResource> resources,
			Map<CatalogResource, Vertex> resourceVertexMap, //
			Map<String, Vertex> vertexMap, //
			Map<String, Edge> edgeMap, //
			Set<String> edges) {
		for(CatalogResource r : resources) {
			final String sourceKey = keyOf(r);
			final Vertex source = vertexMap.get(sourceKey);
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
				for(String targetRef : p.getValue()) {
					final String targetKey = targetRef.toLowerCase();
					Vertex target = vertexMap.get(targetKey);
					if(target == null) {
						target = createVertexForMissingResource(targetRef);
						vertexMap.put(targetKey, target); // keep it if there are more references
						g.addVertex(target);
					}

					Edge edge = new Edge(aName, style, source, target);
					String key = sourceKey + "-" + aName + "-" + targetKey;
					edgeMap.put(key, edge);
					edges.add(key);

				}
			}
		}
	}

	private void generateEdgeDelta(RootGraph g, Set<String> edges, Map<String, Edge> oldEdgeMap,
			Map<String, Edge> newEdgeMap) {
		for(String key : edges) {
			boolean inOld = oldEdgeMap.containsKey(key);
			boolean inNew = newEdgeMap.containsKey(key);

			Edge e = null;
			if(inOld && inNew) {
				e = oldEdgeMap.get(key);
				e.addStyleClass(STYLE_UnModified);
			}
			else if(inOld) {
				e = oldEdgeMap.get(key);
				e.addStyleClass(STYLE_Removed);
				e.setStyles(getStyles().labelFormat(getStyles().labelTemplate(markRemoved)));
			}
			else {
				e = newEdgeMap.get(key);
				e.addStyleClass(STYLE_Added);
				e.setStyles(getStyles().labelFormat(getStyles().labelTemplate(markAdded)));
			}
			g.addEdge(e);
		}
	}

	private String keyOf(CatalogEdge e) {
		return e.getSource().toLowerCase() + "-" + e.getTarget().toLowerCase();

	}

	private String keyOf(CatalogResource r) {
		StringBuilder builder = new StringBuilder();
		builder.append(r.getType().toLowerCase());
		builder.append("[");
		builder.append(r.getTitle().toLowerCase());
		builder.append("]");
		return builder.toString();
	}

	private StyleSet labelStyleForResource(CatalogResource oldR, IPath oldRoot, CatalogResource newR, IPath newRoot,
			String[] resultingStyle) {
		if(resultingStyle == null || resultingStyle.length != 1)
			throw new IllegalArgumentException("resulting style must be String[1]");
		final CatalogResource singleResource = newR == null
				? oldR
				: newR;
		final IPath singleRoot = newR == null
				? oldRoot
				: newRoot;

		if(singleResource == null)
			throw new IllegalArgumentException("At least one catalog must be specified");
		if(oldR != null && newR != null) {
			if(!(oldR.getType().equals(newR.getType()) && oldR.getTitle().toLowerCase().equals(
				newR.getTitle().toLowerCase())))
				throw new IllegalArgumentException("old and new resource must have same type and title");
		}
		// PROPERTIES
		List<LabelRow> innerLabelRows = Lists.newArrayList();
		final PropertyDeltaInfo propertyInfo = computePropertyRows(oldR, newR, innerLabelRows);
		int width = propertyInfo.width;

		// RESULTING OVERALL STYLE
		// i.e. if only one resource - it is either added or removed
		// and if the two were compared, it is unmodified if all properties were present with equal value
		if(propertyInfo.singleResourceStyle != null)
			resultingStyle[0] = propertyInfo.singleResourceStyle;
		else
			resultingStyle[0] = propertyInfo.modifiedCount == 0
					? STYLE_UnModified
					: STYLE_Modified;

		// The title can never differ as that means different resources - it is either a single catalog
		// (the non null catalog), or the newCatalog in case both are passed.
		// Add a labelRow for the 'type[id]'
		StringBuilder builder = new StringBuilder();
		if(resultingStyle[0].equals(STYLE_Added)) {
			builder.append(GT);
			builder.append(" ");
		}
		else if(resultingStyle[0].equals(STYLE_Removed)) {
			builder.append(LT);
			builder.append(" ");
		}
		builder.append(singleResource.getType());
		builder.append("[");
		builder.append(singleResource.getTitle());
		builder.append("]");

		boolean hasParameters = propertyInfo.width > 0;
		boolean hasFooter = singleResource.getFile() != null; // only show new Catalog file even if different
		List<LabelRow> labelRows = Lists.newArrayList();

		if(hasParameters || hasFooter)
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(1))));

		labelRows.add(getStyles().labelRow(STYLE_ResourceTitleRow, //
			getStyles().labelCell(STYLE_ResourceTitleCell, builder.toString(), Span.colSpan(1))));
		width = Math.max(width, builder.length());

		// Rendering of separator line fails in graphviz 2.28 with an error
		// labelRows.add(getStyles().rowSeparator());
		if(hasParameters || hasFooter) {
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(1))));
			labelRows.add(getStyles().labelRow("RowSeparator", getStyles().labelCell("HRCell", "", Span.colSpan(1))));
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(1))));
		}

		// // OLD STYLE
		// labelRows.addAll(innerLabelRows);
		// NEW STYLE
		if(innerLabelRows.size() > 0) {
			LabelCell tableCell = getStyles().labelCell(
				"ResourceTableCell",//
				getStyles().labelTable(STYLE_ResourceTable, innerLabelRows.toArray(new LabelRow[innerLabelRows.size()])));
			labelRows.add(getStyles().labelRow("ResourceTableRow", tableCell));
		}

		// FOOTER
		// A footer with filename[line]
		// (is not always present)
		if(hasFooter) {
			builder = new StringBuilder();
			// shorten the text by making it relative to root if possible
			if(singleRoot != null)
				builder.append(new Path(singleResource.getFile()).makeRelativeTo(singleRoot).toString());
			else
				builder.append(singleResource.getFile());
			if(singleResource.getLine() != null) {
				builder.append("[");
				builder.append(singleResource.getLine());
				builder.append("]");
			}

			String tooltip = builder.toString();
			if(builder.length() > width) {
				builder.delete(0, builder.length() - width);
				builder.insert(0, "[...]");
			}

			if(hasParameters)
				labelRows.add(getStyles().labelRow(
					"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(1))));

			int line = -1;
			try {
				line = Integer.valueOf(singleResource.getLine());
			}
			catch(NumberFormatException e) {
				line = -1;
			}
			labelRows.add(getStyles().labelRow(
				STYLE_ResourceFileInfoRow, //
				getStyles().labelCell(STYLE_ResourceFileInfoCell, builder.toString(), Span.colSpan(1)).withStyles(
					getStyles().tooltip(tooltip), //
					getStyles().href(
						getHrefProducer().hrefToManifest(new Path(singleResource.getFile()), singleRoot, line)) //
				)) //
			);
		}
		else if(hasParameters) {
			// add a bit of padding at the bottom if there is no footer
			labelRows.add(getStyles().labelRow(
				"RowSeparator", getStyles().labelCell("SpacingCell", "", Span.colSpan(1))));
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
	 * @param moduleData
	 *        Name -> 0* MetadataInfo representing one version of a module with given name
	 */
	public void produceGraph(ICancel cancel, String title, Catalog oldCatalog, IPath oldRoot, Catalog newCatalog,
			IPath newRoot, OutputStream out) {
		if(cancel == null || oldCatalog == null || newCatalog == null || out == null)
			throw new IllegalArgumentException("one or more parameters are null");

		RootGraph g = produceRootGraph(cancel, title, oldCatalog, oldRoot, newCatalog, newRoot);
		getInstanceRules().addAll(getTheme().getInstanceRules());
		getDotRenderer().write(cancel, out, g, getTheme().getDefaultRules(), getInstanceRules());
	}

	/**
	 * Produces the graph data structure (RootGraph, Vertexes, Edges).
	 * 
	 */
	private RootGraph produceRootGraph(ICancel cancel, String title, Catalog oldCatalog, IPath oldRoot,
			Catalog newCatalog, IPath newRoot) {

		RootGraph g = new RootGraph(title, "RootGraph", "root");

		// Iterate the catalog
		// What to do with classes and tags? Are they of any value?
		// catalog.getClasses(); // list of classnames
		// catalog.getTags(); // don't know if these have any value...

		Map<String, Vertex> oldVertexMap = Maps.newHashMap();
		Map<String, Vertex> newVertexMap = Maps.newHashMap();
		Map<CatalogResource, Vertex> oldResourceVertexMap = Maps.newHashMap();
		Map<CatalogResource, Vertex> newResourceVertexMap = Maps.newHashMap();
		Map<Vertex, CatalogResource> catalogMap = Maps.newHashMap();

		// create all vertexes for old
		createVertexesFor(oldCatalog.getResources(), oldVertexMap, oldResourceVertexMap, catalogMap);

		// create all vertexes for new
		createVertexesFor(newCatalog.getResources(), newVertexMap, newResourceVertexMap, catalogMap);

		// compute Venn set
		Set<String> oldKeys = oldVertexMap.keySet();
		Set<String> newKeys = newVertexMap.keySet();
		SetView<String> inBoth = Sets.intersection(oldKeys, newKeys);
		SetView<String> removedInNew = Sets.difference(oldKeys, newKeys);
		SetView<String> addedInNew = Sets.difference(newKeys, oldKeys);
		Map<String, Vertex> resultingVertexMap = Maps.newHashMap();

		for(String s : removedInNew) {
			Vertex v = oldVertexMap.get(s);
			v.addStyleClass(STYLE_Removed);
			v.setStyles(labelStyleForResource(catalogMap.get(v), oldRoot, null, null, new String[1]));
			resultingVertexMap.put(s, v);
			g.addVertex(v);
		}
		for(String s : addedInNew) {
			Vertex v = newVertexMap.get(s);
			v.addStyleClass(STYLE_Added);
			v.setStyles(labelStyleForResource(null, null, catalogMap.get(v), newRoot, new String[1]));
			resultingVertexMap.put(s, v);
			g.addVertex(v);
		}
		for(String s : inBoth) {
			Vertex vOld = oldVertexMap.get(s);
			Vertex vNew = newVertexMap.get(s);

			Vertex v = new Vertex("", STYLE_Resource);
			String computedStyle[] = new String[1];
			v.setStyles(labelStyleForResource(
				catalogMap.get(vOld), oldRoot, catalogMap.get(vNew), newRoot, computedStyle));
			v.addStyleClass(computedStyle[0]);
			resultingVertexMap.put(s, v);
			g.addVertex(v);
		}

		// Process Edges
		Map<String, Edge> oldEdgeMap = Maps.newHashMap();
		Map<String, Edge> newEdgeMap = Maps.newHashMap();
		Set<String> edges = Sets.newHashSet();

		edgesForCatalogEdges(oldCatalog.getEdges(), resultingVertexMap, oldEdgeMap, edges);
		edgesForResources(g, oldCatalog.getResources(), oldResourceVertexMap, resultingVertexMap, oldEdgeMap, edges);

		edgesForCatalogEdges(newCatalog.getEdges(), resultingVertexMap, newEdgeMap, edges);
		edgesForResources(g, newCatalog.getResources(), newResourceVertexMap, resultingVertexMap, newEdgeMap, edges);

		generateEdgeDelta(g, edges, oldEdgeMap, newEdgeMap);

		return g;
	}

	private String stringify(Iterable<String> iterable) {
		StringBuilder builder = new StringBuilder();
		for(String v : iterable) {
			builder.append(v);
			builder.append(" ");
		}
		builder.deleteCharAt(builder.length() - 1); // trim size
		return builder.toString();
	}
}
