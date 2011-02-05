# collectd/manifests/init.pp
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

# Module: collectd
# 
# To start managing the collectd, include the collectd class in your node. Use
# the collectd::conf define to set basic parameters of your installation.
# collectd::plugin is the foundation of many plugin-specific defines which help
# you configuring their respective plugin.
#
# Class: collectd
# Manages the installation and running of a collectd as well as the
# /etc/collectd/collectd.conf file.
class collectd {

	libdir { ['collectd', 'collectd/plugins', 'collectd/thresholds' ]: }

	package {
		'collectd':
			ensure => installed;
	}

	service {
		'collectd':
			ensure => running,
			enable => true,
			hasrestart => true,
			pattern => collectd,
			require => Package['collectd'];
	}

	file {
		'/etc/collectd/collectd.conf':
			ensure => present,
			mode => 0644, owner => root, group => 0,
			require => Package['collectd'],
			notify => Service['collectd'];
	}

	collectd::conf {
		'Include':
			value => [
				'${module_dir_path}/collectd/plugins/*.conf',
				'${module_dir_path}/collectd/thresholds/*.conf'
			];
	}

	# add customisations for distributions here
	case $operatingsystem {
		'debian': {
			case $debianversion {
				'etch': {
				}
			}
		}
		default: {
			# no changes needed
		}
	}
}
