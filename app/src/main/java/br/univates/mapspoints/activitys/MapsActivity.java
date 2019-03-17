package br.univates.mapspoints.activitys;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.PortUnreachableException;
import java.util.ArrayList;

import br.univates.mapspoints.R;
import br.univates.mapspoints.classes.LocationListener;
import br.univates.mapspoints.classes.Point;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, android.location.LocationListener {

    private GoogleMap mMap;
    //private LocationListener locationListener;
    private LocationManager mlocManager;

    private final String TAG = "MapsActivity";
    private int refRouteid;
    private boolean history = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            refRouteid = bundle.getInt("routeid");
            history = bundle.getBoolean("history");
            if (refRouteid > 0) {
                if (!history) {
                    //locationListener = new LocationListener(this, routeid);
                    mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                        System.out.println("Provider ON");
                    }
                }
            } else {
                Toast.makeText(this, "Problemas ao iniciar!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void markerPoints() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ArrayList<Point> points = new Point(getApplicationContext()).getPointsByRefRouteid(refRouteid);
                for(final Point point : points) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(point.getLatitude(), point.getLongitude())));
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (history) markerPoints();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: La: " + location.getLatitude() + " - Lo: " + location.getLongitude());

        Point point = new Point(this);
        point.setRefRouteid(refRouteid);
        point.setLatitude(location.getLatitude());
        point.setLongitude(location.getLongitude());
        point.createPoint();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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

    @Override
    protected void onStop() {
        new Point(this).closeConnection();
        super.onStop();
    }
}
