package org.cloudsmith.geppetto.validation;

import org.cloudsmith.geppetto.validation.impl.ValidationServiceImpl;

public class ValidationServiceFactory {
	public static ValidationService createValidationService() {
		return new ValidationServiceImpl();
	}
}
