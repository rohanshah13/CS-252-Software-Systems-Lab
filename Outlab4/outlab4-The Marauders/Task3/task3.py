import numpy as np
import scipy as sp
from scipy.cluster.vq import kmeans2
from PIL import Image
import argparse

def clip_both_sides(arr):
    arr = np.rint(arr)
    arr[arr < 0] = 0
    arr[arr > 255] = 255
    arr = arr.astype('int')
    return arr

ap=argparse.ArgumentParser()
ap.add_argument("--input",required=True)
ap.add_argument("--k",required=True)
ap.add_argument("--output",required=True)
args=vars(ap.parse_args())

in1 = args["input"]
k1 = int(args["k"])
out1 = args["output"]

im = Image.open(in1)
rgb_im = im.convert('RGB')
pix = rgb_im.load()
data = np.asarray(rgb_im,dtype="float")

data_flat=data.reshape(-1,3)
cluster_centroid, label = kmeans2(data_flat,k1,minit='++')
#for i in np.arange(data_flat.shape[0]) :
	#data_flat[i]=cluster_centroid[label[i]]
l=np.arange(data_flat.shape[0])
data_flat[l]=cluster_centroid[label[l]]
data1=clip_both_sides(data)
image_cool = Image.fromarray(data1.astype('uint8'))
image_cool.save(out1)
#https://stackoverflow.com/questions/11064786/get-pixels-rgb-using-pil
