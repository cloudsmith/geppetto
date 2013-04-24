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
package org.cloudsmith.geppetto.graph.dependency;

import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.graph.DependencyGraphProducer;
import org.cloudsmith.geppetto.graph.IHrefProducer;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo;
import org.cloudsmith.graph.ICancel;
import org.cloudsmith.graph.ICancel.NullIndicator;
import org.cloudsmith.graph.IVertex;
import org.cloudsmith.graph.dot.DotRenderer;
import org.cloudsmith.graph.elements.Edge;
import org.cloudsmith.graph.elements.GraphElement;
import org.cloudsmith.graph.elements.RootGraph;
import org.cloudsmith.graph.elements.Vertex;
import org.cloudsmith.graph.graphcss.GraphCSS;
import org.cloudsmith.graph.graphcss.IFunctionFactory;
import org.cloudsmith.graph.graphcss.StyleSet;
import org.cloudsmith.graph.style.IStyle;
import org.cloudsmith.graph.style.IStyleFactory;
import org.cloudsmith.graph.style.labels.LabelRow;
import org.eclipse.emf.ecore.EClass;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Calculates dependency data for a set of modules. The logic handles:
 * <ul>
 * <li>Existing and non existing modules</li>
 * <li>Resolved dependencies (stated in Modulefiles)</li>
 * <li>Unresolved dependencies (dependency on existing module, but unsatisfied version range).</li>
 * <li>Unresolved dependencies to missing modules (all versions aggregated into one 'missing module')</li>
 * <li>Implied 'dependencies' existing due to use of export from module</li>
 * <li>Self references are included in the result</li>
 * </ul>
 * Each module is represented by an instance of {@link ModuleNodeData}, and each relationship is represented
 * by an {@link ModuleEdge}. An edge is further described by an {@link EdgeType} as well as the implied meaning
 * of the given set of non null fields. It is up to the user of this data to decide how to represent multiple
 * edges, although it is suggested that a combination of resolved/implied is to be treated as "normal", and
 * unresolved/implied as "unresolved", if a dependency was not stated there may be only "implied" edges.
 * 
 * For all unresolved references (not found anywhere), the edge leads to a "null" {@link ModuleEdge}.
 * 
 */
public class DependencyDataCalculator implements DependencyGraphStyles, DependencyGraphProducer {

	public static enum EdgeType {
		UNRESOLVED, RESOLVED, IMPLIED;
	}

	public static class ModuleEdge {
		final ModuleNodeData from;

		final ModuleNodeData to;

		final EdgeType edgeType;

		final Iterable<Export> imported;

		final Iterable<String> unresolved;

		/**
		 * null if edgeType == IMPLIED
		 */
		Dependency dependency;

		private Vertex vertex;

		/**
		 * A (sub) collection of imported Exports that are ambiguous. May be null.
		 */
		final Collection<Export> ambiguities;

		/**
		 * @param from
		 * @param implied
		 * @param unresolvedNames
		 */
		public ModuleEdge(ModuleNodeData from, EdgeType implied, Iterable<String> unresolvedNames) {
			this(from, null, implied, null, null, unresolvedNames, null);
		}

		public ModuleEdge(ModuleNodeData from, ModuleNodeData to, EdgeType type, Dependency d) {
			this(from, to, type, d, null, null, null);
		}

		public ModuleEdge(ModuleNodeData from, ModuleNodeData to, EdgeType type, Dependency d,
				Iterable<Export> imported, Iterable<String> unresolved, Collection<Export> ambiguities) {
			this.from = from;
			this.to = to;
			this.edgeType = type;
			this.dependency = d;
			this.imported = imported;
			this.unresolved = unresolved;
			this.ambiguities = ambiguities;
			from.addOutgoing(to, this);
		}

		/**
		 * @param from
		 * @param to
		 * @param type
		 * @param imported
		 */
		public ModuleEdge(ModuleNodeData from, ModuleNodeData to, EdgeType type, Iterable<Export> imported,
				Collection<Export> ambiguities) {
			this(from, to, type, null, imported, null, ambiguities);
		}

