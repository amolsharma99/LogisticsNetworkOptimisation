import dset.LatLongNumshipments;
import dset.pinData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: sujith.j
 * Date: 10/07/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */

public class PInDeliveryHub {
    public void compute_mean_loc(ArrayList<LatLongNumshipments> data)
    {
        String Filename = null;
        //ArrayList<LatLongNumshipments> data = new ArrayList<LatLongNumshipments>();
        //get_data(data,Filename);
        double mean_latitude=0;
        double mean_longitude=0;
        double total_shipments=0;

        for(int i=0; i<data.size();i++)
        {
            LatLongNumshipments datax = data.get(i);
            total_shipments+=datax.getNum_of_shipments();
            mean_latitude+= datax.getLatitude()*datax.getNum_of_shipments();
            mean_longitude+= datax.getLongitude()*datax.getNum_of_shipments();
        }

        mean_latitude=mean_latitude/total_shipments;
        mean_longitude=mean_longitude/total_shipments;
    }

    public void get_data(ArrayList<LatLongNumshipments> data, String Filename){
        String thisLine;
        double latitude;
        double longitude;
        double num_of_shipments;
        LatLongNumshipments datax;
        try {
          BufferedReader br = new BufferedReader(new FileReader(Filename));
            br.readLine();
          while ((thisLine = br.readLine()) != null) { // while loop begins here
              LatLongNumshipments datax=new LatLongNumshipments();

             String[] lineData =  thisLine.split(",");
              latitude = Double.parseDouble(lineData[0]);
              longitude = Double.parseDouble(lineData[1]);
              num_of_shipments = Double.parseDouble(lineData[2]);
              datax.setLatitude(latitude);
              datax.setLongitude(longitude);
              datax.setNum_of_shipments(num_of_shipments);
              data.add(datax);
            //System.out.println(thisLine);
          } // end while
        } // end try
        catch (IOException e) {
          System.err.println("Error: " + e);
        }

    }

}




