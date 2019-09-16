package com.example.matthew.build_a_burger;

import java.util.List;

/**
 * Created by Matthew on 9/10/2017.
 */

public class Burger {
    List<String> toppings;
    String bun;
    String patty;

    public Burger(String bun, String patty, List<String> toppings) {
        this.bun = bun;
        this.patty = patty;
        this.toppings = toppings;
    }
}
