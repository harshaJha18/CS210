import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    private Term[] terms_quer;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
	StdOut.println("Value is null, Please recheck and try again");    		
	throw new java.lang.NullPointerException();
    	}
    	this.terms_quer = terms;
    	Arrays.sort(terms_quer);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String all_prefix) {
        if (all_prefix == null) {
StdOut.println("Value is null, Please recheck and try again"); 		
throw new java.lang.NullPointerException();
    	}
    	Term temporary = new Term(all_prefix, 0);
int temp1 = BinarySearchDeluxe.firstIndexOf(terms_quer, temporary, Term.byPrefixOrder(all_prefix.length()));
    	int temp2 = BinarySearchDeluxe.lastIndexOf(terms_quer, temporary, Term.byPrefixOrder(all_prefix.length()));
    	if (temp1 == -1 || temp2 == -1) {
StdOut.println("Values are equal to -1, Please recheck and try again with some postive values");    		
throw new java.lang.NullPointerException();
    	}
		Term[] a_matches = new Term[temp2 - temp1 + 1];
		a_matches = Arrays.copyOfRange(terms_quer, temp1, temp2);
		Arrays.sort(a_matches, Term.byReverseWeightOrder());
		return a_matches;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String no_prefix) {
        if (no_prefix == null) {
StdOut.println("Value is null, Please recheck and try again");
    		throw new java.lang.NullPointerException();
    	}
    	Term temporary = new Term(no_prefix, 0);
    	int temp1 = BinarySearchDeluxe.firstIndexOf(terms_quer, temporary, Term.byPrefixOrder(no_prefix.length()));
    	int temp2 = BinarySearchDeluxe.lastIndexOf(terms_quer, temporary, Term.byPrefixOrder(no_prefix.length()));
		return temp2- temp1 + 1;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
