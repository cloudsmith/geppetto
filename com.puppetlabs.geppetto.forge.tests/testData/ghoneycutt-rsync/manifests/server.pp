# Class: rsync::server
#
# The rsync server. Supports both standard rsync as well as rsync over ssh
#
# Requires:
#   class xinetd
#   class rsync
#
class rsync::server inherits rsync {

    include xinetd

    $rsync_fragments = "/etc/rsync.d"

    # Definition: rsync::server::module
    #
    # sets up a rsync server
    #
    # Parameters:   
    #   $path           - path to data
    #   $comment        - rsync comment
    #   $motd           - file containing motd info
    #   $read_only      - yes||no, defaults to yes
    #   $write_only     - yes||no, defaults to no
    #   $list           - yes||no, defaults to no
    #   $uid            - uid of rsync server, defaults to 0
    #   $gid            - gid of rsync server, defaults to 0
    #   $incoming_chmod - incoming file mode, defaults to 644
    #   $outgoing_chmod - outgoing file mode, defaults to 644
    #
    # Actions:
    #   sets up an rsync server
    #
    # Requires:
    #   $path must be set
    #
    # Sample Usage:
    #   # setup default rsync repository
    #   rsync::server::module{ "repo":
    #       path    => $base,
    #       require => File["$base"],
    #   } # rsync::server::module
    #
    define module ($path, $comment = undef, $motd = undef, $read_only = 'yes', $write_only = 'no', $list = 'yes', $uid = '0', $gid = '0', $incoming_chmod = '644', $outgoing_chmod = '644') {
        if $motd {
            file { "/etc/rsync-motd-$name":
                source => "puppet:///modules/rsync/motd-$motd",
            }
        } # fi

        file { "$rsync::server::rsync_fragments/frag-$name":
           content => template("rsync/module.erb"),
           notify  => Exec["compile fragements"],
        }
    } # define rsync::server::module

    xinetd::service {"rsync":
        port        => "873",
        server      => "/usr/bin/rsync",
        server_args => "--daemon --config /etc/rsync.conf",
    } # xinetd::service

    file {
        "$rsync_fragments":
            ensure  => directory;
        "$rsync_fragments/header":
            source => "puppet:///modules/rsync/header";
    } # file

    # perhaps this should be a script
    # this allows you to only have a header and no fragments, which happens
    # by default if you have an rsync::server but not an rsync::repo on a host
    # which happens with cobbler systems by default
    exec {"compile fragements":
        refreshonly => true,
        command     => "ls $rsync_fragments/frag-* 1>/dev/null 2>/dev/null && if [ $? -eq 0 ]; then cat $rsync_fragments/header $rsync_fragments/frag-* > /etc/rsync.conf; else cat $rsync_fragments/header > /etc/rsync.conf; fi; $(exit 0)",
        subscribe   => File["$rsync_fragments/header"],
    } # exec
} # class rsync::server    
