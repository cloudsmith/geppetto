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
package org.cloudsmith.geppetto.pp.dsl.linking;

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.COLLECTOR_IS_REGULAR;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.ExprList;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.LiteralExpression;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorEntry;
import org.cloudsmith.geppetto.pp.UnlessExpression;
import org.cloudsmith.geppetto.pp.UnquotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.contentassist.PPProposalsGenerator;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder.SearchResult;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.PPPatternHelper;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Handles special linking of ResourceExpression, ResourceBody and Function references.
 */
public class PPResourceLinker implements IPPDiagnostics {

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(PPDSLConstants.PP_DEBUG_LINKER)
	private ITracer tracer;

	/**
	 * Access to precompiled regular expressions
	 */
	@Inject
	private PPPatternHelper patternHelper;

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider
	 * that is aware of the context (builder, dirty editors, etc.). It is used to obtain the
	 * index for a particular resource. This special provider is obtained here.
	 */
	@Inject
	private org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider indexProvider;

	/**
	 * Classifies ResourceExpression based on its content (regular, override, etc).
	 */
	@Inject
	private PPClassifier classifier;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	private IQualifiedNameConverter converter;

	@Inject
	private PPProposalsGenerator proposer;

	@Inject
	private PPFinder ppFinder;

	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	// Note that order is important
	private final static EClass[] DEF_AND_TYPE = { PPTPPackage.Literals.TYPE, PPPackage.Literals.DEFINITION };

	private static final EClass[] FUNC = { PPTPPackage.Literals.FUNCTION };

	private final static EClass[] CLASS_AND_TYPE = {
			PPPackage.Literals.HOST_CLASS_DEFINITION, PPTPPackage.Literals.TYPE };

	private static String proposalIssue(String issue, String[] proposals) {
		if(proposals == null || proposals.length == 0)
			return issue;
		return issue + IPPDiagnostics.ISSUE_PROPOSAL_SUFFIX;
	}

	private Resource resource;

	@Inject
	private ISearchPathProvider searchPathProvider;

	private PPSearchPath searchPath;

	@Inject
	private Provider<IValidationAdvisor> validationAdvisorProvider;

	private void _link(CollectExpression o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		classifier.classify(o);
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null || resourceType != COLLECTOR_IS_REGULAR)
			return;

