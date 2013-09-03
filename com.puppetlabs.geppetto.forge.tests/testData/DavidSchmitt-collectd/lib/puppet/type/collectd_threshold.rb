# collectd/plugins/puppet/type/collectd_threshold.rb
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

Puppet::Type.newtype(:collectd_threshold) do
	@doc = "Manages the thresholds of collectd"

	ensurable

	newparam(:selector) do
		desc "This describes the selected datasources in this format:
			Host '/' Plugin ':' Instance '/' Type ':' DataSource
			Any and all of those, except the Type, can be replaced
			by '*' to select all of this kind."

		isnamevar
	end

	newproperty(:warning_min) do
		desc "collectd's WarningMin value"
	end

	newproperty(:warning_max) do
		desc "collectd's WarningMax value"
	end

	newproperty(:failure_min) do
		desc "collectd's FailureMin value"
	end

	newproperty(:failure_max) do
		desc "collectd's FailureMax value"
	end

	newproperty(:invert) do
		desc "collectd's Invert value"
	end

	newproperty(:persist) do
		desc "collectd's Persist value"
	end
end
