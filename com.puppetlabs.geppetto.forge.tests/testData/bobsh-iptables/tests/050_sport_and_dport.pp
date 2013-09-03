# Test sport and dport 
iptables { "sport and dport":
	source => "0.0.0.0",
	sport => "14",
	destination => "0.0.0.0",
	dport => "15",
}
