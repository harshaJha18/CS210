import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string 
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;


    public Term(String query1) {
    	if(query1 == null)
    	{
StdOut.println("Please enter the correct value of Query");
    		throw new java.lang.NullPointerException();
    	}
        this.query = query1;
        this.weight = 0;
    }

    // Construct a term given the associated query string and weight.
    public Term(String query2, long weight2) {
        if (query2 == null) {
StdOut.println("Please enter the correct value of Query");
throw new NullPointerException();}
	  if (weight2 < 0){
StdOut.println("Please enter the correct value of Weight, which is more than 0");
 throw new IllegalArgumentException();}
		this.query = query2;
		this.weight = weight2;
    }

    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {

public int compare(Term temp1, Term temp2) {
			if (temp1.weight == temp2.weight) return 0;
			if (temp1.weight > temp2.weight) return -1;
			return 1;
		}
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int temp3) {
        if (temp3 < 0) {
StdOut.println("Please enter the correct value of temp3, which is more than 0");
throw new IllegalArgumentException();}
		return new PrefixOrder(temp3);
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int temp4;
		
		private PrefixOrder(int temp7) {
			this.temp4 = temp7;
		}

		public int compare(Term temp5, Term temp6) {
			String prefixTemp1;
			String prefixTemp2;
			
			if (temp5.query.length() < temp4) {prefixTemp1 = temp5.query;}
			else {prefixTemp1 = temp5.query.substring(0, temp4);}
			
			if (temp6.query.length() < temp4) {prefixTemp2 = temp6.query;}
			else{ prefixTemp2 = temp6.query.substring(0, temp4);}

			return prefixTemp1.compareTo(prefixTemp2);
		}
    }

    // Compare this term to that in lexicographic order by query and 
    // return a negative, zero, or positive integer based on whether this 
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term thatis) {
        return this.query.compareTo(thatis.query);
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }

}
