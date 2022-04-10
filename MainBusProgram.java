import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.*;

public class MainBusProgram {
    public static void main(String[] args)
    {

        Scanner theScanner = new Scanner("stops.txt");
        try {
            FileReader theFileReader = new FileReader("stops.txt");
        } catch (FileNotFoundException e) {}

        ArrayList<String> stopsInputData = new ArrayList<String>();

        while(theScanner.hasNext())
        {
            stopsInputData.add(theScanner.nextLine());
        }

    }


    public String theTimeOfBus(ArrayList<String> stringList)
    {
        return stringList.get(5);

        return ("No Time Found");
    }

}
