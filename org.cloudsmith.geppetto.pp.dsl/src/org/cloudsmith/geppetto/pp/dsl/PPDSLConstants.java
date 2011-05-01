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
package org.cloudsmith.geppetto.pp.dsl;

/**
 * Constants for PP DSL
 * 
 */
public interface PPDSLConstants {
	public static final String DSL_PLUGIN_NAME = "org.cloudsmith.geppetto.pp.dsl";

	public static final String PP_DEBUG_LINKER = DSL_PLUGIN_NAME + "/debug/linker";

	public static final String PPTP_RUBY_LANGUAGE_NAME = "org.cloudsmith.geppetto.pp.dsl.PPTP.RB";

	public static final String PPTP_LANGUAGE_NAME = "org.cloudsmith.geppetto.pp.dsl.PPTP";

	public static final String PPTP_RUBY_EXTENSION = "rb";

	public static final String PPTP_EXTENSION = "pptp";

	/**
	 * Key used in IEObjectDescription data for parent name (i.e. inheritance).
	 */
	public static final String PARENT_NAME_DATA = "org.cloudsmith.geppetto.pp.dsl.parent";

}
