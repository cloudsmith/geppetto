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
package com.puppetlabs.geppetto.pp.pptp;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see com.puppetlabs.geppetto.pp.pptp.PPTPFactory
 * @model kind="package"
 * @generated
 */
public interface PPTPPackage extends EPackage {
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
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TargetEntryImpl <em>Target Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TargetEntryImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTargetEntry()
		 * @generated
		 */
		EClass TARGET_ENTRY = eINSTANCE.getTargetEntry();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TARGET_ENTRY__DESCRIPTION = eINSTANCE.getTargetEntry_Description();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TARGET_ENTRY__FUNCTIONS = eINSTANCE.getTargetEntry_Functions();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TARGET_ENTRY__TYPES = eINSTANCE.getTargetEntry_Types();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TARGET_ENTRY__VERSION = eINSTANCE.getTargetEntry_Version();

		/**
		 * The meta object literal for the '<em><b>Type Fragments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TARGET_ENTRY__TYPE_FRAGMENTS = eINSTANCE.getTargetEntry_TypeFragments();

		/**
		 * The meta object literal for the '<em><b>Meta Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TARGET_ENTRY__META_TYPE = eINSTANCE.getTargetEntry_MetaType();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TARGET_ENTRY__LABEL = eINSTANCE.getTargetEntry_Label();

		/**
		 * The meta object literal for the '<em><b>Meta Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TARGET_ENTRY__META_VARIABLES = eINSTANCE.getTargetEntry_MetaVariables();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.PuppetDistributionImpl <em>Puppet Distribution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PuppetDistributionImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getPuppetDistribution()
		 * @generated
		 */
		EClass PUPPET_DISTRIBUTION = eINSTANCE.getPuppetDistribution();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.FunctionImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>RValue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FUNCTION__RVALUE = eINSTANCE.getFunction_RValue();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.AbstractTypeImpl <em>Abstract Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.AbstractTypeImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getAbstractType()
		 * @generated
		 */
		EClass ABSTRACT_TYPE = eINSTANCE.getAbstractType();

		/**
		 * The meta object literal for the '<em><b>EReference0</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ABSTRACT_TYPE__EREFERENCE0 = eINSTANCE.getAbstractType_EReference0();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ABSTRACT_TYPE__PROPERTIES = eINSTANCE.getAbstractType_Properties();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ABSTRACT_TYPE__PARAMETERS = eINSTANCE.getAbstractType_Parameters();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Super Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE__SUPER_TYPE = eINSTANCE.getType_SuperType();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.MetaTypeImpl <em>Meta Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.MetaTypeImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getMetaType()
		 * @generated
		 */
		EClass META_TYPE = eINSTANCE.getMetaType();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeArgumentImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTypeArgument()
		 * @generated
		 */
		EClass TYPE_ARGUMENT = eINSTANCE.getTypeArgument();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_ARGUMENT__REQUIRED = eINSTANCE.getTypeArgument_Required();

		/**
		 * The meta object literal for the '<em><b>Namevar</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_ARGUMENT__NAMEVAR = eINSTANCE.getTypeArgument_Namevar();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl <em>Name Space</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getNameSpace()
		 * @generated
		 */
		EClass NAME_SPACE = eINSTANCE.getNameSpace();

		/**
		 * The meta object literal for the '<em><b>Reserved</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NAME_SPACE__RESERVED = eINSTANCE.getNameSpace_Reserved();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TPVariableImpl <em>TP Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TPVariableImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTPVariable()
		 * @generated
		 */
		EClass TP_VARIABLE = eINSTANCE.getTPVariable();

		/**
		 * The meta object literal for the '<em><b>Assignable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TP_VARIABLE__ASSIGNABLE = eINSTANCE.getTPVariable_Assignable();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TP_VARIABLE__PATTERN = eINSTANCE.getTPVariable_Pattern();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer <em>ITarget Element Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getITargetElementContainer()
		 * @generated
		 */
		EClass ITARGET_ELEMENT_CONTAINER = eINSTANCE.getITargetElementContainer();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITARGET_ELEMENT_CONTAINER__CONTENTS = eINSTANCE.getITargetElementContainer_Contents();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.MetaVariableImpl <em>Meta Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.MetaVariableImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getMetaVariable()
		 * @generated
		 */
		EClass META_VARIABLE = eINSTANCE.getMetaVariable();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.IDocumented <em>IDocumented</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.IDocumented
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getIDocumented()
		 * @generated
		 */
		EClass IDOCUMENTED = eINSTANCE.getIDocumented();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute IDOCUMENTED__DOCUMENTATION = eINSTANCE.getIDocumented_Documentation();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.INamed <em>INamed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.INamed
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getINamed()
		 * @generated
		 */
		EClass INAMED = eINSTANCE.getINamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INAMED__NAME = eINSTANCE.getINamed_Name();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TargetElementImpl <em>Target Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TargetElementImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTargetElement()
		 * @generated
		 */
		EClass TARGET_ELEMENT = eINSTANCE.getTargetElement();

