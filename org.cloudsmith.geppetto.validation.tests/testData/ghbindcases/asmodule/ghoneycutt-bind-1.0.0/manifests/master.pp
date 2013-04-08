# Class: bind::master
#
# Authoritative name server - Master specific
#
# Requires:
#   class bind
#   class generic
#   class pam
#   class ssh
#   class svn
#   $bindRepoServer must be set in the site manifest
#   $named_listen_ips must be set in nodes manifest
#
class bind::master inherits bind {

    include generic
    include pam
    include ssh
    include svn

    pam::accesslogin {"dnsreposvn":}

    realize Generic::Mkuser[dnsreposvn]

#    ssh::private_key { "dnsreposvn":
#        user    => "dnsreposvn",
#        require => Generic::Mkuser[dnsreposvn],
#    }
#
#    ssh::authorized_keys { "dnsreposvn":
#        users   => [ "dnsreposvn-dnsmaster" ],
#        require => Generic::Mkuser[dnsreposvn],
#    }

    svn::checkout { "dns $dns_branch":
        reposerver => "$bindRepoServer"
        method     => "http",
        repopath   => "dns",
        workingdir => "/var/named/chroot/var/named/zones",
        branch     => "$dns_branch",
#        remoteuser => "dnsreposvn",
        localuser  => "dnsreposvn",
#        require    => Ssh::Private_key["dnsreposvn"],
        require    => Package["bind-chroot"],
        notify     => Service["named"],
    } # svn::checkout

    # directory for reverse zones, not included in the RH bind layout
    File["${bindBasePath}etc/named.conf"] {
        content => template("bind/named.authoritative-only-master.erb"),
    }
} # class bind::master
