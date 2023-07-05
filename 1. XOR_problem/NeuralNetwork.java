
public class NeuralNetwork {
	
	/*
	 * This is the NeuralNetwork class.  In this class you control the whole network.  Particularly, in this class we assume that a network is a table
	 * of neurons.  This means that, if we take a cell from the table it is a neuron and we know rapidly its attributes.  In this class, we can see a method
	 * that generates a random number(this method is used to set the bias and the weights) and some other methods that help us with the training and testing
	 * of the network.  
	 */
	
	private int inp;
	private int out;
	private int hid1;
	private int hid2;
	private int total;
	private double n;
	private double a;
	private Neuron table[];
	private int maxlay;
	
	public NeuralNetwork(int x1,int x2,int i,int o,double n,double a) {
		this.hid1 = x1;
		this.hid2 = x2;
		this.inp = i ;
		this.out = o;
		total = this.hid1+this.hid2+this.inp+this.out;
		this.n = n;
		this.a = a;
		table = new Neuron [total];
		initTable();
	}
	
	private void initTable() {
		int count = 0;
		int layer = 0;
		
		for (int i=0;i<this.inp;i++) {			
			this.table[count] = new Neuron(layer,this.hid1);
			count = count + 1;
		}
		
		if (this.hid2 == 0){
			layer = 1;
			for (int i=0;i<this.hid1;i++) {
				this.table[count] = new Neuron(layer,this.out);
				count = count + 1;
			}
			layer = 2;
			for (int i=0;i<this.out;i++) {
				this.table[count] = new Neuron(layer,0);
				count = count + 1;
			}	
		}else {
			layer = 1;
			for (int i=0;i<this.hid1;i++) {
				this.table[count] = new Neuron(layer,this.hid2);
				count = count + 1;
			}
			layer = 2;
			for (int i=0;i<this.hid2;i++) {
				this.table[count] = new Neuron(layer,this.out);
				count = count + 1;
			}
			layer = 3;
			for (int i=0;i<this.out;i++) {
				this.table[count] = new Neuron(layer,0);
				count = count + 1;
			}
		}
		this.maxlay = layer;
	}
	
	public Neuron getNeuron(int i) {
		return this.table[i];
	}
	
	/*
	 * We use this method for both training and testing.  In fact, this method just change the outputs/values of each neuron serially.  It uses 
	 * the weights and the bias that every neuron has and it sets a new value to the specific neuron using the sigmoid function.
	 */
	public void forwardPass() {
		int count ;
		int thesi ; 
		double val ; 
		for (int l=1;l<=this.maxlay;l++) {
			count = -1;
			for (int i=0;i<this.total;i++) 
				if (this.table[i].getLayer() == l) {
					val = 0.0;
					count = count + 1;
					thesi = i;
					for (int z=0;z<thesi;z++) 
						if(this.table[z].getLayer() == (l-1))
							val = this.table[z].getValue()*this.table[z].getWeight(count) + val;
					val = val + this.table[thesi].getBias(); 
					val = 1 / (1 + Math.exp(-1*val));
					this.table[thesi].setValue(val);
				}
		}
	}
	
	/*
	 * We use this method only for training.  In fact, this method just calculates the error percentage.  It is important to say that there are
	 * 2 types of formulas to calculate the error percentage according to the layer of the neuron(output or hidden). 
	 */
	public void backwardPass1(int target) {	
		double dd = 0.0;
		//output first
		for(int i=0;i<this.total;i++) {
			if (this.table[i].getLayer() == this.maxlay) {	
				dd = this.table[i].getValue() * (1-this.table[i].getValue()) * (this.table[i].getValue() - target);
				this.table[i].setD(dd);
			}
		}
		
		int count;
		int thesi;
		for(int l=this.maxlay-1;l>=1;l--) {
			for (int i=this.total-1;i>=0;i--)
				if (this.table[i].getLayer() == l) {
					dd = 0.0;
					count = -1;
					thesi = i;
					for (int z=this.total-1;z>thesi;z--)
						if (this.table[z].getLayer() == (l+1)) {
							count = count + 1;
							dd = (this.table[i].getWeight(count) * this.table[z].getD()) + dd;	
						}	
					dd = dd * (this.table[i].getValue()) * (1-this.table[i].getValue());
					this.table[i].setD(dd);
				}			
		}
	}
	
	/*
	 * We use this method only for training.  In fact, this method this method uses the method above and it adapts new weights and bias 
	 * to every neuron using momentum.
	 */
	public void backwardPass2() {	
		//set_weights
		int count ;
		int thesi ; 
		double tmp,res ; 
		for (int l=1;l<=this.maxlay;l++) {
			count = -1;
			for (int i=0;i<this.total;i++) 
				if (this.table[i].getLayer() == l) {
					count = count + 1;
					thesi = i;
					for (int z=0;z<thesi;z++) 
						if(this.table[z].getLayer() == (l-1)) {
							tmp = this.table[z].getWeight(count);
							res = tmp - this.n*this.table[i].getD()*this.table[z].getValue() + this.a*(tmp - this.table[z].getoldWeight(count));
							this.table[z].setoldWeight(count);
							this.table[z].setWeight(count, res);
						}
				}
		}
		//set_bias
		double temp = 0.0;
		for (int i=0;i<this.total;i++) {
			temp = this.table[i].getBias() - this.n * this.table[i].getD() + this.a * (this.table[i].getBias() - this.table[i].getOldBias());
			this.table[i].setOldBias();
			this.table[i].setBias(temp);
		}
	}
	
	/*
	 * We use this method for producing random numbers.  Then we set these random numbers to every neuron's weights and bias.
	 */
	public double getRandom() {
		double random ;
		
		int temp = (int)(Math.random()*2);
		if (temp == 1)
			random = -1.0;
		else
			random = 1.0;
		random = random * (Math.random() * 0.5);
		
		return random;
	}
	
	public String toString() {
		String s = new String();
		for (int i=0;i<this.total;i++)
			s = s + i + ":" + this.table[i].toString();
		return s;
	}

}
