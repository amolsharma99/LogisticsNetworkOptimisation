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


public class Logistics extends Application{

    DeliveryHub[] deliveryHubList;

    /* Load a dataset */
    public static void main(String[] args){
        //read the pincode_shipments.csv and conver all pincodes to longitude and latitude before passing to clustering algo

        try{
            Dataset data = FileHandler.loadDataset(new File("/Users/amol.sharma/LogisticsNetworkOptimisation/lat_long.csv"), ",");
            System.out.println(data);
            int numberOfClusters = 100;
          //  PinCodeDistanceMeasure dm = new PinCodeDistanceMeasure();
            Clusterer km = new KMeans(numberOfClusters, 20);
            Dataset[] clusters = km.cluster(data);

            ArrayList<LatLongNumshipments> clustersResult = new ArrayList<LatLongNumshipments>();

            for(int i=0;i<clusters.length;i++){
                Iterator itr = clusters[i].iterator();
                System.out.println("printing cluster attributes");
                while(itr.hasNext()){
                Iterator itrInstance = ((Instance)itr.next()).iterator();
//                System.out.println(itrInstance.next());
//                System.out.println(itrInstance.next());
                double lat = Double.parseDouble(itrInstance.next().toString());
                double lon = Double.parseDouble(itrInstance.next().toString());
                System.out.println(lat +" "+ lon);
                LatLongNumshipments tmp = new LatLongNumshipments(lat, lon, 10);
                clustersResult.add(tmp);
                }
                System.out.println(clusters[i]);
            }

            PinDeliveryHub pDeliveryHub = new PinDeliveryHub();
            LatLongNumshipments dhmedian = pDeliveryHub.computeMeanLoc(clustersResult);
            System.out.println("median of the cluster is "+dhmedian.getLatitude()+","+dhmedian.getLongitude()+","+dhmedian.getNum_of_shipments());
            for(int i=0;i<clustersResult.size();i++){
                System.out.println(clustersResult.get(i).getLatitude());
            }
        }
        catch(IOException e){
        }
        double distance = new Utils().distanceOnEarthBetweenPointsInKm(10,10,20,20);
        System.out.println(distance);
    }



}


