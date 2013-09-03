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
package org.cloudsmith.geppetto.graph;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;

/**
 * Produces HREFs in the form of javascript function calls.
 * 
 */
public class JavascriptHrefProducer extends RelativeFileHrefProducer {

	/**
	 * produces javascript:edgeClicked('idFrom', 'idTo')
	 * Note that if 'idTo' is '', this means an edge between a moduleId 'idFrom' to "puppet" -
	 * the corresponding Import node has classes "vertex", "FROM__idFrom", and no TO__.
	 */
	@Override
	public String hrefForEdge(String idFrom, String idTo, boolean splitEdge) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:edgeClicked(");
		builder.append("'");
		builder.append(idFrom);
		builder.append("', '");
		builder.append(idTo);
		builder.append("',");
		builder.append(splitEdge);
		builder.append(")");
		return builder.toString();
	}

	/**
	 * produces javascript:edgeClickedPptp('idFrom')
	 * Note that this means an edge between a moduleId 'idFrom' to the puppet runtime -
	 * the corresponding Import node has classes "vertex", "FROM__idFrom", and no TO__.
	 */
	@Override
	public String hrefForEdgeToPptp(String idFrom) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:edgeClickedPptp(");
		builder.append("'");
		builder.append(idFrom);
		builder.append("')");

		return builder.toString();
	}

	/**
	 * produces javascript:edgeClickedUnresolved('idFrom')
	 * Note that this means an edge between a moduleId 'idFrom' to something unknown -
	 * the corresponding Import node has classes "vertex", "FROM__idFrom", and no TO__.
	 */
	@Override
	public String hrefForEdgeToUnresolved(String idFrom, boolean splitEdge) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:edgeClickedUnresolved(");
		builder.append("'");
		builder.append(idFrom);
		builder.append("',");
		builder.append(splitEdge);
		builder.append(")");

		return builder.toString();
	}

	@Override
	public String hrefForUnresolved(ModuleName fromModuleName, String name) {

		StringBuilder builder = new StringBuilder();
		builder.append("javascript:showUnresolved(");
		builder.append("'");
		builder.append(fromModuleName.withSeparator('_'));
		builder.append("', ");
		builder.append("'");
		builder.append(name);
		builder.append("'");
		builder.append(")");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.graph.AbstractHrefProducer#hrefToFileLocation(java.lang.String, int, int,
	 * int) */
	@Override
	protected String hrefToFileLocation(String path, int line, int start, int length) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:showFileContent(");
		builder.append("'");
		builder.append(path);
		builder.append("'");
		builder.append(",");
		builder.append(line);
		builder.append(",");
		builder.append(start);
		builder.append(",");
		builder.append(length);
		builder.append(")");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.graph.AbstractHrefProducer#hrefToModule(java.lang.String) */
	@Override
	protected String hrefToModule(String path) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:showModule(");
		builder.append("'");
		builder.append(path);
		builder.append("'");
		builder.append(")");

		return builder.toString();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.graph.AbstractHrefProducer#hrefToModule(java.lang.String) */
	@Override
	protected String hrefToNode(String path) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:showRole(");
		builder.append("'");
		builder.append(path);
		builder.append("'");
		builder.append(")");

		return builder.toString();
	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * org.cloudsmith.geppetto.graph.AbstractHrefProducer#hrefToPPTP(com.cloudsmith.hammer.puppet.validation.runner
	 * .AllModuleReferences
	 * .Export) */
	@Override
	protected String hrefToPPTP(Export e) {
		StringBuilder builder = new StringBuilder();
		builder.append("javascript:showPPTPInfo(");
		builder.append("'");
		builder.append(e.getName());
		builder.append("', ");
		builder.append("'");
		builder.append(e.getEClass().getName());
		builder.append("'");
		builder.append(")");

		return builder.toString();
	}
}