		public Vertex getVertex() {
			return vertex;
		}

		/**
		 * @param edgeVertex
		 */
		public void setVertex(Vertex edgeVertex) {
			this.vertex = edgeVertex;
		}
	}

	public static class ModuleNodeData {
		public static ModuleNodeData existing(ModuleName name, Version version, boolean isNode, String href) {
			return new ModuleNodeData(name, version, isNode
					? ModuleType.PPNODE
					: ModuleType.MODULE, href);
		}

		/**
		 * Formats the label of the node as [NameOf(root.parent)/]NameOf(root)
		 * 
		 * @param root
		 * @return
		 */
		public static ModuleNodeData root(File root) {
			File parent = root.getParentFile();
			ModuleName moduleName;
			if(parent != null)
				moduleName = new ModuleName(parent.getName(), root.getName(), false);
			else
				moduleName = new ModuleName("root", root.getName(), false);
			return new ModuleNodeData(moduleName, null, ModuleType.ROOT, "");
		}

		public static ModuleNodeData unresolved(ModuleName name) {
			return new ModuleNodeData(name, null, ModuleType.NON_EXISTING, "");
		}

		IVertex vertex;

		ModuleName name;

		Version version;

		ModuleType moduleType;

		String href;

		boolean marked;

		Multimap<ModuleNodeData, ModuleEdge> outgoing;

		private int ambiguous;

		private ModuleNodeData(ModuleName name, Version version, ModuleType type, String href) {
			this.name = name;
			this.version = version;
			this.moduleType = type;
			this.outgoing = ArrayListMultimap.create();
			this.href = href;
			this.ambiguous = 0;
		}

		private void addOutgoing(ModuleNodeData to, ModuleEdge edge) {
			// filter out direct self references
			if(to == this)
				return;
			outgoing.put(to, edge);
		}

		public boolean exists() {
			return moduleType != ModuleType.NON_EXISTING;
		}

		private IVertex getVertex() {
			return vertex;
		}

		public boolean isAmbiguous() {
			return this.ambiguous > 0;
		}

		public boolean isNode() {
			return moduleType == ModuleType.PPNODE;
		}

		/**
		 * mark all reachable module nodes.
		 */
		public void mark() {
			if(marked)
				return;
			marked = true;
			for(ModuleNodeData reachable : outgoing.keySet())
				if(reachable != null) // i.e. if resolved
					reachable.mark();
		}

		/**
		 * @param b
		 */
		public void setAmbiguous(int counter) {
			this.ambiguous = counter;
		}

		private void setVertex(IVertex v) {
			vertex = v;
		}

	}

	public static enum ModuleType {
		ROOT, PPNODE, MODULE, NON_EXISTING, PPTP;
	}

	@Inject
	private IStyleFactory styles;

	@Inject
	private DependencyGraphTheme theme;

	@Inject
	private DotRenderer dotRenderer;

	@Inject
	private IHrefProducer hrefProducer;

	@Inject
	private GraphCSS instanceRules;

	private Map<Object, ModuleNodeData> moduleNodeData = Maps.newHashMap();

	private List<ModuleEdge> moduleEdges = Lists.newArrayList();

	private ModuleNodeData nonModularNode;

	private ModuleNodeData pptpNode;

	private File root;

	private ModuleNodeData _file2Module(File f, Map<File, ModuleNodeData> index) {
		if(!f.getPath().endsWith("Modulefile"))
			return index.get(new File(f.getPath() + "/Modulefile"));
		return index.get(f);
	}

	private void addEdgeHref(ModuleNodeData a, ModuleNodeData b, GraphElement... elements) {
		String aId = idOfVertex(a);
		String hrefForEdge = null;
		String bId = b == null || b == pptpNode
				? ""
				: idOfVertex(b);
		boolean splitEdge = elements.length > 1;
		if(b == null)
			hrefForEdge = hrefProducer.hrefForEdgeToUnresolved(aId, splitEdge);
		else if(b == pptpNode)
			hrefForEdge = hrefProducer.hrefForEdgeToPptp(aId);
		else
			hrefForEdge = hrefProducer.hrefForEdge(aId, bId, splitEdge);
		IStyle<String> hrefStyle = styles.href(hrefForEdge);
		for(GraphElement e : elements)
			e.setStyles(StyleSet.withStyle(hrefStyle));
	}

