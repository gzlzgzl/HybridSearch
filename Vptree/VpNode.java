import java.util.PriorityQueue;

public class VpNode {
    public VpNode lc, rc;
    public Point content;
    public double radius;
    public VpNode(Point data){
        lc=null;
        rc=null;
        content=data;
        radius=-1;
    }
    public void knnSearch(Point queryPoint, int k){
        if(Vptree.maxheap == null){
            Vptree.maxheap = new PriorityQueue<>(new Vptree.PointSort(queryPoint));
        }
        if(content == null){
            return;
        }
        double currentDistance = content.distance(queryPoint);
        if(currentDistance <= Vptree.knnThreshold){
            //in-query filtering

            if(content.passesFilter()){
                Vptree.maxheap.add(content);
                if(Vptree.maxheap.size() > k){
                    Vptree.maxheap.poll();
                    Vptree.knnThreshold = Vptree.maxheap.peek().distance(queryPoint);
                }
            }

        }
        if(lc != null){
            if(currentDistance <= radius + Vptree.knnThreshold){
                lc.knnSearch(queryPoint, k);
            }
        }
        if(rc != null){
            if(currentDistance >= radius - Vptree.knnThreshold){
                rc.knnSearch(queryPoint, k);
            }
        }
    }
}
