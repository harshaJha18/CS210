
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int source_Per = 0; 
    private int sink_Per;     
    private boolean[][] arrayH; 
    private int noOfSitesOpen = 0; 
    private WeightedQuickUnionUF uF; 
    private WeightedQuickUnionUF copyuF; 
    private int Size; 
    

        public Percolation(int Size) {
        this.Size = Size;
        this.arrayH = new boolean[Size][Size];
        this.sink_Per = Size * Size + 1; 
        this.uF = new WeightedQuickUnionUF(Size * Size + 2); 
        this.copyuF = new WeightedQuickUnionUF(Size * Size + 1);
        
        if (Size <= 0) { 
         throw new IllegalArgumentException("Illegal Argument");
        }
        for (int r = 0; r <= Size; r++) { 
          uF.union(encode(0, r), source_Per );
          uF.union(encode(Size - 1, r), sink_Per);
          copyuF.union(encode(0, r), source_Per );
        } 
        
    }

    public void open(int i, int j) {
        if (i < 0 || i > Size - 1 || j < 0 || j > Size - 1) {
         throw new IndexOutOfBoundsException("Illegal index.");
        }
    
        if (!isOpen(i, j)) {
           arrayH[i][j] = true;
           noOfSitesOpen++;
           if ((i - 1) >= 0 && arrayH[i-1][j]) { //top
               uF.union(encode(i, j), encode(i-1, j));
               copyuF.union(encode(i, j), encode(i-1, j));
            } 
           if ((j + 1) < Size && arrayH[i][j + 1]) { //right
               uF.union(encode(i, j), encode(i, j + 1));
             copyuF.union(encode(i, j), encode(i, j + 1));
            }
           if ((j - 1) >= 0 && arrayH[i][j - 1]) { //left
               uF.union(encode(i, j), encode(i, j - 1));
            copyuF.union(encode(i, j), encode(i, j - 1));
           }
           if ((i + 1) < Size && arrayH[i + 1][j]) { //bottom
               uF.union(encode(i, j), encode(i+1, j));
               copyuF.union(encode(i, j), encode(i+1, j));
            } 
           
        }  
        } 
    public boolean isOpen(int i, int j) {
         if (i < 0 || i > Size - 1 || j < 0 || j > Size - 1) {
          throw new IndexOutOfBoundsException("Illegal index.");
        }
        if (arrayH[i][j]) { 
            return true;
        }
        return false;
        }
        
       
    public boolean isFull(int i, int j) {
         if (i < 0 || i > Size - 1 || j < 0 || j > Size - 1) {
          throw new IndexOutOfBoundsException("Illegal index.");
        }
 if (arrayH[i][j] && copyuF.connected(encode(i, j), source_Per )) {
            return true; 
        }
        return false;
    }


    public int numberOfOpenSites() {
        return noOfSitesOpen; 
    }

    
    public boolean percolates() {
        if (uF.connected(source_Per , sink_Per)) {
            return true; 
        }
        return false;
    }

    
    private int encode(int i, int j) {
        return (i * Size + j + 1); 
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
                
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
