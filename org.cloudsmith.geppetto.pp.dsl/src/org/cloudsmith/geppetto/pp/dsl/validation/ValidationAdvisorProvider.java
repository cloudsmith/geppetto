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
package org.cloudsmith.geppetto.pp.dsl.validation;

import com.google.inject.Provider;

/**
 * A parameterized provider of validation advisor.
 * 
 */
public class ValidationAdvisorProvider<T extends IValidationAdvisor> implements Provider<IValidationAdvisor> {
	public static <T extends IValidationAdvisor> ValidationAdvisorProvider<T> create(
			IValidationAdvisor.ComplianceLevel level) {
		return new ValidationAdvisorProvider<T>(level);
	}

	private final IValidationAdvisor.ComplianceLevel level;

	public ValidationAdvisorProvider(IValidationAdvisor.ComplianceLevel level) {
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	@Override
	public IValidationAdvisor get() {
		return ValidationAdvisor.create(level);
	}

}
