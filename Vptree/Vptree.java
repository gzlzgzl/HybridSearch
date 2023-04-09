import java.util.*;

public class Vptree{
    VpNode root;
    public Vptree(ArrayList<Point> data){
        root = buildTree(data);
    }
    public static class PointSort implements Comparator<Point>{
        Point queryPoint;
        public  PointSort(Point qp){
            queryPoint = qp;
        }
        public int compare(Point a, Point b){
            if(queryPoint.distance(a) - queryPoint.distance(b) > 0) {
                return -1;
            }
            if(queryPoint.distance(a) - queryPoint.distance(b) < 0) {
                return 1;
            }
            return 0;
        }
    }
    public VpNode buildTree(ArrayList<Point> points){
        if(points.size()==1){
            return new VpNode(points.get(0));
        }
        if(points.size()==0){
            return null;
        }
        Random rand = new Random();
        int vpID = rand.nextInt(points.size());
        Point vp = points.get(vpID);
        VpNode ans = new VpNode(vp);

        points.remove(vpID);
        points.sort(new PointSort(vp));
        ArrayList<Point> inner = new ArrayList<>();
        ArrayList<Point> outer = new ArrayList<>();
        if(points.size()>2){
            ans.radius = (points.get(points.size()/2-1).distance(vp) + points.get(points.size()/2).distance(vp)) / 2;
        }else{
            ans.radius = 0;
        }
        for (Point point : points) {
            if (point.distance(vp) <= ans.radius) {
                inner.add(point);
            } else {
                outer.add(point);
            }
        }
        ans.lc = buildTree(inner);
        ans.rc = buildTree(outer);
        return ans;
    }
    public static PriorityQueue<Point> maxheap = null;
    public static double knnThreshold = Double.MAX_VALUE;
    public PriorityQueue<Point> knnSearch(Point queryPoint, int k){
        root.knnSearch(queryPoint, k);
        try{
            return maxheap;
        }finally {
            maxheap = null;
            knnThreshold = Double.MAX_VALUE;
        }
    }
}
