import java.util.ArrayList;
import java.lang.Math;
public class Point {
    public ArrayList<Double> coordinates;
    public String name;
    public Point(ArrayList<Double> data, String s){
        coordinates = data;
        name = s;
    }

    public boolean passesFilter(){
        return name.length()%2==0;
    }

    @Override
    public String toString() {
        return name;
    }

    public double distance(Point other){
        double sum=0;
        for(int i=0; i<coordinates.size(); i++){
            sum+=Math.pow(coordinates.get(i)-other.coordinates.get(i),2);
        }
        return Math.sqrt(sum);
    }
}
