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
package org.cloudsmith.geppetto.validation.runner;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter.Location;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

/**
 * Interface for data describing exports per Module. TODO: BAD NAME - RENAME TO
 * AllModulesState
 * 
 */
public class AllModuleReferences implements Serializable {

	/**
	 * Describes an exported class. One Export describes the class itself and a
	 * list of Exports describe the parameters.
	 * 
	 */
	public static class ClassDescription {
		private Export theClass;

		private Map<String, Export> theParameters;

		public ClassDescription(Export classExport, Map<String, Export> parameters) {
			theClass = classExport;
			theParameters = parameters;
		}

		/**
		 * Obtains an Export for the class. To obtain the unqualified name use {@link Export#getLastNameSegment() }.
		 * 
		 * @return An export describing the location of the class declaration
		 */
		public Export getExportedClass() {
			return theClass;
		}

		/**
		 * Obtains the Parameters exported for the class. To obtain the
		 * unqualified name of a parameter use the key of the map, or call {@link Export#getLastNameSegment() }
		 * 
		 * @return
		 */
		public Map<String, Export> getExportedParameters() {
			return Collections.unmodifiableMap(theParameters);
		}
	}

	public interface Export extends Serializable {
		/**
		 * The source text (if any) associated with a DefinitionArgument's value
		 * expression. An implementation should trim the result from leading and
		 * trailing whitespace. This is essentially the return of the sequence
		 * of characters found in the file {@link #getFile()} starting at {@link #getStart()} and extending for {@link #getLength()} characters
		 * trimmed of leading and trailing whitespace.
		 * 
		 * @return the source text, or null if a DefinitionArgument has no
		 *         value.
		 */
		String getDefaultValueText();

		/**
		 * The EClass of the exported element (a PPPackage.Literals.xxx class,
		 * or a PPTPPackage.Literals.xxx class).
		 * 
		 * @return
		 */
		public EClass getEClass();

		/**
		 * The file from which the entry is exported.
		 * 
		 * @return
		 */
		public File getFile();

		/**
		 * @return the last segment of the qualified name of this export
		 */
		public String getLastNameSegment();

		/**
		 * The length of the textual description (starting at {@link #getStart()}, or 0 if not available.
		 * 
		 * @return
		 */
		public int getLength();

		/**
		 * The line on which the export is found, or -1 if not available.
		 */
		public int getLine();

		/**
		 * The qualified name in string form of the exported element.
		 * 
		 * @return
		 */
		public String getName();

		/**
		 * @return the name as a string without the last name segment
		 */
		public String getNameWithoutLastSegment();

		/**
		 * The name of the parent the exported element "inherits from", or null
		 * if this element does not inherit. The parent name is a qualified name
		 * in string form.
		 * 
		 * @return
		 */
		public String getParentName();

		/**
		 * The start offset of the definition in source (starting at the
		 * beginning of the file), or -1 if not available.
		 * 
		 * @return
		 */
		public int getStart();

	}

	public interface ImportedName extends Serializable {
		/**
		 * The importing file
		 * 
		 * @return
		 */
		File getFile();

		/**
		 * The length of the source text of the imported
		 * 
		 * @return
		 */
		int getLength();

		/**
		 * The line number of the first line where the reference occurs.
		 * 
		 * @return
		 */
		int getLine();

		/**
		 * The qualified name of the imported
		 * 
		 * @return
		 */
		String getName();

		/**
		 * The start offset of the imported
		 * 
		 * @return
		 */
		int getStart();

	}

	private static class ImportedNameImpl implements ImportedName {
		private static final long serialVersionUID = 1L;

		private File file;

		private String name;

		private int start;

		private int length;

		private int line;

		private ImportedNameImpl(File f, String name, int line, int start, int length) {
			this.file = f;
			this.name = name;
			this.start = start;
			this.length = length;
			this.line = line;
		}

		@Override
		public File getFile() {
			return file;
		}

		@Override
		public int getLength() {
			return length;
		}

		@Override
		public int getLine() {
			return line;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getStart() {
			return start;
		}

	}

	private static final long serialVersionUID = 1L;

	private Multimap<File, Export> exportMap;

	private Map<File, Multimap<File, Export>> importMap;

	private Map<File, Multimap<File, Export>> ambiguityMap;

	private Multimap<File, ImportedName> unresolvedImports;

	private File root;

	private Multimap<String, String> restricted;

