import os
import sys
import tarfile
def tardir():
	return_list=[]
	tf=tarfile.open(sys.argv[1] , "w:gz")
	p=len(sys.argv)-2
	for elem in range(p):
		k=elem+2
		if(os.path.exists(sys.argv[k])):
			for root, dirs, files in os.walk(sys.argv[k]):
				for file1 in files:
					stri=root+"/"+file1
					stri2=os.path.abspath(root+'/'+file1)
					stri3=stri2.replace('/','-')
					if stri3 in tf.getnames():
						pass
					else:
						tf.add(stri , arcname=stri3)
		else:
			return_list.append(sys.argv[k])
	return return_list

if(len(sys.argv)>=3):
	list_rec=tardir()
	if(len(list_rec)<(len(sys.argv)-2)):
		print("Successfully compressed")
		for x in list_rec:
			print(x)
	else:
		print("All files are missing")
else:
	print("Too few arguments")

