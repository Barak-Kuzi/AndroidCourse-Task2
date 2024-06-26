package com.example.rocketapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Objects;

public class GameManager {

    @SuppressLint("StaticFieldLeak")
    public static GameManager game;
    private int lives = 3;
    private boolean isGameOver = false;
    private final Context context;

    private final boolean[][] road = new boolean[6][3];
    private int houseIndex = 1;

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
            if (houseIndex < 2) {
                houseIndex++;
            }
        }
    }

    public void setRocket() {
        int random = (int) (Math.random() * 3);
        this.road[0][random] = true;
    }


    public static void init(Context context) {
        if (game == null)
            game = new GameManager(context);
    }

    public static GameManager getInstance() {
        return game;
    }


    public void initRoad() {
        for (boolean[] booleans : road) {
            Arrays.fill(booleans, false);
        }
    }

    public void setRoad() {
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {

                if (rocketExists(i, j)) {
                    if (i == 5) {
                        if (houseIndex == j)
                            boom();
                    } else {
                        this.road[i + 1][j] = this.road[i][j];
                    }
                }
                road[i][j] = false;
            }
        }
    }

    public boolean rocketExists(int i, int j) {
        return this.road[i][j];
    }

    public void boom() {
        lives--;
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
        lives = 3;
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

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Context getContext() {
        return context;
    }

    public boolean[][] getRoad() {
        return road;
    }

    public int getHouseIndex() {
        return houseIndex;
    }
}
