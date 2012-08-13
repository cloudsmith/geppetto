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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentFormatterAdvice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Overrides default comment configuration with a '#' based single line comment container.
 * 
 */
public class PPCommentConfiguration extends ICommentConfiguration.Default {
	public static final String SL_FORMATTER_ADVICE_NAME = "SL_ADVICE";

	public static final String ML_FORMATTER_ADVICE_NAME = "ML_ADVICE";

	@Inject
	@Named(SL_FORMATTER_ADVICE_NAME)
	Provider<ICommentFormatterAdvice> slAdvice;

	@Inject
	@Named(ML_FORMATTER_ADVICE_NAME)
	Provider<ICommentFormatterAdvice> mlAdvice;

	@Override
	public ICommentContainerInformation getContainerInformation(CommentType commentType) {
		if(commentType == CommentType.SingleLine) {
			return new ICommentContainerInformation.HashSLCommentContainer();
		}
		return super.getContainerInformation(commentType);
	}

	/**
	 * Allows different advice per comment type.
	 */
	@Override
	public ICommentFormatterAdvice getFormatterAdvice(CommentType commentType) {
		// Preconditions.checkArgument(genericCommentType instanceof CommentType, "Must be an instance of CommentType");
		// CommentType commentType = (CommentType) genericCommentType;
		switch(commentType) {
			case SingleLine:
				return slAdvice.get();
			case Multiline:
				return mlAdvice.get();
		}
		// should not really happen - something is wrong, but return the Default advice.
		return super.getFormatterAdvice(commentType);
	}
}
