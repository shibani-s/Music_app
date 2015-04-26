import requests
from bs4 import BeautifulSoup
import urllib.request, urllib.parse
import pymysql
import scrape
import re

devotional=["adherence","adoration","allegiance","dedication","defence","enthsiasm","faith","fealty","fevor","fidelity","piety","reverence","worship","zeal","ardor","consecration","devotedness","devoutness","earnestess","fondness","spiritual","devotional","pray","solemn"];


scraped_lyrics=scrape.search(song+" lyrics")
print(scraped_lyrics)


l = scraped_lyrics.split()
#print(l)
devotional_count = 0
for i in l:
	for j in devotional:
		if re.match(j,i):
			devotional_count+=1
			break

print("devotional count ",devotional_count," out of ",len(l))

