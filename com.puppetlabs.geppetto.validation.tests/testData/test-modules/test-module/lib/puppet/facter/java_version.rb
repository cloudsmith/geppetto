# java_version.rb

Facter.add("java_version") do
	setcode do
		JAVA_VERSION_COMMAND = %w(java -version)

		fdr, fdw = IO.pipe()

		pid = fork() {
			fdr.close()

			# redirect both stdout and stderr to the pipe
			$stdout.reopen(fdw)
			$stderr.reopen(fdw)
			fdw.close()

			# prevent shell expansion by turning the first command token
			# into an array containing the token two times (if it is the only token)
			exec_args = JAVA_VERSION_COMMAND.length() <= 1 ? [JAVA_VERSION_COMMAND * 2] : JAVA_VERSION_COMMAND
			exec(*exec_args)
			exit(127)
		}

		begin
			fdw.close()

			# extract the java version from the first line of output
			fdr.gets() =~ /^java version "(.+)"/i
			java_version = $1

			# read the remaining lines to prevent broken pipe
			fdr.each() {}
			fdr.close()
		ensure
			Process.waitpid(pid)
		end

		java_version
	end
end
