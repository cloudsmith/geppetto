# Test icmp types to avoid regression:
#
# http://github.com/kbarber/puppet-iptables/issues#issue/1
#
notice("Test icmp symbolic name mapping")
iptables {"icmp_type":
	source => "0.0.0.0",
	destination => "0.0.0.0",
	proto => "icmp",
	icmp => "echo-reply"
}

notice("(assertFail) Test an invalid icmp type")
iptables {"icmp_type_invalid":
	source => "0.0.0.0",
	destination => "0.0.0.0",
	proto => "icmp",
	icmp => "foo"
}

