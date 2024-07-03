package com.example.rocketapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class HighestScoreGame extends AppCompatActivity {

    private HighScoreFragment highScoreFragment;
    private GoogleMapFragment googleMapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_score);
        init();
    }

    public void init() {
        highScoreFragment = new HighScoreFragment();
        googleMapFragment = new GoogleMapFragment();

        highScoreFragment.setMapFunctionalityCallBack(new MapFunctionality() {
            @Override
            public void zoomIn(LatLng userLatLng) {
                googleMapFragment.zoomInToSpecificMarkerLocation(userLatLng);
            }

            @Override
            public void putNewMarker(LatLng userLatLng, String markerMsg) {
                googleMapFragment.addMarkerToTheMap(userLatLng, markerMsg);
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameScores, highScoreFragment)
                .add(R.id.frameGoogleMap, googleMapFragment)
                .commit();
    }

}
