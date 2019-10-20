import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class Board {
    private final int num;
    private final char[] blockss;
    private int blankposs;

    
// Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.

    public Board(int[][] blockss) {
        num = blockss.length;
        this.blockss = new char[num*num];
        int k = 0 ;
        for (int i = 0; i < num; i++){
            for (int j = 0; j < num; j++) {
                this.blockss[k] = (char) blockss[i][j];
                if (blockss[i][j] == 0) blankposs = k;
                k ++;
            }
        }
    }

    // board dimension or size
    public int dimension() {
        return num;
    }

    private int row(int l) {
        return (int) Math.ceil((double)l/(double)num);
    }

    
    private int col(int l) {
        if (l % num == 0) return num;
        return l % num;
    }

// Number of tiles out of place.
    public int hamming() {
        int hamming = 0;
        for (int i = 0, j = 1; i < num * num; i++, j++) {
            if (blockss[i] == 0) continue;
            if (blockss[i] != j) hamming++;
        }
        return hamming;
    }

// Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < num*num; i++) {
            if (blockss[i] == 0) continue;
            int rowdiff_m = Math.abs(row(blockss[i]) - row(i+1));
            int coldiff_m = Math.abs(col(blockss[i]) - col(i+1));
            manhattan = manhattan + rowdiff_m + coldiff_m;
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0 ; i < num * num - 2; i++) {
            if (blockss[i] > blockss[i + 1]) return false;
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blockss
    // -randomly swap a pair of blockss
    public Board twin() {
        boolean swap_suc = false;
        char[] twin_b = blockss.clone();
        // choose a non-blank block
        int i = 0;
        do {
            i=StdRandom.uniform(num*num);
        } while (blockss[i] == 0);
        // choose an exchange direction
        while (swap_suc == false) {
            int choice = StdRandom.uniform(4);
            switch(choice) {
                case 0 : //swapAbove
                    if(row(i+1) == 1) swap_suc = false;
                  else if(twin_b[i-num] == 0) swap_suc = false;
                    else {
                        swapAbove(twin_b,i);
                        swap_suc = true;
                    }
                    break;
                case 1 : //swapBelow
                    if(row(i+1) == num) swap_suc = false;
                 else if(twin_b[i+num] == 0) swap_suc = false;
                    else {
                        swapBelow(twin_b,i);
                        swap_suc = true;
                    }
                    break;
                case 2 : //swapLeft
                    if(col(i+1) == 1) swap_suc = false;
                   else if(twin_b[i-1] == 0) swap_suc = false;
                    else {
                        swapLeft(twin_b,i);
                        swap_suc = true;
                    }
                    break;
                case 3 : //swapRight
                    if(col(i+1) == num) swap_suc = false;
                   else if(twin_b[i+1] == 0) swap_suc = false;
                    else {
                        swapRight(twin_b,i);
                        swap_suc = true;
                    }
                    break;
            }
        }
        Board twin_Board = new Board(cloneTiles(twin_b));
        return twin_Board;
    }

// Does this board equal that?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.equals(this.blockss, that.blockss)) return false;
        return true;
    }

// Is this board solvable?
public boolean isSolvable(){
	int inversions = 0;
	for (int k = 0; k < blockss.length; k++) {
		if (blockss[k] == 0) continue; 
		for (int i = k; i < blockss.length; i++) 
		if (blockss[i] < blockss[k] && blockss[i] != 0)
		inversions++;
		}
		
	boolean isEven_Board = (num % 2) == 0;
 	if (isEven_Board) inversions += blankposs / num; 
	boolean isEven_Inversions = (inversions % 2) == 0;
	return isEven_Board != isEven_Inversions; 					}


    // all neighboring boards
    
    public Iterable<Board> neighbors() {
        Stack<Board> stack_Ne = new Stack<Board>();
        char[] neighbors;
        if(row(blankposs+1) != 1) {
            neighbors = blockss.clone();
            swapAbove(neighbors,blankposs);
        Board neighborsBoard = new Board(cloneTiles(neighbors));
            stack_Ne.push(neighborsBoard);
        }
        if(row(blankposs+1) != num) {
            neighbors = blockss.clone();
            swapBelow(neighbors,blankposs);
       Board neighborsBoard = new Board(cloneTiles(neighbors));
            stack_Ne.push(neighborsBoard);
        }
        if(col(blankposs+1) != 1) {
            neighbors = blockss.clone();
            swapLeft(neighbors,blankposs);
       Board neighborsBoard = new Board(cloneTiles(neighbors));
            stack_Ne.push(neighborsBoard);
        }
        if(col(blankposs+1) != num) {
            neighbors = blockss.clone();
            swapRight(neighbors,blankposs);
       Board neighborsBoard = new Board(cloneTiles(neighbors));
            stack_Ne.push(neighborsBoard);
        }
        return stack_Ne;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(num + "\n");
        int k = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                s.append(String.format("%2d ", (int)blockss[k]));
                k++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    //swap functions
    private void swapAbove(char[] c_Tiles, int ik) {
        char temp = c_Tiles[ik];
        c_Tiles[ik] = c_Tiles[ik - num];
        c_Tiles[ik - num] = temp;
    }
    private void swapBelow(char[] c_Tiles, int ik) {
        char temp = c_Tiles[ik];
        c_Tiles[ik] = c_Tiles[ik + num];
        c_Tiles[ik + num] = temp;
    }
    private void swapLeft(char[] c_Tiles, int ik) {
        char temp = c_Tiles[ik];
        c_Tiles[ik] = c_Tiles[ik - 1];
        c_Tiles[ik - 1] = temp;
    }
    private void swapRight(char[] c_Tiles, int ik) {
        char temp = c_Tiles[ik];
        c_Tiles[ik] = c_Tiles[ik + 1];
        c_Tiles[ik + 1] = temp;
    }

// Helper method that clones the tiles[][] array in this board //and returns it.

    private int[][] cloneTiles(char[] c_Tiles) {
        int temp = 0 ;
        int[][] blockss = new int[num][num];
        for (int i = 0; i < num; i++)
            for (int j = 0; j < num; j++) {
            blockss[i][j] = c_Tiles[temp];
            temp++;
        }
        return blockss;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }

    }
}