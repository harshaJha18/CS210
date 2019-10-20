import edu.princeton.cs.algs4.*;

public class BrutePointST<Value> implements PointST<Value> {
    
    private RedBlackBST<Point2D, Value> rbt;
    
    // Construct an empty symbol table of points.
    public BrutePointST() {
        rbt = new RedBlackBST<Point2D, Value>();
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
        return rbt.isEmpty();
    }

    // Number of points in the symbol table.
    public int size() {
        return rbt.size();
    }

    // Associate the value val with point p.
    public void put(Point2D pt, Value valu) {
        rbt.put(pt, valu);
    }

    // Value associated with point p.
    public Value get(Point2D pt) {
        return rbt.get(pt);
    }

    // Does the symbol table contain the point p?
    public boolean contains(Point2D pt) {
        return rbt.contains(pt);
    }

    // All points in the symbol table.
    public Iterable<Point2D> points() {
        Iterable<Point2D> list = rbt.keys();
        Queue<Point2D> queuep = new Queue<Point2D>();
        for (Point2D m : list) {
            queuep.enqueue(m);
        }
        return queuep;
    }

    // All points in the symbol table that are inside the rectangle rect.
    public Iterable<Point2D> range(RectHV recth) {
        Iterable<Point2D> listp = rbt.keys();
        Queue<Point2D> queuep = new Queue<Point2D>();
        for (Point2D m : listp) {
            if (recth.contains(m)) {
                queuep.enqueue(m);
            }
        }
        return queuep;
    }

    // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D pt) {
        if (rbt.isEmpty()) {
            return null;
        }
        Iterable<Point2D> listp = rbt.keys();
        Point2D apoint = rbt.min();
        double min = pt.distanceSquaredTo(apoint);
        for (Point2D x : listp) {
            if (pt.distanceSquaredTo(x) < min && pt.compareTo(x) != 0) {
                min = pt.distanceSquaredTo(x);
                apoint = x;
            }
        }
        return apoint;
    }

    // k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D pt, int kt) {
        Queue<Point2D> queuep = new Queue<Point2D>();
        Point2D aa = new Point2D(1000.0,1000.0);
        double tt = 10000.0;
        Point2D minn = nearest(pt);
        double mm =  pt.distanceSquaredTo(minn);
        queuep.enqueue(minn);
        Iterable<Point2D> list = rbt.keys();
        for (int i = 0; i < kt - 1; i++) {            
            for (Point2D x : list) {
                if (pt.distanceSquaredTo(x) > mm &&
                pt.distanceSquaredTo(x) < tt &&
                pt.compareTo(x) != 0) {
                    tt =  pt.distanceSquaredTo(x);
                    aa = x;                    
                }
            }
            mm = tt;
            tt = 10000.0;
            queuep.enqueue(aa);
        }
        return queuep;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        double rx1 = Double.parseDouble(args[2]);
        double rx2 = Double.parseDouble(args[3]);
        double ry1 = Double.parseDouble(args[4]);
        double ry2 = Double.parseDouble(args[5]);
        int k = Integer.parseInt(args[6]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(rx1, ry1, rx2, ry2);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.println("First " + k + " values:");
        i = 0;
        for (Point2D p : st.points()) {
            StdOut.println("  " + st.get(p));
            if (i++ == k) {
                break;
            }
        }
        StdOut.println("st.contains(" + query + ")? " + st.contains(query));
        StdOut.println("st.range(" + rect + "):");
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.println("st.nearest(" + query + ") = " + st.nearest(query));
        StdOut.println("st.nearest(" + query + ", " + k + "):");
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
