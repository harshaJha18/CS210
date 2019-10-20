import edu.princeton.cs.algs4.*;

// An immutable WordNet data type.
public class WordNet {
    
    private RedBlackBST <String, SET<Integer>> rbbst;
	private RedBlackBST <Integer, String> rrbbst;
	private ShortestCommonAncestor scancestor;

    // Construct a WordNet object given the names of the input (synset and
    // hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        rbbst = new RedBlackBST<String, SET<Integer>>();
		rrbbst = new RedBlackBST<Integer, String>();
		In in = new In(synsets);

		int id = 0;
		while(!in.isEmpty()) {
       String line = in.readLine();
		String[] tokens = line.split(",");

		id = Integer.parseInt(tokens[0]);
		String[] Synset = (tokens[1].split("\\s"));
		for(String noun : Synset) {
		if(!rbbst.contains(noun)) {
		rbbst.put(noun, new SET<Integer>()) ;
		}
		rbbst.get(noun).add(id);	
      }
       rrbbst.put(id, tokens[1]);
    }
    in.close();
    Digraph G = new Digraph(id + 1);
    in = new In(hypernyms);
    while (!in.isEmpty()) {
        String line = in.readLine();
        String[] tokens = line.split(",");
        int v = Integer.parseInt(tokens[0]);
        for (int i =1; i < tokens.length; i++) {
           int w = Integer.parseInt(tokens[i]); 
            G.addEdge(v, w);
        }
    }
    in.close();
    scancestor = new ShortestCommonAncestor(G);
   }
    // All WordNet nouns.
    public Iterable<String> nouns() {
        return rbbst.keys();
    }

    // Is the word a WordNet noun?
    public boolean isNoun(String word) {
        return rbbst.contains(word);
    }

    // A synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
       return rrbbst.get(scancestor.ancestor(rbbst.get(noun1), rbbst.get(noun2)));
    }

    // Distance between noun1 and noun2.
    public int distance(String noun1, String noun2) {
		return scancestor.length(rbbst.get(noun1), rbbst.get(noun2));
        
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];        
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.println("# of nouns = " + nouns);
        StdOut.println("isNoun(" + word1 + ") = " + wordnet.isNoun(word1));
        StdOut.println("isNoun(" + word2 + ") = " + wordnet.isNoun(word2));
        StdOut.println("isNoun(" + (word1 + " " + word2) + ") = "
                       + wordnet.isNoun(word1 + " " + word2));
        StdOut.println("sca(" + word1 + ", " + word2 + ") = "
                       + wordnet.sca(word1, word2));
        StdOut.println("distance(" + word1 + ", " + word2 + ") = "
                       + wordnet.distance(word1, word2));
    }
}