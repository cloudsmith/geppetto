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

/**
 * Definition of styles used by DependencyGraphTheme
 * 
 */
public interface DependencyGraphStyles {
	static final String STYLE_CLASS_RESOLVED_MODULE = "Resolved";

	static final String STYLE_CLASS_AMBIGUOUSLY_RESOLVED_MODULE = "AmbigouslyResolved";

	public static final String STYLE_CLASS_UNRESOLVED_MODULE = "Unresolved";

	public static final String STYLE_CLASS_PPNODE_MODULE = "PPNode";

	public static final String STYLE_CLASS_ROOT = "Root";

	public static final String STYLE_CLASS_IMPORTS = "Imports";

	public static final String STYLE_CLASS_UNRESOLVED_IMPORTS = "UnresolvedImports";

	public final static String STYLE__IMPORT_ROW = "ImportRow";

	public final static String STYLE__IMPORT_NAME_CELL = "ImportNameCell";

	public final static String STYLE__IMPORT_AMBIGUOUS_NAME_CELL = "AmbiguousImportNameCell";

	public final static String STYLE__IMPORT_TYPE_CELL = "ImportTypeCell";

	public final static String STYLE__IMPORT_TABLE = "ImportTable";

	public static final String STYLE__UNRESOLVED_ROW = "UnresolvedRow";

	public static final String STYLE__UNRESOLVED_NAME_CELL = "UnresolvedNameCell";

	public static final String STYLE_EDGE__IMPORT = "EImport";

	public static final String STYLE_EDGE__UIMPORT = "EUImport";

	public static final String STYLE_EDGE__RESOLVED_DEP = "ERDep";

	public static final String STYLE_EDGE__UNRESOLVED_DEP = "EURDep";

	public static final String STYLE_EDGE__IMPLIED_DEP = "EIDep";

	public static final String STYLE_EDGE__UNRESOLVED_IMPLIED_DEP = "EUIDep";

}
