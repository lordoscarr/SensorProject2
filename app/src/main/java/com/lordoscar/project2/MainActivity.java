package com.lordoscar.project2;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    public void updateTemp(Integer temperature){
        sensorTemp = temperature;
        TextView sensorTempText = findViewById(R.id.sensorTempText);
        sensorTempText.setText(temperature + " °C");
    }

    public void updateHumidity(Integer humidity){
        sensorHumidity = humidity;
        TextView sensorHumidityText = findViewById(R.id.sensorHumidityText);
        sensorHumidityText.setText(humidity + "%");
    }

    public void updatePressure(Integer pressure){
        sensorPressure = pressure;
        TextView sensorPressureText = findViewById(R.id.sensorPressureText);
        sensorPressureText.setText(pressure + " hPa");
    }

    public void updateApiText(Double temperature, Integer humidity, Integer pressure){
        TextView apiTempText = findViewById(R.id.apiTempText);
        TextView apiHumidityText = findViewById(R.id.apiHumidityText);
        TextView apiPressureText = findViewById(R.id.apiPressureText);
        TextView estimatedAltitudeText = findViewById(R.id.estimatedAltitudeText);

        apiTempText.setText(temperature + "°C (Difference: " + (temperature - sensorTemp) + "°C)");
        apiHumidityText.setText(humidity + "% (Difference: " + (humidity - sensorHumidity) + "%)");
        apiPressureText.setText(pressure + " hPa (Difference: " + (pressure - sensorPressure) + " hPa)");

        float altitude = SensorManager.getAltitude(1014, pressure);
        estimatedAltitudeText.setText(Math.round(altitude) + " meters above sea level");

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorListener.unregisterListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorListener.registerListeners();
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
