from bs4 import BeautifulSoup
import requests
import json
import urllib.request, urllib.parse
from lxml import etree

def search(searchfor):
	query = urllib.parse.urlencode({'q': searchfor})
	url = 'http://ajax.googleapis.com/ajax/services/search/web?v=1.0&%s&rsz=large' % query
	search_response = urllib.request.urlopen(url)
	search_results = search_response.read().decode("utf8")
	results = json.loads(search_results)
	data = results['responseData']
	hits = data['results']
	for i in hits: #first result is azlyrics
		if('azlyrics' in i['url']):
			url = i['url']
	print(url)
	response = requests.get(url)
	soup = BeautifulSoup(response.text)
	for link in soup.find_all('div'):
		if(link.get('style') != None):
			if("margin-left:10px;margin-right:10px;" in link.get('style')):
				print(link.text) #lyrics of the song
			
		
	'''print('Total results: %s' % data['cursor']['estimatedResultCount'])
	hits = data['results']
	print('Top %d hits:' % len(hits))
	for h in hits:
		print(' ', h['url'])
	print('For more results, see %s' % data['cursor']['moreResultsUrl'])'''

search('21 guns greenday lyrics') #to be got from the database - song+artist+lyrics


