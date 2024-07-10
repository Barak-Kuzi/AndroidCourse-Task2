package com.example.rocketapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rocketapp.Interfaces.MapFunctionality;
import com.example.rocketapp.Models.GameUsers;
import com.example.rocketapp.Models.User;
import com.example.rocketapp.R;
import com.example.rocketapp.UserManagement;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HighScoreFragment extends Fragment {

    private MapFunctionality mapFunctionalityCallBack;

    public void setMapFunctionalityCallBack(MapFunctionality callBack) {
        this.mapFunctionalityCallBack = callBack;
    }

    public MapFunctionality getMapFunctionalityCallBack() {
        return this.mapFunctionalityCallBack;
    }

    // Inflates the fragment's layout when the fragment's view hierarchy is being created, and returns the view for the fragment's UI.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scores_highest_score, container, false);
    }

    // This method is called after the view hierarchy has been created and all views have been instantiated.
    // It sets up the view components, loads the user scores, and updates the UI accordingly.
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout allUsersView = view.findViewById(R.id.allUsersLayout);
        if (UserManagement.getInstance() == null)
            UserManagement.init(this.requireContext());

        ArrayList<User> allUsers = new Gson().fromJson(UserManagement.getInstance().loadUsersFromDB(), GameUsers.class).getAllUsers();
        if (!allUsers.isEmpty()) {
            for (int i = 0; i < allUsers.size(); i++) {
                User user = allUsers.get(i);
                LatLng userLocation = new LatLng(user.getLatitude(), user.getLatitude());
                MaterialButton scrollViewButton = (MaterialButton) allUsersView.getChildAt(i);
                scrollViewButton.setVisibility(View.VISIBLE);
                scrollViewButton.setText(user.getUserName() + ", Score: " + user.getScoreValue());
                scrollViewButton.setOnClickListener(event -> {
                    mapFunctionalityCallBack.putNewMarker(userLocation, user.getUserName());
                    mapFunctionalityCallBack.zoomIn(userLocation);
                });
            }
        }

    }
}
