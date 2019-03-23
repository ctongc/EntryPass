package ood.designpatterns;

import ood.designpatterns.factory.Shape;
import ood.designpatterns.factory.ShapeFactory;

public class FactoryPatternDemo {
    private static final ShapeFactory shapeFactory = new ShapeFactory();

    public static void main(String[] args) {
        // get an object of Circle and call its draw method
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();

        // get an object of Circle and call its draw method
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        shape2.draw();

        // get an object of Circle and call its draw method
        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape3.draw();
    }
}
