import java.util.*;
import java.io.*;
//import java.util.ArrayList;

public class DijkstraBus {

    double[][] routes; //2D array with all the routes 
    double[] timeTo;        //Distance for shortest known path to Stop
    boolean[] stopedAt;
    int nrStops = 0, nrStreets = 0;
    int busSpeedA, busSpeedB, busSpeedC; // Average walking speed sof 3 contestants in meters per minute (50 <= sA,sB,sC <= 100)
    int slowestBusSpeed = 101;
    Stop[] stops; //Array of stops to add to priority queue
    
    DijkstraBus (String filename, int sA, int sB, int sC) {

        busSpeedA = sA; busSpeedB = sB; busSpeedC = sC;
        if(busSpeedA < slowestBusSpeed) slowestBusSpeed=busSpeedA;//Finding slowest walking speed
        if(busSpeedB < slowestBusSpeed) slowestBusSpeed=busSpeedB;//Finding slowest walking speed
        if(busSpeedC < slowestBusSpeed) slowestBusSpeed=busSpeedC;//Finding slowest walking speed
    
        try{
            File cityFile = new File(filename);
            Scanner input = new Scanner(System.in);
            input = new Scanner(cityFile);

            if (input.hasNextInt()) nrStops = Integer.parseInt(input.next()); //Creating Stop variable       
            if (input.hasNextInt()) nrStreets = Integer.parseInt(input.next());       //Creating Street Variable 

            routes =  new double[nrStops][nrStops]; //Making the connection array the appropriate size

            for(int i = 0 ; i < nrStops ; i++)
            {
                for(int j = 0 ; j < nrStops; j++)
                {
                    if(i!=j)routes[i][j] = Double.MAX_VALUE;
                }
            }

            //Filling in the 2D array of routes (streets and their weights) between stops
            // (Rest of the non connected stops are connected with 0.0 by default)
            while(input.hasNextInt())
            {
                int v = 0; int w = 0; double weight = 0;

                    v = Integer.parseInt(input.next());
                    w = Integer.parseInt(input.next());
                    weight = Double.parseDouble(input.next());

                routes[v][w] = weight;
            }

            //Creating array of Stop objects
            stops = new Stop[nrStops];

            input.close();
        }catch(Exception e)
        {
            
        }
        
    }

    public int timeRequiredforCompetition()
    {
        if(nrStops < 3) return -1;
        double maxDist = 0.0;
        double numb = 0.0;
        for(int i = 0 ; i < nrStops ; i++)
        {
            timeTo = new double[nrStops];
            for(int j=0;j<nrStops;j++){timeTo[j] = Double.MAX_VALUE;} //Fill Array with infinity values
            stopedAt = new boolean[nrStops];
            for(int j = 0 ; j < nrStops ; j++)
            {
                stops[j] = new Stop(j);
            }

            numb = dijkstraLongest(i);
            if(numb > maxDist) maxDist = numb;
            else if (numb == -1) return -1;
        }

        return (int)Math.round(Math.ceil(( maxDist*1000)  / slowestBusSpeed));
    }

    public double dijkstraLongest(int source)
    {
        timeTo[source] = 0;
        stops[source].timeToLocal = 0;
        stopedAt[source] = true;

        //PriorityQueue to be able to sort stops based on their current timeToLocal Value
        PriorityQueue<Stop> pq = new PriorityQueue<Stop>();
        pq.add(stops[source]);

        Stop currentStop;

        while(!(pq.isEmpty()))
        {
            currentStop = pq.poll();
            for ( int i = 0 ; i < nrStops ; i ++)
            {
                if(routes[currentStop.numb][i] != Double.MAX_VALUE)
                {
                    if(timeTo[i] > (timeTo[currentStop.numb] + routes[currentStop.numb][i]) )
                    {
                        timeTo[i] = timeTo[currentStop.numb] + routes[currentStop.numb][i];
                        stopedAt[i] = true;
                        stops[i].timeToLocal = timeTo[i];
                        pq.add(stops[i]);
                    }
                }
            }
        }
        double maxDistance = 0;
        for (int i = 0 ; i < timeTo.length ; i++)
        {
            if(timeTo[i] > maxDistance) maxDistance = timeTo[i];
            if(stopedAt[i] == false) return -1;

        }
        return maxDistance;
    }

    public class Stop implements Comparable<Stop>
    {
        int numb;
        double timeToLocal = Double.MAX_VALUE;

        Stop(int n){
            numb = n;
            timeToLocal = Double.MAX_VALUE;
        }
        Stop(int n,double d){
            numb = n; timeToLocal = d;
        }
        
        @Override
        public int compareTo(Stop otherStop) {
            return Double.compare(timeToLocal, otherStop.timeToLocal);
        }
    }
}