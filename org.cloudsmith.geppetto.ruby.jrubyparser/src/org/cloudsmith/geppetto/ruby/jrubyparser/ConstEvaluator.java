package org.cloudsmith.geppetto.ruby.jrubyparser;

import java.util.List;
import java.util.Map;

import org.jrubyparser.ast.ArrayNode;
import org.jrubyparser.ast.Colon2Node;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.HashNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.SymbolNode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Evaluates (a limited set of) Ruby constant expressions.
 *
 */
public class ConstEvaluator extends AbstractJRubyVisitor {
	public Object eval(Node node) {
		return node.accept(this);
	}
	@SuppressWarnings("unchecked")
	private List<String> stringList(Object x) {
		if(x instanceof List)
			return (List<String>)x; // have faith
		if(x instanceof String)
			return Lists.newArrayList((String)x);
		throw new IllegalArgumentException("Not a string or lists of strings");
	}
	@Override
	public Object visitArrayNode(ArrayNode iVisited) {
		List<Object> result = Lists.newArrayList();
		for(Node n : iVisited.childNodes())
			result.add(eval(n));
		return result;
	}
	@Override
	public Object visitSymbolNode(SymbolNode iVisited) {
		return iVisited.getName();
	}
	@Override
	public Object visitHashNode(HashNode iVisited) {
		Map<Object, Object> result = Maps.newHashMap();
		List<Node> children = iVisited.childNodes();
		children = children.get(0).childNodes();
		for(int i = 0; i < children.size(); i++) {
			Object key = eval(children.get(i++));
			Object value = eval(children.get(i));
			result.put(key, value);
		}
		return result;
	}
	@Override
	public Object visitConstNode(ConstNode iVisited) {
		return iVisited.getName();
	}
	@Override
	public Object visitColon2Node(Colon2Node iVisited) {
		return splice(eval(iVisited.getLeftNode()), iVisited.getName());
	}
	private List<String> splice(Object a, Object b) {
		 return addAll(stringList(a),stringList(b));
	}
	private List<String> addAll(List<String> a, List<String> b) {
		a.addAll(b);
		return a;
	}
}