		/**
		 * The meta object literal for the '<em><b>Deprecated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TARGET_ELEMENT__DEPRECATED = eINSTANCE.getTargetElement_Deprecated();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PropertyImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.ParameterImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeFragmentImpl <em>Type Fragment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeFragmentImpl
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTypeFragment()
		 * @generated
		 */
		EClass TYPE_FRAGMENT = eINSTANCE.getTypeFragment();

		/**
		 * The meta object literal for the '<em>File</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.io.File
		 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getFile()
		 * @generated
		 */
		EDataType FILE = eINSTANCE.getFile();

	}

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "pptp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.puppetlabs.com/geppetto/1.0.0/PPTP";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "pptp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	PPTPPackage eINSTANCE = com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer <em>ITarget Element Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getITargetElementContainer()
	 * @generated
	 */
	int ITARGET_ELEMENT_CONTAINER = 15;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITARGET_ELEMENT_CONTAINER__CONTENTS = 0;

	/**
	 * The number of structural features of the '<em>ITarget Element Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TargetEntryImpl <em>Target Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TargetEntryImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTargetEntry()
	 * @generated
	 */
	int TARGET_ENTRY = 0;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.PuppetDistributionImpl <em>Puppet Distribution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PuppetDistributionImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getPuppetDistribution()
	 * @generated
	 */
	int PUPPET_DISTRIBUTION = 1;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.IDocumented <em>IDocumented</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.IDocumented
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getIDocumented()
	 * @generated
	 */
	int IDOCUMENTED = 4;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.FunctionImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 2;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.INamed <em>INamed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.INamed
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getINamed()
	 * @generated
	 */
	int INAMED = 5;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__CONTENTS = ITARGET_ELEMENT_CONTAINER__CONTENTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__DESCRIPTION = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__FUNCTIONS = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__TYPES = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__VERSION = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Fragments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__TYPE_FRAGMENTS = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Meta Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__META_TYPE = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__LABEL = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Meta Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY__META_VARIABLES = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Target Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ENTRY_FEATURE_COUNT = ITARGET_ELEMENT_CONTAINER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__CONTENTS = TARGET_ENTRY__CONTENTS;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__DESCRIPTION = TARGET_ENTRY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__FUNCTIONS = TARGET_ENTRY__FUNCTIONS;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__TYPES = TARGET_ENTRY__TYPES;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__VERSION = TARGET_ENTRY__VERSION;

	/**
	 * The feature id for the '<em><b>Type Fragments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__TYPE_FRAGMENTS = TARGET_ENTRY__TYPE_FRAGMENTS;

	/**
	 * The feature id for the '<em><b>Meta Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__META_TYPE = TARGET_ENTRY__META_TYPE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__LABEL = TARGET_ENTRY__LABEL;

	/**
	 * The feature id for the '<em><b>Meta Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION__META_VARIABLES = TARGET_ENTRY__META_VARIABLES;

	/**
	 * The number of structural features of the '<em>Puppet Distribution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PUPPET_DISTRIBUTION_FEATURE_COUNT = TARGET_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDOCUMENTED__DOCUMENTATION = 0;

	/**
	 * The number of structural features of the '<em>IDocumented</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDOCUMENTED_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DOCUMENTATION = IDOCUMENTED__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = IDOCUMENTED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DEPRECATED = IDOCUMENTED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>RValue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION__RVALUE = IDOCUMENTED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = IDOCUMENTED_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>INamed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INAMED_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TargetElementImpl <em>Target Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TargetElementImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTargetElement()
	 * @generated
	 */
	int TARGET_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ELEMENT__NAME = INAMED__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ELEMENT__DOCUMENTATION = INAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ELEMENT__DEPRECATED = INAMED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Target Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TARGET_ELEMENT_FEATURE_COUNT = INAMED_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.AbstractTypeImpl <em>Abstract Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.AbstractTypeImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getAbstractType()
	 * @generated
	 */
	int ABSTRACT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__NAME = TARGET_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__DOCUMENTATION = TARGET_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__DEPRECATED = TARGET_ELEMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>EReference0</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__EREFERENCE0 = TARGET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__PROPERTIES = TARGET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE__PARAMETERS = TARGET_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TYPE_FEATURE_COUNT = TARGET_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeArgumentImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTypeArgument()
	 * @generated
	 */
	int TYPE_ARGUMENT = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT__NAME = TARGET_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT__DOCUMENTATION = TARGET_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT__DEPRECATED = TARGET_ELEMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT__REQUIRED = TARGET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Namevar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT__NAMEVAR = TARGET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT_FEATURE_COUNT = TARGET_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 10;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PropertyImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = TYPE_ARGUMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DOCUMENTATION = TYPE_ARGUMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DEPRECATED = TYPE_ARGUMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REQUIRED = TYPE_ARGUMENT__REQUIRED;

	/**
	 * The feature id for the '<em><b>Namevar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAMEVAR = TYPE_ARGUMENT__NAMEVAR;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = TYPE_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.ParameterImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = TYPE_ARGUMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DOCUMENTATION = TYPE_ARGUMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DEPRECATED = TYPE_ARGUMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__REQUIRED = TYPE_ARGUMENT__REQUIRED;

	/**
	 * The feature id for the '<em><b>Namevar</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAMEVAR = TYPE_ARGUMENT__NAMEVAR;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = TYPE_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TypeFragmentImpl <em>Type Fragment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TypeFragmentImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTypeFragment()
	 * @generated
	 */
	int TYPE_FRAGMENT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__NAME = ABSTRACT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__DOCUMENTATION = ABSTRACT_TYPE__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__DEPRECATED = ABSTRACT_TYPE__DEPRECATED;

	/**
	 * The feature id for the '<em><b>EReference0</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__EREFERENCE0 = ABSTRACT_TYPE__EREFERENCE0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__PROPERTIES = ABSTRACT_TYPE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT__PARAMETERS = ABSTRACT_TYPE__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Type Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FRAGMENT_FEATURE_COUNT = ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = ABSTRACT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__DOCUMENTATION = ABSTRACT_TYPE__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__DEPRECATED = ABSTRACT_TYPE__DEPRECATED;

	/**
	 * The feature id for the '<em><b>EReference0</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__EREFERENCE0 = ABSTRACT_TYPE__EREFERENCE0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__PROPERTIES = ABSTRACT_TYPE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__PARAMETERS = ABSTRACT_TYPE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Super Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__SUPER_TYPE = ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = ABSTRACT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.MetaTypeImpl <em>Meta Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.MetaTypeImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getMetaType()
	 * @generated
	 */
	int META_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__NAME = ABSTRACT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__DOCUMENTATION = ABSTRACT_TYPE__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__DEPRECATED = ABSTRACT_TYPE__DEPRECATED;

	/**
	 * The feature id for the '<em><b>EReference0</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__EREFERENCE0 = ABSTRACT_TYPE__EREFERENCE0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__PROPERTIES = ABSTRACT_TYPE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE__PARAMETERS = ABSTRACT_TYPE__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Meta Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_TYPE_FEATURE_COUNT = ABSTRACT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl <em>Name Space</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getNameSpace()
	 * @generated
	 */
	int NAME_SPACE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE__NAME = TARGET_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE__DOCUMENTATION = TARGET_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE__DEPRECATED = TARGET_ELEMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE__CONTENTS = TARGET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reserved</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE__RESERVED = TARGET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Name Space</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_SPACE_FEATURE_COUNT = TARGET_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.TPVariableImpl <em>TP Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.TPVariableImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getTPVariable()
	 * @generated
	 */
	int TP_VARIABLE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE__NAME = TARGET_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE__DOCUMENTATION = TARGET_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE__DEPRECATED = TARGET_ELEMENT__DEPRECATED;

	/**
	 * The feature id for the '<em><b>Assignable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE__ASSIGNABLE = TARGET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE__PATTERN = TARGET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TP Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TP_VARIABLE_FEATURE_COUNT = TARGET_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.puppetlabs.geppetto.pp.pptp.impl.MetaVariableImpl <em>Meta Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.MetaVariableImpl
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getMetaVariable()
	 * @generated
	 */
	int META_VARIABLE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_VARIABLE__NAME = TARGET_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_VARIABLE__DOCUMENTATION = TARGET_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_VARIABLE__DEPRECATED = TARGET_ELEMENT__DEPRECATED;

	/**
	 * The number of structural features of the '<em>Meta Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int META_VARIABLE_FEATURE_COUNT = TARGET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>File</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.io.File
	 * @see com.puppetlabs.geppetto.pp.pptp.impl.PPTPPackageImpl#getFile()
	 * @generated
	 */
	int FILE = 17;

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.AbstractType <em>Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Abstract Type</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.AbstractType
	 * @generated
	 */
	EClass getAbstractType();

	/**
	 * Returns the meta object for the reference '{@link com.puppetlabs.geppetto.pp.pptp.AbstractType#getEReference0 <em>EReference0</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>EReference0</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.AbstractType#getEReference0()
	 * @see #getAbstractType()
	 * @generated
	 */
	EReference getAbstractType_EReference0();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.AbstractType#getParameters
	 * <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.AbstractType#getParameters()
	 * @see #getAbstractType()
	 * @generated
	 */
	EReference getAbstractType_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.AbstractType#getProperties
	 * <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.AbstractType#getProperties()
	 * @see #getAbstractType()
	 * @generated
	 */
	EReference getAbstractType_Properties();

	/**
	 * Returns the meta object for data type '{@link java.io.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>File</em>'.
	 * @see java.io.File
	 * @model instanceClass="java.io.File"
	 * @generated
	 */
	EDataType getFile();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Function</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.Function#isRValue <em>RValue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>RValue</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Function#isRValue()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_RValue();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.IDocumented <em>IDocumented</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>IDocumented</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.IDocumented
	 * @generated
	 */
	EClass getIDocumented();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.IDocumented#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.IDocumented#getDocumentation()
	 * @see #getIDocumented()
	 * @generated
	 */
	EAttribute getIDocumented_Documentation();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>INamed</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.INamed
	 * @generated
	 */
	EClass getINamed();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.INamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.INamed#getName()
	 * @see #getINamed()
	 * @generated
	 */
	EAttribute getINamed_Name();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer <em>ITarget Element Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>ITarget Element Container</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer
	 * @generated
	 */
	EClass getITargetElementContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer#getContents
	 * <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer#getContents()
	 * @see #getITargetElementContainer()
	 * @generated
	 */
	EReference getITargetElementContainer_Contents();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.MetaType <em>Meta Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Meta Type</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.MetaType
	 * @generated
	 */
	EClass getMetaType();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.MetaVariable <em>Meta Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Meta Variable</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.MetaVariable
	 * @generated
	 */
	EClass getMetaVariable();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.NameSpace <em>Name Space</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Name Space</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.NameSpace
	 * @generated
	 */
	EClass getNameSpace();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.NameSpace#isReserved <em>Reserved</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Reserved</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.NameSpace#isReserved()
	 * @see #getNameSpace()
	 * @generated
	 */
	EAttribute getNameSpace_Reserved();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PPTPFactory getPPTPFactory();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.PuppetDistribution <em>Puppet Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Puppet Distribution</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.PuppetDistribution
	 * @generated
	 */
	EClass getPuppetDistribution();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.TargetElement <em>Target Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Target Element</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetElement
	 * @generated
	 */
	EClass getTargetElement();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TargetElement#isDeprecated <em>Deprecated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deprecated</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetElement#isDeprecated()
	 * @see #getTargetElement()
	 * @generated
	 */
	EAttribute getTargetElement_Deprecated();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry <em>Target Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Target Entry</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry
	 * @generated
	 */
	EClass getTargetEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getDescription()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EAttribute getTargetEntry_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getFunctions <em>Functions</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getFunctions()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EReference getTargetEntry_Functions();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getLabel()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EAttribute getTargetEntry_Label();

	/**
	 * Returns the meta object for the containment reference '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getMetaType <em>Meta Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Meta Type</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getMetaType()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EReference getTargetEntry_MetaType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getMetaVariables
	 * <em>Meta Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Meta Variables</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getMetaVariables()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EReference getTargetEntry_MetaVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getTypeFragments
	 * <em>Type Fragments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Type Fragments</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getTypeFragments()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EReference getTargetEntry_TypeFragments();

	/**
	 * Returns the meta object for the containment reference list '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getTypes()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EReference getTargetEntry_Types();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TargetEntry#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TargetEntry#getVersion()
	 * @see #getTargetEntry()
	 * @generated
	 */
	EAttribute getTargetEntry_Version();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.TPVariable <em>TP Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>TP Variable</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TPVariable
	 * @generated
	 */
	EClass getTPVariable();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#isAssignable <em>Assignable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Assignable</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TPVariable#isAssignable()
	 * @see #getTPVariable()
	 * @generated
	 */
	EAttribute getTPVariable_Assignable();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TPVariable#getPattern()
	 * @see #getTPVariable()
	 * @generated
	 */
	EAttribute getTPVariable_Pattern();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.Type#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Super Type</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.Type#getSuperType()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_SuperType();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.TypeArgument <em>Type Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Argument</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TypeArgument
	 * @generated
	 */
	EClass getTypeArgument();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TypeArgument#isNamevar <em>Namevar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Namevar</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TypeArgument#isNamevar()
	 * @see #getTypeArgument()
	 * @generated
	 */
	EAttribute getTypeArgument_Namevar();

	/**
	 * Returns the meta object for the attribute '{@link com.puppetlabs.geppetto.pp.pptp.TypeArgument#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TypeArgument#isRequired()
	 * @see #getTypeArgument()
	 * @generated
	 */
	EAttribute getTypeArgument_Required();

	/**
	 * Returns the meta object for class '{@link com.puppetlabs.geppetto.pp.pptp.TypeFragment <em>Type Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Fragment</em>'.
	 * @see com.puppetlabs.geppetto.pp.pptp.TypeFragment
	 * @generated
	 */
	EClass getTypeFragment();

} // PPTPPackage