	private final static Multimap<File, Export> EmptyExports = ArrayListMultimap.create();

	private final static Multimap<File, String> EmptyUnresolved = ArrayListMultimap.create();

	private final static Map<File, Multimap<File, Export>> EmptyImports = Collections.emptyMap();

	private final static Multimap<String, String> EmptyRestricted = ArrayListMultimap.create();

	/**
	 * Creates an empty instance.
	 */
	public AllModuleReferences() {
		restricted = Multimaps.unmodifiableMultimap(EmptyRestricted);
	}

	private Map<String, Export> _collectParameters(Set<String> processed, Map<String, Export> result, String className,
			Map<String, Export> classes, Multimap<String, Export> parameters) {
		// if there was no parent
		if(className == null || className.length() < 1)
			return result;
		if(processed.contains(className))
			return result; // circular
		processed.add(className);

		Export theClassExport = classes.get(className);
		if(theClassExport == null)
			return result;

		for(Export e : parameters.get(className)) {
			String pName = e.getLastNameSegment();
			if(!result.containsKey(pName))
				result.put(pName, e);
		}
		return _collectParameters(processed, result, theClassExport.getParentName(), classes, parameters);
	}

	/**
	 * Add an ambiguity of e from exporting module to the importing module.
	 * 
	 * @param importingModule
	 * @param exportingModule
	 * @param e
	 */
	public void addAmbiguity(File importingModule, File exportingModule, Export e) {
		if(ambiguityMap == null)
			ambiguityMap = Maps.newHashMap();
		Multimap<File, Export> ambiguities = ambiguityMap.get(importingModule);
		if(ambiguities == null) {
			// only allow unique key-value combinations
			ambiguities = HashMultimap.create();
			// imports = ArrayListMultimap.create();
			ambiguityMap.put(importingModule, ambiguities);
		}
		if(e == null) {
			System.err.println("null Export found");
		}
		ambiguities.put(exportingModule, e);
	}

	/**
	 * Add an export of e from the given module.
	 * 
	 * @param moduleDir
	 * @param e
	 */
	public void addExport(File moduleDir, Export e) {
		if(exportMap == null)
			exportMap = ArrayListMultimap.create();
		exportMap.put(moduleDir, e);
	}

	/**
	 * Add an import of e from exporting module to the importing module.
	 * 
	 * @param importingModule
	 * @param exportingModule
	 * @param e
	 */
	public void addImport(File importingModule, File exportingModule, Export e) {
		if(importMap == null)
			importMap = Maps.newHashMap();
		Multimap<File, Export> imports = importMap.get(importingModule);
		if(imports == null) {
			// only allow unique key-value combinations
			imports = HashMultimap.create();
			// imports = ArrayListMultimap.create();
			importMap.put(importingModule, imports);
		}
		if(e == null) {
			System.err.println("null Export found");
		}
		imports.put(exportingModule, e);
	}

	/**
	 * @param importingModuleDir
	 * @param uri
	 * @param unresolved
	 */
	public void addUnresolved(File importingModuleDir, URI uri, Map<QualifiedName, List<Location>> unresolved,
			Function<QualifiedName, String> fQualifiedToString) {
		if(unresolved.isEmpty())
			return;
		if(!uri.isFile())
			return;
		final File file = new File(uri.toFileString());
		if(unresolvedImports == null) {
			unresolvedImports = ArrayListMultimap.create();
		}

		for(Entry<QualifiedName, List<Location>> e : unresolved.entrySet()) {
			final String name = fQualifiedToString.apply(e.getKey());
			for(Location location : e.getValue())
				unresolvedImports.put(importingModuleDir, //
					new ImportedNameImpl(file, name, location.getLength(), location.getOffset(), location.getLength()));
		}
	}

	private Map<String, Export> collectParameters(String className, Map<String, Export> classes,
			Multimap<String, Export> parameters) {
		Map<String, Export> result = Maps.newHashMap();
		Set<String> processed = Sets.newHashSet();
		return _collectParameters(processed, result, className, classes, parameters);
	}

	private String file2ContainerKey(File f) {
		String path = f.getPath();
		if(!(path.equals("_pptp") || f.isAbsolute()))
			f = new File(root, path);

		return f.getPath();

	}

