package com.lordoscar.project2;

import android.content.Context;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherAPI {
    private static final String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String apiKey = "abc78c421fa7510a6486356657bcb876";
    private String city;
    private double temp = 0;
    private int pressure = 0;
    private int humidity = 0;
    private Context context;

    public WeatherAPI(String city, boolean asynctask, Context context){
        this.city=city;
        this.context = context;
        try {
            String apiJson;
            if(asynctask) {
                apiJson = new HttpTask().execute(apiUrl + city + "&APPID=" + apiKey).get();
                parseJSON(apiJson);
            }else {
                HttpVolley volley = new HttpVolley(apiUrl + city + "&APPID=" +apiKey, context, this);
                volley.volleyRequest();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void parseJSON(String json) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject weatherObject = (JSONObject) jsonObject.get("main");

        temp = Double.parseDouble(weatherObject.get("temp").toString()) - 273.15;
        humidity = Integer.parseInt(weatherObject.get("humidity").toString());
        pressure = Integer.parseInt(weatherObject.get("pressure").toString());

        ((MainActivity) context).updateApiText(temp, humidity, pressure);

        Log.d("WEATHER","TEMP:" + temp + ", PRESSURE:" + pressure + ", HUMIDITY: " + humidity);
    }
}
