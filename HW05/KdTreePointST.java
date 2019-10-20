import edu.princeton.cs.algs4.*;

public class KdTreePointST<Value> implements PointST<Value>  

{
    Node n_root;
    public int size_n;
    Node last=null;
    
    
    private class Node {
        private Point2D pt;   // the point
        private Value valu;   
        private RectHV recth; 
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Construct a node given the point, the associated value, and the 
        // axis-aligned rectangle corresponding to the node.
        public Node(Point2D pt,Value valu,RectHV recth) {
            this.pt = pt;
            this.valu = valu;
            this.recth = recth;
            rt=null;
            lb=null;
        }
         public Node(Point2D pt, Value valu) {
            this.pt = pt;
            this.valu = valu;
            rt=null;
            lb=null;
         
        }
    }

    // Construct an empty symbol table of points.
    public KdTreePointST() {
        n_root=null;
        size_n=0;
        last=null;
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
       if(size_n==0){ return true;}
       
       return false;
    }

    // Number of points in the symbol table.
    public int size() {
        return size_n;
    }

    // Associate the value val with point p.
    public void put(Point2D pt, Value valu) {
     if(pt==null || valu==null){
     throw new java.lang.NullPointerException();}

     Node newer=new Node(pt,valu),temp=n_root,prev=temp;
        int tp=0;  
        if(size_n==0)
        {
		n_root=newer;
        	size_n++;
        }
        else
        {
        	if(!contains(pt))
        		{
        		 while(temp!=null)
         			{prev=temp;
          				if(tp%2==0)
          				{
           				if(temp.pt.x()<newer.pt.x())
           				{temp=temp.rt;}
          					else
           				{temp=temp.lb;}
          				}
					else
         	 			{ if(temp.pt.y()<newer.pt.y())
          					 {temp=temp.rt;}
           			  else
           			  {temp=temp.lb;}
          				}
          
         			tp++;
         			}tp=tp-1;
         		if(tp%2==0)
          			{
         			  if(prev.pt.x()<newer.pt.x())
          				 {prev.rt=newer; }
          			 else
          			 	{prev.lb=newer; }
         		}else
          			{ if(prev.pt.y()<newer.pt.y())
           			{prev.rt=newer; }
           		  else
          				 {prev.lb=newer; }
          			}
       
   			 size_n++;
    		}
   		else
   		 {
   		 while(!temp.pt.equals(pt))
         		{prev=temp;
         		 if(tp%2==0)
          			{
           		if(temp.pt.x()<newer.pt.x())
           			{temp=temp.rt;}
           		else
           			{temp=temp.lb;}
          		}else
          			{ if(temp.pt.y()<newer.pt.y())
           			{temp=temp.rt;}
           		  else
          				{temp=temp.lb;}
         			}
          
        		tp++;
        		 }
         temp.valu=valu;}
   }
   }

    
    // Value associated with point p.
    public Value get(Point2D pt) {
    if(pt==null){
    throw new java.lang.NullPointerException();}
    Node temp=n_root;
    return get(temp,pt,true);
      }
    
       
    // Helper for get(Point2D p).
    private Value get(Node xx, Point2D pt, boolean blr) {
        if(xx==null){return null;}
        if(blr)
        {
       	 if(xx.pt.equals(pt))
        		{return xx.valu;}
        	else if(xx.pt.x()<pt.x())
       		{ return get(xx.rt,pt,!blr);}
        	else
        		{return get(xx.lb,pt,!blr);}
        }
        else
        {
        	if(xx.pt.equals(pt))
       		{ return xx.valu;}
       	else if(xx.pt.y()<pt.y())
        		{return get(xx.rt,pt,!blr);}
       	else
        		{return get(xx.lb,pt,!blr);}
        }
  
	}

     //Does the symbol table contain the point p?
   public boolean contains(Point2D pt) {
     if(pt==null){
     throw new java.lang.NullPointerException();}
          
	Node temp=n_root; 
      int tp=0;
        
      while(temp!=null)
         {
         	if(temp.pt.equals(pt))
         		{return true;}	
          if(tp%2==0)
          {
          		if(temp.pt.x()<pt.x())
           		{temp=temp.rt;}
           	else
           		{temp=temp.lb;}
          }else
          { 	if(temp.pt.y()<pt.y())
           		{temp=temp.rt;}
           	else
           		{temp=temp.lb;}
          }
          
         tp++;
         }
        
    return false;   
    
    }
   

