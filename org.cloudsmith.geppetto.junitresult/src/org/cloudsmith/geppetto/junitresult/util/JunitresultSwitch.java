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
package org.cloudsmith.geppetto.junitresult.util;

import org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest;
import org.cloudsmith.geppetto.junitresult.Failure;
import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Property;
import org.cloudsmith.geppetto.junitresult.Skipped;
import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage
 * @generated
 */
public class JunitresultSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static JunitresultPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JunitresultSwitch() {
		if(modelPackage == null) {
			modelPackage = JunitresultPackage.eINSTANCE;
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Aggregated Test</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Aggregated Test</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractAggregatedTest(AbstractAggregatedTest object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseError(org.cloudsmith.geppetto.junitresult.Error object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Failure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Failure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFailure(Failure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Junit Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Junit Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJunitResult(JunitResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Negative Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Negative Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNegativeResult(NegativeResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Skipped</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Skipped</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSkipped(Skipped object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Testcase</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Testcase</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestcase(Testcase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Testrun</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Testrun</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestrun(Testrun object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Testsuite</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Testsuite</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestsuite(Testsuite object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Testsuites</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Testsuites</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestsuites(Testsuites object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch(classifierID) {
			case JunitresultPackage.TESTSUITE: {
				Testsuite testsuite = (Testsuite) theEObject;
				T result = caseTestsuite(testsuite);
				if(result == null)
					result = caseAbstractAggregatedTest(testsuite);
				if(result == null)
					result = caseJunitResult(testsuite);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.PROPERTY: {
				Property property = (Property) theEObject;
				T result = caseProperty(property);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.TESTCASE: {
				Testcase testcase = (Testcase) theEObject;
				T result = caseTestcase(testcase);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.ERROR: {
				org.cloudsmith.geppetto.junitresult.Error error = (org.cloudsmith.geppetto.junitresult.Error) theEObject;
				T result = caseError(error);
				if(result == null)
					result = caseNegativeResult(error);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.FAILURE: {
				Failure failure = (Failure) theEObject;
				T result = caseFailure(failure);
				if(result == null)
					result = caseNegativeResult(failure);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.NEGATIVE_RESULT: {
				NegativeResult negativeResult = (NegativeResult) theEObject;
				T result = caseNegativeResult(negativeResult);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.TESTRUN: {
				Testrun testrun = (Testrun) theEObject;
				T result = caseTestrun(testrun);
				if(result == null)
					result = caseAbstractAggregatedTest(testrun);
				if(result == null)
					result = caseJunitResult(testrun);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST: {
				AbstractAggregatedTest abstractAggregatedTest = (AbstractAggregatedTest) theEObject;
				T result = caseAbstractAggregatedTest(abstractAggregatedTest);
				if(result == null)
					result = caseJunitResult(abstractAggregatedTest);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.TESTSUITES: {
				Testsuites testsuites = (Testsuites) theEObject;
				T result = caseTestsuites(testsuites);
				if(result == null)
					result = caseAbstractAggregatedTest(testsuites);
				if(result == null)
					result = caseJunitResult(testsuites);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.JUNIT_RESULT: {
				JunitResult junitResult = (JunitResult) theEObject;
				T result = caseJunitResult(junitResult);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JunitresultPackage.SKIPPED: {
				Skipped skipped = (Skipped) theEObject;
				T result = caseSkipped(skipped);
				if(result == null)
					result = caseNegativeResult(skipped);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

} // JunitresultSwitch
