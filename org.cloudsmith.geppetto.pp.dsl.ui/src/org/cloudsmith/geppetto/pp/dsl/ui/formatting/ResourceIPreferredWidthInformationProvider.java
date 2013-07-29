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
package org.cloudsmith.geppetto.pp.dsl.ui.formatting;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.FormatterGeneralPreferences;
import org.cloudsmith.xtext.formatting.IPreferredMaxWidthInformation;
import org.cloudsmith.xtext.ui.resource.PlatformResourceSpecificProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.formatting.IIndentationInformation;

import com.google.inject.Inject;

/**
 * A {@link Provider} of {@link IIndentationInformation} that can look up information specific to the current
 * resource.
 * 
 */
public class ResourceIPreferredWidthInformationProvider extends
		PlatformResourceSpecificProvider<IPreferredMaxWidthInformation> {
	@Inject
	private FormatterGeneralPreferences formatterPreferences;

	@Override
	protected IPreferredMaxWidthInformation dataForResource(IResource resource) {
		final int maxWidth = formatterPreferences.getPreferredMaxWidth(resource);
		return new IPreferredMaxWidthInformation() {

			@Override
			public int getPreferredMaxWidth() {
				return maxWidth;
			}

		};
	}

}
