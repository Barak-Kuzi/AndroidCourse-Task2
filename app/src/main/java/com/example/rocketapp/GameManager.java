package com.example.rocketapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
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
    private final int NUM_OF_ELEMENTS = 3;
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final int SLOW_MODE = 900;
    public static final int FAST_MODE = 450;
    public static final int SENSOR_MODE = 800;
    private int gameMode;
    private final MediaPlayer rocketCollision;

    private GameManager(Context context, int gameMode) {
        this.context = context;
        this.gameMode = gameMode;
        this.initRoad();
        this.rocketCollision = MediaPlayer.create(context, R.raw.rocket_collision);
    }

    public static void init(Context context, int gameMode) {
        if (game == null || getInstance().getContext() != context)
            game = new GameManager(context, gameMode);
    }

    public void moveHouse(String movement) {
        if (Objects.equals(movement, LEFT)) {
            if (houseIndex > 0) {
                houseIndex--;
            }
        } else if (Objects.equals(movement, RIGHT)) {
            if (houseIndex < 4) {
                houseIndex++;
            }
        }
    }

    public void setRocketOrIsraelCoin() {
        int randomCol = (int) (Math.random() * COLS);
        int rocketOrIsraelCoin = (int) (Math.random() * NUM_OF_ELEMENTS); // 0/1 = rocket, 2 = israel_coin
        if (rocketOrIsraelCoin != 2)
            this.road[0][randomCol] = 1;
        else
            this.road[0][randomCol] = 2;
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
        this.rocketCollision.start();
        this.lives--;
        if (lives == 0) {
            this.isGameOver = true;
            toast("You lost the game!");
//            restartGame();
        } else {
            toast("You lost a life\n" + lives + " lives left");
        }

        vibrate();
    }

    public void restartGame() {
        this.lives = 3;
        this.score = 0;
        this.odometer = 0;
        this.isGameOver = false;
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

    public int getGameMode() {
        return this.gameMode;
    }

    public int getScore() {
        return this.score;
    }

}
