package com.example.collinhardash.openroom;

/**
 * Created by Collin Hardash on 12/14/2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class DownloadJSON extends IntentService {

    public static final String ACTION_DOWNLOAD = "com.example.collin.openroom.action.download";


    public static final String URL = "url";





    private DatabaseManager manager;

    public DownloadJSON() {
        super("DownloadJSON");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("jsondata","onHandleIntent()");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {

                populateDB(
                        download()

                );
            }
        }
    }


    private String download(){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        manager = new DatabaseManager(this.getApplicationContext());
        Log.d("update","SENDING REQUEST");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = RequestBody.create(JSON,"{\"gps_lat\":37.23062,\"gps_lon\":-80.42178}");
        Request request = new Request.Builder()
                .url("http://192.168.1.70:443/api/request_room")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        Log.d("update", "request sent");

        Response response = null;

        String jsonData = null;

        try {
            response = call.execute();
            Log.d("Response received","hello");
            if (response.isSuccessful()) {
                jsonData = response.body().string();

            } else {
                jsonData = null;
            }

        } catch (IOException e) {
            Log.d("error:","failed");
            e.printStackTrace();
        }
        //Log.d("update data", jsonData);
        return jsonData; //This is returned to onPostExecute()

    }


    private void populateDB(String jsonData) {
        if(jsonData == null){
            return;
        }
        //Log.d("jsondata","can we see it:?"+jsonData);
        manager.open();
        manager.deleteAll();
        JSONObject jsonResponse = null;
        try {

            jsonResponse = new JSONObject(jsonData);

            JSONArray rooms = jsonResponse.getJSONArray("rooms");

            int movie_list_size = rooms.length();



            for (int i = 0; i < movie_list_size; i++) {

                JSONObject data = rooms.getJSONObject(i);

                String building = data.getString("building");
                String room = data.getString("room");
                String capacity = Integer.toString(data.getInt("Capacity"));
                String squareFeet = Integer.toString(data.getInt("Square Feet"));
                String population = Integer.toString(data.getInt("population"));
                Log.d("building"," "+ building);
                Log.d("room", " " + room);

                double distance = data.getDouble("gps_distance");



                if (building != null) {
                    manager.insertRoomInfo(room,building, capacity, population,distance);

                }
            }
            manager.close();

            //broadcasting that it worked

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
        }
        catch (JSONException e) {

            e.printStackTrace();
        }}


}