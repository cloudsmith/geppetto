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
package org.cloudsmith.geppetto.pp.dsl.tests;

import static com.google.inject.util.Modules.override;
import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;

import java.util.Iterator;
import java.util.List;

import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.PPFactory;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.dsl.PPRuntimeModule;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPStylesheetProvider;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.PPIndentationInformation;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.CSSDomFormatter;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.Tuples;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Tests PP language formatting using the new DomFormatter.
 * 
 */
public class TestPPFormatting extends AbstractPuppetTests {
	public static class DebugFormatter extends CSSDomFormatter {

		@Inject
		public DebugFormatter(Provider<DomCSS> domProvider, DomNodeLayoutFeeder feeder) {
			super(domProvider, feeder);
		}

		@Override
		public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
				Acceptor errors) {
			// System.err.println("TestSemanticCssFormatter.DebugFormatter");
			// System.err.println(DomModelUtils.compactDump(dom, true));
			return super.format(dom, regionToFormat, formattingContext, errors);
		}

	}

	public static class TestSetup extends PPTestSetup {
		public static class TestModule extends PPTestModule {

			@Override
			public void configure(Binder binder) {
				super.configure(binder);
				binder.bind(ISerializer.class).to(DomBasedSerializer.class);
				binder.bind(IDomModelFormatter.class).to(DebugFormatter.class);
				// Want serializer to insert empty WS even if there is no node model
				binder.bind(IHiddenTokenSequencer.class).to(
					org.cloudsmith.xtext.serializer.acceptor.HiddenTokenSequencer.class);

				// Bind the default style sheet (TODO: should use a test specific sheet)
				binder.bind(DomCSS.class).toProvider(PPStylesheetProvider.class);
				// specific to pp (2 spaces).
				binder.bind(IIndentationInformation.class).to(PPIndentationInformation.class);
				// binder.bind(IIndentationInformation.class).to(IIndentationInformation.Default.class);

				binder.bind(ILayoutManager.class).annotatedWith(Names.named("Default")).to(PPSemanticLayout.class);
			}
		}

		@Override
		public Injector createInjector() {
			return Guice.createInjector(override(getCommonModule(), new PPRuntimeModule()).with(new TestModule()));
		}
	}

	protected void brutalDetachNodeModel(EObject eObject) {
		EcoreUtil.resolveAll(eObject);
		List<Pair<EObject, ICompositeNode>> result = Lists.newArrayList();
		Iterator<Object> iterator = EcoreUtil.getAllContents(eObject.eResource(), false);
		while(iterator.hasNext()) {
			EObject object = (EObject) iterator.next();
			Iterator<Adapter> adapters = object.eAdapters().iterator();
			while(adapters.hasNext()) {
				Adapter adapter = adapters.next();
				if(adapter instanceof ICompositeNode) {
					adapters.remove();
					result.add(Tuples.create(object, (ICompositeNode) adapter));
					break;
				}
			}
		}
	}

	/**
	 * Don't want to test serializer as this test barfs on some PP constructs (with and without serialization
	 * is not the same result).
	 */
	@Override
	protected boolean shouldTestSerializer(XtextResource resource) {
		// true here (the default), just makes testing slower and it intermittently fails ?!?
		return false;
	}

	@Test
	public void test_Comment_acceptedAsBreak() throws Exception {
		String code = "node x { # comment\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_Comment_InLineML() throws Exception {
		// source with space after inline comment
		String code = "$a = 1 + /* x */ 2\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);

		// source without space after inline comment (one should be added).
		String code2 = "$a = 1 + /* x */2\n";
		r = getResourceFromString(code2);
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);

	}

	@Test
	public void test_CommentFoldingSL() throws Exception {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 133; i++)
			builder.append("f");
		String _133f = builder.toString();
		String code = "# ok askdj faoskdjf oalskdjf aalksjf alskdjf kdj " + _133f + " ffffffffffff\n";
		String expected = "# ok askdj faoskdjf oalskdjf aalksjf alskdjf kdj\n# " + _133f + "\n# ffffffffffff\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", expected, s);

	}

	@Test
	public void test_CommentShouldBeIndentedOkML() throws Exception {
		String code = "class foo {\n" + "  /* ok comment\n" + "   * ok comment\n" + "   */\n" + "}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_CommentShouldBeIndentedOkSL() throws Exception {
		String code = "class foo {\n" + "  # ok comment\n" + "  # ok comment\n" + "  #\n" + "}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_CommentShouldNotBeMoved1() throws Exception {
		String code = "$a = 10\n# comment\n$b = 20\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce same result", code, s);
	}

	@Test
	public void test_CommentShouldNotBeMoved2() throws Exception {
		String code = "$a = 10\n\n# comment\n$b = 20\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce same result", code, s);
	}

	@Test
	public void test_CommentShouldNotBeMovedToNextLine() throws Exception {
		// issue caused linebreaks between comment and statement to be removed
		String code = "$a = 10 # comment\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce same result", code, s);

	}

	@Test
	public void test_CommentShouldNotBeMovedToPreviousLine() throws Exception {
		// issue caused linebreaks between comment and statement to be removed
		String code = "$a = 10\n# comment\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce same result", code, s);
	}

	@Test
	public void test_CommentShouldNotBeTurnedIntoDocumentation() throws Exception {
		// issue caused linebreaks after comment, before statement to be removed
		String code = "# wtf\n\nclass foo {\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);

		code = "/* wtf */\n\nclass foo {\n}\n";
		r = getResourceFromString(code);
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_DocumentationShouldStickToElement() throws Exception {
		// issue caused documentation comment to be separated from its element
		String code = "class foo {\n}\n# doc\nclass foo {\n}\n";
		String fmt = "class foo {\n}\n\n# doc\nclass foo {\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);

		code = "class foo {\n}\n/* doc */\nclass foo {\n}\n";
		fmt = "class foo {\n}\n\n/* doc */\nclass foo {\n}\n";
		r = getResourceFromString(code);
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);
	}

	@Test
	public void test_HeyJenkins_EncodeThis() {
		assertEquals("åäö", "åäö");
	}

	@Test
	public void test_IndentsBracketsOk() throws Exception {
		String code = "file { \"x\":\n  notify => Service[\"y\"],\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_issue142_Interpolation() throws Exception {
		String code = "$a = 10\n" + //
				"$b = \"123${a}234\"\n" + //
				"\n" + //
				"class x {\n" + //
				"}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_List() throws Exception {
		String code = "$a=[\"10\",'20']";
		String fmt = "$a = [\"10\", '20']\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);
	}

	/**
	 * Test that model without node-model formats and adds the optional end comma in a list.
	 */
	@Test
	public void test_List_NoNodeModel() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression assignment = PPFactory.eINSTANCE.createAssignmentExpression();
		assignment.setLeftExpr(createVariable("a"));
		LiteralList pplist = PPFactory.eINSTANCE.createLiteralList();
		assignment.setRightExpr(pplist);
		pplist.getElements().add(createSqString("10"));
		pplist.getElements().add(createSqString("20"));
		pp.getStatements().add(assignment);
		String fmt = "$a = ['10', '20',]\n";
		String s = serializeFormatted(pp);
		assertEquals("formatting should produce wanted result", fmt, s);
	}

	@Test
	public void test_List_WithComments() throws Exception {
		String code = "/*1*/$a/*2*/=/*3*/[/*4*/'10'/*5*/,/*6*/'20'/*7*/]/*8*/";
		String fmt = "/* 1 */ $a /* 2 */ = /* 3 */ [/* 4 */ '10' /* 5 */, /* 6 */ '20' /* 7 */] /* 8 */\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);
	}

	@Test
	public void test_NoFunnyLeadingInsert() throws Exception {
		// one issue caused a space to be inserted when the first element was a comment
		String code = "# wtf\n$a = 1\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);

		code = "/* wtf */\n$a = 1\n";
		r = getResourceFromString(code);
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);
	}

	@Test
	public void test_OptionalTrailingBreaks() throws Exception {
		String code = "file{'afile':owner=>'foo'}\n\n\n\n\n";
		String fmt = "file { 'afile':\n  owner => 'foo'\n}\n\n";
		XtextResource r = getResourceFromString(code);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);
	}

	@Test
	public void test_Resource_MultipleBodies() throws Exception {
		String code = "file { 'title': owner => 777, ensure => present; 'title2': owner=>777,ensure=>present }";
		String fmt = //
		"file {\n  'title':\n    owner  => 777,\n    ensure => present;\n\n" + //
				"  'title2':\n    owner  => 777,\n    ensure => present\n}\n";
		String fmt2 = //
		"file {\n  'title':\n    owner  => 777,\n    ensure => present;\n\n" + //
				"  'title2':\n    owner  => 777,\n    ensure => present;\n}\n";

		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting with node model should produce wanted result", fmt, s);

		brutalDetachNodeModel(r.getContents().get(0));
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting without node model should produce wanted result", fmt2, s);
	}

	@Test
	public void test_Resource_OneBody() throws Exception {
		String code = "file { 'title': owner => 777, ensure => present }";
		String fmt = "file { 'title':\n  owner  => 777,\n  ensure => present\n}\n";
		String fmt2 = "file { 'title':\n  owner  => 777,\n  ensure => present,\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting with node-model should produce wanted result", fmt, s);

		brutalDetachNodeModel(r.getContents().get(0));
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting without node-model should produce wanted result", fmt2, s);
	}

	@Test
	public void test_Resource_OneBody_NoTitle() throws Exception {
		String code = "File { owner => 777, ensure => present }";
		String fmt = "File {\n  owner  => 777,\n  ensure => present\n}\n";
		String fmt2 = "File {\n  owner  => 777,\n  ensure => present,\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", fmt, s);

		brutalDetachNodeModel(r.getContents().get(0));
		s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting without node-model should produce wanted result", fmt2, s);
	}

	@Test
	public void test_selectiveFormatting1() throws Exception {
		String code1 /*
						*/= "class x {\n";
		//
		String code2 /*
						*/= "  exec { 'x':\n" //
				+ "    command => 'echo gotcha',\n" //
				+ "  }"; //
		String code3 = /*
						*/"\n}\n";

		String code = code1 + code2 + code3;
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("formatting should produce wanted result", code, s);

		s = serializeFormatted(r.getContents().get(0), new TextRegion(10, 47));
		assertEquals("formatting should produce wanted result", code2, s);

	}
}
