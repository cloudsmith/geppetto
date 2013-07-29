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
package org.cloudsmith.geppetto.pp.facter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cloudsmith.geppetto.pp.pptp.PPTPFactory;
import org.cloudsmith.geppetto.pp.pptp.PuppetDistribution;
import org.cloudsmith.geppetto.pp.pptp.TPVariable;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;

/**
 * An interface for Facter.
 * 
 */
public interface Facter {

	public abstract static class AbstractFacter implements Facter {

		private ImmutableMap<String, String> factmap;

		private ImmutableMap<String, String> factmapArgumented;

		@Override
		public TargetEntry asPPTP() {
			// -- TODO: should really be called something different than "Puppet Distribution"
			PuppetDistribution facter = PPTPFactory.eINSTANCE.createPuppetDistribution();
			facter.setDescription("Facter Variables");

			facter.setLabel("Facter");
			facter.setVersion(getVersionString());

			for(Entry<String, String> entry : getRegularFactMap().entrySet()) {
				TPVariable var = PPTPFactory.eINSTANCE.createTPVariable();
				var.setName(entry.getKey());
				var.setAssignable(true);
				var.setDeprecated(false);
				var.setDocumentation(entry.getValue());
				facter.getContents().add(var);
			}
			for(Entry<String, String> entry : getArgumentedFactMap().entrySet()) {
				TPVariable var = PPTPFactory.eINSTANCE.createTPVariable();
				var.setName(entry.getKey());
				var.setAssignable(true);
				var.setDeprecated(false);
				var.setDocumentation(entry.getValue());
				var.setPattern(patternForVariable(entry.getKey()));
				facter.getContents().add(var);
			}
			return facter;
		}

		/**
		 * Immutable map of factname to documentation of that fact.
		 * 
		 * @return
		 */
		@Override
		public Map<String, String> getArgumentedFactMap() {
			if(factmapArgumented == null)
				lazyBuildFactmap();
			return factmapArgumented;
		}

		protected abstract List<String> getArgumentedFacts();

		/**
		 * Immutable map of factname to documentation of that fact.
		 * 
		 * @return
		 */
		@Override
		public Map<String, String> getRegularFactMap() {
			if(factmap == null)
				lazyBuildFactmap();
			return factmap;
		}

		protected abstract List<String> getRegularFacts();

		@Override
		public boolean isFactName(String name) {
			if(name.startsWith("$"))
				name = name.substring(1);
			if(name.startsWith("::"))
				name = name.substring(2);
			if(getRegularFacts().contains(name))
				return true;

			if(!name.contains("::")) { // there are no facts in a sub namespace
				for(String f : getArgumentedFacts()) {
					if(name.startsWith(f)) {
						if(name.length() > f.length())
							return true;
						return false;
					}
				}
			}
			return false;
		}

		private void lazyBuildFactmap() {
			Builder<String, String> mapbuilder = ImmutableMap.builder();
			Iterator<String> itor = getRegularFacts().iterator();
			while(itor.hasNext()) {
				mapbuilder.put(itor.next(), itor.next());
			}
			factmap = mapbuilder.build();

			mapbuilder = ImmutableMap.builder();
			itor = getArgumentedFacts().iterator();
			while(itor.hasNext()) {
				mapbuilder.put(itor.next(), itor.next());
			}
			factmapArgumented = mapbuilder.build();
		}

		protected abstract String patternForVariable(String varName);

	}

	public static class Facter1_6 extends AbstractFacter {
		String networkInterfacePattern = "\\w+"; // sequence of word characters

		String processorPattern = "[0-9a-fA-F]+"; // octal, dec or hex number (unchecked for validity).

		List<String> argumentedFacts = ImmutableList.<String> of(//
			"arp_", "", // {NETWORK INTERFACE}
			"ipaddress_", "The IP4 address for a specific network interface (from the list in the $interfaces fact).", // {NETWORK INTERFACE}
			"ipaddress6_", "The IP6 address for a specific network interface (from the list in the $interfaces fact).",// {NETWORK INTERFACE}
			"macaddress_", "The MAC address for a specific network interface (from the list in the $interfaces fact).",// {NETWORK INTERFACE}
			"netmask_", "The netmask for a specific network interface (from the list in the $interfaces fact).",// {NETWORK INTERFACE}
			"network_", "The network for a specific network interface (from the list in the $interfaces fact).",// {NETWORK INTERFACE}
			"processor", "One fact for each processor, with processor info." // {NUMBER}
		);

		private static String EC2DOC = "See EC2 documentation";

		private static String OSXSPDOC = "Retrieved from OS X system profiler.";

