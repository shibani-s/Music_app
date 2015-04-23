import re
lyrics="""You took my kisses and all my love
You taught me how to care
Am I to be just remnant of a one-sided love affair

All you took I gladly gave
There is nothing left for me to save

All of me
Why not take all of me
Can't you see
I'm no good without you
Take my lips
I want to lose them
Take my arms
I'll never use them
Your goodbye left me with eyes that cry
How can I go on dear, without you
You took the part that once was my heart
So why not take all of me"""

romantic = ["love","miss","life","kiss","hug","mine","heart","smile","eyes","honey","baby","lips","doll","night","dream","care","rain",
"forever","together","everything","darling","sweet","angel","faith","adore","dear"]
romantic_bit = 0
def is_romantic():
	l = lyrics.split()
	#print(l)
	romantic_count = 0
	for i in l:
		for j in romantic:
			if re.match(j,i):
				romantic_count+=1
				break

	print(romantic_count," out of ",len(l))


is_romantic()
		
