import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import random
from scipy.misc import derivative

def fn_plot1d(fn, x_min, x_max, filename):
	x = np.linspace(x_min,x_max,1000)
	vfunc = np.vectorize(fn)
	y=vfunc(x)
	plt.plot(x, y)
	
	plt.savefig(filename+".png")
	plt.close()

def nth_derivative_plotter(fn, n, x_min, x_max, filename):
	x=np.linspace(x_min,x_max,1000)
	vfunc=np.vectorize(derivative)
	if(n%2==0):
		y=vfunc(fn, x, n=n,dx=(x_max-x_min)/999,order=n+1)
	else:
		y=vfunc(fn, x, n=n,dx=(x_max-x_min)/999,order=n+2)

	plt.title(str(n)+"th derivative of the given function")	
	plt.plot(x,y)
	plt.savefig(filename+".png")
	plt.close()

def fn_plot2d(fn, x_min, x_max, y_min, y_max, filename):
	fig = plt.figure()
	ax = fig.add_subplot(111, projection='3d')
	x = np.linspace(x_min, x_max, 1000)
	y = np.linspace(y_min, y_max, 1000)
	X, Y = np.meshgrid(x, y)
	vfunc = np.vectorize(fn)
	zs = np.array(vfunc(np.ravel(X), np.ravel(Y)))
	Z = zs.reshape(X.shape)

	ax.plot_surface(X, Y, Z)

	plt.savefig(filename+".png")
	plt.close()

def b(x):
    return g(np.absolute(x))

def g(x):
	y=0
	num=h(2-x)
	den=num + h(x-1)
	y=(num/den)
	return y

def h(x):
	y=0
	if(x>0):
		y=np.exp(-1/(x**2))
	else:
		y=0
	return y

def twodsinc(x,y):
	a=np.sqrt(x**2 + y**2)
	if(a>0):
		return np.sin(a)/a
	else:
		return 1

fn_plot1d(b,-2,2,"fn1plot")
lower_lim=(-1.5)*(np.pi)
upper_lim=(1.5)*(np.pi)
fn_plot2d(twodsinc,lower_lim,upper_lim,lower_lim,upper_lim,"fn2plot")
i=1
while(i<=10):
	nth_derivative_plotter(b,i,-2,2,"bn_"+str(i)+".png")
	i=i+1
