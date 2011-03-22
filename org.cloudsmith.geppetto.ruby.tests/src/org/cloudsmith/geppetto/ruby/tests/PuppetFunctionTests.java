package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;
import java.util.List;

import org.cloudsmith.geppetto.ruby.AbstractJRubyVisitor;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.RubyHelper.Result;
import org.cloudsmith.geppetto.ruby.tests.PuppetFunctionTests.ModuleVisitor;
import org.eclipse.core.runtime.Path;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.Node;

import junit.framework.TestCase;

public class PuppetFunctionTests extends TestCase {

	public void testParseFunction() throws Exception {
		File aRubyFile = TestDataProvider
				.getTestFile(new Path(
						"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest.rb"));
		RubyHelper helper = new RubyHelper();
		Result r = helper.parse(aRubyFile);
		Node root = r.getAST();
		ModuleVisitor moduleVisitor = new ModuleVisitor();
		moduleVisitor.all(root);
		System.err.println("Found root: " + root.toString());
	}
	
//	public List<PPFunctionInfo> getAllPPFunctions(Node root) {
//		root.accept(null);
//	}
	
	public static class PPFunctionInfo {
		public String getFunctionName() {
			return functionName;
		}

		public boolean isrValue() {
			return rValue;
		}

		private String functionName;
		private boolean rValue;
		
		PPFunctionInfo(String name, boolean rvalue) {
			this.functionName = name;
			this.rValue = rvalue;
		}
		
	}
	public static class ModuleVisitor extends AbstractJRubyVisitor {
		@Override
		public Object visitModuleNode(ModuleNode iVisited) {
			return super.visitModuleNode(iVisited);
		}
	}
//	public void foo() { 
//		((NewlineNode 4, 
//			(ModuleNode 4, 
//				(Colon2ConstNode+Functions 4, (Colon2ConstNode+Parser 4, (ConstNode+Puppet 4))), 
//		(NewlineNode 10, (FCallTwoArgBlockNode+newfunction 10, 
//				(ArrayNode 10, (SymbolNode+join 10), (Hash19Node 10, (ArrayNode 10, 
//						(SymbolNode+type 10), (SymbolNode+rvalue 10)))), 
//				(IterNode 10, 
//						(ArgsPreOneArgNode 10, (ArrayNode 10, (ArgumentNode+args 10))), 
//						(NewlineNode 11, (IfNode 11, (CallOneArgNode+is_a? 11, 
//								(CallOneArgNode+[] 11, (DVarNode+args 11), 
//								(ArrayNode 11, (FixnumNode 11))), 
//								(ArrayNode 11, (ConstNode+Array 11))),
//								(NewlineNode 12, (CallOneArgNode+join 12, 
//										(CallOneArgNode+[] 12, (DVarNode+args 12), 
//												(ArrayNode 12, (FixnumNode 12))), 
//												(ArrayNode 12, (CallOneArgNode+[] 12, (DVarNode+args 12), 
//														(ArrayNode 12, (FixnumNode 12)))))), 
//								(NewlineNode 14, (CallOneArgNode+[] 14, (DVarNode+args 14), 
//										(ArrayNode 14, (FixnumNode 14))))))))))))
//}
}
