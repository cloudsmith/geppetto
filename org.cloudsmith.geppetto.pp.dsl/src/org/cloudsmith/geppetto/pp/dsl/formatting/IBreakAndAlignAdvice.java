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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import com.google.inject.ImplementedBy;

/**
 * Formatting advisor for breaks and alignments
 * 
 */
@ImplementedBy(IBreakAndAlignAdvice.Default.class)
public interface IBreakAndAlignAdvice {

	public static class Default implements IBreakAndAlignAdvice {

		/**
		 * @return 20
		 */
		@Override
		public int clusterSize() {
			return 20;
		}

		@Override
		public boolean compactCasesWhenPossible() {
			return true;
		}

		/**
		 * @return OnOverflow
		 */
		@Override
		public WhenToApplyForDefinition definitionParameterListAdvice() {
			return WhenToApplyForDefinition.OnOverflow;
		}

		/**
		 * @return OnOverflow
		 */
		@Override
		public WhenToApply hashesAdvice() {
			return WhenToApply.OnOverflow;
		}

		/**
		 * @return true
		 */
		@Override
		public boolean isAlignCases() {
			return true;
		}

		/**
		 * @return OnOverflow
		 */
		@Override
		public WhenToApply listsAdvice() {
			return WhenToApply.OnOverflow;
		}
	}

	public enum WhenToApply {
		Always, OnOverflow, Never, ;
	}

	public enum WhenToApplyForDefinition {
		Always, DefaultsPresent, OnOverflow, Never, ;
	}

	/**
	 * When aligning, what is the max cluster size (amount of max inserted padding when aligning).
	 */
	public int clusterSize();

	/**
	 * Should cases be compacted if possible?
	 */
	public boolean compactCasesWhenPossible();

	/**
	 * When to break align definition argument lists (i.e. class and definition parameters)
	 */
	public WhenToApplyForDefinition definitionParameterListAdvice();

	/**
	 * When to break/align hashes
	 */
	public WhenToApply hashesAdvice();

	/**
	 * If cases should be aligned on ':'
	 */
	boolean isAlignCases();

	/**
	 * When to break/align lists
	 */
	public WhenToApply listsAdvice();
}
