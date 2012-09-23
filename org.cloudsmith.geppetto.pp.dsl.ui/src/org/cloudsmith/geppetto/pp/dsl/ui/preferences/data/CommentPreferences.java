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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.data;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.BannerAdvice;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.CommentTextAdvice;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Manages the Comment related formatter preferences.
 * 
 */
public class CommentPreferences extends AbstractPreferenceData {
	public static final String FORMATTER_COMMENTS_ID = "org.cloudsmith.geppetto.pp.dsl.PP.formatter.comments";

	public static final String FORMATTER_COMMENTS_USE_PROJECT_SETTINGS = FORMATTER_COMMENTS_ID + "." +
			PPPreferenceConstants.USE_PROJECT_SETTINGS;

	public static final String FORMATTER_COMMENTS_BANNERS = "formatCommentBanners";

	public static final String FORMATTER_COMMENTS_TEXT = "formatCommentText";

	public static final String FORMATTER_COMMENTS_SL_ENABLED = "formatSLComments";

	public static final String FORMATTER_COMMENTS_ML_ENABLED = "formatMLComments";

	public static final String FORMATTER_COMMENTS_SPECIAL_LINES_ALIGNMENT = "alignmentSpecialCommentLines";

	public static final String FORMATTER_COMMENTS_VERBATIM_DOUBLEDOLLAR = "verbatimDoubleDollar";

	@Override
	protected void doInitialize(IPreferenceStore store) {
		// formatting
		store.setDefault(FORMATTER_COMMENTS_BANNERS, BannerAdvice.Truncate.toString());
		store.setDefault(FORMATTER_COMMENTS_SL_ENABLED, "true");
		store.setDefault(FORMATTER_COMMENTS_ML_ENABLED, "true");
		store.setDefault(FORMATTER_COMMENTS_SPECIAL_LINES_ALIGNMENT, "true");
		store.setDefault(FORMATTER_COMMENTS_VERBATIM_DOUBLEDOLLAR, "true");
		store.setDefault(FORMATTER_COMMENTS_TEXT, CommentTextAdvice.Fold.toString());

	}

	/**
	 * Returns how <i>homogeneous</i> special lines (other than banners) should be aligned.
	 * 
	 * @return true, if line should be flush left
	 */
	public boolean getAlignSpecialLinesLeft() {
		return getBoolean(FORMATTER_COMMENTS_SPECIAL_LINES_ALIGNMENT);
	}

	/**
	 * Returns how <i>homogeneous</i> special lines (other than banners) should be aligned in the context of the given {@code IResource}.
	 * 
	 * @return true, if line should be flush left
	 */
	public boolean getAlignSpecialLinesLeft(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMMENTS_SPECIAL_LINES_ALIGNMENT);
	}

	/**
	 * Returns how <i>banner lines</li> should be formatted.
	 * 
	 * @see BannerAdvice
	 * @return how banner lines should be formatted
	 */
	public BannerAdvice getBannerAdvice() {
		return getEnum(BannerAdvice.class, FORMATTER_COMMENTS_BANNERS, BannerAdvice.Truncate);
	}

	/**
	 * Returns how <i>banner lines</li> should be formatted in the context of the given {@code IResource}.
	 * 
	 * @see BannerAdvice
	 * @return how banner lines should be formatted
	 */
	public BannerAdvice getBannerAdvice(IResource r) {
		return getContextualEnum(r, BannerAdvice.class, FORMATTER_COMMENTS_BANNERS, BannerAdvice.Truncate);
	}

	/**
	 * Returns how <i>comment text lines</li> should be formatted.
	 * 
	 * @see CommentTextAdvice
	 * @return how comment text lines should be formatted
	 */
	public CommentTextAdvice getCommentTextAdvice() {
		return getEnum(CommentTextAdvice.class, FORMATTER_COMMENTS_TEXT, CommentTextAdvice.Fold);

	}

	/**
	 * Returns how <i>comment text lines</li> should be formatted in the context of the given {@code IResource}.
	 * 
	 * @see CommentTextAdvice
	 * @return how comment text lines should be formatted
	 */
	public CommentTextAdvice getCommentTextAdvice(IResource r) {
		return getContextualEnum(r, CommentTextAdvice.class, FORMATTER_COMMENTS_TEXT, CommentTextAdvice.Fold);

	}

	@Override
	public String getUseProjectSettingsID() {
		return FORMATTER_COMMENTS_USE_PROJECT_SETTINGS;
	}

	/**
	 * Returns if embedded sequences of $ ... $ should be verbatim or not
	 * 
	 * @return true if text between $ should be verbatim
	 */
	public boolean isDoubleDollarVerbatim() {
		return getBoolean(FORMATTER_COMMENTS_VERBATIM_DOUBLEDOLLAR);
	}

	/**
	 * Returns if embedded sequences of $ ... $ should be verbatim or not in the context of the given resource {@code IResource}.
	 * 
	 * @return true if text between $ should be verbatim
	 */
	public boolean isDoubleDollarVerbatim(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMMENTS_VERBATIM_DOUBLEDOLLAR);
	}

	/**
	 * If the advice is enabled for ML comment, the more detailed advice should be used. If not, the comment should
	 * be formatted in verbatim/"preserve whitespace" mode.
	 * 
	 * @return true if the advice is enabled
	 */
	public boolean isMLCommentEnabled() {
		return getBoolean(FORMATTER_COMMENTS_ML_ENABLED);

	}

	/**
	 * If the advice is enabled for ML comment, the more detailed advice should be used. If not, the comment should
	 * be formatted in verbatim/"preserve whitespace" mode.
	 * 
	 * @return true if the advice is enabled
	 */
	public boolean isMLCommentEnabled(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMMENTS_ML_ENABLED);
	}

	/**
	 * If the advice is enabled for SL comment, the more detailed advice should be used. If not, the comment should
	 * be formatted in verbatim/"preserve whitespace" mode.
	 * 
	 * @return true if the advice is enabled
	 */
	public boolean isSLCommentEnabled() {
		return getBoolean(FORMATTER_COMMENTS_SL_ENABLED);

	}

	/**
	 * If the advice is enabled for SL comment, the more detailed advice should be used. If not, the comment should
	 * be formatted in verbatim/"preserve whitespace" mode.
	 * 
	 * @return true if the advice is enabled
	 */
	public boolean isSLCommentEnabled(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMMENTS_SL_ENABLED);
	}
}
