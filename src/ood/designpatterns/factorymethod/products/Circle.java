package ood.designpatterns.factorymethod.products;

public class Circle implements Shape {

    public Circle() {
    }

    @Override
    public void draw() {
        System.out.println("Circle::draw created a circle.");
    }
}
