# react-native-push.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-push"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-push
                   DESC
  s.homepage     = "https://github.com/github_account/react-native-push"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/github_account/react-native-push.git", :tag => "#{s.version}" }

  s.source_files    = 'ios/*.{h,m}'
  s.ios.vendored_libraries = 'ios/*.a'
  s.requires_arc = true

  s.frameworks = 'UserNotifications','SystemConfiguration','MobileCoreServices','CFNetwork','CoreTelephony'
  s.library = 'libresolv','libxml2','libz'
  s.dependency "React"
  s.dependency 'RNCPushNotificationIOS'
end

