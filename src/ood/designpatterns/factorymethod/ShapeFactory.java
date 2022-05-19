package ood.designpatterns.factorymethod;

import ood.designpatterns.factorymethod.products.Circle;
import ood.designpatterns.factorymethod.products.Rectangle;
import ood.designpatterns.factorymethod.products.Shape;
import ood.designpatterns.factorymethod.products.Square;

/**
 * This class is for the factory pattern
 */
public class ShapeFactory {

    // use gatShape method to get object of type shape
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }

        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }

        return null;
    }
}
