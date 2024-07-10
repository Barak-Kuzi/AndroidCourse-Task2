package com.example.rocketapp.GameUtility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

public class UserLocationTracking implements LocationListener {

    private final LocationManager locationManager;
    private MutableLiveData<Location> currentUserLocation = new MutableLiveData<>();

    public UserLocationTracking(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        this.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public MutableLiveData<Location> getCurrentUserLocation() {
        return this.currentUserLocation;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.currentUserLocation.setValue(location);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