    // All points in the symbol table, in level order.
    public Iterable<Point2D> points() {
        Node temp=n_root;Node temp1;
        int td=0;
	  Queue<Point2D> pt_q=new Queue<Point2D>();
        Queue<Node> n_qu=new Queue<Node>();
        Point2D[] pt1=new Point2D[size_n];
        n_qu.enqueue(temp);
        pt1[td]=temp.pt;
        td++;
        while(!n_qu.isEmpty())
        {
        	temp=n_qu.dequeue();
       	if(temp.lb!=null)
       	 {
        		temp1=temp.lb;
                n_qu.enqueue(temp1);
        		pt1[td]=temp1.pt;
        		td++;
       	 }
        	if(temp.rt!=null)
        	{
        	temp1=temp.rt;
           n_qu.enqueue(temp1);
       	pt1[td]=temp1.pt;
       	td++;
       	}
        
        }
        for(int i=0;i<size_n;i++)
        {pt_q.enqueue(pt1[i]);}
    		return pt_q;
       }

    // All points in the symbol table that are inside the rectangle rect.
   public Iterable<Point2D> range(RectHV recth) {
     if(recth==null){
     throw new java.lang.NullPointerException();}
     Queue<Point2D> pt_q=new Queue<Point2D>();
     for(Point2D p :points())
     		{
   		if(recth.contains(p))
   		pt_q.enqueue(p);
  		 }
   
   	return pt_q;
    }
    


 // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D pt) {
    if(pt==null){
    throw new java.lang.NullPointerException();}
    Point2D[] p1;int co=0;Point2D mini;
    for(Point2D p2:points())
   {
	co++;
   }
   p1=new Point2D[co];
   int i=0;
   for(Point2D p2:points())
   {
	 p1[i]=p2;
	 i++;
   } 
   
  mini=p1[0]; double min=p1[0].distanceTo(pt); 
  for(i=1;i<co;i++)
   {
 	if(!p1[i].equals(pt))
 		if(min>p1[i].distanceTo(pt))
  			{mini=p1[i];
  			min=p1[i].distanceTo(pt);}
  
   	}
         
         return mini;
   }
    
    
    // k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D pt, int kt) 
    {
    if(pt==null){
    throw new java.lang.NullPointerException();}
    double msum=0,sum=0;Point2D minn;
    int count=0;
    for(Point2D p2:points())
     {
      count++;
     }
    
    Point2D[] p2=new Point2D[count];
    int minnaddr=0;
    RedBlackBST<Point2D , Value> secondbst=new RedBlackBST<Point2D , Value>();
     count=0;
     for(Point2D p1:points())
  		{
		 p2[count]=p1;
		 count++;
   		}   
     Point2D[] minnest=new Point2D[kt];
     int zz=0;
     for(int i=0;i<kt;i++)
            {
     		 minn=p2[i];
            msum=p2[i].distanceTo(pt);
            for(int j=i+1;j<count;j++)
            	{
                	sum=p2[j].distanceTo(pt);
                 if(msum>sum)
            		{
           		minn=p2[j];
            		minnaddr=j;
            		msum=sum; }
     			}
     		 if(minn.equals(pt))
     			{kt++;
     			p2[minnaddr]=p2[i];
     			p2[i]=minn;
     			continue;}
     		 minnest[zz]=minn;
      	p2[minnaddr]=p2[i];
      	p2[i]=minn;
      	secondbst.put(minnest[zz],get(minn));
     		zz++;
    		}
      return secondbst.keys();
      
}
    

    

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<Integer> ();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        double rx1 = Double.parseDouble(args[2]);
        double rx2 = Double.parseDouble(args[3]);
        double ry1 = Double.parseDouble(args[4]);
        double ry2 = Double.parseDouble(args[5]);
        int k =Integer.parseInt(args[6]);
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
