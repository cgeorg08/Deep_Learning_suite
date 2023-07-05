public class Neuron {
	
	/*
	 * This is the Neuron class.  In this class you control a neuron.  Particularly, in this class we can see what are the attributes
	 * of a neuron.  For example, every neuron has its value(output), its bias, some weights and it is located in one layer.  In addition,
	 * in this class we can use methods that set or take an attribute of a neuron.   
	 */
	
		private double value;
		private double weights[];		
		private double oldWeights[];
		private int nextlay;
		private int layer;
		private double bias;
		private double oldBias = 0.0;
		private double d;
		
		public Neuron(int l, int num) {
			this.layer = l;
			this.nextlay = num;
			this.weights = new double [nextlay];
			this.oldWeights = new double [nextlay];
			for (int i=0;i<this.nextlay;i++) {
				this.weights[i] = 0.0;
				this.oldWeights[i] = 0.0;
			}
		}
		
		public void setValue(double x) {
			this.value = x;
		}
		
		public double getValue() {
			return this.value;
		}
		
		public void setWeight(int n,double w) {
			this.weights[n] = w;
		}
		
		public double getWeight(int n) {
			return this.weights [n];
		}
		
		public void setoldWeight(int n) {
			this.oldWeights[n] = this.weights[n];
		}
		
		public double getoldWeight(int n) {
			return this.oldWeights [n];
		}
		
		public void setLayer(int x) {
			this.layer = x;
		}
		
		public int getLayer() {
			return this.layer;
		}
		
		public void setBias(double x) {
			this.bias = x;
		}

		public double getBias() {
			return this.bias;
		}
		
		public void setOldBias() {
			this.oldBias = this.bias;
		}

		public double getOldBias() {
			return this.oldBias;
		}
		
		public void setD(double d) {
			this.d = d;
		}

		public double getD() {
			return this.d;
		}
		
		public String toString() {
			String s = new String();
			s = "Neuron HERE - layer " + this.layer + " - value " + this.value + " - bias " + this.bias + " - d " + this.d + " - weights ";
			for (int i=0;i<this.nextlay;i++)
				s = s + this.weights[i] + "  ";
			s = s + "-\n";
			return (s);
		}
}
