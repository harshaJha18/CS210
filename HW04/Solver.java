import edu.princeton.cs.algs4.*;

public class Solver {
    private MinPQ<SearchNode> min_pq;
    private MinPQ<SearchNode> min_pqTwin;
    private int num;
    private Board initialb;
    private Board goalb;
    private SearchNode s_end;

// Helper search node class.
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves_b;
        private int pri;
        private SearchNode p_Node;

        public SearchNode(Board board, int moves_b, SearchNode p_Node) {
            this.board = board;
            this.moves_b = moves_b;
            pri = moves_b + board.manhattan();
            this.p_Node = p_Node;
        }

        public int compareTo(SearchNode that) {
            return (this.pri - that.pri);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initialb) {
         if(initialb == null) throw new  NullPointerException();
        this.initialb = initialb;
        num = initialb.dimension();
        min_pq = new MinPQ<SearchNode>();
        min_pqTwin = new MinPQ<SearchNode>();

        int[][] blocks = new int[num][num];
        int temp = 1 ;
        for (int i = 0; i < num; i++)
            for (int j = 0; j < num; j++) {
            blocks[i][j] = temp;
            temp++;
        }
        blocks[num-1][num-1] = 0;
        goalb = new Board(blocks);

        SearchNode mNode;
        SearchNode mNodeT;
        min_pq.insert(new SearchNode(initialb, 0, null));
     min_pqTwin.insert(new SearchNode(initialb.twin(), 0, null));
 while(!min_pq.min().board.equals(goalb) && !min_pqTwin.min().board.equals(goalb)) {
            mNode = min_pq.min();
            mNodeT = min_pqTwin.min();
            min_pq.delMin();
            min_pqTwin.delMin();
            for (Board neighbor: mNode.board.neighbors()) {
                if (mNode.moves_b == 0) {
                    min_pq.insert(new SearchNode(neighbor, mNode.moves_b+1, mNode));
                }
                else if (!neighbor.equals(mNode.p_Node.board)) {
                    min_pq.insert(new SearchNode(neighbor, mNode.moves_b+1, mNode));
                }
            }
            // Twin
            for (Board neighbor: mNodeT.board.neighbors()) {
                if (mNodeT.moves_b == 0) {
                    min_pqTwin.insert(new SearchNode(neighbor, mNodeT.moves_b+1, mNodeT));
                }
                else if (!neighbor.equals(mNodeT.p_Node.board)) {
                    min_pqTwin.insert(new SearchNode(neighbor, mNodeT.moves_b+1, mNodeT));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (min_pq.min().board.equals(goalb)) {
            return true;
        }
        if (min_pqTwin.min().board.equals(goalb)) {
            return false;
        }
        return false;
    }

    // The minimum number of moves to solve the initial board.
    public int moves_b() {
        if(!isSolvable()) return -1;
        return min_pq.min().moves_b;
    }

    // Sequence of boards in a shortest solution.
    public Iterable<Board> solution() {
        if(!isSolvable()) return null;
        Stack<Board> s_Sol = new Stack<Board>();
        SearchNode c = min_pq.min();
        while (c.p_Node!=null) {
            s_Sol.push(c.board);
            c = c.p_Node;
        }
        s_Sol.push(initialb);
        return s_Sol;
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves_b());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }

}