		private static String LSBDOC = "Linux Standard Base information for the host. Only Linux and requires 'lsb_release' program.";

		Set<String> deprecatedFacts = ImmutableSet.<String> of("selinux_mode", "memorytotal");

		List<String> regularFacts = ImmutableList.<String> of(
			//
			"architecture",
			"The CPU hardware architecture. On OpenBSD, Linux and Debian's kfreebsd, use the hardwaremodel fact. Gentoo and Debian call 'x86_86' 'amd64'. Gentoo also calls 'i386' 'x86'.",
			//
			"arp", "", //
			"augeasversion", "The version of the Auegas library.", //
			"boardmanufacturer", "The manufacturer of the machine's motherboard", //
			"boardproductname", "The model name of the machine's motherboard.", //
			"boardserialnumber", "The serial number of the machine's motherboard.",//
			"cfkey", "The public key(s) for CFengine.", //
			"domain", "The host's primary DNS name.",//

			// EC2 stuff
			"ec2_ami_id", EC2DOC, //
			"ec2_ami_launch_index", EC2DOC, //
			"ec2_ami_manifest_path", EC2DOC, //
			"ec2_block_device_mapping_ami", EC2DOC, //
			"ec2_block_device_mapping_ephemeral0", EC2DOC, //
			"ec2_block_device_mapping_root", EC2DOC,//
			"ec2_hostname", EC2DOC, //
			"ec2_instance_id", EC2DOC, //
			"ec2_instance_type", EC2DOC, //
			"ec2_kernel_id", EC2DOC, //
			"ec2_local_hostname", EC2DOC, //
			"ec2_local_ipv4", EC2DOC, //
			"ec2_placement_availability_zone", EC2DOC,//
			"ec2_profile", EC2DOC, //
			"ec2_public_hostname", EC2DOC, //
			"ec2_public_ipv4", EC2DOC,//
			"ec2_public_keys_0_openssh_key", EC2DOC, //
			"ec2_reservation_id", EC2DOC, //
			"ec2_security_groups", EC2DOC,//
			"ec2_userdata", EC2DOC, //

			"facterversion", "The version of the facter module.", //
			"fqdn", "The fully qualified domain name of the host, i.e. (hostname fact + domain name fact).", //
			"hardwareisa", "Hardware processor type. (Result of 'uname -p' or equivalent).", //
			"hardwaremodel", "The hardware model of the system. (Result of 'uname -m' or equivalent).", //
			"hostname", "The system's short hostname.",//
			"id", "The name of the program producing 'username' (e.g. 'whoami'). On Solaris, fact is the username.", //
			"interfaces", "List of network interface names (suffixes to several other facts).", //
			"ipaddress", "The main IP address for a host.",//
			"ipaddress6", "The \"main\" IPv6 IP address of a system.",//
			"iphostnumber", "On selected versions of Darwin, returns the host's IP address.", //
			"is_virtual", "Boolean indicating if this is a virtualised machine.", //
			"kernel", "The operation system's name.", //
			"kernelmajversion", "The operation system's release version's major number.", //
			"kernelrelease", "The operating system's release number. (Result of 'uname -r' or equivalent).", //
			"kernelversion", "The operating system's kernel version. (Result of 'uname -v' or equivalent).", //

			// LSB
			"lsbdistcodename", LSBDOC, //
			"lsbdistdescription", LSBDOC,//
			"lsbdistid", LSBDOC,//
			"lsbdistrelease", LSBDOC, //
			"lsbmajdistrelease", LSBDOC, //
			"lsbrelease", LSBDOC, //

			//
			"macaddress", "The MAC address of the primary network interface", //
			"macosx_buildversion", "The system's Mac OS X build version.", //
			"macosx_productname", "The system's Mac OS X product name. Will almost always be \"Mac OS X\".", //
			"macosx_productversion", "The system's full Mac OS X version number (e.g. 10.7.4).", //
			"macosx_productversion_major", "The system's major Mac OS X version number (e.g. 10.7).", //
			"macosx_productversion_minor", "The system's major Mac OS X version number (e.g. 4).",//
			"manufacturer", "The hardware manufacturer information about the hardware.", //
			"memoryfree", "The amount of free memory on the system.", //
			"memorysize", "The total amount of memory in the system.",//
			"memorytotal", "Synonym for $::memorysize. Deprecated.", // DEPRECATED - MOVE TO SEPARATE LIST
			"netmask", "The netmask for the main interfaces.", //
			"operatingsystem", "The name of the operating system.",//
			"operatingsystemrelease", "The release of the operating system.", //
			"osfamily", "The operating system family derived from $operatingsystem.", //
			"path", "The system/shell $PATH variable", //
			"physicalprocessorcount", "The number of physical processors.", //
			"processor", "Additional Facts about the machine's CPU. Only on BSD.", //
			"processorcount", "The number of processors in the machine.", //
			"productname", "The model identifier of the machine.", //
			"ps", "Name of command to list all processes. Is 'ps -ef' on all except BSD where 'ps auxwww' is used.", //
			"puppetversion", "The version of puppet installed.", //
			"rubysitedir", "Ruby's site library directory", //
			"rubyversion", "The version of Ruby facter is running under. ",//

			// SELINUX
			"selinux", "", //
			"selinux_config_mode", "", //
			"selinux_config_policy", "", //
			"selinux_current_mode", "", //
			"selinux_enforced", "", //
			"selinux_mode", "Deprecated, use $selinux_config_policy.", // DEPRECATED - selinux_config_policy
			"selinux_policyversion", "", //

			//
			"serialnumber", "The machine's serial number.", //

			// OS X SP_

			"sp_64bit_kernel_and_kexts", OSXSPDOC, //
			"sp_boot_mode", OSXSPDOC, //
			"sp_boot_rom_version", OSXSPDOC,//
			"sp_boot_volume", OSXSPDOC, //
			"sp_cpu_interconnect_speed", OSXSPDOC,//
			"sp_cpu_type", OSXSPDOC, //
			"sp_current_processor_speed", OSXSPDOC,//
			"sp_kernel_version", OSXSPDOC, //
			"sp_l2_cache_core", OSXSPDOC, //
			"sp_l3_cache", OSXSPDOC, //
			"sp_local_host_name", OSXSPDOC,//
			"sp_machine_model", OSXSPDOC, //
			"sp_machine_name", OSXSPDOC, //
			"sp_mmm_entry", OSXSPDOC,//
			"sp_number_processors", OSXSPDOC,//
			"sp_os_version", OSXSPDOC, //
			"sp_packages", OSXSPDOC, //
			"sp_physical_memory", OSXSPDOC,//
			"sp_platform_uuid", OSXSPDOC, //
			"sp_secure_vm", OSXSPDOC, //
			"sp_serial_number", OSXSPDOC,//
			"sp_smc_version_system", OSXSPDOC,//
			"sp_uptime", OSXSPDOC, //
			"sp_user_name", OSXSPDOC,//

			//
			"sshdsakey", "The host's SSH DSA key,",//
			"sshecdsakey", "The host's SSH ECDSA key.", //
			"sshrsakey", "The host's SSH RSA key.", //
			"swapencrypted", "Boolean indicating if the system's swap space is encrypted. Only on Darwin.", //
			"swapfree", "The amount of free swap space.", //
			"swapsize", "The total amount of swap space.", //
			"timezone", "The machine's time zone.", //
			"type", "The machine's chassis type.", //
			"uniqueid", "The output of the 'hostid' command.", //
			"uptime", "System uptime in human readable format.", //
			"uptime_days", "Number of days of uptime (i.e. '$uptime_hours / 24'.", //
			"uptime_hours", "Number of hours of uptime (i.e. '$uptime_seconds / 3600'.", //
			"uptime_seconds", "Number of seconds of uptime.", //
			"virtual", "Boolean indicating of the system's hardware is virtualised.", //
			"vlans", "The list of all the VLANs on the system. Only Linux.", //
			"xendomains", "The list of Xen domains on the Dom().");

		@Override
		public List<String> getArgumentedFacts() {
			return argumentedFacts;
		}

		@Override
		public List<String> getRegularFacts() {
			return regularFacts;
		}

		@Override
		public String getVersionString() {
			return "1.6";
		}

		@Override
		protected String patternForVariable(String name) {
			if(!argumentedFacts.contains(name))
				return null;
			if("processor".equals(name))
				return processorPattern;

			return networkInterfacePattern;
		}
	}

	/**
	 * Produce a PPTP instance describing the facts.
	 * 
	 * @return
	 */
	public TargetEntry asPPTP();

	/**
	 * Immutable map of factname to documentation of that fact. Each fact ends with a wildcard (sort of
	 * which is a network interface name (e.g. en0, en1, ln0, ln1, etc.), or a processor number (0-n? hex? octal?).
	 */
	public Map<String, String> getArgumentedFactMap();

	/**
	 * Immutable map of factname to documentation of that fact.
	 */
	public Map<String, String> getRegularFactMap();

	/**
	 * Information about the fact set.
	 * 
	 * @return
	 */
	public String getVersionString();

	/**
	 * Check if the given name is a fact.
	 * 
	 * @return true, if the name is a fact described by this facter.
	 */
	public boolean isFactName(String name);

}
