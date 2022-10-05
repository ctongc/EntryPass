package com.ctong.entrypass.ood.designpatterns;

import com.ctong.entrypass.ood.designpatterns.abstractfactory.Application;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.factories.GuiFactory;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.factories.MacFactory;
import com.ctong.entrypass.ood.designpatterns.abstractfactory.factories.WinFactory;

/**
 * Abstract Factory Pattern
 * 抽象工厂模式
 * 将一组具有同一主题的单独的工厂封装起来, 可以将一组对象的实现细节与他们的一般使用分离开来
 * 优点: 客户端与具体要创建的产品解耦, 扩展性和灵活性高
 * 缺点: 增加要创建的对象时, 需要增加的代码较多, 会使系统变得较为复杂
 *
 * 简单工厂和抽象工厂除了结构上的区别，主要区别在于使用场景不同:
 * 简单工厂
 * - 用于创建单一产品, 将所有子类的创建过程集中在一个工厂中, 修改时只需修改一个工厂即可
 * - 简单工厂经常和单例模式一起使用, 例如用简单工厂创建缓存对象(文件缓存), 需要改用redis缓存, 修改工厂即可
 * 抽象工厂
 * - 常用于创建一整个产品族而不是单一产品, 通过选择不同的工厂来达到目的, 其优势在于可以通过替换工厂而
 *   快速替换整个产品族, 例如 Mac工厂生产MacButton，Win工厂生产WinButton
 */
public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {
        // choose the factory
        GuiFactory macFactory = new MacFactory();
        Application macApp = new Application(macFactory);
        // provide product based on the factory chose
        macApp.paint();

        GuiFactory winFactory = new WinFactory();
        Application winApp = new Application(winFactory);
        winApp.paint();
    }
}
