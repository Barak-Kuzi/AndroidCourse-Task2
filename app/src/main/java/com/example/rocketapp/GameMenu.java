package com.example.rocketapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class GameMenu extends AppCompatActivity {

    private MaterialButton normalModeBtn, fastModeBtn, sensorModeBtn, highestScoreBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        normalModeBtn = findViewById(R.id.normalModeBtn);
        fastModeBtn = findViewById(R.id.fastModeBtn);
        sensorModeBtn = findViewById(R.id.sensorModeBtn);
        highestScoreBtn = findViewById(R.id.highestScoreBtn);

        normalModeBtn.setOnClickListener((event) -> setGameMode(event));
        fastModeBtn.setOnClickListener((event) -> setGameMode(event));
        sensorModeBtn.setOnClickListener((event) -> setGameMode(event));
        highestScoreBtn.setOnClickListener((event) -> {
            Intent intent = new Intent(this, HighestScoreGame.class);
            startActivity(intent);
        });
    }

    private void setGameMode(View button) {
        Intent intent = new Intent(this, GameActivity.class);
        if (button == normalModeBtn) {
            intent.putExtra("gameMode", GameManager.SLOW_MODE);
        } else if (button == fastModeBtn) {
            intent.putExtra("gameMode", GameManager.FAST_MODE);
        } else if (button == sensorModeBtn) {
            intent.putExtra("gameMode", GameManager.SENSOR_MODE);
        }
        startActivity(intent);
    }

}
