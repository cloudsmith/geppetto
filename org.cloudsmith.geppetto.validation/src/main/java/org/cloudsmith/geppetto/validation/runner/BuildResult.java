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
package org.cloudsmith.geppetto.validation.runner;

/**
 * Provides access to any build result produced by "org.cloudsmith.geppetto.validation" (which should really be renamed to "build").
 * 
 */
public class BuildResult {

	private AllModuleReferences allModuleReferences;

	private RakefileInfo rakefileInfo;

	private boolean rubyServicesAvailable;

	public BuildResult(boolean rubyAvailable) {
		this.rubyServicesAvailable = rubyAvailable;
	}

	public AllModuleReferences getAllModuleReferences() {
		return allModuleReferences;
	}

	/**
	 * Get information about all rakefiles and their tasks discovered in the result, or
	 * null if rakefile information was not requested or possible to compute for a particular request.
	 * 
	 * @return
	 */
	public RakefileInfo getRakefileInfo() {
		return rakefileInfo;
	}

	public boolean isRubyServicesAvailable() {
		return rubyServicesAvailable;
	}

	public void setAllModuleReferences(AllModuleReferences allReferences) {
		this.allModuleReferences = allReferences;
	}

	public void setRakefileInfo(RakefileInfo rakefileInfo) {
		this.rakefileInfo = rakefileInfo;
	}

	public void setRubyServicesAvailable(boolean flag) {
		rubyServicesAvailable = flag;
	}

}
