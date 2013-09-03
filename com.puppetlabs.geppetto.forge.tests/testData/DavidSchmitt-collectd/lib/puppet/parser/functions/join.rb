# collectd/plugins/puppet/parser/functions/join.rb
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

module Puppet::Parser::Functions
	# join($separator, $values)
	# joins all elements of $values into a single string separated by $separator
	# example:
	# $values = ["a", "b"]
	# join("|", $values) == "a|b"
	newfunction(:join, :type => :rvalue) do |args|
		if args[1].is_a?(Array)
			args[1].join(args[0])
		else
			args[1]
		end
	end
end

