package br.univates.mapspoints.activitys;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import br.univates.mapspoints.R;
import br.univates.mapspoints.classes.Point;
import br.univates.mapspoints.utils.DefaultLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private LocationListener locationListener;
//    private LocationManager mlocManager;
    private DefaultLocation defaultLocation;

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
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        System.out.println("DefaultLocation ON");
                        defaultLocation = DefaultLocation.instance(this);
                        defaultLocation.addCallback(new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                Point point = new Point(MapsActivity.this);
                                point.setRefRouteid(refRouteid);
                                point.setLatitude(locationResult.getLastLocation().getLatitude());
                                point.setLongitude(locationResult.getLastLocation().getLongitude());
                                point.createPoint();

                                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(latLng));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                System.out.println("Location: " + locationResult.toString());
                            }
                        });
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
                for (final Point point : points) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        if (defaultLocation != null) defaultLocation.stop();
        super.onStop();
    }
}
