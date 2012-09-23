/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.AdditiveExpression;
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
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.UnaryExpression;
import org.cloudsmith.geppetto.pp.UnaryMinusExpression;
import org.cloudsmith.geppetto.pp.UnaryNotExpression;
import org.cloudsmith.geppetto.pp.UnlessExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPExpressionEquivalenceCalculator;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.eval.PPTypeEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.linking.PPClassifier;
import org.cloudsmith.geppetto.pp.dsl.linking.ValidationBasedMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.util.TextExpressionHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.validation.Check;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;

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

		public void check(AtExpression o, boolean left) {
			if(left)
				check(o);
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
				"Expression type not allowed here.", o.eContainer(), o.eContainingFeature(), INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
		}

		public void check(Expression o, boolean left) {
			acceptor.acceptError(
				"Expression type not allowed as " + (left
						? "left"
						: "right") + " expression.", o.eContainer(), o.eContainingFeature(), INSIGNIFICANT_INDEX,
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

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	final protected IMessageAcceptor acceptor;

	@Inject
	private PPPatternHelper patternHelper;

	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	private final Provider<IValidationAdvisor> validationAdvisorProvider;

	@Inject
	private PPExpressionEquivalenceCalculator eqCalculator;

	/**
	 * Classes accepted as top level statements in a pp manifest.
	 */
	private static final Class<?>[] topLevelExprClasses = { ResourceExpression.class, // resource, virtual, resource override
			RelationshipExpression.class, //
			CollectExpression.class, //
			AssignmentExpression.class, //
			IfExpression.class, //
			UnlessExpression.class, //
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
	protected PPTypeEvaluator typeEvaluator;

	@Inject
	private PPGrammarAccess grammarAccess;

	private final ImmutableSet<EClass> extendedRelationshipClasses = ImmutableSet.of(
		PPPackage.Literals.VARIABLE_EXPRESSION, //
		PPPackage.Literals.DOUBLE_QUOTED_STRING, //
		PPPackage.Literals.SINGLE_QUOTED_STRING, //
		PPPackage.Literals.LITERAL_HASH, //
		PPPackage.Literals.LITERAL_LIST, //
		PPPackage.Literals.SELECTOR_EXPRESSION, //
		PPPackage.Literals.CASE_EXPRESSION, //
		PPPackage.Literals.COLLECT_EXPRESSION
	// ,
	);

	@Inject
	public PPJavaValidator(IGrammarAccess ga, Provider<IValidationAdvisor> validationAdvisorProvider) {
		acceptor = new ValidationBasedMessageAcceptor(this);
		this.validationAdvisorProvider = validationAdvisorProvider;
	}

	private IValidationAdvisor advisor() {
		return validationAdvisorProvider.get();
	}

	@Check
	public void checkAdditiveExpression(AdditiveExpression o) {
		checkOperator(o, "+", "-");
		checkNumericBinaryExpression(o);
	}

	@Check
	public void checkAppendExpression(AppendExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression))
			acceptor.acceptError(
				"Not an appendable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_APPENDABLE);
		else
			checkAssignability(o, (VariableExpression) leftExpr);

	}

	/**
	 * Common functionality for $x = ... and $x += ...
	 * 
	 * @param o
	 *            the binary expr where varExpr is the leftExpr
	 * @param varExpr
	 *            the leftExpr of the given o BinaryExpression
	 */
	private void checkAssignability(BinaryExpression o, VariableExpression varExpr) {

		// Variables in 'other namespaces' are not assignable.
		if(varExpr.getVarName().contains("::"))
			acceptor.acceptError("Cannot assign to variables in other namespaces", o, //
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX, //
				IPPDiagnostics.ISSUE__ASSIGNMENT_OTHER_NAMESPACE);

		// Decimal numeric variables are not assignable (clash with magic regexp variables)
		if(patternHelper.isDECIMALVAR(varExpr.getVarName()))
			acceptor.acceptError("Cannot assign to regular expression result variable", o, //
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, INSIGNIFICANT_INDEX, //
				IPPDiagnostics.ISSUE__ASSIGNMENT_DECIMAL_VAR);

		// The name "$string" causes inline templates to produce the wrong content.
		// http://projects.puppetlabs.com/issues/14093
		ValidationPreference preference = advisor().assignmentToVarNamedString();
		if(preference.isWarningOrError() &&
				("string".equals(varExpr.getVarName()) || "$string".equals(varExpr.getVarName()))) {
			warningOrError(
				acceptor, preference, "Assignment to $string will cause inline templates to fail", varExpr,
				IPPDiagnostics.ISSUE__ASSIGNMENT_TO_VAR_NAMED_STRING);
		}

	}

	@Check
	public void checkAssignmentExpression(AssignmentExpression o) {
		Expression leftExpr = o.getLeftExpr();
		if(!(leftExpr instanceof VariableExpression || leftExpr instanceof AtExpression))
			acceptor.acceptError(
				"Not an assignable expression", o, PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_ASSIGNABLE);
		if(leftExpr instanceof VariableExpression)
			checkAssignability(o, (VariableExpression) leftExpr);

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
			acceptor.acceptError(
				"Expression left of [] is required", o, PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
		else if(!(leftExpr instanceof VariableExpression || (o.eContainer() instanceof ExpressionTE && leftExpr instanceof LiteralNameOrReference))) {
			// then, the leftExpression *must* be an AtExpression with a leftExpr being a variable
			if(leftExpr instanceof AtExpression) {
				// older versions limited access to two levels.
				if(!advisor().allowMoreThan2AtInSequence()) {
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
	public void checkAttributeAddition(AttributeOperation o) {
		if(!isNAME(o.getKey()))
			acceptor.acceptError(
				"Bad name format.", o, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_NAME);
		String op = o.getOp();
		if(!(op != null && (op.equals("=>") || op.equals("+>"))))
			acceptor.acceptError(
				"Bad operator.", o, PPPackage.Literals.ATTRIBUTE_OPERATION__OP, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_OPERATOR);
		if(o.getValue() == null)
			acceptor.acceptError(
				"Missing value expression", o, PPPackage.Literals.ATTRIBUTE_OPERATION__VALUE, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NULL_EXPRESSION);
	}

	@Check
	public void checkAttributeOperations(AttributeOperations o) {
		final int count = o.getAttributes().size();
		EList<AttributeOperation> attrs = o.getAttributes();
		for(int i = 0; i < count - 1; i++) {
			INode n = NodeModelUtils.getNode(attrs.get(i));
			INode n2 = NodeModelUtils.getNode(attrs.get(i + 1));

			INode commaNode = null;
			for(commaNode = n.getNextSibling(); commaNode != null; commaNode = commaNode.getNextSibling())
				if(commaNode == n2)
					break;
				else if(commaNode instanceof LeafNode && ((LeafNode) commaNode).isHidden())
					continue;
				else
					break;

			if(commaNode == null || !",".equals(commaNode.getText())) {
				int expectOffset = n.getTotalOffset() + n.getTotalLength();
				acceptor.acceptError("Missing comma.", n.getSemanticElement(),
				// note that offset must be -1 as this ofter a hidden newline and this
				// does not work otherwise. Any quickfix needs to adjust the offset on replacement.
					expectOffset - 1, 2, IPPDiagnostics.ISSUE__MISSING_COMMA);
			}
		}
		// check for duplicate use of attribute/parameter
		Set<String> duplicates = Sets.newHashSet();
		Set<String> processed = Sets.newHashSet();
		AttributeOperations aos = o;

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
						"Duplicate attribute: '" + ao.getKey() + "'", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
						IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_PARAMETER);
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
	public void checkCase(Case o) {
		internalCheckTopLevelExpressions(o.getStatements());

		ValidationPreference periodInCase = validationAdvisorProvider.get().periodInCase();
		if(periodInCase != ValidationPreference.IGNORE) {
			for(Expression value : o.getValues()) {
				if(value.eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE) {
					String v = ((LiteralNameOrReference) value).getValue();
					if(v != null && v.contains(".")) {
						if(periodInCase == ValidationPreference.ERROR)
							acceptor.acceptError(
								"A case value containing '.' (period) must be quoted", value,
								IPPDiagnostics.ISSUE__REQUIRES_QUOTING);
						else
							acceptor.acceptWarning(
								"A case value containing '.' (period) should be quoted", value,
								IPPDiagnostics.ISSUE__REQUIRES_QUOTING);
					}
				}
			}
		}
	}

	@Check
	public void checkCaseExpression(CaseExpression o) {
		final Expression switchExpr = o.getSwitchExpr();

		boolean theDefaultIsSeen = false;
		// collect unreachable entries to avoid multiple unreachable markers for an entry
		Set<Integer> unreachables = Sets.newHashSet();
		Set<Integer> duplicates = Sets.newHashSet();
		List<Expression> caseExpressions = Lists.newArrayList();
		for(Case caze : o.getCases()) {
			for(Expression e : caze.getValues()) {
				caseExpressions.add(e);

				if(e instanceof LiteralDefault)
					theDefaultIsSeen = true;
			}
		}

		// if a default is seen it should (optionally) appear last
		if(theDefaultIsSeen) {
			IValidationAdvisor advisor = advisor();
			ValidationPreference shouldBeLast = advisor.caseDefaultShouldAppearLast();
			if(shouldBeLast.isWarningOrError()) {
				int last = caseExpressions.size() - 1;
				for(int i = 0; i < last; i++)
					if(caseExpressions.get(i) instanceof LiteralDefault)
						acceptor.accept(
							severity(shouldBeLast), "A 'default' should appear last", caseExpressions.get(i),
							IPPDiagnostics.ISSUE__DEFAULT_NOT_LAST);
			}
		}

		// Check duplicate by equivalence (mark as duplicate)
		// Check equality to switch expression (mark all others as unreachable),
		for(int i = 0; i < caseExpressions.size(); i++) {
			Expression e1 = caseExpressions.get(i);

			// if a case value is equivalent to the switch expression, all other are unreachable
			if(eqCalculator.isEquivalent(e1, switchExpr))
				for(int u = 0; u < caseExpressions.size(); u++)
					if(u != i)
						unreachables.add(u);

			// or if equal to the case expression e1, that this particular expression is a duplicate (mark both).
			for(int j = i + 1; j < caseExpressions.size(); j++)
				if(eqCalculator.isEquivalent(e1, caseExpressions.get(j)))
					duplicates.addAll(Lists.newArrayList(i, j));
		}

		// mark all that are unreachable
		for(Integer i : unreachables)
			acceptor.acceptWarning("Unreachable case", caseExpressions.get(i), IPPDiagnostics.ISSUE__UNREACHABLE);

		// mark all that are duplicates
		for(Integer i : duplicates)
			acceptor.acceptError("Duplicate case", caseExpressions.get(i), IPPDiagnostics.ISSUE__DUPLICATE_CASE);

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
		internalCheckTopLevelExpressions(o.getStatements());

		String typeLabel = o instanceof HostClassDefinition
				? "class"
				: "define";
		// Can only be contained by manifest (global scope), or another class.
		EObject container = o.eContainer();
		if(!(container instanceof PuppetManifest || container instanceof HostClassDefinition))
			acceptor.acceptError(
				"A '" + typeLabel + "' may only appear at top level or directly inside a class.", o.eContainer(),
				o.eContainingFeature(), INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_AT_TOPLEVEL_OR_CLASS);

		if(!isCLASSNAME(o.getClassName())) {
			// invalid name
			acceptor.acceptError(
				"Must be a valid name (each segment must start with lower case letter)", o,
				PPPackage.Literals.DEFINITION__CLASS_NAME, IPPDiagnostics.ISSUE__NOT_CLASSNAME);
		}
		else {
			// hyphen in name =
			ValidationPreference hyphens = advisor().hyphensInNames();
			if(hyphens.isWarningOrError()) {
				int hyphenIdx = o.getClassName().indexOf("-");
				if(hyphenIdx >= 0) {
					String message = "Hyphen '-' in name only unofficially supported in some puppet versions.";
					if(hyphens == ValidationPreference.WARNING)
						acceptor.acceptWarning(
							message, o, PPPackage.Literals.DEFINITION__CLASS_NAME, IPPDiagnostics.ISSUE__HYPHEN_IN_NAME);
					else
						acceptor.acceptError(
							message, o, PPPackage.Literals.DEFINITION__CLASS_NAME, IPPDiagnostics.ISSUE__HYPHEN_IN_NAME);
				}
			}
			// reserved ?
			if(PPReservedNameHelper.isReservedClassName(o.getClassName())) {
				acceptor.acceptError(
					"Reserved name", o, PPPackage.Literals.DEFINITION__CLASS_NAME, IPPDiagnostics.ISSUE__RESERVED_NAME);
			}
		}
	}

	@Check
	public void checkDefinitionArgument(DefinitionArgument o) {
		// -- LHS should be a variable, use of name is deprecated
		String argName = o.getArgName();
		ValidationPreference missingDollar;
		if(argName == null || argName.length() < 1)
			acceptor.acceptError(
				"Empty or null argument", o, PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		else if(!argName.startsWith("$")) {
			missingDollar = advisor().definitionParamterMissingDollar();
			warningOrError(
				acceptor, missingDollar, (missingDollar.isWarning()
						? "Deprecation: "
						: "") + "Definition argument should start with $", o,
				PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, IPPDiagnostics.ISSUE__NOT_VARNAME);
		}
		else if(!isVARIABLE(argName))
			acceptor.acceptError(
				"Not a valid variable name", o, PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__NOT_VARNAME);

		else if(!isFirstNonDollarLowerCase(argName))
			acceptor.acceptError(
				"A parameter must start with a lower case letter", o, PPPackage.Literals.DEFINITION_ARGUMENT__ARG_NAME,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_INITIAL_LOWERCASE);

		if(o.getOp() != null && !"=".equals(o.getOp()))
			acceptor.acceptError(
				"Must be an assignment operator '=' (not definition '=>')", o,
				PPPackage.Literals.DEFINITION_ARGUMENT__OP, IPPDiagnostics.ISSUE__NOT_ASSIGNMENT_OP);
		// -- RHS should be a rvalue
		internalCheckRvalueExpression(o.getValue());
	}

	@Check
	public void checkDefinitionArgumentList(DefinitionArgumentList o) {
		Set<String> seen = Sets.newHashSet();
		for(DefinitionArgument arg : o.getArguments()) {
			String s = arg.getArgName();
			if(s.startsWith("$"))
				s = s.substring(1);
			if(seen.contains(s)) {
				acceptor.acceptError(
					"Duplicate definition of parameter", arg, IPPDiagnostics.ISSUE__DUPLICATE_PARAMETER);
			}
			else
				seen.add(s);
		}
		IValidationAdvisor advisor = advisor();
		ValidationPreference endComma = advisor.definitionArgumentListEndComma();
		if(endComma.isWarningOrError()) {
			// Check if list ends with optional comma.
			INode n = NodeModelUtils.getNode(o);
			for(INode i : n.getAsTreeIterable().reverse()) {
				if(",".equals(i.getText())) {
					EObject grammarE = i.getGrammarElement();
					if(grammarE instanceof RuleCall && "endComma".equals(((RuleCall) grammarE).getRule().getName())) {
						// not allowed in versions < 2.7.8
						if(endComma.isError())
							acceptor.acceptError(
								"End comma not allowed in versions < 2.7.8", o, i.getOffset(), i.getLength(),
								IPPDiagnostics.ISSUE__ENDCOMMA);
						else
							acceptor.acceptWarning(
								"End comma not allowed in versions < 2.7.8", o, i.getOffset(), i.getLength(),
								IPPDiagnostics.ISSUE__ENDCOMMA);
						return;
					}
					return;
				}
			}
		}
	}

	@Check
	public void checkDoubleQuotedString(DoubleQuotedString o) {
		// Check if a verbatim part starting with '-' follows a VariableTE.
		// If so, issue configurable issue for the VariableTE
		//
		TextExpression previous = null;
		int idx = 0;
		IValidationAdvisor advisor = advisor();
		ValidationPreference hyphens = advisor.interpolatedNonBraceEnclosedHyphens();
		for(TextExpression te : o.getStringPart()) {
			if(idx > 0 && previous instanceof VariableTE && te instanceof VerbatimTE) {
				VerbatimTE verbatim = (VerbatimTE) te;
				if(verbatim.getText().startsWith("-")) {
					if(hyphens.isWarningOrError()) {
						String message = "Interpolation continues past '-' in some puppet 2.7 versions";
						if(hyphens == ValidationPreference.WARNING)
							acceptor.acceptWarning(
								message, o, PPPackage.Literals.DOUBLE_QUOTED_STRING__STRING_PART, idx - 1,
								IPPDiagnostics.ISSUE__INTERPOLATED_HYPHEN);
						else
							acceptor.acceptError(
								message, o, PPPackage.Literals.DOUBLE_QUOTED_STRING__STRING_PART, idx - 1,
								IPPDiagnostics.ISSUE__INTERPOLATED_HYPHEN);
					}
				}
			}
			previous = te;
			idx++;
		}
		ValidationPreference booleansInStringForm = advisor.booleansInStringForm();

		BOOLEAN_STRING: if(booleansInStringForm.isWarningOrError()) {
			// Check if string contains "true" or "false"
			String constant = stringConstantEvaluator.doToString(o);
			if(constant == null)
				break BOOLEAN_STRING;
			constant = constant.trim();
			boolean flagIt = "true".equals(constant) || "false".equals(constant);
			if(flagIt)
				if(booleansInStringForm == ValidationPreference.WARNING)
					acceptor.acceptWarning("This is not a boolean", o, IPPDiagnostics.ISSUE__STRING_BOOLEAN, constant);
				else
					acceptor.acceptError("This is not a boolean", o, IPPDiagnostics.ISSUE__STRING_BOOLEAN, constant);
		}

		// DQ_STRING_NOT_REQUIRED
		ValidationPreference dqStringNotRequired = advisor.dqStringNotRequired();
		if(dqStringNotRequired.isWarningOrError() && !hasInterpolation(o)) {
			// contains escape sequences?
			String constant = stringConstantEvaluator.doToString(o);
			if(!constant.contains("\\"))
				warningOrError(
					acceptor, dqStringNotRequired, "Double quoted string not required", o,
					IPPDiagnostics.ISSUE__DQ_STRING_NOT_REQUIRED);
		}
		// UNBRACED INTERPOLATION
		ValidationPreference unbracedInterpolation = advisor.unbracedInterpolation();
		if(unbracedInterpolation.isWarningOrError()) {
			for(TextExpression te : o.getStringPart()) {
				if(te.eClass().getClassifierID() == PPPackage.VARIABLE_TE) {
					warningOrError(
						acceptor, unbracedInterpolation, "Unbraced interpolation of variable", te,
						IPPDiagnostics.ISSUE__UNBRACED_INTERPOLATION);
				}
			}
		}
		// SINGLE INTERPOLATION
		ValidationPreference dqStringNotRequiredVariable = advisor.dqStringNotRequiredVariable();
		SINGLE_INTERPOLATION: if(dqStringNotRequiredVariable.isWarningOrError()) {
			if(o.getStringPart().size() == 1) {
				String replacement = null;
				TextExpression te = o.getStringPart().get(0);
				switch(te.eClass().getClassifierID()) {
					case PPPackage.VARIABLE_TE:
						replacement = ((VariableTE) te).getVarName();
						break;
					case PPPackage.EXPRESSION_TE:
						Expression expr = ((ExpressionTE) te).getExpression();
						expr = ((ParenthesisedExpression) expr).getExpr();
						if(expr instanceof LiteralNameOrReference)
							replacement = "$" + ((LiteralNameOrReference) expr).getValue();
						break;
					default:
						break SINGLE_INTERPOLATION;
				}
				if(replacement != null) {
					warningOrError(
						acceptor, dqStringNotRequiredVariable,
						"String contains single interpolated variable. Double quotes not required.", o,
						IPPDiagnostics.ISSUE__DQ_STRING_NOT_REQUIRED_VAR, replacement);
				}
			}
		}
	}

	@Check
	public void checkElseExpression(ElseExpression o) {
		internalCheckTopLevelExpressions(o.getStatements());
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
		internalCheckTopLevelExpressions(o.getThenStatements());
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
		// Checks performed by checkDefinition, and in PPResourceLinker
	}

	@Check
	public void checkIfExpression(IfExpression o) {
		internalCheckTopLevelExpressions(o.getThenStatements());
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
		checkNumericBinaryExpression(o);
	}

	@Check
	public void checkNodeDefinition(NodeDefinition o) {
		internalCheckTopLevelExpressions(o.getStatements());

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

	private void checkNumericBinaryExpression(BinaryOpExpression o) {
		switch(typeEvaluator.numericType(o.getLeftExpr())) {
			case YES:
				break;
			case NO:
				acceptor.acceptError(
					"Operator " + o.getOpName() + " requires numeric value.", o,
					PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR, IPPDiagnostics.ISSUE__NOT_NUMERIC);
				break;
			case INCONCLUSIVE:
		}
		switch(typeEvaluator.numericType(o.getRightExpr())) {
			case YES:
				break;
			case NO:
				acceptor.acceptError(
					"Operator " + o.getOpName() + " requires numeric value.", o,
					PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR, IPPDiagnostics.ISSUE__NOT_NUMERIC);
				break;
			case INCONCLUSIVE:
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
		internalCheckComments(o);
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

		boolean okL = internalCheckRelationshipOperand(
			o, o.getLeftExpr(), PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR);
		boolean okR = internalCheckRelationshipOperand(
			o, o.getRightExpr(), PPPackage.Literals.BINARY_EXPRESSION__RIGHT_EXPR);

		// optionally flag RtoL relationships
		if(opName.startsWith("<")) {
			// what does the advice say
			IValidationAdvisor advisor = advisor();
			ValidationPreference rightToLeft = advisor.rightToLeftRelationships();
			if(rightToLeft.isWarningOrError()) {
				List<INode> x = NodeModelUtils.findNodesForFeature(o, PPPackage.Literals.BINARY_OP_EXPRESSION__OP_NAME);
				// in case there is embedded whitespace or crazy stuff... (locate the node)
				INode theNode = null;
				for(INode n : x) {
					if(n.getGrammarElement() == grammarAccess.getRelationshipExpressionAccess().getOpNameEdgeOperatorParserRuleCall_1_1_0())
						theNode = n;
				}
				// a node should have been found, but just to be safe, report the error on the entire expression if there was none.
				if(theNode != null)
					warningOrError(
						acceptor, rightToLeft, "Resource dependencies using <- or <~ are discouraged", theNode,
						IPPDiagnostics.ISSUE_RIGHT_TO_LEFT_RELATIONSHIP, opName, Boolean.toString(okL && okR));
				else
					warningOrError(
						acceptor, rightToLeft, "Resource dependencies using <- or <~ are discouraged", o,
						IPPDiagnostics.ISSUE_RIGHT_TO_LEFT_RELATIONSHIP, opName, Boolean.toString(okL && okR));
			}
		}
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

		// prior to 2.7 a qualified name caused problems
		boolean unquotedNameFlagged = false;
		if(!validationAdvisorProvider.get().allowUnquotedQualifiedResourceNames())
			if(nameExpr instanceof LiteralNameOrReference) {
				if(((LiteralNameOrReference) nameExpr).getValue().contains("::")) {
					acceptor.acceptError(
						"Qualified name must be quoted.", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__UNQUOTED_QUALIFIED_NAME);
					unquotedNameFlagged = true;
				}
			}
		if(!unquotedNameFlagged && nameExpr instanceof LiteralNameOrReference) {
			IValidationAdvisor advisor = advisor();
			ValidationPreference unquotedResourceTitles = advisor.unquotedResourceTitles();
			if(unquotedResourceTitles.isWarningOrError()) {
				warningOrError(
					acceptor, unquotedResourceTitles, "Unquoted resource title", nameExpr,
					IPPDiagnostics.ISSUE__UNQUOTED_QUALIFIED_NAME);
			}
		}
		ValidationPreference ensureFirstAdvise = advisor().ensureShouldAppearFirstInResource();
		if(ensureFirstAdvise.isWarningOrError() && o.getAttributes() != null) {
			int ix = 0;
			for(AttributeOperation ao : o.getAttributes().getAttributes()) {
				// is first ensure, if not, find it and mark it
				if("ensure".equals(ao.getKey()) && ix != 0)
					warningOrError(
						acceptor, ensureFirstAdvise, "Resource property 'ensure' not stated first", ao,
						PPPackage.Literals.ATTRIBUTE_OPERATION__KEY, IPPDiagnostics.ISSUE__ENSURE_NOT_FIRST);
				ix++;
			}

		}
	}

	/**
	 * Checks ResourceExpression and derived VirtualResourceExpression.
	 * 
	 * @param o
	 */
	@Check
	public void checkResourceExpression(ResourceExpression o) {
		classifier.classify(o);
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();

		if(resourceType == RESOURCE_IS_BAD) {
			acceptor.acceptError(
				"Resource type must be a literal name, 'class', class reference, or a resource reference.", o,
				PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, INSIGNIFICANT_INDEX,
				ISSUE__RESOURCE_BAD_TYPE_FORMAT);
			// not much use checking the rest
			return;
		}

		// -- can not virtualize/export non regular resources
		if(o.getResourceExpr() instanceof VirtualNameOrReference && resourceType != RESOURCE_IS_REGULAR) {
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
					if("+>".equals(ao.getOp())) // instanceof AttributeAddition)
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
		// -- one of them should have LiteralDefault as left expr
		// -- there should only be one default
		boolean theDefaultIsSeen = false;
		IValidationAdvisor advisor = advisor();
		// collect unreachable entries to avoid multiple unreachable markers for an entry
		Set<Integer> unreachables = Sets.newHashSet();
		Set<Integer> duplicates = Sets.newHashSet();
		List<Expression> caseExpressions = Lists.newArrayList();

		for(Expression e : o.getParameters()) {
			if(!(e instanceof SelectorEntry)) {
				acceptor.acceptError(
					"Must be a selector entry. Was:" + expressionTypeNameProvider.doToString(e), o,
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, o.getParameters().indexOf(e),
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
				caseExpressions.add(null); // to be skipped later
			}
			else {
				// it is a selector entry
				SelectorEntry se = (SelectorEntry) e;
				Expression e1 = se.getLeftExpr();
				caseExpressions.add(e1);
				if(e1 instanceof LiteralDefault)
					theDefaultIsSeen = true;
			}
		}

		ValidationPreference defaultLast = advisor.selectorDefaultShouldAppearLast();
		if(defaultLast.isWarningOrError() && theDefaultIsSeen) {
			for(int i = 0; i < caseExpressions.size() - 1; i++) {
				Expression e1 = caseExpressions.get(i);
				if(e1 == null)
					continue;
				if(e1 instanceof LiteralDefault) {
					acceptor.accept(
						severity(defaultLast), "A 'default' should be placed last", e1,
						IPPDiagnostics.ISSUE__DEFAULT_NOT_LAST);
				}
			}
		}

		// check that there is a default
		if(!theDefaultIsSeen) {
			ValidationPreference missingDefaultInSelector = advisor.missingDefaultInSelector();
			if(missingDefaultInSelector.isWarningOrError())
				acceptor.accept(
					severity(missingDefaultInSelector), "Missing 'default' selector case", o,
					IPPDiagnostics.ISSUE__MISSING_DEFAULT);
		}

		// Check unreachable by equivalence
		// If a case expr is the same as the switch, all other are unreachable
		// Check for duplicates
		for(int i = 0; i < caseExpressions.size(); i++) {
			Expression e1 = caseExpressions.get(i);
			if(e1 == null)
				continue;
			if(eqCalculator.isEquivalent(e1, o.getLeftExpr()))
				for(int u = 0; u < caseExpressions.size(); u++) {
					if(i == u || caseExpressions.get(u) == null)
						continue;
					unreachables.add(u);
				}
			for(int j = i + 1; j < caseExpressions.size(); j++) {
				Expression e2 = caseExpressions.get(j);
				if(e2 == null)
					continue;
				if(eqCalculator.isEquivalent(e1, e2)) {
					duplicates.add(i);
					duplicates.add(j);
				}
			}
		}

		for(Integer i : unreachables)
			if(caseExpressions.get(i) != null)
				acceptor.acceptWarning("Unreachable", caseExpressions.get(i), IPPDiagnostics.ISSUE__UNREACHABLE);

		for(Integer i : duplicates)
			if(caseExpressions.get(i) != null)
				acceptor.acceptError(
					"Duplicate selector case", caseExpressions.get(i), IPPDiagnostics.ISSUE__DUPLICATE_CASE);

		// check missing comma between entries
		final int count = o.getParameters().size();
		EList<Expression> params = o.getParameters();
		for(int i = 0; i < count - 1; i++) {
			// do not complain about missing ',' if expression is not a selector entry
			Expression e1 = params.get(i);
			if(e1 instanceof SelectorEntry == false)
				continue;
			INode n = NodeModelUtils.getNode(e1);
			INode n2 = NodeModelUtils.getNode(params.get(i + 1));

			INode commaNode = null;
			for(commaNode = n.getNextSibling(); commaNode != null; commaNode = commaNode.getNextSibling())
				if(commaNode == n2)
					break;
				else if(commaNode instanceof LeafNode && ((LeafNode) commaNode).isHidden())
					continue;
				else
					break;

			if(commaNode == null || !",".equals(commaNode.getText())) {
				int expectOffset = n.getTotalOffset() + n.getTotalLength();
				acceptor.acceptError("Missing comma.", n.getSemanticElement(),
				// note that offset must be -1 as this ofter a hidden newline and this
				// does not work otherwise. Any quickfix needs to adjust the offset on replacement.
					expectOffset - 1, 2, IPPDiagnostics.ISSUE__MISSING_COMMA);
			}
		}

	}

	@Check
	public void checkShiftExpression(ShiftExpression o) {
		checkOperator(o, "<<", ">>");
		checkNumericBinaryExpression(o);
	}

	@Check
	public void checkSingleQuotedString(SingleQuotedString o) {
		if(!isSTRING(o.getText()))
			acceptor.acceptError(
				"Contains illegal character(s). Probably an unescaped single quote.", o,
				PPPackage.Literals.SINGLE_QUOTED_STRING__TEXT, IPPDiagnostics.ISSUE__NOT_STRING);

		IValidationAdvisor advisor = advisor();
		ValidationPreference booleansInStringForm = advisor.booleansInStringForm();

		if(booleansInStringForm.isWarningOrError()) {
			// Check if string contains "true" or "false"
			String constant = o.getText();
			if(constant != null) {
				constant = constant.trim();
				if("true".equals(constant) || "false".equals(constant))
					acceptor.accept(
						severity(booleansInStringForm), "This is not a boolean", o,
						IPPDiagnostics.ISSUE__STRING_BOOLEAN, constant);
			}
		}

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
	void checkUnlessExpression(UnlessExpression o) {
		internalCheckTopLevelExpressions(o.getThenStatements());
		if(!advisor().allowUnless()) {
			acceptor.acceptError(
				"The 'unless' statment is only available in Puppet version >= 3.0. (Change target preference?)", o,
				IPPDiagnostics.ISSUE__UNSUPPORTED_UNLESS);
		}
	}

	@Check
	public void checkUnquotedString(UnquotedString o) {
		// Turns out these are not supported at all !
		acceptor.acceptError("Unquoted interpolation is not supported", o, IPPDiagnostics.ISSUE__UNQUOTED_INTERPOLATION);
	}

	@Check
	public void checkVariableExpression(VariableExpression o) {
		if(!isVARIABLE(o.getVarName()))
			acceptor.acceptError(
				"Expected to comply with Variable rule", o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME,
				INSIGNIFICANT_INDEX, IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	@Check
	void checkVariableTextExpression(VariableTE o) {
		// TODO: There is not much that can go wrong here, but should protect against manual model problems (like not a valid variable name.
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

	public void internalCheckComments(PuppetManifest o) {
		ValidationPreference mlComments = validationAdvisorProvider.get().mlComments();
		if(mlComments.isWarningOrError()) {
			INode root = NodeModelUtils.getNode(o);
			if(root != null) {
				root = root.getRootNode();
			}
			for(INode n : root.getAsTreeIterable()) {
				if(n.getGrammarElement() == grammarAccess.getML_COMMENTRule())
					warningOrError(
						acceptor, mlComments, "Comments using /* */ are discouraged", n,
						IPPDiagnostics.ISSUE_UNWANTED_ML_COMMENT, Boolean.toString(n.getText().endsWith("\n")));
			}
		}

	}

	private boolean internalCheckRelationshipOperand(RelationshipExpression r, Expression o, EReference feature) {
		boolean result = true;
		// if extended is true, allow these puppet grammar elements:
		// (resource | resourceref | collection | variable | quoted text | selector | case statement | hasharrayaccesses)
		final boolean extended = advisor().allowExtendedDependencyTypes();

		// -- chained relationsips A -> B -> C
		if(o instanceof RelationshipExpression)
			return result; // ok, they are chained

		// first check classes where validity depends on semantics
		if(o instanceof ResourceExpression) {
			// may not be a resource override
			ResourceExpression re = (ResourceExpression) o;
			if(re.getResourceExpr() instanceof AtExpression) {
				acceptor.acceptError(
					"Dependency can not be defined for a resource override.", r, feature, INSIGNIFICANT_INDEX,
					IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
				result = false;
			}
		}
		else if(o instanceof AtExpression) {
			// extended allows hasharray access, so any form of AtExpression is legal
			if(!extended) {
				// the AtExpression is validated as standard or resource reference, so only need
				// to check correct form
				if(isStandardAtExpression((AtExpression) o)) {
					acceptor.acceptError(
						"Dependency can not be formed for an array/hash access", r, feature, INSIGNIFICANT_INDEX,
						IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
					result = false;
				}
			}
		}
		else if(o instanceof VirtualNameOrReference) {
			acceptor.acceptError(
				"Dependency can not be formed for virtual resource", r, feature, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
			result = false;
		}
		// then check classes where further semantic checks are not required
		else if(!((extended && extendedRelationshipClasses.contains(o.eClass())) || o instanceof CollectExpression)) {
			acceptor.acceptError(
				"Dependency can not be formed for this type of expression", r, feature, INSIGNIFICANT_INDEX,
				IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION);
			result = false;
		}
		return result;
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

	private boolean isFirstNonDollarLowerCase(String s) {
		int pos = s.startsWith("$")
				? 1
				: 0;
		if(s.length() <= pos)
			return false;
		return Character.isLowerCase(s.charAt(pos));
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
		if(advisor().allowHashInSelector() && lhs instanceof AtExpression)
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

	private Severity severity(ValidationPreference pref) {
		return pref.isError()
				? Severity.ERROR
				: Severity.WARNING;
	}

	private void warningOrError(IMessageAcceptor acceptor, ValidationPreference validationPreference, String message,
			EObject o, EAttribute feature, String issue) {
		if(validationPreference.isWarning())
			acceptor.acceptWarning(message, o, feature, issue);
		else if(validationPreference.isError())
			acceptor.acceptError(message, o, feature, issue);

		// remaining case is "ignore"... which we ignore ;)
	}

	private void warningOrError(IMessageAcceptor acceptor, ValidationPreference validationPreference, String message,
			EObject o, String issueCode, String... data) {
		if(validationPreference.isWarning())
			acceptor.acceptWarning(message, o, issueCode, data);
		else if(validationPreference.isError())
			acceptor.acceptError(message, o, issueCode, data);

		// remaining case is "ignore"...
	}

	private void warningOrError(IMessageAcceptor acceptor, ValidationPreference validationPreference, String message,
			INode n, String issueCode, String... data) {
		if(validationPreference.isWarning()) {
			acceptor.acceptWarning(message, n.getSemanticElement(), n.getOffset(), n.getLength(), issueCode, data);
			// acceptor.acceptWarning(message, n, issueCode, data);
		}
		else if(validationPreference.isError())
			acceptor.acceptError(message, n.getSemanticElement(), n.getOffset(), n.getLength(), issueCode, data);

		// remaining case is "ignore"...
	}
}
