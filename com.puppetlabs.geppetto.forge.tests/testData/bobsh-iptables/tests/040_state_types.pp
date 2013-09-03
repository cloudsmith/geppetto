# Test state types
iptables {
	"state types 1":
		source => "0.0.0.0",
		state => ["NEW","RELATED"],
		jump => "ACCEPT"
}
