package com.example.matthew.build_a_burger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    RadioGroup rgBun;
    RadioButton whiteBun;
    RadioButton wheatBun;
    Spinner pattyType;
    CheckBox cbLettuce;
    CheckBox cbTomato;
    CheckBox cbMayo;
    CheckBox cbPickles;
    CheckBox cbMushrooms;
    CheckBox cbMustard;
    EditText txtQuantity;
    TextView txtCalories;
    TextView txtCost;

    List<CheckBox> currentToppings;
    HashMap<View, String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentToppings = new ArrayList<CheckBox>();
        strings = new HashMap<View, String>();

        rgBun = (RadioGroup) findViewById(R.id.rgBun);
        whiteBun = (RadioButton) findViewById(R.id.whiteBun);
        wheatBun = (RadioButton) findViewById(R.id.wheatBun);
        pattyType = (Spinner) findViewById(R.id.pattyType);
        cbLettuce = (CheckBox) findViewById(R.id.cbLettuce);
        cbTomato = (CheckBox) findViewById(R.id.cbTomato);
        cbMayo = (CheckBox) findViewById(R.id.cbMayo);
        cbPickles = (CheckBox) findViewById(R.id.cbPickles);
        cbMushrooms = (CheckBox) findViewById(R.id.cbMushrooms);
        cbMustard = (CheckBox) findViewById(R.id.cbMustard);
        txtQuantity = (EditText) findViewById(R.id.txtQuantity);
        txtCalories = (TextView) findViewById(R.id.txtCalories);
        txtCost = (TextView) findViewById(R.id.txtCost);

        cbLettuce.setOnCheckedChangeListener(this);
        cbTomato.setOnCheckedChangeListener(this);
        cbMayo.setOnCheckedChangeListener(this);
        cbPickles.setOnCheckedChangeListener(this);
        cbMushrooms.setOnCheckedChangeListener(this);
        cbMustard.setOnCheckedChangeListener(this);
        txtQuantity.setOnClickListener(this);
        rgBun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                updateBurger();
            }
        });
        pattyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateBurger();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        strings.put(whiteBun, "White Bun");
        strings.put(wheatBun, "Wheat Bun");
        strings.put(cbLettuce, "Lettuce");
        strings.put(cbTomato, "Tomato");
        strings.put(cbMayo, "Mayo");
        strings.put(cbPickles, "Pickles");
        strings.put(cbMushrooms, "Mushrooms");
        strings.put(cbMustard, "Mustard");
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("MainActivity", "OnCheckedChanged");
        if (compoundButton.getId() == cbLettuce.getId()) {
            if (b)
                currentToppings.add(cbLettuce);
            else
                currentToppings.remove(cbLettuce);
        }
        else if (compoundButton.getId() == cbTomato.getId()) {
            if (b)
                currentToppings.add(cbTomato);
            else
                currentToppings.remove(cbTomato);
        }
        else if (compoundButton.getId() == cbMustard.getId()) {
            if (b)
                currentToppings.add(cbMustard);
            else
                currentToppings.remove(cbMustard);
        }
        else if (compoundButton.getId() == cbPickles.getId()) {
            if (b)
                currentToppings.add(cbPickles);
            else
                currentToppings.remove(cbPickles);
        }
        else if (compoundButton.getId() == cbMushrooms.getId()) {
            if (b)
                currentToppings.add(cbMushrooms);
            else
                currentToppings.remove(cbMushrooms);
        }
        else if (compoundButton.getId() == cbMayo.getId()) {
            if (b)
                currentToppings.add(cbMayo);
            else
                currentToppings.remove(cbMayo);
        }

        updateBurger();
    }

    @Override
    public void onClick(View view) {
        updateBurger();
    }

    public void updateBurger() {
        if (currentToppings.size() > 3) {
            Toast.makeText(this,"Limit of 3 toppings", Toast.LENGTH_SHORT).show();
            txtCalories.setText("Calories: -");
            txtCost.setText("$-");
        }
        else if (checkCompletion()) {
            String bun = strings.get(findViewById(rgBun.getCheckedRadioButtonId()));
            String patty = pattyType.getSelectedItem().toString();
            List<String> toppings = new ArrayList<>();
            for(View topping : currentToppings) {
                toppings.add(strings.get(topping));
            }

            Burger burger = new Burger(bun, patty, toppings);
            Calculator calculator = new Calculator();

            double price = calculator.getPrice(burger);
            int amount = Integer.parseInt(txtQuantity.getText().toString());
            double totalCost = price * amount;
            String stringCost = "$" + String.format("%.2f", totalCost);
            txtCost.setText(stringCost);

            int calories = calculator.getCalories(burger);
            String stringCalories = "Calories: " + calories;
            txtCalories.setText(stringCalories);
        }
    }

    public boolean checkCompletion() {
        if (rgBun.getCheckedRadioButtonId() != -1) {
            if (pattyType.getSelectedItem() != null) {
                if (!TextUtils.isEmpty(txtQuantity.getText().toString())) {
                    Log.d("checkCompletion", "Number is input");
                    return true;
                }
            }
        }
        return false;
    }
}
