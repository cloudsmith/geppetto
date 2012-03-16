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

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.PPFactory;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPStylesheetProvider;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.PPIndentationInformation;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.CSSDomFormatter;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.MeasuredTextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.cloudsmith.xtext.textflow.TextFlowRecording;
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
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

/**
 * Tests PP language formatting using the new DomFormatter.
 * 
 */
public class TestSemanticCssFormatter extends AbstractPuppetTests {
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
			return Guice.createInjector(Modules.override(new org.cloudsmith.geppetto.pp.dsl.PPRuntimeModule()).with(
				new TestModule()));
		}
	}

	private void appendSampleFlow(ITextFlow flow) {
		flow.appendText("123456");
		flow.appendBreak();
		flow.appendText("123456789");
		flow.changeIndentation(1);
		flow.appendBreak();
		flow.appendText("123");
		flow.changeIndentation(-1);
		flow.appendBreak();
		flow.appendBreak();
	}

	/**
	 * @param flow
	 */
	private void assertFlowOneLineNoBreak(ITextFlow.IMetrics flow) {
		assertFalse("ends with break", flow.endsWithBreak());
		assertEquals("Height", 1, flow.getHeight());
		assertEquals("Last used indent", 0, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 3, flow.getWidth());
		assertEquals("Width of last line", 3, flow.getWidthOfLastLine());
		assertFalse("Empty", flow.isEmpty());

	}

	/**
	 * @param flow
	 */
	private void assertSampleFlowMetrics(ITextFlow.IMetrics flow) {
		assertTrue("ends with break notTrue", flow.endsWithBreak());
		assertEquals("Height", 4, flow.getHeight());
		assertEquals("Last used indent", 1, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 9, flow.getWidth());
		assertEquals("Width of last line", 5, flow.getWidthOfLastLine());
		assertFalse("Not empty", flow.isEmpty());
	}

	// @Override
	// public void setUp() throws Exception {
	// super.setUp();
	// // with(PPStandaloneSetup.class);
	// with(TestSetup.class);
	// }

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.junit.AbstractXtextTests#shouldTestSerializer(org.eclipse.xtext.resource.XtextResource)
	 */
	@Override
	protected boolean shouldTestSerializer(XtextResource resource) {
		// true here (the default), just makes testing slower and it intermittently fails ?!?
		return false;
	}

	public void test_MeasuringTextStream() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	public void test_MeasuringTextStreamEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	public void test_MeasuringTextStreamOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(MeasuredTextFlow.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	public void test_PPResourceMultipleBodies() throws Exception {
		String code = "file { 'title': owner => 777, ensure => present; 'title2': owner=>777,ensure=>present }";
		String fmt = //
		"file {\n  'title':\n    owner  => 777,\n    ensure => present;\n\n" + //
				"  'title2':\n    owner  => 777,\n    ensure => present;\n}\n";
		// for(int i = 0; i < 1000; i++) {

		XtextResource r = getResourceFromString(code);
		brutalDetachNodeModel(r.getContents().get(0));
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);
		// }
	}

	public void test_PPResourceOneBody() throws Exception {
		String code = "file { 'title': owner => 777, ensure => present }";
		String fmt = "file { 'title':\n  owner  => 777,\n  ensure => present,\n}\n";
		for(int i = 0; i < 1; i++) {
			XtextResource r = getResourceFromString(code);
			// brutalDetachNodeModel(r.getContents().get(0));
			String s = serializeFormatted(r.getContents().get(0));
			assertEquals("serialization should produce same result", fmt, s);
		}
	}

	public void test_PPResourceOneBodyNoTitle() throws Exception {
		String code = "File { owner => 777, ensure => present }";
		String fmt = "File {\n  owner  => 777,\n  ensure => present,\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);
	}

	public void test_Recording() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	public void test_RecordingOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	public void test_Serialize_arrayNoSpaces() throws Exception {
		String code = "$a=[\"10\",'20']";
		String fmt = "$a = [\"10\", '20']\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);
	}

	public void test_Serialize_arrayWithComments() throws Exception {
		String code = "/*1*/$a/*2*/=/*3*/[/*4*/'10'/*5*/,/*6*/'20'/*7*/]/*8*/";
		String fmt = "/*1*/ $a /*2*/ = /*3*/ [/*4*/ '10' /*5*/, /*6*/ '20' /*7*/] /*8*/\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);
	}

	public void test_Serialize_assignArray() throws Exception {
		PuppetManifest pp = pf.createPuppetManifest();
		AssignmentExpression assignment = PPFactory.eINSTANCE.createAssignmentExpression();
		assignment.setLeftExpr(createVariable("a"));
		LiteralList pplist = PPFactory.eINSTANCE.createLiteralList();
		assignment.setRightExpr(pplist);
		pplist.getElements().add(createSqString("10"));
		pplist.getElements().add(createSqString("20"));
		pp.getStatements().add(assignment);
		String fmt = "$a = ['10', '20']\n";
		String s = serializeFormatted(pp);
		assertEquals("serialization should produce same result", fmt, s);
	}

	public void test_Serialize_simpleResource() throws Exception {
		String code = "file{'afile':owner=>'foo'}\n\n\n\n\n";
		// this is not wanted end result, only used to test intermediate progress on indentation and linebreak
		String fmt = "file { 'afile':\n  owner => 'foo',\n}\n\n";
		XtextResource r = getResourceFromString(code);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce same result", fmt, s);
	}

	public void test_TextFlow() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);

	}

	public void test_TextFlowEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		appendSampleFlow(flow);
		assertSampleFlowMetrics(flow);
	}

	public void test_TextFlowOneLineNoBreak() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlow.class);
		flow.appendText("123");
		assertFlowOneLineNoBreak(flow);
	}

	public void test_TextRecordingEmpty() {
		MeasuredTextFlow flow = this.getInjector().getInstance(TextFlowRecording.class);
		assertFalse("ends with break", flow.endsWithBreak());
		assertEquals("Height", 0, flow.getHeight());
		assertEquals("Last used indent", 0, flow.getLastUsedIndentation());
		assertEquals("Current indent", 0, flow.getIndentation());
		assertEquals("Width", 0, flow.getWidth());
		assertEquals("Width of last line", 0, flow.getWidthOfLastLine());
		assertTrue("Empty", flow.isEmpty());

	}

	public void testBasicEnumSets() {
		// assert that containsAll of empty set is true
		Set<NodeType> none = EnumSet.noneOf(NodeType.class);
		Set<NodeType> ws = EnumSet.of(NodeType.WHITESPACE);
		assertTrue(ws.containsAll(none));
	}
}
