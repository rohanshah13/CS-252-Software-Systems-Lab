import numpy as np
from matplotlib import pyplot as plt
def mean_filter(arr, k):
	temp=arr.astype('float64')
	s=np.r_[np.zeros(k),temp,np.zeros(k)]
	cumulative_sum_arr=np.cumsum(s)
	cumulative_sum_arr_1=np.r_[cumulative_sum_arr,np.zeros(2*k+1)]
	cumulative_sum_arr_2=np.r_[np.zeros(2*k+1),cumulative_sum_arr]

	subtraction_arr=cumulative_sum_arr_1-cumulative_sum_arr_2
	answer_array=subtraction_arr[2*k:len(arr)+2*k]
	answer_array=answer_array/(2*k+1)
	return answer_array

def generate_sin_wave(period,range_,num):
	domain=np.linspace(range_[0],range_[1],num)*2*np.pi/period
	return np.sin(domain)

def noisify(array,var):
	return array + np.random.normal(0,np.sqrt(var),len(array))
