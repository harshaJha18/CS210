import edu.princeton.cs.algs4.*;
import java.util.*;
 
public class LinkedDeque<Item> implements Iterable<Item> {
    private Node first_node;
    private Node last_node;
    private int size_node;
 
    private class Node {
        private Item item_node;
        private Node next_node;
        private Node previous_node;
 
        public Node(Item item_node) {
            this.item_node = item_node;
    }    }
    
    public LinkedDeque() {        
        first_node = null;   
        last_node = first_node;  
        size_node = 0;      }
 
    
    public boolean isEmpty() {
        return size_node == 0;
    }
 
    public int size_node() {
        return size_node;
    }
 
    
    public void addfirst_node(Item item_addfirst) {
        Node addfirstnode = new Node(item_addfirst);
        if (isEmpty()) {
            last_node = addfirstnode;
            first_node = addfirstnode;
        } else {
            addfirstnode.next_node = first_node;
            first_node.previous_node = addfirstnode;
            first_node = addfirstnode;
        }
        size_node++;
    }
    
    public void addlast_node(Item item_addlast) {
        Node addlastnode = new Node(item_addlast);
        if (isEmpty()) {
            last_node = addlastnode;
            first_node = addlastnode;
        }
        else {
            last_node.next_node = addlastnode;
            addlastnode.previous_node = last_node;
            last_node = addlastnode;
        }
        size_node++;
    }
 

    public Item removefirst_node() {
        
        Item removefirstitem = first_node.item_node;
        if (size_node == 1) {
            first_node = null;
            last_node = null;
        } else {
            first_node = first_node.next_node;
            first_node.previous_node = null;
        }
        size_node--;
        return removefirstitem;
    }
 
    
    public Item removelast_node() {
        Item removelastitem = last_node.item_node;
        if (size_node == 1) {
            first_node = null;
            last_node = null;
        } else {
            last_node = last_node.previous_node;
            last_node.next_node = null;
        }
        size_node--;
        return removelastitem;
    }
 
    
    public Iterator<Item> iterator() {
        return new LinkedDequeIterator();
    }
 
    
    private class LinkedDequeIterator implements Iterator<Item> {
        private Node current_node;
 
        LinkedDequeIterator() {
            this.current_node = first_node;
        }
 
        public boolean hasNext()  {
            return current_node != null;
        }
 
        public void remove() {
            throw new UnsupportedOperationException();
        }
 
        public Item next() {
            Item item_node = current_node.item_node;
            current_node = current_node.next_node;
            return item_node;
        }
    }
 
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }
 
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
            + "several powers, having been originally breathed into a few "
            + "forms or into one; and that, whilst this planet has gone "
            + "cycling on according to the fixed law of gravity, from so "
            + "simple a beginning endless forms most beautiful and most "
            + "wonderful have been, and are being, evolved. ~ "
            + "Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addfirst_node(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addlast_node(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size_node());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removefirst_node();
            }
            else {
                deque.removelast_node();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}

