class bclass($bparam = 10) inherits cclass{ }
#should see all functions
bfunc(20)
cfunc(30)
delete_lines { file => "aFile", pattern => "*foobar*" }
c::short { a => "b" }
aaa { a => b }
bbb { a => b }
ccc { a => b }
# added to check that the cparam does not show up in the graph
class { "cclass" : cparam => 20 }