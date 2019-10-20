---------------------------Harsha Jha---------------------------
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private boolean[][] arrayH;
    private double[] p;
    private Percolation percH;
    private int Time;
    private double mn = 0;
    private double sd = 0;

    public PercolationStats(int N, int Time) {
        int count = 0;
        this.Time = Time;       
        p = new double[Time];
        
        if (N <= 0 || Time <= 0) {
            throw new java.lang.IllegalArgumentException(
            " Value must be greater than 0.");
        }
        
        
        for (int i = 0; i < Time; i++) {
            
            count = 0;
            percH = new Percolation(N);
            int row = 0;
            int col = 0;
            while (!percH.percolates()) {
                row = StdRandom.uniform(0, N);
                col = StdRandom.uniform(0, N);
                if (!percH.isOpen(row, col)) {
                   count++;
                   percH.open(row, col);
                }
            }
            p[i] = count/(double)(N * N);
        }
    }
    
    
    public double mean() { 
       double sum = 0;
       for (int i = 0; i < p.length; i++) {
           sum = sum + p[i];
       } 
       return sum/Time;
    }

    
    public double stddev() { 
       
        return StdStats.stddev(p);
    }

    
    public double confidenceLow() {
        if (sd == 0) { 
            sd = stddev();
        }
        
        return (mean() - ((1.96 *stddev())/Math.sqrt(Time)));
    }

    public double confidenceHigh() {
       if (sd == 0) {
            sd = stddev();
        }
       return (mean() + ((1.96 * stddev())/Math.sqrt(Time)));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
  StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
  StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
