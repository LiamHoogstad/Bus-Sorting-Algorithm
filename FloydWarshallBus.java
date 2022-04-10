import java.util.*;
import java.io.*;

public class FloydWarshallBus {

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */

    double[][] routes; //2D array with all the routes (0 if none and weight otherwise)
    double[] timeTO;        //Distance for shortest known path to intersection
    boolean[] stopedAT;
    int nrStops = 0, nrStreets = 0;
    int busSpeed; // Average walking speed sof 3 contestants in meters per minute (50 <= sA,sB,sC <= 100)

    FloydWarshallBus (String filename){

        try{
            File cityFile = new File(filename);
            Scanner input = new Scanner(System.in);
            input = new Scanner(cityFile);

            if (input.hasNextInt()) nrStops = Integer.parseInt(input.next()); //Creating Intersection variable       
            if (input.hasNextInt()) nrStreets = Integer.parseInt(input.next());       //Creating Street Variable 

            routes =  new double[nrStops][nrStops]; //Making the connection array the appropriate size

            for(int i = 0 ; i < nrStops ; i++)
            {
                for(int j = 0 ; j < nrStops; j++)
                {
                    if(i!=j)routes[i][j] = Double.MAX_VALUE;
                }
            }

            //Filling in the 2D array of routes (streets and their weights) between intersections
            // (Rest of the non connected intersections are connected with 0.0 by default)
            while(input.hasNextInt())
            {
                int v = 0; int w = 0; double weight = 0;

                    v = Integer.parseInt(input.next());
                    w = Integer.parseInt(input.next());
                    weight = Double.parseDouble(input.next());

                routes[v][w] = weight;
            }
            input.close();
        }catch(Exception e)
        {
            
        }
    }

    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforJourney(){

        if(nrStops < 3) return -1;
        Double number = floydwarshallM();
        if(number == -1) return -1;
        return (int)Math.round(Math.ceil(( number*1000)));
    }

    public double floydwarshallM()
    {
        for(int k = 0 ; k < nrStops ; k++)
        {
            for(int i = 0 ; i < nrStops ; i++)
            {
                for(int j = 0 ; j < nrStops ; j++)
                {
                    if(routes[i][k] + routes[k][j] < routes[i][j])
                    {
                        routes[i][j] = routes[i][k] + routes[k][j];
                    }
                }
            }
        }

        double maxSP = Double.MIN_VALUE;
        for(int i = 0 ; i < nrStops ; i++)
        {
            for(int j = 0 ; j < nrStops ; j++)
            {
                if(routes[i][j] == Double.MAX_VALUE) return -1;
                if(routes[i][j] > maxSP) maxSP = routes[i][j];
            }
        }

        return maxSP;
    }

}