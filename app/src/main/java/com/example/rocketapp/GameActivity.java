package com.example.rocketapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity {

    private ShapeableImageView[] lives;
    private ShapeableImageView[] houses;
    private ShapeableImageView[][] rockets;
    private ShapeableImageView[][] israel_coins;
    private AppCompatImageButton left, right;
    private TextView odometer;
    private Handler handler;
    private Runnable runnable;
    private SensorSetup sensorSetup;

    private final int ROWS = 8;
    private final int COLS = 5;
    private final int LIVES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.sensorSetup != null)
            this.sensorSetup.stopSensor();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.sensorSetup != null)
            this.sensorSetup.startSensor();
        GameManager.getInstance().restartGame();
        startGame();
    }

    public void moveLeft(View view) {
        GameManager.getInstance().moveHouse(GameManager.LEFT);
        updateHouse();
    }

    public void moveRight(View view) {
        GameManager.getInstance().moveHouse(GameManager.RIGHT);
        updateHouse();
    }

    public void updateHouse() {

        int index = GameManager.getInstance().getHouseIndex();

        for (int i = 0; i < COLS; i++) {
            houses[i].setVisibility(i == GameManager.getInstance().getHouseIndex() ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void init() {
        lives = new ShapeableImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        houses = new ShapeableImageView[]{
                findViewById(R.id.house0),
                findViewById(R.id.house1),
                findViewById(R.id.house2),
                findViewById(R.id.house3),
                findViewById(R.id.house4),
        };

        rockets = new ShapeableImageView[][]{
                {findViewById(R.id.rocket0), findViewById(R.id.rocket1), findViewById(R.id.rocket2), findViewById(R.id.rocket3), findViewById(R.id.rocket4)},
                {findViewById(R.id.rocket5), findViewById(R.id.rocket6), findViewById(R.id.rocket7), findViewById(R.id.rocket8), findViewById(R.id.rocket9)},
                {findViewById(R.id.rocket10), findViewById(R.id.rocket11), findViewById(R.id.rocket12), findViewById(R.id.rocket13), findViewById(R.id.rocket14)},
                {findViewById(R.id.rocket15), findViewById(R.id.rocket16), findViewById(R.id.rocket17), findViewById(R.id.rocket18), findViewById(R.id.rocket19)},
                {findViewById(R.id.rocket20), findViewById(R.id.rocket21), findViewById(R.id.rocket22), findViewById(R.id.rocket23), findViewById(R.id.rocket24)},
                {findViewById(R.id.rocket25), findViewById(R.id.rocket26), findViewById(R.id.rocket27), findViewById(R.id.rocket28), findViewById(R.id.rocket29)},
                {findViewById(R.id.rocket30), findViewById(R.id.rocket31), findViewById(R.id.rocket32), findViewById(R.id.rocket33), findViewById(R.id.rocket34)},
                {findViewById(R.id.rocket35), findViewById(R.id.rocket36), findViewById(R.id.rocket37), findViewById(R.id.rocket38), findViewById(R.id.rocket39)}
        };

        israel_coins = new ShapeableImageView[][]{
                {findViewById(R.id.israel_coin0), findViewById(R.id.israel_coin1), findViewById(R.id.israel_coin2), findViewById(R.id.israel_coin3), findViewById(R.id.israel_coin4)},
                {findViewById(R.id.israel_coin5), findViewById(R.id.israel_coin6), findViewById(R.id.israel_coin7), findViewById(R.id.israel_coin8), findViewById(R.id.israel_coin9)},
                {findViewById(R.id.israel_coin10), findViewById(R.id.israel_coin11), findViewById(R.id.israel_coin12), findViewById(R.id.israel_coin13), findViewById(R.id.israel_coin14)},
                {findViewById(R.id.israel_coin15), findViewById(R.id.israel_coin16), findViewById(R.id.israel_coin17), findViewById(R.id.israel_coin18), findViewById(R.id.israel_coin19)},
                {findViewById(R.id.israel_coin20), findViewById(R.id.israel_coin21), findViewById(R.id.israel_coin22), findViewById(R.id.israel_coin23), findViewById(R.id.israel_coin24)},
                {findViewById(R.id.israel_coin25), findViewById(R.id.israel_coin26), findViewById(R.id.israel_coin27), findViewById(R.id.israel_coin28), findViewById(R.id.israel_coin29)},
                {findViewById(R.id.israel_coin30), findViewById(R.id.israel_coin31), findViewById(R.id.israel_coin32), findViewById(R.id.israel_coin33), findViewById(R.id.israel_coin34)},
                {findViewById(R.id.israel_coin35), findViewById(R.id.israel_coin36), findViewById(R.id.israel_coin37), findViewById(R.id.israel_coin38), findViewById(R.id.israel_coin39)}
        };

        odometer = findViewById(R.id.odometer);
        right = findViewById(R.id.right);
        right.setOnClickListener(this::moveRight);
        left = findViewById(R.id.left);
        left.setOnClickListener(this::moveLeft);

        int gameMode = getIntent().getIntExtra("gameMode", GameManager.SLOW_MODE);
        GameManager.init(this, gameMode);

        if (GameManager.getInstance().getGameMode() == GameManager.SENSOR_MODE) {
            this.sensorSetup = new SensorSetup(getApplicationContext());
            sensorSetup.setChangeUI(new ChangeUI() {
                @Override
                public void upDateUISensor() {
                    updateHouse();
                }
            });
            left.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
            this.sensorSetup.startSensor();
        }
    }

    public void updateLives() {
        int currentLives = GameManager.getInstance().getLives();
        for (int i = 0; i < LIVES; i++) {
            lives[i].setVisibility(currentLives >= i + 1 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void timeTick() {
        if (GameManager.getInstance().isGameOver())
            showGameOverScreen();
        else {
            GameManager.getInstance().setRoad();
            GameManager.getInstance().setRocketOrIsraelCoin();
            updateUI();
        }
    }

    public void startGame() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                timeTick();
                handler.postDelayed(this, GameManager.getInstance().getGameMode());
            }
        };
        handler.postDelayed(runnable, GameManager.getInstance().getGameMode());
    }

    @SuppressLint("SetTextI18n")
    public void updateUI() {
        int[][] road = GameManager.getInstance().getRoad();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (road[i][j] > 0) {
                    if (road[i][j] == 1)
                        rockets[i][j].setVisibility(View.VISIBLE);
                    else
                        israel_coins[i][j].setVisibility(View.VISIBLE);
                } else {
                    rockets[i][j].setVisibility(View.INVISIBLE);
                    israel_coins[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
        odometer.setText("Odometer: " + GameManager.getInstance().getOdometer());
        updateLives();
    }

    private void showGameOverScreen() {
        handler.removeCallbacks(runnable);
        Intent intent = new Intent(this, GameOverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", GameManager.getInstance().getScore());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}