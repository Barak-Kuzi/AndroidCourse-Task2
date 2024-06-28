package com.example.rocketapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Objects;

public class GameManager {

    @SuppressLint("StaticFieldLeak")
    public static GameManager game;
    private int lives = 3, score = 0, odometer = 0;
    private boolean isGameOver = false;
    private final Context context;

    private final int ROWS = 8;
    private final int COLS = 5;
    private final int[][] road = new int[ROWS][COLS];
    private int houseIndex = 2; // initial to center;

    private GameManager(Context context) {
        this.context = context;
        this.initRoad();
    }

    public void moveHouse(String movement) {
        if (Objects.equals(movement, "left")) {
            if (houseIndex > 0) {
                houseIndex--;
            }
        } else if (Objects.equals(movement, "right")) {
            if (houseIndex < 4) {
                houseIndex++;
            }
        }
    }

    public void setRocketOrIsraelCoin() {
        int randomCol = (int) (Math.random() * COLS);
        int rocketOrIsraelCoin = (int) (Math.random() * 3); // 0/1 = rocket, 2 = israel_coin
        if (rocketOrIsraelCoin != 2)
            this.road[0][randomCol] = 1;
        else
            this.road[0][randomCol] = 2;
    }


    public static void init(Context context) {
        if (game == null)
            game = new GameManager(context);
    }

    public static GameManager getInstance() {
        return game;
    }

    public void initRoad() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                road[i][j] = 0;
            }
        }
    }

    public void setRoad() {
        updateOdometer();
        for (int i = ROWS - 1; i >= 0; i--) {
            for (int j = 0; j < COLS; j++) {
                if (this.road[i][j] != 0) {
                    if (i == ROWS - 1) {
                        if (houseIndex == j) {
                            if (this.road[i][j] == 1) // A collision with a rocket
                                boom();
                            else if (this.road[i][j] == 2) // A collision with a coin
                                updateScore();
                        }
                    } else {
                        this.road[i + 1][j] = this.road[i][j];
                    }
                }
                road[i][j] = 0;
            }
        }
    }

    private void updateScore() {
        this.score += 10;
        toast("You gained a points\nYour score: " + this.score);
    }

    private void updateOdometer() {
        this.odometer += 1;
    }

    public void boom() {
        this.lives--;
        if (lives == 0) {
            isGameOver = true;
            toast("You lost the game!");
            restartGame();
        } else {
            toast("You lost a life\n" + lives + " lives left");
        }

        vibrate();
    }


    public void restartGame() {
        this.lives = 3;
        this.score = 0;
        this.odometer = 0;
        isGameOver = false;
        initRoad();
    }

    public void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public int getOdometer() {
        return this.odometer;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Context getContext() {
        return context;
    }

    public int[][] getRoad() {
        return road;
    }

    public int getHouseIndex() {
        return houseIndex;
    }
}
