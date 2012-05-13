/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.junitresult.impl;

import org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest;
import org.cloudsmith.geppetto.junitresult.Failure;
import org.cloudsmith.geppetto.junitresult.JunitresultFactory;
import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Property;
import org.cloudsmith.geppetto.junitresult.Skipped;
import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class JunitresultPackageImpl extends EPackageImpl implements JunitresultPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testsuiteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testcaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass failureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass negativeResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testrunEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass abstractAggregatedTestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testsuitesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass junitResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass skippedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link JunitresultPackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static JunitresultPackage init() {
		if(isInited)
			return (JunitresultPackage) EPackage.Registry.INSTANCE.getEPackage(JunitresultPackage.eNS_URI);

		// Obtain or create and register package
		JunitresultPackageImpl theJunitresultPackage = (JunitresultPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof JunitresultPackageImpl
				? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new JunitresultPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theJunitresultPackage.createPackageContents();

		// Initialize created meta-data
		theJunitresultPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theJunitresultPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(JunitresultPackage.eNS_URI, theJunitresultPackage);
		return theJunitresultPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private JunitresultPackageImpl() {
		super(eNS_URI, JunitresultFactory.eINSTANCE);
	}

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if(isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		testsuiteEClass = createEClass(TESTSUITE);
		createEReference(testsuiteEClass, TESTSUITE__PROPERTIES);
		createEReference(testsuiteEClass, TESTSUITE__TESTCASES);
		createEAttribute(testsuiteEClass, TESTSUITE__SYSTEM_OUT);
		createEAttribute(testsuiteEClass, TESTSUITE__SYSTEM_ERR);
		createEAttribute(testsuiteEClass, TESTSUITE__HOSTNAME);
		createEAttribute(testsuiteEClass, TESTSUITE__TIMESTAMP);
		createEAttribute(testsuiteEClass, TESTSUITE__TIME);
		createEReference(testsuiteEClass, TESTSUITE__TESTSUITES);
		createEAttribute(testsuiteEClass, TESTSUITE__ID);
		createEAttribute(testsuiteEClass, TESTSUITE__PACKAGE);
		createEAttribute(testsuiteEClass, TESTSUITE__DISABLED);
		createEAttribute(testsuiteEClass, TESTSUITE__SKIPPED);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__NAME);
		createEAttribute(propertyEClass, PROPERTY__VALUE);

		testcaseEClass = createEClass(TESTCASE);
		createEReference(testcaseEClass, TESTCASE__SKIPPED);
		createEAttribute(testcaseEClass, TESTCASE__NAME);
		createEAttribute(testcaseEClass, TESTCASE__CLASSNAME);
		createEAttribute(testcaseEClass, TESTCASE__TIME);
		createEAttribute(testcaseEClass, TESTCASE__SYSTEM_OUT);
		createEAttribute(testcaseEClass, TESTCASE__SYSTEM_ERR);
		createEAttribute(testcaseEClass, TESTCASE__STATUS);
		createEAttribute(testcaseEClass, TESTCASE__ASSERTIONS);
		createEReference(testcaseEClass, TESTCASE__FAILURES);
		createEReference(testcaseEClass, TESTCASE__ERRORS);

		errorEClass = createEClass(ERROR);

		failureEClass = createEClass(FAILURE);

		negativeResultEClass = createEClass(NEGATIVE_RESULT);
		createEAttribute(negativeResultEClass, NEGATIVE_RESULT__MESSAGE);
		createEAttribute(negativeResultEClass, NEGATIVE_RESULT__TYPE);
		createEAttribute(negativeResultEClass, NEGATIVE_RESULT__VALUE);

		testrunEClass = createEClass(TESTRUN);
		createEAttribute(testrunEClass, TESTRUN__PROJECT);
		createEAttribute(testrunEClass, TESTRUN__STARTED);
		createEAttribute(testrunEClass, TESTRUN__IGNORED);
		createEReference(testrunEClass, TESTRUN__TESTSUITES);

		abstractAggregatedTestEClass = createEClass(ABSTRACT_AGGREGATED_TEST);
		createEAttribute(abstractAggregatedTestEClass, ABSTRACT_AGGREGATED_TEST__NAME);
		createEAttribute(abstractAggregatedTestEClass, ABSTRACT_AGGREGATED_TEST__TESTS);
		createEAttribute(abstractAggregatedTestEClass, ABSTRACT_AGGREGATED_TEST__FAILURES);
		createEAttribute(abstractAggregatedTestEClass, ABSTRACT_AGGREGATED_TEST__ERRORS);

		testsuitesEClass = createEClass(TESTSUITES);
		createEReference(testsuitesEClass, TESTSUITES__TESTSUITES);
		createEAttribute(testsuitesEClass, TESTSUITES__TIME);
		createEAttribute(testsuitesEClass, TESTSUITES__DISABLED);

		junitResultEClass = createEClass(JUNIT_RESULT);

		skippedEClass = createEClass(SKIPPED);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAbstractAggregatedTest() {
		return abstractAggregatedTestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractAggregatedTest_Errors() {
		return (EAttribute) abstractAggregatedTestEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractAggregatedTest_Failures() {
		return (EAttribute) abstractAggregatedTestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractAggregatedTest_Name() {
		return (EAttribute) abstractAggregatedTestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractAggregatedTest_Tests() {
		return (EAttribute) abstractAggregatedTestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getError() {
		return errorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFailure() {
		return failureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getJunitResult() {
		return junitResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public JunitresultFactory getJunitresultFactory() {
		return (JunitresultFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNegativeResult() {
		return negativeResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNegativeResult_Message() {
		return (EAttribute) negativeResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNegativeResult_Type() {
		return (EAttribute) negativeResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNegativeResult_Value() {
		return (EAttribute) negativeResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProperty_Name() {
		return (EAttribute) propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProperty_Value() {
		return (EAttribute) propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSkipped() {
		return skippedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestcase() {
		return testcaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_Assertions() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_Classname() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestcase_Errors() {
		return (EReference) testcaseEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestcase_Failures() {
		return (EReference) testcaseEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_Name() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestcase_Skipped() {
		return (EReference) testcaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_Status() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_System_err() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_System_out() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestcase_Time() {
		return (EAttribute) testcaseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestrun() {
		return testrunEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestrun_Ignored() {
		return (EAttribute) testrunEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestrun_Project() {
		return (EAttribute) testrunEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestrun_Started() {
		return (EAttribute) testrunEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestrun_Testsuites() {
		return (EReference) testrunEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestsuite() {
		return testsuiteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Disabled() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Hostname() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Id() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Package() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestsuite_Properties() {
		return (EReference) testsuiteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Skipped() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_System_err() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_System_out() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestsuite_Testcases() {
		return (EReference) testsuiteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestsuite_Testsuites() {
		return (EReference) testsuiteEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Time() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuite_Timestamp() {
		return (EAttribute) testsuiteEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestsuites() {
		return testsuitesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuites_Disabled() {
		return (EAttribute) testsuitesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestsuites_Testsuites() {
		return (EReference) testsuitesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestsuites_Time() {
		return (EAttribute) testsuitesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if(isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		testsuiteEClass.getESuperTypes().add(this.getAbstractAggregatedTest());
		errorEClass.getESuperTypes().add(this.getNegativeResult());
		failureEClass.getESuperTypes().add(this.getNegativeResult());
		testrunEClass.getESuperTypes().add(this.getAbstractAggregatedTest());
		abstractAggregatedTestEClass.getESuperTypes().add(this.getJunitResult());
		testsuitesEClass.getESuperTypes().add(this.getAbstractAggregatedTest());
		skippedEClass.getESuperTypes().add(this.getNegativeResult());

		// Initialize classes and features; add operations and parameters
		initEClass(
			testsuiteEClass, Testsuite.class, "Testsuite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getTestsuite_Properties(), this.getProperty(), null, "properties", null, 0, -1, Testsuite.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(
			getTestsuite_Testcases(), this.getTestcase(), null, "testcases", null, 0, -1, Testsuite.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_System_out(), ecorePackage.getEString(), "system_out", null, 0, 1, Testsuite.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_System_err(), ecorePackage.getEString(), "system_err", null, 0, 1, Testsuite.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Hostname(), ecorePackage.getEString(), "hostname", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Timestamp(), ecorePackage.getEDate(), "timestamp", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Time(), ecorePackage.getEDouble(), "time", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTestsuite_Testsuites(), this.getTestsuite(), null, "testsuites", null, 0, -1, Testsuite.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Id(), ecorePackage.getEInt(), "id", null, 0, 1, Testsuite.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Package(), ecorePackage.getEString(), "package", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Disabled(), ecorePackage.getEInt(), "disabled", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuite_Skipped(), ecorePackage.getEInt(), "skipped", null, 0, 1, Testsuite.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getProperty_Name(), ecorePackage.getEString(), "name", null, 1, 1, Property.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getProperty_Value(), ecorePackage.getEString(), "value", null, 1, 1, Property.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testcaseEClass, Testcase.class, "Testcase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getTestcase_Skipped(), this.getSkipped(), null, "skipped", null, 0, 1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(
			getTestcase_Name(), ecorePackage.getEString(), "name", null, 1, 1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_Classname(), ecorePackage.getEString(), "classname", null, 0, 1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_Time(), ecorePackage.getEDouble(), "time", null, 0, 1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_System_out(), ecorePackage.getEString(), "system_out", null, 0, -1, Testcase.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_System_err(), ecorePackage.getEString(), "system_err", null, 0, -1, Testcase.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_Status(), ecorePackage.getEString(), "status", null, 0, 1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestcase_Assertions(), ecorePackage.getEString(), "assertions", null, 0, 1, Testcase.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTestcase_Failures(), this.getFailure(), null, "failures", null, 0, -1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(
			getTestcase_Errors(), this.getError(), null, "errors", null, 0, -1, Testcase.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(
			errorEClass, org.cloudsmith.geppetto.junitresult.Error.class, "Error", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(failureEClass, Failure.class, "Failure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(
			negativeResultEClass, NegativeResult.class, "NegativeResult", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getNegativeResult_Message(), ecorePackage.getEString(), "message", null, 0, 1, NegativeResult.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getNegativeResult_Type(), ecorePackage.getEString(), "type", null, 0, 1, NegativeResult.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getNegativeResult_Value(), ecorePackage.getEString(), "value", null, 0, 1, NegativeResult.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testrunEClass, Testrun.class, "Testrun", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getTestrun_Project(), ecorePackage.getEString(), "project", null, 1, 1, Testrun.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestrun_Started(), ecorePackage.getEInt(), "started", null, 1, 1, Testrun.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestrun_Ignored(), ecorePackage.getEInt(), "ignored", null, 1, 1, Testrun.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(
			getTestrun_Testsuites(), this.getTestsuite(), null, "testsuites", null, 0, -1, Testrun.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(
			abstractAggregatedTestEClass, AbstractAggregatedTest.class, "AbstractAggregatedTest", IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(
			getAbstractAggregatedTest_Name(), ecorePackage.getEString(), "name", null, 1, 1,
			AbstractAggregatedTest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getAbstractAggregatedTest_Tests(), ecorePackage.getEInt(), "tests", null, 1, 1,
			AbstractAggregatedTest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getAbstractAggregatedTest_Failures(), ecorePackage.getEInt(), "failures", null, 0, 1,
			AbstractAggregatedTest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getAbstractAggregatedTest_Errors(), ecorePackage.getEInt(), "errors", null, 0, 1,
			AbstractAggregatedTest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			testsuitesEClass, Testsuites.class, "Testsuites", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(
			getTestsuites_Testsuites(), this.getTestsuite(), null, "testsuites", null, 0, -1, Testsuites.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuites_Time(), ecorePackage.getEDouble(), "time", null, 0, 1, Testsuites.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(
			getTestsuites_Disabled(), ecorePackage.getEInt(), "disabled", null, 0, 1, Testsuites.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(
			junitResultEClass, JunitResult.class, "JunitResult", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(skippedEClass, Skipped.class, "Skipped", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // JunitresultPackageImpl
