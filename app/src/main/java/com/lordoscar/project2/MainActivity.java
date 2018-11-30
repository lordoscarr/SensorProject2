package com.lordoscar.project2;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SensorListener sensorListener;
    private Switch taskSwitch;
    private TextView taskText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorListener = new SensorListener(this);

        Button compareButton = findViewById(R.id.compareButton);
        compareButton.setOnClickListener(new ButtonListener());
        taskSwitch = findViewById(R.id.taskSwitch);
        taskText = findViewById(R.id.taskText);
    }

    private int sensorTemp = 0;
    private int sensorHumidity = 0;
    private int sensorPressure = 0;
    private static DecimalFormat df = new DecimalFormat(".##");

    public void updateTemp(Integer temperature, Date date){
        sensorTemp = temperature;
        TextView sensorTempText = findViewById(R.id.sensorTempText);
        sensorTempText.setText(temperature + " °C");
        updateSensorDate(date);
    }

    public void updateHumidity(Integer humidity, Date date){
        sensorHumidity = humidity;
        TextView sensorHumidityText = findViewById(R.id.sensorHumidityText);
        sensorHumidityText.setText(humidity + "%");
        updateSensorDate(date);
    }

    public void updatePressure(Integer pressure, Date date){
        sensorPressure = pressure;
        TextView sensorPressureText = findViewById(R.id.sensorPressureText);
        sensorPressureText.setText(pressure + " hPa");
        updateSensorDate(date);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    private void updateSensorDate(Date date){
        TextView sensorUpdateText = findViewById(R.id.sensorUpdateText);
        sensorUpdateText.setText("Last updated " + dateFormat.format(date));
    }

    public void updateApiText(Double temperature, Integer humidity, Integer pressure, String weather){
        TextView apiTempText = findViewById(R.id.apiTempText);
        TextView apiHumidityText = findViewById(R.id.apiHumidityText);
        TextView apiPressureText = findViewById(R.id.apiPressureText);
        TextView estimatedAltitudeText = findViewById(R.id.estimatedAltitudeText);
        TextView weatherText = findViewById(R.id.weatherText);

        apiTempText.setText(df.format(temperature) + "°C (Difference: " + df.format((temperature - sensorTemp)) + "°C)");
        apiHumidityText.setText(humidity + "% (Difference: " + (humidity - sensorHumidity) + "%)");
        apiPressureText.setText(pressure + " hPa (Difference: " + (pressure - sensorPressure) + " hPa)");
        weatherText.setText(weather);

        float altitude = SensorManager.getAltitude(1014, pressure);
        estimatedAltitudeText.setText(Math.round(altitude) + " meters above sea level");

        TextView apiUpdateText = findViewById(R.id.apiUpdateText);
        apiUpdateText.setText("Last updated " + dateFormat.format(new Date()));
    }

    public void noTemp(){
        TextView sensorTempText = findViewById(R.id.sensorTempText);
        sensorTempText.setText("No sensor available.");
    }

    public void noHumidity(){
        TextView sensorHumidityText = findViewById(R.id.sensorHumidityText);
        sensorHumidityText.setText("No sensor available.");
    }

    public void noPressure(){
        TextView sensorPressureText = findViewById(R.id.sensorPressureText);
        sensorPressureText.setText("No sensor available.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorListener.unregisterListeners();
        Toast.makeText(this, "Unregistered listeners.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorListener.registerListeners();
        Toast.makeText(this, "Registered listeners.", Toast.LENGTH_SHORT).show();
    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            boolean asynctask = taskSwitch.isChecked();

            new WeatherAPI("Malmö", asynctask, MainActivity.this);

            taskText.setText("API fetched via AsyncTask");
            if(!asynctask)
                taskText.setText("API fetched via Volley");
        }
    }
}
