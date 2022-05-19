package ood.designpatterns;

import ood.designpatterns.observer.observers.Customer;
import ood.designpatterns.observer.observers.DiscountSite;
import ood.designpatterns.observer.EcommercePlatform;

/**
 * Observer Pattern
 * 观察者模式
 * 定义一种订阅机制, 可在对象事件发生时通知多个"观察"该对象的其他对象
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        EcommercePlatform platform = new EcommercePlatform();
        platform.addSubscriber(new Customer("Kerr"));
        platform.addSubscriber(new Customer("Olsen"));
        platform.addSubscriber(new DiscountSite("SMZDM"));
        platform.newSaleEvent();
    }
}
