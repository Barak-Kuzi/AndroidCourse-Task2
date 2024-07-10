package com.example.rocketapp.GameUtility;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.rocketapp.GameManager;
import com.example.rocketapp.Interfaces.ChangeUI;

public class SensorSetup {
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener; // Interface
    private Sensor sensor;

    private ChangeUI changeUI;

    public void setChangeUI(ChangeUI changeUI) {
        this.changeUI = changeUI;
    }

    public SensorSetup(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                final int LEFT_TILT = 2;
                final int RIGHT_TILT = -2;
                final float FORWARD_TILT = 2.0f;
                final float BACKWARD_TILT = -2.0f;

                if (event.values[0] > LEFT_TILT){
                    GameManager.getInstance().moveHouse(GameManager.LEFT);
                    changeUI.upDateUISensor();
                }

                else if (event.values[0] < RIGHT_TILT) {
                    GameManager.getInstance().moveHouse(GameManager.RIGHT);
                    changeUI.upDateUISensor();
                }

                // Bonus implementation

                // Slowdown
                if (event.values[1] > FORWARD_TILT) {
                   GameManager.getInstance().setGameMode(Math.min(GameManager.getInstance().getGameMode() + 100, 1500));
                }

                else if (event.values[1] < BACKWARD_TILT) {
                    GameManager.getInstance().setGameMode(Math.max(GameManager.getInstance().getGameMode() - 100, 500));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }

    public void startSensor() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensor() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}
