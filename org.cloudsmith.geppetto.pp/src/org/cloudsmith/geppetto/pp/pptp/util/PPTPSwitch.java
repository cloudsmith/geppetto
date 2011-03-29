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
package org.cloudsmith.geppetto.pp.pptp.util;

import java.util.List;

import org.cloudsmith.geppetto.pp.pptp.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage
 * @generated
 */
public class PPTPSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PPTPPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PPTPSwitch() {
		if(modelPackage == null) {
			modelPackage = PPTPPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if(theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty()
					? defaultCase(theEObject)
					: doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch(classifierID) {
			case PPTPPackage.PUPPET_TARGET: {
				PuppetTarget puppetTarget = (PuppetTarget) theEObject;
				T result = casePuppetTarget(puppetTarget);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.TARGET_ENTRY: {
				TargetEntry targetEntry = (TargetEntry) theEObject;
				T result = caseTargetEntry(targetEntry);
				if(result == null)
					result = caseINamed(targetEntry);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.PUPPET_DISTRIBUTION: {
				PuppetDistribution puppetDistribution = (PuppetDistribution) theEObject;
				T result = casePuppetDistribution(puppetDistribution);
				if(result == null)
					result = caseTargetEntry(puppetDistribution);
				if(result == null)
					result = caseINamed(puppetDistribution);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.RUBY_DIRECTORY: {
				RubyDirectory rubyDirectory = (RubyDirectory) theEObject;
				T result = caseRubyDirectory(rubyDirectory);
				if(result == null)
					result = caseTargetEntry(rubyDirectory);
				if(result == null)
					result = caseINamed(rubyDirectory);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.PREDEFINED: {
				Predefined predefined = (Predefined) theEObject;
				T result = casePredefined(predefined);
				if(result == null)
					result = caseTargetEntry(predefined);
				if(result == null)
					result = caseINamed(predefined);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.FUNCTION: {
				Function function = (Function) theEObject;
				T result = caseFunction(function);
				if(result == null)
					result = caseTargetElement(function);
				if(result == null)
					result = caseIDocumented(function);
				if(result == null)
					result = caseINamed(function);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.TYPE: {
				Type type = (Type) theEObject;
				T result = caseType(type);
				if(result == null)
					result = caseTargetElement(type);
				if(result == null)
					result = caseINamed(type);
				if(result == null)
					result = caseIDocumented(type);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.IDOCUMENTED: {
				IDocumented iDocumented = (IDocumented) theEObject;
				T result = caseIDocumented(iDocumented);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.INAMED: {
				INamed iNamed = (INamed) theEObject;
				T result = caseINamed(iNamed);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.TARGET_ELEMENT: {
				TargetElement targetElement = (TargetElement) theEObject;
				T result = caseTargetElement(targetElement);
				if(result == null)
					result = caseINamed(targetElement);
				if(result == null)
					result = caseIDocumented(targetElement);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.PROPERTY: {
				Property property = (Property) theEObject;
				T result = caseProperty(property);
				if(result == null)
					result = caseTargetElement(property);
				if(result == null)
					result = caseINamed(property);
				if(result == null)
					result = caseIDocumented(property);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case PPTPPackage.PARAMETER: {
				Parameter parameter = (Parameter) theEObject;
				T result = caseParameter(parameter);
				if(result == null)
					result = caseTargetElement(parameter);
				if(result == null)
					result = caseINamed(parameter);
				if(result == null)
					result = caseIDocumented(parameter);
				if(result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Puppet Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Puppet Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePuppetTarget(PuppetTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Target Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Target Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTargetEntry(TargetEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Puppet Distribution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Puppet Distribution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePuppetDistribution(PuppetDistribution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ruby Directory</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ruby Directory</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRubyDirectory(RubyDirectory object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Predefined</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Predefined</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePredefined(Predefined object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseType(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDocumented</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDocumented</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDocumented(IDocumented object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamed(INamed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Target Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Target Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTargetElement(TargetElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameter(Parameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //PPTPSwitch
