import com.apple.eawt.Application;
import dset.LatLongNumshipments;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;

/**
 * Created with IntelliJ IDEA.
 * User: amol.sharma
 * Date: 09/07/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */


public class Logistics extends Application {

    DeliveryHub[] deliveryHubList;

    public static void main(String[] args) {
        ArrayList<LatLongNumshipments> dhMedianList = new ArrayList<LatLongNumshipments>();
        Map<Integer,Integer> pincodehash = new HashMap<Integer,Integer>();
        String Filename="/Users/sujith.j/Downloads/pincode_shipments_data.csv";
         Map<Set<Double>, Integer> mymap = new HashMap<Set<Double>, Integer>();
       String latLongFileName = "/Users/sujith.j/LogisticsNetworkOptimisation/pincode_lat_long.csv";
        new Utils().createLatLonPincodeMapping(latLongFileName, mymap);
        new Utils().getPincodeHash(pincodehash, Filename);
        //System.out.println("safjhfv"+pincodehash);

        try {
            Dataset data = FileHandler.loadDataset(new File(latLongFileName), 0, ",");
            System.out.println(data);
            int numberOfClusters = 100;
            //  PinCodeDistanceMeasure dm = new PinCodeDistanceMeasure();
            Clusterer km = new KMeans(numberOfClusters, 20);
            Dataset[] clusters = km.cluster(data);

            for (int i = 0; i < clusters.length; i++) {
                ArrayList<LatLongNumshipments> clustersResult = new ArrayList<LatLongNumshipments>();
                Iterator itr = clusters[i].iterator();
                // System.out.println("printing cluster attributes");
                while (itr.hasNext()) {
                    Iterator itrInstance = ((Instance) itr.next()).iterator();
                    double lat = Double.parseDouble(itrInstance.next().toString());
                    double lon = Double.parseDouble(itrInstance.next().toString());
//                    System.out.println(lat + " " + lon);
                    Set<Double> coordinates = new HashSet<Double>();
                    coordinates.add(lat);
                    coordinates.add(lon);
                    //System.out.println("jehfviche"+);
                    try{
                    LatLongNumshipments tmp = new LatLongNumshipments(lat, lon, pincodehash.get(mymap.get(coordinates)));
                         clustersResult.add(tmp);
                    }
                    catch(NullPointerException e){
                        System.out.println("Error at "+coordinates);
                    }

                }
                PinDeliveryHub pDeliveryHub = new PinDeliveryHub();
                LatLongNumshipments dhmedian = pDeliveryHub.computeMeanLoc(clustersResult);
                dhMedianList.add(dhmedian);
                System.out.println(clusters[i]);
                System.out.println("median of the cluster is " + dhmedian.getLatitude() + "," + dhmedian.getLongitude() + "," + dhmedian.getNum_of_shipments());
            }


        } catch (IOException e) {
        }
    }



}


