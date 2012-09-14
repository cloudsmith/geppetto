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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.data;

import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApply;
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApplyForDefinition;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Manages the Break and Align related formatter preferences.
 * 
 */
public class BreakAndAlignPreferences extends AbstractPreferenceData {
	public static final String FORMATTER_ALIGN_ID = "org.cloudsmith.geppetto.pp.dsl.PP.formatter.align";

	public static final String FORMATTER_ALIGN_USE_PROJECT_SETTINGS = FORMATTER_ALIGN_ID + "." +
			PPPreferenceConstants.USE_PROJECT_SETTINGS;

	public static final String FORMATTER_ALIGN_DEFINITION_PARAMS = "formatAlignDefinitionParams";

	public static final String FORMATTER_ALIGN_LISTS = "formatAlignLists";

	public static final String FORMATTER_ALIGN_HASHES = "formatAlignHashes";

	public static final String FORMATTER_COMPACT_CASES = "formatCompactCases";

	public static final String FORMATTER_COMPACT_RESOURCES = "formatCompactResources";

	public static final String FORMATTER_ALIGN_CASES = "formatAlignCases";

	public static final String FORMATTER_ALIGN_CLUSTERWIDTH = "formatClusterWidth";

	@Override
	protected void doInitialize(IPreferenceStore store) {
		store.setDefault(FORMATTER_ALIGN_CLUSTERWIDTH, 20);
		store.setDefault(FORMATTER_ALIGN_DEFINITION_PARAMS, WhenToApplyForDefinition.OnOverflow.toString());
		store.setDefault(FORMATTER_ALIGN_LISTS, WhenToApply.OnOverflow.toString());
		store.setDefault(FORMATTER_ALIGN_HASHES, WhenToApply.OnOverflow.toString());
		store.setDefault(FORMATTER_COMPACT_CASES, true);
		store.setDefault(FORMATTER_COMPACT_RESOURCES, true);
		store.setDefault(FORMATTER_ALIGN_CASES, true);
	}

	/**
	 * Returns max cluster width
	 * 
	 * @return max alignment padding (cluster width)
	 */
	public int getClusterSize() {
		return getInt(FORMATTER_ALIGN_CLUSTERWIDTH);
	}

	/**
	 * Returns max cluster width
	 * 
	 * @return max alignment padding (cluster width)
	 */
	public int getClusterSize(IResource r) {
		return getContextualInt(r, FORMATTER_ALIGN_CLUSTERWIDTH);
	}

	/**
	 * Returns when definition parameter lists should be aligned
	 * 
	 * @see WhenToApplyForDefinition
	 * @return when definition parameter lists should be aligned
	 */
	public WhenToApplyForDefinition getDefinitionParametersAdvice() {
		return getEnum(
			WhenToApplyForDefinition.class, FORMATTER_ALIGN_DEFINITION_PARAMS, WhenToApplyForDefinition.OnOverflow);
	}

	/**
	 * Returns when definition parameter lists should be aligned
	 * 
	 * @see WhenToApplyForDefinition
	 * @return when definition parameter lists should be aligned
	 */
	public WhenToApplyForDefinition getDefinitionParametersAdvice(IResource r) {
		return getContextualEnum(
			r, WhenToApplyForDefinition.class, FORMATTER_ALIGN_DEFINITION_PARAMS, WhenToApplyForDefinition.OnOverflow);
	}

	/**
	 * Returns when hashes should be aligned
	 * 
	 * @see WhenToApply
	 * @return when hashes should be aligned
	 */
	public WhenToApply getHashesAdvice() {
		return getEnum(WhenToApply.class, FORMATTER_ALIGN_HASHES, WhenToApply.OnOverflow);
	}

	/**
	 * Returns when hashes should be aligned
	 * 
	 * @see WhenToApply
	 * @return when hashes should be aligned
	 */
	public WhenToApply getHashesAdvice(IResource r) {
		return getContextualEnum(r, WhenToApply.class, FORMATTER_ALIGN_HASHES, WhenToApply.OnOverflow);
	}

	/**
	 * Returns when lists should be aligned
	 * 
	 * @see WhenToApply
	 * @return when lists should be aligned
	 */
	public WhenToApply getListsAdvice() {
		return getEnum(WhenToApply.class, FORMATTER_ALIGN_LISTS, WhenToApply.OnOverflow);
	}

	/**
	 * Returns when lists should be aligned
	 * 
	 * @see WhenToApply
	 * @return when lists should be aligned
	 */
	public WhenToApply getListsAdvice(IResource r) {
		return getContextualEnum(r, WhenToApply.class, FORMATTER_ALIGN_LISTS, WhenToApply.OnOverflow);
	}

	@Override
	public String getUseProjectSettingsID() {
		return FORMATTER_ALIGN_USE_PROJECT_SETTINGS;
	}

	public boolean isAlignCases() {
		return getBoolean(FORMATTER_ALIGN_CASES);
	}

	public boolean isAlignCases(IResource r) {
		return getContextualBoolean(r, FORMATTER_ALIGN_CASES);
	}

	/**
	 * Returns if compactable cases should be rendered in compact form
	 * 
	 * @return true if compactable cases should be rendered in compact form
	 */
	public boolean isCompactCases() {
		return getBoolean(FORMATTER_COMPACT_CASES);
	}

	/**
	 * Returns if compactable cases should be rendered in compact form
	 * 
	 * @return true if compactable cases should be rendered in compact form
	 */
	public boolean isCompactCases(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMPACT_CASES);
	}

	/**
	 * Returns if compactable cases should be rendered in compact form
	 * 
	 * @return true if compactable cases should be rendered in compact form
	 */
	public boolean isCompactResources() {
		return getBoolean(FORMATTER_COMPACT_RESOURCES);
	}

	/**
	 * Returns if compactable resources should be rendered in compact form
	 * 
	 * @return true if compactable resources should be rendered in compact form
	 */
	public boolean isCompactResources(IResource r) {
		return getContextualBoolean(r, FORMATTER_COMPACT_RESOURCES);
	}

}
