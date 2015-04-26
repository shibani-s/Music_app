import requests
from bs4 import BeautifulSoup
import urllib.request, urllib.parse
import pymysql
import scrape
import re

happy=["aglow","air","alive","altruis","amaze","amazed","amazing","amuse","amused","anew","angels","anticipate","anticipation","apples","applepie","autumn","awe","awesome","bask","baby","balloons","beach","beautiful","birds","birthday","blessed","bliss","blissful","blithe","bloom","blooming","blossom","blush","book","books","breathe","breeze","cool","breeze","bright","brownies","brunch","bubbles","bubbly","butterflies","butterfly","kisses","cake","calm","camaraderie","candlelight","casual","celebrate","celebration","cheer","cheerful","saycheese","child","childhood","children","chocolate","comfort","compliments","cookiesandmilk","cool","cooperate","cooperation","comedy","comic","companion","companionship","compassion","congratulations","content","cozy","cuddle","curl","cute","daisy","daisies","dance","dawn","day","daylight","dear","delight","delightful","dew","dimples","dreams","drizzle","ecstasy","elated", "elation","empathy","enjoy","enthusiastic","euphoric","excellence","exhale","exubera","family","father","favor","fireplace","flowers","fly","fortunate","friend","friends","bestfriends","friendship","freckles","free","fresh","full","fun","funny","generosity","give","glad","glee","glitter","glow","glowing","good","garden","gifts","giggles","glee","good","morning","good","afternoon","good","evening","grace","gracious","grass","gratitude","great","weather","grin","grow","hallelujah","hands","happiness","happy","anniversary","birthday","harmony","healthy","heart","heaven","hide","hobby","holiday","holidays","honey","hope","hopeful","hubby","hug","humor","humorous","idea","inspiration","inspirational","inspired","jello","joke","jolly","joy", "joyful","joyous","jubilant","kindness","kiss","kittens","lake","leaves","lego","laugh","life","light","lighthearted","lily","lollipops","love","love","lovely","lullaby","lunch","magical","marshmallows","melody","merry","mild","mom","mother","music","nap","nature","natural","nostalgic","ocean","open","optimistic","oreos","overjoyed","pancakes","patient","patience","passion","peace","pets","pictures","pie","pillow","pleasant","pleased","pleasure","poetry","positive","presents","puppy","puppies","quality","quiet","rain","rainbow","rapture","red","relax","relief","renewal","respect","rest","restore","river","rose","run","safe","salvation","sand","satisfaction","shine","silly","sing","simple","sky","sleep","smile","smiling","smitten","snuggles","soft","song","sooth","soothing","sparkle","special","spiritual","splash","spontaneous","spring","stars","still","strawberries","stretch","success","summer","sun","sunlight","sunshine","sunrise","sunset","surprise","sweet","sweetheart","sweet","dreams","swing","tasty","tickle","thrill","together","togetherness","touch","toys","travel","trees","truth","twilight","upbeat","uplifting","useful","vacation","vivacious","walk","warm","warmth","water","water-balloons","waterfall","waves","weekend","welcome","welcoming","whole","whoosh","woo-hoo","win","wish","wishes","wonder","wonderful","yellow","yay","yippee","yum", "yummy","amused","beaming","blissful","blithe","buoyant","carefree","cheerful","cheery","chipper","chirpy","content","contented","delighted","ebullient","ecstatic","elated","enraptured","euphoric","exhilarated","exultant","funny","glad","gleeful","gratified","grinning","happy","mood","good","heaven","invigorated","jocular","jolly","jovial","joyful","joyous","jubilant","heart","merry","mirthful","top","world","optimistic","overjoy","over","moon","pleased","radiant","rapturous","satisfied","smiling","sunny","thrilled","tickled","pink","untroubled","upbeat","walking","air"]

song="";#song name
urls_for_happy=["http://www.songfacts.com/category-songs_about_joy_and_happiness.php","http://www.songfacts.com/category-songs_about_rockin%27_out.php","http://www.songfacts.com/category-new_year_songs.php"]

#------------------------------------------scrape-----------------------------------------------------------------------

scraped_lyrics="My lover's got humour She's the giggle at a funeral Knows everybody's disapproval I should've worshipped her sooner"#scrape.search(song+" lyrics")
print(scraped_lyrics)

#-------------------------------------------scrape----------------------------------------------------------------------------

status_for_happy=False
i=0
while(i<len(urls_for_happy)):
	req=urllib.request.Request(urls_for_happy[i],None);
	resp=urllib.request.urlopen(req)
	data=resp.read()
	songs=re.findall('<li><a href=\"/detail.php\?id=(.*?)\">(.*?)</a> - (.*?)</li>',str(data))
#	print(songs)
	for j in songs:
		#print(j[1]+":"+j[2])
		if(song==j[1] and artist==j[2]):
			status_for_happy=True
			happy_count=len(scraped_lyrics)
			break;
	i+=1
print("status_for_happy=",status_for_happy)

if(not status_for_happy):
	l = scraped_lyrics.split()
	print(l)
	happy_count = 0
	for i in l:
		for j in happy:
			if re.match(j,i):
				happy_count+=1
				break

	print("happy count ",happy_count," out of ",len(l))
	

