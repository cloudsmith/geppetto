# Test for multiple sources, originally added here:
iptables {
	"multiple sources 1":
		source => ["1.2.3.4/32","4.3.2.1/32"],
		dport => 15,
		jump => "ACCEPT";
}
iptables {
	"multiple sources 2":
		source => ["1.2.3.5/32","5.3.2.1/32"],
		dport => 15,
		jump => "ACCEPT";
}
