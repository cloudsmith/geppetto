module Puppet
  Puppet::Type.type(:mocktype).newproperty(:extra2) do
    desc "An extra property called extra2"
  end
end