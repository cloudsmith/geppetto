notice("test: test rule")
iptables {
	"test rule":
		source => "0.0.0.0",
		destination => "0.0.0.0",
}
