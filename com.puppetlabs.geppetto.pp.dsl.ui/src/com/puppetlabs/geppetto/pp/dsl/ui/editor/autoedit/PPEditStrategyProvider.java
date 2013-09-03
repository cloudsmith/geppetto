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
package com.puppetlabs.geppetto.pp.dsl.ui.editor.autoedit;

import java.util.List;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.xtext.ui.editor.ISourceViewerAware;
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Overrides the default to make auto matching/insert configurable.
 * 
 */
public class PPEditStrategyProvider extends DefaultAutoEditStrategyProvider {

	private static class PreferenceCheckingStrategy implements IAutoEditStrategy {
		final private Supplier<Boolean> enablement;

		final protected IAutoEditStrategy wrapped;

		public PreferenceCheckingStrategy(IAutoEditStrategy wrapped, Supplier<Boolean> enablement) {
			this.enablement = enablement;
			this.wrapped = wrapped;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument,
		 * org.eclipse.jface.text.DocumentCommand)
		 */
		@Override
		public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
			if(!enablement.get())
				return;
			wrapped.customizeDocumentCommand(document, command);

		}
	}

	private static class WrappingAcceptor implements IEditStrategyAcceptor {
		private IEditStrategyAcceptor acceptor;

		private Supplier<Boolean> enablement;

		public WrappingAcceptor(IEditStrategyAcceptor acceptor, Supplier<Boolean> enablement) {
			this.acceptor = acceptor;
			this.enablement = enablement;
		}

		@Override
		public void accept(IAutoEditStrategy strategy, String contentType) {
			acceptor.accept(new PreferenceCheckingStrategy(strategy, enablement), contentType);
		}
	}

	@Inject
	private PPPreferencesHelper prefsHelper;

	/**
	 * @see org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider#configureCompoundBracesBlocks(org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider.IEditStrategyAcceptor)
	 */
	@Override
	protected void configureCompoundBracesBlocks(IEditStrategyAcceptor acceptor) {
		super.configureCompoundBracesBlocks(new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoCompleteBlockWanted();
			}
		}));
	}

	@Override
	protected void configureCurlyBracesBlock(IEditStrategyAcceptor acceptor) {
		super.configureCurlyBracesBlock(new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoBraceInsertWanted();
			}
		}));
	}

	protected void configureDqStringLiteral(IEditStrategyAcceptor acceptor) {
		acceptor = new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoDqStringInsertWanted();
			}
		});

		acceptor.accept(partitionInsert.newInstance("\"", "\""), IDocument.DEFAULT_CONTENT_TYPE);
		// The following two are registered for the default content type, because on deletion
		// the command.offset is cursor-1, which is outside the partition of terminals.length = 1.
		// How crude is that?
		// Note that in case you have two string literals following each other directly, the deletion strategy wouldn't apply.
		// One could add the same strategy for the STRING partition in addition to solve this
		acceptor.accept(partitionDeletion.newInstance("\"", "\""), IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(
			partitionEndSkippingEditStrategy.get(), TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION);
	}

	@Override
	protected void configureMultilineComments(IEditStrategyAcceptor acceptor) {
		super.configureMultilineComments(new WrappingAcceptor(acceptor, new Supplier<Boolean>() {

			@Override
			public Boolean get() {
				return prefsHelper.isAutoMLCommentInsertWanted();
			}

		}));
	}

	@Override
	protected void configureParenthesis(IEditStrategyAcceptor acceptor) {
		super.configureParenthesis(new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoParenthesisInsertWanted();
			}
		}));
	}

	protected void configureSqStringLiteral(IEditStrategyAcceptor acceptor) {
		acceptor = new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoSqStringInsertWanted();
			}
		});
		acceptor.accept(partitionInsert.newInstance("'", "'"), IDocument.DEFAULT_CONTENT_TYPE);
		// The following two are registered for the default content type, because on deletion
		// the command.offset is cursor-1, which is outside the partition of terminals.length = 1.
		// How crude is that?
		// Note that in case you have two string literals following each other directly, the deletion strategy wouldn't apply.
		// One could add the same strategy for the STRING partition in addition to solve this
		acceptor.accept(partitionDeletion.newInstance("\"", "\""), IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(
			partitionEndSkippingEditStrategy.get(), TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION);
	}

	@Override
	protected void configureSquareBrackets(IEditStrategyAcceptor acceptor) {
		super.configureSquareBrackets(new WrappingAcceptor(acceptor, new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				return prefsHelper.isAutoBracketInsertWanted();
			}
		}));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider#configureStringLiteral(org.eclipse.xtext.ui.editor.autoedit.
	 * AbstractEditStrategyProvider.IEditStrategyAcceptor)
	 */
	@Override
	protected void configureStringLiteral(IEditStrategyAcceptor acceptor) {
		// separate Sq and Dq configuration
		configureSqStringLiteral(acceptor);
		configureDqStringLiteral(acceptor);

	}

	/*
	 * Overrides the default, checking if strategy is wrapped and if so applies the view/key listener
	 * on the wrapped strategy.
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider#getStrategies(org.eclipse.jface.text.source.ISourceViewer,
	 * java.lang.String)
	 */
	@Override
	public List<IAutoEditStrategy> getStrategies(final ISourceViewer sourceViewer, final String contentType) {
		final List<IAutoEditStrategy> strategies = Lists.newArrayList();
		configure(new IEditStrategyAcceptor() {
			public void accept(IAutoEditStrategy strategy, String type) {
				if(type == null || contentType.equals(type)) {
					IAutoEditStrategy original = strategy;
					if(strategy instanceof PreferenceCheckingStrategy)
						strategy = ((PreferenceCheckingStrategy) strategy).wrapped;
					if(strategy instanceof ISourceViewerAware) {
						((ISourceViewerAware) strategy).setSourceViewer(sourceViewer);
					}
					if(strategy instanceof VerifyKeyListener) {
						sourceViewer.getTextWidget().addVerifyKeyListener((VerifyKeyListener) strategy);
					}
					strategies.add(original);
				}
			}
		});
		return strategies;
	}
}
