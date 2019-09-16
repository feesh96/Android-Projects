package com.example.matthew.weatherapp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.matthew.weatherapp.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matthew on 10/5/2017.
 */

public class WeatherAPI implements Response.Listener<String>, Response.ErrorListener{
    String requestURL = "http://api.openweathermap.org/data/2.5/weather";
    String key = "79095b87aa25b99305df0c7df90d4772";
    String unit = "imperial";

    MainActivity mainActivity;

    RequestQueue requestQueue;

    public WeatherAPI(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        requestQueue = Volley.newRequestQueue(mainActivity);
    }

    public void getInfo(String query) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getRequest(query), this, this);
        requestQueue.add(stringRequest);
    }

    private String getRequest(String query) {
        return requestURL + "?q=" + query + "&appid=" + key + "&units=" + unit;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String mainString = jsonObject.getString("main");
            String name = jsonObject.getString("name");
            JSONObject mainObject = new JSONObject(mainString);
            double tempersture = mainObject.getDouble("temp");
            double humidity = mainObject.getDouble("humidity");
            mainActivity.saveInfo(name, tempersture, humidity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
