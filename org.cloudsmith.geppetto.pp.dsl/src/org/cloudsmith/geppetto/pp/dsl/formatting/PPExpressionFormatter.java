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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import java.util.Collections;

import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.BinaryExpression;
import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.ExportedCollectQuery;
import org.cloudsmith.geppetto.pp.ExprList;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralClass;
import org.cloudsmith.geppetto.pp.LiteralDefault;
import org.cloudsmith.geppetto.pp.LiteralHash;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.LiteralUndef;
import org.cloudsmith.geppetto.pp.OrExpression;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorEntry;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.UnaryMinusExpression;
import org.cloudsmith.geppetto.pp.UnaryNotExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.VirtualCollectQuery;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public class PPExpressionFormatter {
	private class DerivedSemanticToken extends FormattingToken {
		int id;

		public DerivedSemanticToken(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	private class FormattingToken {

	}

	private static class FormStream {
		int initialIndent;

		public FormStream() {
			this.initialIndent = 0;
		}

		public void addToken(FormattingToken t) {

		}

		public void breakLine() {

		}

		public void dedent() {

		}

		public void flush() {

		}

		public void indent() {

		}

		public void noSpace() {

		}

		/**
		 * one space, SL comment, or space MLComment space ( 1 MLCMNT + 2 )
		 */
		public void oneSpace() {
			space(1);
		}

		public void pseudoToken(int token, EObject o, String s) {

		}

		/**
		 * count space, or SL comment, or max(ML.length, count)
		 * 
		 * @param count
		 */
		public void space(int count) {

		}

		public void text(String s) {

		}
	}

	private class UnparenthesizedFunctionCall extends DerivedSemanticToken {
		private LiteralNameOrReference func;

		private Expression arg;

		public UnparenthesizedFunctionCall(LiteralNameOrReference func, Expression arg) {
			super(UNPARENTHESIZED_CALL);
			this.func = func;
			this.arg = arg;
		}
	}

	private static final int UNPARENTHESIZED_CALL = 1;

	private static final int UNPARENTHESIZED_ARG = 2;

	@Inject
	private IGrammarAccess grammarAccess;

	private PolymorphicDispatcher<Void> formatDispatcher = new PolymorphicDispatcher<Void>(
		"_format", 2, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Void> get()) {
		@Override
		protected Void handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	protected void _format(AndExpression o, FormStream stream) {
		internalFormat(o, "and", stream);
	}

	protected void _format(AppendExpression o, FormStream stream) {
		internalFormat(o, "+=", stream);
	}

	protected void _format(AssignmentExpression o, FormStream stream) {
		internalFormat(o, "=", stream);
	}

	protected void _format(BinaryOpExpression o, FormStream stream) {
		internalFormat(o, o.getOpName(), stream);
	}

	protected void _format(CollectExpression o, FormStream stream) {
		doFormat(o.getClassReference(), stream);
		stream.oneSpace();
		doFormat(o.getQuery(), stream);
		stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();
		internalFormat(o.getAttributes(), true, stream);
		stream.dedent();
		stream.breakLine();
		stream.text("}");
	}

	protected void _format(DoubleQuotedString o, FormStream stream) {
		stream.text("\"");
		for(TextExpression te : o.getStringPart()) {
			doFormat(te, stream);
		}
		stream.text("\"");
	}

	protected void _format(ExportedCollectQuery o, FormStream stream) {
		stream.text("<<|");
		stream.oneSpace();
		doFormat(o.getExpr());
		stream.oneSpace();
		stream.text("|>>");
	}

	protected void _format(ExpressionTE o, FormStream stream) {
		stream.text("${");
		stream.noSpace();
		doFormat(((ParenthesisedExpression) o.getExpression()).getExpr());
		stream.noSpace();
		stream.text("}");
	}

	protected void _format(ExprList o, FormStream stream) {
		int size = o.getExpressions().size();
		for(int i = 0; i < size; i++) {
			Expression e = o.getExpressions().get(i);
			doFormat(e, stream);
			if(i + 1 < size) {
				stream.noSpace();
				stream.text(",");
				stream.oneSpace();
			}
		}
	}

	protected void _format(ImportExpression o, FormStream stream) {
		// TODO
		// import quotedString list
	}

	protected void _format(LiteralBoolean o, FormStream stream) {
		stream.text(String.valueOf(o.isValue()));
	}

	protected void _format(LiteralClass o, FormStream stream) {
		stream.text("class");
	}

	protected void _format(LiteralDefault o, FormStream stream) {
		stream.text("default");
	}

	protected void _format(LiteralHash o, FormStream stream) {
		// TODO
		// [list of HashEntry] with optional endcomma
	}

	protected void _format(LiteralList o, FormStream stream) {
		// TODO
		// [list of expression] with optional endcomma
	}

	protected void _format(LiteralNameOrReference o, FormStream stream) {
		stream.text(o.getValue());
	}

	protected void _format(LiteralRegex o, FormStream stream) {
		stream.text(o.getValue());
	}

	protected void _format(LiteralUndef o, FormStream stream) {
		stream.text("undef");
	}

	protected void _format(OrExpression o, FormStream stream) {
		internalFormat(o, "or", stream);
	}

	protected void _format(ParenthesisedExpression o, FormStream stream) {
		stream.text("(");
		stream.noSpace();
		doFormat(o.getExpr(), stream);
		stream.noSpace();
		stream.text(")");
	}

	protected void _format(PuppetManifest o, FormStream stream) {
		formatStatementList(o.getStatements(), stream);
	}

	protected void _format(ResourceBody o, FormStream stream) {
		throw new UnsupportedOperationException("Should not be called - use internalFormat");
	}

	protected void _format(ResourceExpression o, FormStream stream) {
		doFormat(o.getResourceExpr(), stream);
		stream.oneSpace();
		stream.text("{");
		int bodyCount = o.getResourceData().size();
		switch(bodyCount) {
			case 0:
				stream.breakLine();
				break;
			case 1:
				stream.oneSpace();
				internalFormat(o.getResourceData().get(0), true, stream);
				stream.breakLine();
				break;
			default:
				stream.indent();
				stream.breakLine();
				for(int i = 0; i < bodyCount; i++) {
					internalFormat(o.getResourceData().get(i), false, stream);
					stream.text(";");
					if(i + 1 >= bodyCount)
						stream.dedent();
					stream.breakLine();
				}
				break;
		}
		stream.text("}");
	}

	/**
	 * TODO: Column collections
	 * 
	 * @param o
	 * @param stream
	 */
	protected void _format(SelectorEntry o, FormStream stream) {
		internalFormat(o, "=>", stream);
	}

	protected void _format(SelectorExpression o, FormStream stream) {
		doFormat(o.getLeftExpr());
		stream.oneSpace();
		stream.text("?");
		stream.oneSpace();

		// always surround with {} even if they are optional when there is only one entry
		int selectorCount = o.getParameters().size();
		stream.text("{");
		stream.indent();
		stream.breakLine();

		// calculate column width
		// TODO: COMPLEX - CAN CONTAIN HASH/AT,
		for(Expression e : o.getParameters()) {
			if(e instanceof SelectorEntry == false) {
				// Ouch, this is a syntax error, what is the width ?
			}
			else {
				SelectorEntry se = (SelectorEntry) e;
				// Width of leftExpr (can be complex)
				se.getLeftExpr();

			}
		}
	}

	protected void _format(SingleQuotedString o, FormStream stream) {
		stream.text(o.getText());
	}

	protected void _format(UnaryMinusExpression o, FormStream stream) {
		stream.text("-");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnaryNotExpression o, FormStream stream) {
		stream.text("!");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnquotedString o, FormStream stream) {
		// TODO
	}

	protected void _format(VariableExpression o, FormStream stream) {
		stream.text(o.getVarName());
	}

	protected void _format(VariableTE o, FormStream stream) {
		stream.text(o.getVarName());
	}

	protected void _format(VerbatimTE o, FormStream stream) {
		stream.text(o.getText());
	}

	protected void _format(VirtualCollectQuery o, FormStream stream) {
		stream.text("<|");
		stream.oneSpace();
		doFormat(o.getExpr());
		stream.oneSpace();
		stream.text("|>");
	}

	protected void _format(VirtualNameOrReference o, FormStream stream) {
		// TODO: : '@' exported = ATBoolean? value = unionNameOrReference
		// ATBoolean is @ == true
	}

	public void doFormat(EObject o) {
		FormStream stream = new FormStream();
		formatDispatcher.invoke(o, stream);
		stream.flush();
	}

	protected void doFormat(EObject o, FormStream stream) {
		formatDispatcher.invoke(o, stream);
	}

	protected void formatStatementList(EList<Expression> statements, FormStream stream) {
		int size = statements.size();
		for(int i = 0; i < size; i++) {
			Expression s = statements.get(i);
			if(s instanceof LiteralNameOrReference) {
				_format((LiteralNameOrReference) s, stream);
				if(i + 1 < size) {
					stream.oneSpace();
					doFormat(statements.get(i + 1), stream);
					i++;
				}
			}
			else
				doFormat(s, stream);
			stream.breakLine();
		}
	}

	private PPGrammarAccess getGrammarAccess() {
		return (PPGrammarAccess) grammarAccess;
	}

	protected void internalFormat(AttributeOperations o, boolean commaLast, FormStream stream) {
		if(o == null)
			return;
		int maxWidth = 0;
		if(o != null) {
			final int size = o.getAttributes().size();
			for(AttributeOperation ao : o.getAttributes()) {
				String key = ao.getKey();
				if(key != null)
					maxWidth = Math.max(maxWidth, key.length());
			}
			int counter = 0;
			for(AttributeOperation ao : o.getAttributes()) {
				String key = ao.getKey();
				if(key == null)
					key = "";
				stream.text(key);
				stream.space(maxWidth - key.length() + 1);
				stream.text(ao.getOp());
				stream.oneSpace();
				doFormat(ao.getValue(), stream);
				if(commaLast || counter + 1 < size)
					stream.text(",");
				if(counter + 1 < size)
					stream.breakLine();
				counter++;
			}
		}

	}

	protected void internalFormat(BinaryExpression o, String op, FormStream stream) {
		doFormat(o.getLeftExpr(), stream);
		stream.oneSpace();
		stream.text(op);
		stream.oneSpace();
		doFormat(o.getRightExpr(), stream);
	}

	protected void internalFormat(ResourceBody o, boolean commaLast, FormStream stream) {
		if(o.getNameExpr() != null) {
			doFormat(o.getNameExpr());
			stream.noSpace();
			stream.text(":");
		}
		stream.indent();
		stream.breakLine();

		internalFormat(o.getAttributes(), commaLast, stream);
		stream.dedent();
	}
	// TODO
	// IfExpression
	// ElseExpression
	// ElseIfExpression
	// LiteralName
	// CaseExpression
	// Case
	// Definition
	// HostClass
	// Node
	// FunctionCall
	// AtExpression
}
