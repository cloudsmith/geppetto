class passenger {
        include rubygems19
        include rubygems19::common_dependencies
        include apache2
        include apache2::variables

        $build_dependencies = [
                'httpd-devel', 'apr-devel', 'apr-util-devel',
                'libcurl-devel', 'openssl-devel', 'zlib-devel'
        ]
        $config_file = "${apache2::variables::config_include_dir}/passenger.conf"
        $config_util = '/usr/local/lib/passenger_apache_module_config_util.rb'

        package { [$build_dependencies]:
                ensure => latest,
        }

        package { 'passenger':
                ensure => latest,
                provider => gem19,
                require => [Class['rubygems19::common_dependencies'], Package[$build_dependencies]],
        }

        file { "${config_util}":
                source => 'puppet:///modules/passenger/passenger_apache_module_config_util.rb',
                owner => root,
                group => root,
                mode => 0644,
        }

        exec { 'unconfigure':
                onlyif => "test -f \"${config_file}\"",
                command => "rm \"${config_file}\"",
                path => ['/usr/local/bin', '/bin', '/usr/bin'],
                refreshonly => true,
                subscribe => [Class['rubygems19::common_dependencies'], Package['passenger'], Package[$build_dependencies]],
        }

        exec { 'configure':
                unless => "ruby \"${config_util}\" check \"${config_file}\"",
                command => "ruby \"${config_util}\" configure \"${config_file}\"",
                path => ['/usr/local/bin', '/bin', '/usr/bin'],
                timeout => 0,
                require => [Exec['unconfigure'], File["${config_util}"]],
                notify => Service['apache2'],
        }
}