	private void addStyleClasses(List<String> classes, GraphElement... elements) {
		for(GraphElement e : elements)
			e.addAllStyleClasses(classes);
	}

	/**
	 * Install tooltip as instance style for edge.
	 * 
	 * @param tooltip
	 * @param edges
	 */
	private void addTooltip(String tooltip, GraphElement... elements) {
		for(GraphElement e : elements)
			e.setStyles(StyleSet.withStyle(styles.tooltip(tooltip)));
	}

	/**
	 * Calculates dependency data and returns a map from Modulefiles to ModuleNodeData.
	 * 
	 * @param moduleData
	 * @param exportData
	 * @return
	 */
	public Map<File, ModuleNodeData> calculateDependencyData(File root, Multimap<ModuleName, MetadataInfo> moduleData,
			AllModuleReferences exportData) {

		// create node data for all existing modules and check if there are ambiguities
		Multimap<ModuleName, ModuleNodeData> processedModules = ArrayListMultimap.create();
		for(MetadataInfo mi : moduleData.values()) {
			Metadata m = mi.getMetadata();
			ModuleNodeData mnd = ModuleNodeData.existing(m.getName(), m.getVersion(), mi.isRole(), toHREF_URL(mi));
			moduleNodeData.put(mi, mnd);
			processedModules.put(m.getName(), mnd);
		}
		for(ModuleName key : processedModules.keySet()) {
			Collection<ModuleNodeData> modules = processedModules.get(key);
			if(modules.size() > 1) {
				int counter = 0;
				for(ModuleNodeData mnd : modules)
					mnd.setAmbiguous(++counter);
			}
		}
		// moduleData is keyed by "fullName" to lower case

		// Create pseudo module for non modular content
		nonModularNode = ModuleNodeData.root(root);
		pptpNode = new ModuleNodeData(new ModuleName("root", "puppet", false), null, ModuleType.PPTP, ""); // will not be rendered

		// create module nodes for missing (unsatisfied dependencies)
		// unless dependency is to represented module name, but version is not matched (in which case
		// the unmatched but existing node is used.
		// if a dependency appears more than once, use the first (skip the rest with same name)
		for(MetadataInfo mi : moduleData.values()) {
			final ModuleNodeData a = moduleNodeData.get(mi);
			Set<ModuleName> processed = Sets.newHashSet();
			for(Dependency d : mi.getUnresolvedDependencies()) {
				final ModuleName name = d.getName();
				if(!processed.add(name))
					continue;
				Collection<MetadataInfo> existingVersions = moduleData.get(name);
				ModuleNodeData b = null;
				if(existingVersions == null || existingVersions.size() < 1) {
					b = moduleNodeData.get(name);
					if(b == null) {
						// need a node for the missing module
						b = ModuleNodeData.unresolved(name);
						// need to generate one that can not be found if name is null
						moduleNodeData.put(name == null
								? new ModuleName("no", "name", false)
								: name, b);
					}
				}
				else {
					// pick (one of) the existing versions (it is actually illegal to have more
					// than one, so just pick the first one).
					MetadataInfo first = Iterables.get(existingVersions, 0);
					b = moduleNodeData.get(first);
				}
				createUnresolvedEdge(a, b, d);
			}
			// Add edges for all resolved dependencies
			for(MetadataInfo.Resolution r : mi.getResolvedDependencies()) {
				createResolvedEdge(a, moduleNodeData.get(r.metadata), r.dependency);
			}
		}
		Map<File, ModuleNodeData> fileIndex = Maps.newHashMap();
		for(Map.Entry<Object, ModuleNodeData> m : moduleNodeData.entrySet()) {
			if(!(m.getKey() instanceof ModuleName)) {
				MetadataInfo mi = (MetadataInfo) m.getKey();
				fileIndex.put(mi.getFile(), m.getValue());
			}
		}
		Map<File, Multimap<File, Export>> ambiguities = exportData.getAmbiguityMap();
		for(Map.Entry<File, Multimap<File, AllModuleReferences.Export>> x : exportData.getImportMap().entrySet()) {
			// get the imported
			File fromFile = x.getKey();
			Multimap<File, Export> m = x.getValue();

			// get any ambiguities
			Multimap<File, Export> ambiguitiesForFile = ambiguities.get(fromFile);
			for(File toFile : m.keySet()) {
				createImportEdge(
					file2Module(fromFile, fileIndex), file2Module(toFile, fileIndex), m.get(toFile),
					ambiguitiesForFile != null
							? ambiguitiesForFile.get(toFile)
							: null);
			}
		}

		for(File fromFile : exportData.getUnresolvedMap().keySet()) {
			createUnresolvedEdge(file2Module(fromFile, fileIndex), exportData.getUnresolvedMap().get(fromFile));

		}
		return fileIndex;
	}

