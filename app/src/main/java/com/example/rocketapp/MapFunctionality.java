package com.example.rocketapp;

import com.google.android.gms.maps.model.LatLng;

public interface MapFunctionality {

    void zoomIn(LatLng userLatLng);

    void putNewMarker(LatLng userLatLng, String markerMsg);

}
