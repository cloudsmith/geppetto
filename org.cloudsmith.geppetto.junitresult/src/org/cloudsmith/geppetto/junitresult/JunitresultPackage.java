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
package org.cloudsmith.geppetto.junitresult;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultFactory
 * @model kind="package"
 * @generated
 */
public interface JunitresultPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl <em>Testsuite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestsuite()
		 * @generated
		 */
		EClass TESTSUITE = eINSTANCE.getTestsuite();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTSUITE__PROPERTIES = eINSTANCE.getTestsuite_Properties();

		/**
		 * The meta object literal for the '<em><b>Testcases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTSUITE__TESTCASES = eINSTANCE.getTestsuite_Testcases();

		/**
		 * The meta object literal for the '<em><b>System out</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__SYSTEM_OUT = eINSTANCE.getTestsuite_System_out();

		/**
		 * The meta object literal for the '<em><b>System err</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__SYSTEM_ERR = eINSTANCE.getTestsuite_System_err();

		/**
		 * The meta object literal for the '<em><b>Hostname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__HOSTNAME = eINSTANCE.getTestsuite_Hostname();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__TIMESTAMP = eINSTANCE.getTestsuite_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__TIME = eINSTANCE.getTestsuite_Time();

		/**
		 * The meta object literal for the '<em><b>Testsuites</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTSUITE__TESTSUITES = eINSTANCE.getTestsuite_Testsuites();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__ID = eINSTANCE.getTestsuite_Id();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__PACKAGE = eINSTANCE.getTestsuite_Package();

		/**
		 * The meta object literal for the '<em><b>Disabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__DISABLED = eINSTANCE.getTestsuite_Disabled();

		/**
		 * The meta object literal for the '<em><b>Skipped</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTSUITE__SKIPPED = eINSTANCE.getTestsuite_Skipped();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.PropertyImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl <em>Testcase</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestcase()
		 * @generated
		 */
		EClass TESTCASE = eINSTANCE.getTestcase();

		/**
		 * The meta object literal for the '<em><b>Negative Result</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTCASE__NEGATIVE_RESULT = eINSTANCE.getTestcase_NegativeResult();

		/**
		 * The meta object literal for the '<em><b>Classname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTCASE__CLASSNAME = eINSTANCE.getTestcase_Classname();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTCASE__NAME = eINSTANCE.getTestcase_Name();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTCASE__TIME = eINSTANCE.getTestcase_Time();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.ErrorImpl <em>Error</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.ErrorImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getError()
		 * @generated
		 */
		EClass ERROR = eINSTANCE.getError();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.FailureImpl <em>Failure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.FailureImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getFailure()
		 * @generated
		 */
		EClass FAILURE = eINSTANCE.getFailure();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.NegativeResultImpl <em>Negative Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.NegativeResultImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getNegativeResult()
		 * @generated
		 */
		EClass NEGATIVE_RESULT = eINSTANCE.getNegativeResult();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NEGATIVE_RESULT__MESSAGE = eINSTANCE.getNegativeResult_Message();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NEGATIVE_RESULT__TYPE = eINSTANCE.getNegativeResult_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NEGATIVE_RESULT__VALUE = eINSTANCE.getNegativeResult_Value();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestrunImpl <em>Testrun</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.TestrunImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestrun()
		 * @generated
		 */
		EClass TESTRUN = eINSTANCE.getTestrun();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTRUN__PROJECT = eINSTANCE.getTestrun_Project();

		/**
		 * The meta object literal for the '<em><b>Started</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTRUN__STARTED = eINSTANCE.getTestrun_Started();

		/**
		 * The meta object literal for the '<em><b>Ignored</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TESTRUN__IGNORED = eINSTANCE.getTestrun_Ignored();

		/**
		 * The meta object literal for the '<em><b>Testsuites</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTRUN__TESTSUITES = eINSTANCE.getTestrun_Testsuites();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.AbstractAggregatedTestImpl
		 * <em>Abstract Aggregated Test</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.AbstractAggregatedTestImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getAbstractAggregatedTest()
		 * @generated
		 */
		EClass ABSTRACT_AGGREGATED_TEST = eINSTANCE.getAbstractAggregatedTest();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ABSTRACT_AGGREGATED_TEST__NAME = eINSTANCE.getAbstractAggregatedTest_Name();

		/**
		 * The meta object literal for the '<em><b>Tests</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ABSTRACT_AGGREGATED_TEST__TESTS = eINSTANCE.getAbstractAggregatedTest_Tests();

		/**
		 * The meta object literal for the '<em><b>Failures</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ABSTRACT_AGGREGATED_TEST__FAILURES = eINSTANCE.getAbstractAggregatedTest_Failures();

		/**
		 * The meta object literal for the '<em><b>Errors</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ABSTRACT_AGGREGATED_TEST__ERRORS = eINSTANCE.getAbstractAggregatedTest_Errors();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl <em>Testsuites</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestsuites()
		 * @generated
		 */
		EClass TESTSUITES = eINSTANCE.getTestsuites();

		/**
		 * The meta object literal for the '<em><b>Testsuites</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TESTSUITES__TESTSUITES = eINSTANCE.getTestsuites_Testsuites();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.JunitResult <em>Junit Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.JunitResult
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getJunitResult()
		 * @generated
		 */
		EClass JUNIT_RESULT = eINSTANCE.getJunitResult();

		/**
		 * The meta object literal for the '{@link org.cloudsmith.geppetto.junitresult.impl.SkippedImpl <em>Skipped</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.cloudsmith.geppetto.junitresult.impl.SkippedImpl
		 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getSkipped()
		 * @generated
		 */
		EClass SKIPPED = eINSTANCE.getSkipped();

	}

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "junitresult";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.cloudsmith.org/geppetto/1.0.0/Junitresult";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "junitresult";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	JunitresultPackage eINSTANCE = org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.JunitResult <em>Junit Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.JunitResult
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getJunitResult()
	 * @generated
	 */
	int JUNIT_RESULT = 9;

	/**
	 * The number of structural features of the '<em>Junit Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int JUNIT_RESULT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.AbstractAggregatedTestImpl <em>Abstract Aggregated Test</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.AbstractAggregatedTestImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getAbstractAggregatedTest()
	 * @generated
	 */
	int ABSTRACT_AGGREGATED_TEST = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_AGGREGATED_TEST__NAME = JUNIT_RESULT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_AGGREGATED_TEST__TESTS = JUNIT_RESULT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Failures</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_AGGREGATED_TEST__FAILURES = JUNIT_RESULT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_AGGREGATED_TEST__ERRORS = JUNIT_RESULT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Abstract Aggregated Test</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT = JUNIT_RESULT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl <em>Testsuite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestsuite()
	 * @generated
	 */
	int TESTSUITE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__NAME = ABSTRACT_AGGREGATED_TEST__NAME;

	/**
	 * The feature id for the '<em><b>Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__TESTS = ABSTRACT_AGGREGATED_TEST__TESTS;

	/**
	 * The feature id for the '<em><b>Failures</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__FAILURES = ABSTRACT_AGGREGATED_TEST__FAILURES;

	/**
	 * The feature id for the '<em><b>Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__ERRORS = ABSTRACT_AGGREGATED_TEST__ERRORS;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__PROPERTIES = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Testcases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__TESTCASES = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>System out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__SYSTEM_OUT = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>System err</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__SYSTEM_ERR = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__HOSTNAME = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__TIMESTAMP = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__TIME = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Testsuites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__TESTSUITES = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__ID = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__PACKAGE = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Disabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__DISABLED = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Skipped</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE__SKIPPED = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Testsuite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITE_FEATURE_COUNT = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.PropertyImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl <em>Testcase</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestcase()
	 * @generated
	 */
	int TESTCASE = 2;

	/**
	 * The feature id for the '<em><b>Negative Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTCASE__NEGATIVE_RESULT = 0;

	/**
	 * The feature id for the '<em><b>Classname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTCASE__CLASSNAME = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTCASE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTCASE__TIME = 3;

	/**
	 * The number of structural features of the '<em>Testcase</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTCASE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.NegativeResultImpl <em>Negative Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.NegativeResultImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getNegativeResult()
	 * @generated
	 */
	int NEGATIVE_RESULT = 5;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_RESULT__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_RESULT__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_RESULT__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Negative Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_RESULT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.ErrorImpl <em>Error</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.ErrorImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getError()
	 * @generated
	 */
	int ERROR = 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__MESSAGE = NEGATIVE_RESULT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__TYPE = NEGATIVE_RESULT__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR__VALUE = NEGATIVE_RESULT__VALUE;

	/**
	 * The number of structural features of the '<em>Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_COUNT = NEGATIVE_RESULT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.FailureImpl <em>Failure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.FailureImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getFailure()
	 * @generated
	 */
	int FAILURE = 4;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAILURE__MESSAGE = NEGATIVE_RESULT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAILURE__TYPE = NEGATIVE_RESULT__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAILURE__VALUE = NEGATIVE_RESULT__VALUE;

	/**
	 * The number of structural features of the '<em>Failure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAILURE_FEATURE_COUNT = NEGATIVE_RESULT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestrunImpl <em>Testrun</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.TestrunImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestrun()
	 * @generated
	 */
	int TESTRUN = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__NAME = ABSTRACT_AGGREGATED_TEST__NAME;

	/**
	 * The feature id for the '<em><b>Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__TESTS = ABSTRACT_AGGREGATED_TEST__TESTS;

	/**
	 * The feature id for the '<em><b>Failures</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__FAILURES = ABSTRACT_AGGREGATED_TEST__FAILURES;

	/**
	 * The feature id for the '<em><b>Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__ERRORS = ABSTRACT_AGGREGATED_TEST__ERRORS;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__PROJECT = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Started</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__STARTED = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ignored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__IGNORED = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Testsuites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN__TESTSUITES = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Testrun</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTRUN_FEATURE_COUNT = ABSTRACT_AGGREGATED_TEST_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl <em>Testsuites</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getTestsuites()
	 * @generated
	 */
	int TESTSUITES = 8;

	/**
	 * The feature id for the '<em><b>Testsuites</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITES__TESTSUITES = JUNIT_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Testsuites</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TESTSUITES_FEATURE_COUNT = JUNIT_RESULT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.cloudsmith.geppetto.junitresult.impl.SkippedImpl <em>Skipped</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.junitresult.impl.SkippedImpl
	 * @see org.cloudsmith.geppetto.junitresult.impl.JunitresultPackageImpl#getSkipped()
	 * @generated
	 */
	int SKIPPED = 10;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SKIPPED__MESSAGE = NEGATIVE_RESULT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SKIPPED__TYPE = NEGATIVE_RESULT__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SKIPPED__VALUE = NEGATIVE_RESULT__VALUE;

	/**
	 * The number of structural features of the '<em>Skipped</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SKIPPED_FEATURE_COUNT = NEGATIVE_RESULT_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest <em>Abstract Aggregated Test</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Abstract Aggregated Test</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest
	 * @generated
	 */
	EClass getAbstractAggregatedTest();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getErrors <em>Errors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Errors</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getErrors()
	 * @see #getAbstractAggregatedTest()
	 * @generated
	 */
	EAttribute getAbstractAggregatedTest_Errors();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getFailures <em>Failures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Failures</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getFailures()
	 * @see #getAbstractAggregatedTest()
	 * @generated
	 */
	EAttribute getAbstractAggregatedTest_Failures();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getName()
	 * @see #getAbstractAggregatedTest()
	 * @generated
	 */
	EAttribute getAbstractAggregatedTest_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getTests <em>Tests</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Tests</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getTests()
	 * @see #getAbstractAggregatedTest()
	 * @generated
	 */
	EAttribute getAbstractAggregatedTest_Tests();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Error <em>Error</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Error
	 * @generated
	 */
	EClass getError();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Failure <em>Failure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Failure</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Failure
	 * @generated
	 */
	EClass getFailure();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.JunitResult <em>Junit Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Junit Result</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.JunitResult
	 * @generated
	 */
	EClass getJunitResult();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	JunitresultFactory getJunitresultFactory();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.NegativeResult <em>Negative Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Negative Result</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.NegativeResult
	 * @generated
	 */
	EClass getNegativeResult();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.NegativeResult#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.NegativeResult#getMessage()
	 * @see #getNegativeResult()
	 * @generated
	 */
	EAttribute getNegativeResult_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.NegativeResult#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.NegativeResult#getType()
	 * @see #getNegativeResult()
	 * @generated
	 */
	EAttribute getNegativeResult_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.NegativeResult#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.NegativeResult#getValue()
	 * @see #getNegativeResult()
	 * @generated
	 */
	EAttribute getNegativeResult_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Skipped <em>Skipped</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Skipped</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Skipped
	 * @generated
	 */
	EClass getSkipped();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Testcase <em>Testcase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Testcase</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testcase
	 * @generated
	 */
	EClass getTestcase();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testcase#getClassname <em>Classname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Classname</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testcase#getClassname()
	 * @see #getTestcase()
	 * @generated
	 */
	EAttribute getTestcase_Classname();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testcase#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testcase#getName()
	 * @see #getTestcase()
	 * @generated
	 */
	EAttribute getTestcase_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.cloudsmith.geppetto.junitresult.Testcase#getNegativeResult
	 * <em>Negative Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Negative Result</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testcase#getNegativeResult()
	 * @see #getTestcase()
	 * @generated
	 */
	EReference getTestcase_NegativeResult();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testcase#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testcase#getTime()
	 * @see #getTestcase()
	 * @generated
	 */
	EAttribute getTestcase_Time();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Testrun <em>Testrun</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Testrun</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testrun
	 * @generated
	 */
	EClass getTestrun();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testrun#getIgnored <em>Ignored</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ignored</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testrun#getIgnored()
	 * @see #getTestrun()
	 * @generated
	 */
	EAttribute getTestrun_Ignored();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testrun#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testrun#getProject()
	 * @see #getTestrun()
	 * @generated
	 */
	EAttribute getTestrun_Project();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testrun#getStarted <em>Started</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Started</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testrun#getStarted()
	 * @see #getTestrun()
	 * @generated
	 */
	EAttribute getTestrun_Started();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.junitresult.Testrun#getTestsuites
	 * <em>Testsuites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Testsuites</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testrun#getTestsuites()
	 * @see #getTestrun()
	 * @generated
	 */
	EReference getTestrun_Testsuites();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Testsuite <em>Testsuite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Testsuite</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite
	 * @generated
	 */
	EClass getTestsuite();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getDisabled <em>Disabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Disabled</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getDisabled()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Disabled();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getHostname <em>Hostname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Hostname</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getHostname()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Hostname();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getId()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getPackage()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Package();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getProperties
	 * <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getProperties()
	 * @see #getTestsuite()
	 * @generated
	 */
	EReference getTestsuite_Properties();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSkipped <em>Skipped</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Skipped</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getSkipped()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Skipped();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_err <em>System err</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>System err</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_err()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_System_err();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_out <em>System out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>System out</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_out()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_System_out();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTestcases
	 * <em>Testcases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Testcases</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getTestcases()
	 * @see #getTestsuite()
	 * @generated
	 */
	EReference getTestsuite_Testcases();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTestsuites
	 * <em>Testsuites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Testsuites</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getTestsuites()
	 * @see #getTestsuite()
	 * @generated
	 */
	EReference getTestsuite_Testsuites();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getTime()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Time();

	/**
	 * Returns the meta object for the attribute '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuite#getTimestamp()
	 * @see #getTestsuite()
	 * @generated
	 */
	EAttribute getTestsuite_Timestamp();

	/**
	 * Returns the meta object for class '{@link org.cloudsmith.geppetto.junitresult.Testsuites <em>Testsuites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Testsuites</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuites
	 * @generated
	 */
	EClass getTestsuites();

	/**
	 * Returns the meta object for the containment reference list '{@link org.cloudsmith.geppetto.junitresult.Testsuites#getTestsuites
	 * <em>Testsuites</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Testsuites</em>'.
	 * @see org.cloudsmith.geppetto.junitresult.Testsuites#getTestsuites()
	 * @see #getTestsuites()
	 * @generated
	 */
	EReference getTestsuites_Testsuites();

} // JunitresultPackage
