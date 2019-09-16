package com.example.collinhardash.openroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button btnFindRooms;
    Spinner spnInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnFindRooms = (Button) findViewById(R.id.btnFindRooms);
        btnFindRooms.setOnClickListener(this);
        spnInstitution = (Spinner) findViewById(R.id.spnInstitution);
        spnInstitution.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!spnInstitution.getSelectedItem().toString().equals("Virginia Tech")) {
            Toast.makeText(this, "Institution not supported yet", Toast.LENGTH_SHORT).show();
            spnInstitution.setSelection(0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}