# Regression test: http://github.com/kbarber/puppet-iptables/issues#issue/2
notice("Test icmp without a type")
iptables {"icmp_type_any":
	source => "0.0.0.0",
	destination => "0.0.0.0",
	proto => "icmp",
}
