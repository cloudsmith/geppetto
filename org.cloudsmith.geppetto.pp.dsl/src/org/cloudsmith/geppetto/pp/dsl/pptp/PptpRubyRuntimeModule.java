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
package org.cloudsmith.geppetto.pp.dsl.pptp;

import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;

/**
 * A runtime module for PPTP RUBY model.
 * 
 */
public class PptpRubyRuntimeModule extends PptpRuntimeModule {
	/**
	 * Note: Ruby < 1.9 does not handle encoding at all (it expects single byte UsAscii). Ruby 1.9 has
	 * a comment line e.g. '# encoding : utf-8'. This declaration is not really used as the ruby parser is
	 * simply given an input stream without any encoding, but an encoding provider must still be provided (to prevent the
	 * default XMLEncodingProvider from opening the file and not finding any XML (and hence no encoding specification)).
	 * 
	 * A provider returning the default charset for the platform is used here.
	 */
	@Override
	public Class<? extends IEncodingProvider> bindIEncodingProvider() {
		return IEncodingProvider.Runtime.class;
	}

	/**
	 * Binds a provider that skips .rb files that are on "uninteresting" paths.
	 * 
	 * @see org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule#bindIResourceServiceProvider()
	 */
	@Override
	public Class<? extends IResourceServiceProvider> bindIResourceServiceProvider() {
		return PptpRubyResourceServiceProvider.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule#getFileExtensions()
	 */
	@Override
	protected String getFileExtensions() {
		return PPDSLConstants.PPTP_RUBY_EXTENSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule#getLanguageName()
	 */
	@Override
	protected String getLanguageName() {
		return PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME;
	}

}
