package com.example.shuo.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        ListFragment.OnFragmentInteractionListener,
        InformationFragment.OnFragmentInteractionListener {

    DeptInformation deptInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deptInformation = new DeptInformation();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public final static String INFORMATION = "information";

    @Override
    public void onButtonClicked(int infoID) {

    }
}
