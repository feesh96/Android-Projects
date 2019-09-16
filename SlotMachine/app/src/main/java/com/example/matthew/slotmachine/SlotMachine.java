package com.example.matthew.slotmachine;

import java.util.Random;

/**
 * Created by Matthew on 9/26/2017.
 */

public class SlotMachine {

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    int num1;
    int num2;
    int num3;
    Random random;



    public SlotMachine(int seed) {
        random = new Random(seed);
    }

    public void spin() {
        num1 = random.nextInt(10);
        num2 = random.nextInt(10);
        num3 = random.nextInt(10);
    }

    public String check() {
        if (num1 == num2 && num2 == num3) {
            return "You win $10!";
        }

        else if (num1 == num2 || num2 == num3 || num1 == num2) {
            return "You win $5!";
        }

        else {
            return "Try again!";
        }
    }
}
