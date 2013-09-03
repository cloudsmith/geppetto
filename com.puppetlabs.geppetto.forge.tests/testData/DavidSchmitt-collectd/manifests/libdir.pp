# collectd/manifests/libdir.pp
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

# private, a purged directory to store the various additional configs
define collectd::libdir() {
	file {
		"${module_dir_path}/${name}":
			source => "puppet:///modules/collectd/empty", # recurse+purge needs empty directory as source
			checksum => mtime,
			ignore => '.ignore', # ignore the placeholder
			recurse => true, purge => true, force => true;
	}
}
