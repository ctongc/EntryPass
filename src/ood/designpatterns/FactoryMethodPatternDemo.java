package ood.designpatterns;

import ood.designpatterns.factorymethod.products.Shape;
import ood.designpatterns.factorymethod.ShapeFactory;

/**
 * Factory Method Pattern
 * 工厂方法模式
 * 在父类中提供一个创建对象的方法, 允许子类决定实例化对象的类型
 * 创建对象时不会对客户端暴露创建逻辑, 而是通过使用一个共同的接口来指向新创建的对象
 */
public class FactoryMethodPatternDemo {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        // get an object of Circle and call its draw method
        Shape circle = shapeFactory.getShape("CIRCLE");
        circle.draw();

        // get an object of Rectangle and call its draw method
        Shape rectangle = shapeFactory.getShape("RECTANGLE");
        rectangle.draw();

        // get an object of Square and call its draw method
        Shape square = shapeFactory.getShape("SQUARE");
        square.draw();
    }
}
