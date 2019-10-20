import edu.princeton.cs.algs4.*;
import java.util.*;


public class ResizingArrayRandomQueue<Item> implements Iterable<Item> 
{
int Number = 0;    
Item queue[];
    
    
    public ResizingArrayRandomQueue() {
        queue = (Item[]) new Object[1];
    }


    public boolean isEmpty() {
        return Number == 0;
    }

    
    public int size() {
        return Number;
    }

    
    public void enqueue(Item enqueue_item) {
      if(Number == queue.length) {
            resize(2*queue.length);
        }
        queue[Number++] = enqueue_item;
    }

    // Remove and return a random item from the queue.
    public Item dequeue() {
        int dequeue_N = StdRandom.uniform(Number);
        Item dequeue_item = queue[dequeue_N ];
        if(dequeue_N  != Number-1) {
            queue[dequeue_N ] = queue[Number-1];
        }
        queue[Number-1] = null;
        Number--;
        if(Number > 0 && Number == queue.length/4) {
            resize(queue.length/2);
        }
        return dequeue_item;
    }

    
    public Item sample() {
        
        int samplenumber = StdRandom.uniform(Number);
        return queue[samplenumber];
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }


    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] copyArray = (Item[]) new Object[queue.length];
        private int copyN = Number;

        public RandomQueueIterator() {
            for(int i = 0;i <queue.length;i++){
                copyArray[i] = queue[i];
            }
        }

        public boolean hasNext()  { 
            return copyN != 0;
        }

        public void remove() { 
            throw new UnsupportedOperationException(); 
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int copy_next = StdRandom.uniform(copyN);
            Item next_item = copyArray[copy_next];
            if(copy_next != copyN-1){
                copyArray[copy_next] = copyArray[copyN-1];
            }
            copyArray[copyN-1] = null;
            copyN--;
            return next_item;
        }
    }

        // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Helper method for resizing the underlying array.
    @SuppressWarnings("unchecked")
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        if (queue != null) {
            for (int i = 0; i < Number; i++) {
                if (queue[i] != null && i < max) {
                    temp[i] = queue[i];
                }
            }
        }
        queue = temp;
    }


    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = 
            new ResizingArrayRandomQueue<Integer>();
        
    while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }


        int sum1 = 0;
        for (int x : q) {
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}


