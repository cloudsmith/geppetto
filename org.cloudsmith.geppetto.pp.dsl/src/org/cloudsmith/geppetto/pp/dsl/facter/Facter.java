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
package org.cloudsmith.geppetto.pp.dsl.facter;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

/**
 * @author henrik
 * 
 */
public abstract class Facter {

	@Singleton
	public static class Facter1_6 extends Facter {
		List<String> argumentedFacts = ImmutableList.<String> of(//
			"arp_", // {NETWORK INTERFACE}
			"ec2_", // {EC2 INSTANCE DATA}
			"ipaddress_", // {NETWORK INTERFACE}
			"ipaddress6_", // {NETWORK INTERFACE}
			"macaddress_", // {NETWORK INTERFACE}
			"netmask_", // {NETWORK INTERFACE}
			"network_", // {NETWORK INTERFACE}
			"processor", // {NUMBER}
			"sp_" // {SYSTEM PROFILER DATA}
		);

		Set<String> regularFacts = ImmutableSet.<String> of(//
			"arp", //
			"augeasversion", //
			"boardmanufacturer", //
			"boardproductname", //
			"boardserialnumber", //
			"cfkey", //
			"domain", //
			"ec2_userdata", //
			"facterversion", //
			"fqdn", //
			"hardwareisa", //
			"hardwaremodel", //
			"hostname", //
			"id", //
			"interfaces", //
			"ipaddress", //
			"ipaddress6", //
			"iphostnumber", //
			"is_virtual", //
			"kernel", //
			"kernelmajversion", //
			"kernelrelease", //
			"kernelversion", //
			"lsbdistcodename", //
			"lsbdistdescription", //
			"lsbdistid", //
			"lsbdistrelease", //
			"lsbmajdistrelease", //
			"lsbrelease", //
			"macaddress", //
			"macosx_buildversion", //
			"macosx_productname", //
			"macosx_productversion", //
			"macosx_productversion_major", //
			"macosx_productversion_minor", //
			"manufacturer", //
			"memoryfree", //
			"memorysize", //
			"memorytotal", //
			"netmask", //
			"operatingsystem", //
			"operatingsystemrelease", //
			"osfamily", //
			"path", //
			"physicalprocessorcount", //
			"processor", //
			"processorcount", //
			"productname", //
			"ps", //
			"puppetversion", //
			"rubysitedir", //
			"rubyversion", //
			"selinux", //
			"selinux_config_mode", //
			"selinux_config_policy", //
			"selinux_current_mode", //
			"selinux_enforced", //
			"selinux_mode", //
			"selinux_policyversion", //
			"serialnumber", //
			"sshdsakey", //
			"sshecdsakey", //
			"sshrsakey", //
			"swapencrypted", //
			"swapfree", //
			"swapsize", //
			"timezone", //
			"type", //
			"uniqueid", //
			"uptime", //
			"uptime_days", //
			"uptime_hours", //
			"uptime_seconds", //
			"virtual", //
			"vlans", //
			"xendomains");

		@Override
		public List<String> getArgumentedFacts() {
			return argumentedFacts;
		}

		@Override
		public Set<String> getRegularFacts() {
			return regularFacts;
		}
	}

	public abstract List<String> getArgumentedFacts();

	public abstract Set<String> getRegularFacts();

	public boolean isFactName(String name) {
		if(name.startsWith("$"))
			name = name.substring(1);
		if(name.startsWith("::"))
			name = name.substring(2);
		if(getRegularFacts().contains(name))
			return true;
		for(String f : getArgumentedFacts()) {
			if(name.startsWith(f)) {
				if(name.length() > f.length())
					return true;
				return false;
			}
		}
		return false;
	}
}
