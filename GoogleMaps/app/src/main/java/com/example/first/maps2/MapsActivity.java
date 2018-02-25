package com.example.first.maps2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    boolean isTrackingOn = false;

    double lat, lon;

    private Marker pplace;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    LocationManager mLocationManager;
    LocationListener mLocListener;
    PolylineOptions path;
    Place currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;
        location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            if(location!=null){
                lon = location.getLongitude();
                lat = location.getLatitude();
            }
        }

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not enabled!")
                    .setMessage("Enable GPS Settings ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            mLocListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(lat, lon))
                            .width(5)
                            .color(Color.RED));
                    lat = location.getLatitude();
                    lon = location.getLongitude();

                    LatLngBounds.Builder builder = new LatLngBounds.Builder().include(new LatLng(location.getLatitude(), location.getLongitude()));
                    builder.build();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }
                @Override
                public void onProviderEnabled(String s) {

                }
                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, mLocListener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

            }
        });

        if(currentPlace!=null)
        {
            pplace = mMap.addMarker(new MarkerOptions().position(currentPlace.getLatLng()).title("Current Place"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace.getLatLng()));
        }
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() + "clicked: " + clickCount ,
                    Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if(!isTrackingOn)
            startLocationTracking(latLng);
        else
            stopLocationTracking(latLng);

    }

    private void stopLocationTracking(LatLng latLng) {
        isTrackingOn = false;

        pplace = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("Marker at stop location."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        Toast.makeText(this, "Tracking stopped!!!!!!.", Toast.LENGTH_SHORT).show();
    }

    private void startLocationTracking(LatLng latLng) {

        isTrackingOn = true;

        pplace = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("Marker at start location."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        Toast.makeText(this, "Tracking Started!!!!!!!!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection not successful", Toast.LENGTH_SHORT).show();
    }
}
