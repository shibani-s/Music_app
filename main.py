import requests
from bs4 import BeautifulSoup
import urllib.request, urllib.parse
import pymysql
import scrape
import re

song="take me to church hozier"


#db = pymysql.connect(host="localhost", user="root", passwd="", db="music_app")
#cur = db.cursor()
#cur.execute("SELECT name FROM songs")
#row = cur.fetchall()
#print (row[1])


#------------------------------------------vocabulary----------------------------------------------------------------------------

romantic = ["love","miss","life","kiss","hug","mine","heart","smile","eyes","honey","baby","lips","doll","night","dream","care","rain",
"forever","together","everything","darling","sweet","angel","faith","adore","dear"]
happy=["aglow","air","alive","altruis","shrine"]
"""["aglow","air","alive","altruis","amaze","amazed","amazing","amuse","amused","anew","angels","anticipate","anticipation","apples","apple pie","autumn","awe","awesome","bask","baby","balloons","beach","beautiful","birds","birthday","blessed","bliss", "blissful","blithe","bloom","blooming","blossom","blush","book", "books","breathe","breeze","cool breeze","bright","brownies","brunch","bubbles","bubble bath","bubbly","butterflies","butterfly kisses","cake","calm","camaraderie","candlelight","casual","celebrate","celebration","cheer", "cheerful","say cheese…","child", "childhood", "children","chocolate","comfort","compliments","cookies and milk","cool","cooperate","cooperation","comedy","comic","companion","companionship","compassion","congratulations","content","cozy","cuddle","curl up","cute","daisy", "daisies","dance","dawn","day","daylight","dear","delight","delightful","dew","dimples","dreams","drizzle","ecstasy","elated", "elation","empathy","enjoy","enthusiastic","euphoric","excellence","exhale","exubera","family","father","favor","fire place","flowers","fly","fortunate","friend", "friends","best friends","friendship","freckles","free","fresh","full","fun","funny","generosity","give","glad","glee","glitter","glow","glowing","good","good thoughts","garden","gifts","giggles","glee","good morning","good afternoon","good evening","grace","gracious","grass","gratitude","great weather","grin","grow","hallelujah","hands","happiness","happy","happy anniversary","happy birthday","happy-go-lucky","harmony","head-over-heels","healthy","heart","heaven","hide and seek","hobby","holiday", "holidays","honey","hope","hopeful","hot dogs","hubby","hug","humor","humorous","ice cream","idea","inspiration","inspirational","inspired","jello","jelly beans","joke","jolly","jolly notes","joy", "joyful","joyous","jubilant","kindness","kiss, kisses","kittens","lake","leaves","lego","laugh, laughter","life","light","lighthearted","lily, lilies","lollipops","love","love struck","I love you","love you","much love","lovely","lullaby","lunch","magical","marshmallows","melody","merry","mild","miss you,,missing you","I’ll miss you","mom","mother","music","nap","nature","natural","nostalgic","ocean","open","optimistic","oreos","overjoyed","pancakes","patient, patience","passion","peace, peaceful","pets","pictures","pie","pillows","pillow fight","play, playful","play dough","pleasant","pleased","pleasure","poetry","positive","presents","puppy, puppies","quality","quiet","rain","rainbow","rapture","red","red letter day","relax","relief","renewal","respect","rest","restore","river","rose, roses","run","safe","salvation","sand","satisfaction","seventh heaven","shine","silly","sing, singing","simple","sky","sleep","good smells","smell of food","smell of roses","smell of rain","smile, smiles, smiling","smitten","smooch,,smoochies:),snow angels","snow flakes","snow man","snuggles","soft","song, songs","sooth","soothing","sparkle","special","spiritual","splash","spontaneous","spring","stars","still","strawberries","stretch","success","summer","sun","sunlight","sunshine","sunrise","sunset","surprise","sweet","sweetheart","sweet dreams","swing","tasty","tea cozy","tickle","tire swings","tiny toes","thanks,,thank you","thinking of you","thrill","together","togetherness","touch","toys","travel","trees","tree house","truth","twilight","upbeat","uplifting","useful","vacation","vivacious","walk","warm","warmth","warm weather","water","water-balloons","waterfall","waves","sound of waves","weekend","welcome","welcoming","whole","whoosh","woo-hoo","win","wish"," wishes","wish you were here","wonder","wonderful","yellow","yay","yippee","yum", "yummy","amused","beaming","blissful","blithe","buoyant","carefree","cheerful","cheery","chipper","chirpy","content","contented","delighted","ebullient","ecstatic","elated","enraptured","euphoric","exhilarated","exultant","funny,,glad","gleeful","gratified","grinning","happy","in a good mood","in good spirits","in seventh heaven","invigorated","jocular","jolly","jovial","joyful","joyous","jubilant","light-hearted","merry","mirthful","never been better,,on cloud nine","on top of the world","optimistic","overjoyed","over-the-moon","pleased","radiant","rapturous","satisfied","smiling","sunny","thrilled","tickled pink","untroubled","upbeat","walking on air"]"""

