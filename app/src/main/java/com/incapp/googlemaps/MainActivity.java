package com.incapp.googlemaps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    TextView textViewLocation;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLocation = findViewById(R.id.textView_location);

        //GoogleApiClient to access the google APIs.
        //Just using location services here.
        googleApiClient = new GoogleApiClient
                .Builder(MainActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                        final Location location = LocationServices
                                .FusedLocationApi
                                .getLastLocation(googleApiClient);

                        showLocation(location);

                        final LocationRequest locationRequest =
                                new LocationRequest();

                        //Get high accuracy location.
                        locationRequest.setPriority(
                                LocationRequest.PRIORITY_HIGH_ACCURACY);

                        //Get location updates every 1 sec.
                        locationRequest.setInterval(1000);

                        //Get location in every 2 sec if some other
                        //app is also accessing updates.
                        locationRequest.setFastestInterval(2000);

                        LocationServices
                                .FusedLocationApi
                                .requestLocationUpdates(
                                        googleApiClient,
                                        locationRequest,
                                        new LocationListener() {
                                            @Override
                                            public void onLocationChanged(Location location) {
                                                showLocation(location);
                                            }
                                        }
                                );
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
    }

    @Override
    protected void onStart() {
        if (googleApiClient != null) {
            //If activity is visible
            //connect to the APIs.
            googleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (googleApiClient != null) {
            //If activity is not visible
            //disconnect from the APIs.
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    private void showLocation(final Location location) {
        if (location != null) {
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            textViewLocation.setText(
                    "Latitude: " + latitude +
                            "\n" +
                            "Longitude: " + longitude
            );

            textViewLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            MainActivity.this,
                            MapsActivity.class
                    );

                    //Send this location to next activity
                    //to display on the map.
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);

                    startActivity(intent);
                }
            });
        }
    }
}
