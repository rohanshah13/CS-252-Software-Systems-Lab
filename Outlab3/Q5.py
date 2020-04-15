import argparse
import csv
import os
ap = argparse.ArgumentParser(usage=argparse.SUPPRESS)


def error(self, message):
	
	args = {'prog': self.prog, 'message': message}
	self.exit(2, ('%(message)s\n') % args)

argparse.ArgumentParser.error=error

ap.add_argument("--first_name", required=True)
ap.add_argument("--last_name", required=True)
ap.add_argument("--roll_no", required=True)
ap.add_argument("--gender", required=True)
ap.add_argument("--mobile", required=True)
ap.add_argument("--dept", required=True)
ap.add_argument("--CGPA", required=True)

args = vars(ap.parse_args())

print('Successfully Added!!')
row=['First Name','Last Name','Roll Number','Gender','Mobile','Dept','CGPA']
lines=[]
lines.append(row)
if not os.path.exists('student_database.csv'):
	with open('student_database.csv', 'w') as csvFile:
		writer = csv.writer(csvFile)
		writer.writerows(lines)
x=[args['first_name'],' '+args['last_name'],' '+args['roll_no'],' '+args['gender'],' '+args['mobile'],' '+args['dept'],' '+args['CGPA']]
data=[]
data.append(x)

with open('student_database.csv', 'a') as csvFile:
		writer = csv.writer(csvFile)
		writer.writerows(data)


