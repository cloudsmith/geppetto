# collectd/plugins/puppet/provider/collectd_conf/parsed.rb
# (C) Copyright: 2008, David Schmitt <david@dasz.at>

require 'puppet/provider/parsedfile'

# General notes: This is modelled after the crontab provider. To use the
# line-based parsing routines of the ParsedFile utility, the file is read
# line-by-line and in a second pass (self.prefetch_hook()) the lines are
# coalesced into a tree structure. On write-out, the various to_line() methods
# know how to create the appropriate lines again.

Puppet::Type.type(:collectd_conf).provide(:parsed,
	:parent => Puppet::Provider::ParsedFile,
	:default_target => "/etc/collectd/collectd.conf"
	) do

	# skip blank and comment only lines
	text_line :blank, :match => %r{^\s*$}
	text_line :comment, :match => %r{^#}

	# recognise <Plugin ...> lines. contained lines will end up in
	# :contents, to be written out by to_line again.
	plugin = record_line :plugin_start, :fields => %w{name},
		:match => %r{^\s*<Plugin\s+(.*)\s*>\s*$}

	class << plugin
		def to_line(record)
			"<Plugin %s>\n%s\n</Plugin>" % [
				record[:name],
				record[:contents].join("\n")
			]
		end
	end

	record_line :plugin_end, :fields => [],
		:match => %r{^\s*</Plugin>\s*$}

	# recognise configuration options. Multiple occurrences of the same
	# option will be coalesced into a single instance with a array as
	# :value. to_line will create multiple lines again.
	config = record_line :parsed, :fields => %w{name value},
		:match => %r{^\s*(\S+)\s+(.*)$}

	class << config
		def to_line(record)
			values = [ record[:value] ]
			if record[:value].is_a? Array 
				values = record[:value]
			end
			values.collect do |v| "%s %s" % [ record[:name], v ] end.sort.join("\n")
		end
	end

	# coalesce configuration directives with multiple values and <Plugin>
	# containers
	def self.prefetch_hook(records)

		# a ":name => record" hash, for collecting multiple config
		# options of the same name
		sorted_records = {}

		# the list of plugin_start resources.
		plugins = []

		# the currently "open" <Plugin>, nil in the top scope
		plugin = nil
		# the list of lines of the currently "open" <Plugin>
		contents = nil
		records.each { |record|

			case record[:record_type]
			when :plugin_start
				# start collecting lines within the <Plugin>
				plugin = record
				contents = []
			when :plugin_end
				# save the collected records
				plugin[:contents] = contents
				plugins << plugin
				plugin = nil
				contents = nil
			when :parsed
				if contents
					# collect the record
					contents << to_line(record)
				else
					# process this record:
					#
					# 1: munge :value into an array
					value = []
					if record[:value]
						unless record[:value].is_a?(Array) 
							value = [ record[:value] ]
						else
							value = record[:value]
						end
					end
			
					# 2: merge array into possible already existing record
					real_record = nil
					if sorted_records[record[:name]]
						real_record = sorted_records[record[:name]]
						value += real_record[:value]
					else
						real_record = record
					end
			
					real_record[:value] = value

					# 3: save merged record
					sorted_records[record[:name]] = real_record
				end
			end
		}

		# Because plugins have to be after their respective LoadModule
		# option, they are added here after the options.
		sorted_records.values + plugins
	end

	# part of the provider pattern. This method returns a list of all found
	# resources
	def self.instances
		prefetch()
		@records.find_all { |r| [:plugin_start, :parased].include?(r[:record_type]) }.collect { |r| new(r) }
	end

end
		
