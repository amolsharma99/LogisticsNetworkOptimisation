import com.apple.eawt.Application;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;

import java.io.File;
import java.io.IOException;

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
        try{
            Dataset data = FileHandler.loadDataset(new File("/Users/amol.sharma/LogisticsNetworkOptimisation/pincode_shipments_data.csv"), ",");
            Clusterer km = new KMeans();
            Dataset[] clusters = km.cluster(data);
        }
        catch(IOException e){
        }
    }
}

