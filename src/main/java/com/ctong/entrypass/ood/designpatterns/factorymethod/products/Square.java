package com.ctong.entrypass.ood.designpatterns.factorymethod.products;

public class Square implements Shape {

    public Square() {
    }

    @Override
    public void draw() {
        System.out.println("Square::draw created a square.");
    }
}
