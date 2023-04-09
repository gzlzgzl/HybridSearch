import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class NSWGraph {
    final int K = 4; //minimum number of neighbors
    public static class NodeDescending implements Comparator<NSWNode> {
        Point queryPoint;
        public NodeDescending(Point qp){
            queryPoint = qp;
        }
        public int compare(NSWNode a, NSWNode b){
            if(queryPoint.distance(a.content) - queryPoint.distance(b.content) > 0) {
                return -1;
            }
            if(queryPoint.distance(a.content) - queryPoint.distance(b.content) < 0) {
                return 1;
            }
            return 0;
        }
    }
    public static class NodeAscending implements Comparator<NSWNode> {
        Point queryPoint;
        public NodeAscending(Point qp){
            queryPoint = qp;
        }
        public int compare(NSWNode a, NSWNode b){
            if(queryPoint.distance(a.content) - queryPoint.distance(b.content) > 0) {
                return 1;
            }
            if(queryPoint.distance(a.content) - queryPoint.distance(b.content) < 0) {
                return -1;
            }
            return 0;
        }
    }

    ArrayList<NSWNode> nodes;
    public NSWGraph(){
        nodes = new ArrayList<>();
    }
    public  NSWNode findNearestOne(Point queryPoint){
        Random random = new Random();
        int index = random.nextInt(nodes.size());
        NSWNode ans = nodes.get(index);
        while (true){
            NSWNode temp = ans.findNearestAdjacent(queryPoint);
            if(ans.content.distance(queryPoint) <= temp.content.distance(queryPoint)){
                return ans;
            }
            ans = temp;
        }
    }
    public PriorityQueue<NSWNode> findNearestK(Point queryPoint, int k){
        ArrayList<NSWNode> visitedList = new ArrayList<>();
        PriorityQueue<NSWNode> maxheap = new PriorityQueue<>(new NodeDescending(queryPoint));
        if(nodes.size()<=k){
            maxheap.addAll(nodes);
            return maxheap;
        }
        NSWNode nearestNode = findNearestOne(queryPoint);
        maxheap.add(nearestNode);

        nearestNode.visited = true;
        visitedList.add(nearestNode);
        while (true){
            PriorityQueue<NSWNode> toAdd = new PriorityQueue<>(new NodeAscending(queryPoint));
            for(NSWNode temp:maxheap){
                for(NSWNode neighbor:temp.neighbors){
                    if(neighbor.visited){
                        continue;
                    }

                    if(maxheap.size()>=k){
                        if(neighbor.content.distance(queryPoint) >maxheap.peek().content.distance(queryPoint)){
                            continue;
                        }
                    }
                    neighbor.visited = true;
                    visitedList.add(neighbor);
                    toAdd.add(neighbor);
                }
            }
            if(maxheap.size()>=k){
                if(!toAdd.isEmpty()){
                    if(toAdd.peek().content.distance(queryPoint) >= maxheap.peek().content.distance(queryPoint)){
                        break;
                    }
                }
            }
            maxheap.addAll(toAdd);


            if(toAdd.isEmpty()){
                break;
            }
            while(maxheap.size()>k){
                maxheap.poll();
            }
        }
        for(NSWNode temp:visitedList){
            temp.visited = false;
        }
        return maxheap;
    }
    public PriorityQueue<NSWNode> findNearestKF(Point queryPoint, int k){
        ArrayList<NSWNode> visitedList = new ArrayList<>();
        PriorityQueue<NSWNode> maxheap = new PriorityQueue<>(new NodeDescending(queryPoint));
        if(nodes.size()<=k){
            maxheap.addAll(nodes);
            return maxheap;
        }
        NSWNode nearestNode = findNearestOne(queryPoint);
        //in-query filtering

 //       if(nearestNode.content.passesFilter()){
            maxheap.add(nearestNode);
 //       }

        nearestNode.visited = true;
        visitedList.add(nearestNode);
        while (true){
            PriorityQueue<NSWNode> toAdd = new PriorityQueue<>(new NodeAscending(queryPoint));
            for(NSWNode temp:maxheap){
                for(NSWNode neighbor:temp.neighbors){
                    if(neighbor.visited){
                        continue;
                    }

                    if(maxheap.size()>=k){
                        if(neighbor.content.distance(queryPoint) >maxheap.peek().content.distance(queryPoint)){
                            continue;
                        }
                    }
                    neighbor.visited = true;
                    visitedList.add(neighbor);
                    //in-query filtering #2
 //                   if(neighbor.content.passesFilter()){
                        toAdd.add(neighbor);
 //                   }
                }
            }
            if(maxheap.size()>=k){
                if(!toAdd.isEmpty()){
                    if(toAdd.peek().content.distance(queryPoint) >= maxheap.peek().content.distance(queryPoint)){
                        break;
                    }
                }
            }
            maxheap.addAll(toAdd);


            if(toAdd.isEmpty()){
                break;
            }
            while(maxheap.size()>k){
                maxheap.poll();
            }
        }
        for(NSWNode temp:visitedList){
            temp.visited = false;
        }
        return maxheap;
    }
    public void addPoint(Point newPoint){
        //System.out.println("Adding point "+newPoint);
        NSWNode newNode = new NSWNode(newPoint);
        if(nodes.size() <= K){
            for(NSWNode temp:nodes){
                if(temp.content == newPoint){
                    continue;
                }
                temp.neighbors.add(newNode);
                newNode.neighbors.add(temp);
            }
        }else{
            //System.out.println("It's nearest to "+ findNearestOne(newPoint).content);
            for(NSWNode temp:findNearestK(newPoint, K)){
                temp.neighbors.add(newNode);
                newNode.neighbors.add(temp);
            }
        }
        nodes.add(newNode);
    }
}
