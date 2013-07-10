import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amol.sharma
 * Date: 10/07/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public double distanceOnEarthBetweenPointsInKm(  //haversine formula
                                                     double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

    public void createLatLonPincodeMapping(String filename, Map<Set<Double>, Integer> mymap) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String thisLine;
            while( (thisLine=reader.readLine())!=null){
                String[] lineData = thisLine.split(",");
                Set<Double> coordinates = new HashSet<Double>();
                coordinates.add(Double.parseDouble(lineData[1]));
                coordinates.add(Double.parseDouble(lineData[2]));
                mymap.put(coordinates,Integer.parseInt(lineData[0]));
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

      public static void getPincodeHash(Map<Integer, Integer> pincodehash, String filename) {
        try {
          BufferedReader br = new BufferedReader(new FileReader(filename));
            br.readLine();
            String thisLine;
          while ((thisLine = br.readLine()) != null) { // while loop begins here
              String[] lineData =  thisLine.split("\t");
              pincodehash.put(Integer.parseInt(lineData[1]), Integer.parseInt(lineData[0]));
            //System.out.println(thisLine);
          } // end while

        } // end try
        catch (IOException e) {
          System.err.println("Error: " + e);
        }
    }


}