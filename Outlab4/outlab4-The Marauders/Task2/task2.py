import numpy as np
def reconstruct_from_noisy_patches(input_dict, shape):
    """
    input_dict: 
    key: 4-tuple: (topleft_row, topleft_col, bottomright_row, bottomright_col): location of the patch in the original image. topleft_row, topleft_col are inclusive but bottomright_row, bottomright_col are exclusive. i.e. if M is the reconstructed matrix. M[topleft_row:bottomright_row, topleft_col:bottomright_col] will give the patch.

    value: 2d numpy array: the image patch.

    shape: shape of the original matrix.
    """
    # Initialization: Initialise M, black_count, mid_count, white_count, mid_total
    M=np.zeros(shape)
    black_count=np.zeros(shape)
    mid_count=np.zeros(shape)
    white_count=np.zeros(shape)
    mid_total=np.zeros(shape)
    for topleft_row, topleft_col, bottomright_row, bottomright_col in input_dict: # no loop except this!
        tlr, tlc, brr, brc = topleft_row, topleft_col, bottomright_row, bottomright_col
        patch = input_dict[(tlr, tlc, brr, brc)]
        bc=black_count[tlr:brr,tlc:brc]
        mc=mid_count[tlr:brr,tlc:brc]
        wc=white_count[tlr:brr,tlc:brc]
        mt=mid_total[tlr:brr,tlc:brc]
        bc[np.where(patch==0)]+=1
        wc[np.where(patch==255)]+=1
        mc[np.where(patch%255!=0)]+=1
        mt[np.where(patch%255!=0)]+=patch[np.where(patch%255!=0)]
        
        # change black_count, mid_count, white_count, mid_total here
    # Finally change M here
    bc_wc=black_count-white_count
    
    tc=white_count+black_count+mid_count
    M[np.where(bc_wc>0)]=0
    M[np.where(bc_wc<=0)]=255
    M[np.where(tc==0)]=0
    M[np.where(mid_count!=0)]=np.rint(mid_total[np.where(mid_count!=0)]/mid_count[np.where(mid_count!=0)])
    M=M.astype('int64')

    
    return M # You have to return the reconstructed matrix (M).

