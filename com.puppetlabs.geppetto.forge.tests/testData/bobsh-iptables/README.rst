====================
Puppet iptables type
====================

This is a simple wrapper around the "iptables" command used on Linux. It is
meant to be used to define half a dozen rules on the host running puppet. For
more serious needs, you might want to have a look at this `shorewall module`_
for puppet.

.. _`shorewall module`: http://github.com/camptocamp/puppet-shorewall/tree

Introduction
------------

The way it works differs slightly from the usual puppet resource types.

The state of a firewall can be seen as the sum of all the rules that compose it
and the order in which they appear. We could define a firewall as one unique
puppet resource, but this doesn't offer much flexibility. It's much more
convenient to define each iptable rule as a separate resource. But then,
ensuring they always get called in the same exact order can be difficult,
especially once they are dispatched in different classes, definitions, and so
on.

Furthermore, it can be tricky to handle only part of the firewall rules using
puppet, and let something/someone else do the rest.

So the idea is to have the "iptables type":

* find the current state of the firewall by parsing the output of
  "iptables-save"
* collect every "iptables resource" found in the manifests
* sort them (currently using the resource name)
* purge any rule it doesn't know about
* run the commands to insert the rules in the right order.

Usage
-----

Example::

  iptables { "001 allow icmp":
    proto => "icmp",
    icmp  => "any",
    jump  => "ACCEPT",
  }
  iptables { "another iptables rule":
    proto       => "tcp",
    dport       => "80",
    source      => "192.168.0.0/16",
    destination => "192.168.1.11/32",
    jump        => "ACCEPT",
  }
  iptables { "my iptables rule":
    proto       => "tcp",
    dport       => "80",
    jump        => "DROP",
  }


  file { "/etc/puppet/iptables/pre.iptables":
    content => "-A INPUT -s 10.0.0.1 -p tcp -m tcp --dport 22 -j ACCEPT",
    mode    => 0600,
  }
  file { "/etc/puppet/iptables/post.iptables":
    content => "-A INPUT -j REJECT --reject-with icmp-port-unreachable",
    mode    => 0600,
  }



This will run the following commands, in this exact order::

  iptables -t filter -D INPUT ...whatever is returned by iptables-save and doesn't match puppet resources...
  iptables -t filter -A INPUT -s 10.0.0.1 -p tcp -m tcp --dport 22 -j ACCEPT
  iptables -t filter -A INPUT -i lo -j ACCEPT
  iptables -t filter -A INPUT -s 192.168.0.0/16 -d 192.168.1.11/32 -p tcp --dport 80 -j ACCEPT
  iptables -t filter -A INPUT -p tcp --dport 80 -j DROP
  iptables -t filter -A INPUT -j REJECT --reject-with icmp-port-unreachable

Reference
---------

Have a look at lib/puppet/type/iptables.rb for the complete list of
parameters.


Installation
------------

Clone this git repository in your $modulepath on the puppetmaster, then ensure
you have the following in your puppet.conf, both on the client and the server
side::

  [main]
    libdir = /var/lib/puppet/lib

  [puppetd]
    pluginsync=true
    plugindest=/var/lib/puppet/lib


