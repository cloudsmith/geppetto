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
import java.util.Iterator;

import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.BinaryExpression;
import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.DefinitionArgumentList;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.ExportedCollectQuery;
import org.cloudsmith.geppetto.pp.ExprList;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.HashEntry;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IQuotedString;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralClass;
import org.cloudsmith.geppetto.pp.LiteralDefault;
import org.cloudsmith.geppetto.pp.LiteralHash;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.LiteralUndef;
import org.cloudsmith.geppetto.pp.NodeDefinition;
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

	@Inject
	private IGrammarAccess grammarAccess;

	private PolymorphicDispatcher<Void> formatDispatcher = new PolymorphicDispatcher<Void>(
		"_format", 2, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Void> get()) {
		@Override
		protected Void handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	protected void _format(AndExpression o, IFormStream stream) {
		internalFormatBinaryExpression(o, "and", stream);
	}

	protected void _format(AppendExpression o, IFormStream stream) {
		internalFormatBinaryExpression(o, "+=", stream);
	}

	protected void _format(AssignmentExpression o, IFormStream stream) {
		internalFormatBinaryExpression(o, "=", stream);
	}

	protected void _format(AtExpression o, IFormStream stream) {
		// TODO: wrap the list if wider than max width
		doFormat(o.getLeftExpr(), stream);
		stream.noSpace();
		stream.text("[");
		stream.noSpace();
		Iterator<Expression> itor = o.getParameters().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.noSpace();
				stream.text(",");
				stream.oneSpace();
			}
		}
		stream.noSpace();
		stream.text("]");
	}

	protected void _format(BinaryOpExpression o, IFormStream stream) {
		internalFormatBinaryExpression(o, o.getOpName(), stream);
	}

	protected void _format(Case o, IFormStream stream) {

	}

	protected void _format(CaseExpression o, IFormStream stream) {
		stream.text("case");
		stream.oneSpace();
		doFormat(o.getSwitchExpr(), stream);
		stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();

		// process cases
		int width = 0;
		for(Case c : o.getCases()) {
			IFormStream inner = new FormStream();
			Iterator<Expression> itor = c.getValues().iterator();
			while(itor.hasNext()) {
				doFormat(itor.next(), inner);
				if(itor.hasNext()) {
					inner.text(",");
					inner.oneSpace();
				}
			}
			width = Math.max(width, inner.size());
		}
		for(Case c : o.getCases()) {
			int before = stream.size();
			Iterator<Expression> itor = c.getValues().iterator();
			while(itor.hasNext()) {
				doFormat(itor.next(), stream);
				if(itor.hasNext()) {
					stream.text(",");
					stream.oneSpace();
				}
			}
			int after = stream.size();
			stream.space(width - (before - after));
			stream.oneSpace();
			stream.text(":");
			stream.oneSpace();
			stream.text("{");
			stream.indent();
			formatStatementList(c.getStatements(), stream);
			stream.dedent();
			stream.text("}");
			stream.breakLine();
		}

		stream.dedent();
		stream.breakLine();
		stream.text("}");

	}

	protected void _format(CollectExpression o, IFormStream stream) {
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

	protected void _format(Definition o, IFormStream stream) {
		stream.text("define");
		stream.oneSpace();
		stream.text(o.getClassName());
		stream.oneSpace();
		internalFormatArguments(o.getArguments(), stream);
		if(o.getArguments() != null)
			stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();

		formatStatementList(o.getStatements(), stream);
		stream.dedent();
		stream.breakLine();
		stream.text("}");
	}

	protected void _format(DoubleQuotedString o, IFormStream stream) {
		stream.text("\"");
		for(TextExpression te : o.getStringPart()) {
			doFormat(te, stream);
		}
		stream.text("\"");
	}

	protected void _format(ElseExpression o, IFormStream stream) {
		stream.text("else");
		stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();
		formatStatementList(o.getStatements(), stream);
		stream.dedent();
		stream.text("}");
	}

	protected void _format(ElseIfExpression o, IFormStream stream) {
		stream.text("elsif");
		stream.oneSpace();
		doFormat(o.getCondExpr(), stream);
		stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();
		formatStatementList(o.getThenStatements(), stream);
		stream.dedent();
		stream.text("}");
		if(o.getElseStatement() != null) {
			stream.oneSpace();
			doFormat(o.getElseStatement(), stream);
		}
	}

	protected void _format(ExportedCollectQuery o, IFormStream stream) {
		stream.text("<<|");
		stream.oneSpace();
		doFormat(o.getExpr(), stream);
		stream.oneSpace();
		stream.text("|>>");
	}

	protected void _format(ExpressionTE o, IFormStream stream) {
		stream.text("${");
		stream.noSpace();
		doFormat(((ParenthesisedExpression) o.getExpression()).getExpr(), stream);
		stream.noSpace();
		stream.text("}");
	}

	protected void _format(ExprList o, IFormStream stream) {
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

	protected void _format(HashEntry o, IFormStream stream) {
		doFormat(o.getKey(), stream);
		stream.oneSpace();
		stream.text("=>");
		stream.oneSpace();
		doFormat(o.getValue(), stream);
	}

	protected void _format(HostClassDefinition o, IFormStream stream) {
		stream.text("class");
		stream.oneSpace();
		stream.text(o.getClassName());
		stream.oneSpace();
		internalFormatArguments(o.getArguments(), stream);
		if(o.getArguments() != null)
			stream.oneSpace();
		if(o.getParent() != null) {
			stream.text("inherits");
			stream.oneSpace();
			doFormat(o.getParent(), stream);
			stream.oneSpace();
		}
		stream.text("{");
		if(o.getStatements().size() > 0) {
			stream.indent();
			stream.breakLine();

			formatStatementList(o.getStatements(), stream);
			stream.dedent();
		}
		stream.breakLine();
		stream.text("}");
	}

	protected void _format(IfExpression o, IFormStream stream) {
		stream.text("if");
		stream.oneSpace();
		doFormat(o.getCondExpr(), stream);
		stream.oneSpace();
		stream.text("{");
		stream.indent();
		stream.breakLine();
		formatStatementList(o.getThenStatements(), stream);
		stream.dedent();
		stream.text("}");
		if(o.getElseStatement() != null) {
			stream.oneSpace();
			doFormat(o.getElseStatement(), stream);
		}

	}

	protected void _format(ImportExpression o, IFormStream stream) {
		stream.text("import");
		stream.oneSpace();
		Iterator<IQuotedString> itor = o.getValues().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.noSpace();
				stream.text(",");
				stream.oneSpace();
			}
		}
	}

	protected void _format(LiteralBoolean o, IFormStream stream) {
		stream.text(String.valueOf(o.isValue()));
	}

	protected void _format(LiteralClass o, IFormStream stream) {
		stream.text("class");
	}

	protected void _format(LiteralDefault o, IFormStream stream) {
		stream.text("default");
	}

	protected void _format(LiteralHash o, IFormStream stream) {
		stream.text("[");
		stream.noSpace();
		internalCommaSeparatedList(o.getElements(), stream);
		stream.noSpace();
		stream.text("]");
	}

	protected void _format(LiteralList o, IFormStream stream) {
		stream.text("[");
		stream.noSpace();
		internalCommaSeparatedList(o.getElements(), stream);
		stream.noSpace();
		stream.text("]");
	}

	protected void _format(LiteralName o, IFormStream stream) {
		stream.text(o.getValue());
	}

	protected void _format(LiteralNameOrReference o, IFormStream stream) {
		stream.text(o.getValue());
	}

	protected void _format(LiteralRegex o, IFormStream stream) {
		stream.text(o.getValue());
	}

	protected void _format(LiteralUndef o, IFormStream stream) {
		stream.text("undef");
	}

	protected void _format(NodeDefinition o, IFormStream stream) {
		stream.text("node");
		stream.oneSpace();
		internalCommaSeparatedList(o.getHostNames(), stream);
		stream.oneSpace();
		if(o.getParentName() != null) {
			doFormat(o.getParentName(), stream);
			stream.oneSpace();
		}
		stream.text("{");
		stream.indent();
		stream.breakLine();
		formatStatementList(o.getStatements(), stream);
		stream.dedent();
		stream.text("}");
	}

	protected void _format(OrExpression o, IFormStream stream) {
		internalFormatBinaryExpression(o, "or", stream);
	}

	protected void _format(ParenthesisedExpression o, IFormStream stream) {
		stream.text("(");
		stream.noSpace();
		doFormat(o.getExpr(), stream);
		stream.noSpace();
		stream.text(")");
	}

	protected void _format(PuppetManifest o, IFormStream stream) {
		formatStatementList(o.getStatements(), stream);
	}

	protected void _format(ResourceBody o, IFormStream stream) {
		throw new UnsupportedOperationException("Should not be called - use internalFormat");
	}

	protected void _format(ResourceExpression o, IFormStream stream) {
		doFormat(o.getResourceExpr(), stream);
		stream.oneSpace();
		stream.text("{");
		int bodyCount = o.getResourceData().size();
		switch(bodyCount) {
			case 0:
				stream.breakLine();
				break;
			case 1:
				ResourceBody body = o.getResourceData().get(0);
				// no space after brace if first body has no title
				if(body.getNameExpr() != null)
					stream.oneSpace();
				internalFormatResourceBody(body, true, stream);
				stream.breakLine();
				break;
			default:
				stream.indent();
				stream.breakLine();
				Iterator<ResourceBody> itor = o.getResourceData().iterator();
				while(itor.hasNext()) {
					internalFormatResourceBody(itor.next(), false, stream);
					stream.text(";");
					stream.breakLine();
					// add extra blank line between resoures, but not between last and closing brace
					if(itor.hasNext())
						stream.breakLine();
				}
				stream.dedent();
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
	protected void _format(SelectorEntry o, IFormStream stream) {
		internalFormatBinaryExpression(o, "=>", stream);
	}

	protected void _format(SelectorExpression o, IFormStream stream) {
		doFormat(o.getLeftExpr(), stream);
		stream.oneSpace();
		stream.text("?");
		stream.oneSpace();

		// always surround with {} even if they are optional when there is only one entry
		stream.text("{");
		stream.indent();
		stream.breakLine();

		// calculate column width
		// Simply skip entries that can be very wide/unknown - format the rest
		// TODO: Set "folding" in the stream to allow nested List/hash to fold
		//
		int width = 0;
		for(Expression e : o.getParameters()) {
			IFormStream inner = new FormStream();
			// if e is not a SelectorEntry, it is really a syntax error, but do something reasonable
			doFormat(e instanceof SelectorEntry
					? ((SelectorEntry) e).getLeftExpr()
					: e, inner);
			width = Math.max(width, inner.size());
		}
		for(Expression e : o.getParameters()) {
			if(e instanceof SelectorEntry == false) {
				doFormat(e, stream);
				stream.breakLine();
			}
			else {
				SelectorEntry se = (SelectorEntry) e;
				int before = stream.size();
				doFormat(se.getLeftExpr(), stream);
				int after = stream.size();
				// pad to width
				stream.space(width - (after - before));
				stream.oneSpace();
				stream.text("=>");
				stream.oneSpace();
				doFormat(se.getRightExpr(), stream);
				stream.noSpace();
				stream.text(",");
				stream.breakLine();
			}
		}
		stream.dedent();
		stream.text("}");
	}

	protected void _format(SingleQuotedString o, IFormStream stream) {
		stream.text("'");
		stream.text(o.getText());
		stream.text("'");
	}

	protected void _format(UnaryMinusExpression o, IFormStream stream) {
		stream.text("-");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnaryNotExpression o, IFormStream stream) {
		stream.text("!");
		doFormat(o.getExpr(), stream);
	}

	protected void _format(UnquotedString o, IFormStream stream) {
		stream.text("${");
		stream.noSpace();
		doFormat(o.getExpression(), stream);
		stream.noSpace();
		stream.text("}");
	}

	protected void _format(VariableExpression o, IFormStream stream) {
		stream.text(o.getVarName());
	}

	protected void _format(VariableTE o, IFormStream stream) {
		stream.text(o.getVarName());
	}

	protected void _format(VerbatimTE o, IFormStream stream) {
		stream.text(o.getText());
	}

	protected void _format(VirtualCollectQuery o, IFormStream stream) {
		stream.text("<|");
		stream.oneSpace();
		doFormat(o.getExpr(), stream);
		stream.oneSpace();
		stream.text("|>");
	}

	protected void _format(VirtualNameOrReference o, IFormStream stream) {
		stream.text("@");
		if(o.isExported()) {
			stream.noSpace();
			stream.text("@");
		}
		stream.text(o.getValue());
	}

	public void doFormat(EObject o, IFormStream stream) {
		formatDispatcher.invoke(o, stream);
	}

	protected void formatStatementList(EList<Expression> statements, IFormStream stream) {
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

	/**
	 * @param hostNames
	 * @param stream
	 */
	private void internalCommaSeparatedList(Iterable<? extends EObject> elements, IFormStream stream) {
		Iterator<? extends EObject> itor = elements.iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.noSpace();
				stream.text(",");
				stream.oneSpace();
			}
		}
	}

	protected void internalFormat(AttributeOperations o, boolean commaLast, IFormStream stream) {
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

	/**
	 * @param arguments
	 * @param stream
	 */
	private void internalFormatArguments(DefinitionArgumentList arguments, IFormStream stream) {
		if(arguments == null)
			return;
		stream.text("(");
		stream.noSpace();
		Iterator<DefinitionArgument> itor = arguments.getArguments().iterator();
		while(itor.hasNext()) {
			doFormat(itor.next(), stream);
			if(itor.hasNext()) {
				stream.noSpace();
				stream.text(",");
				stream.oneSpace();
			}
		}
		stream.noSpace();
		stream.text(")");
	}

	protected void internalFormatBinaryExpression(BinaryExpression o, String op, IFormStream stream) {
		doFormat(o.getLeftExpr(), stream);
		stream.oneSpace();
		stream.text(op);
		stream.oneSpace();
		doFormat(o.getRightExpr(), stream);
	}

	protected void internalFormatResourceBody(ResourceBody o, boolean commaLast, IFormStream stream) {
		if(o.getNameExpr() != null) {
			doFormat(o.getNameExpr(), stream);
			stream.text(":");
		}
		if(o.getAttributes() != null && o.getAttributes().getAttributes().size() > 0) {
			stream.indent();
			stream.breakLine();
			internalFormat(o.getAttributes(), commaLast, stream);
			stream.dedent();
		}
	}
}
