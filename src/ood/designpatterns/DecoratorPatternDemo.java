package ood.designpatterns;

import ood.designpatterns.decorator.decorators.GucciSneaker;
import ood.designpatterns.decorator.decorators.LeatherJacket;
import ood.designpatterns.decorator.Customer;
import ood.designpatterns.decorator.RichCustomer;

/**
 * Decorator Pattern
 * 装饰模式
 * 通过将对象放入包含行为的特殊封装对象中来为原对象绑定新的行为, 可以给某个对象而不是整个類別添加一些功能
 */
public class DecoratorPatternDemo {

    public static void main(String[] args) {
        Customer richCustomer = new RichCustomer("JJ Lin");
        richCustomer = new LeatherJacket(richCustomer);
        richCustomer = new GucciSneaker(richCustomer);
        richCustomer.show();
    }
}
