package com.example.matthew.build_a_burger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 9/10/2017.
 */

public class Calculator {
    HashMap<String, Integer> calories = new HashMap<String, Integer>();
    HashMap<String, Double> price = new HashMap();

    public Calculator() {
        calories.put("White Bun", 140);
        calories.put("Wheat Bun", 100);
        calories.put("Beef", 240);
        calories.put("Grilled Chicken", 180);
        calories.put("Turkey", 190);
        calories.put("Salmon", 95);
        calories.put("Veggie", 80);
        calories.put("Lettuce", 20);
        calories.put("Tomato", 20);
        calories.put("Mushrooms", 60);
        calories.put("Pickles", 30);
        calories.put("Mayo", 100);
        calories.put("Mustard", 60);

        price.put("White Bun", 1.0);
        price.put("Wheat Bun", 1.0);
        price.put("Beef", 5.5);
        price.put("Grilled Chicken", 5.0);
        price.put("Turkey", 5.0);
        price.put("Salmon", 7.5);
        price.put("Veggie", 4.5);
        price.put("Lettuce", 0.3);
        price.put("Tomato", 0.3);
        price.put("Mushrooms", 1.0);
        price.put("Pickles", 0.5);
        price.put("Mayo", 0.0);
        price.put("Mustard", 0.0);
    }

    public double getPrice(Burger burger) {
        double total = 0;

        total += price.get(burger.bun);
        total += price.get(burger.patty);
        for(String topping : burger.toppings) {
            total += price.get(topping);
        }

        return total;
    }

    public int getCalories(Burger burger) {
        int total = 0;

        total += calories.get(burger.bun);
        total += calories.get(burger.patty);
        for(String topping : burger.toppings) {
            total += calories.get(topping);
        }

        return total;
    }
}