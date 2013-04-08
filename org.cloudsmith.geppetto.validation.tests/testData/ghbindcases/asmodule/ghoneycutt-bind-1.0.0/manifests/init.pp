# Class: bind
#
# base class for all bind server types
#
# Requires:
#   class generic
#
class bind {

    include generic

    $bindBasePath = "/var/named/chroot/"

    package {
        "bind": ;
        "bind-chroot":
            require => Package["bind"];
        "bind-utils":
            require => Package["bind"];
    } # package

    file {
        "/etc/named.conf":
            ensure  => link,
            target  => "${bindBasePath}etc/named.conf";
        "${bindBasePath}etc/named.conf":
            require => Package["bind-chroot"],
            notify => Service["named"];
    } # file

    service { "named" :
        ensure  => running,
        enable  => true,
        require => [ Package["bind-chroot"], File["/etc/named.conf"] ],
    } # service
} # class bind