sad_words=["sad", "unhappy" ,"gloomy" ,"sombre" ,"melancholy","sorrowful" ,"subdued" ,"bleak" ,"wistful" ,"homesick","bad", "blue", "brokenhearted", "cast down", "crestfallen", "dejected", "depressed", "despondent", "disconsolate", "doleful", "down", "downcast", "downhearted", "down in the mouth","droopy", "forlorn", "gloomy", "glum", "hangdog",  "heartbroken", "heartsick", "heart-sore", "heavyhearted", "inconsolable", "joyless","low", "low-spirited", "melancholi","melancholy", "miserable", "mournful", "saddened", "sorrowful", "sorry", "unhappy", "woebegone","woeful", "wretched","aggrieved", "distressed", "troubled", "uneasy", "unquiet", "upset", "worried", "despairing", "hopeless", "sunk", "disappointed", "discouraged", "disheartened", "dispirited", "suicidal", "dolorous", "lachrymose", "lugubrious", "plaintive", "tearful","regretful", "rueful", "agonized", "anguished", "grieving", "wailing", "weeping", "black", "bleak", "cheerless", "comfortless", "dark", "darkening","depressing", "desolate", "dismal", "drear", "dreary", "elegiac", "funereal", "gray", "morbid", "morose", "murky", "saturnine", "somber" , "sullen"]

#------------------------------------------vocabulary----------------------------------------------------------------------------



#------------------------------------------urls-----------------------------------------------------------------------------------

urls_for_happy=["http://www.songfacts.com/category-songs_about_joy_and_happiness.php","http://www.songfacts.com/category-songs_about_rockin%27_out.php","http://www.songfacts.com/category-new_year_songs.php"]
urls_for_sad=["http://www.songfacts.com/category-songs_about_depression.php","http://www.songfacts.com/category-songs_about_the_dangers_of_materialism.php","http://www.songfacts.com/category-songs_about_an_ex-girlfriend_or_ex-boyfriend.php","http://www.songfacts.com/category-songs_about_suicide.php","http://www.songfacts.com/category-songs_about_loneliness_or_isolation.php","http://www.songfacts.com/category-songs_about_marital_problems_or_divorce.php"]

#------------------------------------------urls-----------------------------------------------------------------------------------


#------------------------------------------scrape-----------------------------------------------------------------------

scraped_lyrics=scrape.search(song+" lyrics")
print(scraped_lyrics)

#-----------------------------------------------------------------------------------------------------------------------



#-------------------------------------------romantic----------------------------------------------------------------------------------

#input = "All Out Of Love"
input = ""
new_input = ""
status_romantic=False

l = input.split();
for i in l: #modify the input to suit the format of the site
	new_input = new_input + i.lower() 
#print(new_input)
url = "http://www.theromantic.com/lovesongs/main.htm"
response = requests.get(url)
soup = BeautifulSoup(response.text)
'''
for i in soup.find_all('a',href = True):
	#print(i['href'])
	if new_input in i['href']:
		print("found")
		status_romantic=True
#		cur.execute("update songs set Romantic=1 where Name=%s", (input,))
		break
'''
if(not status_romantic):    #if not present in site
	l = scraped_lyrics.split()
	#print(l)
	romantic_count = 0
	for i in l:
		for j in romantic:
			if re.match(j,i):
				romantic_count+=1
				break

	print("romantic count is ",romantic_count," out of ",len(l))


#db.commit()

#---------------------------------------------------romantic------------------------------------------------------------------------------



#---------------------------------------------------happy----------------------------------------------------------------------
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
			break;
	i+=1
print("status_for_happy=",status_for_happy)

if(not status_for_happy):
	l = scraped_lyrics.split()
	#print(l)
	happy_count = 0
	for i in l:
		for j in happy:
			if re.match(j,i):
				happy_count+=1
				break

	print("happy count ",happy_count," out of ",len(l))
	

#---------------------------------------------------sad----------------------------------------------------------------------

status_for_sad=False
i=0
while(i<len(urls_for_sad)):
	req=urllib.request.Request(urls_for_sad[i],None);
	resp=urllib.request.urlopen(req)
	data=resp.read()
	songs=re.findall('<li><a href=\"/detail.php\?id=(.*?)\">(.*?)</a> - (.*?)</li>',str(data))
#	print(songs)
	for j in songs:
		#print(j[1]+":"+j[2])
		if(song==j[1] and artist==j[2]):
			status_for_sad=True
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

