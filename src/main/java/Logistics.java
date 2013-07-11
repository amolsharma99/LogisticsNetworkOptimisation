import com.apple.eawt.Application;
import dset.LatLong;
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
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.tools.data.FileHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        int num = args.length;
        int n = Integer.parseInt(args[0]);
        ArrayList<LatLongNumshipments> dhMedianList = new ArrayList<LatLongNumshipments>();
        Map<Integer, Integer> pincodehash = new HashMap<Integer, Integer>();
        String Filename = "/Users/amol.sharma/LogisticsNwOptimisation/LogisticsNetworkOptimisation/pincode_shipments_data.csv";
        Map<Set<Double>, Integer> mymap = new HashMap<Set<Double>, Integer>();
        Map<Integer, LatLong> pincodeLatLong = new HashMap<Integer, LatLong>();
        String latLongFileName = "/Users/amol.sharma/LogisticsNwOptimisation/LogisticsNetworkOptimisation/pincode_lat_long.csv";
        new Utils().createLatLonPincodeMapping(latLongFileName, mymap);
        new Utils().getPincodeHash(pincodehash, Filename);
        new Utils().getpincodelatlongHash(pincodeLatLong, latLongFileName);
        ArrayList<LatLongNumshipments>[] clusterdata = (ArrayList<LatLongNumshipments>[]) new ArrayList[n];
        Dataset data = new DefaultDataset();
        Dataset[] clusters;
        ArrayList<LatLongNumshipments> clustersResult = new ArrayList<LatLongNumshipments>();
        try {
            try {
                for (int ii = 1; ii < args.length; ii++) {
                    LatLong x;
                    x = pincodeLatLong.get(Integer.parseInt(args[ii]));
                    double[] values = new double[]{x.getLatitude(), x.getLongitude()};
                    Instance instance = new DenseInstance(values);
                    data.add(instance);
                }
            } catch (NullPointerException e) {
                return;
            }
            AtomicInteger numberOfClusters = new AtomicInteger();
            numberOfClusters.set(Integer.parseInt(args[0]));
            Clusterer km = new KMeans(numberOfClusters.get(), 1000);
            clusters = km.cluster(data);
            for (int i = 0; i < clusters.length; i++) {
                Iterator itr = clusters[i].iterator();
                while (itr.hasNext()) {
                    Iterator itrInstance = ((Instance) itr.next()).iterator();
                    double lat = Double.parseDouble(itrInstance.next().toString());
                    double lon = Double.parseDouble(itrInstance.next().toString());
                    Set<Double> coordinates = new HashSet<Double>();
                    coordinates.add(lat);
                    coordinates.add(lon);
                    try {
                        LatLongNumshipments tmp = new LatLongNumshipments(lat, lon, pincodehash.get(mymap.get(coordinates)));
                        clustersResult.add(tmp);
                    } catch (NullPointerException e) {
                        System.out.println("caught exception " + e);
                    }
                }
                PinDeliveryHub pDeliveryHub = new PinDeliveryHub();
                LatLongNumshipments dhmedian = pDeliveryHub.computeMeanLoc(clustersResult);
                dhMedianList.add(dhmedian);
                clusterdata[i] = (ArrayList<LatLongNumshipments>) clustersResult.clone();
                //System.out.println(clusterdata[i]);
                //System.out.println("median of the cluster is " + dhmedian.getLatitude() + "," + dhmedian.getLongitude() + "," + dhmedian.getNum_of_shipments());
                clustersResult.clear();
            }


        } catch (NullPointerException e) {
            System.out.println("caught exception " + e);
        }
        //System.out.println(args[0]);
        JSONArray result = new JSONArray();

        for (int i = 0; i < n; i++) {
            JSONArray dh = new JSONArray();
            dh.add(dhMedianList.get(i).getLatitude());      //add latitude
            dh.add(dhMedianList.get(i).getLongitude());     //add longitude
            dh.add(dhMedianList.get(i).getNum_of_shipments());  //number of shipments
            //System.out.println(dhMedianList.get(i).getLatitude()+" "+dhMedianList.get(i).getLongitude()+" "+dhMedianList.get(i).getNum_of_shipments() );
            ArrayList<LatLongNumshipments> clusterd = clusterdata[i];
            ////System.out.println(clusterdata[i]);
            //System.out.println(clusterd.size());
            for (int j = 0; j < clusterd.size(); j++) {
                JSONArray point = new JSONArray();
                point.add(clusterd.get(j).getLatitude());
                point.add(clusterd.get(j).getLongitude());
                dh.add(point);
            }
            result.add(dh);
        }
        System.out.println(result);
    }
}
