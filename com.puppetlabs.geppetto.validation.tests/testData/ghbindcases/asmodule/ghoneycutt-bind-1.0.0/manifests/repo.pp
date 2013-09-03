# Class: bind::repo
#
# This module manages a SVN repo for zones
#
# Requires:
#   class apache::ssl
#   class generic
#   class pam
#   class ssh
#   class svn::repo
#   $lsbProvider must be set
#
class bind::repo {

    include apache::ssl
    include generic
    include pam
    include ssh
    include svn::repo

    package { "bind": }

    svn::server::setup { "dns":
        base => "$svn::repo::base",
    } # svn::server::setup

    #realize Apache::Ssl::Set_cert["default"]

    apache::vhost { "dnsrepo":
        content => template("bind/dnsrepo.conf.erb"),
    } # apache::vhost

    realize Generic::Mkuser[dnsreposvn]

    file { "/opt/$lsbProvider/bin/dns-svn-push":
        source => "puppet:///modules/bind/dns-svn-push",
        mode   => "750",
    } # file

#    # setup private key for post-commit
#    ssh::private_key { "dnsreposvn":
#        user    => "dnsreposvn",
#        require => Generic::Mkuser[dnsreposvn]
#    }
#
#    # setup authorized key for post-commit
#    ssh::authorized_keys { "dnsreposvn":
#        users   => [ "dnsreposvn-dnsrepo" ],
#        require => Generic::Mkuser[dnsreposvn]
#    }

    pam::accesslogin { "dnsreposvn": }
} # class bind::repo
