require './boilerpipe-common-2.0-SNAPSHOT-jar-with-dependencies.jar'
java_import 'com.kohlschutter.boilerpipe.extractors.ArticleExtractor'
#url = java.net.URL.new("http://www.cnn.com/")
url = java.net.URL.new("https://blog.openshift.com/day-18-boilerpipe-article-extraction-for-java-developers/")
url = java.net.URL.new("http://blog.carbonfive.com/2016/02/24/es6-es7-and-looking-forward/")

puts 'starting'
puts ArticleExtractor::INSTANCE.get_text(url)
puts '...end'
