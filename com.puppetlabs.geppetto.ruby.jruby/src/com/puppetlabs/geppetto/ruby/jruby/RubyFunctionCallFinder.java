package org.cloudsmith.geppetto.ruby.jruby;

import java.util.LinkedList;

import org.jruby.ast.FCallNode;
import org.jruby.ast.FCallTwoArgBlockNode;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.Node;

import com.google.common.collect.Iterables;

public class RubyFunctionCallFinder {
	
	/**
	 * Returns the first found module with the given qualified name, or null if no such module
	 * was found. The qualified name should be specified in natural order 
	 * e.g. new String[] { "Puppet", "Parser", "Functions" }.
	 * 
	 * @param root
	 * @param qualifiedName
	 * @return found module or null
	 */
	public FCallNode findFuntion(ModuleNode root, String name) {
		return new FunctionVisitor().findFunction(root, name);
	}
	
	private static class FunctionVisitor extends AbstractJRubyVisitor {

		/**
		 * Returned when a visited node detect it is not meaningful to visit its 
		 * children.
		 */
		public static final Object DO_NOT_VISIT_CHILDREN = new Object();
		private ConstEvaluator constEvaluator = new ConstEvaluator();
		
		private String name = null;
		public FCallNode findFunction(Node root, String name) {
			this.name = name;
			return (FCallNode) findFunction(root);
		}
		
		/**
		 * Visits all nodes in graph, and if visitor returns non-null, the iteration stops
		 * and the returned non-null value is returned.
		 * @param root
		 * @return
		 */
		private Object findFunction(Node root) {
			Object r = root.accept(this);
			if(r != DO_NOT_VISIT_CHILDREN) {
				if(r != null) {
					return r;
				}
				for(Node n : root.childNodes()) {
					r = findFunction(n);
					if(r != null)
						return r;
				}
			}
			return null;
		}
		@Override
		public Object visitFCallNode(FCallNode iVisited) {
			if(name.equals(iVisited.getName()))
					return iVisited;
			return null;
		}
	}
	
}
