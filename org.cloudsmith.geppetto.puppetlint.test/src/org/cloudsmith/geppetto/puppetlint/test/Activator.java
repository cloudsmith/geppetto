package org.cloudsmith.geppetto.puppetlint.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		context = null;
	}

	/**
	 * Get a resource found in this bundle as a File. Extracting it
	 * into the filesystem if necessary.
	 * 
	 * @param bundleRelativeResourcePath
	 *            bundle relative path of the resource
	 * @return a {@link File} incarnation of the resource
	 * @throws IOException
	 */
	public static File getBundleResourceAsFile(IPath bundleRelativeResourcePath) throws IOException {
		URL resourceURL = FileLocator.find(context.getBundle(), bundleRelativeResourcePath, null);
		if(resourceURL == null)
			return null;

		resourceURL = FileLocator.toFileURL(resourceURL);
		try {
			return new File(URIUtil.toURI(resourceURL));
		}
		catch(URISyntaxException e) {
			throw new IllegalStateException("Failed to convert resource URL to URI", e);
		}
	}
}
