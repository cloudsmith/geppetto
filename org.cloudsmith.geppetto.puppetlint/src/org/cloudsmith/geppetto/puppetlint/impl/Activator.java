package org.cloudsmith.geppetto.puppetlint.impl;

import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner;
import org.cloudsmith.geppetto.puppetlint.PuppetLintService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Activator extends PuppetLintService implements BundleActivator {

	private static Activator instance;

	public static Activator getInstance() {
		Activator activator = instance;
		if(activator == null)
			throw new IllegalStateException("Bundle has been stopped");
		return activator;
	}

	private Injector injector;

	@Override
	public PuppetLintRunner getPuppetLintRunner() {
		return injector.getInstance(PuppetLintRunner.class);
	}

	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		injector = Guice.createInjector(new ExternalModule());
	}

	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
	}
}