		internalLinkTypeExpression(o, o.getClassReference(), true, importedNames, acceptor);
		IEObjectDescription desc = adapter.getTargetObjectDescription(IEObjectDescription.class);
		if(desc != null)
			internalLinkAttributeOperations(o.getAttributes(), desc, importedNames, acceptor);
	}

	/**
	 * Links an arbitrary interpolation expression. Handles the special case of a literal name expression e.g. "${literalName}" as
	 * if it was "${$literalname}"
	 * 
	 * @param o
	 * @param importedNames
	 * @param acceptor
	 */
	private void _link(ExpressionTE o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		Expression expr = o.getExpression();
		if(expr instanceof ParenthesisedExpression)
			expr = ((ParenthesisedExpression) expr).getExpr();
		String varName = null;
		if(expr instanceof LiteralNameOrReference)
			varName = ((LiteralNameOrReference) expr).getValue();
		if(varName == null)
			return; // it is some other type of expression - it is validated as expression
		StringBuilder varName2 = new StringBuilder();
		if(!varName.startsWith("$"))
			varName2.append("$");
		varName2.append(varName);
		if(patternHelper.isVARIABLE(varName2.toString()))
			internalLinkVariable(
				expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, varName, importedNames, acceptor);
		else
			acceptor.acceptError(
				"Not a valid variable name", expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
				IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(FunctionCall o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {

		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		final String name = ((LiteralNameOrReference) o.getLeftExpr()).getValue();

		final SearchResult searchResult = ppFinder.findFunction(o, name, importedNames);
		final List<IEObjectDescription> found = searchResult.getAdjusted(); // findFunction(o, name, importedNames);
		if(found.size() > 0) {
			// record resolution at resource level
			importedNames.addResolved(found);
			CrossReferenceAdapter.set(o.getLeftExpr(), found);
			internalLinkFunctionArguments(name, o, importedNames, acceptor);
			return; // ok, found
		}
		if(searchResult.getRaw().size() > 0) {
			// Not a hard error, it may be valid with a different path
			// not found on path, but exists somewhere in what is visible
			// record resolution at resource level
			importedNames.addResolved(searchResult.getRaw());
			CrossReferenceAdapter.set(o.getLeftExpr(), searchResult.getRaw());
			internalLinkFunctionArguments(name, o, importedNames, acceptor);
			acceptor.acceptWarning("Found outside current path: '" + name + "'", o.getLeftExpr(), //
				IPPDiagnostics.ISSUE__NOT_ON_PATH //
			);
			return; // sort of ok
		}
		String[] proposals = proposer.computeProposals(name, ppFinder.getExportedDescriptions(), searchPath, FUNC);
		acceptor.acceptError("Unknown function: '" + name + "'", o.getLeftExpr(), //
			proposalIssue(IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
			proposals);
		// record failure at resource level
		addUnresolved(importedNames, name, NodeModelUtils.findActualNodeFor(o.getLeftExpr()));
		CrossReferenceAdapter.clear(o.getLeftExpr());
	}

	private void _link(HostClassDefinition o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		final LiteralExpression parent = o.getParent();
		if(parent == null)
			return;
		String parentString = null;
		if(parent.eClass() == PPPackage.Literals.LITERAL_DEFAULT)
			parentString = "default";
		else if(parent.eClass() == PPPackage.Literals.LITERAL_NAME_OR_REFERENCE)
			parentString = ((LiteralNameOrReference) parent).getValue();
		if(parentString == null || parentString.length() < 1)
			return;

		SearchResult searchResult = ppFinder.findHostClasses(o, parentString, importedNames);
		List<IEObjectDescription> descs = searchResult.getAdjusted();
		if(descs.size() > 0) {
			// make list only contain unique references
			descs = Lists.newArrayList(Sets.newHashSet(descs));

			CrossReferenceAdapter.set(parent, descs);
			// record resolution at resource level
			importedNames.addResolved(descs);

			if(descs.size() > 1) {
				// this is an ambiguous link - multiple targets available and order depends on the
				// order at runtime (may not be the same).
				importedNames.addAmbiguous(descs);
				acceptor.acceptWarning(
					"Ambiguous reference to: '" + parentString + "' found in: " +
							visibleResourceList(o.eResource(), descs), o,
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
					IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
					proposer.computeDistinctProposals(parentString, descs));
			}
			// must check for circularity
			List<QualifiedName> visited = Lists.newArrayList();
			visited.add(converter.toQualifiedName(o.getClassName()));
			checkCircularInheritence(o, descs, visited, acceptor, importedNames);
		}
		else if(searchResult.getRaw().size() > 0) {
			List<IEObjectDescription> raw = searchResult.getRaw();
			CrossReferenceAdapter.set(parent, raw);

			// Sort of ok, it is not on the current path
			// record resolution at resource level, so recompile knows about the dependencies
			importedNames.addResolved(raw);
			acceptor.acceptWarning(
				"Found outside current search path: '" + parentString + "'", o,
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		}
		else {
			// record unresolved name at resource level
			addUnresolved(
				importedNames, converter.toQualifiedName(parentString), NodeModelUtils.findActualNodeFor(parent));
			// importedNames.addUnresolved(converter.toQualifiedName(parentString));
			CrossReferenceAdapter.clear(parent);

			// ... and finally, if there was neither a type nor a definition reference
			String[] proposals = proposer.computeProposals(
				parentString, ppFinder.getExportedDescriptions(), searchPath, CLASS_AND_TYPE);
			acceptor.acceptError(
				"Unknown class: '" + parentString + "'", o, //
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
				proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
				proposals);
		}

		if(!advisor().allowInheritanceFromParameterizedClass()) {
			List<IEObjectDescription> targets = descs.size() > 0
					? descs
					: searchResult.getRaw();
			if(targets.size() > 0) {
				IEObjectDescription target = targets.get(0);
				if(target.getUserData(PPDSLConstants.CLASS_ARG_COUNT) != null)
					acceptor.acceptError(
						"Can not inherit from a parameterized class in Puppet versions < 3.0.", o, //
						PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
						IPPDiagnostics.ISSUE__INHERITANCE_WITH_PARAMETERS);
			}
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(ResourceBody o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor,
			boolean profileThis) {

		ResourceExpression resource = (ResourceExpression) o.eContainer();
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		if(adapter.getClassifier() == ClassifierAdapter.UNKNOWN) {
			classifier.classify(resource);
			adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		}
		CLASSPARAMS: if(adapter.getClassifier() == RESOURCE_IS_CLASSPARAMS) {
			// pp: class { classname : parameter => value ... }

			final String className = stringConstantEvaluator.doToString(o.getNameExpr());
			if(className == null) {
				if(canBeAClassReference(o.getNameExpr())) {
					acceptor.acceptWarning(
						"Can not determine until runtime if this is a valid class reference (parameters not validated).", //
						o, // Flag entire body
							// PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, //
						IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
				}
				else {
					acceptor.acceptError(
						"Not a valid class reference", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						IPPDiagnostics.ISSUE__NOT_CLASSREF);
				}
				CrossReferenceAdapter.clear(o.getNameExpr());
				break CLASSPARAMS;
			}
			SearchResult searchResult = ppFinder.findHostClasses(o, className, importedNames);
			List<IEObjectDescription> descs = searchResult.getAdjusted();
			if(descs.size() < 1) {
				if(searchResult.getRaw().size() > 0) {
					// Sort of ok
					importedNames.addResolved(searchResult.getRaw());
					CrossReferenceAdapter.set(o.getNameExpr(), searchResult.getRaw());
					acceptor.acceptWarning(
						"Found outside current search path (parameters not validated): '" + className + "'", o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, IPPDiagnostics.ISSUE__NOT_ON_PATH);
					return; // skip validating parameters

				}

				// Add unresolved info at resource level
				addUnresolved(
					importedNames, converter.toQualifiedName(className),
					NodeModelUtils.findActualNodeFor(o.getNameExpr()));
				// importedNames.addUnresolved(converter.toQualifiedName(className));
				CrossReferenceAdapter.clear(o.getNameExpr());

				String[] proposals = proposer.computeProposals(
					className, ppFinder.getExportedDescriptions(), searchPath, CLASS_AND_TYPE);
				acceptor.acceptError(
					"Unknown class: '" + className + "'", o, //
					PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
					proposals);
				return; // not meaningful to continue (do not report errors for each "inner name")
			}
			if(descs.size() > 0) {
				descs = Lists.newArrayList(Sets.newHashSet(descs));
				// Report resolution at resource level
				importedNames.addResolved(descs);
				CrossReferenceAdapter.set(o.getNameExpr(), descs);

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same). ISSUE: o can be a ResourceBody
					importedNames.addAmbiguous(descs);
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + className + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(className, descs));
				}
				// use the first description found to find attributes
				internalLinkAttributeOperations(o.getAttributes(), descs.get(0), importedNames, acceptor);
			}

		}
		else if(adapter.getClassifier() != RESOURCE_IS_OVERRIDE || resource.getResourceExpr() instanceof AtExpression) {
			// normal resource or override file{} or File[x] { }
			IEObjectDescription desc = (IEObjectDescription) adapter.getTargetObjectDescription();
			// do not flag undefined parameters as errors if type is unknown
			if(desc != null) {
				internalLinkAttributeOperations(o.getAttributes(), desc, importedNames, acceptor);
			}
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(ResourceExpression o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		classifier.classify(o);

		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return;

		// If resource is good, and not 'class', then it must have a known reference type.
		// the resource type - also requires getting the type name from the override's expression).
		if(resourceType == RESOURCE_IS_CLASSPARAMS) {
			// resource is pp: class { classname : parameter => value }
			// do nothing
		}
		else if(resourceType == RESOURCE_IS_OVERRIDE) {
			// TODO: possibly check a resource override if the expression is constant (or it is impossible to lookup
			// do nothing
			if(o.getResourceExpr() instanceof AtExpression) {
				internalLinkTypeExpression(
					o, ((AtExpression) o.getResourceExpr()).getLeftExpr(), true, importedNames, acceptor);
			}
		}
		else {
			internalLinkTypeExpression(o, o.getResourceExpr(), false, importedNames, acceptor);
		}
	}

	private void _link(VariableExpression o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		// a definition of a variable (as opposed to a reference) is a leftExpr in an assignment expression
		if(o.eContainer().eClass() == PPPackage.Literals.ASSIGNMENT_EXPRESSION &&
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR == o.eContainingFeature())
			return; // is a definition
		internalLinkVariable(
			o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME, o.getVarName(), importedNames, acceptor);

	}

	private void _link(VariableTE o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		internalLinkVariable(o, PPPackage.Literals.VARIABLE_TE__VAR_NAME, o.getVarName(), importedNames, acceptor);
	}

	private void addUnresolved(PPImportedNamesAdapter importedNames, QualifiedName name, INode node) {
		importedNames.addUnresolved(name, node.getTotalStartLine(), node.getTotalOffset(), node.getTotalLength());
	}

	private void addUnresolved(PPImportedNamesAdapter importedNames, String name, INode node) {
		addUnresolved(importedNames, converter.toQualifiedName(name), node);
	}

	private IValidationAdvisor advisor() {
		return validationAdvisorProvider.get();
	}

	/**
	 * Returns false if it is impossible that the given expression can result in a valid class
	 * reference at runtime.
	 * 
	 * TODO: this is a really stupid way of doing "type inference", but better than nothing.
	 * 
	 * @param e
	 * @return
	 */
	private boolean canBeAClassReference(Expression e) {
		if(e == null)
			return false; // can happen while editing
		switch(e.eClass().getClassifierID()) {
			case PPPackage.HOST_CLASS_DEFINITION:
			case PPPackage.ASSIGNMENT_EXPRESSION:
			case PPPackage.NODE_DEFINITION:
			case PPPackage.DEFINITION:
			case PPPackage.IMPORT_EXPRESSION:
			case PPPackage.RELATIONAL_EXPRESSION:
			case PPPackage.RESOURCE_EXPRESSION:
			case PPPackage.IF_EXPRESSION:
			case PPPackage.SELECTOR_EXPRESSION:
			case PPPackage.AND_EXPRESSION:
			case PPPackage.OR_EXPRESSION:
			case PPPackage.CASE_EXPRESSION:
			case PPPackage.EQUALITY_EXPRESSION:
			case PPPackage.RELATIONSHIP_EXPRESSION:
				return false;
		}
		return true;
	}

	private void checkCircularInheritence(HostClassDefinition o, Collection<IEObjectDescription> descs,
			List<QualifiedName> stack, IMessageAcceptor acceptor, PPImportedNamesAdapter importedNames) {
		for(IEObjectDescription d : descs) {
			QualifiedName name = d.getName();
			if(stack.contains(name)) {
				// Gotcha!
				acceptor.acceptError( //
					"Circular inheritence", o, //
					PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, //
					IPPDiagnostics.ISSUE__CIRCULAR_INHERITENCE);
				return; // no use continuing
			}
			stack.add(name);
			String parentName = d.getUserData(PPDSLConstants.PARENT_NAME_DATA);
			if(parentName == null || parentName.length() == 0)
				continue;
			SearchResult searchResult = ppFinder.findHostClasses(d.getEObjectOrProxy(), parentName, importedNames);
			List<IEObjectDescription> parents = searchResult.getAdjusted(); // findHostClasses(d.getEObjectOrProxy(), parentName, importedNames);
			checkCircularInheritence(o, parents, stack, acceptor, importedNames);
		}
	}

	private boolean containsNameVar(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription d : descriptions)
			if("true".equals(d.getUserData(PPDSLConstants.PARAMETER_NAMEVAR)))
				return true;
		return false;
	}

	private boolean containsRegularExpression(EObject o) {
		if(o.eClass().getClassifierID() == PPPackage.LITERAL_REGEX)
			return true;
		TreeIterator<EObject> itor = o.eAllContents();
		while(itor.hasNext())
			if(itor.next().eClass().getClassifierID() == PPPackage.LITERAL_REGEX)
				return true;
		return false;
	}

	/**
	 * Returns the first type description. If none is found, the first description is returned.
	 * 
	 * @param descriptions
	 * @return
	 */
	private IEObjectDescription getFirstTypeDescription(List<IEObjectDescription> descriptions) {
		for(IEObjectDescription e : descriptions) {
			if(e.getEClass() == PPTPPackage.Literals.TYPE)
				return e;
		}
		return descriptions.get(0);
	}

	private void internalLinkAttributeOperations(AttributeOperations aos, IEObjectDescription desc,
			PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		List<AttributeOperation> nameVariables = Lists.newArrayList();
		// Multimap<String, AttributeOperation> seen = ArrayListMultimap.create();

		if(aos != null)
			for(AttributeOperation ao : aos.getAttributes()) {
				QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
				// Accept name if there is at least one type/definition that lists the key
				// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
				// the property could be ok in one, but not in another instance.
				// finding that A'::x exists but not A''::x requires a lot more work
				List<IEObjectDescription> foundAttributes = ppFinder.findAttributes(aos, fqn, importedNames).getAdjusted();
				// if the ao is a namevar reference, remember it so uniqueness can be validated
				if(foundAttributes.size() > 0) {
					importedNames.addResolved(foundAttributes);
					CrossReferenceAdapter.set(ao, foundAttributes);
					// seen.put(ao.getKey(), ao);

					if(containsNameVar(foundAttributes))
						nameVariables.add(ao);
					continue; // found one such parameter == ok
				}
				// if the reference is to "name" (and it was not found), then this is a deprecated
				// reference to the namevar
				if("name".equals(ao.getKey())) {
					nameVariables.add(ao);
					acceptor.acceptWarning(
						"Deprecated use of the alias 'name'. Use the type's real name attribute.", ao,
						PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
						IPPDiagnostics.ISSUE__RESOURCE_DEPRECATED_NAME_ALIAS);
					continue;
				}
				String[] proposals = proposer.computeAttributeProposals(
					fqn, ppFinder.getExportedDescriptions(), searchPath);
				acceptor.acceptError(
					"Unknown attribute: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
					PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
					proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
			}
		if(nameVariables.size() > 1) {
			for(AttributeOperation ao : nameVariables) {
				acceptor.acceptError(
					"Duplicate resource name attribute", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
					IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_NAME_PARAMETER);
				// // do not also flag as a general duplicate name
				// seen.removeAll(ao.getKey());
			}
		}
	}

	/**
	 * Link well known functions that must have references to defined things.
	 * 
	 * @param o
	 * @param importedNames
	 * @param acceptor
	 */
	private void internalLinkFunctionArguments(String name, FunctionCall o, PPImportedNamesAdapter importedNames,
			IMessageAcceptor acceptor) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {
			int parameterIndex = -1;
			for(Expression pe : o.getParameters()) {
				parameterIndex++;
				final String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					SearchResult searchResult = ppFinder.findHostClasses(o, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						acceptor.acceptWarning(
							"Ambiguous reference to: '" + className + "' found in: " +
									visibleResourceList(o.eResource(), foundClasses), o,
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
							proposer.computeDistinctProposals(className, foundClasses));
					}
					else if(foundClasses.size() < 1) {
						if(searchResult.getRaw().size() > 0) {
							// sort of ok
							importedNames.addResolved(searchResult.getRaw());
							CrossReferenceAdapter.set(pe, searchResult.getRaw());
							acceptor.acceptWarning(
								"Found outside current search path: '" + className + "'", o,
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								IPPDiagnostics.ISSUE__NOT_ON_PATH);
						}
						else {
							// not found
							// record unresolved name at resource level
							addUnresolved(importedNames, className, NodeModelUtils.findActualNodeFor(pe));
							// importedNames.addUnresolved(converter.toQualifiedName(className));
							CrossReferenceAdapter.clear(pe);

							String[] p = proposer.computeProposals(
								className, ppFinder.getExportedDescriptions(), searchPath, CLASS_AND_TYPE);
							acceptor.acceptError(
								"Unknown class: '" + className + "'", o, //
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, p), //
								p);
						}
					}
					else {
						// found
						importedNames.addResolved(foundClasses);
						CrossReferenceAdapter.set(pe, foundClasses);
					}
				}
				else {
					CrossReferenceAdapter.clear(pe);
					// warning or error depending on if this is a reasonable class reference expr or not
					if(canBeAClassReference(pe)) {
						acceptor.acceptWarning(
							"Can not determine until runtime if this is a valid class reference", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
					else {
						acceptor.acceptError(
							"Not an acceptable parameter. Function '" + name + "' requires a class reference.", //
							o, //
							PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
					}
				}

			}
			// there should have been at least one argument
			if(parameterIndex < 0) {
				acceptor.acceptError("Call to '" + name + "' must have at least one argument.", o, //
					PPPackage.Literals.PARAMETERIZED_EXPRESSION__LEFT_EXPR, //
					IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);

			}
		}
	}

	private void internalLinkFunctionArguments(String name, LiteralNameOrReference s, EList<Expression> statements,
			int idx, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		// have 0:M classes as arguments
		if("require".equals(name) || "include".equals(name)) {

			// there should have been at least one argument
			if(idx >= statements.size()) {
				acceptor.acceptError("Call to '" + name + "' must have at least one argument.", s, //
					PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, //
					IPPDiagnostics.ISSUE__REQUIRED_EXPRESSION);
				return;
			}

			Expression param = statements.get(idx);
			List<Expression> parameterList = null;
			if(param instanceof ExprList)
				parameterList = ((ExprList) param).getExpressions();
			else
				parameterList = Lists.newArrayList(param);

			int parameterIndex = -1;
			for(Expression pe : parameterList) {
				parameterIndex++;
				final String className = stringConstantEvaluator.doToString(pe);
				if(className != null) {
					SearchResult searchResult = ppFinder.findHostClasses(s, className, importedNames);
					List<IEObjectDescription> foundClasses = searchResult.getAdjusted(); // findHostClasses(o, className, importedNames);
					if(foundClasses.size() > 1) {
						// ambiguous
						importedNames.addAmbiguous(foundClasses);
						if(param instanceof ExprList)
							acceptor.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " +
										visibleResourceList(s.eResource(), foundClasses), //
								param, //
								PPPackage.Literals.EXPR_LIST__EXPRESSIONS,
								parameterIndex, //
								IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
								proposer.computeDistinctProposals(className, foundClasses));
						else
							acceptor.acceptWarning(
								"Ambiguous reference to: '" + className + "' found in: " +
										visibleResourceList(s.eResource(), foundClasses), //
								param.eContainer(), param.eContainingFeature(),
								idx, //
								IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
								proposer.computeDistinctProposals(className, foundClasses));

					}
					else if(foundClasses.size() < 1) {
						if(searchResult.getRaw().size() > 0) {
							// sort of ok
							importedNames.addResolved(searchResult.getRaw());
							CrossReferenceAdapter.set(pe, searchResult.getRaw());

							if(param instanceof ExprList)
								acceptor.acceptWarning(
									"Found outside current search path: '" + className + "'", param,
									PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex,
									IPPDiagnostics.ISSUE__NOT_ON_PATH);
							else
								acceptor.acceptWarning("Found outside current search path: '" + className + "'", //
									param.eContainer(), param.eContainingFeature(), idx, // IPPDiagnostics.ISSUE__NOT_ON_PATH);
									IPPDiagnostics.ISSUE__NOT_ON_PATH);

						}
						else {
							// not found
							// record unresolved name at resource level
							addUnresolved(importedNames, className, NodeModelUtils.findActualNodeFor(pe));
							// importedNames.addUnresolved(converter.toQualifiedName(className));
							CrossReferenceAdapter.clear(pe);

							String[] proposals = proposer.computeProposals(
								className, ppFinder.getExportedDescriptions(), searchPath, CLASS_AND_TYPE);
							String issueCode = proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals);
							if(param instanceof ExprList) {
								acceptor.acceptError("Unknown class: '" + className + "'", //
									param, //
									PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
									issueCode, //
									proposals);
							}
							else {
								acceptor.acceptError("Unknown class: '" + className + "'", //
									param.eContainer(), param.eContainingFeature(), idx, //
									issueCode, //
									proposals);
							}
						}
					}
					else {
						// found
						importedNames.addResolved(foundClasses);
						CrossReferenceAdapter.set(pe, foundClasses);
					}
				}
				else {
					CrossReferenceAdapter.clear(pe);

					// warning or error depending on if this is a reasonable class reference expr or not
					String msg = null;
					boolean error = false;
					if(canBeAClassReference(pe)) {
						msg = "Can not determine until runtime if this is a valid class reference";
					}
					else {
						msg = "Not an acceptable parameter. Function '" + name + "' requires a class reference.";
						error = true;
					}
					if(param instanceof ExprList)
						acceptor.accept(error
								? Severity.ERROR
								: Severity.WARNING, msg, //
							param, //
							PPPackage.Literals.EXPR_LIST__EXPRESSIONS, parameterIndex, //
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);

					else
						acceptor.accept(error
								? Severity.ERROR
								: Severity.WARNING, msg, //
							param.eContainer(), param.eContainingFeature(), idx, //
							IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE);
				}
			}
		}
	}

	/**
	 * Produces an error if the given EObject o is not contained (nested) in an expression that injects
	 * the result of a regular expression evaluation (i.e. $0 - $n).
	 * The injecting expressions are unless, if, elseif, case (entry), case expression, and selector entry.
	 * 
	 * TODO: Check if there are (less obvious) expressions
	 * 
	 * @param o
	 * @param varName
	 * @param attr
	 * @param acceptor
	 */
	private void internalLinkRegexpVariable(EObject o, String varName, EAttribute attr, IMessageAcceptor acceptor) {
		// upp the containment chain
		for(EObject p = o.eContainer() /* , contained = o */; p != null; /* contained = p, */p = p.eContainer()) {
			switch(p.eClass().getClassifierID()) {
				case PPPackage.UNLESS_EXPRESSION:
					// o is either in cond, or then part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((UnlessExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.IF_EXPRESSION:
					// o is either in cond, then or else part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((IfExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.ELSE_IF_EXPRESSION:
					// o is either in cond, then or else part
					// TODO: pedantic, check position in cond, must have regexp to the left.
					if(containsRegularExpression(((ElseIfExpression) p).getCondExpr()))
						return;
					break;
				case PPPackage.SELECTOR_ENTRY:
					// TODO: pedantic, check position in leftExpr, must have regexp to the left.
					if(containsRegularExpression(((SelectorEntry) p).getLeftExpr()))
						return;
					break;
				// TODO: CHECK IF THIS ISOTHERIC CASE IS SUPPORTED
				// case PPPackage.SELECTOR_EXPRESSION:
				// if(containsRegularExpression(((SelectorExpression)p).get))
				// return;
				// break;
				case PPPackage.CASE:
					// i.e. case expr { v0, v1, v2 : statements }
					for(EObject v : ((Case) p).getValues())
						if(containsRegularExpression(v))
							return;
					break;
				case PPPackage.CASE_EXPRESSION:
					// TODO: Investigate if this is allowed i.e.:
					// case $α =~ /regexp { true : $a = $0; false : $a = $0 }
					if(containsRegularExpression(((CaseExpression) p).getSwitchExpr()))
						return;
					break;
			}
		}
		acceptor.acceptWarning(
			"Corresponding regular expression not found. Value of '" + varName +
					"' can only be undefined at this point: '" + varName + "'", o, attr,
			IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);

	}

	private void internalLinkTypeExpression(EObject o, EObject reference, boolean upperCaseProposals,
			PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		// int resourceType = adapter.getClassifier();
		String resourceTypeName = adapter.getResourceTypeName();

		// Should not really happen, but if a workspace state is maintained with old things...
		if(resourceTypeName == null)
			return;

		// normal resource
		SearchResult searchResult = ppFinder.findDefinitions(o, resourceTypeName, importedNames);
		List<IEObjectDescription> descs = searchResult.getAdjusted(); // findDefinitions(o, resourceTypeName, importedNames);
		if(descs.size() > 0) {
			// make list only contain unique references
			descs = Lists.newArrayList(Sets.newHashSet(descs));
			removeDisqualifiedContainers(descs, o);
			// if any remain, pick the first type (or the first if there are no types)
			IEObjectDescription usedResolution = null;
			if(descs.size() > 0) {
				usedResolution = getFirstTypeDescription(descs);
				adapter.setTargetObject(usedResolution); // Resource expression's resolution of type
				CrossReferenceAdapter.set(reference, descs); // the actual reference
			}

			if(descs.size() > 1) {
				// this is an ambiguous link - multiple targets available and order depends on the
				// order at runtime (may not be the same).
				importedNames.addAmbiguous(descs);
				acceptor.acceptWarning(
					"Ambiguous reference to: '" + resourceTypeName + "' found in: " +
							visibleResourceList(o.eResource(), descs), reference,
					IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
					proposer.computeDistinctProposals(resourceTypeName, descs, upperCaseProposals));
			}
			// Add resolved information at resource level
			if(usedResolution != null)
				importedNames.addResolved(usedResolution);
			else
				importedNames.addResolved(descs);
		}
		else if(searchResult.getRaw().size() > 0) {
			// sort of ok
			importedNames.addResolved(searchResult.getRaw());
			CrossReferenceAdapter.set(reference, searchResult.getRaw());

			// do not record the type

			acceptor.acceptWarning(
				"Found outside search path: '" + resourceTypeName + "'", reference, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		}
		// only report unresolved if no raw (since if not found adjusted, error is reported as warning)
		if(searchResult.getRaw().size() < 1) {
			CrossReferenceAdapter.clear(reference);
			// ... and finally, if there was neither a type nor a definition reference
			if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null) {
				// Add unresolved info at resource level
				addUnresolved(importedNames, resourceTypeName, NodeModelUtils.findActualNodeFor(reference));
				String[] proposals = proposer.computeProposals(
					resourceTypeName, ppFinder.getExportedDescriptions(), upperCaseProposals, searchPath, DEF_AND_TYPE);
				acceptor.acceptError("Unknown resource type: '" + resourceTypeName + "'", reference,
				// PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, //
				proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
					proposals);
			}
		}
	}

	/**
	 * Links/validates unparenthesized function calls.
	 * 
	 * @param statements
	 * @param acceptor
	 */
	private void internalLinkUnparenthesisedCall(EList<Expression> statements, PPImportedNamesAdapter importedNames,
			IMessageAcceptor acceptor) {
		if(statements == null || statements.size() == 0)
			return;

		each_top: for(int i = 0; i < statements.size(); i++) {
			Expression s = statements.get(i);

			// -- may be a non parenthesized function call
			if(s instanceof LiteralNameOrReference) {
				// there must be one more expression in the list (a single argument, or
				// an Expression list
				// TODO: different issue, can be fixed by adding "()" if this is a function call without
				// parameters, but difficult as validator does not know if function exists (would need
				// an adapter to be able to pick this up in validation).
				if((i + 1) >= statements.size()) {
					continue each_top; // error reported by validation.
				}
				// the next expression is consumed as a single arg, or an expr list
				// TODO: if there are expressions that can not be used as arguments check them here
				i++;
				// Expression arg = statements.get(i); // not used yet...
				String name = ((LiteralNameOrReference) s).getValue();
				SearchResult searchResult = ppFinder.findFunction(s, name, importedNames);
				final boolean existsAdjusted = searchResult.getAdjusted().size() > 0;
				final boolean existsOutside = searchResult.getRaw().size() > 0;

				recordCrossReference(
					converter.toQualifiedName(name), searchResult, existsAdjusted, existsOutside, true, importedNames,
					s);
				if(existsAdjusted || existsOutside) {
					internalLinkFunctionArguments(
						name, (LiteralNameOrReference) s, statements, i, importedNames, acceptor);

					if(!existsAdjusted)
						acceptor.acceptWarning("Found outside current path: '" + name + "'", s, //
							IPPDiagnostics.ISSUE__NOT_ON_PATH);
					continue each_top; // ok, found
				}
				String[] proposals = proposer.computeProposals(
					name, ppFinder.getExportedDescriptions(), searchPath, FUNC);
				acceptor.acceptError(
					"Unknown function: '" + name + "'", s, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
					proposalIssue(IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE, proposals), //
					proposals);

				continue each_top;
			}
		}
	}

	private void internalLinkVariable(EObject o, EAttribute attr, String varName, PPImportedNamesAdapter importedNames,
			IMessageAcceptor acceptor) {
		boolean qualified = false;
		boolean global = false;
		boolean disqualified = false;
		QualifiedName qName = null;
		SearchResult searchResult = null;
		boolean existsAdjusted = false; // variable found as stated
		boolean existsOutside = false; // if not found, reflects if found outside search path
		try {
			qName = converter.toQualifiedName(varName);
			if(patternHelper.isDECIMALVAR(varName)) {
				internalLinkRegexpVariable(o, varName, attr, acceptor);
				return;
			}

			qualified = qName.getSegmentCount() > 1;
			global = qName.getFirstSegment().length() == 0;
			searchResult = ppFinder.findVariable(o, qName, importedNames);

			// remove all references to not yet initialized variables
			disqualified = (0 != removeDisqualifiedVariables(searchResult.getRaw(), o));
			if(disqualified) // adjusted can not have disqualified entries if raw did not have them
				removeDisqualifiedVariables(searchResult.getAdjusted(), o);
			existsAdjusted = searchResult.getAdjusted().size() > 0;
			existsOutside = existsAdjusted
					? false // we are not interested in that it may be both adjusted and raw
					: searchResult.getRaw().size() > 0;
		}
		catch(IllegalArgumentException iae) {
			// Can happen if there is something seriously wrong with the qualified name, should be caught by
			// validation - just ignore it here
			return;
		}
		IValidationAdvisor advisor = advisor();

		boolean mustExist = true;
		if(qualified && global) {
			// TODO: Possible future improvement when more global variables are known.
			// if reported as error now, almost all global variables would be flagged as errors.
			// Future enhancement could warn about those that are not found (resolved at runtime).
			mustExist = false;
		}

		// Record facts at resource and model levels about where variable was found
		recordCrossReference(qName, searchResult, existsAdjusted, existsOutside, mustExist, importedNames, o);

		if(mustExist) {
			if(!(existsAdjusted || existsOutside)) {
				// importedNames.addUnresolved(qName);

				// found nowhere
				if(qualified || advisor.unqualifiedVariables().isWarningOrError()) {
					StringBuilder message = new StringBuilder();
					if(disqualified)
						message.append("Reference to not yet initialized variable: ");
					else
						message.append(qualified
								? "Unknown variable: '"
								: "Unqualified and Unknown variable: '");
					message.append(varName);
					message.append("'");

					String issue = disqualified
							? IPPDiagnostics.ISSUE__UNINITIALIZED_VARIABLE
							: IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE;
					if(disqualified || advisor.unqualifiedVariables() == ValidationPreference.ERROR)
						acceptor.acceptError(message.toString(), o, attr, issue);
					else
						acceptor.acceptWarning(message.toString(), o, attr, issue);
				}
			}
			else if(!existsAdjusted && existsOutside) {
				// found outside
				if(qualified || advisor.unqualifiedVariables().isWarningOrError())
					acceptor.acceptWarning(
						"Found outside current search path variable: '" + varName + "'", o, attr,
						IPPDiagnostics.ISSUE__NOT_ON_PATH);
			}
		}

	}

	/**
	 * Returns true if the descriptions resource path is the same as for the given object and
	 * the fragment path of the given object starts with the fragment path of the description.
	 * 
	 * (An alternative impl would be to first check if they are from the same resource - if so,
	 * it is know that this resource is loaded (since we have the given o) and it should
	 * be possible to search up the containment chain.
	 * 
	 * @param desc
	 * @param o
	 * @return
	 */
	private boolean isParent(IEObjectDescription desc, EObject o) {
		URI descUri = desc.getEObjectURI();
		URI oUri = o.eResource().getURI();
		if(!descUri.path().equals(oUri.path()))
			return false;
		// same resource, if desc's fragment is in at the start of the path, then o is contained
		boolean result = o.eResource().getURIFragment(o).startsWith(descUri.fragment());
		return result;
	}

	/**
	 * Link all resources in the model
	 * 
	 * @param model
	 * @param acceptor
	 */
	public void link(EObject model, IMessageAcceptor acceptor, boolean profileThis) {
		ppFinder.configure(model);
		resource = model.eResource();
		searchPath = searchPathProvider.get(resource);

		// clear names remembered in the past
		PPImportedNamesAdapter importedNames = PPImportedNamesAdapterFactory.eINSTANCE.adapt(resource);
		importedNames.clear();

		IResourceDescriptions descriptionIndex = indexProvider.getResourceDescriptions(resource);
		IResourceDescription descr = descriptionIndex.getResourceDescription(resource.getURI());

		if(descr == null) {
			if(tracer.isTracing()) {
				tracer.trace("Cleaning resource: " + resource.getURI().path());
			}
			return;
		}

		if(tracer.isTracing())
			tracer.trace("Linking resource: ", resource.getURI().path(), "{");

		// Need to get everything in the resource, not just the content of the PuppetManifest (as the manifest has top level
		// expressions that need linking).
		TreeIterator<EObject> everything = resource.getAllContents();
		// it is important that ResourceExpresion are linked before ResourceBodyExpression (but that should
		// be ok with the tree iterator as the bodies are contained).

		while(everything.hasNext()) {
			EObject o = everything.next();
			EClass clazz = o.eClass();
			switch(clazz.getClassifierID()) {
				case PPPackage.EXPRESSION_TE:
					_link((ExpressionTE) o, importedNames, acceptor);
					break;

				case PPPackage.VARIABLE_TE:
					_link((VariableTE) o, importedNames, acceptor);
					break;

				case PPPackage.VARIABLE_EXPRESSION:
					_link((VariableExpression) o, importedNames, acceptor);
					break;

				case PPPackage.RESOURCE_EXPRESSION:
					_link((ResourceExpression) o, importedNames, acceptor);
					break;

				case PPPackage.RESOURCE_BODY:
					_link((ResourceBody) o, importedNames, acceptor, profileThis);
					break;

				case PPPackage.FUNCTION_CALL:
					_link((FunctionCall) o, importedNames, acceptor);
					break;

				// these are needed to link un-parenthesised function calls
				case PPPackage.PUPPET_MANIFEST:
					internalLinkUnparenthesisedCall(((PuppetManifest) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.IF_EXPRESSION:
					internalLinkUnparenthesisedCall(((IfExpression) o).getThenStatements(), importedNames, acceptor);
					break;

				case PPPackage.UNLESS_EXPRESSION:
					internalLinkUnparenthesisedCall(((UnlessExpression) o).getThenStatements(), importedNames, acceptor);
					break;

				case PPPackage.ELSE_EXPRESSION:
					internalLinkUnparenthesisedCall(((ElseExpression) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.ELSE_IF_EXPRESSION:
					internalLinkUnparenthesisedCall(((ElseIfExpression) o).getThenStatements(), importedNames, acceptor);
					break;

				case PPPackage.NODE_DEFINITION:
					internalLinkUnparenthesisedCall(((NodeDefinition) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.DEFINITION:
					internalLinkUnparenthesisedCall(((Definition) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.CASE:
					internalLinkUnparenthesisedCall(((Case) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.HOST_CLASS_DEFINITION:
					_link((HostClassDefinition) o, importedNames, acceptor);
					internalLinkUnparenthesisedCall(((HostClassDefinition) o).getStatements(), importedNames, acceptor);
					break;

				case PPPackage.COLLECT_EXPRESSION:
					_link((CollectExpression) o, importedNames, acceptor);
					break;

				case PPPackage.UNQUOTED_STRING:
					Expression expr = ((UnquotedString) o).getExpression();
					if(expr != null && expr instanceof LiteralNameOrReference) {
						//
						String varName = ((LiteralNameOrReference) expr).getValue();
						StringBuilder varName2 = new StringBuilder();
						if(!varName.startsWith("$"))
							varName2.append("$");
						varName2.append(varName);
						if(patternHelper.isVARIABLE(varName2.toString()))
							internalLinkVariable(
								expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE, varName, importedNames,
								acceptor);
						else
							acceptor.acceptError(
								"Not a valid variable name", expr, PPPackage.Literals.LITERAL_NAME_OR_REFERENCE__VALUE,
								IPPDiagnostics.ISSUE__NOT_VARNAME);

					}
					break;
			}
		}
		if(tracer.isTracing())
			tracer.trace("}");

	}

	/**
	 * Record facts about resolution of qName at model and resource level
	 * 
	 * @param qName
	 *            - the name being resolved
	 * @param searchResult
	 *            - the result
	 * @param existsAdjusted
	 *            - if the adjusted result should be used
	 * @param existsOutside
	 *            - if the raw result should be used
	 * @param mustExists
	 *            - if non existence should be recored as unresolved
	 * @param importedNames
	 *            - resource level fact recorder
	 * @param o
	 *            - the model element where positive result is (also) recorded.
	 */
	private void recordCrossReference(QualifiedName qName, SearchResult searchResult, boolean existsAdjusted,
			boolean existsOutside, boolean mustExists, PPImportedNamesAdapter importedNames, EObject o) {
		List<IEObjectDescription> descriptions = null;

		if(existsAdjusted)
			descriptions = searchResult.getAdjusted();
		else if(existsOutside)
			searchResult.getRaw();

		if(descriptions != null) {
			importedNames.addResolved(descriptions);
			CrossReferenceAdapter.set(o, descriptions);
		}
		else {
			CrossReferenceAdapter.clear(o);
		}
		if(mustExists && !(existsAdjusted || existsOutside)) {
			addUnresolved(importedNames, qName, NodeModelUtils.findActualNodeFor(o));
			// importedNames.addUnresolved(qName);
		}

	}

	/**
	 * Surgically remove all disqualified descriptions (those that are HostClass and a container
	 * of the given object 'o'.
	 * 
	 * @param descs
	 * @param o
	 */
	private void removeDisqualifiedContainers(List<IEObjectDescription> descs, EObject o) {
		if(descs == null)
			return;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			if(x.getEClass() == PPPackage.Literals.DEFINITION || !isParent(x, o))
				continue;
			litor.remove();
		}
	}

	private int removeDisqualifiedVariables(List<IEObjectDescription> descs, EObject o) {
		return removeDisqualifiedVariablesDefinitionArg(descs, o) + removeDisqualifiedVariablesAssignment(descs, o);
	}

	/**
	 * Disqualifies variables that appear on the lhs of an assignment
	 * 
	 * @param descs
	 * @param o
	 * @return
	 */
	private int removeDisqualifiedVariablesAssignment(List<IEObjectDescription> descs, EObject o) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = null;
		for(p = o; p != null; p = p.eContainer()) {
			int classifierId = p.eClass().getClassifierID();
			if(classifierId == PPPackage.ASSIGNMENT_EXPRESSION || classifierId == PPPackage.APPEND_EXPRESSION)
				break;
		}
		if(p == null)
			return 0; // not in an assignment expression

		// p is a BinaryExpression at this point, we want it's parent being an abstract Definition
		final String definitionFragment = p.eResource().getURIFragment(p);
		final String definitionURI = p.eResource().getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;

	}

	/**
	 * Remove variables/entries that are not yet initialized. These are the values
	 * defined in the same name and type if the variable is contained in a definition argument
	 * 
	 * <p>
	 * e.g. in define selfref($selfa = $selfref::selfa, $selfb=$selfa::x) { $x=10 } none of the references to selfa, or x are disqualified.
	 * 
	 * @param descs
	 * @param o
	 * @return the number of disqualified variables removed from the list
	 */
	private int removeDisqualifiedVariablesDefinitionArg(List<IEObjectDescription> descs, EObject o) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = o;
		while(p != null && p.eClass().getClassifierID() != PPPackage.DEFINITION_ARGUMENT)
			p = p.eContainer();
		if(p == null)
			return 0; // not in a definition argument value tree

		// p is a DefinitionArgument at this point, we want it's parent being an abstract Definition
		EObject d = p.eContainer();
		if(d == null)
			return 0; // broken model
		d = d.eContainer();
		final String definitionFragment = d.eResource().getURIFragment(d);
		final String definitionURI = d.eResource().getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;
	}

	/**
	 * Collects the (unique) set of resource paths and returns a message with <=5 (+ ... and x others).
	 * 
	 * @param descriptors
	 * @return
	 */
	private String visibleResourceList(Resource r, List<IEObjectDescription> descriptors) {
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		URI root = (URI) adapter.get(PPDSLConstants.RESOURCE_PROPERTY__ROOT_URI);

		// collect the (unique) resource paths
		List<String> resources = Lists.newArrayList();
		for(IEObjectDescription d : descriptors) {
			URI uri = EcoreUtil.getURI(d.getEObjectOrProxy());
			if(root != null) {
				uri = uri.deresolve(root.appendSegment(""));
			}
			boolean isPptpResource = "pptp".equals(uri.fileExtension());
			String path = isPptpResource
					? uri.lastSegment().replace(".pptp", "")
					: uri.devicePath();
			if(!resources.contains(path))
				resources.add(path);
		}
		StringBuffer buf = new StringBuffer();
		buf.append(resources.size());
		buf.append(" resource");
		buf.append(resources.size() > 1
				? "s ["
				: " [");

		int count = 0;

		// if there are 4 include all, else limit to 3 - typically 2 (fresh user mistake) or is *many*
		final int countCap = resources.size() == 4
				? 4
				: 3;
		for(String s : resources) {
			if(count > 0)
				buf.append(", ");
			buf.append(s);
			if(count++ > countCap) {
				buf.append("and ");
				buf.append(resources.size() - countCap);
				buf.append(" other files...");
				break;
			}
		}
		buf.append("]");
		return buf.toString();
	}
}
