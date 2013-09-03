/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.validation;

import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import org.cloudsmith.geppetto.validation.runner.IEncodingProvider;
import org.eclipse.emf.common.util.URI;

public class ValidationOptions {
	private ComplianceLevel complianceLevel;

	private IEncodingProvider encodingProvider;

	private String environment;

	private FileType fileType;

	private URI platformURI;

	private IPotentialProblemsAdvisor problemsAdvisor;

	private String searchPath;

	private boolean checkLayout;

	private boolean checkModuleSemantics;

	private boolean checkReferences;

	/**
	 * Defaults to 2.7 if not specified.
	 * 
	 * @return the value of the '<em>complianceLevel</em>' attribute.
	 */
	public ComplianceLevel getComplianceLevel() {
		return complianceLevel;
	}

	/**
	 * @return the value of the '<em>encodingProvider</em>' attribute.
	 */
	public IEncodingProvider getEncodingProvider() {
		return encodingProvider;
	}

	/**
	 * @return the value of the '<em>environment</em>' attribute.
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @return the value of the '<em>fileType</em>' attribute.
	 */
	public FileType getFileType() {
		return fileType;
	}

	/**
	 * A URI to a pptp resource in string form. If null, a default pptp will be
	 * used when validating. An unloadable pptp reference will result in an
	 * internal error.
	 * 
	 * @return the value of the '<em>platformURI</em>' attribute.
	 */
	public URI getPlatformURI() {
		return platformURI;
	}

	/**
	 * @return the value of the '<em>problemsAdvisor</em>' attribute.
	 */
	public IPotentialProblemsAdvisor getProblemsAdvisor() {
		return problemsAdvisor;
	}

	/**
	 * @return the value of the '<em>searchPath</em>' attribute.
	 */
	public String getSearchPath() {
		return searchPath;
	}

	/**
	 * @return the value of the '<em>checkLayout</em>' attribute.
	 */
	public boolean isCheckLayout() {
		return checkLayout;
	}

	/**
	 * Checking module semantics means that the module's dependencies are
	 * satisfied.
	 * 
	 * @return the value of the '<em>checkModuleSemantics</em>' attribute.
	 */
	public boolean isCheckModuleSemantics() {
		return checkModuleSemantics;
	}

	/**
	 * 
	 * @return the value of the '<em>checkReferences</em>' attribute.
	 */
	public boolean isCheckReferences() {
		return checkReferences;
	}

	/**
	 * Sets the value of the '<em>checkLayout</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>checkLayout</em>' attribute.
	 */
	public void setCheckLayout(boolean value) {
		checkLayout = value;
	}

	/**
	 * Sets the value of the '<em>checkModuleSemantics</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>checkModuleSemantics</em>'
	 *            attribute.
	 */
	public void setCheckModuleSemantics(boolean value) {
		checkModuleSemantics = value;
	}

	/**
	 * Sets the value of the '<em>checkReferences</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>checkReferences</em>' attribute.
	 */
	public void setCheckReferences(boolean value) {
		checkReferences = value;
	}

	/**
	 * Sets the value of the '<em>complianceLevel</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>complianceLevel</em>' attribute.
	 */
	public void setComplianceLevel(ComplianceLevel value) {
		complianceLevel = value;
	}

	/**
	 * Sets the value of the '<em>encodingProvider</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>encodingProvider</em>' attribute.
	 */
	public void setEncodingProvider(IEncodingProvider value) {
		encodingProvider = value;
	}

	/**
	 * Sets the value of the '<em>environment</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>environment</em>' attribute.
	 */
	public void setEnvironment(String value) {
		environment = value;
	}

	/**
	 * Sets the value of the '<em>fileType</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>fileType</em>' attribute.
	 */
	public void setFileType(FileType value) {
		fileType = value;
	}

	/**
	 * Sets the value of the '<em>platformURI</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>platformURI</em>' attribute.
	 */
	public void setPlatformURI(URI value) {
		platformURI = value;
	}

	/**
	 * Sets the value of the '<em>problemsAdvisor</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>problemsAdvisor</em>' attribute.
	 */
	public void setProblemsAdvisor(IPotentialProblemsAdvisor value) {
		problemsAdvisor = value;
	}

	/**
	 * Sets the value of the '<em>searchPath</em>' attribute.
	 * 
	 * @param value
	 *            the new value of the '<em>searchPath</em>' attribute.
	 */
	public void setSearchPath(String value) {
		searchPath = value;
	}
}
