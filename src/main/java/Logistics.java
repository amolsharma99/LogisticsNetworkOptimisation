import com.apple.eawt.Application;
import dset.LatLongNumshipments;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;

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

    /* Load a dataset */
    public static void main(String[] args) {
        //read the pincode_shipments.csv and conver all pincodes to longitude and latitude before passing to clustering algo
        ArrayList<LatLongNumshipments> dhMedianList = new ArrayList<LatLongNumshipments>();

        try {
            Dataset data = FileHandler.loadDataset(new File("/Users/sujith.j/practise/LogisticsNetworkOptimisation/pincode_lat_long.csv"), ",");
            System.out.println(data);
            int numberOfClusters = 100;
            //  PinCodeDistanceMeasure dm = new PinCodeDistanceMeasure();
            Clusterer km = new KMeans(numberOfClusters, 20);
            Dataset[] clusters = km.cluster(data);

            for (int i = 0; i < clusters.length; i++) {
                ArrayList<LatLongNumshipments> clustersResult = new ArrayList<LatLongNumshipments>();
                Iterator itr = clusters[i].iterator();
                System.out.println("printing cluster attributes");
                while (itr.hasNext()) {
                    Iterator itrInstance = ((Instance) itr.next()).iterator();
//                System.out.println(itrInstance.next());
//                System.out.println(itrInstance.next());
                    double lat = Double.parseDouble(itrInstance.next().toString());
                    double lon = Double.parseDouble(itrInstance.next().toString());
                    System.out.println(lat + " " + lon);
                    LatLongNumshipments tmp = new LatLongNumshipments(lat, lon, 10);
                    clustersResult.add(tmp);
                }
                PinDeliveryHub pDeliveryHub = new PinDeliveryHub();
                LatLongNumshipments dhmedian = pDeliveryHub.computeMeanLoc(clustersResult);
                System.out.println("adding dhmedian");
                dhMedianList.add(dhmedian);
                System.out.println("median of the cluster is " + dhmedian.getLatitude() + "," + dhmedian.getLongitude() + "," + dhmedian.getNum_of_shipments());

                System.out.println(clusters[i]);
            }


        } catch (IOException e) {
        }
        System.out.println(dhMedianList.size());

        for(int i=0; i<dhMedianList.size();i++){
            LatLongNumshipments dhmedian = dhMedianList.get(i);
            System.out.println("dhmedian is "+dhmedian.getLatitude()+" "+dhmedian.getLongitude()+" "+dhmedian.getNum_of_shipments());
        }
        double distance = new Utils().distanceOnEarthBetweenPointsInKm(10, 10, 20, 20);
        System.out.println(distance);
    }


}


