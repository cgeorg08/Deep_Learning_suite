import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainClass {

	/*
	 * This is the mainClass.  In this class you control the program.  Some actions are: take the arguments from the input files,
	 * initialise the weights and the bias of each neuron, train and test the neural network.  We also print some results in some
	 * files.   
	 */
	public static void main(String[] args) throws FileNotFoundException {	
		
		//get all the information from the text files and initialise the variables
		File paramf = new File(args[0]);
		File trainf = new File(args[1]);
		File testf = new File(args[2]);
		
		Scanner scanp = new Scanner(new FileInputStream(paramf));		
		Scanner scantr = new Scanner(new FileInputStream(trainf));
		Scanner scante = new Scanner(new FileInputStream(testf));

		int numNeuHid1 = 0;
		int numNeuHid2 = 0;
		int numNeuInp = 0;
		int numNeuOut = 0;
		double n = 0.0;
		double a = 0.0;
		int maxIter = 0;		
		
		int pat = 0;
		int col = 3;
				
		while(scanp.hasNext()) {
		String p = scanp.next();
			if (p.equals("numHiddenLayerOneNeurons"))
				numNeuHid1 = Integer.parseInt(scanp.next());
			if (p.equals("numHiddenLayerTwoNeurons"))
				numNeuHid2 = Integer.parseInt(scanp.next());
			if (p.equals("numInputNeurons"))
				numNeuInp = Integer.parseInt(scanp.next());
			if (p.equals("numOutputNeurons"))
				numNeuOut = Integer.parseInt(scanp.next());
			if (p.equals("learningRate"))
				n = Double.parseDouble(scanp.next());
			if (p.equals("momentum"))
				a = Double.parseDouble(scanp.next());
			if (p.equals("maxIterations"))
				maxIter = Integer.parseInt(scanp.next());				
		}
				
		while(scantr.hasNextLine()) {
			pat = pat + 1;
			scantr.nextLine();
		}

		int trainTab[][] = new int [pat][col];

		int num = 0;
		int x = 0;
		int y = 0;
		scantr = new Scanner(new FileInputStream(trainf));
		while(scantr.hasNext()) {
			num = scantr.nextInt();
			trainTab[x][y] = num;
			y = y + 1;
			if (y == col) {
				y = 0;
				x = x + 1;
			}		
		}
	
		int patTest = 0;
		while(scante.hasNextLine()) {
			patTest = patTest + 1;
			scante.nextLine();
		}

		int testTab[][] = new int [patTest][col];

		int numt = 0;
		int xt = 0;
		int yt = 0;
		scante = new Scanner(new FileInputStream(testf));
		while(scante.hasNext()) {
			numt = scante.nextInt();
			testTab[xt][yt] = numt;
			yt = yt + 1;
			if (yt == col) {
				yt = 0;
				xt = xt + 1;
			}		
		}	
				
		scanp.close();
		scantr.close();
		scante.close();
		
		//make the Neural Network object
		int tot = numNeuHid1 + numNeuHid2 + numNeuInp + numNeuOut;
		NeuralNetwork nn = new NeuralNetwork(numNeuHid1,numNeuHid2,numNeuInp,numNeuOut,n,a);

		//initialise weights and bias only 1 time
		nn.getNeuron(0).setWeight(0,nn.getRandom());	
		nn.getNeuron(0).setWeight(1,nn.getRandom());	
		nn.getNeuron(1).setWeight(0,nn.getRandom());	
		nn.getNeuron(1).setWeight(1,nn.getRandom());	
		nn.getNeuron(2).setWeight(0,nn.getRandom());	
		nn.getNeuron(3).setWeight(0,nn.getRandom());	
		
		nn.getNeuron(0).setBias(nn.getRandom());		
		nn.getNeuron(1).setBias(nn.getRandom());		
		nn.getNeuron(2).setBias(nn.getRandom());		
		nn.getNeuron(3).setBias(nn.getRandom());		
		nn.getNeuron(4).setBias(nn.getRandom());		
		
		String name = new String ();
		name = "results.txt";
		File objfile = new File(name);				
		PrintWriter pr=null;
		try {
			pr=new PrintWriter(new FileOutputStream(name));
		}
		catch(FileNotFoundException e) {
			System.out.println("Error opening file");
			System.exit(0);
		}
		
		String errname = new String ();
		errname = "errors.txt";
		File errfile = new File(errname);				
		PrintWriter pr1=null;
		try {
			pr1=new PrintWriter(new FileOutputStream(errname));
		}
		catch(FileNotFoundException e) {
			System.out.println("Error opening file");
			System.exit(0);
		}
		
		String sucname = new String ();
		sucname = "successrate.txt";
		File sucfile = new File(sucname);				
		PrintWriter pr2=null;
		try {
			pr2=new PrintWriter(new FileOutputStream(sucname));
		}
		catch(FileNotFoundException e) {
			System.out.println("Error opening file");
			System.exit(0);
		}		
		
		pr.println("n=" + n + "//a=" + a + "//iterations=" + maxIter);
		
		double temp1,temp2;
		double percent1,percent2,success1,success2;
		percent1 = 100 / pat;
		percent2 = 100 / patTest;
		double errorTr [] = new double [maxIter];
		double errorTe [] = new double [maxIter];
		for (int i=0;i<maxIter;i++) {
			errorTr[i] = 0.0;
			errorTe[i] = 0.0;
		}
		
		// for each epoch we first train the neural network (forward and backward stages) and then test it (only forward stage)
		// we also take some metrics like : training error, testing error and success rate in training and testing 
		int t;
		for (int it=0;it<maxIter;it++) {
			temp1 = 0.0;
			temp2 = 0.0;
			success1 = 0.0;
			success2 = 0.0;
			pr.println("ITERATION:" + (it+1));
			pr.println("TRAINING STARTS . . . ");
			for (int p=0;p<pat;p++) {	//training
				nn.getNeuron(0).setValue(trainTab[p][0]);
				nn.getNeuron(1).setValue(trainTab[p][1]);
				
				t = trainTab[p][2];
				
				nn.forwardPass();
				nn.backwardPass1(t);
				nn.backwardPass2();
				
				temp1 = temp1 + Math.pow(t - nn.getNeuron(4).getValue() , 2);
				if (((t==1)&&(nn.getNeuron(4).getValue()>0.5))||((t==0)&&(nn.getNeuron(4).getValue()<0.5)))
					success1 = success1 + percent1;
				
				pr.println("(inputA:" + nn.getNeuron(0).getValue() + ",inputB:" + nn.getNeuron(1).getValue() + ") EXPECT:" + t + "-----" + "OUTPUT:" + nn.getNeuron(4).getValue());	//xero oti exo mono 1 output
			}
			errorTr[it] = temp1 * (0.5); 
			pr.println("error in training = " + errorTr[it]);
			pr.println("success rate in training = " + success1 + "%");
			
			pr.println("TESTING STARTS . . . ");
			for (int p=0;p<patTest;p++) {	//testing
				nn.getNeuron(0).setValue(testTab[p][0]);
				nn.getNeuron(1).setValue(testTab[p][1]);
				
				t = testTab[p][2];
				
				nn.forwardPass();
				
				temp2 = temp2 + Math.pow(t - nn.getNeuron(4).getValue() , 2);
				if (((t==1)&&(nn.getNeuron(4).getValue()>0.5))||((t==0)&&(nn.getNeuron(4).getValue()<0.5)))
					success2 = success2 + percent2;
				
				pr.println("(inputA:" + nn.getNeuron(0).getValue() + ",inputB:" + nn.getNeuron(1).getValue() + ") EXPECT:" + t + "-----" + "OUTPUT:" + nn.getNeuron(4).getValue());
			}
			
			errorTe[it] = temp2 * (0.5); 
			pr.println("error in testing = " + errorTe[it]);
			pr.println("success rate in testing = " + success2 + "%");
			
			pr1.println((it+1) + "		" +  errorTr[it] + "		" +  errorTe[it]);
			pr2.println((it+1) + "		" +  success1 + "%		" +  success2 + "%");
		}
		
		pr.close();
		pr1.close();
		pr2.close();
		
	}    
}