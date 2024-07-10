package com.example.rocketapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rocketapp.Fragments.GoogleMapFragment;
import com.example.rocketapp.Fragments.HighScoreFragment;
import com.example.rocketapp.Interfaces.MapFunctionality;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;

public class HighestScoreGame extends AppCompatActivity {

    private HighScoreFragment highScoreFragment;
    private GoogleMapFragment googleMapFragment;
    private MaterialButton menuButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_score);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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

        menuButton = findViewById(R.id.menuButtonHS);
        menuButton.setOnClickListener(event -> {
            Intent intent = new Intent(this, GameMenu.class);
            startActivity(intent);
        });
    }
}
