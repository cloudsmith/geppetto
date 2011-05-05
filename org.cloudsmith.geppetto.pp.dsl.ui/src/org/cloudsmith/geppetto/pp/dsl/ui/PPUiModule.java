/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui;

import org.cloudsmith.geppetto.common.tracer.DefaultTracer;
import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.pp.dsl.lexer.PPOverridingLexer;
import org.cloudsmith.geppetto.pp.dsl.ui.builder.PPModulefileBuilder;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPHighlightConfiguration;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPSemanticHighlightingCalculator;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPTokenToAttributeIdMapper;
import org.cloudsmith.geppetto.pp.dsl.ui.container.PPWorkspaceProjectsStateProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit.PPTokenTypeToPartionMapper;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.toggleComments.PPSingleLineCommentHelper;
import org.cloudsmith.geppetto.pp.dsl.ui.outline.PPLocationInFileProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.toggleComments.ISingleLineCommentHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;

import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
public class PPUiModule extends org.cloudsmith.geppetto.pp.dsl.ui.AbstractPPUiModule {
	public PPUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	// public Class<? extends IContainer.Manager> bindIContainer$Manager() {
	// return StateBasedContainerManager.class;
	// }

	public Class<? extends IHighlightingConfiguration> bindILexicalHighlightingConfiguration() {
		return PPHighlightConfiguration.class;
	}

	/**
	 * Add specialization that selects better "significant part" per semantic object.
	 * 
	 */
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return PPLocationInFileProvider.class;
	}

	/**
	 * Deal with dependency on JDT.
	 */
	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForIEditorInputFactory.class;
	}

	/**
	 * Deal with dependency on JDT.
	 */
	@Override
	public Class<? extends IResourceSetProvider> bindIResourceSetProvider() {
		return SimpleResourceSetProvider.class;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return PPSemanticHighlightingCalculator.class;
	}

	@Override
	public Class<? extends ISingleLineCommentHelper> bindISingleLineCommentHelper() {
		return PPSingleLineCommentHelper.class;
	}

	public Class<? extends ITokenTypeToPartitionTypeMapper> bindITokenTypeToPartitionTypeMapper() {
		return PPTokenTypeToPartionMapper.class;
	}

	public Class<? extends PPModulefileBuilder> bindModulefileBuilder() {
		return PPModulefileBuilder.class;
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindTokenToAttributeIdMapper() {
		return PPTokenToAttributeIdMapper.class;
	}

	public void configureDebugTracing(com.google.inject.Binder binder) {
		binder.bind(ITracer.class).annotatedWith(Names.named(PPUiConstants.DEBUG_OPTION_MODULEFILE)).toInstance(
			new DefaultTracer(PPUiConstants.DEBUG_OPTION_MODULEFILE));
		binder.bind(ITracer.class).annotatedWith(Names.named(PPUiConstants.DEBUG_OPTION_PARSER)).toInstance(
			new DefaultTracer(PPUiConstants.DEBUG_OPTION_PARSER));
	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
	@Override
	public void configureHighlightingLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class).annotatedWith(
			com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.HIGHLIGHTING)).to(
			PPOverridingLexer.class);
	}

	/**
	 * Deal with dependency on JDT.
	 */
	@Override
	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		// return org.eclipse.xtext.ui.shared.Access.getWorkspaceProjectsState();
		return new PPWorkspaceProjectsStateProvider();
	}
}
