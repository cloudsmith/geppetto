/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.cloudsmith.geppetto.pp.dsl.ui.builder.PPBuildJob;
import org.cloudsmith.geppetto.pp.dsl.ui.pptp.PptpTargetProjectHandler;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.ValidationPreference;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A facade that helps with preference checking.
 * (The idea is to not litter the code with specifics about how preferences are stated, where they are stored etc.)
 * 
 */
@Singleton
public class PPPreferencesHelper implements IPreferenceStoreInitializer, IPropertyChangeListener {

	private class RebuildChecker extends Job {
		private boolean drainAndBuild;

		/**
		 * The purpose of the RebuildChecker is to collect/aggregate events for validation preferences
		 * to avoid having to issue multiple rebuilds for a set of changes.
		 * This job reschedules itself.
		 */
		RebuildChecker() {
			super("Puppet RebuildChecker");
			setSystem(true);
			this.setUser(false);
			drainAndBuild = false;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			if(problemChanges.peek() == null) {
				drainAndBuild = false;
				this.schedule(500);
				return Status.OK_STATUS;
			}
			// if one event found, wait 500ms and then drain queue and rebuild
			if(!drainAndBuild) {
				drainAndBuild = true;
				this.schedule(500);
				return Status.OK_STATUS;
			}
			drainAndBuild = false;

			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;
			// drain the queue of everything pending
			List<String> drained = Lists.newArrayList();
			problemChanges.drainTo(drained);

			if(monitor.isCanceled())
				return Status.CANCEL_STATUS;

			// run a build
			PPBuildJob job = new PPBuildJob(workspace);
			job.schedule();
			this.schedule(1000);

			return Status.OK_STATUS;
		}

	}

	private int autoInsertOverrides = 0;

	private final static String OVERRIDE_AUTO_INSERT = "org.cloudsmith.geppetto.override.autoinsert";

	public final static int AUTO_INSERT_BRACKETS = 0x01;

	public final static int AUTO_INSERT_BRACES = 0x02;

	public final static int AUTO_INSERT_PARENTHESES = 0x04;

	public final static int AUTO_INSERT_COMMENT = 0x08;

	public final static int AUTO_INSERT_SQ = 0x10;

	public final static int AUTO_INSERT_DQ = 0x20;

	private IPreferenceStore store;

	@Inject
	private PptpTargetProjectHandler pptpHandler;

	private static final String defaultProjectPath = "lib/*:environments/$environment/*:manifests/*:modules/*"; //$NON-NLS-1$

	private static final String defaultPuppetEnvironment = "production"; //$NON-NLS-1$

	private static final String defaultForgeURI = "http://forge.puppetlabs.com"; //$NON-NLS-1$

	@Inject
	IWorkspace workspace;

	LinkedBlockingQueue<String> problemChanges;

	Job backgroundRebuildChecker;

	/**
	 * IMPORTANT:
	 * Add all preference that requires a rebuild when their value change.
	 */
	private List<String> requiresRebuild = Lists.newArrayList(//
		PPPreferenceConstants.PUPPET_TARGET_VERSION, //
		PPPreferenceConstants.PUPPET_ENVIRONMENT, //
		PPPreferenceConstants.PUPPET_PROJECT_PATH, //
		PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, //
		PPPreferenceConstants.PROBLEM_CIRCULAR_DEPENDENCY, //
		PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, //
		PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, //
		PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST, //
		PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST, //

		PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE, //
		PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED, //
		PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR, //
		PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION, //
		PPPreferenceConstants.PROBLEM_ML_COMMENTS, //
		PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP //

	);

	private IPreferenceStoreAccess preferenceStoreAccess;

	public PPPreferencesHelper() {
		configureAutoInsertOverride();
	}

	private void configureAutoInsertOverride() {
		String propValue = System.getProperty(OVERRIDE_AUTO_INSERT, "0");

		try {
			autoInsertOverrides = Integer.parseInt(propValue);
		}
		catch(NumberFormatException e) {
			autoInsertOverrides = 0;
		}
	}

