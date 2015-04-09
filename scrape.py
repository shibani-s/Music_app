from bs4 import BeautifulSoup
import requests
import json
import urllib.request, urllib.parse
#from lxml import etree
song_lyrics=""
def scrape_az(url): #scrape azlyrics.com
	response = requests.get(url)
	soup = BeautifulSoup(response.text)
	global song_lyrics
	for link in soup.find_all('div'):
		if(link.get('style') != None):
			if("margin-left:10px;margin-right:10px;" in link.get('style')):
				#print(link.text) #lyrics of the song
				song_lyrics=song_lyrics+link.text
				#print(song_lyrics)
				return song_lyrics	

def search(searchfor):
		query = urllib.parse.urlencode({'q': searchfor})
		url = 'http://ajax.googleapis.com/ajax/services/search/web?v=1.0&%s&rsz=large' % query
		search_response = urllib.request.urlopen(url) #google search
		search_results = search_response.read().decode("utf8")
		results = json.loads(search_results)
		data = results['responseData']
		hits = data['results']
		for i in hits: 
			if('azlyrics' in i['url']):
				url = i['url']
				return scrape_az(url) 
	

#search("take me to church hozier" + "lyrics") #to be got from the database - song+artist+lyrics