	private List<String> classesFor(ModuleNodeData from, ModuleNodeData to) {
		List<String> styleClasses = Lists.newArrayList();
		if(from != null)
			styleClasses.add("FROM__" + idOfVertex(from));
		if(to != null && to != pptpNode)
			styleClasses.add("TO__" + idOfVertex(to));
		return styleClasses;
	}

	private List<String> classesOfEdge(ModuleEdge edge) {
		return classesFor(edge.from, edge.to);
	}

	private void createImportEdge(ModuleNodeData from, ModuleNodeData to, Iterable<Export> imported,
			Collection<Export> ambiguities) {
		moduleEdges.add(new ModuleEdge(from, to, EdgeType.IMPLIED, imported, ambiguities));

	}

	private void createResolvedEdge(final ModuleNodeData a, final ModuleNodeData b, Dependency d) {
		moduleEdges.add(new ModuleEdge(a, b, EdgeType.RESOLVED, d));
	}

	private void createUnresolvedEdge(ModuleNodeData from, Iterable<String> unresolvedNames) {
		moduleEdges.add(new ModuleEdge(from, EdgeType.IMPLIED, unresolvedNames));

	}

	private void createUnresolvedEdge(final ModuleNodeData a, final ModuleNodeData b, Dependency d) {
		moduleEdges.add(new ModuleEdge(a, b, EdgeType.UNRESOLVED, d));
	}

	private Vertex createVertexForEdge(ModuleEdge me) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("References from ");
		stringBuilder.append(makeTooltip(me.from, me.to));
		String tooltipString = stringBuilder.toString();
		Vertex edgeVertex = null;
		if(me.imported != null) {
			edgeVertex = new Vertex("", STYLE_CLASS_IMPORTS);
			edgeVertex.setStyles(labelStyleForImported(me.imported, me.ambiguities).add(
				StyleSet.withStyle(styles.tooltip(tooltipString))));
			me.setVertex(edgeVertex);
		}
		else if(me.unresolved != null) {
			edgeVertex = new Vertex("", STYLE_CLASS_UNRESOLVED_IMPORTS);
			edgeVertex.setStyles(labelStyleForUnresolved(me).add(StyleSet.withStyle(styles.tooltip(tooltipString))));
			me.setVertex(edgeVertex);
		}
		// Set style classes for FROM and TO if vertex was created
		if(edgeVertex != null)
			edgeVertex.addAllStyleClasses(classesOfEdge(me));

