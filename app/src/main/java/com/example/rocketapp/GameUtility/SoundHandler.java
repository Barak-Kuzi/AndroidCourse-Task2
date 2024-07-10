package com.example.rocketapp.GameUtility;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundHandler {

    private Context context;
    private Executor executor;

    public SoundHandler(Context context) {
        this.context = context.getApplicationContext();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSound(int resID) {
        executor.execute(() -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, resID);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mediaPlayerEvent -> {
                mediaPlayerEvent.stop();
                mediaPlayerEvent.release();
                mediaPlayerEvent = null;
            });
        });
    }

}
