/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs - initial API and implementation
 */
package com.puppetlabs.geppetto.injectable.eclipse;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
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

	private static final String ORG_CLOUDSMITH = "org.cloudsmith.";

	private static final String COM_PUPPETLABS = "com.puppetlabs.";

	// This is a hack to preserve workspaces created with a Geppetto that used the 'org.cloudsmith.'
	// package and bundle naming. It ensures that the bundle states and workspace preferences are
	// changed accordingly.
	//
	private static void renameCloudsmithPrefs(Bundle bundle) {
		try {
			IPath wsRoot = Platform.getLocation();
			IPath bundleStateRoot = wsRoot.append(".metadata").append(".plugins");
			File[] bundleDirs = bundleStateRoot.toFile().listFiles();
			if(bundleDirs == null) {
				System.out.format("%s is not a directory\n", bundleStateRoot.toOSString());
				return;
			}

			for(File bundleDir : bundleDirs) {
				String name = bundleDir.getName();
				if(!name.startsWith(ORG_CLOUDSMITH))
					continue;

				String newName = COM_PUPPETLABS + name.substring(ORG_CLOUDSMITH.length());
				File newDir = new File(bundleDir.getParentFile(), newName);
				if(!newDir.exists()) {
					bundleDir.renameTo(newDir);
					System.out.format("Renamed %s to %s\n", bundleDir.getAbsolutePath(), newName);
				}
			}

			IPath settingsRoot = bundleStateRoot.append("org.eclipse.core.runtime").append(".settings");
			File[] prefsFiles = settingsRoot.toFile().listFiles();
			if(prefsFiles == null) {
				System.out.format("%s is not a directory\n", settingsRoot.toOSString());
				return;
			}

			for(File prefsFile : prefsFiles) {
				String name = prefsFile.getName();
				if(!name.startsWith(ORG_CLOUDSMITH))
					continue;

				String changed = "";
				String newName = COM_PUPPETLABS + name.substring(ORG_CLOUDSMITH.length());
				File newFile = new File(prefsFile.getParentFile(), newName);
				Reader reader = new FileReader(prefsFile);
				try {
					StringBuilder bld = new StringBuilder();
					char[] buf = new char[1024];
					int count;
					while((count = reader.read(buf)) > 0)
						bld.append(buf, 0, count);
					int nxt = bld.indexOf(ORG_CLOUDSMITH);
					while(nxt >= 0) {
						bld.replace(nxt, nxt + ORG_CLOUDSMITH.length(), COM_PUPPETLABS);
						nxt = bld.indexOf(ORG_CLOUDSMITH, nxt + ORG_CLOUDSMITH.length());
						changed = " and perfomed file substitutions";
					}
					Writer writer = new FileWriter(newFile);
					try {
						writer.write(bld.toString());
					}
					finally {
						writer.close();
					}
				}
				finally {
					reader.close();
				}
				System.out.format("Renamed %s to %s%s\n", prefsFile.getAbsolutePath(), newName, changed);
				prefsFile.delete();
			}

			File oldTP = wsRoot.append(".org_cloudsmith_geppetto_pptp_target").toFile();
			File newTP = wsRoot.append(".com_puppetlabs_geppetto_pptp_target").toFile();
			if(oldTP.exists() && !newTP.exists() && oldTP.renameTo(newTP))
				System.out.format("Renamed %s to %s\n", oldTP.getAbsolutePath(), newTP.getName());

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized IProxyService getProxyService() {
		if(proxyServiceReference == null) {
			proxyServiceReference = context.getServiceReference(IProxyService.class);
			proxyService = context.getService(proxyServiceReference);
		}
		return proxyService;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		renameCloudsmithPrefs(context.getBundle());

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
