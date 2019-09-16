package com.matthewfishman.calculator;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.matthewfishman.calculator.R.id.bequals;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operatorDisplay;
    private boolean neg = false;

    private Double operand1 = null;
    private String pendingOperation = "=";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        operatorDisplay = (TextView) findViewById(R.id.operator);
        newNumber = (EditText) findViewById(R.id.newNumber);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button decimal = (Button) findViewById(R.id.decimal);

        Button multiply = (Button) findViewById(R.id.multiply);
        Button divide = (Button) findViewById(R.id.divide);
        Button add = (Button) findViewById(R.id.add);
        Button subtract = (Button) findViewById(R.id.subtract);
        Button equals = (Button) findViewById(bequals);
        Button negate = (Button) findViewById(R.id.negate);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (button.getText().toString().equals("Neg")) {
                    neg = !(neg);
                    if (neg)
                        newNumber.setText("-" + newNumber.getText().toString());
                    else
                        newNumber.setText(newNumber.getText().toString().substring(1));
                }
                else {
                    newNumber.append(button.getText().toString());
                }
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        decimal.setOnClickListener(listener);
        negate.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    if (neg) {
                        value = value.substring(1);
                    }
                    Double doubleVal = Double.valueOf(value);
                    if (neg) {
                        doubleVal *= -1;
                    }
                    neg = false;
                    performOperation(doubleVal, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                operatorDisplay.setText(pendingOperation);
            }
        };

        equals.setOnClickListener(opListener);
        multiply.setOnClickListener(opListener);
        divide.setOnClickListener(opListener);
        add.setOnClickListener(opListener);
        subtract.setOnClickListener(opListener);

        Button clear = (Button) findViewById(R.id.clear);

        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1 = null;
                result.setText("");
                newNumber.setText("");
                operatorDisplay.setText("");
                pendingOperation = null;
            }
        };

        clear.setOnClickListener(clearListener);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void performOperation(Double number, String operation) {
        operatorDisplay.setText(operation);
        if (operand1 != null) {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
                if (operation.equals("=")) {
                    operand1 = number;
                }
            } else {
                switch (pendingOperation) {
                    case "=":
                        operand1 = number;
                        break;
                    case "+":
                        operand1 += number;
                        break;
                    case "-":
                        operand1 -= number;
                        break;
                    case "*":
                        operand1 *= number;
                        break;
                    case "/":
                        operand1 = (number != 0) ? (operand1 / number) : 0;
                        break;
                    default:
                        break;
                }
            }
        } else {
            operand1 = number;
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("pending operation", pendingOperation);
        if (operand1 != null)
            outState.putDouble("operand", operand1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operatorDisplay.setText(savedInstanceState.getString("pending operation"));
        Double savedValue = savedInstanceState.getDouble("operand");
        result.setText(savedValue.toString());
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}