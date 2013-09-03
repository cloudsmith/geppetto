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
package com.puppetlabs.geppetto.pp.dsl.ui.formatting;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.data.FormatterGeneralPreferences;
import com.puppetlabs.xtext.textflow.CharSequences;
import com.puppetlabs.xtext.ui.resource.PlatformResourceSpecificProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.formatting.IIndentationInformation;

import com.google.inject.Inject;

/**
 * A {@link Provider} of {@link IIndentationInformation} that can look up information specific to the current
 * resource.
 * 
 */
public class ResourceIIndentationInformationProvider extends PlatformResourceSpecificProvider<IIndentationInformation> {
	@Inject
	private FormatterGeneralPreferences formatterPreferences;

	@Override
	protected IIndentationInformation dataForResource(IResource resource) {
		int indentSize = formatterPreferences.getIndentSize(resource);
		final String indentString = CharSequences.spaces(indentSize).toString();
		return new IIndentationInformation() {

			@Override
			public String getIndentString() {
				return indentString;
			}

		};
	}

}
