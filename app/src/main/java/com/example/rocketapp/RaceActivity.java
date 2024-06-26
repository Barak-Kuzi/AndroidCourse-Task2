package com.example.rocketapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.imageview.ShapeableImageView;

public class RaceActivity extends AppCompatActivity {


    private ShapeableImageView[] lives;
    private ShapeableImageView[] houses;
    private ShapeableImageView[][] rockets;
    private AppCompatImageButton left, right;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startGame();
    }

    public void moveLeft(View view) {
        GameManager.getInstance().moveHouse("left");
        updateHouse();
    }

    public void moveRight(View view) {
        GameManager.getInstance().moveHouse("right");
        updateHouse();
    }

    public void updateHouse() {

        int index = GameManager.getInstance().getHouseIndex();

        for (int i = 0; i < 3; i++) {
            houses[i].setVisibility(i == GameManager.getInstance().getHouseIndex() ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void init() {
        GameManager.init(this);
        lives = new ShapeableImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        houses = new ShapeableImageView[]{
                findViewById(R.id.leftHouse),
                findViewById(R.id.centerHouse),
                findViewById(R.id.rightHouse)
        };

        rockets = new ShapeableImageView[][]{
                {findViewById(R.id.rocket1), findViewById(R.id.rocket2), findViewById(R.id.rocket3)},
                {findViewById(R.id.rocket4), findViewById(R.id.rocket5), findViewById(R.id.rocket6)},
                {findViewById(R.id.rocket7), findViewById(R.id.rocket8), findViewById(R.id.rocket9)},
                {findViewById(R.id.rocket10), findViewById(R.id.rocket11), findViewById(R.id.rocket12)},
                {findViewById(R.id.rocket13), findViewById(R.id.rocket14), findViewById(R.id.rocket15)},
                {findViewById(R.id.rocket16), findViewById(R.id.rocket17), findViewById(R.id.rocket18)}
        };

        right = findViewById(R.id.right);
        right.setOnClickListener(this::moveRight);
        left = findViewById(R.id.left);
        left.setOnClickListener(this::moveLeft);
    }

    public void updateLives() {

        int currentLives = GameManager.getInstance().getLives();

        for (int i = 0; i < 3; i++) {
            lives[i].setVisibility(currentLives >= i+1 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void timeTick() {
        GameManager.getInstance().setRoad();
        GameManager.getInstance().setRocket();
        updateUI();
    }

    public void startGame() {
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                timeTick();
                handler.postDelayed(this, 800);
            }
        };

        handler.postDelayed(runnable, 800);
    }

    public void updateUI() {

        boolean[][] road = GameManager.getInstance().getRoad();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                rockets[i][j].setVisibility(road[i][j] ? View.VISIBLE : View.INVISIBLE);
            }
        }

        updateLives();

    }


}