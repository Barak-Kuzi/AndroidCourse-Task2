package com.example.rocketapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorSetup {
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener; // Interface
    private Sensor sensor;

    public SensorSetup(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] > 1)
                    GameManager.getInstance().moveHouse(GameManager.LEFT);
                else if (event.values[0] < -1)
                    GameManager.getInstance().moveHouse(GameManager.RIGHT);
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
