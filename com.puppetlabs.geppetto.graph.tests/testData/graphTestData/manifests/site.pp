class myclass($cparam=10) {
}
node foo {
	include toothFairyDoesNotExist
	class { cclass: cparam => "something" }
#	class { myclass: cparam => "something" }
}