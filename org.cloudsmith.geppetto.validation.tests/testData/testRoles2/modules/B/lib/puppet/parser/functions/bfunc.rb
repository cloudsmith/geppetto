module Puppet::Parser::Functions
    newfunction(:bfunc, :type => :rvalue) do |args|
       args[0].to_s.length
    end
end

