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
package org.cloudsmith.geppetto.pp.dsl.tests;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPFactory;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.dsl.validation.PPJavaValidator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.junit.AbstractXtextTests;
import org.eclipse.xtext.junit.validation.ValidatorTester;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.validation.EValidatorRegistrar;

public class AbstractPuppetTests extends AbstractXtextTests {

	protected ValidatorTester<PPJavaValidator> tester;

	protected final PPFactory pf = PPFactory.eINSTANCE;

	protected void addResourceBody(ResourceExpression o, String title, Object... keyValPairs) {
		o.getResourceData().add(createResourceBody(title, keyValPairs));
	}

	/**
	 * Asserts that instance is assignable to expected. Appends information to message about what was
	 * expected and given.
	 * 
	 * @param message
	 * @param expected
	 * @param instance
	 */
	protected void assertInstanceOf(String message, Class<?> expected, Object instance) {
		assertTrue(message + ": expected instanceof: " + expected.getSimpleName() + " got: " +
				instance.getClass().getSimpleName(), expected.isAssignableFrom(instance.getClass()));
	}

	protected AttributeOperation createAttributeAddition(String name, Expression value) {
		AttributeOperation ao = pf.createAttributeOperation();
		ao.setKey(name);
		ao.setValue(value);
		ao.setOp("+>");
		return ao;
	}

	protected AttributeOperation createAttributeAddition(String name, String value) {
		return createAttributeAddition(name, createNameOrReference(value));
	}

	protected AttributeOperation createAttributeDefinition(String name, Expression value) {
		AttributeOperation ao = pf.createAttributeOperation();
		ao.setKey(name);
		ao.setValue(value);
		ao.setOp("=>");
		return ao;
	}

	protected AttributeOperation createAttributeDefinition(String name, String value) {
		return createAttributeDefinition(name, createNameOrReference(value));
	}

	protected LiteralNameOrReference createNameOrReference(String name) {
		LiteralNameOrReference o = pf.createLiteralNameOrReference();
		o.setValue(name);
		return o;
	}

	protected ResourceBody createResourceBody(boolean additive, Expression titleExpr, Object... keyValPairs) {
		ResourceBody rb = pf.createResourceBody();
		rb.setNameExpr(titleExpr);
		AttributeOperations aos = pf.createAttributeOperations();
		EList<AttributeOperation> aoList = aos.getAttributes();
		for(int i = 0; i < keyValPairs.length; i++) {
			AttributeOperation ao = pf.createAttributeOperation();
			ao.setOp(additive
					? "+>"
					: "=>");
			if(!(keyValPairs[i] instanceof String))
				throw new IllegalArgumentException("Bad test spec, key not a String");
			ao.setKey((String) (keyValPairs[i++]));

			if(keyValPairs[i] instanceof String) {
				SingleQuotedString valueExpr = pf.createSingleQuotedString();
				valueExpr.setText((String) (keyValPairs[i]));
				ao.setValue(valueExpr);
			}
			else if(keyValPairs[i] instanceof Expression)
				ao.setValue((Expression) keyValPairs[i]);
			else
				throw new IllegalArgumentException("Bad test spec, keyValPair value neither String not expression");
			aoList.add(ao);
		}
		if(aos.getAttributes().size() > 0)
			rb.setAttributes(aos);
		return rb;
	}

	protected ResourceBody createResourceBody(boolean additive, String title, Object... keyValPairs) {
		SingleQuotedString titleExpr = null;
		if(title != null) {
			titleExpr = pf.createSingleQuotedString();
			titleExpr.setText(title);
		}
		return createResourceBody(additive, titleExpr, keyValPairs);
	}

	protected ResourceBody createResourceBody(String title, Object... keyValPairs) {
		return createResourceBody(false, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean exported, boolean virtual, boolean additive,
			String type, Expression title, Object... keyValPairs) {

		ResourceExpression re = pf.createResourceExpression();

		Expression resourceExpr = null;
		if(virtual) {
			VirtualNameOrReference resourceName = pf.createVirtualNameOrReference();
			resourceName.setValue(type);
			resourceName.setExported(exported);
			resourceExpr = resourceName;

		}
		else {
			LiteralNameOrReference resourceName = pf.createLiteralNameOrReference();
			resourceName.setValue(type);
			resourceExpr = resourceName;
		}
		re.setResourceExpr(resourceExpr);

		re.getResourceData().add(createResourceBody(additive, title, keyValPairs));
		return re;
	}

	protected ResourceExpression createResourceExpression(boolean exported, boolean virtual, boolean additive,
			String type, String title, Object... keyValPairs) {
		SingleQuotedString titleExpr = null;
		if(title != null) {
			titleExpr = pf.createSingleQuotedString();
			titleExpr.setText(title);
		}
		return createResourceExpression(exported, virtual, additive, type, titleExpr, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean virtual, boolean additive, String type,
			Expression title, Object... keyValPairs) {
		return createResourceExpression(false, virtual, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean virtual, boolean additive, String type, String title,
			Object... keyValPairs) {
		return createResourceExpression(false, virtual, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean additive, String type, Expression title,
			Object... keyValPairs) {
		return createResourceExpression(false, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(boolean additive, String type, String title,
			Object... keyValPairs) {
		return createResourceExpression(false, additive, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(false, type, title, keyValPairs);
	}

	protected ResourceExpression createResourceExpression(String type, String title, Object... keyValPairs) {
		return createResourceExpression(false, type, title, keyValPairs);
	}

	protected SingleQuotedString createSqString(String text) {
		SingleQuotedString s = pf.createSingleQuotedString();
		s.setText(text);
		return s;
	}

	protected Expression createValue(String txt) {
		LiteralNameOrReference v = pf.createLiteralNameOrReference();
		v.setValue(txt);
		return v;
	}

	protected VariableExpression createVariable(String name) {
		VariableExpression v = pf.createVariableExpression();
		v.setVarName("$" + name);
		return v;
	}

	protected ResourceExpression createVirtualExportedResourceExpression(String type, Expression title,
			Object... keyValPairs) {
		return createResourceExpression(true, true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualExportedResourceExpression(String type, String title,
			Object... keyValPairs) {
		return createResourceExpression(true, true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualResourceExpression(String type, Expression title, Object... keyValPairs) {
		return createResourceExpression(true, false, type, title, keyValPairs);
	}

	protected ResourceExpression createVirtualResourceExpression(String type, String title, Object... keyValPairs) {
		return createResourceExpression(true, false, type, title, keyValPairs);
	}

	protected AssertableResourceDiagnostics resourceErrorDiagnostics(XtextResource r) {
		return new AssertableResourceDiagnostics(r.getErrors());
	}

	protected AssertableResourceDiagnostics resourceWarningDiagnostics(XtextResource r) {
		return new AssertableResourceDiagnostics(r.getWarnings());
	}

	@Override
	public String serialize(EObject obj) {
		SaveOptions options = SaveOptions.newBuilder().getOptions();
		// System.err.println(options.toString());
		return getSerializer().serialize(obj, options);
	}

	public String serializeFormatted(EObject obj) {
		return getSerializer().serialize(obj, SaveOptions.newBuilder().format().getOptions());
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		// with(PPStandaloneSetup.class);
		with(PPTestSetup.class);
		PPJavaValidator validator = get(PPJavaValidator.class);
		EValidatorRegistrar registrar = get(EValidatorRegistrar.class);
		tester = new ValidatorTester<PPJavaValidator>(validator, registrar, "org.cloudsmith.geppetto.pp.dsl.PP");
	}
}
