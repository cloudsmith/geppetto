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
package com.puppetlabs.geppetto.graph.catalog;

/**
 * Definition of styles used by DependencyGraphTheme
 * 
 */
public interface CatalogGraphStyles {
	static final String STYLE_Resource = "Resource";

	static final String STYLE_ResourceEdge = "ResourceEdge";

	static final String STYLE_ResourceFileInfoCell = "ResourceFileInfoCell";

	static final String STYLE_ResourceFileInfoRow = "ResourceFileInfoRow";

	static final String STYLE_ResourcePropertyName = "ResourcePropertyName";

	static final String STYLE_ResourcePropertyMarker = "ResourcePropertyMarker";

	static final String STYLE_ResourcePropertyRow = "ResourcePropertyRow";

	static final String STYLE_ResourcePropertyValue = "ResourcePropertyValue";

	static final String STYLE_ResourceTable = "ResourceTable";

	static final String STYLE_ResourceTitleCell = "ResourceTitleCell";

	static final String STYLE_ResourceTitleRow = "ResourceTitleRow";

	static final String STYLE_Removed = "Removed";

	static final String STYLE_UnModified = "UnModiied";

	static final String STYLE_Added = "Added";

	static final String STYLE_Modified = "Modified";

	static final String STYLE_SubscribeEdge = "SubscribeEdge";

	static final String STYLE_BeforeEdge = "BeforeEdge";

	static final String STYLE_RequireEdge = "RequireEdge";

	static final String STYLE_NotifyEdge = "NotifyEdge";

	static final String STYLE_MissingResource = "MissingResource";

}
