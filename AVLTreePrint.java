import net.datastructures.*;

public class AVLTreePrint {
	
	//uses AVLTreeMap Structure from net.datastructures in sourcecode.jar
	public static void main(String [] args) {
		String[] inputs = {
				"CBDAE",
				"DACBEFMLGHJK",
				"JABCDEFISN",
				"NATASHASHARMA",
				"NEWYORKCITY"
		}; 
		for (int k=0; k<inputs.length;k++) {
		AVLTree<String,Integer> mytree = new AVLTree<String,Integer>();
		// this code populates your tree
		for (int i =0 ; i< inputs[k].length(); i++) {
			mytree.put(inputs[k].substring(i,i+1), 1);
		}
		 System.out.println("Input of " + inputs[k]);
		 // this line of code call the printTree method you are to write
		 mytree.printTree();
		 System.out.println();
		}
	}
}
