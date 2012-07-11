module Puppet
  Puppet::Type.type(:mocktype).newproperty(:extra1) do
    desc "An extra property called extra1"
  end
end