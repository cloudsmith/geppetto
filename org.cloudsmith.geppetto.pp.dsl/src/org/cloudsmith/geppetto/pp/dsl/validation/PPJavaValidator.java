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
package org.cloudsmith.geppetto.pp.dsl.validation;

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_BAD;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_DEFAULT;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_REGULAR;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeAddition;
import org.cloudsmith.geppetto.pp.AttributeDefinition;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.BinaryExpression;
import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.EqualityExpression;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IQuotedString;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.InExpression;
import org.cloudsmith.geppetto.pp.LiteralBoolean;
import org.cloudsmith.geppetto.pp.LiteralDefault;
import org.cloudsmith.geppetto.pp.LiteralHash;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.LiteralRegex;
import org.cloudsmith.geppetto.pp.LiteralUndef;
import org.cloudsmith.geppetto.pp.MatchingExpression;
import org.cloudsmith.geppetto.pp.MultiplicativeExpression;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.OrExpression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.RelationalExpression;
import org.cloudsmith.geppetto.pp.RelationshipExpression;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorEntry;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.ShiftExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.StringExpression;
import org.cloudsmith.geppetto.pp.UnaryExpression;
import org.cloudsmith.geppetto.pp.UnaryMinusExpression;
import org.cloudsmith.geppetto.pp.UnaryNotExpression;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.linking.ValidationBasedMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.util.TextExpressionHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.validation.Check;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class PPJavaValidator extends AbstractPPJavaValidator implements IPPDiagnostics {

	/**
	 * The CollectChecker is used to check the validity of puppet CollectExpression
	 */
	protected class CollectChecker {
		private final PolymorphicDispatcher<Void> collectCheckerDispatch = new PolymorphicDispatcher<Void>(
			"check", 1, 2, Collections.singletonList(this), new ErrorHandler<Void>() {
				public Void handle(Object[] params, Throwable e) {
					return handleError(params, e);
				}
			});

		public void check(AndExpression o) {
			doCheck(o.getLeftExpr());
			doCheck(o.getRightExpr());
		}

		public void check(EqualityExpression o) {
			doCheck(o.getLeftExpr(), Boolean.TRUE);
			doCheck(o.getRightExpr(), Boolean.FALSE);
		}

		public void check(EqualityExpression o, boolean left) {
			check(o);
		}

		public void check(Expression o) {
			acceptor.acceptError(
				"Expression type not allowed here.", o, o.eContainingFeature(), INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}

		public void check(Expression o, boolean left) {
			acceptor.acceptError(
				"Expression type not allowed as " + (left
						? "left"
						: "right") + " expression.", o, o.eContainingFeature(), INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}

		public void check(LiteralBoolean o, boolean left) {
			if(left)
				check(o);
		}

		public void check(LiteralName o, boolean left) {
			// intrinsic check of LiteralName is enough
		}

		public void check(LiteralNameOrReference o, boolean left) {
			if(isNAME(o.getValue()))
				return; // ok both left and right
			if(!left && isCLASSREF(o.getValue())) // accept "type" if right
				return;

			acceptor.acceptError("Must be a name" + (!left
					? " or type."
					: "."), o, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, INSIGNIFICANT_INDEX, left
					? IPPDiagnostics.ISSUE__NOT_NAME
					: IPPDiagnostics.ISSUE__NOT_NAME_OR_REF);
		}

		public void check(OrExpression o) {
			doCheck(o.getLeftExpr());
			doCheck(o.getRightExpr());
		}

		public void check(ParenthesisedExpression o) {
			doCheck(o.getExpr());
		}

		public void check(SelectorExpression o, boolean left) {
			if(left)
				check(o);
		}

		public void check(StringExpression o, boolean left) {
			// TODO: follow up if all types of StringExpression (single, double, and unquoted are supported).
			if(left)
				check(o);
		}

		public void check(VariableExpression o, boolean left) {
			// intrinsic variable check is enough
		}

		/**
		 * Calls "collectCheck" in polymorphic fashion.
		 * 
		 * @param o
		 */
		public void doCheck(Object... o) {
			collectCheckerDispatch.invoke(o);
		}

		public Void handleError(Object[] params, Throwable e) {
			return Exceptions.throwUncheckedException(e);
		}
	}

	final protected IMessageAcceptor acceptor;

	@Inject
	private PPPatternHelper patternHelper;

	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	private PPGrammarAccess puppetGrammarAccess;

	/**
	 * Classes accepted as top level statements in a pp manifest.
	 */
	private static final Class<?>[] topLevelExprClasses = { ResourceExpression.class, // resource, virtual, resource override
			RelationshipExpression.class, //
			CollectExpression.class, //
			AssignmentExpression.class, //
			IfExpression.class, //
			CaseExpression.class, //
			ImportExpression.class, //
			FunctionCall.class, //
			Definition.class, HostClassDefinition.class, //
			NodeDefinition.class, //
			AppendExpression.class //
	};

	/**
	 * Classes accepted as RVALUE.
	 */
	private static final Class<?>[] rvalueClasses = { StringExpression.class, // TODO: was LiteralString, follow up
			LiteralNameOrReference.class, // NAME and TYPE
			LiteralBoolean.class, //
			SelectorExpression.class, //
			VariableExpression.class, //
			LiteralList.class, //
			LiteralHash.class, //
			AtExpression.class, // HashArray access or ResourceReference are accepted
			// resource reference - see AtExpression
			FunctionCall.class, // i.e. only parenthesized form
			LiteralUndef.class, };

	private static final String[] keywords = {
			"and", "or", "case", "default", "define", "import", "if", "elsif", "else", "inherits", "node", "in",
			"undef", "true", "false" };

	@Inject
	protected PPExpressionTypeNameProvider expressionTypeNameProvider;

	@Inject
	public PPJavaValidator(IGrammarAccess ga) {
		acceptor = new ValidationBasedMessageAcceptor(this);
		puppetGrammarAccess = (PPGrammarAccess) ga;
	}

	@Check
	public void checkAdditiveExpression(ShiftExpression o) {
		checkOperator(o, "+", "-");
	}

	@Check
	public void checkAppendExpression(AppendExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression))
			acceptor.acceptError(
				"Not an appendable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_APPENDABLE);
	}

	@Check
	public void checkAssignmentExpression(AssignmentExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression || leftExpr instanceof AtExpression))
			acceptor.acceptError(
				"Not an assignable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_ASSIGNABLE);
		// TODO: rhs is not validated, it allows expression, which includes rvalue, but some top level expressions
		// are probably not allowed (case?)

		// Variables in 'other namespaces' are not assignable.
		if(leftExpr instanceof VariableExpression) {
			VariableExpression varExpr = (VariableExpression) leftExpr;
			if(varExpr.getVarName().contains("::"))
				acceptor.acceptError("Cannot assign to variables in other namespaces", o, //
					PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX, //
					IPPDiagnostics.ISSUE__ASSIGNMENT_OTHER_NAMESPACE);
		}
	}

	/**
	 * Checks if an AtExpression is either a valid hash access, or a resource reference.
	 * Use isStandardAtExpression to check if an AtExpression is one or the other.
	 * Also see {@link #isStandardAtExpression(AtExpression)})
	 * 
	 * @param o
	 */
	@Check
	public void checkAtExpression(AtExpression o) {
		if(!isStandardAtExpression(o)) {
			checkAtExpressionAsResourceReference(o);
			return;
		}

		// Puppet grammar At expression is VARIABLE[expr]([expr])? (i.e. only one nested level).
		//
		final Expression leftExpr = o.getLeftExpr();
		if(leftExpr == null)
			acceptor.acceptError(
				"Expression left of [] is required", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
		else if(!(leftExpr instanceof VariableExpression || (o.eContainer() instanceof ExpressionTE && leftExpr instanceof LiteralNameOrReference))) {
			// then, the leftExpression *must* be an AtExpression with a leftExpr being a variable
			if(leftExpr instanceof AtExpression) {
				// older versions limited access to two levels.
				if(!PuppetCompatibilityHelper.allowMoreThan2AtInSequence()) {
					final Expression nestedLeftExpr = ((AtExpression) leftExpr).getLeftExpr();
					// if nestedLeftExpr is null, it is validated for the nested instance
					if(nestedLeftExpr != null &&
							!(nestedLeftExpr instanceof VariableExpression || (o.eContainer() instanceof ExpressionTE && nestedLeftExpr instanceof LiteralNameOrReference)))
						acceptor.acceptError(
							"Expression left of [] must be a variable.", nestedLeftExpr,
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
							IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
				}
			}
			else {
				acceptor.acceptError(
					"Expression left of [] must be a variable.", leftExpr,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
			}
		}
		// -- check that there is exactly one parameter expression (the key)
		switch(o.getParameters().size()) {
			case 0:
				acceptor.acceptError(
					"Key/index expression is required", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
				break;
			case 1:
				break; // ok
			default:
				acceptor.acceptError(
					"Multiple expressions are not allowed", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
		// // TODO: Check for valid expressions (don't know if any expression is acceptable)
		// for(Expression expr : o.getParameters()) {
		// //
		// }
	}

	/**
	 * Checks that an AtExpression that acts as a ResourceReference is valid.
	 * 
	 * @param resourceRef
	 * @param errorStartText
	 */
	public void checkAtExpressionAsResourceReference(AtExpression resourceRef) {
		final String errorStartText = "A resource reference";
		Expression leftExpr = resourceRef.getLeftExpr();
		if(leftExpr == null)
			acceptor.acceptError(
				errorStartText + " must start with a name or referece (was null).", resourceRef,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__RESOURCE_REFERENCE_NO_REFERENCE);
		else {
			String name = leftExpr instanceof LiteralNameOrReference
					? ((LiteralNameOrReference) leftExpr).getValue()
					: null;
			boolean isref = isCLASSREF(name);
			boolean isname = isNAME(name);
			if(!isref) {
				if(!isname)
					acceptor.acceptError(
						errorStartText + " must start with a [(deprecated) name, or] class reference.", resourceRef,
						PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__NOT_CLASSREF);
				else
					acceptor.acceptWarning(
						errorStartText + " uses deprecated form of reference. Should start with upper case letter.",
						resourceRef, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__DEPRECATED_REFERENCE);
			}

		}
		if(resourceRef.getParameters().size() < 1)
			acceptor.acceptError(
				errorStartText + " must have at least one expression in list.", resourceRef,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__RESOURCE_REFERENCE_NO_PARAMETERS);

		// TODO: Possibly check valid expressions in the list, there are probably many illegal constructs valid in Puppet grammar
		// TODO: Handle all relaxations in the puppet model/grammar
		for(Expression expr : resourceRef.getParameters()) {
			if(expr instanceof LiteralRegex)
				acceptor.acceptError(
					errorStartText + " invalid resource reference parameter expression type.", resourceRef,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, resourceRef.getParameters().indexOf(expr),
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
	}

	@Check
	public void checkAttributeAddition(AttributeAddition o) {
		if(!isNAME(o.getKey()))
			acceptor.acceptError(
				"Bad name format.", o, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_NAME);
	}

	@Check
	public void checkAttributeDefinition(AttributeDefinition o) {
		if(!isNAME(o.getKey()))
			acceptor.acceptError(
				"Bad name format.", o, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_NAME);
	}

	/**
	 * Checks that AttributeOperation objects in the list are separated by commas
	 * (if there is an associated node model).
	 * 
	 * @param o
	 */
	@Check
	public void checkAttributeOperations(AttributeOperations o) {
		ICompositeNode rootNode = NodeModelUtils.getNode(o);
		if(rootNode != null) {
			boolean expectComma = false;
			int expectOffset = 0;
			for(INode n : rootNode.getChildren()) {
				// skip whitespace and comments
				if(n instanceof ILeafNode && ((ILeafNode) n).isHidden())
					continue;
				if(expectComma) {
					if(!(n instanceof ILeafNode && ",".equals(n.getText()))) {
						acceptor.acceptError("Missing comma.", n.getSemanticElement(),
						// note that offset must be -1 as this ofter a hidden newline and this
						// does not work otherwise. Any quickfix needs to adjust the offset on replacement.
							expectOffset - 1, 2, IPPDiagnostics.ISSUE__MISSING_COMMA);
					}
					expectComma = false;
				}

				if(n.getGrammarElement() instanceof RuleCall) {
					RuleCall rc = (RuleCall) n.getGrammarElement();
					if(rc.getRule().getName().equals(puppetGrammarAccess.getAttributeOperationRule().getName())) {
						expectComma = true;
						// pos where would have liked to see a comma
						expectOffset = n.getTotalOffset() + n.getTotalLength();
					}
				}
			}
		}
	}

	@Check
	public void checkBinaryExpression(BinaryExpression o) {
		if(o.getLeftExpr() == null)
			acceptor.acceptError(
				"A binary expression must have a left expr", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NULL_EXPRESSION);
		if(o.getRightExpr() == null)
			acceptor.acceptError(
				"A binary expression must have a right expr", o, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	/**
	 * Checks case literals that puppet will barf on:
	 * - an unquoted text sequence that contains "."
	 * 
	 * @param o
	 */
	@Check
	public void checkCaseExpression(Case o) {
		for(Expression value : o.getValues()) {
			if(value.eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE) {
				String v = ((LiteralNameOrReference) value).getValue();
				if(v != null && v.contains("."))
					acceptor.acceptError(
						"A case value containing '.' (period) must be quoted", value,
						IPPDiagnostics.ISSUE__REQUIRES_QUOTING);

			}
		}
	}

	@Check
	public void checkCollectExpression(CollectExpression o) {

		// -- the class reference must have valid class ref format
		final Expression classRefExpr = o.getClassReference();
		if(classRefExpr instanceof LiteralNameOrReference) {
			final String classRefString = ((LiteralNameOrReference) classRefExpr).getValue();
			if(!isCLASSREF(classRefString))
				acceptor.acceptError(
					"Not a well formed class reference.", o, PPPackage.Literals.COLLECT_EXPRESSION__CLASS_REFERENCE,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_CLASSREF);
		}
		else {
			acceptor.acceptError(
				"Not a class reference.", o, PPPackage.Literals.COLLECT_EXPRESSION__CLASS_REFERENCE,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_CLASSREF);
		}

		// -- the rhs expressions do not allow a full set of expressions, an extensive search must be made
		// Note: queries must implement both IQuery and be UnaryExpressions
		UnaryExpression q = (UnaryExpression) o.getQuery();
		Expression queryExpr = q.getExpr();

		// null expression is accepted, if stated it must comply with the simplified expressions allowed
		// for a collect expression
		if(queryExpr != null) {
			CollectChecker cc = new CollectChecker();
			cc.doCheck(queryExpr);
		}
	}

	@Check
	public void checkDefinition(Definition o) {
		// Can only be contained by manifest (global scope), or another class.
		EObject container = o.eContainer();
		if(!(container instanceof PuppetManifest || container instanceof HostClassDefinition))
			acceptor.acceptError(
				"A definition may only appear at toplevel or directly inside classes.", o.eContainer(),
				o.eContainingFeature(), INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_AT_TOPLEVEL_OR_CLASS);

		if(!isCLASSNAME(o.getClassName())) {
			acceptor.acceptError(
				"Must be a valid name (each segment must start with lower case letter)", o,
				PPPackage.Literals.DEFINITION__CLASS_NAME, IPPDiagnostics.ISSUE__NOT_CLASSNAME);
		}
	}

	@Check
	public void checkDefinitionArgument(DefinitionArgument o) {
		// -- LHS should be a variable, use of name is deprecated
		String argName = o.getArgName();
		if(argName == null || argName.length() < 1)
			acceptor.acceptError(
				"Empty or null argument", o, PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		else if(!argName.startsWith("$"))
			acceptor.acceptWarning(
				"Deprecation: Definition argument should now start with $", o,
				PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		else if(!isVARIABLE(argName))
			acceptor.acceptError(
				"Not a valid variable name", o, PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		if(o.getOp() != null && !"=".equals(o.getOp()))
			acceptor.acceptError(
				"Must be an assignment operator '=' (not definition '=>')", o,
				PPPackage.Literals.DEFINITION_ARGUMENT__OP, IPPDiagnostics.ISSUE__NOT_ASSIGNMENT_OP);
		// -- RHS should be a rvalue
		internalCheckRvalueExpression(o.getValue());
	}

	@Check
	public void checkElseExpression(ElseExpression o) {
		EObject container = o.eContainer();
		if(container instanceof IfExpression || container instanceof ElseExpression ||
				container instanceof ElseIfExpression)
			return;
		acceptor.acceptError(
			"'else' expression can only be used in an 'if', 'else' or 'elsif'", o, o.eContainingFeature(),
			INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	@Check
	public void checkElseIfExpression(ElseIfExpression o) {
		EObject container = o.eContainer();
		if(container instanceof IfExpression || container instanceof ElseExpression ||
				container instanceof ElseIfExpression)
			return;
		acceptor.acceptError(
			"'elsif' expression can only be used in an 'if', 'else' or 'elsif'", o, o.eContainingFeature(),
			INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	@Check
	public void checkEqualityExpression(EqualityExpression o) {
		checkOperator(o, "==", "!=");
	}

	@Check
	public void checkFunctionCall(FunctionCall o) {
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			acceptor.acceptError(
				"Must be a name or reference.", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR,
				IPPDiagnostics.ISSUE__NOT_NAME_OR_REF);
		// rest of validation - valid function - is done during linking
	}

	@Check
	public void checkHostClassDefinition(HostClassDefinition o) {
		// TODO: name must be NAME, CLASSNAME or "class"
		// TODO: parent should be a known class

		// Can only be contained by manifest (global scope), or another class.
		EObject container = o.eContainer();
		if(!(container instanceof PuppetManifest || container instanceof HostClassDefinition))
			acceptor.acceptError(
				"Classes may only appear at toplevel or directly inside other classes.", o,
				IPPDiagnostics.ISSUE__NOT_AT_TOPLEVEL_OR_CLASS);
		internalCheckTopLevelExpressions(o.getStatements());
	}

	@Check
	public void checkIfExpression(IfExpression o) {
		Expression elseStatement = o.getElseStatement();
		if(elseStatement == null || elseStatement instanceof ElseExpression || elseStatement instanceof IfExpression ||
				elseStatement instanceof ElseIfExpression)
			return;
		acceptor.acceptError(
			"If Expression's else part can only be an 'if' or 'elsif'", o,
			PPPackage.Literals.IF_EXPRESSION__ELSE_STATEMENT, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	@Check
	public void checkImportExpression(ImportExpression o) {
		if(o.getValues().size() <= 0)
			acceptor.acceptError(
				"Empty import - should be followed by at least one string.", o,
				PPPackage.Literals.IMPORT_EXPRESSION__VALUES, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
		// warn against interpolation in double quoted strings
		for(IQuotedString s : o.getValues()) {
			if(s instanceof DoubleQuotedString)
				if(hasInterpolation(s))
					acceptor.acceptWarning(
						"String has interpolation expressions that will not be evaluated", s,
						PPPackage.Literals.IMPORT_EXPRESSION__VALUES, o.getValues().indexOf(s),
						IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
	}

	@Check
	public void checkInExpression(InExpression o) {
		checkOperator(o, "in");
	}

	@Check
	public void checkLiteralName(LiteralName o) {
		if(!isNAME(o.getValue()))
			acceptor.acceptError(
				"Expected to comply with NAME rule", o, PPPackage.Literals.LITERAL_NAME__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_NAME);
	}

	@Check
	public void checkLiteralNameOrReference(LiteralNameOrReference o) {
		if(isKEYWORD(o.getValue())) {
			acceptor.acceptError(
				"Reserved word.", o, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__RESERVED_WORD);
			return;
		}

		if(isCLASSNAME_OR_REFERENCE(o.getValue()))
			return;
		acceptor.acceptError(
			"Must be a name or type (all segments must start with same case).", o,
			PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__NOT_NAME_OR_REF, o.getValue());

	}

	@Check
	public void checkLiteralRegex(LiteralRegex o) {
		if(!isREGEX(o.getValue())) {
			acceptor.acceptError(
				"Expected to comply with Puppet regular expression", o, PPPackage.Literals.LITERAL_REGEX__VALUE,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_REGEX);
			return;
		}
		if(!o.getValue().endsWith("/"))
			acceptor.acceptError(
				"Puppet regular expression does not support flags after end slash", o,
				PPPackage.Literals.LITERAL_REGEX__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_REGEX_FLAGS);
	}

	@Check
	public void checkMatchingExpression(MatchingExpression o) {
		Expression regex = o.getRightExpr();
		if(regex == null || !(regex instanceof LiteralRegex))
			acceptor.acceptError(
				"Right expression must be a regular expression.", o, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		checkOperator(o, "=~", "!~");
	}

	@Check
	public void checkMultiplicativeExpression(MultiplicativeExpression o) {
		checkOperator(o, "*", "/");
	}

	@Check
	public void checkNodeDefinition(NodeDefinition o) {
		// Can only be contained by manifest (global scope), or another class.
		EObject container = o.eContainer();
		if(!(container instanceof PuppetManifest || container instanceof HostClassDefinition))
			acceptor.acceptError(
				"A node definition may only appear at toplevel or directly inside classes.", container,
				o.eContainingFeature(), INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_AT_TOPLEVEL_OR_CLASS);

		Expression parentExpr = o.getParentName();
		if(parentExpr != null) {
			String parentName = stringConstantEvaluator.doToString(parentExpr);
			if(parentName == null)
				acceptor.acceptError(
					"Must be a constant name/string expression.", o, PPPackage.Literals.NODE_DEFINITION__PARENT_NAME,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_CONSTANT);
		}
	}

	protected void checkOperator(BinaryOpExpression o, String... ops) {
		String op = o.getOpName();
		for(String s : ops)
			if(s.equals(op))
				return;
		acceptor.acceptError(
			"Illegal operator: " + op == null
					? "null"
					: op, o, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__ILLEGAL_OP);

	}

	@Check
	public void checkParenthesisedExpression(ParenthesisedExpression o) {
		if(o.getExpr() == null) {
			final String msg = "Empty expression";
			final String issue = IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION;
			final ICompositeNode node = NodeModelUtils.getNode(o);
			if(node == null)
				acceptor.acceptError(
					msg, o, PPPackage.Literals.PARENTHESISED_EXPRESSION__EXPR, INSIGNIFICANT_INDEX, issue);
			else {
				// if node's text is empty, mark the nodes before/after, if present.
				int textSize = node.getLength();
				INode endNode = textSize == 0 && node.hasNextSibling()
						? node.getNextSibling()
						: node;
				INode startNode = textSize == 0 && node.hasPreviousSibling()
						? node.getPreviousSibling()
						: node;

				((ValidationBasedMessageAcceptor) acceptor).acceptError(
					msg, o, startNode.getOffset(), startNode.getLength() + ((startNode != endNode)
							? endNode.getLength()
							: 0), issue);
			}
		}
	}

	@Check
	public void checkPuppetManifest(PuppetManifest o) {
		internalCheckTopLevelExpressions(o.getStatements());
	}

	@Check
	public void checkRelationalExpression(RelationalExpression o) {
		String op = o.getOpName();
		if("<".equals(op) || "<=".equals(op) || ">".equals(op) || ">=".equals(op))
			return;
		acceptor.acceptError(
			"Illegal operator.", o, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__ILLEGAL_OP);
	}

	/**
	 * Checks that a RelationshipExpression
	 * only has left and right of type
	 * - ResourceExpression (but not a ResourceOverride)
	 * - ResourceReference
	 * - CollectExpression
	 * 
	 * That the operator name is a valid name (if defined from code).
	 * INEDGE : MINUS GT; // '->'
	 * OUTEDGE : LT MINUS; // '<-'
	 * INEDGE_SUB : TILDE GT; // '~>'
	 * OUTEDGE_SUB : LT TILDE; // '<~'
	 */
	@Check
	public void checkRelationshipExpression(RelationshipExpression o) {
		// -- Check operator validity
		String opName = o.getOpName();
		if(opName == null ||
				!("->".equals(opName) || "<-".equals(opName) || "~>".equals(opName) || "<~".equals(opName)))
			acceptor.acceptError(
				"Illegal operator: " + opName == null
						? "null"
						: opName, o, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__ILLEGAL_OP);

		internalCheckRelationshipOperand(o, o.getLeftExpr(), PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR);
		internalCheckRelationshipOperand(o, o.getRightExpr(), PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR);
	}

	@Check
	public void checkResourceBody(ResourceBody o) {
		Expression nameExpr = o.getNameExpr();
		// missing name is checked by container (if it is ok or not)
		if(nameExpr == null)
			return;
		if(!(nameExpr instanceof StringExpression ||
				// TODO: was LiteralString, follow up
				nameExpr instanceof LiteralNameOrReference || nameExpr instanceof LiteralName ||
				nameExpr instanceof VariableExpression || nameExpr instanceof AtExpression ||
				nameExpr instanceof LiteralList || nameExpr instanceof SelectorExpression))
			acceptor.acceptError(
				"Expression unsupported as resource name/title.", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION_STRING_OK);

		if(nameExpr instanceof LiteralNameOrReference) {
			if(((LiteralNameOrReference) nameExpr).getValue().contains("::")) {
				acceptor.acceptError(
					"Qualiied name must be quoted.", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNQUOTED_QUALIFIED_NAME);
			}
		}
		// check for duplicate use of parameter
		Set<String> duplicates = Sets.newHashSet();
		Set<String> processed = Sets.newHashSet();
		AttributeOperations aos = o.getAttributes();

		if(aos != null) {
			// find duplicates
			for(AttributeOperation ao : aos.getAttributes()) {
				final String key = ao.getKey();
				if(processed.contains(key))
					duplicates.add(key);
				processed.add(key);
			}
			// mark all instances of duplicate name
			if(duplicates.size() > 0)
				for(AttributeOperation ao : aos.getAttributes())
					if(duplicates.contains(ao.getKey()))
						acceptor.acceptError(
							"Parameter redefinition", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_PARAMETER);

		}
	}

	/**
	 * Checks ResourceExpression and derived VirtualResourceExpression.
	 * 
	 * @param o
	 */
	@Check
	public void checkResourceExpression(ResourceExpression o) {
		// A regular resource must have a classname
		// Use of class reference is deprecated
		// classname : NAME | "class" | CLASSNAME
		int resourceType = RESOURCE_IS_BAD; // unknown at this point
		final Expression resourceExpr = o.getResourceExpr();
		String resourceTypeName = null;
		if(resourceExpr instanceof LiteralNameOrReference || resourceExpr instanceof VirtualNameOrReference) {

			if(resourceExpr instanceof LiteralNameOrReference) {
				LiteralNameOrReference resourceTypeExpr = (LiteralNameOrReference) resourceExpr;
				resourceTypeName = resourceTypeExpr.getValue();
			}
			else {
				VirtualNameOrReference vn = (VirtualNameOrReference) resourceExpr;
				resourceTypeName = vn.getValue();
			}
			if("class".equals(resourceTypeName))
				resourceType = RESOURCE_IS_CLASSPARAMS;
			else if(isCLASSREF(resourceTypeName))
				resourceType = RESOURCE_IS_DEFAULT;
			else if(isNAME(resourceTypeName) || isCLASSNAME(resourceTypeName))
				resourceType = RESOURCE_IS_REGULAR;
			// else the resource is BAD
		}
		if(resourceExpr instanceof AtExpression) {
			resourceType = RESOURCE_IS_OVERRIDE;
		}
		/*
		 * IMPORTANT: set the validated classifier to enable others to more quickly determine the type of
		 * resource, and its typeName (what it is a reference to).
		 */
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		adapter.setClassifier(resourceType);
		adapter.setResourceType(null);
		adapter.setResourceTypeName(resourceTypeName);

		if(resourceType == RESOURCE_IS_BAD) {
			acceptor.acceptError(
				"Resource type must be a literal name, 'class', class reference, or a resource reference.", o,
				PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, INSIGNIFICANT_INDEX,
				ISSUE__RESOURCE_BAD_TYPE_FORMAT);
			// not much use checking the rest
			return;
		}

		// -- can not virtualize/export non regular resources
		if(resourceExpr instanceof VirtualNameOrReference && resourceType != RESOURCE_IS_REGULAR) {
			acceptor.acceptError(
				"Only regular resources can be virtual", o, PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__RESOURCE_NOT_VIRTUALIZEABLE);
		}
		boolean onlyOneBody = resourceType == RESOURCE_IS_DEFAULT || resourceType == RESOURCE_IS_OVERRIDE;
		boolean titleExpected = !onlyOneBody;
		boolean attrAdditionAllowed = resourceType == RESOURCE_IS_OVERRIDE;

		String errorStartText = "Resource ";
		switch(resourceType) {
			case RESOURCE_IS_OVERRIDE:
				errorStartText = "Resource override ";
				break;
			case RESOURCE_IS_DEFAULT:
				errorStartText = "Resource defaults ";
				break;
			case RESOURCE_IS_CLASSPARAMS:
				errorStartText = "Class parameter defaults ";
				break;
		}

		// check multiple bodies
		if(onlyOneBody && o.getResourceData().size() > 1)
			acceptor.acceptError(
				errorStartText + "can not have multiple resource instances.", o,
				PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_DATA, INSIGNIFICANT_INDEX,
				ISSUE__RESOURCE_MULTIPLE_BODIES);

		// rules for body:
		// - should not have a title (deprecated for default, but not allowed
		// for override)
		// TODO: Make deprecation error optional for default
		// - only override may have attribute additions
		//

		// check title

		List<String> titles = Lists.newArrayList();
		for(ResourceBody body : o.getResourceData()) {
			boolean hasTitle = body.getNameExpr() != null; // && body.getName().length() > 0;
			if(titleExpected) {
				if(!hasTitle)
					acceptor.acceptError(
						errorStartText + "must have a title.", body, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__RESOURCE_WITHOUT_TITLE);
				else {
					// TODO: Validate the expression type

					// check for uniqueness (within same resource expression)
					if(body.getNameExpr() instanceof LiteralList) {
						int index = 0;
						for(Expression ne : ((LiteralList) body.getNameExpr()).getElements()) {
							String titleString = stringConstantEvaluator.doToString(ne);
							if(titleString != null) {
								if(titles.contains(titleString)) {
									acceptor.acceptError(
										errorStartText + "redefinition of: " + titleString, body.getNameExpr(),
										PPPackage.Literals.LITERAL_LIST__ELEMENTS, index,
										IPPDiagnostics.ISSUE__RESOURCE_NAME_REDEFINITION);
								}
								else
									titles.add(titleString);
							}
							index++;
						}
					}
					else {
						String titleString = stringConstantEvaluator.doToString(body.getNameExpr());
						if(titleString != null) {
							if(titles.contains(titleString)) {
								acceptor.acceptError(
									errorStartText + "redefinition of: " + titleString, body,
									PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, INSIGNIFICANT_INDEX,
									IPPDiagnostics.ISSUE__RESOURCE_NAME_REDEFINITION);
							}
							else
								titles.add(titleString);
						}
					}
				}
			}
			else if(hasTitle) {
				acceptor.acceptError(
					errorStartText + " can not have a title", body, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__RESOURCE_WITH_TITLE);
			}

			// ensure that only resource override has AttributeAdditions
			if(!attrAdditionAllowed && body.getAttributes() != null) {
				for(AttributeOperation ao : body.getAttributes().getAttributes()) {
					if(ao instanceof AttributeAddition)
						acceptor.acceptError(
							errorStartText + " can not have attribute additions.", body,
							PPPackage.Literals.RESOURCE_BODY__ATTRIBUTES,
							body.getAttributes().getAttributes().indexOf(ao),
							IPPDiagnostics.ISSUE__RESOURCE_WITH_ADDITIONS);
				}
			}
		}

		// --Check Resource Override (the AtExpression)
		if(resourceType == RESOURCE_IS_OVERRIDE) {
			if(isStandardAtExpression((AtExpression) o.getResourceExpr()))
				acceptor.acceptError(
					"Resource override can not be done with array/hash access", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
	}

	@Check
	public void checkSelectorEntry(SelectorEntry o) {
		Expression lhs = o.getLeftExpr();
		if(!isSELECTOR_LHS(lhs))
			acceptor.acceptError(
				"Not an acceptable selector entry left hand side expression. Was: " +
						expressionTypeNameProvider.doToString(lhs), o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		// TODO: check rhs is "rvalue"
	}

	@Check
	public void checkSelectorExpression(SelectorExpression o) {
		Expression lhs = o.getLeftExpr();

		// -- non null lhs, and must be an acceptable lhs value for selector
		if(lhs == null)
			acceptor.acceptError(
				"A selector expression must have a left expression", o,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
		else if(!isSELECTOR_LHS(lhs))
			acceptor.acceptError(
				"Not an acceptable selector left hand side expression", o,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		// -- there must be at least one parameter
		if(o.getParameters().size() < 1)
			acceptor.acceptError(
				"A selector expression must have at least one right side entry", o,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);

		// -- all parameters must be SelectorEntry instances
		for(Expression e : o.getParameters())
			if(!(e instanceof SelectorEntry))
				acceptor.acceptError(
					"Must be a selector entry. Was:" + expressionTypeNameProvider.doToString(e), o,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, o.getParameters().indexOf(e),
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	@Check
	public void checkShiftExpression(ShiftExpression o) {
		checkOperator(o, "<<", ">>");
	}

	@Check
	public void checkSingleQuotedString(SingleQuotedString o) {
		if(!isSTRING(o.getText()))
			acceptor.acceptError(
				"Contains illegal character(s). Probably an unescaped single quote.", o,
				PPPackage.Literals.SINGLE_QUOTED_STRING__TEXT, IPPDiagnostics.ISSUE__NOT_STRING);
		// String s = o.getText();

		// Unrecognized escape sequences are simply used verbatim in sq string, not need to check
		// remove all escaped \ to make it easier to find the illegal escapes
		// Matcher m1 = patternHelper.getRecognizedSQEscapePattern().matcher(s);
		// s = m1.replaceAll("");
		// Matcher m = patternHelper.getUnrecognizedSQEscapesPattern().matcher(s);
		// StringBuffer unrecognized = new StringBuffer();
		// while(m.find())
		// unrecognized.append(m.group());
		// if(unrecognized.length() > 0)
		// acceptor.acceptWarning(
		// "Unrecognized escape sequence(s): " + unrecognized.toString(), o,
		// PPPackage.Literals.SINGLE_QUOTED_STRING__TEXT, IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);
	}

	@Check
	public void checkUnaryExpression(UnaryMinusExpression o) {
		if(o.getExpr() == null)
			acceptor.acceptError(
				"An unary minus expression must have right hand side expression", o,
				PPPackage.Literals.UNARY_EXPRESSION__EXPR, //
				INSIGNIFICANT_INDEX, //
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	public void checkUnaryExpression(UnaryNotExpression o) {
		if(o.getExpr() == null)
			acceptor.acceptError("A not expression must have a righ hand side expression", o, //
				PPPackage.Literals.UNARY_EXPRESSION__EXPR, INSIGNIFICANT_INDEX, //
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	public void checkVariableExpression(VariableExpression o) {
		if(!isVARIABLE(o.getVarName()))
			acceptor.acceptError(
				"Expected to comply with Variable rule", o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	@Check
	public void checkVerbatimTextExpression(VerbatimTE o) {
		String s = o.getText();
		if(s == null || s.length() == 0)
			return;
		// remove all escaped \ to make it easier to find the illegal escapes
		Matcher m1 = patternHelper.getRecognizedDQEscapePattern().matcher(s);
		s = m1.replaceAll("");

		Matcher m = patternHelper.getUnrecognizedDQEscapesPattern().matcher(s);
		StringBuffer unrecognized = new StringBuffer();
		while(m.find())
			unrecognized.append(m.group());
		if(unrecognized.length() > 0)
			acceptor.acceptWarning(
				"Unrecognized escape sequence(s): " + unrecognized.toString(), o,
				IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);
	}

	/**
	 * NOTE: Adds validation to the puppet package (in 1.0 the package was not added
	 * automatically, in 2.0 it is.
	 */
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = super.getEPackages();
		if(!result.contains(PPPackage.eINSTANCE))
			result.add(PPPackage.eINSTANCE);

		return result;
	}

	protected boolean hasInterpolation(IQuotedString s) {
		if(!(s instanceof DoubleQuotedString))
			return false;
		return TextExpressionHelper.hasInterpolation((DoubleQuotedString) s);
	}

	private void internalCheckRelationshipOperand(RelationshipExpression r, Expression o, EReference feature) {

		// -- chained relationsips A -> B -> C
		if(o instanceof RelationshipExpression)
			return; // ok, they are chained

		if(o instanceof ResourceExpression) {
			// may not be a resource override
			ResourceExpression re = (ResourceExpression) o;
			if(re.getResourceExpr() instanceof AtExpression)
				acceptor.acceptError(
					"Dependency can not be defined for a resource override.", r, feature, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
		else if(o instanceof AtExpression) {
			// the AtExpression is validated as standard or resource reference, so only need
			// to check correct form
			if(isStandardAtExpression((AtExpression) o))
				acceptor.acceptError(
					"Dependency can not be formed for an array/hash access", r, feature, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		}
		else if(o instanceof VirtualNameOrReference) {
			acceptor.acceptError(
				"Dependency can not be formed for virtual resource", r, feature, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
		else if(!(o instanceof CollectExpression)) {
			acceptor.acceptError(
				"Dependency can not be formed for this type of expression", r, feature, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		}
	}

	protected void internalCheckRvalueExpression(EList<Expression> statements) {
		for(Expression expr : statements)
			internalCheckRvalueExpression(expr);
	}

	protected void internalCheckRvalueExpression(Expression expr) {
		if(expr == null)
			return;
		for(Class<?> c : rvalueClasses) {
			if(c.isAssignableFrom(expr.getClass()))
				return;
		}
		acceptor.acceptError(
			"Not a right hand side value. Was: " + expressionTypeNameProvider.doToString(expr), expr.eContainer(),
			expr.eContainingFeature(), INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_RVALUE);

	}

	protected void internalCheckTopLevelExpressions(EList<Expression> statements) {
		if(statements == null || statements.size() == 0)
			return;

		// check that all statements are valid as top level statements
		each_top: for(int i = 0; i < statements.size(); i++) {
			Expression s = statements.get(i);
			// -- may be a non parenthesized function call
			if(s instanceof LiteralNameOrReference) {
				// there must be one more expression in the list (a single argument, or
				// an Expression list
				// TODO: different issue, can be fixed by adding "()" if this is a function call without
				// parameters.
				if((i + 1) >= statements.size()) {
					acceptor.acceptError(
						"Not a top level expression. (Looks like a function call without arguments, use '()')",
						s.eContainer(), s.eContainingFeature(), i, IPPDiagnostics.ISSUE__NOT_TOPLEVEL);
					// continue each_top;
				}
				// the next expression is consumed as a single arg, or an expr list
				// TODO: if there are expressions that can not be used as arguments check them here
				i++;
				continue each_top;
			}
			for(Class<?> c : topLevelExprClasses) {
				if(c.isAssignableFrom(s.getClass()))
					continue each_top;
			}
			acceptor.acceptError(
				"Not a top level expression. Was: " + expressionTypeNameProvider.doToString(s), s.eContainer(),
				s.eContainingFeature(), i, IPPDiagnostics.ISSUE__NOT_TOPLEVEL);
		}

	}

	private boolean isCLASSNAME(String s) {
		return patternHelper.isCLASSNAME(s);
	}

	private boolean isCLASSNAME_OR_REFERENCE(String s) {
		return patternHelper.isCLASSNAME(s) || patternHelper.isCLASSREF(s) || patternHelper.isNAME(s);
	}

	private boolean isCLASSREF(String s) {
		return patternHelper.isCLASSREF(s);
	}

	// NOT considered to be keywords:
	// "class" - it is used when describing defaults TODO: follow up after migration to 2.0 model
	private boolean isKEYWORD(String s) {
		if(s == null || s.length() < 1)
			return false;
		for(String k : keywords)
			if(k.equals(s))
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.validation.AbstractInjectableValidator#isLanguageSpecific()
	 * ISSUE: See https://bugs.eclipse.org/bugs/show_bug.cgi?id=335624
	 * TODO: remove work around now that issue is fixed.
	 */
	@Override
	public boolean isLanguageSpecific() {
		// return super.isLanguageSpecific(); // when issue is fixed, or remove method
		return false;
	}

	private boolean isNAME(String s) {
		return patternHelper.isNAME(s);
	}

	private boolean isREGEX(String s) {
		return patternHelper.isREGEXP(s);
	}

	/**
	 * Checks acceptable expression types for SELECTOR lhs
	 * 
	 * @param lhs
	 * @return
	 */
	protected boolean isSELECTOR_LHS(Expression lhs) {
		// the lhs can be one of:
		// name, type, quotedtext, variable, funccall, boolean, undef, default, or regex.
		// Or after fix of puppet issue #5515 also hash/At
		if(lhs instanceof StringExpression ||
				// TODO: was LiteralString follow up
				lhs instanceof LiteralName || lhs instanceof LiteralNameOrReference ||
				lhs instanceof VariableExpression || lhs instanceof FunctionCall || lhs instanceof LiteralBoolean ||
				lhs instanceof LiteralUndef || lhs instanceof LiteralRegex || lhs instanceof LiteralDefault)
			return true;
		if(PuppetCompatibilityHelper.allowHashInSelector() && lhs instanceof AtExpression)
			return true;
		return false;
	}

	private boolean isStandardAtExpression(AtExpression o) {
		// an At expression is standard if the lhs is a variable or an AtExpression
		Expression lhs = o.getLeftExpr();
		return (lhs instanceof VariableExpression || lhs instanceof AtExpression || (o.eContainer() instanceof ExpressionTE && lhs instanceof LiteralNameOrReference));

	}

	private boolean isSTRING(String s) {
		return patternHelper.isSQSTRING(s);
	}

	private boolean isVARIABLE(String s) {
		return patternHelper.isVARIABLE(s);
	}
}
