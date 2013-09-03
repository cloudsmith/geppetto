namespace :outer do
namespace :myspace do
    desc "this is my task"
    task :mytask do
       puts "doing mytask"
    end
end
desc "a cucumber task"
Cucumber::Rake::Task.new(:cucumberTask) do |t| 
end
desc "a rspec task"
RSpec::Core::RakeTask.new(:rspecTask => ['this', 'that']) do |t|
end
end
desc "the default task"
task :default => 'outer:myspace:mytask'
