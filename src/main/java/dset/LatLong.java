package dset;

/**
 * Created by IntelliJ IDEA.
 * User: sujith.j
 * Date: 11/07/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class LatLong {
    private double latitude;
    private double longitude;


    public  LatLong()
    {


    }

    public LatLong(double  lat, double lon)
    {
        latitude =lat;
        longitude=lon;
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
}
