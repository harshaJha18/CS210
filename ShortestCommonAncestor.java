import edu.princeton.cs.algs4.*;


public class ShortestCommonAncestor {
    private final Digraph Graph;

    public ShortestCommonAncestor(Digraph Graph) {
        this.Graph = new Digraph(Graph);
    }

    
    public int length(int vv, int ww) {
	   int ancestor = ancestor(vv,ww);
        return distFrom(vv).get(ancestor) + distFrom(ww).get(ancestor);
    }

    // Shortest common ancestor of vertices v and w.
    public int ancestor(int vv, int ww) {
        SeparateChainingHashST<Integer, Integer> vDistFrom = distFrom(vv);
	   SeparateChainingHashST<Integer, Integer> wDistFrom = distFrom(ww);
		int sDist = Integer.MAX_VALUE;
		int sAncestor = -1;
		for (int uu : wDistFrom.keys()) {
		if (vDistFrom.contains(uu)) {
		int distance = vDistFrom.get(uu) + wDistFrom.get(uu);
		if (distance < sDist) {
		sDist = distance;
		sAncestor = uu;
		  }
		 }
		}
		return sAncestor;
    }

    // Length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int[] triadd = triad(subsetA, subsetB);
        return distFrom(triadd[1]).get(triadd[0]) + distFrom(triadd[2]).get(triadd[0]) ;
    }

    // A shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int[] triadd = triad(subsetA, subsetB);
        return  triadd[0];
    }

    private SeparateChainingHashST<Integer, Integer> distFrom(int vv) {
      SeparateChainingHashST<Integer, Integer> stt =
		new SeparateChainingHashST<Integer, Integer>();
		LinkedQueue<Integer> qq = new LinkedQueue<Integer>();
		stt.put(vv, 0);
		qq.enqueue(vv);
		while(!qq.isEmpty()) {
		int xx = qq.dequeue();
		for(int yy : Graph.adj(xx)) {
		if (!stt.contains(yy)) {
		stt.put(yy, stt.get(xx) + 1);
		qq.enqueue(yy);
       }
     }
    }
     return stt;
}
    
    private int[] triad(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
       int sDist = Integer.MAX_VALUE;
		int sAncestor = -1;
		int vv = -1;
		int ww = -1;
		for (int aa : subsetA) {
			for (int bb : subsetB) {
			int d = length(aa, bb);
			if (d < sDist) {
			sDist = d;
			sAncestor = ancestor(aa, bb);
			vv = aa ;
		    ww = bb ;
	    	}
    	}
	}
     return new int[] {sAncestor, vv, ww } ;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}