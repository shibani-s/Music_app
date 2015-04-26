import requests
from bs4 import BeautifulSoup
import urllib.request, urllib.parse
import pymysql
import scrape
import re

song="take me to church hozier"

sad_words=["sad", "unhappy" ,"gloomy" ,"sombre","melancholy","sorrowful","subdued","bleak","wistful","homesick","bad","blue","brokenhearted", "cast down", "crestfallen", "dejected", "depressed", "despondent", "disconsolate", "doleful", "down", "downcast", "downhearted", "down in the mouth","droopy", "forlorn", "gloomy", "glum", "hangdog",  "heartbroken", "heartsick", "heart-sore", "heavyhearted", "inconsolable", "joyless","low", "low-spirited", "melancholi","melancholy", "miserable", "mournful", "saddened", "sorrowful", "sorry", "unhappy", "woebegone","woeful", "wretched","aggrieved", "distressed", "troubled", "uneasy", "unquiet", "upset", "worried", "despairing", "hopeless", "sunk", "disappointed", "discouraged", "disheartened", "dispirited", "suicidal", "dolorous", "lachrymose", "lugubrious", "plaintive", "tearful","regretful", "rueful", "agonized", "anguished", "grieving", "wailing", "weeping", "black", "bleak", "cheerless", "comfortless", "dark", "darkening","depressing", "desolate", "dismal", "drear", "dreary", "elegiac", "funereal", "gray", "morbid","morose","murky", "saturnine", "somber" , "sullen"]

#------------------------------------------vocabulary----------------------------------------------------------------------------
urls_for_sad=["http://www.songfacts.com/category-songs_about_depression.php","http://www.songfacts.com/category-songs_about_the_dangers_of_materialism.php","http://www.songfacts.com/category-songs_about_an_ex-girlfriend_or_ex-boyfriend.php","http://www.songfacts.com/category-songs_about_suicide.php","http://www.songfacts.com/category-songs_about_loneliness_or_isolation.php","http://www.songfacts.com/category-songs_about_marital_problems_or_divorce.php"]

#------------------------------------------scrape-----------------------------------------------------------------------
print("before")
scraped_lyrics="My lover's got humour"#scrape.search(song+" lyrics")
print(scraped_lyrics)
print("after")
#-----------------------------------------------------------------------------------------------------------------------

#---------------------------------------------------sad----------------------------------------------------------------------

status_for_sad=False
i=0
while(i<len(urls_for_sad)):
	req=urllib.request.Request(urls_for_sad[i],None);
	resp=urllib.request.urlopen(req)
	data=resp.read()
	songs=re.findall('<li><a href=\"/detail.php\?id=(.*?)\">(.*?)</a> - (.*?)</li>',str(data))
	print(songs)
	for j in songs:
		print(j[1]+":"+j[2])
		if(song==j[1] and artist==j[2]):
			status_for_sad=True
			sad_count=len(scraped_lyrics);
			break;
	i+=1
print("status_for_sad=",status_for_sad)

if(not status_for_sad):
	l = scraped_lyrics.split()
	#print(l)
	sad_count = 0
	for i in l:
		for j in sad_words:
			if re.match(j,i):
				sad_count+=1
				break

	print("sad count ",sad_count," out of ",len(l))


#---------------------------------------------------sad---------------------------------------------------------------------

