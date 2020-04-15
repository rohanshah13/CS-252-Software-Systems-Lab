import re
import sys

f = open(sys.argv[1], "r")
freq_Dict={}

for x in f:
	y = re.findall(r"\b[1-9][0-9]{9}\b", x)
	for z in y:
		if z in freq_Dict:
			freq_Dict[z]=freq_Dict[z]+1
		else:
			freq_Dict[z]=1
	y = re.findall(r"\b(?:[A-Za-z0-9]+[.]|[A-Za-z0-9]+[_])*[A-Za-z0-9]+[@](?:[A-Za-z0-9]+[.])*[A-Za-z]+\b", x)
	for z in y:
		if z in freq_Dict:
			freq_Dict[z]=freq_Dict[z]+1
		else:
			freq_Dict[z]=1

print("my frequency: "+str(freq_Dict[sys.argv[2]]))

my_freq=freq_Dict[sys.argv[2]]
cheater=False
for x in freq_Dict:
	if(freq_Dict[x]>my_freq):
		print("Cheater alert! "+x+" "+str(freq_Dict[x]))
		cheater=True

if not cheater:
	print("It's all good yo!")