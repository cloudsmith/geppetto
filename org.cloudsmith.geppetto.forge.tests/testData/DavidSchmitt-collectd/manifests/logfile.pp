# collectd/manifests/libdir.pp
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

# Define: collectd::logfile
# specifies a logfile for collectd
# 
# Parameters:
#   namevar	- The basename for the logfile. It'll actually be called "/var/log/collectd_${namevar}.log"
#   level	- The collectd loglevel
#   timestamp	- 'true' (default) or 'false'; whether to add timestamps to the log
define collectd::logfile($level, $timestamp = 'true') {
	collectd::plugin {
		"logfile_${name}":
			lines => [
				"File \"/var/log/collectd_${name}.log\"",
				"Timestamp ${timestamp}",
				"LogLevel ${level}"
			]
	}
}

