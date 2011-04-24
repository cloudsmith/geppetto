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

import org.cloudsmith.geppetto.common.tracer.DefaultTracer;
import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.dsl.lexer.PPOverridingLexer;
import org.cloudsmith.geppetto.pp.dsl.linking.PPLinker;
import org.cloudsmith.geppetto.pp.dsl.linking.PPQualifiedNameConverter;
import org.cloudsmith.geppetto.pp.dsl.linking.PPQualifiedNameProvider;
import org.cloudsmith.geppetto.pp.dsl.linking.PPResourceDescriptionManager;
import org.cloudsmith.geppetto.pp.dsl.serialization.PPValueSerializer;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.validation.CompositeEValidator;

import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class PPRuntimeModule extends org.cloudsmith.geppetto.pp.dsl.AbstractPPRuntimeModule {
	/**
	 * Handles association of documentation comments, and resource linking (not based on
	 * standard EReference links).
	 */
	@Override
	public Class<? extends org.eclipse.xtext.linking.ILinker> bindILinker() {
		return PPLinker.class;
	}

	/**
	 * Handles FQN <-> String conversion and defines "::" as the separator.
	 */
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return PPQualifiedNameConverter.class;
	}

	/**
	 * Handles creation of QualifiedNames for referenceable PP model elements.
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.AbstractPPRuntimeModule#bindIQualifiedNameProvider()
	 */
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return PPQualifiedNameProvider.class;
	}

	/**
	 * Binds a resource description manager version that provides a resource description
	 * that takes specially imported names into account.
	 * 
	 * @return
	 */
	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return PPResourceDescriptionManager.class;
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

	@Override
	public Class<? extends Lexer> bindLexer() {
		return PPOverridingLexer.class;
	}

	public void configureDebugTracing(com.google.inject.Binder binder) {
		binder.bind(ITracer.class).annotatedWith(Names.named(PPDSLConstants.PP_DEBUG_LINKER)).toInstance(
			new DefaultTracer(PPDSLConstants.PP_DEBUG_LINKER));
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
