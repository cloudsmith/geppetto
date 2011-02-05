Exec { path => '/sbin:/bin:/usr/sbin:/usr/bin' }

filebucket { 'server': }

import "common"
import "collectd"

include collectd

resources { collectd_conf: purge => true; }

collectd::conf {
	'FQDNLookup':
		value => 'true';
	'Server':
		value => [ '"foo" 1002', '"foo3" 2000' ];
	'LoadPlugin':
		value => [ 'syslog', 'network', 'cpu' ];
}

collectd::syslog { 'debug': }
collectd::logfile {
	'debug': level => debug;
	'error': level => err;
}

collectd_threshold {
    '*/*:bar/foo':
        warning_min => "2.00",
        warning_max => "1000.00",
        failure_min => "0.00",
        failure_max => "1200.00",
        invert => false;
    '*/interface:eth0/if_octets:rx':
        failure_max => 10000000;
    'hostname/*:idle/cpu':
        failure_min => 10;
    'hostname/memory:cached/memory':
        warning_min => 100000000;
}


