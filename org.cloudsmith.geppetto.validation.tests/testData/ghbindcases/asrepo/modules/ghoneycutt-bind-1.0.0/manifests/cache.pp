# Class: bind::cache
#
# This module manages a caching only bind server
#
# Requires:
#   class bind
#   $named_listen_ips must be set in nodes manifest
#   $auth_nameservers must be set in site manifest
#
class bind::cache inherits bind {

    package { "caching-nameserver": }

    # $named_listen_ips in site.pp    
    File["${bindBasePath}etc/named.conf"] {
            content => template("bind/named.caching-nameserver.erb"),
    } # File

    file { "${bindBasePath}var/named/named.root":
            source  => "puppet:///modules/bind/named.root",
            notify  => Service["named"],
            require => [ Package["bind-chroot"], Package["caching-nameserver"] ],
            group   => "named",
            mode    => "640";
    } # file
} # class bind::cache
