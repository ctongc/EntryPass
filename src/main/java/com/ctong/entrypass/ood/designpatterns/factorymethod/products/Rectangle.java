package com.ctong.entrypass.ood.designpatterns.factorymethod.products;

public class Rectangle implements Shape {

    public Rectangle(){
    }

    @Override
    public void draw() {
        System.out.println("Rectangle::draw created a rectangle.");
    }
}
