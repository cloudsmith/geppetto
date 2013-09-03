require 'find'

desc "Run puppet tests."
task :test, :path do |t, args|
  args.with_defaults(:path => ".")

  paths = []

  Find.find(args.path) do |path|
    if path =~ /tests\/\d+.+\.pp/
      paths << path
    end
  end

  paths.sort.each do |path|
    print("Processing " + path + ": ")
    result = `puppet --noop --modulepath=. #{path} 2>&1`
    if $?.exitstatus == 0
      puts("\tSuccess")
    else
      puts("\tFailed")
      puts(result)
    end
  end
end
