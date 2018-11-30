package com.lordoscar.project2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorListener implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;
    private Sensor tempSensor = null;
    private Sensor humiditySensor = null;
    private Sensor pressureSensor = null;


    public SensorListener(Context context){
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void registerListeners(){
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListeners(){
        sensorManager.unregisterListener(this);
    }

    public void updateTemp(Integer temp){
        ((MainActivity) context).updateTemp(temp);
    }

    public void updateHumidity(Integer humidity){
        ((MainActivity) context).updateHumidity(humidity);
    }

    public void updatePressure(Integer pressure){
        ((MainActivity) context).updatePressure(pressure);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        switch (sensor.getType()){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                updateTemp(Math.round(event.values[0]));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                updateHumidity(Math.round(event.values[0]));
                break;
            case Sensor.TYPE_PRESSURE:
                updatePressure(Math.round(event.values[0]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Accuracy changed", sensor.getName() + " changed accuracy to " + accuracy);
    }
}
