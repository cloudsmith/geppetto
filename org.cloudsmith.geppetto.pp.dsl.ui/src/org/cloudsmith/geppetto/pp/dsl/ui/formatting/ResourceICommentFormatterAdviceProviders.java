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
package org.cloudsmith.geppetto.pp.dsl.ui.formatting;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.CommentPreferences;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.BannerAdvice;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice.CommentTextAdvice;
import org.cloudsmith.xtext.ui.resource.PlatformResourceSpecificProvider;
import org.eclipse.core.resources.IResource;

import com.google.inject.Inject;

/**
 * A {@link Provider} of {@link IIndentationInformation} that can look up information specific to the current
 * resource.
 * 
 */
public class ResourceICommentFormatterAdviceProviders {

	private abstract static class CommonCommentAdviceProvider extends
			PlatformResourceSpecificProvider<ICommentFormatterAdvice> {
		@Inject
		protected CommentPreferences commentPreferences;

		@Override
		protected ICommentFormatterAdvice dataForResource(IResource resource) {

			final boolean isEnabled = getIsEnabled(resource);
			final boolean alignSpecialLeft = commentPreferences.getAlignSpecialLinesLeft(resource);
			final BannerAdvice bannerAdvice = commentPreferences.getBannerAdvice(resource);
			final CommentTextAdvice textAdvice = commentPreferences.getCommentTextAdvice(resource);
			final boolean isDoubleDollarVerbatim = commentPreferences.isDoubleDollarVerbatim(resource);

			return new ICommentFormatterAdvice() {

				@Override
				public boolean enabled() {
					return isEnabled;
				}

				@Override
				public boolean getAlignSpecialLinesLeft() {
					return alignSpecialLeft;
				}

				@Override
				public BannerAdvice getBannerAdvice() {
					return bannerAdvice;
				}

				@Override
				public CommentTextAdvice getCommentTextAdvice() {
					return textAdvice;
				}

				@Override
				public boolean isDoubleDollarVerbatim() {
					return isDoubleDollarVerbatim;
				}
			};
		}

		protected abstract boolean getIsEnabled(IResource resource);
	}

	public static class MLCommentAdviceProvider extends CommonCommentAdviceProvider {
		@Override
		protected boolean getIsEnabled(IResource resource) {
			return commentPreferences.isMLCommentEnabled(resource);
		}
	}

	public static class SLCommentAdviceProvider extends CommonCommentAdviceProvider {
		@Override
		protected boolean getIsEnabled(IResource resource) {
			return commentPreferences.isSLCommentEnabled(resource);
		}
	}
}
