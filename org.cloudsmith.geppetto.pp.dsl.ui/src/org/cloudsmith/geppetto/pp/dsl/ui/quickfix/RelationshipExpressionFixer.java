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
package org.cloudsmith.geppetto.pp.dsl.ui.quickfix;

import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.RelationshipExpression;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

/**
 * A Fixer of Relationship Expressions
 * 
 */
public class RelationshipExpressionFixer {

	// L and R can be
	// RelationshipExpression
	// AtExpression
	// CollectExpression
	// All others are illegal, and no refactoring should be made

	@Fix(IPPDiagnostics.ISSUE_RIGHT_TO_LEFT_RELATIONSHIP)
	public void fixRightToLeftRelationsip(final Issue issue, final IssueResolutionAcceptor acceptor) {

		if(issue.getLength() > 2)
			return; // can't fix it, not reported on the operator

		suggestReversedOperator(issue, acceptor);

		// real solution - rewrite the expression

		// TODO: Implement the logic:
		// R = root of N
		// Sn = N.left
		// N.left = Ref(right(N.left))
		// Sn = N.left
		// flip(R)
		//
		// where flip(R) is a reqursive flip (d <- e <- f, becomes f -> e -> d) by
		// tranforming tree (<- (<- d e) f) to (-> (-> f e) d)

		// acceptor.accept(issue, "Rewrite the expression", "Rewrite", null, new ISemanticModification() {
		//
		// @Override
		// public void apply(EObject element, IModificationContext context) throws Exception {
		// RelationshipExpression original = (RelationshipExpression) element;
		// RelationshipExpression relExpr = PPFactory.eINSTANCE.createRelationshipExpression();
		// relExpr.setLeftExpr(EcoreUtil.copy(original.getRightExpr()));
		// relExpr.setRightExpr(EcoreUtil.copy(original.getLeftExpr()));
		// relExpr.setOpName(reverseRelationshipOp(issue.getData()[0]));
		// EStructuralFeature feature = original.eContainingFeature();
		// Object x = original.eContainer().eGet(feature);
		// if(x instanceof List) {
		// List<Object> list = ((List<Object>) x);
		// for(int i = 0; i < list.size(); i++) {
		// if(list.get(i) == original)
		//
		// list.set(i, relExpr);
		// }
		// }
		// }
		// });
	}

	private boolean isValidRelationshipExpression(EObject o) {
		if(o instanceof RelationshipExpression || o instanceof AtExpression || o instanceof CollectExpression)
			return true;
		// Resource is trickier - must check form

		return false;
	}

	private String reverseRelationshipOp(String op) {
		if(op.charAt(0) == '<') {
			if(op.charAt(1) == '-')
				return "->";
			return "~>";
		}
		if(op.charAt(0) == '-')
			return "<-";
		return "<~";
	}

	private void suggestReversedOperator(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// dirty solution; reverse the op
		acceptor.accept(
			issue, "Reverse the operation",
			"Change the dependency to go the other direction.\nThis alters the meaning.", null,
			new ReplacingModification(issue.getOffset(), issue.getLength(), reverseRelationshipOp(issue.getData()[0])));
	}

}
