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

import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeAddition;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.BinaryExpression;
import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.EqualityExpression;
import org.cloudsmith.geppetto.pp.Expression;
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
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.util.TextExpressionHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.validation.Check;

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
			error(
				"Expression type not allowed here.", o, o.eContainingFeature(), INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}

		public void check(Expression o, boolean left) {
			error(
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

			error("Must be a name" + (!left
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

	@Inject
	private PPPatternHelper patternHelper;

	/**
	 * "built in" functions that return a value
	 */
	public static String[] namesOfValueFunctions = {
			"defined", "extlookup", "file", "fqdn_rand", "generate", "inline_template", "md5", "regsubst", "sha1",
			"shellquote", "split", "sprintf", "tagged", "template", "versioncmp", };

	/**
	 * "built in" void functions
	 */
	public static String[] namesOfVoidFunctions = {
			"alert", "crit", "debug", "emerg", "err", "file", "include", "info", "notice", "realize", "require",
			"search", "tag", "warning", };

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

	@Check
	public void checkAdditiveExpression(ShiftExpression o) {
		checkOperator(o, "+", "-");
	}

	@Check
	public void checkAppendExpression(AppendExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression))
			error(
				"Not an appendable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_APPENDABLE);
	}

	@Check
	public void checkAssignmentExpression(AssignmentExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression || leftExpr instanceof AtExpression))
			error(
				"Not an assignable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_ASSIGNABLE);
		// TODO: rhs is not validated, it allows expression, which includes rvalue, but some top level expressions
		// are probably not allowed (case?)
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
			error(
				"Expression left of [] is required", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
		else if(!(leftExpr instanceof VariableExpression)) {
			// then, the leftExpression *must* be an AtExpression with a leftExpr being a variable
			if(leftExpr instanceof AtExpression) {
				final Expression nestedLeftExpr = ((AtExpression) leftExpr).getLeftExpr();
				// if nestedLeftExpr is null, it is validated for the nested instance
				if(nestedLeftExpr != null && !(nestedLeftExpr instanceof VariableExpression))
					error(
						"Expression left of [] must be a variable.", nestedLeftExpr,
						PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
			}
			else {
				error(
					"Expression left of [] must be a variable.", leftExpr,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
			}
		}
		// -- check that there is exactly one parameter expression (the key)
		switch(o.getParameters().size()) {
			case 0:
				error(
					"Key/index expression is required", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
				break;
			case 1:
				break; // ok
			default:
				error(
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
			error(
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
					error(
						errorStartText + " must start with a [(deprecated) name, or] class reference.", leftExpr,
						PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__NOT_CLASSREF);
				else
					warning(
						errorStartText + " uses deprecated form of reference. Should start with upper case letter.",
						leftExpr, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__DEPRECATED_REFERENCE);
			}

		}
		if(resourceRef.getParameters().size() < 1)
			error(
				errorStartText + " must have at least one expression in list.", resourceRef,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__RESOURCE_REFERENCE_NO_PARAMETERS);

		// TODO: Possibly check valid expressions in the list, there are probably many illegal constructs valid in Puppet grammar
		// TODO: Handle all relaxations in the puppet model/grammar
		for(Expression expr : resourceRef.getParameters()) {
			if(expr instanceof LiteralRegex)
				error(
					errorStartText + " invalid resource reference parameter expression type.", resourceRef,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, resourceRef.getParameters().indexOf(expr),
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
	}

	@Check
	void checkBinaryExpression(BinaryExpression o) {
		if(o.getLeftExpr() == null)
			error(
				"A binary expression must have a left expr", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NULL_EXPRESSION);
		if(o.getRightExpr() == null)
			error(
				"A binary expression must have a right expr", o, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	public void checkCollectExpression(CollectExpression o) {

		// -- the class reference must have valid class ref format
		final Expression classRefExpr = o.getClassReference();
		if(classRefExpr instanceof LiteralNameOrReference) {
			final String classRefString = ((LiteralNameOrReference) classRefExpr).getValue();
			if(!isCLASSREF(classRefString))
				error(
					"Not a well formed class reference.", o, PPPackage.Literals.COLLECT_EXPRESSION__CLASS_REFERENCE,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_CLASSREF);
		}
		else {
			error(
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
	public void checkDefinitionArgument(DefinitionArgument o) {
		// -- LHS should be a variable, use of name is deprecated
		if(!isVARIABLE(o.getArgName()))
			warning(
				"Deprecation: Definition argument should now start with $", o,
				PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		// -- RHS should be a rvalue
		internalCheckRvalueExpression(o.getValue());
	}

	@Check
	public void checkEqualityExpression(EqualityExpression o) {
		checkOperator(o, "==", "!=");
	}

	@Check
	public void checkHostClassDefinition(HostClassDefinition o) {
		// TODO: name must be NAME, CLASSNAME or "class"
		// TODO: parent should be a known class
		// TODO: more?
		internalCheckTopLevelExpressions(o.getStatements());
	}

	@Check
	public void checkImportExpression(ImportExpression o) {
		if(o.getValues().size() <= 0)
			error(
				"Empty import - should be followed by at least one string.", o,
				PPPackage.Literals.IMPORT_EXPRESSION__VALUES, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
		// warn against interpolation in double quoted strings
		for(IQuotedString s : o.getValues()) {
			if(s instanceof DoubleQuotedString)
				if(hasInterpolation(s))
					warning(
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
			error(
				"Expected to comply with NAME rule", o, PPPackage.Literals.LITERAL_NAME__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_NAME);
	}

	// TODO: CHECK OF STRING EXPRESSIONS, INTERPOLATION ETC.
	@Check
	public void checkLiteralNameOrReference(LiteralNameOrReference o) {
		if(isKEYWORD(o.getValue())) {
			error(
				"Reserved word.", o, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__RESERVED_WORD);
			return;
		}

		if(isCLASSNAME_OR_REFERENCE(o.getValue()))
			return;
		error(
			"Must be a name or type.", o, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__NOT_NAME_OR_REF);

	}

	@Check
	public void checkLiteralRegex(LiteralRegex o) {
		if(!isREGEX(o.getValue())) {
			error(
				"Expected to comply with Puppet regular expression", o, PPPackage.Literals.LITERAL_REGEX__VALUE,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_REGEX);
			return;
		}
		if(!o.getValue().endsWith("/"))
			error(
				"Puppet regular expression does not support flags after end slash", o,
				PPPackage.Literals.LITERAL_REGEX__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_REGEX_FLAGS);
	}

	@Check
	public void checkMatchingExpression(MatchingExpression o) {
		Expression regex = o.getRightExpr();
		if(regex == null || !(regex instanceof LiteralRegex))
			error(
				"Right expression must be a regular expression.", o, PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		checkOperator(o, "=~", "!~");
	}

	@Check
	public void checkMultiplicativeExpression(MultiplicativeExpression o) {
		checkOperator(o, "*", "/");
	}

	protected void checkOperator(BinaryOpExpression o, String... ops) {
		String op = o.getOpName();
		for(String s : ops)
			if(s.equals(op))
				return;
		error(
			"Illegal operator: " + op == null
					? "null"
					: op, o, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME, INSIGNIFICANT_INDEX,
			IPPDiagnostics.ISSUE__ILLEGAL_OP);

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
		error(
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
			error(
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
		if(nameExpr instanceof StringExpression ||
				// TODO: was LiteralString, follow up
				nameExpr instanceof LiteralNameOrReference || nameExpr instanceof LiteralName ||
				nameExpr instanceof VariableExpression || nameExpr instanceof AtExpression ||
				nameExpr instanceof LiteralList || nameExpr instanceof SelectorExpression)
			return;
		error(
			"Expression unsupported as resource name/title.", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
			INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
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
		if(resourceExpr instanceof LiteralNameOrReference || resourceExpr instanceof VirtualNameOrReference) {

			String resourceTypeName = null;
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
		 * resource.
		 */
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		adapter.setClassifier(resourceType);

		if(resourceType == RESOURCE_IS_BAD) {
			error(
				"Resource type must be a literal name, 'class', class reference, or a resource reference.",
				resourceExpr, PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, INSIGNIFICANT_INDEX,
				ISSUE__RESOURCE_BAD_TYPE_FORMAT);
			// not much use checking the rest
			return;
		}
		// -- can not virtualize/export non regular resources
		if(resourceExpr instanceof VirtualNameOrReference && resourceType != RESOURCE_IS_REGULAR) {
			error(
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
				errorStartText = "Reousrce defaults ";
				break;
			case RESOURCE_IS_CLASSPARAMS:
				errorStartText = "Class parameter defaults ";
				break;
		}

		// check multiple bodies
		if(onlyOneBody && o.getResourceData().size() > 1)
			error(
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
		for(ResourceBody body : o.getResourceData()) {
			boolean hasTitle = body.getNameExpr() != null; // && body.getName().length() > 0;
			if(titleExpected) {
				if(!hasTitle)
					error(
						errorStartText + "must have a title.", body, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__RESOURCE_WITHOUT_TITLE);
				else {
					// TODO: Validate the expression type
				}
			}
			else if(hasTitle) {
				error(
					errorStartText + " can not have a title", body, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__RESOURCE_WITH_TITLE);
			}

			// ensure that only resource override has AttributeAdditions
			if(!attrAdditionAllowed && body.getAttributes() != null) {
				for(AttributeOperation ao : body.getAttributes().getAttributes()) {
					if(ao instanceof AttributeAddition)
						error(
							errorStartText + " can not have attribute additions.", body,
							PPPackage.Literals.ATTRIBUTE_OPERATIONS__ATTRIBUTES,
							body.getAttributes().getAttributes().indexOf(ao),
							IPPDiagnostics.ISSUE__RESOURCE_WITH_ADDITIONS);
				}
			}
		}

		// --Check Resource Override (the AtExpression)
		if(resourceType == RESOURCE_IS_OVERRIDE) {
			if(isStandardAtExpression((AtExpression) o.getResourceExpr()))
				error(
					"Resource override can not be done with array/hash access", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
	}

	@Check
	public void checkSelectorEntry(SelectorEntry o) {
		Expression lhs = o.getLeftExpr();
		if(!isSELECTOR_LHS(lhs))
			error(
				"Not an acceptable selector entry left hand side expression. Was: " + lhs.getClass().getSimpleName(),
				o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
	}

	@Check
	public void checkSelectorExpression(SelectorExpression o) {
		Expression lhs = o.getLeftExpr();

		// -- non null lhs, and must be an acceptable lhs value for selector
		if(lhs == null)
			error(
				"A selector expression must have a left expression", o,
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
		else if(!isSELECTOR_LHS(lhs))
			error(
				"Not an acceptable selector left hand side expression", o,
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		// -- there must be at least one parameter
		if(o.getParameters().size() < 1)
			error(
				"A selector expression must have at least one right side entry", o,
				PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);

		// -- all parameters must be SelectorEntry instances
		for(Expression e : o.getParameters())
			if(!(e instanceof SelectorEntry))
				error(
					"Must be a selector entry. Was:" + e.getClass().getSimpleName(), o,
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
			error(
				"Expected to comply with String rule", o, o.eContainingFeature(), INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_STRING);

	}

	@Check
	void checkUnaryExpression(UnaryMinusExpression o) {
		if(o.getExpr() == null)
			error(
				"An unary minus expression must have right hand side expression", o,
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	void checkUnaryExpression(UnaryNotExpression o) {
		if(o.getExpr() == null)
			error(
				"A not expression must have a righ hand side expression", o,
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	public void checkVariableExpression(VariableExpression o) {
		if(!isVARIABLE(o.getVarName()))
			error(
				"Expected to comply with Variable rule", o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_VARNAME);
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

	// private PPGrammarAccess getGrammarAccess() {
	// return (PPGrammarAccess) grammarAccess;
	// }

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
				error(
					"Dependency can not be defined for a resource override.", r, feature, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
		else if(o instanceof AtExpression) {
			// the AtExpression is validated as standard or resource reference, so only need
			// to check correct form
			if(isStandardAtExpression((AtExpression) o))
				error(
					"Dependency can not be formed for an array/hash access", r, feature, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);

		}
		else if(o instanceof VirtualNameOrReference) {
			error(
				"Dependency can not be formed for virtual resource", r, feature, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}
		else if(!(o instanceof CollectExpression)) {
			error(
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
		error(
			"Not a right hand side value. Was: " + expr.getClass().getSimpleName(), expr.eContainer(),
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
					error(
						"Not a top level expression. (Looks like a function call without arguments)", s,
						s.eContainingFeature(), i, IPPDiagnostics.ISSUE__NOT_TOPLEVEL);
				}
				else {
					// the next expression is consumed as a single arg, or an expr list
					// TODO: if there are expressions that can not be used as arguments check them here
					i++;
					Expression arg = statements.get(i);
					internalValidateFunctionCall((LiteralNameOrReference) s, i, arg);
					continue each_top;
				}
			}
			for(Class<?> c : topLevelExprClasses) {
				if(c.isAssignableFrom(s.getClass()))
					continue each_top;
			}
			error(
				"Not a top level expression. Was: " + s.getClass().getSimpleName(), s, s.eContainingFeature(), i,
				IPPDiagnostics.ISSUE__NOT_TOPLEVEL);
		}

	}

	/**
	 * Validate a function call when found in the form of two separate expressions:
	 * a name, and a single Expression, or an ExpressionList.
	 * 
	 * @param name
	 * @param args
	 */
	protected void internalValidateFunctionCall(LiteralNameOrReference name, int index, Expression args) {
		// -- check that name is a reference to an existing function
		// TODO: provide list + extension mechanism (scan module for ruby code?) for validation of functions
		// TODO: is overloading supported?

		// -- check that the argument count complies with the function
		// TODO: requires function meta data

		// Simple checking of names, producing a warning for unknown names
		nameCheck: {
			String n = name.getValue();
			for(String s : namesOfValueFunctions)
				if(n.equals(s))
					break nameCheck;
			for(String s : namesOfVoidFunctions)
				if(n.equals(s))
					break nameCheck;
			warning(
				"Unknown function: " + n, name, name.eContainingFeature(), index,
				IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE);

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
	 * TODO: remove work around when issue is fixed.
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
		// name, type, quotedtext, variable, funccall, boolean, undef, default, or regex
		if(lhs instanceof StringExpression ||
				// TODO: was LiteralString follow up
				lhs instanceof LiteralName || lhs instanceof LiteralNameOrReference ||
				lhs instanceof VariableExpression || lhs instanceof FunctionCall || lhs instanceof LiteralBoolean ||
				lhs instanceof LiteralUndef || lhs instanceof LiteralRegex || lhs instanceof LiteralDefault)
			return true;
		return false;
	}

	private boolean isStandardAtExpression(AtExpression o) {
		// an At expression is standard if the lhs is a variable or an AtExpression
		Expression lhs = o.getLeftExpr();
		return (lhs instanceof VariableExpression || lhs instanceof AtExpression);

		// // TODO: There may be other references that means that the AtExpression is a ResourceReference.
		//
		// // -- when used as a resource reference in a resource override
		// if(o.eContainmentFeature().eClass() == PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR)
		// return false; // checked elsewhere
		//
		// //-- when used as an operand in a relationship expression
		// if(o.eContainer().eClass() == PPPackage.Literals.RELATIONSHIP_EXPRESSION)
		// return false; // checked elsewhere
		//
		// // -- when used as a value in an attribute definition or addition
		// if(o.eContainmentFeature() == PPPackage.Literals.ATTRIBUTE_OPERATION__VALUE)
		// return false;
		//
		// // -- when used as an argument in a function call
		// if(o.eContainmentFeature() == PPPackage.Literals.FUN)
		//
		// return true;
	}

	private boolean isSTRING(String s) {
		return patternHelper.isSQSTRING(s);
	}

	private boolean isVARIABLE(String s) {
		return patternHelper.isVARIABLE(s);
	}
}