	/**
	 * Returns an exported class with the given name, or null if no such class
	 * can be found.
	 * 
	 * @param className
	 *            the fqn of the wanted class
	 * @param visibleExports
	 *            - all exports to consider
	 * @return
	 */
	public Export findExportedClass(String className, Iterable<Export> visibleExports) {
		if(className == null || className.length() < 1)
			return null;
		final EClass HostClassLiteral = PPPackage.Literals.HOST_CLASS_DEFINITION;
		for(Export e : visibleExports) {
			if(HostClassLiteral.isSuperTypeOf(e.getEClass()) && className.equals(e.getName()))
				return e;
		}
		return null;
	}

	/**
	 * Returns an iterable iterating over all exported values from every module.
	 * 
	 * @return
	 */
	public Iterable<Export> getAllExported() {
		return getExportMap().values();
	}

	/**
	 * Returns an unmodifiable Map of importing module to a {@link Multimap} of
	 * ambiguously imported exports per module. To get what is ambiguously
	 * imported from module B into module A perform result.get(A).get(B).
	 * 
	 * @return
	 */
	public Map<File, Multimap<File, Export>> getAmbiguityMap() {
		return Collections.unmodifiableMap(ambiguityMap != null
				? ambiguityMap
				: EmptyImports); // reuse "EmptyImports"

	}

	public List<ClassDescription> getClassDescriptions(Iterable<Export> visibleExports) {
		ArrayList<ClassDescription> result = Lists.newArrayList();
		final EClass HostClassLiteral = PPPackage.Literals.HOST_CLASS_DEFINITION;
		final EClass DefinitionArgumentLiteral = PPPackage.Literals.DEFINITION_ARGUMENT;
		final Map<String, Export> classes = Maps.newHashMap();
		final Multimap<String, Export> parameters = ArrayListMultimap.create();
		for(Export e : visibleExports) {
			EClass eClass = e.getEClass();
			if(HostClassLiteral.isSuperTypeOf(eClass)) {
				classes.put(e.getName(), e);
			}
			else if(DefinitionArgumentLiteral.isSuperTypeOf(eClass)) {
				parameters.put(e.getNameWithoutLastSegment(), e);
			}
		}
		for(String className : classes.keySet())
			result.add(new ClassDescription(classes.get(className), //
				collectParameters(className, classes, parameters)));
		return result;
	}

	/**
	 * Returns an Iterable for all Exports of a puppet class. The given iterable
	 * exports should contain exports of wanted visibility e.g. {@link AllModuleReferences#getVisibleExports(File)} if
	 * {@link AllModuleReferences#isVisibilityRestricted(File)} returns true and
	 * only classes visible to content in the container represented by the given
	 * File are wanted, or {@link AllModuleReferences#getAllExported() } if
	 * visibility is not restricted (all non restricted have the same visibility
	 * and it is always all exports).
	 * 
	 * @param exports
	 * @return
	 */
	public Iterable<Export> getClasses(Iterable<Export> exports) {
		return Iterables.filter(exports, new Predicate<Export>() {

			@Override
			public boolean apply(Export input) {
				return PPPackage.Literals.HOST_CLASS_DEFINITION.isSuperTypeOf(input.getEClass());
			}
		});
	}

	/**
	 * Returns an unmodifiable {@link Multimap} containing exports per File,
	 * where a given File is a reference to a module directory.
	 * 
	 * @return
	 */
	private Multimap<File, Export> getExportMap() {
		return Multimaps.unmodifiableMultimap(exportMap != null
				? exportMap
				: EmptyExports);
	}

	/**
	 * Returns an unmodifiable Multimap mapping module directory to a Collection
	 * of Export. The export collection represents what is exported from the
	 * module (if the File is a module directory), the root, or the special file
	 * "_pptp".
	 * 
	 * @return An unmodifiable multimap from module to what is exported from
	 *         that module.
	 */
	public Multimap<File, Export> getExportsPerModule() {
		return getExportMap();
	}

	/**
	 * Returns an unmodifiable Map of importing module to a {@link Multimap} of
	 * imported exports per module. To get what is imported from module B into
	 * module A perform result.get(A).get(B).
	 * 
	 * @return
	 */
	public Map<File, Multimap<File, Export>> getImportMap() {
		return Collections.unmodifiableMap(importMap != null
				? importMap
				: EmptyImports);

	}

	public Iterable<String> getParameterNames(Export exportedClass, Iterable<Export> visibleExports) {
		return Iterables.transform(getParameters(exportedClass, visibleExports), new Function<Export, String>() {

			@Override
			public String apply(Export from) {
				return from.getLastNameSegment();
				// return lastSegmentOfQualifiedName(from.getName());
			}
		});
	}

