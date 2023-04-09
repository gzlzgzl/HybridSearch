import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<Point> a = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        final int k = 2000, dim = 50;
        File input = new File("glove.6B.50d.txt");
        try{
            Scanner fileScanner = new Scanner(input);

            for(int i = 1; i <= 400000; i++){
                String pointName = fileScanner.next();
                ArrayList<Double> temp = new ArrayList<>();
                for(int d = 0; d < dim; d++){
                    temp.add(fileScanner.nextDouble());
                }
                a.add(new Point(temp,pointName));
                if(i % 10000 == 0){
                    System.out.println(i+" points have been read.");
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            return;
        }

        System.out.println("Finished reading.\n");
        NSWGraph nsw = new NSWGraph();
        int count = 0;
        Collections.shuffle(a);
        for(Point p:a){

            //pre-query filtering
//            if(p.passesFilter()){
                nsw.addPoint(p);
//            }
            count++;
            if(count % 1000 == 0){
                System.out.println(count + " points have been added to nsw.");
            }
        }
        ArrayList<Double> temp = new ArrayList<>();
        while(true){
            temp.clear();
            System.out.println("Enter a point:");
            for(int d = 0; d < dim; d++){
                temp.add(scanner.nextDouble());
            }
            Point p = new Point(temp, "Query Point");

            ArrayList<Integer> ks = new ArrayList<>();

            ks.add(40);ks.add(4000);ks.add(40000);ks.add(200000);

            for(int kk:ks){
                PriorityQueue<NSWNode> pq = new PriorityQueue<>();
                long t1 = System.currentTimeMillis();
                pq = nsw.findNearestKF(p,kk);
                //post-query filtering

            ArrayList<Point> pq2 = new ArrayList<>();
            for(NSWNode node:pq){
                if(node.content.passesFilter()){
                    pq2.add(node.content);
                }
            }

                long t2 = System.currentTimeMillis();
                //System.out.println("The nearest "+k+" are: "+pq);
                System.out.println("Time used: "+(t2-t1)+"ms");
            }
        }
    }
}
