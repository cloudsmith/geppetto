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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.CommentPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.EnumPreferenceFieldEditor;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.BannerAdvice;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.CommentTextAdvice;
import org.eclipse.jface.preference.BooleanFieldEditor;

/**
 * This is the puppet preference pane for comment formatting.
 * 
 */
public class PPCommentsPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		BooleanFieldEditor formatSLEditor = new BooleanFieldEditor(
			CommentPreferences.FORMATTER_COMMENTS_SL_ENABLED, "Format # comments", getFieldEditorParent());
		addField(formatSLEditor);

		BooleanFieldEditor formatMLEditor = new BooleanFieldEditor(
			CommentPreferences.FORMATTER_COMMENTS_ML_ENABLED, "Format /* */ comments", getFieldEditorParent());
		addField(formatMLEditor);

		EnumPreferenceFieldEditor commentTextEditor = new EnumPreferenceFieldEditor(
			CommentTextAdvice.class, CommentPreferences.FORMATTER_COMMENTS_TEXT, "When comment text is too wide",
			getFieldEditorParent());
		addField(commentTextEditor);

		EnumPreferenceFieldEditor bannerLinesEditor = new EnumPreferenceFieldEditor(
			BannerAdvice.class, CommentPreferences.FORMATTER_COMMENTS_BANNERS, "When 'banner line' is too wide",
			getFieldEditorParent());
		addField(bannerLinesEditor);

		BooleanFieldEditor alignSpecialLeft = new BooleanFieldEditor(
			CommentPreferences.FORMATTER_COMMENTS_SPECIAL_LINES_ALIGNMENT,
			"Place special lines ('--', '++', ...) left", getFieldEditorParent());
		addField(alignSpecialLeft);

		BooleanFieldEditor doubleDollarVerbatim = new BooleanFieldEditor(
			CommentPreferences.FORMATTER_COMMENTS_VERBATIM_DOUBLEDOLLAR,
			"Spaces in text between two '$' are non-breaking", getFieldEditorParent());
		addField(doubleDollarVerbatim);
	}

	@Override
	protected String qualifiedName() {
		return CommentPreferences.FORMATTER_COMMENTS_ID;
	}

}
