/**
 * Copyright (c) 2011, 2012 Cloudsmith, Inc. and others.
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
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPCommentConfiguration;
import org.cloudsmith.geppetto.pp.dsl.lexer.PPOverridingLexer;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.builder.NatureAddingEditorCallback;
import org.cloudsmith.geppetto.pp.dsl.ui.builder.PPModuleMetadataBuilder;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPHighlightConfiguration;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPSemanticHighlightingCalculator;
import org.cloudsmith.geppetto.pp.dsl.ui.coloring.PPTokenToAttributeIdMapper;
import org.cloudsmith.geppetto.pp.dsl.ui.container.PPWorkspaceProjectsStateProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.contentassist.PPContentAssistLexer;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.PPSourceViewerConfiguration;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.actions.SaveActions;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit.PPEditStrategyProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit.PPTokenTypeToPartionMapper;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.folding.PPFoldingRegionProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.hover.PPDocumentationProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.hover.PPHoverProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.hyperlinking.PPHyperlinkHelper;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.model.PPPartitionTokenScanner;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.toggleComments.PPSingleLineCommentHelper;
import org.cloudsmith.geppetto.pp.dsl.ui.formatting.ResourceIBreakAndAlignAdviceProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.formatting.ResourceICommentFormatterAdviceProviders;
import org.cloudsmith.geppetto.pp.dsl.ui.formatting.ResourceIIndentationInformationProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.formatting.ResourceIPreferredWidthInformationProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.linked.ExtLinkedXtextEditor;
import org.cloudsmith.geppetto.pp.dsl.ui.linked.IExtXtextEditorCustomizer;
import org.cloudsmith.geppetto.pp.dsl.ui.linked.ISaveActions;
import org.cloudsmith.geppetto.pp.dsl.ui.linked.PPEditorCustomizer;
import org.cloudsmith.geppetto.pp.dsl.ui.linking.PPUISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.outline.PPLocationInFileProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.BreakAndAlignPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.CommentPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.FormatterGeneralPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.PPPreferenceStoreAccess;
import org.cloudsmith.geppetto.pp.dsl.ui.resource.PPResource;
import org.cloudsmith.geppetto.pp.dsl.ui.resource.PPResourceFactory;
import org.cloudsmith.geppetto.pp.dsl.ui.validation.PreferenceBasedPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.ui.validation.PreferenceBasedValidationAdvisorProvider;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice;
import org.cloudsmith.xtext.formatting.IPreferredMaxWidthInformation;
import org.cloudsmith.xtext.ui.editor.formatting.ContentFormatterFactory;
import org.cloudsmith.xtext.ui.editor.formatting.ResourceILineSeparatorProvider;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.linking.lazy.LazyLinker;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextSourceViewerConfiguration;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.toggleComments.ISingleLineCommentHelper;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
public class PPUiModule extends org.cloudsmith.geppetto.pp.dsl.ui.AbstractPPUiModule {
	public PPUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	/**
	 * This binding makes auto edit configurable with preferences
	 */
	@Override
	public Class<? extends AbstractEditStrategyProvider> bindAbstractEditStrategyProvider() {
		return PPEditStrategyProvider.class;
	}

	@Override
	public Class<? extends IContentFormatterFactory> bindIContentFormatterFactory() {
		return ContentFormatterFactory.class;
	}

	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return PPDocumentationProvider.class;
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return PPHoverProvider.class;
	}

	/**
	 * Binds an editor customizer with PP specific features.
	 */
	public Class<? extends IExtXtextEditorCustomizer> bindIExtXtextEditorCustomizer() {
		return PPEditorCustomizer.class;
	}

	/**
	 * This binding makes SL comments fold as expected.
	 */
	public Class<? extends IFoldingRegionProvider> bindIFoldingRegionProvider() {
		return PPFoldingRegionProvider.class;
	}

	/**
	 * This binding adds ability to find/cross reference PP elements (since they do not use EReferences).
	 */
	public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return PPHyperlinkHelper.class;
	}

	@Override
	public Class<? extends IIndentationInformation> bindIIndentationInformation() {
		return null; // block the super version
	}

	public Class<? extends IHighlightingConfiguration> bindILexicalHighlightingConfiguration() {
		return PPHighlightConfiguration.class;
	}

	/**
	 * The runtime module uses PPLinker.class which performs PP linking and documentation association
	 * as part of the linking process. This is not suitable for use in the UI, where the PPResource is
	 * instead taking over the responsibility. <br/>
	 * Overrides the default runtime module's binding
	 * 
	 * @see #bindIResourceFactory()
	 */
	public Class<? extends org.eclipse.xtext.linking.ILinker> bindILinker() {
		return LazyLinker.class;
	}

	/**
	 * Add specialization that selects better "significant part" per semantic object than the default.
	 * (This is used when a location in a file is wanted for selection when opening the file and there is a reference
	 * to the object - which of each features / text should be selected).
	 * 
	 */
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return PPLocationInFileProvider.class;
	}

	@Override
	public Class<? extends IPartitionTokenScanner> bindIPartitionTokenScanner() {
		return PPPartitionTokenScanner.class;
	}

	public Class<? extends IPotentialProblemsAdvisor> bindIPotentialProblemsAdvisor() {
		return PreferenceBasedPotentialProblemsAdvisor.class;
	}

	public Class<? extends IPreferenceStoreAccess> bindIPreferenceStoreAccess() {
		return PPPreferenceStoreAccess.class;
	}

	/**
	 * This is an override of the runtime module's configuration as the UI should use PPResource instead of
	 * LazyLinkingResource for PP linking to work correctly with the global build index.
	 * 
	 */
	public Class<? extends IResourceFactory> bindIResourceFactory() {
		return PPResourceFactory.class;
	}

	/**
	 * Deal with dependency on JDT (not wanted)
	 */
	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForIEditorInputFactory.class;
	}

	/**
	 * Deal with dependency on JDT (not wanted)
	 */
	@Override
	public Class<? extends IResourceSetProvider> bindIResourceSetProvider() {
		return SimpleResourceSetProvider.class;
	}

	public Class<? extends ISaveActions> bindISaveActions() {
		return SaveActions.class;
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

	@Override
	public Class<? extends org.eclipse.xtext.ui.editor.IXtextEditorCallback> bindIXtextEditorCallback() {
		// Bind the Geppetto class that automatically adds puppet and xtext natures (without asking).
		return NatureAddingEditorCallback.class;
	}

	public Class<? extends PPModuleMetadataBuilder> bindModulefileBuilder() {
		return PPModuleMetadataBuilder.class;
	}

	public Class<? extends ISearchPathProvider> bindSearchPathProvider() {
		return PPUISearchPathProvider.class;
	}

	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindTokenToAttributeIdMapper() {
		return PPTokenToAttributeIdMapper.class;
	}

	/**
	 * This is an override of the runtime modules binding (using a regular LazyLinkingResource).
	 */
	public Class<? extends XtextResource> bindXtextResource() {
		return PPResource.class;
	}

	/**
	 * Override that injects a wrapper for the external lexer used by the main parser.
	 * contributed by org.eclipse.xtext.generator.parser.antlr.ex.ca.ContentAssistParserGeneratorFragment.
	 * 
	 * Without this override, a default generated lexer will be used and this lexer will not be correct as
	 * PP parsing requires an external lexer. The binding reuses the main lexer.
	 */
	@Override
	public void configureContentAssistLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer.class).annotatedWith(
			com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.CONTENT_ASSIST)).to(
			PPContentAssistLexer.class);
		// This is the default generated one:
		// org.cloudsmith.geppetto.pp.dsl.ui.contentassist.antlr.lexer.InternalPPLexer.class);
	}

	/**
	 * Override that injects a wrapper for the external lexer used by the main parser.
	 * contributed by org.eclipse.xtext.generator.parser.antlr.ex.ca.ContentAssistParserGeneratorFragment
	 */
	@Override
	public void configureContentAssistLexerProvider(com.google.inject.Binder binder) {
		binder.bind(PPContentAssistLexer.class).toProvider(
			org.eclipse.xtext.parser.antlr.LexerProvider.create(PPContentAssistLexer.class));
	}

	public void configureDebugTracing(com.google.inject.Binder binder) {
		binder.bind(ITracer.class).annotatedWith(Names.named(PPUiConstants.DEBUG_OPTION_MODULEFILE)).toInstance(
			new DefaultTracer(PPUiConstants.DEBUG_OPTION_MODULEFILE));
		binder.bind(ITracer.class).annotatedWith(Names.named(PPUiConstants.DEBUG_OPTION_PARSER)).toInstance(
			new DefaultTracer(PPUiConstants.DEBUG_OPTION_PARSER));
	}

	public void configureDefaultPreferences(Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("PPPreferencesHelper")) //$NON-NLS-1$
		.to(PPPreferencesHelper.class);
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("FormatterGeneralPreferences")) //$NON-NLS-1$
		.to(FormatterGeneralPreferences.class);
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("CommentPreferences")) //$NON-NLS-1$
		.to(CommentPreferences.class);
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("BreakAndAlignPreferences")) //$NON-NLS-1$
		.to(BreakAndAlignPreferences.class);
	}

	public void configureEditor(Binder binder) {
		binder.bind(XtextEditor.class).to(ExtLinkedXtextEditor.class);
		binder.bind(XtextSourceViewerConfiguration.class).to(PPSourceViewerConfiguration.class);
	}

	/**
	 * @see #configureResourceSpecificProviders(Binder)
	 */
	public void configureFormatting(com.google.inject.Binder binder) {

		// Binds resource specific comment advice provider for SL comments
		binder.bind(ICommentFormatterAdvice.class) //
		.annotatedWith(com.google.inject.name.Names.named(PPCommentConfiguration.SL_FORMATTER_ADVICE_NAME))//
		.toProvider(ResourceICommentFormatterAdviceProviders.SLCommentAdviceProvider.class);

		// Binds resource specific comment advice provider for ML comments
		binder.bind(ICommentFormatterAdvice.class) //
		.annotatedWith(com.google.inject.name.Names.named(PPCommentConfiguration.ML_FORMATTER_ADVICE_NAME))//
		.toProvider(ResourceICommentFormatterAdviceProviders.MLCommentAdviceProvider.class);

		binder.bind(IBreakAndAlignAdvice.class) //
		.toProvider(ResourceIBreakAndAlignAdviceProvider.class);

	}

	// contributed by org.eclipse.xtext.generator.parser.antlr.ex.rt.AntlrGeneratorFragment
	@Override
	public void configureHighlightingLexer(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class).annotatedWith(
			com.google.inject.name.Names.named(org.eclipse.xtext.ui.LexerUIBindings.HIGHLIGHTING)).to(
			PPOverridingLexer.class);
	}

	public void configureIIndentationInformationProvider(Binder binder) {
		binder.bind(IIndentationInformation.class).toProvider(ResourceIIndentationInformationProvider.class);
	}

	/**
	 * Binds providers of resource specific information (e.g. resource metadata, scoped preferences,
	 * workspace etc.)
	 */
	public void configureResourceSpecificProviders(com.google.inject.Binder binder) {
		binder.bind(ILineSeparatorInformation.class).toProvider(ResourceILineSeparatorProvider.class);
		// binder.bind(IIndentationInformation.class).toProvider(ResourceIIndentationInformationProvider.class);
		binder.bind(IPreferredMaxWidthInformation.class).toProvider(ResourceIPreferredWidthInformationProvider.class);

	}

	/**
	 * Deal with dependency on JDT (not wanted).
	 */
	@Override
	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		// return org.eclipse.xtext.ui.shared.Access.getWorkspaceProjectsState();
		return new PPWorkspaceProjectsStateProvider();
	}

	/**
	 * A Provider of validation compliance based on preferences.
	 */
	public com.google.inject.Provider<IValidationAdvisor> provideValidationAdvisor() {
		return PreferenceBasedValidationAdvisorProvider.create();
	}

}
