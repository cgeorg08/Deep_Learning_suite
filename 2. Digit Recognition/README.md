# Digit recognition problems

## Introduction

This repository contains two small problems associated with the digit-recognition problem. See their description below.

- <u>Problem 1:</u> Implementation of a Multi-class Perceptron training algorithm (from scratch). Model contains a single layer perceptron with 10 nodes (one per digit), each node having 256+1 inputs (inputs and bias) and 1 output. We train the network on the train set and evaluate on both the train and the test set.
- <u>Problem 2:</u> Construction of a Neural Network for identifying handwritten digits given only 16 features. In other words, the input file ("problem2_data.txt") has the following format: desired output/letter and 16 comma-seperated features in scale 0-15. The architecture of the network is based on the simple problem of XOR. 

    Example of a row in the file: T,2,8,3,5,1,8,13,0,6,6,10,8,0,8,0,8

## Problem 1 - Results

After 18 epochs the algorithm has correctly identified 1690 out of the 1707 examples
in the train set (99.238% success with an error rate of 0.025 in ≈ 60 seconds). After training the network we test its accuracy on the test set. We find a 86.5% success rate on the test set, with an error rate of (0.1549).

## Problem 2 - Results

Some insights for the specific problem are that with just one hidden layer we can achieve at most 75% acccuracy. Adding another hidden layer means that we can score approximately 90-95% accuarcy.