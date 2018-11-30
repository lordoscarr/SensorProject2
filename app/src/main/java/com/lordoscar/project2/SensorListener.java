package com.lordoscar.project2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

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
        else{
            ((MainActivity) context).noTemp();
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            ((MainActivity) context).noHumidity();
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            ((MainActivity) context).noPressure();
        }
    }

    public void unregisterListeners(){
        sensorManager.unregisterListener(this);
    }

    public void updateTemp(Integer temp, Date date){
        ((MainActivity) context).updateTemp(temp, date);
    }

    public void updateHumidity(Integer humidity, Date date){
        ((MainActivity) context).updateHumidity(humidity, date);
    }

    public void updatePressure(Integer pressure, Date date){
        ((MainActivity) context).updatePressure(pressure, date);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        switch (sensor.getType()){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                updateTemp(Math.round(event.values[0]), getTime(event.timestamp));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                updateHumidity(Math.round(event.values[0]), getTime(event.timestamp));
                break;
            case Sensor.TYPE_PRESSURE:
                updatePressure(Math.round(event.values[0]), getTime(event.timestamp));
                break;
        }
    }

    private Date getTime(long timestamp){
        long timeInMillis = (new Date()).getTime()
                + (timestamp - System.nanoTime()) / 1000000L;

        return new Date(timeInMillis);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Accuracy changed", sensor.getName() + " changed accuracy to " + accuracy);
    }
}
