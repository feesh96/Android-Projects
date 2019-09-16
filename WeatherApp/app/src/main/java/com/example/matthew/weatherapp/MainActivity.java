package com.example.matthew.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;
    EditText city;
    TextView summary;

    WeatherAPI weatherAPI;
    DatabaseManager databaseManager;

    @Override
    protected void onResume() {
        super.onResume();
        databaseManager.open();
        updateInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.close();
    }

    public void onSubmit(View view) {
        weatherAPI.getInfo(city.getText().toString());
    }

    public void onClear(View view) {
        if (databaseManager != null) {
            databaseManager.deleteAll();
            updateInfo();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.container);
        city = (EditText) findViewById(R.id.city);
        summary = (TextView) findViewById(R.id.summary);

        weatherAPI = new WeatherAPI(this);
        databaseManager = new DatabaseManager(this);
    }

    public void saveInfo(String name, double temp, double hum) {
        databaseManager.insertWeatherInfo(name, (float) temp, (float) hum);
        updateInfo();
    }

    private void updateInfo() {
        summary.setText("Highest temperature: " + databaseManager.getHighestTemp() +
                "\nAverage humidity: " + databaseManager.getAvgHum());

        container.removeAllViewsInLayout();
        List<String> records = databaseManager.getAllRecords();

        for (String r : records) {
            TextView textView = new TextView(this);
            textView.setText(r);
            textView.setTextSize(20);
            container.addView(textView);
        }
    }
}
