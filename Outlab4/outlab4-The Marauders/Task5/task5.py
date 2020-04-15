import numpy as np 
import pandas as pd
import matplotlib.pyplot as plt
import argparse

ap = argparse.ArgumentParser()
ap.add_argument("--data", required=True)
args = vars(ap.parse_args())

data=pd.read_csv(args['data'],delimiter=',')


instance1=data[data['instance']=='small_scale']
instance2=data[data['instance']=='medium_scale']
instance3=data[data['instance']=='large_scale']

instance1_eps=instance1[instance1['algorithm']=='epsilon-greedy']
instance1_non_eps=instance1[instance1['algorithm']!='epsilon-greedy']

instance2_eps=instance2[instance2['algorithm']=='epsilon-greedy']
instance2_non_eps=instance2[instance2['algorithm']!='epsilon-greedy']

instance3_eps=instance3[instance3['algorithm']=='epsilon-greedy']
instance3_non_eps=instance3[instance3['algorithm']!='epsilon-greedy']

instance1_eps=instance1_eps.groupby(['algorithm','horizon','epsilon'],as_index=False).mean()
instance1_non_eps=instance1_non_eps.groupby(['algorithm','horizon'],as_index=False).mean()                       

plt.figure()


instance1_eps['epsilon'] = 'epsilon-greedy with epsilon=' + instance1_eps['epsilon'].astype(str)

instance1_eps.set_index('horizon',inplace=True)
instance1_eps.groupby('epsilon')['REG'].plot(legend=True)

instance1_non_eps.set_index('horizon',inplace=True)
instance1_non_eps.groupby('algorithm')['REG'].plot(legend=True)

plt.title('Instance 1 - both axes in log scale')
plt.ylabel('Regret')
plt.xscale('log')
plt.yscale('log')

plt.savefig('instance1.png')
plt.close()


instance2_eps=instance2_eps.groupby(['algorithm','horizon','epsilon'],as_index=False).mean()
instance2_non_eps=instance2_non_eps.groupby(['algorithm','horizon'],as_index=False).mean()                       

plt.figure()
instance2_eps['epsilon'] = 'epsilon-greedy with epsilon=' + instance2_eps['epsilon'].astype(str)

instance2_eps.set_index('horizon',inplace=True)
instance2_eps.groupby('epsilon')['REG'].plot(legend=True)


instance2_non_eps.set_index('horizon',inplace=True)
instance2_non_eps.groupby('algorithm')['REG'].plot(legend=True)

plt.title('Instance 2 - both axes in log scale')
plt.ylabel('Regret')
plt.xscale('log')
plt.yscale('log')

plt.savefig('instance2.png')
plt.close()

instance3_eps=instance3_eps.groupby(['algorithm','horizon','epsilon'],as_index=False).mean()
instance3_non_eps=instance3_non_eps.groupby(['algorithm','horizon'],as_index=False).mean()                       

plt.figure()


instance3_eps['epsilon'] = 'epsilon-greedy with epsilon=' + instance3_eps['epsilon'].astype(str)

instance3_eps.set_index('horizon',inplace=True)
instance3_eps.groupby('epsilon')['REG'].plot(legend=True)

instance3_non_eps.set_index('horizon',inplace=True)
instance3_non_eps.groupby('algorithm')['REG'].plot(legend=True)

plt.title('Instance 3 - both axes in log scale')
plt.ylabel('Regret')
plt.xscale('log')
plt.yscale('log')
plt.savefig('instance3.png')
plt.close()