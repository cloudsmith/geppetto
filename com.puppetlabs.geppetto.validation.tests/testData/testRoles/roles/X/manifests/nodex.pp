#should see afunc and #bfunc
afunc(10)
bfunc(20)
#should not see cfunc
cfunc(30)

# should see a/b class and a/b param
class { aclass : aparam => 10}
class { b::bclass : bparam => 20}

# should NOT see a/b class and a/b param
class { cclass : cparam => 20}

#should see xclass
include xclass
class { xclass: }
