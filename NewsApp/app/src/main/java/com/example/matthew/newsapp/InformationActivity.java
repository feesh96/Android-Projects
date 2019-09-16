package com.example.matthew.newsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        setContentView(R.layout.fragment_information);
        Intent intent = getIntent();
        String info = intent.getStringExtra(MainActivity.INFORMATION);
        TextView textView = (TextView) findViewById(R.id.news);
        textView.setText(info);
    }


}
