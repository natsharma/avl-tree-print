import net.datastructures.*;
import java.util.Comparator;
public class AVLTree<K,V> extends TreeMap<K,V> {
	
  /** Constructs an empty map using the natural ordering of keys. */
  public AVLTree() { super(); }

  /**
   * Constructs an empty map using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the map
   */
  public AVLTree(Comparator<K> comp) { super(comp); }

  /** Returns the height of the given tree position. */
  protected int height(Position<Entry<K,V>> p) {
    return tree.getAux(p);
  }

  /** Recomputes the height of the given position based on its children's heights. */
  protected void recomputeHeight(Position<Entry<K,V>> p) {
    tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
  }

  /** Returns whether a position has balance factor between -1 and 1 inclusive. */
  protected boolean isBalanced(Position<Entry<K,V>> p) {
    return Math.abs(height(left(p)) - height(right(p))) <= 1;
  }

  /** Returns a child of p with height no smaller than that of the other child. */
  protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) {
    if (height(left(p)) > height(right(p))) return left(p);     // clear winner
    if (height(left(p)) < height(right(p))) return right(p);    // clear winner
    // equal height children; break tie while matching parent's orientation
    if (isRoot(p)) return left(p);                 // choice is irrelevant
    if (p == left(parent(p))) return left(p);      // return aligned child
    else return right(p);
  }

  /**
   * Utility used to rebalance after an insert or removal operation. This traverses the
   * path upward from p, performing a trinode restructuring when imbalance is found,
   * continuing until balance is restored.
   */
  protected void rebalance(Position<Entry<K,V>> p) {
    int oldHeight, newHeight;
    do {
      oldHeight = height(p);                       // not yet recalculated if internal
      if (!isBalanced(p)) {                        // imbalance detected
        // perform trinode restructuring, setting p to resulting root,
        // and recompute new local heights after the restructuring
        p = restructure(tallerChild(tallerChild(p)));
        recomputeHeight(left(p));
        recomputeHeight(right(p));
      }
      recomputeHeight(p);
      newHeight = height(p);
      p = parent(p);
    } while (oldHeight != newHeight && p != null);
  }

  /** Overrides the TreeMap rebalancing hook that is called after an insertion. */
  @Override
  protected void rebalanceInsert(Position<Entry<K,V>> p) {
    rebalance(p);
  }

  /** Overrides the TreeMap rebalancing hook that is called after a deletion. */
  @Override
  protected void rebalanceDelete(Position<Entry<K,V>> p) {
    if (!isRoot(p))
      rebalance(parent(p));
  }

  /** Ensure that current tree structure is valid AVL (for debug use only). */
  private boolean sanityCheck() {
    for (Position<Entry<K,V>> p : tree.positions()) {
      if (isInternal(p)) {
        if (p.getElement() == null)
          System.out.println("VIOLATION: Internal node has null entry");
        else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
          System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
          dump();
          return false;
        }
      }
    }
    return true;
  }
  
  static String[][] treeArray = new String[100][100];
  public void printTree(){
  	  //Put your code to print AVL tree here
	  System.out.println("Print of tree");
  	  //fill array with empty strings to avoid null space
  	  for (int i = 0; i<treeArray.length; i++){
  		  for (int j=0; j<treeArray[i].length; j++){
  			  treeArray[i][j] = "";
  		  }
  	  } 
  	  printTree1(1,50, this.root());
  	  
  	  print2DArray(treeArray);
  	  //System.out.println();
  }

  public void printTree1(int row, int col, Position<Entry<K,V>> p){
  	  //Put your code to print AVL tree here
  	  treeArray[row][col] = p.getElement().getKey().toString();

  	  if (left(p).getElement() != null){
  		treeArray[row+1][col-1] = "/";
  		printTree1(row+2, col-2, left(p));
  	  }
  	  if (right(p).getElement() != null){
  		treeArray[row+1][col+1] = "\\";
  		printTree1(row+2, col+2, right(p));
  	  }
  	  if (left(p).getElement() == null){
  		  System.out.println("");
  	  }
  	  if (right(p).getElement() == null){
  		  System.out.println("");
  	  }
  	  
  	  
  }

  public static void print2DArray(String[][] array) {
  	    for(int i=0; i<array.length; i++) {
  	       for(int j=0; j<array[i].length; j++){
  	           System.out.print(array[i][j] + " ");
  	       }
  	       System.out.println();
  	    }
  }
   
}
