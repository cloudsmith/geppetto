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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;

/**
 * An interface for accepting messages.
 * 
 */
public interface IMessageAcceptor {

	public static final int INSIGNIFICANT_INDEX = -1;

	/**
	 * <p>
	 * Generates an error for the given EObject feature location determined by 'feature of source (at index)'.
	 * </p>
	 * <p>
	 * Users of this class probably want to use the more specialized acceptError(...) and acceptWarning(...) methods.
	 * </p>
	 * <p>
	 * Example - if a 'naming convention violation' issue should be reported for the feature 'name' in 'Person'.
	 * </p>
	 * <p>
	 * <code>
	 * Person p = ...
	 * accept(Severity.ERROR, "name is wrong", p, ExamplePackage.Literals.PERSON__NAME,
	 * INSIGNIFICANT_INDEX, "myorg.NamingConventionViolation")
	 * </code>
	 * </p>
	 * 
	 * @param severity
	 *            the severity of the message {@link Severity#ERROR } or {@link Severity#WARNING}.
	 * @param message
	 *            the error message text
	 * @param source
	 *            the EObject container of the feature having an issue
	 * @param feature
	 *            the feature having an issue
	 * @param index
	 *            the index if feature is a list (or INSIGNIFICANT_INDEX (-1) for entire list, or no list)
	 * @param issueCode
	 *            the issue code (issue identifier)
	 * @param issueData
	 *            optional data associated with the issue
	 */
	public void accept(Severity severity, String message, EObject source, EStructuralFeature feature, int index,
			String issueCode, String... issueData);

	/**
	 * Generate an error message for the given location in text.
	 * 
	 * @param severity
	 * @param message
	 * @param source
	 * @param textOffset
	 * @param textLength
	 * @param issueCode
	 * @param issueData
	 */
	void accept(Severity severity, String message, EObject source, int textOffset, int textLength, String issueCode,
			String[] issueData);

	/**
	 * The same as calling {@link #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)} with
	 * with source.eContainer(), source.eContainingFeature(), {@link #indexOfSourceInParent(EObject)} instead of just source.
	 * source.eContainer(), so
	 * 
	 * @param severity
	 * @param message
	 * @param source
	 * @param issueCode
	 * @param issueData
	 */
	void accept(Severity severity, String message, EObject source, String issueCode, String... issueData);

	/**
	 * <p>
	 * Generates an error for the given {@link INode}
	 * </p>
	 * <p>
	 * Users of this class probably want to use the more specialized acceptError(...) and acceptWarning(...) methods.
	 * </p>
	 * 
	 * @param severity
	 *            the severity of the message {@link Severity#ERROR } or {@link Severity#WARNING}.
	 * @param message
	 *            the error message text
	 * @param node
	 *            the INode associated with the error
	 * @param issueCode
	 *            the issue code (issue identifier)
	 * @param issueData
	 *            optional data associated with the issue
	 */
	public void accept(Severity severity, String message, INode node, String issueCode, String... issueData);

	/**
	 * Generates an error message for EObject location.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptError(String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData);

	/**
	 * Generates an error for a location determined by 'feature of source'. <br/>
	 * The same as calling {@link #acceptError(String, EObject, EStructuralFeature, int, String, String...)} with {@link #INSIGNIFICANT_INDEX}.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 * 
	 */
	public void acceptError(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData);

	/**
	 * Generate an error message for the given location in text.
	 * 
	 * @param severity
	 * @param message
	 * @param source
	 * @param textOffset
	 * @param textLength
	 * @param issueCode
	 * @param issueData
	 */
	void acceptError(String message, EObject source, int textOffset, int textLength, String issueCode,
			String... issueData);

	/**
	 * Generates an error for a location determined by source's eContainer and eContainingFeature. <br/>
	 * The same as calling {@link #acceptError(String, EObject, EStructuralFeature, int, String, String...)} with source.eContainer(),
	 * source.eContainingFeature(), index instead of just source.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptError(String message, EObject source, int index, String issueCode, String... issueData);

	/**
	 * Generates an error for a location determined by source's eContainer and eContainingFeature.
	 * 
	 * The same as calling {@link #acceptError(String, EObject, EStructuralFeature, int, String, String...)} with source.eContainer(),
	 * source.eContainingFeature(), {@link #indexOfSourceInParent(EObject)} instead of just source.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptError(String message, EObject source, String issueCode, String... issueData);

	/**
	 * Generate error message based on INode location.
	 * 
	 * @see #accept(Severity, String, INode, String, String...)
	 */
	public void acceptError(String message, INode node, String issueCode, String... issueData);

	/**
	 * Generates a warning message for EObject location.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptWarning(String message, EObject source, EStructuralFeature feature, int index, String issueCode,
			String... issueData);

	/**
	 * Generates a warning for a location determined by 'feature of source'. <br/>
	 * The same as calling {@link #acceptWarning(String, EObject, EStructuralFeature, int, String, String...)} with {@link #INSIGNIFICANT_INDEX}.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptWarning(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData);

	/**
	 * Generate a warning message for the given location in text.
	 * 
	 * @param severity
	 * @param message
	 * @param source
	 * @param textOffset
	 * @param textLength
	 * @param issueCode
	 * @param issueData
	 */
	void acceptWarning(String message, EObject source, int textOffset, int textLength, String issueCode,
			String... issueData);

	/**
	 * Generates a warning for a location determined by source's eContainer and eContainingFeature. <br/>
	 * The same as calling {@link #acceptWarning(String, EObject, EStructuralFeature, int, String, String...)} with source.eContainer(),
	 * source.eContainingFeature(), index instead of just source.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptWarning(String message, EObject source, int index, String issueCode, String... issueData);

	/**
	 * Generates a warning for a location determined by source's eContainer and eContainingFeature.
	 * 
	 * The same as calling {@link #acceptWarning(String, EObject, EStructuralFeature, int, String, String...)} with source.eContainer(),
	 * source.eContainingFeature(), {@link #indexOfSourceInParent(EObject)} instead of just source.
	 * 
	 * @see #accept(Severity, String, EObject, EStructuralFeature, int, String, String...)
	 */
	public void acceptWarning(String message, EObject source, String issueCode, String... issueData);

	/**
	 * Generate warning message based on INode location.
	 * 
	 * @see #accept(Severity, String, INode, String, String...)
	 */
	public void acceptWarning(String message, INode node, String issueCode, String... issueData);

}
