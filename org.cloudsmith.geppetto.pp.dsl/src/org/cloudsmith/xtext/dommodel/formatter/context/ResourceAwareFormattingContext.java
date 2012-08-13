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
package org.cloudsmith.xtext.dommodel.formatter.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

/**
 * A provider of IFormattingContext that by default using {@link ResourceAwareFormattingContext#get()} produces
 * a non whitespace preserving context, as opposed to {@link ResourceAwareFormattingContext#getWhitespacePreserving()}.
 * 
 * <p>
 * Note that this Provider does <i>not</i> implement <code>com.google.inject.Provider&lt;IFormattingContext&gt;</code>
 * </p>
 */
public class ResourceAwareFormattingContext {

	public static class Factory implements IFormattingContextFactory {
		@Inject
		private DefaultFormattingContext.Factory defaultFactory;

		/**
		 * Provides a non whitespace preserving (i.e. "formatting") IFormattingContext
		 * 
		 */
		@Override
		public IFormattingContext create(EObject semantic) {
			return defaultFactory.create(semantic);
		}

		@Override
		public IFormattingContext create(EObject semantic, FormattingOption option) {
			return defaultFactory.create(semantic, option);
		}

		@Override
		public IFormattingContext create(XtextResource resource) {
			return defaultFactory.create(resource);
		}

		@Override
		public IFormattingContext create(XtextResource resource, FormattingOption option) {
			return defaultFactory.create(resource, option);
		}
	}

}
