package com.example.rocketapp.GameUtility;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class GameFeedbackHandler {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

}
