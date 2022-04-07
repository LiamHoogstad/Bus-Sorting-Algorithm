import java.util.*;
import java.io.*;

public class CompetitionFloydWarshall {

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */

    double[][] connections; //2D array with all the connections (0 if none and weight otherwise)
    double[] distTo;        //Distance for shortest known path to intersection
    boolean[] reached;
    int nrIntersections = 0, nrStreets = 0;
    int speedA, speedB, speedC; // Average walking speed sof 3 contestants in meters per minute (50 <= sA,sB,sC <= 100)
    int slowestWalkingSpeed = 101;


    CompetitionFloydWarshall (String filename, int sA, int sB, int sC){

        try{
            File cityFile = new File(filename);
            Scanner input = new Scanner(System.in);
            input = new Scanner(cityFile);

            speedA = sA; speedB = sB; speedC = sC;
            if(speedA < slowestWalkingSpeed) slowestWalkingSpeed=speedA;//Finding slowest walking speed
            if(speedB < slowestWalkingSpeed) slowestWalkingSpeed=speedB;//Finding slowest walking speed
            if(speedC < slowestWalkingSpeed) slowestWalkingSpeed=speedC;//Finding slowest walking speed

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
            input.close();
        }catch(Exception e)
        {
            
        }
    }

    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition(){

        if( (speedA < 50 || speedA > 100) || (speedB < 50 || speedB > 100)|| (speedC < 50 || speedC > 100) )
        {
    		return -1;
    	}

        if(nrIntersections < 3) return -1;
        Double number = floydWarshallMax();
        if(number == -1) return -1;
        return (int)Math.round(Math.ceil(( number*1000)  / slowestWalkingSpeed));
    }

    public double floydWarshallMax()
    {
        for(int k = 0 ; k < nrIntersections ; k++)
        {
            for(int i = 0 ; i < nrIntersections ; i++)
            {
                for(int j = 0 ; j < nrIntersections ; j++)
                {
                    if(connections[i][k] + connections[k][j] < connections[i][j])
                    {
                        connections[i][j] = connections[i][k] + connections[k][j];
                    }
                }
            }
        }

        double maxSP = Double.MIN_VALUE;
        for(int i = 0 ; i < nrIntersections ; i++)
        {
            for(int j = 0 ; j < nrIntersections ; j++)
            {
                if(connections[i][j] == Double.MAX_VALUE) return -1;
                if(connections[i][j] > maxSP) maxSP = connections[i][j];
            }
        }

        return maxSP;
    }

}