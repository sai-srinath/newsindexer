import numpy as np
from scipy.optimize import minimize
from scipy.io import loadmat
from math import sqrt


def initializeWeights(n_in,n_out):
    """
    # initializeWeights return the random weights for Neural Network given the
    # number of node in the input layer and output layer

    # Input:
    # n_in: number of nodes of the input layer
    # n_out: number of nodes of the output layer
       
    # Output: 
    # W: matrix of random initial weights with size (n_out x (n_in + 1))"""
    
    epsilon = sqrt(6) / sqrt(n_in + n_out + 1);
    W = (np.random.rand(n_out, n_in + 1)*2* epsilon) - epsilon;
    return W
    
    
    
def sigmoid(z):
    
    """# Notice that z can be a scalar, a vector or a matrix
    # return the sigmoid of input z"""
    g=1/(1+np.exp(-z))
    return g  #your code here
    

    
    

def preprocess():
    """ Input:
     Although this function doesn't have any input, you are required to load
     the MNIST data set from file 'mnist_all.mat'.

     Output:
     train_data: matrix of training set. Each row of train_data contains 
       feature vector of a image
     train_label: vector of label corresponding to each image in the training
       set
     validation_data: matrix of training set. Each row of validation_data 
       contains feature vector of a image
     validation_label: vector of label corresponding to each image in the 
       training set
     test_data: matrix of training set. Each row of test_data contains 
       feature vector of a image
     test_label: vector of label corresponding to each image in the testing
       set

     Some suggestions for preprocessing step:
     - divide the original data set to training, validation and testing set
           with corresponding labels
     - convert original data set from integer to double by using double()
           function
     - normalize the data to [0, 1]
     - feature selection"""
    
    mat = loadmat('/Users/saisrinath/Projects/Canopy_Projects/basecode/mnist_all.mat') #loads the MAT object as a Dictionary
    
    #Pick a reasonable size for validation data
    
    
    #Your code here
    train_data = np.array([]).astype('float64').reshape(0,784)
    train_label = np.array([]).reshape(0,1)
    validation_data = np.array([]).astype('float64').reshape(0,784)
    validation_label = np.array([])
    test_data = np.array([]).astype('float64').reshape(0,784)
    test_label = np.array([]).reshape(0,1)
    
    
    for i in range(10):               
        train_data=np.vstack((train_data,mat.get('train'+str(i))))    
        tmp=np.repeat(i,mat['train'+str(i)].shape[0])
        tmp_col=np.matrix(tmp)    
        tmp1_col=tmp_col.T
        train_label=np.vstack((train_label,tmp1_col))
        test_data=np.vstack((test_data,mat.get('test'+str(i))))
        tmp_test=np.repeat(i,mat['test'+str(i)].shape[0])
        tmp_coltest=np.matrix(tmp_test)    
        tmp1_coltest=tmp_coltest.T
        test_label=np.vstack((test_label,tmp1_coltest))
    a = range(train_data.shape[0])
    aperm = np.random.permutation(a)
    validation_data = train_data[aperm[0:10000],:]
    validation_label= train_label[aperm[0:10000],:]
    train_data = train_data[aperm[10000:],:]
    train_label= train_label[aperm[10000:],:]
    
    train_data=train_data/255
    validation_data=validation_data/255
    test_data=test_data/255
    
    
    return train_data, train_label, validation_data, validation_label, test_data, test_label
    
    
    

