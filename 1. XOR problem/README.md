# The XOR problem

## Introduction
The XOR problem is a way to get to know how Neural Networks work. In other words, we want to play around with:
- the Forward Phase: find the value of each neuron by performing a dot product operation between neurons in the previous layers and weights attached to their edges, and 
- the Backward Phase: find derivatives and update weights.

## Procedure 
The XOR problem is a relatively easy problem since both the training and the testing set consist of the same 4 records with input values and desired output presented as follows:

1 , 0 -> 1

0 , 1 -> 1

1 , 1 -> 0

0 , 0 -> 0

Having the above format in mind we costruct a Neural Network with 1 input layer of 2 neurons, 1 hidden layer with 2 neurons and 1 output layer having just 1 output neuron. Sigmoid activation is used for neurons in hidden and output layers and weights are initialized randomly. Implementation is done in Java (3 java files + a txt file) and Python (notebook). 

## Results and Experiments
- Different randomly starting values help neural networks converge to the global minimum. Also, it is a bad practise to initialize all neurons with the same weights since they will compute the same gradient end up doing the same adjustments.
- The learning rate parameter plays a significant role since large learning rate produces large change in weights and our model may never converge. We achieved our best results with the learning rate parameter being relatively low, specifically when η = 0.2.
- We can achieve 100% accuracy after a few iterations (about 1000) with considerably low MSE (approximately 0.10).
