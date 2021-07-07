source "https://rubygems.org"

gem "bundler"
gem "fastlane", ">= 2.172.0"
gem "dotenv"
plugins_path = File.join(File.dirname(__FILE__), 'fastlane', 'Pluginfile')
eval_gemfile(plugins_path) if File.exist?(plugins_path)
