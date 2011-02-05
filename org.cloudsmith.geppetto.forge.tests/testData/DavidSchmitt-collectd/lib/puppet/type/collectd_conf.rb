# collectd/plugins/puppet/type/collectd_conf.rb
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

Puppet::Type.newtype(:collectd_conf) do
	@doc = "Manages the basic option statements in a collectd.conf file"

	ensurable

	newparam(:key) do
		desc "The name of the configuration key"
		isnamevar
	end

	newproperty(:target) do
		desc "Which file to write to."
		defaultto "/etc/collectd/collectd.conf"
	end

	newproperty(:value, :array_matching => :all) do
		desc "The value to set. Use an array to set a key multiple times"

		def should_to_s(newvalue = @should)
			val_to_s(newvalue)
		end

		def is_to_s(currentvalue = @is)
			val_to_s(currentvalue)
		end
		
		# format an array of values for display
		def val_to_s(val) 
			if val
				unless val.is_a?(Array)
					val = [val]
				end
				val.join(",")
			else
				nil
			end
		end
		
		# disregard the order of values of a single option when
		# deciding whether the config file needs an update
		def insync?(is)
			return is.sort == should.sort
		end
	end

	# the file has to be managed before it is touched by us.
	autorequire(:file) { self[:target] }
end

