package br.univates.mapspoints.classes;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class LocationListener implements android.location.LocationListener {

    private final String TAG = "LocationListener";
    private int refRouteid;
    private Context context;

    public LocationListener(Context context, int refRouteid) {
        this.context = context;
        this.refRouteid = refRouteid;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: La: " + location.getLatitude() + " - Lo: " + location.getLongitude());

        Point point = new Point(context);
        point.setRefRouteid(refRouteid);
        point.setLatitude(location.getLatitude());
        point.setLongitude(location.getLongitude());
        point.createPoint();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
