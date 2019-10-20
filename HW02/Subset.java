import edu.princeton.cs.algs4.*;
public class Subset {

	
public static void main(String[] args) {
		
int number= Integer.parseInt(args[0]);
			
ResizingArrayRandomQueue<String> rarq = new ResizingArrayRandomQueue<String>();
StdOut.println("Enter your length of array: ");  
    int N = StdIn.readInt(); 
    StdOut.println("Please enter number");
    for (int i = 0; i < N; i++)
    {
        
                 rarq.enqueue(StdIn.readString()); 
        
    }
    
     StdOut.println("OUTPUT is : ");
			
			for( int i = 0; i < number; i++) { 
				StdOut.println(rarq.dequeue()); 
				}
		
	}

}
