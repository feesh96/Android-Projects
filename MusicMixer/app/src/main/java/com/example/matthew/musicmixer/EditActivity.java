package com.example.matthew.musicmixer;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {
    Spinner spnSong;
    Spinner spnEffect1;
    Spinner spnEffect2;
    Spinner spnEffect3;
    SeekBar seek1;
    SeekBar seek2;
    SeekBar seek3;
    Button btnPlay;

    Mix currMix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        spnSong = (Spinner) findViewById(R.id.spnSong);
        spnEffect1 = (Spinner) findViewById(R.id.spnEffect1);
        spnEffect2 = (Spinner) findViewById(R.id.spnEffect2);
        spnEffect3 = (Spinner) findViewById(R.id.spnEffect3);
        seek1 = (SeekBar) findViewById(R.id.seek1);
        seek2 = (SeekBar) findViewById(R.id.seek2);
        seek3 = (SeekBar) findViewById(R.id.seek3);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        spnSong.setOnItemSelectedListener(this);
        spnEffect1.setOnItemSelectedListener(this);
        spnEffect2.setOnItemSelectedListener(this);
        spnEffect3.setOnItemSelectedListener(this);
        seek1.setOnSeekBarChangeListener(this);
        seek2.setOnSeekBarChangeListener(this);
        seek3.setOnSeekBarChangeListener(this);
        btnPlay.setOnClickListener(this);

        currMix = new Mix();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Mix", currMix);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        currMix = inState.getParcelable("Mix");
        setScreen(currMix);
    }

    public void setScreen(Mix mix) {
        spnSong.setSelection(getIndexInSpinner(mix.song));
        spnEffect1.setSelection(getIndexInSpinner(mix.effect1));
        spnEffect2.setSelection(getIndexInSpinner(mix.effect2));
        spnEffect3.setSelection(getIndexInSpinner(mix.effect3));
        seek1.setProgress(mix.effect1Time);
        seek2.setProgress(mix.effect2Time);
        seek3.setProgress(mix.effect3Time);
    }

    public int getIndexInSpinner(String songOrEffect) {
        switch (songOrEffect) {
            case "Go Tech Go!":
                return 0;
            case "Hokey Pokey":
                return 1;
            case "Enter Sandman":
                return 2;
            case "Cheering":
                return 0;
            case "Clapping":
                return 1;
            case "Go Hokies!":
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(EditActivity.this,PlayActivity.class);

        intent.putExtra("Mix", currMix);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView != null) {
            if (adapterView.getId() == R.id.spnSong) {
                currMix.song = adapterView.getItemAtPosition(i).toString();
            } else if (adapterView.getId() == R.id.spnEffect1) {
                currMix.effect1 = adapterView.getItemAtPosition(i).toString();
            } else if (adapterView.getId() == R.id.spnEffect2) {
                currMix.effect2 = adapterView.getItemAtPosition(i).toString();
            } else if (adapterView.getId() == R.id.spnEffect3) {
                currMix.effect3 = adapterView.getItemAtPosition(i).toString();
            }
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seek1) {
            currMix.effect1Time = convertProgressToTime(seekBar.getProgress());
        }
        else if (seekBar.getId() == R.id.seek2) {
            currMix.effect2Time = convertProgressToTime(seekBar.getProgress());
        }
        else if (seekBar.getId() == R.id.seek3) {
            currMix.effect3Time = convertProgressToTime(seekBar.getProgress());
        }
    }

    public int convertProgressToTime(int progress) {
        switch (currMix.song) {
            case "Hokey Pokey":
                return progress * 970;
            case "Enter Sandman":
                return progress * 3370;
            default:
                return progress * 490;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
}