		// if neither imported nor unresolved, a vertex is not needed
		return edgeVertex;
	}

	private Vertex createVertexForModuleNode(ModuleNodeData mnd) {
		StringBuilder builder = new StringBuilder();
		builder.append(mnd.name);
		if(mnd.isAmbiguous()) {
			builder.append(" [");
			builder.append(mnd.ambiguous);
			builder.append("]");
		}
		if(mnd.version != null) {
			builder.append("\n");
			builder.append(mnd.version);
		}
		String label = builder.toString();

		String style = mnd.exists()
				? (mnd.isAmbiguous())
						? STYLE_CLASS_AMBIGUOUSLY_RESOLVED_MODULE
						: STYLE_CLASS_RESOLVED_MODULE
				: STYLE_CLASS_UNRESOLVED_MODULE;
		Vertex v = new Vertex(label, style);
		v.setStyles(styles.href(mnd.href));
		v.putUserData(IFunctionFactory.ID_KEY, idOfVertex(mnd));
		mnd.setVertex(v);
		return v;
	}

	private Vertex createVertexForPPNodeNode(ModuleNodeData mnd) {
		String label = mnd.name.toString();
		// no version (user is not aware of one).
		String style = STYLE_CLASS_PPNODE_MODULE;
		Vertex v = new Vertex(label, style);
		v.setStyles(styles.href(mnd.href));
		v.putUserData(IFunctionFactory.ID_KEY, idOfVertex(mnd));
		mnd.setVertex(v);
		return v;
	}

	private Vertex createVertexForRootNode(ModuleNodeData mnd) {
		String label = mnd.name.toString();
		if(mnd.version != null)
			label += "\n" + mnd.version;
		Vertex v = new Vertex(label, STYLE_CLASS_ROOT);
		mnd.setVertex(v);
		return v;
	}

	protected void dumpInfo() {
		for(ModuleNodeData m : moduleNodeData.values()) {
			System.err.printf("module %s(%s) = %s\n", m.name, m.version, m.exists()
					? "exists"
					: "missing");
			for(ModuleNodeData to : m.outgoing.keySet()) {
				System.err.printf("    -> %s\n", to != null
						? to.name
						: "null 'to'");
				for(ModuleEdge edge : m.outgoing.get(to)) {
					System.err.printf("        %s, ", edge.edgeType);
					if(edge.imported != null)
						for(Export e : edge.imported)
							System.err.printf("%s ", e.getName());
					if(edge.unresolved != null)
						for(String s : edge.unresolved)
							System.err.printf("(unresolved) %s ", s);
					if(edge.imported != null || edge.unresolved != null)
						System.err.println();
					if(edge.dependency != null)
						if(edge.dependency.getVersionRequirement() == null)
							System.err.println(">= 0");
						else
							System.err.printf("%s\n", edge.dependency.getVersionRequirement());

				}
			}
		}

	}

	private ModuleNodeData file2Module(File f, Map<File, ModuleNodeData> index) {
		if(f.getPath().equals("_pptp"))
			return pptpNode;
		ModuleNodeData mnd = _file2Module(f, index);
		return mnd != null
				? mnd
				: nonModularNode;
	}

	public String getVersionLabel(ModuleEdge edge) {
		VersionRange vreq = edge.dependency.getVersionRequirement();
		if(vreq == null)
			return "unversioned";
		return vreq.toString();

	}

	/**
	 * Produce id on the form NAME[@ambiguityIdx] e.g. Foo, Bar-1, Bar-2
	 * 
	 * @param mnd
	 * @return
	 */
	private String idOfVertex(ModuleNodeData mnd) {
		StringBuilder idBuilder = new StringBuilder();
		idBuilder.append(mnd.name);
		if(mnd.isAmbiguous()) {
			idBuilder.append("__");
			idBuilder.append(mnd.ambiguous);
		}
		return idBuilder.toString();

	}

	private String labelOfType(EClass clazz) {
		if(clazz == PPPackage.Literals.DEFINITION)
			return "define";
		if(clazz == PPPackage.Literals.HOST_CLASS_DEFINITION)
			return "class";
		if(clazz == PPPackage.Literals.NODE_DEFINITION)
			return "node";
		if(clazz == PPTPPackage.Literals.FUNCTION)
			return "function";
		if(clazz == PPTPPackage.Literals.TYPE)
			return "type";
		return clazz.getName(); // give up, but output something (should not really happen)
	}

	private StyleSet labelStyleForImported(Iterable<Export> imports, Collection<Export> ambiguities) {
		List<LabelRow> labelRows = Lists.newArrayList();
		Export[] sortedImports = Iterables.toArray(imports, Export.class);
		Arrays.sort(sortedImports, new Comparator<Export>() {

			@Override
			public int compare(Export o1, Export o2) {
				// they are not supposed to be null, but just to make sure we don't get a NPE
				// because of a bug elsewhere
				if(o1 == null || o2 == null)
					return 0;
				return o1.getName().compareTo(o2.getName());
			}

		});
		for(Export a : sortedImports) {
			if(a == null) {
				System.err.println("Null Export found");
				continue;
			}
			final boolean ambiguous = ambiguities == null
					? false
					: ambiguities.contains(a);
			labelRows.add(styles.labelRow(STYLE__IMPORT_ROW, //
				styles.labelCell( //
					ambiguous
							? STYLE__IMPORT_AMBIGUOUS_NAME_CELL
							: STYLE__IMPORT_NAME_CELL, //
					a.getName()).withStyle(//
					styles.href(toHREF_URL(a))), //
				styles.labelCell(STYLE__IMPORT_TYPE_CELL, labelOfType(a.getEClass()))));
		}
		return StyleSet.withStyle(styles.labelFormat(styles.labelTable(
			STYLE__IMPORT_TABLE, labelRows.toArray(new LabelRow[0]))));

	}

	private StyleSet labelStyleForUnresolved(ModuleEdge me) {
		Iterable<String> unresolved = me.unresolved;

		List<LabelRow> labelRows = Lists.newArrayList();
		String[] sortedImports = Iterables.toArray(unresolved, String.class);
		Arrays.sort(sortedImports, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
		for(String a : sortedImports) {
			labelRows.add(styles.labelRow(STYLE__UNRESOLVED_ROW, //
				styles.labelCell(STYLE__UNRESOLVED_NAME_CELL, a).withStyle( //
					styles.href(toHREF_UNRESOLVED(me.from.name, a))) //

			));
		}
		return StyleSet.withStyle(styles.labelFormat(styles.labelTable(
			STYLE__IMPORT_TABLE, labelRows.toArray(new LabelRow[0]))));

	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private String makeTooltip(ModuleNodeData a, ModuleNodeData b) {
		final StringBuilder tooltipBuilder = new StringBuilder();
		tooltipBuilder.append(a.name);
		tooltipBuilder.append(" &#8658; "); // i.e. &rArr; a non defined entity in SVG
		if(b != null)
			tooltipBuilder.append(b.name);
		return tooltipBuilder.toString();
	}

	public void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, BuildResult buildResult) {
		if(title == null)
			title = "Module Dependencies";

		AllModuleReferences all = buildResult.getAllModuleReferences();
		produceGraph(cancel, title, roots, output, all.getRoot(), buildResult.getModuleData(), all);
	}

	/**
	 * 
	 * @param root
	 * @param moduleData
	 *            Name -> 0* MetadataInfo representing one version of a module with given name
	 */
	public void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, File root,
			Multimap<ModuleName, MetadataInfo> moduleData, AllModuleReferences exportData) {

		if(cancel == null)
			cancel = new NullIndicator();

		this.root = root;

		Map<File, ModuleNodeData> fileMap = calculateDependencyData(root, moduleData, exportData);

		// Render all unless there is a list of roots to include. If roots are given, mark their
		// transitive closures, and tell renderer to render only those that are marked.
		boolean renderAll = roots == null || roots.length == 0;
		if(!renderAll)
			for(File f : roots) {
				ModuleNodeData x = file2Module(f, fileMap);
				if(x != null)
					x.mark();
			}
		else {
			// mark all
			for(ModuleNodeData x : fileMap.values())
				x.mark();
		}
		RootGraph g = produceRootGraph(cancel, title, moduleData, exportData, renderAll);

		instanceRules.addAll(theme.getInstanceRules());
		dotRenderer.write(cancel, output, g, theme.getDefaultRules(), instanceRules);
	}

	/**
	 * Produces the graph with vertex and edge data. If renderAll is false, only marked ModuleNodeData
	 * will be rendered.
	 * 
	 * @param title
	 * @param moduleData
	 * @param exportData
	 * @param renderAll
	 * @return
	 */
	private RootGraph produceRootGraph(ICancel cancel, String title, Multimap<ModuleName, MetadataInfo> moduleData,
			AllModuleReferences exportData, boolean renderAll) {

		if(title == null)
			title = "";
		if(cancel == null)
			cancel = new NullIndicator();

		RootGraph g = new RootGraph(title, "RootGraph", "root");

		// Create Graph vertexes, one per existing module
		// and one for the root/non-modular
		for(ModuleNodeData mnd : moduleNodeData.values()) {
			cancel.assertContinue();
			if(renderAll || mnd.marked)
				if(mnd.isNode())
					g.addVertex(createVertexForPPNodeNode(mnd));
				else
					g.addVertex(createVertexForModuleNode(mnd));
		}

		// only draw the root node if it has been marked (incoming dependency), or has outgoing
		// when not rendering all, the non modular must have incoming dependencies to show.
		if(renderAll && nonModularNode.outgoing.size() > 0 || nonModularNode.marked) {
			Vertex v = createVertexForRootNode(nonModularNode);
			g.addVertex(v);
		}

		// Create Vertexes for all Edges that have data
		// (very large labels on edges does not work that well as they often overlap)
		// Skip edges that are from non marked module nodes unless everything is rendered
		for(ModuleEdge me : moduleEdges) {
			// filter out self references
			if(me.from == me.to)
				continue;
			if(renderAll || me.from.marked) {
				cancel.assertContinue();
				Vertex v = createVertexForEdge(me);
				if(v != null) {
					g.addVertex(v);
				}
			}
		}

		// Create Edges
		// If an edge has a vertex, it needs to be drawn as two separate graph edges

		for(ModuleNodeData a : Iterables.concat(moduleNodeData.values(), Collections.singleton(nonModularNode))) {
			if(!(renderAll || a.marked))
				continue; // skip non marked unless all is rendered

			for(ModuleNodeData b : a.outgoing.keySet()) {
				cancel.assertContinue();

				int resolved = 0;
				int unresolved = 0;
				int implied = 0;
				int count = 0;
				ModuleEdge edges[] = new ModuleEdge[3];
				for(ModuleEdge e : a.outgoing.get(b)) {
					edges[count++] = e;
					switch(e.edgeType) {
						case IMPLIED:
							implied++;
							break;
						case UNRESOLVED:
							unresolved++;
							break;
						case RESOLVED:
							resolved++;
							break;
						default:
							throw new IllegalStateException("Illegal edge type found");
					}
				}
				String tooltipString = makeTooltip(a, b);
				List<String> styleClasses = classesFor(a, b);

				// CASE 1
				// A --> [...] --> B
				// A imports from resolved B
				if(count == 2 && resolved == 1 && implied == 1) {
					Edge e1 = new Edge("", STYLE_EDGE__IMPORT, a.getVertex(), edges[1].getVertex());
					Edge e2 = new Edge(getVersionLabel(edges[0]), STYLE_EDGE__RESOLVED_DEP, //
					edges[1].getVertex(), b.getVertex());
					g.addEdge(e1, e2);
					addTooltip(tooltipString, e1, e2);
					addStyleClasses(styleClasses, e1, e2);
					addEdgeHref(a, b, e1, e2);
				}
				else if(count == 1 && implied == 1) {
					// CASE 5
					// A --> [...]
					if(edges[0].to == null) {
						Edge e1 = new Edge("unresolved", STYLE_EDGE__UIMPORT, a.getVertex(), edges[0].getVertex());
						g.addEdge(e1);
						addTooltip(tooltipString, e1);
						addStyleClasses(styleClasses, e1);
						addEdgeHref(a, b, e1);
					}
					// CASE 8 (reference to pptp - do not draw imports -> pptp part)
					// A -->[...]
					else if(edges[0].to == pptpNode) {
						Edge e1 = new Edge("puppet", STYLE_EDGE__IMPORT, a.getVertex(), edges[0].getVertex());
						g.addEdge(e1);
						// default tooltip == label
						addStyleClasses(styleClasses, e1);
						addEdgeHref(a, b, e1);

					}
					// CASE 2 (and CASE 0 - self reference)
					// A -->[...] ~~> B
					else {
						if(edges[0].from != edges[0].to) { // skip self references
							Edge e1 = new Edge("", STYLE_EDGE__IMPORT, a.getVertex(), edges[0].getVertex());
							Edge e2 = new Edge(
								"implicit", STYLE_EDGE__IMPLIED_DEP, edges[0].getVertex(), edges[0].to.getVertex());
							g.addEdge(e1, e2);
							addTooltip(tooltipString, e1, e2);
							addStyleClasses(styleClasses, e1, e2);
							addEdgeHref(a, b, e1, e2);
						}
					}
				}
				// CASE 3
				// A --> [...] ~~>(not in range) B
				else if(count == 2 && implied == 1 && unresolved == 1) {
					Edge e1 = new Edge("", STYLE_EDGE__IMPORT, a.getVertex(), edges[1].getVertex());
					String label = "implicit\\nunresolved\\n" + getVersionLabel(edges[0]);
					Edge e2 = new Edge(label, STYLE_EDGE__UNRESOLVED_IMPLIED_DEP, //
					edges[1].getVertex(), edges[1].to.getVertex());
					g.addEdge(e1, e2);
					addTooltip(tooltipString, e1, e2);
					addStyleClasses(styleClasses, e1, e2);
					addEdgeHref(a, b, e1, e2);
				}

				else if(count == 1 && unresolved == 1) {
					// CASE 7
					// A ~~> B where B is not in range
					if(edges[0].to.exists()) {
						String label = "unresolved\\n" + getVersionLabel(edges[0]);
						Edge e1 = new Edge(label, STYLE_EDGE__UNRESOLVED_IMPLIED_DEP, //
						a.getVertex(), b.getVertex());
						g.addEdge(e1);
						addTooltip(tooltipString, e1);
						addStyleClasses(styleClasses, e1);
						addEdgeHref(a, b, e1);
					}
					// CASE 4
					// A --> B where B does not exist
					else {
						String label = "unresolved\\n" + getVersionLabel(edges[0]);
						Edge e1 = new Edge(label, STYLE_EDGE__UNRESOLVED_DEP, //
						a.getVertex(), b.getVertex());
						g.addEdge(e1);
						addTooltip(tooltipString, e1);
						addStyleClasses(styleClasses, e1);
						addEdgeHref(a, b, e1);
					}
				}
				// CASE 6
				// A --> B
				// (nothing is imported from B)
				else if(count == 1 && resolved == 1) {
					Edge e1 = new Edge(getVersionLabel(edges[0]), STYLE_EDGE__RESOLVED_DEP, //
					a.getVertex(), b.getVertex());
					g.addEdge(e1);
					addTooltip(tooltipString, e1);
					addStyleClasses(styleClasses, e1);
					addEdgeHref(a, b, e1);
				}
				else {
					StringBuilder builder = new StringBuilder();
					builder.append("Internal Error - illegal combination of recorded edges");
					builder.append(" count=");
					builder.append(count);
					builder.append(" resolved=");
					builder.append(resolved);
					builder.append(" unresolved=");
					builder.append(unresolved);
					builder.append(" a =");
					builder.append(a.name);
					builder.append(" b=");
					builder.append(b.name);
					throw new IllegalStateException(builder.toString());
				}

			}
		}
		return g;
	}

	public String toHREF_UNRESOLVED(ModuleName fromModuleName, String name) {
		return hrefProducer.hrefForUnresolved(fromModuleName, name);
	}

	private String toHREF_URL(Export e) {
		return hrefProducer.href(e, this.root);
	}

	public String toHREF_URL(MetadataInfo mi) {
		return hrefProducer.href(mi, this.root);
	}
}