def nnObjFunction(params, *args):
    """% nnObjFunction computes the value of objective function (negative log 
    %   likelihood error function with regularization) given the parameters 
    %   of Neural Networks, thetraining data, their corresponding training 
    %   labels and lambda - regularization hyper-parameter.

    % Input:
    % params: vector of weights of 2 matrices w1 (weights of connections from
    %     input layer to hidden layer) and w2 (weights of connections from
    %     hidden layer to output layer) where all of the weights are contained
    %     in a single vector.
    % n_input: number of node in input layer (not include the bias node)
    % n_hidden: number of node in hidden layer (not include the bias node)
    % n_class: number of node in output layer (number of classes in
    %     classification problem
    % training_data: matrix of training data. Each row of this matrix
    %     represents the feature vector of a particular image
    % training_label: the vector of truth label of training images. Each entry
    %     in the vector represents the truth label of its corresponding image.
    % lambda: regularization hyper-parameter. This value is used for fixing the
    %     overfitting problem.
       
    % Output: 
    % obj_val: a scalar value representing value of error function
    % obj_grad: a SINGLE vector of gradient value of error function
    % NOTE: how to compute obj_grad
    % Use backpropagation algorithm to compute the gradient of error function
    % for each weights in weight matrices.

    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % reshape 'params' vector into 2 matrices of weight w1 and w2
    % w1: matrix of weights of connections from input layer to hidden layers.
    %     w1(i, j) represents the weight of connection from unit j in input 
    %     layer to unit i in hidden layer.
    % w2: matrix of weights of connections from hidden layer to output layers.
    %     w2(i, j) represents the weight of connection from unit j in hidden 
    %     layer to unit i in output layer."""
    
    n_input, n_hidden, n_class, training_data, training_label, lambdaval = args
    
    w1 = params[0:n_hidden * (n_input + 1)].reshape( (n_hidden, (n_input + 1)))
    w2 = params[(n_hidden * (n_input + 1)):].reshape((n_class, (n_hidden + 1)))
    obj_val = 0  
    
    #Your code here
    #
    #
    #
    #
    #
    #
    
    grad_w1=np.zeros(w1.shape)
    grad_w2=np.zeros(w2.shape)
    #gradw1sum = np.zeros(w1.shape)
    #gradw2sum = np.zeros(w2.shape)
    enc=np.array([0]*10)
    for i in range(training_data.shape[0]):  
        arr = np.array([0]*10)
            
        index=training_label[i]
        index=int(index)
        arr[index]=1
        enc=np.vstack((enc,arr))
    enc=enc[1:]
    
    training_data=np.c_[training_data,np.ones(training_data.shape[0])]   
    hlayer=np.dot(training_data,w1.T)#check parameter order
    hlayer=sigmoid(hlayer)
    
    hlayer=np.append(hlayer,np.ones([len(hlayer),1]),1)   
    
    #forward pass for second net
        
    out=np.dot(hlayer,w2.T)#check parameter order
    out=sigmoid(out)
    
    #print enc.shape
    #print out.shape
    tmp_array=enc*np.log(out) + (1-enc)*np.log(1-out)
        
    obj_val+=np.sum(tmp_array)
        
        
        
    delvalue=out-enc
        
    
    grad_w2=np.dot(delvalue.T,hlayer)
    
        
        
    
    tmp=np.zeros((1,n_hidden+1))
    
    tmp=(1-hlayer)*(hlayer)*tmp
    
    tmp= np.delete(tmp,np.s_[-1:],1)
        
    
    
    
    grad_w1=np.dot(tmp.T,training_data)
    
    
    obj_val+=lambdaval*(np.sum(np.square(w1))+np.sum(np.square(w2)))/2
    grad_w1=(grad_w1+lambdaval*w1)/training_data.shape[0]
    grad_w2=(grad_w2+lambdaval*w2)/training_data.shape[0]
    
    
    
    
    
    #Make sure you reshape the gradient matrices to a 1D array. for instance if your gradient matrices are grad_w1 and grad_w2
    #you would use code similar to the one below to create a flat array
    obj_grad = np.concatenate((grad_w1.flatten(), grad_w2.flatten()),0)
    
    obj_val=-1*obj_val/training_data.shape[0]
    
    return (obj_val,obj_grad)



