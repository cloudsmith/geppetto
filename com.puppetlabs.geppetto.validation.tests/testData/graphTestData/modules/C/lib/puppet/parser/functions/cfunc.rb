module Puppet::Parser::Functions
    newfunction(:cfunc, :type => :rvalue) do |args|
       args[0].to_s.length
    end
end

