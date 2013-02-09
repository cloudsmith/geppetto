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

import org.cloudsmith.geppetto.pp.AndExpression;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.EqualityExpression;
import org.cloudsmith.geppetto.pp.ExportedCollectQuery;
import org.cloudsmith.geppetto.pp.OrExpression;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.VirtualCollectQuery;
import org.junit.Test;

/**
 * Tests the Puppet Collect Expression.
 * 
 */
public class TestCollectExpression extends AbstractPuppetTests {

	// @fmtOff
	static final String Sample_Collect = "User <| name == Luke |> {\n" +
			"  role  => jedi,\n" +
			"  tasks +> rakeleaves,\n" +
			"}\n";

	static final String Sample_Collect_Exported = "User <<| name == Luke |>> {\n" +
			"  role  => jedi,\n" +
			"  tasks +> rakeleaves,\n" +
			"}\n";

	static final String Sample_Collect_Complex = "User <<| name != Luke and (name != Darth or name != Vader) |>> {\n" +
			"  role  => jedi,\n" +
			"  tasks +> rakeleaves,\n" +
			"}\n";

	// @fmtOn

	@Test
	public void test_Serialize_CollectExpr() {
		final PuppetManifest pp = pf.createPuppetManifest();
		final CollectExpression ce = pf.createCollectExpression();
		pp.getStatements().add(ce);

		ce.setClassReference(createNameOrReference("User"));
		EqualityExpression predicate = pf.createEqualityExpression();
		predicate.setLeftExpr(createNameOrReference("name"));
		predicate.setOpName("==");
		predicate.setRightExpr(createNameOrReference("Luke"));

		VirtualCollectQuery q = pf.createVirtualCollectQuery();
		q.setExpr(predicate);
		ce.setQuery(q);

		// -- add some attributes
		AttributeOperations aos = pf.createAttributeOperations();
		aos.getAttributes().add(createAttributeDefinition("role", "jedi"));
		aos.getAttributes().add(createAttributeAddition("tasks", "rakeleaves"));
		ce.setAttributes(aos);

		String s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Collect, s);

		// -- test exported query
		ExportedCollectQuery eq = pf.createExportedCollectQuery();
		eq.setExpr(predicate);
		ce.setQuery(eq);

		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Collect_Exported, s);

		// -- test with more complex query
		// -- test non equality
		predicate.setOpName("!=");
		// -- test and / or
		AndExpression and = pf.createAndExpression();
		and.setLeftExpr(predicate);
		OrExpression or = pf.createOrExpression();

		EqualityExpression predicate2 = pf.createEqualityExpression();
		predicate2.setLeftExpr(createNameOrReference("name"));
		predicate2.setOpName("!=");
		predicate2.setRightExpr(createNameOrReference("Darth"));
		EqualityExpression predicate3 = pf.createEqualityExpression();
		predicate3.setLeftExpr(createNameOrReference("name"));
		predicate3.setOpName("!=");
		predicate3.setRightExpr(createNameOrReference("Vader"));
		or.setLeftExpr(predicate2);
		or.setRightExpr(predicate3);
		ParenthesisedExpression pe = pf.createParenthesisedExpression();
		pe.setExpr(or);
		and.setRightExpr(pe);
		eq.setExpr(and);
		s = serializeFormatted(pp);
		assertEquals("serialization should produce specified result", Sample_Collect_Complex, s);

	}

	@Test
	public void test_Validate_Collect_Ok() {
		final PuppetManifest pp = pf.createPuppetManifest();
		final CollectExpression ce = pf.createCollectExpression();
		pp.getStatements().add(ce);
		ce.setClassReference(createNameOrReference("User"));
		EqualityExpression predicate = pf.createEqualityExpression();
		predicate.setLeftExpr(createNameOrReference("name"));
		predicate.setOpName("==");
		predicate.setRightExpr(createNameOrReference("Luke"));

		VirtualCollectQuery q = pf.createVirtualCollectQuery();
		q.setExpr(predicate);
		ce.setQuery(q);

		// -- test without attributes
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		// -- add some attributes
		AttributeOperations aos = pf.createAttributeOperations();
		aos.getAttributes().add(createAttributeDefinition("x", "b"));
		aos.getAttributes().add(createAttributeAddition("y", "b"));
		ce.setAttributes(aos);
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		// --test different class references
		ce.setClassReference(createNameOrReference("::User"));
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		ce.setClassReference(createNameOrReference("A::User::B"));
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		// -- test exported query
		ExportedCollectQuery eq = pf.createExportedCollectQuery();
		eq.setExpr(predicate);
		ce.setQuery(eq);
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		// -- test non equality
		predicate.setOpName("!=");
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

		// -- test and / or
		AndExpression and = pf.createAndExpression();
		and.setLeftExpr(predicate);
		OrExpression or = pf.createOrExpression();

		EqualityExpression predicate2 = pf.createEqualityExpression();
		predicate2.setLeftExpr(createNameOrReference("name"));
		predicate2.setOpName("!=");
		predicate2.setRightExpr(createNameOrReference("Darth"));
		EqualityExpression predicate3 = pf.createEqualityExpression();
		predicate3.setLeftExpr(createNameOrReference("name"));
		predicate3.setOpName("!=");
		predicate3.setRightExpr(createNameOrReference("Vader"));

		or.setLeftExpr(predicate2);
		or.setRightExpr(predicate3);
		ParenthesisedExpression pe = pf.createParenthesisedExpression();
		pe.setExpr(or);
		and.setRightExpr(pe);
		eq.setExpr(and);
		tester.validator().checkCollectExpression(ce);
		tester.diagnose().assertOK();

	}
}

/**
 * User <| name == Luke |>
 * ::User <| lastName == Skywalker |>
 * A::User::B <| name != Darth |>
 * 
 * User <| name == Luke and (occupation == jedi or occupation == pilot) |> {
 * duties +> rakeLeaves,
 * newThing => someValue,
 * }
 * User <<| name == Luke and (occupation == jedi or occupation != pilot) |>> {
 * duties +> rakeLeaves,
 * newThing => someValue,
 * someList => [1, 2, 3, foo, class],
 * someHash => { name => bar, type => wtf },
 * 
 * }
 */
