package com.example.matthew.slotmachine;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView num1;
    TextView num2;
    TextView num3;

    Button btnSpin;

    SpinAsyncTask spinAsyncTask;
    SlotMachine slotMachine;
    int currStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);

        btnSpin = (Button) findViewById(R.id.btnSpin);
        btnSpin.setOnClickListener(this);

        slotMachine = new SlotMachine((int) System.currentTimeMillis());
        spinAsyncTask = new SpinAsyncTask();

        currStep = 30;
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        boolean isRunning = inState.getBoolean("thread status");

        if (isRunning) {
            currStep = inState.getInt("thread step");
            spinAsyncTask = new SpinAsyncTask();
            spinAsyncTask.execute(currStep);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (spinAsyncTask != null &&
                spinAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            outState.putInt("thread step", currStep);
            outState.putBoolean("Thread Status", true);

            currStep = 0;
            spinAsyncTask.cancel(true);
        }
    }



    @Override
    public void onClick(View view) {
        //Start Async Task
        spinAsyncTask.execute(30);
    }

    private class SpinAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            int interval = 0;
            currStep = integers[0];

            int count = 30;

            while (currStep > 0) {
                try {
                    slotMachine.spin();

                    publishProgress(slotMachine.getNum1(),
                            slotMachine.getNum2(),
                            slotMachine.getNum3());

                    Thread.sleep(interval);

                    currStep--;
                    interval = (count - currStep) * 10;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            num1.setText(values[0].toString());
            num2.setText(values[1].toString());
            num3.setText(values[2].toString());

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
