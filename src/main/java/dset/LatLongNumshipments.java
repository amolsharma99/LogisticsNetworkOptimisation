package dset;

/**
 * Created by IntelliJ IDEA.
 * User: sujith.j
 * Date: 10/07/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class LatLongNumshipments {

    private  double latitude;
    private  double longitude;
    private  double num_of_shipments;

    public LatLongNumshipments(){
        latitude=0;
        longitude=0;
        num_of_shipments=0;
    }
    public LatLongNumshipments(double lat, double lon, double shipments){
        latitude = lat;
        longitude = lon;
        num_of_shipments = shipments;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getNum_of_shipments() {
        return num_of_shipments;
    }

    public void setNum_of_shipments(double num_of_shipments) {
        this.num_of_shipments = num_of_shipments;
    }
}
