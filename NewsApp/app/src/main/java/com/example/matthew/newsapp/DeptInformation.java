package com.example.matthew.newsapp;

/**
 * Created by sarj21 on 9/14/2017.
 */

public class DeptInformation {

    String[] info=new String[]{
            "The undergraduate program in Computer Science at Virginia Tech is both challenging and rewarding. The objectives of the program are to provide majors with a balanced breadth and depth of knowledge in computer science that allows them the choice between continuing their education in graduate school and beginning their professional career, and to excel in either environment.",
            "From finding tumor edges for a class assignment to research on autonomous vehicles, electrical and computer engineering (ECE) students are studying and developing technology that improves lives."
    };


    public String getInfo(int index){
        return info[index];
    }
}
