package com.example.rocketapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rocketapp.R;
import com.example.rocketapp.GameUtility.UserLocationTracking;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;
    private ArrayList<Marker> usersMarkers = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mapFragmentView = inflater.inflate(R.layout.fragment_map_highest_score, container, false);
        if (supportMapFragment == null) {
            // Gets the FragmentManager for interacting with fragments associated with this fragment's activity.
            // It is used to manage fragments inside this fragment.
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //  Creates a new instance of SupportMapFragment,
            //  which is a special fragment provided by Google Play services for displaying a map.
            supportMapFragment = SupportMapFragment.newInstance();
            // Replaces the existing fragment in the container with the new fragment and commit the transaction.
            // The place where my fragment is located will be the new fragment that arrives, which will be the presentation of the GoogleMap.
            fragmentTransaction.replace(R.id.google_map_fragment, supportMapFragment).commit();
        }
        // Registers the fragment to receive a callback when the map is ready to be used.
        supportMapFragment.getMapAsync(this);
        return mapFragmentView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        UserLocationTracking userLocationTracking = new UserLocationTracking(requireContext());
        userLocationTracking.getCurrentUserLocation().observe(this, location -> {
            if (location != null) {
                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                addMarkerToTheMap(userLatLng, "Your Location");
                userLocationTracking.getCurrentUserLocation().removeObservers(this);
            }
        });
    }

    public void addMarkerToTheMap(LatLng userLocation, String markerMsg) {
        Marker newMarker = googleMap.addMarker(new MarkerOptions().position(userLocation).title(markerMsg));
        usersMarkers.add(newMarker);
    }

    public void zoomInToSpecificMarkerLocation(LatLng markerLocation) {
        final float CAMERA_ZOOM = 13.0f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, CAMERA_ZOOM));
    }

}
