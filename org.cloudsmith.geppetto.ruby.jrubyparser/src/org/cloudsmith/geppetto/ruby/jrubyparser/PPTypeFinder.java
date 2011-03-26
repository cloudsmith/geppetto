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
package org.cloudsmith.geppetto.ruby.jrubyparser;

import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.jrubyparser.ast.BlockAcceptingNode;
import org.jrubyparser.ast.BlockPassNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.IterNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Maps;

/**
 * Find puppet resource type definition(s) in a ruby file.
 *
 */
public class PPTypeFinder {
	private final static String NEWPARAM = "newparam";
	private final static String NEWPROPERTY = "newproperty";
	private final static String NEWTYPE = "newtype";

	private final static String[] PUPPET_TYPE_RECEIVER = new String[] {"Puppet", "Type"};

	private ConstEvaluator constEvaluator = new ConstEvaluator();

	/**
	 * Returns the first found call with given name on given receiver FQN.
	 * 
	 * @param root
	 * @param qualifiedName
	 * @return found module or null
	 */
	private CallNode findReceiverCall(Node root, String opName, String... receiverFQN) {
		return new OpCallVisitor().findOpCall(root, opName, receiverFQN);
	}

	public PPTypeInfo findTypeInfo(Node root) {
		CallNode newTypeCall = findReceiverCall(root, NEWTYPE, PUPPET_TYPE_RECEIVER);
		if(newTypeCall == null)
			return null;

		// should have at least one argument, the name of the type
		String typeName = getFirstArg(newTypeCall);
		if(typeName == null)
			return null;

		return createTypeInfo(safeGetBodyNode(newTypeCall), typeName);

	}
	private Node safeGetBodyNode(BlockAcceptingNode node) {
		Node n = node.getIterNode();
		if(n == null)
			return null;
		switch(n.getNodeType()) {
		case ITERNODE:
			return ((IterNode)n).getBodyNode();
		case BLOCKPASSNODE:
			return ((BlockPassNode)n).getBodyNode();			
		}
		return null;
	}
	private PPTypeInfo createTypeInfo(Node root, String typeName) {
		if(root == null)
			return null;

		Map<String, PPTypeInfo.Entry> propertyMap = Maps.newHashMap();
		Map<String, PPTypeInfo.Entry> parameterMap = Maps.newHashMap();
		String typeDocumentation = ""; 

		for(Node n : root.childNodes()) {
			if(n.getNodeType() == NodeType.NEWLINENODE)
				n = ((NewlineNode)n).getNextNode();

			switch(n.getNodeType()) {
			case FCALLNODE:
				FCallNode callNode = (FCallNode)n;
				if(NEWPARAM.equals(callNode.getName()))
					parameterMap.put(getFirstArg(callNode), getEntry(callNode));
				else if(NEWPROPERTY.equals(callNode.getName()))
					propertyMap.put(getFirstArg(callNode), getEntry(callNode));
				break;
			case INSTASGNNODE:
				InstAsgnNode docNode = (InstAsgnNode)n;
				if("@doc".equals(docNode.getName()))
					typeDocumentation = getStringArgDefault(docNode.getValueNode(), "");
			default:
				break;
			}
		}
		PPTypeInfo typeInfo = new PPTypeInfo(typeName, typeDocumentation,propertyMap, parameterMap);
		return typeInfo;
	}
	private String getStringArgDefault(Node n, String defaultValue) {
		String x = getStringArg(n);
		return x == null ? defaultValue : x;
	}
	private String getStringArg(Node n){
		Object x = constEvaluator.eval(n);
		if(! (x instanceof String))
			return null;
		return (String)x;
	}
	/**
	 * Returns the first String argument from a node with arguments, or null if there are no arguments
	 * or the argument list was not a list.
	 * @param callNode
	 * @return
	 */
	private String getFirstArg(IArgumentNode callNode) {
		Object result = constEvaluator.eval(callNode.getArgsNode());
		if(! (result instanceof List<?>))
			return null;
		List<?> argList = (List<?>)result;
		if(argList.size() < 1)
			return null;
		return argList.get(0).toString();
	}
	private String getFirstArgDefault(IArgumentNode callNode, String defaultValue) {
		String x = getFirstArg(callNode);
		return x == null ? defaultValue : x;
	}
	PPTypeInfo.Entry getEntry(FCallNode callNode) {
		Node bodyNode = safeGetBodyNode(callNode);
		if(bodyNode != null)
			for(Node n : bodyNode.childNodes()) {
				if(n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode)n).getNextNode();
				if(n.getNodeType() == NodeType.FCALLNODE) {
					FCallNode cn = (FCallNode)n;
					if("desc".equals(cn.getName()))
						return new PPTypeInfo.Entry(getFirstArgDefault(cn, ""));
				}
			}

		return new PPTypeInfo.Entry("");
	}
	private static class OpCallVisitor extends AbstractJRubyVisitor {

		/**
		 * Returned when a visited node detect it is not meaningful to visit its 
		 * children.
		 */
		public static final Object DO_NOT_VISIT_CHILDREN = new Object();

		private String name = null;
		private String[] fqn = null;
		private ConstEvaluator constEvaluator = new ConstEvaluator();

		public CallNode findOpCall(Node root, String name, String... receiverFQN) {
			this.name = name;
			this.fqn = receiverFQN;
			return (CallNode) findOpCall(root);
		}

		/**
		 * Visits all nodes in graph, and if visitor returns non-null, the iteration stops
		 * and the returned non-null value is returned.
		 * @param root
		 * @return
		 */
		private Object findOpCall(Node root) {
			Object r = root.accept(this);
			if(r != DO_NOT_VISIT_CHILDREN) {
				if(r != null) {
					return r;
				}
				for(Node n : root.childNodes()) {
					r = findOpCall(n);
					if(r != null)
						return r;
				}
			}
			return null;
		}

		/**
		 * Finds fqn[0..n].name
		 */
		@Override
		public Object visitCallNode(CallNode iVisited) {
			if(!name.equals(iVisited.getName()))
				return null;
			Object x = constEvaluator.eval(iVisited.getReceiverNode());
			if(! (x instanceof List<?>))
				return null;
			List<?> receiver = (List<?>)x;
			if(receiver.size() != fqn.length)
				return null;
			for(int i = 0; i < fqn.length; i++)
				if(! fqn[i].equals(receiver.get(i)))
					return null;

			return iVisited;
		}
	}

}
