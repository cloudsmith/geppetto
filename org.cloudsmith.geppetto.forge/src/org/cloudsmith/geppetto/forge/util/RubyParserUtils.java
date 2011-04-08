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
package org.cloudsmith.geppetto.forge.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;

public abstract class RubyParserUtils {

	/**
	 * Find a list of nodes that correspond to a specific path of node types extending
	 * from a root node. The type of the root node is not included in the path.
	 * 
	 * @param root
	 *            The root node
	 * @param path
	 *            The node type path
	 * @return The list of nodes, possibly empty but never <code>null</null>
	 */
	public static List<Node> findNodes(Node root, NodeType[] path) {
		ArrayList<Node> result = new ArrayList<Node>();
		findNodes(root, path, 0, result);
		return result;
	}

	private static void findNodes(Node root, NodeType[] path, int pathIndex, List<Node> result) {
		if(pathIndex >= path.length) {
			result.add(root);
			return;
		}

		NodeType searchedType = path[pathIndex++];
		for(Node child : root.childNodes()) {
			while(child instanceof NewlineNode)
				child = ((NewlineNode) child).getNextNode();
			if(child == null)
				continue;
			if(child.getNodeType() == searchedType)
				findNodes(child, path, pathIndex, result);
		}
	}

	/**
	 * Parse a File containing Ruby syntax and return the root node of the AST.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static RootNode parseFile(File file) throws IOException {
		Parser parser = new Parser();
		String fileStr = file.getAbsolutePath();
		Reader reader = new BufferedReader(new FileReader(file));
		try {
			return (RootNode) parser.parse(fileStr, reader, new ParserConfiguration(0, CompatVersion.RUBY1_9));
		}
		catch(SyntaxException e) {
			throw new IOException("Unable to parse " + fileStr, e);
		}
		finally {
			StreamUtil.close(reader);
		}
	}
}
