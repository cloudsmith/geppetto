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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import com.google.inject.Singleton;

/**
 * A facade that helps with preference checking.
 * (The idea is to not litter the code with specifics about how preferences are stated, where they are stored etc.)
 * 
 */
@Singleton
public class PPPreferencesHelper {

	private int autoInsertOverrides = 0;

	private final static String OVERRIDE_AUTO_INSERT = "org.cloudsmith.geppetto.override.autoinsert";

	private final static int OVERRIDE_AUTO_INSERT_BRACKETS = 0x01;

	private final static int OVERRIDE_AUTO_INSERT_BRACES = 0x02;

	private final static int OVERRIDE_AUTO_INSERT_PARENTHESES = 0x04;

	private final static int OVERRIDE_AUTO_INSERT_COMMENT = 0x08;

	private final static int OVERRIDE_AUTO_INSERT_SQ = 0x10;

	private final static int OVERRIDE_AUTO_INSERT_DQ = 0x20;

	public PPPreferencesHelper() {
		configureAutoInsertOverride();
	}

	private void configureAutoInsertOverride() {
		String propValue = System.getProperty(OVERRIDE_AUTO_INSERT, "0");

		try {
			autoInsertOverrides = Integer.parseInt(propValue);
		}
		catch(NumberFormatException e) {
			System.err.println("Faulty integer format for property: " + OVERRIDE_AUTO_INSERT + " was: " + propValue);
			autoInsertOverrides = 0;
		}
	}

	public boolean isAutoBraceInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_BRACES) == 0;
	}

	public boolean isAutoBracketInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_BRACKETS) == 0;
	}

	public boolean isAutoDqStringInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_DQ) == 0;
	}

	public boolean isAutoMLCommentInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_COMMENT) == 0;
	}

	public boolean isAutoParenthesisInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_PARENTHESES) == 0;
	}

	public boolean isAutoSqStringInsertWanted() {
		return (autoInsertOverrides & OVERRIDE_AUTO_INSERT_SQ) == 0;
	}
}