def nnPredict(w1,w2,data):
    
    """% nnPredict predicts the label of data given the parameter w1, w2 of Neural
    % Network.

    % Input:
    % w1: matrix of weights of connections from input layer to hidden layers.
    %     w1(i, j) represents the weight of connection from unit i in input 
    %     layer to unit j in hidden layer.
    % w2: matrix of weights of connections from hidden layer to output layers.
    %     w2(i, j) represents the weight of connection from unit i in input 
    %     layer to unit j in hidden layer.
    % data: matrix of data. Each row of this matrix represents the feature 
    %       vector of a particular image
       
    % Output: 
    % label: a column vector of predicted labels""" 
    
    labels = np.array([]).reshape(0,1)
    #Your code here
    #ip=np.zeros((1,(w1.shape[1]-1)))
    #hlayer=np.zeros((1,w1.shape[0]))
    #out=np.zeros((1,w2.shape[0]))
    #hlayer[0][w1.shape[0]]=1
    for i in range(data.shape[0]):
        ip=np.concatenate((data[i,:],[1]))
        ip=ip.reshape(1,ip.size)
    data=np.c_[data,np.ones(data.shape[0])]
    print 'data:'
    print data.shape
    print 'w1'
    print w1.shape
    hlayer=np.dot(data,w1.T)
    hlayer=sigmoid(hlayer)
    hlayer=np.append(hlayer,np.ones([len(hlayer),1]),1)
    print 'hlayer'
    print hlayer.shape
    print hlayer
    print 'w2'
    print w2.shape
    out=np.dot(hlayer,w2.T)
    out=sigmoid(out)
    print 'out'
    print out.shape
    print out
    for i in range(out.shape[0]):
        #label=np.array([0]*10)
        #label[np.argmax(out[i])]=1
    
    #x=np.argmax(out)
    #print x
        labels=np.vstack((labels,np.argmax(out[i])))

    print labels.shape
        
    return labels
    
    '''n_input = w1.shape[1] - 1
    n_hidden = w1.shape[0]
    n_class = w2.shape[0]
    n_samples = data.shape[0];
    inp = np.zeros((1,n_input+1))
    hid = np.zeros((1,n_hidden+1))
    out = np.zeros((1,n_class))
    label = np.array([])
    hid[0][n_hidden] = 1 #setting bias terms
    initLabel = False
    for sam in range(n_samples):
        inp = np.concatenate((data[sam,:],[1])) #appending bias terms
        inp = inp.reshape(1,inp.size)
        # feed forward
        hid[0][:-1] = np.dot(inp,w1.T)
        hid[0][:-1] = sigmoid(hid[0][:-1])
        out = np.dot(hid,w2.T)
        out = sigmoid(out)
        temp = np.array([0]*10)
        temp[np.argmax(out)] = 1
        if (initLabel):
            label = np.vstack((label,temp))
        else:
            label = np.copy(temp)
            initLabel = True
    #print label
    return label'''
    '''bias_col = np.ones_like(data[:,-1]).reshape(-1, 1)
    data_bias = np.hstack((data, bias_col))
    data_bias_w1 = sigmoid(np.dot(data_bias,np.transpose(w1)))
    data_bias_w1_bias = np.hstack((data_bias_w1,bias_col))
    labels = sigmoid(np.dot(data_bias_w1_bias,np.transpose(w2)))
    amax = np.argmax(labels,1);
    tmparr = np.array([[]]).reshape(0,10)
    for i in range(0,amax.size):
        tmptmp = np.repeat(0,10);
        tmptmp[amax[i]] = 1;
        tmparr = np.vstack((tmparr,tmptmp))
    # return labels
    # print tmparr == labels.argmax(0)
    return tmparr'''


"""**************Neural Network Script Starts here********************************"""

train_data, train_label, validation_data,validation_label, test_data, test_label = preprocess();


#  Train Neural Network

# set the number of nodes in input unit (not including bias unit)
n_input = train_data.shape[1]; 

# set the number of nodes in hidden unit (not including bias unit)
n_hidden = 80;
				   
# set the number of nodes in output unit
n_class = 10;				   

# initialize the weights into some random matrices
initial_w1 = initializeWeights(n_input, n_hidden);
initial_w2 = initializeWeights(n_hidden, n_class);

# unroll 2 weight matrices into single column vector
initialWeights = np.concatenate((initial_w1.flatten(), initial_w2.flatten()),0)

# set the regularization hyper-parameter
lambdaval = 0.4;


args = (n_input, n_hidden, n_class, train_data, train_label, lambdaval)

#Train Neural Network using fmin_cg or minimize from scipy,optimize module. Check documentation for a working example

opts = {'maxiter' : 50}    # Preferred value.

nn_params = minimize(nnObjFunction, initialWeights, jac=True, args=args,method='CG', options=opts)

#In Case you want to use fmin_cg, you may have to split the nnObjectFunction to two functions nnObjFunctionVal
#and nnObjGradient. Check documentation for this function before you proceed.
#nn_params, cost = fmin_cg(nnObjFunctionVal, initialWeights, nnObjGradient,args = args, maxiter = 50)


#Reshape nnParams from 1D vector into w1 and w2 matrices
w1 = nn_params.x[0:n_hidden * (n_input + 1)].reshape( (n_hidden, (n_input + 1)))
w2 = nn_params.x[(n_hidden * (n_input + 1)):].reshape((n_class, (n_hidden + 1)))


#Test the computed parameters

predicted_label = nnPredict(w1,w2,train_data)
print 'predicted'
print predicted_label


#find the accuracy on Training Dataset

print('\n Training set Accuracy:' + str(100*np.mean((predicted_label == train_label).astype(float))) + '%')
print 'train'
print train_label

predicted_label = nnPredict(w1,w2,validation_data)

#find the accuracy on Validation Dataset

print('\n Validation set Accuracy:' + str(100*np.mean((predicted_label == validation_label).astype(float))) + '%')


predicted_label = nnPredict(w1,w2,test_data)

#find the accuracy on Validation Dataset

print('\n Test set Accuracy:' + str(100*np.mean((predicted_label == test_label).astype(float))) + '%')