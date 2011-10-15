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
package org.cloudsmith.geppetto.pp.dsl.linking;

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.ElseExpression;
import org.cloudsmith.geppetto.pp.ElseIfExpression;
import org.cloudsmith.geppetto.pp.ExprList;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.FunctionCall;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.LiteralExpression;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
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
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.internal.Lists;
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

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(FunctionCall o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {

		// if not a name, then there is nothing to link, and this error is handled
		// elsewhere
		if(!(o.getLeftExpr() instanceof LiteralNameOrReference))
			return;
		String name = ((LiteralNameOrReference) o.getLeftExpr()).getValue();

		final SearchResult searchResult = ppFinder.findFunction(o, name, importedNames);
		final List<IEObjectDescription> found = searchResult.getAdjusted(); // findFunction(o, name, importedNames);
		if(found.size() > 0) {
			// record resolution at resource level
			importedNames.addResolved(found);
			internalLinkFunctionArguments(name, o, importedNames, acceptor);
			return; // ok, found
		}
		if(searchResult.getRaw().size() > 0) {
			// Not a hard error, it may be valid with a different path
			// not found on path, but exists somewhere in what is visible
			// record resolution at resource level
			importedNames.addResolved(searchResult.getRaw());
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
		importedNames.addUnresolved(converter.toQualifiedName(name));
	}

	private void _link(HostClassDefinition o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		LiteralExpression parent = o.getParent();
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
			List<IEObjectDescription> raw = searchResult.getAdjusted();

			// Sort of ok, it is not on the current path
			// record resolution at resource level, so recompile knows about the dependencies
			importedNames.addResolved(raw);
			acceptor.acceptWarning(
				"Found outside currect search path: '" + parentString + "'", o,
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT, IPPDiagnostics.ISSUE__NOT_ON_PATH);

		}
		else {
			// record unresolved name at resource level
			importedNames.addUnresolved(converter.toQualifiedName(parentString));

			// ... and finally, if there was neither a type nor a definition reference
			String[] proposals = proposer.computeProposals(
				parentString, ppFinder.getExportedDescriptions(), searchPath, CLASS_AND_TYPE);
			acceptor.acceptError(
				"Unknown class: '" + parentString + "'", o, //
				PPPackage.Literals.HOST_CLASS_DEFINITION__PARENT,
				proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
				proposals);
		}
	}

	/**
	 * polymorph {@link #link(EObject, IMessageAcceptor)}
	 */
	private void _link(ResourceBody o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor,
			boolean profileThis) {

		ResourceExpression resource = (ResourceExpression) o.eContainer();
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resource);
		if(adapter.getClassifier() == RESOURCE_IS_CLASSPARAMS) {
			// pp: class { classname : parameter => value ... }

			final String className = stringConstantEvaluator.doToString(o.getNameExpr());
			if(className == null) {
				acceptor.acceptError(
					"Not a valid class reference", o, PPPackage.Literals.RESOURCE_BODY__NAME_EXPR,
					IPPDiagnostics.ISSUE__NOT_CLASSREF);
				return; // not meaningful to continue
			}
			SearchResult searchResult = ppFinder.findHostClasses(o, className, importedNames);
			List<IEObjectDescription> descs = searchResult.getAdjusted();
			if(descs.size() < 1) {
				if(searchResult.getRaw().size() > 0) {
					// Sort of ok
					importedNames.addResolved(searchResult.getRaw());
					acceptor.acceptWarning(
						"Found outside currect search path (parameters not validated): '" + className + "'", o,
						PPPackage.Literals.RESOURCE_BODY__NAME_EXPR, IPPDiagnostics.ISSUE__NOT_ON_PATH);
					return; // skip validating parameters

				}

				// Add unresolved info at resource level
				importedNames.addUnresolved(converter.toQualifiedName(className));
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
				// use the first description found to find parameters
				IEObjectDescription desc = descs.get(0);
				AttributeOperations aos = o.getAttributes();
				if(aos != null)
					for(AttributeOperation ao : aos.getAttributes()) {
						QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
						// Accept name if there is at least one type/definition that lists the key
						// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
						// the property could be ok in one, but not in another instance.
						// finding that A'::x exists but not A''::x requires a lot more work
						if(ppFinder.findAttributes(o, fqn, importedNames).getAdjusted().size() > 0)
							continue; // found one such parameter == ok

						String[] proposals = proposer.computeAttributeProposals(
							fqn, ppFinder.getExportedDescriptions(), searchPath);
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
					}

			}

		}
		else if(adapter.getClassifier() == RESOURCE_IS_OVERRIDE) {
			// do nothing
		}
		else {
			// normal resource
			IEObjectDescription desc = (IEObjectDescription) adapter.getTargetObjectDescription();
			// do not flag undefined parameters as errors if type is unknown
			if(desc != null) {
				AttributeOperations aos = o.getAttributes();
				List<AttributeOperation> nameVariables = Lists.newArrayList();

				if(aos != null)
					for(AttributeOperation ao : aos.getAttributes()) {
						QualifiedName fqn = desc.getQualifiedName().append(ao.getKey());
						// Accept name if there is at least one type/definition that lists the key
						// NOTE/TODO: If there are other problems (multiple definitions with same name etc,
						// the property could be ok in one, but not in another instance.
						// finding that A'::x exists but not A''::x requires a lot more work
						List<IEObjectDescription> foundAttributes = ppFinder.findAttributes(o, fqn, importedNames).getAdjusted();
						// if the ao is a namevar reference, remember it so uniqueness can be validated
						if(foundAttributes.size() > 0) {
							if(containsNameVar(foundAttributes))
								nameVariables.add(ao);
							continue; // found one such parameter == ok
						}
						// if the reference is to "name" (and it was not found), then this is a deprecated
						// reference to the namevar
						if("name".equals(ao.getKey())) {
							nameVariables.add(ao);
							acceptor.acceptWarning(
								"Deprecated use of the alias 'name' for resource name parameter. Use the type's real name variable.",
								ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
								IPPDiagnostics.ISSUE__RESOURCE_DEPRECATED_NAME_ALIAS);
							continue;
						}
						String[] proposals = proposer.computeAttributeProposals(
							fqn, ppFinder.getExportedDescriptions(), searchPath);
						acceptor.acceptError(
							"Unknown parameter: '" + ao.getKey() + "' in definition: '" + desc.getName() + "'", ao,
							PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY, proposals), proposals);
					}
				if(nameVariables.size() > 1) {
					for(AttributeOperation ao : nameVariables)
						acceptor.acceptError(
							"Duplicate resource name specification", ao, PPPackage.Literals.ATTRIBUTE_OPERATION__KEY,
							IPPDiagnostics.ISSUE__RESOURCE_DUPLICATE_NAME_PARAMETER);
				}
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
		}
		else {
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
					adapter.setTargetObject(usedResolution);
				}

				if(descs.size() > 1) {
					// this is an ambiguous link - multiple targets available and order depends on the
					// order at runtime (may not be the same).
					importedNames.addAmbiguous(descs);
					acceptor.acceptWarning(
						"Ambiguous reference to: '" + resourceTypeName + "' found in: " +
								visibleResourceList(o.eResource(), descs), o,
						PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR,
						IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE,
						proposer.computeDistinctProposals(resourceTypeName, descs));
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
				// do not record the type

				acceptor.acceptWarning(
					"Found outside search path: '" + resourceTypeName + "'", o,
					PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, IPPDiagnostics.ISSUE__NOT_ON_PATH);

			}
			// only report unresolved if no raw (since if not found adjusted, error is reported as warning)
			if(searchResult.getRaw().size() < 1) {
				// ... and finally, if there was neither a type nor a definition reference
				if(adapter.getResourceType() == null && adapter.getTargetObjectDescription() == null) {
					// Add unresolved info at resource level
					importedNames.addUnresolved(converter.toQualifiedName(resourceTypeName));
					String[] proposals = proposer.computeProposals(
						resourceTypeName, ppFinder.getExportedDescriptions(), searchPath, DEF_AND_TYPE);
					acceptor.acceptError(
						"Unknown resource type: '" + resourceTypeName + "'", o,
						PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR, //
						proposalIssue(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE, proposals), //
						proposals);
				}
			}
		}
	}

	private void _link(VariableExpression o, PPImportedNamesAdapter importedNames, IMessageAcceptor acceptor) {
		// a definition of a variable (as opposed to a reference) is a leftExpr in an assignment expression
		if(o.eContainer().eClass() == PPPackage.Literals.ASSIGNMENT_EXPRESSION &&
				PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR == o.eContainingFeature())
			return; // is a definition

		boolean qualified = false;
		boolean global = false;
		QualifiedName qName = null;
		SearchResult searchResult = null;
		boolean existsAdjusted = false; // variable found as stated
		boolean existsOutside = false; // if not found, reflects if found outside search path

		try {
			qName = converter.toQualifiedName(o.getVarName());
			qualified = qName.getSegmentCount() > 1;
			global = qName.getFirstSegment().length() == 0;
			searchResult = ppFinder.findVariable(o, qName, importedNames);
			existsAdjusted = searchResult.getAdjusted().size() > 0;
			existsOutside = existsAdjusted
					? true
					: searchResult.getRaw().size() > 0;
		}
		catch(IllegalArgumentException iae) {
			// Can happen if there is something seriously wrong with the qualified name, should be caught by
			// validation - just ignore it here
			return;
		}
		IValidationAdvisor advisor = advisor();

		boolean mustExist = true;
		if(qualified && global) { // && !(existsAdjusted || existsOutside)) {
			// TODO: Possible future improvement when more global variables are known.
			// if reported as error now, almost all global variables would be flagged as errors.
			// Future enhancement could warn about those that are not found (resolved at runtime).
			mustExist = false;
		}
		if(mustExist) {
			if(!(existsAdjusted || existsOutside)) {
				// found nowhere
				if(qualified || advisor.unqualifiedVariables().isWarningOrError()) {
					StringBuilder message = new StringBuilder();
					message.append(qualified
							? "Unknown variable: '"
							: "Unqualified and Unknown variable: '");
					message.append(o.getVarName());
					message.append("'");

					if(advisor.unqualifiedVariables() == ValidationPreference.WARNING)
						acceptor.acceptWarning(
							message.toString(), o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME,
							IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
					else
						acceptor.acceptError(
							message.toString(), o, PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME,
							IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
				}
			}
			else if(!existsAdjusted && existsOutside) {
				// found outside
				if(qualified || advisor.unqualifiedVariables().isWarningOrError())
					acceptor.acceptWarning(
						"Found outside current search path variable: '" + o.getVarName() + "'", o,
						PPPackage.Literals.VARIABLE_EXPRESSION__VAR_NAME, IPPDiagnostics.ISSUE__NOT_ON_PATH);
			}
		}
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
				String className = stringConstantEvaluator.doToString(pe);
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
							acceptor.acceptWarning(
								"Found outside current search path: '" + className + "'", o,
								PPPackage.Literals.PARAMETERIZED_EXPRESSION__PARAMETERS, parameterIndex,
								IPPDiagnostics.ISSUE__NOT_ON_PATH);
						}
						else {
							// not found
							// record unresolved name at resource level
							importedNames.addUnresolved(converter.toQualifiedName(className));

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
					}
				}
				else {
					// warning or error depending on if this is a reasonable class reference expr or not
					if(canBeAClassReference(pe)) {
						acceptor.acceptWarning(
							"Can not determine until runtime if this is valid class reference", //
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
				String className = stringConstantEvaluator.doToString(pe);
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
							importedNames.addUnresolved(converter.toQualifiedName(className));

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
					}
				}
				else {
					// warning or error depending on if this is a reasonable class reference expr or not
					String msg = null;
					boolean error = false;
					if(canBeAClassReference(pe)) {
						msg = "Can not determine until runtime if this is valid class reference";
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
				if(searchResult.getAdjusted().size() > 0 || searchResult.getRaw().size() > 0) {
					internalLinkFunctionArguments(
						name, (LiteralNameOrReference) s, statements, i, importedNames, acceptor);
					if(searchResult.getAdjusted().size() < 1)
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

				case PPPackage.HOST_CLASS_DEFINITION: {
					_link((HostClassDefinition) o, importedNames, acceptor);
					internalLinkUnparenthesisedCall(((HostClassDefinition) o).getStatements(), importedNames, acceptor);
					break;
				}
			}
		}
		if(tracer.isTracing())
			tracer.trace("}");

	}

	/**
	 * Surgically remove all disqualified descriptions (those that are HostClass and a container
	 * of the given object 'o'.
	 * 
	 * @param descs
	 * @param o
	 */
	private void removeDisqualifiedContainers(List<IEObjectDescription> descs, Expression o) {
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			if(x.getEClass() == PPPackage.Literals.DEFINITION || !isParent(x, o))
				continue;
			litor.remove();
		}
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
