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
package org.cloudsmith.geppetto.pp.dsl;

import org.cloudsmith.geppetto.pp.dsl.lexer.PPOverridingLexer;
import org.cloudsmith.geppetto.pp.dsl.linker.PPLinker;
import org.cloudsmith.geppetto.pp.dsl.serialization.PPValueSerializer;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.validation.CompositeEValidator;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class PPRuntimeModule extends org.cloudsmith.geppetto.pp.dsl.AbstractPPRuntimeModule {
	// public Class<? extends IHiddenTokenHelper> bindIHiddenTokenHelper() {
	// return PPHiddenTokenHelper.class;
	// }

	@Override
	public Class<? extends org.eclipse.xtext.linking.ILinker> bindILinker() {
		return PPLinker.class;
	}

	// add transient value serialization service to enable skipping values that are transient from
	// a grammar perspective
	@Override
	public Class<? extends org.eclipse.xtext.parsetree.reconstr.ITransientValueService> bindITransientValueService() {
		return PPGrammarSerialization.class;
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return PPTerminalConverters.class;
	}

	public Class<? extends org.eclipse.xtext.parsetree.reconstr.ITokenSerializer.IValueSerializer> bindIValueSerializer() {
		return PPValueSerializer.class;
	}

	// Needed in Xtext 1.0 version
	// public Class<? extends IElementMatcherProvider> bindMatcherProvider() {
	// return PPMatcherProvider.class;
	// }

	@Override
	public Class<? extends Lexer> bindLexer() {
		return PPOverridingLexer.class;
	}

	/**
	 * for Xtext 2.0 >= M5 see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=322639">Xtext issue 322639</a>
	 */
	public void configureEObjectValidator(com.google.inject.Binder binder) {
		binder.bindConstant().annotatedWith( //
			com.google.inject.name.Names.named(//
			CompositeEValidator.USE_EOBJECT_VALIDATOR)).to(false);
	}

	// Is now done via an external lexer that is automatically configured into the module
	// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
	@Override
	public void configureRuntimeLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class).annotatedWith(
			com.google.inject.name.Names.named(org.eclipse.xtext.parser.antlr.LexerBindings.RUNTIME)).to(
			PPOverridingLexer.class);
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
	public com.google.inject.Provider<PPOverridingLexer> providePPOverridingLexer() {
		return org.eclipse.xtext.parser.antlr.LexerProvider.create(PPOverridingLexer.class);
	}

}
