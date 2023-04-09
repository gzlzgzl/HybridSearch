import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<Point> a = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        final int k = 20, dim = 50;
        File input = new File("glove.6B.50d.txt");
        try{
            Scanner fileScanner = new Scanner(input);
            for(int i = 1; i <= 400000; i++){
                String pointName = fileScanner.next();
                ArrayList<Double> temp = new ArrayList<>();
                for(int d = 0; d < dim; d++){
                    temp.add(fileScanner.nextDouble());
                }
                Point point = new Point(temp,pointName);

                //pre-query filtering

                //if(point.passesFilter()){
                    a.add(point);
                //}


                if(i % 10000 == 0){
                    System.out.println(i+" points have been read.");
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            return;
        }

        System.out.println("Finished reading.\n");
        Vptree vptree = new Vptree(a);

        ArrayList<Double> temp = new ArrayList<>();
        while(true){
            temp.clear();
            System.out.println("Enter a point:");
            try{
                for(int d = 0; d < dim; d++){
                    temp.add(scanner.nextDouble());
                }
            }catch (Exception e){
                return;
            }
            Point p = new Point(temp, "Query Point");
            long t1 = System.currentTimeMillis();
            PriorityQueue<Point> pq = vptree.knnSearch(p,k);

            //post-query filtering
/*
            ArrayList<Point> pq2 = new ArrayList<>();
            for(Point point:pq){
                if(point.passesFilter()){
                    pq2.add(point);
                }
            }
*/
            long t2 = System.currentTimeMillis();
            System.out.println("The nearest "+k+" are: "+pq);
            System.out.println("Time used: "+(t2-t1)+"ms");
        }

    }
}
