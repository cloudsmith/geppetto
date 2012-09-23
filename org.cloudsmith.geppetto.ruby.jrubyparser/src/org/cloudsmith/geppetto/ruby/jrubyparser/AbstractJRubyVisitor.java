package org.cloudsmith.geppetto.ruby.jrubyparser;

import org.jrubyparser.NodeVisitor;
import org.jrubyparser.ast.*;

public class AbstractJRubyVisitor implements NodeVisitor {

	/**
	 * Visits all node in graph.
	 * 
	 * @param root
	 */
	public void all(Node root) {
		root.accept(this);
		for(Node n : root.childNodes())
			all(n);
	}

	/**
	 * Visits all nodes in graph, and if visitor returns non-null, the iteration
	 * stops and the returned non-null value is returned.,
	 * 
	 * @param root
	 * @return
	 */
	public Object untilNotNull(Node root) {
		Object r = root.accept(this);
		if(r != null)
			return r;
		for(Node n : root.childNodes()) {
			r = untilNotNull(n);
			if(r != null)
				return r;
		}
		return null;
	}

	@Override
	public Object visitAliasNode(AliasNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitAndNode(AndNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitArgsCatNode(ArgsCatNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitArgsNode(ArgsNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitArgsPushNode(ArgsPushNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitArrayNode(ArrayNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitAttrAssignNode(AttrAssignNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBackRefNode(BackRefNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBeginNode(BeginNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBignumNode(BignumNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBlockArgNode(BlockArgNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBlockNode(BlockNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBlockPassNode(BlockPassNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitBreakNode(BreakNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitCallNode(CallNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitCaseNode(CaseNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitClassNode(ClassNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitClassVarAsgnNode(ClassVarAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitClassVarDeclNode(ClassVarDeclNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitClassVarNode(ClassVarNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitColon2Node(Colon2Node iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitColon3Node(Colon3Node iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitConstDeclNode(ConstDeclNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitConstNode(ConstNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDAsgnNode(DAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDefinedNode(DefinedNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDefnNode(DefnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDefsNode(DefsNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDotNode(DotNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDRegxNode(DRegexpNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDStrNode(DStrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDSymbolNode(DSymbolNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDVarNode(DVarNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitDXStrNode(DXStrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitEncodingNode(EncodingNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitEnsureNode(EnsureNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitEvStrNode(EvStrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitFalseNode(FalseNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitFCallNode(FCallNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitFixnumNode(FixnumNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitFlipNode(FlipNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitFloatNode(FloatNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitForNode(ForNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitGlobalAsgnNode(GlobalAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitGlobalVarNode(GlobalVarNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitHashNode(HashNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitIfNode(IfNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitInstAsgnNode(InstAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitInstVarNode(InstVarNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitIterNode(IterNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitLiteralNode(LiteralNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitLocalAsgnNode(LocalAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitLocalVarNode(LocalVarNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitMatch2Node(Match2Node iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitMatch3Node(Match3Node iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitMatchNode(MatchNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitModuleNode(ModuleNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitMultipleAsgnNode(MultipleAsgn19Node iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitMultipleAsgnNode(MultipleAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitNewlineNode(NewlineNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitNextNode(NextNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitNilNode(NilNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitNotNode(NotNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitNthRefNode(NthRefNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitOpAsgnAndNode(OpAsgnAndNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitOpAsgnNode(OpAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitOpAsgnOrNode(OpAsgnOrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitOpElementAsgnNode(OpElementAsgnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitOrNode(OrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitPostExeNode(PostExeNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitPreExeNode(PreExeNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRedoNode(RedoNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRegexpNode(RegexpNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRescueBodyNode(RescueBodyNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRescueNode(RescueNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRestArgNode(RestArgNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRetryNode(RetryNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitReturnNode(ReturnNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitRootNode(RootNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSClassNode(SClassNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSelfNode(SelfNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSplatNode(SplatNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitStrNode(StrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSuperNode(SuperNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSValueNode(SValueNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitSymbolNode(SymbolNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitToAryNode(ToAryNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitTrueNode(TrueNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitUndefNode(UndefNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitUntilNode(UntilNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitVAliasNode(VAliasNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitVCallNode(VCallNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitWhenNode(WhenNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitWhileNode(WhileNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitXStrNode(XStrNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitYieldNode(YieldNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitZArrayNode(ZArrayNode iVisited) {
		// DOES NOTHING
		return null;
	}

	@Override
	public Object visitZSuperNode(ZSuperNode iVisited) {
		// DOES NOTHING
		return null;
	}
}
