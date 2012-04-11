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

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPSemanticLayout;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPStylesheetProvider;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.PPIndentationInformation;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.CSSDomFormatter;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

/**
 * @author henrik
 * 
 */
public class TestPPFormattingFailing extends AbstractPuppetTests {
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

	private String doubleQuote(String s) {
		return '"' + s + '"';
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

	/**
	 * Due to issues in the formatter, this test may hit a bug that inserts whitespace
	 * between quotes and string - no workaround found - needs to be fixed in Xtext formatter.
	 * Also see {@link #test_Serialize_DoubleQuotedString_2()}
	 * 
	 * @throws Exception
	 */
	public void test_Serialize_DoubleQuotedString_1() throws Exception {
		String original = "before${var}/after${1+2}$$${$var}";
		String formatted = doubleQuote("before${var}/after${1 + 2}$$${$var}");
		formatted += "\n";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", formatted, s);
	}
}
