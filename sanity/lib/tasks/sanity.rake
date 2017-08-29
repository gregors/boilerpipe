namespace :sanity do
  desc 'Downloads forked boilerpipe jar from Gregors github for sanity checks'
  task download_boilerpipe_jar: :environment do
    Dir.chdir 'lib'
    `wget 'https://github.com/gregors/jruby-boilerpipe/raw/master/lib/boilerpipe-common-2.0-SNAPSHOT-jar-with-dependencies.jar'`
  end

end
