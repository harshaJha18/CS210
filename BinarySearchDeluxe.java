import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// Implements binary search for clients that may want to know the index of 
// either the first or last key in a (sorted) collection of keys.

public class BinarySearchDeluxe {

    // The index of the first key in array[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] array, Key keyy, Comparator<Key> comparator_key) {
      if (array == null || keyy == null || comparator_key == null) {
	StdOut.println("Values are null, Please recheck and try again");	
	throw new java.lang.NullPointerException();
    	}
    	if (array.length == 0) {
    		return -1;
    	}
    	int temp1 = 0;
    	int temp2 = array.length - 1;
     	while (temp1 + 1 < temp2) {
    		int middle = temp1 + (temp2 - temp1)/2;
    		if (comparator_key.compare(keyy, array[middle]) <= 0)      		{
    			temp2 = middle;
    		} else {
    			temp1 = middle;
    		}
    	}
    	if (comparator_key.compare(keyy, array[temp1]) == 0) {
    		return temp1;
    	}
    	if (comparator_key.compare(keyy, array[temp2]) == 0) {
    		return temp2;
    	}
    	return -1;
    }

    // The index of the last key in array[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] array, Key keyy, 
                                        Comparator<Key> comparator_key) {

	if (array == null || keyy == null || comparator_key == null)   	{
	StdOut.println("Values are null, Please recheck and try again");    		
	throw new java.lang.NullPointerException();
    	}
    	if (array == null || array.length == 0) {
    		return -1;
    	}
    	int temp1 = 0;
    	int temp2 = array.length - 1;
    	while (temp1 + 1 < temp2) {
    		int middle = temp1 + (temp2 - temp1)/2;
    		if (comparator_key.compare(keyy, array[middle]) < 0) {
    			temp2= middle;
    		} else {
    			temp1 = middle;
    		}
    	}
    	if (comparator_key.compare(keyy, array[temp2]) == 0) {
    		return temp2;
    	}
    	if (comparator_key.compare(keyy, array[temp1]) == 0) {
    		return temp1;
    	}
    	return -1;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println(count);       
    }
}
