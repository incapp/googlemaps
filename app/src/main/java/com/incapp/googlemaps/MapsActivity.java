package com.incapp.googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                Intent intent = getIntent();

                double latitude = intent.getDoubleExtra(
                        "latitude",
                        -34
                );

                double longitude = intent.getDoubleExtra(
                        "longitude",
                        151
                );

                LatLng latLng = new LatLng(
                        latitude,
                        longitude
                );

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("You are Here"));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }
}
