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
package com.puppetlabs.xtext.ui.editor.formatting;

import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import com.puppetlabs.xtext.ui.resource.PlatformResourceSpecificProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * A @ link ResourceScoped} {@link Provider} of {@link ILinkSeparatorInformation} that finds the
 * line separator information to use in the following order:
 * <ol>
 * <li>The closest existing IResource for the {@code resourceURI} is determined.</li>
 * <li>The project preference line separator.</li>
 * <li>The workspace preference for line separator.</li>
 * <li>The system default line separator.</li>
 * </ol>
 * The first found line separator information is used. Never produces a null result.
 */
public class ResourceILineSeparatorProvider extends PlatformResourceSpecificProvider<ILineSeparatorInformation> {

	/**
	 * Returns the system default line separator.
	 * 
	 * @return The line separator to use.
	 */
	private static String getLineSeparator() {
		return System.getProperty("line.separator"); //$NON-NLS-1$;
	}

	/**
	 * Returns the line separator defined in preference {@link org.eclipse.core.runtime.Platform#PREF_LINE_SEPARATOR} on
	 * the project or workspace of the given resource.
	 * If this is null, returns the platform separator.
	 * 
	 * @return The line separator to use.
	 */
	private static String getLineSeparator(IResource r) {
		if(r != null) {
			final IPreferencesService prefs = Platform.getPreferencesService();
			IScopeContext[] scopeContext = new IScopeContext[] { new ProjectScope(r.getProject()) };
			String lineSeparator = prefs.getString(
				Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, scopeContext);
			if(lineSeparator != null)
				return lineSeparator;
			// line delimiter in workspace preference
			scopeContext = new IScopeContext[] { InstanceScope.INSTANCE };
			lineSeparator = prefs.getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, scopeContext);
			if(lineSeparator != null)
				return lineSeparator;
		}
		return getLineSeparator();
	}

	@Override
	protected ILineSeparatorInformation dataForResource(IResource resource) {
		final String lineSeparator = getLineSeparator(resource);
		return new ILineSeparatorInformation() {

			@Override
			public String getLineSeparator() {
				return lineSeparator;
			}
		};
	}
}
