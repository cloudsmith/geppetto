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
package org.cloudsmith.geppetto.pp.dsl.target;

import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;
import org.cloudsmith.geppetto.semver.Version;
import org.eclipse.emf.common.util.URI;

public enum PuppetTarget {
	// @fmtOff
	PUPPET26("2.6.9", "2.6", "puppet-2.6.9.pptp", false, ComplianceLevel.PUPPET_2_6),
	PUPPET27("2.7.19", "2.7", "puppet-2.7.19.pptp", false, ComplianceLevel.PUPPET_2_7),
	PUPPET30("3.0.0", "3.0", "puppet-3.0.0.pptp", false, ComplianceLevel.PUPPET_3_0),
	PUPPET_ENTERPRISE20("2.7.19", "PE 2.0", "puppet-2.7.19.pptp", true, ComplianceLevel.PUPPET_2_7),
	PUPPET_ENTERPRISE27("2.7.19", "PE 2.7", "puppet-2.7.19.pptp", true, ComplianceLevel.PUPPET_2_7);
	// @fmtOn

	/**
	 * Returns the PuppetTarget with a literal equal to the argument
	 * 
	 * @param literal
	 *            The argument to match
	 * @return The matching target
	 * @throws IllegalArgumentException
	 *             if no match is found
	 */
	public static PuppetTarget forLiteral(String literal) {
		if(literal != null)
			for(PuppetTarget pt : values())
				if(literal.equals(pt.getLiteral()))
					return pt;
		throw new IllegalArgumentException("No PuppetTarget matches literal '" + literal + '\'');
	}

	public static PuppetTarget getDefault() {
		return PUPPET27;
	}

	public static PuppetTarget forComplianceLevel(ComplianceLevel level, boolean enterprise) {
		for(PuppetTarget target : values())
			if(target.complianceLevel == level && target.enterprise == enterprise)
				return target;
		StringBuilder bld = new StringBuilder();
		bld.append("No ");
		if(enterprise)
			bld.append("enterprise ");
		bld.append("target found for compliance level ");
		bld.append(level);
		throw new IllegalArgumentException(bld.toString());
	}

	private final Version version;

	private final String literal;

	private final boolean enterprise;

	private final ComplianceLevel complianceLevel;

	private final URI targetURI;

	PuppetTarget(String version, String literal, String targetURI, boolean enterprise, ComplianceLevel complianceLevel) {
		this.version = Version.create(version);
		this.literal = literal;
		this.targetURI = PptpResourceUtil.getURI(targetURI);
		this.enterprise = enterprise;
		this.complianceLevel = complianceLevel;
	}

	public ComplianceLevel getComplianceLevel() {
		return complianceLevel;
	}

	public String getLiteral() {
		return literal;
	}

	public URI getPlatformURI() {
		return targetURI;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isPuppetEnterprise() {
		return enterprise;
	}
}
