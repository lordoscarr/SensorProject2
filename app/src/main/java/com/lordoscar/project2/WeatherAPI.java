package com.lordoscar.project2;

import android.content.Context;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherAPI {
    private static final String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String apiKey = "abc78c421fa7510a6486356657bcb876";
    private Context context;

    public WeatherAPI(String city, boolean asynctask, Context context){
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
        JSONObject mainObject = (JSONObject) jsonObject.get("main");
        JSONObject weatherObject = (JSONObject) ((JSONArray) jsonObject.get("weather")).get(0);

        double temp = Double.parseDouble(mainObject.get("temp").toString()) - 273.15;
        int humidity = Integer.parseInt(mainObject.get("humidity").toString());
        int pressure = Integer.parseInt(mainObject.get("pressure").toString());
        String weather = weatherObject.get("main").toString();

        ((MainActivity) context).updateApiText(temp, humidity, pressure, weather);

        Log.d("WEATHER","TEMP:" + temp + ", PRESSURE:" + pressure + ", HUMIDITY: " + humidity);

        Log.d("THE WEATHER IS", weather);
    }
}
