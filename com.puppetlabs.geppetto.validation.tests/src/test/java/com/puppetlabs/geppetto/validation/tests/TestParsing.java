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
package com.puppetlabs.geppetto.validation.tests;

import static org.junit.Assert.assertTrue;

import com.puppetlabs.geppetto.pp.PPPackage;
import com.puppetlabs.geppetto.pp.PuppetManifest;
import com.puppetlabs.geppetto.pp.dsl.services.PPGrammarAccess;
import com.puppetlabs.geppetto.pp.dsl.validation.DefaultPotentialProblemsAdvisor;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.validation.runner.PPDiagnosticsRunner;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.IParseResult;
import org.junit.Test;

/**
 * Tests PPDiagnosticsRunner's parseString method.
 * 
 */
public class TestParsing {

	@Test
	public void parseString() throws Exception {

		// The diagnostics runner has all the capabilities to perform parsing
		PPDiagnosticsRunner runner = new PPDiagnosticsRunner();
		runner.setUp(IValidationAdvisor.ComplianceLevel.PUPPET_2_7, new DefaultPotentialProblemsAdvisor());

		// The grammar access provides methods to obtain any parser rule.
		// It is possible to start anywhere in the grammar and only get specific
		// things back. Look at PP.xtext to understand what the parser rules
		// expects
		// to parse. (Note that some rules are tricky to call from the outside
		// as the "hidden" configuration may have to be a certain way). Parsing
		// PuppetManifest, or the high level Expression, or ExprList, should be
		// fine.
		//
		PPGrammarAccess ga = runner.get(PPGrammarAccess.class);

		// Parse a string using the root rule "PuppetManifest"
		// throws an exception with the IParseResult if there are syntax errors
		// (test just fails if that is the case).
		IParseResult parseResult = runner.parseString(ga.getPuppetManifestRule(), "'I am often quoted'");

		// The RootASTElement is an instance of what the grammar rule specified
		// as return
		// (In this case a PuppetManifest).
		EObject result = parseResult.getRootASTElement();
		assertTrue("PuppetManifest retured", result instanceof PuppetManifest);

		// A PuppetManifest has a list of statements which are either
		// expressions
		// or a special ExprList which in turn contains a list (ExprList is for
		// expressions
		// that change meaning depending on if there is a single expression or
		// followed by a comma
		// and more expressions.
		// Here it is assumed that a single expression (SingleQuotedString) is
		// returned.
		//
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue(
			"An instance of SingleQuotedString is obtained",
			PPPackage.Literals.SINGLE_QUOTED_STRING.isSuperTypeOf(result.eClass()));

		runner.tearDown(); // bye for now
	}
}
