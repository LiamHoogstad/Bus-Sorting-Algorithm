import java.util.*;
import java.io.*;
//import java.util.ArrayList;

public class CompetitionDijkstra {

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     * 
    */

    double[][] connections; //2D array with all the connections (0 if none and weight otherwise)
    double[] distTo;        //Distance for shortest known path to intersection
    boolean[] reached;
    int nrIntersections = 0, nrStreets = 0;
    int speedA, speedB, speedC; // Average walking speed sof 3 contestants in meters per minute (50 <= sA,sB,sC <= 100)
    int slowestWalkingSpeed = 101;
    Intersection[] intersections; //Array of Intersections to add to priority queue
    
    CompetitionDijkstra (String filename, int sA, int sB, int sC) {

        speedA = sA; speedB = sB; speedC = sC;
        if(speedA < slowestWalkingSpeed) slowestWalkingSpeed=speedA;//Finding slowest walking speed
        if(speedB < slowestWalkingSpeed) slowestWalkingSpeed=speedB;//Finding slowest walking speed
        if(speedC < slowestWalkingSpeed) slowestWalkingSpeed=speedC;//Finding slowest walking speed
    
        try{
            File cityFile = new File(filename);
            Scanner input = new Scanner(System.in);
            input = new Scanner(cityFile);

            if (input.hasNextInt()) nrIntersections = Integer.parseInt(input.next()); //Creating Intersection variable       
            if (input.hasNextInt()) nrStreets = Integer.parseInt(input.next());       //Creating Street Variable 

            connections =  new double[nrIntersections][nrIntersections]; //Making the connection array the appropriate size

            for(int i = 0 ; i < nrIntersections ; i++)
            {
                for(int j = 0 ; j < nrIntersections; j++)
                {
                    if(i!=j)connections[i][j] = Double.MAX_VALUE;
                }
            }

            //Filling in the 2D array of connections (streets and their weights) between intersections
            // (Rest of the non connected intersections are connected with 0.0 by default)
            while(input.hasNextInt())
            {
                int v = 0; int w = 0; double weight = 0;

                    v = Integer.parseInt(input.next());
                    w = Integer.parseInt(input.next());
                    weight = Double.parseDouble(input.next());

                connections[v][w] = weight;
            }

            //Creating array of Intersection objects
            intersections = new Intersection[nrIntersections];

            input.close();
        }catch(Exception e)
        {
            
        }
        // PRINTING 2D Array////////////////////////////////////////////
        /*
        System.out.println("Intersections "+ nrIntersections);
        System.out.println("Streets "+ nrStreets);

        String twoDArray = "";
        for(int i = 0 ; i < nrIntersections ; i++)
        {
            for(int j = 0 ; j < nrIntersections; j++)
            {
                twoDArray += connections[i][j];
                twoDArray += " ";
            }
            twoDArray += "\n";
        }
        System.out.println(twoDArray);
        */
        //PRINTING 2D Array********************************************** END
    }

    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int timeRequiredforCompetition()
    {
        if( (speedA < 50 || speedA > 100) || (speedB < 50 || speedB > 100)|| (speedC < 50 || speedC > 100) )
        {
    		return -1;
    	}
        if(nrIntersections < 3) return -1;
        double maximumDistance = 0.0;
        double number = 0.0;
        for(int i = 0 ; i < nrIntersections ; i++)
        {
            distTo = new double[nrIntersections];
            for(int j=0;j<nrIntersections;j++){distTo[j] = Double.MAX_VALUE;} //Fill Array with infinity values
            reached = new boolean[nrIntersections];
            for(int j = 0 ; j < nrIntersections ; j++)
            {
                intersections[j] = new Intersection(j);
            }

            number = dijkstraLongest(i);
            if(number > maximumDistance) maximumDistance = number;
            else if (number == -1) return -1;
        }

        return (int)Math.round(Math.ceil(( maximumDistance*1000)  / slowestWalkingSpeed));
    }

    public double dijkstraLongest(int source)
    {
        distTo[source] = 0;
        intersections[source].distanceTo = 0;
        reached[source] = true;

        //PriorityQueue to be able to sort intersections based on their current distanceTo Value
        PriorityQueue<Intersection> pq = new PriorityQueue<Intersection>();
        pq.add(intersections[source]);

        Intersection currentIntersection;

        while(!(pq.isEmpty()))
        {
            currentIntersection = pq.poll();
            for ( int i = 0 ; i < nrIntersections ; i ++)
            {
                if(connections[currentIntersection.number][i] != Double.MAX_VALUE)
                {
                    if(distTo[i] > (distTo[currentIntersection.number] + connections[currentIntersection.number][i]) )
                    {
                        distTo[i] = distTo[currentIntersection.number] + connections[currentIntersection.number][i];
                        reached[i] = true;
                        intersections[i].distanceTo = distTo[i];
                        pq.add(intersections[i]);
                    }
                }
            }
        }
        // FINDING MAXIMUM ///////////////////////////////////////////////////////////
        double maxDistance = 0;
        for (int i = 0 ; i < distTo.length ; i++)
        {
            if(distTo[i] > maxDistance) maxDistance = distTo[i];
            if(reached[i] == false) return -1;
        
            //System.out.println(distTo[i]);
        }
        return maxDistance;
        // FINDING MAXIMUM///////////////////////////////////////////////////////////END
    }

    public class Intersection implements Comparable<Intersection>
    {
        int number;
        double distanceTo = Double.MAX_VALUE;

        Intersection(int n){
            number = n;
            distanceTo = Double.MAX_VALUE;
        }
        Intersection(int n,double d){
            number = n; distanceTo = d;
        }
        
        @Override
        public int compareTo(Intersection otherIntersection) {
            return Double.compare(distanceTo, otherIntersection.distanceTo);
        }
    }

}