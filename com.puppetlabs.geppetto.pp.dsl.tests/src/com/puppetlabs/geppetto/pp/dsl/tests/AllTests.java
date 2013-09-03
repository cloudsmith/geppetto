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
package org.cloudsmith.geppetto.pp.dsl.tests;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Tests.
 */
@SuiteClasses({
// @fmtOff
	TestVariables.class,
	TestWsAndComments.class,
	TestPuppetResourceExpr.class,
	TestLiterals.class,
	TestExpressions.class,
	TestCollectExpression.class,
	TestSelectorExpression.class,
	TestDoubleQuotedString.class,
	TestIssues.class,
	TestIssues3_0.class,
	TestLinking.class,
	TestSemanticOneSpaceFormatter.class,
	TestSemanticCssFormatter.class,
	TestPPFormatting.class,
	TestPPFormattingFailing.class,
	TestFormatterUtils.class,
	TestPptpResourceAsFile.class
// @fmtOn
})
@RunWith(Suite.class)
public class AllTests extends TestSuite {
}
