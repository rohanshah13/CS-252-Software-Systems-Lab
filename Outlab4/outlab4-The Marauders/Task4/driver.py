import numpy as np
from matplotlib import pyplot as plt
import task4 

def driver():
	clean_sin=task4.generate_sin_wave(2,(-2,8),1000)
	plt.plot(np.linspace(-2,8,1000),clean_sin)
	plt.savefig('clean_sin.png')
	plt.close()

	dirty_sin=task4.noisify(clean_sin,0.05**2)
	plt.plot(np.linspace(-2,8,1000),dirty_sin)
	plt.savefig('dirty_sin.png')
	plt.close()

	cleaned_sin=task4.mean_filter(dirty_sin,1)
	plt.plot(np.linspace(-2,8,1000),cleaned_sin)
	plt.savefig('cleaned_sin.png')
	plt.close()

driver()

