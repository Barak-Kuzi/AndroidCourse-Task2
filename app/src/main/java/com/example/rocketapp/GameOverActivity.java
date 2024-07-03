package com.example.rocketapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class GameOverActivity extends AppCompatActivity {

    private MaterialButton saveBtn, menuBtn;
    private TextView scoreUI;
    private EditText userName;
    private int scoreGame;
    private UserLocationTracking myUserLocationTracking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        this.myUserLocationTracking = new UserLocationTracking(getApplicationContext());
        init();
    }

    @SuppressLint("SetTextI18n")
    public void init() {
        saveBtn = findViewById(R.id.saveButton);
        menuBtn = findViewById(R.id.menuButton);
        scoreUI = findViewById(R.id.scoreValue);
        userName = findViewById(R.id.userInput);

        this.scoreGame = getIntent().getIntExtra("score", 0);
        scoreUI.setText("" + scoreGame);

        menuBtn.setOnClickListener((event) -> goToGameMenu());

        saveBtn.setOnClickListener((event) -> onClickSaveButton(event));
        UserManagement.init(this);

    }

    public void onClickSaveButton(View button) {
        // If: Checks if the app has permission to access fine location data. if the permission is not granted, it requests the permission from the user.
        // Else: If the permission is granted, it observes the current location. Once the location is obtained, it saves the score with the current location coordinates and then stops observing the location.
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            this.myUserLocationTracking.getCurrentUserLocation().observe(this, location -> {
                UserManagement.getInstance().saveUserDetailsInDB(userName.getText().toString(), scoreGame, location.getLatitude(), location.getLongitude());
                myUserLocationTracking.getCurrentUserLocation().removeObservers(this);
            });
        }
        goToGameMenu();
    }

    public void goToGameMenu() {
        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }
}

