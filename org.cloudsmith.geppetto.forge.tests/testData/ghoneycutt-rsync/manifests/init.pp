# Class: rsync
#
# This module manages rsync and provides a define for grabbing files via rsync
# It is included in the generic module for *ALL* nodes
#
class rsync {

    package { "rsync": }

    # Definition: rsync::get
    #
    # get files via rsync
    #
    # Parameters:   
    #   $source  - source to copy from
    #   $path    - path to copy to, defaults to $name
    #   $user    - username on remote system
    #   $purge   - if set, rsync will use '--delete'
    #   $exlude  - string to be excluded
    #   $keyfile - ssh key used to connect to remote host
    #   $timeout - timeout in seconds, defaults to 900
    #
    # Actions:
    #   get files via rsync
    #
    # Requires:
    #   $source must be set
    #
    # Sample Usage:
    #
    #    rsync::get { "/foo":
    #        source  => "rsync://$rsyncServer/repo/foo/",
    #        require => File["/foo"],
    #    } # rsync
    #
    define get ($source, $path = undef, $user = undef, $purge = undef, $exclude = undef, $keyfile = undef, $timeout = "900") {

        if $keyfile {
            $Mykeyfile = $keyfile
        } else {
            $Mykeyfile = "/home/$user/.ssh/id_rsa"
        }

        if $user {
            $MyUser = "-e 'ssh -i $Mykeyfile -l $user' $user@"
        }

        if $purge {
            $MyPurge = "--delete"
        }

        if $exclude {
            $MyExclude = "--exclude=$exclude"
        }

        if $path {
            $MyPath = $path
        } else {
            $MyPath = $name
        }

        exec {"rsync $name":
            command => "rsync -qa $MyPurge $MyExclude $MyUser$source $MyPath",
            timeout => $timeout,
        } # exec
    } # define rsync::get
} # class rsync
