import sys
n = int(sys.stdin.readline().strip())
#print(n)
A=[]
count=0 # to ensure loop terminates
Dict={}
#lines1=sys.stdin.readlines().strip() doesn't iterate well
for l in sys.stdin:
	#print(f"{l} blah blah")
	if(l.isspace()): #to remove blank lines
		continue
	else:
		#s=str(l)
		count=count+1
		l=l[:-1] #to remove /n
		#l=l.strip() doesn't work
		#print(l)
		# print()
		B=[]
		B=l.split(':')
		#print(B)
		Dict[B[0]]={} # need to initialise to make nested dict
		s1=B[1]
		C=[]
		C=s1.split(',')
		#print(C)
		D=[]
		for c in C:
			D=c.split('-')
			Dict[B[0]][D[0]]=int(D[1])
			#print(D)
	if(count>=n):
		break
print(Dict)
Dictf={}
for k1,d1 in Dict.items():
	for k2 in d1.keys():
		Dictf[k2]=0
for k1,d1 in Dict.items():	
	for k2,v2 in d1.items():
		Dictf[k2]=Dictf[k2]+v2
#print(Dictf)
list_of_tuples=list((k,v) for k,v in sorted(Dictf.items(), key = lambda d: (d[1],d[0]), reverse=True))
#list_of_tuples=list(Dictf.items())
print(list_of_tuples)
#exit()

