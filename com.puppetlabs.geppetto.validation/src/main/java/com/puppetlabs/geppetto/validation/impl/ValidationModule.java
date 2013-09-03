package org.cloudsmith.geppetto.validation.impl;

import org.cloudsmith.geppetto.validation.ValidationService;

import com.google.inject.AbstractModule;

public class ValidationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ValidationService.class).to(ValidationServiceImpl.class);
	}
}
