Puppet::Type.newtype(:mocktype) do
  @doc = "This is a mock type"
  newparam(:param1) do
    desc "This is parameter1"
  end
  newproperty(:prop1) do
    desc "This is property1"
  end
end