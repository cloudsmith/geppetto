/**
 * 
 */
package com.puppetlabs.geppetto.injectable.eclipse;

import org.eclipse.core.net.proxy.IProxyService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author thhal
 */
public class Activator implements BundleActivator {

	private static Activator instance;

	public static Activator getInstance() {
		Activator a = instance;
		if(a == null)
			throw new IllegalStateException("Bundle is not active");
		return a;
	}

	private BundleContext context;

	private ServiceReference<IProxyService> proxyServiceReference;

	private IProxyService proxyService;

	public synchronized IProxyService getProxyService() {
		if(proxyServiceReference == null) {
			proxyServiceReference = context.getServiceReference(IProxyService.class);
			proxyService = context.getService(proxyServiceReference);
		}
		return proxyService;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		if(proxyServiceReference != null) {
			context.ungetService(proxyServiceReference);
			proxyServiceReference = null;
			proxyService = null;
		}
	}
}
