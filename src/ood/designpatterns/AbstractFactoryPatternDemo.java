package ood.designpatterns;

import ood.designpatterns.abstractfactory.Application;
import ood.designpatterns.abstractfactory.GuiFactory;
import ood.designpatterns.abstractfactory.MacFactory;
import ood.designpatterns.abstractfactory.WinFactory;

public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {
        GuiFactory f1 = new MacFactory();
        Application app1 = new Application(f1);
        app1.makeButton();

        GuiFactory f2 = new WinFactory();
        Application app2 = new Application(f2);
        app2.makeButton();
    }
}
