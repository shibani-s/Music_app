import urllib.request, urllib.parse
import re

song_name="diamonds";
artist="rihanna"

splitted_song_name=re.split(" ",song_name)
print(splitted_song_name)
joined_song_name="+".join(splitted_song_name)
print(joined_song_name)

splitted_artist=re.split(" ",artist)
print(splitted_artist)
joined_artist="+".join(splitted_artist)
print(joined_artist)


url="https://songbpm.com/"+joined_artist+"/"+joined_song_name;
print(url);
req=urllib.request.Request(url,None);
resp=urllib.request.urlopen(req)
data=resp.read()
print(data);
bpm_search=re.search(r'<div class="bpm side">.*?<div class="number">(\d+)</div>.*?<div class="text">BPM</div>',str(data));
if(bpm_search):
	print(bpm_search.group(0));
	print(bpm_search.group(1))
	song_bpm=bpm_search.group(1)


