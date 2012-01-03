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
package org.cloudsmith.geppetto.pp.dsl.eval;

import java.util.Collections;
import java.util.Iterator;

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralDefault;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.LiteralUndef;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * @author henrik
 * 
 */
public class PPExpressionEquivalenceCalculator {

	private PolymorphicDispatcher<Boolean> eqDispatcher = new PolymorphicDispatcher<Boolean>(
		"_eq", 2, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	private Class<?> eqOrder[] = {
			/* A very important order */
			FunctionCall.class, DoubleQuotedString.class, UnquotedString.class, SingleQuotedString.class,
			ExpressionTE.class, VariableTE.class, VerbatimTE.class, LiteralName.class, LiteralNameOrReference.class,
			LiteralUndef.class, LiteralDefault.class, LiteralBoolean.class, LiteralRegex.class,
			VariableExpression.class, String.class };

	protected Boolean _eq(DoubleQuotedString e1, DoubleQuotedString e2) {
		if(e1.getStringPart().size() != e2.getStringPart().size())
			return Boolean.FALSE;
		Iterator<TextExpression> itor1 = e1.getStringPart().iterator();
		Iterator<TextExpression> itor2 = e2.getStringPart().iterator();

		Boolean result = null;
		while(itor1.hasNext()) {
			result = isEquivalent(itor1.next(), itor2.next());
			if(result == null)
				break;
			if(!result)
				return result; // give up (false) if two segments are unequal

		}
		return result; // do source compare if result is null, else it is true here
	}

	protected Boolean _eq(DoubleQuotedString e1, ExpressionTE e2) {
		return isEquivalent(e1, e2.getExpression());
	}

	protected Boolean _eq(DoubleQuotedString e1, LiteralName e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(DoubleQuotedString e1, LiteralNameOrReference e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(DoubleQuotedString e1, SingleQuotedString e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(DoubleQuotedString e1, String e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(DoubleQuotedString e1, UnquotedString e2) {
		return isEquivalent(e1, e2.getExpression());

	}

	protected Boolean _eq(DoubleQuotedString e1, VariableExpression e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(DoubleQuotedString e1, VerbatimTE e2) {
		if(e1.getStringPart().size() != 1)
			return Boolean.FALSE;
		return isEquivalent(e1.getStringPart().get(0), e2);
	}

	protected Boolean _eq(ExpressionTE e1, ExpressionTE e2) {
		return isEquivalent(e1.getExpression(), e2.getExpression());
	}

	protected Boolean _eq(ExpressionTE e1, String e2) {
		Expression tmp = e1.getExpression();
		tmp = ((ParenthesisedExpression) tmp).getExpr();
		return isEquivalent(tmp, e2);
		// // The various expr that can represent a variable
		// if(tmp instanceof LiteralName)
		// return isEquivalent(tmp, e2);
		// if(tmp instanceof LiteralNameOrReference)
		// return isEquivalent(tmp, e2);
		// if(tmp instanceof VariableExpression)
		// return isEquivalent(tmp, e2);
		//
		// return Boolean.FALSE;
	}

	protected Boolean _eq(ExpressionTE e1, VariableExpression e2) {
		Expression tmp = e1.getExpression();
		tmp = ((ParenthesisedExpression) tmp).getExpr();
		// The various expr that can represent a variable
		if(tmp instanceof LiteralName)
			return isEquivalent(((LiteralName) tmp).getValue(), e2);
		if(tmp instanceof LiteralNameOrReference)
			return isEquivalent(((LiteralNameOrReference) tmp).getValue(), e2);
		if(tmp instanceof VariableExpression)
			return isEquivalent(tmp, e2);

		return Boolean.FALSE;

	}

	protected Boolean _eq(ExpressionTE e1, VariableTE e2) {
		Expression tmp = e1.getExpression();
		tmp = ((ParenthesisedExpression) tmp).getExpr();
		// The various expr that can represent a variable
		if(tmp instanceof LiteralName)
			return isEquivalent(((LiteralName) tmp).getValue(), e2);
		if(tmp instanceof LiteralNameOrReference)
			return isEquivalent(((LiteralNameOrReference) tmp).getValue(), e2);
		if(tmp instanceof VariableExpression)
			return isEquivalent(tmp, e2);

		return Boolean.FALSE;
	}

	protected Boolean _eq(FunctionCall e1, FunctionCall e2) {
		// debatable, Function calls without parameters are almost certain to return the same value
		// but all other functions may return different result even if given exactly the same input, as it is completely
		// unknown if they use time or random numbers, perform some kind of UUID calculation etc.
		// However, since the purpose of this logic is to find user copy/paste mistakes, this logic treats functions with
		// the same text as if they produce the same value.
		// Puppet is supposedly a functional language so it should be relatively safe to assume that two function expressions
		// with the same text produce the same result.

		// if names are not equivalent
		if(!isEquivalent(e1.getLeftExpr(), e2.getLeftExpr()))
			return Boolean.FALSE;
		// if different number of arguments
		if(e1.getParameters().size() != e2.getParameters().size())
			return Boolean.FALSE;

		// if parameter expressions are not equivalent
		EList<Expression> p1 = e1.getParameters();
		EList<Expression> p2 = e2.getParameters();
		for(int i = 0; i < p1.size(); i++)
			if(!isEquivalent(p1.get(i), p2.get(i)))
				return Boolean.FALSE;
		return Boolean.TRUE;
	}

	protected Boolean _eq(LiteralBoolean e1, LiteralBoolean e2) {
		return e1.isValue() == e2.isValue();
	}

	protected Boolean _eq(LiteralDefault e1, LiteralDefault e2) {
		return Boolean.TRUE;
	}

	protected Boolean _eq(LiteralName e1, LiteralName e2) {
		return e1.getValue().equals(e2.getValue());
	}

	protected Boolean _eq(LiteralName e1, LiteralNameOrReference e2) {
		return e1.getValue().equals(e2.getValue());
	}

	protected Boolean _eq(LiteralName e1, String e2) {
		return e1.getValue().equals(e2);
	}

	protected Boolean _eq(LiteralNameOrReference e1, LiteralNameOrReference e2) {
		return e1.getValue().equals(e2.getValue());
	}

	protected Boolean _eq(LiteralNameOrReference e1, String e2) {
		return e1.getValue().equals(e2);
	}

	protected Boolean _eq(LiteralRegex e1, LiteralRegex e2) {
		return e1.getValue().equals(e2.getValue());
	}

	protected Boolean _eq(LiteralUndef e1, LiteralUndef e2) {
		return Boolean.TRUE;
	}

	protected Boolean _eq(SingleQuotedString e1, ExpressionTE e2) {
		return isEquivalent(e1.getText(), e2);
	}

	protected Boolean _eq(SingleQuotedString e1, LiteralName e2) {
		return isEquivalent(e1.getText(), e2);
	}

	protected Boolean _eq(SingleQuotedString e1, LiteralNameOrReference e2) {
		return isEquivalent(e1.getText(), e2);
	}

	protected Boolean _eq(SingleQuotedString e1, SingleQuotedString e2) {
		return isEquivalent(e1.getText(), e2);
	}

	protected Boolean _eq(SingleQuotedString e1, String e2) {
		return e1.getText().equals(e2);
	}

	protected Boolean _eq(SingleQuotedString e1, VerbatimTE e2) {
		return isEquivalent(e1.getText(), e2);
	}

	protected Boolean _eq(UnquotedString e1, Expression e2) {
		Expression tmp = e1.getExpression();
		boolean e2IsVar = e2 instanceof VariableExpression;
		if(tmp instanceof LiteralName) {
			if(e2IsVar)
				return isEquivalent(((LiteralName) tmp).getValue(), e2);
			return Boolean.FALSE;
		}
		else if(tmp instanceof LiteralNameOrReference) {
			if(e2IsVar)
				return isEquivalent(((LiteralNameOrReference) tmp).getValue(), e2);
			return Boolean.FALSE;
		}
		// all other expression wrapped in e1 represent themselves
		return isEquivalent(e1.getExpression(), e2);
	}

	protected Boolean _eq(UnquotedString e1, ExpressionTE e2) {
		return isEquivalent(e1.getExpression(), e2.getExpression());
	}

	protected Boolean _eq(UnquotedString e1, UnquotedString e2) {
		return isEquivalent(e1.getExpression(), e2.getExpression());
	}

	protected Boolean _eq(UnquotedString e1, VariableExpression e2) {
		Expression tmp = e1.getExpression();
		if(tmp instanceof LiteralName)
			return isEquivalent(((LiteralName) tmp).getValue(), e2);
		if(tmp instanceof LiteralNameOrReference)
			return isEquivalent(((LiteralNameOrReference) tmp).getValue(), e2);
		if(tmp instanceof VariableExpression)
			return isEquivalent(tmp, e2);
		return Boolean.FALSE;

	}

	protected Boolean _eq(UnquotedString e1, VariableTE e2) {
		return isEquivalent(e1.getExpression(), e2.getVarName());
	}

	protected Boolean _eq(UnquotedString e1, VerbatimTE e2) {
		return isEquivalent(e1.getExpression(), e2.getText());
	}

	protected Boolean _eq(VariableExpression e1, String e2) {
		String s = e1.getVarName();
		if(s.startsWith("$"))
			s = s.substring(1);
		return s.equals(e2);
	}

	protected Boolean _eq(VariableExpression e1, VariableExpression e2) {
		return e1.getVarName().equals(e2.getVarName());
	}

	protected Boolean _eq(VariableTE e1, LiteralName e2) {
		String s = e1.getVarName();
		if(s.startsWith("$"))
			s = s.substring(1);
		return isEquivalent(s, e2);
	}

	protected Boolean _eq(VariableTE e1, LiteralNameOrReference e2) {
		String s = e1.getVarName();
		if(s.startsWith("$"))
			s = s.substring(1);
		return isEquivalent(s, e2);
	}

	protected Boolean _eq(VariableTE e1, String e2) {
		String s = e1.getVarName();
		if(s.startsWith("$"))
			s = s.substring(1);
		return s.equals(e2);
	}

	protected Boolean _eq(VariableTE e1, VariableTE e2) {
		return e1.getVarName().equals(e2.getVarName());
	}

	protected Boolean _eq(VerbatimTE e1, String e2) {
		return e1.getText().equals(e2);
	}

	protected Boolean _eq(VerbatimTE e1, VerbatimTE e2) {
		return e1.getText().equals(e2.getText());
	}

	private Boolean doEq(Object e1, Object e2) {

		return eqDispatcher.invoke(e1, e2);
	}

	private int eqPriority(Object e1) {
		Class<?> candidateClass = e1.getClass();
		for(int i = 0; i < eqOrder.length; i++) {
			if(eqOrder[i].isAssignableFrom(candidateClass))
				return i;
		}
		return -1;
	}

	public Boolean isEquivalent(Object e1, Object e2) {
		if(e1 == e2)
			return Boolean.TRUE;
		if(e1 == null || e2 == null)
			return Boolean.FALSE;
		if(e1.equals(e2))
			return Boolean.TRUE;

		Boolean isEq = eqPriority(e1) > eqPriority(e2)
				? doEq(e2, e1)
				: doEq(e1, e2);
		if(isEq == null && e1 instanceof EObject && e2 instanceof EObject) {
			// no eq possible, compare source text if available
			INode n1 = NodeModelUtils.getNode((EObject) e1);
			INode n2 = NodeModelUtils.getNode((EObject) e2);
			if(n1 == null || n2 == null)
				return Boolean.FALSE;

			// compare source text, but skip hidden nodes
			isEq = NodeModelUtils.getTokenText(n1).equals(NodeModelUtils.getTokenText(n2));
		}
		return isEq;
	}
}
