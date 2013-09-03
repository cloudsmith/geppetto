# Class: test-module
#
# This module ensures java of at least version 1.5 is installed
#
# Parameters:
#
# Actions:
#
# Requires:
#
# Sample Usage:
#
# [Remember: No empty lines between comments and class definition]
class test-module {
	$java_release = $java_version ? {
		/^(\d+\.\d+)(?:\.|$)/ => $1,
		default => undef
	}

	if ! $java_release or $java_release < 1.5 {
		package { "java-1.6.0-openjdk":
			ensure => installed
		}

		$java_requirement = Package["java-1.6.0-openjdk"]

		$java_release_content = '1.6'
	} else {
		$java_requirement = undef

		$java_release_content = $java_release
	}

	file { "/etc/java_release":
		owner => root, group => root, mode => 440,
		content => $java_release_content,
		ensure => present,
		require => $java_requirement
	}
}
