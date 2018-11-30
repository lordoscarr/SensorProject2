package com.lordoscar.project2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener {

    private Context context;

    public SensorListener(Context context){
        this.context = context;
    }

    public void updateSensorText(String temp, String humidity, String pressure){
        ((MainActivity) context).updateSensorText(temp,humidity, pressure);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
