import java.util.ArrayList;

public class NSWNode {
    public ArrayList<NSWNode> neighbors;
    public Point content;

    public boolean visited;

    public NSWNode(Point data){
        content = data;
        visited = false;
        neighbors = new ArrayList<>();
    }
    public NSWNode findNearestAdjacent(Point queryPoint){
        //System.out.println("Finding nearest adjacent: "+queryPoint);
        NSWNode ans = neighbors.get(0);
        for(NSWNode temp: neighbors){
            if(temp.content.distance(queryPoint) < ans.content.distance(queryPoint)){
                ans = temp;
            }
        }
        return  ans;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
