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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.inject.Inject;

/**
 * Overrides the default to make auto matching/insert configurable.
 * 
 */
public class PPEditStrategyProvider extends DefaultAutoEditStrategyProvider {

	@Inject
	private PPPreferencesHelper prefsHelper;

	@Override
	protected void configureCurlyBracesBlock(IEditStrategyAcceptor acceptor) {
		if(prefsHelper.isAutoBraceInsertWanted())
			super.configureCurlyBracesBlock(acceptor);
	}

	protected void configureDqStringLiteral(IEditStrategyAcceptor acceptor) {
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
		if(prefsHelper.isAutoMLCommentInsertWanted())
			super.configureMultilineComments(acceptor);
	}

	@Override
	protected void configureParenthesis(IEditStrategyAcceptor acceptor) {
		if(prefsHelper.isAutoParenthesisInsertWanted())
			super.configureParenthesis(acceptor);
	}

	protected void configureSqStringLiteral(IEditStrategyAcceptor acceptor) {
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
		if(prefsHelper.isAutoBracketInsertWanted())
			super.configureSquareBrackets(acceptor);
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
		if(prefsHelper.isAutoSqStringInsertWanted())
			configureSqStringLiteral(acceptor);
		if(prefsHelper.isAutoDqStringInsertWanted())
			configureDqStringLiteral(acceptor);

	}
}