	public Iterable<Export> getParameters(Export exportedClass, Iterable<Export> visibleExports) {
		// figure out which parameters to include
		final List<Export> classes = Lists.newArrayList();
		for(Export parent = exportedClass; parent != null; parent = findExportedClass(
			parent.getParentName(), visibleExports)) {
			if(classes.contains(parent))
				break; // circular
			classes.add(parent);
		}
		final EClass DefinitionArgumentLiteral = PPPackage.Literals.DEFINITION_ARGUMENT;
		final Map<String, Export> parameters = Maps.newHashMap();
		// final List<Export> parameters = Lists.newArrayList();
		for(Export e : visibleExports) {
			if(!DefinitionArgumentLiteral.isSuperTypeOf(e.getEClass()))
				continue;
			for(Export c : classes)
				if(e.getName().startsWith(c.getName())) {
					String lastSegment = e.getLastNameSegment();
					// String lastSegment =
					// lastSegmentOfQualifiedName(e.getName());
					if(parameters.get(lastSegment) == null)
						parameters.put(e.getLastNameSegment(), e);
					// parameters.put(lastSegmentOfQualifiedName(e.getName()),
					// e);
				}

		}
		return parameters.values();

	}

	/**
	 * Returns an (unmodifiable) multimap describing the restricted visibility
	 * of modules. If a module's directory is a key in the returned map, its
	 * view is restricted to containers listed in the value for that key.
	 * 
	 * @return
	 */
	public Multimap<String, String> getRestricted() {
		return restricted;
	}

	/**
	 * Returns the root to use for relative lookups
	 * 
	 * @return
	 */
	public File getRoot() {
		return root;
	}

	/**
	 * Returns an unmodifiable {@link Multimap} of the full information Module
	 * -> unresolved names/file/locations.
	 * 
	 * @return
	 */
	public Multimap<File, ImportedName> getUnresolved() {
		return Multimaps.unmodifiableMultimap(unresolvedImports);
	}

	/**
	 * Returns an unmodifiable {@link Multimap} of Module -> unresolved names.
	 * 
	 * @return
	 */
	public Multimap<File, String> getUnresolvedMap() {
		if(unresolvedImports == null)
			return EmptyUnresolved;

		Multimap<File, String> result = ArrayListMultimap.create();
		for(File moduleFile : unresolvedImports.keySet()) {
			result.putAll(
				moduleFile,
				Iterables.transform(unresolvedImports.get(moduleFile), new Function<ImportedName, String>() {

					@Override
					public String apply(ImportedName from) {
						return from.getName();
					}
				}));
		}
		return result;
	}

	/**
	 * Returns the exports visible to code in the given module. The
	 * moduleDirectory may be relative in which case the root must have been
	 * set. If module directory path is the special "_pptp" the content of the
	 * target platform is obtained. If the moduleDirectory is the root path, all
	 * non modular exports (from manifests and target contributions from ruby
	 * code) not in any module.
	 * 
	 * @param moduleDirectory
	 * @return
	 */
	public Iterable<Export> getVisibleExports(File moduleDirectory) {
		// make absolute if relative and not _pptp
		String containerKey = file2ContainerKey(moduleDirectory);

		// restricted returns iterator over lookup of all visible handles
		if(restricted.containsKey(containerKey)) {
			Iterable<String> r = restricted.get(containerKey);
			return Iterables.concat(Iterables.transform(r, new Function<String, Iterable<Export>>() {

				@Override
				public Iterable<Export> apply(String from) {
					return getExportMap().get(new File(from));
				}
			}));
		}
		return getExportMap().values();
	}

	/**
	 * Returns true if the given moduleDirectory has limited visibility into all
	 * exports. This is useful to know as the set of moduleDirectories with full
	 * visibility see the same set of exports.
	 * 
	 * @param moduleDirectory
	 * @return
	 */
	public boolean isVisibilityRestricted(File moduleDirectory) {
		return restricted.containsKey(file2ContainerKey(moduleDirectory));

	}

	public void setRestricted(Multimap<String, String> restricted) {
		if(restricted == null)
			throw new IllegalArgumentException("null 'restricted'");
		this.restricted = Multimaps.unmodifiableMultimap(restricted);
	}

	/**
	 * Sets the root file to allow relative lookup later.
	 */
	public void setRoot(File root) {
		this.root = root;

	}

}
