#!/usr/bin/ruby -Ku

require 'rexml/document'
require 'open-uri'

require File.dirname(__FILE__)+'/../dbfiles/twitter_posts.rb'
require File.dirname(__FILE__)+'/../mekatuna/modules/twitterstring.rb'

class String; include TwitterString; end

Twitter = 'http://twitter.com/'
Twitter_rss = 'http://twitter.com/statuses/user_timeline/'
Twitter_rss_suffix = '.atom?page='
connect_TwitterPostsDB_with_log

user = 'pokutuna'

buffer404 = Array.new
count404 = 0

catch(:exit){
	begin
		doc = REXML::Document.new(open(Twitter+user).read)
		doc.elements['//*[@id="rssfeed"]'].to_s =~ /user_timeline\/(\d+)\.rss/
		user_id =  $1
	rescue => e
		puts e.class, e.message, e.backtrace
		throw :exit
	end

	for pagenum in 1..150
		sleep 5
		begin
			urlstr = Twitter_rss+user_id+Twitter_rss_suffix+pagenum.to_s
			doc = REXML::Document.new(open(urlstr).read.unescapeHTML)
			count404 = 0
			doc.elements.each('//entry') do |e|
				text = e.elements['content'].text.sub(user+': ', '')
				e.elements['updated'].text =~ /(\d+)\-(\d+)\-(\d+)T(\d+):(\d+):(\d+)\+(.*)/
				time = Time.utc($1,$2,$3,$4,$5,$6)
				url =  e.elements['link[@rel="alternate"]/attribute::href'].to_s

				if Post.find_by_url(url) == nil
					Post.create(
						:username => user, :message => text, :url => url, :time => time)
				else
					throw :exit
				end
			end
			
		rescue => e
			count404 += 1
			break if count404 >= 5
			buffer404.push(pagenum)
			p e.class.to_s + e.message.to_s + e.backtrace.to_s
		end
	end

}

p buffer404
Update.create(:time => Time.new.utc, :action => 'Crawled')
