package com.lordoscar.project2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpVolley extends Volley {

    private Context context;
    private String url;
    private WeatherAPI api;

    public HttpVolley(String url, Context context, WeatherAPI api){
        this.url = url;
        this.context = context;
        this.api = api;
    }

    public void volleyRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSON", response);
                try {
                    api.parseJSON(response);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley error", error.toString());
                }
            });
        queue.add(stringRequest);

        queue.start();
    }
}
