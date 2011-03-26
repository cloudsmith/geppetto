Puppet::Type.newtype(:thing) do
  @doc = "Description of thing"
    
  ensurable

  newparam(:name) do
    desc "Description of name"
    isnamevar
  end
  
  newproperty(:weight) do
    desc "Description of weight"
  end
  newproperty(:empty)
  
end
