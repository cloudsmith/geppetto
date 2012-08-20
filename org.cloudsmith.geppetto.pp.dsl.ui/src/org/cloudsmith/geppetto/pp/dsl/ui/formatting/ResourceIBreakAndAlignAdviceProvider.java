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

import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice;
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApply;
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApplyForDefinition;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.BreakAndAlignPreferences;
import org.cloudsmith.xtext.ui.resource.PlatformResourceSpecificProvider;
import org.eclipse.core.resources.IResource;

import com.google.inject.Inject;

/**
 * A {@link Provider} of {@link IBreakAndAlignAdvice} that can look up information specific to the current
 * resource.
 * 
 */
public class ResourceIBreakAndAlignAdviceProvider extends PlatformResourceSpecificProvider<IBreakAndAlignAdvice> {
	@Inject
	private BreakAndAlignPreferences formatterPreferences;

	@Override
	protected IBreakAndAlignAdvice dataForResource(IResource resource) {
		final int clusterSize = formatterPreferences.getClusterSize(resource);
		final WhenToApplyForDefinition definitionParameters = formatterPreferences.getDefinitionParametersAdvice(resource);
		final WhenToApply hashes = formatterPreferences.getHashesAdvice(resource);
		final WhenToApply lists = formatterPreferences.getListsAdvice(resource);
		final boolean compact = formatterPreferences.isCompactCases();
		return new IBreakAndAlignAdvice() {

			@Override
			public int clusterSize() {
				return clusterSize;
			}

			@Override
			public boolean compactCasesWhenPossible() {
				return compact;
			}

			@Override
			public WhenToApplyForDefinition definitionParameterListAdvice() {
				return definitionParameters;
			}

			@Override
			public WhenToApply hashesAdvice() {
				return hashes;
			}

			@Override
			public WhenToApply listsAdvice() {
				return lists;
			}
		};
	}

}
