# collectd/manifests/conf.pp
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

# Define: collectd::conf
#
# Parameters:
#   namevar	- the name of the collect.conf option
#   value	- the value to set for this option. Use an array to specify
#		  multiple values, these will be put on separate lines
#   ensure	- 'present' or 'absent'
#   quote	- specify whether the value needs quoting. A default is chosen
#   		  for known options, if nothing is specified.
define collectd::conf($value, $ensure = present, $quote = '') {

	case $quote {
		'': {
			case $name {
				'LoadPlugin', 'TypesDB',
				'Server': {
					$quote_real = 'no'
				}
				'BaseDir', 'Include',
				'PIDFile', 'PluginDir',
				'Interval', 'ReadThreads',
				'Hostname', 'FQDNLookup': {
					$quote_real = 'yes'
				}
				default: {
					fail("Unknown collectd.conf directive: ${name}")
				}
			}
		}
		true, false, yes, no: {
			$quote_real = $quote
		}
	}

	case $quote_real {
		true, yes: {
			collectd_conf {
				$name:
					ensure => $ensure,
					require => Package['collectd'],
					notify => Service['collectd'],
					value => regsubst($value, '^(.*)$', '"\1"', "G", "U")
			}
		}
		false, no: {
			collectd_conf {
				$name:
					ensure => $ensure,
					require => Package['collectd'],
					notify => Service['collectd'],
					value => $value
			}
		}
	}
}