	/**
	 * @return
	 */
	public ValidationPreference getBooleansInStringForm() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_BOOLEAN_STRING));
	}

	/**
	 * @return
	 */
	public ValidationPreference getCaseDefaultShouldAppearLast() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST));
	}

	/**
	 * @return
	 */
	public ValidationPreference getcircularDependencyPreference() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_CIRCULAR_DEPENDENCY));
	}

	/**
	 * @return
	 */
	public ValidationPreference getDqStringNotRequired() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED));
	}

	/**
	 * @return
	 */
	public ValidationPreference getDqStringNotRequiredVar() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR));
	}

	public String getForgeURI() {
		return store.getString(PPPreferenceConstants.FORGE_LOCATION);
	}

	public ValidationPreference getInterpolatedNonBraceEnclosedHypens() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN));
	}

	/**
	 * @return
	 */
	public ValidationPreference getMissingDefaultInSwitch() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_MISSING_DEFAULT));
	}

	/**
	 * @return
	 */
	public ValidationPreference getMLCommentsValidationPreference() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_ML_COMMENTS));
	}

	public String getPptpVersion() {
		String result = store.getString(PPPreferenceConstants.PUPPET_TARGET_VERSION);
		// there never was a 2.8, but older preferences settings may still have this string, use 3.0 instead
		if("2.8".equals(result))
			return "3.0";
		else if("PE 2.0".equals(result)) // PE 2.0 includes puppet 2.7
			return "2.7";
		return result;
	}

	private boolean getResourceSpecificBoolean(IResource r, String property) {
		// get project specific preference and use them if they are enabled
		IPreferenceStore store = preferenceStoreAccess.getContextPreferenceStore(r.getProject());
		return store.getBoolean(property);

	}

	public ValidationPreference getRightToLeftRelationships() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP));
	}

	public boolean getSaveActionEnsureEndsWithNewLine() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL);
	}

	public boolean getSaveActionEnsureEndsWithNewLine(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL)
				: getSaveActionEnsureEndsWithNewLine();
	}

	public boolean getSaveActionFormat() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_FORMAT);
	}

	public boolean getSaveActionFormat(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_FORMAT)
				: getSaveActionFormat();
	}

	public boolean getSaveActionReplaceFunkySpaces() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES);
	}

	public boolean getSaveActionReplaceFunkySpaces(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES)
				: getSaveActionReplaceFunkySpaces();
	}

	public boolean getSaveActionTrimLines() {
		return store.getBoolean(PPPreferenceConstants.SAVE_ACTION_TRIM_LINES);
	}

	public boolean getSaveActionTrimLines(IResource r) {
		boolean projectSpecific = getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTIONS_USE_PROJECT_SETTINGS);
		return projectSpecific
				? getResourceSpecificBoolean(r, PPPreferenceConstants.SAVE_ACTION_TRIM_LINES)
				: getSaveActionTrimLines();
	}

	public ValidationPreference getSelectorDefaultShouldAppearLast() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST));
	}

	public ValidationPreference getUnbracedInterpolation() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION));
	}

	public ValidationPreference getUnquotedResourceTitles() {
		return ValidationPreference.fromString(store.getString(PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE));
	}

	synchronized public IValidationAdvisor.ComplianceLevel getValidationComplianceLevel() {
		String result = store.getString(PPPreferenceConstants.PUPPET_TARGET_VERSION);
		if("2.6".equals(result))
			return IValidationAdvisor.ComplianceLevel.PUPPET_2_6;
		if("3.0".equals(result) || "2.8".equals(result))
			return IValidationAdvisor.ComplianceLevel.PUPPET_3_0;

		// for 2.7 and default
		return IValidationAdvisor.ComplianceLevel.PUPPET_2_7;
	}

	@Override
	synchronized public void initialize(IPreferenceStoreAccess access) {
		// if already initialized
		if(store != null)
			return;
		preferenceStoreAccess = access;
		store = preferenceStoreAccess.getWritablePreferenceStore();
		store.setDefault(PPPreferenceConstants.AUTO_EDIT_STRATEGY, 0);
		store.setDefault(PPPreferenceConstants.AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS, true);
		store.setDefault(PPPreferenceConstants.PUPPET_TARGET_VERSION, "3.0");
		store.setDefault(PPPreferenceConstants.PUPPET_PROJECT_PATH, defaultProjectPath);
		store.setDefault(PPPreferenceConstants.PUPPET_ENVIRONMENT, defaultPuppetEnvironment);
		store.setDefault(PPPreferenceConstants.FORGE_LOCATION, defaultForgeURI);

		store.setDefault(PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, ValidationPreference.WARNING.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_CIRCULAR_DEPENDENCY, ValidationPreference.WARNING.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, ValidationPreference.WARNING.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, ValidationPreference.WARNING.toString());

		// stylistic
		store.setDefault(PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST, ValidationPreference.IGNORE.toString());

		store.setDefault(PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED, ValidationPreference.IGNORE.toString());
		store.setDefault(
			PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_ML_COMMENTS, ValidationPreference.IGNORE.toString());
		store.setDefault(PPPreferenceConstants.PROBLEM_RTOL_RELATIONSHIP, ValidationPreference.IGNORE.toString());

		// save actions
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_TRIM_LINES, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES, false);
		store.setDefault(PPPreferenceConstants.SAVE_ACTION_FORMAT, false);

		autoInsertOverrides = (int) store.getLong(PPPreferenceConstants.AUTO_EDIT_STRATEGY);

		// Schedule the background job that makes rebuild after property changes more efficient
		// (Removes the need to run one rebuild per changing preference).
		problemChanges = new LinkedBlockingQueue<String>();
		backgroundRebuildChecker = new RebuildChecker();
		backgroundRebuildChecker.schedule();
		store.addPropertyChangeListener(this);

	}

	public boolean isAutoBraceInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_BRACES) == 0;
	}

	public boolean isAutoBracketInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_BRACKETS) == 0;
	}

	public boolean isAutoCompleteBlockWanted() {
		return store.getBoolean(PPPreferenceConstants.AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS);
	}

	public boolean isAutoDqStringInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_DQ) == 0;
	}

	public boolean isAutoMLCommentInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_COMMENT) == 0;
	}

	public boolean isAutoParenthesisInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_PARENTHESES) == 0;
	}

	public boolean isAutoSqStringInsertWanted() {
		return (autoInsertOverrides & AUTO_INSERT_SQ) == 0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// System.err.println("Preference changed value: " + event.getProperty());
		if(PPPreferenceConstants.AUTO_EDIT_STRATEGY.equals(event.getProperty()))
			autoInsertOverrides = Integer.valueOf((String) event.getNewValue());

		// If pptp changes, recheck the workspace
		if(PPPreferenceConstants.PUPPET_TARGET_VERSION.equals(event.getProperty())) {
			pptpHandler.initializePuppetTargetProject();
			// problemChanges.offer(event.getProperty());
		}
		if(requiresRebuild.contains(event.getProperty()))
			problemChanges.offer(event.getProperty());
		// System.out.println("PPHelper gets event:" + event.getProperty());
	}

	/**
	 * Stops the helper from checking for preference store changes and scheduling rebuilds.
	 */
	public void stop() {
		store.removePropertyChangeListener(this);
		if(backgroundRebuildChecker == null)
			return;
		if(!backgroundRebuildChecker.cancel()) {
			// if not in cancelable state, poke it harder
			Thread t = backgroundRebuildChecker.getThread();
			t.interrupt();
			try {
				// must wait for *job* to die
				backgroundRebuildChecker.join();
			}
			catch(InterruptedException e) {
				// ok ok, I was interrupted when stopping... no need to make it worse...
				System.err.println("Interrupted");
			}
		}
	}

}
