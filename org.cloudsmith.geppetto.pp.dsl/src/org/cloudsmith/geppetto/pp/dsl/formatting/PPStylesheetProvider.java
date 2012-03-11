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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.formatter.css.DefaultStylesheetProvider;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.Select;
import org.eclipse.xtext.IGrammarAccess;

import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public class PPStylesheetProvider extends DefaultStylesheetProvider {

	@Inject
	private IStyleFactory styles;

	@Inject
	private IFunctionFactory functions;

	@Inject
	IGrammarAccess grammarAccess;

	@Inject
	PPLayouts.ResourceExpressionLayout resourceExpressionLayout;

	@Inject
	PPLayouts.ResourceBodyLayout resourceBodyLayout;

	@Inject
	PPLayouts.AttributeOperationsLayout attributeOperationLayout;

	@Override
	public DomCSS get() {
		DomCSS css = super.get();
		PPGrammarAccess ga = getGrammarAccess();

		css.addRules( //
			// ResourceExpression Layout
			Select.semantic(PPPackage.Literals.RESOURCE_EXPRESSION).withStyles( //
				styles.layout(resourceExpressionLayout)), //
			Select.semantic(PPPackage.Literals.RESOURCE_BODY).withStyles( //
				styles.layout(resourceBodyLayout)), //
			Select.semantic(PPPackage.Literals.ATTRIBUTE_OPERATIONS).withStyles( //
				styles.layout(attributeOperationLayout)));
		return css;
	}

	private PPGrammarAccess getGrammarAccess() {
		return ((PPGrammarAccess) grammarAccess);
	}
}
