package com.example.matthew.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener{

    DeptInformation deptInfo;
    public static String INFORMATION = "information";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deptInfo = new DeptInformation();
    }

    @Override
    public void onButtonClicked(int infoID){
        InformationFragment informationFragment = (InformationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.newsFrag);
        if(informationFragment != null && informationFragment.isInLayout()){
            //Landscape mode
            informationFragment.setText(deptInfo.getInfo(infoID));
        }
        else{
            //Portraio Mode
            Intent intent = new Intent(this, InformationFragment.class);
            intent.putExtra(INFORMATION, deptInfo.getInfo(infoID));
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
